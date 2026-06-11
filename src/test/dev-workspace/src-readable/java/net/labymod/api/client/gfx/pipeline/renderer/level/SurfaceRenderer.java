package net.labymod.api.client.gfx.pipeline.renderer.level;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.gfx.pipeline.renderer.mesh.MeshRenderer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.block.RenderShape;
import net.labymod.api.debug.DebugRegistry;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.laby3d.shaders.block.SprayDataUniformBlock;
import net.labymod.api.laby3d.shaders.block.SprayDataUniformBlockData;
import net.labymod.api.laby3d.vertex.VertexDescriptions;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.math.Direction;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.FloatVector2;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.laby3d.api.buffers.BufferBuilder;
import net.labymod.laby3d.api.buffers.ByteBufferBuilder;
import net.labymod.laby3d.api.pipeline.DrawingMode;
import net.labymod.laby3d.api.vertex.VertexConsumer;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/renderer/level/SurfaceRenderer.class */
@Singleton
@Referenceable
public final class SurfaceRenderer {
    private static final int DEBUG_BUFFER_CAPACITY = 256;
    private final LabyAPI labyAPI;
    private final ByteBufferBuilder debugBugger = new ByteBufferBuilder(256);
    private float minX;
    private float maxX;
    private float minZ;
    private float maxZ;
    private float radius;
    private float rotation;

    public SurfaceRenderer(LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
    }

    public void renderSurface(Stack stack, VertexConsumer consumer, double x, double y, double z, Direction direction, float radius, float rotation) {
        this.radius = radius;
        this.rotation = rotation;
        float halfRadius = radius / 2.0f;
        this.minX = -halfRadius;
        this.minZ = -halfRadius;
        this.maxX = halfRadius;
        this.maxZ = halfRadius;
        int blockX = MathHelper.floor(x);
        int blockY = MathHelper.floor(y);
        int blockZ = MathHelper.floor(z);
        List<FloatVector3> positions = calculatePositions(direction, blockX, blockY, blockZ, x, y, z, halfRadius);
        ClientWorld world = this.labyAPI.minecraft().clientWorld();
        BufferBuilder debugBufferBuilder = null;
        if (DebugRegistry.SURFACE_WIREFRAME.isEnabled()) {
            debugBufferBuilder = new BufferBuilder(this.debugBugger, VertexDescriptions.MODEL, DrawingMode.QUADS);
        }
        stack.push();
        stack.translate(-0.5f, -0.5f, 0.0f);
        for (int i = positions.size() - 1; i >= 0; i--) {
            FloatVector3 position = positions.get(i);
            renderPosition(stack, consumer, debugBufferBuilder, x, y, z, direction, position, blockX, blockY, blockZ, world);
        }
        stack.pop();
        if ((debugBufferBuilder != null) & DebugRegistry.SURFACE_WIREFRAME.isEnabled()) {
            MeshRenderer.drawImmediate(debugBufferBuilder.build(), RenderStates.SPRAY_DEBUG, cmd -> {
                SprayDataUniformBlockData sprayData = new SprayDataUniformBlockData();
                sprayData.setWear(1.0f);
                cmd.bindUniformBlock(SprayDataUniformBlock.NAME, Laby.references().laby3D().sprayData());
                cmd.bindUniformBlockData(SprayDataUniformBlock.NAME, sprayData);
            });
        }
    }

