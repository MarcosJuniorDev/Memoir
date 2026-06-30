package main.ui;

import main.model.Game;
import main.ui.components.CoverPick;
import main.ui.components.ResponsiveCover;
import main.ui.components.RoundButton;
import main.ui.theme.AppTheme;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class InfoGameScreen extends JPanel {
    private MainScreen mainScreen;
    private CoverPick coverPick;
    private Image coverImage;


    public InfoGameScreen(MainScreen mainScreen, Game game){


        setLayout(new BorderLayout());
        setBackground(AppTheme.BG_MAIN.getColor());


        //BOTOES
        RoundButton btnCancel = new RoundButton("CANCEL", AppTheme.SECONDARY, AppTheme.TEXT_WHITE, 15);

        JPanel panel = new JPanel(new MigLayout("wrap 1, fill, insets 30"));
        panel.setBackground(AppTheme.BG_MAIN.getColor());

        JPanel contentPanel = new JPanel(new MigLayout("insets 0, fillx, filly", "[35%][65%]"));
        contentPanel.setOpaque(false);

        //Nome do Game
        JLabel gameName = new JLabel(game.getName());
        gameName.setForeground(AppTheme.PRIMARY.getColor());
        gameName.setFont(FontUtils.importFont("/fonts/Orbitron-VariableFont_wght.ttf", 26));
        contentPanel.add(gameName, "top, center");

        //AVALIACAO
        JLabel rating = new JLabel("Rating: ");
        rating.setForeground(AppTheme.PRIMARY.getColor());
        rating.setFont(FontUtils.importFont("/fonts/Orbitron-VariableFont_wght.ttf", 16));
        contentPanel.add(rating, "center, cell 0 1");

        //IMAGEM DO GAME
        ResponsiveCover gameCover = new ResponsiveCover(game.getCoverPath());
        contentPanel.add(gameCover, "grow, pushy, wmin 0, hmin 0,  cell 0 2");

        //ULTIMO BACKUP:
        JLabel gameLastBackupTitle = new JLabel("Last backup: ");
        gameLastBackupTitle.setForeground(AppTheme.PRIMARY.getColor());
        gameLastBackupTitle.setFont(FontUtils.importFont("/fonts/Orbitron-VariableFont_wght.ttf", 20));
        contentPanel.add(gameLastBackupTitle, "center, cell 0 3");

        JLabel backupDate = new JLabel(game.getLastBackup());
        backupDate.setForeground(AppTheme.PRIMARY.getColor());
        backupDate.setFont(FontUtils.importFont("/fonts/Orbitron-VariableFont_wght.ttf", 20));
        contentPanel.add(backupDate, "center, cell 0 3");

        //BOTAO PARA REALIZAR BACKUP

        RoundButton btnBackupGame = new RoundButton("BACKUP NOW", AppTheme.PRIMARY, AppTheme.TEXT_BLACK, 15);
        btnBackupGame.setFont(FontUtils.importFont("/fonts/Orbitron-VariableFont_wght.ttf", 26));
        contentPanel.add(btnBackupGame, "center, gaptop 30, cell 0 4");














        /*JLabel lblDireita = new JLabel("LADO DIREITO (Opções de Backup)", SwingConstants.CENTER);
        lblDireita.setOpaque(true);
        lblDireita.setBackground(Color.decode("#4C4CFF")); // Azul*/

        panel.add(contentPanel, "grow, push");



        //FOOTER
        JPanel footerPanel = new JPanel(new MigLayout());
        footerPanel.setOpaque(false);
        footerPanel.add(btnCancel, "align right,pushx, width 120!, height 40!, gapbottom 30, gapright 10");
        panel.add(footerPanel, "dock south");

        btnCancel.addActionListener(e -> mainScreen.restoreMainScreenState());



        // Adiciona o panel no CENTRO do BorderLayout para ele esticar
        add(panel, BorderLayout.CENTER);
    }


}
