package net.labymod.v1_17_1.mixins.nbt;

import java.util.Map;
import java.util.Set;
import net.labymod.api.nbt.NBTTag;
import net.labymod.api.nbt.NBTTagType;
import net.labymod.api.nbt.tags.NBTTagCompound;
import net.labymod.api.nbt.tags.NBTTagList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/mixins/nbt/MixinCompoundTag.class */
@Mixin({na.class})
@Implements({@Interface(iface = NBTTagCompound.class, prefix = "labyMod$", remap = Interface.Remap.NONE)})
public abstract class MixinCompoundTag implements NBTTagCompound {

    @Shadow
    @Final
    private Map<String, ns> x;

    @Shadow
    public abstract ns a(String str, ns nsVar);

    @Shadow
    public abstract void r(String str);

    @Shadow
    public abstract ns shadow$c(String str);

    @Shadow
    public abstract boolean e(String str);

    @Shadow
    public abstract boolean b(String str, int i);

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
    public abstract byte f(String str);

    @Shadow
    public abstract short g(String str);

    @Shadow
    public abstract int h(String str);

    @Shadow
    public abstract long i(String str);

    @Shadow
    public abstract float j(String str);

    @Shadow
    public abstract double k(String str);

    @Shadow
    public abstract String l(String str);

    @Shadow
    public abstract byte[] m(String str);

    @Shadow
    public abstract int[] n(String str);

    @Shadow
    public abstract na shadow$p(String str);

    @Shadow
    public abstract ng c(String str, int i);

    @Shadow
    public abstract boolean q(String str);

    @Shadow
    public abstract Set<String> d();

    @Shadow
    public abstract byte d(String str);

    @Shadow
    public abstract void a(String str, long[] jArr);

    @Shadow
    public abstract long[] o(String str);

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
        a(key, (ns) tag);
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
        return shadow$c(key);
    }

    @Intrinsic
    public boolean labyMod$contains(@NotNull String key) {
        return e(key);
    }

    @Intrinsic
    public boolean labyMod$contains(@NotNull String key, @NotNull NBTTagType type) {
        return b(key, type.getId());
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
        return f(key);
    }

    @Intrinsic
    public short labyMod$getShort(@NotNull String key) {
        return g(key);
    }

    @Intrinsic
    public int labyMod$getInt(@NotNull String key) {
        return h(key);
    }

    @Intrinsic
    public long labyMod$getLong(@NotNull String key) {
        return i(key);
    }

    @Intrinsic
    public float labyMod$getFloat(@NotNull String key) {
        return j(key);
    }

    @Intrinsic
    public double labyMod$getDouble(@NotNull String key) {
        return k(key);
    }

    @Intrinsic
    public byte[] labyMod$getByteArray(@NotNull String key) {
        return m(key);
    }

    @Intrinsic
    public int[] labyMod$getIntArray(@NotNull String key) {
        return n(key);
    }

    @Intrinsic
    public long[] labyMod$getLongArray(@NotNull String key) {
        return o(key);
    }

    @Intrinsic
    public String labyMod$getString(@NotNull String key) {
        return l(key);
    }

    @Intrinsic
    public boolean labyMod$getBoolean(@NotNull String key) {
        return q(key);
    }

    @Intrinsic
    public NBTTagCompound labyMod$getCompound(@NotNull String key) {
        return shadow$p(key);
    }

    @Override // net.labymod.api.nbt.tags.NBTTagCompound
    public <I, T extends NBTTag<I>> NBTTagList<I, T> getList(@NotNull String key, @NotNull NBTTagType contentType) {
        return c(key, contentType.getId());
    }

    @Intrinsic
    public NBTTagType labyMod$getType(@NotNull String key) {
        return NBTTagType.getById(d(key));
    }

    @Override // net.labymod.api.nbt.tags.NBTTagCompound
    @NotNull
    public Set<String> keySet() {
        return d();
    }
}
