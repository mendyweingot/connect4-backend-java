package src.dataObjects;

import src.domainObjects.MoveDomainObject;

public class MoveDataObject{
    private int gameDataObjectId;
    private int moveNumber;
    private int moveColumn;

    public MoveDataObject(int gameId, int moveNumber, int moveColumn){
        this.gameDataObjectId = gameId;
        this.moveNumber = moveNumber;
        this.moveColumn = moveColumn;
    }
    public MoveDataObject(MoveDataObject move){
        this.gameDataObjectId = move.getGameId();
        this.moveNumber = move.getMoveNumber();
        this.moveColumn = move.getMoveColumn();
    }
    public MoveDataObject(MoveDomainObject move){
        this.gameDataObjectId = move.getGameId();
        this.moveNumber = move.getMoveNumber();
        this.moveColumn = move.getMoveColumn();
    }

    public int getGameId(){
        return gameDataObjectId;
    }
    public int getMoveNumber(){
        return moveNumber;
    }
    public int getMoveColumn(){
        return moveColumn;
    }
}