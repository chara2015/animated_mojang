package net.labymod.core.main.animation.old.animations;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.entity.HumanoidModel;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.render.item.RenderFirstPersonItemInHandEvent;
import net.labymod.api.event.client.render.model.entity.HumanoidModelAnimateEvent;
import net.labymod.api.event.client.render.model.entity.HumanoidModelPoseAnimationEvent;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.user.permission.PermissionRegistry;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.core.main.animation.old.AbstractOldAnimation;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/animation/old/animations/BowOldAnimation.class */
public class BowOldAnimation extends AbstractOldAnimation {
    public static final String NAME = "bow";
    public static final boolean LEGACY_BLOCK_BUILD_THIRD_PERSON = MinecraftVersions.V1_12_2.orOlder();
    private final PermissionRegistry permissionRegistry;

    public BowOldAnimation() {
        super(NAME);
        this.permissionRegistry = Laby.references().permissionRegistry();
    }

    @Subscribe
    public void cancelVanillaArmPoseThirdPerson(HumanoidModelPoseAnimationEvent event) {
        ItemStack offHandItemStack;
        if (!isEnabled() || event.phase() != Phase.POST || LEGACY_BLOCK_BUILD_THIRD_PERSON) {
            return;
        }
        LivingEntity entity = event.livingEntity();
        LivingEntity.Hand handInUse = entity.getUsedItemHand();
        if (handInUse == LivingEntity.Hand.MAIN_HAND) {
            offHandItemStack = entity.getMainHandItemStack();
        } else {
            offHandItemStack = entity.getOffHandItemStack();
        }
        ItemStack itemInUse = offHandItemStack;
        if (!(entity instanceof Player) || !itemInUse.isBow() || entity.getItemUseDurationTicks() <= 0) {
            return;
        }
        event.setCancelled(true);
    }

    @Subscribe
    public void injectArmPoseThirdPerson(HumanoidModelAnimateEvent event) {
        ItemStack offHandItemStack;
        boolean z;
        if (!isEnabled() || event.phase() != Phase.POST || LEGACY_BLOCK_BUILD_THIRD_PERSON) {
            return;
        }
        LivingEntity entity = event.livingEntity();
        LivingEntity.Hand handInUse = entity.getUsedItemHand();
        if (handInUse == LivingEntity.Hand.MAIN_HAND) {
            offHandItemStack = entity.getMainHandItemStack();
        } else {
            offHandItemStack = entity.getOffHandItemStack();
        }
        ItemStack itemInUse = offHandItemStack;
        if (!(entity instanceof Player) || !itemInUse.isBow() || entity.getItemUseDurationTicks() <= 0) {
            return;
        }
        boolean mainHandIsRight = entity.isMainHandRight();
        if (mainHandIsRight) {
            z = handInUse == LivingEntity.Hand.MAIN_HAND;
        } else {
            z = handInUse == LivingEntity.Hand.OFF_HAND;
        }
        boolean handInUseRight = z;
        float mirror = handInUseRight ? 1.0f : -1.0f;
        HumanoidModel model = event.model();
        FloatVector3 head = model.getHead().getAnimationTransformation().getRotation();
        ModelPart mainArm = handInUseRight ? model.getRightArm() : model.getLeftArm();
        ModelPart supportiveArm = handInUseRight ? model.getLeftArm() : model.getRightArm();
        FloatVector3 mainRot = mainArm.getAnimationTransformation().getRotation();
        mainRot.setY(((-0.1f) * mirror) + head.getY());
        mainRot.setX((-1.5707964f) + head.getX());
        FloatVector3 secRot = supportiveArm.getAnimationTransformation().getRotation();
        secRot.setY((0.1f * mirror) + head.getY() + (0.4f * mirror));
        secRot.setX((-1.5707964f) + head.getX());
    }

    @Subscribe
    public void onRenderItemInHand(RenderFirstPersonItemInHandEvent event) {
        if (event.phase() != RenderFirstPersonItemInHandEvent.TransformPhase.PRE_RENDER || !isEnabled()) {
            return;
        }
        RenderFirstPersonItemInHandEvent.AnimationType type = event.animationType();
        if (type != RenderFirstPersonItemInHandEvent.AnimationType.BOW) {
            return;
        }
        Stack stack = event.stack();
        if (event.isUsingItem()) {
            if (GeneralItemPostureOldAnimation.LEGACY_PVP) {
                stack.translate(-0.080625f, 0.11625f, -0.11125f);
                stack.rotate(0.08f, 1.0f, 0.0f, 0.0f);
                stack.rotate(0.97f, 0.0f, 1.0f, 0.0f);
                stack.rotate(0.16f, 0.0f, 0.0f, 1.0f);
                return;
            }
            stack.translate(-0.046875f, 0.0375f, -0.0125f);
            stack.rotate(2.0f, 0.0f, 0.0f, 1.0f);
            return;
        }
        GeneralItemPostureOldAnimation.apply(stack, event.hand());
    }

    @Override // net.labymod.core.main.animation.old.OldAnimation
    public boolean isEnabled() {
        return this.permissionRegistry.isPermissionEnabled("animations", this.classicPvPConfig.oldBow());
    }
}
