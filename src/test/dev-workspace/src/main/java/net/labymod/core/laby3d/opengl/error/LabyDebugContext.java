package net.labymod.core.laby3d.opengl.error;

import net.labymod.api.util.logging.Logging;
import org.lwjgl.opengl.GL11;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/LabyDebugContext.class */
public final class LabyDebugContext {
    private static final Logging LOGGER = Logging.getLogger();

    public static void glError(String methodName, Object... args) {
        int error = GL11.glGetError();
        if (error == 0) {
            return;
        }
        LOGGER.error("[OpenGL Error] [{}] {} {}", getReadableErrorCode(error), methodName, buildParameters(args));
    }

    private static String getReadableErrorCode(int error) {
        if (error == 1280) {
            return "Invalid Enum";
        }
        if (error == 1281) {
            return "Invalid Value";
        }
        if (error == 1282) {
            return "Invalid Operation";
        }
        if (error == 1283) {
            return "Stack Overflow";
        }
        if (error == 1284) {
            return "Stack Underflow";
        }
        if (error == 1285) {
            return "Out Of Memory";
        }
        return "0x" + Integer.toHexString(error);
    }

    private static String buildParameters(Object[] args) {
        if (args == null || args.length == 0) {
            return "";
        }
        StringBuilder bobTheBuilder = new StringBuilder();
        bobTheBuilder.append("Parameters: ");
        for (int index = 0; index < args.length; index += 2) {
            bobTheBuilder.append(args[index]).append(" = ").append(args[index + 1]);
            if (index < args.length - 2) {
                bobTheBuilder.append(", ");
            }
        }
        return bobTheBuilder.toString();
    }
}
