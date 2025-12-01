package src.restServices.requestObjects;

public class GetSimilarGamesRequest {
    
    private final int gameId;
    private final int moveNumber;
    private final int playerId;

    public GetSimilarGamesRequest(int gameId, int playerId, int moveNumber){
        this.gameId = gameId;
        this.moveNumber = moveNumber;
        this.playerId = playerId;
    }

    public GetSimilarGamesRequest(int gameId, int playerId){
        this.gameId = gameId;
        this.playerId = playerId;
        this.moveNumber = -1;
    }

    public int getGameId(){
        return gameId;
    }

    public int getMoveNumber(){
        return moveNumber;
    }

    public int getPlayerId(){
        return playerId;
    }
}
