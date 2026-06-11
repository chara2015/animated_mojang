package net.labymod.v1_21_8.mixins.mojang.vertex;

import com.mojang.blaze3d.vertex.VertexFormatElement;
import net.labymod.api.mixin.dynamic.DynamicMixin;
import net.labymod.api.thirdparty.optifine.OptiFine;
import net.labymod.core.thirdparty.optifine.vertex.OptiFineVertexFormatElement;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/mojang/vertex/MixinVertexFormatElement.class */
@DynamicMixin(OptiFine.NAMESPACE)
@Mixin({VertexFormatElement.class})
public abstract class MixinVertexFormatElement implements OptiFineVertexFormatElement {
    @Shadow
    @Dynamic
    public abstract int getAttributeIndex();

    @Override // net.labymod.core.thirdparty.optifine.vertex.OptiFineVertexFormatElement
    public int bridge$optifine$getAttributeIndex() {
        return getAttributeIndex();
    }
}
