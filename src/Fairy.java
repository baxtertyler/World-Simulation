import processing.core.PImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class Fairy extends ActionEntity {

    private PathingStrategy pathingStrategy;

    private ImageStore imageStore;

    private boolean tased = false;

    private long timeTased = -1;

    public Fairy(String id, Point position, ImageStore imageStore, double animationPeriod, double actionPeriod) {
        super(id, position, imageStore.getImageList("fairy"), animationPeriod, actionPeriod);
        this.pathingStrategy = new AstarPathingStrategy();
        this.imageStore = imageStore;
    }

    public void setTased(boolean t)
    {
        this.tased = t;
    }

    public void setTimeTased(long time)
    {
        this.timeTased = time;
    }

    public boolean getTased()
    {
        return this.tased;
    }

    public boolean moveToFairy(WorldModel world, Entity target, EventScheduler scheduler) {
        if (super.getPosition().adjacent(target.getPosition())) {
            world.removeEntity(scheduler, target);
            return true;
        } else {
            Point nextPos = nextPositionFairy(world, target.getPosition());

            if (!super.getPosition().equals(nextPos)) {
                world.moveEntity(scheduler, this, nextPos);
            }
            return false;
        }
    }

    /*
    public Point nextPositionFairy(WorldModel world, Point destPos) {
        int horiz = Integer.signum(destPos.x - super.getPosition().x);
        Point newPos = new Point(super.getPosition().x + horiz, super.getPosition().y);

        if (horiz == 0 || world.isOccupied(newPos)) {
            int vert = Integer.signum(destPos.y - super.getPosition().y);
            newPos = new Point(super.getPosition().x, super.getPosition().y + vert);

            if (vert == 0 || world.isOccupied(newPos)) {
                newPos = super.getPosition();
            }
        }

        return newPos;
    }
    */

    public Point nextPositionFairy(WorldModel world, Point destPos) {
        if (this.tased && this.timeTased + 5000 > System.currentTimeMillis())
        {
            this.pathingStrategy = new NoMovementPathingStrategy();
        }
        else
        {
            this.tased = false;
            this.timeTased = -1;
            super.setImages(this.imageStore.getImageList("fairy"));
            this.pathingStrategy = new AstarPathingStrategy();
        }
        Predicate<Point> canPassThrough = p -> (world.withinBounds(p) && !world.isOccupied(p));
        BiPredicate<Point, Point> withinReach = (p1, p2) -> (p1.adjacent(p2));
        List<Point> path = pathingStrategy.computePath(this.getPosition(), destPos, canPassThrough, withinReach,
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

    public void executeFairyActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> fairyTarget = world.findNearest(super.getPosition(),
                new ArrayList<>(List.of(Stump.class)));

        if (fairyTarget.isPresent()) {
            Point tgtPos = fairyTarget.get().getPosition();

            if (this.moveToFairy(world, fairyTarget.get(), scheduler)) {
                Sapling sapling = Creator.createSapling("sapling" + "_" + fairyTarget.get().getId(),
                        tgtPos, imageStore.getImageList("sapling"), 0);

                world.addEntity(sapling);
                sapling.scheduleActions(scheduler, world, imageStore);
            }
        }

        scheduler.scheduleEvent(this, super.createActivityAction(world, imageStore), super.getActionPeriod());
    }

}
