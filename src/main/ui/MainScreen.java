package main.ui;

import dorkbox.systemTray.MenuItem;
import dorkbox.systemTray.SystemTray;
import main.model.Game;
import main.service.AutoBackupService;
import main.service.BackupService;
import main.service.GameRepository;
import main.ui.theme.AppTheme;
import net.miginfocom.swing.MigLayout;
import main.ui.components.RoundButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import dorkbox.systemTray.*;
import java.util.Map;

public class MainScreen extends JFrame {
    private JPanel gridPanel;
    private GameRepository gameRepository;
    private Map<String, AutoBackupService> activeBackupServices = new HashMap<>();

    //GUARDA A MAINSCREEN ORIGINAL
    private Container originalPanel;

    public MainScreen(){
        this.gameRepository = new GameRepository();

        autoBackupsInit();

        ToolTipManager.sharedInstance().setInitialDelay(0);
        UIManager.put("ToolTip.background", AppTheme.BG_MAIN.getColor());
        UIManager.put("ToolTip.foreground", AppTheme.PRIMARY.getColor());
        UIManager.put("Tooltip.border", BorderFactory.createLineBorder(AppTheme.PRIMARY.getColor()));

        //PAINEL DOS CARDS
        gridPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 20, 20));
        gridPanel.setBackground(AppTheme.BG_MAIN.getColor());
        //ICON
        URL iconURL = getClass().getResource("/icons/mIcon256.png");
        if(iconURL != null){
            ImageIcon appIcon = new ImageIcon(iconURL);
            setIconImage(appIcon.getImage());
        }
        else {
            System.out.println("Erro: Icone não encontrado!");
        }


        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);


        setTitle("Memoir");
        setSize(1560, 960);
        setLocationRelativeTo(null);
        setMinimumSize(new java.awt.Dimension(1024, 768));
        systemTraySetup();

        JPanel panel = new JPanel(new MigLayout("wrap 2"));
        panel.setBackground(Color.decode("#101213"));

        //Titulo
        JLabel lblTitle = new JLabel("Memoir");
        lblTitle.setFont(FontUtils.importFont("/fonts/Orbitron-VariableFont_wght.ttf", 70));
        lblTitle.setBorder(new EmptyBorder(0, 10, 10 ,10));
        lblTitle.setForeground(AppTheme.PRIMARY.getColor());
        panel.add(lblTitle);
        //button
        RoundButton rb = new RoundButton("ADD GAME", AppTheme.PRIMARY, AppTheme.TEXT_BLACK,44, 20);
        rb.setFont(FontUtils.importFont("/fonts/Orbitron-VariableFont_wght.ttf", 20));
        panel.add(rb, "pushx, align right, wrap, gapright 20");
        add(panel, BorderLayout.NORTH);

        //ADD GAME Panel
        rb.addActionListener(e -> {
            AddGame ad = new AddGame(MainScreen.this);
            ad.setModal(true);
            ad.setVisible(true);
            loadgamesToGrid();
        });

        loadgamesToGrid();
        //SALVA TUDO DO MAINSCREEN NO ORIGINAL
        this.originalPanel = this.getContentPane();

    }

    public void changeScreen(JPanel newScreen){
        this.setContentPane(newScreen);
        this.revalidate();
        this.repaint();
    }

    public void restoreMainScreenState() {
        this.setContentPane(originalPanel);
        loadgamesToGrid();
        this.revalidate();
        this.repaint();
    }

    public void loadgamesToGrid(){
        gridPanel.removeAll();

        List<Game> savedGames = gameRepository.loadAll();
        //REVERTER A LISTA PARA OS CARD APARECER DA DIREITA PARA ESQUERDA
        Collections.reverse(savedGames);
        if (!savedGames.isEmpty()) {
            for (Game game : savedGames){
                GameCard card = new GameCard(game, clickedGame -> {
                    System.out.println("Abrindo Painel de backup: " + clickedGame.getName());
                    InfoGameScreen infoGameScreen = new InfoGameScreen(MainScreen.this, game);
                    changeScreen(infoGameScreen);
                });
                gridPanel.add(card);
            }
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private void autoBackupsInit() {
        List<Game> savedGames = gameRepository.loadAll();

        for (Game game : savedGames) {
            if (game.isAutoBackupEnabled()) {
                startAutoBackupForGame(game);
            }
        }
    }

    public void startAutoBackupForGame(Game game) {
        // Só inicia se já não tiver um rodando para esse jogo
        if (!activeBackupServices.containsKey(game.getName())) {

            String exeName = "";
            if (game.getGamePath() != null && !game.getGamePath().isEmpty()) {
                exeName = new File(game.getGamePath()).getName();
            }

            BackupService bkService = new BackupService(game);
            AutoBackupService abs = new AutoBackupService(exeName, bkService);

            abs.beginWatch();

            // Guarda na lista de serviços ativos
            activeBackupServices.put(game.getName(), abs);
        }
    }
    public void stopAutoBackupForGame(Game game) {
        // Tenta pegar o serviço na lista e remove ele
        AutoBackupService abs = activeBackupServices.remove(game.getName());
        if (abs != null) {
            abs.stopWatch();
        }
    }


    public void systemTraySetup()
    {
        /*  POR ALGUMA RAZÃO O SYSTEMTRAY NATIVO NÃO FUNCIONA NO KDE PLASMA 6.7 NOBARA
            ENTÃO OPTEI POR UTILIZAR A FERRAMENTA DO DORKBOX QUE FUNCIONOU CORRETAMENTE
            AE O DORKBOX NÃO FUNCIONOU NO WINDOWS 10 E O NATIVO FUNCIONOU...
            ENTAO UTILIZEI OS DOIS METODOS, QUE VIROU ESSA ABERRACAO ABAIXO

         */
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            //WINDOWS: USA AWT NATIVO DO JAVA
            if (!java.awt.SystemTray.isSupported()) {
                System.out.println("System Tray não suportado no Windows!");
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                return;
            }

            try {
                java.awt.SystemTray nativeTray = java.awt.SystemTray.getSystemTray();
                URL iconURL = getClass().getResource("/icons/mIcon256.png");

                if (iconURL != null) {
                    java.awt.Image imageOriginal = javax.imageio.ImageIO.read(iconURL);
                    java.awt.Image iconTray = imageOriginal.getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH);

                    // Menu clássico do AWT
                    java.awt.PopupMenu popupMenu = new java.awt.PopupMenu();

                    java.awt.MenuItem showItem = new java.awt.MenuItem("Show");
                    showItem.addActionListener(new java.awt.event.ActionListener() {
                        @Override
                        public void actionPerformed(java.awt.event.ActionEvent e) {
                            SwingUtilities.invokeLater(() -> {
                                setVisible(true);
                                setExtendedState(JFrame.NORMAL);
                                toFront();
                            });
                        }
                    });

                    java.awt.MenuItem exitItem = new java.awt.MenuItem("Exit");
                    exitItem.addActionListener(new java.awt.event.ActionListener() {
                        @Override
                        public void actionPerformed(java.awt.event.ActionEvent e) {
                            System.exit(0);
                        }
                    });

                    popupMenu.add(showItem);
                    popupMenu.add(exitItem);

                    java.awt.TrayIcon trayIcon = new java.awt.TrayIcon(iconTray, "Memoir", popupMenu);
                    trayIcon.setImageAutoSize(true);

                    nativeTray.add(trayIcon);
                    System.out.println("System Tray carregado via AWT Nativo (Windows).");
                }
            } catch (Exception e) {
                System.out.println("Erro ao configurar Tray nativo no Windows: " + e.getMessage());
            }

        } else {
            // LINUX/OUTROS: USA O DORKBOX
            dorkbox.systemTray.SystemTray systemTray = dorkbox.systemTray.SystemTray.get();

            if (systemTray == null) {
                System.out.println("System Tray não suportado pelo Dorkbox!");
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                return;
            }

            URL iconURL = getClass().getResource("/icons/mIcon256.png");
            if (iconURL != null) {
                systemTray.setImage(iconURL);
            }

            dorkbox.systemTray.Menu mainMenu = systemTray.getMenu();

            mainMenu.add(new dorkbox.systemTray.MenuItem("Show", e -> SwingUtilities.invokeLater(() -> {
                setVisible(true);
                setExtendedState(JFrame.NORMAL);
                toFront();
                repaint();
            })));

            mainMenu.add(new dorkbox.systemTray.MenuItem("Exit", e -> {
                if (systemTray != null) {
                    systemTray.shutdown();
                }
                System.exit(0);
            }));

            System.out.println("System Tray carregado via Dorkbox (Linux).");
        }

    }

    public AutoBackupService getAutoBackupService(String gameName) {
        return activeBackupServices.get(gameName);
    }



}
