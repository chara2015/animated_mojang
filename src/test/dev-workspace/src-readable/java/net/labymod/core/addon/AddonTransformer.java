package net.labymod.core.addon;

import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.addon.transform.LoadedAddonClassTransformer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/addon/AddonTransformer.class */
public class AddonTransformer {
    public static byte[] transform(String name, String transformedName, byte[] classData) {
        DefaultAddonService instance = DefaultAddonService.getInstance();
        if (instance == null) {
            return classData;
        }
        for (LoadedAddon addon : instance.getLoadedAddons()) {
            for (LoadedAddonClassTransformer transformer : addon.getTransformers()) {
                if (transformer.shouldTransform(name, transformedName)) {
                    classData = transformer.transform(name, transformedName, classData);
                }
            }
        }
        return classData;
    }
}
