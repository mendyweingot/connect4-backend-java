package Tests;

import org.junit.Test;
import static org.junit.Assert.*;
import static src.controller.PlayerController.*;
import src.restServices.requestObjects.*;
import src.restServices.responseObjects.*;

public class CreatePlayerTest {

    @Test
    public void Name_Too_Short(){
        //Scenario 1.1
        String name = "Jack";
        String password = "verySecure";
        CreatePlayerResponse response = createPlayer(new CreatePlayerRequest(name, password));

        assertEquals(false, response.wasSuccess());
        assertEquals("Invalid Username. Username must be between five and twenty characters long.", response.getStatusMessage());
        assertEquals(-1, response.getPlayerId());
        assertEquals("Jack", response.getName());
    }

    @Test
    public void Name_Too_Long(){
        //Scenario 1.2
        String name = "WhatAVeryLongNameItIs";
        String password = "verySecure";
        CreatePlayerResponse response = createPlayer(new CreatePlayerRequest(name, password));

        assertEquals(false, response.wasSuccess());
        assertEquals("Invalid Username. Username must be between five and twenty characters long.", response.getStatusMessage());
        assertEquals(-1, response.getPlayerId());
        assertEquals("WhatAVeryLongNameItIs", response.getName());
    }

    @Test
    public void Name_Contains_Invalid_Character(){
        //Scenario 1.3
        String name = "Jack name";
        String password = "verySecure";
        CreatePlayerResponse response = createPlayer(new CreatePlayerRequest(name, password));

        assertEquals(false, response.wasSuccess());
        assertEquals("Invalid Username. Username must only contain letters and digits.", response.getStatusMessage());
        assertEquals(-1, response.getPlayerId());
        assertEquals("Jack name", response.getName());

        //Additional test similar to Scenario 1.3
        name = "Jack,name";
        response = createPlayer(new CreatePlayerRequest(name, password));
        assertEquals("Invalid Username. Username must only contain letters and digits.", response.getStatusMessage());
    }

    @Test
    public void Name_Not_Unique(){
        //Scenario 1.4
        createPlayer(new CreatePlayerRequest("GoodName14", "password"));
        String name = "GoodName14";
        String password = "verySecure";
        CreatePlayerResponse response = createPlayer(new CreatePlayerRequest(name, password));

        assertEquals(false, response.wasSuccess());
        assertEquals("Username GoodName14 is already taken.", response.getStatusMessage());
        assertEquals(-1, response.getPlayerId());
        assertEquals("GoodName14", response.getName());
    }

    @Test
    public void Password_Too_Short(){
        //Scenario 1.5
        String name = "GoodName";
        String password = "pass";
        CreatePlayerResponse response = createPlayer(new CreatePlayerRequest(name, password));

        assertEquals(false, response.wasSuccess());
        assertEquals("Invalid Password. Password must be between five and twenty characters long.", response.getStatusMessage());
        assertEquals(-1, response.getPlayerId());
        assertEquals("GoodName", response.getName());
    }

    @Test
    public void Password_Too_Long(){
        //Scenario 1.6
        String name = "GoodName";
        String password = "MyVerySecurePassword1";
        CreatePlayerResponse response = createPlayer(new CreatePlayerRequest(name, password));

        assertEquals(false, response.wasSuccess());
        assertEquals("Invalid Password. Password must be between five and twenty characters long.", response.getStatusMessage());
        assertEquals(-1, response.getPlayerId());
        assertEquals("GoodName", response.getName());
    }

    @Test
    public void Password_Contains_Invalid_Character(){
        //Scenario 1.7
        String name = "GoodName";
        String password = "very*Secure";
        CreatePlayerResponse response = createPlayer(new CreatePlayerRequest(name, password));

        assertEquals(false, response.wasSuccess());
        assertEquals("Invalid Password. Password must only contain letters and digits.", response.getStatusMessage());
        assertEquals(-1, response.getPlayerId());
        assertEquals("GoodName", response.getName());
    }

    @Test
    public void Successful_Player_Creation(){
        //Scenario 1.8 
        //Part 1 - Player Created Successfully.
        String name = "Five5";
        String password = "verySecure1";
        CreatePlayerResponse response = createPlayer(new CreatePlayerRequest(name, password));

        assertEquals(true, response.wasSuccess());
        assertEquals("", response.getStatusMessage());
        assertEquals("Five5", response.getName());

        //Part 2 - Next Id
        name = "TwentyLettersExactly";
        password = "verySecure1";
        int nextId = response.getPlayerId() + 1;
        response = createPlayer(new CreatePlayerRequest(name, password));

        assertEquals(true, response.wasSuccess());
        assertEquals(nextId, response.getPlayerId());
    }
}
