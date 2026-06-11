package net.labymod.core.client.gui.lss.style.modifier.cache;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.gui.lss.style.modifier.Forwarder;
import net.labymod.api.client.gui.lss.style.modifier.PostProcessor;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/modifier/cache/SimpleWidgetModifierCache.class */
public class SimpleWidgetModifierCache implements WidgetModifierCache {
    private final Object2ObjectMap<String, Forwarder> forwarderCache = new Object2ObjectOpenHashMap();
    private final Object2ObjectMap<CacheKey, PostProcessor> postProcessorCache = new Object2ObjectOpenHashMap();

    @Override // net.labymod.core.client.gui.lss.style.modifier.cache.WidgetModifierCache
    public void addForwarder(@NotNull String key, Forwarder forwarder) {
        this.forwarderCache.put(key, forwarder);
    }

    @Override // net.labymod.core.client.gui.lss.style.modifier.cache.WidgetModifierCache
    public Forwarder findForwarder(@NotNull String key, Forwarder def) {
        return (Forwarder) this.forwarderCache.getOrDefault(key, def);
    }

    @Override // net.labymod.core.client.gui.lss.style.modifier.cache.WidgetModifierCache
    public void addPostProcessor(CacheKey key, PostProcessor processor) {
        this.postProcessorCache.put(key, processor);
    }

    @Override // net.labymod.core.client.gui.lss.style.modifier.cache.WidgetModifierCache
    public PostProcessor findPostProcessor(@NotNull CacheKey key, PostProcessor def) {
        return (PostProcessor) this.postProcessorCache.getOrDefault(key, def);
    }

    @Override // net.labymod.core.client.gui.lss.style.modifier.cache.WidgetModifierCache
    public CacheKey createKey(String key, Element element, Class<?> type) {
        return new CacheKey(key, element, type);
    }

    @Override // net.labymod.core.client.gui.lss.style.modifier.cache.WidgetModifierCache
    public void clearForwarders() {
        this.forwarderCache.clear();
    }

    @Override // net.labymod.core.client.gui.lss.style.modifier.cache.WidgetModifierCache
    public void clearPostProcessors() {
        this.postProcessorCache.clear();
    }
}
