package net.labymod.core.main.user.shop.cosmetic.render;

import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.gfx.pipeline.renderer.shader.ShaderUtil;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.box.ModelBox;
import net.labymod.api.client.render.model.box.ModelBoxQuad;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.MinecraftCamera;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.laby3d.render.queue.SubmissionOrders;
import net.labymod.api.laby3d.textures.TextureBindingSet;
import net.labymod.api.user.GameUser;
import net.labymod.api.util.math.Direction;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.FloatVector4;
import net.labymod.core.client.render.model.DefaultModel;
import net.labymod.core.client.render.model.DefaultModelPart;
import net.labymod.core.client.render.model.box.DefaultModelBoxQuad;
import net.labymod.core.main.user.DefaultGameUser;
import net.labymod.core.main.user.shop.cosmetic.CosmeticDefinition;
import net.labymod.core.main.user.shop.cosmetic.state.EyelidsCosmeticState;
import net.labymod.core.main.user.shop.item.geometry.effect.effects.color.ColorGeometryEffect;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;
import net.labymod.core.main.user.shop.item.renderer.ItemModel;
import net.labymod.core.main.user.shop.item.renderer.ItemModelCompiler;
import net.labymod.laby3d.api.vertex.VertexConsumer;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/render/EyelidsCosmeticRenderer.class */
public class EyelidsCosmeticRenderer extends AbstractCosmeticRenderer {
    public static final int EYELIDS_ID = 36;
    private static final ResourceLocation EYELIDS_SLEEP_LOCATION = ResourceLocation.create("labymod", "textures/cosmetics/eyelids_sleep.png");
    private static final int BLINK_SPEED = 100;
    private static final int MAX_IDLE_DURATION = 240000;
    private static final float IDLE_CLOSE_SPEED = 5000.0f;

