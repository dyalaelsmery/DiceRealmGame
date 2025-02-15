package game.realms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import game.collectibles.ArcaneBoost;
import game.collectibles.Bonus;
import game.collectibles.ElementalCrest;
import game.collectibles.EssenceBonus;
import game.collectibles.Reward;
import game.collectibles.TimeWarp;
import game.creatures.*;
import game.dice.Dice;
import game.dice.YellowDice;
import game.engine.Move;
import game.exceptions.InvalidMoveException;
import game.exceptions.RewardException;

public  class YellowRealm extends Realms{
    private Lion lion;
    //constructor
    public YellowRealm()
    {
        super(RealmColor.YELLOW,11,11);
        lion=new Lion(); 
    }

    //Methods
    public boolean isRealmDefeated()  //checks if Realm is defeated
    {
        return (getTotalNumberOfAttacks()==11);
    }

    @Override
    public  boolean attack(int diceValue,Creature creature,Dice dice) // Attacks creature if possible
    {   
        try{
        if(isRealmDefeated())
            throw new InvalidMoveException("Yellow Realm has been defeated and cannot be accessed");    
        if(diceValue<=0)
            throw new InvalidMoveException("Dice Value is <=0 attack failed");  

        if(lion.checkPossibleAttack(diceValue))
        {
            incrementTotalNumberOfAttacks();
            int value=diceValue;
            int attackNumber= getTotalNumberOfAttacks();
            switch(attackNumber)
            {
                case 4:case 7:case 9: value*=2;break;
                case 11: value*=3;closeRealm();break;
                default: break;
            }
            updateTotalRealmScore(value);
            recordAttack(value);
            return true;
        }else{
            throw new InvalidMoveException("Dice Value is not enough to attack Lion");     
        }
    }catch(InvalidMoveException ex){
        System.out.println(ex.getMessage());
    }
        return false;
    }

    

    @Override
    public void updateTotalRealmScore(int value) //updates yellow realm score
    {   
        setTotalRealmScore(getTotalRealmScore()+value);
    }
    
    @Override
    public  void initializePreviousAttacks(String[] previousAttacks) //puts the previous attacks in String for the toString method (to print scoresheet)
    {
        for(int i=0;i<previousAttacks.length;i++)
            previousAttacks[i]="0    ";
    }
    @Override
    public String[] getDrawRewards(){
        return drawRew;
    }

    @Override
    public  boolean isRewardAvailable() //checks if Reward is available
    {
        return getRealmRewards()[getTotalNumberOfAttacks()-1]!=null;
    }

    //setters and getters
    @Override
    public void setRealmRewards(Reward[] rewards) //Reads rewards from config file
    {
        Reward[] temp= new Reward[rewards.length];
        String path = "src/main/resources/config/RadiantSvannaRewards.properties";
        String[] string=new String[rewards.length];
        int s=0;
        try (BufferedReader reader = new BufferedReader(new FileReader(path))){
            String line;
            line=reader.readLine();
            while (((line=reader.readLine())) != null) {
                String[] splitter = line.split("=");
                string[s++]=splitter[1].trim();
            }
        for(int i=0;i<string.length;i++)
        {
            String value=string[i];
            switch (value) {
                case "RedBonus":
                    temp[i]=new Bonus(RealmColor.RED);rewards[i]=temp[i];drawRew[i]="RB   ";break;
                case "GreenBonus":
                    temp[i]=new Bonus(RealmColor.GREEN);rewards[i]=temp[i];drawRew[i]="GB   ";break;
                case "BlueBonus":
                    temp[i]=new Bonus(RealmColor.BLUE);rewards[i]=temp[i];drawRew[i]="BB   ";break;
                case "MagentaBonus":
                    temp[i]=new Bonus(RealmColor.MAGENTA);rewards[i]=temp[i];drawRew[i]="MB   ";break;
                case "YellowBonus":
                    temp[i]=new Bonus(RealmColor.YELLOW);rewards[i]=temp[i];drawRew[i]="YB   ";break;
                case "ArcaneBoost":
                    temp[i]=new ArcaneBoost();rewards[i]=temp[i];drawRew[i]="AB   ";break;
                case "EssenceBonus":
                    temp[i]=new EssenceBonus();rewards[i]=temp[i];drawRew[i]="EB   ";break;
                case "ElementalCrest":
                    temp[i]=new ElementalCrest();rewards[i]=temp[i];drawRew[i]="EC   ";break;
                case "TimeWarp":
                    temp[i]=new TimeWarp();rewards[i]=temp[i];drawRew[i]="TW   ";break;
                case "null":
                        rewards[i] =null;
                        drawRew[i]="     ";
                        break;
                default : throw new IOException("Unrecognized Reward");
            }
            
        }
        }catch(Exception ex){
            // if file not found or error in it proceed with default values
            System.out.println("Error in loading property file Yellow realm, proceeding with the default values");
            Reward[] templateRewards=new Reward[]{null,null,new TimeWarp(),null,new Bonus(RealmColor.RED),
                new ArcaneBoost(),null,new ElementalCrest(), null,new Bonus(RealmColor.MAGENTA),null};   
            for(int i=0;i<templateRewards.length;i++)
                rewards[i]=templateRewards[i];

            String[]tempdrawRew=new String[]{"     ","     ","TW   ","     ","RB   ","AB   ","     ","EC   ","     ","MB   ","     "};
            for(int i=0;i<tempdrawRew.length;i++)
                drawRew[i]=tempdrawRew[i];
        }
    }    
    
