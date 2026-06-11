package net.labymod.core.laby3d.opengl;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.GlConst;
import net.labymod.api.laby3d.foreign.target.ForeignRenderTarget;
import net.labymod.api.laby3d.foreign.target.ForeignRenderTargetRegistry;
import net.labymod.api.laby3d.foreign.target.opengl.GlForeignRenderTarget;
import net.labymod.api.laby3d.foreign.target.opengl.GlForeignRenderTargetRegistry;
import net.labymod.api.laby3d.foreign.texture.ForeignDeviceTexture;
import net.labymod.api.laby3d.foreign.texture.ForeignDeviceTextureRegistry;
import net.labymod.api.laby3d.foreign.texture.opengl.GlForeignDeviceTexture;
import net.labymod.api.laby3d.util.FboWriteTracker;
import net.labymod.api.util.Lazy;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.main.profiler.RenderProfiler;
import net.labymod.laby3d.api.RenderDeviceException;
import net.labymod.laby3d.api.textures.DeviceTexture;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL11C;
import org.lwjgl.opengl.GL13C;
import org.lwjgl.opengl.GL14C;
import org.lwjgl.opengl.GL20C;
import org.lwjgl.opengl.GL30C;
import org.lwjgl.opengl.GL31C;
import org.lwjgl.opengl.GL32C;
import org.lwjgl.opengl.GL45C;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/GlStateTracker.class */
public final class GlStateTracker {
    static int activeTexture;
    static int currentProgram;
    static int currentVao;
    private static final Logging LOGGER = Logging.getLogger();
    public static final FboWriteTracker FBO_WRITE_TRACKER = FboWriteTracker.INSTANCE;
    static final Int2ObjectMap<GlBooleanState> BOOLEAN_STATES = new Int2ObjectOpenHashMap();
    static final GlAlphaTestState ALPHA_TEST = (GlAlphaTestState) create(new GlAlphaTestState());
    static final GlBlendState BLEND = (GlBlendState) create(new GlBlendState());
    static final GlBooleanState CULL = create(new GlBooleanState(GlConst.GL_CULL_FACE));
    static final GlColorLogicOpState COLOR_LOGIC_OP = (GlColorLogicOpState) create(new GlColorLogicOpState());
    static final GlColorMaskState COLOR_MASK = new GlColorMaskState();
    static final GlDepthTestState DEPTH_TEST = (GlDepthTestState) create(new GlDepthTestState());
    static final GlBooleanState LIGHTING = create(new GlBooleanState(GlConst.GL_LIGHTING));
    static final GlScissorTestState SCISSOR_TEST = (GlScissorTestState) create(new GlScissorTestState());
    static final GlPolygonOffsetState POLYGON_OFFSET = (GlPolygonOffsetState) create(new GlPolygonOffsetState());
    static final Lazy<GlTextureState[]> TEXTURES = Lazy.of(() -> {
        int size = GL11.glGetInteger(GlConst.GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS);
        GlTextureState[] states = new GlTextureState[size];
        for (int index = 0; index < size; index++) {
            states[index] = new GlTextureState();
        }
        return states;
    });
    static final Lazy<List<GlVertexAttrib>> ATTRIBUTES = Lazy.of(() -> {
        int maxVertexAttributes = GL11.glGetInteger(GlConst.GL_MAX_VERTEX_ATTRIBS);
        List<GlVertexAttrib> attributes = new ArrayList<>(maxVertexAttributes);
        for (int index = 0; index < maxVertexAttributes; index++) {
            attributes.add(new GlVertexAttrib(index));
        }
        return attributes;
    });
    static final GlViewportState VIEWPORT = new GlViewportState();
    public static final GlFramebufferState FRAMEBUFFER = new GlFramebufferState();
    static boolean depthMask = true;
    static int shadeMode = GlConst.GL_SMOOTH;

    private static <T extends GlBooleanState> T create(T value) {
        BOOLEAN_STATES.put(value.getTarget(), value);
        return value;
    }

    public static void glEnableVertexAttribArray(int index) {
        List<GlVertexAttrib> attribList = ATTRIBUTES.get();
        GlVertexAttrib attrib = attribList.get(index);
        attrib.setEnabled();
        GL20C.glEnableVertexAttribArray(index);
    }

    public static void glDisableVertexAttribArray(int index) {
        List<GlVertexAttrib> attribList = ATTRIBUTES.get();
        GlVertexAttrib attrib = attribList.get(index);
        attrib.setDisabled();
        GL20C.glDisableVertexAttribArray(index);
    }

