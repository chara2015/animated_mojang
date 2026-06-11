package net.minecraft.world.item.crafting.display;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.util.context.ContextMap;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.SmithingTrimRecipe;
import net.minecraft.world.item.crafting.display.DisplayContentsFactory;
import net.minecraft.world.item.equipment.trim.TrimPattern;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import net.minecraft.world.level.block.entity.FuelValues;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/crafting/display/SlotDisplay.class */
public interface SlotDisplay {
    public static final Codec<SlotDisplay> CODEC = BuiltInRegistries.SLOT_DISPLAY.byNameCodec().dispatch((v0) -> {
        return v0.type();
    }, (v0) -> {
        return v0.codec();
    });
    public static final StreamCodec<RegistryFriendlyByteBuf, SlotDisplay> STREAM_CODEC = ByteBufCodecs.registry(Registries.SLOT_DISPLAY).dispatch((v0) -> {
        return v0.type();
    }, (v0) -> {
        return v0.streamCodec();
    });

    <T> Stream<T> resolve(ContextMap contextMap, DisplayContentsFactory<T> displayContentsFactory);

    Type<? extends SlotDisplay> type();

    default boolean isEnabled(FeatureFlagSet $$0) {
        return true;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/crafting/display/SlotDisplay$Type.class */
    public static final class Type<T extends SlotDisplay> extends Record {
        private final MapCodec<T> codec;
        private final StreamCodec<RegistryFriendlyByteBuf, T> streamCodec;

        public Type(MapCodec<T> $$0, StreamCodec<RegistryFriendlyByteBuf, T> $$1) {
            this.codec = $$0;
            this.streamCodec = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Type.class), Type.class, "codec;streamCodec", "FIELD:Lnet/minecraft/world/item/crafting/display/SlotDisplay$Type;->codec:Lcom/mojang/serialization/MapCodec;", "FIELD:Lnet/minecraft/world/item/crafting/display/SlotDisplay$Type;->streamCodec:Lnet/minecraft/network/codec/StreamCodec;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Type.class), Type.class, "codec;streamCodec", "FIELD:Lnet/minecraft/world/item/crafting/display/SlotDisplay$Type;->codec:Lcom/mojang/serialization/MapCodec;", "FIELD:Lnet/minecraft/world/item/crafting/display/SlotDisplay$Type;->streamCodec:Lnet/minecraft/network/codec/StreamCodec;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Type.class, Object.class), Type.class, "codec;streamCodec", "FIELD:Lnet/minecraft/world/item/crafting/display/SlotDisplay$Type;->codec:Lcom/mojang/serialization/MapCodec;", "FIELD:Lnet/minecraft/world/item/crafting/display/SlotDisplay$Type;->streamCodec:Lnet/minecraft/network/codec/StreamCodec;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public MapCodec<T> codec() {
            return this.codec;
        }

