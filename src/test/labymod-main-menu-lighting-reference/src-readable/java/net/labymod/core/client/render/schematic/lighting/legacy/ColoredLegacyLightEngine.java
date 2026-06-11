package net.labymod.core.client.render.schematic.lighting.legacy;

import net.labymod.api.util.math.MathHelper;
import net.labymod.core.client.render.schematic.SchematicAccessor;
import net.labymod.core.client.render.schematic.block.Block;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/schematic/lighting/legacy/ColoredLegacyLightEngine.class */
public class ColoredLegacyLightEngine implements WorldLightAccessor, LegacyLightEngine {
    private final SchematicAccessor world;
    private final DefaultLegacyLightEngine redEngine;
    private final DefaultLegacyLightEngine greenEngine;
    private final DefaultLegacyLightEngine blueEngine;

    public ColoredLegacyLightEngine(SchematicAccessor world, int width, int height, int length, int redSkyLevel, int greenSkyLevel, int blueSkyLevel) {
        this.world = world;
        this.redEngine = new DefaultLegacyLightEngine(this, width, height, length, redSkyLevel);
        this.greenEngine = new DefaultLegacyLightEngine(this, width, height, length, greenSkyLevel);
        this.blueEngine = new DefaultLegacyLightEngine(this, width, height, length, blueSkyLevel);
    }

    @Override // net.labymod.core.client.render.schematic.lighting.legacy.WorldLightAccessor
    public Block getBlockAt(int x, int y, int z) {
        return this.world.getBlockAt(x, y, z);
    }

    @Override // net.labymod.core.client.render.schematic.lighting.legacy.WorldLightAccessor
    public boolean isLightSource(int x, int y, int z) {
        return this.world.isLightSource(x, y, z);
    }

    @Override // net.labymod.core.client.render.schematic.lighting.legacy.WorldLightAccessor
    public boolean isTranslucent(int x, int y, int z) {
        return this.world.isTranslucent(x, y, z);
    }

    @Override // net.labymod.core.client.render.schematic.lighting.legacy.WorldLightAccessor
    public boolean isFullBlock(int x, int y, int z) {
        return this.world.isFullBlock(x, y, z);
    }

    @Override // net.labymod.core.client.render.schematic.lighting.legacy.WorldLightAccessor
    public int getLightLevel(DefaultLegacyLightEngine engine, Block block) {
        int maxLevel = block.material().getLight(block);
        int color = block.material().getLightColor(block);
        int red = (color >> 16) & 255;
        int green = (color >> 8) & 255;
        int blue = color & 255;
        int maxColor = Math.max(Math.max(red, green), blue);
        if (maxColor > 0) {
            float factor = 255.0f / maxColor;
            red = applyColorCorrectionPre(red, factor, 0);
            green = applyColorCorrectionPre(green, factor, 1);
            blue = applyColorCorrectionPre(blue, factor, 2);
        }
        if (engine == this.redEngine) {
            return (int) ((maxLevel / 255.0f) * red);
        }
        if (engine == this.greenEngine) {
            return (int) ((maxLevel / 255.0f) * green);
        }
        if (engine == this.blueEngine) {
            return (int) ((maxLevel / 255.0f) * blue);
        }
        return 0;
    }

    @Override // net.labymod.core.client.render.schematic.lighting.legacy.LegacyLightEngine
    public int getLightAt(int x, int y, int z) {
        return Math.max(Math.max(this.redEngine.getLightAt(x, y, z), this.greenEngine.getLightAt(x, y, z)), this.blueEngine.getLightAt(x, y, z));
    }

    @Override // net.labymod.core.client.render.schematic.lighting.legacy.LegacyLightEngine
    public float getRedStrengthAt(int x, int y, int z) {
        float level = this.redEngine.getAverageLightLevelAt(x, y, z);
        return applyColorCorrectionPost(getStrengthFrom(level), 0);
    }

    @Override // net.labymod.core.client.render.schematic.lighting.legacy.LegacyLightEngine
    public float getGreenStrengthAt(int x, int y, int z) {
        float level = this.greenEngine.getAverageLightLevelAt(x, y, z);
        return applyColorCorrectionPost(getStrengthFrom(level), 1);
    }

    @Override // net.labymod.core.client.render.schematic.lighting.legacy.LegacyLightEngine
    public float getBlueStrengthAt(int x, int y, int z) {
        float level = this.blueEngine.getAverageLightLevelAt(x, y, z);
        return applyColorCorrectionPost(getStrengthFrom(level), 2);
    }

    @Override // net.labymod.core.client.render.schematic.lighting.legacy.LegacyLightEngine
    public void handleLightUpdates() {
        this.redEngine.handleLightUpdates();
        this.greenEngine.handleLightUpdates();
        this.blueEngine.handleLightUpdates();
    }

    @Override // net.labymod.core.client.render.schematic.lighting.legacy.LegacyLightEngine
    public boolean isDirty() {
        return this.redEngine.isDirty() || this.greenEngine.isDirty() || this.blueEngine.isDirty();
    }

    @Override // net.labymod.core.client.render.schematic.lighting.legacy.LegacyLightEngine
    public boolean isInProgress() {
        return this.redEngine.isInProgress() || this.greenEngine.isInProgress() || this.blueEngine.isInProgress();
    }

    @Override // net.labymod.core.client.render.schematic.lighting.legacy.LegacyLightEngine
    public void reset() {
        this.redEngine.reset();
        this.greenEngine.reset();
        this.blueEngine.reset();
    }

    @Override // net.labymod.core.client.render.schematic.lighting.legacy.LegacyLightEngine
    public float getAverageLightLevelAt(int x, int y, int z) {
        return Math.max(Math.max(this.redEngine.getAverageLightLevelAt(x, y, z), this.greenEngine.getAverageLightLevelAt(x, y, z)), this.blueEngine.getAverageLightLevelAt(x, y, z));
    }

    @Override // net.labymod.core.client.render.schematic.lighting.legacy.LegacyLightEngine
    public byte[] getData() {
        throw new UnsupportedOperationException("Select an engine first");
    }

    @Override // net.labymod.core.client.render.schematic.lighting.legacy.LegacyLightEngine
    public void setData(byte[] data) {
        throw new UnsupportedOperationException("Select an engine first");
    }

    private int applyColorCorrectionPre(int value, float factor, int channel) {
        return (int) MathHelper.clamp(value * factor * 1.45f, 0.0f, 255.0f);
    }

    private float applyColorCorrectionPost(float strength, int channel) {
        return (float) Math.min(Math.pow(2.718281828459045d, (-2.5f) + (strength * 2.5f)), 1.0d);
    }

    @Override // net.labymod.core.client.render.schematic.lighting.legacy.LegacyLightEngine
    public float applyBrightness(float value) {
        float x = brightnessSetting.get().floatValue();
        float curve = ((float) (-Math.pow(2.718281828459045d, 1.0f - (x * 1.2f)))) + 1.718f;
        return Math.min(value * curve, 1.0f);
    }

    public DefaultLegacyLightEngine getRedEngine() {
        return this.redEngine;
    }

    public DefaultLegacyLightEngine getGreenEngine() {
        return this.greenEngine;
    }

    public DefaultLegacyLightEngine getBlueEngine() {
        return this.blueEngine;
    }
}
