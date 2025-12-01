package src.controller;

import src.domainObjects.*;
import src.models.*;
import src.restServices.requestObjects.*;
import src.restServices.responseObjects.*;

public class PlayerController {

    public static CreatePlayerResponse createPlayer(CreatePlayerRequest request){
        String name = request.getPlayerName();
        String password = request.getPassword();

        String validationStatus = PlayerModel.validate(name, password);
        if (validationStatus != null) return new CreatePlayerResponse(validationStatus, name);

        PlayerDomainObject player = new PlayerDomainObject(name, password);
        PlayerDomainObject createdPlayer = PlayerModel.addPlayer(player);
        return new CreatePlayerResponse(createdPlayer.getId(), createdPlayer.getName());
    }    

    //needed for the purpose of testing playMove()
    public static GetPlayerStatisticsResponse getPlayerStatistics(GetPlayerStatisticsRequest request){
        int playerId = request.getPlayerId();

        PlayerDomainObject player = PlayerModel.getPlayerById(playerId);
        if (player == null) return new GetPlayerStatisticsResponse("Player with ID of #" + playerId + " does not exist.");

        return new GetPlayerStatisticsResponse(player.getName(), player.getWins(), player.getLosses(), player.getDraws());
    }
}