        public StreamCodec<RegistryFriendlyByteBuf, T> streamCodec() {
            return this.streamCodec;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/crafting/display/SlotDisplay$ItemStackContentsFactory.class */
    public static class ItemStackContentsFactory implements DisplayContentsFactory.ForStacks<ItemStack> {
        public static final ItemStackContentsFactory INSTANCE = new ItemStackContentsFactory();

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // net.minecraft.world.item.crafting.display.DisplayContentsFactory.ForStacks
        public ItemStack forStack(ItemStack $$0) {
            return $$0;
        }
    }

    default List<ItemStack> resolveForStacks(ContextMap $$0) {
        return resolve($$0, ItemStackContentsFactory.INSTANCE).toList();
    }

    default ItemStack resolveForFirstStack(ContextMap $$0) {
        return (ItemStack) resolve($$0, ItemStackContentsFactory.INSTANCE).findFirst().orElse(ItemStack.EMPTY);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/crafting/display/SlotDisplay$Empty.class */
    public static class Empty implements SlotDisplay {
        public static final Empty INSTANCE = new Empty();
        public static final MapCodec<Empty> MAP_CODEC = MapCodec.unit(INSTANCE);
        public static final StreamCodec<RegistryFriendlyByteBuf, Empty> STREAM_CODEC = StreamCodec.unit(INSTANCE);
        public static final Type<Empty> TYPE = new Type<>(MAP_CODEC, STREAM_CODEC);

        private Empty() {
        }

        @Override // net.minecraft.world.item.crafting.display.SlotDisplay
        public Type<Empty> type() {
            return TYPE;
        }

        public String toString() {
            return "<empty>";
        }

        @Override // net.minecraft.world.item.crafting.display.SlotDisplay
        public <T> Stream<T> resolve(ContextMap $$0, DisplayContentsFactory<T> $$1) {
            return Stream.empty();
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/crafting/display/SlotDisplay$AnyFuel.class */
    public static class AnyFuel implements SlotDisplay {
        public static final AnyFuel INSTANCE = new AnyFuel();
        public static final MapCodec<AnyFuel> MAP_CODEC = MapCodec.unit(INSTANCE);
        public static final StreamCodec<RegistryFriendlyByteBuf, AnyFuel> STREAM_CODEC = StreamCodec.unit(INSTANCE);
        public static final Type<AnyFuel> TYPE = new Type<>(MAP_CODEC, STREAM_CODEC);

        private AnyFuel() {
        }

        @Override // net.minecraft.world.item.crafting.display.SlotDisplay
        public Type<AnyFuel> type() {
            return TYPE;
        }

        public String toString() {
            return "<any fuel>";
        }

        @Override // net.minecraft.world.item.crafting.display.SlotDisplay
        public <T> Stream<T> resolve(ContextMap $$0, DisplayContentsFactory<T> $$1) {
            if ($$1 instanceof DisplayContentsFactory.ForStacks) {
                DisplayContentsFactory.ForStacks<T> $$2 = (DisplayContentsFactory.ForStacks) $$1;
                FuelValues $$3 = (FuelValues) $$0.getOptional(SlotDisplayContext.FUEL_VALUES);
                if ($$3 != null) {
                    Stream stream = $$3.fuelItems().stream();
                    Objects.requireNonNull($$2);
                    return stream.map($$2::forStack);
                }
            }
            return Stream.empty();
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/crafting/display/SlotDisplay$SmithingTrimDemoSlotDisplay.class */
    public static final class SmithingTrimDemoSlotDisplay extends Record implements SlotDisplay {
        private final SlotDisplay base;
        private final SlotDisplay material;
        private final Holder<TrimPattern> pattern;
        public static final MapCodec<SmithingTrimDemoSlotDisplay> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
            return $$0.group(SlotDisplay.CODEC.fieldOf("base").forGetter((v0) -> {
                return v0.base();
            }), SlotDisplay.CODEC.fieldOf("material").forGetter((v0) -> {
                return v0.material();
            }), TrimPattern.CODEC.fieldOf("pattern").forGetter((v0) -> {
                return v0.pattern();
            })).apply($$0, SmithingTrimDemoSlotDisplay::new);
        });
        public static final StreamCodec<RegistryFriendlyByteBuf, SmithingTrimDemoSlotDisplay> STREAM_CODEC = StreamCodec.composite(SlotDisplay.STREAM_CODEC, (v0) -> {
            return v0.base();
        }, SlotDisplay.STREAM_CODEC, (v0) -> {
            return v0.material();
        }, TrimPattern.STREAM_CODEC, (v0) -> {
            return v0.pattern();
        }, SmithingTrimDemoSlotDisplay::new);
        public static final Type<SmithingTrimDemoSlotDisplay> TYPE = new Type<>(MAP_CODEC, STREAM_CODEC);

        public SmithingTrimDemoSlotDisplay(SlotDisplay $$0, SlotDisplay $$1, Holder<TrimPattern> $$2) {
            this.base = $$0;
            this.material = $$1;
            this.pattern = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, SmithingTrimDemoSlotDisplay.class), SmithingTrimDemoSlotDisplay.class, "base;material;pattern", "FIELD:Lnet/minecraft/world/item/crafting/display/SlotDisplay$SmithingTrimDemoSlotDisplay;->base:Lnet/minecraft/world/item/crafting/display/SlotDisplay;", "FIELD:Lnet/minecraft/world/item/crafting/display/SlotDisplay$SmithingTrimDemoSlotDisplay;->material:Lnet/minecraft/world/item/crafting/display/SlotDisplay;", "FIELD:Lnet/minecraft/world/item/crafting/display/SlotDisplay$SmithingTrimDemoSlotDisplay;->pattern:Lnet/minecraft/core/Holder;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, SmithingTrimDemoSlotDisplay.class), SmithingTrimDemoSlotDisplay.class, "base;material;pattern", "FIELD:Lnet/minecraft/world/item/crafting/display/SlotDisplay$SmithingTrimDemoSlotDisplay;->base:Lnet/minecraft/world/item/crafting/display/SlotDisplay;", "FIELD:Lnet/minecraft/world/item/crafting/display/SlotDisplay$SmithingTrimDemoSlotDisplay;->material:Lnet/minecraft/world/item/crafting/display/SlotDisplay;", "FIELD:Lnet/minecraft/world/item/crafting/display/SlotDisplay$SmithingTrimDemoSlotDisplay;->pattern:Lnet/minecraft/core/Holder;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, SmithingTrimDemoSlotDisplay.class, Object.class), SmithingTrimDemoSlotDisplay.class, "base;material;pattern", "FIELD:Lnet/minecraft/world/item/crafting/display/SlotDisplay$SmithingTrimDemoSlotDisplay;->base:Lnet/minecraft/world/item/crafting/display/SlotDisplay;", "FIELD:Lnet/minecraft/world/item/crafting/display/SlotDisplay$SmithingTrimDemoSlotDisplay;->material:Lnet/minecraft/world/item/crafting/display/SlotDisplay;", "FIELD:Lnet/minecraft/world/item/crafting/display/SlotDisplay$SmithingTrimDemoSlotDisplay;->pattern:Lnet/minecraft/core/Holder;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public SlotDisplay base() {
            return this.base;
        }

        public SlotDisplay material() {
            return this.material;
        }

        public Holder<TrimPattern> pattern() {
            return this.pattern;
        }

        @Override // net.minecraft.world.item.crafting.display.SlotDisplay
        public Type<SmithingTrimDemoSlotDisplay> type() {
            return TYPE;
        }

        @Override // net.minecraft.world.item.crafting.display.SlotDisplay
        public <T> Stream<T> resolve(ContextMap contextMap, DisplayContentsFactory<T> displayContentsFactory) {
            if (displayContentsFactory instanceof DisplayContentsFactory.ForStacks) {
                DisplayContentsFactory.ForStacks forStacks = (DisplayContentsFactory.ForStacks) displayContentsFactory;
                HolderLookup.Provider provider = (HolderLookup.Provider) contextMap.getOptional(SlotDisplayContext.REGISTRIES);
                if (provider != null) {
                    RandomSource randomSourceCreate = RandomSource.create(System.identityHashCode(this));
                    List<ItemStack> listResolveForStacks = this.base.resolveForStacks(contextMap);
                    if (listResolveForStacks.isEmpty()) {
                        return Stream.empty();
                    }
                    List<ItemStack> listResolveForStacks2 = this.material.resolveForStacks(contextMap);
                    if (listResolveForStacks2.isEmpty()) {
                        return Stream.empty();
                    }
                    Stream<T> streamLimit = Stream.generate(() -> {
                        ItemStack $$4 = (ItemStack) Util.getRandom(listResolveForStacks, randomSourceCreate);
                        ItemStack $$5 = (ItemStack) Util.getRandom(listResolveForStacks2, randomSourceCreate);
                        return SmithingTrimRecipe.applyTrim(provider, $$4, $$5, this.pattern);
                    }).limit(256L).filter($$0 -> {
                        return !$$0.isEmpty();
                    }).limit(16L);
                    Objects.requireNonNull(forStacks);
                    return (Stream<T>) streamLimit.map(forStacks::forStack);
                }
            }
            return Stream.empty();
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/crafting/display/SlotDisplay$ItemSlotDisplay.class */
    public static final class ItemSlotDisplay extends Record implements SlotDisplay {
        private final Holder<Item> item;
        public static final MapCodec<ItemSlotDisplay> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
            return $$0.group(Item.CODEC.fieldOf(DecoratedPotBlockEntity.TAG_ITEM).forGetter((v0) -> {
                return v0.item();
            })).apply($$0, ItemSlotDisplay::new);
        });
        public static final StreamCodec<RegistryFriendlyByteBuf, ItemSlotDisplay> STREAM_CODEC = StreamCodec.composite(Item.STREAM_CODEC, (v0) -> {
            return v0.item();
        }, ItemSlotDisplay::new);
        public static final Type<ItemSlotDisplay> TYPE = new Type<>(MAP_CODEC, STREAM_CODEC);

        public ItemSlotDisplay(Holder<Item> $$0) {
            this.item = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ItemSlotDisplay.class), ItemSlotDisplay.class, "item", "FIELD:Lnet/minecraft/world/item/crafting/display/SlotDisplay$ItemSlotDisplay;->item:Lnet/minecraft/core/Holder;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ItemSlotDisplay.class), ItemSlotDisplay.class, "item", "FIELD:Lnet/minecraft/world/item/crafting/display/SlotDisplay$ItemSlotDisplay;->item:Lnet/minecraft/core/Holder;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ItemSlotDisplay.class, Object.class), ItemSlotDisplay.class, "item", "FIELD:Lnet/minecraft/world/item/crafting/display/SlotDisplay$ItemSlotDisplay;->item:Lnet/minecraft/core/Holder;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Holder<Item> item() {
            return this.item;
        }

        @Override // net.minecraft.world.item.crafting.display.SlotDisplay
        public Type<ItemSlotDisplay> type() {
            return TYPE;
        }

        public ItemSlotDisplay(Item $$0) {
            this($$0.builtInRegistryHolder());
        }

        @Override // net.minecraft.world.item.crafting.display.SlotDisplay
        public <T> Stream<T> resolve(ContextMap $$0, DisplayContentsFactory<T> $$1) {
            if ($$1 instanceof DisplayContentsFactory.ForStacks) {
                DisplayContentsFactory.ForStacks<T> $$2 = (DisplayContentsFactory.ForStacks) $$1;
                return Stream.of($$2.forStack(this.item));
            }
            return Stream.empty();
        }

        @Override // net.minecraft.world.item.crafting.display.SlotDisplay
        public boolean isEnabled(FeatureFlagSet $$0) {
            return this.item.value().isEnabled($$0);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/crafting/display/SlotDisplay$ItemStackSlotDisplay.class */
    public static final class ItemStackSlotDisplay extends Record implements SlotDisplay {
        private final ItemStack stack;
        public static final MapCodec<ItemStackSlotDisplay> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
            return $$0.group(ItemStack.STRICT_CODEC.fieldOf(DecoratedPotBlockEntity.TAG_ITEM).forGetter((v0) -> {
                return v0.stack();
            })).apply($$0, ItemStackSlotDisplay::new);
        });
        public static final StreamCodec<RegistryFriendlyByteBuf, ItemStackSlotDisplay> STREAM_CODEC = StreamCodec.composite(ItemStack.STREAM_CODEC, (v0) -> {
            return v0.stack();
        }, ItemStackSlotDisplay::new);
        public static final Type<ItemStackSlotDisplay> TYPE = new Type<>(MAP_CODEC, STREAM_CODEC);

        public ItemStackSlotDisplay(ItemStack $$0) {
            this.stack = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ItemStackSlotDisplay.class), ItemStackSlotDisplay.class, "stack", "FIELD:Lnet/minecraft/world/item/crafting/display/SlotDisplay$ItemStackSlotDisplay;->stack:Lnet/minecraft/world/item/ItemStack;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ItemStackSlotDisplay.class), ItemStackSlotDisplay.class, "stack", "FIELD:Lnet/minecraft/world/item/crafting/display/SlotDisplay$ItemStackSlotDisplay;->stack:Lnet/minecraft/world/item/ItemStack;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        public ItemStack stack() {
            return this.stack;
        }

        @Override // net.minecraft.world.item.crafting.display.SlotDisplay
        public Type<ItemStackSlotDisplay> type() {
            return TYPE;
        }

        @Override // net.minecraft.world.item.crafting.display.SlotDisplay
        public <T> Stream<T> resolve(ContextMap $$0, DisplayContentsFactory<T> $$1) {
            if ($$1 instanceof DisplayContentsFactory.ForStacks) {
                DisplayContentsFactory.ForStacks<T> $$2 = (DisplayContentsFactory.ForStacks) $$1;
                return Stream.of($$2.forStack(this.stack));
            }
            return Stream.empty();
        }

        @Override // java.lang.Record
        public boolean equals(Object $$0) {
            if (this != $$0) {
                if ($$0 instanceof ItemStackSlotDisplay) {
                    ItemStackSlotDisplay $$1 = (ItemStackSlotDisplay) $$0;
                    if (ItemStack.matches(this.stack, $$1.stack)) {
                    }
                }
                return false;
            }
            return true;
        }

        @Override // net.minecraft.world.item.crafting.display.SlotDisplay
        public boolean isEnabled(FeatureFlagSet $$0) {
            return this.stack.getItem().isEnabled($$0);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/crafting/display/SlotDisplay$TagSlotDisplay.class */
    public static final class TagSlotDisplay extends Record implements SlotDisplay {
        private final TagKey<Item> tag;
        public static final MapCodec<TagSlotDisplay> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
            return $$0.group(TagKey.codec(Registries.ITEM).fieldOf("tag").forGetter((v0) -> {
                return v0.tag();
            })).apply($$0, TagSlotDisplay::new);
        });
        public static final StreamCodec<RegistryFriendlyByteBuf, TagSlotDisplay> STREAM_CODEC = StreamCodec.composite(TagKey.streamCodec(Registries.ITEM), (v0) -> {
            return v0.tag();
        }, TagSlotDisplay::new);
        public static final Type<TagSlotDisplay> TYPE = new Type<>(MAP_CODEC, STREAM_CODEC);

        public TagSlotDisplay(TagKey<Item> $$0) {
            this.tag = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, TagSlotDisplay.class), TagSlotDisplay.class, "tag", "FIELD:Lnet/minecraft/world/item/crafting/display/SlotDisplay$TagSlotDisplay;->tag:Lnet/minecraft/tags/TagKey;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, TagSlotDisplay.class), TagSlotDisplay.class, "tag", "FIELD:Lnet/minecraft/world/item/crafting/display/SlotDisplay$TagSlotDisplay;->tag:Lnet/minecraft/tags/TagKey;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, TagSlotDisplay.class, Object.class), TagSlotDisplay.class, "tag", "FIELD:Lnet/minecraft/world/item/crafting/display/SlotDisplay$TagSlotDisplay;->tag:Lnet/minecraft/tags/TagKey;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public TagKey<Item> tag() {
            return this.tag;
        }

        @Override // net.minecraft.world.item.crafting.display.SlotDisplay
        public Type<TagSlotDisplay> type() {
            return TYPE;
        }

        @Override // net.minecraft.world.item.crafting.display.SlotDisplay
        public <T> Stream<T> resolve(ContextMap contextMap, DisplayContentsFactory<T> displayContentsFactory) {
            if (displayContentsFactory instanceof DisplayContentsFactory.ForStacks) {
                DisplayContentsFactory.ForStacks forStacks = (DisplayContentsFactory.ForStacks) displayContentsFactory;
                HolderLookup.Provider provider = (HolderLookup.Provider) contextMap.getOptional(SlotDisplayContext.REGISTRIES);
                if (provider != null) {
                    return (Stream<T>) provider.lookupOrThrow((ResourceKey) Registries.ITEM).get(this.tag).map($$1 -> {
                        Stream<Holder<T>> stream = $$1.stream();
                        Objects.requireNonNull(forStacks);
                        return stream.map(forStacks::forStack);
                    }).stream().flatMap($$0 -> {
                        return $$0;
                    });
                }
            }
            return Stream.empty();
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/crafting/display/SlotDisplay$Composite.class */
    public static final class Composite extends Record implements SlotDisplay {
        private final List<SlotDisplay> contents;
        public static final MapCodec<Composite> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
            return $$0.group(SlotDisplay.CODEC.listOf().fieldOf("contents").forGetter((v0) -> {
                return v0.contents();
            })).apply($$0, Composite::new);
        });
        public static final StreamCodec<RegistryFriendlyByteBuf, Composite> STREAM_CODEC = StreamCodec.composite(SlotDisplay.STREAM_CODEC.apply(ByteBufCodecs.list()), (v0) -> {
            return v0.contents();
        }, Composite::new);
        public static final Type<Composite> TYPE = new Type<>(MAP_CODEC, STREAM_CODEC);

        public Composite(List<SlotDisplay> $$0) {
            this.contents = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Composite.class), Composite.class, "contents", "FIELD:Lnet/minecraft/world/item/crafting/display/SlotDisplay$Composite;->contents:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Composite.class), Composite.class, "contents", "FIELD:Lnet/minecraft/world/item/crafting/display/SlotDisplay$Composite;->contents:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Composite.class, Object.class), Composite.class, "contents", "FIELD:Lnet/minecraft/world/item/crafting/display/SlotDisplay$Composite;->contents:Ljava/util/List;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public List<SlotDisplay> contents() {
            return this.contents;
        }

        @Override // net.minecraft.world.item.crafting.display.SlotDisplay
        public Type<Composite> type() {
            return TYPE;
        }

        @Override // net.minecraft.world.item.crafting.display.SlotDisplay
        public <T> Stream<T> resolve(ContextMap contextMap, DisplayContentsFactory<T> displayContentsFactory) {
            return (Stream<T>) this.contents.stream().flatMap($$2 -> {
                return $$2.resolve(contextMap, displayContentsFactory);
            });
        }

        @Override // net.minecraft.world.item.crafting.display.SlotDisplay
        public boolean isEnabled(FeatureFlagSet $$0) {
            return this.contents.stream().allMatch($$1 -> {
                return $$1.isEnabled($$0);
            });
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/crafting/display/SlotDisplay$WithRemainder.class */
    public static final class WithRemainder extends Record implements SlotDisplay {
        private final SlotDisplay input;
        private final SlotDisplay remainder;
        public static final MapCodec<WithRemainder> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
            return $$0.group(SlotDisplay.CODEC.fieldOf("input").forGetter((v0) -> {
                return v0.input();
            }), SlotDisplay.CODEC.fieldOf("remainder").forGetter((v0) -> {
                return v0.remainder();
            })).apply($$0, WithRemainder::new);
        });
        public static final StreamCodec<RegistryFriendlyByteBuf, WithRemainder> STREAM_CODEC = StreamCodec.composite(SlotDisplay.STREAM_CODEC, (v0) -> {
            return v0.input();
        }, SlotDisplay.STREAM_CODEC, (v0) -> {
            return v0.remainder();
        }, WithRemainder::new);
        public static final Type<WithRemainder> TYPE = new Type<>(MAP_CODEC, STREAM_CODEC);

        public WithRemainder(SlotDisplay $$0, SlotDisplay $$1) {
            this.input = $$0;
            this.remainder = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, WithRemainder.class), WithRemainder.class, "input;remainder", "FIELD:Lnet/minecraft/world/item/crafting/display/SlotDisplay$WithRemainder;->input:Lnet/minecraft/world/item/crafting/display/SlotDisplay;", "FIELD:Lnet/minecraft/world/item/crafting/display/SlotDisplay$WithRemainder;->remainder:Lnet/minecraft/world/item/crafting/display/SlotDisplay;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, WithRemainder.class), WithRemainder.class, "input;remainder", "FIELD:Lnet/minecraft/world/item/crafting/display/SlotDisplay$WithRemainder;->input:Lnet/minecraft/world/item/crafting/display/SlotDisplay;", "FIELD:Lnet/minecraft/world/item/crafting/display/SlotDisplay$WithRemainder;->remainder:Lnet/minecraft/world/item/crafting/display/SlotDisplay;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, WithRemainder.class, Object.class), WithRemainder.class, "input;remainder", "FIELD:Lnet/minecraft/world/item/crafting/display/SlotDisplay$WithRemainder;->input:Lnet/minecraft/world/item/crafting/display/SlotDisplay;", "FIELD:Lnet/minecraft/world/item/crafting/display/SlotDisplay$WithRemainder;->remainder:Lnet/minecraft/world/item/crafting/display/SlotDisplay;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public SlotDisplay input() {
            return this.input;
        }

        public SlotDisplay remainder() {
            return this.remainder;
        }

        @Override // net.minecraft.world.item.crafting.display.SlotDisplay
        public Type<WithRemainder> type() {
            return TYPE;
        }

        @Override // net.minecraft.world.item.crafting.display.SlotDisplay
        public <T> Stream<T> resolve(ContextMap contextMap, DisplayContentsFactory<T> displayContentsFactory) {
            if (displayContentsFactory instanceof DisplayContentsFactory.ForRemainders) {
                DisplayContentsFactory.ForRemainders forRemainders = (DisplayContentsFactory.ForRemainders) displayContentsFactory;
                List<T> list = this.remainder.resolve(contextMap, displayContentsFactory).toList();
                return (Stream<T>) this.input.resolve(contextMap, displayContentsFactory).map($$2 -> {
                    return forRemainders.addRemainder($$2, list);
                });
            }
            return this.input.resolve(contextMap, displayContentsFactory);
        }

        @Override // net.minecraft.world.item.crafting.display.SlotDisplay
        public boolean isEnabled(FeatureFlagSet $$0) {
            return this.input.isEnabled($$0) && this.remainder.isEnabled($$0);
        }
    }
}
