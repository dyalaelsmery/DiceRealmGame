package game.exceptions;

public class InvalidMoveException extends PlayerActionException{
    public InvalidMoveException(){
        super("Invalid Move Exception occured move is invalid");
    }
    public InvalidMoveException(String message){
        super(message);
    }
}
