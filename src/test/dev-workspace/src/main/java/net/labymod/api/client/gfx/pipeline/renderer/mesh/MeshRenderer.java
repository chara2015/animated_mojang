package net.labymod.api.client.gfx.pipeline.renderer.mesh;

import java.util.Objects;
import java.util.function.Consumer;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.shader.ShaderTextures;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.laby3d.buffer.ImmediateDeviceBufferStorage;
import net.labymod.laby3d.api.RenderDevice;
import net.labymod.laby3d.api.mesh.BufferResource;
import net.labymod.laby3d.api.mesh.GeometryData;
import net.labymod.laby3d.api.mesh.Mesh;
import net.labymod.laby3d.api.mesh.SharedMeshIndexBuffer;
import net.labymod.laby3d.api.pipeline.LoadOp;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;
import net.labymod.laby3d.api.textures.DeviceTextureView;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/renderer/mesh/MeshRenderer.class */
public final class MeshRenderer {
    private static final String UNNAMED_MESH = "Unnamed Mesh (Immediate)";
    private static final Laby3D LABY_3D = Laby.references().laby3D();

    public static void draw(Mesh mesh, RenderState renderState, Consumer<CommandBuffer> commandConsumer) {
        RenderDevice renderDevice = LABY_3D.renderDevice();
        CommandBuffer cmd = renderDevice.createCommandBuffer(1);
        try {
            cmd.beginPass(LABY_3D.resolveDrawRenderTarget(), LoadOp.LOAD);
            draw(cmd, mesh, renderState, commandConsumer, false);
            cmd.endPass();
            cmd.submit();
            if (cmd != null) {
                cmd.close();
            }
        } catch (Throwable th) {
            if (cmd != null) {
                try {
                    cmd.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public static void draw(CommandBuffer cmd, Mesh mesh, RenderState renderState, Consumer<CommandBuffer> commandConsumer, boolean closeMesh) {
        cmd.bindPipeline(renderState);
        for (int index = 0; index < 12; index++) {
            DeviceTextureView textureView = ShaderTextures.getShaderTexture(index);
            if (textureView != null) {
                cmd.bindTexture(index, textureView);
            }
        }
        LABY_3D.setupDefaultUniforms(cmd);
        commandConsumer.accept(cmd);
        if (closeMesh) {
            Objects.requireNonNull(mesh);
            cmd.addCleanupAction(mesh::close);
        }
        cmd.draw(mesh);
    }

    public static void drawImmediate(CommandBuffer cmd, @Nullable GeometryData geometryData, RenderState renderState, Consumer<CommandBuffer> commandConsumer) {
        if (geometryData == null) {
            return;
        }
        RenderDevice renderDevice = LABY_3D.renderDevice();
        Mesh mesh = Mesh.create(renderDevice, () -> {
            return UNNAMED_MESH;
        }, geometryData);
        draw(cmd, mesh, renderState, commandConsumer, true);
    }

    public static void drawImmediate(@Nullable GeometryData geometryData, RenderState renderState) {
        drawImmediate(geometryData, renderState, cmd -> {
        });
    }

    public static void drawImmediate(@Nullable GeometryData geometryData, RenderState renderState, Consumer<CommandBuffer> commandConsumer) {
        if (geometryData == null) {
            return;
        }
        ImmediateDeviceBufferStorage immediateDeviceBufferStorage = LABY_3D.immediateDeviceBufferStorage();
        RenderDevice renderDevice = LABY_3D.renderDevice();
        SharedMeshIndexBuffer sharedMeshBuffer = renderDevice.getSharedMeshBuffer(renderState.drawingMode());
        GeometryData.Details details = geometryData.details();
        Mesh mesh = Mesh.create(() -> {
            return UNNAMED_MESH;
        }, new BufferResource(immediateDeviceBufferStorage.uploadImmediateVertexBuffer(renderState.vertexDescription(), geometryData.vertexBuffer()), sharedMeshBuffer.getBuffer(details.indexCount()), sharedMeshBuffer.getType(), true), 0, 0, details.vertexCount(), details.indexCount(), 0);
        try {
            CommandBuffer cmd = renderDevice.createCommandBuffer(1);
            try {
                cmd.beginPass(LABY_3D.resolveDrawRenderTarget(), LoadOp.LOAD);
                draw(cmd, mesh, renderState, commandConsumer, false);
                cmd.endPass();
                cmd.submit();
                if (cmd != null) {
                    cmd.close();
                }
                if (geometryData != null) {
                    geometryData.close();
                }
            } finally {
            }
        } catch (Throwable th) {
            if (geometryData != null) {
                try {
                    geometryData.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }
}
