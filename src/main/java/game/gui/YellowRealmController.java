package game.gui;

import game.dice.YellowDice;
import game.engine.GUIGameController;
import game.engine.Move;
import game.engine.Player;
import game.realms.YellowRealm;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class YellowRealmController extends SceneController{
    //EACH REALM UPDATES ITS OWN STUFF AFTER ATTACK AND ATTACKS USING MAKE MOVE
    //TO ATTACK USING MAKE MOVE USE APPROPRIATE DICE FROM GAMEBOARD HOW TO HANDLE WHITE DICE??
    //CHECK SOMEWHERE FOR A VARIABLE THAT INDACTES A BONUS HAS BEEN ACTIVATED IF TRUE THEN....
    //AFTER FINISHING ATTACK MUST CALL THE METHOD finishAttack at the end of attack 
    //This method ends the scence and switches back and applies logic

    @FXML Label realmScore;
    @FXML Label remainingAttack;
    @FXML ImageView LionBackground;
    @FXML ImageView lionImage;
    @FXML
    Label playerName;
    GUIGameController guiGameController;
    @FXML 
    public void initialize(){
        Image background  = new Image(getClass().getResourceAsStream("/images/yellowBackground.png"));
        LionBackground.setImage(background);
        Image lion  = new Image(getClass().getResourceAsStream("/images/LionCreature.png"));
        lionImage.setImage(lion);
        guiGameController=getGuiGameController();
    }
    public void enterLion(MouseEvent e){
        lionImage.setOpacity(0.8);
    }
    public void exitLion(MouseEvent e){
        lionImage.setOpacity(1);
    }
    public void setYellowRealm(){
        Player currentPlayer=getGuiGameController().getCurrentPlayer();
        YellowRealm yellowRealm=currentPlayer.getYellowRealm();
        playerName.setText("Player: " + currentPlayer.getPlayerName());
        int score=yellowRealm.getTotalRealmScore();
        int reaminingAttacks=11-yellowRealm.getTotalNumberOfAttacks();

        remainingAttack.setText("Remaining Attacks: "+reaminingAttacks);
        realmScore.setText("Realm Score: "+score);

    }
    public void attackLion(MouseEvent e){
        GUIGameController controller=getGuiGameController();
        YellowRealm yellowRealm=getGuiGameController().getCurrentPlayer().getYellowRealm();
        int diceValue;
        if(controller.isBonusActivated())
            diceValue=6;
        else
            diceValue=controller.getChosenDice().getValue();

        getGuiGameController().makeMove(controller.getCurrentPlayer(),new Move(new YellowDice(diceValue),null));
        

        int score=yellowRealm.getTotalRealmScore();
        int reaminingAttacks=11-yellowRealm.getTotalNumberOfAttacks();

        remainingAttack.setText("Remaining Attacks: "+reaminingAttacks);
        realmScore.setText("Realm Score: "+score);
        finishAttack();
    }

}
