package net.labymod.api.client.resources.texture;

import java.util.Locale;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.theme.config.VanillaThemeConfigAccessor;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.loader.platform.PlatformEnvironment;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/texture/AbstractMinecraftTextures.class */
public abstract class AbstractMinecraftTextures implements MinecraftTextures {
    protected static final String TEXTURES = "textures/";
    protected static final String GUI = "textures/gui/";
    protected static final String GUI_TITLE = "textures/gui/title/";
    protected static final String GUI_TITLE_BACKGROUND = "textures/gui/title/background/";
    private final ResourceLocation BUTTON_TEXTURE = resource(GUI, "widgets.png");
    private final ResourceLocation ICONS_TEXTURE = resource(GUI, "icons.png");
    private final ResourceLocation SERVER_SELECTION_TEXTURE = resource(GUI, "server_selection.png");
    private final ResourceLocation BARS_TEXTURE = resource(GUI, "bars.png");
    private final ResourceLocation MINECRAFT_LOGO = resource(GUI_TITLE, "minecraft.png");
    private final ResourceLocation MINECRAFT_EDITION = resource(GUI_TITLE, "edition.png");
    private final ResourceLocation SPLASH = resource(GUI_TITLE, "mojangstudios.png");
    private final ResourceLocation[] PANORAMA = new ResourceLocation[6];
    private final ResourceLocation PANORAMA_OVERLAY = resource(GUI_TITLE_BACKGROUND, "panorama_overlay.png");
    private final ResourceLocation SCREEN_LEGACY_BACKGROUND_TEXTURE = resource(GUI, "options_background.png");
    private final ResourceLocation SCREEN_LIST_BACKGROUND_TEXTURE = resource(GUI, "menu_list_background.png");
    private final ResourceLocation SCREEN_MENU_BACKGROUND_TEXTURE = resource(GUI, "menu_background.png");
    private final ResourceLocation INWORLD_MENU_BACKGROUND_TEXTURE = resource(GUI, "inworld_menu_background.png");
    private final ResourceLocation INWORLD_MENU_LIST_BACKGROUND_TEXTURE = resource(GUI, "inworld_menu_list_background.png");
    private final ResourceLocation SCREEN_MENU_HEADER_SEPARATOR_TEXTURE = resource(GUI, "header_separator.png");
    private final ResourceLocation SCREEN_MENU_FOOTER_SEPARATOR_TEXTURE = resource(GUI, "footer_separator.png");
    private final ResourceLocation INWORLD_MENU_HEADER_SEPARATOR_TEXTURE = resource(GUI, "inworld_header_separator.png");
    private final ResourceLocation INWORLD_MENU_FOOTER_SEPARATOR_TEXTURE = resource(GUI, "inworld_footer_separator.png");

    public AbstractMinecraftTextures() {
        for (int i = 0; i < this.PANORAMA.length; i++) {
            this.PANORAMA[i] = resource(GUI_TITLE_BACKGROUND, String.format(Locale.ROOT, "panorama_%s.png", Integer.valueOf(i)));
        }
    }

    @Override // net.labymod.api.client.resources.texture.MinecraftTextures
    public ResourceLocation widgetsTexture() {
        return this.BUTTON_TEXTURE;
    }

    @Override // net.labymod.api.client.resources.texture.MinecraftTextures
    public ResourceLocation screenListBackgroundTexture() {
        if (PlatformEnvironment.isFreshUI()) {
            if (isInWorld() && isFreshUIEnabled()) {
                return this.INWORLD_MENU_LIST_BACKGROUND_TEXTURE;
            }
            return this.SCREEN_LIST_BACKGROUND_TEXTURE;
        }
        return this.SCREEN_LEGACY_BACKGROUND_TEXTURE;
    }

    @Override // net.labymod.api.client.resources.texture.MinecraftTextures
    public ResourceLocation screenMenuBackgroundTexture() {
        if (PlatformEnvironment.isFreshUI()) {
            if (isInWorld() && isFreshUIEnabled()) {
                return this.INWORLD_MENU_BACKGROUND_TEXTURE;
            }
            return this.SCREEN_MENU_BACKGROUND_TEXTURE;
        }
        return this.SCREEN_LEGACY_BACKGROUND_TEXTURE;
    }

    @Override // net.labymod.api.client.resources.texture.MinecraftTextures
    public ResourceLocation screenMenuHeaderSeparatorTexture() {
        if (isInWorld()) {
            return this.INWORLD_MENU_HEADER_SEPARATOR_TEXTURE;
        }
        return this.SCREEN_MENU_HEADER_SEPARATOR_TEXTURE;
    }

    @Override // net.labymod.api.client.resources.texture.MinecraftTextures
    public ResourceLocation screenMenuFooterSeparatorTexture() {
        if (isInWorld()) {
            return this.INWORLD_MENU_FOOTER_SEPARATOR_TEXTURE;
        }
        return this.SCREEN_MENU_FOOTER_SEPARATOR_TEXTURE;
    }

    @Override // net.labymod.api.client.resources.texture.MinecraftTextures
    public ResourceLocation iconsTexture() {
        return this.ICONS_TEXTURE;
    }

    @Override // net.labymod.api.client.resources.texture.MinecraftTextures
    public ResourceLocation serverSelectionTexture() {
        return this.SERVER_SELECTION_TEXTURE;
    }

    @Override // net.labymod.api.client.resources.texture.MinecraftTextures
    public ResourceLocation barsTexture() {
        return this.BARS_TEXTURE;
    }

    @Override // net.labymod.api.client.resources.texture.MinecraftTextures
    public ResourceLocation minecraftLogoTexture() {
        return this.MINECRAFT_LOGO;
    }

    @Override // net.labymod.api.client.resources.texture.MinecraftTextures
    public ResourceLocation minecraftEditionTexture() {
        return this.MINECRAFT_EDITION;
    }

    @Override // net.labymod.api.client.resources.texture.MinecraftTextures
    public ResourceLocation splashTexture() {
        return this.SPLASH;
    }

    @Override // net.labymod.api.client.resources.texture.MinecraftTextures
    public ResourceLocation[] panoramaTextures() {
        return this.PANORAMA;
    }

    @Override // net.labymod.api.client.resources.texture.MinecraftTextures
    public ResourceLocation panoramaOverlayTexture() {
        return this.PANORAMA_OVERLAY;
    }

    public static ResourceLocation resource(String parent, String path) {
        return resource(parent + path);
    }

    public static ResourceLocation resource(String path) {
        return Laby.labyAPI().renderPipeline().resources().resourceLocationFactory().createMinecraft(path);
    }

    private boolean isInWorld() {
        return Laby.labyAPI().minecraft().isIngame();
    }

    private boolean isFreshUIEnabled() {
        VanillaThemeConfigAccessor config = (VanillaThemeConfigAccessor) Laby.labyAPI().themeService().getThemeConfig(VanillaThemeConfigAccessor.class);
        return config != null && config.freshUI().get().booleanValue();
    }
}
