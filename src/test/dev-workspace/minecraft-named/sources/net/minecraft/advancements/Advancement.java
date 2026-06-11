package net.minecraft.advancements;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.criterion.CriterionValidator;
import net.minecraft.core.ClientAsset;
import net.minecraft.core.HolderGetter;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Crypt;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/advancements/Advancement.class */
public final class Advancement extends Record {
    private final Optional<Identifier> parent;
    private final Optional<DisplayInfo> display;
    private final AdvancementRewards rewards;
    private final Map<String, Criterion<?>> criteria;
    private final AdvancementRequirements requirements;
    private final boolean sendsTelemetryEvent;
    private final Optional<Component> name;
    private static final Codec<Map<String, Criterion<?>>> CRITERIA_CODEC = Codec.unboundedMap(Codec.STRING, Criterion.CODEC).validate($$0 -> {
        return $$0.isEmpty() ? DataResult.error(() -> {
            return "Advancement criteria cannot be empty";
        }) : DataResult.success($$0);
    });
    public static final Codec<Advancement> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(Identifier.CODEC.optionalFieldOf("parent").forGetter((v0) -> {
            return v0.parent();
        }), DisplayInfo.CODEC.optionalFieldOf("display").forGetter((v0) -> {
            return v0.display();
        }), AdvancementRewards.CODEC.optionalFieldOf("rewards", AdvancementRewards.EMPTY).forGetter((v0) -> {
            return v0.rewards();
        }), CRITERIA_CODEC.fieldOf("criteria").forGetter((v0) -> {
            return v0.criteria();
        }), AdvancementRequirements.CODEC.optionalFieldOf("requirements").forGetter($$0 -> {
            return Optional.of($$0.requirements());
        }), Codec.BOOL.optionalFieldOf("sends_telemetry_event", false).forGetter((v0) -> {
            return v0.sendsTelemetryEvent();
        })).apply($$0, ($$02, $$1, $$2, $$3, $$4, $$5) -> {
            AdvancementRequirements $$6 = (AdvancementRequirements) $$4.orElseGet(() -> {
                return AdvancementRequirements.allOf($$3.keySet());
            });
            return new Advancement($$02, $$1, $$2, $$3, $$6, $$5.booleanValue());
        });
    }).validate(Advancement::validate);
    public static final StreamCodec<RegistryFriendlyByteBuf, Advancement> STREAM_CODEC = StreamCodec.ofMember((v0, v1) -> {
        v0.write(v1);
    }, Advancement::read);

    public Advancement(Optional<Identifier> $$0, Optional<DisplayInfo> $$1, AdvancementRewards $$2, Map<String, Criterion<?>> $$3, AdvancementRequirements $$4, boolean $$5, Optional<Component> $$6) {
        this.parent = $$0;
        this.display = $$1;
        this.rewards = $$2;
        this.criteria = $$3;
        this.requirements = $$4;
        this.sendsTelemetryEvent = $$5;
        this.name = $$6;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Advancement.class), Advancement.class, "parent;display;rewards;criteria;requirements;sendsTelemetryEvent;name", "FIELD:Lnet/minecraft/advancements/Advancement;->parent:Ljava/util/Optional;", "FIELD:Lnet/minecraft/advancements/Advancement;->display:Ljava/util/Optional;", "FIELD:Lnet/minecraft/advancements/Advancement;->rewards:Lnet/minecraft/advancements/AdvancementRewards;", "FIELD:Lnet/minecraft/advancements/Advancement;->criteria:Ljava/util/Map;", "FIELD:Lnet/minecraft/advancements/Advancement;->requirements:Lnet/minecraft/advancements/AdvancementRequirements;", "FIELD:Lnet/minecraft/advancements/Advancement;->sendsTelemetryEvent:Z", "FIELD:Lnet/minecraft/advancements/Advancement;->name:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Advancement.class), Advancement.class, "parent;display;rewards;criteria;requirements;sendsTelemetryEvent;name", "FIELD:Lnet/minecraft/advancements/Advancement;->parent:Ljava/util/Optional;", "FIELD:Lnet/minecraft/advancements/Advancement;->display:Ljava/util/Optional;", "FIELD:Lnet/minecraft/advancements/Advancement;->rewards:Lnet/minecraft/advancements/AdvancementRewards;", "FIELD:Lnet/minecraft/advancements/Advancement;->criteria:Ljava/util/Map;", "FIELD:Lnet/minecraft/advancements/Advancement;->requirements:Lnet/minecraft/advancements/AdvancementRequirements;", "FIELD:Lnet/minecraft/advancements/Advancement;->sendsTelemetryEvent:Z", "FIELD:Lnet/minecraft/advancements/Advancement;->name:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Advancement.class, Object.class), Advancement.class, "parent;display;rewards;criteria;requirements;sendsTelemetryEvent;name", "FIELD:Lnet/minecraft/advancements/Advancement;->parent:Ljava/util/Optional;", "FIELD:Lnet/minecraft/advancements/Advancement;->display:Ljava/util/Optional;", "FIELD:Lnet/minecraft/advancements/Advancement;->rewards:Lnet/minecraft/advancements/AdvancementRewards;", "FIELD:Lnet/minecraft/advancements/Advancement;->criteria:Ljava/util/Map;", "FIELD:Lnet/minecraft/advancements/Advancement;->requirements:Lnet/minecraft/advancements/AdvancementRequirements;", "FIELD:Lnet/minecraft/advancements/Advancement;->sendsTelemetryEvent:Z", "FIELD:Lnet/minecraft/advancements/Advancement;->name:Ljava/util/Optional;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Optional<Identifier> parent() {
        return this.parent;
    }

    public Optional<DisplayInfo> display() {
        return this.display;
    }

    public AdvancementRewards rewards() {
        return this.rewards;
    }

    public Map<String, Criterion<?>> criteria() {
        return this.criteria;
    }

    public AdvancementRequirements requirements() {
        return this.requirements;
    }

    public boolean sendsTelemetryEvent() {
        return this.sendsTelemetryEvent;
    }

    public Optional<Component> name() {
        return this.name;
    }

    private static DataResult<Advancement> validate(Advancement $$0) {
        return $$0.requirements().validate($$0.criteria().keySet()).map($$1 -> {
            return $$0;
        });
    }

    public Advancement(Optional<Identifier> $$0, Optional<DisplayInfo> $$1, AdvancementRewards $$2, Map<String, Criterion<?>> $$3, AdvancementRequirements $$4, boolean $$5) {
        this($$0, $$1, $$2, Map.copyOf($$3), $$4, $$5, $$1.map(Advancement::decorateName));
    }

    private static Component decorateName(DisplayInfo $$0) {
        Component $$1 = $$0.getTitle();
        ChatFormatting $$2 = $$0.getType().getChatColor();
        Component $$3 = ComponentUtils.mergeStyles($$1.copy(), Style.EMPTY.withColor($$2)).append(Crypt.MIME_LINE_SEPARATOR).append($$0.getDescription());
        Component $$4 = $$1.copy().withStyle($$12 -> {
            return $$12.withHoverEvent(new HoverEvent.ShowText($$3));
        });
        return ComponentUtils.wrapInSquareBrackets($$4).withStyle($$2);
    }

    public static Component name(AdvancementHolder $$0) {
        return $$0.value().name().orElseGet(() -> {
            return Component.literal($$0.id().toString());
        });
    }

    private void write(RegistryFriendlyByteBuf $$0) {
        $$0.writeOptional(this.parent, (v0, v1) -> {
            v0.writeIdentifier(v1);
        });
        DisplayInfo.STREAM_CODEC.apply(ByteBufCodecs::optional).encode($$0, this.display);
        this.requirements.write($$0);
        $$0.m1594writeBoolean(this.sendsTelemetryEvent);
    }

    private static Advancement read(RegistryFriendlyByteBuf $$0) {
        return new Advancement($$0.readOptional((v0) -> {
            return v0.readIdentifier();
        }), (Optional) DisplayInfo.STREAM_CODEC.apply(ByteBufCodecs::optional).decode($$0), AdvancementRewards.EMPTY, Map.of(), new AdvancementRequirements($$0), $$0.readBoolean());
    }

    public boolean isRoot() {
        return this.parent.isEmpty();
    }

    public void validate(ProblemReporter $$0, HolderGetter.Provider $$1) {
        this.criteria.forEach(($$2, $$3) -> {
            CriterionValidator $$4 = new CriterionValidator($$0.forChild(new ProblemReporter.RootFieldPathElement($$2)), $$1);
            $$3.triggerInstance().validate($$4);
        });
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/advancements/Advancement$Builder.class */
    public static class Builder {
        private Optional<Identifier> parent = Optional.empty();
        private Optional<DisplayInfo> display = Optional.empty();
        private AdvancementRewards rewards = AdvancementRewards.EMPTY;
        private final ImmutableMap.Builder<String, Criterion<?>> criteria = ImmutableMap.builder();
        private Optional<AdvancementRequirements> requirements = Optional.empty();
        private AdvancementRequirements.Strategy requirementsStrategy = AdvancementRequirements.Strategy.AND;
        private boolean sendsTelemetryEvent;

        public static Builder advancement() {
            return new Builder().sendsTelemetryEvent();
        }

        public static Builder recipeAdvancement() {
            return new Builder();
        }

        public Builder parent(AdvancementHolder $$0) {
            this.parent = Optional.of($$0.id());
            return this;
        }

        @Deprecated(forRemoval = true)
        public Builder parent(Identifier $$0) {
            this.parent = Optional.of($$0);
            return this;
        }

        public Builder display(ItemStack $$0, Component $$1, Component $$2, Identifier $$3, AdvancementType $$4, boolean $$5, boolean $$6, boolean $$7) {
            return display(new DisplayInfo($$0, $$1, $$2, Optional.ofNullable($$3).map(ClientAsset.ResourceTexture::new), $$4, $$5, $$6, $$7));
        }

        public Builder display(ItemLike $$0, Component $$1, Component $$2, Identifier $$3, AdvancementType $$4, boolean $$5, boolean $$6, boolean $$7) {
            return display(new DisplayInfo(new ItemStack($$0.asItem()), $$1, $$2, Optional.ofNullable($$3).map(ClientAsset.ResourceTexture::new), $$4, $$5, $$6, $$7));
        }

        public Builder display(DisplayInfo $$0) {
            this.display = Optional.of($$0);
            return this;
        }

        public Builder rewards(AdvancementRewards.Builder $$0) {
            return rewards($$0.build());
        }

        public Builder rewards(AdvancementRewards $$0) {
            this.rewards = $$0;
            return this;
        }

        public Builder addCriterion(String $$0, Criterion<?> $$1) {
            this.criteria.put($$0, $$1);
            return this;
        }

        public Builder requirements(AdvancementRequirements.Strategy $$0) {
            this.requirementsStrategy = $$0;
            return this;
        }

        public Builder requirements(AdvancementRequirements $$0) {
            this.requirements = Optional.of($$0);
            return this;
        }

        public Builder sendsTelemetryEvent() {
            this.sendsTelemetryEvent = true;
            return this;
        }

        public AdvancementHolder build(Identifier $$0) {
            ImmutableMap immutableMapBuildOrThrow = this.criteria.buildOrThrow();
            AdvancementRequirements $$2 = this.requirements.orElseGet(() -> {
                return this.requirementsStrategy.create(immutableMapBuildOrThrow.keySet());
            });
            return new AdvancementHolder($$0, new Advancement(this.parent, this.display, this.rewards, immutableMapBuildOrThrow, $$2, this.sendsTelemetryEvent));
        }

        public AdvancementHolder save(Consumer<AdvancementHolder> $$0, String $$1) {
            AdvancementHolder $$2 = build(Identifier.parse($$1));
            $$0.accept($$2);
            return $$2;
        }
    }
}
