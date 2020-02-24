public class Rodian extends Enemy {
    public Rodian(int level, int maxHp, Item item) {
        super("Rodian", level, maxHp, item);
    }

    @Override
    public void attack(Entity e) {
        super.attack(e);
    }
}
