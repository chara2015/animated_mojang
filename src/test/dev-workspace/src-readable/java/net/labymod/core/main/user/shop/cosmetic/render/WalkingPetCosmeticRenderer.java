package net.labymod.core.main.user.shop.cosmetic.render;

import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.client.render.font.ComponentRenderer;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.render.font.text.TextDrawMode;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.render.model.animation.ModelAnimation;
import net.labymod.api.client.render.model.animation.meta.AnimationMeta;
import net.labymod.api.client.render.model.animation.meta.AnimationTrigger;
import net.labymod.api.client.world.MinecraftCamera;
import net.labymod.api.laby3d.math.JomlMath;
import net.labymod.api.profiler.frame.ProfilerScope;
import net.labymod.api.user.GameUser;
import net.labymod.api.util.ArrayUtil;
import net.labymod.api.util.Color;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.client.entity.player.DummyPlayer;
import net.labymod.core.main.user.DefaultGameUser;
import net.labymod.core.main.user.shop.cosmetic.CosmeticDefinition;
import net.labymod.core.main.user.shop.cosmetic.pet.ai.PetBehavior;
import net.labymod.core.main.user.shop.cosmetic.pet.ai.Transform;
import net.labymod.core.main.user.shop.cosmetic.state.WalkingPetCosmeticState;
import net.labymod.core.main.user.shop.item.geometry.AnimationStorage;
import net.labymod.core.main.user.shop.item.geometry.effect.PhysicData;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;
import net.labymod.laby3d.api.math.Axis;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/render/WalkingPetCosmeticRenderer.class */
public class WalkingPetCosmeticRenderer extends AbstractCosmeticRenderer {
    private static final float NAME_TAG_OFFSET = 5.5f;
    private static final float NAME_TAG_SCALE = 0.5f;
    private static final float NAME_TAG_VISIBLE_DISTANCE = (float) Math.pow(16.0d, 2.0d);
    private static final DoubleVector3 DEFAULT_CAMERA_POSITION = new DoubleVector3();
    private static final Quaternionf DEFAULT_CAMERA_ROTATION = Axis.YP.rotationDegrees(180.0f);

