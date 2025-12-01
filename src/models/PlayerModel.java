package src.models;

import java.util.ArrayList;
import java.util.HashSet;

import src.dataAccess.PlayerDataAccess;
import src.dataObjects.PlayerDataObject;
import src.domainObjects.PlayerDomainObject;

public class PlayerModel {

    public static PlayerDomainObject addPlayer(PlayerDomainObject player){
        return new PlayerDomainObject(PlayerDataAccess.addPlayer(new PlayerDataObject(player)));
    }

    public static PlayerDomainObject getPlayerById(int id){
        PlayerDataObject player = PlayerDataAccess.getPlayerById(id);
        if (player == null) return null;
        return new PlayerDomainObject(player);
    }

    public static ArrayList<PlayerDomainObject> getAllPlayers(){
        return PlayerDomainObject.MapList(PlayerDataAccess.getAllPlayers());
    }    

    public static PlayerDomainObject save(PlayerDomainObject player){
        PlayerDataObject playerData = PlayerDataAccess.save(new PlayerDataObject(player));
        if (playerData == null) return null;
        return new PlayerDomainObject(playerData);
    }
    
    public static String validate(String name, String password){
        if (name.length() < 5 || name.length() > 20) return "Invalid Username. Username must be between five and twenty characters long.";
        else if (!name.matches("[a-zA-Z0-9]*")) return "Invalid Username. Username must only contain letters and digits.";
        else if (getAllPlayerNames().contains(name)) return "Username " + name + " is already taken.";
        else if (password.length() < 5 || password.length() > 20) return "Invalid Password. Password must be between five and twenty characters long.";
        else if (!password.matches("[a-zA-Z0-9]*")) return "Invalid Password. Password must only contain letters and digits.";
        return null;
    }

    private static HashSet<String> getAllPlayerNames(){
        HashSet<String> names = new HashSet<>();
        for (PlayerDomainObject player: getAllPlayers()){
            names.add(player.getName());
        }
        return names;
    } 
}
