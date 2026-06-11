package net.labymod.core.client.gfx.pipeline.renderer.util;

import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.render.matrix.Stack;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gfx/pipeline/renderer/util/BrandingRenderer.class */
public final class BrandingRenderer {
    public static final float NORMAL_INVENTORY_HEIGHT = 96.0f;
    public static final float CREATIVE_INVENTORY_HEIGHT = 108.0f;
    private static final Icon BRANDING = Icon.texture(Textures.LABYMOD_LOGO);
    private static final float BRANDING_WIDTH = 100.0f;
    private static final float BRANDING_HEIGHT = 24.25f;

    public static void renderCentered(Stack stack, float y) {
        renderCentered(stack, y, Laby.labyAPI().config().ingame().inventoryBanner().get().booleanValue());
    }

    public static void renderCentered(Stack stack, float y, boolean shouldRender) {
        Window window = Laby.labyAPI().minecraft().minecraftWindow();
        float halfWidth = window.getScaledWidth() / 2.0f;
        float halfHeight = window.getScaledHeight() / 2.0f;
        render(stack, halfWidth - 50.0f, (halfHeight - 12.125f) - y, shouldRender);
    }

    public static void render(Stack stack, float x, float y, boolean shouldRender) {
        if (!shouldRender) {
            return;
        }
        Minecraft minecraft = Laby.labyAPI().minecraft();
        Laby.references().renderEnvironmentContext().screenContext().runInContext(stack, minecraft.mouse(), minecraft.getPartialTicks(), context -> {
            context.canvas().submitIcon(BRANDING, x, y, BRANDING_WIDTH, BRANDING_HEIGHT);
        });
    }
}
