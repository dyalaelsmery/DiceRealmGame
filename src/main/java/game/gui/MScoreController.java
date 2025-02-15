package game.gui;

import game.engine.Player;
import game.realms.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

public class MScoreController extends ScoreSheetsController{
     Player player;
     @SuppressWarnings("exports")
    public void setScoreSheet(Player player){
        this.player=player;
        playerName.setText(player.getPlayerName()+"'s Scoresheet");
        updateMagentaScoreSheet();
    }
    public void switchScoreScene(MouseEvent e){
        char thechar;
        if(e.getSource()==red)
            thechar='R';
        else if(e.getSource()==green)
            thechar='G';
        else if(e.getSource()==blue)
            thechar='B';
        else
            thechar='Y';
        switchScoreScreen(thechar,player);
    }
      
    public void enteredObject(MouseEvent e){
        ((Node)e.getSource()).setOpacity(0.8);
    }
    public void exitedObject(MouseEvent e){
        ((Node)e.getSource()).setOpacity(1);
    }
    public void updateMagentaScoreSheet(){
        MagentaRealm magentaRealm=player.getMagentaRealm();
        String[] prevAt=magentaRealm.getPreviousAttacks();        
        Label[] values={S1,S2,S3,S4,S5,S6,S7,S8,S9,S10,S11};
        for(int i=0;i<values.length;i++)
            if(prevAt[i].charAt(0)=='0')
                values[i].setText(null);
            else    
            values[i].setText(prevAt[i]);
             

        String[] drawRew=magentaRealm.getDrawRewards();        
        ImageView[] rewards={R1,R2,R3,R4,R5,R6,R7,R8,R9,R10,R11};
        for(int i=0;i<drawRew.length;i++)
            rewards[i].setImage(getImage(drawRew[i]));

        redScore.setText("Red Realm Score : "+player.getRedRealm().getTotalRealmScore());
        greenScore.setText("Green Realm Score: "+player.getGreenRealm().getTotalRealmScore());
        blueScore.setText("Blue Realm Score : "+player.getBlueRealm().getTotalRealmScore());
        magentaScore.setText("Magenta Realm Score : "+player.getMagentaRealm().getTotalRealmScore());
        yellowScore.setText("Yellow Realm Score : "+player.getYellowRealm().getTotalRealmScore());
        totalScore.setText("Total Score : "+player.getGameScore().getTotalGameScore());    
        ECLabel.setText("X"+player.getElementalCrests().size());

        updateAT();

    }
    public void updateAT(){
        ImageView[] arcaneBonuses=new ImageView[]{ab1,ab2,ab3,ab4,ab5,ab6,ab7};
        int totalUsed=player.getUsedArcanes();
        int totalRem=player.getArcaneBoosts().size();
        int i=0;
        Image arcaneImage= new Image(getClass().getResourceAsStream("/images/ArcaneBoost.png"));
        Image timeWarpImage=new Image(getClass().getResourceAsStream("/images/TimeWarp.png"));
        Image xImage=new Image(getClass().getResourceAsStream("/images/x.png"));


        for(;i<totalUsed;i++){
            arcaneBonuses[i].setVisible(true);
            arcaneBonuses[i].setImage(xImage);
        }
        for(;i<totalRem+totalUsed;i++){
            arcaneBonuses[i].setVisible(true);
            arcaneBonuses[i].setImage(arcaneImage);
        }

        ImageView[] timeWarps=new ImageView[]{tw1,tw2,tw3,tw4,tw5,tw6,tw7};
        totalUsed=player.getUsedTimeWarps();
        totalRem=player.getTimeWarps().size();
        i=0;

        for(;i<totalUsed;i++){
            timeWarps[i].setVisible(true);
            timeWarps[i].setImage(xImage);
        }
        for(;i<totalRem+totalUsed;i++){
            timeWarps[i].setVisible(true);
            timeWarps[i].setImage(timeWarpImage);
        }

        
        String[] roundRew=getGuiGameController().getRoundRewardAr();
        ImageView[] roundRewards=new ImageView[]{RR1,RR2,RR3,RR4,RR5,RR6};
        int currentRound=getGuiGameController().getGameStatus().getGameRound();

        for(int j=0;j<roundRew.length;j++){
            String rewName="";
            switch(roundRew[j]){
            case "RedBonus":rewName="RB";break;
            case "GreenBonus":rewName="GB";break;
            case "BlueBonus":rewName="BB";break;
            case "MagentaBonus":rewName="MB";break;
            case "YellowBonus":rewName="YB";break;
            case "EssenceBonus":rewName="EB";break;
            case "ElementalCrest":rewName="EC";break;
            case "ArcaneBoost":rewName="AB";break;
            case "TimeWarp":rewName="TW";break;
            }
            if((j+1<currentRound||(j+1==currentRound&&
            (player==getGuiGameController().getLastPlayerToGetReward()||getGuiGameController().getGameStatus().playerSwitched())))&&rewName!="")
                rewName="X";

            roundRewards[j].setImage(getImage(rewName));
        }
    }
    @Override
    public void initialize() {
        background.setImage(new Image(getClass().getResourceAsStream("/images/scoreback.png")));
        theScoreSheet.setImage(new Image(getClass().getResourceAsStream("/images/magentaScoreSheet.png")));
        ImageView[] bonuses=new ImageView[]{ab1,ab2,ab3,ab4,ab5,ab6,ab7,tw1,tw2,tw3,tw4,tw5,tw6,tw7};
        for(ImageView x:bonuses)
            x.setVisible(false);
            EC.setImage(new Image(getClass().getResourceAsStream("/images/ElementalCrest.png")));    

    }
    @FXML
    private ImageView EC;

