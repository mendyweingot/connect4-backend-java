package src.controller;

import java.util.ArrayList;

import src.domainObjects.*;
import src.models.*;
import src.restServices.requestObjects.*;
import src.restServices.responseObjects.*;

public class GameController {

    public static CreateGameResponse createGame(CreateGameRequest request){
        int player1Id = request.getPlayer1Id();
        int player2Id = request.getPlayer2Id();

        PlayerDomainObject player1 = PlayerModel.getPlayerById(player1Id);
        PlayerDomainObject player2 = PlayerModel.getPlayerById(player2Id);
        if (player1 == null && player2 == null) return new CreateGameResponse("Player with ID of #" + player1Id + " does not exist, and player with ID of #" + player2Id + " does not exist.", player1Id, player2Id);
        if (player1 == null) return new CreateGameResponse("Player with ID of #" + player1Id + " does not exist.", player1Id, player2Id);
        if (player2 == null) return new CreateGameResponse("Player with ID of #" + player2Id + " does not exist.", player1Id, player2Id);

        GameDomainObject game = new GameDomainObject(player1Id, player2Id);
        GameDomainObject createdGame = GameModel.addGame(game);

        GameBoardDomainObject board = new GameBoardDomainObject(createdGame.getId());
        GameBoardDomainObject createdGameBoard = GameBoardModel.addBoard(board);

        return new CreateGameResponse(createdGame.getId(), createdGame.getPlayer1Id(), createdGame.getPlayer2Id(), createdGame.getCurrentTurnPlayerId(), createdGameBoard.getBoardSafely());
    }    

    public static PlayMoveResponse playMove(PlayMoveRequest request){
        int gameId = request.getGameId();
        int playerId = request.getPlayerId();
        int columnNumber = request.getColumnNumber() - 1;

        GameDomainObject game = GameModel.getGameById(gameId);
        if (game == null) return new PlayMoveResponse("Game with ID of #" + gameId + " does not exist.", gameId);

        PlayerDomainObject player = PlayerModel.getPlayerById(playerId);
        if (player == null) return new PlayMoveResponse("Player with ID of #" + playerId + " does not exist.", gameId);

        //value will be 1 for player1, 2 for player2, -1 if player is not registered to game.
        int value = GameModel.getPlayerValueInGame(game, player);
        if (value == -1) return new PlayMoveResponse("Player with ID of #" + playerId + " is not registered to game with ID of #" + gameId + ".", gameId);
        
        if (game.getStatus() != -1) return new PlayMoveResponse("Invalid operation. Game with ID of #" + gameId + " has already been completed.", gameId);
        if (game.getCurrentTurnPlayerId() != playerId) return new PlayMoveResponse("Sorry! It's not your turn to move!", gameId);
        if (columnNumber < 0 || columnNumber >= 7) return new PlayMoveResponse("Column " + (columnNumber+1) + " is out of bounds for this game.",  gameId);

        GameBoardDomainObject gameBoard = game.getGameBoard();
        int moveStatus = gameBoard.playMove(columnNumber, value);
        if (moveStatus == -3) return new PlayMoveResponse("Oops! Column " + (columnNumber+1) + " is already full!", gameId);

        MoveDomainObject newMove = new MoveDomainObject(gameId, game.getMoveNumber(), columnNumber);
        MoveModel.addPMove(newMove);
        game.incrementMoveNumber();
        GameModel.switchCurrentTurnPlayerId(game);
        game.setStatus((moveStatus < 0)? moveStatus: playerId);

        if (moveStatus >= 0){
            player.addWin();
            PlayerModel.getPlayerById(game.getCurrentTurnPlayerId()).addLoss();
        }
        else if (moveStatus == -2){
            game.getPlayer1().addDraw();
            game.getPlayer2().addDraw();
        }

        String status = (moveStatus == -1)? PlayMoveResponse.PLAYING: PlayMoveResponse.COMPLETE;
        int winner = (moveStatus < 0)? -1: playerId;
        int[][] board = game.getGameBoard().getBoardSafely();

        return new PlayMoveResponse(game.getId(), game.getMoveNumber(), game.getPlayer1Id(), game.getPlayer2Id(), game.getCurrentTurnPlayerId(), status, board, winner);
    }

    public static GetGameDetailsResponse getGameDetails(GetGameDetailsRequest request){
        int gameId = request.getGameId();

        GameDomainObject game = GameModel.getGameById(gameId);
        if (game == null) return new GetGameDetailsResponse("Game with ID of #" + gameId + " does not exist.", gameId);

        String status = (game.getStatus() == -1)? GetGameDetailsResponse.PLAYING: GetGameDetailsResponse.COMPLETE;
        int winner = (game.getStatus() >= 0)? game.getStatus(): -1;

        return new GetGameDetailsResponse(game.getId(), game.getMoveNumber(), game.getPlayer1Id(), game.getPlayer2Id(), game.getCurrentTurnPlayerId(), status, game.getGameBoard().getBoardSafely(), winner);
    }

