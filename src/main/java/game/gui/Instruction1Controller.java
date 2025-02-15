package game.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Instruction1Controller extends SceneController{

    public void next(ActionEvent e){
        try{
            Stage stage= getGuiGameController().getStage();
            Parent root =FXMLLoader.load(getClass().getResource("instruction2.fxml"));
             Scene instruction2=new Scene(root);
             stage.setScene(instruction2);
             }catch(Exception ex){
                 System.out.println("Couldnt load main");
             }
     

    }
        @Override
    public void initialize() {
        Image background = new Image(getClass().getResourceAsStream("/images/FirstHelp.jpeg"));
        back.setImage(background);
    }
    @FXML
    private ImageView back;

}
