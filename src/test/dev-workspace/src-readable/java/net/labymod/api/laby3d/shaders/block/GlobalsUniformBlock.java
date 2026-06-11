package net.labymod.api.laby3d.shaders.block;

import java.util.List;
import net.labymod.laby3d.api.RenderDevice;
import net.labymod.laby3d.api.buffers.layout.DataType;
import net.labymod.laby3d.api.buffers.layout.LayoutDefinition;
import net.labymod.laby3d.api.shaders.UniformType;
import net.labymod.laby3d.api.shaders.block.AbstractUniformBlock;
import net.labymod.laby3d.api.shaders.block.property.FloatUniformProperty;
import net.labymod.laby3d.api.shaders.block.property.UniformProperty;
import net.labymod.laby3d.api.shaders.block.property.Vector2fUniformProperty;
import org.joml.Vector2f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/shaders/block/GlobalsUniformBlock.class */
public class GlobalsUniformBlock extends AbstractUniformBlock {
    public static final String NAME = "Globals";
    private static final String SCREEN_SIZE_NAME = "ScreenSize";
    private static final String GAME_TIME_NAME = "GameTime";
    private static final String MOUSE_POSITION_NAME = "MousePosition";
    public static final LayoutDefinition LAYOUT = LayoutDefinition.std140().add(SCREEN_SIZE_NAME, DataType.VEC2, UniformType.VEC2).add(GAME_TIME_NAME, DataType.SCALAR, UniformType.FLOAT).add(MOUSE_POSITION_NAME, DataType.VEC2, UniformType.VEC2).build();
    private final UniformProperty<Vector2f> screenSize;
    private final UniformProperty<Vector2f> mousePosition;
    private final UniformProperty<Float> gameTime;

    public GlobalsUniformBlock(RenderDevice device) {
        super(device, NAME, LAYOUT);
        this.screenSize = createProperty(SCREEN_SIZE_NAME, Vector2fUniformProperty::new);
        this.gameTime = createProperty(GAME_TIME_NAME, FloatUniformProperty::new);
        this.mousePosition = createProperty(MOUSE_POSITION_NAME, Vector2fUniformProperty::new);
    }

    public UniformProperty<Vector2f> screenSize() {
        return this.screenSize;
    }

    public UniformProperty<Vector2f> mousePosition() {
        return this.mousePosition;
    }

    public UniformProperty<Float> gameTime() {
        return this.gameTime;
    }

    protected List<UniformProperty<?>> buildProperties() {
        return List.of(this.screenSize, this.gameTime, this.mousePosition);
    }
}
