package net.labymod.v1_21_11.mixins.nbt;

import java.util.ArrayList;
import java.util.List;
import net.labymod.api.nbt.NBTTag;
import net.labymod.api.nbt.NBTTagType;
import net.labymod.api.nbt.tags.NBTTagList;
import net.labymod.api.util.CastUtil;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/nbt/MixinListTag.class */
@Mixin({ListTag.class})
@Implements({@Interface(iface = NBTTagList.class, prefix = "labyMod$", remap = Interface.Remap.NONE)})
public abstract class MixinListTag implements NBTTagList {

    @Shadow
    @Final
    private List<Tag> v;

    @Shadow
    public abstract boolean b(int i, Tag tag);

    @Shadow
    public abstract boolean a(int i, Tag tag);

    @Shadow
    public abstract void d(int i, Tag tag);

    @Shadow
    public abstract Tag shadow$d(int i);

    @Nullable
    /* JADX INFO: renamed from: value, reason: merged with bridge method [inline-methods] */
    public List m71value() {
        List values = new ArrayList(this.v.size());
        for (int i = 0; i < this.v.size(); i++) {
            NBTTag tag = (NBTTag) CastUtil.cast(this.v.get(i));
            values.add(i, tag.value());
        }
        return values;
    }

    @NotNull
    public List tags() {
        return this.v;
    }

    public int size() {
        return this.v.size();
    }

    public boolean isEmpty() {
        return this.v.isEmpty();
    }

    public Object getValue(int i) {
        return get(i).value();
    }

    public NBTTag get(int i) {
        return (NBTTag) CastUtil.cast(this.v.get(i));
    }

    public void add(NBTTag tag) {
        b(size(), (Tag) CastUtil.cast(tag));
    }

    public void add(int i, NBTTag tag) {
        d(i, (Tag) CastUtil.cast(tag));
    }

    public void set(int i, NBTTag tag) {
        a(i, (Tag) CastUtil.cast(tag));
    }

    public void remove(int i) {
        shadow$d(i);
    }

    public void remove(NBTTag tag) {
        this.v.remove(CastUtil.cast(tag));
    }

    public void clear() {
        this.v.clear();
    }

    @Nullable
    public NBTTagType contentType() {
        return NBTTagType.LIST;
    }
}
