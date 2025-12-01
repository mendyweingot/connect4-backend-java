package src.restServices.requestObjects;

public class CreatePlayerRequest {

    private final String playerName;
    private final String password;

    public CreatePlayerRequest(String playerName, String password){
        this.playerName = playerName;
        this.password = password;
    }

    public String getPlayerName(){
        return playerName;
    }

    public String getPassword(){
        return password;
    }
}
