package game.gui;


import game.dice.ArcanePrism;
import game.dice.Dice;
import game.dice.GreenDice;
import game.engine.GUIGameController;
import game.engine.GameStatus;
import game.engine.Player;
import game.engine.PlayerStatus;
import game.exceptions.ExhaustedResourceException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


public class DiceScreenController extends SceneController{
    private boolean viewForgotten;
    private ColorAdjust View_1;
    private ColorAdjust View_2;
    private ColorAdjust View_3;
    private ColorAdjust View_4;
    private ColorAdjust View_5;
    private ColorAdjust View_6;
    
    private int tookRoundRewardinRound;
    private Player lastPlayertoTakeReward;
//=======================================Methods======================================
    public void rollDice(ActionEvent e){
        GUIGameController controller=getGuiGameController();
        controller.rollDice();
        updateDiceData();
        if(viewForgotten)
            ToggleDiceView(null);


        //Logic depenidng on player
        if(controller.getCurrentPlayer().getTimeWarps().size()>0){
            System_Message.setText("Would you like to use Timewarp?");
            System_Message.setVisible(true);
            if(controller.getActivePlayer()==controller.getGameBoard().getPlayer1()){
                Player_1_Time_yes.setVisible(true);
                Player_1_Time_no.setVisible(true);
            }else{
                Player_2_Time_yes.setVisible(true);
                Player_2_Time_no.setVisible(true);
            }
        }else if(shouldSkipTurn()){
            promptToSkipTurn();
        }
        RollDiceButton.setVisible(false);
    }


    public void enteredObject(MouseEvent e){
        ((Node)e.getSource()).setOpacity(0.8);
    }
    public void exitedObject(MouseEvent e){
        ((Node)e.getSource()).setOpacity(1);
    }



    public void usePower(MouseEvent e){
        GUIGameController controller=getGuiGameController();
        if(skipButton.isVisible())
            return;
        if(controller.isArcaneSequence()){
            try{
                if(controller.isArcaneSequenceActive())
                    controller.getActivePlayer().removeArcaneBoost();
                
                else
                    controller.getPassivePlayer().removeArcaneBoost();

            }catch(ExhaustedResourceException ex){
                rejectPower(e);
                return;
            }
            Player_1_Arcane_yes.setVisible(false);
            Player_1_Arcane_no.setVisible(false);
            Player_2_Arcane_yes.setVisible(false);
            Player_2_Arcane_no.setVisible(false);
            System_Message.setText("ArcaneBoost used");
            
        }else{
            controller.rollDice();
            updateDiceData();
            try{
               controller.getActivePlayer().removeTimeWarp();
            }catch(ExhaustedResourceException ex){
                System_Message.setText("How?");
                System_Message.setVisible(true);
            }
            if(controller.getActivePlayer().getTimeWarps().size()>0)
                System_Message.setText("Would you like to use Timewarp Again?");
            else{
                System_Message.setVisible(false);
                rejectPower(null);
            }
        }
        Player player1=controller.getGameBoard().getPlayer1();
        Player player2=controller.getGameBoard().getPlayer2();

        Player_1_ArcaneCount.setText("x"+player1.getArcaneBoosts().size());
        Player_1_TimeWarpCount.setText("x"+player1.getTimeWarps().size());
        Player_2_ArcaneCount.setText("x"+player2.getArcaneBoosts().size());
        Player_2_TimeWarpCount.setText("x"+player2.getTimeWarps().size());
        controller.updateScoreSheets();
    }




