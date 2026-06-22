package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Game;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GameRepository {
    public void GameData(List<Game> GamesInfos){
        try {

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String GameDataJson = gson.toJson(GamesInfos);

            FileWriter fw = new FileWriter("gameData.json");
            fw.write(GameDataJson);
            fw.close();

        }catch (Exception e){
            System.out.println("Erro nessa porra");
        }
    }
}
