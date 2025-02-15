package game.exceptions;

public class CommandFormatException extends Exception{
    public CommandFormatException(){
        super("Command Format Exception occured please use proper format");
    }
    public CommandFormatException(String message){
        super(message);
    }
}
