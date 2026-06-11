package net.labymod.api.util.io.locator;

import java.nio.file.Path;
import java.util.function.BiFunction;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/io/locator/StringResourceLocator.class */
public class StringResourceLocator extends ResourceLocator<String> {
    public StringResourceLocator(ClassLoader loader, String resourcePath) {
        super(loader, resourcePath);
    }

    public StringResourceLocator(ClassLoader loader, String resourcePath, BiFunction<Path, String, String> resourceProcessor) {
        super(loader, resourcePath, resourceProcessor);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.util.io.locator.ResourceLocator
    public String map(Path path) {
        return path.toString();
    }
}
