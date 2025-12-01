package src.restServices.requestObjects;

public class PlayMoveRequest {
    private final int gameId;
    private final int playerId;
    private final int columnNumber;

    public PlayMoveRequest(int gameId, int playerId, int columnNumber){
        this.gameId = gameId;
        this.playerId = playerId;
        this.columnNumber = columnNumber;
    }

    public int getGameId(){
        return gameId;
    }
    public int getPlayerId(){
        return playerId;
    }
    public int getColumnNumber(){
        return columnNumber;
    }
}
