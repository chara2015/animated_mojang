package net.minecraft.world.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ReferenceLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ReferenceSortedSets;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import java.util.SequencedSet;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/component/TooltipDisplay.class */
public final class TooltipDisplay extends Record {
    private final boolean hideTooltip;
    private final SequencedSet<DataComponentType<?>> hiddenComponents;
    private static final Codec<SequencedSet<DataComponentType<?>>> COMPONENT_SET_CODEC = DataComponentType.CODEC.listOf().xmap((v1) -> {
        return new ReferenceLinkedOpenHashSet(v1);
    }, (v0) -> {
        return List.copyOf(v0);
    });
    public static final Codec<TooltipDisplay> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(Codec.BOOL.optionalFieldOf("hide_tooltip", false).forGetter((v0) -> {
            return v0.hideTooltip();
        }), COMPONENT_SET_CODEC.optionalFieldOf("hidden_components", ReferenceSortedSets.emptySet()).forGetter((v0) -> {
            return v0.hiddenComponents();
        })).apply($$0, (v1, v2) -> {
            return new TooltipDisplay(v1, v2);
        });
    });
    public static final StreamCodec<RegistryFriendlyByteBuf, TooltipDisplay> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.BOOL, (v0) -> {
        return v0.hideTooltip();
    }, DataComponentType.STREAM_CODEC.apply(ByteBufCodecs.collection(ReferenceLinkedOpenHashSet::new)), (v0) -> {
        return v0.hiddenComponents();
    }, (v1, v2) -> {
        return new TooltipDisplay(v1, v2);
    });
    public static final TooltipDisplay DEFAULT = new TooltipDisplay(false, ReferenceSortedSets.emptySet());

    public TooltipDisplay(boolean $$0, SequencedSet<DataComponentType<?>> $$1) {
        this.hideTooltip = $$0;
        this.hiddenComponents = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, TooltipDisplay.class), TooltipDisplay.class, "hideTooltip;hiddenComponents", "FIELD:Lnet/minecraft/world/item/component/TooltipDisplay;->hideTooltip:Z", "FIELD:Lnet/minecraft/world/item/component/TooltipDisplay;->hiddenComponents:Ljava/util/SequencedSet;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, TooltipDisplay.class), TooltipDisplay.class, "hideTooltip;hiddenComponents", "FIELD:Lnet/minecraft/world/item/component/TooltipDisplay;->hideTooltip:Z", "FIELD:Lnet/minecraft/world/item/component/TooltipDisplay;->hiddenComponents:Ljava/util/SequencedSet;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, TooltipDisplay.class, Object.class), TooltipDisplay.class, "hideTooltip;hiddenComponents", "FIELD:Lnet/minecraft/world/item/component/TooltipDisplay;->hideTooltip:Z", "FIELD:Lnet/minecraft/world/item/component/TooltipDisplay;->hiddenComponents:Ljava/util/SequencedSet;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public boolean hideTooltip() {
        return this.hideTooltip;
    }

    public SequencedSet<DataComponentType<?>> hiddenComponents() {
        return this.hiddenComponents;
    }

    public TooltipDisplay withHidden(DataComponentType<?> $$0, boolean $$1) {
        if (this.hiddenComponents.contains($$0) == $$1) {
            return this;
        }
        ReferenceLinkedOpenHashSet referenceLinkedOpenHashSet = new ReferenceLinkedOpenHashSet(this.hiddenComponents);
        if ($$1) {
            referenceLinkedOpenHashSet.add($$0);
        } else {
            referenceLinkedOpenHashSet.remove($$0);
        }
        return new TooltipDisplay(this.hideTooltip, referenceLinkedOpenHashSet);
    }

    public boolean shows(DataComponentType<?> $$0) {
        return (this.hideTooltip || this.hiddenComponents.contains($$0)) ? false : true;
    }
}
