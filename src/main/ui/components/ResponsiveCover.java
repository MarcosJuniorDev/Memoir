package main.ui.components;

import main.ui.theme.AppTheme;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class ResponsiveCover extends JPanel {
    private Image coverImage;

    public ResponsiveCover(String imagePath){
        setOpaque(false);
        if(imagePath != null && !imagePath.isEmpty()) {
            this.coverImage = new ImageIcon(imagePath).getImage();
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (coverImage != null) {
            Graphics2D g2d = (Graphics2D) g.create();

            // Filtros para a imagem não ficar serrilhada quando encolher ou esticar
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            // Cria o molde arredondado usando o tamanho EXATO que o painel tem naquele milissegundo
            Shape molde = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20);

            g2d.setColor(AppTheme.PRIMARY.getColor());
            g2d.setClip(molde);
            // Desenha a imagem esticando/encolhendo para caber no molde
            g2d.drawImage(coverImage, 0, 0, getWidth(), getHeight(), this);
            g2d.setClip(null);

            g2d.setStroke(new BasicStroke(4f));
            g2d.draw(molde);

            g2d.dispose();
        }
    }
}
