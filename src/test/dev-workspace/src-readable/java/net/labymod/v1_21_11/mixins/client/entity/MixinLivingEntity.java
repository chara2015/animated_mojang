package net.labymod.v1_21_11.mixins.client.entity;

import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Map;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.world.effect.PotionEffect;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.animation.old.animations.BackwardsOldAnimation;
import net.labymod.core.watcher.map.WatchableHashMap;
import net.labymod.v1_21_11.client.util.MinecraftUtil;
import net.labymod.v1_21_11.client.util.WatchableActivePotionMap;
import net.minecraft.core.Holder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
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

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/entity/MixinLivingEntity.class */
@Mixin({LivingEntity.class})
@Implements({@Interface(iface = net.labymod.api.client.entity.LivingEntity.class, prefix = "livingEntity$", remap = Interface.Remap.NONE)})
public abstract class MixinLivingEntity extends MixinEntity implements net.labymod.api.client.entity.LivingEntity {
    private final Map<Holder<MobEffect>, PotionEffect> labyMod$activePotionEffects = Maps.newHashMap();

    @Shadow
    public int hurtTime;

    @Shadow
    public int deathTime;

    @Mutable
    @Shadow
    @Final
    private Map<Holder<MobEffect>, MobEffectInstance> activeEffects;

    @Shadow
    public abstract InteractionHand shadow$getUsedItemHand();

    @Shadow
    public abstract int getUseItemRemainingTicks();

    @Shadow
    public abstract ItemStack getItemBySlot(EquipmentSlot equipmentSlot);

    @Shadow
    public abstract boolean shadow$isSleeping();

    @Intrinsic
    public float livingEntity$getHealth() {
        return labyMod$getLivingEntity().getHealth();
    }

    public float getMaximalHealth() {
        return labyMod$getLivingEntity().getMaxHealth();
    }

    public float getAbsorptionHealth() {
        return labyMod$getLivingEntity().getAbsorptionAmount();
    }

    public int getItemUseDurationTicks() {
        return labyMod$getLivingEntity().getTicksUsingItem();
    }

    public LivingEntity.Hand getUsedItemHand() {
        if (shadow$getUsedItemHand() == InteractionHand.MAIN_HAND) {
            return LivingEntity.Hand.MAIN_HAND;
        }
        return LivingEntity.Hand.OFF_HAND;
    }

    public boolean isMainHandRight() {
        return labyMod$getLivingEntity().getMainArm() == HumanoidArm.RIGHT;
    }

    public net.labymod.api.client.world.item.ItemStack getMainHandItemStack() {
        return MinecraftUtil.fromMinecraft(labyMod$getLivingEntity().getMainHandItem());
    }

    public net.labymod.api.client.world.item.ItemStack getRightHandItemStack() {
        if (labyMod$getLivingEntity().getMainArm() == HumanoidArm.RIGHT) {
            return getMainHandItemStack();
        }
        return getOffHandItemStack();
    }

    public net.labymod.api.client.world.item.ItemStack getLeftHandItemStack() {
        if (labyMod$getLivingEntity().getMainArm() == HumanoidArm.LEFT) {
            return getMainHandItemStack();
        }
        return getOffHandItemStack();
    }

    public net.labymod.api.client.world.item.ItemStack getOffHandItemStack() {
        return MinecraftUtil.fromMinecraft(labyMod$getLivingEntity().getOffhandItem());
    }

    public net.labymod.api.client.world.item.ItemStack getEquipmentItemStack(LivingEntity.EquipmentSpot equipmentSlot) {
        return MinecraftUtil.fromMinecraftSlot(labyMod$getLivingEntity(), equipmentSlot);
    }

    public float getBodyRotationY() {
        return labyMod$getLivingEntity().yBodyRot;
    }

    public float getPreviousBodyRotationY() {
        return labyMod$getLivingEntity().yBodyRotO;
    }

    public void setBodyRotationY(float rotationY) {
        labyMod$getLivingEntity().yBodyRot = rotationY;
    }

    public void setPreviousBodyRotationY(float rotationY) {
        labyMod$getLivingEntity().yBodyRotO = rotationY;
    }

    public float getHeadRotationY() {
        return labyMod$getLivingEntity().yHeadRot;
    }

    public float getPreviousHeadRotationY() {
        return labyMod$getLivingEntity().yHeadRotO;
    }

    public void setHeadRotationY(float rotationY) {
        labyMod$getLivingEntity().yHeadRot = rotationY;
    }

    public void setPreviousHeadRotationY(float rotationY) {
        labyMod$getLivingEntity().yHeadRotO = rotationY;
    }

    public float getHeadRotationX() {
        return labyMod$getLivingEntity().getXRot();
    }

    public void setHeadRotationX(float rotationX) {
        labyMod$getLivingEntity().setXRot(rotationX);
    }

    public float getPreviousHeadRotationX() {
        return labyMod$getLivingEntity().xRotO;
    }

    public void setPreviousHeadRotationX(float rotationX) {
        labyMod$getLivingEntity().xRotO = rotationX;
    }

    public boolean isWearingElytra() {
        ItemStack itemStack = getItemBySlot(EquipmentSlot.CHEST);
        return itemStack.is(Items.ELYTRA);
    }

    public int getHurtTime() {
        return this.hurtTime;
    }

    public int getDeathTime() {
        return this.deathTime;
    }

    public boolean isHostile() {
        return this instanceof Enemy;
    }

    @ModifyConstant(method = {"tick"}, constant = {@Constant(floatValue = 180.0f, ordinal = 0)})
    private float labyMod$oldBackwards(float value) {
        BackwardsOldAnimation animation = LabyMod.getInstance().getOldAnimationRegistry().get("backwards");
        return animation == null ? value : animation.modify(value);
    }

    private net.minecraft.world.entity.LivingEntity labyMod$getLivingEntity() {
        return (net.minecraft.world.entity.LivingEntity) this;
    }

    @Inject(method = {"<init>"}, at = {@At("TAIL")})
    private void labyMod$useWatchableMap(EntityType param0, Level param1, CallbackInfo ci) {
        this.activeEffects = new WatchableHashMap(new WatchableActivePotionMap(this.labyMod$activePotionEffects));
    }

    public Collection<PotionEffect> getActivePotionEffects() {
        return this.labyMod$activePotionEffects.values();
    }

    @Intrinsic
    public boolean livingEntity$isSleeping() {
        return shadow$isSleeping();
    }
}

