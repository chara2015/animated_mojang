package com.mojang.blaze3d.opengl;

import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.lwjgl.opengl.ARBVertexAttribBinding;
import org.lwjgl.opengl.GLCapabilities;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/opengl/VertexArrayCache.class */
public abstract class VertexArrayCache {
    public abstract void bindVertexArray(VertexFormat vertexFormat, GlBuffer glBuffer);

    public static VertexArrayCache create(GLCapabilities $$0, GlDebugLabel $$1, Set<String> $$2) {
        if ($$0.GL_ARB_vertex_attrib_binding && GlDevice.USE_GL_ARB_vertex_attrib_binding) {
            $$2.add("GL_ARB_vertex_attrib_binding");
            return new Separate($$1);
        }
        return new Emulated($$1);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/opengl/VertexArrayCache$Emulated.class */
    static class Emulated extends VertexArrayCache {
        private final Map<VertexFormat, VertexArray> cache = new HashMap();
        private final GlDebugLabel debugLabels;

        public Emulated(GlDebugLabel $$0) {
            this.debugLabels = $$0;
        }

        @Override // com.mojang.blaze3d.opengl.VertexArrayCache
        public void bindVertexArray(VertexFormat $$0, GlBuffer $$1) {
            VertexArray $$2 = this.cache.get($$0);
            if ($$2 == null) {
                int $$3 = GlStateManager._glGenVertexArrays();
                GlStateManager._glBindVertexArray($$3);
                if ($$1 != null) {
                    GlStateManager._glBindBuffer(GlConst.GL_ARRAY_BUFFER, $$1.handle);
                    setupCombinedAttributes($$0, true);
                }
                VertexArray $$4 = new VertexArray($$3, $$0, $$1);
                this.debugLabels.applyLabel($$4);
                this.cache.put($$0, $$4);
                return;
            }
            GlStateManager._glBindVertexArray($$2.id);
            if ($$1 != null && $$2.lastVertexBuffer != $$1) {
                GlStateManager._glBindBuffer(GlConst.GL_ARRAY_BUFFER, $$1.handle);
                $$2.lastVertexBuffer = $$1;
                setupCombinedAttributes($$0, false);
            }
        }

        private static void setupCombinedAttributes(VertexFormat $$0, boolean $$1) {
            int $$2 = $$0.getVertexSize();
            List<VertexFormatElement> $$3 = $$0.getElements();
            for (int $$4 = 0; $$4 < $$3.size(); $$4++) {
                VertexFormatElement $$5 = $$3.get($$4);
                if ($$1) {
                    GlStateManager._enableVertexAttribArray($$4);
                }
                switch ($$5.usage()) {
                    case POSITION:
                    case GENERIC:
                    case UV:
                        if ($$5.type() == VertexFormatElement.Type.FLOAT) {
                            GlStateManager._vertexAttribPointer($$4, $$5.count(), GlConst.toGl($$5.type()), false, $$2, $$0.getOffset($$5));
                        } else {
                            GlStateManager._vertexAttribIPointer($$4, $$5.count(), GlConst.toGl($$5.type()), $$2, $$0.getOffset($$5));
                        }
                        break;
                    case NORMAL:
                    case COLOR:
                        GlStateManager._vertexAttribPointer($$4, $$5.count(), GlConst.toGl($$5.type()), true, $$2, $$0.getOffset($$5));
                        break;
                }
            }
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/opengl/VertexArrayCache$Separate.class */
    static class Separate extends VertexArrayCache {
        private final Map<VertexFormat, VertexArray> cache = new HashMap();
        private final GlDebugLabel debugLabels;
        private final boolean needsMesaWorkaround;

        public Separate(GlDebugLabel $$0) {
            this.debugLabels = $$0;
            if ("Mesa".equals(GlStateManager._getString(7936))) {
                String $$1 = GlStateManager._getString(7938);
                this.needsMesaWorkaround = $$1.contains("25.0.0") || $$1.contains("25.0.1") || $$1.contains("25.0.2");
            } else {
                this.needsMesaWorkaround = false;
            }
        }

        @Override // com.mojang.blaze3d.opengl.VertexArrayCache
        public void bindVertexArray(VertexFormat $$0, GlBuffer $$1) {
            VertexArray $$2 = this.cache.get($$0);
            if ($$2 == null) {
                int $$3 = GlStateManager._glGenVertexArrays();
                GlStateManager._glBindVertexArray($$3);
                if ($$1 != null) {
                    List<VertexFormatElement> $$4 = $$0.getElements();
                    for (int $$5 = 0; $$5 < $$4.size(); $$5++) {
                        VertexFormatElement $$6 = $$4.get($$5);
                        GlStateManager._enableVertexAttribArray($$5);
                        switch ($$6.usage()) {
                            case POSITION:
                            case GENERIC:
                            case UV:
                                if ($$6.type() == VertexFormatElement.Type.FLOAT) {
                                    ARBVertexAttribBinding.glVertexAttribFormat($$5, $$6.count(), GlConst.toGl($$6.type()), false, $$0.getOffset($$6));
                                } else {
                                    ARBVertexAttribBinding.glVertexAttribIFormat($$5, $$6.count(), GlConst.toGl($$6.type()), $$0.getOffset($$6));
                                }
                                break;
                            case NORMAL:
                            case COLOR:
                                ARBVertexAttribBinding.glVertexAttribFormat($$5, $$6.count(), GlConst.toGl($$6.type()), true, $$0.getOffset($$6));
                                break;
                        }
                        ARBVertexAttribBinding.glVertexAttribBinding($$5, 0);
                    }
                }
                if ($$1 != null) {
                    ARBVertexAttribBinding.glBindVertexBuffer(0, $$1.handle, 0L, $$0.getVertexSize());
                }
                VertexArray $$7 = new VertexArray($$3, $$0, $$1);
                this.debugLabels.applyLabel($$7);
                this.cache.put($$0, $$7);
                return;
            }
            GlStateManager._glBindVertexArray($$2.id);
            if ($$1 != null && $$2.lastVertexBuffer != $$1) {
                if (this.needsMesaWorkaround && $$2.lastVertexBuffer != null && $$2.lastVertexBuffer.handle == $$1.handle) {
                    ARBVertexAttribBinding.glBindVertexBuffer(0, 0, 0L, 0);
                }
                ARBVertexAttribBinding.glBindVertexBuffer(0, $$1.handle, 0L, $$0.getVertexSize());
                $$2.lastVertexBuffer = $$1;
            }
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/opengl/VertexArrayCache$VertexArray.class */
    public static class VertexArray {
        final int id;
        final VertexFormat format;
        GlBuffer lastVertexBuffer;

        VertexArray(int $$0, VertexFormat $$1, GlBuffer $$2) {
            this.id = $$0;
            this.format = $$1;
            this.lastVertexBuffer = $$2;
        }
    }
}
