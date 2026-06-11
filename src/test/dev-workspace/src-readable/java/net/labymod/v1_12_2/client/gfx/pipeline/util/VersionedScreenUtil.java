package net.labymod.v1_12_2.client.gfx.pipeline.util;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.util.ScreenUtil;
import net.labymod.api.client.gui.screen.ScreenCustomFontStack;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.window.Window;
import net.labymod.v1_12_2.client.VersionedMinecraft;
import net.labymod.v1_12_2.client.render.matrix.VersionedStackProvider;
import net.labymod.v1_12_2.client.util.MinecraftUtil;
import org.lwjgl.input.Mouse;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/gfx/pipeline/util/VersionedScreenUtil.class */
public final class VersionedScreenUtil {
    private static int lastMouseX = -1;
    private static int lastMouseY = -1;

    public static void drawScreen(Operation<Void> original, blk screen, int mouseX, int mouseY, float tickDelta) {
        handleMouseDragged();
        ScreenCustomFontStack screenCustomFontStack = Laby.references().screenCustomFontStack();
        screenCustomFontStack.push(screen);
        MinecraftUtil.obtainScreenContextFromStack(VersionedStackProvider.DEFAULT_STACK, mouseX, mouseY, tickDelta, screenContext -> {
            ScreenUtil.wrapRender(screenContext, () -> {
                original.call(new Object[]{screen, Integer.valueOf(mouseX), Integer.valueOf(mouseY), Float.valueOf(tickDelta)});
            });
        });
        screenCustomFontStack.pop(screen);
    }

    private static void handleMouseDragged() {
        int rawMouseX = Mouse.getX();
        int rawMouseY = Mouse.getY();
        if (rawMouseX == lastMouseX && rawMouseY == lastMouseY) {
            return;
        }
        VersionedMinecraft versionedMinecraftZ = bib.z();
        MouseButton mouseButton = versionedMinecraftZ.getCurrentEventButton();
        if (lastMouseX != -1 && lastMouseY != -1 && mouseButton != null) {
            Window window = Laby.labyAPI().minecraft().minecraftWindow();
            double widthFactor = ((double) window.getScaledWidth()) / ((double) ((bib) versionedMinecraftZ).d);
            double heightFactor = ((double) window.getScaledHeight()) / ((double) ((bib) versionedMinecraftZ).e);
            double deltaX = ((double) (rawMouseX - lastMouseX)) * widthFactor;
            double deltaY = ((double) (-(rawMouseY - lastMouseY))) * heightFactor;
            Laby.labyAPI().minecraft().updateMouse(((double) rawMouseX) * widthFactor, ((double) window.getScaledHeight()) - (((double) rawMouseY) * heightFactor), mouse -> {
                ((VersionedMinecraft) versionedMinecraftZ).handleMouseDragged(mouse, mouseButton, deltaX, deltaY);
            });
        }
        lastMouseX = rawMouseX;
        lastMouseY = rawMouseY;
    }
}
