package game.creatures;

import game.realms.RealmColor;

public class Lion extends Creature {
    public Lion(){
        super(RealmColor.YELLOW);
    }
    @Override
    public boolean checkPossibleAttack(int diceValue) {
        if(diceValue>0&&diceValue<7)
        return true;//always Possible
        return false;
    }
    @Override public String toString(){
        return "Lion";
    }
}
