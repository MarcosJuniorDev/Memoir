package main.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import main.model.Game;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/*
    IMPLEMENTAÇÃO BEM BASICA DE JSON APENAS ESCREVENDO EM UM ARQUIVO
    AINDA PRECISO PENSAR EM COMO ESTRUTURAR, POR ENQUANTO ESTA APENAS ALGO PRIMITIVO PARA TESTE
    DE FUNCIONAMENTO.
 */
public class GameRepository {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
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
            List<Game> gameToLoad = gson.fromJson(fr, new TypeToken<List<Game>>(){}.getType());
            return gameToLoad;
        } catch (FileNotFoundException e){
            return new ArrayList<>();
        } catch (IOException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
