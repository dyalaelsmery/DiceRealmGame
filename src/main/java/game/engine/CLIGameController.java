package game.engine;
import game.dice.*;
import game.exceptions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import game.collectibles.*;
import game.realms.*;
import game.creatures.*;
public class CLIGameController extends GameController{
    private GameBoard gameBoard;
    private Scanner sc;
    private static final String ANSI_RED="\u001B[31m";
    private static final String ANSI_GREEN="\u001B[32m";
    private static final String ANSI_BLUE="\u001B[34m";
    private static final String ANSI_YELLOW="\u001B[33m";
    private static final String ANSI_MAGENTA="\u001B[35m";
    private static final String ANSI_CROSS="\u001B[9m";
    private static final String ANSI_RESET="\u001B[0m";
    private String[] roundRewards;
//=======================================Constructor===================================    
    public CLIGameController(){
        gameBoard=new GameBoard();
        sc=new Scanner(System.in);
        roundRewards=new String[6];
        setRoundRewards();
    }
//=======================================GameFlow======================================
    public void startGame(){
        //Delay to Show Game Name
        createDelay();
        createDelay();
        //Enter Player Name
        selectPlayerName(getActivePlayer(), 1);
        selectPlayerName(getPassivePlayer(), 2);
        while(!getGameStatus().isGameFinished()){
            //Displaying ActivePlayer Name and Round Number,Grimore
            Player activePlayer=getActivePlayer();
            System.out.println("Round: "+getGameStatus().getGameRound());
            System.out.println(activePlayer.getPlayerName()+"'s Turn!!!");
            createDelay();
            displayPlayerGrimore(activePlayer);
            checkRoundReward(activePlayer);
            //Turn Loop
            while(getGameStatus().getTurn()<=3){
                System.out.println("Turn: "+getGameStatus().getTurn());
                createDelay();
                //Rolling Dice Ending if no more dice Avaiable
                System.out.println("Rolling dice......");
                rollDice();
                createDelay();
                if(getAvailableDice().length==0){
                    System.out.println("No available  dice found ending turn");
                    createDelay();
                    break;
                }
                //Display Dice and promting for TimeWarp
                displayAvailableDice();
                displayTimeWarpPrompt(activePlayer);

                //No possible moves for dice
                if(getPossibleMovesForAvailableDice(activePlayer).length==0){
                    System.out.println("All Dice have no possible moves skipping turn");
                }else{
                //Selecting dice
                Dice selectedDice=selectDiceSequence(activePlayer);
                //Send Remaning Dice to forgotten Realm
                selectDice(selectedDice, activePlayer);
                System.out.println("Sending remaining Dice to forgoten realm.....");
                createDelay();
                displayForgottenRealmDice();
                //attacking
                attackSequence(activePlayer,selectedDice);
                //Display Score Sheet after each turn
                displayPlayerGrimore(activePlayer);}
                getGameStatus().incrementTurn();
            }
            passivePlayerSequence(getPassivePlayer());
            switchPlayer();
            resetDiceStatus(); 
            getGameStatus().incrementRound();
        }
        System.out.println(printFinalScore(getGameBoard().getPlayer1(),getGameBoard().getPlayer2()));
        if(getGameScore(getActivePlayer()).getTotalGameScore()>getGameScore(getPassivePlayer()).getTotalGameScore())
        System.out.println(getActivePlayer().getPlayerName()+" Wins!!!");
        else if(getGameScore(getActivePlayer()).getTotalGameScore()<getGameScore(getPassivePlayer()).getTotalGameScore())
        System.out.println(getPassivePlayer().getPlayerName()+" Wins!!!");
        else
            System.out.println("Its a TIE!");     
        System.out.println("Thanks for playing !!!");    
    }
    public String printFinalScore(Player player1,Player player2){
      String[] player1Data=getPlayerData(player1);
      String[] player2Data=getPlayerData(player2);
      System.out.println(player1.getPlayerName()+"'s Grimore: ");
      System.out.println(player1.getScoreSheet());
      System.out.println("=========================================");
      System.out.println(player2.getPlayerName()+"'s Grimore:");
      System.out.println(player2.getScoreSheet());
      System.out.println("=========================================");

      player2.getScoreSheet();
       return   "+---------------------------------------------+\n"+
                "|  #  	      |"+player1Data[0]+"|"+player2Data[0]+"|\n"+
                "+-------------+-------------------------------+\n"+
                "|\u001B[31mRedRealm\u001B[0m     |"+player1Data[1]+"|"+player2Data[1]+"|\n"+
                "|\u001B[32mGreenRealm\u001B[0m   |"+player1Data[2]+"|"+player2Data[2]+"|\n"+
                "|\u001B[34mBlueRealm\u001B[0m    |"+player1Data[3]+"|"+player2Data[3]+"|\n"+
                "|\u001B[35mMagentaRealm\u001B[0m |"+player1Data[4]+"|"+player2Data[4]+"|\n"+
                "|\u001B[33mYellowRealm\u001B[0m  |"+player1Data[5]+"|"+player2Data[5]+"|\n"+
                "+-------------+---------------+---------------+\n"+
                "|CrestsScore  |"+player1Data[6]+"|"+player2Data[6]+"|\n"+
                "+-------------+---------------+---------------+\n"+
                "|TotalScore   |"+player1Data[7]+"|"+player2Data[7]+"|\n"+
                "+-------------+---------------+---------------+ \n";
    }
    public String[] getPlayerData(Player player){
        String[] playerScore=new String[8];
        player.updateGameScore();
        GameScore gameScore=player.getGameScore();
        playerScore[0]=spaceGenerator(player.getPlayerName());
        playerScore[1]=spaceGenerator(""+gameScore.getRedRealmScore());
        playerScore[2]=spaceGenerator(""+gameScore.getGreenRealmScore());
        playerScore[3]=spaceGenerator(""+gameScore.getBlueRealmScore());
        playerScore[4]=spaceGenerator(""+gameScore.getMagentaRealmScore());
        playerScore[5]=spaceGenerator(""+gameScore.getYellowRealmScore());
        playerScore[6]=spaceGenerator(""+gameScore.getCrestScore());
        playerScore[7]=spaceGenerator(""+gameScore.getTotalGameScore());
        return playerScore;
    }
    public String spaceGenerator(String x){
        while(x.length()!=15)
            x+=" ";
        return x;    
    }
    public void printWithColor(RealmColor color,String text,boolean cross){
        if(cross){
            System.out.print(ANSI_CROSS+text+ANSI_RESET);return;}
        switch (color) {
            case RED: System.out.print(ANSI_RED+text+ANSI_RESET);break;
            case GREEN:System.out.print(ANSI_GREEN+text+ANSI_RESET);break;
            case BLUE:System.out.print(ANSI_BLUE+text+ANSI_RESET);break;
            case MAGENTA:System.out.print(ANSI_MAGENTA+text+ANSI_RESET);break;
            case YELLOW:System.out.print(ANSI_YELLOW+text+ANSI_RESET);break;
            case WHITE:System.out.print(text);break;
        }
    }
    public void createDelay(){
        try{
            Thread.sleep(1500);
        }
        catch(Exception ex){
            System.out.println("Error in Thread Sleep");
            ex.printStackTrace();}
    }
    public void setRoundRewards() {
        //Gets the properties of a config file
        try {
            String path = "src/main/resources/config/RoundsRewards.properties";
            int s=0;
            try (BufferedReader reader = new BufferedReader(new FileReader(path))){
                String line;
                line=reader.readLine();
                while (((line=reader.readLine())) != null) {
                    String[] splitter = line.split("=");
                    roundRewards[s++]=splitter[1].trim();
                }
            for (String reward:roundRewards) {
                switch (reward) {
                    case "RedBonus":    
                    case "GreenBonus":
                    case "BlueBonus":
                    case "MagentaBonus":
                    case "YellowBonus":
                    case "EssenceBonus":
                    case "ElementalCrest":
                    case "ArcaneBoost":
                    case "TimeWarp":
                    case "null":break;
                    default: throw new RewardException();    
                }
            }
        }
        }catch (Exception e){
            // if file not found or error in it proceed with default values
            System.out.println("Error in loading property file Round rewards, proceeding with the default values");
            String[] templateRewards=new String[]{"TimeWarp","ArcaneBoost","TimeWarp","EssenceBonus","null","null"}; 
            for(int i=0;i<templateRewards.length;i++)
                roundRewards[i]=templateRewards[i];
        }
    } 
    public void checkRoundReward(Player player){
            String reward =roundRewards[(getGameStatus().getGameRound())-1];
            switch (reward) {
                case "RedBonus":    
                    useColorBonusPrompt(player,new Bonus(RealmColor.RED));
                    break;
                case "GreenBonus":
                    useColorBonusPrompt(player,new Bonus(RealmColor.GREEN));
                break;
                case "BlueBonus":
                    useColorBonusPrompt(player,new Bonus(RealmColor.BLUE));
                    break;
                case "MagentaBonus":
                    useColorBonusPrompt(player,new Bonus(RealmColor.MAGENTA));
                    break;
                case "YellowBonus":
                    useColorBonusPrompt(player,new Bonus(RealmColor.YELLOW));
                    break;
                case "EssenceBonus":
                    displaySelectEssenceBonusColorPromt(player,new EssenceBonus());
                    break;
                case "ElementalCrest":
                    System.out.println("You got an Elemental Crest!");
                    player.addElementalCrest(new ElementalCrest());
                    break;
                case "ArcaneBoost":
                    System.out.println("You got an Arcane Boost!");
                    player.addArcaneBoost(new ArcaneBoost());
                    break;
                case "TimeWarp":
                    System.out.println("You got a TimeWarp!");
                    player.addTimeWarp(new TimeWarp());
                    break;         
                case "null":break;
        }
    }
    public void selectPlayerName(Player player,int playerNumber){
        String playerName="";
        while(playerName.isEmpty()){//While loop till user enters valid name
        try{
        System.out.println("Player"+playerNumber+" Enter Your Name:");    
        System.out.print("Name:");
        playerName=sc.next().trim();
        if(playerName.isEmpty())
            throw new CommandFormatException("Player name cannot be blank ");
        if(playerName.toUpperCase().charAt(0)<'A'||playerName.toUpperCase().charAt(0)>'Z'){
            playerName="";
            throw new CommandFormatException("Player name cannot start with a special character!");
        }
        if(playerName.length()>14)
            throw new CommandFormatException("Player Name Cannot Exceed 14 characters");
        }catch(CommandFormatException ex){
            System.out.println(ex.getMessage());
        }
        }
        player.setPlayerName(playerName);
    }
    public void displayPlayerGrimore(Player player){
        System.out.println("Printing "+player.getPlayerName()+"'s Grimore");
        createDelay();
        System.out.println(getScoreSheet(player));
        System.out.println("Type anything to continue:");
        sc.next();
    }
    public void displayAvailableDice(){
        Dice[]availableDice=getAvailableDice();
        for(int i=1;i<=availableDice.length;i++){
            String text="("+i+") "+availableDice[i-1]+"  ";
            printWithColor(availableDice[i-1].getDiceColor(), text,
            getPossibleMovesForADie(getActivePlayer(),availableDice[i-1]).length==0);
        }
        System.out.println();
    }
    public void displayTimeWarpPrompt(Player player){
        //Not Prompting if no TimeWarps Available
        if(player.getTimeWarps().size()==0)
            return;
        while(player.getTimeWarps().size()>0){ 
        try{
        System.out.println("Would you like to use a TimeWarp?"); 
        System.out.println("You have x"+player.getTimeWarps().size()+" TimeWarps remaining");
        if(getPossibleMovesForAvailableDice(player).length==0)
            System.out.println("Please note that all dice have no possible moves");
        System.out.println("Enter YES or NO");
        System.out.print("Choice: ");
        String choice=""+sc.next().trim().toUpperCase().charAt(0);
        if(choice.equals("Y"))
            useTimeWarp(player);
        else if(choice.equals("N"))
            return;
        else
            throw new CommandFormatException("Invalid input please enter YES or No!!");       
        }catch(CommandFormatException ex){
            System.out.println(ex.getMessage());
        }
    }
        //If player used all TimeWarps
        if(player.getTimeWarps().size()==0)
            System.out.println("No more TimeWarps Avaialble");
    }
    public void useTimeWarp(Player player){
        try{
        player.removeTimeWarp();
        System.out.println("TimeWarp power activated!");
        System.out.println("Rerolling dice......");
        rollDice();
        createDelay();
        displayAvailableDice();
        }catch(ExhaustedResourceException ex){
            System.out.println(ex.getMessage());
        }
    }
    public Dice selectDiceSequence(Player player){
         //First select dice
         Dice selectedDice;
         int selectedDiceIndex=displaySelectDicePromt(player)-1;//-1 Because Prompt starts from 1
         if(player.getPlayerStatus()==PlayerStatus.ACTIVE){//Active
             selectedDice=getAvailableDice()[selectedDiceIndex];
         }
         else{//Passive
            selectedDice=getForgottenRealmDice()[selectedDiceIndex];
         }
         //Prompt Player to choose Arcane Dice color if chosen
         RealmColor selectedDiceColor=selectedDice.getDiceColor();    
         if(selectedDiceColor==RealmColor.WHITE){
             displayChooseArcaneDiceColorPrompt(selectedDice,player);
             selectedDiceColor=((ArcanePrism)selectedDice).getChosenColor(); 
         }
         return selectedDice;
    }
    public void attackSequence(Player player,Dice selectedDice){
        //White
        RealmColor selectedDiceColor=selectedDice.getDiceColor();    
         if(selectedDiceColor==RealmColor.WHITE)
             selectedDiceColor=((ArcanePrism)selectedDice).getChosenColor(); 
         
        //Select Creature
        Creature selectedCreature=null;//to initialize
        if(selectedDice.getDiceStatus()==DiceStatus.POWER_SELECTED
        &&selectedDiceColor==RealmColor.RED
        &&(((selectedDice instanceof RedDice)&&(((RedDice)selectedDice).getselectsDragon()!=-1))))
        {//-1 To prevent ArcaneBoost from entering the bonus as dragon selection is different user chooses in bonus
            RedRealm redRealm=player.getRedRealm();
            RedDice bonusDice=(RedDice)selectedDice;
            int dragonNumber=bonusDice.getselectsDragon();
            System.out.print("Attacking ");
            printWithColor(RealmColor.RED, "Dragon"+dragonNumber, false);
            System.out.print(".....");
            System.out.println();
            createDelay();
            try{
            switch(dragonNumber){
                case 1:selectedCreature= redRealm.getDragon1();break;
                case 2:selectedCreature= redRealm.getDragon2();break;
                case 3:selectedCreature= redRealm.getDragon3();break;
                case 4:selectedCreature= redRealm.getDragon4();break;
                default:throw new PlayerActionException("An error occured in dragon selection ending attack sequence");
            }
            }catch(PlayerActionException ex){
                System.out.println(ex.getMessage());
                return;
            }
        }
        else
        try{
        selectedCreature=selectCreatureToAttack(player, selectedDiceColor,selectedDice.getValue());
        }catch(InvalidDiceSelectionException ex){
            System.out.println(ex.getMessage());
        }
        catch(PlayerActionException ex){
            System.out.println(ex.getMessage());
        }
        //Make a move
        boolean isMoveSuccessful=makeMove(player,new Move(selectedDice, selectedCreature));
        if(isMoveSuccessful)
            System.out.println("Attack was successfull!!");
        //If attack failed it is already printed in try catch in attack method
    }
    public int displaySelectDicePromt(Player player){
        Dice[]availableDice;
        if(player.getPlayerStatus()==PlayerStatus.ACTIVE)
            availableDice=getAvailableDice();
        else
            availableDice=getForgottenRealmDice();

        //Check if user can bypass as no moves work
        boolean bypass=false;
        for(int i=0;i<availableDice.length;i++)
            if(getPossibleMovesForADie(player, availableDice[i]).length>0)break;
            else if(i==availableDice.length-1)bypass=true;
        if(bypass)
            System.out.println("All dice have no possible moves");    
        


        while(true){
            System.out.println("Choose a number to select a dice");
            System.out.print("Choice: ");
            try{
                int choice=Integer.parseInt(sc.next().trim());
                if(1<=choice&&choice<=availableDice.length){
                    Dice selectedDice;
                    if(player.getPlayerStatus()==PlayerStatus.ACTIVE){//Active
                        selectedDice=getAvailableDice()[choice-1];
                    }
                    else{//Passive
                       selectedDice=getForgottenRealmDice()[choice-1];
                    }
                    if(getPossibleMovesForADie(player, selectedDice).length==0&&!bypass)
                        throw new InvalidDiceSelectionException("No Possible attacks for this die please choose another");
                    else    
                    return choice;
                }else
                    throw new CommandFormatException("Please choose a number between 1 and "+(availableDice.length)+"!!");
    
            }catch(InvalidDiceSelectionException ex){
                System.out.println(ex.getMessage());
            }catch(CommandFormatException ex){
                System.out.println(ex.getMessage());
            }
            catch(Exception ex){
                System.out.println("Please enter a valid number");
            }
        }
    }


