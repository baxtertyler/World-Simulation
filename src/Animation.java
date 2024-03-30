public class Animation extends Action{

    public Animation(Entity entity, WorldModel world, ImageStore imageStore, int repeatCount) {
        super(entity, world, imageStore, repeatCount);
    }

    @Override
    public void executeAction(EventScheduler scheduler) {
        executeAnimationAction(scheduler);
    }

    public void executeAnimationAction(EventScheduler scheduler) {
        getEntity().nextImage();

        if (super.getRepeatCount() != 1) {
            scheduler.scheduleEvent(super.getEntity(), ((AnimationEntity) super.getEntity()).createAnimationAction(Math.max(super.getRepeatCount() - 1, 0)), ((AnimationEntity) getEntity()).getAnimationPeriod());
        }
    }
}
