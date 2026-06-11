package net.labymod.v26_1.mixins.nbt;

import java.util.Collection;
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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/mixins/nbt/MixinCompoundTag.class */
@Mixin({CompoundTag.class})
@Implements({@Interface(iface = NBTTagCompound.class, prefix = "labyMod$", remap = Interface.Remap.NONE)})
public abstract class MixinCompoundTag implements NBTTagCompound {

    @Shadow
    @Final
    private Map<String, Tag> tags;

    @Shadow
    public abstract Tag put(String str, Tag tag);

    @Shadow
    public abstract Tag shadow$remove(String str);

    @Shadow
    public abstract Tag shadow$get(String str);

    @Override // net.labymod.api.nbt.tags.NBTTagCompound
    @Shadow
    public abstract boolean contains(String str);

    @Shadow
    public abstract void putByte(String str, byte b);

    @Shadow
    public abstract void putShort(String str, short s);

    @Shadow
    public abstract void putInt(String str, int i);

    @Shadow
    public abstract void putLong(String str, long j);

    @Shadow
    public abstract void putFloat(String str, float f);

    @Shadow
    public abstract void putDouble(String str, double d);

    @Shadow
    public abstract void putString(String str, String str2);

    @Shadow
    public abstract void putByteArray(String str, byte[] bArr);

    @Shadow
    public abstract void putIntArray(String str, int[] iArr);

    @Shadow
    public abstract void putBoolean(String str, boolean z);

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
    public abstract boolean getBooleanOr(String str, boolean z);

    @Shadow
    public abstract String getStringOr(String str, String str2);

    @Shadow
    public abstract Optional<long[]> shadow$getLongArray(String str);

    @Shadow
    public abstract Optional<int[]> shadow$getIntArray(String str);

    @Shadow
    public abstract Optional<byte[]> shadow$getByteArray(String str);

    @Shadow
    public abstract double getDoubleOr(String str, double d);

    @Shadow
    public abstract byte getByteOr(String str, byte b);

    @Shadow
    public abstract short getShortOr(String str, short s);

    @Shadow
    public abstract int getIntOr(String str, int i);

    @Shadow
    public abstract long getLongOr(String str, long j);

    @Shadow
    public abstract float getFloatOr(String str, float f);

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.nbt.NBTTag
    @Nullable
    public Map<String, NBTTag<?>> value() {
        return this.tags;
    }

    @Intrinsic
    public int labyMod$size() {
        return this.tags.size();
    }

    @Intrinsic
    public void labyMod$set(@NotNull String key, @NotNull NBTTag<?> tag) {
        put(key, (Tag) CastUtil.cast(tag));
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
        return contains(key);
    }

    @Intrinsic
    public boolean labyMod$contains(@NotNull String key, @NotNull NBTTagType type) {
        return contains(key);
    }

    @Intrinsic
    public void labyMod$setByte(@NotNull String key, byte value) {
        putByte(key, value);
    }

    @Intrinsic
    public void labyMod$setShort(@NotNull String key, short value) {
        putShort(key, value);
    }

    @Intrinsic
    public void labyMod$setInt(@NotNull String key, int value) {
        putInt(key, value);
    }

    @Intrinsic
    public void labyMod$setLong(@NotNull String key, long value) {
        putLong(key, value);
    }

    @Intrinsic
    public void labyMod$setFloat(@NotNull String key, float value) {
        putFloat(key, value);
    }

    @Intrinsic
    public void labyMod$setDouble(@NotNull String key, double value) {
        putDouble(key, value);
    }

    @Intrinsic
    public void labyMod$setByteArray(@NotNull String key, byte[] value) {
        putByteArray(key, value);
    }

    @Intrinsic
    public void labyMod$setIntArray(@NotNull String key, int[] value) {
        putIntArray(key, value);
    }

    @Intrinsic
    public void labyMod$setLongArray(@NotNull String key, long[] value) {
        putLongArray(key, value);
    }

    @Intrinsic
    public void labyMod$setString(@NotNull String key, @NotNull String value) {
        putString(key, value);
    }

    @Intrinsic
    public void labyMod$setBoolean(@NotNull String key, boolean value) {
        putBoolean(key, value);
    }

    @Intrinsic
    public byte labyMod$getByte(@NotNull String key) {
        return getByteOr(key, (byte) 0);
    }

    @Intrinsic
    public short labyMod$getShort(@NotNull String key) {
        return getShortOr(key, (short) 0);
    }

    @Intrinsic
    public int labyMod$getInt(@NotNull String key) {
        return getIntOr(key, 0);
    }

    @Intrinsic
    public long labyMod$getLong(@NotNull String key) {
        return getLongOr(key, 0L);
    }

    @Intrinsic
    public float labyMod$getFloat(@NotNull String key) {
        return getFloatOr(key, 0.0f);
    }

    @Intrinsic
    public double labyMod$getDouble(@NotNull String key) {
        return getDoubleOr(key, 0.0d);
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
        return getStringOr(key, "");
    }

    @Intrinsic
    public boolean labyMod$getBoolean(@NotNull String key) {
        return getBooleanOr(key, false);
    }

    @Intrinsic
    public NBTTagCompound labyMod$getCompound(@NotNull String key) {
        return (NBTTagCompound) CastUtil.cast(getCompoundOrEmpty(key));
    }

    @Override // net.labymod.api.nbt.tags.NBTTagCompound
    public <I, T extends NBTTag<I>> NBTTagList<I, T> getList(@NotNull String key, @NotNull NBTTagType contentType) {
        return (NBTTagList) CastUtil.cast((Collection<?>) getListOrEmpty(key));
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
