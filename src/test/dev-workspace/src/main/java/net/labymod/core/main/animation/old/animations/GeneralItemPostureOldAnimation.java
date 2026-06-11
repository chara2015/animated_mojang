package net.labymod.core.main.animation.old.animations;

import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.render.item.RenderFirstPersonItemInHandEvent;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.core.main.animation.old.AbstractOldAnimation;
import net.labymod.core.main.animation.old.BlockingSwordAccessor;
import net.labymod.core.main.animation.old.animations.legacy.LegacySwordOldAnimation;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/animation/old/animations/GeneralItemPostureOldAnimation.class */
public class GeneralItemPostureOldAnimation extends AbstractOldAnimation {
    public static final String NAME = "general_item_posture";
    public static final boolean LEGACY_PVP = MinecraftVersions.V1_8_9.orOlder();
    private final BlockingSwordAccessor swordAnimation;

    public GeneralItemPostureOldAnimation() {
        super(NAME);
        this.swordAnimation = (BlockingSwordAccessor) getAnimation(LEGACY_PVP ? LegacySwordOldAnimation.NAME : SwordOldAnimation.NAME);
    }

    @Subscribe
    public void onRenderItemInHand(RenderFirstPersonItemInHandEvent event) {
        if (event.phase() != RenderFirstPersonItemInHandEvent.TransformPhase.POST_ATTACK_TRANSFORM || !isEnabled()) {
            return;
        }
        ItemStack itemStack = event.itemStack();
        if (!itemStack.isItem() || itemStack.isFishingTool() || itemStack.isBow()) {
            return;
        }
        if ((itemStack.isSword() && this.swordAnimation.isBlockingWithSword(event.player()) && this.swordAnimation.isEnabled()) || itemStack.isFood() || itemStack.isFishingTool()) {
            return;
        }
        Stack stack = event.stack();
        apply(stack, event.hand());
    }

    @Override // net.labymod.core.main.animation.old.OldAnimation
    public boolean isEnabled() {
        return this.permissionRegistry.isPermissionEnabled("animations", this.classicPvPConfig.oldItemPosture());
    }

    public static void apply(Stack stack, LivingEntity.Hand hand) {
        if (LEGACY_PVP) {
            stack.translate(0.0f, -0.025f, -0.04375f);
            stack.rotate(4.0f, 0.0f, 1.0f, 0.0f);
        } else {
            int position = hand == LivingEntity.Hand.MAIN_HAND ? 1 : -1;
            stack.translate((-0.025f) * position, -0.005f, 0.0f);
            stack.rotate(2.0f, 0.0f, position, 0.0f);
        }
    }
}
