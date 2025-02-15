package game.AI;
import game.dice.Dice;
import game.collectibles.ArcaneBoost;
import game.collectibles.ElementalCrest;
import game.collectibles.Reward;
import game.collectibles.TimeWarp;
import game.creatures.Creature;
import game.creatures.Dragon;
import game.creatures.DragonNumber;
import game.dice.ArcanePrism;
import game.dice.BlueDice;
import game.dice.DiceStatus;
import game.dice.GreenDice;
import game.dice.MagentaDice;
import game.dice.RedDice;
import game.dice.YellowDice;
import game.engine.Move;
import game.engine.Player;
import game.engine.PlayerStatus;
import game.realms.RealmColor;
import java.util.*;


public class AI {
    //FOr white Dice, to determine Color of it
    private boolean red=false;
    private boolean green=false;
    private boolean blue=false;
    private boolean magenta=false;
    private boolean yellow=false;

    //To know if it's the dragon1 using red dice
    private boolean drag1=false;
    private boolean drag2=false;
    private boolean drag3=false;
    private boolean drag4=false;

    //to know which dragon using Arcane Prism (White dice)
    private boolean drag1white=false;
    private boolean drag2white=false;
    private boolean drag3white=false;
    private boolean drag4white=false;

    //scores for redDraogn Bonust prompt
    private int score1,score2,score3,score4,score5,score6,resultForDragonBonusPrompt;




    
    
    
    public Move findBestMove(Player player,Dice[] dice,int turn)
    {
        // if(turn==1)
        // {
        //     Move temp=bestMoveTurn3(player, get)
        //     if(temp==null) return null;
        // }
        return bestMoveTurn3 (player,dice);
    }

    public double calculateOthers(Player player, Dice[] dice,int diceValue,int exclude)
    {
        if(diceValue==0) return 0;
        //Red
        Dice redDie=searchRed(player, dice);
        if(redDie.getValue()<diceValue) redDie=null;
        double ifRed=probabilityRed(redDie, player);
        

        //Green
        Dice greenDie=searchGreen(player, dice);
        if(greenDie.getValue()<diceValue) greenDie=null;
        double  ifGreen=probabilityGreen(greenDie, player);

        //Blue
        Dice blueDIe=searchBlue(player, dice);
        if(blueDIe.getValue()<diceValue) blueDIe=null;
        double ifBlue=probabilityBlue(blueDIe, player);

        //Magenta
        Dice magentaDie=searchMagenta(player, dice);
        if(magentaDie.getValue()<diceValue) magentaDie=null;
        double  ifMagenta=probabilityMagenta(magentaDie, player);
        //Yellow
        Dice yellowDie=searchYellow(player, dice);
        if(yellowDie.getValue()<diceValue) yellowDie=null;
        double ifYellow=probabilityYellow(yellowDie, player);

        //excluding selected dice
        switch(exclude)
        {
            case 1: ifRed=0;break;
            case 2: ifGreen=0;break;
            case 3: ifBlue=0;break;
            case 4: ifMagenta=0;break;
            case 5: ifYellow=0;break;
            default: break;
        }

        //White
        Dice whiteDie=searchWhite(player, dice);
        if(whiteDie.getValue()<diceValue) whiteDie=null;
        double  ifWhite=Math.max(ifRed, Math.max(ifGreen, Math.max(ifBlue, Math.max(ifMagenta, ifYellow))));

        


        double result=ifRed + ifGreen + ifBlue + ifMagenta + ifYellow + ifWhite;
        return result;

    }

    public Move calculteProbabilities(Player player, Dice[] dice)
    {
        int vr,vg,vb,vm,vy,vw;
        //Red
        Dice redDie=searchRed(player, dice); 
        if(redDie==null) vr=0;
        else vr=redDie.getValue();
        double ifRed=calculateOthers(player, dice, vr, 1);

        //Green
        Dice greenDie=searchGreen(player, dice);
        if(greenDie==null) vg=0;
        else vg=greenDie.getValue();
        double ifGreen=calculateOthers(player, dice, vg, 2);


        //Blue
        Dice blueDIe=searchBlue(player, dice);
        if(blueDIe==null) vb=0;
        else vb=blueDIe.getValue();
        double ifBlue=calculateOthers(player, dice, vb, 3);


        //Magenta
        Dice magentaDie=searchMagenta(player, dice);
        if(magentaDie==null) vm=0;
        else  vm=magentaDie.getValue();
        double ifMagenta=calculateOthers(player, dice, vm, 4);


        //Yellow
        Dice yellowDie=searchYellow(player, dice);
        if(yellowDie==null)  vy=0;
        else vy=yellowDie.getValue();
        double ifYellow=calculateOthers(player, dice, vy, 5);

        //White
        Dice whiteDie=searchWhite(player, dice);
        if(whiteDie==null) vw=0;
        else vw=whiteDie.getValue();
        double ifWhite=calculateOthers(player, dice, vw, 6);

        double  result=Math.max(ifRed, Math.max(ifGreen, Math.max(ifBlue, Math.max(ifMagenta, Math.max(ifYellow, ifWhite)))));
        if(result==ifRed) return new Move(redDie, null);
        if(result==ifGreen) return new Move(greenDie, null);
        if(result==ifBlue) return new Move(blueDIe, null);
        if(result==ifMagenta) return new Move(magentaDie, null);
        if(result==ifYellow) return new Move(yellowDie, null);
        return new Move(whiteDie, null);


    }

    

    

    public Dice searchRed(Player player, Dice[] dice)
    {
        for(int i=0;i<dice.length;i++)
        if(dice[i].getDiceColor()==RealmColor.RED) return dice[i];
        return null;
    }
    public Dice searchGreen(Player player, Dice[] dice)
    {
        for(int i=0;i<dice.length;i++)
        if(dice[i].getDiceColor()==RealmColor.GREEN) return dice[i];
        return null;
    }
    public Dice searchBlue(Player player, Dice[] dice)
    {
        for(int i=0;i<dice.length;i++)
        if(dice[i].getDiceColor()==RealmColor.BLUE) return dice[i];
        return null;
    }
    public Dice searchMagenta(Player player, Dice[] dice)
    {
        for(int i=0;i<dice.length;i++)
        if(dice[i].getDiceColor()==RealmColor.MAGENTA) return dice[i];
        return null;
    }
    public Dice searchYellow(Player player, Dice[] dice)
    {
        for(int i=0;i<dice.length;i++)
        if(dice[i].getDiceColor()==RealmColor.YELLOW) return dice[i];
        return null;
    }
    public Dice searchWhite(Player player, Dice[] dice)
    {
        for(int i=0;i<dice.length;i++)
        if(dice[i].getDiceColor()==RealmColor.WHITE) return dice[i];
        return null;
    }
    public Creature getWhiteCreature(Player player,Dice dice)
    {
        if(red) return getWhichcDragonRed(player, true);
        if(green) return player.getGreenRealm().getGaia();
        if(blue) return player.getBlueRealm().getHydra();
        if(magenta) return player.getMagentaRealm().getPhoenix();
        setWhiteFalse();
        return player.getYellowRealm().getLion();
    }

