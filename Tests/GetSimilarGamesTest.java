package Tests;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static src.controller.GameController.*;
import static src.controller.PlayerController.*;
import src.restServices.requestObjects.*;
import src.restServices.responseObjects.*;

public class GetSimilarGamesTest {

    public static int playerId;
    public static int game1;
    public static int game2;

   @BeforeClass
    public static void Set_Up_Two_Games(){
        playerId = createPlayer(new CreatePlayerRequest("name501", "verySecure")).getPlayerId();
        int player2Id = createPlayer(new CreatePlayerRequest("name502", "verySecure")).getPlayerId();
        game1 = createGame(new CreateGameRequest(playerId, player2Id)).getGameId();
        playMove(new PlayMoveRequest(game1, playerId, 4));
        playMove(new PlayMoveRequest(game1, player2Id, 3));
        playMove(new PlayMoveRequest(game1, playerId, 2));
        playMove(new PlayMoveRequest(game1, player2Id, 1));
        playMove(new PlayMoveRequest(game1, playerId, 3));
        playMove(new PlayMoveRequest(game1, player2Id, 1));
        playMove(new PlayMoveRequest(game1, playerId, 1));
        playMove(new PlayMoveRequest(game1, player2Id, 2));
        playMove(new PlayMoveRequest(game1, playerId, 1));

        //Board will look like so:
        //     [0][0][0][0][0][0][0]
        //     [0][0][0][0][0][0][0]
        //     [1][0][0][0][0][0][0]
        //     [1][2][0][0][0][0][0]
        //     [2][2][1][0][0][0][0]
        //     [2][1][2][1][0][0][0]

        game2 = createGame(new CreateGameRequest(player2Id, playerId)).getGameId();        
        playMove(new PlayMoveRequest(game2, player2Id, 2));
        playMove(new PlayMoveRequest(game2, playerId, 1));
        playMove(new PlayMoveRequest(game2, player2Id, 4));
        playMove(new PlayMoveRequest(game2, playerId, 1));
        playMove(new PlayMoveRequest(game2, player2Id, 1));
        playMove(new PlayMoveRequest(game2, playerId, 3));
        playMove(new PlayMoveRequest(game2, player2Id, 3));
        playMove(new PlayMoveRequest(game2, playerId, 2));
        playMove(new PlayMoveRequest(game2, player2Id, 1));
        playMove(new PlayMoveRequest(game2, playerId, 2));
        //Board will look the same
    }
    
    @Test
    public void Game_Does_Not_Exist(){
        //Scenario 5.1
        int playerId = createPlayer(new CreatePlayerRequest("name511", "verySecure")).getPlayerId();
        int player2Id = createPlayer(new CreatePlayerRequest("name512", "verySecure")).getPlayerId(); //needed for the purpose of obtaining a non existent game ID
        int nonExistentGameId = createGame(new CreateGameRequest(playerId, player2Id)).getGameId() + 1;
        GetSimilarGamesResponse response = getSimilarGames(new GetSimilarGamesRequest(nonExistentGameId, playerId, -1));

        assertEquals(false, response.wasSuccess());
        assertEquals("Game with ID of #" + nonExistentGameId + " does not exist.", response.getStatusMessage());
        assertEquals(nonExistentGameId, response.getGameId());
        assertEquals(playerId, response.getPlayerId());
        assertEquals(-1, response.getMoveNumber());
        assertNull(response.getBoard());
        assertNull(response.getGames());
    }

    @Test
    public void Player_Does_Not_Exist(){
        //Scenario 5.2
        int player1Id = createPlayer(new CreatePlayerRequest("name521", "verySecure")).getPlayerId();
        int player2Id = createPlayer(new CreatePlayerRequest("name522", "verySecure")).getPlayerId();
        int gameId = createGame(new CreateGameRequest(player1Id, player2Id)).getGameId();
        int nonExistentPlayerId = player2Id + 1;
        GetSimilarGamesResponse response = getSimilarGames(new GetSimilarGamesRequest(gameId, nonExistentPlayerId, 0));

        assertEquals(false, response.wasSuccess());
        assertEquals("Player with ID of #" + nonExistentPlayerId + " does not exist.", response.getStatusMessage());
        assertEquals(gameId, response.getGameId());
        assertEquals(nonExistentPlayerId, response.getPlayerId());
        assertEquals(0, response.getMoveNumber());
        assertNull(response.getBoard());
        assertNull(response.getGames());
    }

