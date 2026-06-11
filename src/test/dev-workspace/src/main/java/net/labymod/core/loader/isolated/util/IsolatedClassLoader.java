package net.labymod.core.loader.isolated.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.Manifest;
import net.labymod.api.loader.platform.PlatformChildClassloader;
import net.labymod.api.util.io.zip.Zips;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/isolated/util/IsolatedClassLoader.class */
public class IsolatedClassLoader extends URLClassLoader implements PlatformChildClassloader {
    private final String name;
    private final List<Path> files;

    public IsolatedClassLoader(String name) {
        super(new URL[0]);
        this.files = new ArrayList();
        this.name = name;
    }

    public IsolatedClassLoader(ClassLoader parent, String name) {
        super(new URL[0], parent);
        this.files = new ArrayList();
        this.name = name;
    }

    public void addPath(Path path) throws IOException {
        this.files.add(path);
        addURL(path.toUri().toURL());
    }

    public List<Path> getFiles() {
        return this.files;
    }

    public byte[] findJarResource(String name) {
        DataHolder dataHolder = new DataHolder();
        for (Path file : this.files) {
            if (dataHolder.getData() == null) {
                try {
                    Zips.read(file, (entry, data) -> {
                        if (entry.getName().startsWith("org/lwjgl/glfw")) {
                            System.out.println(entry.getName());
                        }
                        if (entry.getName().equals(name)) {
                            dataHolder.setData(data);
                            return true;
                        }
                        return false;
                    });
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        }
        return dataHolder.getData();
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/isolated/util/IsolatedClassLoader$DataHolder.class */
    private static class DataHolder {
        private byte[] data;

        private DataHolder() {
        }

        public byte[] getData() {
            return this.data;
        }

        public void setData(byte[] data) {
            this.data = data;
        }
    }

    @Override // net.labymod.api.loader.platform.PlatformChildClassloader
    public ClassLoader getChildClassloader() {
        return this;
    }

    public byte[] getClassData(String name) {
        URL resource = findResource(name.replace(".", "/").concat(".class"));
        if (resource == null) {
            return null;
        }
        try {
            InputStream stream = resource.openStream();
            try {
                byte[] allBytes = stream.readAllBytes();
                if (stream != null) {
                    stream.close();
                }
                return allBytes;
            } finally {
            }
        } catch (IOException e) {
            return null;
        }
    }

    @Override // net.labymod.api.loader.platform.PlatformChildClassloader
    public String getName() {
        return this.name;
    }

    public String toString() {
        return getName();
    }

    @Override // net.labymod.api.loader.platform.PlatformChildClassloader
    public Class<?> defineClassObject(String name, byte[] data, int offset, int length, CodeSource source) {
        return super.defineClass(name, data, offset, length, source);
    }

    @Override // net.labymod.api.loader.platform.PlatformChildClassloader
    public URL findResourceObject(String name) {
        return super.findResource(name);
    }

    @Override // net.labymod.api.loader.platform.PlatformChildClassloader
    public Enumeration<URL> findResourceObjects(String name) throws IOException {
        return findResources(name);
    }

    @Override // net.labymod.api.loader.platform.PlatformChildClassloader
    public Package findPackageObject(String name) {
        return super.getPackage(name);
    }

    @Override // net.labymod.api.loader.platform.PlatformChildClassloader
    public Package definePackageObject(String name, Manifest manifest, URL url) {
        return super.definePackage(name, manifest, url);
    }

    @Override // net.labymod.api.loader.platform.PlatformChildClassloader
    public Package definePackageObject(String name, String specTitle, String specVersion, String specVendor, String implTitle, String implVersion, String implVendor, URL url) {
        return super.definePackage(name, specTitle, specVersion, specVendor, implTitle, implVersion, implVendor, url);
    }
}
