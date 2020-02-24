public class Geonosian extends Enemy{
    public Geonosian(int level, int maxHp, Item item) {
        super("Geonosian", level, maxHp, item);
    }

    @Override
    public void attack(Entity e) {
        super.attack(e);
    }
}
