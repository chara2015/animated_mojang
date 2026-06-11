package net.minecraft.client.renderer.special;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/special/NoDataSpecialModelRenderer.class */
public interface NoDataSpecialModelRenderer extends SpecialModelRenderer<Void> {
    void submit(ItemDisplayContext itemDisplayContext, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int i, int i2, boolean z, int i3);

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.minecraft.client.renderer.special.SpecialModelRenderer
    default Void extractArgument(ItemStack $$0) {
        return null;
    }

    @Override // net.minecraft.client.renderer.special.SpecialModelRenderer
    default void submit(Void $$0, ItemDisplayContext $$1, PoseStack $$2, SubmitNodeCollector $$3, int $$4, int $$5, boolean $$6, int $$7) {
        submit($$1, $$2, $$3, $$4, $$5, $$6, $$7);
    }
}
