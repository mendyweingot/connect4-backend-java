package src.restServices.responseObjects;

public class GetGameDetailsResponse {

    public static final String PLAYING = "Playing";
    public static final String COMPLETE = "Complete";

    private final boolean success;
    private final String STATUS_MESSAGE;
    private final int gameId;
    private final int player1Id;
    private final int player2Id;
    private final int currentTurnPlayerId;
    private final int moveNumber;
    private final String status;
    private final int winner;
    private final int[][] board;

    public GetGameDetailsResponse(int gameId, int moveNumber, int player1Id, int player2Id, int currentTurnPlayerId, String status, int[][] board, int winner){
        success = true;
        STATUS_MESSAGE = "";
        this.gameId = gameId; 
        this.moveNumber = moveNumber;
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        this.currentTurnPlayerId = currentTurnPlayerId;
        this.status = status;
        this.board = board;
        this.winner = winner;
    }

    public GetGameDetailsResponse(int gameId, int moveNumber, int player1Id, int player2Id, int currentTurnPlayerId, String status, int[][] board){
        this(gameId, moveNumber, player1Id, player2Id, currentTurnPlayerId, status, board, -1);
    }

    public GetGameDetailsResponse(String statusMessage, int gameId){
        success = false;
        STATUS_MESSAGE = statusMessage;
        this.gameId = gameId;
        moveNumber = -1;
        player1Id = -1;
        player2Id = -1;
        currentTurnPlayerId = -1;
        status = "";
        winner = -1;
        board = null;
    }

    public boolean wasSuccess(){
        return success;
    }

    public String getStatusMessage(){
        return STATUS_MESSAGE;
    }

    public int getGameId(){
        return gameId;
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

    public int getMoveNumber(){
        return moveNumber;
    }

    public String getStatus(){
        return status;
    }

    public int getWinner(){
        return winner;
    }

    public int[][] getBoard(){
        return board;
    }
}
