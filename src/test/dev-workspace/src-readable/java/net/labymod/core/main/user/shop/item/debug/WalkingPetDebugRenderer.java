package net.labymod.core.main.user.shop.item.debug;

import java.util.concurrent.atomic.AtomicInteger;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.gfx.pipeline.renderer.mesh.MeshRenderer;
import net.labymod.api.client.gfx.pipeline.renderer.text.TextRenderer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.world.MinecraftCamera;
import net.labymod.api.debug.DebugRegistry;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.laby3d.vertex.VertexDescriptions;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.core.main.user.shop.cosmetic.CosmeticDefinition;
import net.labymod.core.main.user.shop.cosmetic.pet.ai.PetBehavior;
import net.labymod.laby3d.api.buffers.BufferBuilder;
import net.labymod.laby3d.api.pipeline.DrawingMode;
import net.labymod.laby3d.api.vertex.VertexConsumer;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/item/debug/WalkingPetDebugRenderer.class */
public class WalkingPetDebugRenderer {
    private static final int HITBOX_COLOR = ColorFormat.ARGB32.pack(1.0f, 1.0f, 1.0f, 0.2f);
    private static final AtomicInteger COUNTER = new AtomicInteger(0);
    private static final String INTERNAL_NAME = "WalkingPet";
    private final Laby3D laby3D = Laby.references().laby3D();
    private final String name = "WalkingPet" + COUNTER.getAndIncrement();

    public void render(Stack stack, float partialTicks, CosmeticDefinition definition, PetBehavior behavior) {
        if (!DebugRegistry.PETS_AI.isEnabled()) {
            return;
        }
        BufferBuilder buffer = this.laby3D.begin(DrawingMode.QUADS, VertexDescriptions.POSITION_COLOR);
        renderAABB(stack, buffer, behavior.boundingBox(), HITBOX_COLOR);
        MeshRenderer.drawImmediate(buffer.build(), RenderStates.DEBUG_BOXES);
        GFXRenderPipeline renderPipeline = Laby.references().gfxRenderPipeline();
        TextRenderer renderer = Laby.references().textRendererProvider().getRenderer();
        stack.push();
        MinecraftCamera camera = Laby.labyAPI().minecraft().getCamera();
        stack.rotate(-camera.getYaw(), 0.0f, 1.0f, 0.0f);
        stack.scale(0.015625f);
        stack.scale((float) definition.details().getScale());
        RenderEnvironmentContext renderEnvironmentContext = renderPipeline.renderEnvironmentContext();
        int packedLight = renderEnvironmentContext.getPackedLight();
        renderEnvironmentContext.setPackedLight(RenderEnvironmentContext.FULL_BRIGHT);
        Model model = definition.itemModel() != null ? definition.itemModel().getModel() : null;
        float modelHeight = model != null ? model.getHeight() + 4.0f : 4.0f;
        float y = (modelHeight / 0.015625f) * 0.0625f;
        stack.translate(0.0f, -y, 0.0f);
        renderer.render(stack.getProvider().getPose(), this.name + " (" + definition.getName() + ")", 0.0f, 0.0f, -1, packedLight, 0, 8);
        stack.translate(0.0f, -8.0f, 0.0f);
        renderer.render(stack.getProvider().getPose(), Component.text("Animation Trigger: ").append(Component.text(definition.animationContainer().getTrigger().getName(), NamedTextColor.YELLOW)), 0.0f, 0.0f, -1, packedLight, 0, 8);
        renderEnvironmentContext.setPackedLight(packedLight);
        stack.pop();
    }

    private void renderAABB(Stack stack, VertexConsumer consumer, AxisAlignedBoundingBox aabb, int color) {
        stack.push();
        DoubleVector3 center = aabb.getCenter();
        stack.translate(-center.getX(), -aabb.getHeight(), -center.getZ());
        Matrix4f pose = stack.getProvider().getPose();
        fillVertex(pose, consumer, aabb.getMinX(), aabb.getMinY(), aabb.getMinZ(), color);
        fillVertex(pose, consumer, aabb.getMinX(), aabb.getMaxY(), aabb.getMinZ(), color);
        fillVertex(pose, consumer, aabb.getMaxX(), aabb.getMaxY(), aabb.getMinZ(), color);
        fillVertex(pose, consumer, aabb.getMaxX(), aabb.getMinY(), aabb.getMinZ(), color);
        fillVertex(pose, consumer, aabb.getMinX(), aabb.getMinY(), aabb.getMaxZ(), color);
        fillVertex(pose, consumer, aabb.getMinX(), aabb.getMaxY(), aabb.getMaxZ(), color);
        fillVertex(pose, consumer, aabb.getMaxX(), aabb.getMaxY(), aabb.getMaxZ(), color);
        fillVertex(pose, consumer, aabb.getMaxX(), aabb.getMinY(), aabb.getMaxZ(), color);
        fillVertex(pose, consumer, aabb.getMinX(), aabb.getMinY(), aabb.getMaxZ(), color);
        fillVertex(pose, consumer, aabb.getMinX(), aabb.getMaxY(), aabb.getMaxZ(), color);
        fillVertex(pose, consumer, aabb.getMinX(), aabb.getMaxY(), aabb.getMinZ(), color);
        fillVertex(pose, consumer, aabb.getMinX(), aabb.getMinY(), aabb.getMinZ(), color);
        fillVertex(pose, consumer, aabb.getMaxX(), aabb.getMinY(), aabb.getMaxZ(), color);
        fillVertex(pose, consumer, aabb.getMaxX(), aabb.getMaxY(), aabb.getMaxZ(), color);
        fillVertex(pose, consumer, aabb.getMaxX(), aabb.getMaxY(), aabb.getMinZ(), color);
        fillVertex(pose, consumer, aabb.getMaxX(), aabb.getMinY(), aabb.getMinZ(), color);
        fillVertex(pose, consumer, aabb.getMinX(), aabb.getMinY(), aabb.getMinZ(), color);
        fillVertex(pose, consumer, aabb.getMinX(), aabb.getMinY(), aabb.getMaxZ(), color);
        fillVertex(pose, consumer, aabb.getMaxX(), aabb.getMinY(), aabb.getMaxZ(), color);
        fillVertex(pose, consumer, aabb.getMaxX(), aabb.getMinY(), aabb.getMinZ(), color);
        fillVertex(pose, consumer, aabb.getMinX(), aabb.getMaxY(), aabb.getMinZ(), color);
        fillVertex(pose, consumer, aabb.getMinX(), aabb.getMaxY(), aabb.getMaxZ(), color);
        fillVertex(pose, consumer, aabb.getMaxX(), aabb.getMaxY(), aabb.getMaxZ(), color);
        fillVertex(pose, consumer, aabb.getMaxX(), aabb.getMaxY(), aabb.getMinZ(), color);
        stack.pop();
    }

    private void fillVertex(Matrix4f pose, VertexConsumer consumer, double x, double y, double z, int color) {
        consumer.addVertex(pose, x, y, z).setColor(color);
    }
}
