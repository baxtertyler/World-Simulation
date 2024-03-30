import java.util.List;
import processing.core.PImage;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public abstract class Dude extends ActionEntity {
    private int resourceLimit;
    private int resourceCount;
    private final PathingStrategy pathingStrategy;

    private boolean hasGem;

    private ImageStore imageStore;

    public Dude(String id, Point position, ImageStore imageStore, double animationPeriod, double actionPeriod,
                int resourceLimit, int resourceCount, boolean hasGem) {
        super(id, position, imageStore.getImageList("dude"), animationPeriod, actionPeriod);
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
        this.pathingStrategy = new AstarPathingStrategy();
        this.hasGem = hasGem;
        this.imageStore = imageStore;
        if (this.hasGem)
        {
            super.setImages(imageStore.getImageList("dudeWithGem"));
        }
        else
        {
            super.setImages(imageStore.getImageList("dude"));
        }
    }

    public int getResourceCount() {
        return resourceCount;
    }

    public void setResourceCount(int resourceCount) {
        this.resourceCount = resourceCount;
    }

    public int getResourceLimit() {
        return resourceLimit;
    }

    public boolean getHasGem()
    {
        return this.hasGem;
    }

    public void setHasGem(boolean b)
    {
        this.hasGem = b;
    }

    public Point nextPositionDude(WorldModel world, Point destPos) {
        if (this.getHasGem()) {
            super.setImages(imageStore.getImageList("dudeWithGem"));
        }

        Predicate<Point> canPassThrough = p -> (world.withinBounds(p) &&
                (!world.isOccupied(p) || (world.getOccupancyCell(p) instanceof Stump)));
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
}
