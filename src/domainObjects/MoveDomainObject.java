package src.domainObjects;

import src.dataObjects.MoveDataObject;
import src.models.GameModel;
import java.util.ArrayList;

public class MoveDomainObject {
    private int gameDomainObjectId;
    private int moveNumber;
    private int moveColumn;

    private GameDomainObject game;

    public MoveDomainObject(int gameId, int moveNumber, int moveColumn){
        this.gameDomainObjectId = gameId;
        this.moveNumber = moveNumber;
        this.moveColumn = moveColumn;
    }
    public MoveDomainObject(MoveDataObject move){
        this.gameDomainObjectId = move.getGameId();
        this.moveNumber = move.getMoveNumber();
        this.moveColumn = move.getMoveColumn();
    }

    public static ArrayList<MoveDomainObject> MapList(ArrayList<MoveDataObject> dataMoves){
        ArrayList<MoveDomainObject> domainMoves = new ArrayList<>();
        for (MoveDataObject move: dataMoves){
            domainMoves.add(new MoveDomainObject(move));
        }
        return domainMoves;
    }

    public int getGameId(){
        return gameDomainObjectId;
    }
    public int getMoveNumber(){
        return moveNumber;
    }
    public int getMoveColumn(){
        return moveColumn;
    }

    public GameDomainObject getGame(){
        if (game == null){
            game = GameModel.getGameById(gameDomainObjectId);
        }
        return game;
    }
}
