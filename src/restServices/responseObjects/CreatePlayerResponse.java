package src.restServices.responseObjects;

public class CreatePlayerResponse {
    
    private final boolean success;
    private final String STATUS_MESSAGE;
    private final int playerId;
    private final String name;

    public CreatePlayerResponse(int playerId, String name){
        this.playerId = playerId;
        this.name = name;
        this.success = true;
        this.STATUS_MESSAGE = "";
    }

    public CreatePlayerResponse(String statusMessage, String name){
        this.playerId = -1;
        this.name = name;
        this.success = false;
        this.STATUS_MESSAGE = statusMessage;
    }

    public int getPlayerId(){
        return playerId;
    }

    public String getName(){
        return name;
    }

    public boolean wasSuccess(){
        return success;
    }

    public String getStatusMessage(){
        return STATUS_MESSAGE;
    }
}
