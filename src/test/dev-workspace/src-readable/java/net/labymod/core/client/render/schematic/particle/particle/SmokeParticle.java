package net.labymod.core.client.render.schematic.particle.particle;

import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.core.client.render.schematic.SchematicAccessor;
import net.labymod.core.client.render.schematic.particle.Particle;
import net.labymod.laby3d.api.vertex.VertexConsumer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/schematic/particle/particle/SmokeParticle.class */
public class SmokeParticle extends Particle {
    private final float initialSize;

    public SmokeParticle(SchematicAccessor level, double x, double y, double z) {
        this(level, x, y, z, 1.0f);
    }

    public SmokeParticle(SchematicAccessor level, double x, double y, double z, float scale) {
        super(level, x, y, z, 0.0f, 0.0f, 0.0f);
        this.motionX *= 0.1d;
        this.motionY *= 0.1d;
        this.motionZ *= 0.1d;
        float brightness = (float) (Math.random() * 0.3d);
        this.color = ColorFormat.ARGB32.pack(brightness, brightness, brightness, 1.0f);
        this.size *= 0.75f;
        this.size *= scale;
        this.initialSize = this.size;
        this.maxTicksExisted = (int) (8.0d / ((Math.random() * 0.8d) + 0.2d));
        this.maxTicksExisted = (int) (this.maxTicksExisted * scale);
        onUpdate();
        this.resourceLocation = createResource("generic");
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
        this.motionY += 0.001d;
        this.x += this.motionX;
        this.y += this.motionY;
        this.z += this.motionZ;
        if (this.y == this.prevY) {
            this.motionX *= 1.1d;
            this.motionZ *= 1.1d;
        }
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

    @Override // net.labymod.core.client.render.schematic.particle.Particle
    protected int getFrame(int frames) {
        float progress = this.ticksExisted / this.maxTicksExisted;
        return (frames - ((int) (progress * 7.0f))) - 1;
    }
}
