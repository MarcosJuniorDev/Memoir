package main.ui;

import main.model.Game;
import main.ui.theme.AppTheme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.function.Consumer;

public class GameCard extends JPanel {
    private Image coverImage;
    private String gameName;
    private Game game;

    public GameCard(Game game, Consumer<Game> onClickAction){
        this.game = game;
        this.gameName = game.getName();


        setPreferredSize(new Dimension(200, 300));
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
            this.coverImage= new ImageIcon(game.getCoverPath()).getImage();
        }
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        FontMetrics fm = g2d.getFontMetrics();

        Shape molde = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight() - 30, 15, 15);
        if (coverImage != null) {
            g2d.setClip(molde);
            g2d.drawImage(coverImage, 0, 0, getWidth(), getHeight() - 30, this);
            g2d.setClip(null);
        } else {
            //g2d.setColor(Color.DARK_GRAY);
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


        //BORDA
        g2d.setStroke(new BasicStroke(2f));
        g2d.draw(molde);

        int maxWidth = getWidth() - 10;

        if (fm.stringWidth(gameName) > maxWidth){
            while (fm.stringWidth(gameName + "...") > maxWidth && !gameName.isEmpty()) {
                gameName = gameName.substring(0, gameName.length() - 1);
            }
            gameName += "...";
        }

        int xTexto = (getWidth() - fm.stringWidth(gameName)) / 2;
        int yTexto = getHeight() - 12;

        g2d.drawString(gameName, xTexto, yTexto);
        g2d.dispose();
    }

}
