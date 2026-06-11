package net.labymod.api.volt.classloader;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/volt/classloader/DefiningClassLoader.class */
public class DefiningClassLoader extends ClassLoader {
    public DefiningClassLoader(ClassLoader parent) {
        super(parent);
    }

    public <T> Class<T> defineClass(String str, byte[] bArr) {
        return (Class<T>) defineClass(str, bArr, 0, bArr.length);
    }
}
