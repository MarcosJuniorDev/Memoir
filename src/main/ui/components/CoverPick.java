package main.ui.components;

import main.ui.theme.AppTheme;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class CoverPick extends JPanel {
    private int radius;
    private String text;
    private Color backgroundColor;

    private Image coverImage;

    public CoverPick(int radius, String text){
        this.radius = radius;
        this.text = text;
        this.backgroundColor = AppTheme.SECONDARY.getColor();

        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void setCoverImage(String imagePath){
        this.coverImage = new ImageIcon(imagePath).getImage();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Shape roudShape = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radius, radius);

        //TENTATIVA DE IMAGEM
        if(coverImage != null){
            g2d.setClip(roudShape);
            g2d.drawImage(coverImage, 0, 0, getWidth(), getHeight(), this);
        }
        else {
            // Desenha o fundo da capa
            g2d.setColor(backgroundColor);
            g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radius, radius));

            // Centraliza o texto "+ ADD COVER"
            g2d.setColor(AppTheme.PRIMARY.getColor()); // Texto em Cyan
            g2d.setFont(new Font("SansSerif", Font.BOLD, 14));

            FontMetrics fm = g2d.getFontMetrics();
            int xTexto = (getWidth() - fm.stringWidth(text)) / 2;
            int yTexto = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
            g2d.drawString(text, xTexto, yTexto);
        }


        g2d.dispose();
        super.paintComponent(g);
    }
}
