package src.dataAccess;

import java.util.*;
import src.dataObjects.*;

public class GameDataAccess{
    private static ArrayList<GameDataObject> games = new ArrayList<>();
    private static int nextId = 0;

    public static GameDataObject addGame(GameDataObject game){
        GameDataObject newGame = new GameDataObject(getNextId(), game.getPlayer1Id(), game.getPlayer2Id());
        newGame.setCurrentTurnPlayerId(game.getCurrentTurnPlayerId());
        newGame.setStatus(game.getStatus());
        newGame.setMoveNumber(game.getMoveNumber());
        games.add(newGame);
        return new GameDataObject(newGame);
    }

    public static GameDataObject getGameById(int id){
        for (GameDataObject game: games){
            if (game.getId() == id){
                return new GameDataObject(game);
            }
        }
        return null;
    }
    public static ArrayList<GameDataObject> getAllGames(){
        ArrayList<GameDataObject> gameList = new ArrayList<>();
        for (GameDataObject game: games){
            gameList.add(new GameDataObject(game));
        }
        return gameList;
    }
    public static ArrayList<GameDataObject> getGamesByPlayerId(int playerId){
        ArrayList<GameDataObject> gameList = new ArrayList<>();
        for (GameDataObject game: games){
            if (game.getPlayer1Id() == playerId || game.getPlayer2Id() == playerId){
                gameList.add(new GameDataObject(game));
            }
        }
        return gameList;
    }    
    
    public static GameDataObject save(GameDataObject game){
        for (GameDataObject gameToSave: games){
            if (game.getId() == gameToSave.getId()){
                gameToSave.setCurrentTurnPlayerId(game.getCurrentTurnPlayerId());
                gameToSave.setStatus(game.getStatus());
                gameToSave.setMoveNumber(game.getMoveNumber());
                return new GameDataObject(gameToSave);
            }
        }
        return null;        
    }

    private static int getNextId(){
        return nextId++;
    }
}