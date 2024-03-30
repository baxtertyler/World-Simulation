import processing.core.PImage;

import java.util.List;

public abstract class AnimationEntity extends Entity {

    private double animationPeriod;

    public AnimationEntity(String id, Point position, List<PImage> images, double animationPeriod) {
        super(id, position, images);
        this.animationPeriod = animationPeriod;
    }

    public Animation createAnimationAction(int repeatCount) {
        return new Animation(this, null, null, repeatCount);
    }

    public double getAnimationPeriod() {
        return this.animationPeriod;
    }


    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, createAnimationAction(0), getAnimationPeriod());
    }


}
