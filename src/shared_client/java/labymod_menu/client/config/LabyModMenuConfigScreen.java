package labymod_menu.client.config;

import labymod_menu.ModMetadata;
import labymod_menu.config.LabyModMenuConfig;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public final class LabyModMenuConfigScreen extends Screen {
	private final Screen parent;

	public LabyModMenuConfigScreen(Screen parent) {
		super(Component.literal(ModMetadata.MOD_NAME));
		this.parent = parent;
	}

	@Override
	protected void init() {
		int left = this.width / 2 - 100;
		int top = this.height / 2 - 46;
		addRenderableWidget(Button.builder(mojangButtonText(), button -> {
			LabyModMenuConfig.setMojangLogoAnimationEnabled(
					!LabyModMenuConfig.isMojangLogoAnimationEnabled());
			button.setMessage(mojangButtonText());
		}).bounds(left, top, 200, 20).build());
		addRenderableWidget(Button.builder(darkLoadingScreenButtonText(), button -> {
			LabyModMenuConfig.setDarkLoadingScreenEnabled(
					!LabyModMenuConfig.isDarkLoadingScreenEnabled());
			button.setMessage(darkLoadingScreenButtonText());
		}).bounds(left, top + 24, 200, 20).build());
		addRenderableWidget(Button.builder(minecraftButtonText(), button -> {
			LabyModMenuConfig.setMinecraftTitleAnimationEnabled(
					!LabyModMenuConfig.isMinecraftTitleAnimationEnabled());
			button.setMessage(minecraftButtonText());
		}).bounds(left, top + 48, 200, 20).build());
		addRenderableWidget(Button.builder(Component.translatable("gui.done"), button -> onClose())
				.bounds(left, top + 82, 200, 20).build());
	}

	@Override
	public void onClose() {
		LabyModMenuConfig.save();
		if (minecraft != null) {
			minecraft.setScreen(parent);
		}
	}

	private static Component mojangButtonText() {
		return optionText("labymod_menu.option.mojang_logo_animation",
				LabyModMenuConfig.isMojangLogoAnimationEnabled());
	}

	private static Component minecraftButtonText() {
		return optionText("labymod_menu.option.minecraft_title_animation",
				LabyModMenuConfig.isMinecraftTitleAnimationEnabled());
	}

	private static Component darkLoadingScreenButtonText() {
		return optionText("labymod_menu.option.dark_loading_screen",
				LabyModMenuConfig.isDarkLoadingScreenEnabled());
	}

	private static Component optionText(String translationKey, boolean enabled) {
		return Component.translatable(translationKey).append(": ")
				.append(Component.translatable(enabled ? "options.on" : "options.off"));
	}
}
