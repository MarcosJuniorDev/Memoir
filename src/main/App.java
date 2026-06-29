package main;

import main.ui.MainScreen;

import javax.swing.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class App {
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        SwingUtilities.invokeLater(() -> {
            MainScreen ms = new MainScreen();
            ms.setVisible(true);
        });

    }


}
