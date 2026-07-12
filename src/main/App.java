package main;

import main.ui.MainScreen;

import javax.swing.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import com.formdev.flatlaf.FlatLightLaf;

public class App {
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        try{
            FlatLightLaf.setup();
        } catch (Exception e){
            System.out.println("Error in setting up Laf");
        }

        SwingUtilities.invokeLater(() -> {
            MainScreen ms = new MainScreen();
            ms.setVisible(true);
        });

    }


}
