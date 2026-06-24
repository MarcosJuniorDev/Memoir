import model.Game;
import service.BackupService;
import service.GameRepository;
import service.HashFileSave;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
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
        /*Path filePath = Paths.get("/home/marcospc/Imagens/Screenshots");
        HashFileSave hfs = new HashFileSave();
        hfs.SaveFileHash(filePath);*/
        // 1. Pastas dentro do projeto
        Path projectRoot = Paths.get("").toAbsolutePath();
        Path backupBasePath = projectRoot.resolve("test-backups");
        Path saveTestPath = projectRoot.resolve("test-saves");
        Path repoFilePath = projectRoot.resolve("test-games.json");

        // Cria as pastas se não existirem
        Files.createDirectories(backupBasePath);
        Files.createDirectories(saveTestPath);

        GameRepository repo = new GameRepository();
        HashFileSave hashService = new HashFileSave();

        // 2. Carrega jogos existentes
        List<Game> allGames = repo.loadAll();
        System.out.println("Jogos carregados: " + allGames.size());

        // 3. Cria arquivos de save simulados dentro de test-saves
        Files.writeString(saveTestPath.resolve("save1.dat"), "progresso 1");
        Files.writeString(saveTestPath.resolve("config.ini"), "config");

        Game game = new Game(
                "JogoTeste",
                "/caminho/do/jogo/nao/usado/agora", // não afeta o backup
                saveTestPath.toString(),
                null  // lastHash
        );

        // 4. Adiciona o jogo à lista (essencial!)
        allGames.add(game);

        // 5. Configura o BackupService
        BackupService backupService = new BackupService(
                backupBasePath.toString(),
                game,
                hashService,
                repo
        );

        // 6. Primeiro backup (deve ocorrer porque lastHash é null)
        System.out.println("\n--- Primeiro backup ---");
        backupService.saveFilesBackup(allGames);
        System.out.println("Hash: " + game.getLastHash());
        System.out.println("Data: " + game.getLastBackup());

        // 7. Segunda tentativa sem alteração (não deve gerar backup)
        System.out.println("\n--- Segunda tentativa (sem alteração) ---");
        backupService.saveFilesBackup(allGames);
        System.out.println("Hash ainda: " + game.getLastHash());

        // 8. Modifica um arquivo de save
        System.out.println("\n--- Alterando save ---");
        Files.writeString(saveTestPath.resolve("save1.dat"), "progresso 3");

        // 9. Backup após alteração (deve ocorrer)
        System.out.println("--- Backup após alteração ---");
        backupService.saveFilesBackup(allGames);
        System.out.println("Novo hash: " + game.getLastHash());
        System.out.println("Nova data: " + game.getLastBackup());

        // 10. Exibe o JSON resultante
        System.out.println("\n--- Conteúdo do JSON ---");
        List<Game> carregados = repo.loadAll();
        carregados.forEach(g ->
                System.out.println(g.getName() + " | Hash: " + g.getLastHash() + " | Backup: " + g.getLastBackup())
        );

        System.out.println("\nBackups criados em: " + backupBasePath.toAbsolutePath());
        System.out.println("JSON salvo em: " + repoFilePath.toAbsolutePath());
        System.out.println("Saves de teste em: " + saveTestPath.toAbsolutePath());





    }
}
