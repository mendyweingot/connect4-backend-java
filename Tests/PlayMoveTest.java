package Tests;

import org.junit.Test;
import static org.junit.Assert.*;
import static src.controller.GameController.*;
import static src.controller.PlayerController.*;
import src.restServices.requestObjects.*;
import src.restServices.responseObjects.*;

public class PlayMoveTest {
    
    @Test
    public void Game_Does_Not_Exist(){
        //Scenario 4.1
        int playerId = createPlayer(new CreatePlayerRequest("name411", "verySecure")).getPlayerId();
        int player2Id = createPlayer(new CreatePlayerRequest("name412", "verySecure")).getPlayerId(); //needed for the purpose of obtaining a non existent game ID
        int nonExistentGameId = createGame(new CreateGameRequest(playerId, player2Id)).getGameId() + 1;
        PlayMoveResponse response = playMove(new PlayMoveRequest(nonExistentGameId, playerId, 3));

        assertEquals(false, response.wasSuccess());
        assertEquals("Game with ID of #" + nonExistentGameId + " does not exist.", response.getStatusMessage());
        assertEquals(nonExistentGameId, response.getGameId());
        assertEquals(-1, response.getPlayer1Id());
        assertEquals(-1, response.getPlayer2Id());
        assertEquals(-1, response.getCurrentTurnPlayerId());
        assertEquals(-1, response.getMoveNumber());
        assertEquals("", response.getStatus());
        assertEquals(-1, response.getWinner());
        assertNull(response.getBoard());
    }

    @Test
    public void Player_Does_Not_Exist(){
        //Scenario 4.2
        int player1Id = createPlayer(new CreatePlayerRequest("name421", "verySecure")).getPlayerId();
        int player2Id = createPlayer(new CreatePlayerRequest("name422", "verySecure")).getPlayerId();
        int gameId = createGame(new CreateGameRequest(player1Id, player2Id)).getGameId();
        int nonExistentPlayerId = player2Id + 1;
        PlayMoveResponse response = playMove(new PlayMoveRequest(gameId, nonExistentPlayerId, 3));

        assertEquals(false, response.wasSuccess());
        assertEquals("Player with ID of #" + nonExistentPlayerId + " does not exist.", response.getStatusMessage());
        assertEquals(gameId, response.getGameId());
        assertEquals(-1, response.getPlayer1Id());
        assertEquals(-1, response.getPlayer2Id());
        assertEquals(-1, response.getCurrentTurnPlayerId());
        assertEquals(-1, response.getMoveNumber());
        assertEquals("", response.getStatus());
        assertEquals(-1, response.getWinner());
        assertNull(response.getBoard());
    }

    @Test
    public void Player_Not_Registered_To_Game(){
        //Scenario 4.3
        int player1Id = createPlayer(new CreatePlayerRequest("name431", "verySecure")).getPlayerId();
        int player2Id = createPlayer(new CreatePlayerRequest("name432", "verySecure")).getPlayerId();
        int gameId = createGame(new CreateGameRequest(player1Id, player2Id)).getGameId();
        int notRegisteredPlayerId = createPlayer(new CreatePlayerRequest("name43", "verySecure")).getPlayerId();
        PlayMoveResponse response = playMove(new PlayMoveRequest(gameId, notRegisteredPlayerId, 3));

        assertEquals(false, response.wasSuccess());
        assertEquals("Player with ID of #" + notRegisteredPlayerId + " is not registered to game with ID of #" + gameId + ".", response.getStatusMessage());
        assertEquals(gameId, response.getGameId());
        assertEquals(-1, response.getPlayer1Id());
        assertEquals(-1, response.getPlayer2Id());
        assertEquals(-1, response.getCurrentTurnPlayerId());
        assertEquals(-1, response.getMoveNumber());
        assertEquals("", response.getStatus());
        assertEquals(-1, response.getWinner());
        assertNull(response.getBoard());
    }

