import java.util.Random;

/**
 * Enemy Class - Single instance of an enemy
 * @author Sergio Vasquez
 */
public class Enemy extends Entity {
    /** The item that the enemy is holding */
    private Item item;
    /**
     * Creates an enemy with the specific attributes
     * @param name  The name of the enemy
     * @param level The level of the enemy
     * @param maxHp The level of the enemy
     * @param item  The item of the enemy
     */
    public Enemy(String name, int level, int maxHp, Item item) {
        super(name, level, maxHp);
        this.item = item;
    }
    /**
     * Attack an entity
     * @param e the entity to be attacked
     */
    @Override
    public void attack(Entity e) {
        // Damage range is between [CONSTANT_DAMAGE, e.getLevel() + CONSTANT_DAMAGE]
        final int CONSTANT_DAMAGE = 1;
        int randomDamage = new Random().nextInt(e.getLevel() + 1);
        int attackPower = randomDamage + CONSTANT_DAMAGE;

        System.out.println(getName() + " attacks " + e.getName() + " for " + attackPower + " damage.");
        e.takeDamage(attackPower);
    }
    /**
     * Retrieve the enemy item
     * @return the enemy item
     */
    Item getItem() {
        return item;
    }
}
