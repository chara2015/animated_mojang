package net.labymod.api.nbt.tags;

import net.labymod.api.nbt.NBTTagType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/nbt/tags/NBTTagShort.class */
public interface NBTTagShort extends NBTTagNumber<Short> {
    @Override // net.labymod.api.nbt.NBTTag
    @Nullable
    Short value();

    @Override // net.labymod.api.nbt.NBTTag
    @NotNull
    default NBTTagType type() {
        return NBTTagType.SHORT;
    }
}
