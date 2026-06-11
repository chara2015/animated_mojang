package net.labymod.api.nbt.tags;

import net.labymod.api.nbt.NBTTag;
import net.labymod.api.nbt.NBTTagType;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/nbt/tags/NBTTagByteArray.class */
public interface NBTTagByteArray extends NBTTag<byte[]> {
    @Override // net.labymod.api.nbt.NBTTag
    @NotNull
    default NBTTagType type() {
        return NBTTagType.BYTE_ARRAY;
    }
}
