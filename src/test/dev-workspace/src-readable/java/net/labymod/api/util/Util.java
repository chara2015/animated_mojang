package net.labymod.api.util;

import net.labymod.api.client.resources.IllegalResourceLocationException;
import net.labymod.api.client.resources.ResourceLocation;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/Util.class */
public final class Util {
    public static ResourceLocation rewriteShaderId(String input, String targetId) {
        try {
            return ResourceLocation.parse(input);
        } catch (IllegalResourceLocationException e) {
            ResourceLocation contained = ResourceLocation.parse(targetId);
            return contained.transformPath(path -> {
                return input.replace(targetId, path);
            });
        }
    }
}
