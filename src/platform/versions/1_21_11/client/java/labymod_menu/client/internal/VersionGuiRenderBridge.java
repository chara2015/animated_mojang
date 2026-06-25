package labymod_menu.client.internal;

import labymod_menu.client.mixin.GuiGraphicsAccessor;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.render.state.GuiElementRenderState;

final class VersionGuiRenderBridge {
	private VersionGuiRenderBridge() {
	}

	static void submit(GuiGraphics graphics, GuiElementRenderState element) {
		((GuiGraphicsAccessor) graphics).labyModMenu$getGuiRenderState().submitGuiElement(element);
	}
}
