package net.labymod.core.client.gui.lss.style.modifier.cache;

import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.gui.lss.style.modifier.Forwarder;
import net.labymod.api.client.gui.lss.style.modifier.PostProcessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/modifier/cache/WidgetModifierCache.class */
public interface WidgetModifierCache {
    void addForwarder(String str, Forwarder forwarder);

    Forwarder findForwarder(@NotNull String str, Forwarder forwarder);

    void addPostProcessor(CacheKey cacheKey, PostProcessor postProcessor);

    PostProcessor findPostProcessor(@NotNull CacheKey cacheKey, PostProcessor postProcessor);

    CacheKey createKey(String str, Element element, Class<?> cls);

    void clearForwarders();

    void clearPostProcessors();

    @Nullable
    default Forwarder findForwarder(String key) {
        return findForwarder(key, null);
    }

    @Nullable
    default PostProcessor findPostProcessor(@NotNull CacheKey key) {
        return findPostProcessor(key, null);
    }
}
