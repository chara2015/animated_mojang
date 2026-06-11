package net.minecraft.world.attribute.modifier;

import com.mojang.serialization.Codec;
import net.minecraft.world.attribute.EnvironmentAttribute;
import net.minecraft.world.attribute.LerpFunction;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/attribute/modifier/BooleanModifier.class */
public enum BooleanModifier implements AttributeModifier<Boolean, Boolean> {
    AND,
    NAND,
    OR,
    NOR,
    XOR,
    XNOR;

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.minecraft.world.attribute.modifier.AttributeModifier
    public Boolean apply(Boolean $$0, Boolean $$1) throws MatchException {
        switch (this) {
            case AND:
                return Boolean.valueOf($$1.booleanValue() && $$0.booleanValue());
            case NAND:
                return Boolean.valueOf(($$1.booleanValue() && $$0.booleanValue()) ? false : true);
            case OR:
                return Boolean.valueOf($$1.booleanValue() || $$0.booleanValue());
            case NOR:
                return Boolean.valueOf(($$1.booleanValue() || $$0.booleanValue()) ? false : true);
            case XOR:
                return Boolean.valueOf($$1.booleanValue() ^ $$0.booleanValue());
            case XNOR:
                return Boolean.valueOf($$1 == $$0);
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    @Override // net.minecraft.world.attribute.modifier.AttributeModifier
    public Codec<Boolean> argumentCodec(EnvironmentAttribute<Boolean> $$0) {
        return Codec.BOOL;
    }

    @Override // net.minecraft.world.attribute.modifier.AttributeModifier
    public LerpFunction<Boolean> argumentKeyframeLerp(EnvironmentAttribute<Boolean> $$0) {
        return LerpFunction.ofConstant();
    }
}
