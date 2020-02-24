import java.awt.*;
import java.util.Random;

/**
 * Main class - Where the game takes place
 * @author Sergio Vasquez
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("What is your name? ");
        String name = CheckInput.getString();

        Map map = Map.getInstance();
        Hero hero = new Hero(name, map);
        ItemGenerator itemGenerator = ItemGenerator.getInstance();
        EnemyGenerator enemyGenerator = EnemyGenerator.getInstance(itemGenerator);
        int mapNum = 1;
        boolean gameOver = false;

        // Continue while the hero has not died or the user has not decided to quit
        while ( !gameOver ) {

            hero.display();
            hero.displayItems();
            map.displayMap(hero.getLocation());
            System.out.println("1. Go North\n2. Go South\n3. Go East\n4. Go West\n5. Quit");

            // The hero has 5 options to choose form 1 indexed
            int choice = CheckInput.getIntRange(1, 5);

            char c = 'n';
            switch ( choice ) {
                case 1:
                    c = hero.goNorth();
                    break;
                case 2:
                    c = hero.goSouth();
                    break;
                case 3:
                    c = hero.goEast();
                    break;
                case 4:
                    c = hero.goWest();
                    break;
                case 5:
                    // The user has chosen to exit the game
                    System.out.println("Game Over");
                    return;
            }

            switch ( c ) {
                case 'e':
                    gameOver = !enemyRoom(hero, map, enemyGenerator);
                    break;
                case 'i':
                    itemRoom(hero, map, itemGenerator);
                    break;
                case 'f':
                    // If the hero successfully goes on to the next map
                    // set the current map to it
                    if ( finishRoom(hero, map, mapNum + 1) ) {
                        mapNum++;
                    }
                    break;
            }
        }
        System.out.println("Game Over");
    }
    /**
     * Hero enters finishRoom, decides if hero moves to the next level or not
     * @param hero   the Hero of the game
     * @param map    the current map
     * @param mapNum the new map location to be loaded
     * @return true if the hero successfully cleared the map, false otherwise
     */
    public static boolean finishRoom(Hero hero, Map map, int mapNum) {

        boolean moveOntoNextLevel = false;
        if ( hero.hasKey() ) {
            moveOntoNextLevel = true;
            hero.removeItem("Key");
        } else if ( hero.hasHolocron() && !hero.hasKey() ) {
            System.out.println("Would you like to use the force to try to open the door?");

            /* If the hero has decided to use the force
               remove it from their inventory and
               give them a random chance to open
               the door
             */
            if ( CheckInput.getYesNo() ) {
                hero.removeItem("Holocron");

                // Give the hero a random chance of opening the door
                final double THRESHOLD = .5;
                moveOntoNextLevel = ( Math.random() >= THRESHOLD );
                if ( !moveOntoNextLevel ) {
                    System.out.println("Attempt Failed!");
                }
            }
        }

        if ( moveOntoNextLevel ) {
            hero.increaseLevel();
            map.loadMap( mapNum );
            System.out.println("Now on level " + mapNum);
            return true;
        }
        return false;
    }
    /**
     * Method handles when Hero enters enemy room
     * @param hero           the current hero
     * @param map            the current map
     * @param enemyGenerator generates a random enemy
     * @return true if the hero is still alive false otherwise
     */
    public static boolean enemyRoom(Hero hero, Map map, EnemyGenerator enemyGenerator) {
        Enemy enemy = enemyGenerator.generateEnemy(hero.getLevel());
        System.out.println("You've encountered a " + enemy.getName());
        enemy.display();

        // Continue until the hero or the enemy is killed
        while ( hero.getHP() != 0 && enemy.getHP() != 0 ) {
            // The default option menu and the amount of option the hero has currently to choose from
            String menu = "1. Fight\n2. Run Away";
            int numOfOptions = 2;

            // If the hero has a med kit give them option of using it
            if ( hero.hasMedKit() ) {
                menu += "\n3. Med Kit";
                numOfOptions++;
            }

            System.out.println( menu );
            int choice = CheckInput.getIntRange(1, numOfOptions);

            switch ( choice ) {
                case 1:
                    fight(hero, enemy);

                    // The enemy has died remove them from the map
                    if ( enemy.getHP() == 0 ) {
                        map.removeCharAtLoc(hero.getLocation());
                    }
                    break;
                case 2:
                    runAway( hero );
                    return true;
                case 3:
                    // Hero phase they heal up to maxHp
                    final int HEAL_AMOUNT = 25;
                    hero.heal(HEAL_AMOUNT);
                    hero.removeItem("Med Kit");
                    System.out.println( "You heal" );

                    // Enemy Phase
                    if ( hero.hasArmor() ) {
                        String armorName = hero.removeFirstArmorItem();
                        System.out.println(hero.getName() + " defended himself with " + armorName);
                    } else {
                        enemy.attack(hero);
                    }
                    break;
            }
        }
        // Did the hero survive the encounter?
        return hero.getHP() != 0;
    }
    /**
     * hero moves a random direction on the map
     * @param hero the hero of the game
     */
    public static void runAway(Hero hero) {
        Random random = new Random();
        Point oldLocation = hero.getLocation();

        // While the hero location has not changed
        while ( oldLocation.equals( hero.getLocation() ) ) {

            // hero has four directions they can go
            final int BOUND = 4;
            int walkDirection = random.nextInt( BOUND );

            switch ( walkDirection ) {
                case 0:
                    hero.goNorth();
                    break;
                case 1:
                    hero.goSouth();
                    break;
                case 2:
                    hero.goWest();
                    break;
                case 3:
                    hero.goEast();
                    break;
            }
        }
    }
    /**
     * Does a single trial of a fight between the hero and the enemy
     * @param hero the current hero
     * @param e    the current enemy
     * @return true if the hero is still alive, false otherwise
     */
    public static boolean fight(Hero hero, Enemy e) {

        hero.attack( e );
        // if the enemy survived the hero's attack
        // the enemy attacks the hero back
        // else the hero collects his item
        if ( e.getHP() != 0 ) {
            if ( hero.hasArmor() ) {
                String armorName = hero.removeFirstArmorItem();
                System.out.println(hero.getName() + " defended himself with " + armorName);
            } else {
                e.attack(hero);
            }
        } else {
            // Display victory screen
            System.out.println("You defeated the " + e.getName() + "!");
            Item item = e.getItem();
            System.out.println("You received a " + item.getName() + " from the enemy. ");

            // If the hero inventory is full
            // give them the option to drop an item
            if ( !hero.pickUpItem( item ) ) {
                System.out.println("Inventory full would you like to drop an item? ");
                // User decided to drop an item
                if ( CheckInput.getYesNo() ) {
                    System.out.println("What item would you liked to drop?");
                    hero.displayItems();

                    // The hero has up to fives items that they can drop
                    int index = CheckInput.getIntRange(1, 5);
                    // The hero item's are zero index
                    hero.removeItem(index - 1);
                    hero.pickUpItem(item);
                }
            }
        }
        return hero.getHP() != 0;
    }
    /**
     * Item room gives the hero a random item if they have available inventory
     * @param hero          the current hero of the game
     * @param map           the current map of the game
     * @param itemGenerator generates a random item
     */
    public static void itemRoom(Hero hero, Map map, ItemGenerator itemGenerator) {
        Item item = itemGenerator.generateItem();
        System.out.println( "You found a " + item.getName() );

        // if the hero has room for the item pick it up else give them the option of dropping an item
        if ( hero.pickUpItem( item ) ) {
            map.removeCharAtLoc( hero.getLocation() );
        } else {
            System.out.println("Inventory full would you like to drop an item?");
            // User decided to drop an item
            if ( CheckInput.getYesNo() ) {
                System.out.println("What item would you liked to drop?");
                hero.displayItems();

                // The hero currently has 5 items one indexed
                int index = CheckInput.getIntRange( 1,  5);
                hero.removeItem(index - 1);

                // Pick up the item from the item room and remove it from the map
                hero.pickUpItem(item);
                map.removeCharAtLoc(hero.getLocation());
            }
        }
    }
}
