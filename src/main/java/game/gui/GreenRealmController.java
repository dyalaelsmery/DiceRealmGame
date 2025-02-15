package game.gui;
import game.dice.GreenDice;
import game.engine.GUIGameController;
import game.engine.Move;
import game.realms.GreenRealm;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class GreenRealmController extends SceneController {
    @FXML
    ImageView greenRealm;
    @FXML
    ImageView gaia;
    @FXML
    ImageView life2;
    @FXML
    ImageView life3;
    @FXML
    ImageView life4;
    @FXML
    ImageView life5;
    @FXML
    ImageView life6;
    @FXML
    ImageView life7;
    @FXML
    ImageView life8;
    @FXML
    ImageView life9;
    @FXML
    ImageView life10;
    @FXML
    ImageView life11;
    @FXML
    ImageView life12;
    @FXML
    Label score;
    @FXML
    StackPane stack1;
    @FXML
    StackPane stack2;
    @FXML
    StackPane stack3;
    @FXML
    StackPane stack4;
    @FXML
    StackPane stack5;
    @FXML
    StackPane stack6;
    @FXML
    StackPane stack7;
    @FXML
    StackPane stack8;
    @FXML
    StackPane stack9;
    @FXML
    StackPane stack10;
    @FXML
    StackPane stack11;
    @FXML
    Label playerName;
    public void setGreenRealm(int dice){
        playerName.setText("Player: " + getGuiGameController().getCurrentPlayer().getPlayerName());
        GreenRealm greenRealm = getGuiGameController().getCurrentPlayer().getGreenRealm();
        StackPane[]tempStack=new StackPane[]{stack1,stack2,stack3,stack4,stack5,stack6,stack7,stack8,stack9,stack10,stack11};
        
        //Set all visible&opacity
        for(StackPane temp:tempStack){
            temp.setVisible(true);
            setOpacity(temp, 1);
        }
        //Set opacity based on dice and skip if 
        if(!getGuiGameController().isBonusActivated()){
            for(int i=0;i<tempStack.length;i++)
                if(i+2!=dice)
                    setOpacity(tempStack[i], 0.5);
        }
        //Set all dead hidden
        boolean[] gaiaHealth=greenRealm.getGaia().getGurdiansHealth();
        for(int i=0;i<tempStack.length;i++)
            if(!gaiaHealth[i+2])
                tempStack[i].setVisible(false);

        score.setText("Score: " + greenRealm.getTotalRealmScore() );
    }

    @Override
    public void initialize() {
        Image greenRealmImage = new Image(getClass().getResourceAsStream("/images/greenRealm.jpeg"));
        greenRealm.setImage(greenRealmImage);
        Image gaiaImage = new Image(getClass().getResourceAsStream("/images/gaia.png"));
        gaia.setImage(gaiaImage);
        Image life = new Image(getClass().getResourceAsStream("/images/GaiaHealth.png"));
        ImageView[] imageViews = {life2,life3,life4,life5,life6,life7,life8,life9,life10,life11,life12};
        for (ImageView img: imageViews) {
            img.setImage(life);
        }
    }
    private void setOpacity(StackPane pane, double opacity) {
        pane.setOpacity(opacity);
    }
    @FXML
    public void attack(MouseEvent e) {
        GUIGameController guiController = getGuiGameController();
        if(e.getSource() instanceof StackPane && ((StackPane) e.getSource()).getOpacity() != 0.5){
            String diceNum=(((StackPane)e.getSource()).getId()).substring(5);
            int diceVal=Integer.parseInt(diceNum)+1;//to COmpensate for label name
            GreenDice greenDice=new GreenDice(diceVal);
            Move move = new Move(greenDice, null);
            guiController.makeMove(guiController.getCurrentPlayer(),move);
            finishAttack();
        }
    }
    @FXML
    private void hover(MouseEvent e){
        if(e.getSource() instanceof StackPane && ((StackPane) e.getSource()).getOpacity() != 0.5 ) {
            setOpacity((StackPane) e.getSource(), 0.7);
        }

    }

    @FXML
    private void hoverExit(MouseEvent e){
        if(e.getSource() instanceof StackPane && ((StackPane) e.getSource()).getOpacity() != 0.5 ) {
            setOpacity((StackPane) e.getSource(), 1);
        }
    }
}
