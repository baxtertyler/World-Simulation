import processing.core.PImage;

import java.util.List;

public class Obstacle extends AnimationEntity {

    public Obstacle(String id, Point position, List<PImage> images, double animationPeriod) {
        super(id, position, images, animationPeriod);
    }
}