    @FXML
    private Label ECLabel;
    @FXML
    private ImageView RR1;

    @FXML
    private ImageView RR2;

    @FXML
    private ImageView RR3;

    @FXML
    private ImageView RR4;

    @FXML
    private ImageView RR5;

    @FXML
    private ImageView RR6;
    @FXML
    private ImageView ab1;

    @FXML
    private ImageView ab2;

    @FXML
    private ImageView ab3;

    @FXML
    private ImageView ab4;

    @FXML
    private ImageView ab5;

    @FXML
    private ImageView ab6;

    @FXML
    private ImageView ab7;
    @FXML
    private ImageView tw1;

    @FXML
    private ImageView tw2;

    @FXML
    private ImageView tw3;

    @FXML
    private ImageView tw4;

    @FXML
    private ImageView tw5;

    @FXML
    private ImageView tw6;

    @FXML
    private ImageView tw7;
    @FXML
    private Label redScore;
    @FXML
    private Label greenScore;
    @FXML
    private Label blueScore;
    @FXML
    private Label magentaScore;
    @FXML
    private Label yellowScore;
    @FXML
    private Label totalScore;
    @FXML
    private ImageView R1;

    @FXML
    private ImageView R10;

    @FXML
    private ImageView R11;

    @FXML
    private ImageView R2;

    @FXML
    private ImageView R3;

    @FXML
    private ImageView R4;

    @FXML
    private ImageView R5;

    @FXML
    private ImageView R6;

    @FXML
    private ImageView R7;

    @FXML
    private ImageView R8;

    @FXML
    private ImageView R9;

    @FXML
    private Label S1;

    @FXML
    private Label S10;

    @FXML
    private Label S11;

    @FXML
    private Label S2;

    @FXML
    private Label S3;

    @FXML
    private Label S4;

    @FXML
    private Label S5;

    @FXML
    private Label S6;

    @FXML
    private Label S7;

    @FXML
    private Label S8;

    @FXML
    private Label S9;

    @FXML
    private ImageView background;

    @FXML
    private Rectangle blue;

    @FXML
    private Rectangle green;

    @FXML
    private Label playerName;

    @FXML
    private Rectangle red;

    @FXML
    private ImageView theScoreSheet;

    @FXML
    private Rectangle yellow;

}
