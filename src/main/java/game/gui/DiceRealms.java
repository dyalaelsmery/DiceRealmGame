package game.gui;
import game.engine.GUIGameController;
import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.stage.*;


public class DiceRealms extends Application {
    private static GUIGameController guiGameController;
    MusicPlayer musicPlayer = new MusicPlayer();

    @Override
    public void start(Stage primaryStage) throws Exception {
        guiGameController=new GUIGameController(primaryStage);
        guiGameController.initializeScenes();
        Parent root =FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene mainMenu=new Scene(root);
        primaryStage.setScene(mainMenu);
        primaryStage.setResizable(false);
        Image dragon = new Image(getClass().getResourceAsStream("/images/MainMenu.jpeg"));
        primaryStage.getIcons().add(dragon);
        primaryStage.setTitle("DiceRealms");
        primaryStage.show();
    }
    
    @SuppressWarnings("exports")
    public static GUIGameController getGUIGameController(){
        return guiGameController;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
  
}
