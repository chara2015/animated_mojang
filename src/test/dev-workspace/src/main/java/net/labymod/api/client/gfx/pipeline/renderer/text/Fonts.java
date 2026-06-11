package net.labymod.api.client.gfx.pipeline.renderer.text;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.labymod.api.Laby;
import net.labymod.api.Namespaces;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.reflection.Reflection;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/renderer/text/Fonts.class */
public final class Fonts {
    private static final Map<ResourceLocation, Font> FONTS = new HashMap();
    private static final Set<ResourceLocation> RESERVED_FONTS = new HashSet();
    public static final Font MINECRAFT = reserve(Namespaces.MINECRAFT, "default_font");
    public static final Font LEGACY_MSDF = reserve("legacy_msdf");

    private Fonts() {
    }

    public static Font create(String id) {
        ResourceLocation keyId = createId(Laby.labyAPI().getNamespace(Reflection.getCallerClass()), id);
        if (RESERVED_FONTS.contains(keyId)) {
            throw new IllegalStateException(id + " is a reserved font id.");
        }
        return FONTS.computeIfAbsent(keyId, Font::new);
    }

    private static Font reserve(String id) {
        return reserve(Laby.labyAPI().getNamespace(Reflection.getCallerClass()), id);
    }

    private static Font reserve(String namespace, String id) {
        ResourceLocation keyId = createId(namespace, id);
        return FONTS.computeIfAbsent(keyId, key -> {
            RESERVED_FONTS.add(key);
            return new Font(key);
        });
    }

    private static ResourceLocation createId(String namespace, String id) {
        return ResourceLocation.create(namespace, "font/" + id);
    }
}
