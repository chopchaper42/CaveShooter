package Engine.Level;

import Engine.Entity.Tile.Door;
import Engine.Entity.Tile.Floor;
import Engine.Entity.Tile.Tile;
import Engine.Entity.Tile.Wall;
import Logs.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LevelReader
{
    /**
     * Reads a level from the specified file
     * @param file file with level description
     * @return {@code levelInfo} if the reading went good, otherwise {@code null}
     */
    public static LevelInfo readLevel(File file)
    {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            return parseLevel(reader);

        } catch (IOException exception) {
            Logger.log(exception.getMessage());
            System.exit(-1);

        }
        return null;
    }

    /*
     * Level editor will always generate a "rectangular" text file,
     * so on each row the width of the level is constant.
     *
     * What if the Level editor will make a list of Tiles from the user's input and serialize it to JSON,
     * so here we will only deserialize it
     */
    private static LevelInfo parseLevel(BufferedReader reader) throws IOException
    {
        String currentLine;
        List<Tile> tiles = new ArrayList<>();
        int levelWidth = 0;
        int levelHeight = 0;
        int rowNumber = 0;

        while ( (currentLine = reader.readLine()) != null)
        {
            String[] levelRow = currentLine.split(" ");
            int posY = rowNumber * Tile.TILE_SIZE;
            int posX = 0;

            levelWidth = 0;
            levelHeight += Tile.TILE_SIZE;

            for (String s : levelRow) {

                switch (s) {
                    case "w" -> tiles.add(new Wall(posX, posY));
                    case "d" -> tiles.add(new Door(posX, posY));
                    default -> tiles.add(new Floor(posX, posY));
                }

                posX += Tile.TILE_SIZE;
                levelWidth += Tile.TILE_SIZE;
            }
            rowNumber++;
        }
        return new LevelInfo(tiles, levelHeight, levelWidth);
    }
}
