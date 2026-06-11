package net.labymod.api.client.entity;

import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/entity/EntityVisualizer.class */
@Referenceable
public interface EntityVisualizer {
    void submitEntity(ScreenContext screenContext, Entity entity, float f, float f2, float f3, float f4, float f5, float f6, float f7, boolean z, boolean z2);

    default void submitEntity(ScreenContext context, Entity entity, float x, float y, float rotationX, float rotationY, float rotationZ, float scale) {
        submitEntity(context, entity, x, y, rotationX, rotationY, rotationZ, scale, -1.0f, false, true);
    }

    default <E extends LivingEntity> void submitEntity(ScreenContext context, E entity, float x, float y, float rotationX, float rotationY, float rotationZ, float scale, float headRotationStrength) {
        submitEntity(context, entity, x, y, rotationX, rotationY, rotationZ, scale, headRotationStrength, false, true);
    }

    default EntityRenderBuilder entity(Entity entity) {
        return new EntityRenderBuilder(this, entity);
    }
}
