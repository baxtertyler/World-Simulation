import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Full extends Dude{

    private ImageStore imageStore;
    public Full(String id, Point position, ImageStore imageStore, double animationPeriod, double actionPeriod,
                int resourceLimit, int resourceCount, boolean hasGem) {
        super(id, position, imageStore, animationPeriod, actionPeriod, resourceLimit, resourceCount, hasGem);
        this.imageStore = imageStore;
        if (this.getHasGem())
        {
            super.setImages(imageStore.getImageList("dudeWithGem"));
        }
        else
        {
            super.setImages(imageStore.getImageList("dude"));
        }
    }


    public void transformFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        NotFull dude = Creator.createDudeNotFull(super.getId(), super.getPosition(), super.getActionPeriod(),
                super.getAnimationPeriod(), super.getResourceLimit(), this.imageStore, super.getHasGem());

        world.removeEntity(scheduler, this);

        world.addEntity(dude);
        dude.scheduleActions(scheduler, world, imageStore);
    }

    public boolean moveToFull(WorldModel world, Entity target, EventScheduler scheduler) {
        if (super.getPosition().adjacent(target.getPosition())) {
            if (target instanceof Gem)
            {
                world.removeEntity(scheduler, target);
                scheduler.unscheduleAllEvents(this);
                this.setHasGem(true);
                this.setImages(imageStore.getImageList("dudeWithGem"));
            }
            return true;
        } else {
            Point nextPos = nextPositionDude(world, target.getPosition());

            if (!super.getPosition().equals(nextPos)) {
                world.moveEntity(scheduler, this, nextPos);
            }
            return false;
        }
    }

    public void executeDudeFullActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        if (this.getHasGem())
        {
            super.setImages(imageStore.getImageList("dudeWithGem"));
        }
        else
        {
            super.setImages(imageStore.getImageList("dude"));
        }

        Optional<Entity> target;
        target = world.findNearest(super.getPosition(), new ArrayList<>(List.of(Gem.class)));
        if (this.getHasGem() ||
                world.findNearest(super.getPosition(), new ArrayList<>(List.of(Gem.class))).isEmpty())
        {
            target = world.findNearest(super.getPosition(), new ArrayList<>(List.of(House.class)));
        }


        if (target.isPresent() && this.moveToFull(world, target.get(), scheduler)) {
            this.transformFull(world, scheduler, imageStore);
        } else {
            scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), super.getActionPeriod());
        }
    }


}
