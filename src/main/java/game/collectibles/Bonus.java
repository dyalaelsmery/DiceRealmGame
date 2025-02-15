package game.collectibles;

import game.realms.RealmColor;

public class Bonus extends Reward{
    private RealmColor bonusColor;
    public Bonus(RealmColor bonusColor){
        super(RewardType.BONUS);
        this.bonusColor=bonusColor;
    }
    public RealmColor getBonusColor(){
        return bonusColor;
    }
    protected void setEssenceBonusColor(RealmColor color){
        this.bonusColor=color;
    }
}
