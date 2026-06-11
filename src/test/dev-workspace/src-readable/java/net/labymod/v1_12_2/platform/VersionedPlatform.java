package net.labymod.v1_12_2.platform;

import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;
import net.labymod.api.service.annotation.AutoService;
import net.labymod.core.client.render.batch.DefaultRenderContexts;
import net.labymod.core.platform.Platform;
import net.labymod.v1_12_2.client.render.matrix.VersionedStackProvider;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/platform/VersionedPlatform.class */
@Singleton
@AutoService(value = Platform.class, versionSpecific = true)
public class VersionedPlatform extends Platform {
    @Override // net.labymod.core.platform.Platform
    public void onInitialization() {
        setMinecraft((Minecraft) bib.z());
        setPlatformScreenHandler(new VersionedPlatformScreenHandler());
    }

    @Override // net.labymod.core.platform.Platform
    public void onPostStartup() {
        ((DefaultRenderContexts) Laby.references().renderContexts()).setCurrentStack(VersionedStackProvider.DEFAULT_STACK);
    }
}
