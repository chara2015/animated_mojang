package net.labymod.core.client.render.schematic.particle.particle;

import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.core.client.render.schematic.SchematicAccessor;
import net.labymod.core.client.render.schematic.block.material.material.TorchMaterial;
import net.labymod.core.client.render.schematic.particle.Particle;
import net.labymod.laby3d.api.vertex.VertexConsumer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/schematic/particle/particle/FlameParticle.class */
public class FlameParticle extends Particle {
    private final float initialSize;

    public FlameParticle(SchematicAccessor level, double x, double y, double z, TorchMaterial.Type type) {
        super(level, x, y, z, 0.0f, 0.0f, 0.0f);
        this.motionX *= 0.0099d;
        this.motionY *= 0.0099d;
        this.motionZ *= 0.0099d;
        this.color = 16777215;
        this.maxTicksExisted = ((int) (8.0d / ((Math.random() * 0.8d) + 0.2d))) + 4;
        this.initialSize = this.size;
        this.resourceLocation = createResource(type.getParticleResourceName());
    }

    @Override // net.labymod.core.client.render.schematic.particle.Particle
    public void onUpdate() {
        this.prevX = this.x;
        this.prevY = this.y;
        this.prevZ = this.z;
        int i = this.ticksExisted;
        this.ticksExisted = i + 1;
        if (i >= this.maxTicksExisted) {
            kill();
        }
        this.x += this.motionX;
        this.y += this.motionY;
        this.z += this.motionZ;
        this.motionX *= 0.95d;
        this.motionY *= 0.95d;
        this.motionZ *= 0.95d;
    }

    @Override // net.labymod.core.client.render.schematic.particle.Particle
    public void render(VertexConsumer consumer, TextureAtlas atlas, float partialTicks, float offsetX, float offsetY, float offsetZ, float offset2X, float offset2Z) {
        float progress = (this.ticksExisted + partialTicks) / this.maxTicksExisted;
        this.size = this.initialSize * (1.0f - ((progress * progress) * 0.5f));
        super.render(consumer, atlas, partialTicks, offsetX, offsetY, offsetZ, offset2X, offset2Z);
    }
}
