package net.labymod.api.nbt;

import java.io.DataOutput;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/nbt/NBTTag.class */
public interface NBTTag<T> {
    @NotNull
    NBTTagType type();

    @Nullable
    T value();

    void write(@NotNull DataOutput dataOutput);
}
