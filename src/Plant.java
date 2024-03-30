import processing.core.PImage;

import java.util.List;

public abstract class Plant extends ActionEntity {

    //InstanceVariables
    private int health;
    private int healthLimit;
    private final String STUMP_KEY = "stump";


    //Getter & setter methods
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {this.health = health;}
    public int getHealthLimit() {
        return healthLimit;
    }
    public String getSTUMP_KEY() {return STUMP_KEY;}


    //Constructor
    public Plant(String id, Point position, List<PImage> images, double animationPeriod, double actionPeriod, int health, int healthLimit) {
        super(id, position, images, animationPeriod, actionPeriod);
        this.health = health;
        this.healthLimit = healthLimit;
    }

    public abstract boolean transformPlant(WorldModel world, EventScheduler scheduler, ImageStore imageStore);
}