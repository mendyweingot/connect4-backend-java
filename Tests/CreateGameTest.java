package Tests;

import org.junit.Test;
import static org.junit.Assert.*;
import static src.controller.GameController.*;
import static src.controller.PlayerController.*;
import src.restServices.requestObjects.*;
import src.restServices.responseObjects.*;

public class CreateGameTest {
    
    @Test
    public void Player1_Does_Not_Exist(){
        //Scenario 2.1
        int player2Id = createPlayer(new CreatePlayerRequest("goodName21", "secret")).getPlayerId();
        int player1Id = player2Id + 1;
        CreateGameResponse response = createGame(new CreateGameRequest(player1Id, player2Id));

        assertEquals(false, response.wasSuccess());
        assertEquals("Player with ID of #" + player1Id +" does not exist.", response.getStatusMessage());
        assertEquals(-1, response.getGameId());
        assertEquals(player1Id, response.getPlayer1Id());
        assertEquals(player2Id, response.getPlayer2Id());
        assertEquals(-1, response.getCurrentTurnPlayerId());
        assertNull(response.getBoard());
    }

    @Test
    public void Player2_Does_Not_Exist(){
        //Scenario 2.2
        int player1Id = createPlayer(new CreatePlayerRequest("goodName22", "secret")).getPlayerId();
        int player2Id = player1Id + 1;
        CreateGameResponse response = createGame(new CreateGameRequest(player1Id, player2Id));

        assertEquals(false, response.wasSuccess());
        assertEquals("Player with ID of #" + player2Id +" does not exist.", response.getStatusMessage());
        assertEquals(-1, response.getGameId());
        assertEquals(player1Id, response.getPlayer1Id());
        assertEquals(player2Id, response.getPlayer2Id());
        assertEquals(-1, response.getCurrentTurnPlayerId());
        assertNull(response.getBoard());
    }

    @Test
    public void Player1_And_Player2_Do_Not_Exist(){
        //Scenario 2.3

        //This provides the latest ID; so that any ID that's larger, does not exist.
        int latestPlayerId = createPlayer(new CreatePlayerRequest("lastPlayer23", "secret")).getPlayerId(); 

        int player1Id = latestPlayerId + 1;
        int player2Id = player1Id + 1;
        CreateGameResponse response = createGame(new CreateGameRequest(player1Id, player2Id));

        assertEquals(false, response.wasSuccess());
        assertEquals("Player with ID of #" + player1Id +" does not exist, and player with ID of #" + player2Id + " does not exist.", response.getStatusMessage());
        assertEquals(-1, response.getGameId());
        assertEquals(player1Id, response.getPlayer1Id());
        assertEquals(player2Id, response.getPlayer2Id());
        assertEquals(-1, response.getCurrentTurnPlayerId());
        assertNull(response.getBoard());
    }

    @Test
    public void Successful_Game_Creation(){
        //Scenario 2.4
        //Part 1 - Successful Game Creation
        int player1Id = createPlayer(new CreatePlayerRequest("goodName241", "secret")).getPlayerId();
        int player2Id = createPlayer(new CreatePlayerRequest("goodName242", "secret")).getPlayerId();
        CreateGameResponse response = createGame(new CreateGameRequest(player1Id, player2Id));

        assertEquals(true, response.wasSuccess());
        assertEquals("", response.getStatusMessage());        
        assertEquals(player1Id, response.getPlayer1Id());
        assertEquals(player2Id, response.getPlayer2Id());
        assertEquals(player1Id, response.getCurrentTurnPlayerId());
        assertArrayEquals(new int[6][7], response.getBoard());

        //Part 2 - Next ID
        int nextId = response.getGameId() + 1;
        response = createGame(new CreateGameRequest(player1Id, player2Id));

        assertEquals(true, response.wasSuccess());
        assertEquals(nextId, response.getGameId());
    }
}
