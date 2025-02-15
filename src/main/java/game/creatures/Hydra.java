package game.creatures;

import game.realms.RealmColor;

public class Hydra extends Creature{
    private int remainingHeads;
    private int totalHeads;
    private boolean isRespawned;
//=======================================Constructor===================================
    public Hydra(){
            super(RealmColor.BLUE);
    }
//=======================================Methods=======================================
    @Override
    public boolean checkPossibleAttack(int diceValue){
        int minValue=(totalHeads-remainingHeads)+1;
        return diceValue>=minValue;
    }
    public boolean killHead(){
        remainingHeads-=1;
        return remainingHeads==0;
    }  
    public void spawnHydra(){
        remainingHeads=5;
        totalHeads=5;
    }
    public void respawnHydra(){
        remainingHeads=6;
        totalHeads=6;
        isRespawned=true;
    }
//=======================================Get&Set=======================================
    @Override
    public int getMinimumAttackValue(){
        return (totalHeads-remainingHeads)+1;
    }
    public boolean isRespawned(){
        return isRespawned;
    }
    public int getRemainingNumberOfHeads(){
        return remainingHeads;
    }
//=======================================Display=======================================
    @Override 
    public String toString(){
        return "Hydra";
    }
}