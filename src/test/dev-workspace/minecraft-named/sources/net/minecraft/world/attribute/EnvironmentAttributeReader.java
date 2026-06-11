package net.minecraft.world.attribute;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/attribute/EnvironmentAttributeReader.class */
public interface EnvironmentAttributeReader {
    public static final EnvironmentAttributeReader EMPTY = new EnvironmentAttributeReader() { // from class: net.minecraft.world.attribute.EnvironmentAttributeReader.1
        @Override // net.minecraft.world.attribute.EnvironmentAttributeReader
        public <Value> Value getDimensionValue(EnvironmentAttribute<Value> $$0) {
            return $$0.defaultValue();
        }

        @Override // net.minecraft.world.attribute.EnvironmentAttributeReader
        public <Value> Value getValue(EnvironmentAttribute<Value> $$0, Vec3 $$1, SpatialAttributeInterpolator $$2) {
            return $$0.defaultValue();
        }
    };

    <Value> Value getDimensionValue(EnvironmentAttribute<Value> environmentAttribute);

    <Value> Value getValue(EnvironmentAttribute<Value> environmentAttribute, Vec3 vec3, SpatialAttributeInterpolator spatialAttributeInterpolator);

    default <Value> Value getValue(EnvironmentAttribute<Value> environmentAttribute, BlockPos blockPos) {
        return (Value) getValue(environmentAttribute, Vec3.atCenterOf(blockPos));
    }

    default <Value> Value getValue(EnvironmentAttribute<Value> environmentAttribute, Vec3 vec3) {
        return (Value) getValue(environmentAttribute, vec3, null);
    }
}
