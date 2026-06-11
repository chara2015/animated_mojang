package net.labymod.core.main.animation.old.animations;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.options.MinecraftOptions;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.entity.HumanoidModel;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.render.entity.layers.ItemInHandLayerRenderEvent;
import net.labymod.api.event.client.render.item.RenderFirstPersonItemInHandEvent;
import net.labymod.api.event.client.render.model.entity.HumanoidModelAnimateEvent;
import net.labymod.api.event.client.render.model.entity.HumanoidModelPoseAnimationEvent;
import net.labymod.api.event.client.render.overlay.HudWidgetDropzoneElementShiftEvent;
import net.labymod.api.event.client.render.overlay.IngameOverlayElementRenderEvent;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.core.main.animation.old.AbstractOldAnimation;
import net.labymod.core.main.animation.old.BlockingSwordAccessor;
import net.labymod.core.main.animation.old.OldAnimation;
import net.labymod.core.main.animation.old.animations.legacy.LegacySwordOldAnimation;
import org.jetbrains.annotations.ApiStatus;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/animation/old/animations/SwordOldAnimation.class */
public class SwordOldAnimation extends AbstractOldAnimation implements BlockingSwordAccessor {
    public static final String NAME = "sword";
    public static final FloatVector3 ARM_ROTATION_IDLE = new FloatVector3(-0.31415927f, 0.0f, 0.0f);
    public static final FloatVector3 ARM_ROTATION_BLOCKING = ARM_ROTATION_IDLE.copy().multiply(3.0f);
    private final Map<UUID, Boolean> blocking;
    private final Minecraft minecraft;
    private final OldAnimation blockBuildAnimation;

    public SwordOldAnimation() {
        super(NAME);
        this.minecraft = Laby.labyAPI().minecraft();
        this.blocking = new HashMap();
        this.blockBuildAnimation = getAnimation(BlockBuildOldAnimation.NAME);
    }

    @Subscribe
    public void injectBlockAnimationInFirstPerson(RenderFirstPersonItemInHandEvent event) {
        if (!isEnabled() || event.phase() != RenderFirstPersonItemInHandEvent.TransformPhase.PRE_RENDER || event.hand() != LivingEntity.Hand.MAIN_HAND || !isBlockingWithSword(event.player())) {
            return;
        }
        Stack stack = event.stack();
        int mirror = event.side() == LivingEntity.HandSide.RIGHT ? 1 : -1;
        stack.translate((-0.0375f) * mirror, 0.0625f, 0.125f);
        stack.translate((-0.1f) * mirror, 0.05f, 0.0f);
        stack.rotate(55.0f, 0.0f, 0.0f, mirror);
        stack.rotate(90.0f, 0.0f, mirror, 0.0f);
        stack.rotate(20.0f, -1.0f, 0.0f, 0.0f);
        stack.rotate(12.5f, 0.0f, mirror, 0.0f);
        stack.rotate(15.0f, 0.0f, 0.0f, (-1) * mirror);
    }

    @Subscribe
    public void cancelVanillaBlockAnimation(RenderFirstPersonItemInHandEvent event) {
        if (!isEnabled() || event.phase() != RenderFirstPersonItemInHandEvent.TransformPhase.HEAD || event.hand() != LivingEntity.Hand.MAIN_HAND || !isBlockingWithSword(event.player())) {
            return;
        }
        event.setUsingItem(false);
    }

    @Subscribe
    public void cancelSwingVanillaAnimationInFirstPerson(RenderFirstPersonItemInHandEvent event) {
        if (!isEnabled() || event.phase() != RenderFirstPersonItemInHandEvent.TransformPhase.PRE_ARM_TRANSFORM || event.hand() != LivingEntity.Hand.MAIN_HAND || !isBlockingWithSword(event.player())) {
            return;
        }
        boolean isRightHand = event.side() == LivingEntity.HandSide.RIGHT;
        int mirror = isRightHand ? 1 : -1;
        float attackProgress = event.getAttackProgress();
        float x = (-0.4f) * MathHelper.sin(Math.sqrt(attackProgress) * 3.1415927410125732d);
        float y = 0.2f * MathHelper.sin(Math.sqrt(attackProgress) * 6.2831854820251465d);
        float z = (-0.2f) * MathHelper.sin(attackProgress * 3.1415927f);
        event.stack().translate(mirror * (-x), -y, -z);
    }

