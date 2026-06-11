package net.labymod.api.nbt.tags;

import net.labymod.api.nbt.NBTTagType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/nbt/tags/NBTTagInt.class */
public interface NBTTagInt extends NBTTagNumber<Integer> {
    @Override // net.labymod.api.nbt.NBTTag
    @Nullable
    Integer value();

    @Override // net.labymod.api.nbt.NBTTag
    @NotNull
    default NBTTagType type() {
        return NBTTagType.INT;
    }
}
