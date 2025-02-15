package game.realms;
import game.creatures.*;
import game.dice.Dice;
import game.engine.*;
import game.exceptions.RewardException;
import game.collectibles.*;
public abstract class Realms {
    private final RealmColor REALM_COLOR;
    private boolean isRealmAccessible;//Realm isnt accessible when all enemies are dead!!
    private int totalRealmScore;
    private int totalNumberOfAttacks;
    private Reward[] realmRewards;
    private String[] previousAttacks;
    String[] drawRew;
//=======================================Constructor===================================
    public Realms(RealmColor color,int rewardSize,int maximumNumberOfAttacks){
        REALM_COLOR=color;
        isRealmAccessible=true;
        previousAttacks=new String[maximumNumberOfAttacks];
        initializePreviousAttacks(previousAttacks);
        drawRew=new String[rewardSize];
        realmRewards=new Reward[rewardSize];
        setRealmRewards(realmRewards);
    }
//=======================================Methods=======================================
public abstract void initializePreviousAttacks(String[] previousAttacks);
    public abstract boolean attack(int diceValue,Creature creature,Dice dice);
    public abstract void updateTotalRealmScore(int value);
    public abstract Move[] getAllPossibleMoves();
    public abstract Move[] getPossibleMovesForADie(int diceValue,RealmColor colorOfDice,Dice dice);
    public void incrementTotalNumberOfAttacks(){
        totalNumberOfAttacks++;
    }
    public void rewardClaimed(int index){
        realmRewards[index]=null;
    }
    public void recordAttack(int diceValue){
        if(diceValue<10)
            previousAttacks[totalNumberOfAttacks-1]=diceValue+"    ";
        else 
            previousAttacks[totalNumberOfAttacks-1]=diceValue+"   ";  
    }
//=======================================Get&Set=======================================
    public abstract boolean isRealmDefeated();
    public abstract boolean isRewardAvailable();
    public RealmColor getRealmColor(){
        return REALM_COLOR;
    }
    public void closeRealm(){//maybe rename to fit conventioln
        isRealmAccessible=false;
    }
    public boolean isRealmAccessible(){
        return isRealmAccessible;
    }
    public int getTotalRealmScore(){
        return totalRealmScore;
    }
    public void setTotalRealmScore(int value){
        totalRealmScore=value;
    }
    public int getTotalNumberOfAttacks(){
        return totalNumberOfAttacks;
    }
    public Reward[] getRealmRewards(){
        return realmRewards;
    }
    public String[] getPreviousAttacks(){
        return previousAttacks;
    }
    public abstract String[] getDrawRewards();
    public abstract Reward[] getReward() throws RewardException;
    public abstract void setRealmRewards(Reward[] realmRewards);
    public abstract Creature getCreatureByRealm(Dice dice);
    
//=======================================Display=======================================    
    @Override
    public abstract String toString();
}
