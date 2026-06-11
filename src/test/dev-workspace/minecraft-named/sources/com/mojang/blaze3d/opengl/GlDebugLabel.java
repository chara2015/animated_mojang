package com.mojang.blaze3d.opengl;

import com.mojang.blaze3d.opengl.VertexArrayCache;
import com.mojang.logging.LogUtils;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.util.StringUtil;
import org.lwjgl.opengl.EXTDebugLabel;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.opengl.KHRDebug;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/opengl/GlDebugLabel.class */
public abstract class GlDebugLabel {
    private static final Logger LOGGER = LogUtils.getLogger();

    public void applyLabel(GlBuffer $$0) {
    }

    public void applyLabel(GlTexture $$0) {
    }

    public void applyLabel(GlShaderModule $$0) {
    }

    public void applyLabel(GlProgram $$0) {
    }

    public void applyLabel(VertexArrayCache.VertexArray $$0) {
    }

    public void pushDebugGroup(Supplier<String> $$0) {
    }

    public void popDebugGroup() {
    }

    public static GlDebugLabel create(GLCapabilities $$0, boolean $$1, Set<String> $$2) {
        if ($$1) {
            if ($$0.GL_KHR_debug && GlDevice.USE_GL_KHR_debug) {
                $$2.add("GL_KHR_debug");
                return new Core();
            }
            if ($$0.GL_EXT_debug_label && GlDevice.USE_GL_EXT_debug_label) {
                $$2.add("GL_EXT_debug_label");
                return new Ext();
            }
            LOGGER.warn("Debug labels unavailable: neither KHR_debug nor EXT_debug_label are supported");
        }
        return new Empty();
    }

    public boolean exists() {
        return false;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/opengl/GlDebugLabel$Empty.class */
    static class Empty extends GlDebugLabel {
        Empty() {
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/opengl/GlDebugLabel$Core.class */
    static class Core extends GlDebugLabel {
        private final int maxLabelLength = GL11.glGetInteger(33512);

        Core() {
        }

        @Override // com.mojang.blaze3d.opengl.GlDebugLabel
        public void applyLabel(GlBuffer $$0) {
            Supplier<String> $$1 = $$0.label;
            if ($$1 != null) {
                KHRDebug.glObjectLabel(33504, $$0.handle, StringUtil.truncateStringIfNecessary($$1.get(), this.maxLabelLength, true));
            }
        }

        @Override // com.mojang.blaze3d.opengl.GlDebugLabel
        public void applyLabel(GlTexture $$0) {
            KHRDebug.glObjectLabel(5890, $$0.id, StringUtil.truncateStringIfNecessary($$0.getLabel(), this.maxLabelLength, true));
        }

        @Override // com.mojang.blaze3d.opengl.GlDebugLabel
        public void applyLabel(GlShaderModule $$0) {
            KHRDebug.glObjectLabel(33505, $$0.getShaderId(), StringUtil.truncateStringIfNecessary($$0.getDebugLabel(), this.maxLabelLength, true));
        }

        @Override // com.mojang.blaze3d.opengl.GlDebugLabel
        public void applyLabel(GlProgram $$0) {
            KHRDebug.glObjectLabel(33506, $$0.getProgramId(), StringUtil.truncateStringIfNecessary($$0.getDebugLabel(), this.maxLabelLength, true));
        }

        @Override // com.mojang.blaze3d.opengl.GlDebugLabel
        public void applyLabel(VertexArrayCache.VertexArray $$0) {
            KHRDebug.glObjectLabel(32884, $$0.id, StringUtil.truncateStringIfNecessary($$0.format.toString(), this.maxLabelLength, true));
        }

        @Override // com.mojang.blaze3d.opengl.GlDebugLabel
        public void pushDebugGroup(Supplier<String> $$0) {
            KHRDebug.glPushDebugGroup(33354, 0, $$0.get());
        }

        @Override // com.mojang.blaze3d.opengl.GlDebugLabel
        public void popDebugGroup() {
            KHRDebug.glPopDebugGroup();
        }

        @Override // com.mojang.blaze3d.opengl.GlDebugLabel
        public boolean exists() {
            return true;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/opengl/GlDebugLabel$Ext.class */
    static class Ext extends GlDebugLabel {
        Ext() {
        }

        @Override // com.mojang.blaze3d.opengl.GlDebugLabel
        public void applyLabel(GlBuffer $$0) {
            Supplier<String> $$1 = $$0.label;
            if ($$1 != null) {
                EXTDebugLabel.glLabelObjectEXT(37201, $$0.handle, StringUtil.truncateStringIfNecessary($$1.get(), 256, true));
            }
        }

        @Override // com.mojang.blaze3d.opengl.GlDebugLabel
        public void applyLabel(GlTexture $$0) {
            EXTDebugLabel.glLabelObjectEXT(5890, $$0.id, StringUtil.truncateStringIfNecessary($$0.getLabel(), 256, true));
        }

        @Override // com.mojang.blaze3d.opengl.GlDebugLabel
        public void applyLabel(GlShaderModule $$0) {
            EXTDebugLabel.glLabelObjectEXT(35656, $$0.getShaderId(), StringUtil.truncateStringIfNecessary($$0.getDebugLabel(), 256, true));
        }

        @Override // com.mojang.blaze3d.opengl.GlDebugLabel
        public void applyLabel(GlProgram $$0) {
            EXTDebugLabel.glLabelObjectEXT(35648, $$0.getProgramId(), StringUtil.truncateStringIfNecessary($$0.getDebugLabel(), 256, true));
        }

        @Override // com.mojang.blaze3d.opengl.GlDebugLabel
        public void applyLabel(VertexArrayCache.VertexArray $$0) {
            EXTDebugLabel.glLabelObjectEXT(32884, $$0.id, StringUtil.truncateStringIfNecessary($$0.format.toString(), 256, true));
        }

        @Override // com.mojang.blaze3d.opengl.GlDebugLabel
        public boolean exists() {
            return true;
        }
    }
}
