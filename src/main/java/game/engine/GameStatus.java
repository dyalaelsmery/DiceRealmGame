package game.engine;

public class GameStatus {
    private int gameRound;
    private int turn;
    private boolean playerSwitched;
 //=======================================Constructor===================================
    public GameStatus(){
        gameRound=1;
        turn=1;
    }
//=======================================Methods=======================================
    public void resetTurn(){
        turn=1;
    }
    public void incrementTurn(){
        turn++;
    }
    

    public boolean playerSwitched(){
        return playerSwitched;
    }
    public void incrementRound(){
        if(playerSwitched)  
            gameRound++;
        playerSwitched=playerSwitched?false:true;
        resetTurn();    
    }
//=======================================Get&Set=======================================
    public boolean isGameFinished(){
        return gameRound==7;
    }
    public int getGameRound(){
        return gameRound;
    }
    public int getTurn(){
        return turn;
    }
    public void setTurn(int val){
        turn=val;
    }
}
