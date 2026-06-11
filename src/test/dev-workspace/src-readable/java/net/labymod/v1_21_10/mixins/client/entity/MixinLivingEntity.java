package net.labymod.v1_21_10.mixins.client.entity;

import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Map;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.world.effect.PotionEffect;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.animation.old.animations.BackwardsOldAnimation;
import net.labymod.core.watcher.map.WatchableHashMap;
import net.labymod.v1_21_10.client.util.MinecraftUtil;
import net.labymod.v1_21_10.client.util.WatchableActivePotionMap;
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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/entity/MixinLivingEntity.class */
@Mixin({cew.class})
@Implements({@Interface(iface = LivingEntity.class, prefix = "livingEntity$", remap = Interface.Remap.NONE)})
public abstract class MixinLivingEntity extends MixinEntity implements LivingEntity {
    private final Map<jk<ccv>, PotionEffect> labyMod$activePotionEffects = Maps.newHashMap();

    @Shadow
    public int bt;

    @Shadow
    public int bv;

    @Mutable
    @Shadow
    @Final
    private Map<jk<ccv>, ccx> cl;

    @Shadow
    public abstract cbp shadow$fP();

    @Shadow
    public abstract int fR();

    @Shadow
    public abstract dhp a(cef cefVar);

    @Shadow
    public abstract boolean shadow$gg();

    @Intrinsic
    public float livingEntity$getHealth() {
        return labyMod$getLivingEntity().eU();
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getMaximalHealth() {
        return labyMod$getLivingEntity().fj();
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getAbsorptionHealth() {
        return labyMod$getLivingEntity().fM();
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public int getItemUseDurationTicks() {
        return labyMod$getLivingEntity().fS();
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public LivingEntity.Hand getUsedItemHand() {
        if (shadow$fP() == cbp.a) {
            return LivingEntity.Hand.MAIN_HAND;
        }
        return LivingEntity.Hand.OFF_HAND;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public boolean isMainHandRight() {
        return labyMod$getLivingEntity().as() == cem.b;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public ItemStack getMainHandItemStack() {
        return MinecraftUtil.fromMinecraft(labyMod$getLivingEntity().fq());
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public ItemStack getRightHandItemStack() {
        if (labyMod$getLivingEntity().as() == cem.b) {
            return getMainHandItemStack();
        }
        return getOffHandItemStack();
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public ItemStack getLeftHandItemStack() {
        if (labyMod$getLivingEntity().as() == cem.a) {
            return getMainHandItemStack();
        }
        return getOffHandItemStack();
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public ItemStack getOffHandItemStack() {
        return MinecraftUtil.fromMinecraft(labyMod$getLivingEntity().fr());
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public ItemStack getEquipmentItemStack(LivingEntity.EquipmentSpot equipmentSlot) {
        return MinecraftUtil.fromMinecraftSlot(labyMod$getLivingEntity(), equipmentSlot);
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getBodyRotationY() {
        return labyMod$getLivingEntity().bB;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getPreviousBodyRotationY() {
        return labyMod$getLivingEntity().bC;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public void setBodyRotationY(float rotationY) {
        labyMod$getLivingEntity().bB = rotationY;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public void setPreviousBodyRotationY(float rotationY) {
        labyMod$getLivingEntity().bC = rotationY;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getHeadRotationY() {
        return labyMod$getLivingEntity().bD;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getPreviousHeadRotationY() {
        return labyMod$getLivingEntity().bE;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public void setHeadRotationY(float rotationY) {
        labyMod$getLivingEntity().bD = rotationY;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public void setPreviousHeadRotationY(float rotationY) {
        labyMod$getLivingEntity().bE = rotationY;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getHeadRotationX() {
        return labyMod$getLivingEntity().dZ();
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public void setHeadRotationX(float rotationX) {
        labyMod$getLivingEntity().w(rotationX);
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getPreviousHeadRotationX() {
        return labyMod$getLivingEntity().ac;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public void setPreviousHeadRotationX(float rotationX) {
        labyMod$getLivingEntity().ac = rotationX;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public boolean isWearingElytra() {
        dhp itemStack = a(cef.e);
        return itemStack.a(dht.pq);
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public int getHurtTime() {
        return this.bt;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public int getDeathTime() {
        return this.bv;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public boolean isHostile() {
        return this instanceof cwf;
    }

    @ModifyConstant(method = {"tick"}, constant = {@Constant(floatValue = 180.0f, ordinal = 0)})
    private float labyMod$oldBackwards(float value) {
        BackwardsOldAnimation animation = (BackwardsOldAnimation) LabyMod.getInstance().getOldAnimationRegistry().get(BackwardsOldAnimation.NAME);
        return animation == null ? value : animation.modify(value);
    }

    private cew labyMod$getLivingEntity() {
        return (cew) this;
    }

    @Inject(method = {"<init>"}, at = {@At("TAIL")})
    private void labyMod$useWatchableMap(cee param0, drq param1, CallbackInfo ci) {
        this.cl = new WatchableHashMap(new WatchableActivePotionMap(this.labyMod$activePotionEffects));
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public Collection<PotionEffect> getActivePotionEffects() {
        return this.labyMod$activePotionEffects.values();
    }

    @Intrinsic
    public boolean livingEntity$isSleeping() {
        return shadow$gg();
    }
}
