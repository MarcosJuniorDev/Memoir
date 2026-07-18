package main.ui;

import main.model.Game;
import main.ui.theme.AppTheme;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

public class GameCard extends JPanel {
    private Image coverImage;
    private String gameName;
    private Game game;
    private static final int CARD_WIDTH = 200;
    private static final int CARD_HEIGHT = 300;
    private static final int TITLE_HEIGHT = 30;
    private static final int COVER_HEIGHT = CARD_HEIGHT - TITLE_HEIGHT;

    public GameCard(Game game, Consumer<Game> onClickAction){
        this.game = game;
        this.gameName = game.getName();


        setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setToolTipText("<html><body style='width: 200px; font-size: 14px; padding: 5px;'>"
                + gameName
                + "</body></html>");

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onClickAction.accept(game);
            }
        });

        if (game.getCoverPath() != null && !game.getCoverPath().isEmpty()){
            this.coverImage= loadThumbnail(game.getCoverPath(), CARD_WIDTH, COVER_HEIGHT);
        }
    }

    private Image loadThumbnail(String imagePath, int width, int height){
        try {
            BufferedImage original = ImageIO.read(new File(imagePath));
            if(original == null){
                return null;
            }

            BufferedImage thumbnail = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = thumbnail.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.drawImage(original, 0, 0, width, height, null);
            g2d.dispose();

            return thumbnail;
        } catch (IOException e){
            System.out.println("Erro trying to load thumbnail: " + imagePath);
            return null;
        }
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        FontMetrics fm = g2d.getFontMetrics();

        Shape molde = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight() - TITLE_HEIGHT, 15, 15);
        if (coverImage != null) {
            g2d.setClip(molde);
            g2d.drawImage(coverImage, 0, 0, getWidth(), getHeight() - TITLE_HEIGHT, this);
            g2d.setClip(null);
        } else {
            g2d.setColor(Color.DARK_GRAY);
            g2d.fill(molde);
            g2d.setColor(AppTheme.PRIMARY.getColor());
            g2d.setFont(new Font("SansSerif", Font.BOLD, 14));
            String text = "NO COVER";
            int xTexto = (getWidth() - fm.stringWidth(text)) / 2;
            int yTexto = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
            g2d.drawString(text, xTexto, yTexto);
        }

        g2d.setColor(AppTheme.PRIMARY.getColor());
        g2d.setFont(FontUtils.importFont("/fonts/Orbitron-VariableFont_wght.ttf", 18));

        FontMetrics fmOrbitron = g2d.getFontMetrics();
        //BORDA
        g2d.setStroke(new BasicStroke(2f));
        g2d.draw(molde);

        int maxWidth = getWidth() - 10;

        if (fmOrbitron.stringWidth(gameName) > maxWidth){
            while (fmOrbitron.stringWidth(gameName + "...") > maxWidth && !gameName.isEmpty()) {
                gameName = gameName.substring(0, gameName.length() - 1);
            }
            gameName += "...";
        }

        int xTexto = (getWidth() - fmOrbitron.stringWidth(gameName)) / 2;
        int yTexto = getHeight() - 12;

        g2d.drawString(gameName, xTexto, yTexto);
        g2d.dispose();
    }

}
