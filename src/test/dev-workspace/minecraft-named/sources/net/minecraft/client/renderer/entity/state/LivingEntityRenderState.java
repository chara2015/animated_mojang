package net.minecraft.client.renderer.entity.state;

import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.block.SkullBlock;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/entity/state/LivingEntityRenderState.class */
public class LivingEntityRenderState extends EntityRenderState {
    public float bodyRot;
    public float yRot;
    public float xRot;
    public float deathTime;
    public float walkAnimationPos;
    public float walkAnimationSpeed;
    public float ticksSinceKineticHitFeedback;
    public boolean isUpsideDown;
    public boolean isFullyFrozen;
    public boolean isBaby;
    public boolean isInWater;
    public boolean isAutoSpinAttack;
    public boolean hasRedOverlay;
    public boolean isInvisibleToPlayer;
    public Direction bedOrientation;
    public float wornHeadAnimationPos;
    public SkullBlock.Type wornHeadType;
    public ResolvableProfile wornHeadProfile;
    public float scale = 1.0f;
    public float ageScale = 1.0f;
    public Pose pose = Pose.STANDING;
    public final ItemStackRenderState headItem = new ItemStackRenderState();

    public boolean hasPose(Pose $$0) {
        return this.pose == $$0;
    }
}
