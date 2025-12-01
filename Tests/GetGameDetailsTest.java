package Tests;

import org.junit.Test;
import static org.junit.Assert.*;
import static src.controller.GameController.*;
import static src.controller.PlayerController.*;
import src.restServices.requestObjects.*;
import src.restServices.responseObjects.*;

public class GetGameDetailsTest {

    @Test
    public void Game_Does_Not_Exist(){
        //Scenario 3.1

        //this is needed to obtain the latest gameId so that any later ID does not exist
        int player1Id = createPlayer(new CreatePlayerRequest("goodName311", "verySecure")).getPlayerId();
        int player2Id = createPlayer(new CreatePlayerRequest("goodName312", "verySecure")).getPlayerId();
        int latestGameId = createGame(new CreateGameRequest(player1Id, player2Id)).getGameId();
        int nonExistentGameId = latestGameId + 1;

        GetGameDetailsResponse response = getGameDetails(new GetGameDetailsRequest(nonExistentGameId));

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
    public void Successful_Game_Details_Retrieval(){
        //Scenario 3.2
        int player1Id = createPlayer(new CreatePlayerRequest("goodName321", "verySecure")).getPlayerId();
        int player2Id = createPlayer(new CreatePlayerRequest("goodName322", "verySecure")).getPlayerId();
        int gameId = createGame(new CreateGameRequest(player1Id, player2Id)).getGameId();
        playMove(new PlayMoveRequest(gameId, player1Id, 1));
        
        GetGameDetailsResponse response = getGameDetails(new GetGameDetailsRequest(gameId));

        int[][] board = new int[6][7];
        board[5][0] = 1;

        assertEquals(true, response.wasSuccess());
        assertEquals("", response.getStatusMessage());
        assertEquals(gameId, response.getGameId());
        assertEquals(player1Id, response.getPlayer1Id());
        assertEquals(player2Id, response.getPlayer2Id());
        assertEquals(player2Id, response.getCurrentTurnPlayerId());
        assertEquals(1, response.getMoveNumber());
        assertEquals("Playing", response.getStatus());
        assertEquals(-1, response.getWinner());
        assertArrayEquals(board, response.getBoard());
    }    
}
