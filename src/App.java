import model.Game;
import service.GameRepository;
import service.HashFileSave;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        /*List<Game> g = new ArrayList<>();
        for (int i = 0; i < 2; i++){
            Scanner sc = new Scanner(System.in);
            System.out.print("Insira o nome do jogo: ");
            String name = sc.nextLine();
            System.out.print("Insira o Caminho do Jogo: ");
            String pathGame = sc.nextLine();
            System.out.print("Insira o Path do SaveFile: ");
            String pathSaveFile = sc.nextLine();

            g.add(new Game(name, pathGame, pathSaveFile));
        }

        for(Game games : g){
            System.out.println(games.getName() +
                    "\n" + games.getGamePath() +
                    "\n" + games.getLastBackup() + "\n");
        }

        GameRepository gp = new GameRepository();

        gp.GameData(g);*/
        Path filePath = Paths.get("/home/marcospc/Imagens/Screenshots");
        HashFileSave hfs = new HashFileSave();
        hfs.SaveFileHash(filePath);





    }
}
