package src.restServices.requestObjects;

public class CreateGameRequest {
    
    private final int player1Id;
    private final int player2Id;

    public CreateGameRequest(int player1Id, int player2Id){
        this.player1Id = player1Id;
        this.player2Id = player2Id;
    }

    public int getPlayer1Id(){
        return player1Id;
    }

    public int getPlayer2Id(){
        return player2Id;
    }
}
