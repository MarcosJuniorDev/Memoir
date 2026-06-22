package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Game {
    private String name;
    private String GamePath;
    private String SaveGamePath;
    private LocalDateTime lastBackup;
    private String lastHash;

    public Game() {
    }

    public Game(String name, String gamePath, String saveGamePath) {
        this.name = name;
        this.GamePath = gamePath;
        this.SaveGamePath = saveGamePath;
        lastBackup = LocalDateTime.now();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGamePath() {
        return GamePath;
    }

    public void setGamePath(String gamePath) {
        GamePath = gamePath;
    }

    public String getSaveGamePath() {
        return SaveGamePath;
    }

    public void setSaveGamePath(String saveGamePath) {
        SaveGamePath = saveGamePath;
    }

    public String getLastBackup() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return lastBackup.format(formatter);
    }

    public void updateLastBackup(){
        lastBackup = LocalDateTime.now();
    }

    public String getLastHash() {
        return lastHash;
    }

    public void setLastHash(String lastHash) {
        this.lastHash = lastHash;
    }
}
