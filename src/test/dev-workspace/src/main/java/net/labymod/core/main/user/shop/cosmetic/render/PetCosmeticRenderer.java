package net.labymod.core.main.user.shop.cosmetic.render;

import net.labymod.api.client.entity.EntityPose;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.animation.ModelAnimation;
import net.labymod.api.client.render.model.animation.meta.AnimationTrigger;
import net.labymod.api.profiler.frame.ProfilerScope;
import net.labymod.api.user.GameUser;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.main.user.DefaultGameUser;
import net.labymod.core.main.user.shop.AnimationContainer;
import net.labymod.core.main.user.shop.cosmetic.CosmeticAssets;
import net.labymod.core.main.user.shop.cosmetic.CosmeticDefinition;
import net.labymod.core.main.user.shop.cosmetic.PetCosmeticTypeData;
import net.labymod.core.main.user.shop.cosmetic.pet.FlyingPetAI;
import net.labymod.core.main.user.shop.cosmetic.pet.PetAI;
import net.labymod.core.main.user.shop.cosmetic.pet.PetDataStorage;
import net.labymod.core.main.user.shop.cosmetic.state.PetCosmeticState;
import net.labymod.core.main.user.shop.item.ItemUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/render/PetCosmeticRenderer.class */
public class PetCosmeticRenderer extends AbstractCosmeticRenderer {
    @Override // net.labymod.core.main.user.shop.cosmetic.render.CosmeticRenderer
    public void render(CosmeticDefinition definition, RenderContext context, Stack stack) {
        AnimationTrigger animationTrigger;
        PetCosmeticTypeData typeData = (PetCosmeticTypeData) definition.typeData(PetCosmeticTypeData.class);
        if (typeData == null) {
            return;
        }
        PetAI petAI = typeData.petAI();
        float renderTick = context.renderTick();
        float partialTicks = context.partialTicks();
        PetDataStorage petDataStorage = getPetDataStorage(context, definition);
        if (petDataStorage == null) {
            return;
        }
        petDataStorage.setRightShoulder(context.metadata().isRightSide());
        petDataStorage.getAnimationController().tickProvider(() -> {
            return renderTick;
        });
        petDataStorage.getOwnerPosition().set(context.player().position());
        if (partialTicks != 1.0f || petDataStorage.isAttachedToOwner()) {
            if (petDataStorage.getLastPartialTicks() > partialTicks) {
                ProfilerScope ignored = ProfilerScope.of("pet_ai_tick");
                try {
                    petAI.tick(context.playerModel(), context.player(), petDataStorage, renderTick, partialTicks);
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
            petDataStorage.setLastPartialTicks(partialTicks);
            if (ItemUtil.isSkipFlyingPets() && !petAI.canAttach()) {
                return;
            }
            stack.push();
            try {
                petAI.earlyRender(context.playerModel(), context.player(), petDataStorage, stack, partialTicks);
                if (!petDataStorage.getAnimationController().isPlaying()) {
                    if (petDataStorage.isMoving() && !petDataStorage.isFakeIdle()) {
                        animationTrigger = AnimationTrigger.MOVING;
                    } else {
                        animationTrigger = AnimationTrigger.IDLE;
                    }
                    AnimationTrigger trigger = animationTrigger;
                    AnimationContainer animations = getAnimationContainer(definition);
                    ModelAnimation animation = animations.handleAnimationTrigger(trigger, petDataStorage.getAnimationController(), context.player());
                    if (animation != null) {
                        petDataStorage.setAttached(petAI.canAttach() && trigger.isIdle());
                    }
                }
                FloatVector3 previousRotation = petDataStorage.getPreviousRotation();
                FloatVector3 rotation = petDataStorage.getRotation();
                float rotX = MathHelper.lerp(rotation.getX(), previousRotation.getX(), partialTicks);
                float rotY = MathHelper.lerp(rotation.getY(), previousRotation.getY(), partialTicks);
                float rotZ = MathHelper.lerp(rotation.getZ(), previousRotation.getZ(), partialTicks);
                if (!petDataStorage.isAttachedToOwner() && (petAI instanceof FlyingPetAI)) {
                    stack.rotate(rotX, 1.0f, 0.0f, 0.0f);
                    stack.rotate(rotY, 0.0f, 1.0f, 0.0f);
                    stack.rotate(rotZ, 0.0f, 0.0f, 1.0f);
                    boolean fallFlying = context.player().entityPose() == EntityPose.FALL_FLYING;
                    long since = TimeUtil.getMillis() - petDataStorage.getFallFlyingChangedAt();
                    float t = Math.min(1.0f, since / 400.0f);
                    float factor = fallFlying ? t : 1.0f - t;
                    if (factor > 0.0f) {
                        stack.rotate((-90.0f) * factor, 1.0f, 0.0f, 0.0f);
                    }
                }
                if (definition.itemModel() != null && definition.itemModel().getModel() != null) {
                    AbstractCosmeticRenderer.translateOffset(stack, context.metadata(), true);
                    ProfilerScope ignored2 = ProfilerScope.of("render_with_effects");
                    try {
                        renderWithEffects(definition, context, stack, petDataStorage.isRightShoulder(), petDataStorage.getAnimationController());
                        if (ignored2 != null) {
                            ignored2.close();
                        }
                    } catch (Throwable th3) {
                        if (ignored2 != null) {
                            try {
                                ignored2.close();
                            } catch (Throwable th4) {
                                th3.addSuppressed(th4);
                            }
                        }
                        throw th3;
                    }
                }
            } finally {
                stack.pop();
            }
        }
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.render.CosmeticRenderer
    public boolean isVisibleInFirstPerson(CosmeticDefinition definition) {
        return false;
    }

    private AnimationContainer getAnimationContainer(CosmeticDefinition definition) {
        CosmeticAssets assets = definition.assets();
        if (assets != null) {
            return assets.animationContainer();
        }
        return definition.animationContainer();
    }

    private PetDataStorage getPetDataStorage(RenderContext context, CosmeticDefinition definition) {
        GameUser gameUserUser = context.user();
        if (gameUserUser instanceof DefaultGameUser) {
            DefaultGameUser defaultUser = (DefaultGameUser) gameUserUser;
            PetCosmeticState state = (PetCosmeticState) defaultUser.getCosmeticStateStorage().getState(definition.id(), PetCosmeticState.class);
            if (state != null) {
                return state.petData();
            }
            return defaultUser.getUserItemStorage().getPetDataStorage(definition.id());
        }
        return null;
    }
}
