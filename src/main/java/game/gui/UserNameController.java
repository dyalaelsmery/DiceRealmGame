package game.gui;

import game.engine.GUIGameController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class UserNameController extends SceneController{
    Image player1_still;
    Image player2_still;
    Image player1_cast;
    Image player2_cast;

    @Override
    public void initialize() {
             player1_still = new Image(getClass().getResourceAsStream("/images/RedWizardStill.png"));
             player2_still = new Image(getClass().getResourceAsStream("/images/BlueWizardStill.png"));
             player1_cast = new Image(getClass().getResourceAsStream("/images/RedWizardCast.png"));
             player2_cast = new Image(getClass().getResourceAsStream("/images/BlueWizardCast.png"));
            Image back = new Image(getClass().getResourceAsStream("/images/UserNameBackground.jpg"));
            background.setImage(back);
            Player_1.setImage(player1_still);
            Player_2.setImage(player2_still);
            Player_1_dynamic.setText("");
            Player_2_dynamic.setText("");
            
    }
    public void submit(ActionEvent e){
        if(Player_1_name.getText().length()==0||Player_2_name.getText().length()==0){
            SystemMessage.setVisible(true);
            SystemMessage.setText("Username cant be blank");
            return;
        }
        if(Player_1_name.getText().length()>12||Player_2_name.getText().length()>12){
            SystemMessage.setVisible(true);
            SystemMessage.setText("Username cant exceed 9 characters");
            return;
        }
        char p1=Player_1_name.getText().toLowerCase().charAt(0);
        char p2=Player_2_name.getText().toLowerCase().charAt(0);

        if(p1<'a'||p1>'z'||p2<'a'||p2>'z'){
            SystemMessage.setVisible(true);
            SystemMessage.setText("Username has to start with a letter");
            return;
        }
        if(Player_1_name.getText().equals(Player_2_name.getText())){
            SystemMessage.setVisible(true);
            SystemMessage.setText("Both players cant have same name");
            return;
        }
        GUIGameController controller=getGuiGameController();
//        controller.getGameBoard().getPlayer1().setPlayerName(Player_1_name.getText());
//        controller.getGameBoard().getPlayer2().setPlayerName(Player_2_name.getText());
      

        controller.getPlayerSelectorController().setNames(Player_1_name.getText(),Player_2_name.getText());
        controller.switchScene(GUIScenes.Player_Selector_Scene);
//        controller.switchScene(GUIScenes.DICE_SCENE);
    }
    public void selectWizard(MouseEvent e){
        Player_1.setImage(player1_still);
        Player_1.setLayoutY(363);
        Player_2.setImage(player2_still);
        Player_2.setLayoutY(367);

        if(e.getSource()==Player_1_name){
            Player_1.setImage(player1_cast);
            Player_1.setLayoutY(378);
        }
        else if(e.getSource()==Player_2_name){
            Player_2.setImage(player2_cast);  
            Player_2.setLayoutY(386);

        }else{
            Player_1_dynamic.requestFocus();
        }
    }
    public void updateWizardName(KeyEvent e){
        if(e.getSource()==Player_1_name){
            if(Player_1_name.getText().length()>9){
                Player_1_name.setText(prevText1);
                SystemMessage.setText("Username cant exceed 9 characters");
                SystemMessage.setVisible(true);
                return;
            }
            Player_1_dynamic.setText(Player_1_name.getText());
            prevText1=Player_1_name.getText();
        }
        else if(e.getSource()==Player_2_name){
            if(Player_2_name.getText().length()>9){
                Player_2_name.setText(prevText2);
                SystemMessage.setText("Username cant exceed 9 characters");
                SystemMessage.setVisible(true);
                return;
            }
            Player_2_dynamic.setText(Player_2_name.getText());
            prevText2=Player_2_name.getText();
        }//363 376
    }
    @FXML
    private ImageView Player_1;

    @FXML
    private Label Player_1_dynamic;

    @FXML
    private TextField Player_1_name;

    @FXML
    private ImageView Player_2;

    @FXML
    private Label Player_2_dynamic;

    @FXML
    private TextField Player_2_name;

    @FXML
    private Label SystemMessage;
    @FXML 
    private ImageView background;

    private String prevText1;
    private String prevText2;
}
