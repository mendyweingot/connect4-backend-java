package src.models;

import src.dataAccess.GameBoardDataAccess;
import src.dataObjects.GameBoardDataObject;
import src.domainObjects.GameBoardDomainObject;

public class GameBoardModel {

    public static GameBoardDomainObject addBoard(GameBoardDomainObject board){
        return new GameBoardDomainObject(GameBoardDataAccess.addBaord(new GameBoardDataObject(board)));
    }

    public static GameBoardDomainObject getGameBoardByGameId(int id){
        GameBoardDataObject gameBoard = GameBoardDataAccess.getGameBoardByGameId(id);
        if (gameBoard == null) return null;
        return new GameBoardDomainObject(gameBoard);
    }

    //returns -4 if gameboard with gameId was not found in database
    //returns -3 if selected column was full
    //returns -2 if move was successful and game is drawn
    //returns -1 if move was successful and game continues
    //returns 1 if move was successful and game is won
    public static int playMove(GameBoardDomainObject gameBoard, int column, int value){
        int[][] board = gameBoard.getBoard();

        int row = getAvailableRow(board, column);
        if (row == -1) return -3;

        board[row][column] = value;
        if (GameBoardDataAccess.addMoveToGameBoard(gameBoard.getGameId(), row, column, value) == null) return -4;

        if (hasWon(board, row, column)) return 1;
        if (isDraw(board)) return -2;

        return -1;
    }

    //checks if for all the columns the top row is already taken
    private static boolean isDraw(int[][] board){
        for (int i = 0; i < board[0].length; i++){
            if (board[0][i] == 0){
                return false;
            }
        }
        return true;
    }

    private static boolean hasWon(int[][] board, int row, int column){
        int winLen = 4; //have this as a variable in case later win length may want to be manipulated to be three or five....

        if (hasWonInDirection(board, row, column, 1, 0, winLen)) return true; //checks for vertical win.
        if (hasWonInDirection(board, row, column, 0, 1, winLen)) return true; //checks for horizontal win.
        if (hasWonInDirection(board, row, column, -1, 1, winLen)) return true; //checks for right diagonal win.
        if (hasWonInDirection(board, row, column, 1, 1, winLen)) return true; //checks for left diagonal win.
        return false;
    }

    //for a given coordinate in the grid and given directions for row and column, runs first loop to count length of pattern (sequence of identical tokens) in that direction
    //then inverts the row and column directions, and runs a second loop to add to the length of the pattern
    //if the total length of the pattern is greater than or equal to that of the required win length, returns true.
    private static boolean hasWonInDirection(int[][] board, int row, int column, int rowDirection, int columnDirection, int winLen){
        int patternLen = 1;
        for (int i = 1; i < winLen; i++){
            int rowNumber = row + i * rowDirection;
            int columnNumber = column + i * columnDirection;
            if (!isInGrid(board, rowNumber, columnNumber) || 
                !(board[rowNumber][columnNumber] == board[row][column])){
                    break;
                }
            patternLen++;
        }
        rowDirection = -rowDirection;
        columnDirection = -columnDirection;
        for (int i = 1; i < winLen; i++){
            int rowNumber = row + i * rowDirection;
            int columnNumber = column + i * columnDirection;
            if (!isInGrid(board, rowNumber, columnNumber) || 
                !(board[rowNumber][columnNumber] == board[row][column])){
                    break;
                }
            patternLen++;
        }
        return patternLen >= winLen;
    }

    //checks if a given coordinate is within the bounds of the grid.
    private static boolean isInGrid(int[][] board, int row, int column){
        return row >= 0 && row < board.length && column >= 0 && column < board[0].length;
    }

    //returns first available row from bottom for the specified column, returns -1 if column is full.
    public static int getAvailableRow(int[][] board, int column){
        for (int i = board.length -1; i >= 0; i--){
            if (board[i][column] == 0) return i;
        }
        return -1;
    }
}