    public void displayForgottenRealmDice(){
        System.out.println("Forgotten Realm Dice:");
        Dice[]forgottenDice=getForgottenRealmDice();
        if(forgottenDice.length==0){
            System.out.println("No dice in Forgotten Realm");
            return;
        }
        for(int i=1;i<=forgottenDice.length;i++){
            String text="("+i+") "+forgottenDice[i-1]+"  ";
            printWithColor(forgottenDice[i-1].getDiceColor(), text,
            getPossibleMovesForADie(getPassivePlayer(),forgottenDice[i-1]).length==0);
        }
        System.out.println();
    }
    public void displayChooseArcaneDiceColorPrompt(Dice dice,Player player){
        ArcanePrism whiteDice=gameBoard.getArcanePrism();
        //To check realm is accessible before attacking 
        boolean red=player.getRedRealm().getPossibleMovesForADie(dice.getValue(),RealmColor.RED,null).length>0;
        boolean blue=player.getBlueRealm().getPossibleMovesForADie(dice.getValue(),RealmColor.BLUE,null).length>0;

        int greenDiceValue=getGameBoard().getGreenDice().getValue()+dice.getValue();
        boolean green=player.getGreenRealm().getPossibleMovesForADie(greenDiceValue,RealmColor.GREEN,null).length>0;


        boolean magenta=player.getMagentaRealm().getPossibleMovesForADie(dice.getValue(),RealmColor.MAGENTA,null).length>0;
        boolean yellow=player.getYellowRealm().getPossibleMovesForADie(dice.getValue(),RealmColor.YELLOW,null).length>0;
        boolean bypass=false;

        System.out.println("Choose ArcanePrism color: ");
        if(!red&&!green&&!blue&&!magenta&&!yellow){
            bypass=true;
            System.out.println("Choose any color all colors have no moves");
        }
        //Printing dice with color and crossed if not avaialble
        printWithColor(RealmColor.RED, "Red ", !red);
        printWithColor(RealmColor.GREEN,"Green ", !green);
        printWithColor(RealmColor.BLUE,"Blue ", !blue);
        printWithColor(RealmColor.MAGENTA,"Magenta ", !magenta);
        printWithColor(RealmColor.YELLOW,"Yellow ",!yellow);
        System.out.println();

        while(true){
            try{
            System.out.print("Choice: ");
            String choice=sc.next().trim().toUpperCase();
            if(!choice.isEmpty()){
                switch (choice.charAt(0)) {
                    case 'R':
                    if(bypass||red){
                    whiteDice.setChosenColor(RealmColor.RED);return;}
                    else
                    throw new PlayerActionException("The Dragons cannot be attacked");
                    case 'G':
                    if(bypass||green){
                    whiteDice.setChosenColor(RealmColor.GREEN);return;}
                    else
                    throw new PlayerActionException("The Gurdians cannot be attacked");
                    case 'B':
                    if(bypass||blue){
                    whiteDice.setChosenColor(RealmColor.BLUE);return;}
                    else
                    throw new PlayerActionException("The Hydra cannot be attacked");
                    case 'M':
                    if(bypass||magenta){
                    whiteDice.setChosenColor(RealmColor.MAGENTA);return;}
                    else
                    throw new PlayerActionException("The Phoenix cannot be attacked");
                    case 'Y':
                    if(bypass||yellow){
                    whiteDice.setChosenColor(RealmColor.YELLOW);return;}
                    else
                    throw new PlayerActionException("The Lion cannot be attacked");
                    default:
                    throw new CommandFormatException("Please Enter A valid Color");
                }
            }
            else throw new CommandFormatException("Choice cannot be blank");
        }catch(CommandFormatException ex){
            System.out.println(ex.getMessage());
        }catch(PlayerActionException ex){
            System.out.println(ex.getMessage());
        }
        }
    }
    public Creature selectCreatureToAttack(Player player,RealmColor color,int diceValue)throws PlayerActionException,InvalidDiceSelectionException{
        switch (color) {
            case RED:return displaySelectDragonPrompt(player,diceValue);
            case GREEN:
            System.out.println("Attacking Gaia Guardian....");
            createDelay();
            return player.getGreenRealm().getGaia();
            case BLUE:
            System.out.println("Attacking Hydra....");
            createDelay();
            return player.getBlueRealm().getHydra();
            case MAGENTA:
            System.out.println("Attacking Phoenix....");
            createDelay();
            return player.getMagentaRealm().getPhoenix();
            case YELLOW:
            System.out.println("Attacking Lion....");
            createDelay();
            return player.getYellowRealm().getLion();
            case WHITE:throw new InvalidDiceSelectionException("Error occured White color Selection wasnt successful");  
        }
        throw new PlayerActionException("Creature was null and cant be attacked ending attack sequence");
    }
    public Creature displaySelectDragonPrompt(Player player,int diceValue) throws InvalidDiceSelectionException{
        RedRealm redRealm=player.getRedRealm();
        Dragon dragon1=redRealm.getDragon1();
        Dragon dragon2=redRealm.getDragon2();
        Dragon dragon3=redRealm.getDragon3();
        Dragon dragon4=redRealm.getDragon4();
        System.out.println("Choose a  dragon to attack:");

        int choice=0;
        int possibleSelections=1;
        //Only Printing dragons that can be attacked
        if(dragon1.checkPossibleAttack(diceValue))
            printWithColor(RealmColor.RED,"("+(possibleSelections++)+") Dragon1 ", false);
        if(dragon2.checkPossibleAttack(diceValue))
            printWithColor(RealmColor.RED,"("+(possibleSelections++)+") Dragon2 ", false);
        if(dragon3.checkPossibleAttack(diceValue))
            printWithColor(RealmColor.RED,"("+(possibleSelections++)+") Dragon3 ", false);    
        if(dragon4.checkPossibleAttack(diceValue))
            printWithColor(RealmColor.RED,"("+(possibleSelections++)+") Dragon4 ", false);
        //No dragon can be attacked
        if(!dragon1.checkPossibleAttack(diceValue)&&!dragon2.checkPossibleAttack(diceValue)
        &&!dragon3.checkPossibleAttack(diceValue)&&!dragon4.checkPossibleAttack(diceValue)) {
            throw new InvalidDiceSelectionException("all dragons cant be attacked by dice");
        }      
        //User choosing
        System.out.println();
        while(true){
            try{
            System.out.print("Choice: ");
            choice=Integer.parseInt(sc.next().trim());
            if(choice>=1&&choice<=possibleSelections-1)
                break;
            throw new CommandFormatException("Please choose a number within the range");
            }catch(CommandFormatException ex){
                System.out.println(ex.getMessage());
            }catch(Exception ex){
                System.out.println("Please enter a valid number");
            }
        }
        //finding to get the dragon selected
        int indexSoFar=1;
        boolean found=false;
        if(dragon1.checkPossibleAttack(diceValue)){
            if(indexSoFar++==choice&&!found){
                choice=1;  
                found=true;}
        }
        if(dragon2.checkPossibleAttack(diceValue)){
            if(indexSoFar++==choice&&!found){
                choice=2;  
                found=true;}
        }
        if(dragon3.checkPossibleAttack(diceValue)){
            if(indexSoFar++==choice&&!found){
                choice=3;  
                found=true;}
        }
        if(dragon4.checkPossibleAttack(diceValue)){
            if(indexSoFar++==choice&&!found){
                found=true;
                choice=4;  }
        }

        gameBoard.getRedDice().selectsDragon(choice);
        System.out.println("Attacking Dragon"+choice+".....");
        createDelay();
        switch(choice){
            case 1:return redRealm.getDragon1();
            case 2:return redRealm.getDragon2();
            case 3:return redRealm.getDragon3();
            case 4:return redRealm.getDragon4();
            default:System.out.println("An error occured in dragon selection");return null;
        }
    }
    public void checkForPossibleRewards(Player player,RealmColor diceColor){
        //Checking which realm was attacked to receive rewards from
        Realms checkInRealm=null;
        switch(diceColor){
            case RED:checkInRealm=player.getRedRealm();break;
            case BLUE:checkInRealm=player.getBlueRealm();break;
            case GREEN:checkInRealm=player.getGreenRealm();break;
            case YELLOW:checkInRealm=player.getYellowRealm();break;
            case MAGENTA:checkInRealm=player.getMagentaRealm();break;
            case WHITE:System.out.println("Error checking reward in white realm");
        }
        if(!checkInRealm.isRewardAvailable())
            return;
        Reward[]allRewards=null;    
        try{    
        allRewards=checkInRealm.getReward();
        }catch(RewardException ex){
            System.out.println(ex.getMessage());
            return;
        }
        for(int i=0;i<allRewards.length;i++){//Reward array for multiple cases of rewards
            Reward earnedReward=allRewards[i];
            if(earnedReward==null)
                continue;
            switch(earnedReward.getRewardType()){
                case CREST:
                System.out.println("You earned an ElementalCrest!!");
                player.addElementalCrest((ElementalCrest)earnedReward);
                break;
                case POWER:
                if(earnedReward instanceof TimeWarp){
                    System.out.println("You earned a TimeWarp power");
                    player.addTimeWarp((TimeWarp)earnedReward);
                }
                else if(earnedReward instanceof ArcaneBoost){
                    System.out.println("You earned an ArcaneBoost power");  
                    player.addArcaneBoost((ArcaneBoost)earnedReward);
                }
                else
                    System.out.println("An error has occured in Power Rewards?");
                
                break;
                case BONUS:
                if(earnedReward instanceof EssenceBonus)
                    displaySelectEssenceBonusColorPromt(player,(EssenceBonus)earnedReward);
                else if(earnedReward instanceof Bonus)
                    useColorBonusPrompt(player,(Bonus)earnedReward);    
                else
                    System.out.println("An error has occured in Bonus Rewards?");
            }
        }  
    }
    public void displaySelectEssenceBonusColorPromt(Player player,EssenceBonus bonus){
        System.out.println("You earned an EssenceBonus Choose which realm to attack ");
        boolean red=player.getRedRealm().isRealmAccessible();
        boolean blue=player.getBlueRealm().isRealmAccessible();
        boolean green=player.getGreenRealm().isRealmAccessible();
        boolean magenta=player.getMagentaRealm().isRealmAccessible();
        boolean yellow=player.getYellowRealm().isRealmAccessible();
        
        if(!red&&!green&&!blue&&!magenta&&!yellow){
            System.out.println("All Realms have been defeated bonus lost");
            return;
        }
        //Printing the realms with color and crossed if not avaiable 
        printWithColor(RealmColor.RED, "Red ", !red);
        printWithColor(RealmColor.GREEN,"Green ", !green);
        printWithColor(RealmColor.BLUE,"Blue ", !blue);
        printWithColor(RealmColor.MAGENTA,"Magenta ", !magenta);
        printWithColor(RealmColor.YELLOW,"Yellow ", !yellow);
        System.out.println();
        //Selecting Color that only is avaialbe
         while(true){
            System.out.print("Choice: ");
            String choice=sc.next().trim().toUpperCase();
            try{
            if(!choice.isEmpty()){
                switch (choice.charAt(0)) {
                    case 'R':
                    if(red){
                    bonus.setEssenceBonusColor(RealmColor.RED);break;}
                    else
                    throw new PlayerActionException("The Dragons cannot be attacked");
                    case 'G':
                    if(green){
                    bonus.setEssenceBonusColor(RealmColor.GREEN);break;}
                    else
                    throw new PlayerActionException("The Gurdians cannot be attacked");
                    case 'B':
                    if(blue){
                    bonus.setEssenceBonusColor(RealmColor.BLUE);break;}
                    else
                    throw new PlayerActionException("The Hydra cannot be attacked");
                    case 'M':
                    if(magenta){
                    bonus.setEssenceBonusColor(RealmColor.MAGENTA);break;}
                    else
                    throw new PlayerActionException("The Phoenix has cannot be attacked");
                    case 'Y':
                    if(yellow){
                    bonus.setEssenceBonusColor(RealmColor.YELLOW);break;}
                    else
                    throw new PlayerActionException("The Lion cannot be attacked");
                    default:throw new CommandFormatException("Please Enter A valid Color");
                }
                if(bonus.getBonusColor()!=RealmColor.WHITE){
                useColorBonusPrompt(player, bonus);
                break;
                }
            }
            else
            throw new CommandFormatException("Choice cannot be blank");   
        }catch(CommandFormatException ex){
            System.out.println(ex.getMessage());

        }catch(PlayerActionException ex){
            System.out.println(ex.getMessage());
        }
        }
    }
    public void useColorBonusPrompt(Player player,Bonus bonus){
        if(!(bonus instanceof EssenceBonus))
            System.out.println("You earned a "+bonus.getBonusColor()+" Color Bonus!!");
        //Making sure not all realms closed
        boolean red=player.getRedRealm().isRealmAccessible();
        boolean blue=player.getRedRealm().isRealmAccessible();
        boolean green=player.getRedRealm().isRealmAccessible();
        boolean magenta=player.getRedRealm().isRealmAccessible();
        boolean yellow=player.getRedRealm().isRealmAccessible();

        Dice diceToBeUsed=null;
        try{ 
        if(!red&&!green&&!blue&&!magenta&&!yellow){
            throw new RewardException("All Realms have no moves Essence bonus lost");
        }    

   
        switch(bonus.getBonusColor()){
            case RED:
            if(red){
            diceToBeUsed=selectRedColorBonusDragon(player);break;}
            else{
                throw new RewardException("RedRealm has been defeated Bonus lost");
            }
            case GREEN:
            if(green){
            diceToBeUsed=selectGreenColorBonusGaia(player);break;
            }else{
                throw new RewardException("GreenRealm has been defeated Bonus lost");
            }
            case BLUE:
            if(blue){
            BlueDice blueDice=new BlueDice(6);
            blueDice.setDiceStatus(DiceStatus.POWER_SELECTED);
            diceToBeUsed=blueDice;break;
            }else{
                throw new RewardException("BlueRealm has been defeated Bonus lost");
            }
            case MAGENTA:
            if(magenta){
            MagentaDice magentaDice=new MagentaDice(6);
            magentaDice.setDiceStatus(DiceStatus.POWER_SELECTED);
            diceToBeUsed=magentaDice;break;
            }else{
                throw new RewardException("MagentaRealm has been defeated Bonus lost");
            }
            case YELLOW:
            if(yellow){
            YellowDice yellowDice=new YellowDice(6);
            yellowDice.setDiceStatus(DiceStatus.POWER_SELECTED);
            diceToBeUsed=yellowDice;break;
            }else{
                throw new RewardException("YellowRealm has been defeated Bonus lost");
            }
            case WHITE:
            throw new RewardException("An error has occured in Essence color selection");
        }
        }catch(RewardException ex){
            System.out.println(ex.getMessage());
            return;
        }      
        attackSequence(player, diceToBeUsed);
    }
    public Dice selectRedColorBonusDragon(Player player){
        //Choosing which dragon
        RedRealm redRealm=player.getRedRealm();
        Dragon dragon1=redRealm.getDragon1();
        Dragon dragon2=redRealm.getDragon2();
        Dragon dragon3=redRealm.getDragon3();
        Dragon dragon4=redRealm.getDragon4();

        int choice=0;
        int possibleSelections=1;
        //Making sure only dragons that can be attack can be chosen
        if(!dragon1.isDeadDragon())
        printWithColor(RealmColor.RED,"("+(possibleSelections++)+") Dragon1 ", false);
        if(!dragon2.isDeadDragon())
            printWithColor(RealmColor.RED,"("+(possibleSelections++)+") Dragon2 ", false);
        if(!dragon3.isDeadDragon())
            printWithColor(RealmColor.RED,"("+(possibleSelections++)+") Dragon3 ", false);    
        if(!dragon4.isDeadDragon())
            printWithColor(RealmColor.RED,"("+(possibleSelections++)+") Dragon4 ", false);
        System.out.println();
        if(dragon1.isDeadDragon()&&dragon2.isDeadDragon()&&dragon3.isDeadDragon()&&dragon4.isDeadDragon()){
            System.out.println("All dragons are dead");
            RedDice bonusDice=new RedDice(1);    
            bonusDice.setDiceStatus(DiceStatus.POWER_SELECTED);
            bonusDice.selectsDragon(1);
            return bonusDice; 
        }

        while(true){
            try{
            System.out.print("Choice: ");
            choice=Integer.parseInt(sc.next().trim());
            if(choice>=1&&choice<=4)
                break;
            throw new CommandFormatException("Please choose a number between 1 and 4");
            }catch(CommandFormatException ex){
                System.out.println(ex.getMessage());
            }
            catch(Exception ex){
                System.out.println("Please enter a valid number");
            }
        }
        Dragon dragon=null;
        //Shifting to see which dragon
        int indexSoFar=1;
        boolean found=false;
        if(!dragon1.isDeadDragon()){
            if(indexSoFar++==choice&&!found){
                choice=1;  
                found=true; }
        }
        if(!dragon2.isDeadDragon()){
            if(indexSoFar++==choice&&!found){
                choice=2;  
                found=true;}
        }
        if(!dragon3.isDeadDragon()){
            if(indexSoFar++==choice&&!found){
                choice=3;  
                found=true;}
        }
        if(!dragon4.isDeadDragon()){
            if(indexSoFar++==choice&&!found){
                choice=4;  
                found=true;}
        }
        switch (choice) {
            case 1:dragon=dragon1;break;
            case 2:dragon=dragon2;break;
            case 3:dragon=dragon3;break;
            case 4:dragon=dragon4;break;
            default:System.out.println("An error occured in dragon selection");
        }
        //choosing which part
        System.out.println("Please Choose which part to attack");
        if(!dragon.isFaceKilled())
            System.out.print("Face  ");
        if(!dragon.isWingsKilled())
            System.out.println("Wings  ");
        if(!dragon.isTailKilled())
            System.out.println("Tail  ");
        if(!dragon.isHeartKilled())
            System.out.println("Heart  ");
        System.out.println();    
        int diceValue=-1;
        while(true){
            try{
            String selection=sc.next().toUpperCase().trim();
            if(!selection.isEmpty()){
                switch (selection.charAt(0)) {
                   case 'F':
                        if(!dragon.isFaceKilled())
                            diceValue=dragon.getFace();
                        else
                            throw new PlayerActionException("Not a valid Selection");
                   case 'W':
                        if(!dragon.isWingsKilled())
                            diceValue=dragon.getWings();
                        else
                        throw new PlayerActionException("Not a valid Selection");
                    case 'T':
                         if(!dragon.isTailKilled())
                            diceValue=dragon.getTail();
                         else
                         throw new PlayerActionException("Not a valid Selection");
                    case 'H':
                        if(!dragon.isHeartKilled())
                            diceValue=dragon.getHeart();
                        else
                        throw new PlayerActionException("Not a valid Selection");
                    }
                if(diceValue!=-1)
                break;
            }else throw new CommandFormatException("Choice cant be blank");
        }catch(CommandFormatException ex){
            System.out.println(ex.getMessage());
        }catch(PlayerActionException ex){
            System.out.println(ex.getMessage());
        }
        }     
        RedDice bonusDice=new RedDice(diceValue);    
        bonusDice.setDiceStatus(DiceStatus.POWER_SELECTED);
        bonusDice.selectsDragon(choice);
        return bonusDice; 
    }
    public Dice selectGreenColorBonusGaia(Player player){
        System.out.println("Please Choose Which Gaia Guardian To Attack");
        Gaia gaia=player.getGreenRealm().getGaia();
        boolean[] gaiaHealth=gaia.getGurdiansHealth();
        //Making sure green realm hasnt been defeated

        if(!player.getGreenRealm().isRealmAccessible()){
            System.out.println("Gaia realm has been defeated");
            GreenDice bonusDice=new GreenDice(2);
            bonusDice.setDiceStatus(DiceStatus.POWER_SELECTED);
            return bonusDice;
        }

        for(int i=2;i<gaiaHealth.length;i++)
            if(gaiaHealth[i])
                System.out.print(i+" ");
        System.out.println(); 
        int choiceNum;    
        while(true){
            System.out.print("Choice: ");
            String choice=sc.next().trim();
            try{
                choiceNum=Integer.parseInt(choice);
                if(choiceNum>=2&&choiceNum<=12){
                    if(gaiaHealth[choiceNum])
                        break;
                    else
                        System.out.println("This gaia is already dead choose another one");    
                }
                else
                    System.out.println("Please choose from the previous numbers");     
            }catch(Exception ex){System.out.println("Please Enter a  number");}
        }    
        GreenDice bonusDice=new GreenDice(choiceNum);
        bonusDice.setDiceStatus(DiceStatus.POWER_SELECTED);
        return bonusDice;
    }
    public void displayArcaneBoostPrompt(Player player){
        //No available dice or arcaneboosts
        if(player.getArcaneBoosts().size()==0)
            return;
        resetDiceStatus();//To reset all the dice so player can reuse

        while(player.getArcaneBoosts().size()>0&&getAvailableDice().length>0&&getPossibleMovesForAvailableDice(player).length>0){  
        System.out.println(player.getPlayerName()+" would you like to use an ArcaneBoost?"); 
        System.out.println("You have x"+player.getArcaneBoosts().size()+" ArcaneBoosts remaining");
        System.out.println("Enter YES or NO");
        System.out.print("Choice: ");
        String choice=""+sc.next().trim().toUpperCase().charAt(0);
        if(choice.equals("Y"))
            useArcaneBoost(player);
        else if(choice.equals("N"))
            return;
        else
             System.out.println("Invalid input please enter YES or No!!");        
        }
        //If player used all ArcaneBoost or All Dice
        if(player.getArcaneBoosts().size()==0){
            System.out.println("No more ArcaneBoosts Avaialble"); 
            createDelay();
        }
        else if(getAvailableDice().length==0){
            System.out.println("No more Dice Available"); 
            createDelay();
        }
        else if(getPossibleMovesForAvailableDice(player).length>0){
            System.out.println("No more possible moves for avaialbe dice");
            createDelay();
        }

    }
    public void useArcaneBoost(Player player){
        try{
        player.removeArcaneBoost();
        Dice boostDice=selectDiceSequenceArcane(player);
        boostDice.setDiceStatus(DiceStatus.POWER_SELECTED);
        if(boostDice.getDiceColor()==RealmColor.RED)
            ((RedDice)boostDice).selectsDragon(-1);
        attackSequence(player, boostDice);   
        }catch(ExhaustedResourceException ex){
            System.out.println(ex.getMessage());
        } 
    }
    public Dice selectDiceSequenceArcane(Player player){
        //First select dice
        displayAvailableDice();
        int selectedDiceIndex=displaySelectArcaneDicePromt(player)-1;//-1 Because Prompt starts from 1
        Dice selectedDice=getAvailableDice()[selectedDiceIndex];
        //Prompt Player to choose Arcane Dice color if chosen
        RealmColor selectedDiceColor=selectedDice.getDiceColor();    
        if(selectedDiceColor==RealmColor.WHITE){
            displayChooseArcaneDiceColorPrompt(selectedDice,player);
            selectedDiceColor=((ArcanePrism)selectedDice).getChosenColor(); 
        }
        return selectedDice;
    }
    
