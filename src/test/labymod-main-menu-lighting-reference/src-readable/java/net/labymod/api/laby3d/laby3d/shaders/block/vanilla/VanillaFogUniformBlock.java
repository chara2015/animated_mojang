package net.labymod.api.laby3d.shaders.block.vanilla;

import java.util.List;
import net.labymod.laby3d.api.RenderDevice;
import net.labymod.laby3d.api.buffers.layout.DataType;
import net.labymod.laby3d.api.buffers.layout.LayoutDefinition;
import net.labymod.laby3d.api.shaders.UniformType;
import net.labymod.laby3d.api.shaders.block.AbstractUniformBlock;
import net.labymod.laby3d.api.shaders.block.property.FloatUniformProperty;
import net.labymod.laby3d.api.shaders.block.property.UniformProperty;
import net.labymod.laby3d.api.shaders.block.property.Vector4fUniformProperty;
import org.joml.Vector4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/shaders/block/vanilla/VanillaFogUniformBlock.class */
public class VanillaFogUniformBlock extends AbstractUniformBlock {
    public static final String NAME = "Fog";
    private static final String FOG_COLOR_NAME = "FogColor";
    private static final String FOG_ENVIRONMENTAL_START = "FogEnvironmentalStart";
    private static final String FOG_ENVIRONMENTAL_END = "FogEnvironmentalEnd";
    private static final String FOG_RENDER_DISTANCE_START = "FogRenderDistanceStart";
    private static final String FOG_RENDER_DISTANCE_END = "FogRenderDistanceEnd";
    private static final String FOG_SKY_END = "FogSkyEnd";
    private static final String FOG_CLOUDS_END = "FogCloudsEnd";
    public static final LayoutDefinition LAYOUT = LayoutDefinition.std140().add(FOG_COLOR_NAME, DataType.VEC4, UniformType.VEC4).add(FOG_ENVIRONMENTAL_START, DataType.SCALAR, UniformType.FLOAT).add(FOG_ENVIRONMENTAL_END, DataType.SCALAR, UniformType.FLOAT).add(FOG_RENDER_DISTANCE_START, DataType.SCALAR, UniformType.FLOAT).add(FOG_RENDER_DISTANCE_END, DataType.SCALAR, UniformType.FLOAT).add(FOG_SKY_END, DataType.SCALAR, UniformType.FLOAT).add(FOG_CLOUDS_END, DataType.SCALAR, UniformType.FLOAT).build();
    private final UniformProperty<Vector4f> fogColor;
    private final UniformProperty<Float> fogEnvironmentalStart;
    private final UniformProperty<Float> fogEnvironmentalEnd;
    private final UniformProperty<Float> fogRenderDistanceStart;
    private final UniformProperty<Float> fogRenderDistanceEnd;
    private final UniformProperty<Float> fogSkyEnd;
    private final UniformProperty<Float> fogCloudsEnd;

    public VanillaFogUniformBlock(RenderDevice device) {
        super(device, NAME, LAYOUT);
        this.fogColor = createProperty(FOG_COLOR_NAME, Vector4fUniformProperty::new);
        this.fogEnvironmentalStart = createProperty(FOG_ENVIRONMENTAL_START, FloatUniformProperty::new);
        this.fogEnvironmentalEnd = createProperty(FOG_ENVIRONMENTAL_END, FloatUniformProperty::new);
        this.fogRenderDistanceStart = createProperty(FOG_RENDER_DISTANCE_START, FloatUniformProperty::new);
        this.fogRenderDistanceEnd = createProperty(FOG_RENDER_DISTANCE_END, FloatUniformProperty::new);
        this.fogSkyEnd = createProperty(FOG_SKY_END, FloatUniformProperty::new);
        this.fogCloudsEnd = createProperty(FOG_CLOUDS_END, FloatUniformProperty::new);
    }

    public UniformProperty<Vector4f> fogColor() {
        return this.fogColor;
    }

    public UniformProperty<Float> fogEnvironmentalStart() {
        return this.fogEnvironmentalStart;
    }

    public UniformProperty<Float> fogEnvironmentalEnd() {
        return this.fogEnvironmentalEnd;
    }

    public UniformProperty<Float> fogRenderDistanceStart() {
        return this.fogRenderDistanceStart;
    }

    public UniformProperty<Float> fogRenderDistanceEnd() {
        return this.fogRenderDistanceEnd;
    }

    public UniformProperty<Float> fogSkyEnd() {
        return this.fogSkyEnd;
    }

    public UniformProperty<Float> fogCloudsEnd() {
        return this.fogCloudsEnd;
    }

    protected List<UniformProperty<?>> buildProperties() {
        return List.of(this.fogColor, this.fogEnvironmentalStart, this.fogEnvironmentalEnd, this.fogRenderDistanceStart, this.fogRenderDistanceEnd, this.fogSkyEnd, this.fogCloudsEnd);
    }
}
