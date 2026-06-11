package net.labymod.api.event.client.render.model.entity;

import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.event.DefaultCancellable;
import net.labymod.api.event.Event;
import net.labymod.api.event.Phase;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/render/model/entity/HumanoidModelPoseAnimationEvent.class */
public class HumanoidModelPoseAnimationEvent extends DefaultCancellable implements Event {
    private final Phase phase;
    private final LivingEntity livingEntity;
    private final LivingEntity.HandSide handSide;

    public HumanoidModelPoseAnimationEvent(@NotNull Phase phase, @NotNull LivingEntity livingEntity, @NotNull LivingEntity.HandSide handSide) {
        this.phase = phase;
        this.livingEntity = livingEntity;
        this.handSide = handSide;
    }

    public Phase phase() {
        return this.phase;
    }

    public LivingEntity livingEntity() {
        return this.livingEntity;
    }

    public LivingEntity.HandSide handSide() {
        return this.handSide;
    }
}
