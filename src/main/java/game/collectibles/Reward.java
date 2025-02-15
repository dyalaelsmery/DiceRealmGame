package game.collectibles;

import game.realms.RealmColor;

public abstract class Reward implements Comparable<Reward>{
    private final RewardType rewardType;
//=======================================Constructor===================================
    public Reward(RewardType rewardType){
        this.rewardType=rewardType;
    }
//=======================================Methods=======================================
    @Override
    public int compareTo(Reward o) {
        RewardType reward1=getRewardType();
        RewardType reward2=o.getRewardType();
        if(reward1==reward2&&reward1==RewardType.BONUS){//Secondary Sort based on color only if both bonus
            Bonus bonus1=(Bonus)this;
            Bonus bonus2=(Bonus)o;
            RealmColor color1=bonus1.getBonusColor();
            RealmColor color2=bonus2.getBonusColor();
            //Primary Sort on Color
            if(color1==RealmColor.WHITE)
                return -1;
            if(color2==RealmColor.WHITE)
                return 1;    
            if(color1==RealmColor.RED)
                return -1;
            if(color2==RealmColor.RED)
                return 1;
            if(color1==RealmColor.GREEN)
                return -1;
            if(color2==RealmColor.GREEN)
                return 1;
            if(color1==RealmColor.BLUE)
                return -1;
            if(color2==RealmColor.BLUE)
                return 1;  
            if(color1==RealmColor.MAGENTA)
                return -1;
            if(color2==RealmColor.MAGENTA)
                return 1;  
            if(color1==RealmColor.YELLOW)
                return -1;    
            return 1;//color2 yellow  
        }
        if(reward1==RewardType.BONUS)
            return-1;
        if(reward2==RewardType.BONUS)
            return 1;
        if(reward1==RewardType.POWER)
            return-1;
        if(reward2==RewardType.POWER)
            return 1; 
        if(reward1==RewardType.CREST)
            return-1;
        return 1;//Reward2 is crest           
    }
//=======================================Get&Set=======================================
    public RewardType getRewardType(){
        return rewardType;
    }
}
