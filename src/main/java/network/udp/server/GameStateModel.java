package network.udp.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;

public class GameStateModel
{
    private JsonObject gameState;

    public GameStateModel() {
        // Load initial game state from a JSON file
        gameState = loadGameStateFromFile("game_state.json");
    }

    private JsonObject loadGameStateFromFile(String filePath) {
        try {
            FileReader fileReader = new FileReader(filePath);
            JsonParser jsonParser = new JsonParser();
            return jsonParser.parse(fileReader).getAsJsonObject();
        } catch (FileNotFoundException e) {
            // Handle file not found error
            e.printStackTrace();
            return null;
        }
    }

    public void updateGameState() {
        // Update the game state logic here

        // Example: Increment the player's score
        int currentScore = gameState.get("score").getAsInt();
        int newScore = currentScore + 10;
        gameState.addProperty("score", newScore);

        // Save the updated game state to the JSON file
        saveGameStateToFile("game_state.json", gameState);
    }

    private void saveGameStateToFile(String filePath, JsonObject gameState) {
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonString = gson.toJson(gameState);
            fileWriter.write(jsonString);
            fileWriter.close();
        } catch (IOException e) {
            // Handle file write error
            e.printStackTrace();
        }
    }
}