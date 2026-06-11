package net.minecraft.util.valueproviders;

import java.util.Arrays;
import net.minecraft.util.RandomSource;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/valueproviders/MultipliedFloats.class */
public class MultipliedFloats implements SampledFloat {
    private final SampledFloat[] values;

    public MultipliedFloats(SampledFloat... $$0) {
        this.values = $$0;
    }

    @Override // net.minecraft.util.valueproviders.SampledFloat
    public float sample(RandomSource $$0) {
        float $$1 = 1.0f;
        for (SampledFloat $$2 : this.values) {
            $$1 *= $$2.sample($$0);
        }
        return $$1;
    }

    public String toString() {
        return "MultipliedFloats" + Arrays.toString(this.values);
    }
}