    public Move getWhiteMove(Player player, Dice dice)
    {
        if(red) ((ArcanePrism)dice).setChosenColor(RealmColor.RED);
        if(green) ((ArcanePrism)dice).setChosenColor(RealmColor.GREEN);
        if(blue) ((ArcanePrism)dice).setChosenColor(RealmColor.BLUE);
        if(magenta) ((ArcanePrism)dice).setChosenColor(RealmColor.MAGENTA);
        if(yellow) ((ArcanePrism)dice).setChosenColor(RealmColor.YELLOW);
        return new Move(dice,getWhiteCreature(player,dice));
    }

    public Move bestMoveTurn3(Player player, Dice[] dice)
    {
        //R,G,B,M,Y,W
        //Red Dice
        Dice redDice=searchRed(player, dice);
        int redScore=scoreRedTurn3(player,redDice,false); 
        Move redMove= new Move(redDice,getWhichcDragonRed(player, false));
 
        //Green Dice
        Dice greenDice=searchGreen(player, dice);
        int greenScore=scoreGreenTurn3(player,greenDice);
        Move greenMove= new Move (greenDice,player.getGreenRealm().getGaia());

        //Blue Dice
        Dice blueDice=searchBlue(player, dice);
        int blueScore=scoreBlueTurn3(player,blueDice);
        Move blueMove=new Move (blueDice,player.getBlueRealm().getHydra());

        //Magenta
        Dice magentaDice=searchMagenta(player, dice);
        int magentaScore=scoreMagentaTurn3(player, magentaDice);
        Move magentaMove=new Move (magentaDice,player.getMagentaRealm().getPhoenix());

        //Yellow
        Dice yellowDice=searchYellow(player, dice);
        int yellowScore=scoreYellowTurn3(player, yellowDice);
        Move yellowMove=new Move(yellowDice,player.getYellowRealm().getLion());
        
        //White
        Dice whiteDice=searchWhite(player, dice);
        int  whiteScore=scoreWhiteTurn3(player,whiteDice);
        Move whiteMove=new Move(whiteDice, null);

        //getting the maximum score for the best move
        int result=Math.max(redScore,Math.max(greenScore,Math.max(magentaScore,Math.max(yellowScore, Math.max(blueScore, whiteScore)))));
        if(result== redScore ) return redMove;
        if(result== greenScore) return greenMove;
        if(result== blueScore) return blueMove;
        if(result== magentaScore) return magentaMove;
        if(result== yellowScore)  return yellowMove;
        return whiteMove;
    }

    public void setWhiteFalse()  //sets everything back to false
    {
        red=false; green=false; blue=false; magenta=false; yellow=false;
    }


    public boolean do_Roll_Dice(Player player, Dice[] dice)
    {   Move temp=bestMoveTurn3(player, dice);
        if (temp.getDice().getValue()<3)
        {
            return true;
        }
        return false;
        
    }
    public int getChoiceIndex(Move move,Dice[] dice)
    {
        Dice temp=move.getDice();
        for(int i=0;i<dice.length;i++)
        {
            if(temp.getDiceColor()==dice[i].getDiceColor() && temp.getValue()==dice[i].getValue()) return i;
        }
        return 0;
    }
    public String getChoiceArcaneColor(Player player, Dice dice)
    {
        Move temp=getWhiteMove(player, dice);
        RealmColor color=temp.getDice().getDiceColor();
        String result="";
        switch(color)
        {
            case RED: result="R";break;
            case GREEN: result="G";break;
            case BLUE: result="B";break;
            case MAGENTA: result="M";break;
            default: result="Y";break;
        }
        return result;
    }

    public int getChoice(Player player,Dice[] dice,int turn) //think
    {
        
            Move temp=findBestMove(player, dice,turn);
            int choice=getChoiceIndex(temp, dice) +1;
            return choice;
    }
    public String chooseDragonBonus(Player player)
    {
        //Value 1
        RedDice dice1=new RedDice(1);
         score1=scoreRedTurn3(player, dice1, false);
        Creature creature1=(Dragon) getWhichcDragonRed(player, false);

        //Value 2
        RedDice dice2=new RedDice(2);
         score2=scoreRedTurn3(player, dice2, false);
        Creature creature2=(Dragon) getWhichcDragonRed(player, false);

        //Value 3
        RedDice dice3=new RedDice(3);
         score3=scoreRedTurn3(player, dice3, false);
        Creature creature3=(Dragon) getWhichcDragonRed(player, false);

        
        //Value 4
        RedDice dice4=new RedDice(4);
         score4=scoreRedTurn3(player, dice4, false);
        Creature creature4=(Dragon) getWhichcDragonRed(player, red);

        //Value 5
        RedDice dice5=new RedDice(5);
         score5=scoreRedTurn3(player, dice5, false);
        Creature creature5=(Dragon) getWhichcDragonRed(player, red);

        //Value 6
        RedDice dice6=new RedDice(6);
         score6=scoreRedTurn3(player, dice6, false);
        Creature creature6=(Dragon) getWhichcDragonRed(player, red);

         resultForDragonBonusPrompt=Math.max(score1,Math.max(score2,Math.max(score3,Math.max(score4,Math.max(score5, score6)))));

        if(resultForDragonBonusPrompt==score1) //for value 1
        {
            if(creature1!=null)
            if(creature1==  player.getRedRealm().getDragon1())
                return "1";
                return "2";
        }
        if(resultForDragonBonusPrompt==score2) //for value 2
        {
            if(creature2!=null)

            if(creature2==  player.getRedRealm().getDragon1())
                return "1";
                return "3";
        }
        if(resultForDragonBonusPrompt==score3) //for value 3
        {
            if(creature3!=null)

            if(creature3==  player.getRedRealm().getDragon1())
                return "1";
                return "3";
        }
        if(resultForDragonBonusPrompt==score4) //for value 4
        {
            if(creature4!=null)

            if(creature4==  player.getRedRealm().getDragon3())
                return "3";
                return "4";
        }
        if(resultForDragonBonusPrompt==score5) //for value 5
        {
            if(creature5!=null)
            if(creature5==  player.getRedRealm().getDragon3())
                return "3";
                return "4";
        }
        //for value 6 
        if(creature6!=null)
        if(creature6==player.getRedRealm().getDragon4())
            return "2";
            return "4";
    }

