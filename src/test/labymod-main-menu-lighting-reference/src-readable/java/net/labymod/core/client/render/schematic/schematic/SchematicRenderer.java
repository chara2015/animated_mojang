package net.labymod.core.client.render.schematic;

import java.util.ArrayList;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.Textures;
import net.labymod.api.client.gfx.pipeline.texture.atlas.AtlasRegistry;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.client.render.RenderPipeline;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.laby3d.shaders.block.SchematicUniformBlock;
import net.labymod.api.util.Disposable;
import net.labymod.core.client.gui.background.DynamicBackgroundController;
import net.labymod.core.client.render.schematic.block.Block;
import net.labymod.core.client.render.schematic.block.BlockRenderer;
import net.labymod.core.client.render.schematic.block.material.RenderLayer;
import net.labymod.core.client.render.schematic.lighting.LightSourceRegistry;
import net.labymod.core.client.render.schematic.lighting.legacy.LegacyLightEngine;
import net.labymod.core.client.render.schematic.particle.ParticleRenderer;
import net.labymod.core.client.render.schematic.renderer.RenderedSchematicLayer;
import net.labymod.core.util.ArrayIndex;
import net.labymod.core.util.camera.CinematicCamera;
import net.labymod.core.util.camera.spline.position.Location;
import net.labymod.laby3d.api.RenderDevice;
import net.labymod.laby3d.api.pipeline.LoadOp;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;
import net.labymod.laby3d.api.pipeline.target.RenderTarget;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/schematic/SchematicRenderer.class */
public class SchematicRenderer implements Disposable {
    private final Schematic schematic;
    private final DynamicBackgroundController backgroundController;
    private final BlockRenderer blockRenderer;
    private final ParticleRenderer particleRenderer;
    private final List<RenderHook> renderHooks = new ArrayList();
    private int liquidUpdateTicks = 0;
    private final LabyAPI labyAPI = Laby.labyAPI();
    private final Laby3D laby3D = Laby.references().laby3D();
    private final ConfigProperty<Boolean> animatedTextures = this.labyAPI.config().appearance().dynamicBackground().animatedTextures();
    private final CinematicCamera camera = new ShaderCamera(50.0f);
    private final AtlasRegistry atlasRegistry = Laby.references().atlasRegistry();
    private final ArrayIndex<RenderedSchematicLayer> layers = new ArrayIndex<>(RenderLayer.values().length, x$0 -> {
        return new RenderedSchematicLayer[x$0];
    });

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/schematic/SchematicRenderer$RenderHook.class */
    public interface RenderHook {
        void render(CommandBuffer commandBuffer, Stack stack, float f, float f2, float f3, float f4, float f5);
    }

    public SchematicRenderer(DynamicBackgroundController controller, Schematic schematic) {
        this.schematic = schematic;
        this.backgroundController = controller;
        this.blockRenderer = new BlockRenderer(schematic);
        this.particleRenderer = new ParticleRenderer(schematic, this.camera, controller);
        this.layers.fill(index -> {
            return new RenderedSchematicLayer(RenderLayer.values()[index], controller);
        });
    }

    public void onTick() {
        this.particleRenderer.onTick();
        LightSourceRegistry.getInstance().tick();
        if (this.liquidUpdateTicks > 6 && this.animatedTextures.get().booleanValue()) {
            this.liquidUpdateTicks = 0;
            RenderedSchematicLayer layer = this.layers.get(RenderLayer.LIQUID.ordinal());
            if (layer != null) {
                layer.setDirty();
            }
        }
        this.liquidUpdateTicks++;
    }

    public void onRenderTick(Stack stack, float left, float top, float right, float bottom, float partialTicks) {
        this.camera.setup(left, top, right, bottom, partialTicks);
    }

