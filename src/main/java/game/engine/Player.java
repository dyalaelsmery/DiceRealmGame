package game.engine;

import game.collectibles.*;
import game.exceptions.ExhaustedResourceException;
import game.realms.BlueRealm;
import game.realms.GreenRealm;
import game.realms.MagentaRealm;
import game.realms.RedRealm;
import game.realms.YellowRealm;

import java.util.*;
public class Player {
     private ScoreSheet scoreSheet;
    private GameScore gameScore;

    private PlayerStatus playerStatus;

    private final BlueRealm BLUE_REALM;
    private final RedRealm RED_REALM;
    private final GreenRealm GREEN_REALM;
    private final YellowRealm YELLOW_REALM;
    private final MagentaRealm MAGENTA_REALM;

    private ArrayList<ArcaneBoost> arcaneBoosts;
    private ArrayList<TimeWarp> timeWarps;
    private ArrayList<ElementalCrest> elementalCrests;

    private int usedArcanes;
    private int usedTimeWarps;

    private String playerName;
//=======================================Constructor===================================

    public Player(){
        scoreSheet=new ScoreSheet();
        gameScore=new GameScore();
        BLUE_REALM=scoreSheet.getBlueRealm();
        RED_REALM=scoreSheet.getRedRealm();
        GREEN_REALM=scoreSheet.getGreenRealm();
        YELLOW_REALM=scoreSheet.getYellowRealm();
        MAGENTA_REALM=scoreSheet.getMagentaRealm();
        arcaneBoosts=new ArrayList<ArcaneBoost>();
        timeWarps=new ArrayList<TimeWarp>();
        elementalCrests=new ArrayList<ElementalCrest>();

    }
//=======================================Methods=======================================
    public void updateGameScore(){
        gameScore.calculateGameScore(RED_REALM.getTotalRealmScore(),
        BLUE_REALM.getTotalRealmScore(),
         GREEN_REALM.getTotalRealmScore(),
         YELLOW_REALM.getTotalRealmScore(),
         MAGENTA_REALM.getTotalRealmScore(),
         elementalCrests.size());
    }
    public void addTimeWarp(TimeWarp warp){
        timeWarps.add(warp);
    }
    public void addArcaneBoost(ArcaneBoost boost){
        arcaneBoosts.add(boost);
    }
    public void addElementalCrest(ElementalCrest crest){
        elementalCrests.add(crest);
    }
    public void removeTimeWarp() throws ExhaustedResourceException{
        if(timeWarps==null)
            throw new ExhaustedResourceException("TimeWarps List is null");
        else if(timeWarps.size()==0)
            throw new ExhaustedResourceException("No TimeWarps are available");
        timeWarps.remove(0);
        usedTimeWarps++;
        
    }
    public void removeArcaneBoost() throws ExhaustedResourceException{
        if(arcaneBoosts==null)
            throw new ExhaustedResourceException("ArcaneBoosts List is null");
        else if(arcaneBoosts.size()==0)
            throw new ExhaustedResourceException("No ArcaneBoosts are available");

        arcaneBoosts.remove(0);
        usedArcanes++;
    }
//=======================================Get&Set=======================================
    public ScoreSheet getScoreSheet(){
        return scoreSheet;
    }
    public PlayerStatus getPlayerStatus(){
        return playerStatus;
    }
    public void setPlayerStatus(PlayerStatus status){
        playerStatus=status;
    }
    public BlueRealm getBlueRealm(){
        return BLUE_REALM;
    }
    public RedRealm getRedRealm(){
        return RED_REALM;
    }
    public GreenRealm getGreenRealm(){
        return GREEN_REALM;
    }
    public YellowRealm getYellowRealm(){
        return YELLOW_REALM;
    }
    public MagentaRealm getMagentaRealm(){
        return MAGENTA_REALM;
    }
    public ArrayList<ArcaneBoost> getArcaneBoosts(){
        return arcaneBoosts;
    }
    public ArrayList<TimeWarp> getTimeWarps(){
        return timeWarps;
    }
    public ArrayList<ElementalCrest> getElementalCrests(){
        return elementalCrests;
    }
    public GameScore getGameScore(){
        updateGameScore();
        return gameScore;
    }
    public void setPlayerName(String name){
        playerName=name;
    }
    public String getPlayerName(){
        return playerName;
    }
    public int getUsedArcanes(){
        return usedArcanes;
    }
    public int getUsedTimeWarps(){
        return usedTimeWarps;
    }
}
