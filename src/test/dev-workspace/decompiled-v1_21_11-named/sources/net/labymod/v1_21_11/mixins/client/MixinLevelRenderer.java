package net.labymod.v1_21_11.mixins.client;

import net.labymod.api.client.world.WorldRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.state.LevelRenderState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/MixinLevelRenderer.class */
@Mixin({LevelRenderer.class})
public class MixinLevelRenderer implements WorldRenderer {

    @Shadow
    @Final
    private LevelRenderState Q;

    public int getRenderedEntities() {
        return this.Q.entityRenderStates.size();
    }
}
