package src.dataObjects;

import src.domainObjects.GameDomainObject;

public class GameDataObject{
    private int id;
    private int player1Id;
    private int player2Id;
    private int status;     //-1 if game is in progress, -2 if draw, otherwise playerId of winner.
    private int moveNumber;
    private int currentTurnPlayerId;

    public GameDataObject(int id, int player1Id, int player2Id){
        this.id = id;
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        this.status = -1;
        this.currentTurnPlayerId = player1Id;
    }

    public GameDataObject(GameDataObject game){
        this.id = game.getId();
        this.player1Id = game.getPlayer1Id();
        this.player2Id = game.getPlayer2Id();
        this.currentTurnPlayerId = game.getCurrentTurnPlayerId();
        this.status = game.getStatus();
        this.moveNumber = game.getMoveNumber();
    }

    public GameDataObject(GameDomainObject game){
        this.id = game.getId();
        this.player1Id = game.getPlayer1Id();
        this.player2Id = game.getPlayer2Id();
        this.currentTurnPlayerId = game.getCurrentTurnPlayerId();
        this.status = game.getStatus();
        this.moveNumber = game.getMoveNumber();
    }

    public int getId(){
        return id;
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
    public int getStatus(){
        return status;
    }
    public int getMoveNumber(){
        return moveNumber;
    }

    public void setCurrentTurnPlayerId(int currentTurnPlayerId){
        this.currentTurnPlayerId = currentTurnPlayerId;
    }
    public void setStatus(int status){
        this.status = status;
    }
    public void setMoveNumber(int moveNumber){
        this.moveNumber = moveNumber;
    }
}