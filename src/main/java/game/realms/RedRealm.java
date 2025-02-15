package game.realms;
import game.collectibles.*;
import game.creatures.Creature;
import game.creatures.Dragon;
import game.creatures.DragonNumber;
import game.dice.ArcanePrism;
import game.dice.Dice;
import game.dice.RedDice;
import game.engine.Move;
import game.exceptions.InvalidMoveException;
import game.exceptions.RewardException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;;

public class RedRealm extends Realms{
    private Dragon dragon1 = new Dragon(DragonNumber.Dragon1,3,2,1,0,10);
    private Dragon dragon2 = new Dragon(DragonNumber.Dragon2,6,1,0,3,14);
    private Dragon dragon3 = new Dragon(DragonNumber.Dragon3,5,0,2,4,16);
    private Dragon dragon4 = new Dragon(DragonNumber.Dragon4,0,5,4,6,20);
    private int maximumNumberOfAttacks = 12;
    private LinkedList <Reward> earnedRewards = new LinkedList<>();

    //============================Constructor============================================
    /**
     * Initializes the Red realm with the color, number of rewards in the realm and the maximum number of attacks.
     */
    public RedRealm() {
        super(RealmColor.RED, 5, 12);
    }

    //============================G&S============================================


    public Dragon getDragon1() {
        return dragon1;
    }

    public Dragon getDragon2() {
        return dragon2;
    }

    public Dragon getDragon3() {
        return dragon3;
    }

    public Dragon getDragon4() {
        return dragon4;
    }

    //============================Moves and Attack============================================

