package game.dice;
import game.realms.*;
public abstract class Dice implements Comparable<Dice>{
    private DiceStatus diceStatus;
    private int diceValue;
    private final RealmColor DICE_COLOR;
//=======================================Constructor===================================
    public Dice(RealmColor diceColor,int diceValue){
        DICE_COLOR=diceColor;
        diceStatus=DiceStatus.AVAILABLE;
        this.diceValue=diceValue;
    }
//=======================================Methods=======================================
    public void rollDice(){
        int newValue=((int)((Math.random()*6)+1));
        diceValue=newValue;
    }
    @Override
    public int compareTo(Dice o){
        Dice dice1=this;
        Dice dice2=o;
        //Make sure white color doesnt affect
        RealmColor color1=dice1.getDiceColor();
        if(color1==RealmColor.WHITE)color1=((ArcanePrism)dice1).getChosenColor();
        RealmColor color2=dice2.getDiceColor();
        if(color2==RealmColor.WHITE)color2=((ArcanePrism)dice2).getChosenColor();
        //Secondary sort based on int value
        if(color1==color2)
            return dice2.getValue()-dice1.getValue();
        //Primary Sort on Color    
        if(color1==RealmColor.RED)
            return -1;
        if(color2==RealmColor.RED)
            return 1;
        if(color1==RealmColor.GREEN)
            return -1;
        if(color2==RealmColor.GREEN)
            return 1;
        if(color1==RealmColor.BLUE)
            return -1;
        if(color2==RealmColor.BLUE)
            return 1;  
        if(color1==RealmColor.MAGENTA)
            return -1;
        if(color2==RealmColor.MAGENTA)
            return 1;  
        if(color1==RealmColor.YELLOW)
            return -1;    
        return 1;//color2 yellow   
    }
//=======================================Get&Set=======================================
    public RealmColor getDiceColor(){
       return DICE_COLOR;
    }
    public RealmColor getRealm(){
        return DICE_COLOR;
    }
    public DiceStatus getDiceStatus(){
        return diceStatus;
    }
    public void setDiceStatus(DiceStatus status){
        diceStatus=status;
    }
    public int getValue(){
        return diceValue;
    }
    public void setValue(int value){
        diceValue=value;
    }
//=======================================Display=======================================    
    @Override
    public String toString(){
        return DICE_COLOR+": "+diceValue;
    }
}

