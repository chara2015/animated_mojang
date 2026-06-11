package net.labymod.v1_21_10.mixins.nbt;

import java.util.ArrayList;
import java.util.List;
import net.labymod.api.nbt.NBTTag;
import net.labymod.api.nbt.NBTTagType;
import net.labymod.api.nbt.tags.NBTTagList;
import net.labymod.api.util.CastUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/nbt/MixinListTag.class */
@Mixin({uv.class})
@Implements({@Interface(iface = NBTTagList.class, prefix = "labyMod$", remap = Interface.Remap.NONE)})
public abstract class MixinListTag implements NBTTagList {

    @Shadow
    @Final
    private List<vp> v;

    @Shadow
    public abstract boolean b(int i, vp vpVar);

    @Shadow
    public abstract boolean a(int i, vp vpVar);

    @Shadow
    public abstract void d(int i, vp vpVar);

    @Shadow
    public abstract vp shadow$d(int i);

    @Override // net.labymod.api.nbt.NBTTag
    @Nullable
    public List value() {
        List values = new ArrayList(this.v.size());
        for (int i = 0; i < this.v.size(); i++) {
            NBTTag tag = (NBTTag) CastUtil.cast(this.v.get(i));
            values.add(i, tag.value());
        }
        return values;
    }

    @Override // net.labymod.api.nbt.tags.NBTTagList
    @NotNull
    public List tags() {
        return this.v;
    }

    @Override // net.labymod.api.nbt.tags.NBTTagList
    public int size() {
        return this.v.size();
    }

    @Override // net.labymod.api.nbt.tags.NBTTagList
    public boolean isEmpty() {
        return this.v.isEmpty();
    }

    @Override // net.labymod.api.nbt.tags.NBTTagList
    public Object getValue(int i) {
        return get(i).value();
    }

    @Override // net.labymod.api.nbt.tags.NBTTagList
    public NBTTag get(int i) {
        return (NBTTag) CastUtil.cast(this.v.get(i));
    }

    @Override // net.labymod.api.nbt.tags.NBTTagList
    public void add(NBTTag tag) {
        b(size(), (vp) CastUtil.cast(tag));
    }

    @Override // net.labymod.api.nbt.tags.NBTTagList
    public void add(int i, NBTTag tag) {
        d(i, (vp) CastUtil.cast(tag));
    }

    @Override // net.labymod.api.nbt.tags.NBTTagList
    public void set(int i, NBTTag tag) {
        a(i, (vp) CastUtil.cast(tag));
    }

    @Override // net.labymod.api.nbt.tags.NBTTagList
    public void remove(int i) {
        shadow$d(i);
    }

    @Override // net.labymod.api.nbt.tags.NBTTagList
    public void remove(NBTTag tag) {
        this.v.remove(CastUtil.cast(tag));
    }

    @Override // net.labymod.api.nbt.tags.NBTTagList
    public void clear() {
        this.v.clear();
    }

    @Override // net.labymod.api.nbt.tags.NBTTagList
    @Nullable
    public NBTTagType contentType() {
        return NBTTagType.LIST;
    }
}
