package net.labymod.gfx_lwjgl3.client.gfx.pipeline.imgui;

import imgui.ImDrawData;
import imgui.ImFontAtlas;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.ImGuiViewport;
import imgui.ImVec2;
import imgui.ImVec4;
import imgui.callback.ImPlatformFuncViewport;
import imgui.type.ImInt;
import java.nio.ByteBuffer;
import net.labymod.api.client.gfx.GlConst;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/gfx_lwjgl3/client/gfx/pipeline/imgui/LabyImGuiImplGl2.class */
public class LabyImGuiImplGl2 {
    private static final int POSITION_OFFSET = 0;
    private static final int TEXTURE_OFFSET = 8;
    private static final int COLOR_OFFSET = 16;
    private final int[] lastTexture = new int[1];
    private final int[] lastPolygonMode = new int[2];
    private final int[] lastViewport = new int[4];
    private final int[] lastScissorBox = new int[4];
    private final int[] lastShadeModel = new int[1];
    private final int[] lastTexEnvMode = new int[1];
    private int gFontTexture;

    public void init() {
        ImGuiIO io = ImGui.getIO();
        io.setBackendRendererName("imgui_java_impl_opengl2");
        io.addBackendFlags(4096);
        updateFontsTexture();
        if (io.hasConfigFlags(1024)) {
            initPlatformInterface();
        }
    }

    public void renderDrawData(ImDrawData drawData) {
        int commandListsCount = drawData.getCmdListsCount();
        if (commandListsCount <= 0) {
            return;
        }
        int framebufferWidth = (int) (drawData.getDisplaySize().x * drawData.getFramebufferScale().x);
        int framebufferHeight = (int) (drawData.getDisplaySize().y * drawData.getFramebufferScale().y);
        if (framebufferWidth == 0 || framebufferHeight == 0) {
            return;
        }
        GL11.glGetIntegerv(GlConst.GL_TEXTURE_BINDING_2D, this.lastTexture);
        GL11.glGetIntegerv(GlConst.GL_POLYGON_MODE, this.lastPolygonMode);
        GL11.glGetIntegerv(GlConst.GL_VIEWPORT, this.lastViewport);
        GL11.glGetIntegerv(GlConst.GL_SCISSOR_BOX, this.lastScissorBox);
        GL11.glGetIntegerv(GlConst.GL_SHADE_MODEL, this.lastShadeModel);
        GL11.glGetTexEnviv(GlConst.GL_TEXTURE_ENV, GlConst.GL_TEXTURE_ENV_MODE, this.lastTexEnvMode);
        GL11.glPushAttrib(28672);
        setupRenderState(drawData, framebufferWidth, framebufferHeight);
        ImVec2 clipOff = drawData.getDisplayPos();
        ImVec2 clipScale = drawData.getFramebufferScale();
        float clipOffX = clipOff.x;
        float clipOffY = clipOff.y;
        float clipScaleX = clipScale.x;
        float clipScaleY = clipScale.y;
        for (int commandListIndex = 0; commandListIndex < commandListsCount; commandListIndex++) {
            ByteBuffer indexBuffer = drawData.getCmdListIdxBufferData(commandListIndex);
            ByteBuffer indexBuffer2 = ByteBuffer.allocateDirect(indexBuffer.remaining()).put(indexBuffer);
            ByteBuffer vertexBuffer = drawData.getCmdListVtxBufferData(commandListIndex);
            int indexSize = ImDrawData.sizeOfImDrawIdx();
            int stride = ImDrawData.sizeOfImDrawVert();
            vertexBuffer.position(0);
            GL11.glVertexPointer(2, GlConst.GL_FLOAT, stride, vertexBuffer);
            vertexBuffer.position(8);
            GL11.glTexCoordPointer(2, GlConst.GL_FLOAT, stride, vertexBuffer);
            vertexBuffer.position(16);
            GL11.glColorPointer(4, GlConst.GL_UNSIGNED_BYTE, stride, vertexBuffer);
            int commandBufferSize = drawData.getCmdListCmdBufferSize(commandListIndex);
            for (int commandBufferIndex = 0; commandBufferIndex < commandBufferSize; commandBufferIndex++) {
                ImVec4 clipRect = new ImVec4();
                drawData.getCmdListCmdBufferClipRect(commandListIndex, commandBufferIndex, clipRect);
                float clipMinX = (clipRect.x - clipOffX) * clipScaleX;
                float clipMinY = (clipRect.y - clipOffY) * clipScaleY;
                float clipMaxX = (clipRect.z - clipOffX) * clipScaleX;
                float clipMaxY = (clipRect.w - clipOffY) * clipScaleY;
                if (clipMaxX > clipMinX && clipMaxY > clipMinY) {
                    GL11.glScissor((int) clipMinX, (int) (framebufferHeight - clipMaxY), (int) (clipMaxX - clipMinX), (int) (clipMaxY - clipMinY));
                    int textureId = drawData.getCmdListCmdBufferTextureId(commandListIndex, commandBufferIndex);
                    int elemCount = drawData.getCmdListCmdBufferElemCount(commandListIndex, commandBufferIndex);
                    int indexOffset = drawData.getCmdListCmdBufferIdxOffset(commandListIndex, commandBufferIndex);
                    int type = indexSize == 2 ? GlConst.GL_UNSIGNED_SHORT : GlConst.GL_UNSIGNED_INT;
                    GL11.glBindTexture(GlConst.GL_TEXTURE_2D, textureId);
                    indexBuffer2.position(indexOffset * indexSize);
                    GL11.glDrawElements(4, elemCount, type, MemoryUtil.memAddress(indexBuffer2));
                }
            }
        }
        GL11.glDisableClientState(GlConst.GL_COLOR_ARRAY);
        GL11.glDisableClientState(GlConst.GL_TEXTURE_COORD_ARRAY);
        GL11.glDisableClientState(GlConst.GL_VERTEX_ARRAY);
        GL11.glBindTexture(GlConst.GL_TEXTURE_2D, this.lastTexture[0]);
        GL11.glMatrixMode(GlConst.GL_MODELVIEW);
        GL11.glPopMatrix();
        GL11.glMatrixMode(GlConst.GL_PROJECTION);
        GL11.glPopMatrix();
        GL11.glPopAttrib();
        GL11.glPolygonMode(GlConst.GL_FRONT, this.lastPolygonMode[0]);
        GL11.glPolygonMode(GlConst.GL_BACK, this.lastPolygonMode[1]);
        GL11.glViewport(this.lastViewport[0], this.lastViewport[1], this.lastViewport[2], this.lastViewport[3]);
        GL11.glScissor(this.lastScissorBox[0], this.lastScissorBox[1], this.lastScissorBox[2], this.lastScissorBox[3]);
        GL11.glShadeModel(this.lastShadeModel[0]);
        GL11.glTexEnvi(GlConst.GL_TEXTURE_ENV, GlConst.GL_TEXTURE_ENV_MODE, this.lastTexEnvMode[0]);
    }