    public String chooseDragonPart(Player player)
    {
        String s="";
        if(chooseDragonPart(player)=="1") 
        {
            if(resultForDragonBonusPrompt==score1)
            s= "T";
            else  if(resultForDragonBonusPrompt==score2)
            s= "W";
            s= "F";
        }
        if(chooseDragonPart(player)=="2") 
        {
            if(resultForDragonBonusPrompt==score1)
            s= "W";
            else  if(resultForDragonBonusPrompt==score3)
            s= "H";
            s= "F";
        }
        if(chooseDragonPart(player)=="3") 
        {
            if(resultForDragonBonusPrompt==score5)
            s= "F";
            else  if(resultForDragonBonusPrompt==score2)
            s= "T";
            s= "H";
        }
        if(chooseDragonPart(player)=="4") 
        {
            if(resultForDragonBonusPrompt==score5)
            s= "W";
            else  if(resultForDragonBonusPrompt==score4)
            s= "T";
            s= "H";
        }

        setScoresZero();
        return s;

    }
    public void setScoresZero()
    {
        score1=0;score2=0;score3=0;score4=0;score5=0;score6=0;resultForDragonBonusPrompt=0;
    }
    public int chooseDragon(Player player, int diceValue)
    {
        Creature creature= (Dragon) getWhichcDragonRed(player, false);
        if (creature==null)
        creature=getWhichcDragonRed(player, true);
        int result=getDragonNumber((Dragon) creature,diceValue);
        return result;
    }
    public int getDragonNumber(Dragon creature, int diceValue)
    {
        Dragon dragon=(Dragon) creature;
        DragonNumber number=dragon.getDragonNumber();
        if(diceValue==1)
        {
            if(number==DragonNumber.Dragon1)
            return 1;
            return 2;
        }
        if(diceValue==2)
        {
            if(number==DragonNumber.Dragon1)
            return 1;
            return 2;
        }
        if(diceValue==3)
        {
            if(number==DragonNumber.Dragon1)
            return 1;
            return 2;
        }
        if(diceValue==4)
        {
            if(number==DragonNumber.Dragon3)
            return 1;
            return 2;
        }
        if(diceValue==5)
        {
            if(number==DragonNumber.Dragon3)
            return 1;
            return 2;
        }
        if(diceValue==6)
        {
            if(number==DragonNumber.Dragon2)
            return 1;
            return 2;
        }
        return 0;


    }


    
    public String do_Arcane_Boost ()
    {
        return "Y";
    }
    public Move color_Bonus(Player player)
    {   
        //Calculating Red Bonus score
        int  maxRed=0;
        for(int i=1;i<7;i++)
        {
            int temp=scoreRedTurn3(player, new RedDice(i),false);
            if (temp>maxRed)
            maxRed=i;
        }
        int redBonus=scoreRedTurn3(player, new RedDice(maxRed), false);
        Move redMove=new Move(new RedDice(maxRed),getWhichcDragonRed(player, false));

        //Calculating Green Bonus score
        int maxGreen=0;
        for(int i=2;i<13;i++)
        {
            int temp=scoreGreenTurn3(player, new GreenDice(i));
            if (temp>maxGreen)
            maxGreen=i;
        }
        int greenBonus=scoreGreenTurn3(player, new GreenDice(maxGreen));
        Move greeMove=new  Move(new GreenDice(maxGreen),player.getGreenRealm().getGaia());

        //Calculating Blue Bonus score
        int blueBonus=scoreBlueTurn3(player, new BlueDice(player.getBlueRealm().getHydra().getMinimumAttackValue()));
        Move blueMove=new Move(new BlueDice(player.getBlueRealm().getHydra().getMinimumAttackValue()),player.getBlueRealm().getHydra());

        //Calculating Magenta Bonus score
        int magentaBonus=scoreMagentaTurn3(player, new MagentaDice(6));
        Move magentaMove=new Move(new MagentaDice(6),player.getMagentaRealm().getPhoenix());

        //calculating Yellow Bonus score
        int yellowBonus=scoreYellowTurn3(player, new YellowDice(6));
        Move yellowMove=new Move(new YellowDice(6), player.getYellowRealm().getLion());
 
        int result=Math.max(redBonus,Math.max(greenBonus,Math.max(magentaBonus,Math.max(yellowBonus,blueBonus))));
        if(result==redBonus) return  redMove;
        if(result==greenBonus) return greeMove;
        if(result==blueBonus) return blueMove;
        if(result==magentaBonus) return magentaMove;
        return yellowMove;
    }

    public void setWhiteDragonFalse()
    {
        drag1white=false; drag2white=false; drag3white=false; drag4white=false;
    }

    public void setDragonFalse()
    {
        drag1=false; drag2=false; drag3=false; drag4=false;
    }
    public Creature getWhichcDragonRed(Player  player,boolean isWhiteDice)
    {
        Creature temp=null;
        if(isWhiteDice)
        {  
            if(drag1white) temp=player.getRedRealm().getDragon1();
            if(drag2white) temp=player.getRedRealm().getDragon2();
            if(drag3white) temp=player.getRedRealm().getDragon3();
            if(drag4white)temp=player.getRedRealm().getDragon4();
            setWhiteDragonFalse();

        }else
        {
            if(drag1) temp=player.getRedRealm().getDragon1();
            if(drag2) temp=player.getRedRealm().getDragon2();
            if(drag3) temp=player.getRedRealm().getDragon3();
            if(drag4)temp=player.getRedRealm().getDragon4();
            setDragonFalse();
        }
        return temp;

    }
    public String chooseEssenceColor (Player player)
    {
        Move result=color_Bonus(player);
        if(result.getDice().getDiceColor()==RealmColor.RED) return "R";
        if(result.getDice().getDiceColor()==RealmColor.GREEN) return "G";
        if(result.getDice().getDiceColor()==RealmColor.BLUE) return "B";
        if(result.getDice().getDiceColor()==RealmColor.MAGENTA) return "M";
         return "Y";
    }

    public String chooseGreenGaiaBonus(Player player)
    {
        //Calculating Green Bonus score
        int maxGreen=2;
        for(int i=2;i<13;i++)
        {
            int temp=scoreGreenTurn3(player, new GreenDice(i));
            if (temp>maxGreen)
            maxGreen=i;
        }
        return ""+maxGreen;

    }

    public int scoreWhiteTurn3(Player player,Dice dice)
    {
        if (dice== null) return 0;
        int redScore=scoreRedTurn3(player, dice,true);
        int greenScore=scoreGreenTurn3(player, dice);
        int blueScore=scoreBlueTurn3(player, dice);
        int magentaScore=scoreMagentaTurn3(player, dice);
        int yellowScore=scoreYellowTurn3(player, dice);
        int result=Math.max(redScore,Math.max(greenScore,Math.max(magentaScore,Math.max(yellowScore, blueScore))));
        if(result== redScore)
        {
            red=true;
        }
        if(result== greenScore)
        {
            green=true;
        }
        if(result== blueScore)
        {
            blue=true;
        }
        if(result== magentaScore)
        {
            magenta=true;
        }
        yellow=true;
        return result;
    }


