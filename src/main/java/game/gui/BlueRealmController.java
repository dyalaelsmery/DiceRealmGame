package game.gui;

import game.dice.BlueDice;
import game.engine.GUIGameController;
import game.engine.Move;
import game.engine.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class BlueRealmController extends SceneController{
    @FXML
    ImageView creature2;
    @FXML
    ImageView creature1;
    @FXML
    ImageView head1;
    @FXML
    ImageView head2;
    @FXML
    ImageView head3;
    @FXML
    ImageView head4;
    @FXML
    ImageView head5;
    @FXML
    ImageView head6;
    @FXML
    Label score;
    @FXML
    Label playerName;
    boolean[] player1Hits = {false,false,false,false,true,false};
    boolean[] player2Hits = {false,false,false,false,true,false};

    @Override
    public void initialize() {
        Image hydra5 = new Image(getClass().getResourceAsStream("/images/Hydra5.jpg"));
        Image hydra6 = new Image(getClass().getResourceAsStream("/images/Hydra6.png"));
        Image button = new Image(getClass().getResourceAsStream("/images/BlueButton.png"));
        head5.setVisible(false);
        creature1.setImage(hydra5);
        creature2.setImage(hydra6);
        creature2.setVisible(false);
        ImageView[] imageViews = new ImageView[]{head1,head2,head3,head4,head5,head6};
        for(ImageView imageView: imageViews) {
            imageView.setImage(button);
        }

    }

    public void setBlueRealm(){
        GUIGameController controller = getGuiGameController();
        playerName.setText("Player: " + controller.getCurrentPlayer().getPlayerName());
        Player player = getGuiGameController().getCurrentPlayer();
        score.setText("Score: " + player.getBlueRealm().getTotalRealmScore());
        ImageView[] imageViews = new ImageView[]{head1,head2,head3,head4,head5,head6};
        if (player.getBlueRealm().getTotalNumberOfAttacks() == 5){
            creature1.setVisible(false);
            creature2.setVisible(true);
            for(ImageView imageView: imageViews) {
                imageView.setVisible(true);
            }
            if(player == controller.getGameBoard().getPlayer1())
                for(int i = 0; i < 6; i++){
                    player1Hits[i] = false;
                }
            else{
                for(int i = 0; i < 6; i++){
                    player2Hits[i] = false;
                }
            }
        }
        else {
            if(player.getBlueRealm().getTotalNumberOfAttacks() < 5) {
                creature1.setVisible(true);
                creature2.setVisible(false);
            }
            else{
                creature1.setVisible(false);
                creature2.setVisible(true);
            }
            if(controller.getGameBoard().getPlayer1() == player){
                for(int i = 0; i<imageViews.length; i++) {
                    if(!player1Hits[i])
                        imageViews[i].setVisible(true);
                    else
                        imageViews[i].setVisible(false);
                }
            }
            else {
                for(int i = 0; i<imageViews.length; i++) {
                    if(!player2Hits[i])
                        imageViews[i].setVisible(true);
                    else
                        imageViews[i].setVisible(false);
                }
            }
        }
        if(controller.getGameBoard().getPlayer1() == player){
            for(int i = 0; i<imageViews.length; i++) {
                if(!player1Hits[i])
                    imageViews[i].setVisible(true);
                else
                    imageViews[i].setVisible(false);
            }
        }
        else {
            for(int i = 0; i<imageViews.length; i++) {
                if(!player2Hits[i])
                    imageViews[i].setVisible(true);
                else
                    imageViews[i].setVisible(false);
            }
        }
    }

    @FXML
    public void attack(MouseEvent e){
        GUIGameController controller = getGuiGameController();
        Player player = getGuiGameController().getCurrentPlayer();
        int diceval = controller.getChosenDice().getValue();
        BlueDice blueDice = new BlueDice(diceval);
        Move move = new Move(blueDice, null);
        if(e.getSource() instanceof ImageView){
            String head = ((ImageView) e.getSource()).getId();
            if(controller.getGameBoard().getPlayer1() == player) {
                switch (head) {
                    case "head1": player1Hits[0] = true; break;
                    case "head2": player1Hits[1] = true; break;
                    case "head3": player1Hits[2] = true; break;
                    case "head4": player1Hits[3] = true; break;
                    case "head5": player1Hits[4] = true; break;
                    case "head6": player1Hits[5] = true; break;

                }
            }
            else {
                switch (head) {
                    case "head1": player2Hits[0] = true; break;
                    case "head2": player2Hits[1] = true; break;
                    case "head3": player2Hits[2] = true; break;
                    case "head4": player2Hits[3] = true; break;
                    case "head5": player2Hits[4] = true; break;
                    case "head6": player2Hits[5] = true; break;
                }
            }
            if (controller.isBonusActivated()){
                ((ImageView) e.getSource()).setVisible(false);
                blueDice = new BlueDice(6);
                move = new Move(blueDice, null);
                controller.makeMove(player, move);
            }
            else {
                ((ImageView) e.getSource()).setVisible(false);
                controller.makeMove(player, move);
            }
        }
        finishAttack();
    }
    @FXML
    private void hover(MouseEvent e){
        if(e.getSource() instanceof ImageView && ((ImageView) e.getSource()).getOpacity() != 0.5){
            ((ImageView) e.getSource()).setOpacity(0.7);
        }
    }

    @FXML
    private void hoverExit(MouseEvent e){
        if(e.getSource() instanceof ImageView && ((ImageView) e.getSource()).getOpacity() != 0.5){
            ((ImageView) e.getSource()).setOpacity(1);
        }
    }
}