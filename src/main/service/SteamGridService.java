package main.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.swing.*;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

public class SteamGridService {
    private String API_KEY;
    private static final String BASE_URL = "https://www.steamgriddb.com/api/v2";

    private final HttpClient httpClient;
    private final Gson gson;

    public SteamGridService(){
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new Gson();

        loadApiKey();
    }

    private void loadApiKey(){
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("config.properties")){
            if (is == null){
                System.err.println("File with API Key not found");
                return;
            }
            Properties prop = new Properties();
            prop.load(is);
            this.API_KEY = prop.getProperty("steamgrid.api.key");
        } catch (Exception e){
            System.err.println("Error reading API KEY: " + e.getMessage());
        }
    }


    //REQUISICAO VIA HTTP REQUEST PARA DESCOBRIR ID DO GAME PELO NOME
    public String findIdGame(String gameName){
        String findGameByName = gameName.replace(" ", "%20");

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/search/autocomplete/" + findGameByName))
                    .header("Authorization", "Bearer " + API_KEY)
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
            JsonArray dataArray = jsonObject.getAsJsonArray("data");

            if (dataArray != null && !dataArray.isEmpty()){
                return dataArray.get(0).getAsJsonObject().get("id").getAsString();
            }

        } catch (Exception e){
            System.err.println("Error API REQUEST" + e.getMessage());
        }
        return null;
    }

    //BUSCAR PELO ID
    public String getCover(String gameID, String saveName){
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/grids/game/" + gameID))
                    .header("Authorization", "Bearer " + API_KEY)
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            //RESPOSTA DA API SO PARA VER RESPOSTA VOU DEIXAR POR ENQUANTO

            System.out.println("===== RESPOSTA CRUA DA API =====");
            System.out.println(response.body());
            System.out.println("================================");

            JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
            JsonArray dataArray = jsonObject.getAsJsonArray("data");

            if (dataArray != null && !dataArray.isEmpty()){
                //PEGAR CAPA MAIS CURTIDA
                String urlCover = dataArray.get(0).getAsJsonObject().get("url").getAsString();

                return coverDownload(urlCover, saveName);
            }


        } catch (Exception e){
            System.out.println("Error API request: " + e.getMessage());
        }
        System.out.println("FOI NULLO? QUE CAPETA");
        return null;
    }

    private String coverDownload(String urlString, String fileName) throws Exception {
        Path folderLocation = Paths.get("data", "covers");
        try {
            Files.createDirectories(folderLocation);
        }catch (Exception e){
            System.out.println("FOI????? " + e.getMessage());
        }

        String extension = urlString.substring(urlString.lastIndexOf("."));

        Path finalFile = folderLocation.resolve(fileName.replace(" ", "-") + extension);

        try (InputStream  is = URI.create(urlString).toURL().openStream()){
            Files.copy(is, finalFile, StandardCopyOption.REPLACE_EXISTING);
        }

        return finalFile.toString();
    }
}