    @Test
    public void Not_Players_Turn_To_Move(){
        //Scenario 4.4
        int player1Id = createPlayer(new CreatePlayerRequest("name441", "verySecure")).getPlayerId();
        int player2Id = createPlayer(new CreatePlayerRequest("name442", "verySecure")).getPlayerId();
        int gameId = createGame(new CreateGameRequest(player1Id, player2Id)).getGameId();
        PlayMoveResponse response = playMove(new PlayMoveRequest(gameId, player2Id, 3));

        assertEquals(false, response.wasSuccess());
        assertEquals("Sorry! It's not your turn to move!", response.getStatusMessage());
        assertEquals(gameId, response.getGameId());
        assertEquals(-1, response.getPlayer1Id());
        assertEquals(-1, response.getPlayer2Id());
        assertEquals(-1, response.getCurrentTurnPlayerId());
        assertEquals(-1, response.getMoveNumber());
        assertEquals("", response.getStatus());
        assertEquals(-1, response.getWinner());
        assertNull(response.getBoard());
    }

    //test scenario 4.5 is further down below

    @Test
    public void Column_Out_Of_Bounds(){
        //Part 1 - Column too low
        //Scenario 4.6
        int player1Id = createPlayer(new CreatePlayerRequest("name461", "verySecure")).getPlayerId();
        int player2Id = createPlayer(new CreatePlayerRequest("name462", "verySecure")).getPlayerId();
        int gameId = createGame(new CreateGameRequest(player1Id, player2Id)).getGameId();
        int column = 0;
        PlayMoveResponse response = playMove(new PlayMoveRequest(gameId, player1Id, column));

        assertEquals(false, response.wasSuccess());
        assertEquals("Column " + column + " is out of bounds for this game.", response.getStatusMessage());
        assertEquals(gameId, response.getGameId());
        assertEquals(-1, response.getPlayer1Id());
        assertEquals(-1, response.getPlayer2Id());
        assertEquals(-1, response.getCurrentTurnPlayerId());
        assertEquals(-1, response.getMoveNumber());
        assertEquals("", response.getStatus());
        assertEquals(-1, response.getWinner());
        assertNull(response.getBoard());

        //Part 2 - Column too high
        //Scenario 4.7
        column = 8;
        response = playMove(new PlayMoveRequest(gameId, player1Id, column));

        assertEquals(false, response.wasSuccess());
        assertEquals("Column " + column + " is out of bounds for this game.", response.getStatusMessage());
    }

    //test scenario 4.8  is further down below

    @Test
    public void Successful_Move(){
        //Scenario 4.8 (supposed to be 4.9; in the unit testing plan, 4.8 was accidentally written twice)
        //Part 1 - game board update
        int player1Id = createPlayer(new CreatePlayerRequest("name491", "verySecure")).getPlayerId();
        int player2Id = createPlayer(new CreatePlayerRequest("name492", "verySecure")).getPlayerId();
        int gameId = createGame(new CreateGameRequest(player1Id, player2Id)).getGameId();
        playMove(new PlayMoveRequest(gameId, player1Id, 1));
        PlayMoveResponse response = playMove(new PlayMoveRequest(gameId, player2Id, 1));
        int[][] board = new int[6][7];
        board[5][0] = 1;
        board[4][0] = 2;

        assertEquals(true, response.wasSuccess());
        assertEquals("", response.getStatusMessage());
        assertEquals(gameId, response.getGameId());
        assertEquals(player1Id, response.getPlayer1Id());
        assertEquals(player2Id, response.getPlayer2Id());
        assertEquals(player1Id, response.getCurrentTurnPlayerId());
        assertEquals(2, response.getMoveNumber());
        assertEquals("Playing", response.getStatus());
        assertEquals(-1, response.getWinner());
        assertArrayEquals(board, response.getBoard());

        //Scenario 4.9 (supposed to be 4.10)
        //Part 2 - currentTurnPlayerId should be swapped
        int currentTurnPlayerId = response.getCurrentTurnPlayerId();
        int expectedNewCurrentTurnPlayerId = (currentTurnPlayerId == player1Id)? player2Id: player1Id;
        response = playMove(new PlayMoveRequest(gameId, currentTurnPlayerId, 7));

        assertEquals(true, response.wasSuccess());
        assertEquals(expectedNewCurrentTurnPlayerId, response.getCurrentTurnPlayerId());

        //Scenario 4.10 (supposed to be 4.11)
        //Part 3 - moveNumber should be incemented
        int moveNumber = response.getMoveNumber();
        int expectedNewMoveNumber = moveNumber + 1;
        response = playMove(new PlayMoveRequest(gameId, response.getCurrentTurnPlayerId(), 7));

        assertEquals(true, response.wasSuccess());
        assertEquals(expectedNewMoveNumber, response.getMoveNumber());
    }

