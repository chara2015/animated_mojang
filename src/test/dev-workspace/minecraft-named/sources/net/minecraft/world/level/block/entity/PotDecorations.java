package net.minecraft.world.level.block.entity;

import com.mojang.serialization.Codec;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/entity/PotDecorations.class */
public final class PotDecorations extends Record implements TooltipProvider {
    private final Optional<Item> back;
    private final Optional<Item> left;
    private final Optional<Item> right;
    private final Optional<Item> front;
    public static final PotDecorations EMPTY = new PotDecorations((Optional<Item>) Optional.empty(), (Optional<Item>) Optional.empty(), (Optional<Item>) Optional.empty(), (Optional<Item>) Optional.empty());
    public static final Codec<PotDecorations> CODEC = BuiltInRegistries.ITEM.byNameCodec().sizeLimitedListOf(4).xmap(PotDecorations::new, (v0) -> {
        return v0.ordered();
    });
    public static final StreamCodec<RegistryFriendlyByteBuf, PotDecorations> STREAM_CODEC = ByteBufCodecs.registry(Registries.ITEM).apply(ByteBufCodecs.list(4)).map(PotDecorations::new, (v0) -> {
        return v0.ordered();
    });

    public PotDecorations(Optional<Item> $$0, Optional<Item> $$1, Optional<Item> $$2, Optional<Item> $$3) {
        this.back = $$0;
        this.left = $$1;
        this.right = $$2;
        this.front = $$3;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, PotDecorations.class), PotDecorations.class, "back;left;right;front", "FIELD:Lnet/minecraft/world/level/block/entity/PotDecorations;->back:Ljava/util/Optional;", "FIELD:Lnet/minecraft/world/level/block/entity/PotDecorations;->left:Ljava/util/Optional;", "FIELD:Lnet/minecraft/world/level/block/entity/PotDecorations;->right:Ljava/util/Optional;", "FIELD:Lnet/minecraft/world/level/block/entity/PotDecorations;->front:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, PotDecorations.class), PotDecorations.class, "back;left;right;front", "FIELD:Lnet/minecraft/world/level/block/entity/PotDecorations;->back:Ljava/util/Optional;", "FIELD:Lnet/minecraft/world/level/block/entity/PotDecorations;->left:Ljava/util/Optional;", "FIELD:Lnet/minecraft/world/level/block/entity/PotDecorations;->right:Ljava/util/Optional;", "FIELD:Lnet/minecraft/world/level/block/entity/PotDecorations;->front:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, PotDecorations.class, Object.class), PotDecorations.class, "back;left;right;front", "FIELD:Lnet/minecraft/world/level/block/entity/PotDecorations;->back:Ljava/util/Optional;", "FIELD:Lnet/minecraft/world/level/block/entity/PotDecorations;->left:Ljava/util/Optional;", "FIELD:Lnet/minecraft/world/level/block/entity/PotDecorations;->right:Ljava/util/Optional;", "FIELD:Lnet/minecraft/world/level/block/entity/PotDecorations;->front:Ljava/util/Optional;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Optional<Item> back() {
        return this.back;
    }

    public Optional<Item> left() {
        return this.left;
    }

    public Optional<Item> right() {
        return this.right;
    }

    public Optional<Item> front() {
        return this.front;
    }

    private PotDecorations(List<Item> $$0) {
        this(getItem($$0, 0), getItem($$0, 1), getItem($$0, 2), getItem($$0, 3));
    }

    public PotDecorations(Item $$0, Item $$1, Item $$2, Item $$3) {
        this(List.of($$0, $$1, $$2, $$3));
    }

    private static Optional<Item> getItem(List<Item> $$0, int $$1) {
        if ($$1 >= $$0.size()) {
            return Optional.empty();
        }
        Item $$2 = $$0.get($$1);
        return $$2 == Items.BRICK ? Optional.empty() : Optional.of($$2);
    }

    public List<Item> ordered() {
        return Stream.of((Object[]) new Optional[]{this.back, this.left, this.right, this.front}).map($$0 -> {
            return (Item) $$0.orElse(Items.BRICK);
        }).toList();
    }

    @Override // net.minecraft.world.item.component.TooltipProvider
    public void addToTooltip(Item.TooltipContext $$0, Consumer<Component> $$1, TooltipFlag $$2, DataComponentGetter $$3) {
        if (equals(EMPTY)) {
            return;
        }
        $$1.accept(CommonComponents.EMPTY);
        addSideDetailsToTooltip($$1, this.front);
        addSideDetailsToTooltip($$1, this.left);
        addSideDetailsToTooltip($$1, this.right);
        addSideDetailsToTooltip($$1, this.back);
    }

    private static void addSideDetailsToTooltip(Consumer<Component> $$0, Optional<Item> $$1) {
        $$0.accept(new ItemStack($$1.orElse(Items.BRICK), 1).getHoverName().plainCopy().withStyle(ChatFormatting.GRAY));
    }
}
