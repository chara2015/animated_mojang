package net.minecraft.world.effect;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/effect/InstantenousMobEffect.class */
public class InstantenousMobEffect extends MobEffect {
    public InstantenousMobEffect(MobEffectCategory $$0, int $$1) {
        super($$0, $$1);
    }

    @Override // net.minecraft.world.effect.MobEffect
    public boolean isInstantenous() {
        return true;
    }

    @Override // net.minecraft.world.effect.MobEffect
    public boolean shouldApplyEffectTickThisTick(int $$0, int $$1) {
        return $$0 >= 1;
    }
}
