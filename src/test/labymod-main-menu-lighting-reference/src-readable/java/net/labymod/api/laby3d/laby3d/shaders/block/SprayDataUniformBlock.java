package net.labymod.api.laby3d.shaders.block;

import java.util.List;
import net.labymod.laby3d.api.RenderDevice;
import net.labymod.laby3d.api.buffers.layout.DataType;
import net.labymod.laby3d.api.buffers.layout.LayoutDefinition;
import net.labymod.laby3d.api.shaders.UniformType;
import net.labymod.laby3d.api.shaders.block.AbstractUniformBlock;
import net.labymod.laby3d.api.shaders.block.property.FloatUniformProperty;
import net.labymod.laby3d.api.shaders.block.property.UniformProperty;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/shaders/block/SprayDataUniformBlock.class */
public class SprayDataUniformBlock extends AbstractUniformBlock {
    public static final String NAME = "SprayData";
    private static final String WEAR_NAME = "Wear";
    public static final LayoutDefinition LAYOUT = LayoutDefinition.std140().add(WEAR_NAME, DataType.SCALAR, UniformType.FLOAT).build();
    private final UniformProperty<Float> wear;

    public SprayDataUniformBlock(RenderDevice device) {
        super(device, NAME, LAYOUT);
        this.wear = createProperty(WEAR_NAME, FloatUniformProperty::new);
    }

    public UniformProperty<Float> wear() {
        return this.wear;
    }

    protected List<UniformProperty<?>> buildProperties() {
        return List.of(this.wear);
    }
}
