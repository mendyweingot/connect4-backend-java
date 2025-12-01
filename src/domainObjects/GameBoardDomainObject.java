package src.domainObjects;

import src.dataObjects.GameBoardDataObject;
import src.models.GameBoardModel;
import src.models.GameModel;

public class GameBoardDomainObject {
    private int gameId;
    private int[][] board;

    private GameDomainObject game;

    public GameBoardDomainObject(int gameId){
        this.gameId = gameId;
        this.board = new int[6][7];
    }
    public GameBoardDomainObject(GameBoardDataObject gameBoard){
        this.gameId = gameBoard.getGameId();
        this.board = gameBoard.getBoard();
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

    public GameDomainObject getGame(){
        if (game == null){
            game = GameModel.getGameById(gameId);
        }
        return game;
    }

    public int playMove(int column, int value){
        //returns -4 if gameboard not found in database
        // returns -3 if column is already full
        // returns -2 if move was successful and game was drawn
        // returns -1 if move was successful and game continues
        // returns  1 if move was successful and game was won.
        return GameBoardModel.playMove(this, column, value);
    }
}
