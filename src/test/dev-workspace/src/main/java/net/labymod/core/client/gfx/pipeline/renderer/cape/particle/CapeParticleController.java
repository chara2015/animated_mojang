package net.labymod.core.client.gfx.pipeline.renderer.cape.particle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.datawatcher.DataWatcher;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.gfx.pipeline.renderer.mesh.MeshRenderer;
import net.labymod.api.client.gfx.shader.ShaderTextures;
import net.labymod.api.client.render.matrix.DefaultStackProvider;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.laby3d.math.JomlMath;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.laby3d.vertex.VertexDescriptions;
import net.labymod.api.loader.MinecraftVersion;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.laby3d.api.buffers.BufferBuilder;
import net.labymod.laby3d.api.pipeline.DrawingMode;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gfx/pipeline/renderer/cape/particle/CapeParticleController.class */
@Singleton
@Referenceable
public final class CapeParticleController {
    private static final Random RANDOM = new Random();
    private static final Stack PARTICLE_STACK = Stack.create((StackProvider) new DefaultStackProvider());
    private static final FloatVector3 OFFSET_VECTOR = new FloatVector3();
    private static final boolean CUSTOM_OFFSET = isSupportedVersion();
    private static final float PARTICLE_MIN_X = -0.28125f;
    private static final float PARTICLE_MIN_Y = -0.03125f;
    private static final float PARTICLE_MAX_X = 0.8f;
    private static final float PARTICLE_MAX_Y = 0.8f;
    private static final float PARTICLE_AREA_PIVOT_OFFSET_X = 0.4375f;
    private static final float PARTICLE_AREA_PIVOT_OFFSET_Y = 0.4375f;
    private final List<CapeParticleStorage> storages = new ArrayList();
    private final Laby3D laby3D = Laby.references().laby3D();

    @Inject
    public CapeParticleController() {
        Laby.labyAPI().eventBus().registerListener(this);
    }

    private static boolean isSupportedVersion() {
        return isSupportedVersion(MinecraftVersions.current());
    }

    private static boolean isSupportedVersion(MinecraftVersion current) {
        return current.isGreaterThan(MinecraftVersions.V1_13) && current.isLowerThan(MinecraftVersions.V1_21_3);
    }

    private void updateOffsetVector(Player player) {
        float offsetZ = -1.0125f;
        float offsetY = -0.85f;
        if (CUSTOM_OFFSET) {
            if (!player.getEquipmentItemStack(LivingEntity.EquipmentSpot.CHEST).isAir()) {
                if (player.isCrouching()) {
                    offsetZ = -0.75f;
                    offsetY = -1.85f;
                } else {
                    offsetZ = -2.2f;
                    offsetY = -0.4f;
                }
            } else if (player.isCrouching()) {
                offsetZ = 0.35f;
                offsetY = -2.7f;
            }
        }
        OFFSET_VECTOR.set(0.0f * 0.0625f, offsetY * 0.0625f, offsetZ * 0.0625f);
    }

    public void spawn(Matrix4f modelViewMatrix, Player player, float partialTicks) {
        if (!Laby.labyAPI().config().ingame().showCapeParticles().get().booleanValue()) {
            return;
        }
        PARTICLE_STACK.push();
        PARTICLE_STACK.mul(modelViewMatrix);
        updateOffsetVector(player);
        float renderTick = player.getRenderTick(partialTicks);
        DataWatcher dataWatcher = player.dataWatcher();
        List<CapeParticle> particles = (List) dataWatcher.computeIfAbsent("capeParticles", k -> {
            return new LinkedList();
        }).get();
        spawnParticle(dataWatcher, particles, renderTick);
        Iterator<CapeParticle> iterator = particles.iterator();
        while (iterator.hasNext()) {
            CapeParticle particle = iterator.next();
            PARTICLE_STACK.push();
            float x = (particle.getX() - OFFSET_VECTOR.getX()) - 0.4375f;
            PARTICLE_STACK.translate(x, (particle.getY() - OFFSET_VECTOR.getY()) - 0.4375f, OFFSET_VECTOR.getZ());
            PARTICLE_STACK.translate(0.5f, 0.5f, 0.0f);
            float ticksPassed = renderTick - particle.getSpawnTick();
            float previousProgress = particle.getPreviousProgress();
            float progress = MathHelper.lerp(ticksPassed / 10.0f, previousProgress, previousProgress == -1.0f ? 1.0f : partialTicks);
            float scale = progress * 2.0f;
            PARTICLE_STACK.scale(0.015625f);
            PARTICLE_STACK.scale(scale);
            PARTICLE_STACK.translate(-0.5f, -0.5f, 0.0f);
            this.storages.add(new CapeParticleStorage(JomlMath.cloneMatrix(PARTICLE_STACK.getProvider().getPose()), progress));
            particle.setPreviousProgress(progress);
            PARTICLE_STACK.pop();
            if (progress > 2.0f) {
                iterator.remove();
            }
        }
        renderParticles();
        this.storages.clear();
        PARTICLE_STACK.pop();
    }

    private void renderParticles() {
        ShaderTextures.setShaderTexture(0, Textures.STAR);
        BufferBuilder buffer = this.laby3D.begin(DrawingMode.QUADS, VertexDescriptions.POSITION_UV_COLOR);
        int index = 0;
        ColorFormat format = ColorFormat.ARGB32;
        for (CapeParticleStorage storage : this.storages) {
            float storedAlpha = MathHelper.clamp(storage.getAlpha(), 0.0f, 1.0f);
            int alpha = format.normalize(storedAlpha);
            Matrix4f modelViewMatrix = storage.modelViewMatrix();
            modelViewMatrix.translate(0.0f, 0.0f, 1.0E-6f * index);
            buffer.addVertex(modelViewMatrix, 0.0f, 0.0f, 0.0f).setUv(0.0f, 0.0f).setColor(format.pack(-1, alpha));
            buffer.addVertex(modelViewMatrix, 0.0f, 1.0f, 0.0f).setUv(0.0f, 1.0f).setColor(format.pack(-1, alpha));
            buffer.addVertex(modelViewMatrix, 1.0f, 1.0f, 0.0f).setUv(1.0f, 1.0f).setColor(format.pack(-1, alpha));
            buffer.addVertex(modelViewMatrix, 1.0f, 0.0f, 0.0f).setUv(1.0f, 0.0f).setColor(format.pack(-1, alpha));
            index++;
        }
        MeshRenderer.drawImmediate(buffer.build(), RenderStates.CAPE_PARTICLES);
    }

    private void spawnParticle(DataWatcher dataWatcher, List<CapeParticle> particles, float currentTickTime) {
        if (((Float) dataWatcher.get("lastParticleSpawned", Float.valueOf(0.0f)).get()).floatValue() < currentTickTime) {
            dataWatcher.set("lastParticleSpawned", Float.valueOf(currentTickTime + 8.0f));
            float x = PARTICLE_MIN_X + (RANDOM.nextFloat() * 0.5f);
            float y = PARTICLE_MIN_Y + RANDOM.nextFloat();
            particles.add(new CapeParticle(MathHelper.clamp(x, PARTICLE_MIN_X, 0.8f), MathHelper.clamp(y, PARTICLE_MIN_Y, 0.8f), currentTickTime));
        }
    }
}
