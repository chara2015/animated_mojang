package net.minecraft.client.renderer.texture;

import com.google.common.collect.ImmutableList;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import net.minecraft.client.renderer.texture.Stitcher.Entry;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/texture/Stitcher.class */
public class Stitcher<T extends Entry> {
    private static final Comparator<Holder<?>> HOLDER_COMPARATOR = Comparator.comparing($$0 -> {
        return Integer.valueOf(-$$0.height);
    }).thenComparing($$02 -> {
        return Integer.valueOf(-$$02.width);
    }).thenComparing($$03 -> {
        return $$03.entry.name();
    });
    private final int mipLevel;
    private final List<Holder<T>> texturesToBeStitched = new ArrayList();
    private final List<Region<T>> storage = new ArrayList();
    private int storageX;
    private int storageY;
    private final int maxWidth;
    private final int maxHeight;
    private final int padding;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/texture/Stitcher$Entry.class */
    public interface Entry {
        int width();

        int height();

        Identifier name();
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/texture/Stitcher$SpriteLoader.class */
    public interface SpriteLoader<T extends Entry> {
        void load(T t, int i, int i2, int i3);
    }

    public Stitcher(int $$0, int $$1, int $$2, int $$3) {
        this.mipLevel = $$2;
        this.maxWidth = $$0;
        this.maxHeight = $$1;
        this.padding = (1 << $$2) << Mth.clamp($$3 - 1, 0, 4);
    }

    public int getWidth() {
        return this.storageX;
    }

    public int getHeight() {
        return this.storageY;
    }

    public void registerSprite(T $$0) {
        Holder<T> $$1 = new Holder<>($$0, smallestFittingMinTexel($$0.width() + (this.padding * 2), this.mipLevel), smallestFittingMinTexel($$0.height() + (this.padding * 2), this.mipLevel));
        this.texturesToBeStitched.add($$1);
    }

    public void stitch() {
        List<Holder<T>> $$0 = new ArrayList<>(this.texturesToBeStitched);
        $$0.sort(HOLDER_COMPARATOR);
        for (Holder<T> $$1 : $$0) {
            if (!addToStorage($$1)) {
                throw new StitcherException(((Holder) $$1).entry, (Collection) $$0.stream().map($$02 -> {
                    return $$02.entry;
                }).collect(ImmutableList.toImmutableList()));
            }
        }
    }

    public void gatherSprites(SpriteLoader<T> $$0) {
        for (Region<T> $$1 : this.storage) {
            $$1.walk($$0, this.padding);
        }
    }

    private static int smallestFittingMinTexel(int $$0, int $$1) {
        return (($$0 >> $$1) + (($$0 & ((1 << $$1) - 1)) == 0 ? 0 : 1)) << $$1;
    }

    private boolean addToStorage(Holder<T> $$0) {
        for (Region<T> $$1 : this.storage) {
            if ($$1.add($$0)) {
                return true;
            }
        }
        return expand($$0);
    }

