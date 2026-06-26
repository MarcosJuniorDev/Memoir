package main.ui.theme;

import java.awt.*;

public enum AppTheme {
    PRIMARY(new Color(0, 255, 255)), //Cyan
    SECONDARY(Color.decode("#2D2F31")), //Cinza escuro botoes
    BG_MAIN(Color.decode("#101213")), //fundo do main screen
    BG_MODAL(Color.decode("#1A1C1E")),
    TEXT_WHITE(Color.WHITE),
    TEXT_BLACK(Color.BLACK);

    private final Color color;

    AppTheme(Color color){
        this.color = color;
    }

    public Color getColor(){
        return color;
    }
}