    public void rejectPower(MouseEvent e){
        GUIGameController controller=getGuiGameController();
        //Time
        if(skipButton.isVisible()&&e!=null)
            return;
        if(!controller.isArcaneSequence()){
            Player_1_Time_yes.setVisible(false);
            Player_1_Time_no.setVisible(false);
            Player_2_Time_yes.setVisible(false);
            Player_2_Time_no.setVisible(false);
            System_Message.setVisible(false);
            if(viewForgotten)
            ToggleDiceView(null);
            if(shouldSkipTurn())
                promptToSkipTurn();
        }else{
            //Arcane
            if(controller.isArcaneSequenceActive()){  
            controller.endArcaneSequenceActive(); 
            if(controller.getGameBoard().getPlayer1()==controller.getActivePlayer()){ 
            Player_1_Arcane_yes.setVisible(false);
            Player_1_Arcane_no.setVisible(false);
            Player_2_Arcane_yes.setVisible(true);
            Player_2_Arcane_no.setVisible(true);
            }else{
                Player_2_Arcane_yes.setVisible(false);
                Player_2_Arcane_no.setVisible(false);
                Player_1_Arcane_yes.setVisible(true);
                Player_1_Arcane_no.setVisible(true);
            }
            controller.resetDiceStatus();
            System_Message.setText("Passive Player would you like to use Arcane Boost");
            System_Message.setVisible(true);
            controller.switchScene(GUIScenes.DICE_SCENE);
            return;
            }else{
            controller.endArcaneSequencePassive();  
            Player_1_Arcane_yes.setVisible(false);
            Player_1_Arcane_no.setVisible(false);
            Player_2_Arcane_yes.setVisible(false);
            Player_2_Arcane_no.setVisible(false);
            controller.switchPlayer();
            controller.getGameStatus().incrementRound();
            controller.resetDiceStatus();
            controller.switchScene(GUIScenes.DICE_SCENE);
            return;
            }
        }
    }
    public boolean shouldSkipTurn(){
        if(viewForgotten&&getGuiGameController().getGameStatus().getTurn()<=3)
            ToggleDiceView(null);
        else if(!viewForgotten&&getGuiGameController().getGameStatus().getTurn()==4)
            ToggleDiceView(null);
    
        return (!View_Dice_1.isVisible()||View_1.getBrightness()!=0)&&(!View_Dice_2.isVisible()||View_2.getBrightness()!=0)
        &&(!View_Dice_3.isVisible()||View_3.getBrightness()!=0)&&(!View_Dice_4.isVisible()||View_4.getBrightness()!=0)
        &&(!View_Dice_5.isVisible()||View_5.getBrightness()!=0)&&(!View_Dice_6.isVisible()||View_6.getBrightness()!=0);
    }
    public void promptToSkipTurn(){
        System_Message.setText("There are no moves for available dice please skip turn");
        System_Message.setVisible(true);
        skipButton.setText("Skip Turn");
        skipButton.setVisible(true);
    }
    public void skipTurn(ActionEvent e){
        finishAttack();
    }
    public void skipsth(ActionEvent e){
        dontCheckRewards();
        skipButton.setVisible(false);
       System_Message.setVisible(false);
        if(skipButton.getText().equals("End Round"))
            endRound(e);
        else
            skipTurn(e);
                
    }
    @SuppressWarnings("exports")
    public Player getLastPlayerToGetReward(){
        return lastPlayertoTakeReward;
    }
    public void promptToEndRound(){
        GUIGameController controller=getGuiGameController();
        if(controller.getAvailableDice().length>0&&controller.getGameStatus().getTurn()!=4){
        skipButton.setVisible(false);
        ToggleDiceView.setVisible(true );;
         return;
        }else if(controller.getGameStatus().getTurn()==4)
            return;
        if(viewForgotten)
            ToggleDiceView(null);
         RollDiceButton.setVisible(false);   
         ToggleDiceView.setVisible(false);
         System_Message.setText("There are no available dice please end round");
         System_Message.setVisible(true);
         skipButton.setText("End Round");
         skipButton.setVisible(true);
    }
    public void endRound(ActionEvent e){
        GUIGameController controller=getGuiGameController();
        if(controller.isArcaneSequence()){
            rejectPower(null);
            return;
        }
        controller.getGameStatus().setTurn(3);
        finishAttack();
    }
   

