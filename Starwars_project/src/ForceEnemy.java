import java.util.Random;

/**
 * @author Sergio Vasquez
 * Force Enemy Class - Representation of a single Force Enemy
 */
public class ForceEnemy extends Enemy implements Force {
    /**
     * Creates a force enemy with the specific attributes
     * @param name  The name of the enemy
     * @param level The level of the enemy
     * @param maxHp The level of the enemy
     * @param item  The item of the enemy
     */
    public ForceEnemy(String name, int level, int maxHp, Item item) {
        super(name, level, maxHp, item);
    }
    /**
     * Perform a force push
     * @return the attack power of the force push
     */
    @Override
    public int forcePush() {
        double prob = Math.random();
        final double THRESHOLD = 0.5;

        // return different damage amount based on if probability reached certain threshold
        final int LOW_DAMAGE = 3;
        final int MEDIUM_DAMAGE = 5;
        if(Double.compare(prob, THRESHOLD) < 0){
            return LOW_DAMAGE * getLevel();
        } else {
            return MEDIUM_DAMAGE * getLevel();
        }
    }
    /**
     * Perform a force choke
     * @return the attack power of the force choke
     */
    @Override
    public int forceChoke() {
        final int MULTIPLIER = 2;
        return getLevel() * MULTIPLIER;
    }
    /**
     * Perform a force slam
     * @return the attack power of the force slam
     */
    @Override
    public int forceSlam() {
        int damage = (int) (Math.random() * getLevel()) + 1;
        double prob = Math.random();
        final double THRESHOLD = 0.75;
        /* return different damage amount based on if probability reached certain threshold
        * force slam is a high-risk high reward attack  */
        if (Double.compare(prob, THRESHOLD) < 0) {
            final int LOW_DAMAGE = 1;
            return LOW_DAMAGE;
        } else {
            final int MULTIPLIER = 2;
            return damage * MULTIPLIER;
        }
    }
    /**
     * Attack an entity
     * @param e the entity to be attacked
     */
    @Override
    public void attack(Entity e){
        // force enemy has a 1/4 chance in using all different types of attacks
        final int BOUND = 4;
        Random random = new Random();
        int choice = random.nextInt(BOUND);

        // Using the force will require we retrieve the necessary
        // content to print to the user
        boolean using_force = choice > 0;
        String forceOption = "";
        int damageAmount = 0;
        // 0 : regular attack
        // 1 : force push
        // 2 : force choke
        // 3 : force slam
        switch(choice){
            case 0:
                    // Enemy normal attack already prints necessary information
                    super.attack(e);
                    break;
            case 1:
                    forceOption = "Force Push";
                    damageAmount = forcePush();
                    break;
            case 2:
                    forceOption = "Force Choke";
                    damageAmount = forceChoke();
                    break;
            case 3:
                    forceOption = "Force Slam";
                    damageAmount = forceSlam();
                    break;
        }

        if(using_force){
            // Example : Sith Apprentice hits Luke with a Force Slam for 5 damage.
            System.out.println(getName() + " hits " + e.getName() + " with a " + forceOption + " for " + damageAmount + " damage.");
            e.takeDamage( damageAmount );
        }
    }

    /**
    public static void main(String[] args) {
        Map map = new Map();
        Hero hero = new Hero("Luke", map);
        ForceEnemy forceEnemy = new ForceEnemy("Sith Apprentice", 1, hero.getLevel() * 1, new Item("Med Kit"));
        for( int i = 0; i < 10; ++i){
            forceEnemy.attack(hero);
        }
    }
     */
}
