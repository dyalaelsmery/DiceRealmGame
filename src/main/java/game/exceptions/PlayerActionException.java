package game.exceptions;

public class PlayerActionException extends Exception{
    public PlayerActionException(){
        super("Player Action Exception occured action cannot be executed");
    }
    public PlayerActionException(String message){
        super(message);
    }
}
