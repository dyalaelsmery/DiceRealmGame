package game.gui;

import game.engine.GUIGameController;
import game.engine.Player;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public abstract class ScoreSheetsController {
    public Image getImage(String rew){
        switch (rew.trim()) {
            case "RB": return  new Image(getClass().getResourceAsStream("/images/RedColorPicker.jpg"));
            case "GB": return new Image(getClass().getResourceAsStream("/images/GreenColorPicker.jpg"));
            case "BB": return new Image(getClass().getResourceAsStream("/images/BlueColorPicker.jpg"));
            case "MB": return new Image(getClass().getResourceAsStream("/images/MagentaColorPicker.jpg"));
            case "YB": return new Image(getClass().getResourceAsStream("/images/yellowBackground.png"));
            case "EB": return new Image(getClass().getResourceAsStream("/images/EssenceBonus.png"));
            case "EC": return new Image(getClass().getResourceAsStream("/images/ElementalCrest.png"));
            case "AB": return new Image(getClass().getResourceAsStream("/images/ArcaneBoost.png"));
            case "TW": return new Image(getClass().getResourceAsStream("/images/TimeWarp.png"));
            case "X":  return new Image(getClass().getResourceAsStream("/images/x.png"));
            default:
                return null;
        }
        
        
    }
    
    @SuppressWarnings("exports")
    public GUIGameController getGuiGameController(){
        return DiceRealms.getGUIGameController();
    }
    public abstract void switchScoreScene(MouseEvent e);
    @SuppressWarnings("exports")
    public void switchScoreScreen(char color,Player player){
        if(player==getGuiGameController().getGameBoard().getPlayer1())
            getGuiGameController().getPlayer1ScoreSheetController().switchScoreSheets(color);
        else    
            getGuiGameController().getPlayer2ScoreSheetController().switchScoreSheets(color);

    }
    abstract  public void initialize();//Used to initialize images and other components to prevent errors

     
    
 
}
