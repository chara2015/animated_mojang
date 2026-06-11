package net.labymod.core.client.gfx;

import java.lang.StackWalker;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.client.gfx.GlConst;
import net.labymod.api.client.gfx.pipeline.Blaze3DGlStatePipeline;
import net.labymod.api.util.logging.Logging;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gfx/AbstractGFXBridge.class */
public abstract class AbstractGFXBridge implements GFXBridge {
    private static final Logging LOGGER = Logging.getLogger();
    private static final StackWalker WALKER = StackWalker.getInstance();
    protected final Blaze3DGlStatePipeline blaze3DGlStatePipeline;

    public abstract void onError(Object... objArr);

    public AbstractGFXBridge(Blaze3DGlStatePipeline blaze3DGlStatePipeline) {
        this.blaze3DGlStatePipeline = blaze3DGlStatePipeline;
    }

    @Override // net.labymod.api.client.gfx.GFXBridge
    public void storeBlaze3DStates() {
        Laby.references().laby3D().storeStates();
    }

    @Override // net.labymod.api.client.gfx.GFXBridge
    public void restoreBlaze3DStates() {
        Laby.references().laby3D().restoreStates();
    }

    @Override // net.labymod.api.client.gfx.GFXBridge
    public Blaze3DGlStatePipeline blaze3DGlStatePipeline() {
        return this.blaze3DGlStatePipeline;
    }

    protected void printError(int errorCode, Object... args) {
        String str;
        StringBuilder content = new StringBuilder();
        int length = args.length;
        for (int i = 0; i < length; i += 2) {
            content.append("{}={}");
            if (i + 1 != length - 1) {
                content.append(", ");
            }
        }
        StackWalker.StackFrame frame = (StackWalker.StackFrame) WALKER.walk(stream -> {
            return (StackWalker.StackFrame) stream.skip(4L).findFirst().orElse(null);
        });
        if (frame == null) {
            LOGGER.error("That's strange, no StackFrame could be found. OpenGL Error code: 0x{}", Integer.toHexString(errorCode));
            return;
        }
        String callerMethod = frame.getMethodName();
        switch (errorCode) {
            case 1280:
                str = "Invalid Enum";
                break;
            case GlConst.GL_INVALID_VALUE /* 1281 */:
                str = "Invalid Value";
                break;
            case GlConst.GL_INVALID_OPERATION /* 1282 */:
                str = "Invalid Operation";
                break;
            case GlConst.GL_STACK_OVERFLOW /* 1283 */:
                str = "Stack Overflow";
                break;
            case GlConst.GL_STACK_UNDERFLOW /* 1284 */:
                str = "Stack Underflow";
                break;
            case GlConst.GL_OUT_OF_MEMORY /* 1285 */:
                str = "Out of Memory";
                break;
            default:
                str = "0x" + Integer.toHexString(errorCode);
                break;
        }
        String errorCodeMessage = str;
        LOGGER.error("[Error Code: " + errorCodeMessage + "] " + callerMethod + "(" + String.valueOf(content) + ")", args);
    }
}
