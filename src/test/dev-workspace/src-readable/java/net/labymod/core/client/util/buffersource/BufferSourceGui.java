package net.labymod.core.client.util.buffersource;

import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/util/buffersource/BufferSourceGui.class */
@Referenceable
public interface BufferSourceGui {
    void blit(Object obj, Object obj2, int i, int i2, int i3, int i4, int i5, int i6);

    void blit(Object obj, Object obj2, int i, int i2, int i3, float f, float f2, int i4, int i5, int i6, int i7);

    void blit(Object obj, Object obj2, int i, int i2, int i3, int i4, float f, float f2, int i5, int i6, int i7, int i8);

    void blit(Object obj, Object obj2, int i, int i2, float f, float f2, int i3, int i4, int i5, int i6);
}
