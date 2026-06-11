package net.minecraft.client.renderer.entity.state;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/entity/state/HumanoidRenderState.class */
public class HumanoidRenderState extends ArmedEntityRenderState {
    public float swimAmount;
    public float maxCrossbowChargeDuration;
    public float ticksUsingItem;
    public boolean isCrouching;
    public boolean isFallFlying;
    public boolean isVisuallySwimming;
    public boolean isPassenger;
    public boolean isUsingItem;
    public float elytraRotX;
    public float elytraRotY;
    public float elytraRotZ;
    public float speedValue = 1.0f;
    public HumanoidArm attackArm = HumanoidArm.RIGHT;
    public InteractionHand useItemHand = InteractionHand.MAIN_HAND;
    public ItemStack headEquipment = ItemStack.EMPTY;
    public ItemStack chestEquipment = ItemStack.EMPTY;
    public ItemStack legsEquipment = ItemStack.EMPTY;
    public ItemStack feetEquipment = ItemStack.EMPTY;

    @Override // net.minecraft.client.renderer.entity.state.ArmedEntityRenderState
    public float ticksUsingItem(HumanoidArm $$0) {
        if (!this.isUsingItem) {
            return 0.0f;
        }
        if ((this.useItemHand == InteractionHand.MAIN_HAND) == ($$0 == this.mainArm)) {
            return this.ticksUsingItem;
        }
        return 0.0f;
    }
}
