package net.labymod.v1_19_4.mixins.nbt;

import java.util.ArrayList;
import java.util.List;
import net.labymod.api.nbt.NBTTag;
import net.labymod.api.nbt.NBTTagType;
import net.labymod.api.nbt.tags.NBTTagList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/nbt/MixinListTag.class */
@Mixin({rk.class})
@Implements({@Interface(iface = NBTTagList.class, prefix = "labyMod$", remap = Interface.Remap.NONE)})
public abstract class MixinListTag implements NBTTagList {

    @Shadow
    @Final
    private List<rx> c;

    @Shadow
    private byte w;

    @Shadow
    public abstract boolean b(int i, rx rxVar);

    @Shadow
    public abstract boolean a(int i, rx rxVar);

    @Shadow
    public abstract void c(int i, rx rxVar);

    @Shadow
    public abstract rx shadow$c(int i);

    @Override // net.labymod.api.nbt.NBTTag
    @Nullable
    public List value() {
        List values = new ArrayList(this.c.size());
        for (int i = 0; i < this.c.size(); i++) {
            values.add(i, this.c.get(i).value());
        }
        return values;
    }

    @Override // net.labymod.api.nbt.tags.NBTTagList
    @NotNull
    public List tags() {
        return this.c;
    }

    @Override // net.labymod.api.nbt.tags.NBTTagList
    public int size() {
        return this.c.size();
    }

    @Override // net.labymod.api.nbt.tags.NBTTagList
    public boolean isEmpty() {
        return this.c.isEmpty();
    }

    @Override // net.labymod.api.nbt.tags.NBTTagList
    public Object getValue(int i) {
        return get(i).value();
    }

    @Override // net.labymod.api.nbt.tags.NBTTagList
    public NBTTag get(int i) {
        return this.c.get(i);
    }

    @Override // net.labymod.api.nbt.tags.NBTTagList
    public void add(NBTTag tag) {
        b(size(), (rx) tag);
    }

    @Override // net.labymod.api.nbt.tags.NBTTagList
    public void add(int i, NBTTag tag) {
        c(i, (rx) tag);
    }

    @Override // net.labymod.api.nbt.tags.NBTTagList
    public void set(int i, NBTTag tag) {
        a(i, (rx) tag);
    }

    @Override // net.labymod.api.nbt.tags.NBTTagList
    public void remove(int i) {
        shadow$c(i);
    }

    @Override // net.labymod.api.nbt.tags.NBTTagList
    public void remove(NBTTag tag) {
        this.c.remove((rx) tag);
    }

    @Override // net.labymod.api.nbt.tags.NBTTagList
    public void clear() {
        this.c.clear();
    }

    @Override // net.labymod.api.nbt.tags.NBTTagList
    @Nullable
    public NBTTagType contentType() {
        if (this.w != 0 || isEmpty()) {
            return NBTTagType.getById(this.w);
        }
        return null;
    }
}
