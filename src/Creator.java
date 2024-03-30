import processing.core.PImage;

import java.util.List;

public class Creator {
    private final static double SAPLING_ACTION_ANIMATION_PERIOD = 1.000;
    private final static int SAPLING_HEALTH_LIMIT = 5;

    public static House createHouse(String id, Point position, List<PImage> images) {
        return new House(id, position, images);
    }
    public static Obstacle createObstacle(String id, Point position, double animationPeriod, List<PImage> images) {
        return new Obstacle(id, position, images, animationPeriod);
    }
    public static Tree createTree(String id, Point position, double actionPeriod, double animationPeriod, int health, List<PImage> images) {
        return new Tree(id, position, images, animationPeriod, actionPeriod, health, 0);
    }
    public static Stump createStump(String id, Point position, List<PImage> images) {
        return new Stump(id, position, images);
    }
    public static Sapling createSapling(String id, Point position, List<PImage> images, int health) {
        return new Sapling(id, position, images, SAPLING_ACTION_ANIMATION_PERIOD, SAPLING_ACTION_ANIMATION_PERIOD, health, SAPLING_HEALTH_LIMIT);
    }
    public static Fairy createFairy(String id, Point position, double actionPeriod, double animationPeriod, ImageStore images) {
        return new Fairy(id, position, images, animationPeriod, actionPeriod);
    }
    public static NotFull createDudeNotFull(String id, Point position, double actionPeriod, double animationPeriod, int resourceLimit, ImageStore imageStore, boolean hasGem) {
        return new NotFull(id, position, imageStore, animationPeriod, actionPeriod, resourceLimit,0, hasGem);
    }

    public static Full createDudeFull(String id, Point position, double actionPeriod, double animationPeriod, int resourceLimit, ImageStore imageStore, boolean hasGem) {
        return new Full(id, position, imageStore, animationPeriod, actionPeriod, resourceLimit, 0, hasGem);
    }

    public static Hunter createHunter(String id, Point position, List<PImage> images, int imageIndex, int actionPeriod,
                                      int animationPeriod) {
        return new Hunter(id, position, images, animationPeriod, actionPeriod, 0);
    }

    public static Gem createGem(String id, Point position, double animationPeriod, List<PImage> images) {
        return new Gem(id, position, images, animationPeriod);
    }}
