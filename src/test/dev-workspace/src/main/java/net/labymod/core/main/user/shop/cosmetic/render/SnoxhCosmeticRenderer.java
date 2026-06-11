package net.labymod.core.main.user.shop.cosmetic.render;

import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.gfx.pipeline.renderer.shader.ShaderUtil;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.laby3d.pipeline.ItemRenderStates;
import net.labymod.api.laby3d.textures.TextureBindingSet;
import net.labymod.api.user.GameUser;
import net.labymod.api.util.Color;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.FloatVector4;
import net.labymod.core.main.user.DefaultGameUser;
import net.labymod.core.main.user.shop.cosmetic.CosmeticDefinition;
import net.labymod.core.main.user.shop.cosmetic.state.EyelidsCosmeticState;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;
import net.labymod.core.main.user.shop.item.renderer.ItemModelCompiler;
import net.labymod.laby3d.api.vertex.VertexConsumer;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/render/SnoxhCosmeticRenderer.class */
public class SnoxhCosmeticRenderer extends AbstractCosmeticRenderer {
    public static final int SNOXH_ID = 32;
    private static final int ITERATIONS = 2;
    private final Vertex[] vertices = {new Vertex(), new Vertex(), new Vertex(), new Vertex()};

    @Override // net.labymod.core.main.user.shop.cosmetic.render.CosmeticRenderer
    public void render(CosmeticDefinition definition, RenderContext context, Stack stack) {
        Color finalColor;
        if (context.playerModel().getHead().isInvisible()) {
            return;
        }
        ItemMetadata metadata = context.metadata();
        int brightness = metadata.getBrightness();
        int minColor = Math.min(brightness, 0);
        float colorCos = MathHelper.cos(context.player().getAgeTick() / 30.0f) * (brightness - minColor);
        float animation = (minColor + Math.abs(colorCos)) / 255.0f;
        GameUser gameUserUser = context.user();
        if (gameUserUser instanceof DefaultGameUser) {
            DefaultGameUser defaultUser = (DefaultGameUser) gameUserUser;
            EyelidsCosmeticState eyelidsState = (EyelidsCosmeticState) defaultUser.getCosmeticStateStorage().getState(36, EyelidsCosmeticState.class);
            if (eyelidsState != null) {
                animation *= 1.0f - eyelidsState.animationData().getLastRenderedPercentage();
            }
        }
        FloatVector4 size = metadata.getSize();
        float x = size.getX();
        float y = size.getY();
        float width = size.getZ();
        float height = size.getW();
        boolean overlappingLeft = (x + width) - 1.0f == 3.0f;
        stack.push();
        context.playerModel().getHead().getAnimationTransformation().transform(stack);
        stack.translate((-4.0f) * 0.0625f, (-8.0f) * 0.0625f, -0.251f);
        stack.translate(0.0f, 0.0f, animation == 0.0f ? 0.001f : 0.0f);
        stack.scale(0.0625f, 0.0625f, 0.0625f);
        Color[] colors = metadata.getColors();
        if (colors != null && colors.length != 0) {
            finalColor = colors[0];
        } else {
            finalColor = Color.WHITE;
        }
        float finalAnimation = animation;
        Color color = finalColor;
        Laby.references().geometrySubmitter().submitCustomGeometry(stack, ItemRenderStates.EMISSIVE_TRANSLUCENT_COSMETICS, TextureBindingSet.builder().setTexture(0, Textures.WHITE).build(), (pose, consumer) -> {
            int side = 0;
            while (side < 2) {
                for (int iteration = 0; iteration < 2; iteration++) {
                    if (side == 0) {
                        if (metadata.isRightVisible()) {
                            renderGlowingEye(pose, consumer, side == 0 ? x : (8.0f - x) - width, y, width, height, color, finalAnimation, overlappingLeft, side == 0);
                        }
                    } else if (metadata.isLeftVisible()) {
                    }
                }
                side++;
            }
        });
        stack.pop();
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.render.CosmeticRenderer
    public boolean isVisibleInFirstPerson(CosmeticDefinition definition) {
        return false;
    }

    private void renderGlowingEye(Matrix4f pose, VertexConsumer consumer, float x, float y, float width, float height, Color color, float alpha, boolean overlapping, boolean isLeftSide) {
        float red = color.getRed() / 255.0f;
        float green = color.getGreen() / 255.0f;
        float blue = color.getBlue() / 255.0f;
        float middleLeft = x + 0.0f;
        float middleTop = y + 0.0f;
        float middleRight = (x + width) - 0.0f;
        float middleBottom = (y + height) - 0.0f;
        renderGradient(consumer, pose, middleRight, middleTop - 0.6f, 0.0f, middleLeft, middleTop - 0.6f, 0.0f, middleLeft, middleTop, alpha, middleRight, middleTop, alpha, red, green, blue);
        renderGradient(consumer, pose, middleRight, middleBottom, alpha, middleRight + 0.6f, middleBottom, 0.0f, middleRight + 0.6f, middleTop, 0.0f, middleRight, middleTop, alpha, red, green, blue);
        renderGradient(consumer, pose, middleLeft, middleTop, alpha, middleLeft - 0.6f, middleTop, 0.0f, middleLeft - 0.6f, middleBottom, 0.0f, middleLeft, middleBottom, alpha, red, green, blue);
        renderGradient(consumer, pose, middleLeft, middleBottom + 0.6f, 0.0f, middleRight, middleBottom + 0.6f, 0.0f, middleRight, middleBottom, alpha, middleLeft, middleBottom, alpha, red, green, blue);
        renderGradient(consumer, pose, middleRight, middleTop, 0.4f, middleLeft, middleTop, 0.4f, middleLeft, middleBottom, 0.4f, middleRight, middleBottom, 0.4f, red, green, blue);
        float cornerRadiusWidth = 0.6f * MathHelper.cos(MathHelper.toRadiansFloat(45.0f));
        float cornerRadiusHeight = 0.6f * MathHelper.sin(MathHelper.toRadiansFloat(45.0f));
        float lowCornerRadiusWidth = 0.6f * MathHelper.cos(MathHelper.toRadiansFloat(22.5f));
        float lowCornerRadiusHeight = 0.6f * MathHelper.sin(MathHelper.toRadiansFloat(22.5f));
        float highCornerRadiusWidth = 0.6f * MathHelper.cos(MathHelper.toRadiansFloat(67.5f));
        float highCornerRadiusHeight = 0.6f * MathHelper.sin(MathHelper.toRadiansFloat(67.5f));
        if (!overlapping || isLeftSide == overlapping) {
            renderGradient(consumer, pose, middleLeft - 0.6f, middleTop, 0.0f, middleLeft - lowCornerRadiusWidth, middleTop - lowCornerRadiusHeight, 0.0f, middleLeft - cornerRadiusWidth, middleTop - cornerRadiusHeight, 0.0f, middleLeft, middleTop, alpha, red, green, blue);
            renderGradient(consumer, pose, middleLeft - cornerRadiusWidth, middleTop - cornerRadiusHeight, 0.0f, middleLeft - highCornerRadiusWidth, middleTop - highCornerRadiusHeight, 0.0f, middleLeft, middleTop - 0.6f, 0.0f, middleLeft, middleTop, alpha, red, green, blue);
        }
        if (!overlapping || isLeftSide != overlapping) {
            renderGradient(consumer, pose, middleRight + 0.6f, middleTop, 0.0f, middleRight + lowCornerRadiusWidth, middleTop - lowCornerRadiusHeight, 0.0f, middleRight + cornerRadiusWidth, middleTop - cornerRadiusHeight, 0.0f, middleRight, middleTop, alpha, red, green, blue);
            renderGradient(consumer, pose, middleRight + cornerRadiusWidth, middleTop - cornerRadiusHeight, 0.0f, middleRight + highCornerRadiusWidth, middleTop - highCornerRadiusHeight, 0.0f, middleRight, middleTop - 0.6f, 0.0f, middleRight, middleTop, alpha, red, green, blue);
        }
        if (!overlapping || isLeftSide == overlapping) {
            renderGradient(consumer, pose, middleLeft - 0.6f, middleBottom, 0.0f, middleLeft - lowCornerRadiusWidth, middleBottom + lowCornerRadiusHeight, 0.0f, middleLeft - cornerRadiusWidth, middleBottom + cornerRadiusHeight, 0.0f, middleLeft, middleBottom, alpha, red, green, blue);
            renderGradient(consumer, pose, middleLeft - cornerRadiusWidth, middleBottom + cornerRadiusHeight, 0.0f, middleLeft - highCornerRadiusWidth, middleBottom + highCornerRadiusHeight, 0.0f, middleLeft, middleBottom + 0.6f, 0.0f, middleLeft, middleBottom, alpha, red, green, blue);
        }
        if (!overlapping || isLeftSide != overlapping) {
            renderGradient(consumer, pose, middleRight + 0.6f, middleBottom, 0.0f, middleRight + lowCornerRadiusWidth, middleBottom + lowCornerRadiusHeight, 0.0f, middleRight + cornerRadiusWidth, middleBottom + cornerRadiusHeight, 0.0f, middleRight, middleBottom, alpha, red, green, blue);
            renderGradient(consumer, pose, middleRight + cornerRadiusWidth, middleBottom + cornerRadiusHeight, 0.0f, middleRight + highCornerRadiusWidth, middleBottom + highCornerRadiusHeight, 0.0f, middleRight, middleBottom + 0.6f, 0.0f, middleRight, middleBottom, alpha, red, green, blue);
        }
    }

    private void renderGradient(VertexConsumer consumer, Matrix4f pose, float x1, float y1, float alpha1, float x2, float y2, float alpha2, float x3, float y3, float alpha3, float x4, float y4, float alpha4, float red, float green, float blue) {
        this.vertices[0].setVertex(x1, y1, red, green, blue, alpha1);
        this.vertices[1].setVertex(x2, y2, red, green, blue, alpha2);
        this.vertices[2].setVertex(x3, y3, red, green, blue, alpha3);
        this.vertices[3].setVertex(x4, y4, red, green, blue, alpha4);
        for (Vertex vertex : this.vertices) {
            vertex.fillBuffer(consumer, pose);
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/render/SnoxhCosmeticRenderer$Vertex.class */
    private static class Vertex {
        private static final Vector2f UV = new Vector2f();
        private float x;
        private float y;
        private float red;
        private float green;
        private float blue;
        private float alpha;

        private Vertex() {
        }

        public void setVertex(float x, float y, float red, float green, float blue, float alpha) {
            this.x = x;
            this.y = y;
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.alpha = alpha;
        }

        public void fillBuffer(VertexConsumer consumer, Matrix4f pose) {
            Vector3f position = pose.transformPosition(this.x, this.y, 0.0f, new Vector3f());
            ItemModelCompiler.writeVertex(ShaderUtil.isShaderSelected() ? ItemModelCompiler.CompilerMode.VANILLA : ItemModelCompiler.CompilerMode.IMMEDIATE, consumer, position.x(), position.y(), position.z(), this.red, this.green, this.blue, this.alpha, UV.x(), UV.y(), RenderEnvironmentContext.NO_OVERLAY, RenderEnvironmentContext.FULL_BRIGHT, 1.0f, 1.0f, 1.0f, true, 0, 0.0f);
        }
    }
}
