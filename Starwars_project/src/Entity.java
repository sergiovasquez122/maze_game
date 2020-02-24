/**
 * Entity class - Representation of a generic type of entity
 * @author Sergio Vasquez
 */
public abstract class Entity {
    /** The name of the entity */
    private String name;
    /** The current level of the entity */
    private int level;
    /** The maximum health of the entity */
    private int maxHp;
    /** The current health of the entity */
    private int hp;
    /**
     * Constructor - sets the attributes of the entity
     * @param name sets the name of the entity
     * @param level sets the level of the entity
     * @param maxHp sets the max health of the entity
     */
    public Entity(String name, int level, int maxHp){
        this.name = name;
        this.level = level;
        this.maxHp = maxHp;
        // Entity health by default is maximum
        hp = this.maxHp;
    }
    /**
     * Attack another entity
     * @param e the entity to be attacked
     */
    public abstract void attack(Entity e);
    /**
     * Retrieve the entity's level
     * @return  the entity's level
     */
    public int getLevel(){
        return level;
    }
    /**
     * Retrieve the entity's health
     * @return the entity's health
     */
    public int getHP(){
        return hp;
    }
    /**
     * Retrieve the entity's max health
     * @return the entity's max health
     */
    public int getMaxHP(){
        return maxHp;
    }
    /**
     * Increase the level of the entity by one and increase their maxHp by 10
     */
    public void increaseLevel() {
        final int HEALTH_INCREASE = 10;
        level++;
        increaseMaxHP(HEALTH_INCREASE);
    }
    /**
     * Retrieve the entity's name
     * @return the entity's name
     */
    public String getName(){return name;}
    /**
     * Increases the entity hp up to it's maximum health
     * @param h increase the entity's hp up to h amount
     */
    public void heal(int h){
        if(hp + h > maxHp){
            hp = maxHp;
        } else{
            hp += h;
        }
    }
    /**
     * Decreases the entity hp by h
     * @param h decrease the entity hp by h
     */
    public void takeDamage(int h){
        if(hp - h < 0){
            hp = 0;
        } else{
            hp -= h;
        }
    }
    /**
     * Increase maxHp of the entity by h amount
     * @param h the amount to increase the entity's hp by
     */
    public void increaseMaxHP(int h){
        maxHp += h;
    }
    /**
     * Decrease the maxHp of the entity by h amount
     * @param h the amount to decrease the entity's hp by
     */
    public void decreaseMaxHP(int h){
        /* The health of the entity must at least be one */
        if(maxHp - h < 1){
            maxHp = 1;
        } else {
            maxHp -= h;
        }
    }
    /**
     * Displays the entity's attributes
     */
    public void display(){
        System.out.println(name + " Lvl:" + level);
        System.out.println("HP: " + hp + "/" + maxHp);
    }
}
