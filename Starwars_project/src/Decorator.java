public abstract class Decorator extends Enemy {
    private Enemy enemy;
    public Decorator(Enemy e, String title, int healthIncrement){
        super(title, e.getLevel(), e.getMaxHP() + healthIncrement, e.getItem());
        this.enemy = e;
    }

    @Override
    public void attack(Entity e){
        enemy.attack(e);
    }
}
