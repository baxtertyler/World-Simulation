public class Node {
    private Point position;
    private Node prevNode;
    private int fCost;
    private int gCost;
    private int hCost;

    public Node(Point position, Node prev, int gCost, int hCost)
    {
        this.position = position;
        this.prevNode = prev;
        this.gCost = gCost;
        this.hCost = hCost;
        this.fCost = gCost + hCost;
    }

    public int getFCost()
    {
        return fCost;
    }

    public int getGCost()
    {
        return gCost;
    }

    public int getHCost()
    {
        return hCost;
    }

    public boolean equals(Object other)
    {
        return other instanceof Node &&
                ((Node)other).position.x == this.position.x &&
                ((Node)other).position.y == this.position.y;
    }

    public int hashCode()
    {
        int result = 17;
        result = result * 31 + position.x;
        result = result * 31 + position.y;
        return result;
    }

    public Point getPosition()
    {
        return position;
    }

    public Node getPrev()
    {
        return prevNode;
    }

    public void setPrev(Node prevNode)
    {
        this.prevNode = prevNode;
    }


}
