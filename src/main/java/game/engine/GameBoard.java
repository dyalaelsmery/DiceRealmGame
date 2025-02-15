package game.engine;



import game.dice.*;
public class GameBoard {
    private Player player1;
    private Player player2;
    private GameStatus gameStatus;

    private ArcanePrism arcanePrism;
    private BlueDice blueDice;
    private RedDice redDice;
    private GreenDice greenDice;
    private YellowDice yellowDice;
    private MagentaDice magentaDice;

//=======================================Constructor===================================
     public GameBoard(){
          player1=new Player();
          player1.setPlayerStatus(PlayerStatus.ACTIVE);
          player2=new Player();
          player2.setPlayerStatus(PlayerStatus.PASSIVE);

          gameStatus=new GameStatus();
          
          arcanePrism=new ArcanePrism(6);
          blueDice=new BlueDice(6);
          redDice=new RedDice(6);
          greenDice=new GreenDice(6);
          yellowDice=new YellowDice(6);
          magentaDice=new MagentaDice(6);
     }
//=======================================Get&Set=======================================
   public Player getActivePlayer(){
        if(player1.getPlayerStatus()==PlayerStatus.ACTIVE)
            return player1;
        return player2;    
   }
   public Player getPassivePlayer(){
        if(player1.getPlayerStatus()==PlayerStatus.PASSIVE)
             return player1;
        return player2;    
   }
   public Player getPlayer1(){
    return player1;
   }
   public Player getPlayer2(){
    return player2;
   }
   public Dice[] getAllDice(){
    return new Dice[]{redDice,greenDice,blueDice,magentaDice,yellowDice,arcanePrism};
   }
   public RedDice getRedDice(){
     return redDice;
   }
   public BlueDice getBlueDice(){
     return blueDice;
   } 
   public GreenDice getGreenDice(){
     return greenDice;
   }
   public YellowDice getYellowDice(){
     return yellowDice;
   }
   public MagentaDice getMagentaDice(){
     return magentaDice;
   }
   public ArcanePrism getArcanePrism(){
     return arcanePrism;
   }
   public GameStatus getGameStatus(){
      return gameStatus;
   }
   public Dice[] getDice(){
    return new Dice[]{redDice,greenDice,blueDice,magentaDice,yellowDice,arcanePrism};
   }
}