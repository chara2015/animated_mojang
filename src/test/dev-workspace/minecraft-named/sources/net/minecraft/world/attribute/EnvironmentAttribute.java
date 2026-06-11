package net.minecraft.world.attribute;

import com.mojang.serialization.Codec;
import java.util.Objects;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Util;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/attribute/EnvironmentAttribute.class */
public class EnvironmentAttribute<Value> {
    private final AttributeType<Value> type;
    private final Value defaultValue;
    private final AttributeRange<Value> valueRange;
    private final boolean isSyncable;
    private final boolean isPositional;
    private final boolean isSpatiallyInterpolated;

    EnvironmentAttribute(AttributeType<Value> $$0, Value $$1, AttributeRange<Value> $$2, boolean $$3, boolean $$4, boolean $$5) {
        this.type = $$0;
        this.defaultValue = $$1;
        this.valueRange = $$2;
        this.isSyncable = $$3;
        this.isPositional = $$4;
        this.isSpatiallyInterpolated = $$5;
    }

    public static <Value> Builder<Value> builder(AttributeType<Value> $$0) {
        return new Builder<>($$0);
    }

    public AttributeType<Value> type() {
        return this.type;
    }

    public Value defaultValue() {
        return this.defaultValue;
    }

    public Codec<Value> valueCodec() {
        Codec<Value> codecValueCodec = this.type.valueCodec();
        AttributeRange<Value> attributeRange = this.valueRange;
        Objects.requireNonNull(attributeRange);
        return codecValueCodec.validate(attributeRange::validate);
    }

    public Value sanitizeValue(Value $$0) {
        return this.valueRange.sanitize($$0);
    }

    public boolean isSyncable() {
        return this.isSyncable;
    }

    public boolean isPositional() {
        return this.isPositional;
    }

    public boolean isSpatiallyInterpolated() {
        return this.isSpatiallyInterpolated;
    }

    public String toString() {
        return Util.getRegisteredName(BuiltInRegistries.ENVIRONMENT_ATTRIBUTE, this);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/attribute/EnvironmentAttribute$Builder.class */
    public static class Builder<Value> {
        private final AttributeType<Value> type;
        private Value defaultValue;
        private AttributeRange<Value> valueRange = AttributeRange.any();
        private boolean isSyncable = false;
        private boolean isPositional = true;
        private boolean isSpatiallyInterpolated = false;

        public Builder(AttributeType<Value> $$0) {
            this.type = $$0;
        }

        public Builder<Value> defaultValue(Value $$0) {
            this.defaultValue = $$0;
            return this;
        }

        public Builder<Value> valueRange(AttributeRange<Value> $$0) {
            this.valueRange = $$0;
            return this;
        }

        public Builder<Value> syncable() {
            this.isSyncable = true;
            return this;
        }

        public Builder<Value> notPositional() {
            this.isPositional = false;
            return this;
        }

        public Builder<Value> spatiallyInterpolated() {
            this.isSpatiallyInterpolated = true;
            return this;
        }

        public EnvironmentAttribute<Value> build() {
            return new EnvironmentAttribute<>(this.type, Objects.requireNonNull(this.defaultValue, "Missing default value"), this.valueRange, this.isSyncable, this.isPositional, this.isSpatiallyInterpolated);
        }
    }
}