    public static GetSimilarGamesResponse getSimilarGames(GetSimilarGamesRequest request){
        int gameId = request.getGameId();
        int moveNumber = request.getMoveNumber();
        int playerId = request.getPlayerId();

        GameDomainObject game = GameModel.getGameById(gameId);
        if (game == null) return new GetSimilarGamesResponse("Game with ID of #" + gameId + " does not exist.", gameId, moveNumber, playerId);
        
        PlayerDomainObject player = PlayerModel.getPlayerById(playerId);
        if (player == null) return new GetSimilarGamesResponse("Player with ID of #" + playerId + " does not exist.", gameId, moveNumber, playerId);
        if (game.getPlayer1Id() != playerId && game.getPlayer2Id() != playerId) return new GetSimilarGamesResponse("Player with ID of #" + playerId + " is not registered to game with ID of #" + gameId + ".", gameId, moveNumber, playerId);

        moveNumber = (moveNumber >= 0)? moveNumber: game.getMoveNumber();
        if (moveNumber > game.getMoveNumber()) return new GetSimilarGamesResponse("Game with ID of #" + gameId + " has not yet reached move number " + moveNumber, gameId, moveNumber, playerId);

        ArrayList<MoveDomainObject> moveObjects = MoveModel.getMovesByGameId(gameId);

        //Obtain the board to be returned. The board will have to be reconstructed to reflect a previous state if necessary.
        int[][] board;        
        if (moveNumber == game.getMoveNumber()){
            board = game.getGameBoard().getBoardSafely();
        }
        else {
            board = new int[6][7];
            for (int i = 0; i < moveNumber; i++){
                MoveDomainObject move = moveObjects.get(i);
                int column = move.getMoveColumn();
                int row = GameBoardModel.getAvailableRow(board, column);
                int value = (move.getMoveNumber() % 2 == 0)? 1: 2;
                board[row][column] = value;
            }
        }
        //The moves of the game are sorted into an array representing the board's columns. Each "column" will contain an arrayList
        //where the value 0 will be inserted for even moveNumbers and 1 for odd moveNumbers in the order in which the moves were played in that column
        //thus distinguishing the moves played by each player.
        ArrayList<Integer>[] columns =  new ArrayList[7];
        for (int i = 0; i < moveNumber; i++){
            MoveDomainObject move = moveObjects.get(i);
            int col = move.getMoveColumn();
            if (columns[col] == null) columns[col] = new ArrayList<>();
            columns[col].add(move.getMoveNumber() % 2);
        }

        //all the games played by the specified player will be retrieved to be compared with the specified game
        //if the game has not yet reached the specified moveNumber, it's obviously not a match so continue.
        //Games will be compared to have tokens occupying the same positions as each other. The corresponding tokens must also be part of the same pattern on each board,
        //meaning, either all even moveNumbers will be in the same position as all the odd moveNumbers on the other board, or as all the even moveNumbers.
        //Also it doesn't matter how the game reached the current position on the board. As long as the corresponding tokens correlate with each other right now, it's a match.
        ArrayList<Integer> similarGames = new ArrayList<>();        
        ArrayList<GameDomainObject> gamesByPlayerId = GameModel.getGamesByPlayerId(playerId);
        for (GameDomainObject gameByPlayerId: gamesByPlayerId){
            if (gameByPlayerId.getId() == gameId) continue;
            if (gameByPlayerId.getMoveNumber() < moveNumber) continue;   
            //keeps track of what row will next be compared for the specified column.
            int[] columnCount = new int[7];         
            ArrayList<MoveDomainObject> gameMovesByPlayerId = MoveModel.getMovesByGameId(gameByPlayerId.getId());
            //This will be true if even values must be matched to even values, and false of even values must be matched to odd values
            boolean isEqualValue = true;
            boolean isSimilarGame = true;
            for (int i = 0; i < moveNumber && isSimilarGame; i++){
                MoveDomainObject move = gameMovesByPlayerId.get(i);
                int columnNumber = move.getMoveColumn();
                int moveValue = move.getMoveNumber() % 2;
                if (columns[columnNumber] == null || columnCount[columnNumber] >= columns[columnNumber].size()){
                    isSimilarGame = false;
                    break;
                } 
                //When the first tokens are compared we learn if they are both even or are both odd, or if one is even and one is odd. This will have to remain true for all the tokens for the game to be a match.
                if (i == 0) isEqualValue = (moveValue == columns[columnNumber].get(columnCount[columnNumber]))? true: false;
                //game is not a match if values are equal to each other but are not supposed to be, or if values are not equal but are supposed to be.
                if (moveValue == columns[columnNumber].get(columnCount[columnNumber]) ^ isEqualValue) isSimilarGame = false;
                columnCount[columnNumber]++;
            }
            if (isSimilarGame) similarGames.add(gameByPlayerId.getId());            
        }
        return new GetSimilarGamesResponse(gameId, moveNumber, playerId, board, similarGames);
    }
}
