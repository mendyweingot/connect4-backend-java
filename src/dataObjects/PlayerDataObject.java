package src.dataObjects;

import src.domainObjects.PlayerDomainObject;

public class PlayerDataObject{
    private String name;
    private int id;
    private String password;
    private int wins;
    private int losses;
    private int draws;

    public PlayerDataObject(String name, int id, String password){
        this.name = name;
        this.id = id;
        this.password = password;
    }

    public PlayerDataObject(PlayerDataObject player){
        this.name = player.getName();
        this.id = player.getId();
        this.wins = player.getWins();
        this.losses = player.getLosses();
        this.draws = player.getDraws();
        this.password = player.getPassword();
    }

    public PlayerDataObject(PlayerDomainObject player){
        this.name = player.getName();
        this.id = player.getId();
        this.wins = player.getWins();
        this.losses = player.getLosses();
        this.draws = player.getDraws();
        this.password = player.getPassword();
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

    public void setWins(int wins){
        this.wins = wins;
    }
    public void setLosses(int losses){
        this.losses = losses;
    }
    public void setDraws(int draws){
        this.draws = draws;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setPassword(String password){
        this.password = password;
    }
}