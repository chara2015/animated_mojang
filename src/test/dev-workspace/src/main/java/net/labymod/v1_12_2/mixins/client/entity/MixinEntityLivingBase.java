package net.labymod.v1_12_2.mixins.client.entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.world.effect.PotionEffect;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.animation.old.animations.BackwardsOldAnimation;
import net.labymod.core.watcher.map.WatchableHashMap;
import net.labymod.v1_12_2.client.util.MinecraftUtil;
import net.labymod.v1_12_2.client.util.WatchableActivePotionMap;
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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/entity/MixinEntityLivingBase.class */
@Mixin({vp.class})
@Implements({@Interface(iface = LivingEntity.class, prefix = "livingEntity$", remap = Interface.Remap.NONE)})
public abstract class MixinEntityLivingBase extends MixinEntity implements LivingEntity {

    @Shadow
    public int ay;

    @Shadow
    public int aB;
    private final Map<uz, PotionEffect> labyMod$activePotions = new HashMap();

    @Mutable
    @Shadow
    @Final
    private Map<uz, va> bu;

    @Shadow
    public abstract boolean cz();

    @Shadow
    public abstract float cd();

    @Shadow
    public abstract float cj();

    @Shadow
    public abstract float cD();

    @Shadow
    public abstract int cL();

    @Shadow
    public abstract aip b(ub ubVar);

    @Shadow
    public abstract wd a(wc wcVar);

    @Inject(method = {"<init>"}, at = {@At("TAIL")})
    private void labyMod$useWatchableMap(amu world, CallbackInfo ci) {
        this.bu = new WatchableHashMap(new WatchableActivePotionMap(this.labyMod$activePotions));
    }

    @ModifyConstant(method = {"onUpdate"}, constant = {@Constant(floatValue = 180.0f, ordinal = 0)})
    private float labyMod$oldBackwards(float value) {
        BackwardsOldAnimation animation = (BackwardsOldAnimation) LabyMod.getInstance().getOldAnimationRegistry().get(BackwardsOldAnimation.NAME);
        return animation == null ? value : animation.modify(value);
    }

    @Intrinsic
    public float livingEntity$getHealth() {
        return cd();
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getMaximalHealth() {
        return cj();
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getAbsorptionHealth() {
        return cD();
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public int getItemUseDurationTicks() {
        return cL();
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public ItemStack getMainHandItemStack() {
        return MinecraftUtil.fromMinecraft(b(ub.a));
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public ItemStack getOffHandItemStack() {
        return MinecraftUtil.fromMinecraft(b(ub.b));
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public ItemStack getRightHandItemStack() {
        return getMainHandItemStack();
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public ItemStack getLeftHandItemStack() {
        return getMainHandItemStack();
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public ItemStack getEquipmentItemStack(LivingEntity.EquipmentSpot equipmentSpot) {
        return MinecraftUtil.fromMinecraftSlot((vp) this, equipmentSpot);
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getBodyRotationY() {
        return labyMod$getLivingEntity().aN;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getPreviousBodyRotationY() {
        return labyMod$getLivingEntity().aO;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public void setBodyRotationY(float rotationY) {
        labyMod$getLivingEntity().aN = rotationY;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public void setPreviousBodyRotationY(float rotationY) {
        labyMod$getLivingEntity().aO = rotationY;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getHeadRotationX() {
        return labyMod$getLivingEntity().w;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public void setHeadRotationX(float rotationX) {
        labyMod$getLivingEntity().w = rotationX;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getPreviousHeadRotationX() {
        return labyMod$getLivingEntity().y;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public void setPreviousHeadRotationX(float rotationX) {
        labyMod$getLivingEntity().y = rotationX;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getHeadRotationY() {
        return labyMod$getLivingEntity().aP;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getPreviousHeadRotationY() {
        return labyMod$getLivingEntity().aQ;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public void setHeadRotationY(float rotationY) {
        labyMod$getLivingEntity().aP = rotationY;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public void setPreviousHeadRotationY(float rotationY) {
        labyMod$getLivingEntity().aQ = rotationY;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public boolean isWearingElytra() {
        ItemStack itemStack = getEquipmentItemStack(LivingEntity.EquipmentSpot.CHEST);
        return itemStack.getAsItem() == air.cS;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public int getHurtTime() {
        return this.ay;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public int getDeathTime() {
        return this.aB;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public boolean isHostile() {
        return this instanceof acw;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public double getMovementSpeedAttribute() {
        return a(adh.d).e();
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public Collection<PotionEffect> getActivePotionEffects() {
        return this.labyMod$activePotions.values();
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public boolean isSleeping() {
        return cz();
    }

    private vp labyMod$getLivingEntity() {
        return (vp) this;
    }
}
