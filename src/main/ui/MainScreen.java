package main.ui;

import main.ui.theme.AppTheme;
import net.miginfocom.swing.MigLayout;
import main.ui.components.RoundButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainScreen extends JFrame {
    public MainScreen(){
        setTitle("Memoir");
        setSize(1560, 960);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new MigLayout("wrap 2"));
        panel.setBackground(Color.decode("#101213"));

        //Titulo
        JLabel lblTitle = new JLabel("Memoir");
        lblTitle.setFont(FontUtils.importFont("/fonts/Orbitron-VariableFont_wght.ttf", 70));
        lblTitle.setBorder(new EmptyBorder(0, 10, 10 ,10));
        lblTitle.setForeground(Color.WHITE);
        panel.add(lblTitle);
        //button
        RoundButton rb = new RoundButton("ADD GAME", AppTheme.PRIMARY, AppTheme.TEXT_BLACK,44, 20);
        rb.setFont(FontUtils.importFont("/fonts/Orbitron-VariableFont_wght.ttf", 20));
        panel.add(rb, "pushx, align right, wrap, gapright 20");
        add(panel);

        //ADD GAME Panel
        rb.addActionListener(e -> {
            AddGame ad = new AddGame(MainScreen.this);
            ad.setVisible(true);
        });

    }

    //Metodo para importar a fonte do projeto

}
