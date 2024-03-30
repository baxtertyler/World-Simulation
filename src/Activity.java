public class Activity extends Action{

    public Activity(Entity entity, WorldModel world, ImageStore imageStore, int repeatCount) {
        super(entity, world, imageStore, repeatCount);
    }

    @Override
    public void executeAction(EventScheduler scheduler) {
        executeActivityAction(scheduler);
    }

    public void executeActivityAction(EventScheduler scheduler) {

        if (super.getEntity().getClass() == (Sapling.class)){
            ((Sapling)getEntity()).executeSaplingActivity(super.getWorld(), super.getImageStore(), scheduler);
        }
        if (super.getEntity().getClass() == (Tree.class)){
            ((Tree) getEntity()).executeTreeActivity(super.getWorld(), super.getImageStore(), scheduler);
        }
        if (super.getEntity().getClass() == (Fairy.class)){
            ((Fairy) getEntity()).executeFairyActivity(super.getWorld(), super.getImageStore(), scheduler);
        }
        if (super.getEntity().getClass() == (NotFull.class)) {
            ((NotFull) getEntity()).executeDudeNotFullActivity(super.getWorld(), super.getImageStore(), scheduler);
        }
        if (super.getEntity().getClass() == (Full.class)) {
            ((Full) getEntity()).executeDudeFullActivity(super.getWorld(), super.getImageStore(), scheduler);
        }
        if (super.getEntity().getClass() == (Hunter.class)) {
            ((Hunter) getEntity()).executeHunterActivity(super.getWorld(), super.getImageStore(), scheduler);
        }
    }
}
