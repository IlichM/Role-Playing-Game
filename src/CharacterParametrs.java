public abstract class CharacterParametrs implements Destroyer {

    //имя персонажа
    private String name;

    //опции персонажа
    private int health;
    private int power;
    private int skill;

    //золото и опыт
    private int gold;
    private int xp;

    public CharacterParametrs(String name,int health,int power,int skill,int gold,int xp) {
         this.name = name;
         this.health = health;
         this.power = power;
         this.skill = skill;
         this.gold = gold;
         this.xp = xp;
    }

    public String getName() { return name; }

    public void setHealth(int health) { this.health = health; }
    public int getHealth() { return health; }

    public void setPower(int power) { this.power = power; }
    public int getPower() { return power; }

    public void setSkill(int skill) { this.skill = skill; }
    public int getSkill() { return skill; }

    public void setGold(int gold) { this.gold = gold; }
    public int getGold() { return gold; }

    public void setXp(int xp) { this.xp = xp; }
    public int getXp() { return xp; }

    public int getRandomValue() { return (int) (Math.random()*100); }

    @Override
    public String toString() { return String.format("%sздоровье %d:",name,health); }

    @Override
    public int attack() {
        if(skill*3 > getRandomValue()) { return power; }
        else { return 0; }
    }
}