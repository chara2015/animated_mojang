package net.labymod.core.laby3d.opengl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.labymod.api.client.gfx.GlConst;
import net.labymod.laby3d.api.opengl.GlFunctions;
import net.labymod.laby3d.api.opengl.state.track.GlAlphaTest;
import net.labymod.laby3d.api.opengl.state.track.GlBlend;
import net.labymod.laby3d.api.opengl.state.track.GlColorMask;
import net.labymod.laby3d.api.opengl.state.track.GlCull;
import net.labymod.laby3d.api.opengl.state.track.GlDepthTest;
import net.labymod.laby3d.api.opengl.state.track.GlFramebuffer;
import net.labymod.laby3d.api.opengl.state.track.GlLighting;
import net.labymod.laby3d.api.opengl.state.track.GlPolygonOffset;
import net.labymod.laby3d.api.opengl.state.track.GlScissorTest;
import net.labymod.laby3d.api.opengl.state.track.GlState;
import net.labymod.laby3d.api.opengl.state.track.GlStateFactory;
import net.labymod.laby3d.api.opengl.state.track.GlTexture2D;
import net.labymod.laby3d.api.opengl.state.track.GlViewport;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/OptimizedGlStateFactory.class */
public class OptimizedGlStateFactory implements GlStateFactory {
    public GlState createState(GlFunctions functions) {
        GlAlphaTest alphaTest = null;
        if (functions.isAlphaTestSupported()) {
            alphaTest = createAlphaTest(GlStateTracker.ALPHA_TEST);
        }
        GlBlend blend = createBlend(GlStateTracker.BLEND);
        GlCull cull = createCull(GlStateTracker.CULL);
        GlDepthTest depthTest = createDepthTest(GlStateTracker.DEPTH_TEST);
        GlFramebuffer framebuffer = createFramebuffer(GlStateTracker.FRAMEBUFFER);
        GlPolygonOffset polygonOffset = createPolygonOffset(GlStateTracker.POLYGON_OFFSET);
        GlScissorTest scissorTest = createScissorTest(GlStateTracker.SCISSOR_TEST);
        GlViewport viewport = createViewport(GlStateTracker.VIEWPORT);
        GlColorMask colorMask = createColorMask(GlStateTracker.COLOR_MASK);
        GlLighting lighting = createLighting(GlStateTracker.LIGHTING);
        List<GlTexture2D> textures = createTextures(GlStateTracker.TEXTURES.get());
        int activeProgram = GlStateTracker.currentProgram;
        int activeTextureUnit = GlStateTracker.activeTexture + GlConst.GL_TEXTURE0;
        int shadeMode = GlStateTracker.shadeMode;
        return new GlState(alphaTest, blend, cull, depthTest, framebuffer, polygonOffset, scissorTest, viewport, colorMask, textures, lighting, activeProgram, activeTextureUnit, shadeMode, Collections.emptyList());
    }

    private GlAlphaTest createAlphaTest(GlAlphaTestState alphaTestState) {
        return new GlAlphaTest(alphaTestState.isEnabled(), alphaTestState.getFunc(), alphaTestState.getRef());
    }

    private GlBlend createBlend(GlBlendState blendState) {
        return new GlBlend(blendState.isEnabled(), blendState.getEquationRgb(), blendState.getEquationAlpha(), blendState.getSrcRgb(), blendState.getDstRgb(), blendState.getSrcAlpha(), blendState.getDstAlpha());
    }

    private GlCull createCull(GlBooleanState booleanState) {
        return new GlCull(booleanState.isEnabled());
    }

    private GlDepthTest createDepthTest(GlDepthTestState depthTestState) {
        return new GlDepthTest(depthTestState.isEnabled(), depthTestState.getFunc(), depthTestState.isWriteEnabled());
    }

    private GlFramebuffer createFramebuffer(GlFramebufferState framebufferState) {
        return new GlFramebuffer(framebufferState.getDrawFramebuffer(), framebufferState.getReadFramebuffer());
    }

    private GlPolygonOffset createPolygonOffset(GlPolygonOffsetState polygonOffsetState) {
        return new GlPolygonOffset(polygonOffsetState.isEnabled(), polygonOffsetState.getFactor(), polygonOffsetState.getUnits());
    }

    private GlScissorTest createScissorTest(GlScissorTestState scissorTestState) {
        return new GlScissorTest(scissorTestState.isEnabled(), scissorTestState.getX(), scissorTestState.getY(), scissorTestState.getWidth(), scissorTestState.getHeight());
    }

    private GlViewport createViewport(GlViewportState viewportState) {
        return new GlViewport(viewportState.getX(), viewportState.getY(), viewportState.getWidth(), viewportState.getHeight());
    }

    private GlColorMask createColorMask(GlColorMaskState colorMaskState) {
        return new GlColorMask(colorMaskState.isRed(), colorMaskState.isGreen(), colorMaskState.isBlue(), colorMaskState.isAlpha());
    }

    private GlLighting createLighting(GlBooleanState booleanState) {
        return new GlLighting(booleanState.isEnabled());
    }

    private List<GlTexture2D> createTextures(GlTextureState[] textures) {
        List<GlTexture2D> glTextures = new ArrayList<>(textures.length);
        for (int index = 0; index < textures.length; index++) {
            GlTextureState textureState = textures[index];
            GlTexture2D glTexture2D = new GlTexture2D(textureState.isEnabled(), index, textureState.getTexture());
            glTextures.add(glTexture2D);
        }
        return glTextures;
    }
}
