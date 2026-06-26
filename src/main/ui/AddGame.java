package main.ui;

import net.miginfocom.swing.MigLayout;
import main.ui.components.RoundButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AddGame extends JDialog {
    public AddGame(JFrame parent){
        super(parent, "Add new Game", true);
        setUndecorated(true);

        //tentando deixar ela fixa relacionada a tela principal
        ComponentAdapter lockMovement = new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e){
                setLocationRelativeTo(parent);
            }
        };
        parent.addComponentListener(lockMovement);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                parent.removeComponentListener(lockMovement);
            }
        });

        //Cores para os btn
        Color cyanTheme = new Color(0, 255, 255);
        Color darkGrey = Color.decode("#2D2F31");
        Color whiteText = Color.WHITE;
        Color BlackText = Color.BLACK;

        //BTN cyan para adicionar
        RoundButton btnAddGame = new RoundButton("ADD GAME", cyanTheme, BlackText, 15);

        //BTN Cinza para SAIR
        RoundButton btnCancel = new RoundButton("CANCEL", darkGrey, whiteText, 15);


        setSize(1000, 740);
        setLocationRelativeTo(parent);
        JPanel panel = new JPanel(new MigLayout("wrap 1, fill, insets 20"));
        panel.setBackground(Color.decode("#1A1C1E"));

        //Title Popup
        JLabel lblTitle = new JLabel("New Game");
        lblTitle.setFont(new Font("Open Sans", Font.BOLD, 16));
        lblTitle.setForeground(Color.WHITE);
        panel.add(lblTitle, "dock north");

        //COLOCAR ESSAS PORRA DE BOTAO NO CANTO INFERIOR DIREITO
        JPanel footerPanel = new JPanel(new MigLayout("insets 0"));
        footerPanel.setOpaque(false);

        footerPanel.add(btnAddGame, "pushx, align right, width 180!, height 40!, gapbottom 30, gapright 10");
        footerPanel.add(btnCancel, "width 120!, height 40!, gapright 20");

        btnCancel.addActionListener(e -> dispose());
        panel.add(footerPanel, "dock south");
        add(panel);
    }
}
