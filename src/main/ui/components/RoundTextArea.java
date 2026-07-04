package main.ui.components;

import main.ui.theme.AppTheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundTextArea extends JTextArea {
    private int radius;
    private int size;

    public RoundTextArea(int radius, int size){
        super();
        this.radius = radius;
        //configuracao basica
        setOpaque(false);
        setBackground(AppTheme.SECONDARY.getColor());
        setForeground(AppTheme.PRIMARY.getColor());
        setCaretColor(AppTheme.PRIMARY.getColor());
        this.size = size;
        setFont(new Font("Open Sans", Font.PLAIN, size));
        //MARGEM
        setBorder(new EmptyBorder(5, 10, 5, 10));

        setLineWrap(true);
        setWrapStyleWord(true);
        setMargin(new Insets(10,15,10,15));
    }

    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getBackground());
        g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radius, radius));

        g2d.dispose();
        super.paintComponent(g);
    }


}