    private void renderPosition(Stack stack, VertexConsumer consumer, @Nullable VertexConsumer debugConsumer, double x, double y, double z, Direction direction, FloatVector3 position, int blockX, int blockY, int blockZ, ClientWorld world) {
        int blockPosX = MathHelper.floor(position.getX());
        int blockPosY = MathHelper.floor(position.getY());
        int blockPosZ = MathHelper.floor(position.getZ());
        int posX = blockPosX - blockX;
        int posY = blockPosY - blockY;
        int posZ = blockPosZ - blockZ;
        double offsetX = ((double) blockPosX) - x;
        double offsetY = ((double) blockPosY) - y;
        double offsetZ = ((double) blockPosZ) - z;
        if (direction == Direction.EAST) {
            blockPosX--;
        } else if (direction == Direction.SOUTH) {
            blockPosZ--;
        } else if (direction == Direction.UP) {
            blockPosY--;
        }
        BlockState blockState = world.getBlockState(blockPosX, blockPosY, blockPosZ);
        if (blockState.renderShape() == RenderShape.INVISIBLE || !MathHelper.isBox(blockState.bounds())) {
            return;
        }
        stack.push();
        if (direction == Direction.EAST || direction == Direction.WEST) {
            buildTile(stack, consumer, debugConsumer, posZ, posY, offsetZ, offsetY, direction);
        } else if (direction == Direction.NORTH || direction == Direction.SOUTH) {
            buildTile(stack, consumer, debugConsumer, posX, posY, offsetX, offsetY, direction);
        } else {
            buildTile(stack, consumer, debugConsumer, posX, posZ, offsetX, offsetZ, direction);
        }
        stack.pop();
    }

    private List<FloatVector3> calculatePositions(Direction direction, int blockX, int blockY, int blockZ, double x, double y, double z, float halfRadius) {
        int blockMinX = MathHelper.floor(x + ((double) this.minX));
        int blockMaxX = MathHelper.floor(x + ((double) this.maxX));
        int blockMinY = MathHelper.floor(y + ((double) (-halfRadius)));
        int blockMaxY = MathHelper.floor(y + ((double) halfRadius));
        int blockMinZ = MathHelper.floor(z + ((double) this.minZ));
        int blockMaxZ = MathHelper.floor(z + ((double) this.maxZ));
        List<FloatVector3> positions = new ArrayList<>();
        if (direction == Direction.EAST || direction == Direction.WEST) {
            for (int yIndex = blockMinY; yIndex <= blockMaxY; yIndex++) {
                for (int zIndex = blockMinZ; zIndex <= blockMaxZ; zIndex++) {
                    positions.add(new FloatVector3(blockX, yIndex, zIndex));
                }
            }
        } else if (direction == Direction.NORTH || direction == Direction.SOUTH) {
            for (int yIndex2 = blockMinY; yIndex2 <= blockMaxY; yIndex2++) {
                for (int xIndex = blockMinX; xIndex <= blockMaxX; xIndex++) {
                    positions.add(new FloatVector3(xIndex, yIndex2, blockZ));
                }
            }
        } else {
            for (int zIndex2 = blockMinZ; zIndex2 <= blockMaxZ; zIndex2++) {
                for (int xIndex2 = blockMinX; xIndex2 <= blockMaxX; xIndex2++) {
                    positions.add(new FloatVector3(xIndex2, blockY, zIndex2));
                }
            }
        }
        return positions;
    }

