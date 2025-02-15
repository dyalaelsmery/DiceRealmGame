package game.realms;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import game.collectibles.*;
import game.creatures.*;
import game.dice.*;
import game.engine.Move;
import game.exceptions.InvalidMoveException;
import game.exceptions.RewardException;



public class GreenRealm extends Realms{
    Gaia gaiaGurdian;
    private final int rewardColumn1;
    private final int rewardColumn2;
    private final int rewardColumn3;
    private final int rewardColumn4;
    private final int rewardRow1;
    private final int rewardRow2;
    private final int rewardRow3;
//=======================================Constructor===================================
    public GreenRealm(){
        super(RealmColor.GREEN,7,12);
        gaiaGurdian=new Gaia();
        rewardColumn1=0;
        rewardColumn2=1;
        rewardColumn3=2;
        rewardColumn4=3;
        rewardRow1=4;
        rewardRow2=5;
        rewardRow3=6;
    }
//=======================================Methods=======================================
    @Override
    public boolean attack(int diceValue, Creature creature,Dice dice) {
        try{
        if(isRealmDefeated())
            throw new InvalidMoveException("Green realm has been defeated and cannot be accessed");
        if(diceValue<=0)
            throw new InvalidMoveException("Dice value is <=0 attack failed");    
        if(gaiaGurdian.checkPossibleAttack(diceValue)){
            gaiaGurdian.killGaiaGurdian(diceValue);
            incrementTotalNumberOfAttacks();
            updateTotalRealmScore(getTotalNumberOfAttacks());
            recordAttack(diceValue);//the string thing
            if(isRealmDefeated())
                closeRealm();
            return true;
        }else{
            throw new InvalidMoveException("Dice Value is not enough to attack the gaia");
        }
        }catch(InvalidMoveException ex){
            System.out.println(ex.getMessage());
        }   
        return false;
    }
    @Override
    public void updateTotalRealmScore(int totalNumberOfAttacks) {
        switch(totalNumberOfAttacks){
            case 1:setTotalRealmScore(1);break;
            case 2:setTotalRealmScore(2);break;
            case 3:setTotalRealmScore(4);break;
            case 4:setTotalRealmScore(7);break;
            case 5:setTotalRealmScore(11);break;
            case 6:setTotalRealmScore(16);break;
            case 7:setTotalRealmScore(22);break;
            case 8:setTotalRealmScore(29);break;
            case 9:setTotalRealmScore(37);break;
            case 10:setTotalRealmScore(46);break;
            case 11:setTotalRealmScore(56);break;
        }
    }
    @Override
    public Move[] getAllPossibleMoves() {
        if(!isRealmAccessible())
            return new Move[0];

        boolean[] gurdiansHealth=gaiaGurdian.getGurdiansHealth();
        int moveArraySize=0;
        
        for(boolean gaiaAlive:gurdiansHealth)
            if(gaiaAlive)moveArraySize++;
        Move[] moves=new Move[moveArraySize];
        
        for(int i=2,arr=0;i<gurdiansHealth.length;i++)
            if(gurdiansHealth[i])
                moves[arr++]=getPossibleMovesForADie(i,RealmColor.GREEN,null)[0];
        return moves;
    }
    @Override
    public Move[] getPossibleMovesForADie(int diceValue,RealmColor colorOfDice,Dice d) {//Assumes FUll number Comes In
        if(!isRealmAccessible())
            return new Move[0];
        if(gaiaGurdian.checkPossibleAttack(diceValue)){
                 Dice tempDice=new GreenDice(diceValue);
            return new Move[]{new Move(tempDice,gaiaGurdian)};
        }
        return new Move[0];
    }
    @Override
    public boolean isRewardAvailable() {
        boolean[] gurdiansHealth=gaiaGurdian.getGurdiansHealth();
        Reward[] rewards=getRealmRewards();

        if(!(gurdiansHealth[5]||gurdiansHealth[9])&&rewards[rewardColumn1]!=null)
            return true;
        else if(!(gurdiansHealth[2]||gurdiansHealth[6]||gurdiansHealth[10])&&rewards[rewardColumn2]!=null)   
            return true;
        else if(!(gurdiansHealth[3]||gurdiansHealth[7]||gurdiansHealth[11])&&rewards[rewardColumn3]!=null)
            return true;
        else if(!(gurdiansHealth[4]||gurdiansHealth[8]||gurdiansHealth[12])&&rewards[rewardColumn4]!=null)      
            return true;
        else if(!(gurdiansHealth[2]||gurdiansHealth[3]||gurdiansHealth[4])&&rewards[rewardRow1]!=null)
            return true;
        else if(!(gurdiansHealth[5]||gurdiansHealth[6]||gurdiansHealth[7]||gurdiansHealth[8])&&rewards[rewardRow2]!=null)
            return true;
        else if(!(gurdiansHealth[9]||gurdiansHealth[10]||gurdiansHealth[11]||gurdiansHealth[12])&&rewards[rewardRow3]!=null)
            return true; 
       return false;                     
    }
    @Override
    public void initializePreviousAttacks(String[] previousAttacks) {
        previousAttacks[0]="X    ";
        for(int i=1;i<9;i++)
            previousAttacks[i]=(i+1)+"    ";
        previousAttacks[9]=10+"   ";
        previousAttacks[10]=11+"   ";
        previousAttacks[11]=12+"   ";      
    }
    @Override
    public void recordAttack(int diceValue){
        String[]previousAttacks=getPreviousAttacks();
        previousAttacks[diceValue-1]="X    ";

    }
    @Override
    public boolean isRealmDefeated() {
        return getTotalNumberOfAttacks()==11;
    }
    public  String[] getDrawRewards(){
        return drawRew;
    }

