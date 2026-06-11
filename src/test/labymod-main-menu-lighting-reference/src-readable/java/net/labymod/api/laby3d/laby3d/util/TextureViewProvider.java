package net.labymod.api.laby3d.util;

import net.labymod.api.util.CastUtil;
import net.labymod.laby3d.api.textures.DeviceTextureView;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/util/TextureViewProvider.class */
public interface TextureViewProvider {
    DeviceTextureView getDeviceTextureView();

    static TextureViewProvider cast(Object object) {
        return (TextureViewProvider) CastUtil.requireInstanceOf(object, TextureViewProvider.class);
    }
}
