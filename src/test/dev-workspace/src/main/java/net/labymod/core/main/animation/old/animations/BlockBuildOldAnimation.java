package net.labymod.core.main.animation.old.animations;

import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.options.MinecraftInputMapping;
import net.labymod.api.client.options.MinecraftOptions;
import net.labymod.api.client.particle.ParticleController;
import net.labymod.api.client.world.phys.hit.BlockHitResult;
import net.labymod.api.client.world.phys.hit.HitResult;
import net.labymod.api.client.world.phys.hit.HitResultController;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.input.MouseButtonEvent;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.api.event.client.render.item.RenderFirstPersonItemInHandEvent;
import net.labymod.api.user.permission.PermissionRegistry;
import net.labymod.core.main.animation.old.AbstractOldAnimation;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/animation/old/animations/BlockBuildOldAnimation.class */
public class BlockBuildOldAnimation extends AbstractOldAnimation {
    public static final String NAME = "block_build";
    private final HitResultController hitResultController;
    private final ParticleController particleController;
    private final PermissionRegistry permissionRegistry;
    private final Minecraft minecraft;
    private boolean interruptedAttack;
    private boolean interruptedItemUse;

    public BlockBuildOldAnimation() {
        super(NAME);
        this.interruptedAttack = false;
        this.interruptedItemUse = false;
        this.hitResultController = Laby.references().hitResultController();
        this.particleController = Laby.references().particleController();
        this.permissionRegistry = Laby.references().permissionRegistry();
        this.minecraft = Laby.labyAPI().minecraft();
    }

    @Subscribe
    public void onRenderFirstPersonItemInHand(RenderFirstPersonItemInHandEvent event) {
        if (!isEnabled() || event.phase() != RenderFirstPersonItemInHandEvent.TransformPhase.HEAD) {
            return;
        }
        if (event.itemStack().isSword()) {
            event.setAttackWhileItemUse(true);
            MinecraftOptions options = this.minecraft.options();
            event.setUsingItem(options.useItemInput().isDown() && !this.minecraft.isLastBlockUsed());
        }
        if (event.isUsingItem()) {
            event.setAttackWhileItemUse(true);
        }
    }

    @Subscribe
    public void onMouseButton(MouseButtonEvent event) {
        if (!isEnabled()) {
            return;
        }
        updateInputs();
    }

    @Subscribe(126)
    public void onTick(GameTickEvent event) {
        if (!isEnabled() || event.phase() != Phase.PRE) {
            return;
        }
        updateInputs();
        ClientPlayer clientPlayer = this.minecraft.getClientPlayer();
        if (clientPlayer != null && !this.minecraft.isDestroying()) {
            if ((this.interruptedAttack || this.interruptedItemUse) && this.minecraft.isMouseLocked() && this.hitResultController.isCrosshairOverBlock()) {
                HitResult result = this.hitResultController.getResult();
                if (result instanceof BlockHitResult) {
                    BlockHitResult blockHitResult = (BlockHitResult) result;
                    this.particleController.crackBlock(blockHitResult.getBlockPosition(), blockHitResult.getBlockDirection());
                }
                clientPlayer.swingArm(LivingEntity.Hand.MAIN_HAND, true);
            }
        }
    }

    private void updateInputs() {
        MinecraftOptions minecraftOptions = this.minecraft.options();
        ClientPlayer clientPlayer = this.minecraft.getClientPlayer();
        MinecraftInputMapping attackInput = minecraftOptions.attackInput();
        MinecraftInputMapping useItemInput = minecraftOptions.useItemInput();
        boolean attackActuallyDown = attackInput.isActuallyDown();
        boolean useItemActuallyDown = useItemInput.isActuallyDown();
        if (!attackActuallyDown) {
            this.interruptedAttack = false;
        }
        if (!useItemActuallyDown) {
            this.interruptedItemUse = false;
        }
        if (clientPlayer == null || !useItemActuallyDown || !attackActuallyDown || !this.minecraft.isMouseLocked() || !this.hitResultController.isCrosshairOverBlock() || !clientPlayer.canDestroyBlocks()) {
            return;
        }
        if (this.minecraft.isDestroying()) {
            attackInput.unpress();
            continueAttack();
            this.interruptedAttack = true;
        } else if (!this.interruptedAttack && !attackInput.isDown()) {
            attackInput.press();
            continueAttack();
            this.interruptedAttack = true;
        }
    }

    private void continueAttack() {
        boolean leftClick = this.minecraft.minecraftWindow().currentScreen() == null && this.minecraft.options().attackInput().isDown() && this.minecraft.isMouseLocked();
        this.minecraft.updateBlockBreak(leftClick);
    }

    @Override // net.labymod.core.main.animation.old.OldAnimation
    public boolean isEnabled() {
        return this.permissionRegistry.isPermissionEnabled("blockbuild", this.classicPvPConfig.oldBlockBuild());
    }
}