    public int scoreRedTurn3(Player player,Dice dice,boolean isWhiteDice)
    { 
        if(dice==null) return 0;
        isWhiteDice=!isWhiteDice;
        int value=dice.getValue();
        Move[] move=player.getRedRealm().getPossibleMovesForADie(value, dice.getRealm(),dice);
        boolean access, status, isvalidAttack;
        access=player.getRedRealm().isRealmAccessible();
        status=!(dice.getDiceStatus()==DiceStatus.FORGOTTEN_REALM);
        if(player.getPlayerStatus()==PlayerStatus.PASSIVE) status=!status;
        isvalidAttack=(move.length>0);

        int score=0;
        if (!access || !status || !isvalidAttack) return 0;

        boolean d1Face=player.getRedRealm().getDragon1().isFaceKilled();
        boolean d1Wing=player.getRedRealm().getDragon1().isWingsKilled();
        boolean d1Tail=player.getRedRealm().getDragon1().isTailKilled();
        boolean d2Face=player.getRedRealm().getDragon2().isFaceKilled();
        boolean d2Wing=player.getRedRealm().getDragon2().isWingsKilled();
        boolean d2Heart=player.getRedRealm().getDragon2().isHeartKilled();
        boolean d3Face=player.getRedRealm().getDragon3().isFaceKilled();
        boolean d3Tail=player.getRedRealm().getDragon3().isTailKilled();
        boolean d3Heart=player.getRedRealm().getDragon3().isHeartKilled();
        boolean d4Wing=player.getRedRealm().getDragon4().isWingsKilled();
        boolean d4Tail=player.getRedRealm().getDragon3().isTailKilled();
        boolean d4Heart=player.getRedRealm().getDragon3().isHeartKilled();

         // ==================VALUE 1======================================

         if(value==1 && !d1Tail) //for Dragon 1
        {   
             d1Tail=true;
             boolean ok=false;
            if(isBonus(d1Face, d1Wing, d1Tail)) //Columnn Bonus
             {
                 score+=10; ok=true;
             }
            if(isBonus(d1Tail, d3Tail, d4Tail)) //Row Bonus
             {
                 score+=10; ok=true;
             }
             if(!ok)
             {
                score=dragonRemaining(d1Face,d1Wing)+1; //Defeating Dragon 1
                
             }
        }
        int max=score;
 
 
             if(value==1 && !d2Wing) //for Dragon 2
         {
             d2Wing=true;
             boolean ok=false;
             if(isBonus(d1Face, d2Wing, d3Tail,d4Heart)) //Diagonal Bonus
             {
                score+=redBonus(player, "diagonal"); ok=true;
             }
            if(isBonus(d2Face, d2Wing, d2Heart)) //Column Bonus
             {
                 score+=14; ok=true;
             }
            if(isBonus(d1Wing, d2Wing, d4Wing)) //Row Bonus
             {
                 score+=redBonus(player, "row2"); ok=true;
             }
            if(!ok)
             {
                score=dragonRemaining(d2Face,d2Heart)+1; //Defeating Dragon 2

             }
   
        }
        if(max>score)
        {
            if(!isWhiteDice)
                drag1white=true;
            else if(isWhiteDice) drag1=true;

            return max;
        }else if (score>=max)
        {
            if(!isWhiteDice)
                drag2white=true;
            else if(isWhiteDice) drag2=true;

            return score;
        }

          // =======================VALUE 2====================================
          if(value==2 && !d1Wing) //for Dragon 1
        {
              d1Wing=true;
              boolean ok=false;
            if(isBonus(d1Face, d1Wing,d1Tail)) //Column Bonus
              {
                  score+=10; ok=true;
              }
            if(isBonus(d1Wing, d2Wing,d4Wing)) //Row Bonus
              {
                  score+=redBonus(player, "row2"); ok=true;
              }
            if(!ok)
              {
                score=dragonRemaining(d1Face,d1Tail)+1; //defeating Dragon1

              }
        }

            max=score;
              
              if(value==2 && d3Tail) //for Dragon 3
        {
              d3Tail=true;
              boolean ok=false;
            if(isBonus(d1Face, d2Wing,d3Tail,d4Heart)) //Diagonal Bonus
              {
                  score+=redBonus(player, "diagonal"); ok=true;
              }
            if(isBonus(d1Tail, d3Tail,d4Tail)) //Row Bonus
              {
                  score+=redBonus(player, "row3"); ok=true;
              }
            if(isBonus(d3Face, d3Tail, d3Heart)) //Column Bonus
              {
                  score+=16; ok=true;
              }
            if(!ok)
              {
                score=dragonRemaining(d3Face,d3Heart)+1; //Defeating Dragon 3

              }
        }
        if(max>score)
        {
            if(!isWhiteDice)
                drag1=true;
            else if(isWhiteDice) drag1white=true;
            return max;
        }else if (score>=max)
        {
            if(!isWhiteDice)
                drag3=true;
            else if(isWhiteDice) drag3white=true;

            return score;
        }
        
  

        // ========================================= VALUE 3 ==================
        if(value==3 && !d1Face) //for Dragon 1
        {
            d1Face=true;
            boolean ok=false;
            if(isBonus(d1Face, d2Wing, d3Tail,d4Heart)) //Diagonal Bonus
            {
                score+=redBonus(player, "diagonal"); ok=true;
            }
            if(isBonus(d1Face, d1Wing, d1Tail)) //Column Bonus
            {
                score+=10; ok=true;
            }
            if(isBonus(d1Face, d2Face, d3Face)) //Row Bonus
            {
                score+=redBonus(player, "row1"); ok=true;
            }
            if(!ok)
            {
                score=dragonRemaining(d1Wing,d1Tail)+1; //Defeating Dragon 1

            }
  
        }
         max=score;

        if(value==3 && !d2Heart) //for Dragon 2
    {
            d2Heart=true;
            boolean ok=false;
            if(isBonus(d2Face,d2Wing , d2Heart)) //Column Bonus
            {
                score+=14; ok=true;
            }
            if(isBonus(d2Heart,d3Heart,d4Heart)) //Row Bonus
            {
                score+=redBonus(player, "row4"); ok=true;
            }
            if(!ok)
            {
                score=dragonRemaining(d2Face,d2Wing)+1; //Defeating Dragon 2

            }
  
    }
        if(max>score)
        {
            if(!isWhiteDice)
                drag1=true;
            else if(isWhiteDice) drag1white=true;
            return max;
        }else if (score>=max)
        {
            if(!isWhiteDice)
                drag2=true;
            else if(isWhiteDice) drag2white=true;

            return score;
        }

        // ================= VALUE 4 ================================
        if(value==4 && !d3Heart) //Dragon 3
    {
            d3Heart=true;
            boolean ok=false;
        if(isBonus(d3Face, d3Tail,d3Heart)) //Column Bonus
        {
            score+=16; ok=true;
        }
        if(isBonus(d2Heart, d3Heart,d4Heart)) //Row Bonu
        {
            score+=redBonus(player, "row4"); ok=true;
        }
        if(!ok)
        {
            score=dragonRemaining(d3Face,d3Tail)+1; //Defeating Dragon 3

        }
    }
    max=score;

    if(value==4 && !d4Tail) //Dragon 4
    {
            d4Tail=true;
            boolean ok=false;
        if(isBonus(d4Wing, d4Tail,d4Heart)) //Column Bonus
        {
            score+=20; ok=true;
        }
        if(isBonus(d1Tail, d3Tail,d4Tail)) //Row Bonu
        {
            score+=redBonus(player, "row3"); ok=true;
        }
        if(!ok)
        {
            score=dragonRemaining(d4Wing,d4Heart)+1; //Defeating Dragon 4

        }
    }
    if(max>score)
        {
            if(!isWhiteDice)
                drag3=true;
            else if(isWhiteDice) drag3white=true;
            return max;
        }else if (score>=max)
        {
            if(!isWhiteDice)
                drag4=true;
            else if(isWhiteDice) drag4white=true;
            return score;
        }

        // =================== VALUE  5 ===========================

        if(value==5 && !d3Face) //for Dragon 3
    {
            d3Face=true;
            boolean ok=false;
        if(isBonus(d1Face, d2Face, d3Face)) //Row Bonus
            {
                score+=redBonus(player, "row1"); ok=true;
            }
        if(isBonus(d3Face, d3Tail, d3Heart)) //COlumn Bonus
            {
                score+=16; ok=true;
            }
        if(!ok)
            {
                score=dragonRemaining(d3Tail,d3Heart)+1; //Defeating Dragon 3

                score+=12211293; 
            }
    }
    max=score;

        if(value==5 && !d4Wing) //for Dragon 4
    {
            d4Wing=true;
            boolean ok=false;
        if(isBonus(d1Wing, d2Wing, d4Wing)) //Row Bonus
            {
                score+=redBonus(player, "row2"); ok=true;
            }
        if(isBonus(d4Wing, d4Tail, d4Heart)) //Column Bonus
            {
                score+=20; ok=true;
            }
        if(!ok)
            {
                score=dragonRemaining(d4Tail,d4Heart)+1; //Defeating Dragon 4

            }
    }
    if(max>score)
        {
            if(!isWhiteDice)
                drag3=true;
            else if(isWhiteDice) drag3white=true;
            return max;
        }else if (score>=max)
        {
            if(!isWhiteDice)
                drag4=true;
            else if(isWhiteDice) drag4white=true;

            return score;
        }


        // =======================VALUE 6 =======================================
        if(value==6 && !d2Face) //for Dragon 2
    {
            d2Face=true;
            boolean ok=false;
        if(isBonus(d2Face, d2Wing,d2Heart)) //Column Bonus
            {
                score+=14; ok=true;
            }
        if(isBonus(d1Face, d2Face,d3Face)) //Row Bonus
            {
                score+=redBonus(player, "row1"); ok=true;
            }
        if(!ok)
            {
                score=dragonRemaining(d2Wing,d2Heart)+1; //Defeating Dragon 2

            }
    }

    max=score;
        
        if(value==6 && !d4Heart) //for Dragon 4
    {
            d4Heart=true;
            boolean ok=false;

        if(isBonus(d2Heart, d3Heart,d4Heart)) //Row Bonus
            {
                score+=redBonus(player, "row4"); ok=true;
            }
        if(isBonus(d4Wing, d4Tail,d4Heart)) //Column Bonus
            {
                score+=20; ok=true;
            }
        if(isBonus(d1Face, d2Wing, d3Tail,d4Heart))//Diagonal Bonus
            {
                score+=redBonus(player, "diagonal"); ok=true;
            }
        if(!ok)
            {
                score=dragonRemaining(d4Tail,d4Wing)+1; //Defeating Dragon 4

            }
    }
    if(max>score)
        {
            if(!isWhiteDice)
                drag2=true;
            else if(isWhiteDice) drag2white=true;
            return max;
        }else if (score>=max)
        {
            if(!isWhiteDice)
                drag4=true;
            else if(isWhiteDice) drag4white=true;
            return score;
        }
        return score;
           
            
 }

