package net.minecraft.client.renderer.entity.state;

import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.monster.illager.AbstractIllager;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/entity/state/IllagerRenderState.class */
public class IllagerRenderState extends UndeadRenderState {
    public boolean isRiding;
    public boolean isAggressive;
    public HumanoidArm mainArm = HumanoidArm.RIGHT;
    public AbstractIllager.IllagerArmPose armPose = AbstractIllager.IllagerArmPose.NEUTRAL;
    public int maxCrossbowChargeDuration;
    public float ticksUsingItem;
    public float attackAnim;
}
