package main.ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundButton extends JButton {
    private Color backgroundColor;
    private Color textColor;
    private int borderRadius;
    private int size;

    public RoundButton(String text, Color backgroundColor, Color textColor, int borderRadius){
        super(text);
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        this.borderRadius = borderRadius;

        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);

        setForeground(textColor);
        setFont(new Font("Open Sans", Font.BOLD, 14));

    }
    public RoundButton(String text, Color backgroundColor, Color textColor, int borderRadius, int size){
        super(text);
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        this.borderRadius = borderRadius;
        this.size = size;

        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);

        setForeground(textColor);
        setFont(new Font("Open Sans", Font.BOLD, size));

    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        // Ativa o antialiasing para suavizar as bordas arredondadas do botão
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Define a cor de fundo desejada
        g2d.setColor(backgroundColor);

        // Desenha a forma arredondada do botão (formato de pílula)
        g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), borderRadius, borderRadius));

        g2d.dispose();

        // Chama o método da superclasse para renderizar o texto do botão sobre a forma que desenhamos
        super.paintComponent(g);
    }
}
