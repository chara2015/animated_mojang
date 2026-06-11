package net.labymod.v1_21_5.client.util;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/client/util/EntityRenderStateAccessor.class */
public interface EntityRenderStateAccessor<T> {
    T labyMod$getEntity();

    void labyMod$setEntity(T t);

    static <E> EntityRenderStateAccessor<E> self(Object obj) {
        if (obj instanceof EntityRenderStateAccessor) {
            return (EntityRenderStateAccessor) obj;
        }
        return null;
    }
}
