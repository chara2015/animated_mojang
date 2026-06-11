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
    private List<Tag> list;

    @Shadow
    public abstract boolean addTag(int i, Tag tag);

    @Shadow
    public abstract boolean setTag(int i, Tag tag);

    @Shadow
    public abstract void add(int i, Tag tag);

    @Shadow
    public abstract Tag shadow$remove(int i);

    @Nullable
    /* JADX INFO: renamed from: value, reason: merged with bridge method [inline-methods] */
    public List m71value() {
        List values = new ArrayList(this.list.size());
        for (int i = 0; i < this.list.size(); i++) {
            NBTTag tag = (NBTTag) CastUtil.cast(this.list.get(i));
            values.add(i, tag.value());
        }
        return values;
    }

    @NotNull
    public List tags() {
        return this.list;
    }

    public int size() {
        return this.list.size();
    }

    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    public Object getValue(int i) {
        return get(i).value();
    }

    public NBTTag get(int i) {
        return (NBTTag) CastUtil.cast(this.list.get(i));
    }

    public void add(NBTTag tag) {
        addTag(size(), (Tag) CastUtil.cast(tag));
    }

    public void add(int i, NBTTag tag) {
        add(i, (Tag) CastUtil.cast(tag));
    }

    public void set(int i, NBTTag tag) {
        setTag(i, (Tag) CastUtil.cast(tag));
    }

    public void remove(int i) {
        shadow$remove(i);
    }

    public void remove(NBTTag tag) {
        this.list.remove(CastUtil.cast(tag));
    }

    public void clear() {
        this.list.clear();
    }

    @Nullable
    public NBTTagType contentType() {
        return NBTTagType.LIST;
    }
}

