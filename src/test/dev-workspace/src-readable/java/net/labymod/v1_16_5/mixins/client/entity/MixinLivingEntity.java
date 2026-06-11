package net.labymod.v1_16_5.mixins.client.entity;

import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Map;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.world.effect.PotionEffect;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.animation.old.animations.BackwardsOldAnimation;
import net.labymod.core.watcher.map.WatchableHashMap;
import net.labymod.v1_16_5.client.util.MinecraftUtil;
import net.labymod.v1_16_5.client.util.WatchableActivePotionMap;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/entity/MixinLivingEntity.class */
@Mixin({aqm.class})
@Implements({@Interface(iface = LivingEntity.class, prefix = "livingEntity$", remap = Interface.Remap.NONE)})
public abstract class MixinLivingEntity extends MixinEntity implements LivingEntity {
    private final Map<aps, PotionEffect> labyMod$activePotionEffects = Maps.newHashMap();

    @Shadow
    public int an;

    @Shadow
    public int aq;

    @Mutable
    @Shadow
    @Final
    private Map<aps, apu> bm;

    @Shadow
    public boolean ai;

    @Shadow
    public abstract aot shadow$dX();

    @Shadow
    public abstract int dZ();

    @Shadow
    public abstract bmb b(aqf aqfVar);

    @Shadow
    public abstract boolean shadow$em();

    @Shadow
    public abstract int ea();

    @Shadow
    public abstract double b(arg argVar);

    @Intrinsic
    public float livingEntity$getHealth() {
        return labyMod$getLivingEntity().dk();
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getMaximalHealth() {
        return labyMod$getLivingEntity().dx();
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getAbsorptionHealth() {
        return labyMod$getLivingEntity().dT();
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public int getItemUseDurationTicks() {
        return labyMod$getLivingEntity().ea();
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public LivingEntity.Hand getUsedItemHand() {
        if (shadow$dX() == aot.a) {
            return LivingEntity.Hand.MAIN_HAND;
        }
        return LivingEntity.Hand.OFF_HAND;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public boolean isMainHandRight() {
        return labyMod$getLivingEntity().dV() == aqi.b;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public ItemStack getMainHandItemStack() {
        return MinecraftUtil.fromMinecraft(labyMod$getLivingEntity().dD());
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public ItemStack getRightHandItemStack() {
        if (labyMod$getLivingEntity().dV() == aqi.b) {
            return getMainHandItemStack();
        }
        return getOffHandItemStack();
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public ItemStack getLeftHandItemStack() {
        if (labyMod$getLivingEntity().dV() == aqi.a) {
            return getMainHandItemStack();
        }
        return getOffHandItemStack();
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public ItemStack getOffHandItemStack() {
        return MinecraftUtil.fromMinecraft(labyMod$getLivingEntity().dE());
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public ItemStack getEquipmentItemStack(LivingEntity.EquipmentSpot equipmentSlot) {
        return MinecraftUtil.fromMinecraftSlot(labyMod$getLivingEntity(), equipmentSlot);
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getBodyRotationY() {
        return labyMod$getLivingEntity().aA;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getPreviousBodyRotationY() {
        return labyMod$getLivingEntity().aB;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public void setBodyRotationY(float rotationY) {
        labyMod$getLivingEntity().aA = rotationY;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public void setPreviousBodyRotationY(float rotationY) {
        labyMod$getLivingEntity().aB = rotationY;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getHeadRotationX() {
        return labyMod$getLivingEntity().q;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public void setHeadRotationX(float rotationX) {
        labyMod$getLivingEntity().q = rotationX;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getPreviousHeadRotationX() {
        return labyMod$getLivingEntity().s;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public void setPreviousHeadRotationX(float rotationX) {
        labyMod$getLivingEntity().s = rotationX;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getHeadRotationY() {
        return labyMod$getLivingEntity().aC;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getPreviousHeadRotationY() {
        return labyMod$getLivingEntity().aD;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public void setHeadRotationY(float rotationY) {
        labyMod$getLivingEntity().aC = rotationY;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public void setPreviousHeadRotationY(float rotationY) {
        labyMod$getLivingEntity().aD = rotationY;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public boolean isWearingElytra() {
        bmb itemStack = b(aqf.e);
        return itemStack.b() == bmd.qo;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public int getHurtTime() {
        return this.an;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public int getDeathTime() {
        return this.aq;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public double getMovementSpeedAttribute() {
        return b(arl.d);
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public boolean isHostile() {
        return this instanceof bdi;
    }

    @ModifyConstant(method = {"tick"}, constant = {@Constant(floatValue = 180.0f, ordinal = 0)})
    private float labyMod$oldBackwards(float value) {
        BackwardsOldAnimation animation = (BackwardsOldAnimation) LabyMod.getInstance().getOldAnimationRegistry().get(BackwardsOldAnimation.NAME);
        return animation == null ? value : animation.modify(value);
    }

    private aqm labyMod$getLivingEntity() {
        return (aqm) this;
    }

    @Inject(method = {"<init>"}, at = {@At("TAIL")})
    private void labyMod$useWatchableMap(aqe param0, brx param1, CallbackInfo ci) {
        this.bm = new WatchableHashMap(new WatchableActivePotionMap(this.labyMod$activePotionEffects));
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public Collection<PotionEffect> getActivePotionEffects() {
        return this.labyMod$activePotionEffects.values();
    }

    @Intrinsic
    public boolean livingEntity$isSleeping() {
        return shadow$em();
    }
}
