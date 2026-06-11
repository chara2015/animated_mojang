package net.minecraft.world.entity.projectile.arrow;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SpellParticleOption;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Density;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/projectile/arrow/SpectralArrow.class */
public class SpectralArrow extends AbstractArrow {
    private static final int DEFAULT_DURATION = 200;
    private int duration;

    public SpectralArrow(EntityType<? extends SpectralArrow> $$0, Level $$1) {
        super($$0, $$1);
        this.duration = 200;
    }

    public SpectralArrow(Level $$0, LivingEntity $$1, ItemStack $$2, ItemStack $$3) {
        super(EntityType.SPECTRAL_ARROW, $$1, $$0, $$2, $$3);
        this.duration = 200;
    }

    public SpectralArrow(Level $$0, double $$1, double $$2, double $$3, ItemStack $$4, ItemStack $$5) {
        super(EntityType.SPECTRAL_ARROW, $$1, $$2, $$3, $$0, $$4, $$5);
        this.duration = 200;
    }

    @Override // net.minecraft.world.entity.projectile.arrow.AbstractArrow, net.minecraft.world.entity.projectile.Projectile, net.minecraft.world.entity.Entity
    public void tick() {
        super.tick();
        if (level().isClientSide() && !isInGround()) {
            level().addParticle(SpellParticleOption.create(ParticleTypes.EFFECT, -1, 1.0f), getX(), getY(), getZ(), Density.SURFACE, Density.SURFACE, Density.SURFACE);
        }
    }

    @Override // net.minecraft.world.entity.projectile.arrow.AbstractArrow
    protected void doPostHurtEffects(LivingEntity $$0) {
        super.doPostHurtEffects($$0);
        MobEffectInstance $$1 = new MobEffectInstance(MobEffects.GLOWING, this.duration, 0);
        $$0.addEffect($$1, getEffectSource());
    }

    @Override // net.minecraft.world.entity.projectile.arrow.AbstractArrow, net.minecraft.world.entity.projectile.Projectile, net.minecraft.world.entity.Entity
    protected void readAdditionalSaveData(ValueInput $$0) {
        super.readAdditionalSaveData($$0);
        this.duration = $$0.getIntOr("Duration", 200);
    }

    @Override // net.minecraft.world.entity.projectile.arrow.AbstractArrow, net.minecraft.world.entity.projectile.Projectile, net.minecraft.world.entity.Entity
    protected void addAdditionalSaveData(ValueOutput $$0) {
        super.addAdditionalSaveData($$0);
        $$0.putInt("Duration", this.duration);
    }

    @Override // net.minecraft.world.entity.projectile.arrow.AbstractArrow
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(Items.SPECTRAL_ARROW);
    }
}
