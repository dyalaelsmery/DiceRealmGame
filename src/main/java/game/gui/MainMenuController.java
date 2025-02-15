package game.gui;



import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
public class MainMenuController extends SceneController{
    boolean tried;

    @Override
    public void initialize(){
        Image backgrounds = new Image(getClass().getResourceAsStream("/images/MainMenu.jpeg"));
        background.setImage(backgrounds);
        Image multiplayer = new Image(getClass().getResourceAsStream("/images/MultiplayerButton.png"));
        multi.setImage(multiplayer);
        Image singleplayer = new Image(getClass().getResourceAsStream("/images/SingleplayerButton.png"));
        single.setImage(singleplayer);
        Image helpp = new Image(getClass().getResourceAsStream("/images/Question.png"));
        help.setImage(helpp);

    }
    public void startGame(MouseEvent e)throws Exception{
        getGuiGameController().switchScene(GUIScenes.Username_Scene);
//        getGuiGameController().switchScene(GUIScenes.Player_Selector_Scene);

//       getGuiGameController().switchScene(GUIScenes.DICE_SCENE);
    }   
    public void startGameAI(MouseEvent e){
        if(tried)
            return;
        Image wip = new Image(getClass().getResourceAsStream("/images/WIPButton.png"));
        single.setImage(wip);
        tried=true;
    } 
 public void enteredObject(MouseEvent e){
        ImageView x=(ImageView)e.getSource();
        if(tried&&x==single)
            return;
        ((Node)e.getSource()).setOpacity(0.8);
    }
    public void exitedObject(MouseEvent e){
        ((Node)e.getSource()).setOpacity(1);
    }
    public void openHelp(MouseEvent e){
          try{
            Stage stage= getGuiGameController().getStage();
            Parent root =FXMLLoader.load(getClass().getResource("instructions.fxml"));
             Scene instructions=new Scene(root);
             stage.setScene(instructions);
             }catch(Exception ex){
                 System.out.println("Couldnt load main");
             }
     

    }

    @FXML
    private ImageView background;

    @FXML
    private ImageView multi;

    @FXML
    private ImageView single;
    @FXML
    private ImageView help;


}
