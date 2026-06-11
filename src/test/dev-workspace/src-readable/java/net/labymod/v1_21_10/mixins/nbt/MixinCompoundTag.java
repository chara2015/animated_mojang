package net.labymod.v1_21_10.mixins.nbt;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import net.labymod.api.nbt.NBTTag;
import net.labymod.api.nbt.NBTTagType;
import net.labymod.api.nbt.tags.NBTTagCompound;
import net.labymod.api.nbt.tags.NBTTagList;
import net.labymod.api.util.CastUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/nbt/MixinCompoundTag.class */
@Mixin({up.class})
@Implements({@Interface(iface = NBTTagCompound.class, prefix = "labyMod$", remap = Interface.Remap.NONE)})
public abstract class MixinCompoundTag implements NBTTagCompound {

    @Shadow
    @Final
    private Map<String, vp> x;

    @Shadow
    public abstract vp a(String str, vp vpVar);

    @Shadow
    public abstract void r(String str);

    @Shadow
    public abstract vp shadow$a(String str);

    @Shadow
    public abstract boolean b(String str);

    @Shadow
    public abstract void a(String str, byte b);

    @Shadow
    public abstract void a(String str, short s);

    @Shadow
    public abstract void a(String str, int i);

    @Shadow
    public abstract void a(String str, long j);

    @Shadow
    public abstract void a(String str, float f);

    @Shadow
    public abstract void a(String str, double d);

    @Shadow
    public abstract void a(String str, String str2);

    @Shadow
    public abstract void a(String str, byte[] bArr);

    @Shadow
    public abstract void a(String str, int[] iArr);

    @Shadow
    public abstract void a(String str, boolean z);

    @Shadow
    public abstract void a(String str, long[] jArr);

    @Shadow
    public abstract up n(String str);

    @Shadow
    public abstract uv p(String str);

    @Shadow
    public abstract Set<String> shadow$e();

    @Shadow
    public abstract vr<up> c();

    @Shadow
    public abstract boolean b(String str, boolean z);

    @Shadow
    public abstract String b(String str, String str2);

    @Shadow
    public abstract Optional<long[]> shadow$l(String str);

    @Shadow
    public abstract Optional<int[]> shadow$k(String str);

    @Shadow
    public abstract Optional<byte[]> shadow$j(String str);

    @Shadow
    public abstract double b(String str, double d);

    @Shadow
    public abstract byte b(String str, byte b);

    @Shadow
    public abstract short b(String str, short s);

    @Shadow
    public abstract int b(String str, int i);

    @Shadow
    public abstract long b(String str, long j);

    @Shadow
    public abstract float b(String str, float f);

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.nbt.NBTTag
    @Nullable
    public Map<String, NBTTag<?>> value() {
        return this.x;
    }

    @Intrinsic
    public int labyMod$size() {
        return this.x.size();
    }

    @Intrinsic
    public void labyMod$set(@NotNull String key, @NotNull NBTTag<?> tag) {
        a(key, (vp) CastUtil.cast(tag));
    }

    @Intrinsic
    public void labyMod$remove(@NotNull String key) {
        r(key);
    }

    @Intrinsic
    public void labyMod$clear() {
        this.x.clear();
    }

    @Intrinsic
    @Nullable
    public NBTTag<?> labyMod$get(@NotNull String key) {
        return (NBTTag) CastUtil.cast(shadow$a(key));
    }

    @Intrinsic
    public boolean labyMod$contains(@NotNull String key) {
        return b(key);
    }

    @Intrinsic
    public boolean labyMod$contains(@NotNull String key, @NotNull NBTTagType type) {
        return b(key);
    }

    @Intrinsic
    public void labyMod$setByte(@NotNull String key, byte value) {
        a(key, value);
    }

    @Intrinsic
    public void labyMod$setShort(@NotNull String key, short value) {
        a(key, value);
    }

    @Intrinsic
    public void labyMod$setInt(@NotNull String key, int value) {
        a(key, value);
    }

    @Intrinsic
    public void labyMod$setLong(@NotNull String key, long value) {
        a(key, value);
    }

    @Intrinsic
    public void labyMod$setFloat(@NotNull String key, float value) {
        a(key, value);
    }

    @Intrinsic
    public void labyMod$setDouble(@NotNull String key, double value) {
        a(key, value);
    }

    @Intrinsic
    public void labyMod$setByteArray(@NotNull String key, byte[] value) {
        a(key, value);
    }

    @Intrinsic
    public void labyMod$setIntArray(@NotNull String key, int[] value) {
        a(key, value);
    }

    @Intrinsic
    public void labyMod$setLongArray(@NotNull String key, long[] value) {
        a(key, value);
    }

    @Intrinsic
    public void labyMod$setString(@NotNull String key, @NotNull String value) {
        a(key, value);
    }

    @Intrinsic
    public void labyMod$setBoolean(@NotNull String key, boolean value) {
        a(key, value);
    }

    @Intrinsic
    public byte labyMod$getByte(@NotNull String key) {
        return b(key, (byte) 0);
    }

    @Intrinsic
    public short labyMod$getShort(@NotNull String key) {
        return b(key, (short) 0);
    }

    @Intrinsic
    public int labyMod$getInt(@NotNull String key) {
        return b(key, 0);
    }

    @Intrinsic
    public long labyMod$getLong(@NotNull String key) {
        return b(key, 0L);
    }

    @Intrinsic
    public float labyMod$getFloat(@NotNull String key) {
        return b(key, 0.0f);
    }

    @Intrinsic
    public double labyMod$getDouble(@NotNull String key) {
        return b(key, 0.0d);
    }

    @Intrinsic
    public byte[] labyMod$getByteArray(@NotNull String key) {
        return shadow$j(key).orElse(new byte[0]);
    }

    @Intrinsic
    public int[] labyMod$getIntArray(@NotNull String key) {
        return shadow$k(key).orElse(new int[0]);
    }

    @Intrinsic
    public long[] labyMod$getLongArray(@NotNull String key) {
        return shadow$l(key).orElse(new long[0]);
    }

    @Intrinsic
    public String labyMod$getString(@NotNull String key) {
        return b(key, "");
    }

    @Intrinsic
    public boolean labyMod$getBoolean(@NotNull String key) {
        return b(key, false);
    }

    @Intrinsic
    public NBTTagCompound labyMod$getCompound(@NotNull String key) {
        return (NBTTagCompound) CastUtil.cast(n(key));
    }

    @Override // net.labymod.api.nbt.tags.NBTTagCompound
    public <I, T extends NBTTag<I>> NBTTagList<I, T> getList(@NotNull String key, @NotNull NBTTagType contentType) {
        return (NBTTagList) CastUtil.cast((Collection<?>) p(key));
    }

    @Intrinsic
    public NBTTagType labyMod$getType(@NotNull String key) {
        return null;
    }

    @Intrinsic
    @NotNull
    public Set<String> labyMod$keySet() {
        return shadow$e();
    }
}