 public int redBonus(Player player,String s)
    {
        int score=0;
        Reward temp[]=player.getGreenRealm().getRealmRewards();
        if (s=="row1")
        {
            String attack=whatReward(temp,1);
        switch (attack) {
            case "null": score+=0;break;
            case "tw": score+=1;break;
            case "ec": score+=2;break;
            case "AB": score+=12;break;
            default: score+=10;break;
        }
            return score;

        }
        if (s=="row2")
        {
            String attack=whatReward(temp,2);
        switch (attack) {
            case "null": score+=0;break;
            case "tw": score+=1;break;
            case "ec": score+=2;break;
            case "AB": score+=12;break;
            default: score+=10;break;
        }
            return score;

        }
        if (s=="row3")
        {
            String attack=whatReward(temp,3);
        switch (attack) {
            case "null": score+=0;break;
            case "tw": score+=1;break;
            case "ec": score+=2;break;
            case "AB": score+=12;break;
            default: score+=10;break;
        }
            return score;

        }
        if (s=="row4")
        {
            String attack=whatReward(temp,4);
        switch (attack) {
            case "null": score+=0;break;
            case "tw": score+=1;break;
            case "ec": score+=2;break;
            case "AB": score+=12;break;
            default: score+=10;break;
        }
            return score;

        }
        if (s=="diagonal")
        {
            String attack=whatReward(temp,5);
        switch (attack) {
            case "null": score+=0;break;
            case "tw": score+=1;break;
            case "ec": score+=2;break;
            case "AB": score+=12;break;
            default: score+=10;break;
        }
            return score;

        }
        return 0;
        
    }

