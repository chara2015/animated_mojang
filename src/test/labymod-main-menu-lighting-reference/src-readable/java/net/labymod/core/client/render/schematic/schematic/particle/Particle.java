package net.labymod.core.client.render.schematic.particle;

import java.util.Random;
import net.labymod.api.client.gfx.pipeline.texture.atlas.AnimatedTextureSprite;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureSprite;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureUV;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.math.MathHelper;
import net.labymod.core.client.gui.background.DynamicBackgroundController;
import net.labymod.core.client.render.schematic.SchematicAccessor;
import net.labymod.laby3d.api.vertex.VertexConsumer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/schematic/particle/Particle.class */
public abstract class Particle {
    protected final SchematicAccessor level;
    protected double x;
    protected double y;
    protected double z;
    protected double prevX;
    protected double prevY;
    protected double prevZ;
    protected double motionX;
    protected double motionY;
    protected double motionZ;
    protected int maxTicksExisted;
    protected int ticksExisted;
    protected int color;
    protected float size;
    protected ResourceLocation resourceLocation;
    protected boolean isDead;
    protected final Random random = new Random();
    protected double randomX = this.random.nextFloat() * 3.0f;
    protected double randomY = this.random.nextFloat() * 3.0f;
    protected double randomZ = ((this.random.nextFloat() * 0.5f) + 0.5f) * 2.0f;

    public Particle(SchematicAccessor level, double x, double y, double z, float motionX, float motionY, float motionZ) {
        this.level = level;
        this.x = x;
        this.y = y;
        this.z = z;
        this.prevX = this.x;
        this.prevY = this.y;
        this.prevZ = this.z;
        this.motionX = ((double) motionX) + (((Math.random() * 2.0d) - 1.0d) * 0.4d);
        this.motionY = ((double) motionY) + (((Math.random() * 2.0d) - 1.0d) * 0.4d);
        this.motionZ = ((double) motionZ) + (((Math.random() * 2.0d) - 1.0d) * 0.4d);
        double strength = (Math.random() + Math.random() + 1.0d) * 0.15000000596046448d;
        double length = Math.sqrt((this.motionX * this.motionX) + (this.motionY * this.motionY) + (this.motionZ * this.motionZ));
        this.motionX = (this.motionX / length) * strength * 0.4d;
        this.motionY = ((this.motionY / length) * strength * 0.4d) + 0.1d;
        this.motionZ = (this.motionZ / length) * strength * 0.4d;
        this.size = ((this.random.nextFloat() * 0.5f) + 0.5f) * 2.0f;
        this.maxTicksExisted = MathHelper.floor(4.0d / ((((double) this.random.nextFloat()) * 0.9d) + 0.1d));
        this.color = -1;
    }

    protected ResourceLocation createResource(String id) {
        return ResourceLocation.create("labymod", "particle/" + id);
    }

    public void onUpdate() {
        this.prevX = this.x;
        this.prevY = this.y;
        this.prevZ = this.z;
        if (this.ticksExisted >= this.maxTicksExisted) {
            kill();
        }
        this.motionY -= 0.04d;
        this.x += this.motionX;
        this.y += this.motionY;
        this.z += this.motionZ;
        this.motionX *= 0.98d;
        this.motionY *= 0.98d;
        this.motionZ *= 0.98d;
    }

    public boolean isDead() {
        return this.isDead;
    }

    public void render(VertexConsumer consumer, TextureAtlas atlas, float partialTicks, float offsetX, float offsetY, float offsetZ, float offset2X, float offset2Z) {
        TextureSprite sprite;
        if (this.resourceLocation == null || (sprite = atlas.findSprite(this.resourceLocation)) == null) {
            return;
        }
        TextureUV uv = sprite.uv();
        if (sprite instanceof AnimatedTextureSprite) {
            AnimatedTextureSprite animatedTextureSprite = (AnimatedTextureSprite) sprite;
            uv = animatedTextureSprite.uv(getFrame(animatedTextureSprite.getFrames()));
        }
        float minU = uv.getMinU();
        float minV = uv.getMinV();
        float maxU = uv.getMaxU();
        float maxV = uv.getMaxV();
        float size = 0.1f * this.size;
        float r = ((this.color >> 16) & 255) / 255.0f;
        float g = ((this.color >> 8) & 255) / 255.0f;
        float b = (this.color & 255) / 255.0f;
        float x = (float) MathHelper.lerp(this.x, this.prevX, partialTicks);
        float y = (float) MathHelper.lerp(this.y, this.prevY, partialTicks);
        float z = (float) MathHelper.lerp(this.z, this.prevZ, partialTicks);
        addVertexWithUV(consumer, (x - (offsetX * size)) - (offset2X * size), y - (offsetY * size), (z - (offsetZ * size)) - (offset2Z * size), minU, maxV, r, g, b);
        addVertexWithUV(consumer, (x - (offsetX * size)) + (offset2X * size), y + (offsetY * size), (z - (offsetZ * size)) + (offset2Z * size), minU, minV, r, g, b);
        addVertexWithUV(consumer, x + (offsetX * size) + (offset2X * size), y + (offsetY * size), z + (offsetZ * size) + (offset2Z * size), maxU, minV, r, g, b);
        addVertexWithUV(consumer, (x + (offsetX * size)) - (offset2X * size), y - (offsetY * size), (z + (offsetZ * size)) - (offset2Z * size), maxU, maxV, r, g, b);
    }

    protected int getFrame(int frames) {
        return 0;
    }

    private void addVertexWithUV(VertexConsumer consumer, float x, float y, float z, float u, float v, float r, float g, float b) {
        consumer.addVertex(x, y, z).setUv(u, v).setColor(r, g, b, 1.0f).setFloat(DynamicBackgroundController.LIGHTING ? 2.0f : 0.0f).setNormal(0.0f, 0.0f, 0.0f);
    }

    public void kill() {
        this.isDead = true;
    }
}