    public void render(Stack stack, float left, float top, float right, float bottom, float partialTicks) {
        stack.push();
        stack.identity();
        this.camera.setup(left, top, right, bottom, partialTicks);
        float tickDelta = Laby.labyAPI().minecraft().getTickDelta();
        this.camera.update(left, top, right, bottom, tickDelta);
        RenderDevice renderDevice = this.laby3D.renderDevice();
        RenderTarget renderTarget = Laby.references().gfxRenderPipeline().currentTarget();
        this.backgroundController.configureBackground();
        CommandBuffer cmd = renderDevice.createCommandBuffer(1);
        try {
            cmd.beginPass(renderTarget, LoadOp.CLEAR, () -> {
                return SchematicUniformBlock.NAME;
            });
            renderLayer(cmd, this.atlasRegistry.getAtlas(Textures.Splash.BLOCKS), RenderLayer.CUBE);
            renderLayer(cmd, this.atlasRegistry.getAtlas(Textures.Splash.LAVA_FLOW), RenderLayer.LIQUID);
            renderLayer(cmd, this.atlasRegistry.getAtlas(Textures.Splash.BLOCKS), RenderLayer.CUT_OUT);
            this.layers.forEach(renderedSchematicLayer -> {
                renderedSchematicLayer.upload(cmd);
            });
            this.particleRenderer.render(cmd, this.atlasRegistry.getAtlas(Textures.Splash.PARTICLES), partialTicks);
            for (RenderHook hook : this.renderHooks) {
                hook.render(cmd, stack, left, top, right, bottom, partialTicks);
            }
            cmd.endPass();
            cmd.submit();
            if (cmd != null) {
                cmd.close();
            }
            Location position = this.camera.location();
            LightSourceRegistry registry = LightSourceRegistry.getInstance();
            registry.sort(new Vector3f((float) position.getX(), (float) position.getY(), (float) position.getZ()));
            stack.pop();
            this.schematic.legacyLightEngine().handleLightUpdates();
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

    private void renderLayer(CommandBuffer cmd, TextureAtlas atlas, RenderLayer type) {
        RenderedSchematicLayer layer = this.layers.get(type.ordinal());
        if (layer == null) {
            return;
        }
        if (layer.isAnyMeshIndexBufferClosed()) {
            setDirty();
        }
        layer.setAtlas(atlas);
        LegacyLightEngine lightEngine = this.schematic.legacyLightEngine();
        if (lightEngine == layer.getLightEngine() && !lightEngine.isDirty() && !layer.isDirty() && layer.renderMesh(cmd)) {
            return;
        }
        int chunksInWidth = this.schematic.getChunksInWidth();
        int chunksInHeight = this.schematic.getChunksInHeight();
        int chunksInLength = this.schematic.getChunksInLength();
        for (int chunkX = 0; chunkX < chunksInWidth; chunkX++) {
            for (int chunkY = 0; chunkY < chunksInHeight; chunkY++) {
                for (int chunkZ = 0; chunkZ < chunksInLength; chunkZ++) {
                    renderChunk(cmd, layer, type, chunkX, chunkY, chunkZ);
                }
            }
        }
        layer.setLightEngine(lightEngine);
    }

    private void renderChunk(CommandBuffer cmd, RenderedSchematicLayer layer, RenderLayer type, int chunkX, int chunkY, int chunkZ) {
        int minX = chunkX * 16;
        int minY = chunkY * 16;
        int minZ = chunkZ * 16;
        int maxX = Math.min(minX + 16, (int) this.schematic.getWidth());
        int maxY = Math.min(minY + 16, (int) this.schematic.getHeight());
        int maxZ = Math.min(minZ + 16, (int) this.schematic.getLength());
        RenderPipeline renderPipeline = Laby.references().renderPipeline();
        renderPipeline.setModifiedAlpha(false);
        for (int x = minX; x < maxX; x++) {
            for (int y = minY; y < maxY; y++) {
                for (int z = minZ; z < maxZ; z++) {
                    Block block = this.schematic.getBlockAt(x, y, z);
                    RenderLayer renderLayer = block.material().getRenderLayer();
                    if (renderLayer == type) {
                        layer.render(this.blockRenderer, block, x, y, z);
                    }
                }
            }
        }
        renderPipeline.setModifiedAlpha(true);
        layer.uploadChunk();
    }

    public void registerRenderHook(RenderHook hook) {
        this.renderHooks.add(hook);
    }

    public CinematicCamera camera() {
        return this.camera;
    }

    public ParticleRenderer particleRenderer() {
        return this.particleRenderer;
    }

    public Schematic schematic() {
        return this.schematic;
    }

    public void setDirty() {
        this.layers.forEach((v0) -> {
            v0.setDirty();
        });
    }

    @Override // net.labymod.api.util.Disposable
    public void dispose() {
        this.layers.forEach((v0) -> {
            v0.dispose();
        });
        LightSourceRegistry.getInstance().reset();
    }
}
