package src.restServices.responseObjects;

import java.util.ArrayList;

public class GetSimilarGamesResponse {
    
    private final boolean success;
    private final String STATUS_MESSAGE;
    private final int gameId;
    private final int moveNumber;
    private final int playerId;
    private final int[][] board;
    private final ArrayList<Integer> games;

    public GetSimilarGamesResponse(int gameId, int moveNumber, int playerId, int[][] board, ArrayList<Integer> games){
        success = true;
        STATUS_MESSAGE = "";
        this.gameId = gameId;
        this.moveNumber = moveNumber;
        this.playerId = playerId;
        this.board = board;
        this.games = games;
    }

    public GetSimilarGamesResponse(String statusMessage, int gameId, int moveNumber, int playerId){
        success = false;
        board = null;
        games = null;
        this.STATUS_MESSAGE = statusMessage;
        this.gameId = gameId;
        this.moveNumber = moveNumber;
        this.playerId = playerId;
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

    public int getMoveNumber(){
        return moveNumber;
    }

    public int getPlayerId(){
        return playerId;
    }

    public int[][] getBoard(){
        return board;
    }

    public ArrayList<Integer> getGames(){
        return games;
    }
}
