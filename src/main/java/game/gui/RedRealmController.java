package game.gui;

import game.creatures.Dragon;
import game.dice.RedDice;
import game.engine.GUIGameController;
import game.engine.Move;
import game.realms.RedRealm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;

public class RedRealmController extends SceneController {
    @FXML
    ImageView dragon;
    @FXML
    ImageView realm;
    @FXML
    Label dragonNum;
    @FXML
    Button dragon1;
    @FXML
    Button dragon2;
    @FXML
    Button dragon3;
    @FXML
    Button dragon4;
    @FXML
    Circle faceHit;
    @FXML
    Circle heartHit;
    @FXML
    Circle wingsHit;
    @FXML
    Circle tailHit;
    @FXML
    Label faceHitValue;
    @FXML
    Label heartHitValue;
    @FXML
    Label wingsHitValue;
    @FXML
    Label tailHitValue;
    @FXML
    StackPane facePane;
    @FXML
    StackPane heartPane;
    @FXML
    StackPane wingsPane;
    @FXML
    StackPane tailPane;
    @FXML
    Label score;
    @FXML
    Label playerName;
    @Override
    public void initialize() {
        Stop[] stops=new Stop[]{new Stop(0,Color.RED),new Stop(0.55, Color.ORANGE),new Stop(1, Color.YELLOW)};
        LinearGradient linearGradient=new LinearGradient(0, 0, 0.5, 0.5, true,javafx.scene.paint.CycleMethod.NO_CYCLE, stops);
        dragon1.setBackground(new Background(new BackgroundFill(linearGradient,null,null)));
        dragon2.setBackground(new Background(new BackgroundFill(linearGradient,null,null)));
        dragon3.setBackground(new Background(new BackgroundFill(linearGradient,null,null)));
        dragon4.setBackground(new Background(new BackgroundFill(linearGradient,null,null)));

        score.setText("Score: 0");
        dragonNum.setText("Dragon 1");
        faceHitValue.setText("3");
        wingsHitValue.setText("2");
        tailHitValue.setText("1");
        heartPane.setVisible(false);
        Image dragonImage = new Image(getClass().getResourceAsStream("/images/RedDragon.png"));
        Image realmImage = new Image(getClass().getResourceAsStream("/images/RedRealm.jpeg"));
        dragon.setImage(dragonImage);
        realm.setImage(realmImage);
        ActionEvent e = new ActionEvent();
        switchDragon(e);
    }

    public void setRedRealm(int dice) {
        GUIGameController controller = getGuiGameController();
        score.setText("Score: "+ controller.getCurrentPlayer().getRedRealm().getTotalRealmScore());
        playerName.setText("Player: " + controller.getCurrentPlayer().getPlayerName());
        Button[] buttons = new Button[]{dragon1,dragon2,dragon3,dragon4};
        RedRealm redRealm = controller.getCurrentPlayer().getRedRealm();
        Dragon[] dragons = {redRealm.getDragon1(),redRealm.getDragon2(),redRealm.getDragon3(),redRealm.getDragon4()};
        boolean fired=false;

        for(int i=0;i<dragons.length;i++){
            Dragon currentDragon=dragons[i];
            if((controller.isBonusActivated()||currentDragon.valueInDragon(dice))&&!currentDragon.isDeadDragon()){
                buttons[i].setOpacity(1);
                buttons[i].setDisable(false);
                if(!fired){
                    fired=true;
                    buttons[i].fire();}
            }else{
                buttons[i].setOpacity(0.2);
                buttons[i].setDisable(true);
            }   
        }
    }
    @SuppressWarnings("exports")
    public void setDragonCircles(Dragon dragon,int dice){
        GUIGameController controller = getGuiGameController();
        if (dragon.getFace() == dice||controller.isBonusActivated() && !dragon.isFaceKilled())
            facePane.setOpacity(1);
        else
            facePane.setOpacity(0.5);
        if(dragon.getWings() == dice ||controller.isBonusActivated() && !dragon.isWingsKilled())
            wingsPane.setOpacity(1);
        else
            wingsPane.setOpacity(0.5);
        if(dragon.getTail() == dice ||controller.isBonusActivated() && !dragon.isTailKilled())
            tailPane.setOpacity(1);
        else
            tailPane.setOpacity(0.5);
        if (dragon.getHeart() == dice ||controller.isBonusActivated() && !dragon.isHeartKilled())
            heartPane.setOpacity(1);
        else
            heartPane.setOpacity(0.5);

    }