    public int displaySelectArcaneDicePromt(Player player){
        Dice[]totalDice=getAvailableDice();

        //Check if user can bypass as no moves work
        boolean bypass=false;
        for(int i=0;i<totalDice.length;i++)
            if(getPossibleMovesForADie(player, totalDice[i]).length>0)break;
            else if(i==totalDice.length-1)bypass=true;
        if(bypass)
            System.out.println("All dice have no possible moves ");    

        while(true){
            System.out.println("Choose a number to select a dice");
            System.out.print("Choice: ");
            try{
                int choice=Integer.parseInt(sc.next().trim());
                if(1<=choice&&choice<=totalDice.length){
                    if(getPossibleMovesForADie(player, totalDice[choice-1]).length>0)
                        return choice;
                    else
                        System.out.println("No possible moves for this dice");
                }
                else
                    System.out.println("Please choose a number between 1 and "+(totalDice.length)+"!!");    

            }catch(Exception ex){
                System.out.println("Please enter a valid number");
            }
        }
    }

    public void passivePlayerSequence(Player player){
        //Sending unused to forgotten
        System.out.println("Sending unused dice to forgotten realm....");
        createDelay();
        sendUnusedToForgotten();
        
        displayPlayerGrimore(player);
        System.out.println("Passive Player Choose from forgotten Realm");
        createDelay();
        createDelay();

        Dice[] forgottenDice=getForgottenRealmDice();
        boolean possibleMove=false;
        for(Dice x:forgottenDice)
            if(getPossibleMovesForADie(getPassivePlayer(),x).length>0){
                possibleMove=true;
                break;}


        if(getForgottenRealmDice().length==0){
            System.out.println("No Dice Found in forgotten Realm");
            createDelay();
        }else if(!possibleMove){
            System.out.println("All dice in forgotten realms have no moves for passive player");
            createDelay();
        }else{
        displayForgottenRealmDice();
        Dice selectedDice=selectDiceSequence(player);
        selectedDice.setDiceStatus(DiceStatus.AVAILABLE);
        attackSequence(player, selectedDice);
        }
        //ArcaneBoost For active player
        displayArcaneBoostPrompt(getActivePlayer());
        //ArcaneBoost For passive player
        displayArcaneBoostPrompt(player);
    }
    public void resetDiceStatus(){
        Dice[] allDice= getAllDice();
        for(Dice x:allDice)
             x.setDiceStatus(DiceStatus.AVAILABLE);
     }
    public void sendUnusedToForgotten(){
        Dice[] dice=getAvailableDice();
        for(Dice x:dice)
            x.setDiceStatus(DiceStatus.FORGOTTEN_REALM);
    } 
//=======================================Methods=======================================

