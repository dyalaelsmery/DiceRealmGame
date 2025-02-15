package game.engine;

import game.dice.*;
import game.exceptions.*;
import game.gui.*;
import java.io.*;
import java.util.*;
import game.collectibles.*;
import game.realms.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.*;
import game.creatures.*;
public class GUIGameController extends GameController implements Runnable{
    //Instances
    private Stage stage;
    private GameBoard gameBoard;
    private String[] roundRewards;
    transient private Thread stageThread;
    //Score sheet
    private Stage player1ScoreSheetStage;
    private Stage player2ScoreSheetStage;
  
    private PlayerScoreSheetController player1ScoreSheetControllerr;
    private PlayerScoreSheetController player2ScoreSheetControllerr;
    //Scenes and Controllers
    private Scene redRealmScene;
    private RedRealmController redController;
    //
    private Scene playerSelectorScene;
    private PlayerSelectorController playerSelectorController;
    //
    private Scene greenRealmScene;
    private GreenRealmController greenController;
    //
    private BlueRealmController blueController;
    private Scene blueRealmScene;
    //
    private Scene yellowRealmScene;
    private YellowRealmController yellowController;
    //
    private MagentaRealmController magentaController;
    private Scene magentaRealmScene;
    //
    private Scene colorPickerScene;
    private ColorPickerController colorPickerController;
    //
    private Scene diceScene;
    private DiceScreenController diceScreenController;
    //GUI GameFlow RELATED
    private boolean arcaneSequence_ACTIVE;
    private boolean arcaneSequence_PASSIVE;
    private Dice chosenDice;
    private RealmColor chosenColor;
    public boolean bonusActivated;
    private ArrayList<Reward> remainingRewards;
    private boolean roundBonusActivated;

