package game.realms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import game.collectibles.*;
import game.creatures.*;
import game.dice.BlueDice;
import game.dice.Dice;
import game.engine.*;
import game.exceptions.InvalidMoveException;
import game.exceptions.RewardException;

public class BlueRealm extends Realms{
    private Hydra hydra;
    private int totalDefeatedHeads;

//=======================================Constructor===================================
    public BlueRealm(){
        super(RealmColor.BLUE,11,11);
        hydra=new Hydra();
        hydra.spawnHydra();
    }
//=======================================Methods=======================================
    @Override
    public boolean attack(int diceValue,Creature creature,Dice dice){
        try{
        if(isRealmDefeated())
            throw new InvalidMoveException("Blue Realm has been defeated and cannot be accessed");    
        if(diceValue<=0)
            throw new InvalidMoveException("Dice Value is <=0 attack failed");  
        if(hydra.checkPossibleAttack(diceValue)){
            if(hydra.killHead()&&!hydra.isRespawned())
                hydra.respawnHydra();
            totalDefeatedHeads++;
            incrementTotalNumberOfAttacks();
            recordAttack(diceValue);//the string thing
            updateTotalRealmScore(totalDefeatedHeads);
            if(isRealmDefeated())
                closeRealm();

            return true;
        }
        else
        throw new InvalidMoveException("Dice Value is not enough to attack hydra");     
        }catch(InvalidMoveException ex){
            System.out.println(ex.getMessage());
        }
        return false;
    } 
    @Override    
    public void updateTotalRealmScore(int totalDefeatedHeads){
        switch(totalDefeatedHeads){
            case 1:setTotalRealmScore(1);break;
            case 2:setTotalRealmScore(3);break;
            case 3:setTotalRealmScore(6);break;
            case 4:setTotalRealmScore(10);break;
            case 5:setTotalRealmScore(15);break;
            case 6:setTotalRealmScore(21);break;
            case 7:setTotalRealmScore(28);break;
            case 8:setTotalRealmScore(36);break;
            case 9:setTotalRealmScore(45);break;
            case 10:setTotalRealmScore(55);break;
            case 11:setTotalRealmScore(66);break;
        }
    }   
    @Override
    public Move[] getAllPossibleMoves() {
        if(!isRealmAccessible())
            return new Move[0];
        int minimumAttackValue=hydra.getMinimumAttackValue();
        int moveArraySize=6-minimumAttackValue+1;       //+1 to include the minimumAttack Value
        Move[] moves=new Move[moveArraySize];
        for(int i=0;i<moves.length;i++,minimumAttackValue++)
            moves[i]=getPossibleMovesForADie(minimumAttackValue,RealmColor.BLUE,null)[0];
        return  moves;
    }
    @Override
    public Move[] getPossibleMovesForADie(int diceValue,RealmColor colorOfDice,Dice d) {
        if(!isRealmAccessible())
            return new Move[0];
            
        if(hydra.checkPossibleAttack(diceValue)){
            BlueDice tempDice=new BlueDice(diceValue);
            return new Move[]{new Move(tempDice,hydra)};
        }
        return new Move[0];
    }
    
    @Override
    public void initializePreviousAttacks(String[] previousAttacks){
        for(int i=0;i<previousAttacks.length;i++)
            previousAttacks[i]="---  ";
        
    }
//=======================================Get&Set=======================================
    @Override
    public Reward[] getReward() throws RewardException{
        if(!isRewardAvailable())
            throw new RewardException("There are no rewards available , an error occured in checking  in BlueRealm");

        Reward[] rewards=getRealmRewards();
        Reward recievedReward=rewards[getTotalNumberOfAttacks()-1];

        if(recievedReward==null)
            throw new RewardException("Null reward found in BlueRealm");   
            
        drawRew[getTotalNumberOfAttacks()-1]="X    ";    
        rewardClaimed(getTotalNumberOfAttacks()-1);
        return new Reward[]{recievedReward};
    }
    @Override
    public void setRealmRewards(Reward[] realmRewards) {
        //Gets the properties of a config file
        try {
            
            String path = "src/main/resources/config/TideAbyssRewards.properties";
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
            System.out.println("Error in loading property file Blue realm, proceeding with the default values");
            Reward[] templateRewards=new Reward[]{null,null,null,new ArcaneBoost(),null,
                new Bonus(RealmColor.GREEN),new ElementalCrest(),null,new Bonus(RealmColor.MAGENTA),new TimeWarp(),null};   
            for(int i=0;i<templateRewards.length;i++)
                realmRewards[i]=templateRewards[i];

            String[]tempdrawRew=new String[]{"     ","     ","     ","AB   ","     ","GB   ","EC   ","     ","MB   ","TW   ","     "};
            for(int i=0;i<tempdrawRew.length;i++)
                drawRew[i]=tempdrawRew[i];
        }
    }
    
    @Override
    public boolean isRealmDefeated() {
        return getTotalNumberOfAttacks()==11;
    }  
    @Override
    public boolean isRewardAvailable() {
        Reward[] rewards=getRealmRewards();
        return rewards[getTotalNumberOfAttacks()-1]!=null;
    }
    public Hydra getHydra(){
        return hydra;
    }
    public  Creature getCreatureByRealm(Dice dice){
        return hydra;
    }
    @Override
    public String[] getDrawRewards() {
        return drawRew;
    }
//=======================================Display=======================================
    @Override
    public String toString() {
       String[] prevAt=getPreviousAttacks();//previousAttacks
       for(int i=0;i<getTotalNumberOfAttacks()-1;i++){
            if(!drawRew[i].equals("     "))
                drawRew[i]="X    ";
       }
        return "Tide Abyss: Hydra Serpents (BLUE REALM):"+"\n"+
        "+-----------------------------------------------------------------------+"+"\n"+
        "|  #  |H11  |H12  |H13  |H14  |H15  |H21  |H22  |H23  |H24  |H25  |H26  |"+"\n"+
        "+-----------------------------------------------------------------------+"+"\n"+
        "|  H  |"+prevAt[0]+"|"+prevAt[1]+"|"+prevAt[2]+"|"+prevAt[3]+"|"+prevAt[4]+"|"
        +prevAt[5]+"|"+prevAt[6]+"|"+prevAt[7]+"|"+prevAt[8]+"|"+prevAt[9]+"|"+prevAt[10]+"|"+"\n"+
        "|  C  |≥1   |≥2   |≥3   |≥4   |≥5   |≥1   |≥2   |≥3   |≥4   |≥5   |≥6   |"+"\n"+
        "|  R  |"+drawRew[0]+"|"+drawRew[1]+"|"+drawRew[2]+"|"+drawRew[3]+"|"+drawRew[4]+"|"+drawRew[5]+"|"+drawRew[6]+"|"+drawRew[7]+"|"
        +drawRew[8]+"|"+drawRew[9]+"|"+drawRew[10]+"|"+"\n"+
        "+-----------------------------------------------------------------------+"+"\n"+
        "|  S  |1    |3    |6    |10   |15   |21   |28   |36   |45   |55   |66   |"+"\n"+
        "+-----------------------------------------------------------------------+"+"\n\n";
    }
}