package net.labymod.core.client.gui.lss.style.modifier.cache;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Objects;
import net.labymod.api.client.gui.lss.style.function.Element;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/modifier/cache/CacheKey.class */
public final class CacheKey extends Record {
    private final String key;
    private final Element element;
    private final Class<?> type;

    public CacheKey(String key, Element element, Class<?> type) {
        this.key = key;
        this.element = element;
        this.type = type;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, CacheKey.class), CacheKey.class, "key;element;type", "FIELD:Lnet/labymod/core/client/gui/lss/style/modifier/cache/CacheKey;->key:Ljava/lang/String;", "FIELD:Lnet/labymod/core/client/gui/lss/style/modifier/cache/CacheKey;->element:Lnet/labymod/api/client/gui/lss/style/function/Element;", "FIELD:Lnet/labymod/core/client/gui/lss/style/modifier/cache/CacheKey;->type:Ljava/lang/Class;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    public String key() {
        return this.key;
    }

    public Element element() {
        return this.element;
    }

    public Class<?> type() {
        return this.type;
    }

    @Override // java.lang.Record
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        CacheKey cacheKey = (CacheKey) object;
        if (!Objects.equals(this.key, cacheKey.key) || !Objects.equals(this.element, cacheKey.element)) {
            return false;
        }
        return Objects.equals(this.type, cacheKey.type);
    }

    @Override // java.lang.Record
    public int hashCode() {
        int result = this.key != null ? this.key.hashCode() : 0;
        return (31 * ((31 * result) + (this.element != null ? this.element.hashCode() : 0))) + (this.type != null ? this.type.hashCode() : 0);
    }
}
