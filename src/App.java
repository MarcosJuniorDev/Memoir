import model.Game;

import java.util.Scanner;

public class App {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("Insira o nome do jogo: ");
        String name = sc.nextLine();
        System.out.print("Insira o Caminho do Jogo: ");
        String pathGame = sc.nextLine();
        System.out.print("Insira o Path do SaveFile: ");
        String pathSaveFile = sc.nextLine();

        Game g = new Game(name, pathGame, pathSaveFile);

        System.out.println("Name: " + g.getName() + "\nDir do game: " + g.getGamePath() +
                "\nultimo  Backup: " + g.getLastBackup());

    }
}
