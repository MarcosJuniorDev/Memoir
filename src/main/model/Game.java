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
    private String gamePath;
    private String saveGamePath;
    private LocalDateTime lastBackup = null;
    private String lastHash;
    private String backupLocation;
    private String coverPath;

    public Game(String name, String gamePath, String saveGamePath, String lastHash, String backupLocation, String coverPath) {
        this.name = name;
        this.gamePath = gamePath;
        this.saveGamePath = saveGamePath;
        this.lastHash = lastHash;
        this.backupLocation = backupLocation;
        this.coverPath = coverPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGamePath() {
        return gamePath;
    }

    public void setGamePath(String gamePath) {
        this.gamePath = gamePath;
    }

    public String getSaveGamePath() {
        return saveGamePath;
    }

    public void setSaveGamePath(String saveGamePath) {
        this.saveGamePath = saveGamePath;
    }

    public String getLastBackup() {
        if (this.lastBackup == null) {
            return "no backup yet";
        }
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

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }
}
