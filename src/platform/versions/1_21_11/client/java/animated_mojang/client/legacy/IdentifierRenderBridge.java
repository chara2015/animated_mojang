package animated_mojang.client.legacy;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;

public final class IdentifierRenderBridge {
	private IdentifierRenderBridge() {
	}

	public static Object location(String namespace, String path) {
		return Identifier.fromNamespaceAndPath(namespace, path);
	}

	public static void blit(GuiGraphics graphics, Object texture, int x, int y, float u, float v, int width, int height,
			int sourceWidth, int sourceHeight, int textureWidth, int textureHeight, int color) {
		graphics.blit(RenderPipelines.GUI_TEXTURED, (Identifier) texture, x, y, u, v, width, height,
				sourceWidth, sourceHeight, textureWidth, textureHeight, color);
	}
}