    private void setupRenderState(ImDrawData drawData, int framebufferWidth, int framebufferHeight) {
        GL11.glEnable(GlConst.GL_BLEND);
        GL11.glBlendFunc(GlConst.GL_SRC_ALPHA, GlConst.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GlConst.GL_CULL_FACE);
        GL11.glDisable(GlConst.GL_DEPTH_TEST);
        GL11.glDisable(GlConst.GL_STENCIL_TEST);
        GL11.glDisable(GlConst.GL_LIGHTING);
        GL11.glDisable(GlConst.GL_COLOR_MATERIAL);
        GL11.glEnable(GlConst.GL_SCISSOR_TEST);
        GL11.glEnableClientState(GlConst.GL_VERTEX_ARRAY);
        GL11.glEnableClientState(GlConst.GL_TEXTURE_COORD_ARRAY);
        GL11.glEnableClientState(GlConst.GL_COLOR_ARRAY);
        GL11.glDisableClientState(GlConst.GL_NORMAL_ARRAY);
        GL11.glEnable(GlConst.GL_TEXTURE_2D);
        GL11.glPolygonMode(GlConst.GL_FRONT_AND_BACK, GlConst.GL_FILL);
        GL11.glShadeModel(GlConst.GL_SMOOTH);
        GL11.glTexEnvi(GlConst.GL_TEXTURE_ENV, GlConst.GL_TEXTURE_ENV_MODE, GlConst.GL_MODULATE);
        GL11.glViewport(0, 0, framebufferWidth, framebufferHeight);
        GL11.glMatrixMode(GlConst.GL_PROJECTION);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        float left = drawData.getDisplayPosX();
        float right = left + drawData.getDisplaySizeX();
        float top = drawData.getDisplayPosY();
        float bottom = top + drawData.getDisplaySizeY();
        GL11.glOrtho(left, right, bottom, top, -1.0d, 1.0d);
        GL11.glMatrixMode(GlConst.GL_MODELVIEW);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
    }

    private void updateFontsTexture() {
        GL11.glDeleteTextures(this.gFontTexture);
        ImFontAtlas fontAtlas = ImGui.getIO().getFonts();
        ImInt width = new ImInt();
        ImInt height = new ImInt();
        ByteBuffer buffer = fontAtlas.getTexDataAsRGBA32(width, height);
        this.gFontTexture = GL11.glGenTextures();
        GL11.glBindTexture(GlConst.GL_TEXTURE_2D, this.gFontTexture);
        GL11.glTexParameteri(GlConst.GL_TEXTURE_2D, GlConst.GL_TEXTURE_MIN_FILTER, GlConst.GL_LINEAR);
        GL11.glTexParameteri(GlConst.GL_TEXTURE_2D, GlConst.GL_TEXTURE_MAG_FILTER, GlConst.GL_LINEAR);
        GL11.glPixelStorei(GlConst.GL_UNPACK_ROW_LENGTH, 0);
        GL11.glTexImage2D(GlConst.GL_TEXTURE_2D, 0, GlConst.GL_RGBA, width.get(), height.get(), 0, GlConst.GL_RGBA, GlConst.GL_UNSIGNED_BYTE, buffer);
        fontAtlas.setTexID(this.gFontTexture);
    }

    private void initPlatformInterface() {
        ImGui.getPlatformIO().setRendererRenderWindow(new ImPlatformFuncViewport() { // from class: net.labymod.gfx_lwjgl3.client.gfx.pipeline.imgui.LabyImGuiImplGl2.1
            public void accept(ImGuiViewport vp) {
                if (!vp.hasFlags(256)) {
                    GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                    GL11.glClear(16384);
                }
                LabyImGuiImplGl2.this.renderDrawData(vp.getDrawData());
            }
        });
    }
}
