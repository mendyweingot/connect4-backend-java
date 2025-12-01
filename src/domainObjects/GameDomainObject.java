package src.domainObjects;

import java.util.ArrayList;

import src.dataObjects.GameDataObject;
import src.models.GameBoardModel;
import src.models.GameModel;
import src.models.PlayerModel;


public class GameDomainObject {
    private int id = -1;
    private int player1Id;
    private int player2Id;
    private int status;     //-1 if game is in progress, -2 if draw, otherwise playerId of winner.
    private int currentTurnPlayerId;
    private int moveNumber;

    private PlayerDomainObject player1;
    private PlayerDomainObject player2;
    private GameBoardDomainObject gameBoard;

    public GameDomainObject(int player1Id, int player2Id){
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        status = -1;
        currentTurnPlayerId = player1Id;
    }

    public GameDomainObject(GameDataObject game){
        this.id = game.getId();
        this.player1Id = game.getPlayer1Id();
        this.player2Id = game.getPlayer2Id();
        this.currentTurnPlayerId = game.getCurrentTurnPlayerId();
        this.status = game.getStatus();
        this.moveNumber = game.getMoveNumber();
    }

    public static ArrayList<GameDomainObject> MapList(ArrayList<GameDataObject> dataGames){
        ArrayList<GameDomainObject> domainGames = new ArrayList<>();
        for (GameDataObject game: dataGames){
            domainGames.add(new GameDomainObject(game));
        }
        return domainGames;
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

    public PlayerDomainObject getPlayer1(){
        if (player1 == null){
            player1 = PlayerModel.getPlayerById(player1Id);
        }
        return player1;
    }
    public PlayerDomainObject getPlayer2(){
        if (player2 == null){
            player2 = PlayerModel.getPlayerById(player2Id);
        }
        return player2;
    }
    public GameBoardDomainObject getGameBoard(){
        if (gameBoard == null){
            gameBoard = GameBoardModel.getGameBoardByGameId(id);
        }
        return gameBoard;
    }

    public GameDomainObject setCurrentTurnPlayerId(int currentTurnPlayerId){
        this.currentTurnPlayerId = currentTurnPlayerId;
        GameDomainObject game = GameModel.save(this);
        return game;
    }
    public GameDomainObject setStatus(int status){
        this.status = status;
        GameDomainObject game = GameModel.save(this);
        return game;
    }
    public GameDomainObject incrementMoveNumber(){
        moveNumber++;
        GameDomainObject game = GameModel.save(this);
        return game;
    }
}
