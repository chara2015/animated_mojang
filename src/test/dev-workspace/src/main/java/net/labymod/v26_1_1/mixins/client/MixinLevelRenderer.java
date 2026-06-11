package net.labymod.v26_1_1.mixins.client;

import net.labymod.api.client.world.WorldRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/mixins/client/MixinLevelRenderer.class */
@Mixin({LevelRenderer.class})
public class MixinLevelRenderer implements WorldRenderer {

    @Shadow
    @Final
    private LevelRenderState levelRenderState;

    @Override // net.labymod.api.client.world.WorldRenderer
    public int getRenderedEntities() {
        return this.levelRenderState.entityRenderStates.size();
    }
}
