package net.labymod.v1_16_5.mixins.mojang.blaze3d.vertex;

import com.google.common.collect.ImmutableMap;
import net.labymod.v1_16_5.client.render.vertex.VertexFormatAccessor;
import org.spongepowered.asm.mixin.Mixin;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/mojang/blaze3d/vertex/MixinVertexFormat.class */
@Mixin({dfr.class})
public class MixinVertexFormat implements VertexFormatAccessor {
    @Override // net.labymod.v1_16_5.client.render.vertex.VertexFormatAccessor
    public ImmutableMap<String, dfs> getElements() {
        return ImmutableMap.of();
    }
}