    @Override
    public  Reward[] getReward() throws RewardException//gets Reward
    {
        if(!isRewardAvailable())
            throw new RewardException("There are no rewards available , an error occured in checking  in YellowRealm");

        int rewardPosition=getTotalNumberOfAttacks()-1;
        Reward[] temp= getRealmRewards();
        Reward reward= temp[rewardPosition];
        if(reward==null)
            throw new RewardException("Null reward found in YellowRealm");   

        drawRew[rewardPosition]="X    ";
        Reward[] result= new Reward[]{reward};
        rewardClaimed(rewardPosition);
        return result;
    }

    @Override
    public  Move[] getAllPossibleMoves() //returns all possible moves to the Player
    {
        if(!isRealmAccessible())
            return new Move[0];
        
        Move[] result= new Move[6];
        for (int i=0;i<result.length;i++)
        {
            Move[] temp=getPossibleMovesForADie(i+1, getRealmColor(),null);
            result[i]=temp[0];
        }
        return result;
    }

    @Override
    public Move[] getPossibleMovesForADie(int diceValue,RealmColor colorOfDice,Dice d) //return the move that's possible with the dice value, in this realm, every move is possible
    {
        if(!isRealmAccessible())
            return new Move[0];
            
        if(lion.checkPossibleAttack(diceValue))
        {
            YellowDice temp=new YellowDice(diceValue);
            Move[] move= new Move[]{new Move(temp, lion)};
            return  move;
        }
        return new Move[0];
        
    }

    @Override
    public String toString() //Prints scoresheet
    {
        String[] attackIndex=getPreviousAttacks();
        return 
        "Radiant Savanna: Solar Lion (YELLOW REALM):\n" +
                "+-----------------------------------------------------------------------+\n" +
                "|  #  |1    |2    |3    |4    |5    |6    |7    |8    |9    |10   |11   |\n" +
                "+-----------------------------------------------------------------------+\n" +
                "|  H  |"+attackIndex[0]+"|"+attackIndex[1]+"|"+attackIndex[2]+"|"+attackIndex[3]+"|"+attackIndex[4]+"|"+attackIndex[5]+"|"+attackIndex[6]+"|"+attackIndex[7]+"|"+attackIndex[8]+"|"+attackIndex[9]+"|"+attackIndex[10]+"|\n" +
                "|  M  |     |     |     |x2   |     |     |x2   |     |x2   |     |x3   |\n" +
                "|  R  |"+drawRew[0]+"|"+drawRew[1]+"|"+drawRew[2]+"|"+drawRew[3]+"|"+drawRew[4]+"|"+drawRew[5]+"|"+drawRew[6]+"|"+drawRew[7]+"|"+drawRew[8]+"|"+drawRew[9]+"|"+drawRew[10]+"|\n" +
                "+-----------------------------------------------------------------------+\n\n";
    }
    public Lion getLion(){
        return lion;
    }

    @Override
    public Creature getCreatureByRealm(Dice dice) {
        return lion;
    }
}