    @Test
    public void Column_Already_Full(){
        //Scenario 4.8
        int player1Id = createPlayer(new CreatePlayerRequest("name481", "verySecure")).getPlayerId();
        int player2Id = createPlayer(new CreatePlayerRequest("name482", "verySecure")).getPlayerId();
        int gameId = createGame(new CreateGameRequest(player1Id, player2Id)).getGameId();
        playMove(new PlayMoveRequest(gameId, player1Id, 1));
        playMove(new PlayMoveRequest(gameId, player2Id, 1));
        playMove(new PlayMoveRequest(gameId, player1Id, 1));
        playMove(new PlayMoveRequest(gameId, player2Id, 1));
        playMove(new PlayMoveRequest(gameId, player1Id, 1));
        playMove(new PlayMoveRequest(gameId, player2Id, 1));
        PlayMoveResponse response = playMove(new PlayMoveRequest(gameId, player1Id, 1));

        assertEquals(false, response.wasSuccess());
        assertEquals("Oops! Column 1 is already full!", response.getStatusMessage());
        assertEquals(gameId, response.getGameId());
        assertEquals(-1, response.getPlayer1Id());
        assertEquals(-1, response.getPlayer2Id());
        assertEquals(-1, response.getCurrentTurnPlayerId());
        assertEquals(-1, response.getMoveNumber());
        assertEquals("", response.getStatus());
        assertEquals(-1, response.getWinner());
        assertNull(response.getBoard());
    }

