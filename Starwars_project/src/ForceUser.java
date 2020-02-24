import java.util.Random;

public class ForceUser extends Decorator implements Force {


    public ForceUser(Enemy e){
        super(e, e.getName().contains("Force Fighter") ? e.getName() : e.getName() + " Force Fighter", 2);
    }

    @Override
    public void attack(Entity e) {
        super.attack(e);
        // force enemy has a 1/3 chance in using all different types of attacks
        final int BOUND = 3;
        Random random = new Random();
        int choice = random.nextInt(BOUND);

        // Using the force will require we retrieve the necessary
        // content to print to the user
        String forceOption = "";
        int damageAmount = 0;
        // 0 : regular attack
        // 1 : force push
        // 2 : force choke
        // 3 : force slam
        switch(choice){
            case 0:
                forceOption = "Force Push";
                damageAmount = forcePush();
                break;
            case 1:
                forceOption = "Force Choke";
                damageAmount = forceChoke();
                break;
            case 2:
                forceOption = "Force Slam";
                damageAmount = forceSlam();
                break;
        }

        // Example : Sith Apprentice hits Luke with a Force Slam for 5 damage.
        System.out.println(getName() +  " again hits " + e.getName() + " with a " + forceOption + " for " + damageAmount + " damage.");
        e.takeDamage( damageAmount );
    }

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

    @Override
    public int forceChoke() {
        final int MULTIPLIER = 2;
        return getLevel() * MULTIPLIER;
    }

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

    public static void main(String[] args) {
        EnemyGenerator enemyGenerator = EnemyGenerator.getInstance(ItemGenerator.getInstance());
        Enemy e = enemyGenerator.generateEnemy(5);
        Enemy e1 = enemyGenerator.generateEnemy(4);
        e.attack(e1);
    }
}
