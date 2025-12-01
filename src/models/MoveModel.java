package src.models;

import java.util.ArrayList;

import src.dataAccess.MoveDataAccess;
import src.dataObjects.MoveDataObject;
import src.domainObjects.MoveDomainObject;

public class MoveModel {

    public static MoveDomainObject addPMove(MoveDomainObject move){
        MoveDataObject newMove = new MoveDataObject(move);
        return new MoveDomainObject(MoveDataAccess.addPMove(newMove));
    }
    
    public static ArrayList<MoveDomainObject> getMovesByGameId(int gameId){
        ArrayList<MoveDomainObject> moves = MoveDomainObject.MapList(MoveDataAccess.getMovesByGameId(gameId));
        moves.sort((m1, m2) -> m1.getMoveNumber() - m2.getMoveNumber());
        return moves;
    }
}
