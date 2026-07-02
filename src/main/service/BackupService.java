package main.service;

import main.model.Game;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.List;

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

    public void ensureBackupDirecotry()throws IOException, NoSuchAlgorithmException {
        if (!Files.exists(Paths.get(game.getBackupLocation(), game.getName()))){
            Files.createDirectories(Paths.get(game.getBackupLocation(), game.getName()));
        }
    }


    public String verifyCurrentHash() throws NoSuchAlgorithmException{
        return hashFileSave.SaveFileHash(Paths.get(game.getSaveGamePath()));
    }

    public boolean isBackupNeeded(String currentHash){
        String lasthash = game.getLastHash();
        return !currentHash.equals(lasthash);
    }

    public boolean saveFilesBackup() throws Exception{
        String currentHash = verifyCurrentHash();
        if (isBackupNeeded(currentHash)) {
            ensureBackupDirecotry();
            FileUtils.copyDirectory(Paths.get(game.getSaveGamePath()).toFile(), Paths.get(game.getBackupLocation(), game.getName()).toFile());
            game.setLastHash(currentHash);
            game.setLastBackup();
            gameRepository.update(game);
            return true;
        }
        return false;
    }
}
