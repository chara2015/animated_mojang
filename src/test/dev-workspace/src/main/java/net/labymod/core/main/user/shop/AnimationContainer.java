package net.labymod.core.main.user.shop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.render.model.animation.AnimationController;
import net.labymod.api.client.render.model.animation.ModelAnimation;
import net.labymod.api.client.render.model.animation.meta.AnimationMeta;
import net.labymod.api.client.render.model.animation.meta.AnimationTrigger;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/AnimationContainer.class */
public class AnimationContainer {
    private static final Random RANDOM = new Random();
    private final Map<AnimationTrigger, List<ModelAnimation>> animationsByTrigger;
    private Collection<ModelAnimation> animations;
    private AnimationTrigger trigger;

    public AnimationContainer() {
        this(null);
    }

    public AnimationContainer(Collection<ModelAnimation> animations) {
        this.trigger = AnimationTrigger.NONE;
        this.animationsByTrigger = new HashMap();
        updateAnimations(animations);
    }

    public void updateAnimations(Collection<ModelAnimation> animations) {
        this.animations = animations;
        this.animationsByTrigger.clear();
        if (animations == null) {
            return;
        }
        for (ModelAnimation animation : animations) {
            List<AnimationTrigger> triggers = animation.getTriggers();
            if (triggers.isEmpty()) {
                this.animationsByTrigger.computeIfAbsent(AnimationTrigger.NONE, key -> {
                    return new ArrayList();
                }).add(animation);
            } else {
                for (AnimationTrigger trigger : triggers) {
                    this.animationsByTrigger.computeIfAbsent(trigger, key2 -> {
                        return new ArrayList();
                    }).add(animation);
                }
            }
        }
    }

    public boolean hasTriggerAnimations() {
        List<ModelAnimation> noTriggerAnimations = this.animationsByTrigger.get(AnimationTrigger.NONE);
        return (noTriggerAnimations == null ? 0 : noTriggerAnimations.size()) != (this.animations == null ? 0 : this.animations.size());
    }

    public List<ModelAnimation> getTriggerAnimations(AnimationTrigger animationTrigger) {
        return this.animationsByTrigger.getOrDefault(animationTrigger, Collections.emptyList());
    }

    public boolean isTriggerPresent(AnimationTrigger trigger) {
        return !getTriggerAnimations(trigger).isEmpty();
    }

    public ModelAnimation getByTrigger(AnimationTrigger animationTrigger) {
        return getByTrigger(animationTrigger, null);
    }

    public ModelAnimation getByTrigger(AnimationTrigger animationTrigger, Entity entity) {
        List<ModelAnimation> triggerAnimations = this.animationsByTrigger.get(animationTrigger);
        if (triggerAnimations == null || triggerAnimations.isEmpty()) {
            return null;
        }
        List<ModelAnimation> matchingAnimations = new ArrayList<>();
        for (ModelAnimation animation : triggerAnimations) {
            if (animation.meetsConditions(entity)) {
                Integer probability = (Integer) animation.getMeta(AnimationMeta.PROBABILITY);
                if (probability == null) {
                    return animation;
                }
                for (int i = 0; i < probability.intValue(); i++) {
                    matchingAnimations.add(animation);
                }
            }
        }
        if (matchingAnimations.isEmpty()) {
            return null;
        }
        return matchingAnimations.get(RANDOM.nextInt(matchingAnimations.size()));
    }

    public ModelAnimation handleAnimationTrigger(AnimationTrigger trigger, AnimationController animationController, Entity entity) {
        Float currentSpeed;
        if (trigger.isFirstPerson() && !isTriggerPresent(trigger)) {
            trigger = AnimationTrigger.getAlternateTrigger(trigger);
        }
        ModelAnimation animation = getByTrigger(trigger, entity);
        boolean triggerChanged = !Objects.equals(this.trigger, trigger);
        this.trigger = trigger;
        if (animation == null) {
            return null;
        }
        boolean force = ((Boolean) animation.getMetaDefault(AnimationMeta.FORCE, false)).booleanValue();
        boolean queue = ((Boolean) animation.getMetaDefault(AnimationMeta.QUEUE, false)).booleanValue();
        ModelAnimation currentAnimation = animationController.getPlaying();
        boolean sameAnimation = currentAnimation == animation || animationController.isQueued(animation) || (currentAnimation != null && currentAnimation.hasTrigger(trigger));
        if (force || !animationController.isPlaying() || (!sameAnimation && !queue)) {
            if (triggerChanged) {
                animationController.clearQueue();
            }
            animationController.playNext(animation);
            return animation;
        }
        if (sameAnimation) {
            return animation;
        }
        animationController.queue(animation);
        if (currentAnimation != null && (currentSpeed = (Float) currentAnimation.getMeta(AnimationMeta.SPEED)) != null && animationController.getSpeed() == 1.0f) {
            animationController.speed(currentSpeed.floatValue());
        }
        return animation;
    }

    public ModelAnimation getByName(String name) {
        for (ModelAnimation animation : this.animations) {
            if (animation.getName().equalsIgnoreCase(name)) {
                return animation;
            }
        }
        return null;
    }

    public Collection<ModelAnimation> getAnimations() {
        return this.animations;
    }

    public AnimationTrigger getTrigger() {
        return this.trigger;
    }
}