    @Test
    public void Successful_Last_Move_On_Board(){
        //Part 1 - play last move on board
        //Scenario 4.11 (supposed to be 4.12)
        int player1Id = createPlayer(new CreatePlayerRequest("name4121", "verySecure")).getPlayerId();
        int player2Id = createPlayer(new CreatePlayerRequest("name4122", "verySecure")).getPlayerId();

        int player1Draws = getPlayerStatistics(new GetPlayerStatisticsRequest(player1Id)).getDraws();
        int player2Draws = getPlayerStatistics(new GetPlayerStatisticsRequest(player2Id)).getDraws();

        int gameId = createGame(new CreateGameRequest(player1Id, player2Id)).getGameId();
        playMove(new PlayMoveRequest(gameId, player1Id, 1));
        playMove(new PlayMoveRequest(gameId, player2Id, 2));
        playMove(new PlayMoveRequest(gameId, player1Id, 3));
        playMove(new PlayMoveRequest(gameId, player2Id, 4));
        playMove(new PlayMoveRequest(gameId, player1Id, 5));
        playMove(new PlayMoveRequest(gameId, player2Id, 6));
        playMove(new PlayMoveRequest(gameId, player1Id, 7));
        playMove(new PlayMoveRequest(gameId, player2Id, 7));
        playMove(new PlayMoveRequest(gameId, player1Id, 1));
        playMove(new PlayMoveRequest(gameId, player2Id, 2));
        playMove(new PlayMoveRequest(gameId, player1Id, 3));
        playMove(new PlayMoveRequest(gameId, player2Id, 4));
        playMove(new PlayMoveRequest(gameId, player1Id, 5));
        playMove(new PlayMoveRequest(gameId, player2Id, 6));
        playMove(new PlayMoveRequest(gameId, player1Id, 1));
        playMove(new PlayMoveRequest(gameId, player2Id, 2));
        playMove(new PlayMoveRequest(gameId, player1Id, 3));
        playMove(new PlayMoveRequest(gameId, player2Id, 4));
        playMove(new PlayMoveRequest(gameId, player1Id, 5));
        playMove(new PlayMoveRequest(gameId, player2Id, 6));
        playMove(new PlayMoveRequest(gameId, player1Id, 7));
        playMove(new PlayMoveRequest(gameId, player2Id, 7));
        playMove(new PlayMoveRequest(gameId, player1Id, 6));
        playMove(new PlayMoveRequest(gameId, player2Id, 5));
        playMove(new PlayMoveRequest(gameId, player1Id, 4));
        playMove(new PlayMoveRequest(gameId, player2Id, 3));
        playMove(new PlayMoveRequest(gameId, player1Id, 2));
        playMove(new PlayMoveRequest(gameId, player2Id, 1));
        playMove(new PlayMoveRequest(gameId, player1Id, 7));
        playMove(new PlayMoveRequest(gameId, player2Id, 1));
        playMove(new PlayMoveRequest(gameId, player1Id, 2));
        playMove(new PlayMoveRequest(gameId, player2Id, 3));
        playMove(new PlayMoveRequest(gameId, player1Id, 4));
        playMove(new PlayMoveRequest(gameId, player2Id, 5));
        playMove(new PlayMoveRequest(gameId, player1Id, 6));
        playMove(new PlayMoveRequest(gameId, player2Id, 1));
        playMove(new PlayMoveRequest(gameId, player1Id, 2));
        playMove(new PlayMoveRequest(gameId, player2Id, 3));
        playMove(new PlayMoveRequest(gameId, player1Id, 4));
        playMove(new PlayMoveRequest(gameId, player2Id, 5));
        playMove(new PlayMoveRequest(gameId, player1Id, 7));
        PlayMoveResponse response = playMove(new PlayMoveRequest(gameId, player2Id, 6));

        int[][] board = {{2, 1, 2, 1, 2, 2, 1},
                         {2, 1, 2, 1, 2, 1, 1},
                         {2, 1, 2, 1, 2, 1, 2},
                         {1, 2, 1, 2, 1, 2, 1},
                         {1, 2, 1, 2, 1, 2, 2},
                         {1, 2, 1, 2, 1, 2, 1}};

        assertEquals(true, response.wasSuccess());
        assertEquals("", response.getStatusMessage());
        assertEquals(gameId, response.getGameId());
        assertEquals(player1Id, response.getPlayer1Id());
        assertEquals(player2Id, response.getPlayer2Id());
        assertEquals(player1Id, response.getCurrentTurnPlayerId());
        assertEquals(42, response.getMoveNumber());
        assertEquals("Complete", response.getStatus());
        assertEquals(-1, response.getWinner());
        assertArrayEquals(board, response.getBoard());

        //Part 2 - play move attempt after game was drawn
        response = playMove(new PlayMoveRequest(gameId, player1Id, 3));
        assertEquals(false, response.wasSuccess());
        assertEquals("Invalid operation. Game with ID of #" + gameId + " has already been completed.", response.getStatusMessage());

        //Part 3 - check player statistics after game was drawn
        assertEquals(player1Draws+1, getPlayerStatistics(new GetPlayerStatisticsRequest(player1Id)).getDraws());
        assertEquals(player2Draws+1, getPlayerStatistics(new GetPlayerStatisticsRequest(player2Id)).getDraws());
    }

