package net.labymod.v1_21_3.mixinplugin.optifine;

import java.util.ArrayList;
import java.util.List;
import net.labymod.api.mixin.dynamic.DynamicMixinApplier;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/mixinplugin/optifine/OptiFineDynamicMixinApplier.class */
public class OptiFineDynamicMixinApplier implements DynamicMixinApplier {
    private final List<String> mixins = new ArrayList();

    public OptiFineDynamicMixinApplier() {
        addMixin("mojang.blaze3d.vertex.MixinBufferUploader");
        addMixin("client.screen.MixinLoadingOverlayDarkBackground");
        addMixin("client.renderer.entity.layers.MixinHumanoidArmorLayer");
        addMixin("client.MixinScreenshot");
        addMixin("client.renderer.MixinLevelRendererRenderWorldEvent");
        addMixin("client.renderer.MixinScreenEffectRenderer");
        addMixin("client.MixinLevelRenderer_CustomBlockOutline");
        addMixin("client.renderer.MixinLevelRendererLevelRenderContext");
    }

    @Override // net.labymod.api.mixin.dynamic.DynamicMixinApplier
    public boolean apply(String targetClassName, String mixinClassName) {
        return !this.mixins.contains(mixinClassName);
    }

    private void addMixin(String path) {
        this.mixins.add("net.labymod.v1_21_3.mixins." + path);
    }
}