    public void attack(MouseEvent e){
        GUIGameController controller=getGuiGameController();
        if(skipButton.isVisible())
            return;
        if(Player_1_Arcane_yes.isVisible()||Player_2_Arcane_yes.isVisible()){
            System_Message.setText("Please accept/reject Arcane Boost first");
            System_Message.setVisible(true);
            return;
        }
        if(Player_1_Time_yes.isVisible()||Player_2_Time_yes.isVisible()){
            System_Message.setText("Please accept/reject Time Warp first");
            System_Message.setVisible(true);
            return;
        }
        if(RollDiceButton.isVisible()){
            System_Message.setVisible(true);
            System_Message.setText("Please Roll Dice");
            return;
        }
        if(controller.getGameStatus().getTurn()==4&&!viewForgotten){
            System_Message.setVisible(true);
            System_Message.setText("Please Choose Forgotten Dice");
            return;
        }
        if(controller.getGameStatus().getTurn()<4&&viewForgotten){
            System_Message.setVisible(true);
            System_Message.setText("Please Choose Available Dice");
            return;
        }
        
        Dice[] dice;
        if(controller.getGameStatus().getTurn()!=4)
            dice=controller.getAvailableDice();
        else
            dice=controller.getForgottenRealmDice();

        Dice selectedDice=null;
        boolean shaded=false;
        if(e.getSource()==View_Dice_1){
            selectedDice=dice[0];
            if(View_1.getBrightness()!=0)
                shaded=true;
        }else if(e.getSource()==View_Dice_2){
            selectedDice=dice[1];
            if(View_2.getBrightness()!=0)
                shaded=true;
        }else if(e.getSource()==View_Dice_3){
            selectedDice=dice[2];
            if(View_3.getBrightness()!=0)
                shaded=true;
        }else if(e.getSource()==View_Dice_4){
            selectedDice=dice[3];
            if(View_4.getBrightness()!=0)
                shaded=true;
        }else if(e.getSource()==View_Dice_5){
            selectedDice=dice[4];
            if(View_5.getBrightness()!=0)
                shaded=true;
        }else if(e.getSource()==View_Dice_6){
            selectedDice=dice[5];
            if(View_6.getBrightness()!=0)
                shaded=true;
        }
        if(shaded){
            System_Message.setText("This dice has no moves please choose another");
            System_Message.setVisible(true);
            return;
        }

        controller.selectDice(selectedDice,controller.getCurrentPlayer());
        controller.setChosenDice(selectedDice);
        switch(selectedDice.getDiceColor()){
            case RED:switchToScene(GUIScenes.RED_REALM_SCENE);break;
            case GREEN:switchToScene(GUIScenes.GREEN_REALM_SCENE);break;
            case BLUE:switchToScene(GUIScenes.BLUE_REALM_SCENE);break;
            case MAGENTA:switchToScene(GUIScenes.MAGENTA_REALM_SCENE);break;
            case YELLOW:switchToScene(GUIScenes.YELLOW_REALM_SCENE);break;
            case WHITE:switchToScene(GUIScenes.COLOR_PICKER_SCENE);
        }
    }
    public void ToggleDiceView(ActionEvent e){
        if(skipButton.isVisible())
            return;
        viewForgotten=viewForgotten?false:true;
        if(viewForgotten)
            ToggleDiceView.setText("Show Available Dice");
        else
            ToggleDiceView.setText("Show Forgotten Dice");
        updateDiceData();
    }
//=======================================UPDATING=====================================


