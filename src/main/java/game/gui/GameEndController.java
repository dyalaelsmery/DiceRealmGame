package game.gui;

import game.engine.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameEndController extends SceneController{

    public void updateScene(){
       Player player1= getGuiGameController().getGameBoard().getPlayer1();
       Player player2= getGuiGameController().getGameBoard().getPlayer2();
       int score1=player1.getGameScore().getTotalGameScore();
       int score2=player2.getGameScore().getTotalGameScore();
       if(score1>score2){
         background.setImage(new Image(getClass().getResourceAsStream("/images/EndRedWizard.jpg")));
         winsLabel.setText(player1.getPlayerName()+" Wins!!!!!");

       }else if(score2>score1){
        background.setImage(new Image(getClass().getResourceAsStream("/images/EndBlueWizard.jpg")));
        winsLabel.setText(player2.getPlayerName()+" Wins!!!!!");

       }else{
        background.setImage(new Image(getClass().getResourceAsStream("/images/EndBlueWizard.jpg")));
        winsLabel.setText(player2.getPlayerName()+" It's a Draw!!!!!");
       }
    }

    @Override
    public void initialize() {
   
    }
    @FXML 
    private ImageView background;
    @FXML
    private Label winsLabel;

}
