package src.dataAccess;

import java.util.*;
import src.dataObjects.*;

public class GameBoardDataAccess{

    private static ArrayList<GameBoardDataObject> boards = new ArrayList<>();

    public static GameBoardDataObject addBaord(GameBoardDataObject board){
        GameBoardDataObject newBoard = new GameBoardDataObject(board);
        boards.add(newBoard);
        return new GameBoardDataObject(newBoard);
    }

    public static GameBoardDataObject getGameBoardByGameId(int id){
        for (GameBoardDataObject board: boards){
            if (board.getGameId() == id){
                return new GameBoardDataObject(board);
            }
        }
        return null;
    }
    
    public static GameBoardDataObject addMoveToGameBoard(int gameId, int row, int column, int val){
        for (GameBoardDataObject board: boards){
            if (board.getGameId() == gameId){
                board.getBoard()[row][column] = val;
                return new GameBoardDataObject(board);
            }
        }
        return null;
    }
}