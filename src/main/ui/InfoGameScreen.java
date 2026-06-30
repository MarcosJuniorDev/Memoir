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

        JPanel contentPanel = new JPanel(new MigLayout("insets 0, fillx, filly", "[50%][50%]"));
        contentPanel.setOpaque(false);


        //IMAGEM DO GAME
        ResponsiveCover gameCover = new ResponsiveCover(game.getCoverPath());
        contentPanel.add(gameCover, "grow, wmin 0, hmin 0");

        //Nome do Game
        JLabel gameName = new JLabel(game.getName());
        gameName.setForeground(AppTheme.PRIMARY.getColor());
        gameName.setFont(FontUtils.importFont("/fonts/Orbitron-VariableFont_wght.ttf", 26));
        contentPanel.add(gameName, "top, center");









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