    @Test
    public void Player_Not_Registered_To_Game(){
        //Scenario 5.3
        int player1Id = createPlayer(new CreatePlayerRequest("name531", "verySecure")).getPlayerId();
        int player2Id = createPlayer(new CreatePlayerRequest("name532", "verySecure")).getPlayerId();
        int gameId = createGame(new CreateGameRequest(player1Id, player2Id)).getGameId();
        int notRegisteredPlayerId = createPlayer(new CreatePlayerRequest("name53", "verySecure")).getPlayerId();
        GetSimilarGamesResponse response = getSimilarGames(new GetSimilarGamesRequest(gameId, notRegisteredPlayerId, -1));

        assertEquals(false, response.wasSuccess());
        assertEquals("Player with ID of #" + notRegisteredPlayerId + " is not registered to game with ID of #" + gameId + ".", response.getStatusMessage());
        assertEquals(gameId, response.getGameId());
        assertEquals(notRegisteredPlayerId, response.getPlayerId());
        assertEquals(-1, response.getMoveNumber());
        assertNull(response.getBoard());
        assertNull(response.getGames());
    }

    @Test
    public void Move_Number_Not_Yet_Reached(){
        //Scenario 5.4
        int player1Id = createPlayer(new CreatePlayerRequest("name541", "verySecure")).getPlayerId();
        int player2Id = createPlayer(new CreatePlayerRequest("name542", "verySecure")).getPlayerId();
        int gameId = createGame(new CreateGameRequest(player1Id, player2Id)).getGameId();
        playMove(new PlayMoveRequest(gameId, player1Id, 3));
        GetSimilarGamesResponse response = getSimilarGames(new GetSimilarGamesRequest(gameId, player2Id, 2));

        assertEquals(false, response.wasSuccess());
        assertEquals("Game with ID of #" + gameId + " has not yet reached move number 2", response.getStatusMessage());
        assertEquals(gameId, response.getGameId());
        assertEquals(player2Id, response.getPlayerId());
        assertEquals(2, response.getMoveNumber());
        assertNull(response.getBoard());
        assertNull(response.getGames());
    }

