package game.gui;

import game.dice.MagentaDice;
import game.engine.GUIGameController;
import game.engine.Move;
import game.engine.Player;
import game.realms.MagentaRealm;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class MagentaRealmController extends SceneController{
 
    @FXML Label realmScore;
    @FXML Label remainingAttack;
    @FXML Label minimumAttack;
    @FXML Label playerName;
    @FXML 
    public void initialize(){
                Image phoenix = new Image(getClass().getResourceAsStream("/images/PhoenixImg.jpg"));
                back.setImage(phoenix);


    }
    public void enterPhoenix(MouseEvent e){
    }
    public void exitPhoenix(MouseEvent e){
    }
     public void setMagentaRealm(){
        Player currentPlayer=getGuiGameController().getCurrentPlayer();
        MagentaRealm magentaRealm=currentPlayer.getMagentaRealm();
        playerName.setText("Player: " + currentPlayer.getPlayerName());
        int score=magentaRealm.getTotalRealmScore();
        int reaminingAttacks=11-magentaRealm.getTotalNumberOfAttacks();

        remainingAttack.setText("Remaining Attacks: "+reaminingAttacks);
        realmScore.setText("Realm Score: "+score);


        int nextMinAttack=magentaRealm.getPhoenix().getMinimumAttackValue();

        minimumAttack.setText("Minimum Attack: "+nextMinAttack);


    }
    public void attackPhoenix(MouseEvent e){
        GUIGameController controller=getGuiGameController();
        MagentaRealm magentaRealm=getGuiGameController().getCurrentPlayer().getMagentaRealm();
        int diceValue;
        if(controller.isBonusActivated())
            diceValue=6;
        else
            diceValue=controller.getChosenDice().getValue();

        getGuiGameController().makeMove(controller.getCurrentPlayer(),new Move(new MagentaDice(diceValue),null));
        

        int score=magentaRealm.getTotalRealmScore();
        int reaminingAttacks=11-magentaRealm.getTotalNumberOfAttacks();
        int nextMinAttack=magentaRealm.getPhoenix().getMinimumAttackValue();
        remainingAttack.setText("Remaining Attacks: "+reaminingAttacks);
        realmScore.setText("Realm Score: "+score);
        minimumAttack.setText("Minimum Attack: "+nextMinAttack);
        finishAttack();
    }
    @FXML
    private ImageView back;

}
