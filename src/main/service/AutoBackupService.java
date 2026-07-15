package main.service;

import javax.swing.*;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AutoBackupService {

    private ScheduledExecutorService watcher;
    private final String gameExec;
    private boolean isGameRunning;
    BackupService bkService;

    private Runnable onBackupSuccess;


    public AutoBackupService(String gameExec, BackupService bkService) {
        this.gameExec = gameExec;
        this.bkService = bkService;

    }

    public void setOnBackupSuccess(Runnable onBackupSuccess) {
        this.onBackupSuccess = onBackupSuccess;
    }

    //COMECA MONITORAR O PROCESSO EM SEGUNDO PLANO
    public void beginWatch(){
        //THREAD SEPARADA PARA VIGIAR O GAME
        watcher = Executors.newSingleThreadScheduledExecutor();

        System.out.println("Busca pelo exec do game: " + gameExec);

        watcher.scheduleAtFixedRate(this::searchGameProcess, 0, 3, TimeUnit.MINUTES);

    }

    //CASO O USER QUEIRA DESATIVAR O AUTO-BACKUP
    public void stopWatch() {
        if (watcher != null && !watcher.isShutdown()) {
            watcher.shutdown();
            System.out.println("Monitoramento desativado");
            isGameRunning = false;
        }
    }

    //BUSCA PELO PROCESSO DO JOGO
    public void searchGameProcess() {
        //MEMOIR SABE QUE O JOGO TA BUSCANDO ENTÃO NAO PRECISA BUSCAR MAIS POR ELE
        if(isGameRunning){
            return;
        }

        Optional<ProcessHandle> gameExecFound = ProcessHandle.allProcesses()
                .filter(p -> {
                    ProcessHandle.Info info = p.info();
                    String targetExec = gameExec.toLowerCase().trim();

                    // 1. Verifica no comando principal (Padrão do Windows)
                    boolean foundInCommand = info.command()
                            .map(c -> c.toLowerCase().contains(targetExec))
                            .orElse(false);

                    // 2. Verifica na linha de comando completa (Padrão do Linux/Proton/Wine)
                    boolean foundInCommandLine = info.commandLine()
                            .map(c -> c.toLowerCase().contains(targetExec))
                            .orElse(false);

                    // Se achou em qualquer um dos dois lugares, é o nosso jogo!
                    return foundInCommand || foundInCommandLine;
                })
                .findFirst();

        if (gameExecFound.isPresent()){
            ProcessHandle gameExecProcess = gameExecFound.get();
            isGameRunning = true;

            System.out.println("Jogo: " + gameExec + " detectado em execução! PID: " + gameExecProcess.pid());


            //GATILHO PRO JOGO FECHAR
            CompletableFuture<ProcessHandle> gameExecEnding = gameExecProcess.onExit();

            gameExecEnding.thenAccept(processHandle -> {
                System.out.println("Game Fechou iniciando verificação de backup!");
                try {
                    boolean backupDone = bkService.saveFilesBackup();

                    if(onBackupSuccess != null){
                        SwingUtilities.invokeLater(onBackupSuccess);
                    }
                } catch (Exception e) {
                    System.out.println("Erro ao salvar arquivo: " + e.getMessage());
                }

                isGameRunning = false;
                System.out.println("Vigiando o processo novamente!");
            });

        }
    }

}
