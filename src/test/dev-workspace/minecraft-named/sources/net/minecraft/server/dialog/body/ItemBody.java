package net.minecraft.server.dialog.body;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Optional;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.Display;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/dialog/body/ItemBody.class */
public final class ItemBody extends Record implements DialogBody {
    private final ItemStack item;
    private final Optional<PlainMessage> description;
    private final boolean showDecorations;
    private final boolean showTooltip;
    private final int width;
    private final int height;
    public static final MapCodec<ItemBody> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(ItemStack.STRICT_CODEC.fieldOf(DecoratedPotBlockEntity.TAG_ITEM).forGetter((v0) -> {
            return v0.item();
        }), PlainMessage.CODEC.optionalFieldOf("description").forGetter((v0) -> {
            return v0.description();
        }), Codec.BOOL.optionalFieldOf("show_decorations", true).forGetter((v0) -> {
            return v0.showDecorations();
        }), Codec.BOOL.optionalFieldOf("show_tooltip", true).forGetter((v0) -> {
            return v0.showTooltip();
        }), ExtraCodecs.intRange(1, 256).optionalFieldOf(Display.TAG_WIDTH, 16).forGetter((v0) -> {
            return v0.width();
        }), ExtraCodecs.intRange(1, 256).optionalFieldOf(Display.TAG_HEIGHT, 16).forGetter((v0) -> {
            return v0.height();
        })).apply($$0, (v1, v2, v3, v4, v5, v6) -> {
            return new ItemBody(v1, v2, v3, v4, v5, v6);
        });
    });

    public ItemBody(ItemStack $$0, Optional<PlainMessage> $$1, boolean $$2, boolean $$3, int $$4, int $$5) {
        this.item = $$0;
        this.description = $$1;
        this.showDecorations = $$2;
        this.showTooltip = $$3;
        this.width = $$4;
        this.height = $$5;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ItemBody.class), ItemBody.class, "item;description;showDecorations;showTooltip;width;height", "FIELD:Lnet/minecraft/server/dialog/body/ItemBody;->item:Lnet/minecraft/world/item/ItemStack;", "FIELD:Lnet/minecraft/server/dialog/body/ItemBody;->description:Ljava/util/Optional;", "FIELD:Lnet/minecraft/server/dialog/body/ItemBody;->showDecorations:Z", "FIELD:Lnet/minecraft/server/dialog/body/ItemBody;->showTooltip:Z", "FIELD:Lnet/minecraft/server/dialog/body/ItemBody;->width:I", "FIELD:Lnet/minecraft/server/dialog/body/ItemBody;->height:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ItemBody.class), ItemBody.class, "item;description;showDecorations;showTooltip;width;height", "FIELD:Lnet/minecraft/server/dialog/body/ItemBody;->item:Lnet/minecraft/world/item/ItemStack;", "FIELD:Lnet/minecraft/server/dialog/body/ItemBody;->description:Ljava/util/Optional;", "FIELD:Lnet/minecraft/server/dialog/body/ItemBody;->showDecorations:Z", "FIELD:Lnet/minecraft/server/dialog/body/ItemBody;->showTooltip:Z", "FIELD:Lnet/minecraft/server/dialog/body/ItemBody;->width:I", "FIELD:Lnet/minecraft/server/dialog/body/ItemBody;->height:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ItemBody.class, Object.class), ItemBody.class, "item;description;showDecorations;showTooltip;width;height", "FIELD:Lnet/minecraft/server/dialog/body/ItemBody;->item:Lnet/minecraft/world/item/ItemStack;", "FIELD:Lnet/minecraft/server/dialog/body/ItemBody;->description:Ljava/util/Optional;", "FIELD:Lnet/minecraft/server/dialog/body/ItemBody;->showDecorations:Z", "FIELD:Lnet/minecraft/server/dialog/body/ItemBody;->showTooltip:Z", "FIELD:Lnet/minecraft/server/dialog/body/ItemBody;->width:I", "FIELD:Lnet/minecraft/server/dialog/body/ItemBody;->height:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public ItemStack item() {
        return this.item;
    }

    public Optional<PlainMessage> description() {
        return this.description;
    }

    public boolean showDecorations() {
        return this.showDecorations;
    }

    public boolean showTooltip() {
        return this.showTooltip;
    }

    public int width() {
        return this.width;
    }

    public int height() {
        return this.height;
    }

    @Override // net.minecraft.server.dialog.body.DialogBody
    public MapCodec<ItemBody> mapCodec() {
        return MAP_CODEC;
    }
}
