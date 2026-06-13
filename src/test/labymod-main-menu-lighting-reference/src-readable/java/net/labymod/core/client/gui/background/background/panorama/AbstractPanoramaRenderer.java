package net.labymod.core.client.gui.background.panorama;

import net.labymod.api.LabyAPI;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.state.UVCoordinates;
import net.labymod.api.client.render.matrix.DefaultStackProvider;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.MinecraftTextures;
import net.labymod.api.laby3d.math.JomlMath;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.laby3d.pipeline.material.GuiMaterial;
import net.labymod.api.laby3d.shaders.block.DynamicTransformsUniformBlockData;
import net.labymod.api.laby3d.shaders.block.ProjectionUniformBlockData;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.util.math.MathHelper;
import net.labymod.laby3d.api.vertex.VertexConsumer;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/background/panorama/AbstractPanoramaRenderer.class */
public abstract class AbstractPanoramaRenderer implements PanoramaRenderer {
    protected static final boolean DISABLE_LEGACY_PANORAMA = true;
    private static final boolean NEW_UI_RENDERING = MinecraftVersions.V1_21_5.orNewer();
    private final Stack stack;
    private final LabyAPI labyAPI;
    private float time;

    public AbstractPanoramaRenderer(LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
        this.stack = NEW_UI_RENDERING ? Stack.create((StackProvider) new DefaultStackProvider()) : null;
    }

    @Override // net.labymod.core.client.gui.background.panorama.PanoramaRenderer
    public void render(ScreenContext context, float left, float top, float right, float bottom) {
        Stack stack = NEW_UI_RENDERING ? this.stack : context.stack();
        ScreenCanvas canvas = context.canvas();
        float tickDelta = context.getTickDelta();
        MinecraftTextures textures = this.labyAPI.minecraft().textures();
        ResourceLocation[] resourceLocations = textures.panoramaTextures();
        Matrix4f projectionMatrix = new Matrix4f();
        projectionMatrix.setPerspective(MathHelper.toRadiansFloat(85.0f), (right - left) / (bottom - top), 0.05f, 10.0f);
        stack.push();
        stack.identity();
        stack.rotate(180.0f, 1.0f, 0.0f, 0.0f);
        float time = this.time + tickDelta;
        this.time = time;
        float rotX = (float) ((Math.sin(time * 0.001f) * 5.0d) + 25.0d);
        float rotY = (-time) * 0.1f;
        for (int j = 0; j < 4; j++) {
            stack.push();
            float x = (((j % 2) / 2.0f) - 0.5f) / 256.0f;
            float y = (((j / 2) / 2.0f) - 0.5f) / 256.0f;
            stack.translate(x, y, 0.0f);
            stack.rotate(rotX, 1.0f, 0.0f, 0.0f);
            stack.rotate(rotY, 0.0f, 1.0f, 0.0f);
            Matrix4f modelViewMatrix = JomlMath.cloneMatrix(stack.getProvider().getPose());
            for (int side = 0; side < 6; side++) {
                float alpha = 1.0f / (j + 1);
                int finalSide = side;
                canvas.submitCustomGeometry(GuiMaterial.builder(RenderStates.PANORAMA).setTexture(0, resourceLocations[side]).build(), left, top, right - left, bottom - top, (pose, builder) -> {
                    if (finalSide == 0) {
                        addVertexWithUV(builder, -1.0f, -1.0f, 1.0f, 0.0f, 0.0f, alpha);
                        addVertexWithUV(builder, -1.0f, 1.0f, 1.0f, 0.0f, 1.0f, alpha);
                        addVertexWithUV(builder, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, alpha);
                        addVertexWithUV(builder, 1.0f, -1.0f, 1.0f, 1.0f, 0.0f, alpha);
                    }
                    if (finalSide == 1) {
                        addVertexWithUV(builder, 1.0f, -1.0f, 1.0f, 0.0f, 0.0f, alpha);
                        addVertexWithUV(builder, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, alpha);
                        addVertexWithUV(builder, 1.0f, 1.0f, -1.0f, 1.0f, 1.0f, alpha);
                        addVertexWithUV(builder, 1.0f, -1.0f, -1.0f, 1.0f, 0.0f, alpha);
                    }
                    if (finalSide == 2) {
                        addVertexWithUV(builder, 1.0f, -1.0f, -1.0f, 0.0f, 0.0f, alpha);
                        addVertexWithUV(builder, 1.0f, 1.0f, -1.0f, 0.0f, 1.0f, alpha);
                        addVertexWithUV(builder, -1.0f, 1.0f, -1.0f, 1.0f, 1.0f, alpha);
                        addVertexWithUV(builder, -1.0f, -1.0f, -1.0f, 1.0f, 0.0f, alpha);
                    }
                    if (finalSide == 3) {
                        addVertexWithUV(builder, -1.0f, -1.0f, -1.0f, 0.0f, 0.0f, alpha);
                        addVertexWithUV(builder, -1.0f, 1.0f, -1.0f, 0.0f, 1.0f, alpha);
                        addVertexWithUV(builder, -1.0f, 1.0f, 1.0f, 1.0f, 1.0f, alpha);
                        addVertexWithUV(builder, -1.0f, -1.0f, 1.0f, 1.0f, 0.0f, alpha);
                    }
                    if (finalSide == 4) {
                        addVertexWithUV(builder, -1.0f, -1.0f, -1.0f, 0.0f, 0.0f, alpha);
                        addVertexWithUV(builder, -1.0f, -1.0f, 1.0f, 0.0f, 1.0f, alpha);
                        addVertexWithUV(builder, 1.0f, -1.0f, 1.0f, 1.0f, 1.0f, alpha);
                        addVertexWithUV(builder, 1.0f, -1.0f, -1.0f, 1.0f, 0.0f, alpha);
                    }
                    if (finalSide == 5) {
                        addVertexWithUV(builder, -1.0f, 1.0f, 1.0f, 0.0f, 0.0f, alpha);
                        addVertexWithUV(builder, -1.0f, 1.0f, -1.0f, 0.0f, 1.0f, alpha);
                        addVertexWithUV(builder, 1.0f, 1.0f, -1.0f, 1.0f, 1.0f, alpha);
                        addVertexWithUV(builder, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, alpha);
                    }
                }, command -> {
                    ProjectionUniformBlockData projectionData = new ProjectionUniformBlockData(projectionMatrix);
                    command.bindUniformBlockData("Projection", projectionData);
                    DynamicTransformsUniformBlockData modelViewData = new DynamicTransformsUniformBlockData();
                    modelViewData.modelViewMatrix().set(modelViewMatrix);
                    command.bindUniformBlockData("DynamicTransforms", modelViewData);
                });
            }
            stack.pop();
        }
        stack.pop();
        ResourceLocation overlayTexture = this.labyAPI.minecraft().textures().panoramaOverlayTexture();
        if (overlayTexture != null) {
            canvas.submitGuiBlit(GuiMaterial.textured(RenderStates.GUI_TEXTURED, overlayTexture), left, top, left + right, top + bottom, UVCoordinates.of(0, 0, 16, 128, 16, 128), -1);
        }
    }

    private void addVertexWithUV(VertexConsumer consumer, float x, float y, float z, float u, float v, float alpha) {
        consumer.addVertex(x, y, z).setUv(u, v).setColor(1.0f, 1.0f, 1.0f, alpha);
    }
}
