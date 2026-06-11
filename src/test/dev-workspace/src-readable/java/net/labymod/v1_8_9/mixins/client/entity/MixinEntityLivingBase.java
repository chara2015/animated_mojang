package net.labymod.v1_8_9.mixins.client.entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.world.effect.PotionEffect;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.core.client.entity.MouseDelayFix;
import net.labymod.core.watcher.map.WatchableHashMap;
import net.labymod.v1_8_9.client.util.MinecraftUtil;
import net.labymod.v1_8_9.client.util.WatchableActivePotionMap;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/entity/MixinEntityLivingBase.class */
@Mixin({pr.class})
@Implements({@Interface(iface = LivingEntity.class, prefix = "livingEntity$", remap = Interface.Remap.NONE)})
public abstract class MixinEntityLivingBase extends MixinEntity implements LivingEntity {

    @Shadow
    public int au;

    @Shadow
    public int ax;

    @Mutable
    @Shadow
    @Final
    private Map<Integer, pf> g;
    private final Map<Integer, PotionEffect> labyMod$activePotions = new HashMap();

    @Shadow
    public abstract float bn();

    @Shadow
    public abstract float bu();

    @Shadow
    public abstract float bN();

    @Shadow
    public abstract zx bA();

    @Shadow
    public abstract zx p(int i);

    @Shadow
    public abstract boolean bJ();

    @Shadow
    public abstract qc a(qb qbVar);

    @Inject(method = {"<init>"}, at = {@At("TAIL")})
    private void labyMod$useWatchableMap(adm world, CallbackInfo ci) {
        this.g = new WatchableHashMap(new WatchableActivePotionMap(this.labyMod$activePotions));
    }

    @Inject(method = {"getLook"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$getLook(float partialTicks, CallbackInfoReturnable<aui> ci) {
        pr entity = (pr) this;
        if ((entity instanceof bew) && MouseDelayFix.isEnabled()) {
            ci.setReturnValue(super.d(partialTicks));
        }
    }

    @Intrinsic
    public float livingEntity$getHealth() {
        return bn();
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getMaximalHealth() {
        return bu();
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getAbsorptionHealth() {
        return bN();
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public int getItemUseDurationTicks() {
        return 0;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public ItemStack getMainHandItemStack() {
        return MinecraftUtil.fromMinecraft(bA());
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public ItemStack getOffHandItemStack() {
        return MinecraftUtil.fromMinecraft(MinecraftUtil.AIR);
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
        return MinecraftUtil.fromMinecraftSlot((pr) this, equipmentSpot);
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getBodyRotationY() {
        return labyMod$getLivingEntity().aI;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getPreviousBodyRotationY() {
        return labyMod$getLivingEntity().aJ;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public void setBodyRotationY(float rotationY) {
        labyMod$getLivingEntity().aI = rotationY;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public void setPreviousBodyRotationY(float rotationY) {
        labyMod$getLivingEntity().aJ = rotationY;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getHeadRotationX() {
        return labyMod$getLivingEntity().z;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public void setHeadRotationX(float rotationX) {
        labyMod$getLivingEntity().z = rotationX;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getPreviousHeadRotationX() {
        return labyMod$getLivingEntity().B;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public void setPreviousHeadRotationX(float rotationX) {
        labyMod$getLivingEntity().B = rotationX;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getHeadRotationY() {
        return labyMod$getLivingEntity().aK;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getPreviousHeadRotationY() {
        return labyMod$getLivingEntity().aL;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public void setHeadRotationY(float rotationY) {
        labyMod$getLivingEntity().aK = rotationY;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public void setPreviousHeadRotationY(float rotationY) {
        labyMod$getLivingEntity().aL = rotationY;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public int getHurtTime() {
        return this.au;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public int getDeathTime() {
        return this.ax;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public boolean isHostile() {
        return this instanceof vq;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public double getMovementSpeedAttribute() {
        return a(vy.d).e();
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public Collection<PotionEffect> getActivePotionEffects() {
        return this.labyMod$activePotions.values();
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public boolean isSleeping() {
        return bJ();
    }

    private pr labyMod$getLivingEntity() {
        return (pr) this;
    }
}
