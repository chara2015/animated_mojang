package net.labymod.core.main.user.shop.cosmetic.render;

import net.laby.lib.cosmetics.AttachmentPoint;
import net.laby.lib.cosmetics.MirrorType;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.animation.AnimationController;
import net.labymod.api.client.render.model.animation.meta.AnimationTrigger;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.profiler.frame.ProfilerScope;
import net.labymod.api.user.GameUser;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.client.entity.player.DummyPlayerModel;
import net.labymod.core.main.user.DefaultGameUser;
import net.labymod.core.main.user.shop.AnimationContainer;
import net.labymod.core.main.user.shop.cosmetic.CosmeticAssets;
import net.labymod.core.main.user.shop.cosmetic.CosmeticDefinition;
import net.labymod.core.main.user.shop.cosmetic.state.CosmeticState;
import net.labymod.core.main.user.shop.item.CosmeticDetails;
import net.labymod.core.main.user.shop.item.geometry.AnimationStorage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/render/AttachmentCosmeticRenderer.class */
public class AttachmentCosmeticRenderer extends AbstractCosmeticRenderer {
    /* JADX WARN: Removed duplicated region for block: B:31:0x0084  */
    @Override // net.labymod.core.main.user.shop.cosmetic.render.CosmeticRenderer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void render(net.labymod.core.main.user.shop.cosmetic.CosmeticDefinition r7, net.labymod.core.main.user.shop.cosmetic.render.RenderContext r8, net.labymod.api.client.render.matrix.Stack r9) {
        /*
            Method dump skipped, instruction units count: 237
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: net.labymod.core.main.user.shop.cosmetic.render.AttachmentCosmeticRenderer.render(net.labymod.core.main.user.shop.cosmetic.CosmeticDefinition, net.labymod.core.main.user.shop.cosmetic.render.RenderContext, net.labymod.api.client.render.matrix.Stack):void");
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.render.CosmeticRenderer
    public boolean isVisibleInFirstPerson(CosmeticDefinition definition) {
        return definition.details().getAttachmentPoint() == AttachmentPoint.ARM;
    }

    private void renderSidedGeometry(CosmeticDefinition definition, RenderContext context, Stack stack, boolean rightSide) {
        float f;
        float f2;
        CosmeticDetails details = definition.details();
        AttachmentPoint attachmentPoint = details.getAttachmentPoint();
        PlayerModel playerModel = context.playerModel();
        ModelPart armOrLeg = getArmOrLeg(playerModel, attachmentPoint, rightSide);
        if (armOrLeg != null && armOrLeg.isInvisible()) {
            return;
        }
        ModelPart headOrBody = getHeadOrBody(playerModel, attachmentPoint);
        if (headOrBody != null && headOrBody.isInvisible()) {
            return;
        }
        stack.push();
        float renderTick = context.renderTick();
        float walkingSpeed = context.walkingSpeed();
        if (armOrLeg != null) {
            boolean isArm = attachmentPoint == AttachmentPoint.ARM;
            boolean slim = context.isSlim();
            FloatVector3 translation = armOrLeg.getModelPartTransform().getTranslation();
            float translationY = translation.getY() + (playerModel instanceof DummyPlayerModel ? 6.0f : 0.0f);
            stack.translate(translation.getX() * 0.0625f, translationY * 0.0625f, translation.getZ() * 0.0625f);
            armOrLeg.getAnimationTransformation().transform(stack);
            if (rightSide) {
                if (details.getMirrorType() == MirrorType.DUPLICATE) {
                    if (isArm) {
                        if (slim) {
                            f2 = -0.0625f;
                        } else {
                            f2 = -0.125f;
                        }
                        stack.translate(f2, 0.0f, 0.0f);
                    }
                } else if (details.getMirrorType() == MirrorType.MIRROR) {
                    stack.scale(-1.0f, 1.0f, 1.0f);
                } else {
                    stack.rotate(180.0f, 0.0f, 1.0f, 0.0f);
                }
            }
            if (isArm) {
                if (slim) {
                    f = 0.03125f;
                } else {
                    f = 0.0625f;
                }
                stack.translate(f, -0.125f, 0.0f);
            }
        }
        if (headOrBody != null) {
            headOrBody.getAnimationTransformation().transform(stack);
        }
        AnimationStorage animationStorage = null;
        GameUser gameUserUser = context.user();
        if (gameUserUser instanceof DefaultGameUser) {
            DefaultGameUser defaultUser = (DefaultGameUser) gameUserUser;
            CosmeticState cosmeticState = defaultUser.getCosmeticStateStorage().getState(definition.id());
            if (cosmeticState != null) {
                animationStorage = cosmeticState.animationStorage();
            }
        }
        if (animationStorage != null) {
            AnimationController controller = animationStorage.getController();
            controller.tickProvider(() -> {
                return renderTick;
            });
            boolean crouching = context.isCrouching();
            boolean moving = ((double) walkingSpeed) > 0.1d;
            if (context.firstPerson() && !animationStorage.isLastFirstPerson()) {
                triggerAnimation(definition, AnimationTrigger.START_FIRST, controller, context.player());
            } else if (!context.firstPerson() && animationStorage.isLastFirstPerson()) {
                triggerAnimation(definition, AnimationTrigger.STOP_FIRST, controller, context.player());
            }
            animationStorage.setLastFirstPerson(context.firstPerson());
            boolean firstPerson = animationStorage.isLastFirstPerson();
            Player player = context.player();
            if (crouching && !animationStorage.isLastSneaking()) {
                triggerAnimation(definition, AnimationTrigger.getSneakingToggle(firstPerson, true), controller, player);
            } else if (!crouching && animationStorage.isLastSneaking()) {
                triggerAnimation(definition, AnimationTrigger.getSneakingToggle(firstPerson, false), controller, player);
            }
            animationStorage.setLastSneaking(crouching);
            if (moving && !animationStorage.isLastMoving()) {
                triggerAnimation(definition, AnimationTrigger.getMovingToggle(firstPerson, true), controller, player);
            } else if (!moving && animationStorage.isLastMoving()) {
                triggerAnimation(definition, AnimationTrigger.getMovingToggle(firstPerson, false), controller, player);
            }
            animationStorage.setLastMoving(moving);
            if (TimeUtil.getMillis() > animationStorage.getLastTriggerMillis() + 500 || !controller.isPlaying()) {
                animationStorage.setLastTriggerMillis(TimeUtil.getMillis());
                AnimationTrigger trigger = AnimationTrigger.getMovingOrIdle(moving, crouching, firstPerson);
                triggerAnimation(definition, trigger, controller, player);
            }
            if (definition.itemModel() != null && definition.itemModel().getModel() != null) {
                AbstractCosmeticRenderer.translateOffset(stack, context.metadata(), false);
                ProfilerScope ignored = ProfilerScope.of("render_with_effects");
                try {
                    renderWithEffects(definition, context, stack, rightSide, controller);
                    if (ignored != null) {
                        ignored.close();
                    }
                } catch (Throwable th) {
                    if (ignored != null) {
                        try {
                            ignored.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            }
        }
        stack.pop();
    }

    private AnimationContainer getAnimationContainer(CosmeticDefinition definition) {
        CosmeticAssets assets = definition.assets();
        if (assets != null) {
            return assets.animationContainer();
        }
        return definition.animationContainer();
    }

    private void triggerAnimation(CosmeticDefinition definition, AnimationTrigger trigger, AnimationController controller, Player player) {
        getAnimationContainer(definition).handleAnimationTrigger(trigger, controller, player);
    }

    /* JADX INFO: renamed from: net.labymod.core.main.user.shop.cosmetic.render.AttachmentCosmeticRenderer$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/render/AttachmentCosmeticRenderer$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$laby$lib$cosmetics$AttachmentPoint = new int[AttachmentPoint.values().length];

        static {
            try {
                $SwitchMap$net$laby$lib$cosmetics$AttachmentPoint[AttachmentPoint.ARM.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$laby$lib$cosmetics$AttachmentPoint[AttachmentPoint.LEG.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$laby$lib$cosmetics$AttachmentPoint[AttachmentPoint.BODY.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$laby$lib$cosmetics$AttachmentPoint[AttachmentPoint.HEAD.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    private ModelPart getArmOrLeg(PlayerModel model, AttachmentPoint point, boolean rightSide) {
        switch (AnonymousClass1.$SwitchMap$net$laby$lib$cosmetics$AttachmentPoint[point.ordinal()]) {
            case 1:
                return model.getArm(rightSide);
            case 2:
                return model.getLeg(rightSide);
            default:
                return null;
        }
    }

    private ModelPart getHeadOrBody(PlayerModel model, AttachmentPoint point) {
        switch (AnonymousClass1.$SwitchMap$net$laby$lib$cosmetics$AttachmentPoint[point.ordinal()]) {
            case 3:
                return model.getBody();
            case 4:
                return model.getHead();
            default:
                return null;
        }
    }
}