    public static Model buildGeometry(ItemModel itemModel) {
        Model model = new DefaultModel();
        ModelPart part = new DefaultModelPart();
        part.addBox(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0625f, false);
        ModelBox modelBox = (ModelBox) part.getBoxes().getFirst();
        for (ModelBoxQuad quad : modelBox.getQuads()) {
            if (quad instanceof DefaultModelBoxQuad) {
                DefaultModelBoxQuad defaultQuad = (DefaultModelBoxQuad) quad;
                if (defaultQuad.getDirection() != Direction.NORTH) {
                    quad.setVisible(false);
                }
            }
        }
        part.setTextureOffset(0, 0);
        part.setTextureSize(4, 2);
        itemModel.loadEffects(List.of(new ColorGeometryEffect("0", part)));
        model.addChild("eyelid", part);
        return model;
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.render.CosmeticRenderer
    public void render(CosmeticDefinition definition, RenderContext context, Stack stack) {
        EyelidsCosmeticState eyelidsState;
        GameUser gameUserUser = context.user();
        if (!(gameUserUser instanceof DefaultGameUser)) {
            return;
        }
        DefaultGameUser defaultUser = (DefaultGameUser) gameUserUser;
        ModelPart head = context.playerModel().getHead();
        if (head.isInvisible() || (eyelidsState = (EyelidsCosmeticState) defaultUser.getCosmeticStateStorage().getState(definition.id(), EyelidsCosmeticState.class)) == null) {
            return;
        }
        EyelidsCosmeticState.AnimationData animationData = eyelidsState.animationData();
        ItemMetadata metadata = context.metadata();
        stack.push();
        head.getAnimationTransformation().transform(stack);
        FloatVector4 size = metadata.getSize();
        float x = size.getX();
        float y = size.getY();
        float width = size.getZ();
        float height = size.getW();
        long blinkTimeLeft = animationData.getBlinkTimeLeft();
        float frameTime = (animationData.getBlinkPauseDuration() - blinkTimeLeft) - 100;
        float animation = Math.min(Math.abs(frameTime), 100.0f);
        float percentage = metadata.canBlink() ? 1.0f - (animation / 100.0f) : 0.0f;
        long idleDuration = animationData.getIdleDuration(context.player());
        boolean isIdle = idleDuration > 240000 && metadata.canSleep();
        if (isIdle) {
            percentage = 2.0E-4f * Math.min(IDLE_CLOSE_SPEED, idleDuration - 240000);
        }
        float scalePercentage = (0.0625f * (-4.0f)) - (percentage == 1.0f ? 0.001f : 0.0055f);
        stack.translate(0.0625f * (-4.0f), 0.0625f * (-8.0f), scalePercentage);
        animationData.setLastRenderedPercentage(percentage);
        float smooth = -MathHelper.cos(1.5707963267948966d + (1.5707963267948966d * ((double) percentage)));
        if (percentage != 0.0f) {
            int index = 0;
            while (index < 2) {
                stack.push();
                definition.setPosition(index == 0 ? CosmeticDefinition.Position.LEFT : CosmeticDefinition.Position.RIGHT);
                stack.translate(((index == 0 ? x : (8.0f - x) - width) * 0.0625f) - 0.001f, (y * 0.0625f) - 0.001f, -0.001f);
                stack.scale(width + ((0.001f * 2.0f) / 0.0625f), (height * smooth) + ((0.001f * 2.0f) / 0.0625f), 1.0f);
                fixOversizeEyelids(stack, 0.0625f);
                if (definition.itemModel() != null) {
                    renderModel(definition, context, stack, 1.0d, null);
                }
                stack.pop();
                index++;
            }
        }
        stack.pop();
        if (!isIdle) {
            return;
        }
        renderSleepingParticles(context, definition, stack, 0.0625f, idleDuration);
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.render.CosmeticRenderer
    public boolean isVisibleInFirstPerson(CosmeticDefinition definition) {
        return false;
    }

    private void fixOversizeEyelids(Stack stack, float scale) {
        stack.scale(0.875f, 0.875f, 1.0f);
        stack.translate(0.07125f * scale, 0.07125f * scale, 0.0f);
    }

    private void renderSleepingParticles(RenderContext context, CosmeticDefinition definition, Stack stack, float scale, long idleDuration) {
        stack.push();
        context.playerModel().getHead().getAnimationTransformation().transform(stack);
        float pitch = context.physicData() != null ? context.physicData().getPitch() : 0.0f;
        stack.translate(scale * 5.0f, scale * (-4.0f), scale * (-((pitch / 15.0f) + 4.0f)));
        int renderedAmount = 4 - Math.min(4, ((int) (idleDuration - 240000)) / SubmissionOrders.DEBUG);
        int startTimeOffset = (4 - 1) * SubmissionOrders.DEBUG;
        for (int index = renderedAmount; index < 4 + 1; index++) {
            int sleepTimeFrame = ((((int) idleDuration) + (index * SubmissionOrders.DEBUG)) + startTimeOffset) % (4 * SubmissionOrders.DEBUG);
            float progress = (1.0f / (4 * SubmissionOrders.DEBUG)) * sleepTimeFrame;
            stack.push();
            stack.translate(progress / 2.0f, (-progress) / 2.0f, 0.0f);
            stack.rotate(((MathHelper.cos(progress * 5.0f) * 20.0f) - 10.0f) * (index % 2 == 0 ? -1 : 1), 0.0f, 0.0f, 1.0f);
            float particleScale = (scale / 2.0f) + (progress / 8.0f);
            float opacity = 1.0f - Math.abs((progress - 0.5f) * 2.0f);
            float finalScale = (-particleScale) / 2.0f;
            MinecraftCamera camera = Laby.labyAPI().minecraft().getCamera();
            if (camera != null) {
                stack.rotate(camera.getYaw(), 0.0f, 1.0f, 0.0f);
                stack.rotate(camera.getPitch(), 1.0f, 0.0f, 0.0f);
                stack.rotate(180.0f, 0.0f, 1.0f, 0.0f);
            }
            Laby.references().geometrySubmitter().submitCustomGeometry(stack, RenderStates.SIMPLE_LEVEL_GEOMETRY, TextureBindingSet.builder().setTexture(0, EYELIDS_SLEEP_LOCATION).build(), (pose, consumer) -> {
                renderImmediateParticle(pose, consumer, finalScale, finalScale, particleScale, particleScale, opacity);
            });
            stack.pop();
        }
        stack.pop();
    }

    private void renderImmediateParticle(Matrix4f pose, VertexConsumer consumer, float x, float y, float width, float height, float opacity) {
        float right = x + width;
        float bottom = y + height;
        writeVertex(consumer, pose, x, y, 0.0f, 0.0f, opacity);
        writeVertex(consumer, pose, x, bottom, 0.0f, 1.0f, opacity);
        writeVertex(consumer, pose, right, bottom, 1.0f, 1.0f, opacity);
        writeVertex(consumer, pose, right, y, 1.0f, 0.0f, opacity);
    }

    private void writeVertex(VertexConsumer consumer, Matrix4f pose, float x, float y, float u, float v, float opacity) {
        Vector3f pos = pose.transformPosition(x, y, 0.0f, new Vector3f());
        if (ShaderUtil.isShaderSelected()) {
            ItemModelCompiler.writeVertex(ItemModelCompiler.CompilerMode.VANILLA, consumer, pos.x(), pos.y(), pos.z(), 1.0f, 1.0f, 1.0f, 1.0f, u, v, RenderEnvironmentContext.NO_OVERLAY, RenderEnvironmentContext.FULL_BRIGHT, 1.0f, 1.0f, 1.0f, false, 0, 0.0f);
        } else {
            consumer.addVertex(pos.x(), pos.y(), pos.z()).setColor(1.0f, 1.0f, 1.0f, opacity).setUv(u, v).setPackedLight(RenderEnvironmentContext.FULL_BRIGHT);
        }
    }
}