    public void updateScene(){
        
        GUIGameController controller=getGuiGameController();
        GameStatus gameStatus=controller.getGameStatus();
        
        if(gameStatus.getGameRound()<7)
            shouldGetRoundReward();
        else
            controller.switchScene(GUIScenes.GAME_END);
        //Update Realm Dice
        updateDiceData();
        //Update arcane
        if(controller.getGameStatus().getTurn()==4&&!viewForgotten)
            ToggleDiceView(null);
        else if(viewForgotten)
            ToggleDiceView(null);
        if(controller.isArcaneSequence())
            viewArcaneSequence();
        //Updating player data
        Player player1=controller.getGameBoard().getPlayer1();
        Player player2=controller.getGameBoard().getPlayer2();
        updatePlayerData(player1, player2);
        //Update round and turn
        RoundNum.setText("Round : "+controller.getGameStatus().getGameRound());
        if(controller.getGameStatus().getTurn()==4){
            System_Message.setText("Passive Player Choose from forgotten Realm");
            System_Message.setVisible(true);
            if(shouldSkipTurn()){
                promptToSkipTurn();
            }
        }
        else{
        if(controller.getGameStatus().getTurn()<=3)
        TurnNum.setText("Turn : "+controller.getGameStatus().getTurn());
        if(!controller.isArcaneSequence()){
        System_Message.setVisible(false);
        RollDiceButton.setVisible(true);
        }
        }
        promptToEndRound();
        controller.updateScoreSheets();

    }
    public void getRoundReward(){
        GUIGameController controller=getGuiGameController();
        GameStatus gameStatus=controller.getGameStatus();
        lastPlayertoTakeReward=controller.getActivePlayer();
        tookRoundRewardinRound=gameStatus.getGameRound();
        controller.checkRoundReward(controller.getActivePlayer());
    }    
    public void shouldGetRoundReward(){
        GUIGameController controller=getGuiGameController();
        GameStatus gameStatus=controller.getGameStatus();
        if (gameStatus.getTurn()==1&&(
            controller.getActivePlayer()!=lastPlayertoTakeReward||gameStatus.getGameRound()!=tookRoundRewardinRound))
            getRoundReward();
    }   
    public void updateDiceData(){
        GUIGameController controller=getGuiGameController();

        Dice[] dice;
        Player currentPlayer=controller.getCurrentPlayer();
        if(viewForgotten)
            dice=controller.getForgottenRealmDice();
        else    
            dice=controller.getAvailableDice();
        int index=0;
        if(index<dice.length){
            View_Dice_1.setVisible(true);
            
            String path="/images/DiceImages/"+dice[index].getDiceColor()+""+dice[index].getValue()+".png";
            View_Dice_1.setImage(new Image(getClass().getResourceAsStream(path)));

            View_1=new ColorAdjust();
            if(controller.getPossibleMovesForADie(currentPlayer,dice[index++]).length==0)
                View_1.setBrightness(-0.65);
            else        
                View_1.setBrightness(0);
            View_Dice_1.setEffect(View_1);
        }else
            View_Dice_1.setVisible(false);

        if(index<dice.length){
            View_Dice_2.setVisible(true);
            String path="/images/DiceImages/"+dice[index].getDiceColor()+""+dice[index].getValue()+".png";
            View_Dice_2.setImage(new Image(getClass().getResourceAsStream(path)));

            View_2=new ColorAdjust();
            if(controller.getPossibleMovesForADie(currentPlayer,dice[index++]).length==0)
                View_2.setBrightness(-0.65);
            else        
                View_2.setBrightness(0);
            View_Dice_2.setEffect(View_2);
        }else
            View_Dice_2.setVisible(false);    

        if(index<dice.length){
            View_Dice_3.setVisible(true);
            String path="/images/DiceImages/"+dice[index].getDiceColor()+""+dice[index].getValue()+".png";
            View_Dice_3.setImage(new Image(getClass().getResourceAsStream(path)));

            View_3=new ColorAdjust();
            if(controller.getPossibleMovesForADie(currentPlayer,dice[index++]).length==0)
                View_3.setBrightness(-0.65);
            else        
                View_3.setBrightness(0);
            View_Dice_3.setEffect(View_3);
        }else
            View_Dice_3.setVisible(false);
        
        if(index<dice.length){
            View_Dice_4.setVisible(true);
            String path="/images/DiceImages/"+dice[index].getDiceColor()+""+dice[index].getValue()+".png";
            View_Dice_4.setImage(new Image(getClass().getResourceAsStream(path)));

            View_4=new ColorAdjust();
            if(controller.getPossibleMovesForADie(currentPlayer,dice[index++]).length==0)
                View_4.setBrightness(-0.65);
            else        
                View_4.setBrightness(0);
            View_Dice_4.setEffect(View_4);
        }else
            View_Dice_4.setVisible(false);

        if(index<dice.length){
            View_Dice_5.setVisible(true);
            String path="/images/DiceImages/"+dice[index].getDiceColor()+""+dice[index].getValue()+".png";
            View_Dice_5.setImage(new Image(getClass().getResourceAsStream(path)));

            View_5=new ColorAdjust();
            if(controller.getPossibleMovesForADie(currentPlayer,dice[index++]).length==0)
                View_5.setBrightness(-0.65);
            else        
                View_5.setBrightness(0);
            View_Dice_5.setEffect(View_5);
        }else
            View_Dice_5.setVisible(false);

        if(index<dice.length){
            View_Dice_6.setVisible(true);
            String path="/images/DiceImages/"+dice[index].getDiceColor()+""+dice[index].getValue()+".png";
            View_Dice_6.setImage(new Image(getClass().getResourceAsStream(path)));

            View_6=new ColorAdjust();
            if(controller.getPossibleMovesForADie(currentPlayer,dice[index++]).length==0)
                View_6.setBrightness(-0.65);
            else        
                View_6.setBrightness(0);
            View_Dice_6.setEffect(View_6);
        }else
            View_Dice_6.setVisible(false);

        GreenDice greenDice=controller.getGameBoard().getGreenDice();
        ArcanePrism whiteDice=controller.getGameBoard().getArcanePrism();
        
        String greenPerma="/images/DiceImages/"+greenDice.getDiceColor()+""+greenDice.getValue()+".png";
        Green_Perma.setImage(new Image(getClass().getResourceAsStream(greenPerma)));

        String whitePerma="/images/DiceImages/"+whiteDice.getDiceColor()+""+whiteDice.getValue()+".png";
        White_Perma.setImage(new Image(getClass().getResourceAsStream(whitePerma)));
 
    }
    @SuppressWarnings("exports")
    public void updatePlayerData( Player player1,Player player2){
        //Updating For Player 1
        Player_1_name.setText(player1.getPlayerName());
        Player_1_status.setText("Status :"+player1.getPlayerStatus());
        Player_1_ArcaneCount.setText("x"+player1.getArcaneBoosts().size());
        Player_1_TimeWarpCount.setText("x"+player1.getTimeWarps().size());
        Player_1_CrestCount.setText("x"+player1.getElementalCrests().size());
        //Updating For Player 2
        Player_2_name.setText(player2.getPlayerName());
        Player_2_status.setText("Status :"+player2.getPlayerStatus());
        Player_2_ArcaneCount.setText("x"+player2.getArcaneBoosts().size());
        Player_2_TimeWarpCount.setText("x"+player2.getTimeWarps().size());
        Player_2_CrestCount.setText("x"+player2.getElementalCrests().size());
        if(player1.getPlayerStatus()==PlayerStatus.ACTIVE){
            Player_1_flag.setImage(new Image(getClass().getResourceAsStream("/images/ActivePlayerFlag.png")));
            Player_2_flag.setImage(new Image(getClass().getResourceAsStream("/images/PassivePlayerFlag.png")));
        }else{
            Player_1_flag.setImage(new Image(getClass().getResourceAsStream("/images/PassivePlayerFlag.png")));
            Player_2_flag.setImage(new Image(getClass().getResourceAsStream("/images/ActivePlayerFlag.png")));
        }
    }
    public void viewArcaneSequence(){
        GUIGameController controller=getGuiGameController();
        boolean player1IsActive=controller.getActivePlayer()==controller.getGameBoard().getPlayer1();
        if(controller.isArcaneSequenceActive()&&controller.getActivePlayer().getArcaneBoosts().size()==0){
            rejectPower(null);
            return;
        }
        if(!controller.isArcaneSequenceActive()&&controller.isArcaneSequence()&&controller.getPassivePlayer().getArcaneBoosts().size()==0){
            rejectPower(null);
            return;
        }//No available for arcane 
        if((View_1.getBrightness()!=0||!View_Dice_1.isVisible())&&(View_2.getBrightness()!=0||!View_Dice_2.isVisible())&&
        (View_3.getBrightness()!=0||!View_Dice_3.isVisible())&&(View_4.getBrightness()!=0||!View_Dice_4.isVisible())&&
        (View_5.getBrightness()!=0||!View_Dice_5.isVisible())&&(View_6.getBrightness()!=0||!View_Dice_6.isVisible())){
            rejectPower(null);
            return;
        }


        if(controller.isArcaneSequenceActive())
            System_Message.setText("Active Player would you like to use Arcane Boost?");
        else
             System_Message.setText("Passive Player would you like to use Arcane Boost?");


        if(controller.isArcaneSequenceActive()&&player1IsActive||!player1IsActive&&!controller.isArcaneSequenceActive()){
            Player_1_Arcane_yes.setVisible(true);
            Player_1_Arcane_no.setVisible(true);
        }else{
            Player_2_Arcane_yes.setVisible(true);
            Player_2_Arcane_no.setVisible(true);
        }
        System_Message.setVisible(true);
    }

