package net.labymod.core.main.user.shop.cosmetic.animation;

import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.render.model.animation.AnimationController;
import net.labymod.core.main.user.shop.AnimationContainer;
import net.labymod.core.main.user.shop.item.geometry.AnimationStorage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/animation/AnimationTriggerStrategy.class */
public interface AnimationTriggerStrategy {
    void updateTriggers(AnimationStorage animationStorage, AnimationContainer animationContainer, AnimationController animationController, Entity entity, boolean z, float f, boolean z2);
}
