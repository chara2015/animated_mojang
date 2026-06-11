package net.labymod.core.main.animation.old.animations.legacy;

import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.render.entity.layers.ItemInHandLayerRenderEvent;
import net.labymod.api.event.client.render.item.RenderFirstPersonItemInHandEvent;
import net.labymod.api.event.client.render.model.entity.HumanoidModelAnimateEvent;
import net.labymod.api.event.client.render.model.entity.HumanoidModelPoseAnimationEvent;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.core.main.animation.old.AbstractOldAnimation;
import net.labymod.core.main.animation.old.BlockingSwordAccessor;
import net.labymod.core.main.animation.old.animations.SwordOldAnimation;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/animation/old/animations/legacy/LegacySwordOldAnimation.class */
public class LegacySwordOldAnimation extends AbstractOldAnimation implements BlockingSwordAccessor {
    public static final String NAME = "legacy_sword";
    public static final boolean LEGACY_PVP = MinecraftVersions.V1_8_9.orOlder();

    public LegacySwordOldAnimation() {
        super(NAME);
    }

    @Subscribe
    public void injectBlockAnimationInFirstPerson(RenderFirstPersonItemInHandEvent event) {
        if (!isEnabled() || event.phase() != RenderFirstPersonItemInHandEvent.TransformPhase.PRE_RENDER || !isBlockingWithSword(event.player())) {
            return;
        }
        Stack stack = event.stack();
        stack.translate(-0.02f, 0.02f, 0.15f);
    }

    @Subscribe
    public void cancelVanillaArmPoseThirdPerson(HumanoidModelPoseAnimationEvent event) {
        if (!isEnabled() || event.phase() != Phase.PRE) {
            return;
        }
        LivingEntity entity = event.livingEntity();
        if (!(entity instanceof Player)) {
            return;
        }
        Player player = (Player) entity;
        if (event.handSide() == LivingEntity.HandSide.RIGHT && isBlockingWithSword(player)) {
            event.setCancelled(true);
        }
    }

    @Subscribe
    public void injectArmPoseThirdPerson(HumanoidModelAnimateEvent event) {
        if (!isEnabled() || event.phase() != Phase.POST) {
            return;
        }
        LivingEntity entity = event.livingEntity();
        if (!(entity instanceof Player)) {
            return;
        }
        Player player = (Player) entity;
        if (!isBlockingWithSword(player)) {
            return;
        }
        ModelPart rightArm = event.model().getRightArm();
        FloatVector3 rotation = rightArm.getAnimationTransformation().getRotation();
        rotation.add(SwordOldAnimation.ARM_ROTATION_BLOCKING);
    }

    @Subscribe
    public void modifyItemsInHandInThirdPerson(ItemInHandLayerRenderEvent event) {
        if (!isEnabled() || event.phase() != Phase.PRE) {
            return;
        }
        LivingEntity entity = event.livingEntity();
        if (!(entity instanceof Player)) {
            return;
        }
        Player player = (Player) entity;
        if (!isBlockingWithSword(player)) {
            return;
        }
        Stack stack = event.stack();
        stack.rotate(70.0f, 0.0f, 0.0f, -1.0f);
        stack.rotate(35.0f, 0.0f, -1.0f, 0.0f);
        stack.rotate(20.0f, 1.0f, 0.0f, 0.0f);
    }

    @Override // net.labymod.core.main.animation.old.OldAnimation
    public boolean isEnabled() {
        return this.permissionRegistry.isPermissionEnabled("animations", this.classicPvPConfig.oldSword()) && LEGACY_PVP;
    }

    @Override // net.labymod.core.main.animation.old.BlockingSwordAccessor
    public boolean isBlockingWithSword(Player player) {
        ItemStack itemStack = player.getMainHandItemStack();
        return itemStack.isSword() && player.getItemUseDurationTicks() > 0;
    }
}
