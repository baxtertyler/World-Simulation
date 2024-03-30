import processing.core.PImage;

import java.util.List;
import java.util.Random;

public class Entity {
    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;

    public Entity(String id, Point position, List<PImage> images) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
    }


    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public List<PImage> getImages() {
        return images;
    }

    public void setImages(List<PImage> i) {
        this.images = i;
    }

    public void tryAddEntity(WorldModel world){
        if (world.isOccupied(this.position)){
            throw new IllegalArgumentException("position occupied");
        }
        world.addEntity(this);
    }

    public String log(){
        return this.id.isEmpty() ? null :
                String.format("%s %d %d %d", this.id, this.position.x, this.position.y, this.imageIndex);
    }

    public void nextImage() {
        this.imageIndex = this.imageIndex + 1;
    }

    public PImage getCurrentImage(){
        return this.images.get(this.imageIndex % this.images.size());
    }

    public double getNumFromRange(double max, double min) {
        Random rand = new Random();
        return min + rand.nextDouble() * (max - min);
    }

    public int getIntFromRange(int max, int min){
        Random rand = new Random();
        return min + rand.nextInt(max-min);
    }

}
