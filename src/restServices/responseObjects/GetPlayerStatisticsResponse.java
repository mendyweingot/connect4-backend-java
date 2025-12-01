package src.restServices.responseObjects;

public class GetPlayerStatisticsResponse {
    private final boolean success;
    private final String STATUS_MESSAGE;
    private final String name;
    private final int wins;
    private final int losses;
    private final int draws;

    public GetPlayerStatisticsResponse(String name, int wins, int losses, int draws){
        success = true;
        STATUS_MESSAGE = "";
        this.name = name;
        this.wins = wins;
        this.losses = losses;
        this.draws = draws;
    }

    public GetPlayerStatisticsResponse(String statusMessage){
        success = false;
        STATUS_MESSAGE = statusMessage;
        name = "";
        wins = losses = draws = -1;
    }

    public boolean wasSuccess(){
        return success;
    }

    public String getStatusMessage(){
        return STATUS_MESSAGE;
    }

    public String getName(){
        return name;
    }

    public int getWins(){
        return wins;
    }

    public int getLosses(){
        return losses;
    }

    public int getDraws(){
        return draws;
    }
}
