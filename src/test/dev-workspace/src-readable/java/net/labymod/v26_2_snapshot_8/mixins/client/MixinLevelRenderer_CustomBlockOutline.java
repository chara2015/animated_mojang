package net.labymod.v26_2_snapshot_8.mixins.client;

import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderBuffers;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/client/MixinLevelRenderer_CustomBlockOutline.class */
@Mixin({LevelRenderer.class})
public class MixinLevelRenderer_CustomBlockOutline {

    @Shadow
    @Final
    private RenderBuffers renderBuffers;
}
