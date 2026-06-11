package net.minecraft.world.entity.ai.attributes;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.level.levelgen.Density;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/ai/attributes/AttributeInstance.class */
public class AttributeInstance {
    private final Holder<Attribute> attribute;
    private double baseValue;
    private double cachedValue;
    private final Consumer<AttributeInstance> onDirty;
    private final Map<AttributeModifier.Operation, Map<Identifier, AttributeModifier>> modifiersByOperation = Maps.newEnumMap(AttributeModifier.Operation.class);
    private final Map<Identifier, AttributeModifier> modifierById = new Object2ObjectArrayMap();
    private final Map<Identifier, AttributeModifier> permanentModifiers = new Object2ObjectArrayMap();
    private boolean dirty = true;

    public AttributeInstance(Holder<Attribute> $$0, Consumer<AttributeInstance> $$1) {
        this.attribute = $$0;
        this.onDirty = $$1;
        this.baseValue = $$0.value().getDefaultValue();
    }

    public Holder<Attribute> getAttribute() {
        return this.attribute;
    }

    public double getBaseValue() {
        return this.baseValue;
    }

    public void setBaseValue(double $$0) {
        if ($$0 == this.baseValue) {
            return;
        }
        this.baseValue = $$0;
        setDirty();
    }

    @VisibleForTesting
    Map<Identifier, AttributeModifier> getModifiers(AttributeModifier.Operation $$0) {
        return this.modifiersByOperation.computeIfAbsent($$0, $$02 -> {
            return new Object2ObjectOpenHashMap();
        });
    }

    public Set<AttributeModifier> getModifiers() {
        return ImmutableSet.copyOf(this.modifierById.values());
    }

    public Set<AttributeModifier> getPermanentModifiers() {
        return ImmutableSet.copyOf(this.permanentModifiers.values());
    }

    public AttributeModifier getModifier(Identifier $$0) {
        return this.modifierById.get($$0);
    }

    public boolean hasModifier(Identifier $$0) {
        return this.modifierById.get($$0) != null;
    }

    private void addModifier(AttributeModifier $$0) {
        AttributeModifier $$1 = this.modifierById.putIfAbsent($$0.id(), $$0);
        if ($$1 != null) {
            throw new IllegalArgumentException("Modifier is already applied on this attribute!");
        }
        getModifiers($$0.operation()).put($$0.id(), $$0);
        setDirty();
    }

    public void addOrUpdateTransientModifier(AttributeModifier $$0) {
        AttributeModifier $$1 = this.modifierById.put($$0.id(), $$0);
        if ($$0 == $$1) {
            return;
        }
        getModifiers($$0.operation()).put($$0.id(), $$0);
        setDirty();
    }

    public void addTransientModifier(AttributeModifier $$0) {
        addModifier($$0);
    }

    public void addOrReplacePermanentModifier(AttributeModifier $$0) {
        removeModifier($$0.id());
        addModifier($$0);
        this.permanentModifiers.put($$0.id(), $$0);
    }

    public void addPermanentModifier(AttributeModifier $$0) {
        addModifier($$0);
        this.permanentModifiers.put($$0.id(), $$0);
    }

    public void addPermanentModifiers(Collection<AttributeModifier> $$0) {
        for (AttributeModifier $$1 : $$0) {
            addPermanentModifier($$1);
        }
    }

    protected void setDirty() {
        this.dirty = true;
        this.onDirty.accept(this);
    }

    public void removeModifier(AttributeModifier $$0) {
        removeModifier($$0.id());
    }

    public boolean removeModifier(Identifier $$0) {
        AttributeModifier $$1 = this.modifierById.remove($$0);
        if ($$1 == null) {
            return false;
        }
        getModifiers($$1.operation()).remove($$0);
        this.permanentModifiers.remove($$0);
        setDirty();
        return true;
    }

    public void removeModifiers() {
        for (AttributeModifier $$0 : getModifiers()) {
            removeModifier($$0);
        }
    }

    public double getValue() {
        if (this.dirty) {
            this.cachedValue = calculateValue();
            this.dirty = false;
        }
        return this.cachedValue;
    }

