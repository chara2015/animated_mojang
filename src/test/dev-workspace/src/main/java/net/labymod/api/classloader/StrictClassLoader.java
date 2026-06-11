package net.labymod.api.classloader;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/classloader/StrictClassLoader.class */
public interface StrictClassLoader {
    Class<?> loadClassStrict(String str) throws ClassNotFoundException;
}