    @Test
    public void Successful_Board_Retrieval_With_No_Similar_Games_Found(){
        //Scenario 5.5
        //Part 1 - At previous move number.
        int player1Id = createPlayer(new CreatePlayerRequest("name551", "verySecure")).getPlayerId();
        int player2Id = createPlayer(new CreatePlayerRequest("name552", "verySecure")).getPlayerId();
        int gameId = createGame(new CreateGameRequest(player1Id, player2Id)).getGameId();
        playMove(new PlayMoveRequest(gameId, player1Id, 1));
        playMove(new PlayMoveRequest(gameId, player2Id, 2));
        playMove(new PlayMoveRequest(gameId, player1Id, 3));
        playMove(new PlayMoveRequest(gameId, player2Id, 4));
        playMove(new PlayMoveRequest(gameId, player1Id, 5));
        GetSimilarGamesResponse response = getSimilarGames(new GetSimilarGamesRequest(gameId, player1Id, 3));

        int[][] board = {{0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {1, 2, 1, 0, 0, 0, 0}};
        
        Integer[] expectedGames = new Integer[0];
        Integer[] responseGames = response.getGames().toArray(new Integer[response.getGames().size()]);

        assertEquals(true, response.wasSuccess());
        assertEquals("", response.getStatusMessage());
        assertEquals(gameId, response.getGameId());
        assertEquals(player1Id, response.getPlayerId());
        assertEquals(3, response.getMoveNumber());
        assertArrayEquals(board, response.getBoard());
        assertArrayEquals(expectedGames, responseGames);

        //Part 2 - At current move number; using negative
        response = getSimilarGames(new GetSimilarGamesRequest(gameId, player2Id, -10000));

        int[][] board2 = {{0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {1, 2, 1, 2, 1, 0, 0}};
        
        expectedGames = new Integer[0];
        responseGames = response.getGames().toArray(new Integer[response.getGames().size()]);

        assertEquals(true, response.wasSuccess());
        assertEquals("", response.getStatusMessage());
        assertEquals(gameId, response.getGameId());
        assertEquals(player2Id, response.getPlayerId());
        assertEquals(5, response.getMoveNumber());
        assertArrayEquals(board2, response.getBoard());
        assertArrayEquals(expectedGames, responseGames);

        //Part 3 - At current move number; using second request constructor
        response = getSimilarGames(new GetSimilarGamesRequest(gameId, player2Id));

        int[][] board3 = {{0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {1, 2, 1, 2, 1, 0, 0}};
        
        expectedGames = new Integer[0];
        responseGames = response.getGames().toArray(new Integer[response.getGames().size()]);

        assertEquals(true, response.wasSuccess());
        assertEquals("", response.getStatusMessage());
        assertEquals(gameId, response.getGameId());
        assertEquals(player2Id, response.getPlayerId());
        assertEquals(5, response.getMoveNumber());
        assertArrayEquals(board3, response.getBoard());
        assertArrayEquals(expectedGames, responseGames);
    }

    @Test
    public void Successful_Similar_Game_Match(){
        //Scenario 5.5 (supposed to be 5.6)
        //Part 1 - at move 4
        int player2Id = createPlayer(new CreatePlayerRequest("name562", "verySecure")).getPlayerId();
        int gameId = createGame(new CreateGameRequest(playerId, player2Id)).getGameId();
        playMove(new PlayMoveRequest(gameId, playerId, 1));
        playMove(new PlayMoveRequest(gameId, player2Id, 2));
        playMove(new PlayMoveRequest(gameId, playerId, 3));
        playMove(new PlayMoveRequest(gameId, player2Id, 4));
        playMove(new PlayMoveRequest(gameId, playerId, 7));
        GetSimilarGamesResponse response = getSimilarGames(new GetSimilarGamesRequest(gameId, playerId, 4));

        int[][] board = {{0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {1, 2, 1, 2, 0, 0, 0}};
        
        Integer[] expectedGames = new Integer[1];
        expectedGames[0] = game1;
        Integer[] responseGames = response.getGames().toArray(new Integer[response.getGames().size()]);

        assertEquals(true, response.wasSuccess());
        assertEquals("", response.getStatusMessage());
        assertEquals(gameId, response.getGameId());
        assertEquals(playerId, response.getPlayerId());
        assertEquals(4, response.getMoveNumber());
        assertArrayEquals(board, response.getBoard());
        assertArrayEquals(expectedGames, responseGames);

        //Part 2 - at move 5 (no matching games)
        response = getSimilarGames(new GetSimilarGamesRequest(gameId, playerId, 5));
        int[][] board2 = {{0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {1, 2, 1, 2, 0, 0, 1}};
        
        expectedGames = new Integer[0];
        responseGames = response.getGames().toArray(new Integer[response.getGames().size()]);

        assertEquals(true, response.wasSuccess());
        assertEquals("", response.getStatusMessage());
        assertEquals(gameId, response.getGameId());
        assertEquals(playerId, response.getPlayerId());
        assertEquals(5, response.getMoveNumber());
        assertArrayEquals(board2, response.getBoard());
        assertArrayEquals(expectedGames, responseGames);
    }

    @Test
    public void Boards_Match_But_Not_At_The_Necessary_MoveNumber(){
        int player2Id = createPlayer(new CreatePlayerRequest("name572", "verySecure")).getPlayerId();
        int gameId = createGame(new CreateGameRequest(playerId, player2Id)).getGameId();
        playMove(new PlayMoveRequest(gameId, playerId, 4));
        playMove(new PlayMoveRequest(gameId, player2Id, 3));
        playMove(new PlayMoveRequest(gameId, playerId, 3));
        GetSimilarGamesResponse response = getSimilarGames(new GetSimilarGamesRequest(gameId, playerId));

        int[][] board = {{0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 1, 0, 0, 0, 0},
                         {0, 0, 2, 1, 0, 0, 0}};
        
        Integer[] expectedGames = new Integer[0];
        Integer[] responseGames = response.getGames().toArray(new Integer[response.getGames().size()]);

        assertEquals(true, response.wasSuccess());
        assertEquals("", response.getStatusMessage());
        assertEquals(gameId, response.getGameId());
        assertEquals(playerId, response.getPlayerId());
        assertEquals(3, response.getMoveNumber());
        assertArrayEquals(board, response.getBoard());
        assertArrayEquals(expectedGames, responseGames);
    }

    @Test
    public void Two_Successful_Similar_Games_Match(){
        int player2Id = createPlayer(new CreatePlayerRequest("name582", "verySecure")).getPlayerId();
        int gameId = createGame(new CreateGameRequest(player2Id, playerId)).getGameId();
        playMove(new PlayMoveRequest(gameId, player2Id, 2));
        playMove(new PlayMoveRequest(gameId, playerId, 1));
        playMove(new PlayMoveRequest(gameId, player2Id, 4));
        playMove(new PlayMoveRequest(gameId, playerId, 1));
        playMove(new PlayMoveRequest(gameId, player2Id, 1));
        playMove(new PlayMoveRequest(gameId, playerId, 3));
        playMove(new PlayMoveRequest(gameId, player2Id, 3));
        playMove(new PlayMoveRequest(gameId, playerId, 2));
        GetSimilarGamesResponse response = getSimilarGames(new GetSimilarGamesRequest(gameId, playerId));

        int[][] board = {{0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {1, 0, 0, 0, 0, 0, 0},
                         {2, 2, 1, 0, 0, 0, 0},
                         {2, 1, 2, 1, 0, 0, 0}};
        
        Integer[] expectedGames = new Integer[2];
        expectedGames[0] = game1;
        expectedGames[1] = game2;
        Integer[] responseGames = response.getGames().toArray(new Integer[response.getGames().size()]);

        assertEquals(true, response.wasSuccess());
        assertEquals("", response.getStatusMessage());
        assertEquals(gameId, response.getGameId());
        assertEquals(playerId, response.getPlayerId());
        assertEquals(8, response.getMoveNumber());
        assertArrayEquals(board, response.getBoard());
        assertArrayEquals(expectedGames, responseGames);
    }
}
