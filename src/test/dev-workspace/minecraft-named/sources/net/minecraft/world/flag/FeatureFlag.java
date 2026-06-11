package net.minecraft.world.flag;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/flag/FeatureFlag.class */
public class FeatureFlag {
    final FeatureFlagUniverse universe;
    final long mask;

    FeatureFlag(FeatureFlagUniverse $$0, int $$1) {
        this.universe = $$0;
        this.mask = 1 << $$1;
    }
}
