package src.restServices.requestObjects;

public class GetPlayerStatisticsRequest {
    private final int playerId;

    public GetPlayerStatisticsRequest(int playerId){
        this.playerId = playerId;
    }

    public int getPlayerId(){
        return playerId;
    }
}
