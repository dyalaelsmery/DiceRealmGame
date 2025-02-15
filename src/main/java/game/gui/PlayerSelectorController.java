package game.gui;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.util.Random;

public class PlayerSelectorController extends SceneController {
    @FXML
    StackPane leftDice;
    @FXML
    StackPane rightDice; // Ensure the ID matches the FXML file
    @FXML
    ImageView background;
    @FXML
    Label info;
    @FXML
    Label player1;
    @FXML
    Label player2;
    @FXML
    Button button;
    private Image[] dices;
    private boolean hasRolled = false;

    @Override
    public void initialize() {
        button.setVisible(false);
        Image chooseBackground = new Image(getClass().getResourceAsStream("/images/choosePlayer.png"));
        background.setImage(chooseBackground);

        dices = new Image[]{
                new Image(getClass().getResourceAsStream("/images/DiceImages/WHITE1.png")),
                new Image(getClass().getResourceAsStream("/images/DiceImages/WHITE2.png")),
                new Image(getClass().getResourceAsStream("/images/DiceImages/WHITE3.png")),
                new Image(getClass().getResourceAsStream("/images/DiceImages/WHITE4.png")),
                new Image(getClass().getResourceAsStream("/images/DiceImages/WHITE5.png")),
                new Image(getClass().getResourceAsStream("/images/DiceImages/WHITE6.png"))
        };

        ObservableList<Node> leftDices = leftDice.getChildren();
        ObservableList<Node> rightDices = rightDice.getChildren();

        for (int i = 0; i < 6; i++) {
            ((ImageView) leftDices.get(i)).setImage(dices[i]);
            ((ImageView) rightDices.get(i)).setImage(dices[i]);
        }
    }
    public void setNames(String player1, String player2){
        this.player1.setText(player1);
        this.player2.setText(player2);
    }

    @FXML
    public void rollDice(MouseEvent e) {
        if (!hasRolled) {
            hasRolled = true;
            Random random = new Random();
            int leftRoll = random.nextInt(6);
            int rightRoll = random.nextInt(6);
            if(leftRoll == rightRoll){
                hasRolled = false;
                info.setText("Roll Again!");
                return;
            }

            for (Node node : leftDice.getChildren())
                node.setOpacity(0);

            for (Node node : rightDice.getChildren())
                node.setOpacity(0);

            if(leftRoll > rightRoll) {
                info.setText(player1.getText() + " is the active player");
                getGuiGameController().getGameBoard().getPlayer1().setPlayerName(player1.getText());
                getGuiGameController().getGameBoard().getPlayer2().setPlayerName(player2.getText());
            }

            else {
                info.setText(player2.getText() + " is the active player");
                getGuiGameController().getGameBoard().getPlayer1().setPlayerName(player2.getText());
                getGuiGameController().getGameBoard().getPlayer2().setPlayerName(player1.getText());
            }
            button.setText("Press to Continue");
            button.setVisible(true);
            ((ImageView) leftDice.getChildren().get(leftRoll)).setOpacity(1);
            ((ImageView) rightDice.getChildren().get(rightRoll)).setOpacity(1);
        }
    }
    @FXML
    public void switchToDiceScreen(ActionEvent e){

        try{
            getGuiGameController().initializeScoreSheet(getGuiGameController().getGameBoard().getPlayer1().getPlayerName()
            ,getGuiGameController().getGameBoard().getPlayer2().getPlayerName());
            }catch(Exception EX){EX.printStackTrace();}
        switchToScene(GUIScenes.DICE_SCENE);
        return;
    }
}
