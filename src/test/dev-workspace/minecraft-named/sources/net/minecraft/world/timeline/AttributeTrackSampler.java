package net.minecraft.world.timeline;

import java.util.Optional;
import java.util.function.LongSupplier;
import net.minecraft.util.KeyframeTrack;
import net.minecraft.util.KeyframeTrackSampler;
import net.minecraft.world.attribute.EnvironmentAttributeLayer;
import net.minecraft.world.attribute.LerpFunction;
import net.minecraft.world.attribute.modifier.AttributeModifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/timeline/AttributeTrackSampler.class */
public class AttributeTrackSampler<Value, Argument> implements EnvironmentAttributeLayer.TimeBased<Value> {
    private final AttributeModifier<Value, Argument> modifier;
    private final KeyframeTrackSampler<Argument> argumentSampler;
    private final LongSupplier dayTimeGetter;
    private int cachedTickId;
    private Argument cachedArgument;

    public AttributeTrackSampler(Optional<Integer> $$0, AttributeModifier<Value, Argument> $$1, KeyframeTrack<Argument> $$2, LerpFunction<Argument> $$3, LongSupplier $$4) {
        this.modifier = $$1;
        this.dayTimeGetter = $$4;
        this.argumentSampler = $$2.bakeSampler($$0, $$3);
    }

    @Override // net.minecraft.world.attribute.EnvironmentAttributeLayer.TimeBased
    public Value applyTimeBased(Value $$0, int $$1) {
        if (this.cachedArgument == null || $$1 != this.cachedTickId) {
            this.cachedTickId = $$1;
            this.cachedArgument = this.argumentSampler.sample(this.dayTimeGetter.getAsLong());
        }
        return this.modifier.apply($$0, this.cachedArgument);
    }
}
