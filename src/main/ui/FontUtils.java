package main.ui;

import java.awt.*;
import java.io.InputStream;

public class FontUtils {
    public static Font importFont(String pathFont, int size){
        try {
            InputStream is = FontUtils.class.getResourceAsStream(pathFont);
            Font OgFont = Font.createFont(Font.TRUETYPE_FONT, is);
            return OgFont.deriveFont(Font.PLAIN, size);
        } catch (Exception e){
            System.out.println("Erro loading font: " + pathFont);
            return new Font("Open Sans", Font.BOLD, (int) size);
        }
    }
}
