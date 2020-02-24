/**
 * Interface for Objects with the force
 * @author Sergio Vasquez
 */
public interface Force {
    public static final String FORCE_MENU = "1. Force Push\n2. Force Choke\n3. Force Slam";
    /**
     * Perform a force push
     * @return the attack power of the force push
     */
    public int forcePush();
    /**
     * Perform a force choke
     * @return the attack power of the force choke
     */
    public int forceChoke();
    /**
     * Perform a force slam
     * @return the attack power of the force slam
     */
    public int forceSlam();
}
