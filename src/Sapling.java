import processing.core.PImage;

import java.util.List;

public class Sapling extends Plant{
    private final String SAPLING_KEY = "sapling";
    private final String TREE_KEY = "tree";
    private final double TREE_ANIMATION_MAX = 0.600;
    private final double TREE_ANIMATION_MIN = 0.050;
    private final double TREE_ACTION_MIN = 1.000;
    private final double TREE_ACTION_MAX = 1.400;
    private final int TREE_HEALTH_MAX = 3;
    private final int TREE_HEALTH_MIN = 1;

    public Sapling(String id, Point position, List<PImage> images, double animationPeriod, double actionPeriod, int health, int healthLimit) {
        super(id, position, images, animationPeriod, actionPeriod, health, healthLimit);
    }

    @Override
    public boolean transformPlant(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        return transformSapling(world, scheduler, imageStore);
    }

    public boolean transformSapling(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (super.getHealth() <= 0) {
            Stump stump = Creator.createStump(getSTUMP_KEY() + "_" + super.getId(), super.getPosition(), imageStore.getImageList(getSTUMP_KEY()));

            world.removeEntity(scheduler, this);

            world.addEntity(stump);

            return true;
        } else if (super.getHealth() >= super.getHealthLimit()) {
            Tree tree = Creator.createTree(TREE_KEY + "_" + super.getId(),
                    super.getPosition(), getNumFromRange(TREE_ACTION_MAX, TREE_ACTION_MIN), getNumFromRange(TREE_ANIMATION_MAX, TREE_ANIMATION_MIN), getIntFromRange(TREE_HEALTH_MAX, TREE_HEALTH_MIN), imageStore.getImageList(TREE_KEY));

            world.removeEntity(scheduler, this);

            world.addEntity(tree);
            tree.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }

    public void executeSaplingActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        int health = super.getHealth() + 1;
        super.setHealth(health);
        if (!this.transformPlant(world, scheduler, imageStore)) {
            scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), super.getActionPeriod());
        }
    }
}