    public static void glEnable(int target) {
        if (target == 3553) {
            TEXTURES.get()[activeTexture].enable();
        } else {
            GlBooleanState state = (GlBooleanState) BOOLEAN_STATES.get(target);
            if (state != null) {
                state.enable();
            }
        }
        GL11C.glEnable(target);
    }

    public static void glDisable(int target) {
        if (target == 3553) {
            TEXTURES.get()[activeTexture].disable();
        } else {
            GlBooleanState state = (GlBooleanState) BOOLEAN_STATES.get(target);
            if (state != null) {
                state.disable();
            }
        }
        GL11C.glDisable(target);
    }

    public static void glActiveTexture(int texture) {
        activeTexture = texture - GlConst.GL_TEXTURE0;
        GL13C.glActiveTexture(texture);
    }

    public static int glGenTextures() {
        int textureId = GL11C.glGenTextures();
        registerForeignDeviceTexture(textureId);
        return textureId;
    }

    public static void glGenTextures(int[] textures) {
        GL11C.glGenTextures(textures);
        for (int texture : textures) {
            registerForeignDeviceTexture(texture);
        }
    }

    public static void glGenTextures(IntBuffer buffer) {
        GL11C.glGenTextures(buffer);
        for (int i = 0; i < buffer.remaining(); i++) {
            registerForeignDeviceTexture(buffer.get(i));
        }
        buffer.rewind();
    }

    public static void glBindTexture(int target, int texture) {
        if (target == 3553) {
            TEXTURES.get()[activeTexture].setTexture(texture);
        }
        GlForeignDeviceTexture foreignDeviceTexture = (GlForeignDeviceTexture) foreignDeviceTextureRegistry().get(Integer.valueOf(texture));
        if (foreignDeviceTexture != null) {
            foreignDeviceTexture.setDimension(toDimension(target));
        }
        GL11C.glBindTexture(target, texture);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: net.labymod.laby3d.api.RenderDeviceException */
    private static DeviceTexture.Dimension toDimension(int target) throws RenderDeviceException {
        if (target == 3552) {
            return DeviceTexture.Dimension.TEXTURE_1D;
        }
        if (target == 3553) {
            return DeviceTexture.Dimension.TEXTURE_2D;
        }
        if (target == 32879) {
            return DeviceTexture.Dimension.TEXTURE_3D;
        }
        if (target == 34067) {
            return DeviceTexture.Dimension.TEXTURE_CUBE_MAP;
        }
        if (target == 35864) {
            return DeviceTexture.Dimension.TEXTURE_1D_ARRAY;
        }
        if (target == 35866) {
            return DeviceTexture.Dimension.TEXTURE_2D_ARRAY;
        }
        if (target == 36873) {
            return DeviceTexture.Dimension.TEXTURE_CUBE_MAP_ARRAY;
        }
        if (target == 35882) {
            return DeviceTexture.Dimension.TEXTURE_BUFFER;
        }
        throw new RenderDeviceException("Unknown texture target: " + target);
    }

    public static void nglTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, long pixels) {
        GlTextureState textureState = TEXTURES.get()[activeTexture];
        GlForeignDeviceTexture foreignDeviceTexture = (GlForeignDeviceTexture) foreignDeviceTextureRegistry().get(Integer.valueOf(textureState.getTexture()));
        if (foreignDeviceTexture != null) {
            foreignDeviceTexture.setWidth(width);
            foreignDeviceTexture.setHeight(height);
            foreignDeviceTexture.setFormat(DeviceTexture.Format.R8G8B8A8_UNORM);
        }
        GL11C.nglTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
    }

    public static void glDeleteTextures(int texture) {
        GL11C.glDeleteTextures(texture);
        unregisterForeignDeviceTexture(texture);
    }

    public static void glDeleteTextures(int[] textures) {
        GL11C.glDeleteTextures(textures);
        for (int texture : textures) {
            unregisterForeignDeviceTexture(texture);
        }
    }

    public static void glDeleteTextures(IntBuffer buffer) {
        GL11C.glDeleteTextures(buffer);
        for (int i = 0; i < buffer.remaining(); i++) {
            unregisterForeignDeviceTexture(buffer.get(i));
        }
        buffer.rewind();
    }

    public static void glDepthFunc(int func) {
        DEPTH_TEST.setFunc(func);
        GL11C.glDepthFunc(func);
    }

    public static void glPolygonOffset(float factor, float units) {
        POLYGON_OFFSET.setFactor(factor);
        POLYGON_OFFSET.setUnits(units);
        GL11C.glPolygonOffset(factor, units);
    }