    public int scoreGreenTurn3(Player player,Dice dice)
    {   
        if(dice==null) return 0;
        Reward[] temp=player.getGreenRealm().getRealmRewards();
        boolean access, status, isvalidAttack;
        access=player.getGreenRealm().isRealmAccessible();
        status=!(dice.getDiceStatus()==DiceStatus.FORGOTTEN_REALM);
        if(player.getPlayerStatus()==PlayerStatus.PASSIVE) status=!status;
        isvalidAttack=player.getGreenRealm().getGaia().checkPossibleAttack(dice.getValue());
        if (!access || !status || !isvalidAttack) return 0;

        int attackNumber=player.getMagentaRealm().getTotalNumberOfAttacks()+1;
        int value=dice.getValue();
        int previousScore=player.getGreenRealm().getTotalRealmScore();
        int score=0;
        switch(attackNumber)
        {
            case 1:score=1-previousScore;break;
            case 2:score=2-previousScore;break;
            case 3:score=4-previousScore;break;
            case 4:score=7-previousScore;break;
            case 5:score=11-previousScore;break;
            case 6:score=16-previousScore;break;
            case 7:score=22-previousScore;break;
            case 8:score=29-previousScore;break;
            case 9:score=37-previousScore;break;
            case 10:score=46-previousScore;break;
            case 11:score=56-previousScore;break;
            default: break;
        }

        boolean[] gaiaHeads= player.getGreenRealm().getGaia().getGurdiansHealth();
        boolean two=gaiaHeads[2];boolean three=gaiaHeads[3];boolean four=gaiaHeads[4];boolean five=gaiaHeads[5];
        boolean six=gaiaHeads[6];boolean seven=gaiaHeads[7];boolean eight=gaiaHeads[8];boolean nine=gaiaHeads[9];
        boolean ten=gaiaHeads[10];boolean eleven=gaiaHeads[11];boolean twelve=gaiaHeads[12];
        String attack=whatReward(temp, attackNumber);
        switch (attack) {
            case "null": score+=0;break;
            case "tw": score+=1;break;
            case "ec": score+=2;break;
            case "AB": score+=12;break;
            default: score+=10;break;
        }
        if (value ==2)
        {
            two=true;
            if (isBonus(two, six, ten))
            score+=greenBonus(player, "col2");
            if (isBonus(two, three, four))
            score+=greenBonus(player, "row1");
        }
        if (value==3)
        {
            three=true;
        if (isBonus(three, seven, eleven))
            score+=greenBonus(player, "col3");
        if (isBonus(two, three, four))
            score+=greenBonus(player, "row1");
        }
        else if (value==4)
        {
            four=true;
            
        if (isBonus(two, three, four))
            score+=greenBonus(player, "row1");
        if (isBonus(four, eight, twelve))
            score+=greenBonus(player, "col4");
        }
        else if (value==5)
        {
            five=true;
        if (isBonus (five,six, seven, eight))
            score+=greenBonus(player, "row2");
        if (isBonus(five, nine, true))
            score+=greenBonus(player, "col1");
        }
        else if (value==6)
        {
            six=true;
        if (isBonus (five,six, seven, eight))
            score+=greenBonus(player, "row2");
        if (isBonus(two, six,ten))
            score+=greenBonus(player, "col2");
        }
        else if (value==7)
        {
            seven=true;
        if (isBonus(five,six, seven, eight))
            score+=greenBonus(player, "row2");
        if (isBonus(three, seven,eleven))
            score+=greenBonus(player, "col2");
        }
        else if(value==8)
        {
            eight=true;
        if (isBonus(five,six, seven, eight))
            score+=greenBonus(player, "row2");
        if (isBonus(four, eight,twelve))
            score+=greenBonus(player, "col4");
        }
        else if(value==9)
        {
            nine=true;
        if (isBonus(nine,ten, eleven, twelve))
            score+=greenBonus(player, "row3");
        if (isBonus(five, nine,true))
            score+=greenBonus(player, "col1");
        }
        else if(value==10)
        {
            ten=true;
        if (isBonus(nine,ten, eleven, twelve))
            score+=greenBonus(player, "row3");
        if (isBonus(two, six,ten))
            score+=greenBonus(player, "col2");
        }
        else if(value==11)
        {
            eleven=true;
        if (isBonus(nine,ten, eleven, twelve))
            score+=greenBonus(player, "row3");
        if (isBonus(three, seven,eleven))
            score+=greenBonus(player, "col3");
        }
        else if(value==12)
        {   
            twelve=true;
        if (isBonus(nine,ten, eleven, twelve))
            score+=greenBonus(player, "row3");
        if (isBonus(four, eight,twelve))
            score+=greenBonus(player, "col4");
        }

        return score;

    }
    
    public int greenBonus(Player player,String s)
    {
        int score=0;
        Reward temp[]=player.getGreenRealm().getRealmRewards();
        if (s=="row1")
        {
            String attack=whatReward(temp,1);
        switch (attack) {
            case "null": score+=0;break;
            case "tw": score+=1;break;
            case "ec": score+=2;break;
            case "AB": score+=12;break;
            default: score+=10;break;
        }
            return score;

        }
        if (s=="row2")
        {
            String attack=whatReward(temp,2);
        switch (attack) {
            case "null": score+=0;break;
            case "tw": score+=1;break;
            case "ec": score+=2;break;
            case "AB": score+=12;break;
            default: score+=10;break;
        }
            return score;

        }
        if (s=="row3")
        {
            String attack=whatReward(temp,3);
        switch (attack) {
            case "null": score+=0;break;
            case "tw": score+=1;break;
            case "ec": score+=2;break;
            case "AB": score+=12;break;
            default: score+=10;break;
        }
            return score;

        }
        if (s=="col1")
        {
            String attack=whatReward(temp,4);
        switch (attack) {
            case "null": score+=0;break;
            case "tw": score+=1;break;
            case "ec": score+=2;break;
            case "AB": score+=12;break;
            default: score+=10;break;
        }
            return score;

        }
        if (s=="col2")
        {
            String attack=whatReward(temp,5);
        switch (attack) {
            case "null": score+=0;break;
            case "tw": score+=1;break;
            case "ec": score+=2;break;
            case "AB": score+=12;break;
            default: score+=10;break;
        }
            return score;

        }
        if (s=="col3")
        {
            String attack=whatReward(temp,6);
        switch (attack) {
            case "null": score+=0;break;
            case "tw": score+=1;break;
            case "ec": score+=2;break;
            case "AB": score+=12;break;
            default: score+=10;break;
        }
            return score;

        }
        if (s=="col4")
        {
            String attack=whatReward(temp,7);
        switch (attack) {
            case "null": score+=0;break;
            case "tw": score+=1;break;
            case "ec": score+=2;break;
            case "AB": score+=12;break;
            default: score+=10;break;
        }
            return score;

        }
        return 0;


    }

    public boolean isBonus(boolean one,boolean two,boolean three) //method for scoreRedTurn3
    {
        boolean isBonusAvailable= (one && two) && (two && three);
        return isBonusAvailable;
    }

    public boolean isBonus(boolean one,boolean two,boolean three,boolean four) //method for scoreRedTurn3
    {
        boolean isBonusAvailable= (one && two) && (two && three) && (three && four);
        return isBonusAvailable;
    }

