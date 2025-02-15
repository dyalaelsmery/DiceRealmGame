package game.gui;

import game.dice.Dice;
import game.engine.GUIGameController;
import game.engine.Player;
import game.realms.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class ColorPickerController extends SceneController{
     
    public void red(MouseEvent e){
        if(red_B.getBrightness()!=0){
            System_Message.setText("There are no possible moves in this ream");
            return;
        }
        switchToScene(GUIScenes.RED_REALM_SCENE);
    }
    public void green(MouseEvent e){
        if(green_B.getBrightness()!=0){
            System_Message.setText("There are no possible moves in this ream");
            return;
        }
        switchToScene(GUIScenes.GREEN_REALM_SCENE);
    }
    public void blue(MouseEvent e){
        if(blue_B.getBrightness()!=0){
            System_Message.setText("There are no possible moves in this ream");
            return;
        }
        switchToScene(GUIScenes.BLUE_REALM_SCENE);
    }
    public void magetna(MouseEvent e){
        if(magenta_B.getBrightness()!=0){
            System_Message.setText("There are no possible moves in this ream");
            return;
        }
        switchToScene(GUIScenes.MAGENTA_REALM_SCENE);
    }
    public void yellow(MouseEvent e){
        if(yellow_B.getBrightness()!=0){
            System_Message.setText("There are no possible moves in this ream");
            return;
        }
        
        switchToScene(GUIScenes.YELLOW_REALM_SCENE);
    }
//============OPACITY======================
    public void enteredObject(MouseEvent e){
        ImageView imageView=(ImageView)e.getSource();
        Effect effect=imageView.getEffect();
        if(effect!=null&&effect instanceof ColorAdjust&&((ColorAdjust)imageView.getEffect()).getBrightness()!=0)
            return;
        imageView.setOpacity(0.8);
        InnerShadow innerShadow = new InnerShadow();
        
        if(imageView==red)
        innerShadow.setColor(Color.RED); 
        else if(imageView==green)
        innerShadow.setColor(Color.GREEN); 
        else if(imageView==blue)
        innerShadow.setColor(Color.BLUE); 
        else if(imageView==magenta)
        innerShadow.setColor(Color.MAGENTA); 
        else
        innerShadow.setColor(Color.YELLOW); 

        innerShadow.setChoke(0.3);
        innerShadow.setWidth(50); 
        innerShadow.setHeight(50); 
        innerShadow.setRadius(25);
        imageView.setEffect(innerShadow);
    }
    public void exitedObject(MouseEvent e){
        ImageView imageView=(ImageView)e.getSource();
        Effect effect=imageView.getEffect();
        if(effect!=null&&effect instanceof ColorAdjust&&((ColorAdjust)imageView.getEffect()).getBrightness()!=0)
            return;
        imageView.setOpacity(1);    
        imageView.setEffect(null);
    }

    public void updateScene(){
        GUIGameController controller=getGuiGameController();
        Player currentPlayer=controller.getCurrentPlayer();
        Dice whiteDice=controller.getGameBoard().getArcanePrism();
        RedRealm redrealm=currentPlayer.getRedRealm();
        GreenRealm greenRealm=currentPlayer.getGreenRealm();
        BlueRealm blueRealm=currentPlayer.getBlueRealm();
        MagentaRealm magentaRealm=currentPlayer.getMagentaRealm();
        YellowRealm yellowRealm=currentPlayer.getYellowRealm();
        
        int greenPlusWhite=whiteDice.getValue()+controller.getGameBoard().getGreenDice().getValue();
        red_B=new ColorAdjust();
        green_B=new ColorAdjust();
        blue_B=new ColorAdjust();
        magenta_B=new ColorAdjust();
        yellow_B=new ColorAdjust();
        if(redrealm.isRealmDefeated()||(!controller.isBonusActivated()&&redrealm.getPossibleMovesForADie(whiteDice.getValue(),whiteDice.getDiceColor(), whiteDice).length==0)){
            red_B.setBrightness(-0.65);

        }else{
            red_B.setBrightness(0);
        }
        if(greenRealm.isRealmDefeated()||(!controller.isBonusActivated()&&greenRealm.getPossibleMovesForADie(greenPlusWhite,whiteDice.getDiceColor(), whiteDice).length==0)){
            green_B.setBrightness(-0.65);

        }else{
            green_B.setBrightness(0);

        }
        if(blueRealm.isRealmDefeated()||(!controller.isBonusActivated()&&blueRealm.getPossibleMovesForADie(whiteDice.getValue(),whiteDice.getDiceColor(), whiteDice).length==0)){
            blue_B.setBrightness(-0.65);

        }else{
            blue_B.setBrightness(0);

        }
        if(magentaRealm.isRealmDefeated()||(!controller.isBonusActivated()&&magentaRealm.getPossibleMovesForADie(whiteDice.getValue(),whiteDice.getDiceColor(), whiteDice).length==0)){
            magenta_B.setBrightness(-0.65);

        }else{
            magenta_B.setBrightness(0);

        }
        if(yellowRealm.isRealmDefeated()||(!controller.isBonusActivated()&&yellowRealm.getPossibleMovesForADie(whiteDice.getValue(),whiteDice.getDiceColor(), whiteDice).length==0)){
            yellow_B.setBrightness(-0.65);

        }else{
            yellow_B.setBrightness(0);

        }
        red.setEffect(red_B);
        green.setEffect(green_B);
        blue.setEffect(blue_B);
        magenta.setEffect(magenta_B);
        yellow.setEffect(yellow_B);       

    }
    
    @Override
    public void initialize() {
            Image redImage = new Image(getClass().getResourceAsStream("/images/RedColorPicker.jpg"));
            red.setImage(redImage);
            Image blueImage = new Image(getClass().getResourceAsStream("/images/BlueColorPicker.jpg"));
            blue.setImage(blueImage);
            Image greenImage = new Image(getClass().getResourceAsStream("/images/GreenColorPicker.jpg"));
            green.setImage(greenImage);
            Image magentaImage = new Image(getClass().getResourceAsStream("/images/MagentaColorPicker.jpg"));
            magenta.setImage(magentaImage);
            Image yellowImage = new Image(getClass().getResourceAsStream("/images/YellowColorPicker.jpg"));
            yellow.setImage(yellowImage);

    }
    @FXML
    private Label System_Message;
    @FXML
    private ImageView blue;

    @FXML
    private ImageView green;

    @FXML
    private ImageView magenta;

    @FXML
    private ImageView red;

    @FXML
    private ImageView yellow;

    private ColorAdjust red_B;
    private ColorAdjust green_B;
    private ColorAdjust blue_B;
    private ColorAdjust magenta_B;
    private ColorAdjust yellow_B;


}
