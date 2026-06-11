package net.labymod.api.util.io.locator;

import java.nio.file.Path;
import java.util.function.BiFunction;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/io/locator/PathResourceLocator.class */
public class PathResourceLocator extends ResourceLocator<Path> {
    public PathResourceLocator(ClassLoader loader, String resourcePath) {
        super(loader, resourcePath);
    }

    public PathResourceLocator(ClassLoader loader, String resourcePath, BiFunction<Path, Path, Path> resourceProcessor) {
        super(loader, resourcePath, resourceProcessor);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.util.io.locator.ResourceLocator
    public Path map(Path path) {
        return path;
    }
}