    @Override // net.labymod.core.main.user.shop.cosmetic.render.CosmeticRenderer
    public void render(CosmeticDefinition definition, RenderContext context, Stack stack) {
        GameUser gameUserUser = context.user();
        if (!(gameUserUser instanceof DefaultGameUser)) {
            return;
        }
        DefaultGameUser defaultUser = (DefaultGameUser) gameUserUser;
        WalkingPetCosmeticState walkingState = (WalkingPetCosmeticState) defaultUser.getCosmeticStateStorage().getState(definition.id(), WalkingPetCosmeticState.class);
        if (walkingState == null || walkingState.behavior() == null) {
            return;
        }
        PetBehavior behavior = walkingState.behavior();
        AnimationStorage storage = walkingState.animationStorage();
        Player player = context.player();
        ItemMetadata metadata = context.metadata();
        float partialTicks = context.partialTicks();
        stack.push();
        try {
            if (!walkingState.isFirstTeleported()) {
                behavior.setPosition(player.position().toFloatVector3().add(1.0f, 0.0f, 1.0f));
                walkingState.setFirstTeleported(true);
            }
            MinecraftCamera camera = Laby.labyAPI().minecraft().getCamera();
            DoubleVector3 cameraPosition = camera == null ? DEFAULT_CAMERA_POSITION : camera.renderPosition();
            DoubleVector3 currentPosition = behavior.position();
            DoubleVector3 previousPosition = behavior.previousPosition();
            double x = currentPosition.lerpX(previousPosition, partialTicks);
            double y = currentPosition.lerpY(previousPosition, partialTicks);
            double z = currentPosition.lerpZ(previousPosition, partialTicks);
            double renderX = x - cameraPosition.getX();
            double renderY = y - cameraPosition.getY();
            double renderZ = z - cameraPosition.getZ();
            boolean isInGui = player instanceof DummyPlayer;
            if (isInGui) {
                stack.scale(-1.0f, 1.0f, 1.0f);
                stack.rotate(180.0f, 0.0f, 1.0f, 0.0f);
                stack.rotate(180.0f, 1.0f, 0.0f, 0.0f);
                stack.translate(-0.75f, -1.5f, 0.0f);
            } else {
                stack.translate(renderX, renderY, renderZ);
            }
            stack.scale(-1.0f, -1.0f, 1.0f);
            if (!isInGui) {
                stack.rotate(180.0f, 0.0f, 1.0f, 0.0f);
            }
            stack.push();
            try {
                Transform transform = behavior.transform();
                if (!isInGui) {
                    transform.rotate(stack, partialTicks);
                }
                boolean crouching = player.isCrouching();
                if (crouching && !storage.isLastSneaking()) {
                    triggerAnimation(definition, storage, AnimationTrigger.START_SNEAKING, player);
                } else if (!crouching && storage.isLastSneaking()) {
                    triggerAnimation(definition, storage, AnimationTrigger.STOP_SNEAKING, player);
                }
                storage.setLastSneaking(crouching);
                boolean walking = behavior.isWalking();
                if (walking && !storage.isLastMoving()) {
                    triggerAnimation(definition, storage, AnimationTrigger.START_MOVING, player);
                } else if (!walking && storage.isLastMoving()) {
                    triggerAnimation(definition, storage, AnimationTrigger.STOP_MOVING, player);
                }
                storage.setLastMoving(walking);
                if (TimeUtil.getMillis() > storage.getLastTriggerMillis() + 500 || !storage.getController().isPlaying()) {
                    AnimationTrigger trigger = AnimationTrigger.getMovingOrIdle(walking, crouching);
                    triggerAnimation(definition, storage, trigger, player);
                }
                updateBoundingBox(definition, behavior);
                ProfilerScope ignored = ProfilerScope.of("render_with_effects");
                try {
                    renderWithEffects(definition, context, stack, false, storage.getController());
                    if (ignored != null) {
                        ignored.close();
                    }
                    stack.pop();
                    if (isNameTagVisible(metadata) && Laby.labyAPI().config().ingame().cosmetics().showWalkingPetNametags().get().booleanValue()) {
                        AxisAlignedBoundingBox box = behavior.boundingBox();
                        DoubleVector3 position = behavior.position();
                        DoubleVector3 v = new DoubleVector3(position.getX(), position.getY(), position.getZ());
                        v.add(-(box.getXSize() / 2.0d), 0.0d, -(box.getZSize() / 2.0d));
                        if (shouldRenderNameTag(player, behavior, box, v, partialTicks)) {
                            renderNameTag(definition, metadata, stack, camera, context.lightCoords());
                        }
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
            } finally {
                stack.pop();
            }
        } catch (Throwable th3) {
            stack.pop();
            throw th3;
        }
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.render.CosmeticRenderer
    public void tick(CosmeticDefinition definition, Player player, GameUser user, PhysicData physicData) {
        if (!(user instanceof DefaultGameUser)) {
            return;
        }
        DefaultGameUser defaultUser = (DefaultGameUser) user;
        WalkingPetCosmeticState walkingState = (WalkingPetCosmeticState) defaultUser.getCosmeticStateStorage().getState(definition.id(), WalkingPetCosmeticState.class);
        if (walkingState == null || walkingState.behavior() == null) {
            return;
        }
        walkingState.setOwner(player);
        PetBehavior behavior = walkingState.behavior();
        AnimationStorage storage = walkingState.animationStorage();
        ModelAnimation playing = storage.getController().getPlaying();
        boolean lockRotation = playing != null && Boolean.TRUE.equals(playing.getMeta(AnimationMeta.LOCK_ROTATION));
        behavior.lookController().setRotationLocked(lockRotation);
        ProfilerScope ignored = ProfilerScope.of("behavior_tick");
        try {
            behavior.tick();
            if (ignored != null) {
                ignored.close();
            }
            if (physicData != null) {
                int petIndex = physicData.getAndIncrementWalkingPetIndex();
                int maxPets = defaultUser.getUserItemStorage().getMaxPets();
                behavior.updatePetIndex(petIndex, maxPets);
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

    @Override // net.labymod.core.main.user.shop.cosmetic.render.CosmeticRenderer
    public boolean isVisibleInFirstPerson(CosmeticDefinition definition) {
        return true;
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.render.AbstractCosmeticRenderer
    protected boolean isInRenderDistance(CosmeticDefinition definition) {
        return true;
    }

    private void triggerAnimation(CosmeticDefinition definition, AnimationStorage storage, AnimationTrigger trigger, Player player) {
        storage.setLastTriggerMillis(TimeUtil.getMillis());
        definition.animationContainer().handleAnimationTrigger(trigger, storage.getController(), player);
    }

    private void updateBoundingBox(CosmeticDefinition definition, PetBehavior behavior) {
        if (definition.itemModel() == null || definition.itemModel().getModel() == null) {
            return;
        }
        Model model = definition.itemModel().getModel();
        float width = model.getWidth() * 0.0625f;
        float height = model.getHeight() * 0.0625f;
        float depth = model.getDepth() * 0.0625f;
        float scale = (float) definition.details().getScale();
        behavior.boundingBox().set(0.0d, 0.0d, 0.0d, width * scale, height * scale, depth * scale);
    }

    private boolean isNameTagVisible(ItemMetadata metadata) {
        return metadata.isNameTagEnabled() && !metadata.getNametag().isEmpty();
    }

    private boolean shouldRenderNameTag(Player player, PetBehavior behavior, AxisAlignedBoundingBox box, DoubleVector3 v, float partialTicks) {
        ClientPlayer selfPlayer;
        if ((player instanceof DummyPlayer) || (selfPlayer = Laby.labyAPI().minecraft().getClientPlayer()) == null) {
            return false;
        }
        if (behavior.position().distanceSquared(selfPlayer.position().toDoubleVector3()) > NAME_TAG_VISIBLE_DISTANCE && selfPlayer != player) {
            return false;
        }
        return box.move(v).rayIntersects(selfPlayer.eyePosition(), selfPlayer.perspectiveVector(partialTicks));
    }

    private void renderNameTag(CosmeticDefinition definition, ItemMetadata metadata, Stack stack, @Nullable MinecraftCamera camera, int lightCoords) {
        stack.push();
        Quaternionf rotation = camera == null ? DEFAULT_CAMERA_ROTATION : camera.cameraRotation();
        stack.rotate(JomlMath.getYaw(rotation), 0.0f, 1.0f, 0.0f);
        renderTag(definition, metadata, stack, lightCoords, camera == null ? 0.0f : camera.getPitch());
        stack.pop();
    }

    private void renderTag(CosmeticDefinition definition, ItemMetadata metadata, Stack stack, int lightCoords, float pitch) {
        float height;
        RenderEnvironmentContext envContext = Laby.references().renderEnvironmentContext();
        int prevPackedLight = envContext.getPackedLight();
        envContext.setPackedLight(lightCoords);
        ComponentRenderer componentRenderer = Laby.references().componentRenderer();
        RenderableComponent tagContent = RenderableComponent.of(Component.text(metadata.getNametag()));
        stack.scale(0.0625f);
        stack.scale(0.5f);
        Model model = definition.itemModel() != null ? definition.itemModel().getModel() : null;
        if (model != null) {
            height = (float) (((double) model.getHeight()) * definition.details().getScale());
        } else {
            height = 0.0f;
        }
        float modelHeight = height;
        stack.translate((-tagContent.getWidth()) / 2.0f, -((tagContent.getHeight() / 0.5f) + modelHeight + 11.0f), 0.0f);
        stack.rotate(pitch, 1.0f, 0.0f, 0.0f);
        float width = tagContent.getWidth() + Math.abs(-1.0f);
        float borderWidth = width + Math.abs(-1.0f);
        int color = ColorFormat.ARGB32.pack(0, 0, 0, 100);
        RectangleRenderer rectangleRenderer = Laby.references().rectangleRenderer();
        ((RectangleRenderer) ((RectangleRenderer) rectangleRenderer.pos(-1.0f, -1.0f)).size(width, tagContent.getHeight() + Math.abs(-1.0f)).color(color)).render(stack);
        Color[] colors = metadata.getColors();
        Color borderColor = (Color) ArrayUtil.getOrDefault(colors, colors.length - 1, null);
        if (metadata.isBorderEnabled() && borderColor != null) {
            int borderArgb = borderColor.get();
            ((RectangleRenderer) ((RectangleRenderer) rectangleRenderer.pos((-1.0f) - 1.0f, (-1.0f) - 1.0f)).size(borderWidth + 1.0f, 1.0f).color(borderArgb)).render(stack);
            ((RectangleRenderer) ((RectangleRenderer) rectangleRenderer.pos((-1.0f) - 1.0f, tagContent.getHeight())).size(borderWidth + 1.0f, 1.0f).color(borderArgb)).render(stack);
            ((RectangleRenderer) ((RectangleRenderer) rectangleRenderer.pos((-1.0f) - 1.0f, -1.0f)).size(1.0f, tagContent.getHeight() + Math.abs(-1.0f)).color(borderArgb)).render(stack);
            ((RectangleRenderer) ((RectangleRenderer) rectangleRenderer.pos(width - 1.0f, -1.0f)).size(1.0f, tagContent.getHeight() + Math.abs(-1.0f)).color(borderArgb)).render(stack);
        }
        stack.push();
        stack.translate(0.0f, 0.0f, 0.03f);
        componentRenderer.builder().text(tagContent).pos(0.0f, 0.0f).color(((Color) ArrayUtil.getOrDefault(colors, colors.length - 2, Color.WHITE)).get()).drawMode(TextDrawMode.POLYGON_OFFSET).shadow(false).render(stack);
        stack.pop();
        envContext.setPackedLight(prevPackedLight);
    }
}
