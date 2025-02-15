package game.creatures;

import game.realms.RealmColor;

public class Dragon extends Creature{
    private int face;
    private int  wings;
    private int tail;
    private int heart;
    private int points;
    private final DragonNumber DRAGON_NUMBER;

    //============================Constructor============================================
    /**
     * Constructs a Dragon object with specified attributes.
     * @param dragonNumber The number representing the dragon.
     * @param face The health of the dragon's face.
     * @param wings The health of the dragon's wings.
     * @param tail The health of the dragon's tail.
     * @param heart The health of the dragon's heart.
     * @param points The points associated with the dragon.
     */
    public Dragon(DragonNumber dragonNumber, int face, int wings, int tail, int heart, int points) {
        super(RealmColor.RED);
        this.face = face;
        this.wings = wings;
        this.tail = tail;
        this.heart = heart;
        this.points = points;
        DRAGON_NUMBER = dragonNumber;
    }

//============================Getters & Setters====================================================

    /**
     * Gets the health of the dragon's face.
     * @return The health of the dragon's face.
     */
    public int getFace() {
        return face;
    }

    /**
     * Sets the health of the dragon's face.
     * @param face The health of the dragon's face.
     */
    private void setFace(int face) {
        this.face = face;
    }

    /**
     * Gets the health of the dragon's wings.
     * @return The health of the dragon's wings.
     */
    public int getWings() {
        return wings;
    }

    /**
     * Sets the health of the dragon's wings.
     * @param wings The health of the dragon's wings.
     */
    private void setWings(int wings) {
        this.wings = wings;
    }

    /**
     * Gets the health of the dragon's tail.
     * @return The health of the dragon's tail.
     */
    public int getTail() {
        return tail;
    }

    /**
     * Sets the health of the dragon's tail.
     * @param tail The health of the dragon's tail.
     */
    private void setTail(int tail) {
        this.tail = tail;
    }

    /**
     * Gets the health of the dragon's heart.
     * @return The health of the dragon's heart.
     */
    public int getHeart() {
        return heart;
    }

    /**
     * Sets the health of the dragon's heart.
     * @param heart The health of the dragon's heart.
     */
    private void setHeart(int heart) {
        this.heart = heart;
    }

    /**
     * Gets the points associated with the dragon.
     * @return The points associated with the dragon.
     */
    public int getPoints() {
        return points;
    }

    /**
     * Sets the health of a specific part of the dragon.
     * @param part The part of the dragon to set health for (e.g., "Face", "Tail", "Wings", "Heart").
     */
    public final void setHealth(String part){
        switch (part){
            case "Face": setFace(0); break;
            case "Tail": setTail(0); break;
            case "Wings": setWings(0); break;
            case "Heart": setHeart(0); break;
            default:
                System.out.println("Unknown part");
        }
    }

    /**
     * Gets the number representing the dragon.
     * @return The number representing the dragon.
     */
    public DragonNumber getDragonNumber(){
        return DRAGON_NUMBER;
    }

//============================Methods================================================

    /**
     * Checks if the dragon is dead (all body parts are destroyed).
     * @return true if the dragon is dead, otherwise false.
     */
    public boolean isDeadDragon() {
        return face == 0 && wings == 0 && tail == 0 && heart == 0;
    }

    /**
     * Checks if the dragon's face is destroyed.
     * @return true if the dragon's face is destroyed, otherwise false.
     */
    public boolean isFaceKilled(){
        return face == 0;
    }

    /**
     * Checks if the dragon's wings are destroyed.
     * @return true if the dragon's wings are destroyed, otherwise false.
     */
    public boolean isWingsKilled(){
        return wings == 0;
    }

    /**
     * Checks if the dragon's tail is destroyed.
     * @return true if the dragon's tail is destroyed, otherwise false.
     */
    public boolean isTailKilled(){
        return tail == 0;
    }

    /**
     * Checks if the dragon's heart is destroyed.
     * @return true if the dragon's heart is destroyed, otherwise false.
     */
    public boolean isHeartKilled(){
        return heart == 0;
    }

    /**
     * Checks if a given value matches the health of any body part of the dragon.
     * @param value The value to check against the health of the dragon's body parts.
     * @return true if the value matches the health of any body part of the dragon, otherwise false.
     */
    public boolean valueInDragon(int value){
        return value == face || value == wings || value == tail || value == heart;
    }
    @Override
    public boolean checkPossibleAttack(int diceValue) {
        return !isDeadDragon() && diceValue == face || diceValue == wings || diceValue == tail || diceValue == heart;
    }
    @Override public String toString(){
        return "Dragon";
    }

}



