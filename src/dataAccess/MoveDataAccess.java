package src.dataAccess;

import java.util.ArrayList;
import src.dataObjects.*;

public class MoveDataAccess{

    private static ArrayList<MoveDataObject> moves = new ArrayList<>();

    public static MoveDataObject addPMove(MoveDataObject move){
        MoveDataObject newMove = new MoveDataObject(move);
        moves.add(newMove);
        return move;
    }    

    public static ArrayList<MoveDataObject> getMovesByGameId(int gameId){
        ArrayList<MoveDataObject> gameMoves = new ArrayList<>();
        for (MoveDataObject move: moves){
            if (move.getGameId() == gameId){
                gameMoves.add(new MoveDataObject(move));
            }
        }
        return gameMoves;
    }
}