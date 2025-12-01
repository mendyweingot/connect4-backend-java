package src.domainObjects;

import java.util.ArrayList;

import src.dataObjects.PlayerDataObject;
import src.models.PlayerModel;

public class PlayerDomainObject {
    private String name;
    private int id = -1;
    private int wins;
    private int losses;
    private int draws;
    private String password;

    public PlayerDomainObject(String name, String password){
        this.name = name;
        this.password = password;
    }

    public PlayerDomainObject(PlayerDataObject player){
        this.name = player.getName();
        this.id = player.getId();
        this.wins = player.getWins();
        this.losses = player.getLosses();
        this.draws = player.getDraws();
        this.password = player.getPassword();
    }

    public static ArrayList<PlayerDomainObject> MapList(ArrayList<PlayerDataObject> dataPlayers){
        ArrayList<PlayerDomainObject> domainPlayers = new ArrayList<>();
        for (PlayerDataObject player: dataPlayers){
            domainPlayers.add(new PlayerDomainObject(player));
        }
        return domainPlayers;
    }

    public String getName(){
        return name;
    }
    public int getId(){
        return id;
    }
    public int getWins(){
        return wins;
    }
    public int getLosses(){
        return losses;
    }
    public int getDraws(){
        return draws;
    }
    public String getPassword(){
        return password;
    }

    public PlayerDomainObject addWin(){
        wins++;
        PlayerDomainObject player =  PlayerModel.save(this);
        return player;
    }
    public PlayerDomainObject addLoss(){
        losses++;
        PlayerDomainObject player = PlayerModel.save(this);
        return player;
    }
    public PlayerDomainObject addDraw(){
        draws++;
        PlayerDomainObject player = PlayerModel.save(this);
        return player;
    }

    public PlayerDomainObject setName(String name){
        this.name = name;
        PlayerDomainObject player = PlayerModel.save(this);
        return player;
    }
    public PlayerDomainObject setPassword(String password){
        this.password = password;
        PlayerDomainObject player = PlayerModel.save(this);
        return player;
    }
}