    public static void glColorMask(boolean red, boolean green, boolean blue, boolean alpha) {
        COLOR_MASK.setRed(red);
        COLOR_MASK.setGreen(green);
        COLOR_MASK.setBlue(blue);
        COLOR_MASK.setAlpha(alpha);
        GL11C.glColorMask(red, green, blue, alpha);
    }

    public static void glDepthMask(boolean flag) {
        depthMask = flag;
        GL11C.glDepthMask(flag);
    }

    public static void glScissor(int x, int y, int width, int height) {
        SCISSOR_TEST.setX(x);
        SCISSOR_TEST.setY(y);
        SCISSOR_TEST.setWidth(width);
        SCISSOR_TEST.setHeight(height);
        GL11C.glScissor(x, y, width, height);
    }

    public static void glLogicOp(int op) {
        COLOR_LOGIC_OP.setMode(op);
        GL11C.glLogicOp(op);
    }

    public static void glAlphaFunc(int func, float ref) {
        ALPHA_TEST.setFunc(func);
        ALPHA_TEST.setRef(ref);
        GL11.glAlphaFunc(func, ref);
    }

    public static void glBlendFunc(int sfactor, int dfactor) {
        BLEND.setSrcRgb(sfactor);
        BLEND.setSrcAlpha(sfactor);
        BLEND.setDstRgb(dfactor);
        BLEND.setDstAlpha(dfactor);
        GL11C.glBlendFunc(sfactor, dfactor);
    }

    public static void glBlendFuncSeparate(int srcRGB, int dstRGB, int srcAlpha, int dstAlpha) {
        BLEND.setSrcRgb(srcRGB);
        BLEND.setDstRgb(dstRGB);
        BLEND.setSrcAlpha(srcAlpha);
        BLEND.setDstAlpha(dstAlpha);
        GL14C.glBlendFuncSeparate(srcRGB, dstRGB, srcAlpha, dstAlpha);
    }

    public static void glUseProgram(int program) {
        currentProgram = program;
        GL20C.glUseProgram(program);
    }

    public static void glUseProgramObjectARB(int program) {
        currentProgram = program;
        ARBShaderObjects.glUseProgramObjectARB(program);
    }

    public static int glGenFramebuffers() {
        int fboId = GL30C.glGenFramebuffers();
        registerForeignRenderTarget(fboId);
        return fboId;
    }

    public static void glGenFramebuffers(int[] framebuffers) {
        GL30C.glGenFramebuffers(framebuffers);
        for (int framebuffer : framebuffers) {
            registerForeignRenderTarget(framebuffer);
        }
    }

    public static void glGenFramebuffers(IntBuffer buffer) {
        GL30C.glGenFramebuffers(buffer);
        for (int i = 0; i < buffer.remaining(); i++) {
            registerForeignRenderTarget(buffer.get(i));
        }
        buffer.rewind();
    }

    public static int glGenFramebuffersEXT() {
        int fboId = EXTFramebufferObject.glGenFramebuffersEXT();
        registerForeignRenderTarget(fboId);
        return fboId;
    }

    public static void glGenFramebuffersEXT(int[] framebuffers) {
        EXTFramebufferObject.glGenFramebuffersEXT(framebuffers);
        for (int framebuffer : framebuffers) {
            registerForeignRenderTarget(framebuffer);
        }
    }

    public static void glGenFramebuffersEXT(IntBuffer buffer) {
        EXTFramebufferObject.glGenFramebuffersEXT(buffer);
        for (int i = 0; i < buffer.remaining(); i++) {
            registerForeignRenderTarget(buffer.get(i));
        }
        buffer.rewind();
    }

    public static int glCreateFramebuffers() {
        int fboId = GL45C.glCreateFramebuffers();
        registerForeignRenderTarget(fboId);
        return fboId;
    }

    public static void glCreateFramebuffers(int[] framebuffers) {
        GL45C.glCreateFramebuffers(framebuffers);
        for (int framebuffer : framebuffers) {
            registerForeignRenderTarget(framebuffer);
        }
    }

    public static void glCreateFramebuffers(IntBuffer buffer) {
        GL45C.glCreateFramebuffers(buffer);
        for (int i = 0; i < buffer.remaining(); i++) {
            registerForeignRenderTarget(buffer.get(i));
        }
        buffer.rewind();
    }

    public static void glBindFramebuffer(int target, int framebuffer) {
        updateFramebuffer(target, framebuffer);
        GL30C.glBindFramebuffer(target, framebuffer);
    }

