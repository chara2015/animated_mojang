package net.labymod.core.client.gui.lss.style.modifier.cache;

import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.gui.lss.style.modifier.Forwarder;
import net.labymod.api.client.gui.lss.style.modifier.PostProcessor;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/modifier/cache/NOPWidgetModifierCache.class */
public class NOPWidgetModifierCache implements WidgetModifierCache {
    @Override // net.labymod.core.client.gui.lss.style.modifier.cache.WidgetModifierCache
    public void addForwarder(String key, Forwarder forwarder) {
    }

    @Override // net.labymod.core.client.gui.lss.style.modifier.cache.WidgetModifierCache
    public Forwarder findForwarder(@NotNull String key, Forwarder def) {
        return null;
    }

    @Override // net.labymod.core.client.gui.lss.style.modifier.cache.WidgetModifierCache
    public void addPostProcessor(CacheKey key, PostProcessor processor) {
    }

    @Override // net.labymod.core.client.gui.lss.style.modifier.cache.WidgetModifierCache
    public PostProcessor findPostProcessor(@NotNull CacheKey key, PostProcessor def) {
        return null;
    }

    @Override // net.labymod.core.client.gui.lss.style.modifier.cache.WidgetModifierCache
    public CacheKey createKey(String key, Element element, Class<?> type) {
        return null;
    }

    @Override // net.labymod.core.client.gui.lss.style.modifier.cache.WidgetModifierCache
    public void clearForwarders() {
    }

    @Override // net.labymod.core.client.gui.lss.style.modifier.cache.WidgetModifierCache
    public void clearPostProcessors() {
    }
}