    private void buildTile(Stack stack, VertexConsumer consumer, @Nullable VertexConsumer debugConsumer, double x, double y, double offsetX, double offsetY, Direction direction) {
        double left = x;
        double top = -y;
        double right = x + 1.0d;
        double bottom = (-y) + 1.0d;
        if (direction == Direction.WEST) {
            left = -x;
            right = (-x) + 1.0d;
        }
        if (direction == Direction.UP || direction == Direction.DOWN) {
            top = y;
            bottom = y + 1.0d;
        }
        if (left == this.minX) {
            left += 0.5d;
        } else if (right == this.maxX + 1.0f) {
            right -= 0.5d;
        }
        if (top == this.minZ) {
            top += 0.5d;
        } else if (bottom == this.maxZ + 1.0f) {
            bottom -= 0.5d;
        }
        double offsetX2 = 1.0d - Math.abs(x - offsetX);
        if (left < 0.0d) {
            if (direction == Direction.WEST) {
                right -= (-0.5d) + offsetX2;
            } else {
                right += (-0.5d) + offsetX2;
            }
        } else if (left > 0.0d) {
            if (direction == Direction.WEST) {
                left -= (-0.5d) + offsetX2;
            } else {
                left += (-0.5d) + offsetX2;
            }
        } else if (direction == Direction.WEST) {
            left -= (-0.5d) + offsetX2;
            right -= (-0.5d) + offsetX2;
        } else {
            left += (-0.5d) + offsetX2;
            right += (-0.5d) + offsetX2;
        }
        double offsetY2 = 1.0d - Math.abs(y - offsetY);
        if (top < 0.0d) {
            if (direction == Direction.UP || direction == Direction.DOWN) {
                bottom += (-0.5d) + offsetY2;
            } else {
                bottom -= (-0.5d) + offsetY2;
            }
        } else if (top > 0.0d) {
            if (direction == Direction.UP || direction == Direction.DOWN) {
                top += (-0.5d) + offsetY2;
            } else {
                top -= (-0.5d) + offsetY2;
            }
        } else if (direction == Direction.UP || direction == Direction.DOWN) {
            top += (-0.5d) + offsetY2;
            bottom += (-0.5d) + offsetY2;
        } else {
            top -= (-0.5d) + offsetY2;
            bottom -= (-0.5d) + offsetY2;
        }
        if (DebugRegistry.SURFACE_WIREFRAME.isEnabled()) {
            buildQuad(stack, debugConsumer, direction, left, top, right, bottom, this.radius);
        }
        buildQuad(stack, consumer, direction, left, top, right, bottom, this.radius);
    }

    private void buildQuad(Stack stack, VertexConsumer consumer, Direction direction, double left, double top, double right, double bottom, float radius) {
        buildQuad(stack, consumer, direction, left, top, right, bottom, radius, -1);
    }

    private void buildQuad(Stack stack, VertexConsumer consumer, Direction direction, double left, double top, double right, double bottom, float radius, int color) {
        float halfRadius = radius / 2.0f;
        float norm = halfRadius - 0.5f;
        float minU = (float) ((left + ((double) norm)) / ((double) radius));
        float minV = (float) ((top + ((double) norm)) / ((double) radius));
        float maxU = (float) ((right + ((double) norm)) / ((double) radius));
        float maxV = (float) ((bottom + ((double) norm)) / ((double) radius));
        if (direction == Direction.WEST || direction == Direction.EAST || direction == Direction.NORTH || direction == Direction.DOWN) {
            minU = 1.0f - minU;
            maxU = 1.0f - maxU;
        }
        buildVertex(stack, consumer, left, top, minU, minV, color);
        buildVertex(stack, consumer, left, bottom, minU, maxV, color);
        buildVertex(stack, consumer, right, bottom, maxU, maxV, color);
        buildVertex(stack, consumer, right, top, maxU, minV, color);
    }

    private void buildVertex(Stack stack, VertexConsumer consumer, double x, double y, float u, float v, int color) {
        FloatVector2 uv = rotateUV(u, v);
        float u2 = uv.getX();
        float v2 = uv.getY();
        consumer.addVertex(stack.getProvider().getPose(), (float) x, (float) y, 0.0f).setUv(u2, v2).setColor(color).setPackedOverlay(RenderEnvironmentContext.NO_OVERLAY).setPackedLight(Laby.references().renderEnvironmentContext().getPackedLight()).setNormal(0.0f, 1.0f, 0.0f);
    }

    private FloatVector2 rotateUV(float u, float v) {
        float center = this.radius / 4.0f;
        float translatedU = u - center;
        float translatedV = v - center;
        float radians = MathHelper.toRadiansFloat(this.rotation);
        float cosTheta = MathHelper.cos(radians);
        float sinTheta = MathHelper.sin(radians);
        float rotatedU = (translatedU * cosTheta) - (translatedV * sinTheta);
        float rotatedV = (translatedU * sinTheta) + (translatedV * cosTheta);
        return new FloatVector2(rotatedU + center, rotatedV + center);
    }

    public void close() {
        this.debugBugger.close();
    }
}
