package animated_mojang.client.internal;

import animated_mojang.client.mixin.GuiGraphicsAccessor;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.render.state.GuiElementRenderState;

final class VersionGuiRenderBridge {
	private VersionGuiRenderBridge() {
	}

	static void submit(GuiGraphics graphics, GuiElementRenderState element) {
		((GuiGraphicsAccessor) graphics).animatedMojang$getGuiRenderState().submitGuiElement(element);
	}
}
