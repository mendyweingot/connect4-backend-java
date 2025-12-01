package src.restServices.responseObjects;

public class CreateGameResponse {
    
    private final boolean success;
    private final String STATUS_MESSAGE;
    private final int gameId;
    private final int player1Id;
    private final int player2Id;
    private final int currentTurnPlayerId;
    private final int[][] board;

    public CreateGameResponse(int gameId, int player1Id, int player2Id, int currentTurnPlayerId, int[][] board){
        this.success = true;
        this.STATUS_MESSAGE = "";
        this.gameId = gameId;
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        this.currentTurnPlayerId = currentTurnPlayerId;
        this.board = board;
    }

    public CreateGameResponse(String statusMessage, int player1Id, int player2Id){
        success = false;
        STATUS_MESSAGE = statusMessage;
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        gameId = -1;
        currentTurnPlayerId = -1;
        board = null;
    }

    public boolean wasSuccess(){
        return success;
    }

    public int getGameId(){
        return gameId;
    }

    public String getStatusMessage(){
        return STATUS_MESSAGE;
    }

    public int getPlayer1Id(){
        return player1Id;
    }

    public int getPlayer2Id(){
        return player2Id;
    }

    public int getCurrentTurnPlayerId(){
        return currentTurnPlayerId;
    }

    public int[][] getBoard(){
        return board;
    }
}
