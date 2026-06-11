package net.labymod.v1_21_11.mixins.client.renderer.rendertype;

import net.labymod.v1_21_11.laby3d.pipeline.RenderTypeAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/renderer/rendertype/MixinRenderType.class */
@Mixin({ijs.class})
public class MixinRenderType implements RenderTypeAccessor {

    @Shadow
    @Final
    protected String d;

    @Override // net.labymod.v1_21_11.laby3d.pipeline.RenderTypeAccessor
    public String getName() {
        return this.d;
    }
}