    public int scoreBlueTurn3(Player player, Dice dice)
    {
        if(dice==null) return 0;
        Reward[] temp=player.getBlueRealm().getRealmRewards();
        boolean  access,status,isvalidAttack;
        access=player.getBlueRealm().isRealmAccessible();
        status=!(dice.getDiceStatus()==DiceStatus.FORGOTTEN_REALM);
        if(player.getPlayerStatus()==PlayerStatus.PASSIVE) status=!status;
        int value=dice.getValue();
        isvalidAttack=player.getBlueRealm().getHydra().checkPossibleAttack(value);
        int attackNumber=player.getBlueRealm().getTotalNumberOfAttacks()+1;
        if (!access || !status || !isvalidAttack) return 0;
        int previousScore=player.getGreenRealm().getTotalRealmScore();
        int score=0;
        switch(attackNumber)
        {
            case 1:score=1-previousScore;break;
            case 2:score=3-previousScore;break;
            case 3:score=6-previousScore;break;
            case 4:score=10-previousScore;break;
            case 5:score=15-previousScore;break;
            case 6:score=21-previousScore;break;
            case 7:score=28-previousScore;break;
            case 8:score=36-previousScore;break;
            case 9:score=45-previousScore;break;
            case 10:score=55-previousScore;break;
            case 11:score=66-previousScore;break;
            default: break;
        }
        String attack=whatReward(temp, attackNumber);
        switch (attack) {
            case "null": score+=0;break;
            case "tw": score+=1;break;
            case "ec": score+=2;break;
            case "AB": score+=12;break;
            default: score+=10;break;
        }
            return score;
    }


    public int scoreMagentaTurn3(Player player, Dice dice)
    {  
        if(dice==null) return 0;
        Reward[] temp=player.getMagentaRealm().getRealmRewards();
         boolean access, status, isvalidAttack;
        access=player.getMagentaRealm().isRealmAccessible();
        status=!(dice.getDiceStatus()==DiceStatus.FORGOTTEN_REALM);
        if(player.getPlayerStatus()==PlayerStatus.PASSIVE) status=!status;

        int value=dice.getValue();
        isvalidAttack=player.getMagentaRealm().getPhoenix().checkPossibleAttack(value);
        if (!access || !status || !isvalidAttack) return 0;

        int attackNumber=player.getMagentaRealm().getTotalNumberOfAttacks()+1;
        int score=0;
        String attack=whatReward(temp, attackNumber);
        switch (attack) {
            case "null": score+=0;break;
            case "tw": score+=1;break;
            case "ec": score+=2;break;
            case "AB": score+=12;break;
            default: score+=10;break;
        }
            score+=value;
            return score;
        
    }





    public String whatReward(Reward[] reward,int attackNumber)
    {
        int i=attackNumber-1;
        if(reward[i]== null) return "null";
        if(reward[i]==new TimeWarp()) return "tw";
        if(reward[i]==new ElementalCrest()) return "ec";
        if(reward[i]==new ArcaneBoost()) return "AB";
        return "bonus";
    }

    public int scoreYellowTurn3(Player player,Dice dice)  //score is the value + whatever I get
    {  
        if(dice==null) return 0;
        Reward[] temp=player.getYellowRealm().getRealmRewards();
        
        //===================================================================================
        boolean access, status;
        access=player.getYellowRealm().isRealmAccessible();
        status=!(dice.getDiceStatus()==DiceStatus.FORGOTTEN_REALM);
        if(player.getPlayerStatus()==PlayerStatus.PASSIVE) status=!status;
        if (!access || !status) return 0;

        int attackNumber=player.getYellowRealm().getTotalNumberOfAttacks()+1;
        int value=dice.getValue();
        int score=0;
        String attack=whatReward(temp, attackNumber);
        switch (attack) {
            case "null": score+=0;break;
            case "tw": score+=1;break;
            case "ec": score+=2;break;
            case "AB": score+=12;break;
            default: score+=10;break;
        }
        if(attackNumber==4 || attackNumber==7 || attackNumber==9) // x2
        score+=value;
        if(attackNumber==11) // x3 
        score+=value*2;
        
        score+=value;
        return score;
    }

