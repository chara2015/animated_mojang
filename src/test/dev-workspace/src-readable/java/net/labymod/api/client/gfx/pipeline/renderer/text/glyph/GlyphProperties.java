package net.labymod.api.client.gfx.pipeline.renderer.text.glyph;

import it.unimi.dsi.fastutil.objects.Reference2ObjectMap;
import it.unimi.dsi.fastutil.objects.Reference2ObjectMaps;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.labymod.api.client.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/renderer/text/glyph/GlyphProperties.class */
public final class GlyphProperties {
    private static final GlyphProperties EMPTY = new GlyphProperties(Reference2ObjectMaps.emptyMap());
    private final Reference2ObjectMap<ResourceLocation, Object> properties;

    public GlyphProperties() {
        this(new Reference2ObjectOpenHashMap());
    }

    public GlyphProperties(Reference2ObjectMap<ResourceLocation, Object> properties) {
        this.properties = properties;
    }

    public static GlyphProperties empty() {
        return EMPTY;
    }

    public static GlyphProperties copyOf(GlyphProperties properties) {
        Reference2ObjectOpenHashMap<ResourceLocation, Object> copiedProperties = new Reference2ObjectOpenHashMap<>(properties.properties);
        return new GlyphProperties(Reference2ObjectMaps.unmodifiable(copiedProperties));
    }

    public void setProperty(ResourceLocation key, Object value) {
        this.properties.put(key, value);
    }

    @Nullable
    public Object getProperty(ResourceLocation key) {
        return this.properties.get(key);
    }

    public <K, V> V getOrDefault(ResourceLocation resourceLocation, K k) {
        return (V) this.properties.getOrDefault(resourceLocation, k);
    }
}
