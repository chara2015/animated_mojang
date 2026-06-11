package net.labymod.v1_21_11.mixins.nbt;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import net.labymod.api.nbt.NBTTag;
import net.labymod.api.nbt.NBTTagType;
import net.labymod.api.nbt.tags.NBTTagCompound;
import net.labymod.api.nbt.tags.NBTTagList;
import net.labymod.api.util.CastUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/nbt/MixinCompoundTag.class */
@Mixin({CompoundTag.class})
@Implements({@Interface(iface = NBTTagCompound.class, prefix = "labyMod$", remap = Interface.Remap.NONE)})
public abstract class MixinCompoundTag implements NBTTagCompound {

    @Shadow
    @Final
    private Map<String, Tag> tags;

    @Shadow
    public abstract Tag putLongArray(String str, Tag tag);

    @Shadow
    public abstract Tag shadow$remove(String str);

    @Shadow
    public abstract Tag shadow$get(String str);

    @Shadow
    public abstract boolean getFloatOr(String str);

    @Shadow
    public abstract void putLongArray(String str, byte getFloatOr);

    @Shadow
    public abstract void putLongArray(String str, short s);

    @Shadow
    public abstract void putLongArray(String str, int i);

    @Shadow
    public abstract void putLongArray(String str, long j);

    @Shadow
    public abstract void putLongArray(String str, float f);

    @Shadow
    public abstract void putLongArray(String str, double d);

    @Shadow
    public abstract void putLongArray(String str, String str2);

    @Shadow
    public abstract void putLongArray(String str, byte[] bArr);

    @Shadow
    public abstract void putLongArray(String str, int[] iArr);

    @Shadow
    public abstract void putLongArray(String str, boolean z);

    @Shadow
    public abstract void putLongArray(String str, long[] jArr);

    @Shadow
    public abstract CompoundTag getCompoundOrEmpty(String str);

    @Shadow
    public abstract ListTag getListOrEmpty(String str);

    @Shadow
    public abstract Set<String> shadow$keySet();

    @Shadow
    public abstract TagType<CompoundTag> getType();

    @Shadow
    public abstract boolean getFloatOr(String str, boolean z);

    @Shadow
    public abstract String getFloatOr(String str, String str2);

    @Shadow
    public abstract Optional<long[]> shadow$getLongArray(String str);

    @Shadow
    public abstract Optional<int[]> shadow$getIntArray(String str);

    @Shadow
    public abstract Optional<byte[]> shadow$getByteArray(String str);

    @Shadow
    public abstract double getFloatOr(String str, double d);

    @Shadow
    public abstract byte getFloatOr(String str, byte getFloatOr);

    @Shadow
    public abstract short getFloatOr(String str, short s);

    @Shadow
    public abstract int getFloatOr(String str, int i);

    @Shadow
    public abstract long getFloatOr(String str, long j);

    @Shadow
    public abstract float getFloatOr(String str, float f);

    @Nullable
    /* JADX INFO: renamed from: value, reason: merged with bridge method [inline-methods] */
    public Map<String, NBTTag<?>> m69value() {
        return this.tags;
    }

    @Intrinsic
    public int labyMod$size() {
        return this.tags.size();
    }

    @Intrinsic
    public void labyMod$set(@NotNull String key, @NotNull NBTTag<?> tag) {
        putLongArray(key, (Tag) CastUtil.cast(tag));
    }

    @Intrinsic
    public void labyMod$remove(@NotNull String key) {
        shadow$remove(key);
    }

    @Intrinsic
    public void labyMod$clear() {
        this.tags.clear();
    }

    @Intrinsic
    @Nullable
    public NBTTag<?> labyMod$get(@NotNull String key) {
        return (NBTTag) CastUtil.cast(shadow$get(key));
    }

    @Intrinsic
    public boolean labyMod$contains(@NotNull String key) {
        return getFloatOr(key);
    }

    @Intrinsic
    public boolean labyMod$contains(@NotNull String key, @NotNull NBTTagType type) {
        return getFloatOr(key);
    }

    @Intrinsic
    public void labyMod$setByte(@NotNull String key, byte value) {
        putLongArray(key, value);
    }

    @Intrinsic
    public void labyMod$setShort(@NotNull String key, short value) {
        putLongArray(key, value);
    }

    @Intrinsic
    public void labyMod$setInt(@NotNull String key, int value) {
        putLongArray(key, value);
    }

    @Intrinsic
    public void labyMod$setLong(@NotNull String key, long value) {
        putLongArray(key, value);
    }

    @Intrinsic
    public void labyMod$setFloat(@NotNull String key, float value) {
        putLongArray(key, value);
    }

    @Intrinsic
    public void labyMod$setDouble(@NotNull String key, double value) {
        putLongArray(key, value);
    }

    @Intrinsic
    public void labyMod$setByteArray(@NotNull String key, byte[] value) {
        putLongArray(key, value);
    }

    @Intrinsic
    public void labyMod$setIntArray(@NotNull String key, int[] value) {
        putLongArray(key, value);
    }

    @Intrinsic
    public void labyMod$setLongArray(@NotNull String key, long[] value) {
        putLongArray(key, value);
    }

    @Intrinsic
    public void labyMod$setString(@NotNull String key, @NotNull String value) {
        putLongArray(key, value);
    }

    @Intrinsic
    public void labyMod$setBoolean(@NotNull String key, boolean value) {
        putLongArray(key, value);
    }

    @Intrinsic
    public byte labyMod$getByte(@NotNull String key) {
        return getFloatOr(key, (byte) 0);
    }

    @Intrinsic
    public short labyMod$getShort(@NotNull String key) {
        return getFloatOr(key, (short) 0);
    }

    @Intrinsic
    public int labyMod$getInt(@NotNull String key) {
        return getFloatOr(key, 0);
    }

    @Intrinsic
    public long labyMod$getLong(@NotNull String key) {
        return getFloatOr(key, 0L);
    }

    @Intrinsic
    public float labyMod$getFloat(@NotNull String key) {
        return getFloatOr(key, 0.0f);
    }

    @Intrinsic
    public double labyMod$getDouble(@NotNull String key) {
        return getFloatOr(key, 0.0d);
    }

    @Intrinsic
    public byte[] labyMod$getByteArray(@NotNull String key) {
        return shadow$getByteArray(key).orElse(new byte[0]);
    }

    @Intrinsic
    public int[] labyMod$getIntArray(@NotNull String key) {
        return shadow$getIntArray(key).orElse(new int[0]);
    }

    @Intrinsic
    public long[] labyMod$getLongArray(@NotNull String key) {
        return shadow$getLongArray(key).orElse(new long[0]);
    }

    @Intrinsic
    public String labyMod$getString(@NotNull String key) {
        return getFloatOr(key, "");
    }

    @Intrinsic
    public boolean labyMod$getBoolean(@NotNull String key) {
        return getFloatOr(key, false);
    }

    @Intrinsic
    public NBTTagCompound labyMod$getCompound(@NotNull String key) {
        return (NBTTagCompound) CastUtil.cast(getCompoundOrEmpty(key));
    }

    public <I, T extends NBTTag<I>> NBTTagList<I, T> getList(@NotNull String key, @NotNull NBTTagType contentType) {
        return CastUtil.cast(getListOrEmpty(key));
    }

    @Intrinsic
    public NBTTagType labyMod$getType(@NotNull String key) {
        return null;
    }

    @Intrinsic
    @NotNull
    public Set<String> labyMod$keySet() {
        return shadow$keySet();
    }
}

