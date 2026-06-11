package net.labymod.api.util;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/IsolatedClassLoader.class */
public class IsolatedClassLoader extends URLClassLoader {
    public IsolatedClassLoader() {
        super(new URL[0]);
    }

    public IsolatedClassLoader(ClassLoader parent) {
        super(new URL[0], parent);
    }

    public void addPath(Path path) throws IOException {
        addURL(path.toUri().toURL());
    }

    @Override // java.net.URLClassLoader
    public void addURL(URL url) {
        super.addURL(url);
    }
}
