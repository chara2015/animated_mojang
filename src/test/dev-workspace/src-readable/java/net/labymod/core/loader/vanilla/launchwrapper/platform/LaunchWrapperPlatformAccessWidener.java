package net.labymod.core.loader.vanilla.launchwrapper.platform;

import net.labymod.api.loader.platform.PlatformAccessWidener;
import net.labymod.core.loader.vanilla.launchwrapper.transformer.accesswidener.AccessWidenerUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/vanilla/launchwrapper/platform/LaunchWrapperPlatformAccessWidener.class */
public class LaunchWrapperPlatformAccessWidener implements PlatformAccessWidener {
    @Override // net.labymod.api.loader.platform.PlatformAccessWidener
    public void findAndCreateAccessWidener(ClassLoader classLoader, String addonId, String runningVersion) {
        AccessWidenerUtil.findAndCreateAccessWidener(classLoader, addonId, runningVersion);
    }
}
