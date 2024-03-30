import java.util.*;

import java.util.function.Predicate;
import java.util.function.BiPredicate;

import java.util.function.Function;
import java.util.stream.Stream;

public class NoMovementPathingStrategy implements PathingStrategy {

    public List<Point> computePath(Point start, Point end, Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach, Function<Point,
            Stream<Point>> potentialNeighbors)
    {
        List<Point> lst = new ArrayList<>();
        lst.add(start);
        return lst;
    }
}