    public int dragonRemaining(boolean one, boolean two)
    {
        if(one && two) return 2;
        if(one || two) return 1;
        return 0;
        
    }
// =========================================Turn 1 Best MOVE ====================================================
    public Dice[] bestChoicesTurn1(Player player, Dice[] dice){ //move []
        if(dice==null) return null;
        int[] diceValues = new int[dice.length];
        for(int i=0 ; i<dice.length;i++){
            diceValues[i]= dice[i].getValue();
        }
        Arrays.sort(diceValues);
        
        if(probabilityBlue(dice[0], player)==probabilityGreen(dice[0], player)&&probabilityGreen(dice[0], player)==probabilityMagenta(dice[0], player)
        && probabilityMagenta(dice[0], player) == probabilityRed(dice[0], player)&& probabilityRed(dice[0], player)==probabilityYellow(dice[0], player)){
            Dice[] choice = new Dice[6];
        for(int i =0; i<dice.length; i++){
            for(int j=0; j<3;j++){
                if(diceValues[j]==dice[i].getValue())
                        choice[i]=dice[i];
                        else
                        choice[i].setValue(0);
            }
        }
        for (int i =0; i<6;i++){
            if(diceValues[2]==choice[i].getValue()){
                if(dice[5].getValue()<=choice[i].getValue())
                    choice[5]= dice[5];
            }
        }
         return choice;  
        }

        double[] probs = new double[5];
        probs[0]= probabilityRed(dice[0], player);
        probs[1]= probabilityGreen(dice[0], player);
        probs[2]= probabilityBlue(dice[0], player);
        probs[3]= probabilityMagenta(dice[0], player);
        probs[4]= probabilityYellow(dice[0], player);
        double sortedProbs[] = new double[5];
        int[] index= new int[3];
        for(int i=0; i<5; i++){
            sortedProbs[i] = probs[i];
        }
        Arrays.sort(sortedProbs);
        if(sortedProbs[4]==sortedProbs[3]){
            if(sortedProbs[3]==sortedProbs[2]){
                for(int i=0; i<5;i++){
                    for(int j =0; j<2;j++)
                    if(sortedProbs[j]==probs[i]){
                        for (int m=0; m<2;m++){
                            index[m]= i;
                            index[2]= 0;
                        }
                    }
                }
                Dice[] choice = new Dice[6];
                for(int i=0;i<3;i++){
                    if(index[i]==0){
                        choice[0]= dice[0];
                        if(index[i]==1){
                            choice[1]= dice[1];
                            if(index[i]==2){
                                choice[2]= dice[2];
                                if(index[i]==3){
                                    choice[3]= dice[3];
                                    if(index[i]==4){
                                        choice[4]= dice[4];}
                                        else
                                    choice[4].setValue(0);
                                }else
                                choice[3].setValue(0);
                            }else
                            choice[2].setValue(0);
                        }  else
                           choice[1].setValue(0);
                        }else
                         choice[0].setValue(0);
                }
                for (int i =0; i<6;i++){
                    if(diceValues[3]==choice[i].getValue()){
                        if(dice[5].getValue()<=choice[i].getValue())
                            choice[5]= dice[5];
                            else choice[5].setValue(0);
                    }
                }
            }
                        else{
                for(int i=0; i<5;i++){
                    for(int j =0; j<3;j++)
                    if(sortedProbs[j]==probs[i]){
                        for (int m=0; m<3;m++){
                            index[m]= i;
                        }
                    }
                }
            }
        }
        Dice[] choice = new Dice[6];
        for(int i=0;i<3;i++){
            if(index[i]==0){
                choice[0]= dice[0];
                if(index[i]==1){
                    choice[1]= dice[1];
                    if(index[i]==2){
                        choice[2]= dice[2];
                        if(index[i]==3){
                            choice[3]= dice[3];
                            if(index[i]==4){
                                choice[4]= dice[4];}
                                else
                            choice[4].setValue(0);
                        }else
                        choice[3].setValue(0);
                    }else
                    choice[2].setValue(0);
                }  else
                   choice[1].setValue(0);
                }else
                 choice[0].setValue(0);
        }
        for (int i =0; i<6;i++){
            if(diceValues[2]==choice[i].getValue()){
                if(dice[5].getValue()<=choice[i].getValue())
                    choice[5]= dice[5];
                    else choice[5].setValue(0);
            }
        }
        return choice;
    }

//=====================================CALCULATING PROBABILITES ===================================
    public double probabilityRed (Dice dice, Player player){
        boolean access, status;
        access = player.getRedRealm().isRealmAccessible();
        status= !(dice.getDiceStatus()==DiceStatus.FORGOTTEN_REALM);
        if(player.getPlayerStatus()==PlayerStatus.PASSIVE) status=!status;
        if(!access || !status) return 0;
        int dragon1Counter = 0;
        int dragon2Counter = 0;
        int dragon3Counter = 0;
        int dragon4Counter = 0;
        if(player.getRedRealm().getDragon1().isDeadDragon()==false){
            if(player.getRedRealm().getDragon1().isFaceKilled()==false) dragon1Counter+=1;
            if(player.getRedRealm().getDragon1().isHeartKilled()==false) dragon1Counter+=1;
            if(player.getRedRealm().getDragon1().isTailKilled()==false) dragon1Counter+=1;
            if(player.getRedRealm().getDragon1().isWingsKilled()==false) dragon1Counter+=1;
        }
        if(player.getRedRealm().getDragon2().isDeadDragon()==false){
            if(player.getRedRealm().getDragon2().isFaceKilled()==false) dragon3Counter+=1;
            if(player.getRedRealm().getDragon2().isHeartKilled()==false) dragon3Counter+=1;
            if(player.getRedRealm().getDragon2().isTailKilled()==false) dragon3Counter+=1;
            if(player.getRedRealm().getDragon2().isWingsKilled()==false) dragon3Counter+=1;
        }
        if(player.getRedRealm().getDragon3().isDeadDragon()==false){
            if(player.getRedRealm().getDragon3().isFaceKilled()==false) dragon3Counter+=1;
            if(player.getRedRealm().getDragon3().isHeartKilled()==false) dragon3Counter+=1;
            if(player.getRedRealm().getDragon3().isTailKilled()==false) dragon3Counter+=1;
            if(player.getRedRealm().getDragon3().isWingsKilled()==false) dragon3Counter+=1;
        }
        if(player.getRedRealm().getDragon4().isDeadDragon()==false){
            if(player.getRedRealm().getDragon4().isFaceKilled()==false) dragon4Counter+=1;
            if(player.getRedRealm().getDragon4().isHeartKilled()==false) dragon4Counter+=1;
            if(player.getRedRealm().getDragon4().isTailKilled()==false) dragon4Counter+=1;
            if(player.getRedRealm().getDragon4().isWingsKilled()==false) dragon4Counter+=1;
        }
        double probDragon1 = dragon1Counter/6;
        double probDragon2= dragon2Counter/6;
        double probDragon3= dragon3Counter/6;
        double probDragon4= dragon4Counter/6;
        if(dragon1Counter==4 && dragon2Counter==4&& dragon3Counter==4&&dragon4Counter==4)
        return 1;
        double prob = Math.max(Math.max(probDragon1, probDragon2), Math.max(probDragon3, probDragon4));
        return prob;
    }
    
    public double probabilityGreen (Dice dice, Player player){
        boolean access, status;
        access=player.getGreenRealm().isRealmAccessible();
        status=!(dice.getDiceStatus()==DiceStatus.FORGOTTEN_REALM);
        if(player.getPlayerStatus()==PlayerStatus.PASSIVE) status=!status;
        if (!access || !status) return 0;
        int gaiaDead=0;
        for(int i = 2; i<13; i++){
         if(player.getGreenRealm().getGaia().getGurdiansHealth()[i]==true)
         gaiaDead+=1;}
        double prob = gaiaDead/ 12;
        return prob;
    }

    public double probabilityBlue (Dice dice, Player player){
        boolean access, status;
        access = player.getBlueRealm().isRealmAccessible();
        status =!(dice.getDiceStatus()==DiceStatus.FORGOTTEN_REALM);
        if(player.getPlayerStatus()==PlayerStatus.PASSIVE) status=!status;
        if(!access || !status) return 0;
        int hydraAv= 6- player.getBlueRealm().getHydra().getMinimumAttackValue();
        double prob = hydraAv/6;
        return prob;
    }

    public double probabilityMagenta (Dice dice, Player player){
        boolean access, status;
        access=player.getMagentaRealm().isRealmAccessible();
        status=!(dice.getDiceStatus()==DiceStatus.FORGOTTEN_REALM);
        if(player.getPlayerStatus()==PlayerStatus.PASSIVE) status=!status;
        if (!access || !status ) return 0;
        int min = 6- player.getMagentaRealm().getPhoenix().getMinimumAttackValue();
        double prob = min/6;
        return prob;
    }

    public double probabilityYellow (Dice dice, Player player){
        boolean access, status;
        access=player.getYellowRealm().isRealmAccessible();
        status=!(dice.getDiceStatus()==DiceStatus.FORGOTTEN_REALM);
        if(player.getPlayerStatus()==PlayerStatus.PASSIVE) status=!status;
        if (!access || !status) return 0;
        return 1.0;
    }
    
    
    

}
    /*
     *  1 bonus-> score=10+value
     *  2 bonuss->score=20+value
     *  x2 Multiplier-> score=values 1,2-> score=1+value,3-> score=3+value  ,4-> scsore=6+value , 5-> score=7+value, 6-> score=8+value
     *  x3 Multiplier-> score=values 1-> score=1+valuee,2-> score =3+value  ,3 -> score=6+valuee ,4->score=8+value,5->score=9+value
     *  TimeWarp-> score=1+ value
     * Elemental Crest-> score+=2+value
     * Arcane Boost +12
     */
    