    @Override
    public Reward[] getReward() throws RewardException{
        
        boolean[] gurdiansHealth=gaiaGurdian.getGurdiansHealth();
        Reward[] rewards=getRealmRewards();
        ArrayList<Reward> outputRewards=new ArrayList<Reward>();

        if(!(gurdiansHealth[5]||gurdiansHealth[9])&&rewards[rewardColumn1]!=null){
            Reward recievedReward=rewards[rewardColumn1];
            drawRew[rewardColumn1]="X    ";
            rewardClaimed(rewardColumn1);
            outputRewards.add(recievedReward);
        }
        if(!(gurdiansHealth[2]||gurdiansHealth[6]||gurdiansHealth[10])&&rewards[rewardColumn2]!=null){
            Reward recievedReward=rewards[rewardColumn2];
            drawRew[rewardColumn2]="X    ";
            rewardClaimed(rewardColumn2);
            outputRewards.add(recievedReward);
        } 
        if(!(gurdiansHealth[3]||gurdiansHealth[7]||gurdiansHealth[11])&&rewards[rewardColumn3]!=null){
            Reward recievedReward=rewards[rewardColumn3];
            drawRew[rewardColumn3]="X    ";
            rewardClaimed(rewardColumn3);
            outputRewards.add(recievedReward);
        }
            
        if(!(gurdiansHealth[4]||gurdiansHealth[8]||gurdiansHealth[12])&&rewards[rewardColumn4]!=null){
            Reward recievedReward=rewards[rewardColumn4];
            drawRew[rewardColumn4]="X    ";
            rewardClaimed(rewardColumn4);
            outputRewards.add(recievedReward);
        }       
        if(!(gurdiansHealth[2]||gurdiansHealth[3]||gurdiansHealth[4])&&rewards[rewardRow1]!=null){
            Reward recievedReward=rewards[rewardRow1];
            drawRew[rewardRow1]="X    ";
            rewardClaimed(rewardRow1);
            outputRewards.add(recievedReward);
        }    
        if(!(gurdiansHealth[5]||gurdiansHealth[6]||gurdiansHealth[7]||gurdiansHealth[8])&&rewards[rewardRow2]!=null){
            Reward recievedReward=rewards[rewardRow2];
            drawRew[rewardRow2]="X    ";
            rewardClaimed(rewardRow2);
            outputRewards.add(recievedReward);

        }  
        if(!(gurdiansHealth[9]||gurdiansHealth[10]||gurdiansHealth[11]||gurdiansHealth[12])&&rewards[rewardRow3]!=null){
            Reward recievedReward=rewards[rewardRow3];
            drawRew[rewardRow3]="X    ";
            rewardClaimed(rewardRow3);
            outputRewards.add(recievedReward);
        }
        Reward[] rewardsOutput=new Reward[outputRewards.size()];
        for(int i=0;i<rewardsOutput.length;i++)
            rewardsOutput[i]=outputRewards.get(i);
        Arrays.sort(rewardsOutput);    //Sorting rewards
        return rewardsOutput;  
    }
    @Override
    public void setRealmRewards(Reward[] realmRewards) {
        //Gets the properties of a config file 
        try {
            String path = "src/main/resources/config/TerrasHeartLandRewards.properties";
            String[] rewardStrings=new String[realmRewards.length];
            int s=0;
            try (BufferedReader reader = new BufferedReader(new FileReader(path))){
                String line;
                line=reader.readLine();
                while (((line=reader.readLine())) != null) {
                    String[] splitter = line.split("=");
                    rewardStrings[s++]=splitter[1].trim();
                }
            String[]rewardString =new String[realmRewards.length];
            int x=0;
            for(int i=3;i<realmRewards.length;i++)
                rewardString[x++]=rewardStrings[i];
            for(int i=0;i<3;i++)
                rewardString[x++]=rewardStrings[i];    
            //Puts the bonuses read from the file into the realm rewards
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
            System.out.println("Error in loading property file Green realm, proceeding with the default values");
            Reward[] templateRewards=new Reward[]{new TimeWarp(),new Bonus(RealmColor.BLUE),new Bonus(RealmColor.MAGENTA),
                new ArcaneBoost(),new Bonus(RealmColor.YELLOW),new Bonus(RealmColor.RED),new ElementalCrest()};  

                for(int i=0;i<templateRewards.length;i++)
                    realmRewards[i]=templateRewards[i];
                    
            String[] tempDrawRew=new String[]{"TW   ","BB   ","MB   ","AB   ","YB   ","RB   ","EC   "};   
            for(int i=0;i<templateRewards.length;i++)
                    drawRew[i]=tempDrawRew[i];
            


        }
    }
    public  Creature getCreatureByRealm(Dice dice){
        return gaiaGurdian;
    }public Gaia getGaia(){
        return gaiaGurdian;
    }
    @Override
    public String toString() {
        String[] prevAt=getPreviousAttacks();//previousAttacks
        Reward[] rewards=getRealmRewards();
        if(rewards[rewardColumn1]==null&&!drawRew[0].equals("     "))
            drawRew[0]="X    ";
        if(rewards[rewardColumn2]==null&&!drawRew[1].equals("     "))
            drawRew[1]="X    ";
        if(rewards[rewardColumn3]==null&&!drawRew[2].equals("     "))
            drawRew[2]="X    ";     
        if(rewards[rewardColumn4]==null&&!drawRew[3].equals("     "))
            drawRew[3]="X    ";
        if(rewards[rewardRow1]==null&&!drawRew[4].equals("     "))
            drawRew[4]="X    ";
        if(rewards[rewardRow2]==null&&!drawRew[5].equals("     "))
            drawRew[5]="X    ";
        if(rewards[rewardRow3]==null&&!drawRew[6].equals("     "))
            drawRew[6]="X    ";
         
        return "Terra's Heartland: Gaia Guardians (GREEN REALM):"+"\n"+ 
        "+-----------------------------------+"+"\n"+
        "|  #  |1    |2    |3    |4    |R    |"+"\n"+
        "+-----------------------------------+"+"\n"+
        "|  1  |"+prevAt[0]+"|"+prevAt[1]+"|"+prevAt[2]+"|"+prevAt[3]+"|"+drawRew[4]+"|"+"\n"+
        "|  2  |"+prevAt[4]+"|"+prevAt[5]+"|"+prevAt[6]+"|"+prevAt[7]+"|"+drawRew[5]+"|"+"\n"+
        "|  3  |"+prevAt[8]+"|"+prevAt[9]+"|"+prevAt[10]+"|"+prevAt[11]+"|"+drawRew[6]+"|"+"\n"+
        "+-----------------------------------+"+"\n"+
        "|  R  |"+drawRew[0]+"|"+drawRew[1]+"|"+drawRew[2]+"|"+drawRew[3]+"|     |"+"\n"+
        "+-----------------------------------------------------------------------+"+"\n"+
        "|  S  |1    |2    |4    |7    |11   |16   |22   |29   |37   |46   |56   |"+"\n"+
        "+-----------------------------------------------------------------------+"+"\n\n";
    }
}
