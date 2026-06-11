package net.labymod.core.laby3d.renderer;

import java.util.EnumMap;
import java.util.function.Function;
import javax.inject.Singleton;
import net.labymod.api.client.gfx.pipeline.renderer.shader.ShaderUtil;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.laby3d.model.ModelInstance;
import net.labymod.api.laby3d.renderer.GeometrySubmitter;
import net.labymod.api.laby3d.textures.TextureBindingSet;
import net.labymod.api.models.Implements;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/renderer/DefaultGeometrySubmitter.class */
@Singleton
@Implements(GeometrySubmitter.class)
public class DefaultGeometrySubmitter implements GeometrySubmitter {
    private final EnumMap<GeometrySubmitter.Type, GeometrySubmitter> submitters = new EnumMap<>(GeometrySubmitter.Type.class);
    private GeometrySubmitter.Type type;
    private GeometrySubmitter currentSubmitter;
    private GeometrySubmitter immediateSubmitter;

    public DefaultGeometrySubmitter() {
        registerSubmitter(GeometrySubmitter.Type.LEVEL, GeometryNodeSubmitter::new);
        registerSubmitter(GeometrySubmitter.Type.HAND, GeometryNodeSubmitter::new);
        registerSubmitter(GeometrySubmitter.Type.ENTITY_IN_UI, GeometryNodeSubmitter::new);
        this.immediateSubmitter = new ImmediateGeometrySubmitter();
        setType(GeometrySubmitter.Type.LEVEL);
    }

    private void registerSubmitter(GeometrySubmitter.Type type, Function<GeometrySubmitter.Type, GeometrySubmitter> factory) {
        this.submitters.put(type, factory.apply(type));
    }

    @Override // net.labymod.api.laby3d.renderer.GeometrySubmitter
    public GeometrySubmitter.Type getType() {
        return this.type;
    }

    @Override // net.labymod.api.laby3d.renderer.GeometrySubmitter
    public void setType(GeometrySubmitter.Type type) {
        if (this.type == type) {
            return;
        }
        this.type = type;
        this.currentSubmitter = this.submitters.get(type);
    }

    @Override // net.labymod.api.laby3d.renderer.GeometrySubmitter
    public <S> void submitModel(S state, ModelInstance<S> modelInstance, Stack stack, RenderState renderState, TextureBindingSet textureBindingSet, int lightCoords, int overlayCoords, int order) {
        currentSubmitter().submitModel(state, modelInstance, stack, renderState, textureBindingSet, lightCoords, overlayCoords, order);
    }

    @Override // net.labymod.api.laby3d.renderer.GeometrySubmitter
    public void submitCustomGeometry(Stack stack, RenderState renderState, TextureBindingSet textureBindingSet, GeometrySubmitter.CustomGeometryRenderer renderer) {
        currentSubmitter().submitCustomGeometry(stack, renderState, textureBindingSet, renderer);
    }

    @Override // net.labymod.api.laby3d.renderer.GeometrySubmitter
    public void render(CommandBuffer cmd, boolean gui) {
        currentSubmitter().render(cmd, gui);
    }

    private GeometrySubmitter currentSubmitter() {
        if (ShaderUtil.isShaderSelected()) {
            return this.immediateSubmitter;
        }
        return this.currentSubmitter;
    }
}
