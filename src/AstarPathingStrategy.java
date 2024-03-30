import java.util.*;

import java.util.function.Predicate;
import java.util.function.BiPredicate;

import java.util.function.Function;
import java.util.stream.Stream;

class AstarPathingStrategy implements PathingStrategy {

    private List<Point> path;
    private PriorityQueue<Node> openList;
    private HashMap<Point, Integer> closedList;
    private HashMap<Point, Node> openList_Hash;

    public List<Point> computePath(Point start, Point end, Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach, Function<Point,
                                   Stream<Point>> potentialNeighbors)
    {

        // give variables an initialization
        path = new LinkedList<Point>();
        openList = new PriorityQueue<>(Comparator.comparing(n -> n.getFCost()));
        openList_Hash = new HashMap<>();
        closedList = new HashMap<>();

        // create node variable to define current node being analyzed
        Node current = new Node(start, null, 0,
                Math.abs(end.x - start.x) + Math.abs(end.y - start.y));
        openList.add(current);
        openList_Hash.put(current.getPosition(), current);

        // AStarPathing algorithm
        while (current != null && !withinReach.test(current.getPosition(), end))
        {

            // get list of all neighbors
            List<Point> neighbors = potentialNeighbors.apply(current.getPosition()).filter(canPassThrough).toList();
            List<Point> validNeighbors = new ArrayList<>();

            // loop through potential neighbors and filter out positions in close list
            for (Point neighbor : neighbors)
            {
                if (!closedList.containsKey(neighbor))
                {
                    validNeighbors.add(neighbor);
                }
            }

            // loop through potential neighbors
            for (Point neighborPos : validNeighbors)
            {

                Node neighborNode = new Node(neighborPos, current, current.getGCost() + 1,
                        (Math.abs(end.x - neighborPos.x) + Math.abs(end.y - neighborPos.y)));

                // remove neighbor node and add new node with current as the prev in open list
                if (openList_Hash.containsKey(neighborPos))
                {
                    if (neighborNode.getGCost() < openList_Hash.get(neighborPos).getGCost())
                    {
                        openList.remove(neighborNode);
                        openList_Hash.remove(neighborPos);

                        Node newNode = new Node(neighborNode.getPosition(), current, neighborNode.getGCost(),
                                neighborNode.getHCost());

                        openList.add(newNode);
                        openList_Hash.put(neighborNode.getPosition(), newNode);
                    }
                }
                // if node not in open list add it to the open list
                else
                {
                    neighborNode.setPrev(current);

                    openList.add(neighborNode);
                    openList_Hash.put(neighborPos, neighborNode);
                }
            }

            closedList.put(current.getPosition(), 0);

            // end method so that following method calls can occur without error
            if (openList.size() == 0)
            {
                return path;
            }

            // initialize variables to get the node with the lowest f cost
            Node lowestFNode = openList.peek();
            int lowestFVal = openList.peek().getFCost();

            // get node with the lowest f cost
            for (Node node: openList)
            {
                if (!closedList.containsKey(node.getPosition()) && node.getFCost() < lowestFVal)
                {
                    lowestFNode = node;
                    lowestFVal = lowestFNode.getFCost();
                }
            }

            // remove node with the lowest f cost from the open list and hash version of the open list
            current = lowestFNode;
            openList.remove(current);
            openList_Hash.remove(current.getPosition());
        }

        Node prev = current;

        // add the rest of the nodes to the path
        while (prev != null)
        {
            path.add(0, prev.getPosition());
            prev = prev.getPrev();
        }

        path.remove(0);
        return path;
    }

}
