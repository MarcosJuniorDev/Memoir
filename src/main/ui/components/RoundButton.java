package main.ui.components;

import main.ui.theme.AppTheme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;

public class RoundButton extends JButton {
    private Color currentColor;
    private Color originalColor;
    private Color hoverColor;
    private Color textColor;
    private int borderRadius;
    private int size;

    public RoundButton(String text, AppTheme backgroundColor, AppTheme textColor, int borderRadius){
        super(text);

        this.originalColor = backgroundColor.getColor();
        this.currentColor = backgroundColor.getColor();
        this.textColor = textColor.getColor();
        this.borderRadius = borderRadius;

        //hover
        this.hoverColor = this.originalColor.darker();

        hover();

        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);

        setForeground(this.textColor);
        setFont(new Font("Open Sans", Font.BOLD, 14));

    }
    public RoundButton(String text, AppTheme backgroundColor, AppTheme textColor, int borderRadius, int size){
        super(text);
        this.originalColor = backgroundColor.getColor();
        this.currentColor = backgroundColor.getColor();
        this.textColor = textColor.getColor();
        this.borderRadius = borderRadius;
        this.size = size;

        this.hoverColor = this.originalColor.darker();

        hover();

        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);

        setForeground(this.textColor);
        setFont(new Font("Open Sans", Font.BOLD, size));

    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        // Ativa o antialiasing para suavizar as bordas arredondadas do botão
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Define a cor de fundo desejada
        g2d.setColor(currentColor);

        // Desenha a forma arredondada do botão (formato de pílula)
        g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), borderRadius, borderRadius));

        g2d.dispose();

        // Chama o método da superclasse para renderizar o texto do botão sobre a forma que desenhamos
        super.paintComponent(g);
    }
    public void hover(){
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                currentColor = hoverColor;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                currentColor = originalColor;
                repaint();
            }
        });
    }
}
