package game.creatures;

import game.realms.RealmColor;

public class Gaia extends Creature{
    private boolean[] gurdiansHealth;
    public Gaia(){
        super(RealmColor.GREEN);
        gurdiansHealth=new boolean[13];
        for(int i=2;i<gurdiansHealth.length;i++)
            gurdiansHealth[i]=true;
        //False for dead , True for avaibble, Index = required Value 
    }
    @Override
    public boolean checkPossibleAttack(int diceValue) {
        return diceValue>=2&&diceValue<=12&&gurdiansHealth[diceValue];
    }
    public void killGaiaGurdian(int diceValue){
        gurdiansHealth[diceValue]=false;
    }
    public boolean[] getGurdiansHealth(){
            return gurdiansHealth;
    }
    @Override 
    public String toString(){
        return "Gaia";
    }
}
