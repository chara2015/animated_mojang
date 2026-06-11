package net.labymod.v26_1_1.mixins.client.renderer.rendertype;

import net.labymod.v26_1_1.laby3d.pipeline.RenderTypeAccessor;
import net.minecraft.client.renderer.rendertype.RenderType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/mixins/client/renderer/rendertype/MixinRenderType.class */
@Mixin({RenderType.class})
public class MixinRenderType implements RenderTypeAccessor {

    @Shadow
    @Final
    protected String name;

    @Override // net.labymod.v26_1_1.laby3d.pipeline.RenderTypeAccessor
    public String getName() {
        return this.name;
    }
}
