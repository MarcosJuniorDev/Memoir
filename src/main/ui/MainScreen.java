package main.ui;

import net.miginfocom.swing.MigLayout;
import main.ui.components.RoundButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.InputStream;

public class MainScreen extends JFrame {
    public MainScreen(){
        setTitle("Memoir");
        setSize(1560, 960);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new MigLayout("wrap 2"));
        panel.setBackground(Color.decode("#101213"));

        //Titulo
        JLabel lblTitle = new JLabel("Memoir");
        lblTitle.setFont((importFont("/fonts/Orbitron-VariableFont_wght.ttf", 70)));
        //lblTitle.setFont(new Font("Orbitron", Font.PLAIN, 70));
        lblTitle.setBorder(new EmptyBorder(0, 10, 10 ,10));
        lblTitle.setForeground(Color.WHITE);
        panel.add(lblTitle);
        //button
        RoundButton rb = new RoundButton("ADD GAME", new Color(0, 255, 255), Color.BLACK, 44, 20);
        panel.add(rb, "pushx, align right, wrap, gapright 20");
        add(panel);

        //ADD GAME Panel
        rb.addActionListener(e -> {
            AddGame ad = new AddGame(MainScreen.this);
            ad.setVisible(true);
        });

    }

    //Metodo para importar a fonte do projeto
    private Font importFont(String pathFont, int size){
        try {
            InputStream is = getClass().getResourceAsStream(pathFont);
            Font OgFont = Font.createFont(Font.TRUETYPE_FONT, is);
            return OgFont.deriveFont(Font.PLAIN, size);
        } catch (Exception e){
            System.out.println("Erro loading font: " + pathFont);
            return new Font("Open Sans", Font.BOLD, (int) size);
        }
    }
}