    @Test
    public void Game_Already_Completed_By_A_Win(){
        //Scenario 4.5
        //Part 1 - winning the game
        int player1Id = createPlayer(new CreatePlayerRequest("name451", "verySecure")).getPlayerId();
        int player2Id = createPlayer(new CreatePlayerRequest("name452", "verySecure")).getPlayerId();
        int gameId = createGame(new CreateGameRequest(player1Id, player2Id)).getGameId();
        playMove(new PlayMoveRequest(gameId, player1Id, 1));
        playMove(new PlayMoveRequest(gameId, player2Id, 7));
        playMove(new PlayMoveRequest(gameId, player1Id, 2));
        playMove(new PlayMoveRequest(gameId, player2Id, 6));
        playMove(new PlayMoveRequest(gameId, player1Id, 3));
        playMove(new PlayMoveRequest(gameId, player2Id, 5));
        PlayMoveResponse response = playMove(new PlayMoveRequest(gameId, player1Id, 4));

        int[][] board = {{0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {1, 1, 1, 1, 2, 2, 2}};

        assertEquals(true, response.wasSuccess());
        assertEquals("", response.getStatusMessage());
        assertEquals(gameId, response.getGameId());
        assertEquals(player1Id, response.getPlayer1Id());
        assertEquals(player2Id, response.getPlayer2Id());
        assertEquals(player2Id, response.getCurrentTurnPlayerId());
        assertEquals(7, response.getMoveNumber());
        assertEquals("Complete", response.getStatus());
        assertEquals(player1Id, response.getWinner());
        assertArrayEquals(board, response.getBoard());

        //Scenario 4.5
        //Part 2 - playing a move after game was won
        response = playMove(new PlayMoveRequest(gameId, player2Id, 3));
        assertEquals(false, response.wasSuccess());
        assertEquals("Invalid operation. Game with ID of #" + gameId + " has already been completed.", response.getStatusMessage());
    }

    @Test
    public void Winning_Move_Test1(){
        //Scenario 4.12 (supposed to be 4.13)
        //Part 1 - play winning move (left diagonal win)
        int player1Id = createPlayer(new CreatePlayerRequest("name4131", "verySecure")).getPlayerId();
        int player2Id = createPlayer(new CreatePlayerRequest("name4132", "verySecure")).getPlayerId();

        int player1Wins = getPlayerStatistics(new GetPlayerStatisticsRequest(player1Id)).getWins();
        int player2Losses = getPlayerStatistics(new GetPlayerStatisticsRequest(player1Id)).getLosses();

        int gameId = createGame(new CreateGameRequest(player1Id, player2Id)).getGameId();
        playMove(new PlayMoveRequest(gameId, player1Id, 4));
        playMove(new PlayMoveRequest(gameId, player2Id, 3));
        playMove(new PlayMoveRequest(gameId, player1Id, 2));
        playMove(new PlayMoveRequest(gameId, player2Id, 1));
        playMove(new PlayMoveRequest(gameId, player1Id, 3));
        playMove(new PlayMoveRequest(gameId, player2Id, 1));
        playMove(new PlayMoveRequest(gameId, player1Id, 1));
        playMove(new PlayMoveRequest(gameId, player2Id, 2));
        playMove(new PlayMoveRequest(gameId, player1Id, 1));
        playMove(new PlayMoveRequest(gameId, player2Id, 7));
        PlayMoveResponse response = playMove(new PlayMoveRequest(gameId, player1Id, 2));

        int[][] board = {{0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {1, 0, 0, 0, 0, 0, 0},
                         {1, 1, 0, 0, 0, 0, 0},
                         {2, 2, 1, 0, 0, 0, 0},
                         {2, 1, 2, 1, 0, 0, 2}};

        assertEquals(true, response.wasSuccess());
        assertEquals("", response.getStatusMessage());
        assertEquals(gameId, response.getGameId());
        assertEquals(player1Id, response.getPlayer1Id());
        assertEquals(player2Id, response.getPlayer2Id());
        assertEquals(player2Id, response.getCurrentTurnPlayerId());
        assertEquals(11, response.getMoveNumber());
        assertEquals("Complete", response.getStatus());
        assertEquals(player1Id, response.getWinner());
        assertArrayEquals(board, response.getBoard());

        //Part 2 - check player statistics after game won
        assertEquals(player1Wins+1, getPlayerStatistics(new GetPlayerStatisticsRequest(player1Id)).getWins());
        assertEquals(player2Losses+1, getPlayerStatistics(new GetPlayerStatisticsRequest(player2Id)).getLosses());
    }

    @Test
    public void Winning_Move_Test2(){
        //Additional test similar to Scenario 4.12 (supposed to be 4.13)
        //Part 1 - play winning move (right diagonal win)
        int player1Id = createPlayer(new CreatePlayerRequest("name41321", "verySecure")).getPlayerId();
        int player2Id = createPlayer(new CreatePlayerRequest("name41322", "verySecure")).getPlayerId();
        int gameId = createGame(new CreateGameRequest(player1Id, player2Id)).getGameId();
        playMove(new PlayMoveRequest(gameId, player1Id, 4));
        playMove(new PlayMoveRequest(gameId, player2Id, 5));
        playMove(new PlayMoveRequest(gameId, player1Id, 6));
        playMove(new PlayMoveRequest(gameId, player2Id, 7));
        playMove(new PlayMoveRequest(gameId, player1Id, 5));
        playMove(new PlayMoveRequest(gameId, player2Id, 7));
        playMove(new PlayMoveRequest(gameId, player1Id, 6));
        playMove(new PlayMoveRequest(gameId, player2Id, 7));
        playMove(new PlayMoveRequest(gameId, player1Id, 6));
        playMove(new PlayMoveRequest(gameId, player2Id, 4));
        PlayMoveResponse response = playMove(new PlayMoveRequest(gameId, player1Id, 7));

        int[][] board = {{0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 1},
                         {0, 0, 0, 0, 0, 1, 2},
                         {0, 0, 0, 2, 1, 1, 2},
                         {0, 0, 0, 1, 2, 1, 2}};

        assertEquals(true, response.wasSuccess());
        assertEquals("", response.getStatusMessage());
        assertEquals(gameId, response.getGameId());
        assertEquals(player1Id, response.getPlayer1Id());
        assertEquals(player2Id, response.getPlayer2Id());
        assertEquals(player2Id, response.getCurrentTurnPlayerId());
        assertEquals(11, response.getMoveNumber());
        assertEquals("Complete", response.getStatus());
        assertEquals(player1Id, response.getWinner());
        assertArrayEquals(board, response.getBoard());
    }
    
    @Test
    public void Winning_Move_Test3(){
        //Additional test similar to Scenario 4.12 (supposed to be 4.13)
        //Part 1 - play winning move (vertical win)
        int player1Id = createPlayer(new CreatePlayerRequest("name41331", "verySecure")).getPlayerId();
        int player2Id = createPlayer(new CreatePlayerRequest("name41332", "verySecure")).getPlayerId();
        int gameId = createGame(new CreateGameRequest(player1Id, player2Id)).getGameId();
        playMove(new PlayMoveRequest(gameId, player1Id, 4));
        playMove(new PlayMoveRequest(gameId, player2Id, 5));
        playMove(new PlayMoveRequest(gameId, player1Id, 6));
        playMove(new PlayMoveRequest(gameId, player2Id, 5));
        playMove(new PlayMoveRequest(gameId, player1Id, 6));
        playMove(new PlayMoveRequest(gameId, player2Id, 5));
        playMove(new PlayMoveRequest(gameId, player1Id, 6));
        PlayMoveResponse response = playMove(new PlayMoveRequest(gameId, player2Id, 5));

        int[][] board = {{0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 2, 0, 0},
                         {0, 0, 0, 0, 2, 1, 0},
                         {0, 0, 0, 0, 2, 1, 0},
                         {0, 0, 0, 1, 2, 1, 0}};

        assertEquals(true, response.wasSuccess());
        assertEquals("", response.getStatusMessage());
        assertEquals(gameId, response.getGameId());
        assertEquals(player1Id, response.getPlayer1Id());
        assertEquals(player2Id, response.getPlayer2Id());
        assertEquals(player1Id, response.getCurrentTurnPlayerId());
        assertEquals(8, response.getMoveNumber());
        assertEquals("Complete", response.getStatus());
        assertEquals(player2Id, response.getWinner());
        assertArrayEquals(board, response.getBoard());
    }
}
