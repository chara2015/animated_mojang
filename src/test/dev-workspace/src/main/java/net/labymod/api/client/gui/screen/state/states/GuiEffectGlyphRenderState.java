package net.labymod.api.client.gui.screen.state.states;

import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphProperties;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphRenderable;
import net.labymod.api.client.gui.screen.util.scissor.ScissorArea;
import net.labymod.api.laby3d.math.JomlMath;
import net.labymod.api.laby3d.pipeline.material.GuiMaterial;
import net.labymod.laby3d.api.vertex.VertexConsumer;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/states/GuiEffectGlyphRenderState.class */
public class GuiEffectGlyphRenderState extends AbstractGuiRenderState {
    private final GlyphRenderable effect;
    private final GlyphProperties properties;

    public GuiEffectGlyphRenderState(Matrix4f pose, GlyphRenderable effect, @Nullable ScissorArea scissorArea, GlyphProperties properties) {
        super(GuiMaterial.textured(effect.guiRenderState(), effect.textureView()), pose, 0.0f, 0.0f, 16.0f, 16.0f, scissorArea);
        this.effect = effect;
        this.properties = properties;
    }

    @Override // net.labymod.api.client.gui.screen.state.GuiRenderState
    public void buildVertices(VertexConsumer consumer) {
        Matrix4f pose = pose();
        Matrix4f glyphPose = JomlMath.extractMatrix(pose);
        this.effect.buildVertices(glyphPose, consumer, RenderEnvironmentContext.FULL_BRIGHT, true, this.properties);
    }
}
