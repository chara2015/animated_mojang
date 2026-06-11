package net.labymod.core.main.user.shop.cosmetic.animation;

import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.render.model.animation.AnimationController;
import net.labymod.api.client.render.model.animation.meta.AnimationTrigger;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.main.user.shop.AnimationContainer;
import net.labymod.core.main.user.shop.item.geometry.AnimationStorage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/animation/SimpleAnimationTriggerStrategy.class */
public class SimpleAnimationTriggerStrategy implements AnimationTriggerStrategy {
    @Override // net.labymod.core.main.user.shop.cosmetic.animation.AnimationTriggerStrategy
    public void updateTriggers(AnimationStorage storage, AnimationContainer animations, AnimationController controller, Entity entity, boolean firstPerson, float walkingSpeed, boolean crouching) {
        boolean moving = walkingSpeed > 0.1f;
        if (crouching && !storage.isLastSneaking()) {
            animations.handleAnimationTrigger(AnimationTrigger.START_SNEAKING, controller, entity);
        } else if (!crouching && storage.isLastSneaking()) {
            animations.handleAnimationTrigger(AnimationTrigger.STOP_SNEAKING, controller, entity);
        }
        storage.setLastSneaking(crouching);
        if (moving && !storage.isLastMoving()) {
            animations.handleAnimationTrigger(AnimationTrigger.START_MOVING, controller, entity);
        } else if (!moving && storage.isLastMoving()) {
            animations.handleAnimationTrigger(AnimationTrigger.STOP_MOVING, controller, entity);
        }
        storage.setLastMoving(moving);
        if (TimeUtil.getMillis() > storage.getLastTriggerMillis() + 500 || !controller.isPlaying()) {
            storage.setLastTriggerMillis(TimeUtil.getMillis());
            AnimationTrigger trigger = AnimationTrigger.getMovingOrIdle(moving, crouching);
            animations.handleAnimationTrigger(trigger, controller, entity);
        }
    }
}
