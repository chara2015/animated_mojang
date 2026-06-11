package net.labymod.api.nbt.tags;

import net.labymod.api.nbt.NBTTag;
import net.labymod.api.nbt.NBTTagType;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/nbt/tags/NBTTagLongArray.class */
public interface NBTTagLongArray extends NBTTag<long[]> {
    @Override // net.labymod.api.nbt.NBTTag
    @NotNull
    default NBTTagType type() {
        return NBTTagType.LONG_ARRAY;
    }
}
