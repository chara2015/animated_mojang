package net.labymod.api.event.client.render.model.entity;

import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.render.model.entity.HumanoidModel;
import net.labymod.api.event.Event;
import net.labymod.api.event.Phase;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/render/model/entity/HumanoidModelAnimateEvent.class */
public class HumanoidModelAnimateEvent implements Event {
    private final LivingEntity livingEntity;
    private final HumanoidModel model;
    private final Phase phase;

    public HumanoidModelAnimateEvent(@NotNull LivingEntity livingEntity, @NotNull HumanoidModel model, @NotNull Phase phase) {
        this.livingEntity = livingEntity;
        this.model = model;
        this.phase = phase;
    }

    @NotNull
    public LivingEntity livingEntity() {
        return this.livingEntity;
    }

    @NotNull
    public HumanoidModel model() {
        return this.model;
    }

    @NotNull
    public Phase phase() {
        return this.phase;
    }
}
