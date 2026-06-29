package main.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/*
    VAI SER RESPOSÁVEL POR LIDAR COM OS DADOS DO PROJETO
    AINDA SEM IMPLEMENTAÇÃO DE HASH ENTÃO ELE ESTA COMO NULL POR ENQUANTO.
    1-> ALTERAR CONSTRUTOR PARA TER HASH
*/

public class Game {
    private String name;
    private String GamePath;
    private String SaveGamePath;
    private LocalDateTime lastBackup = null;
    private String lastHash;
    private String backupLocation;

    public Game() {
    }

    public Game(String name, String gamePath, String saveGamePath, String lastHash, String backupLocation) {
        this.name = name;
        this.GamePath = gamePath;
        this.SaveGamePath = saveGamePath;
        this.lastHash = lastHash;
        this.backupLocation = backupLocation;
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

    public void setLastBackup() {
        this.lastBackup = LocalDateTime.now();
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

    public String getBackupLocation() {
        return backupLocation;
    }

    public void setBackupLocation(String backupLocation) {
        this.backupLocation = backupLocation;
    }
}
