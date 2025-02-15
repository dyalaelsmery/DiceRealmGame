package game.exceptions;

public class RewardException extends Exception {
    public RewardException(){
        super("Reward Exception occured reward missing or invalid");
    }
    public RewardException(String message){
        super(message);
    }
}
