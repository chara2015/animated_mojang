package net.labymod.api.loader.platform;

import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.util.Enumeration;
import java.util.jar.Manifest;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/loader/platform/PlatformChildClassloader.class */
public interface PlatformChildClassloader {
    ClassLoader getChildClassloader();

    Class<?> defineClassObject(String str, byte[] bArr, int i, int i2, CodeSource codeSource);

    URL findResourceObject(String str);

    Enumeration<URL> findResourceObjects(String str) throws IOException;

    Package findPackageObject(String str);

    Package definePackageObject(String str, Manifest manifest, URL url);

    Package definePackageObject(String str, String str2, String str3, String str4, String str5, String str6, String str7, URL url);

    default String getName() {
        return getClass().getName();
    }
}
