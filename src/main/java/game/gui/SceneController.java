package game.gui;
import game.engine.GUIGameController;
import game.engine.GameStatus;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
public abstract class SceneController {
    private boolean dontCheckRewards;

    public void finishAttack(){
    GUIGameController controller=getGuiGameController();
    GameStatus status=controller.getGameStatus();
    if(!dontCheckRewards&&controller.checkForPossibleRewards()){
        if(!controller.getNextReward()){
        controller.updateScoreSheets();
        return;
        }
    }
    else if(controller.isBonusActivated()){
        controller.resetBonusActivated();//Ending Bonus whatever it is  
    }   
    controller.updateScoreSheets();

    //Possible error here<< if multiple bonuses obtained
    if(!controller.isArcaneSequence()&&!controller.isRoundBonusActivated()){
        status.incrementTurn();
        switch(status.getTurn()){
            case 4://THIS MEANS PASSIVE PLAYER SEQUENCE    
                controller.sendUnusedToForgotten();
            break;
            case 5:
                controller.startArcaneSequence(); 
                controller.resetDiceStatus();
            break;
        }
    } 
    if(controller.isRoundBonusActivated())
        controller.resetRoundBonusActivated();
    dontCheckRewards=false;
    controller.switchScene(GUIScenes.DICE_SCENE);
    }
    
    public void dontCheckRewards(){
        dontCheckRewards=true;
    }

    
    public void enteredObject(MouseEvent e){
        ((Node)e.getSource()).setOpacity(0.8);
    }
    public void exitedObject(MouseEvent e){
        ((Node)e.getSource()).setOpacity(1);
    }


    public void switchToScene(GUIScenes scenes){
        getGuiGameController().switchScene(scenes);
    }

    @SuppressWarnings("exports")
    public GUIGameController getGuiGameController(){
        return DiceRealms.getGUIGameController();
    }

    abstract  public void initialize();//Used to initialize images and other components to prevent errors

     
    
    

}
