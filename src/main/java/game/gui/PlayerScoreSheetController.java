package game.gui;

import game.engine.Player;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PlayerScoreSheetController {
    Player player;
    Stage stage;

    private Scene redScoreSheet;
    private RScoreController redController;
    //
    private Scene greenScoreSheet;
    private GScoreController greenController;
    //
    private Scene blueScoreSheet;
    private BScoreController blueController;
    //
    private Scene yellowScoreSheet;
    private YScoreController yellowController;
    //
    private Scene magentaScoreSheet;
    private MScoreController magentaController;

    


    @SuppressWarnings("exports")
    public PlayerScoreSheetController(Player player,Stage stage){
        this.player=player;
        this.stage=stage;
            try{
            FXMLLoader redLoader = new FXMLLoader(getClass().getResource("RScoreSheet.fxml"));
            Parent redRoot = redLoader.load();
            redScoreSheet=new Scene(redRoot);
            redController =(RScoreController) redLoader.getController();
    
        
             FXMLLoader greenLoader = new FXMLLoader(getClass().getResource("GScoreSheet.fxml"));
             Parent greenRoot = greenLoader.load();
             greenScoreSheet=new Scene(greenRoot);
             greenController =(GScoreController) greenLoader.getController();
             
             

            FXMLLoader blueLoader = new FXMLLoader(getClass().getResource("BScoreSheet.fxml"));
            Parent blueRoot = blueLoader.load();
            blueScoreSheet=new Scene(blueRoot);
            blueController =(BScoreController) blueLoader.getController();

            FXMLLoader magentaLoader = new FXMLLoader(getClass().getResource("MScoreSheet.fxml"));
            Parent magentaRoot = magentaLoader.load();
            magentaScoreSheet=new Scene(magentaRoot);
            magentaController =(MScoreController) magentaLoader.getController();
    
            FXMLLoader yellowLoader = new FXMLLoader(getClass().getResource("YScoreSheet.fxml"));
            Parent yellowRoot = yellowLoader.load();
            yellowScoreSheet=new Scene(yellowRoot);
            yellowController =(YScoreController) yellowLoader.getController();
            }catch(Exception ex){
                ex.printStackTrace();
                System.out.println("Couldn't load Score sheets");
            }
            redController.setScoreSheet(player);
            greenController.setScoreSheet(player);
            blueController.setScoreSheet(player);
            magentaController.setScoreSheet(player);
            yellowController.setScoreSheet(player);




        stage.setScene(redScoreSheet);
    }
    @SuppressWarnings("exports")
    public Player getScoreSheetPlayer(){
        return player;
    }
    public void updateScoreSheets(){
        if(stage.getScene()==redScoreSheet)
        redController.updateRedScoreSheet();
        else if(stage.getScene()==greenScoreSheet)
        greenController.updateGreenScoreSheet();
        else if(stage.getScene()==blueScoreSheet)
        blueController.updateBlueScoreSheet();
        else if(stage.getScene()==magentaScoreSheet)
        magentaController.updateMagentaScoreSheet();
        else if(stage.getScene()==yellowScoreSheet)
        yellowController.updateYellowScoreSheet();
        else
        System.out.println("An error occured");
    }
    public void switchScoreSheets(char color){
        switch(color){
            case 'R':
            redController.updateRedScoreSheet();
            stage.setScene(redScoreSheet);break;
            case 'G':
            greenController.updateGreenScoreSheet();
            stage.setScene(greenScoreSheet);break;
            case 'B':
            blueController.updateBlueScoreSheet();
            stage.setScene(blueScoreSheet);break;
            case 'M':
            magentaController.updateMagentaScoreSheet();
            stage.setScene(magentaScoreSheet);break;
            case 'Y':
            yellowController.updateYellowScoreSheet();
            stage.setScene(yellowScoreSheet);break;
        }
    }
 

}
