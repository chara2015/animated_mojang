package net.minecraft.client.renderer.entity.state;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwingAnimationType;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/entity/state/ArmedEntityRenderState.class */
public class ArmedEntityRenderState extends LivingEntityRenderState {
    public HumanoidArm mainArm = HumanoidArm.RIGHT;
    public HumanoidModel.ArmPose rightArmPose = HumanoidModel.ArmPose.EMPTY;
    public final ItemStackRenderState rightHandItemState = new ItemStackRenderState();
    public ItemStack rightHandItemStack = ItemStack.EMPTY;
    public HumanoidModel.ArmPose leftArmPose = HumanoidModel.ArmPose.EMPTY;
    public final ItemStackRenderState leftHandItemState = new ItemStackRenderState();
    public ItemStack leftHandItemStack = ItemStack.EMPTY;
    public SwingAnimationType swingAnimationType = SwingAnimationType.WHACK;
    public float attackTime;

    public ItemStackRenderState getMainHandItemState() {
        return this.mainArm == HumanoidArm.RIGHT ? this.rightHandItemState : this.leftHandItemState;
    }

    public ItemStack getMainHandItemStack() {
        return this.mainArm == HumanoidArm.RIGHT ? this.rightHandItemStack : this.leftHandItemStack;
    }

    public ItemStack getUseItemStackForArm(HumanoidArm $$0) {
        return $$0 == HumanoidArm.RIGHT ? this.rightHandItemStack : this.leftHandItemStack;
    }

    public float ticksUsingItem(HumanoidArm $$0) {
        return 0.0f;
    }

    public static void extractArmedEntityRenderState(LivingEntity $$0, ArmedEntityRenderState $$1, ItemModelResolver $$2, float $$3) {
        $$1.mainArm = $$0.getMainArm();
        ItemStack $$4 = $$0.getMainHandItem();
        $$1.swingAnimationType = $$4.getSwingAnimation().type();
        $$1.attackTime = $$0.getAttackAnim($$3);
        $$2.updateForLiving($$1.rightHandItemState, $$0.getItemHeldByArm(HumanoidArm.RIGHT), ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, $$0);
        $$2.updateForLiving($$1.leftHandItemState, $$0.getItemHeldByArm(HumanoidArm.LEFT), ItemDisplayContext.THIRD_PERSON_LEFT_HAND, $$0);
        $$1.leftHandItemStack = $$0.getItemHeldByArm(HumanoidArm.LEFT).copy();
        $$1.rightHandItemStack = $$0.getItemHeldByArm(HumanoidArm.RIGHT).copy();
    }
}
