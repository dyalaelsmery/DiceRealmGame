package game.engine;

import game.creatures.*;
import game.dice.Dice;

public class Move implements Comparable<Move> {
    private Dice dice;
    private Creature creature;
//=======================================Constructor===================================
    public Move(Dice dice,Creature creature){
        this.dice=dice;
        this.creature=creature; 
    }
//=======================================Methods======================================= 
    @Override
    public int compareTo(Move o) {
        return this.dice.compareTo(o.getDice());
    }
//=======================================Get&Set=======================================
    public Dice getDice(){
        return dice;
    }
    public Creature getMoveCreature(){
        return creature;
    }
//=======================================Display=======================================    
    @Override 
    public String toString(){
        return dice+" "+creature;
    }
}