    @Override
    public boolean attack(int dice, Creature creature,Dice rdice) {
        boolean flag = true;
        try{
        if(isRealmDefeated())
            throw new InvalidMoveException("Red Realm has been defeated and cannot be accessed");    
        if(dice<=0)
            throw new InvalidMoveException("Dice Value is <=0 attack failed"); 
        Dragon dragon = (Dragon) creature;
        maximumNumberOfAttacks -= 1;
        

        int selectsDragon=0;
        if(rdice instanceof RedDice)
            selectsDragon=((RedDice)rdice).getselectsDragon();
        else
            selectsDragon=((ArcanePrism)rdice).getselectsDragon();

        if (dragon.getDragonNumber() == DragonNumber.Dragon1&&(selectsDragon==0||selectsDragon==-1||selectsDragon==1)){
            if(dragon1.isDeadDragon()){
                throw new InvalidMoveException("Dragon is dead!");
            }
            else {
                switch (dice) {
                    case 3:
                        dragon1.setHealth("Face");break;
                    case 2:
                        dragon1.setHealth("Wings");break;
                    case 1:
                        dragon1.setHealth("Tail");break;
                    default: //throws error
                }
            }
        }
        else if (dragon.getDragonNumber() == DragonNumber.Dragon2&&(selectsDragon==0||selectsDragon==-1||selectsDragon==2)){
            if(dragon2.isDeadDragon()){
                throw new InvalidMoveException("Dragon is dead!");
            }
            else{
                switch (dice) {
                case 6:
                    dragon2.setHealth("Face");break;
                case 1:
                    dragon2.setHealth("Wings");break;
                case 3:
                    dragon2.setHealth("Heart");break;
                default: //throws error
            }
            }
        }
        else if (dragon.getDragonNumber() == DragonNumber.Dragon3&&(selectsDragon==0||selectsDragon==-1||selectsDragon==3)){
            if(dragon3.isDeadDragon()){
                throw new InvalidMoveException("Dragon is dead!");
            }
            else {
                switch (dice) {
                    case 5:
                        dragon3.setHealth("Face");break;
                    case 2:
                        dragon3.setHealth("Tail");break;
                    case 4:
                        dragon3.setHealth("Heart");break;
                    default: //throws error
                }
            }
        }
        else if (dragon.getDragonNumber() == DragonNumber.Dragon4&&(selectsDragon==0||selectsDragon==-1||selectsDragon==4)){
            if(dragon4.isDeadDragon()){
                throw new InvalidMoveException("Dragon is dead!");
            }
            else {
                switch (dice) {
                    case 5:
                        dragon4.setHealth("Wings");break;
                    case 4:
                        dragon4.setHealth("Tail");break;
                    case 6:
                        dragon4.setHealth("Heart");break;
                    default: //throws error
                }
            }
        }
        else {
            throw new InvalidMoveException("Dice Value is not enough to attack  any of the Dragons");     
        }
        updateTotalRealmScore(dice);
        if(rdice instanceof RedDice)
            ((RedDice)rdice).selectsDragon(0);
        else
            ((ArcanePrism)rdice).selectsDragon(0);

        }catch(InvalidMoveException ex){
            flag=false;
            System.out.println(ex.getMessage());
        }
        return flag;
    }
    @Override
    public void initializePreviousAttacks(String[] previousAttacks) {
        //Implemented in another way
    }
    @Override
    public boolean isRealmDefeated() {
        return dragon1.isDeadDragon() && dragon2.isDeadDragon() && dragon3.isDeadDragon() && dragon4.isDeadDragon();
    }
    @Override
    public Move[] getAllPossibleMoves() {
        Move[] moves = new Move[maximumNumberOfAttacks];
        for(int i = 1, k = 0; i < 7; i++){
            Move[] temp = getPossibleMovesForADie(i, RealmColor.RED,null);
            for(Move x: temp)
                moves[k++] = x;
        }
        return moves;
    }
    @Override
    public Move[] getPossibleMovesForADie(int diceValue, RealmColor colorOfDice,Dice redDice) {
        Move move1 = null;
        Move move2 = null;
        Move move3 = null;
        Move move4 = null;
       
        int selectsDragon=-1;
        if(redDice!=null  && redDice instanceof RedDice){
            selectsDragon=((RedDice)redDice).getselectsDragon();
        }else{
            redDice=new RedDice(diceValue);
        }
        if(dragon1.valueInDragon(diceValue)&&(selectsDragon==0||selectsDragon==-1||selectsDragon==1)){
            move1 = new Move(redDice , dragon1);
        }
        if (dragon2.valueInDragon(diceValue)&&(selectsDragon==0||selectsDragon==-1||selectsDragon==2)) {
            move2 = new Move(redDice, dragon2);
        }
        if (dragon3.valueInDragon(diceValue)&&(selectsDragon==0||selectsDragon==-1||selectsDragon==3)) {
            move3 = new Move(redDice, dragon3);
        }
        if (dragon4.valueInDragon(diceValue)&&(selectsDragon==0||selectsDragon==-1||selectsDragon==4)) {
            move4 = new Move(redDice, dragon4);
        }
        int c = 0;
        if(move1 != null)
            c++;
        if(move2 != null)
            c++;
        if(move3 != null)
            c++;
        if(move4 != null)
            c++;

        Move[] moves = new Move[c];
        c=0;
        if(move1 != null)
            moves[c++] = move1;
        if(move2 != null)
            moves[c++] = move2;
        if(move3 != null)
            moves[c++] = move3;
        if(move4 != null)
            moves[c++] = move4;

        return moves;

    }
    //============================Rewards============================================
    /**
     * checks if a part is hit across all dragons which will result in a bonus accordingly.
     * @return {@code true} if region is hit,
     *         {@code false} otherwise.
     */
    public boolean isRegionHit(){
        boolean flag = false;
        Reward [] rewards = getRealmRewards();
        if (dragon1.isFaceKilled() && dragon2.isFaceKilled() && dragon3.isFaceKilled() && dragon4.isFaceKilled() && rewards[0] != null) {
            earnedRewards.add(rewards[0]);
            drawRew[0]="X    ";
            rewardClaimed(0);
            flag = true;
        }
        if (dragon1.isWingsKilled() && dragon2.isWingsKilled() && dragon3.isWingsKilled() && dragon4.isWingsKilled() && rewards[1] != null){
            earnedRewards.add(rewards[1]);
            drawRew[1]="X    ";
            rewardClaimed(1);
            flag = true;
        }
        if (dragon1.isTailKilled() && dragon2.isTailKilled() && dragon3.isTailKilled() && dragon4.isTailKilled() && rewards[2] != null){
            earnedRewards.add(rewards[2]);
            drawRew[2]="X    ";
            rewardClaimed(2);
            flag = true;
        }
        if (dragon1.isHeartKilled() && dragon2.isHeartKilled() && dragon3.isHeartKilled() && dragon4.isHeartKilled() && rewards[3] != null){
            earnedRewards.add(rewards[3]);
            drawRew[3]="X    ";
            rewardClaimed(3);
            flag = true;
        }
        return flag;
    }
    /**
     * checks if the diagonal parts across all dragons are hit which will result in a bonus accordingly.
     * @return {@code true} if diagonal parts are hit,
     *         {@code false} otherwise.
     */
    public boolean isDiagonalRegionHit(){
        Reward [] rewards = getRealmRewards();
        if(dragon1.isFaceKilled() && dragon2.isWingsKilled() && dragon3.isTailKilled() && dragon4.isHeartKilled() && rewards[4] != null){
            earnedRewards.add(rewards[4]);
            drawRew[4]="X    ";
            rewardClaimed(4);
            return true;
        }
        return false;
    }

