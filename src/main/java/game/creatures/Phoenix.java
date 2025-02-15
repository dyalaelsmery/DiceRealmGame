package game.creatures;

import game.realms.RealmColor;

public class Phoenix extends Creature{
    private int health;
    public Phoenix(){
        super(RealmColor.MAGENTA);
    }
    @Override
    public boolean checkPossibleAttack(int diceValue) {
        return diceValue>health;
    }
    public void resetHealth(){
        health=0;
    }
    @Override
    public int getMinimumAttackValue(){
        return health+1;
    }
    public void setHealth(int value){
       if(value==6)
            resetHealth();
       else
            health=value;     
    }
    public int getHealth(){
        return health;
    }
    @Override 
    public String toString(){
        return "Phoenix";
    }
}    
