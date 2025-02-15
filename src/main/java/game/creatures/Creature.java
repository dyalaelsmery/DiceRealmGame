package game.creatures;
import  game.realms.*;
public abstract class Creature {
    private final RealmColor CREATURE_COLOR;
    //=======================================Constructor===================================
        public Creature(RealmColor color){
                CREATURE_COLOR=color;
            }
    //=======================================Methods=======================================
        public abstract boolean checkPossibleAttack(int diceValue);
        public int getMinimumAttackValue(){//This is a defaultMethod override when needed;
            return 0;
        }
    //=======================================Get&Set=======================================
        public RealmColor getCreatureColor(){
            return CREATURE_COLOR;
        }
    //=======================================Display=======================================
        @Override 
        public abstract String toString();
}
