package net.labymod.v26_2_snapshot_8.client.renderer;

import net.labymod.api.util.CastUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeStorage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/client/renderer/LevelRendererAccessor.class */
public interface LevelRendererAccessor {
    SubmitNodeStorage labyMod$submitNodeStorage();

    static LevelRendererAccessor self() {
        return self(Minecraft.getInstance().levelRenderer);
    }

    static LevelRendererAccessor self(Object obj) {
        return (LevelRendererAccessor) CastUtil.requireInstanceOf(obj, LevelRendererAccessor.class);
    }
}
