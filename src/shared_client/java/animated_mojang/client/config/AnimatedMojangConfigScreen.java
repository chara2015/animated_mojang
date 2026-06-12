package animated_mojang.client.config;

import animated_mojang.ModMetadata;
import animated_mojang.config.AnimatedMojangConfig;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public final class AnimatedMojangConfigScreen extends Screen {
	private final Screen parent;

	public AnimatedMojangConfigScreen(Screen parent) {
		super(Component.literal(ModMetadata.MOD_NAME));
		this.parent = parent;
	}

	@Override
	protected void init() {
		int left = this.width / 2 - 100;
		int top = this.height / 2 - 34;
		addRenderableWidget(Button.builder(mojangButtonText(), button -> {
			AnimatedMojangConfig.setMojangLogoAnimationEnabled(
					!AnimatedMojangConfig.isMojangLogoAnimationEnabled());
			button.setMessage(mojangButtonText());
		}).bounds(left, top, 200, 20).build());
		addRenderableWidget(Button.builder(minecraftButtonText(), button -> {
			AnimatedMojangConfig.setMinecraftTitleAnimationEnabled(
					!AnimatedMojangConfig.isMinecraftTitleAnimationEnabled());
			button.setMessage(minecraftButtonText());
		}).bounds(left, top + 24, 200, 20).build());
		addRenderableWidget(Button.builder(Component.translatable("gui.done"), button -> onClose())
				.bounds(left, top + 58, 200, 20).build());
	}

	@Override
	public void onClose() {
		AnimatedMojangConfig.save();
		if (minecraft != null) {
			minecraft.setScreen(parent);
		}
	}

	private static Component mojangButtonText() {
		return optionText("animated_mojang.option.mojang_logo_animation",
				AnimatedMojangConfig.isMojangLogoAnimationEnabled());
	}

	private static Component minecraftButtonText() {
		return optionText("animated_mojang.option.minecraft_title_animation",
				AnimatedMojangConfig.isMinecraftTitleAnimationEnabled());
	}

	private static Component optionText(String translationKey, boolean enabled) {
		return Component.translatable(translationKey).append(": ")
				.append(Component.translatable(enabled ? "options.on" : "options.off"));
	}
}
