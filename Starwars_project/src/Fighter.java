public class Fighter extends Decorator{

    public Fighter(Enemy e){
        super(e, e.getName().contains("Fighter") ? e.getName() : e.getName() + " Fighter", 1);
    }

    @Override
    public void attack(Entity e) {
        super.attack(e);
        int additionalDamage = 1;
        System.out.println(getName() + " again attacks " + e.getName() + " for " +  additionalDamage + " damage.");
        e.takeDamage(additionalDamage);
    }

    public static void main(String[] args) {
    }
}
