package src.dataObjects;

import src.domainObjects.GameBoardDomainObject;

public class GameBoardDataObject{
    private int gameId;
    private int[][] board;

    public GameBoardDataObject(int gameId){
        this.gameId = gameId;
        board = new int[6][7];
    }
    public GameBoardDataObject(GameBoardDataObject gameBoard){
        this.gameId = gameBoard.getGameId();        
        this.board = gameBoard.getBoardSafely();
    }
    public GameBoardDataObject(GameBoardDomainObject gameBoard){
        this.gameId = gameBoard.getGameId();
        this.board = gameBoard.getBoardSafely();
    }

    public int getGameId(){
        return gameId;
    }
    public int[][] getBoard(){
        return board;        
    }
    public int[][] getBoardSafely(){
        int[][] newBoard = new int[board.length][board[0].length];
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[0].length; j++){
                newBoard[i][j] = board[i][j];
            }
        }
        return newBoard;
    }
}