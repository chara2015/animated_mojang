package net.labymod.v1_18_2.mixins.client.entity;

import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Map;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.world.effect.PotionEffect;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.animation.old.animations.BackwardsOldAnimation;
import net.labymod.core.watcher.map.WatchableHashMap;
import net.labymod.v1_18_2.client.util.MinecraftUtil;
import net.labymod.v1_18_2.client.util.WatchableActivePotionMap;
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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/client/entity/MixinLivingEntity.class */
@Mixin({axy.class})
@Implements({@Interface(iface = LivingEntity.class, prefix = "livingEntity$", remap = Interface.Remap.NONE)})
public abstract class MixinLivingEntity extends MixinEntity implements LivingEntity {
    private final Map<axc, PotionEffect> labyMod$activePotionEffects = Maps.newHashMap();

    @Shadow
    public int aK;

    @Shadow
    public int aN;

    @Mutable
    @Shadow
    @Final
    private Map<axc, axe> bS;

    @Shadow
    public abstract awg shadow$eN();

    @Shadow
    public abstract int eP();

    @Shadow
    public abstract buw b(axp axpVar);

    @Shadow
    public abstract boolean shadow$fc();

    @Shadow
    public abstract double b(ayu ayuVar);

    @Intrinsic
    public float livingEntity$getHealth() {
        return labyMod$getLivingEntity().ea();
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getMaximalHealth() {
        return labyMod$getLivingEntity().em();
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getAbsorptionHealth() {
        return labyMod$getLivingEntity().eJ();
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public int getItemUseDurationTicks() {
        return labyMod$getLivingEntity().eQ();
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public LivingEntity.Hand getUsedItemHand() {
        if (shadow$eN() == awg.a) {
            return LivingEntity.Hand.MAIN_HAND;
        }
        return LivingEntity.Hand.OFF_HAND;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public boolean isMainHandRight() {
        return labyMod$getLivingEntity().eL() == axt.b;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public ItemStack getMainHandItemStack() {
        return MinecraftUtil.fromMinecraft(labyMod$getLivingEntity().es());
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public ItemStack getRightHandItemStack() {
        if (labyMod$getLivingEntity().eL() == axt.b) {
            return getMainHandItemStack();
        }
        return getOffHandItemStack();
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public ItemStack getLeftHandItemStack() {
        if (labyMod$getLivingEntity().eL() == axt.a) {
            return getMainHandItemStack();
        }
        return getOffHandItemStack();
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public ItemStack getOffHandItemStack() {
        return MinecraftUtil.fromMinecraft(labyMod$getLivingEntity().et());
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public ItemStack getEquipmentItemStack(LivingEntity.EquipmentSpot equipmentSlot) {
        return MinecraftUtil.fromMinecraftSlot(labyMod$getLivingEntity(), equipmentSlot);
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getBodyRotationY() {
        return labyMod$getLivingEntity().aX;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getPreviousBodyRotationY() {
        return labyMod$getLivingEntity().aY;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public void setBodyRotationY(float rotationY) {
        labyMod$getLivingEntity().aX = rotationY;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public void setPreviousBodyRotationY(float rotationY) {
        labyMod$getLivingEntity().aY = rotationY;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getHeadRotationX() {
        return labyMod$getLivingEntity().do();
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public void setHeadRotationX(float rotationX) {
        labyMod$getLivingEntity().p(rotationX);
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getPreviousHeadRotationX() {
        return labyMod$getLivingEntity().x;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public void setPreviousHeadRotationX(float rotationX) {
        labyMod$getLivingEntity().x = rotationX;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getHeadRotationY() {
        return labyMod$getLivingEntity().aZ;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getPreviousHeadRotationY() {
        return labyMod$getLivingEntity().ba;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public void setHeadRotationY(float rotationY) {
        labyMod$getLivingEntity().aZ = rotationY;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public void setPreviousHeadRotationY(float rotationY) {
        labyMod$getLivingEntity().ba = rotationY;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public boolean isWearingElytra() {
        buw itemStack = b(axp.e);
        return itemStack.a(buy.lT);
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public int getHurtTime() {
        return this.aK;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public int getDeathTime() {
        return this.aN;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public double getMovementSpeedAttribute() {
        return b(ayz.d);
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public boolean isHostile() {
        return this instanceof blv;
    }

    @ModifyConstant(method = {"tick"}, constant = {@Constant(floatValue = 180.0f, ordinal = 0)})
    private float labyMod$oldBackwards(float value) {
        BackwardsOldAnimation animation = (BackwardsOldAnimation) LabyMod.getInstance().getOldAnimationRegistry().get(BackwardsOldAnimation.NAME);
        return animation == null ? value : animation.modify(value);
    }

    private axy labyMod$getLivingEntity() {
        return (axy) this;
    }

    @Inject(method = {"<init>"}, at = {@At("TAIL")})
    private void labyMod$useWatchableMap(axo param0, cav param1, CallbackInfo ci) {
        this.bS = new WatchableHashMap(new WatchableActivePotionMap(this.labyMod$activePotionEffects));
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public Collection<PotionEffect> getActivePotionEffects() {
        return this.labyMod$activePotionEffects.values();
    }

    @Intrinsic
    public boolean livingEntity$isSleeping() {
        return shadow$fc();
    }
}