    private double calculateValue() {
        double $$0 = getBaseValue();
        for (AttributeModifier $$1 : getModifiersOrEmpty(AttributeModifier.Operation.ADD_VALUE)) {
            $$0 += $$1.amount();
        }
        double $$2 = $$0;
        for (AttributeModifier $$3 : getModifiersOrEmpty(AttributeModifier.Operation.ADD_MULTIPLIED_BASE)) {
            $$2 += $$0 * $$3.amount();
        }
        for (AttributeModifier $$4 : getModifiersOrEmpty(AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)) {
            $$2 *= 1.0d + $$4.amount();
        }
        return this.attribute.value().sanitizeValue($$2);
    }

    private Collection<AttributeModifier> getModifiersOrEmpty(AttributeModifier.Operation $$0) {
        return this.modifiersByOperation.getOrDefault($$0, Map.of()).values();
    }

    public void replaceFrom(AttributeInstance $$0) {
        this.baseValue = $$0.baseValue;
        this.modifierById.clear();
        this.modifierById.putAll($$0.modifierById);
        this.permanentModifiers.clear();
        this.permanentModifiers.putAll($$0.permanentModifiers);
        this.modifiersByOperation.clear();
        $$0.modifiersByOperation.forEach(($$02, $$1) -> {
            getModifiers($$02).putAll($$1);
        });
        setDirty();
    }

    public Packed pack() {
        return new Packed(this.attribute, this.baseValue, List.copyOf(this.permanentModifiers.values()));
    }

    public void apply(Packed $$0) {
        this.baseValue = $$0.baseValue;
        for (AttributeModifier $$1 : $$0.modifiers) {
            this.modifierById.put($$1.id(), $$1);
            getModifiers($$1.operation()).put($$1.id(), $$1);
            this.permanentModifiers.put($$1.id(), $$1);
        }
        setDirty();
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/ai/attributes/AttributeInstance$Packed.class */
    public static final class Packed extends Record {
        private final Holder<Attribute> attribute;
        private final double baseValue;
        private final List<AttributeModifier> modifiers;
        public static final Codec<Packed> CODEC = RecordCodecBuilder.create($$0 -> {
            return $$0.group(BuiltInRegistries.ATTRIBUTE.holderByNameCodec().fieldOf(Entity.TAG_ID).forGetter((v0) -> {
                return v0.attribute();
            }), Codec.DOUBLE.fieldOf("base").orElse(Double.valueOf(Density.SURFACE)).forGetter((v0) -> {
                return v0.baseValue();
            }), AttributeModifier.CODEC.listOf().optionalFieldOf("modifiers", List.of()).forGetter((v0) -> {
                return v0.modifiers();
            })).apply($$0, (v1, v2, v3) -> {
                return new Packed(v1, v2, v3);
            });
        });
        public static final Codec<List<Packed>> LIST_CODEC = CODEC.listOf();

        public Packed(Holder<Attribute> $$0, double $$1, List<AttributeModifier> $$2) {
            this.attribute = $$0;
            this.baseValue = $$1;
            this.modifiers = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Packed.class), Packed.class, "attribute;baseValue;modifiers", "FIELD:Lnet/minecraft/world/entity/ai/attributes/AttributeInstance$Packed;->attribute:Lnet/minecraft/core/Holder;", "FIELD:Lnet/minecraft/world/entity/ai/attributes/AttributeInstance$Packed;->baseValue:D", "FIELD:Lnet/minecraft/world/entity/ai/attributes/AttributeInstance$Packed;->modifiers:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Packed.class), Packed.class, "attribute;baseValue;modifiers", "FIELD:Lnet/minecraft/world/entity/ai/attributes/AttributeInstance$Packed;->attribute:Lnet/minecraft/core/Holder;", "FIELD:Lnet/minecraft/world/entity/ai/attributes/AttributeInstance$Packed;->baseValue:D", "FIELD:Lnet/minecraft/world/entity/ai/attributes/AttributeInstance$Packed;->modifiers:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Packed.class, Object.class), Packed.class, "attribute;baseValue;modifiers", "FIELD:Lnet/minecraft/world/entity/ai/attributes/AttributeInstance$Packed;->attribute:Lnet/minecraft/core/Holder;", "FIELD:Lnet/minecraft/world/entity/ai/attributes/AttributeInstance$Packed;->baseValue:D", "FIELD:Lnet/minecraft/world/entity/ai/attributes/AttributeInstance$Packed;->modifiers:Ljava/util/List;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Holder<Attribute> attribute() {
            return this.attribute;
        }

        public double baseValue() {
            return this.baseValue;
        }

        public List<AttributeModifier> modifiers() {
            return this.modifiers;
        }
    }
}
