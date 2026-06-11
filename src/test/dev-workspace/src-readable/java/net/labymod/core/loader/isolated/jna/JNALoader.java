package net.labymod.core.loader.isolated.jna;

import java.nio.file.Path;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.core.loader.isolated.IsolatedLibrary;
import net.labymod.core.loader.isolated.IsolatedLibraryFinishHandler;
import net.labymod.core.loader.isolated.util.IsolatedClassLoaders;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/isolated/jna/JNALoader.class */
public class JNALoader implements IsolatedLibraryFinishHandler {
    @Override // net.labymod.core.loader.isolated.IsolatedLibraryFinishHandler
    public void onAccept(IsolatedLibrary library) {
    }

    @Override // net.labymod.core.loader.isolated.IsolatedLibraryFinishHandler
    public void onFinish() {
        for (Path file : IsolatedClassLoaders.JNA_CLASS_LOADER.getFiles()) {
            PlatformEnvironment.getPlatformClassloader().addPath(file);
        }
    }
}
