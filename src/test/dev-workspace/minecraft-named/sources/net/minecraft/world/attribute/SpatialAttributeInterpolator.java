package net.minecraft.world.attribute;

import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Reference2DoubleArrayMap;
import it.unimi.dsi.fastutil.objects.Reference2DoubleMap;
import it.unimi.dsi.fastutil.objects.Reference2DoubleMaps;
import java.util.Objects;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/attribute/SpatialAttributeInterpolator.class */
public class SpatialAttributeInterpolator {
    private final Reference2DoubleArrayMap<EnvironmentAttributeMap> weightsBySource = new Reference2DoubleArrayMap<>();

    public void clear() {
        this.weightsBySource.clear();
    }

    public SpatialAttributeInterpolator accumulate(double $$0, EnvironmentAttributeMap $$1) {
        this.weightsBySource.mergeDouble($$1, $$0, Double::sum);
        return this;
    }

    public <Value> Value applyAttributeLayer(EnvironmentAttribute<Value> environmentAttribute, Value value) {
        if (this.weightsBySource.isEmpty()) {
            return value;
        }
        if (this.weightsBySource.size() == 1) {
            return (Value) ((EnvironmentAttributeMap) this.weightsBySource.keySet().iterator().next()).applyModifier(environmentAttribute, value);
        }
        LerpFunction<Value> lerpFunctionSpatialLerp = environmentAttribute.type().spatialLerp();
        Object objApply = null;
        double d = 0.0d;
        ObjectIterator it = Reference2DoubleMaps.fastIterable(this.weightsBySource).iterator();
        while (it.hasNext()) {
            Reference2DoubleMap.Entry entry = (Reference2DoubleMap.Entry) it.next();
            EnvironmentAttributeMap environmentAttributeMap = (EnvironmentAttributeMap) entry.getKey();
            double doubleValue = entry.getDoubleValue();
            Object objApplyModifier = environmentAttributeMap.applyModifier(environmentAttribute, value);
            d += doubleValue;
            if (objApply == null) {
                objApply = objApplyModifier;
            } else {
                objApply = lerpFunctionSpatialLerp.apply((float) (doubleValue / d), objApply, objApplyModifier);
            }
        }
        return (Value) Objects.requireNonNull(objApply);
    }
}
