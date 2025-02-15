package game.dice;

import game.realms.RealmColor;

public class RedDice extends Dice{
    private int selectsDragon;
//=======================================Constructor===================================
    public RedDice(int diceValue){
        super(RealmColor.RED,diceValue);
    }
//=======================================Methods=======================================
    public void selectsDragon(int x){//Doesn't follow naming convention to meet test case
        selectsDragon=x;
    }
//=======================================Get&Set=======================================
    public int getselectsDragon(){
        return selectsDragon;
    }
}
