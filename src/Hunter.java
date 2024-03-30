import processing.core.PImage;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.concurrent.TimeUnit;
import java.lang.System;

public class Hunter extends ActionEntity {

    public Hunter(String id, Point position, List<PImage> images, int imageIndex, int actionPeriod,
                  int animationPeriod){
        super(id, position, images, animationPeriod, actionPeriod);
    }

    public boolean moveToHunter(WorldModel world, Entity target, EventScheduler scheduler) {
        if (this.getPosition().adjacent(target.getPosition()))
        {
            return true;
        }
        else
        {
            Point nextPos = this.nextPosition(world, target.getPosition());
            if (!this.getPosition().equals(nextPos)) {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                occupant.ifPresent(scheduler::unscheduleAllEvents);

                world.moveEntity(scheduler, this, nextPos);
            }
            return false;
        }
    }

    public Point nextPosition(WorldModel world, Point destPos)
    {
        Predicate<Point> canPassThrough = (p) -> (world.withinBounds(p) && !world.isOccupied(p));
        BiPredicate<Point, Point> withinReach = (p1, p2) -> (p1.adjacent(p2));
        PathingStrategy strategy = new AstarPathingStrategy();
        List<Point> path = strategy.computePath(this.getPosition(), destPos, canPassThrough, withinReach,
                PathingStrategy.CARDINAL_NEIGHBORS);

        if (path.size() == 0)
        {
            return this.getPosition();
        }
        else
        {
            return path.get(0);
        }
    }

    public void executeHunterActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {

        Optional<Entity> hunterTarget =
                world.findNearest(this.getPosition(),
                        new ArrayList<>(Arrays.asList(Fairy.class)));

        if (hunterTarget.isPresent())
        {
            if (this.moveToHunter(world, hunterTarget.get(), scheduler))
            {
                System.out.println("HIT!");
                Fairy f = (Fairy) hunterTarget.get();
                f.setTased(true);
                f.setTimeTased(System.currentTimeMillis());
                f.setImages(imageStore.getImageList("tased"));
            }
        }

        scheduler.scheduleEvent(this, new Activity(this, world, imageStore, 0),
                this.getActionPeriod());
    }
}