    public  boolean switchPlayer() {
        try {
        if (getActivePlayer()==null || getPassivePlayer()==null)
        throw new PlayerActionException("Both Players are null switch failed");
        if (getActivePlayer()==getPassivePlayer())
        throw new PlayerActionException("Both active and passive players are the same player switch failed");
        if (getActivePlayer().getPlayerStatus()==getPassivePlayer().getPlayerStatus())
        throw new PlayerActionException("Both Players have the same player statis switch failed");
        Player activePlayer=getActivePlayer();
        Player passivePlayer=getPassivePlayer();
        passivePlayer.setPlayerStatus(PlayerStatus.ACTIVE);
        activePlayer.setPlayerStatus(PlayerStatus.PASSIVE);
        return true;
        }
        catch(PlayerActionException ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public  Dice[] rollDice() {
        try {
            Dice[] rolledDice=getAvailableDice();
            if (rolledDice==null) throw new DiceRollException("Dice to be rolled are null dice rolling failed");
            for(Dice currentDice:rolledDice)
                currentDice.rollDice();
            for(Dice x:rolledDice){
                if(x.getValue()<1||x.getValue()>6)
                    throw new DiceRollException("Some dice have values that are not bounded between 1 and 6 inclusive dice rolling failed");
            } 
            if(rolledDice.length>0){    
            Arrays.sort(rolledDice);}    
            return rolledDice;
        } catch (DiceRollException diceRollException) {
            System.out.println(diceRollException.getMessage());
            return null;
        }
    }

    public  Dice[] getAvailableDice(){
        Dice[] allDice=getAllDice();
        int numberOfAvailableDice=0;
        for(Dice x:allDice)
            if(x.getDiceStatus()==DiceStatus.AVAILABLE)
                numberOfAvailableDice++;
       if(numberOfAvailableDice==0)
            return new Dice[0];
       Dice[] availableDice=new Dice[numberOfAvailableDice];

        for(int i=0,j=0;i<numberOfAvailableDice;j++)
            if(allDice[j].getDiceStatus()==DiceStatus.AVAILABLE)
                availableDice[i++]=allDice[j];
        if(availableDice.length>0){
        Arrays.sort(availableDice);}        
        return availableDice;   
    }
    
    public  Dice[] getAllDice(){
            Dice[]allDice=gameBoard.getAllDice();
            if(allDice.length>0){
            Arrays.sort(allDice);}
            return allDice;
        }

    public  Dice[] getForgottenRealmDice(){
        Dice[] allDice=getAllDice();
        int numberOfForgottenDice=0;
        for(Dice x:allDice)
            if(x.getDiceStatus()==DiceStatus.FORGOTTEN_REALM)
                numberOfForgottenDice++;

       Dice[] forgottenDice=new Dice[numberOfForgottenDice];

        for(int i=0,j=0;i<numberOfForgottenDice;j++)
            if(allDice[j].getDiceStatus()==DiceStatus.FORGOTTEN_REALM)
                forgottenDice[i++]=allDice[j];
         
        if(forgottenDice.length>0){       
        Arrays.sort(forgottenDice);} 
        return forgottenDice; 
        }

    public  Move[] getAllPossibleMoves(Player player){
        BlueRealm playerBlueRealm=player.getBlueRealm();
        RedRealm  playerRedRealm=player.getRedRealm();
        GreenRealm playerGreenRealm=player.getGreenRealm();
        YellowRealm playerYellowRealm=player.getYellowRealm();
        MagentaRealm playerMagentaRealm=player.getMagentaRealm();

        Move[] blueRealmMoves=playerBlueRealm.getAllPossibleMoves();
        Move[] redRealmMoves=playerRedRealm.getAllPossibleMoves();
        Move[] greenRealmMoves=playerGreenRealm.getAllPossibleMoves();
        Move[] yellowRealmMoves=playerYellowRealm.getAllPossibleMoves();
        Move[] magentaRealmMoves=playerMagentaRealm.getAllPossibleMoves();

        return mergeMoves(redRealmMoves,greenRealmMoves,blueRealmMoves,magentaRealmMoves, yellowRealmMoves,new Move[0] );
    }

    public  Move[] getPossibleMovesForAvailableDice(Player player){
        ArcanePrism arcanePrism=gameBoard.getArcanePrism();
        BlueDice blueDice=gameBoard.getBlueDice();
        RedDice redDice=gameBoard.getRedDice();
        GreenDice greenDice=gameBoard.getGreenDice();
        YellowDice yellowDice=gameBoard.getYellowDice();
        MagentaDice magentaDice=gameBoard.getMagentaDice();

        
        Move[] redMoves=getPossibleMovesForADie(player, redDice);
        if(redDice.getDiceStatus()!=DiceStatus.AVAILABLE)
            redMoves=new Move[0];
        Move[] greenMoves=getPossibleMovesForADie(player, greenDice);
        if(greenDice.getDiceStatus()!=DiceStatus.AVAILABLE||arcanePrism.getDiceStatus()==DiceStatus.AVAILABLE)
            greenMoves=new Move[0];
        Move[] blueMoves=getPossibleMovesForADie(player, blueDice);
        if(blueDice.getDiceStatus()!=DiceStatus.AVAILABLE)
            blueMoves=new Move[0];    
        Move[] magentaMoves=getPossibleMovesForADie(player, magentaDice);
        if(magentaDice.getDiceStatus()!=DiceStatus.AVAILABLE)
            magentaMoves=new Move[0];
        Move[] yellowMoves=getPossibleMovesForADie(player, yellowDice);
        if(yellowDice.getDiceStatus()!=DiceStatus.AVAILABLE)
            yellowMoves=new Move[0];
        Move[] arcaneMoves=getPossibleMovesForADie(player, arcanePrism);
        if(arcanePrism.getDiceStatus()!=DiceStatus.AVAILABLE)
            arcaneMoves=new Move[0];

        return mergeMoves(redMoves, greenMoves,blueMoves,magentaMoves,yellowMoves,arcaneMoves);
        }
    public  Move[] getPossibleMovesForADie(Player player, Dice dice){
        RealmColor diceColor=dice.getDiceColor();
        int diceValue=dice.getValue();

        Move[] blueRealmMoves=new Move[0];
        Move[] redRealmMoves=new Move[0];
        Move[] greenRealmMoves=new Move[0];
        Move[] yellowRealmMoves=new Move[0];
        Move[] magentaRealmMoves=new Move[0];
        
        if(diceColor==RealmColor.BLUE||diceColor==RealmColor.WHITE){
            BlueRealm playerBlueRealm=player.getBlueRealm();
            blueRealmMoves=playerBlueRealm.getPossibleMovesForADie(diceValue, diceColor,null);
        }
        if(diceColor==RealmColor.RED||diceColor==RealmColor.WHITE){
            RedRealm  playerRedRealm=player.getRedRealm();
            Dice diceToGo=dice;
            if(diceColor==RealmColor.WHITE){
                diceToGo=new RedDice(diceValue);
                ArcanePrism whiteArcane=(ArcanePrism)dice;
                ((RedDice)diceToGo).selectsDragon(whiteArcane.getselectsDragon());
            }
            redRealmMoves=playerRedRealm.getPossibleMovesForADie(diceValue, diceColor,diceToGo);
        }
        if(diceColor==RealmColor.GREEN||diceColor==RealmColor.WHITE){
            GreenRealm playerGreenRealm=player.getGreenRealm();
            int sumDiceValue=diceValue;
            
            if(diceColor==RealmColor.WHITE){
                GreenDice greenDice=gameBoard.getGreenDice();
                sumDiceValue+=greenDice.getValue();
            }
            else{
                ArcanePrism arcanePrism=gameBoard.getArcanePrism();
                sumDiceValue+=arcanePrism.getValue();
            }

            greenRealmMoves=playerGreenRealm.getPossibleMovesForADie(sumDiceValue, diceColor,null);
        }
        if(diceColor==RealmColor.YELLOW||diceColor==RealmColor.WHITE){
            YellowRealm playerYellowRealm=player.getYellowRealm();
            yellowRealmMoves=playerYellowRealm.getPossibleMovesForADie(diceValue, diceColor,null);
        }
        if(diceColor==RealmColor.MAGENTA||diceColor==RealmColor.WHITE){
            MagentaRealm playerMagentaRealm=player.getMagentaRealm();
            magentaRealmMoves=playerMagentaRealm.getPossibleMovesForADie(diceValue, diceColor,null);
        }

        return mergeMoves( redRealmMoves, greenRealmMoves,blueRealmMoves,magentaRealmMoves,yellowRealmMoves,new Move[0] );
    }

    public Move[] mergeMoves(Move[] red,Move[] green,Move[] blue,Move[]magenta,Move[] yellow,Move[]white){
        int moveArraySize=0;
        moveArraySize+=red.length;
        moveArraySize+=green.length;
        moveArraySize+=blue.length;
        moveArraySize+=magenta.length;
        moveArraySize+=yellow.length;
        moveArraySize+=white.length;

        
        Move[]moves=new Move[moveArraySize];
        int index=0;
        //red
        for(int i=0;i<white.length;i++)
            if(white[i].getDice().getDiceColor()==RealmColor.RED)
                moves[index++]=white[i];
        for(Move x:red)
            moves[index++]=x;
        //Green
        for(int i=0;i<white.length;i++)
            if(white[i].getDice().getDiceColor()==RealmColor.GREEN)
                moves[index++]=white[i];
        for(Move x:green)
            moves[index++]=x;    
        //blue
        for(int i=0;i<white.length;i++)
            if(white[i].getDice().getDiceColor()==RealmColor.BLUE)
                moves[index++]=white[i];
        for(Move x:blue)
            moves[index++]=x;    
        //magenta
        for(int i=0;i<white.length;i++)
            if(white[i].getDice().getDiceColor()==RealmColor.MAGENTA)
                moves[index++]=white[i];
        for(Move x:magenta)
            moves[index++]=x;    
        //yellow
        for(int i=0;i<white.length;i++)
            if(white[i].getDice().getDiceColor()==RealmColor.YELLOW)
                moves[index++]=white[i];
        for(Move x:yellow)
            moves[index++]=x;  

        if(moves.length>0){
            Arrays.sort(moves);
        }
        return moves;
        
    }

    public  GameBoard getGameBoard(){
        return gameBoard;
    }

    public  Player getActivePlayer(){
        return gameBoard.getActivePlayer();
        }
    public  Player getPassivePlayer(){
        return gameBoard.getPassivePlayer();
    }

    public  ScoreSheet getScoreSheet(Player player){
            return player.getScoreSheet();
    }


    public  GameStatus getGameStatus(){
            return gameBoard.getGameStatus();
    }
    public  GameScore getGameScore(Player player){
        return player.getGameScore();
        }
    public  TimeWarp[] getTimeWarpPowers(Player player){
        ArrayList<TimeWarp> timeWarp=player.getTimeWarps();

        if(timeWarp==null||timeWarp.isEmpty())//May change implementation based on tests
            return new TimeWarp[0];

        TimeWarp[] timeWarpOutput=new TimeWarp[timeWarp.size()];
        for(int i=0;i<timeWarpOutput.length;i++)
            timeWarpOutput[i]=timeWarp.get(i);

        return timeWarpOutput;    
        }
 
    public  ArcaneBoost[] getArcaneBoostPowers(Player player){
        ArrayList<ArcaneBoost> arcaneBoosts=player.getArcaneBoosts();

        if(arcaneBoosts==null||arcaneBoosts.isEmpty())//May change implementation based on tests
            return new ArcaneBoost[0];

        ArcaneBoost[] arcaneBoostOutput=new ArcaneBoost[arcaneBoosts.size()];
        for(int i=0;i<arcaneBoostOutput.length;i++)
            arcaneBoostOutput[i]=arcaneBoosts.get(i);
            
        return arcaneBoostOutput;  
        }
    public  boolean selectDice(Dice dice, Player player){
        try{
        Dice[] availableDice=getAvailableDice();
        if (dice==null || player==null || availableDice==null) throw new InvalidDiceSelectionException();
        boolean foundDice=false;
        for(Dice x:availableDice){
            if(x==dice){
                foundDice=true;
                x.setDiceStatus(DiceStatus.TURN_SELECTED);
            }
            else if(x.getValue()<dice.getValue())    
                x.setDiceStatus(DiceStatus.FORGOTTEN_REALM);
            }
        return foundDice; 
        }   
        catch (InvalidDiceSelectionException invalidDiceSelectionException){
            System.out.println(invalidDiceSelectionException.getMessage());
            return false;
        }
    }
    public  boolean makeMove(Player player, Move move){ 
        try{
        if (player==null || move== null) throw new InvalidMoveException();
        Dice moveDice=move.getDice();
        Creature moveCreature=move.getMoveCreature();
        RealmColor color=moveDice.getDiceColor();
        if(color==RealmColor.WHITE)
            color=((ArcanePrism)moveDice).getChosenColor();
        boolean flag=false;
        switch(color){
            case BLUE:
            flag=player.getBlueRealm().attack(moveDice.getValue(), moveCreature,moveDice);break;
            case RED:
            flag= player.getRedRealm().attack(moveDice.getValue(), moveCreature,moveDice);break;
            case GREEN:
            int sumValue=0;
            if(moveDice.getDiceStatus()==DiceStatus.POWER_SELECTED){//From Bonus So wont rely on white only green
                sumValue+=moveDice.getValue();
            }else{
            sumValue+=gameBoard.getGreenDice().getValue();
            sumValue+=gameBoard.getArcanePrism().getValue();}
            
            flag= player.getGreenRealm().attack(sumValue, moveCreature,moveDice);break;
            case YELLOW:
            flag= player.getYellowRealm().attack(moveDice.getValue(), moveCreature,moveDice);break;
            case MAGENTA:
            flag= player.getMagentaRealm().attack(moveDice.getValue(), moveCreature,moveDice);break;
            case WHITE: throw new InvalidMoveException(); 
            default: return false;
        }

            //Check and get Possible Rewards
            checkForPossibleRewards(player,color);   
            return flag;
    }
    catch(InvalidMoveException invalidMoveException){
        System.out.println(invalidMoveException.getMessage());
        return false;
    }
    }
}
