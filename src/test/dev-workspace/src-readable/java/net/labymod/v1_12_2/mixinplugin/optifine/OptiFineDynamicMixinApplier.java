package net.labymod.v1_12_2.mixinplugin.optifine;

import java.util.ArrayList;
import java.util.List;
import net.labymod.api.mixin.dynamic.DynamicMixinApplier;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixinplugin/optifine/OptiFineDynamicMixinApplier.class */
public class OptiFineDynamicMixinApplier implements DynamicMixinApplier {
    private final List<String> mixins = new ArrayList();

    public OptiFineDynamicMixinApplier() {
        this.mixins.add("net.labymod.v1_8_9.mixins.client.screen.MixinGuiVideoSettings");
    }

    @Override // net.labymod.api.mixin.dynamic.DynamicMixinApplier
    public boolean apply(String targetClassName, String mixinClassName) {
        return !this.mixins.contains(mixinClassName);
    }
}
