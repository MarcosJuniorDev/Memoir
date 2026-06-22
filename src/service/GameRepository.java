package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Game;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
/*
    IMPLEMENTAÇÃO BEM BASICA DE JSON APENAS ESCREVENDO EM UM ARQUIVO
    AINDA PRECISO PENSAR EM COMO ESTRUTURAR, POR ENQUANTO ESTA APENAS ALGO PRIMITIVO PARA TESTE
    DE FUNCIONAMENTO.
 */
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
