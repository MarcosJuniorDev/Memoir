package main.ui;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class FontUtils {
    private static final Map<String, Font> FONT_CACHE = new HashMap<>();

    private FontUtils() { }

    public static Font importFont(String pathFont, int size){
        Font baseFont = FONT_CACHE.computeIfAbsent(pathFont, FontUtils::loadFont);
        return baseFont.deriveFont(Font.PLAIN, (float)size);
    }

    private static Font loadFont(String pathFont) {
        try (InputStream is = FontUtils.class.getResourceAsStream(pathFont)){
            if (is == null) {
                System.out.println("Font no found: " + pathFont);
                return new Font("Open Sans", Font.BOLD, 12);
            }
            return Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            System.out.println("Error loading font " + pathFont);
            return new Font("Open Sans", Font.PLAIN, 12);
        }
    }
}
