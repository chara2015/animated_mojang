package net.labymod.api.laby3d.shaders.block;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/shaders/block/ClipDataUniformBlock.class */
public class ClipDataUniformBlock extends AbstractUniformBlock {
    public static final String NAME = "ClipData";
    private static final String CLIP_BOUNDS_NAME = "ClipBounds";
    private static final String CLIP_CORNER_RADIUS_NAME = "ClipCornerRadius";
    private static final String CLIP_ENABLED_NAME = "ClipEnabled";
    public static final LayoutDefinition LAYOUT = LayoutDefinition.std140().add(CLIP_BOUNDS_NAME, DataType.VEC4, UniformType.VEC4).add(CLIP_CORNER_RADIUS_NAME, DataType.VEC4, UniformType.VEC4).add(CLIP_ENABLED_NAME, DataType.SCALAR, UniformType.FLOAT).build();
    private final UniformProperty<Vector4f> clipBounds;
    private final UniformProperty<Vector4f> clipCornerRadius;
    private final UniformProperty<Float> clipEnabled;

    public ClipDataUniformBlock(RenderDevice device) {
        super(device, NAME, LAYOUT);
        this.clipBounds = createProperty(CLIP_BOUNDS_NAME, Vector4fUniformProperty::new);
        this.clipCornerRadius = createProperty(CLIP_CORNER_RADIUS_NAME, Vector4fUniformProperty::new);
        this.clipEnabled = createProperty(CLIP_ENABLED_NAME, FloatUniformProperty::new);
    }

    public UniformProperty<Vector4f> clipBounds() {
        return this.clipBounds;
    }

    public UniformProperty<Vector4f> clipCornerRadius() {
        return this.clipCornerRadius;
    }

    public UniformProperty<Float> clipEnabled() {
        return this.clipEnabled;
    }

    protected List<UniformProperty<?>> buildProperties() {
        return List.of(this.clipBounds, this.clipCornerRadius, this.clipEnabled);
    }
}
