package src.models;

import java.util.ArrayList;

import src.dataAccess.GameDataAccess;
import src.dataObjects.GameDataObject;
import src.domainObjects.GameDomainObject;
import src.domainObjects.PlayerDomainObject;

public class GameModel {

    public static GameDomainObject addGame(GameDomainObject game){
        return new GameDomainObject(GameDataAccess.addGame(new GameDataObject(game)));
    }

    public static GameDomainObject getGameById(int id){
        GameDataObject game = GameDataAccess.getGameById(id);
        if (game == null) return null;
        return new GameDomainObject(game);
    }

    public static ArrayList<GameDomainObject> getAllGames(){
        return GameDomainObject.MapList(GameDataAccess.getAllGames());
    }

    public static ArrayList<GameDomainObject> getGamesByPlayerId(int playerId){
        return GameDomainObject.MapList(GameDataAccess.getGamesByPlayerId(playerId));
    }

    public static GameDomainObject save(GameDomainObject game){
        GameDataObject gameData = GameDataAccess.save(new GameDataObject(game));
        if (gameData == null) return null;
        return new GameDomainObject(gameData);
    }    

    public static GameDomainObject switchCurrentTurnPlayerId(GameDomainObject game){
        int nextCurrentTurnPlayerId = (game.getCurrentTurnPlayerId() == game.getPlayer1Id())? game.getPlayer2Id(): game.getPlayer1Id();
        GameDomainObject savedGame = game.setCurrentTurnPlayerId(nextCurrentTurnPlayerId);
        return savedGame;
    }

    //returns 1 if player is registered in game as Player1, returns 2 if playerId is registered in game as Player2,
    //returns -1 if player is not registered in game
    public static int getPlayerValueInGame(GameDomainObject game, PlayerDomainObject player){
        int playerId = player.getId();
        return (game.getPlayer1Id() == playerId)? 1: (game.getPlayer2Id() == playerId)? 2 : -1;
    } 
}