    public static void glBindFramebufferEXT(int target, int framebuffer) {
        updateFramebuffer(target, framebuffer);
        EXTFramebufferObject.glBindFramebufferEXT(target, framebuffer);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: net.labymod.laby3d.api.RenderDeviceException */
    public static void glFramebufferTexture1D(int target, int attachment, int textarget, int texture, int level) throws RenderDeviceException {
        GL30C.glFramebufferTexture1D(target, attachment, textarget, texture, level);
        setFramebufferTexture(target, attachment, textarget, texture, level, 0);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: net.labymod.laby3d.api.RenderDeviceException */
    public static void glFramebufferTexture2D(int target, int attachment, int textarget, int texture, int level) throws RenderDeviceException {
        GL30C.glFramebufferTexture2D(target, attachment, textarget, texture, level);
        setFramebufferTexture(target, attachment, textarget, texture, level, 0);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: net.labymod.laby3d.api.RenderDeviceException */
    public static void glFramebufferTexture3D(int target, int attachment, int textarget, int texture, int level, int layer) throws RenderDeviceException {
        GL30C.glFramebufferTexture3D(target, attachment, textarget, texture, level, layer);
        setFramebufferTexture(target, attachment, textarget, texture, level, layer);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: net.labymod.laby3d.api.RenderDeviceException */
    private static void setFramebufferTexture(int target, int attachment, int textarget, int texture, int level, int layer) throws RenderDeviceException {
        ForeignRenderTarget foreignRenderTarget;
        ForeignRenderTargetRegistry registry = foreignRenderTargetRegistry();
        switch (target) {
            case GlConst.GL_READ_FRAMEBUFFER /* 36008 */:
                foreignRenderTarget = registry.get(Integer.valueOf(FRAMEBUFFER.getReadFramebuffer()));
                break;
            case GlConst.GL_DRAW_FRAMEBUFFER /* 36009 */:
                foreignRenderTarget = registry.get(Integer.valueOf(FRAMEBUFFER.getDrawFramebuffer()));
                break;
            case GlConst.GL_FRAMEBUFFER /* 36160 */:
                foreignRenderTarget = registry.get(Integer.valueOf(FRAMEBUFFER.getDrawFramebuffer()));
                break;
            default:
                throw new RenderDeviceException("Unknown framebuffer target: " + target);
        }
        GlForeignRenderTarget renderTarget = (GlForeignRenderTarget) foreignRenderTarget;
        renderTarget.setAttachment(attachment, textarget, texture, level, layer);
    }

    public static void glClear(int mask) {
        GL11C.glClear(mask);
        FBO_WRITE_TRACKER.onClear(mask);
    }

    public static void glDeleteFramebuffers(int framebuffer) {
        GL30C.glDeleteFramebuffers(framebuffer);
        FBO_WRITE_TRACKER.onDeleteFramebuffer(framebuffer);
        unregisterForeignRenderTarget(framebuffer);
    }

    public static void glDeleteFramebuffers(int[] framebuffers) {
        GL30C.glDeleteFramebuffers(framebuffers);
        for (int framebuffer : framebuffers) {
            FBO_WRITE_TRACKER.onDeleteFramebuffer(framebuffer);
            unregisterForeignRenderTarget(framebuffer);
        }
    }

    public static void glDeleteFramebuffers(IntBuffer buffer) {
        GL30C.glDeleteFramebuffers(buffer);
        for (int i = 0; i < buffer.remaining(); i++) {
            FBO_WRITE_TRACKER.onDeleteFramebuffer(buffer.get(i));
            unregisterForeignRenderTarget(buffer.get(i));
        }
        buffer.rewind();
    }

    public static void glDeleteFramebuffersEXT(int framebuffer) {
        EXTFramebufferObject.glDeleteFramebuffersEXT(framebuffer);
        FBO_WRITE_TRACKER.onDeleteFramebuffer(framebuffer);
        unregisterForeignRenderTarget(framebuffer);
    }

    public static void glDeleteFramebuffersEXT(int[] framebuffers) {
        EXTFramebufferObject.glDeleteFramebuffersEXT(framebuffers);
        for (int framebuffer : framebuffers) {
            FBO_WRITE_TRACKER.onDeleteFramebuffer(framebuffer);
            unregisterForeignRenderTarget(framebuffer);
        }
    }

    public static void glDeleteFramebuffersEXT(IntBuffer buffer) {
        EXTFramebufferObject.glDeleteFramebuffersEXT(buffer);
        for (int i = 0; i < buffer.remaining(); i++) {
            FBO_WRITE_TRACKER.onDeleteFramebuffer(buffer.get(i));
            unregisterForeignRenderTarget(buffer.get(i));
        }
        buffer.rewind();
    }

    public static void glDrawArrays(int mode, int first, int count) {
        GL11C.glDrawArrays(mode, first, count);
        trackDrawCall();
    }

    public static void glDrawElements(int mode, int count, int type, long indices) {
        GL11C.glDrawElements(mode, count, type, indices);
        trackDrawCall();
    }

    public static void glDrawArraysInstanced(int mode, int first, int count, int instanceCount) {
        GL31C.glDrawArraysInstanced(mode, first, count, instanceCount);
        trackDrawCall();
    }

    public static void glCallList(int list) {
        GL11.glCallList(list);
        trackDrawCall();
    }

    public static void glCallLists(ByteBuffer buffer) {
        GL11.glCallLists(buffer);
        trackDrawCall();
    }

    public static void glCallLists(ShortBuffer buffer) {
        GL11.glCallLists(buffer);
        trackDrawCall();
    }

    public static void glCallLists(IntBuffer buffer) {
        GL11.glCallLists(buffer);
        trackDrawCall();
    }

    public static void glCallLists(int type, ByteBuffer buffer) {
        GL11.glCallLists(type, buffer);
        trackDrawCall();
    }

    public static void glDrawElementsInstanced(int mode, int count, int type, long indices, int instanceCount) {
        GL31C.glDrawElementsInstanced(mode, count, type, indices, instanceCount);
        trackDrawCall();
    }

    public static void glDrawElementsBaseVertex(int mode, int count, int type, long indices, int basevertex) {
        GL32C.glDrawElementsBaseVertex(mode, count, type, indices, basevertex);
        trackDrawCall();
    }

    public static void glDrawElementsInstancedBaseVertex(int mode, int count, int type, long indices, int instanceCount, int basevertex) {
        GL32C.glDrawElementsInstancedBaseVertex(mode, count, type, indices, instanceCount, basevertex);
        trackDrawCall();
    }

    public static void glEnd() {
        GL11.glEnd();
        trackDrawCall();
    }

    private static void trackDrawCall() {
        RenderProfiler.increaseRenderCall();
        FBO_WRITE_TRACKER.onDrawCall();
    }

    public static void glShadeModel(int mode) {
        shadeMode = mode;
        GL11.glShadeModel(mode);
    }

    public static void glViewport(int x, int y, int width, int height) {
        VIEWPORT.setX(x);
        VIEWPORT.setY(y);
        VIEWPORT.setWidth(width);
        VIEWPORT.setHeight(height);
        GL11C.glViewport(x, y, width, height);
    }

    public static void glBindVertexArray(int array) {
        currentVao = array;
        GL30C.glBindVertexArray(array);
    }

    private static void updateFramebuffer(int target, int framebuffer) {
        GlForeignRenderTargetRegistry registry = (GlForeignRenderTargetRegistry) foreignRenderTargetRegistry();
        if (target == 36160 || target == 36009) {
            FRAMEBUFFER.setDrawFramebuffer(framebuffer);
            registry.setDrawFbo(framebuffer);
        }
        if (target == 36160 || target == 36008) {
            FRAMEBUFFER.setReadFramebuffer(framebuffer);
            registry.setReadFbo(framebuffer);
        }
        FBO_WRITE_TRACKER.onBindFramebuffer(target, framebuffer);
    }

    private static void registerForeignRenderTarget(int framebuffer) {
        foreignRenderTargetRegistry().register(new GlForeignRenderTarget(framebuffer));
    }

    private static void unregisterForeignRenderTarget(int framebuffer) {
        ForeignRenderTargetRegistry registry = foreignRenderTargetRegistry();
        ForeignRenderTarget foreignRenderTarget = registry.get(Integer.valueOf(framebuffer));
        if (foreignRenderTarget == null) {
            return;
        }
        registry.unregister(foreignRenderTarget);
    }

    private static void registerForeignDeviceTexture(int textureId) {
        foreignDeviceTextureRegistry().register(new GlForeignDeviceTexture(textureId));
    }

    private static void unregisterForeignDeviceTexture(int textureId) {
        ForeignDeviceTextureRegistry registry = foreignDeviceTextureRegistry();
        ForeignDeviceTexture foreignDeviceTexture = registry.get(Integer.valueOf(textureId));
        if (foreignDeviceTexture == null) {
            return;
        }
        registry.unregister(foreignDeviceTexture);
    }

    private static ForeignRenderTargetRegistry foreignRenderTargetRegistry() {
        return Laby.references().laby3D().foreignRenderTargetRegistry();
    }

    private static ForeignDeviceTextureRegistry foreignDeviceTextureRegistry() {
        return Laby.references().laby3D().foreignDeviceTextureRegistry();
    }
}
