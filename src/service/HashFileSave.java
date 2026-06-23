package service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class HashFileSave {

    public String SaveFileHash(Path filePath) throws NoSuchAlgorithmException {

        /*
        IMPLEMENTAÇÃO DE SIMPLES DE CALCULO DE HASH DE MULTIPLOS ARQUIVOS
        SE A COMBINAÇÃO DOS ARQUIVOS DER UM HASH DIFERENTE ENTÃO O SAVE FILE DO JOGO MUDOU
        E COM ISSO UM NOVO BACKUP PODE SER REALIZADO.
         */
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        try {
            List<Path> filesInPath = Files.walk(filePath)
                    .filter(Files::isRegularFile)
                    .sorted()
                    .toList();
            for (Path f : filesInPath){
                try (InputStream is = Files.newInputStream(f)){
                    byte[] buffer = new byte[8192];
                    int readBytes;
                    while ((readBytes = is.read(buffer)) != -1){
                        md.update(buffer, 0, readBytes);
                    }
                }
            }
            byte[] hashBytes = md.digest();
            return HexConverter(hashBytes);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    private String HexConverter(byte[] hashBytes){
        StringBuilder sb = new StringBuilder();
        for(byte b : hashBytes){
            String hex = String.format("%02x", b & 0xff);
            sb.append(hex);
        }
        System.out.println(sb.toString());
        return sb.toString();
    }


}
