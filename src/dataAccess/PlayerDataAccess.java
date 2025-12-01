package src.dataAccess;

import java.util.ArrayList;

import src.dataObjects.*;

public class PlayerDataAccess{

    private static ArrayList<PlayerDataObject> players = new ArrayList<>();
    private static int nextId = 0;

    public static PlayerDataObject addPlayer(PlayerDataObject player){
        PlayerDataObject newPlayer = new PlayerDataObject(player.getName(), getNextId(), player.getPassword());
        newPlayer.setWins(player.getWins());
        newPlayer.setLosses(player.getLosses());
        newPlayer.setDraws(player.getDraws());
        players.add(newPlayer);
        return new PlayerDataObject(newPlayer);
    }

    public static PlayerDataObject getPlayerById(int id){
        for (PlayerDataObject player: players){
            if (player.getId() == id){
                return new PlayerDataObject(player);
            }
        }
        return null;
    }

    public static ArrayList<PlayerDataObject> getAllPlayers(){
        ArrayList<PlayerDataObject> playerList = new ArrayList<>();
        for (PlayerDataObject player: players){
            playerList.add(new PlayerDataObject(player));
        }
        return playerList;
    }
    

    public static PlayerDataObject save(PlayerDataObject player){
        for (PlayerDataObject playerToSave: players){
            if (player.getId() == playerToSave.getId()){
                playerToSave.setWins(player.getWins());
                playerToSave.setLosses(player.getLosses());
                playerToSave.setDraws(player.getDraws());
                playerToSave.setName(player.getName());
                playerToSave.setPassword(player.getPassword());
                return new PlayerDataObject(playerToSave);
            }
        }
        return null;        
    }

    private static int getNextId(){
        return nextId++;
    }
}