package main.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import main.model.Game;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/*
    IMPLEMENTAÇÃO BEM BASICA DE JSON APENAS ESCREVENDO EM UM ARQUIVO
    AINDA PRECISO PENSAR EM COMO ESTRUTURAR, POR ENQUANTO ESTA APENAS ALGO PRIMITIVO PARA TESTE
    DE FUNCIONAMENTO.
 */
public class GameRepository {
    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter().nullSafe()).create();

    public void saveAll(List<Game> GamesInfos){
        try {
            String GameDataJson = gson.toJson(GamesInfos);

            FileWriter fw = new FileWriter("gameData.json");
            fw.write(GameDataJson);
            fw.close();

        }catch (Exception e){
            System.out.println("Erro nessa porra");
            e.printStackTrace();
        }
    }

    public List<Game> loadAll(){
        try (FileReader fr = new FileReader("gameData.json")){
            return gson.fromJson(fr, new TypeToken<List<Game>>(){}.getType());
        } catch (FileNotFoundException e){
            return new ArrayList<>();
        } catch (IOException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void update(Game updatedGame) {
        List<Game> allGames = loadAll();

        for (int i = 0; i < allGames.size(); i++) {
            if (allGames.get(i).getName().equals(updatedGame.getName())){
                allGames.set(i, updatedGame);
            }
        }

        saveAll(allGames);
    }

    public void deleteGame(String gameName){
        List<Game> allGames = loadAll();

        allGames.removeIf(game -> game.getName().equals(gameName));

        saveAll(allGames);

    }

}
