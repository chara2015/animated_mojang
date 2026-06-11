package net.labymod.v26_2_snapshot_8.mixins.client.entity;

import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Map;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.world.effect.PotionEffect;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.animation.old.animations.BackwardsOldAnimation;
import net.labymod.core.watcher.map.WatchableHashMap;
import net.labymod.v26_2_snapshot_8.client.util.MinecraftUtil;
import net.labymod.v26_2_snapshot_8.client.util.WatchableActivePotionMap;
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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/client/entity/MixinLivingEntity.class */
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

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getMaximalHealth() {
        return labyMod$getLivingEntity().getMaxHealth();
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getAbsorptionHealth() {
        return labyMod$getLivingEntity().getAbsorptionAmount();
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public int getItemUseDurationTicks() {
        return labyMod$getLivingEntity().getTicksUsingItem();
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public LivingEntity.Hand getUsedItemHand() {
        if (shadow$getUsedItemHand() == InteractionHand.MAIN_HAND) {
            return LivingEntity.Hand.MAIN_HAND;
        }
        return LivingEntity.Hand.OFF_HAND;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public boolean isMainHandRight() {
        return labyMod$getLivingEntity().getMainArm() == HumanoidArm.RIGHT;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public net.labymod.api.client.world.item.ItemStack getMainHandItemStack() {
        return MinecraftUtil.fromMinecraft(labyMod$getLivingEntity().getMainHandItem());
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public net.labymod.api.client.world.item.ItemStack getRightHandItemStack() {
        if (labyMod$getLivingEntity().getMainArm() == HumanoidArm.RIGHT) {
            return getMainHandItemStack();
        }
        return getOffHandItemStack();
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public net.labymod.api.client.world.item.ItemStack getLeftHandItemStack() {
        if (labyMod$getLivingEntity().getMainArm() == HumanoidArm.LEFT) {
            return getMainHandItemStack();
        }
        return getOffHandItemStack();
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public net.labymod.api.client.world.item.ItemStack getOffHandItemStack() {
        return MinecraftUtil.fromMinecraft(labyMod$getLivingEntity().getOffhandItem());
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public net.labymod.api.client.world.item.ItemStack getEquipmentItemStack(LivingEntity.EquipmentSpot equipmentSlot) {
        return MinecraftUtil.fromMinecraftSlot(labyMod$getLivingEntity(), equipmentSlot);
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getBodyRotationY() {
        return labyMod$getLivingEntity().yBodyRot;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getPreviousBodyRotationY() {
        return labyMod$getLivingEntity().yBodyRotO;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public void setBodyRotationY(float rotationY) {
        labyMod$getLivingEntity().yBodyRot = rotationY;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public void setPreviousBodyRotationY(float rotationY) {
        labyMod$getLivingEntity().yBodyRotO = rotationY;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getHeadRotationY() {
        return labyMod$getLivingEntity().yHeadRot;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getPreviousHeadRotationY() {
        return labyMod$getLivingEntity().yHeadRotO;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public void setHeadRotationY(float rotationY) {
        labyMod$getLivingEntity().yHeadRot = rotationY;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public void setPreviousHeadRotationY(float rotationY) {
        labyMod$getLivingEntity().yHeadRotO = rotationY;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getHeadRotationX() {
        return labyMod$getLivingEntity().getXRot();
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public void setHeadRotationX(float rotationX) {
        labyMod$getLivingEntity().setXRot(rotationX);
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getPreviousHeadRotationX() {
        return labyMod$getLivingEntity().xRotO;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public void setPreviousHeadRotationX(float rotationX) {
        labyMod$getLivingEntity().xRotO = rotationX;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public boolean isWearingElytra() {
        ItemStack itemStack = getItemBySlot(EquipmentSlot.CHEST);
        return itemStack.is(Items.ELYTRA);
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public int getHurtTime() {
        return this.hurtTime;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public int getDeathTime() {
        return this.deathTime;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public boolean isHostile() {
        return this instanceof Enemy;
    }

    @ModifyConstant(method = {"tick"}, constant = {@Constant(floatValue = 180.0f, ordinal = 0)})
    private float labyMod$oldBackwards(float value) {
        BackwardsOldAnimation animation = (BackwardsOldAnimation) LabyMod.getInstance().getOldAnimationRegistry().get(BackwardsOldAnimation.NAME);
        return animation == null ? value : animation.modify(value);
    }

    private net.minecraft.world.entity.LivingEntity labyMod$getLivingEntity() {
        return (net.minecraft.world.entity.LivingEntity) this;
    }

    @Inject(method = {"<init>"}, at = {@At("TAIL")})
    private void labyMod$useWatchableMap(EntityType param0, Level param1, CallbackInfo ci) {
        this.activeEffects = new WatchableHashMap(new WatchableActivePotionMap(this.labyMod$activePotionEffects));
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public Collection<PotionEffect> getActivePotionEffects() {
        return this.labyMod$activePotionEffects.values();
    }

    @Intrinsic
    public boolean livingEntity$isSleeping() {
        return shadow$isSleeping();
    }
}
