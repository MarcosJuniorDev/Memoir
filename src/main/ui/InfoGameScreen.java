package main.ui;

import main.model.Game;
import main.service.BackupService;
import main.service.GameRepository;
import main.ui.components.ResponsiveCover;
import main.ui.components.RoundButton;
import main.ui.components.RoundTextArea;
import main.ui.components.RoundTextField;
import main.ui.theme.AppTheme;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.function.Consumer;

public class InfoGameScreen extends JPanel {
    private MainScreen mainScreen;
    private BackupService backupService;
    private Game game;

    //TODO TODA ESSA CLASSE ESTA HORRÍVEL COM REPETIÇÃO DE CÓDIGO VOU REFATORAR MAIS TARDE FODAS
    //



    public InfoGameScreen(MainScreen mainScreen, Game game){
        this.mainScreen = mainScreen;
        this.game = game;
        this.backupService = new BackupService(game);

        setLayout(new BorderLayout());
        setBackground(AppTheme.BG_MAIN.getColor());

        //BOTOES
        JPanel panel = new JPanel(new MigLayout("wrap 1, fill, insets 30"));
        panel.setBackground(AppTheme.BG_MAIN.getColor());

        JPanel contentPanel = new JPanel(new MigLayout("insets 0, fillx, filly", "[35%][65%]"));
        contentPanel.setOpaque(false);

        //TENTAIVA DE PAINEL ESQUERDO SEPARADO
        JPanel leftPanel = new JPanel(new MigLayout("wrap 1, insets 0, fill", "[grow]", "[][][grow][][]"));
        leftPanel.setOpaque(false);
        //DIREITO
        JPanel rightPanel = new JPanel(new MigLayout("wrap 1, insets 20, fillx", "[grow]"));
        rightPanel.setOpaque(false);



        //Nome do Game
        JLabel gameName = new JLabel(game.getName());
        gameName.setForeground(AppTheme.PRIMARY.getColor());
        gameName.setFont(FontUtils.importFont("/fonts/Orbitron-VariableFont_wght.ttf", 26));
        leftPanel.add(gameName, "top, center");

        //AVALIACAO
        JLabel rating = new JLabel("Rating: ");
        rating.setForeground(AppTheme.PRIMARY.getColor());
        rating.setFont(FontUtils.importFont("/fonts/Orbitron-VariableFont_wght.ttf", 18));
        JPanel starRatingPanel = createStarRating();
        System.out.println(game.getRating());
        leftPanel.add(rating, "split 2, center, cell 0 1");
        leftPanel.add(starRatingPanel, "");

        //IMAGEM DO GAME
        ResponsiveCover gameCover = new ResponsiveCover(game.getCoverPath());
        leftPanel.add(gameCover, "grow, pushx, pushy, wmin 0, hmin 0,  cell 0 2");

        //ULTIMO BACKUP:
        JLabel gameLastBackupTitle = new JLabel("Last backup: ");
        gameLastBackupTitle.setForeground(AppTheme.PRIMARY.getColor());
        gameLastBackupTitle.setFont(FontUtils.importFont("/fonts/Orbitron-VariableFont_wght.ttf", 20));
        leftPanel.add(gameLastBackupTitle, "center, cell 0 3");

        JLabel backupDate = new JLabel(game.getLastBackup());
        backupDate.setForeground(AppTheme.PRIMARY.getColor());
        backupDate.setFont(FontUtils.importFont("/fonts/Orbitron-VariableFont_wght.ttf", 20));
        leftPanel.add(backupDate, "center, cell 0 3");

        //RESTORE BACKUP
        RoundButton btnRestoreGameSave = new RoundButton("RESTORE BACKUP", AppTheme.PRIMARY, AppTheme.TEXT_BLACK, 15);
        btnRestoreGameSave.setFont(FontUtils.importFont("/fonts/Orbitron-VariableFont_wght.ttf", 26));
        leftPanel.add(btnRestoreGameSave, "center ,gaptop 5, cell 0 5, sg botoes");

        //BOTAO PARA REALIZAR BACKUP

        RoundButton btnBackupGame = new RoundButton("BACKUP NOW", AppTheme.PRIMARY, AppTheme.TEXT_BLACK, 15);
        btnBackupGame.setFont(FontUtils.importFont("/fonts/Orbitron-VariableFont_wght.ttf", 26));
        leftPanel.add(btnBackupGame, "center, gaptop 10, cell 0 4, sg botoes");



        contentPanel.add(leftPanel, "grow, cell 0 0");

        btnBackupGame.addActionListener(e -> {
            try {
                boolean backupDone = backupService.saveFilesBackup();
                if(backupDone){
                    backupDate.setText(game.getLastBackup());
                    JOptionPane.showMessageDialog(
                            this, "Backup Completed successfully!",
                            "Success", JOptionPane.INFORMATION_MESSAGE
                    );
                }
                else{
                    JOptionPane.showMessageDialog(
                            this, "No new changes detected. Your backup is up to date!",
                            "Backup Skipped", JOptionPane.INFORMATION_MESSAGE
                    );
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        this, "Failed to backup save files:\n" + ex.getMessage(),
                        "Backup Error", JOptionPane.INFORMATION_MESSAGE
                );
            }

        });

        btnRestoreGameSave.addActionListener(e -> {
            int awnser = JOptionPane.showConfirmDialog(
                    this, "Do you want to restore backup? This option can't be undone!",
                    "Restore Backup", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE
            );
            if(awnser == JOptionPane.YES_OPTION){
                try {
                    boolean isRestored = backupService.restoreBackup();

                    if(isRestored){
                        JOptionPane.showMessageDialog(this,
                                "Backup restored successfully!",
                                "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else {
                        JOptionPane.showMessageDialog(this,
                                "No backup found to restore!",
                                "Not found", JOptionPane.WARNING_MESSAGE);
                    }
                }catch (IOException ex){
                    JOptionPane.showMessageDialog(this,
                            "Error while restoring backup:\n" + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        });

        //LADO DIREITO
        //TITULO: CAMINHO DO JOGO
        JLabel gamePathTitle = new JLabel("Game Path");
        gamePathTitle.setForeground(AppTheme.PRIMARY.getColor());
        gamePathTitle.setFont(FontUtils.importFont("/fonts/Orbitron-VariableFont_wght.ttf", 26));
        rightPanel.add(gamePathTitle, "left, top");

        //CONTEUDO DO CAMINHO DO JOGO
        RoundTextField gamePath = new RoundTextField(15, 20);
        gamePath.setForeground(AppTheme.PRIMARY.getColor());
        gamePath.setText(game.getGamePath());
        dirChoose(gamePath, false, newPath -> game.setGamePath(newPath));
        rightPanel.add(gamePath, "width 100%, height 50!");

        //TITULO: CAMINHO PARA O SAVEFILE
        JLabel gameSavePathTitle = new JLabel("Save File Path");
        gameSavePathTitle.setForeground(AppTheme.PRIMARY.getColor());
        gameSavePathTitle.setFont(FontUtils.importFont("/fonts/Orbitron-VariableFont_wght.ttf", 26));
        rightPanel.add(gameSavePathTitle, "left, gaptop 20");

        //CONTEUDO
        RoundTextField gameSavePath = new RoundTextField(15, 20);
        gameSavePath.setForeground(AppTheme.PRIMARY.getColor());
        gameSavePath.setText(game.getSaveGamePath());
        gameSavePath.setEditable(false);
        dirChoose(gameSavePath, true, newPath -> game.setGamePath(newPath));
        rightPanel.add(gameSavePath, "width 100%, height 50!");

        //TITULO:CAMINHO DO BACKUP
        JLabel gameBackupPathTitle = new JLabel("Backup Path");
        gameBackupPathTitle.setForeground(AppTheme.PRIMARY.getColor());
        gameBackupPathTitle.setFont(FontUtils.importFont("/fonts/Orbitron-VariableFont_wght.ttf", 26));
        rightPanel.add(gameBackupPathTitle, "left, gaptop 20");
        //CONTEUDO
        RoundTextField gameBackupPath = new RoundTextField(15, 20);
        gameBackupPath.setForeground(AppTheme.PRIMARY.getColor());
        gameBackupPath.setText(game.getBackupLocation());
        gameBackupPath.setEditable(false);
        dirChoose(gameBackupPath, true, newPath -> game.setGamePath(newPath));
        rightPanel.add(gameBackupPath, "width 100%, height 50!");

        //COMENTARIO
        //TITULO
        JLabel commentGame = new JLabel("Comments");
        commentGame.setForeground(AppTheme.PRIMARY.getColor());
        commentGame.setFont(FontUtils.importFont("/fonts/Orbitron-VariableFont_wght.ttf", 26));
        rightPanel.add(commentGame, "left, gaptop 20");
        //CAIXA DE TEXTO
        RoundTextArea commentTxt = new RoundTextArea(15, 20);
        commentTxt.setForeground(AppTheme.PRIMARY.getColor());
        commentTxt.setText(game.getComment());
        //SCROLL
        JScrollPane scrollPane = new JScrollPane(commentTxt);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        rightPanel.add(scrollPane, "width 0:100%, height 30%");
        //BOTAOSAVE
        RoundButton btnSaveComment = new RoundButton("Save Comment", AppTheme.PRIMARY, AppTheme.TEXT_BLACK, 15);
        btnSaveComment.setFont(FontUtils.importFont("/fonts/Orbitron-VariableFont_wght.ttf", 15));
        btnSaveComment.addActionListener(e -> {
            int awnser = JOptionPane.showConfirmDialog(
                    this, "Do you want to save a comment?",
                    "Comment", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE
            );
            if(awnser == JOptionPane.YES_OPTION){
                GameRepository gameRepository = new GameRepository();
                game.setComment(commentTxt.getText());
                gameRepository.update(game);
            }


        });
        rightPanel.add(btnSaveComment, "gaptop 10, right");

        contentPanel.add(rightPanel, "grow, top, cell 1 0");
        panel.add(contentPanel, "grow, push");





        //FOOTER
        //VOLTAR A TELA ANTERIOR
        RoundButton btnCancel = new RoundButton("CANCEL", AppTheme.SECONDARY, AppTheme.TEXT_WHITE, 15);
        JPanel footerPanel = new JPanel(new MigLayout());
        footerPanel.setOpaque(false);


        //DELETAR CADASTRO DO GAME
        RoundButton btnDeleteGame = new RoundButton("DELETE GAME", AppTheme.SECONDARY, AppTheme.TEXT_WHITE, 15);
        btnDeleteGame.addActionListener(e -> {
            GameRepository gameRepository = new GameRepository();
            int awnser = JOptionPane.showConfirmDialog(
                    this, "Do you also want to delete the backup save file?",
                    "Backup Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE
            );
            if(awnser == JOptionPane.YES_OPTION) {
                try {
                    backupService.deleteBackupFile(game.getBackupLocation(), game.getName());
                    deleteGameProfile(game.getName(), gameRepository);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(),
                            "Error", JOptionPane.WARNING_MESSAGE
                    );
                }

            }
            else if (awnser == JOptionPane.NO_OPTION){
                deleteGameProfile(game.getName(), gameRepository);
            }

        });



        footerPanel.add(btnDeleteGame, "align right, pushx, width 140!, height 40!");
        footerPanel.add(btnCancel, " width 120!, height 40!, gapbottom 30, gapright 10");

        panel.add(footerPanel, "dock south");

        btnCancel.addActionListener(e -> mainScreen.restoreMainScreenState());

        //



        // Adiciona o panel no CENTRO do BorderLayout para ele esticar
        add(panel, BorderLayout.CENTER);
    }

    private void updateStarsUI (JLabel[] stars, int score){
        String filledStar = "\u2605"; // ★
        String emptyStar = "\u2606";  // ☆
        for (int i = 0; i < 5; i++){
            if (i < score){
                stars[i].setText(filledStar);
            }
            else {
                stars[i].setText(emptyStar);
            }
        }
    }

    private JPanel createStarRating() {
        JPanel starPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
        starPanel.setOpaque(false);

        JLabel[] stars = new JLabel[5];
        Font startFont = new Font(Font.SANS_SERIF, Font.PLAIN, 20);

        // Loop para criar sempre 5 estrelas
        for (int i = 0; i < 5; i++) {
            final int startValue = i + 1;

            stars[i] = new JLabel();
            stars[i].setFont(startFont);
            stars[i].setForeground(AppTheme.PRIMARY.getColor());
            stars[i].setCursor(new Cursor(Cursor.HAND_CURSOR));

            //ADICIONAR OS EVENTOS DO MOUSE
            stars[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    game.setRating(startValue);

                    GameRepository gr = new GameRepository();
                    gr.update(game);

                    updateStarsUI(stars, game.getRating());
                }

                @Override
                public void mouseEntered(MouseEvent e){
                    updateStarsUI(stars, startValue);
                }

                @Override
                public void mouseExited(MouseEvent e){
                    updateStarsUI(stars, game.getRating());
                }
            });
            starPanel.add(stars[i]);
        }
        updateStarsUI(stars, game.getRating());

        return starPanel;
    }



    private void deleteGameProfile(String gameName, GameRepository gameRepository){
        try {
            gameRepository.deleteGame(gameName);
            JOptionPane.showMessageDialog(this,
                    "Game Profile deleted!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            mainScreen.restoreMainScreenState();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this, "Failed to delete game profile:\n" + ex.getMessage(),
                    "Delete Error", JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private void dirChoose(RoundTextField field, boolean isDirectory, Consumer<String> updateGameField) {
        field.setEditable(false);
        field.setCursor(new Cursor(Cursor.HAND_CURSOR));

        field.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser chooser = new JFileChooser();
                if (isDirectory){
                    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    chooser.setDialogTitle("Select Folder");
                } else {
                    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    chooser.setDialogTitle("Select Executable");
                    chooser.setFileFilter(new FileNameExtensionFilter("Executables (*.exe)", "exe"));
                }

                int result = chooser.showOpenDialog(InfoGameScreen.this);

                if (result == JFileChooser.APPROVE_OPTION){
                    String selectedPath = chooser.getSelectedFile().getAbsolutePath();

                    // 1. Atualiza a tela
                    field.setText(selectedPath);

                    // 2. MÁGICA: Atualiza o atributo certo dentro do objeto 'game'
                    updateGameField.accept(selectedPath);

                    // 3. Agora sim, salva o objeto atualizado no JSON!
                    GameRepository gameRepository = new GameRepository();
                    gameRepository.update(game);
                }
            }
        });
    }

}
