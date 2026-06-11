package net.minecraft.world.entity.ai.control;

import net.minecraft.util.Mth;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/ai/control/Control.class */
public interface Control {
    default float rotateTowards(float $$0, float $$1, float $$2) {
        float $$3 = Mth.degreesDifference($$0, $$1);
        float $$4 = Mth.clamp($$3, -$$2, $$2);
        return $$0 + $$4;
    }
}
