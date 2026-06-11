package net.minecraft.world.attribute.modifier;

import com.mojang.serialization.Codec;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Map;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.attribute.EnvironmentAttribute;
import net.minecraft.world.attribute.LerpFunction;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/attribute/modifier/AttributeModifier.class */
public interface AttributeModifier<Subject, Argument> {
    public static final Map<OperationId, AttributeModifier<Boolean, ?>> BOOLEAN_LIBRARY = Map.of(OperationId.AND, BooleanModifier.AND, OperationId.NAND, BooleanModifier.NAND, OperationId.OR, BooleanModifier.OR, OperationId.NOR, BooleanModifier.NOR, OperationId.XOR, BooleanModifier.XOR, OperationId.XNOR, BooleanModifier.XNOR);
    public static final Map<OperationId, AttributeModifier<Float, ?>> FLOAT_LIBRARY = Map.of(OperationId.ALPHA_BLEND, FloatModifier.ALPHA_BLEND, OperationId.ADD, FloatModifier.ADD, OperationId.SUBTRACT, FloatModifier.SUBTRACT, OperationId.MULTIPLY, FloatModifier.MULTIPLY, OperationId.MINIMUM, FloatModifier.MINIMUM, OperationId.MAXIMUM, FloatModifier.MAXIMUM);
    public static final Map<OperationId, AttributeModifier<Integer, ?>> RGB_COLOR_LIBRARY = Map.of(OperationId.ALPHA_BLEND, ColorModifier.ALPHA_BLEND, OperationId.ADD, ColorModifier.ADD, OperationId.SUBTRACT, ColorModifier.SUBTRACT, OperationId.MULTIPLY, ColorModifier.MULTIPLY_RGB, OperationId.BLEND_TO_GRAY, ColorModifier.BLEND_TO_GRAY);
    public static final Map<OperationId, AttributeModifier<Integer, ?>> ARGB_COLOR_LIBRARY = Map.of(OperationId.ALPHA_BLEND, ColorModifier.ALPHA_BLEND, OperationId.ADD, ColorModifier.ADD, OperationId.SUBTRACT, ColorModifier.SUBTRACT, OperationId.MULTIPLY, ColorModifier.MULTIPLY_ARGB, OperationId.BLEND_TO_GRAY, ColorModifier.BLEND_TO_GRAY);

    Subject apply(Subject subject, Argument argument);

    Codec<Argument> argumentCodec(EnvironmentAttribute<Subject> environmentAttribute);

    LerpFunction<Argument> argumentKeyframeLerp(EnvironmentAttribute<Subject> environmentAttribute);

    static <Value> AttributeModifier<Value, Value> override() {
        return OverrideModifier.INSTANCE;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/attribute/modifier/AttributeModifier$OverrideModifier.class */
    public static final class OverrideModifier<Value> extends Record implements AttributeModifier<Value, Value> {
        static final OverrideModifier<?> INSTANCE = new OverrideModifier<>();

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, OverrideModifier.class), OverrideModifier.class, "").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, OverrideModifier.class), OverrideModifier.class, "").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, OverrideModifier.class, Object.class), OverrideModifier.class, "").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        @Override // net.minecraft.world.attribute.modifier.AttributeModifier
        public Value apply(Value $$0, Value $$1) {
            return $$1;
        }

        @Override // net.minecraft.world.attribute.modifier.AttributeModifier
        public Codec<Value> argumentCodec(EnvironmentAttribute<Value> $$0) {
            return $$0.valueCodec();
        }

        @Override // net.minecraft.world.attribute.modifier.AttributeModifier
        public LerpFunction<Value> argumentKeyframeLerp(EnvironmentAttribute<Value> $$0) {
            return $$0.type().keyframeLerp();
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/attribute/modifier/AttributeModifier$OperationId.class */
    public enum OperationId implements StringRepresentable {
        OVERRIDE("override"),
        ALPHA_BLEND("alpha_blend"),
        ADD("add"),
        SUBTRACT("subtract"),
        MULTIPLY("multiply"),
        BLEND_TO_GRAY("blend_to_gray"),
        MINIMUM("minimum"),
        MAXIMUM("maximum"),
        AND("and"),
        NAND("nand"),
        OR("or"),
        NOR("nor"),
        XOR("xor"),
        XNOR("xnor");

        public static final Codec<OperationId> CODEC = StringRepresentable.fromEnum(OperationId::values);
        private final String name;

        OperationId(String $$0) {
            this.name = $$0;
        }

        @Override // net.minecraft.util.StringRepresentable
        public String getSerializedName() {
            return this.name;
        }
    }
}
