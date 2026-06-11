package net.labymod.api.nbt.tags;

import java.util.Map;
import java.util.Set;
import net.labymod.api.nbt.NBTTag;
import net.labymod.api.nbt.NBTTagType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/nbt/tags/NBTTagCompound.class */
public interface NBTTagCompound extends NBTTag<Map<String, NBTTag<?>>> {
    void set(@NotNull String str, @NotNull NBTTag<?> nBTTag);

    void remove(@NotNull String str);

    void clear();

    int size();

    @Nullable
    NBTTag<?> get(@NotNull String str);

    boolean contains(@NotNull String str);

    boolean contains(@NotNull String str, @NotNull NBTTagType nBTTagType);

    void setByte(@NotNull String str, byte b);

    void setShort(@NotNull String str, short s);

    void setInt(@NotNull String str, int i);

    void setLong(@NotNull String str, long j);

    void setFloat(@NotNull String str, float f);

    void setDouble(@NotNull String str, double d);

    void setByteArray(@NotNull String str, byte[] bArr);

    void setIntArray(@NotNull String str, int[] iArr);

    void setLongArray(@NotNull String str, long[] jArr);

    void setString(@NotNull String str, @NotNull String str2);

    void setBoolean(@NotNull String str, boolean z);

    byte getByte(@NotNull String str);

    short getShort(@NotNull String str);

    int getInt(@NotNull String str);

    long getLong(@NotNull String str);

    float getFloat(@NotNull String str);

    double getDouble(@NotNull String str);

    byte[] getByteArray(@NotNull String str);

    int[] getIntArray(@NotNull String str);

    long[] getLongArray(@NotNull String str);

    String getString(@NotNull String str);

    boolean getBoolean(@NotNull String str);

    NBTTagCompound getCompound(@NotNull String str);

    <I, T extends NBTTag<I>> NBTTagList<I, T> getList(@NotNull String str, @NotNull NBTTagType nBTTagType);

    NBTTagType getType(@NotNull String str);

    @NotNull
    Set<String> keySet();

    @Override // net.labymod.api.nbt.NBTTag
    @NotNull
    default NBTTagType type() {
        return NBTTagType.COMPOUND;
    }
}