    @Override
    public boolean isRewardAvailable() {
        return isDiagonalRegionHit() | isRegionHit();
    }
    @Override
    public Reward[] getReward() throws RewardException{
        if(earnedRewards.size()==0)
            throw new RewardException("There are no rewards available , an error occured in checking  in RedRealm");

        Reward[] giveRewards = new Reward[earnedRewards.size()];
        int c = 0;
        while (!earnedRewards.isEmpty()){
            giveRewards[c++] = earnedRewards.remove();
        }
        for(Reward x:giveRewards)
            if(x ==null)
                throw new RewardException("Null reward found  in RedRealm");
        Arrays.sort(giveRewards);
        return giveRewards;
    }



    
    @Override
    public void setRealmRewards(Reward[] realmRewards) {
        //Gets the properties of a config file
        try {
            String path = "src/main/resources/config/EmberfallDominionRewards.properties";
            String[] rewardString=new String[realmRewards.length];
            int s=0;
            try (BufferedReader reader = new BufferedReader(new FileReader(path))){
                String line;
                line=reader.readLine();
                while (((line=reader.readLine())) != null) {
                    String[] splitter = line.split("=");
                    rewardString[s++]=splitter[1].trim();
                }
            //Puts the bonuses read from the file into the realm rewards
            for (int i = 0; i <5; i++) {
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
            System.out.println("Error in loading property file Red realm, proceeding with the default values");
            Reward[] temp = new Reward[]{new Bonus(RealmColor.GREEN),new Bonus(RealmColor.YELLOW),new Bonus(RealmColor.BLUE), new ElementalCrest(), new ArcaneBoost()};
            for (int i = 0; i < temp.length ; i++) {
                realmRewards [i] = temp [i];
            }
        }
    }
    //============================toString============================================
    @Override
    public void updateTotalRealmScore(int value) {
        int points = 0;
        if(dragon1.isDeadDragon()){
            points += dragon1.getPoints();
        }
        if(dragon2.isDeadDragon()){
            points += dragon2.getPoints();
        }
        if(dragon3.isDeadDragon()){
            points += dragon3.getPoints();
        }
        if(dragon4.isDeadDragon()){
            points += dragon4.getPoints();
        }
        this.setTotalRealmScore(points);
    }
    @Override
    public String toString() {
        String[] rewardsString = drawRew;
        
        return String.format("Emberfall Dominion: Pyroclast Dragon (RED REALM):\n" +
                        "+-----------------------------------+\n" +
                        "|  #  |D1   |D2   |D3   |D4   |R    |\n" +
                        "+-----------------------------------+\n" +
                        "|  F  |%s    |%s    |%s    |X    |%s|\n" +
                        "|  W  |%s    |%s    |X    |%s    |%s|\n" +
                        "|  T  |%s    |X    |%s    |%s    |%s|\n" +
                        "|  H  |X    |%s    |%s    |%s    |%s|\n" +
                        "+-----------------------------------+\n" +
                        "|  S  |10   |14   |16   |20   |%s|\n" +
                        "+-----------------------------------+\n\n",
                dragon1.getFace() == 0? "X" : dragon1.getFace(), dragon2.getFace() == 0 ? "X" : dragon2.getFace(), dragon3.getFace() == 0 ? "X" : dragon3.getFace(), rewardsString[0],
                dragon1.getWings() == 0 ? "X" : dragon1.getWings(), dragon2.getWings() == 0 ? "X" : dragon2.getWings(), dragon4.getWings() == 0 ? "X" : dragon4.getWings(), rewardsString[1],
                dragon1.getTail() == 0 ? "X" : dragon1.getTail(), dragon3.getTail() == 0 ? "X" : dragon3.getTail(), dragon4.getTail() == 0 ? "X": dragon4.getTail(), rewardsString[2],
                dragon2.getHeart() == 0 ? "X" : dragon2.getHeart(), dragon3.getHeart() == 0 ? "X" : dragon3.getHeart(), dragon4.getHeart() == 0 ? "X": dragon4.getHeart(), rewardsString[3],rewardsString[4]);
    }
    public  Creature getCreatureByRealm(Dice dice){
        int dv=dice.getValue();
        Dragon[] temp=new Dragon[]{dragon1,dragon2,dragon3,dragon4};
        for(Dragon x:temp)
            if(x.getFace()==dv||x.getWings()==dv||x.getTail()==dv||x.getHeart()==dv)
                return x;
        return null;
    }


    @Override
    public String[] getDrawRewards() {
      return drawRew;
    }
}

