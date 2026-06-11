package net.labymod.core.client.options;

import java.util.function.Function;
import net.labymod.api.client.options.MinecraftInputMapping;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/options/InputMappingResolver.class */
public class InputMappingResolver {
    private static Function<String, MinecraftInputMapping> resolver = keybind -> {
        return null;
    };

    public static void setResolver(Function<String, MinecraftInputMapping> resolver2) {
        resolver = resolver2;
    }

    public static MinecraftInputMapping resolve(String keybind) {
        if (resolver != null) {
            return resolver.apply(keybind);
        }
        return null;
    }
}
