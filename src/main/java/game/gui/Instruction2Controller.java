package game.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Instruction2Controller extends SceneController{

    public void back(ActionEvent e){
        try{
            Stage stage= getGuiGameController().getStage();
            Parent root =FXMLLoader.load(getClass().getResource("instructions.fxml"));
             Scene instructions=new Scene(root);
             stage.setScene(instructions);
             }catch(Exception ex){
                 System.out.println("Couldnt load main");
             }
     

    }
    public void end(ActionEvent e){
        try{
       Stage stage= getGuiGameController().getStage();
       Parent root =FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene mainMenu=new Scene(root);
        stage.setScene(mainMenu);
        }catch(Exception ex){
            System.out.println("Couldnt load main");
        }

    }
    @Override
    public void initialize() {
        Image background = new Image(getClass().getResourceAsStream("/images/SecondHelp.jpeg"));
        back.setImage(background);
        
    }
    @FXML
    private ImageView back;

}
