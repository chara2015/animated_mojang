package net.labymod.api.mixin.dynamic;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/mixin/dynamic/AlwaysDynamicMixinApplier.class */
public final class AlwaysDynamicMixinApplier implements DynamicMixinApplier {
    public static final AlwaysDynamicMixinApplier INSTANCE = new AlwaysDynamicMixinApplier();

    private AlwaysDynamicMixinApplier() {
    }

    @Override // net.labymod.api.mixin.dynamic.DynamicMixinApplier
    public boolean apply(String targetClassName, String mixinClassName) {
        return true;
    }
}
