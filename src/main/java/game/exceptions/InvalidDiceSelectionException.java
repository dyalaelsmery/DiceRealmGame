package game.exceptions;

public class InvalidDiceSelectionException extends PlayerActionException{
    public InvalidDiceSelectionException(){
        super("Invalid Dice Selection Exception occured dice cannot be selected");
    }
    public InvalidDiceSelectionException(String message){
        super(message);
    }
}
