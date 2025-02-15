package game.exceptions;

public class DiceRollException extends PlayerActionException{
    public DiceRollException(){
        super("Dice Roll Exception occured, dice rolling failed");
    }
    public DiceRollException(String message){
        super(message);
    }
}
