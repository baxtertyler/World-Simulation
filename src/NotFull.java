import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class NotFull extends Dude{

    private ImageStore imageStore;
    public NotFull(String id, Point position, ImageStore imageStore, double animationPeriod, double actionPeriod,
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

    public boolean transformNotFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (super.getResourceCount() >= super.getResourceLimit()) {
            Full dude = Creator.createDudeFull(super.getId(), super.getPosition(), super.getActionPeriod(),
                    super.getAnimationPeriod(), super.getResourceLimit(), this.imageStore, super.getHasGem());

            world.removeEntity(scheduler, this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(dude);
            dude.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }

    public boolean moveToNotFull(WorldModel world, Entity target, EventScheduler scheduler) {
        if (super.getPosition().adjacent(target.getPosition())) {
            if (target instanceof Gem)
            {
                world.removeEntity(scheduler, target);
                scheduler.unscheduleAllEvents(this);
                this.setHasGem(true);
                this.setImages(imageStore.getImageList("dudeWithGem"));
            }
            else {
                int count = super.getResourceCount() + 1;
                super.setResourceCount(count);
                int health = ((Plant) target).getHealth() - 1;
                ((Plant) target).setHealth(health);
                /*target.health--;*/
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

    public void executeDudeNotFullActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
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
            target = world.findNearest(super.getPosition(), new ArrayList<>(Arrays.asList(Tree.class, Sapling.class)));
        }

        if (target.isEmpty() || !moveToNotFull(world, target.get(), scheduler) ||
                !transformNotFull(world, scheduler, imageStore)) {
            scheduler.scheduleEvent(this, createActivityAction(world, imageStore), super.getActionPeriod());
        }
    }

}
