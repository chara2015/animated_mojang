package net.labymod.core.loader.isolated.lwjgl;

import java.nio.file.Path;
import net.labymod.api.loader.platform.PlatformClassloader;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.core.loader.isolated.IsolatedLibrary;
import net.labymod.core.loader.isolated.IsolatedLibraryFinishHandler;
import net.labymod.core.loader.isolated.util.IsolatedClassLoaders;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/isolated/lwjgl/ThinLWJGL.class */
public class ThinLWJGL implements IsolatedLibraryFinishHandler {
    @Override // net.labymod.core.loader.isolated.IsolatedLibraryFinishHandler
    public void onAccept(IsolatedLibrary library) {
    }

    @Override // net.labymod.core.loader.isolated.IsolatedLibraryFinishHandler
    public void onFinish() {
        for (Path file : IsolatedClassLoaders.THIN_LWJGL_LOADER.getFiles()) {
            addThinLWJGL(file);
        }
    }

    private void addThinLWJGL(Path library) {
        PlatformClassloader classloader = PlatformEnvironment.getPlatformClassloader();
        classloader.addPath(library);
    }
}