    @Override
    public void initialize() {
        Image temp = new Image(getClass().getResourceAsStream("/images/DiceScreen.jpg"));
        backGround.setImage(temp);
        temp = new Image(getClass().getResourceAsStream("/images/ElementalCrest.png"));
        Player_1_crest.setImage(temp);
        Player_2_crest.setImage(temp);
        temp = new Image(getClass().getResourceAsStream("/images/ArcaneBoost.png"));
        Player_1_arcane.setImage(temp);
        Player_2_arcane.setImage(temp);
        temp = new Image(getClass().getResourceAsStream("/images/TimeWarp.png"));
        Player_1_timewarp.setImage(temp);
        Player_2_timewarp.setImage(temp);
        temp= new Image(getClass().getResourceAsStream("/images/NO.png"));
        Player_1_Arcane_no.setImage(temp);
        Player_2_Arcane_no.setImage(temp);
        Player_1_Time_no.setImage(temp);
        Player_2_Time_no.setImage(temp);
        temp= new Image(getClass().getResourceAsStream("/images/YES.png"));
        Player_1_Arcane_yes.setImage(temp);
        Player_2_Arcane_yes.setImage(temp);
        Player_1_Time_yes.setImage(temp);
        Player_2_Time_yes.setImage(temp);
        updateDiceData();
    }
//=======================================FXML=========================================

@FXML
private Label Player_1_ArcaneCount;

@FXML
private ImageView Player_1_Arcane_no;

@FXML
private ImageView Player_1_Arcane_yes;

@FXML
private Label Player_1_CrestCount;
@FXML 
private ImageView Green_Perma;
@FXML 
private ImageView White_Perma;
@FXML
private Label Player_1_TimeWarpCount;

@FXML
private ImageView Player_1_Time_no;

@FXML
private ImageView Player_1_Time_yes;

@FXML
private ImageView Player_1_arcane;

@FXML
private ImageView Player_1_crest;

@FXML
private ImageView Player_1_flag;

@FXML
private Label Player_1_name;

@FXML
private Label Player_1_status;

@FXML
private ImageView Player_1_timewarp;

@FXML
private Label Player_2_ArcaneCount;

@FXML
private ImageView Player_2_Arcane_no;

@FXML
private ImageView Player_2_Arcane_yes;

@FXML
private Label Player_2_CrestCount;

@FXML
private Label Player_2_TimeWarpCount;

@FXML
private ImageView Player_2_Time_no;

@FXML
private ImageView Player_2_Time_yes;

@FXML
private ImageView Player_2_arcane;

@FXML
private ImageView Player_2_crest;

@FXML
private ImageView Player_2_flag;

@FXML
private Label Player_2_name;

@FXML
private Label Player_2_status;

@FXML
private ImageView Player_2_timewarp;

@FXML
private Button RollDiceButton;

@FXML
private Label RoundNum;

@FXML
private Label System_Message;

@FXML
private Button ToggleDiceView;

@FXML
private Label TurnNum;

@FXML
private ImageView View_Dice_1;

@FXML
private ImageView View_Dice_2;

@FXML
private ImageView View_Dice_3;

@FXML
private ImageView View_Dice_4;

@FXML
private ImageView View_Dice_5;

@FXML
private ImageView View_Dice_6;

@FXML
private ImageView backGround;

@FXML
private Button skipButton;

}
