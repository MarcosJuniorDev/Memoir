package main;

import main.ui.MainScreen;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.security.NoSuchAlgorithmException;
import com.formdev.flatlaf.FlatLightLaf;

public class App {
    private static final int PORT = 63971;
    private static ServerSocket lockSocket;

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        if(!checkUniqueInstance()){
            System.out.println("instancia aberta ja!");
            JOptionPane.showMessageDialog(null,"The app is already running",
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
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

    private static boolean checkUniqueInstance(){
        try{
            lockSocket = new ServerSocket(PORT);
            return true;
        } catch (IOException e){
            System.out.println("Error " + e.getMessage());
            return false;
        }
    }


}
