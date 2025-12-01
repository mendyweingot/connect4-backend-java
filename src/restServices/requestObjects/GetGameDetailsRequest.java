package src.restServices.requestObjects;

public class GetGameDetailsRequest {
    private final int gameId;  

    public GetGameDetailsRequest(int gameId, int moveNumber){
        this.gameId = gameId;
    }

    public GetGameDetailsRequest(int gameId){
        this(gameId, -1);
    }

    public int getGameId(){
        return gameId;
    }
}
