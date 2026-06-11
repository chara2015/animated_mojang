package net.labymod.api.laby3d.foreign.texture.opengl;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.labymod.api.laby3d.foreign.texture.ForeignDeviceTexture;
import net.labymod.api.laby3d.foreign.texture.ForeignDeviceTextureRegistry;
import net.labymod.api.util.CastUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/foreign/texture/opengl/GlForeignDeviceTextureRegistry.class */
public class GlForeignDeviceTextureRegistry extends ForeignDeviceTextureRegistry {
    private final Int2ObjectMap<ForeignDeviceTexture> textures = new Int2ObjectOpenHashMap();

    @Override // net.labymod.api.laby3d.foreign.texture.ForeignDeviceTextureRegistry
    public void register(ForeignDeviceTexture target) {
        GlForeignDeviceTexture glForeignDeviceTexture = (GlForeignDeviceTexture) CastUtil.requireInstanceOf(target, GlForeignDeviceTexture.class);
        this.textures.put(glForeignDeviceTexture.getId(), target);
    }

    @Override // net.labymod.api.laby3d.foreign.texture.ForeignDeviceTextureRegistry
    public void unregister(ForeignDeviceTexture target) {
        GlForeignDeviceTexture glForeignDeviceTexture = (GlForeignDeviceTexture) CastUtil.requireInstanceOf(target, GlForeignDeviceTexture.class);
        ForeignDeviceTexture texture = (ForeignDeviceTexture) this.textures.remove(glForeignDeviceTexture.getId());
        if (texture != null) {
            texture.close();
        }
    }

    @Override // net.labymod.api.laby3d.foreign.texture.ForeignDeviceTextureRegistry
    public ForeignDeviceTexture get(Object key) {
        Integer textureId = (Integer) CastUtil.requireInstanceOf(key, Integer.class);
        return (ForeignDeviceTexture) this.textures.get(textureId.intValue());
    }
}
