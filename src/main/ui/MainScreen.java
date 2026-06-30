package main.ui;

import main.model.Game;
import main.service.GameRepository;
import main.ui.theme.AppTheme;
import net.miginfocom.swing.MigLayout;
import main.ui.components.RoundButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class MainScreen extends JFrame {
    private JPanel gridPanel;
    private GameRepository gameRepository;

    //GUARDA A MAINSCREEN ORIGINAL
    private Container originalPanel;

    public MainScreen(){
        this.gameRepository = new GameRepository();

        //PAINEL DOS CARDS
        gridPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 20, 20));
        gridPanel.setBackground(AppTheme.BG_MAIN.getColor());



        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);





        setTitle("Memoir");
        setSize(1560, 960);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

        if (savedGames != null && !savedGames.isEmpty()) {
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



}
