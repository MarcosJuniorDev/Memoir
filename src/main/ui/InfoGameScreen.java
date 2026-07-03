package main.ui;

import main.model.Game;
import main.service.BackupService;
import main.service.GameRepository;
import main.ui.components.ResponsiveCover;
import main.ui.components.RoundButton;
import main.ui.components.RoundTextField;
import main.ui.theme.AppTheme;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class InfoGameScreen extends JPanel {
    /*
        TODA ESSA CLASSE ESTA HORRÍVEL COM REPETIÇÃO DE CÓDIGO VOU REFATORAR MAIS TARDE FODAS
     */

    public InfoGameScreen(MainScreen mainScreen, Game game){


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
        rating.setFont(FontUtils.importFont("/fonts/Orbitron-VariableFont_wght.ttf", 16));
        leftPanel.add(rating, "center, cell 0 1");

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

        //BOTAO PARA REALIZAR BACKUP

        RoundButton btnBackupGame = new RoundButton("BACKUP NOW", AppTheme.PRIMARY, AppTheme.TEXT_BLACK, 15);
        btnBackupGame.setFont(FontUtils.importFont("/fonts/Orbitron-VariableFont_wght.ttf", 26));
        leftPanel.add(btnBackupGame, "center, gaptop 30, cell 0 4");
        contentPanel.add(leftPanel, "grow, cell 0 0");

        btnBackupGame.addActionListener(e -> {
            try {
                BackupService backupService = new BackupService(game);
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

        //RESTORE BACKUP
        RoundButton btnRestoreGameSave = new RoundButton("RESTORE BACKUP", AppTheme.PRIMARY, AppTheme.TEXT_BLACK, 15);
        btnRestoreGameSave.setFont(FontUtils.importFont("/fonts/Orbitron-VariableFont_wght.ttf", 26));
        //contentPanel.add(btnRestoreGameSave, " gaptop 30, cell 0 4");

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
        gamePath.setEditable(false);
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
        rightPanel.add(gameBackupPath, "width 100%, height 50!");


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
            try {
                gameRepository.deleteGame(game.getName());
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
        });



        footerPanel.add(btnDeleteGame, "align right, pushx, width 140!, height 40!");
        footerPanel.add(btnCancel, " width 120!, height 40!, gapbottom 30, gapright 10");

        panel.add(footerPanel, "dock south");

        btnCancel.addActionListener(e -> mainScreen.restoreMainScreenState());

        //



        // Adiciona o panel no CENTRO do BorderLayout para ele esticar
        add(panel, BorderLayout.CENTER);
    }

}
