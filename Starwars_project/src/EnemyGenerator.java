
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * EnemyGenerator Class - Single representation of EnemyGenerator
 * @author Sergio Vasquez
 */
public class EnemyGenerator {
    /** Singleton pattern the unique instance of EnemyGenerator */
    private static EnemyGenerator uniqueInstance;
    /** The list of all possible enemies */
    private ArrayList<Enemy> enemyList;
    /** The list of all possible items */
    private ItemGenerator itemGenerator;
    public static EnemyGenerator getInstance(ItemGenerator itemGenerator){
        if(uniqueInstance == null){
            uniqueInstance = new EnemyGenerator(itemGenerator);
        }
        return uniqueInstance;
    }
    /**
     * Constructor - Constructs a EnemyGenerator with a specified ItemGenerator
     * @param itemGenerator generates the possible types of items the enemy can have
     */
    private EnemyGenerator(ItemGenerator itemGenerator) {
        this.itemGenerator = itemGenerator;
        enemyList = new ArrayList<>();
    }
    /**
     * Generate a random enemy with specified level
     * @param level the level of the enemy
     * @return Generate a random enemy with specified level
     */
    public Enemy generateEnemy(int level) {
        // Get a random enemy with equal likelihood and generate a random item
        Random generator = new Random();
        int choice = generator.nextInt(4);
        Item item = itemGenerator.generateItem();
        Enemy e = null;

        if(choice == 0){
            e = new Dathomiri(level, 1,item);
        } else if(choice == 1){
            e = new Geonosian(level, 2, item);
        } else if(choice == 2){
            e = new Rodian(level, 3, item);
        } else if(choice == 3){
            e = new Twilek(level, 4, item);
        }

        int randomDecorator = generator.nextInt(2);
        for(int i = 1;i < level;++i){
            if(randomDecorator == 0){
                e = new Fighter(e);
            } else if(randomDecorator == 1){
                e = new ForceUser(e);
            }
        }
        return e;
    }
}