    private Scene endScene;
    private GameEndController endController;
//=======================================Constructor===================================    
    public GUIGameController(Stage stage){
        this.stage=stage;
        gameBoard=new GameBoard();
        remainingRewards=new ArrayList<>();
        roundRewards=new String[6];
        setRoundRewards(); 
    }
    public Stage getStage(){
        return stage;
    }
    public void initializeScoreSheet(){
        try{
        initializeScoreSheet("ahmed", "omar");
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public void updateScoreSheets(){
        player1ScoreSheetControllerr.updateScoreSheets();
        player2ScoreSheetControllerr.updateScoreSheets();
    }
    public void initializeScoreSheet(String player1Name,String player2Name)throws Exception{
        getGameBoard().getPlayer1().setPlayerName(player1Name);
        getGameBoard().getPlayer2().setPlayerName(player2Name);

        player1ScoreSheetStage=new Stage();
        player1ScoreSheetStage.setResizable(false);

        player2ScoreSheetStage=new Stage();
        player2ScoreSheetStage.setResizable(false);
        
        player1ScoreSheetStage.initStyle(StageStyle.UNDECORATED);
        player2ScoreSheetStage.initStyle(StageStyle.UNDECORATED);


        player2ScoreSheetStage.setOnCloseRequest(event -> {
            event.consume();});
        player1ScoreSheetStage.setOnCloseRequest(event -> {
                event.consume();});

        stage.setOnCloseRequest(event -> {
                    stage.close();player1ScoreSheetStage.close();player2ScoreSheetStage.close();
                stageThread=null;});    
                
                
         player1ScoreSheetControllerr=new PlayerScoreSheetController(gameBoard.getPlayer1(), player1ScoreSheetStage);

         player2ScoreSheetControllerr=new PlayerScoreSheetController(gameBoard.getPlayer2(), player2ScoreSheetStage);

    
        // primaryStage.getIcons().add(dragon);
        updateStagePosition();
   
        player1ScoreSheetStage.show();
        player2ScoreSheetStage.show();
        stageThread=new Thread(this);
		stageThread.start();
    }
    public boolean isRoundBonusActivated(){
        return roundBonusActivated;
    }
    public void resetRoundBonusActivated(){
        roundBonusActivated=false;
    }
    public void run(){
        while(stageThread!=null){
            updateStagePosition();
        }
    }
    public PlayerScoreSheetController getPlayer1ScoreSheetController(){
        return player1ScoreSheetControllerr;
    }
    public PlayerScoreSheetController getPlayer2ScoreSheetController(){
        return player2ScoreSheetControllerr;
    }

    public void updateStagePosition(){
        double x=stage.getX();
        double y=stage.getY();
        player1ScoreSheetStage.setX(x-player1ScoreSheetStage.getWidth()-5);
        player1ScoreSheetStage.setY(y);
        player2ScoreSheetStage.setX(x+stage.getWidth()+5);
        player2ScoreSheetStage.setY(y);        
    }
    public void initializeScenes(){
        try{
            FXMLLoader endLoader = new FXMLLoader(getClass().getResource("/game/gui/GameEnd.fxml"));
            Parent endRoot = endLoader.load();
            endScene = new Scene(endRoot);
            endController = (GameEndController) endLoader.getController();



            FXMLLoader playerSelectorLoader = new FXMLLoader(getClass().getResource("/game/gui/PlayerSelector.fxml"));
            Parent playerSelectorRoot = playerSelectorLoader.load();
            playerSelectorScene = new Scene(playerSelectorRoot);
            playerSelectorController = (PlayerSelectorController) playerSelectorLoader.getController();
        
            FXMLLoader redLoader = new FXMLLoader(getClass().getResource("/game/gui/RedRealm.fxml"));
            Parent redRoot = redLoader.load();
            redRealmScene=new Scene(redRoot);
            redController =(RedRealmController) redLoader.getController();
    
        
             FXMLLoader greenLoader = new FXMLLoader(getClass().getResource("/game/gui/GreenRealm.fxml"));
             Parent greenRoot = greenLoader.load();
             greenRealmScene=new Scene(greenRoot);
             greenController =(GreenRealmController) greenLoader.getController();


            FXMLLoader blueLoader = new FXMLLoader(getClass().getResource("/game/gui/BlueRealm.fxml"));
            Parent blueRoot = blueLoader.load();
            blueRealmScene=new Scene(blueRoot);
            blueController =(BlueRealmController) blueLoader.getController();

            FXMLLoader magentaLoader = new FXMLLoader(getClass().getResource("/game/gui/MagentaRealm.fxml"));
            Parent magentaRoot = magentaLoader.load();
            magentaRealmScene=new Scene(magentaRoot);
            magentaController =(MagentaRealmController) magentaLoader.getController();
    
            FXMLLoader yellowLoader = new FXMLLoader(getClass().getResource("/game/gui/YellowRealm.fxml"));
            Parent yellowRoot = yellowLoader.load();
            yellowRealmScene=new Scene(yellowRoot);
            yellowController =(YellowRealmController) yellowLoader.getController();
    ;
            //End loading realms

            FXMLLoader colorLoader = new FXMLLoader(getClass().getResource("/game/gui/ColorPicker.fxml"));
            Parent colorRoot = colorLoader.load();
            colorPickerScene=new Scene(colorRoot);
            colorPickerController =(ColorPickerController) colorLoader.getController();

            FXMLLoader diceLoader = new FXMLLoader(getClass().getResource("/game/gui/DiceScreen.fxml"));
            Parent diceRoot = diceLoader.load();
            diceScene=new Scene(diceRoot);
            diceScreenController =(DiceScreenController) diceLoader.getController();
        }catch(Exception ex){
                ex.printStackTrace();
                System.out.println("Could not load scenes");
        }
    }
//=======================================GameFlow======================================
    public void setChosenColor(RealmColor color){
        chosenColor=color;
    }
    public void resetDiceStatus(){
        Dice[] allDice= getAllDice();
        for(Dice x:allDice)
             x.setDiceStatus(DiceStatus.AVAILABLE);
     }
     
     

    public void switchScene(GUIScenes nextScene){
        GameBoard gameBoard=getGameBoard();
       try{
        if(chosenDice==null)
        chosenDice=getGameBoard().getRedDice();
        switch(nextScene){
            case Player_Selector_Scene: stage.setScene(playerSelectorScene); break;
            case RED_REALM_SCENE:
            chosenColor=RealmColor.RED;
            redController.setRedRealm(chosenDice.getValue());
            stage.setScene(redRealmScene);break;
            case GREEN_REALM_SCENE:
            chosenColor=RealmColor.GREEN;
            int greenDiceVal=gameBoard.getGreenDice().getValue()+gameBoard.getArcanePrism().getValue();
            greenController.setGreenRealm(greenDiceVal);
            stage.setScene(greenRealmScene);break;
            case BLUE_REALM_SCENE:
            chosenColor=RealmColor.BLUE;
            blueController.setBlueRealm();
            stage.setScene(blueRealmScene);break;
            case MAGENTA_REALM_SCENE:
            chosenColor=RealmColor.MAGENTA;
            magentaController.setMagentaRealm();
            stage.setScene(magentaRealmScene);break;
            case YELLOW_REALM_SCENE:
            chosenColor=RealmColor.YELLOW;
            yellowController.setYellowRealm();
            stage.setScene(yellowRealmScene);break;
            case Username_Scene:
            Parent root =FXMLLoader.load(getClass().getResource("/game/gui/UserName.fxml"));
            Scene getName=new Scene(root);
            getName.getRoot().requestFocus();
            stage.setScene(getName);break;
            case COLOR_PICKER_SCENE:
            colorPickerController.updateScene();
            stage.setScene(colorPickerScene);break;
            case DICE_SCENE:
            stage.setScene(diceScene);
            diceScreenController.updateScene();
            break;
            case GAME_END:
            stage.setScene(endScene);
            endController.updateScene();
        }
    }catch(Exception ex){
        ex.printStackTrace();
    }
    }
    public void sendUnusedToForgotten(){
        Dice[] dice=getAvailableDice();
        for(Dice x:dice)
            x.setDiceStatus(DiceStatus.FORGOTTEN_REALM);
    } 
                        
    public String[] getRoundRewardAr(){
        return roundRewards;
    }
    public Player getLastPlayerToGetReward(){
        return diceScreenController.getLastPlayerToGetReward();
    }       
    public boolean checkRoundReward(Player player){
        String reward =roundRewards[(getGameStatus().getGameRound())-1];
        switch (reward) {
            case "RedBonus":    
                if(!player.getRedRealm().isRealmDefeated()){
                    roundBonusActivated=true;
                    bonusActivated=true;
                    switchScene(GUIScenes.RED_REALM_SCENE);
                }
              return true;
            case "GreenBonus":
                if(!player.getGreenRealm().isRealmDefeated()){
                    roundBonusActivated=true;
                    bonusActivated=true;
                     switchScene(GUIScenes.GREEN_REALM_SCENE);
                }
                return true;
            case "BlueBonus":
                if(!player.getBlueRealm().isRealmDefeated()){
                    roundBonusActivated=true;
                    bonusActivated=true;
                    switchScene(GUIScenes.BLUE_REALM_SCENE);
                }
                return true;

            case "MagentaBonus":
            if(!player.getMagentaRealm().isRealmDefeated()){
                roundBonusActivated=true;
                bonusActivated=true;
            switchScene(GUIScenes.MAGENTA_REALM_SCENE);}
            return true;

            case "YellowBonus":
              if(!player.getYellowRealm().isRealmDefeated()){
                roundBonusActivated=true;
                bonusActivated=true;
                switchScene(GUIScenes.YELLOW_REALM_SCENE);}
                return true;

            case "EssenceBonus":
                bonusActivated=true;
                roundBonusActivated=true;
                switchScene(GUIScenes.COLOR_PICKER_SCENE);
            return true;

            case "ElementalCrest":
                player.addElementalCrest(new ElementalCrest());
                break;
            case "ArcaneBoost":
                player.addArcaneBoost(new ArcaneBoost());
                break;
            case "TimeWarp":
                player.addTimeWarp(new TimeWarp());
                break;         
            case "null":break;
    }
    return false;
}
    public boolean getNextReward(){
        Player player=getCurrentPlayer();
       while(remainingRewards.size()>0){//Reward array for multiple cases of rewards
            Reward earnedReward=remainingRewards.remove(0);
            switch(earnedReward.getRewardType()){
                case CREST:
                player.addElementalCrest((ElementalCrest)earnedReward);
                break;
                case POWER:
                if(earnedReward instanceof TimeWarp){
                    player.addTimeWarp((TimeWarp)earnedReward);
                }
                else if(earnedReward instanceof ArcaneBoost){
                    player.addArcaneBoost((ArcaneBoost)earnedReward);
                }
                break;
                case BONUS:
                bonusActivated=true;
                boolean bonusWasted=false;
                if(earnedReward instanceof EssenceBonus){
                    colorPickerController.updateScene();
                    switchScene(GUIScenes.COLOR_PICKER_SCENE);
                }
                else if(earnedReward instanceof Bonus){
                    RealmColor color=((Bonus)earnedReward).getBonusColor();
                    switch(color){
                        case RED:
                        if(player.getRedRealm().isRealmDefeated())
                            bonusWasted=true;
                        else    
                        switchScene(GUIScenes.RED_REALM_SCENE);break;
                        case GREEN:
                        if(player.getGreenRealm().isRealmDefeated())
                            bonusWasted=true;
                        else
                        switchScene(GUIScenes.GREEN_REALM_SCENE);break;
                        case BLUE:
                        if(player.getBlueRealm().isRealmDefeated())
                            bonusWasted=true;
                        else    
                        switchScene(GUIScenes.BLUE_REALM_SCENE);break;
                        case MAGENTA:
                        if(player.getMagentaRealm().isRealmDefeated())
                            bonusWasted=true;
                        else    
                        switchScene(GUIScenes.MAGENTA_REALM_SCENE);break;
                        case YELLOW:
                        if(player.getYellowRealm().isRealmDefeated())
                            bonusWasted=true;
                        else    
                        switchScene(GUIScenes.YELLOW_REALM_SCENE);break;
                        case WHITE: System.out.println("Error");
                    }
                }
                if(!bonusWasted)
                return false;
                else
                    System.out.println("Bonus was wasted");

            }
        }
        return true;//Means no bonus found so continue in method  

    }
    public boolean checkForPossibleRewards(){
        RealmColor diceColor=chosenColor;
 
       Player player=getCurrentPlayer();
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
            if(remainingRewards.size()>0)
                return true;
            else    
            return false;
        Reward[]allRewards=null;    
        try{
        allRewards=checkInRealm.getReward();
        for(Reward x:allRewards)
            remainingRewards.add(x);
        }catch(RewardException ex){
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }
    public void startArcaneSequence(){
        arcaneSequence_ACTIVE=true;
        arcaneSequence_PASSIVE=true;
    }
    public void endArcaneSequenceActive(){
        arcaneSequence_ACTIVE=false;
    }
    public void endArcaneSequencePassive(){
        arcaneSequence_PASSIVE=false;
    }
//=======================================Get&Set======================================
    public boolean isArcaneSequenceActive(){
        return arcaneSequence_ACTIVE;
    }
    public boolean isArcaneSequence(){
        return arcaneSequence_ACTIVE||arcaneSequence_PASSIVE;
    }
    public Player getCurrentPlayer(){
    
        if(getGameStatus().getTurn()==4||arcaneSequence_PASSIVE&&!arcaneSequence_ACTIVE){
            return getGameBoard().getPassivePlayer();
        }
        return getGameBoard().getActivePlayer();
    }
    public PlayerSelectorController getPlayerSelectorController(){return playerSelectorController;}
    public Dice getChosenDice(){
            return chosenDice;
    }
    public void setChosenDice(Dice dice){
        this.chosenDice=dice;
    }
    public boolean isBonusActivated(){
            return bonusActivated;
    }
    public void resetBonusActivated(){
            bonusActivated=false;
    }





//=======================================Methods=======================================
@Override
public void startGame(){
    //?
}
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
        case GREEN:  //Assumes value full dice given 
        flag= player.getGreenRealm().attack(moveDice.getValue(), moveCreature,moveDice);break;
        case YELLOW:
        flag= player.getYellowRealm().attack(moveDice.getValue(), moveCreature,moveDice);break;
        case MAGENTA:
        flag= player.getMagentaRealm().attack(moveDice.getValue(), moveCreature,moveDice);break;
        case WHITE: throw new InvalidMoveException(); 
        default: return false;
    }
        return flag;
}
catch(InvalidMoveException invalidMoveException){
    System.out.println(invalidMoveException.getMessage());
    return false;
}
}
//=========================RoundRewardsLoad==========================

//Unimportant for now
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




}
   