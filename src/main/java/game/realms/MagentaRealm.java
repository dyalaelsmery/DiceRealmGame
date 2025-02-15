package game.realms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import game.collectibles.*;
import game.creatures.*;
import game.dice.*;
import game.engine.*;
import game.exceptions.InvalidMoveException;
import game.exceptions.RewardException;

public class MagentaRealm extends Realms{
    private Phoenix phoenix;

    public MagentaRealm(){
            super(RealmColor.MAGENTA,11,11);
            phoenix=new Phoenix();
        }
        
@Override
    public  boolean attack(int diceValue,Creature creature,Dice dice){
        try{
        if(isRealmDefeated())
            throw new InvalidMoveException("Magenta Realm has been defeated and cannot be accessed");    
        if(diceValue<=0)
            throw new InvalidMoveException("Dice Value is <=0 attack failed");  
        if(phoenix.checkPossibleAttack(diceValue)){
            phoenix.setHealth(diceValue);
            updateTotalRealmScore(diceValue);
            incrementTotalNumberOfAttacks();
            recordAttack(diceValue);
            if(isRealmDefeated())
                closeRealm();
            return true;
        }else{
            throw new InvalidMoveException("Dice Value is not enough to attack Phoenix");      
        }
        }catch(InvalidMoveException ex){
            System.out.println(ex.getMessage());
        }
        return false;
    }
    @Override
    public void updateTotalRealmScore(int value) {
        int oldScore=getTotalRealmScore();
        setTotalRealmScore(value+oldScore);
    }
    @Override
    public Move[] getAllPossibleMoves() {
        if(!isRealmAccessible())
            return new Move[0];
        int minimumAttackValue=phoenix.getMinimumAttackValue();
        int moveArraySize=6-minimumAttackValue+1;
        Move[] moves=new Move[moveArraySize];
        for(int i=0;i<moves.length;i++,minimumAttackValue++)
            moves[i]=getPossibleMovesForADie(minimumAttackValue,RealmColor.MAGENTA,null)[0];
        return  moves;
    }
    @Override
    public Move[] getPossibleMovesForADie(int diceValue, RealmColor colorOfDice,Dice d) {
        if(!isRealmAccessible())
            return new Move[0];
        if(phoenix.checkPossibleAttack(diceValue)){
            MagentaDice tempDice=new MagentaDice(diceValue);
            return new Move[]{new Move(tempDice,phoenix)};
        }
        return new Move[0];
    }
    @Override
    public void initializePreviousAttacks(String[] previousAttacks) {
        for(int i=0;i<previousAttacks.length;i++)
         previousAttacks[i]="0    ";
    }
   @Override
    public boolean isRealmDefeated() {
        return getTotalNumberOfAttacks()==11;
    }
    @Override
    public Reward[] getReward() throws RewardException{
        if(!isRewardAvailable())
            throw new RewardException("There are no rewards available , an error occured in checking  in Magenta Realm");

        Reward[] rewards=getRealmRewards();
        Reward recievedReward=rewards[getTotalNumberOfAttacks()-1];
        if(recievedReward==null)
            throw new RewardException("Null reward found in Magenta Realm"); 
        rewardClaimed(getTotalNumberOfAttacks()-1);
        drawRew[getTotalNumberOfAttacks()-1]="X    ";
        return new Reward[]{recievedReward};
    }
    @Override
    public void setRealmRewards(Reward[] realmRewards) {
         //Gets the properties of a config file
        try {
            String path = "src/main/resources/config/MysticalSkyRewards.properties";
            String[] rewardString=new String[realmRewards.length];
            int s=0;
            try (BufferedReader reader = new BufferedReader(new FileReader(path))){
                String line;
                line=reader.readLine();
                while (((line=reader.readLine())) != null) {
                    String[] splitter = line.split("=");
                    rewardString[s++]=splitter[1].trim();
                }


            for (int i = 0; i < realmRewards.length; i++) {
                switch (rewardString[i]) {
                    case "RedBonus":    
                        realmRewards[i] = new Bonus(RealmColor.RED);
                        drawRew[i]="RB   ";
                        break;
                    case "GreenBonus":
                        realmRewards[i] = new Bonus(RealmColor.GREEN);
                        drawRew[i]="GB   ";
                        break;
                    case "BlueBonus":
                        realmRewards[i] = new Bonus(RealmColor.BLUE);
                        drawRew[i]="BB   ";
                        break;
                    case "MagentaBonus":
                        realmRewards[i] = new Bonus(RealmColor.MAGENTA);
                        drawRew[i]="MB   ";
                        break;
        
                    case "YellowBonus":
                        realmRewards[i] = new Bonus(RealmColor.YELLOW);
                        drawRew[i]="YB   ";
                        break;
                    case "EssenceBonus":
                        realmRewards[i] = new EssenceBonus();
                        drawRew[i]="EB   ";
                        break;
                    
                    case "ElementalCrest":
                        realmRewards[i] = new ElementalCrest();
                        drawRew[i]="EC   ";
                        break;
                    case "ArcaneBoost":
                        realmRewards[i] = new ArcaneBoost();
                        drawRew[i]="AB   ";
                        break;
                    case "TimeWarp":
                        realmRewards[i] = new TimeWarp();
                        drawRew[i]="TW   ";
                        break;
                    
                    case "null":
                        realmRewards[i] =null;
                        drawRew[i]="     ";
                        break;
                    default : throw new IOException("Unrecognized Reward");
                }
            }
        }
        }catch (IOException e){
            // if file not found or error in it proceed with default values
            System.out.println("Error in loading property file Magenta realm, proceeding with the default values");
            Reward[] templateRewards=new Reward[]{null,null,new TimeWarp(),new Bonus(RealmColor.GREEN),new ArcaneBoost(),
                new Bonus(RealmColor.RED),new ElementalCrest(),new TimeWarp(),new Bonus(RealmColor.BLUE),new Bonus(RealmColor.YELLOW),
                new ArcaneBoost()};

                for(int i=0;i<templateRewards.length;i++)
                    realmRewards[i]=templateRewards[i];


            String[] tempDrawRew=new String[]{"     ","     ","TW   ","GB   ","AB   ","RB   ","EC   ","TW   ","BB   ","YB   ","AB   "};        
            for(int i=0;i<templateRewards.length;i++)
            drawRew[i]=tempDrawRew[i];


 
        }
        
    }
    @Override 
       public String[] getDrawRewards(){
            return drawRew;
        }
    @Override
    public boolean isRewardAvailable() {
        Reward[] rewards=getRealmRewards();
        return rewards[getTotalNumberOfAttacks()-1]!=null;
    }public Phoenix getPhoenix(){
        return phoenix;
    }
    public  Creature getCreatureByRealm(Dice dice){
        return phoenix;   
    }    
    @Override
    public String toString() {
        String[] prevAt=getPreviousAttacks();//previousAttacks

       for(int i=0;i<getTotalNumberOfAttacks()-1;i++){
        if(!drawRew[i].equals("     "))
            drawRew[i]="X    ";}

      return "Mystical Sky: Majestic Phoenix (MAGENTA REALM):"+"\n"+
       "+-----------------------------------------------------------------------+"+"\n"+
       "|  #  |1    |2    |3    |4    |5    |6    |7    |8    |9    |10   |11   |"+"\n"+
       "+-----------------------------------------------------------------------+"+"\n"+
       "|  H  |"+prevAt[0]+"|"+prevAt[1]+"|"+prevAt[2]+"|"+prevAt[3]+"|"+prevAt[4]+"|"+
       prevAt[5]+"|"+prevAt[6]+"|"+prevAt[7]+"|"+prevAt[8]+"|"+prevAt[9]+"|"+prevAt[10]+"|"+"\n"+
       "|  C  |<    |<    |<    |<    |<    |<    |<    |<    |<    |<    |<    |"+"\n"+
       "|  R  |"+drawRew[0]+"|"+drawRew[1]+"|"+drawRew[2]+"|"+drawRew[3]+"|"+drawRew[4]+"|"+drawRew[5]+"|"+
       drawRew[6]+"|"+drawRew[7]+"|"+drawRew[8]+"|"+drawRew[9]+"|"+drawRew[10]+"|"+"\n"+
       "+-----------------------------------------------------------------------+"+"\n\n";
    }
}