    private boolean expand(Holder<T> $$0) {
        boolean $$10;
        Region<T> $$12;
        int $$1 = Mth.smallestEncompassingPowerOfTwo(this.storageX);
        int $$2 = Mth.smallestEncompassingPowerOfTwo(this.storageY);
        int $$3 = Mth.smallestEncompassingPowerOfTwo(this.storageX + ((Holder) $$0).width);
        int $$4 = Mth.smallestEncompassingPowerOfTwo(this.storageY + ((Holder) $$0).height);
        boolean $$5 = $$3 <= this.maxWidth;
        boolean $$6 = $$4 <= this.maxHeight;
        if (!$$5 && !$$6) {
            return false;
        }
        boolean $$7 = $$5 && $$1 != $$3;
        boolean $$8 = $$6 && $$2 != $$4;
        if ($$7 ^ $$8) {
            $$10 = $$7;
        } else {
            $$10 = $$5 && $$1 <= $$2;
        }
        if ($$10) {
            if (this.storageY == 0) {
                this.storageY = $$4;
            }
            $$12 = new Region<>(this.storageX, 0, $$3 - this.storageX, this.storageY);
            this.storageX = $$3;
        } else {
            $$12 = new Region<>(0, this.storageY, this.storageX, $$4 - this.storageY);
            this.storageY = $$4;
        }
        $$12.add($$0);
        this.storage.add($$12);
        return true;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/texture/Stitcher$Holder.class */
    static final class Holder<T extends Entry> extends Record {
        private final T entry;
        private final int width;
        private final int height;

        Holder(T $$0, int $$1, int $$2) {
            this.entry = $$0;
            this.width = $$1;
            this.height = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Holder.class), Holder.class, "entry;width;height", "FIELD:Lnet/minecraft/client/renderer/texture/Stitcher$Holder;->entry:Lnet/minecraft/client/renderer/texture/Stitcher$Entry;", "FIELD:Lnet/minecraft/client/renderer/texture/Stitcher$Holder;->width:I", "FIELD:Lnet/minecraft/client/renderer/texture/Stitcher$Holder;->height:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Holder.class), Holder.class, "entry;width;height", "FIELD:Lnet/minecraft/client/renderer/texture/Stitcher$Holder;->entry:Lnet/minecraft/client/renderer/texture/Stitcher$Entry;", "FIELD:Lnet/minecraft/client/renderer/texture/Stitcher$Holder;->width:I", "FIELD:Lnet/minecraft/client/renderer/texture/Stitcher$Holder;->height:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Holder.class, Object.class), Holder.class, "entry;width;height", "FIELD:Lnet/minecraft/client/renderer/texture/Stitcher$Holder;->entry:Lnet/minecraft/client/renderer/texture/Stitcher$Entry;", "FIELD:Lnet/minecraft/client/renderer/texture/Stitcher$Holder;->width:I", "FIELD:Lnet/minecraft/client/renderer/texture/Stitcher$Holder;->height:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public T entry() {
            return this.entry;
        }

        public int width() {
            return this.width;
        }

        public int height() {
            return this.height;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/texture/Stitcher$Region.class */
    public static class Region<T extends Entry> {
        private final int originX;
        private final int originY;
        private final int width;
        private final int height;
        private List<Region<T>> subSlots;
        private Holder<T> holder;

        public Region(int $$0, int $$1, int $$2, int $$3) {
            this.originX = $$0;
            this.originY = $$1;
            this.width = $$2;
            this.height = $$3;
        }

        public int getX() {
            return this.originX;
        }

        public int getY() {
            return this.originY;
        }

        public boolean add(Holder<T> $$0) {
            if (this.holder != null) {
                return false;
            }
            int $$1 = ((Holder) $$0).width;
            int $$2 = ((Holder) $$0).height;
            if ($$1 > this.width || $$2 > this.height) {
                return false;
            }
            if ($$1 == this.width && $$2 == this.height) {
                this.holder = $$0;
                return true;
            }
            if (this.subSlots == null) {
                this.subSlots = new ArrayList(1);
                this.subSlots.add(new Region<>(this.originX, this.originY, $$1, $$2));
                int $$3 = this.width - $$1;
                int $$4 = this.height - $$2;
                if ($$4 > 0 && $$3 > 0) {
                    int $$5 = Math.max(this.height, $$3);
                    int $$6 = Math.max(this.width, $$4);
                    if ($$5 >= $$6) {
                        this.subSlots.add(new Region<>(this.originX, this.originY + $$2, $$1, $$4));
                        this.subSlots.add(new Region<>(this.originX + $$1, this.originY, $$3, this.height));
                    } else {
                        this.subSlots.add(new Region<>(this.originX + $$1, this.originY, $$3, $$2));
                        this.subSlots.add(new Region<>(this.originX, this.originY + $$2, this.width, $$4));
                    }
                } else if ($$3 == 0) {
                    this.subSlots.add(new Region<>(this.originX, this.originY + $$2, $$1, $$4));
                } else if ($$4 == 0) {
                    this.subSlots.add(new Region<>(this.originX + $$1, this.originY, $$3, $$2));
                }
            }
            for (Region<T> $$7 : this.subSlots) {
                if ($$7.add($$0)) {
                    return true;
                }
            }
            return false;
        }

        public void walk(SpriteLoader<T> $$0, int $$1) {
            if (this.holder != null) {
                $$0.load(((Holder) this.holder).entry, getX(), getY(), $$1);
            } else if (this.subSlots != null) {
                for (Region<T> $$2 : this.subSlots) {
                    $$2.walk($$0, $$1);
                }
            }
        }

        public String toString() {
            return "Slot{originX=" + this.originX + ", originY=" + this.originY + ", width=" + this.width + ", height=" + this.height + ", texture=" + String.valueOf(this.holder) + ", subSlots=" + String.valueOf(this.subSlots) + "}";
        }
    }
}
