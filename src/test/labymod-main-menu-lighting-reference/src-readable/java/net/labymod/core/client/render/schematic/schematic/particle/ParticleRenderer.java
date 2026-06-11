package net.labymod.core.client.render.schematic.particle;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.GlConst;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.client.gfx.shader.ShaderTextures;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.laby3d.shaders.block.SchematicUniformBlock;
import net.labymod.api.util.math.MathHelper;
import net.labymod.core.client.gui.background.DynamicBackgroundController;
import net.labymod.core.client.render.schematic.SchematicAccessor;
import net.labymod.core.client.render.schematic.block.Block;
import net.labymod.core.client.render.schematic.block.material.MaterialRegistry;
import net.labymod.core.client.render.schematic.block.material.material.Material;
import net.labymod.core.util.camera.CinematicCamera;
import net.labymod.core.util.camera.spline.position.Location;
import net.labymod.laby3d.api.buffers.BufferBuilder;
import net.labymod.laby3d.api.buffers.ByteBufferBuilder;
import net.labymod.laby3d.api.mesh.GeometryData;
import net.labymod.laby3d.api.mesh.Mesh;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;
import net.labymod.laby3d.api.vertex.VertexConsumer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/schematic/particle/ParticleRenderer.class */
public class ParticleRenderer {
    private final SchematicAccessor level;
    private final CinematicCamera camera;
    private final DynamicBackgroundController controller;
    private final List<Particle> particles = new ArrayList();
    private final Laby3D laby3D = Laby.references().laby3D();
    private final Random random = new Random();
    private final ByteBufferBuilder bufferBuilder = new ByteBufferBuilder(GlConst.GL_TEXTURE_BIT);

    public ParticleRenderer(SchematicAccessor level, CinematicCamera camera, DynamicBackgroundController controller) {
        this.level = level;
        this.camera = camera;
        this.controller = controller;
    }

    public void spawnParticle(Particle particle) {
        this.particles.add(particle);
    }

    public void onTick() {
        Block block;
        Material material;
        List<Particle> particleList = this.particles;
        int i = 0;
        while (i < particleList.size()) {
            Particle particle = particleList.get(i);
            particle.onUpdate();
            if (particle.isDead()) {
                this.particles.remove(particle);
                i--;
            }
            i++;
        }
        Location position = this.camera.location();
        int playerPosX = MathHelper.floor(position.getX());
        int playerPosY = MathHelper.floor(position.getY());
        int playerPosZ = MathHelper.floor(position.getZ());
        float maxWidth = this.level.getWidth();
        float maxHeight = this.level.getHeight();
        float maxLength = this.level.getLength();
        for (int l = 0; l < 30000; l++) {
            int x = (playerPosX + this.random.nextInt(64)) - this.random.nextInt(64);
            int y = (playerPosY + this.random.nextInt(5)) - this.random.nextInt(5);
            int z = (playerPosZ + this.random.nextInt(64)) - this.random.nextInt(64);
            if (x >= 0 && x < maxWidth && y >= 0 && y < maxHeight && z >= 0 && z < maxLength && (material = (block = this.level.getBlockAt(x, y, z)).material()) != MaterialRegistry.AIR) {
                material.randomDisplayTick(this.level, this, block, x, y, z);
            }
        }
    }

    public void render(CommandBuffer cmd, TextureAtlas atlas, float partialTicks) {
        Location position = this.camera.location();
        float yaw = MathHelper.toRadiansFloat((float) position.getYaw());
        float pitch = MathHelper.toRadiansFloat((float) position.getPitch());
        float offsetX = MathHelper.cos(yaw);
        float offsetY = MathHelper.cos(pitch);
        float offsetZ = MathHelper.sin(yaw);
        float rotX = (-offsetZ) * MathHelper.sin(pitch);
        float rotZ = offsetX * MathHelper.sin(pitch);
        RenderState renderState = RenderStates.SCHEMATIC;
        VertexConsumer bufferBuilder = new BufferBuilder(this.bufferBuilder, renderState.vertexDescription(), renderState.drawingMode());
        for (Particle particle : this.particles) {
            particle.render(bufferBuilder, atlas, partialTicks, offsetX, offsetY, offsetZ, rotX, rotZ);
        }
        GeometryData geometryData = bufferBuilder.build();
        if (geometryData == null) {
            return;
        }
        ShaderTextures.setShaderTexture(0, atlas.resource());
        Mesh mesh = Mesh.create(this.laby3D.renderDevice(), () -> {
            return "Particles";
        }, geometryData);
        cmd.bindPipeline(renderState);
        Objects.requireNonNull(mesh);
        cmd.addCleanupAction(mesh::close);
        cmd.bindTexture(0, ShaderTextures.getShaderTexture(0));
        cmd.bindUniformBlock(SchematicUniformBlock.NAME, this.laby3D.schematic());
        cmd.draw(mesh);
    }

    public void close() {
        if (this.bufferBuilder != null) {
            this.bufferBuilder.close();
        }
    }

    public void clear() {
        this.particles.clear();
    }
}