    @FXML
    private void switchDragon(ActionEvent e){
        RedRealm redRealm = getGuiGameController().getCurrentPlayer().getRedRealm();
        GUIGameController controller = getGuiGameController();
        Button[] buttons = new Button[]{dragon1,dragon2,dragon3,dragon4};
        for(Button button: buttons) {
            if(button.getOpacity() != 0.2)
                button.setOpacity(1);
        }
        if(e.getSource() instanceof Button){
            dragonNum.setText(((Button) e.getSource()).getText());
            switch (dragonNum.getText()){
                case "Dragon 1":
                    dragon1.setOpacity(0.7);
                    setDragonCircles(redRealm.getDragon1(), controller.getChosenDice().getValue());
                    faceHitValue.setText("3"); 
                    wingsHitValue.setText("2");
                    tailHitValue.setText("1");
                    heartPane.setVisible(false);

                    if(!redRealm.getDragon1().isFaceKilled())
                        facePane.setVisible(true);
                    else
                        facePane.setVisible(false);

                    if (!redRealm.getDragon1().isWingsKilled()) 
                        wingsPane.setVisible(true);
                    else
                        wingsPane.setVisible(false);
                 
                    if (!redRealm.getDragon1().isTailKilled()) 
                        tailPane.setVisible(true);
                    else
                        tailPane.setVisible(false);
                    break;
                case "Dragon 2":
                    dragon2.setOpacity(0.7);
                    setDragonCircles(redRealm.getDragon2(), controller.getChosenDice().getValue());
                    faceHitValue.setText("6");
                    wingsHitValue.setText("1");
                    tailPane.setVisible(false);
                    heartHitValue.setText("3");

                    if(!redRealm.getDragon2().isFaceKilled())
                        facePane.setVisible(true);
                    else
                        facePane.setVisible(false);

                    if (!redRealm.getDragon2().isWingsKilled()) 
                        wingsPane.setVisible(true);
                    else
                        wingsPane.setVisible(false);

                    if (!redRealm.getDragon2().isHeartKilled()) 
                        heartPane.setVisible(true);
                    else
                        heartPane.setVisible(false);

                    break;
                case "Dragon 3":
                    dragon3.setOpacity(0.7);
                    setDragonCircles(redRealm.getDragon3(), controller.getChosenDice().getValue());
                    faceHitValue.setText("5");
                    wingsPane.setVisible(false);
                    tailHitValue.setText("2");
                    heartHitValue.setText("4");

                    if(!redRealm.getDragon3().isFaceKilled())
                        facePane.setVisible(true);
                    else
                        facePane.setVisible(false);

                    if (!redRealm.getDragon3().isTailKilled()) 
                        tailPane.setVisible(true);
                    else
                        tailPane.setVisible(false);

                    if (!redRealm.getDragon3().isHeartKilled()) 
                        heartPane.setVisible(true);
                    else
                        heartPane.setVisible(false);    
                    break;
                case "Dragon 4":
                    dragon4.setOpacity(0.7);
                    setDragonCircles(redRealm.getDragon4(), controller.getChosenDice().getValue());
                    facePane.setVisible(false);
                    wingsHitValue.setText("5");
                    tailHitValue.setText("4");
                    heartHitValue.setText("6");
                    heartPane.setVisible(true);
                    if (!redRealm.getDragon4().isWingsKilled()) 
                        wingsPane.setVisible(true);
                    else
                        wingsPane.setVisible(false);
                 
                    if (!redRealm.getDragon4().isTailKilled()) 
                        tailPane.setVisible(true);
                    else
                        tailPane.setVisible(false);
                    
                    if (!redRealm.getDragon4().isHeartKilled()) 
                        heartPane.setVisible(true);
                    else 
                        heartPane.setVisible(false);

                    break;

            }
        }
    }
    @FXML
    public void attack(MouseEvent e) {
        GUIGameController controller = getGuiGameController();
        RedRealm redRealm = controller.getCurrentPlayer().getRedRealm();
        if(e.getSource() instanceof StackPane && ((StackPane) e.getSource()).getOpacity() != 0.5){
            ((StackPane) e.getSource()).setVisible(false);
            Dragon chosendrag = null;
            switch (dragonNum.getText()){
                case "Dragon 1": chosendrag = redRealm.getDragon1();break;
                case "Dragon 2": chosendrag = redRealm.getDragon2();break;
                case "Dragon 3": chosendrag = redRealm.getDragon3();break;
                case "Dragon 4": chosendrag = redRealm.getDragon4();break;
            }
            Label label = (Label) ((StackPane) e.getSource()).getChildren().get(1);
            int dice = Integer.parseInt(label.getText());
            RedDice redDice = new RedDice(dice);
            controller.makeMove(controller.getCurrentPlayer(),new Move(redDice,chosendrag));
            finishAttack();
        }

    }

    @FXML
    private void hover(MouseEvent e){
        if(e.getSource() instanceof StackPane && ((StackPane) e.getSource()).getOpacity() != 0.5 ){
            ((StackPane) e.getSource()).setOpacity(0.7);
        }

        if(e.getSource() instanceof Button && ((Button) e.getSource()).getOpacity() != 0.7 && ((Button) e.getSource()).getOpacity() != 0.2) {
            ((Button) e.getSource()).setOpacity(0.8);
        }

    }

    @FXML
    private void hoverExit(MouseEvent e){
        if(e.getSource() instanceof StackPane && ((StackPane) e.getSource()).getOpacity() != 0.5){
            ((StackPane) e.getSource()).setOpacity(1);
        }

        if(e.getSource() instanceof Button && ((Button) e.getSource()).getOpacity() != 0.7 && ((Button) e.getSource()).getOpacity() != 0.2) {
            ((Button) e.getSource()).setOpacity(1);
        }
    }

}