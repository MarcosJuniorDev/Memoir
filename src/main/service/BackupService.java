package main.service;

import main.model.Game;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;

public class BackupService {
    private Game game;
    private HashFileSave hashFileSave = new HashFileSave();
    private GameRepository gameRepository = new GameRepository();

    public BackupService(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public HashFileSave getHashFileSave() {
        return hashFileSave;
    }

    public void setHashFileSave(HashFileSave hashFileSave) {
        this.hashFileSave = hashFileSave;
    }

    public GameRepository getGameRepository() {
        return gameRepository;
    }

    public void setGameRepository(GameRepository gameRepository) {

        this.gameRepository = gameRepository;
    }

    //HELPER NECESSARIO PORQUE A PORRA DO WINDOWS NAO DEIXA SALVAR PASTA COM ":"
    //E EM UM EXEMPLO BATMAN: ARKHAM ELE QUEBRA A APLICACAO
    private String getSafeBackupFolderName(){
        return game.getName()
                .replaceAll("[\\\\/:*?\"<>|]", "").trim();
    }

    public void ensureBackupDirecotry()throws IOException {
        if (!Files.exists(Paths.get(game.getBackupLocation(), getSafeBackupFolderName()))){
            Files.createDirectories(Paths.get(game.getBackupLocation(), getSafeBackupFolderName()));
        }
    }

    public void deleteBackupFile(String backupPath, String folderName) throws IOException{
        //AJUSTE PRO WINDOWS
        String safeFolderName = folderName.replaceAll("[\\\\/:*?\"<>|]", "").trim();
        FileUtils.deleteDirectory(new File(backupPath, safeFolderName));
    }


    public String verifyCurrentHash() throws NoSuchAlgorithmException{
        return hashFileSave.SaveFileHash(Paths.get(game.getSaveGamePath()));
    }
    public String verifyHashBackupFolder() throws NoSuchAlgorithmException{
        return hashFileSave.SaveFileHash(Paths.get(game.getBackupLocation(),  getSafeBackupFolderName()));
    }

    public boolean isBackupNeeded(String currentHash){
        if (!Files.exists(Paths.get(game.getBackupLocation(), getSafeBackupFolderName()))){
            return true;
        }
        String lasthash = game.getLastHash();
        return !currentHash.equals(lasthash);
    }

    public boolean gameBackupFolderExist(){

        return Files.exists(Paths.get(game.getBackupLocation(), getSafeBackupFolderName()));
    }

    public boolean saveFilesBackup() throws Exception{
        String currentHash = verifyCurrentHash();
        if (isBackupNeeded(currentHash)) {
            System.out.println("backup realizado");
            ensureBackupDirecotry();
            FileUtils.copyDirectory(Paths.get(game.getSaveGamePath()).toFile(), Paths.get(game.getBackupLocation(), getSafeBackupFolderName()).toFile());
            game.setLastHash(currentHash);
            game.setLastBackup();
            gameRepository.update(game);
            return true;
        }
        System.out.println("backup skipado");
        return false;
    }

   public boolean restoreBackup() throws IOException {
        if (gameBackupFolderExist()){
            FileUtils.copyDirectory(Paths.get(game.getBackupLocation(), getSafeBackupFolderName()).toFile(), Paths.get(game.getSaveGamePath()).toFile());
            return true;
        }
        else {
            return false;
        }
    }
}
