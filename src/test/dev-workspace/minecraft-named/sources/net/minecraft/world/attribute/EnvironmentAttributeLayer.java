package net.minecraft.world.attribute;

import net.minecraft.world.phys.Vec3;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/attribute/EnvironmentAttributeLayer.class */
public interface EnvironmentAttributeLayer<Value> {

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/attribute/EnvironmentAttributeLayer$Constant.class */
    @FunctionalInterface
    public interface Constant<Value> extends EnvironmentAttributeLayer<Value> {
        Value applyConstant(Value value);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/attribute/EnvironmentAttributeLayer$Positional.class */
    @FunctionalInterface
    public interface Positional<Value> extends EnvironmentAttributeLayer<Value> {
        Value applyPositional(Value value, Vec3 vec3, SpatialAttributeInterpolator spatialAttributeInterpolator);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/attribute/EnvironmentAttributeLayer$TimeBased.class */
    @FunctionalInterface
    public interface TimeBased<Value> extends EnvironmentAttributeLayer<Value> {
        Value applyTimeBased(Value value, int i);
    }
}