    @Subscribe
    public void cancelShieldInOffHandFirstPerson(RenderFirstPersonItemInHandEvent event) {
        if (isEnabled() && event.phase() == RenderFirstPersonItemInHandEvent.TransformPhase.HEAD && event.hand() == LivingEntity.Hand.OFF_HAND && event.itemStack().isShield()) {
            event.setRenderItem(false);
        }
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
        ItemStack mainHandItemStack = player.getMainHandItemStack();
        ItemStack offHandItemStack = player.getOffHandItemStack();
        if (isMainHand(player, event.handSide())) {
            if (mainHandItemStack.isSword()) {
                event.setCancelled(true);
            }
            if (isBlockingWithShield(player)) {
                event.setCancelled(true);
                return;
            }
            return;
        }
        if (offHandItemStack.isShield()) {
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
        if (!player.getMainHandItemStack().isSword() && !isBlockingWithShield(player)) {
            return;
        }
        boolean isMainRight = isMainHand(player, LivingEntity.HandSide.RIGHT);
        HumanoidModel model = event.model();
        ModelPart mainArm = isMainRight ? model.getRightArm() : model.getLeftArm();
        FloatVector3 rotation = mainArm.getAnimationTransformation().getRotation();
        rotation.add(isBlockingWithSword(player) ? ARM_ROTATION_BLOCKING : ARM_ROTATION_IDLE);
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
        if (isMainHand(player, event.handSide())) {
            if (isBlockingWithSword(player)) {
                Stack stack = event.stack();
                int mirror = event.handSide() == LivingEntity.HandSide.RIGHT ? 1 : -1;
                stack.translate((-0.14f) * mirror, -0.12f, 0.14f);
                stack.translate((-0.1f) * mirror, 0.05f, 0.0f);
                stack.rotate(50.0f, 0.0f, 0.0f, mirror);
                stack.rotate(10.0f, 1.0f, 0.0f, 0.0f);
                stack.rotate(60.0f, 0.0f, mirror, 0.0f);
                stack.rotate(14.96f, 1.0f, 0.0f, 0.0f);
                stack.rotate(3.9f, 0.0f, -mirror, 0.0f);
                stack.rotate(6.23f, 0.0f, 0.0f, mirror);
                return;
            }
            return;
        }
        if (event.itemStack().isShield()) {
            event.setCancelled(true);
        }
    }

    @Subscribe
    public void onIngameOverlayElementRender(IngameOverlayElementRenderEvent event) {
        if (!isEnabled() || event.phase() != Phase.PRE) {
            return;
        }
        IngameOverlayElementRenderEvent.OverlayElementType type = event.elementType();
        ClientPlayer player = this.minecraft.getClientPlayer();
        if (player == null) {
            return;
        }
        if ((type == IngameOverlayElementRenderEvent.OverlayElementType.OFFHAND_ITEM || type == IngameOverlayElementRenderEvent.OverlayElementType.OFFHAND_TEXTURE) && player.getOffHandItemStack().isShield()) {
            event.setCancelled(true);
        }
    }

    @Subscribe
    public void onHudWidgetDropzoneElementShift(HudWidgetDropzoneElementShiftEvent event) {
        if (isEnabled() && event.isOffHandSide() && event.itemStack().isShield()) {
            event.setCancelled(true);
        }
    }

    @Override // net.labymod.core.main.animation.old.OldAnimation
    public boolean isEnabled() {
        return this.permissionRegistry.isPermissionEnabled("animations", this.classicPvPConfig.oldSword()) && !LegacySwordOldAnimation.LEGACY_PVP;
    }

    @Override // net.labymod.core.main.animation.old.BlockingSwordAccessor
    public boolean isBlockingWithSword(Player player) {
        if (!player.getMainHandItemStack().isSword()) {
            return false;
        }
        if (this.minecraft.getClientPlayer() == player) {
            MinecraftOptions options = this.minecraft.options();
            boolean isUsingItem = options.useItemInput().isDown();
            boolean isAttacking = options.attackInput().isDown();
            boolean blockBuild = isAttacking && this.blockBuildAnimation.isEnabled();
            return isUsingItem && (!this.minecraft.isLastBlockUsed() || blockBuild);
        }
        if (player.getItemUseDurationTicks() > 0) {
            return !this.minecraft.isLastBlockUsed();
        }
        Boolean blocking = this.blocking.get(player.getUniqueId());
        return blocking != null && blocking.booleanValue();
    }

    public boolean isBlockingWithShield(Player player) {
        return player.getOffHandItemStack().isShield() && player.getItemUseDurationTicks() > 0 && player.getUsedItemHand() == LivingEntity.Hand.OFF_HAND;
    }

    public boolean isMainHand(LivingEntity entity, LivingEntity.HandSide side) {
        return entity.isMainHandRight() == (side == LivingEntity.HandSide.RIGHT);
    }

    @ApiStatus.Internal
    public void setBlockingState(UUID uuid, boolean blocking) {
        this.blocking.put(uuid, Boolean.valueOf(blocking));
    }
}
