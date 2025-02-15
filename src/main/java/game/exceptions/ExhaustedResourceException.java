package game.exceptions;

public class ExhaustedResourceException extends PlayerActionException{
    public ExhaustedResourceException(){
        super("Exhaused Resource Exception occured not enough resource");
    }
    public ExhaustedResourceException(String message){
        super(message);
    }
}
