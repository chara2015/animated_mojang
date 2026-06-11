package net.labymod.v1_8_9.mixins.nbt;

import java.util.ArrayList;
import java.util.List;
import net.labymod.api.nbt.NBTTag;
import net.labymod.api.nbt.NBTTagType;
import net.labymod.api.nbt.tags.NBTTagList;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/nbt/MixinNBTTagList.class */
@Mixin({du.class})
public abstract class MixinNBTTagList implements NBTTagList {

    @Shadow
    @Final
    private static Logger b;

    @Shadow
    private List<eb> c;

    @Shadow
    private byte d;

    @Shadow
    public abstract void a(eb ebVar);

    @Shadow
    public abstract void a(int i, eb ebVar);

    @Shadow
    public abstract eb a(int i);

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
        a((eb) tag);
    }

    @Override // net.labymod.api.nbt.tags.NBTTagList
    public void add(int i, NBTTag tag) {
        eb base = (eb) tag;
        if (base.a() == 0) {
            b.warn("Invalid TagEnd added to ListTag");
            return;
        }
        if (i >= 0 && i <= this.c.size()) {
            if (this.d == 0) {
                this.d = base.a();
            } else if (this.d != base.a()) {
                b.warn("Adding mismatching tag types to tag list");
                return;
            }
            this.c.add(i, base);
            return;
        }
        b.warn("index out of bounds to add tag in tag list");
    }

    @Override // net.labymod.api.nbt.tags.NBTTagList
    public void set(int i, NBTTag tag) {
        a(i, (eb) tag);
    }

    @Override // net.labymod.api.nbt.tags.NBTTagList
    public void remove(NBTTag tag) {
        this.c.remove((eb) tag);
    }

    @Override // net.labymod.api.nbt.tags.NBTTagList
    public void remove(int i) {
        a(i);
    }

    @Override // net.labymod.api.nbt.tags.NBTTagList
    public void clear() {
        this.c.clear();
    }

    @Override // net.labymod.api.nbt.tags.NBTTagList
    @Nullable
    public NBTTagType contentType() {
        if (this.d != 0 || isEmpty()) {
            return NBTTagType.getById(this.d);
        }
        return null;
    }
}
