package net.labymod.api;

import net.labymod.api.Constants;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.ThemeResourceLocation;
import net.labymod.api.client.resources.texture.ThemeTextureLocation;
import net.labymod.api.util.StringUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/Textures.class */
public class Textures {
    public static final ResourceLocation EMPTY = Constants.Resources.resource("textures/empty.png");
    public static final ResourceLocation SPRAY_LOADING = Constants.Resources.resource("textures/spray/spray_loading.png");
    public static final ResourceLocation WHITE = ThemeTextureLocation.of("white");
    public static final ResourceLocation NOTIFICATION = ThemeTextureLocation.of("activities/notification", 128);
    public static final ResourceLocation POPUP_BACKGROUND = ThemeTextureLocation.of("activities/popup_background", 128);
    public static final ResourceLocation TRANSPARENT = ThemeTextureLocation.of("transparent");
    public static final ResourceLocation LABYMOD_LOGO = ThemeTextureLocation.of("labymod_logo");
    public static final ResourceLocation STAR = ThemeTextureLocation.of("misc/star");
    public static final ResourceLocation DEFAULT_SERVER_ICON = ThemeTextureLocation.of("widgets/default_server_icon");
    public static final ResourceLocation SURVIVAL_INVENTORY_BACKGROUND = ThemeTextureLocation.of("activities/inventory/inventory");
    public static final ResourceLocation CREATIVE_INVENTORY_TAB_INVENTORY = ThemeTextureLocation.of("activities/inventory/creative_inventory/tab_inventory");
    public static final ResourceLocation CREATIVE_INVENTORY_TAB_ITEM_SEARCH = ThemeTextureLocation.of("activities/inventory/creative_inventory/tab_item_search");
    public static final ResourceLocation CREATIVE_INVENTORY_TAB_ITEMS = ThemeTextureLocation.of("activities/inventory/creative_inventory/tab_items");
    public static final ResourceLocation CREATIVE_INVENTORY_TABS = ThemeTextureLocation.of("activities/inventory/creative_inventory/tabs");

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/Textures$Hud.class */
    public static class Hud extends ThemedResourceDirectory {
        private static final String HUD = "textures/activities/hud/";
        public static final ResourceLocation BACKGROUND = resource(HUD, "background.png", 1920, 1080);
        public static final ResourceLocation FRAME = resource(HUD, "frame.png", 762, 762);
        public static final ResourceLocation STRING = resource(HUD, "string.png", 200, 200);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/Textures$SpriteAccountManager.class */
    public static class SpriteAccountManager {
        public static final ThemeTextureLocation TEXTURE = ThemeTextureLocation.of("activities/account_manager");
        public static Icon MOJANG_LOGO = Icon.sprite(TEXTURE, 0, 0, 32);
        public static Icon MICROSOFT_LOGO = Icon.sprite(TEXTURE, 1, 0, 32);
        public static Icon LABYMOD_LOGO = Icon.sprite(TEXTURE, 1, 1, 32);
        public static Icon JAVA_LOGO = Icon.sprite(TEXTURE, 1, 1, 32);
        public static Icon MICROSOFT_STORE_LOGO = Icon.sprite(TEXTURE, 2, 1, 32);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/Textures$SpriteCommon.class */
    public static class SpriteCommon {
        public static final ThemeTextureLocation TEXTURE = ThemeTextureLocation.of("activities/common", 128, 128);
        public static final Icon WHITE_X = Icon.sprite(TEXTURE, 0, 0, 16);
        public static final Icon GREEN_CHECKED = Icon.sprite(TEXTURE, 1, 0, 16);
        public static final Icon ARROW_RIGHT = Icon.sprite(TEXTURE, 3, 0, 16);
        public static final Icon ARROW_LEFT = Icon.sprite(TEXTURE, 3, 1, 16);
        public static final Icon MOUSE_RIGHT = Icon.sprite(TEXTURE, 4, 0, 16);
        public static final Icon MOUSE_LEFT = Icon.sprite(TEXTURE, 4, 1, 16);
        public static final Icon GREATER_THAN = Icon.sprite(TEXTURE, 5, 0, 16);
        public static final Icon LESS_THAN = Icon.sprite(TEXTURE, 5, 1, 16);
        public static final Icon COPY = Icon.sprite(TEXTURE, 12, 0, 8);
        public static final Icon SETTINGS = Icon.sprite(TEXTURE, 2, 0, 16);
        public static final Icon TRASH = Icon.sprite(TEXTURE, 2, 1, 16);
        public static final Icon ADD = Icon.sprite(TEXTURE, 6, 1, 16);
        public static final Icon X = Icon.sprite(TEXTURE, 6, 2, 16);
        public static final Icon QUESTION_MARK = Icon.sprite(TEXTURE, 7, 1, 16);
        public static final Icon LARGE_BURGER_DOTS = Icon.sprite(TEXTURE, 0, 1, 16);
        public static final Icon LARGE_DOTS = Icon.sprite(TEXTURE, 4, 2, 16);
        public static final Icon EDIT = Icon.sprite(TEXTURE, 14, 4, 8);
        public static final Icon REFRESH = Icon.sprite(TEXTURE, 14, 5, 8);
        public static final Icon PIN = Icon.sprite(TEXTURE, 2, 2, 8);
        public static final Icon DARK_ADD = Icon.sprite(TEXTURE, 3, 2, 8);
        public static final Icon SMALL_BURGER_DOTS = Icon.sprite(TEXTURE, 2, 3, 8);
        public static final Icon SMALL_BURGER_WITH_SHADOW = Icon.sprite(TEXTURE, 3, 3, 8);
        public static final Icon SMALL_BURGER = Icon.sprite(TEXTURE, 6, 4, 8);
        public static final Icon EXPORT = Icon.sprite(TEXTURE, 6, 5, 8);
        public static final Icon SMALL_UP_SHADOW = Icon.sprite(TEXTURE, 7, 4, 8);
        public static final Icon SMALL_DOWN_SHADOW = Icon.sprite(TEXTURE, 7, 5, 8);
        public static final Icon SMALL_UP = Icon.sprite(TEXTURE, 0, 4, 8);
        public static final Icon SMALL_DOWN = Icon.sprite(TEXTURE, 0, 5, 8);
        public static final Icon DELIMITER = Icon.sprite(TEXTURE, 15, 2, 8, 16);
        public static final Icon SMALL_X = Icon.sprite(TEXTURE, 2, 4, 8);
        public static final Icon SMALL_CHECKED = Icon.sprite(TEXTURE, 2, 5, 8);
        public static final Icon SMALL_ADD = Icon.sprite(TEXTURE, 3, 4, 8);
        public static final Icon SMALL_ADD_WITH_SHADOW = Icon.sprite(TEXTURE, 3, 5, 8);
        public static final Icon BACK_BUTTON = Icon.sprite(TEXTURE, 4, 4, 8);
        public static final Icon FORWARD_BUTTON = Icon.sprite(TEXTURE, 5, 4, 8);
        public static final Icon SMALL_X_WITH_SHADOW = Icon.sprite(TEXTURE, 4, 5, 8);
        public static final Icon MEDIUM_X_WITH_SHADOW = Icon.sprite(TEXTURE, 5, 5, 8);
        public static final Icon CHAT_BUBBLE = Icon.sprite(TEXTURE, 0, 3, 16);
        public static final Icon MULTIPLAYER = Icon.sprite(TEXTURE, 1, 3, 16);
        public static final Icon SINGLEPLAYER = Icon.sprite(TEXTURE, 2, 3, 16);
        public static final Icon WORKBENCH = Icon.sprite(TEXTURE, 3, 3, 16);
        public static final Icon LABYMOD = Icon.sprite(TEXTURE, 4, 3, 16);
        public static final Icon WHITE_GREATER_THAN = Icon.sprite(TEXTURE, 5, 2, 16);
        public static final Icon WHITE_LESS_THAN = Icon.sprite(TEXTURE, 5, 3, 16);
        public static final Icon SUBMIT = Icon.sprite(TEXTURE, 6, 3, 16);
        public static final Icon SHIELD = Icon.sprite(TEXTURE, 7, 3, 16);
        public static final Icon ROBOT = Icon.sprite(TEXTURE, 0, 6, 16);
        public static final Icon HOPPER = Icon.sprite(TEXTURE, 1, 6, 16);
        public static final Icon SYMBOLS = Icon.sprite(TEXTURE, 2, 6, 16);
        public static final Icon BOOK = Icon.sprite(TEXTURE, 3, 6, 16);
        public static final Icon CIRCLE_WARNING = Icon.sprite(TEXTURE, 4, 6, 16);
        public static final Icon BULLET_POINT = Icon.sprite(TEXTURE, 1, 5, 16);
        public static final Icon CROWN = Icon.sprite(TEXTURE, 0, 4, 16);
        public static final Icon PICTURE = Icon.sprite(TEXTURE, 1, 4, 16);
        public static final Icon CART = Icon.sprite(TEXTURE, 2, 4, 16);
        public static final Icon DISCONNECT = Icon.sprite(TEXTURE, 3, 4, 16);
        public static final Icon FOLDER = Icon.sprite(TEXTURE, 4, 4, 16);
        public static final Icon LEAVE_FOLDER = Icon.sprite(TEXTURE, 5, 4, 16);
        public static final Icon LOOTBOX = Icon.sprite(TEXTURE, 6, 4, 16);
        public static final Icon OPEN_FILE = Icon.sprite(TEXTURE, 3, 5, 16);
        public static final Icon LARGE_COPY = Icon.sprite(TEXTURE, 4, 5, 16);
        public static final Icon UPLOAD = Icon.sprite(TEXTURE, 5, 5, 16);
        public static final Icon PAINT = Icon.sprite(TEXTURE, 6, 5, 16);
        public static final Icon SHARE = Icon.sprite(TEXTURE, 7, 5, 16);
        public static final Icon STATUS_INDICATOR = Icon.sprite(TEXTURE, 0, 10, 8);
        public static final Icon BUG = Icon.sprite(TEXTURE, 1, 7, 16);
        public static final Icon NEW = Icon.sprite(TEXTURE, 2, 7, 16);
        public static final Icon EXCLAMATION_MARK_DARK = Icon.sprite(TEXTURE, 0, 7, 8, 16);
        public static final Icon EXCLAMATION_MARK_LIGHT = Icon.sprite(TEXTURE, 1, 7, 8, 16);
        public static final Icon PEPE_SAD = Icon.sprite(TEXTURE, 3, 3, 32);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/Textures$SpriteCustomization.class */
    public static class SpriteCustomization {
        public static final ThemeTextureLocation TEXTURE = ThemeTextureLocation.of("activities/customization", 128);
        public static final Icon CLOAK = Icon.sprite(TEXTURE, 0, 0, 32);
        public static final Icon JACKET = Icon.sprite(TEXTURE, 1, 0, 32);
        public static final Icon RIGHT_ARM = Icon.sprite(TEXTURE, 2, 0, 32);
        public static final Icon LEFT_ARM = Icon.sprite(TEXTURE, 3, 0, 32);
        public static final Icon RIGHT_LEG = Icon.sprite(TEXTURE, 0, 1, 32);
        public static final Icon LEFT_LEG = Icon.sprite(TEXTURE, 1, 1, 32);
        public static final Icon HEAD = Icon.sprite(TEXTURE, 2, 1, 32);
        public static final Icon BODY = Icon.sprite(TEXTURE, 3, 1, 32);
        public static final Icon LEGS = Icon.sprite(TEXTURE, 0, 2, 32);
        public static final Icon HAND = Icon.sprite(TEXTURE, 1, 2, 32);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/Textures$SpriteFlint.class */
    public static class SpriteFlint {
        public static final ThemeTextureLocation TEXTURE = ThemeTextureLocation.of("activities/flint/flint");
        public static final Icon SMALL_STAR = Icon.sprite16(TEXTURE, 0, 0);
        public static final Icon SMALL_STAR_LEFT = Icon.sprite16(TEXTURE, 1, 0);
        public static final Icon SMALL_STAR_RIGHT = Icon.sprite16(TEXTURE, 2, 0);
        public static final Icon WARNING = Icon.sprite(TEXTURE, 0, 2, 32);
        public static final Icon DOWNLOADS = Icon.sprite(TEXTURE, 0, 1, 32);
        public static final Icon REFRESH = Icon.sprite(TEXTURE, 1, 1, 32);
        public static final Icon PACKAGES = Icon.sprite(TEXTURE, 2, 1, 32);
        public static final Icon DOCUMENT = Icon.sprite(TEXTURE, 3, 1, 32);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/Textures$SpriteHudPlaceholder.class */
    public static class SpriteHudPlaceholder {
        public static final ThemeTextureLocation TEXTURE = ThemeTextureLocation.of("activities/hud/hud_placeholder", 128);
        public static final Icon HELMET = Icon.sprite(TEXTURE, 0, 0, 16);
        public static final Icon CHESTPLATE = Icon.sprite(TEXTURE, 1, 0, 16);
        public static final Icon LEGGINGS = Icon.sprite(TEXTURE, 2, 0, 16);
        public static final Icon BOOTS = Icon.sprite(TEXTURE, 3, 0, 16);
        public static final Icon MAIN_AHND = Icon.sprite(TEXTURE, 4, 0, 16);
        public static final Icon OFF_HAND = Icon.sprite(TEXTURE, 5, 0, 16);
        public static final Icon ARROW = Icon.sprite(TEXTURE, 6, 0, 16);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/Textures$SpriteLabyMod.class */
    public static class SpriteLabyMod {
        public static final ThemeTextureLocation TEXTURE = ThemeTextureLocation.of("labymod", 256, 128);
        public static final Icon DEFAULT_WOLF_BLURRY = Icon.sprite(TEXTURE, 0, 0, 32);
        public static final Icon WHITE_WOLF_BLURRY = Icon.sprite(TEXTURE, 1, 0, 32);
        public static final Icon DEFAULT_WOLF_SHARP = Icon.sprite(TEXTURE, 0, 1, 32);
        public static final Icon WHITE_WOLF_HIGH_RES = Icon.spriteCoordinates(TEXTURE, 32, 32, 96, 96);
        public static final Icon DEFAULT_WOLF_HIGH_RES = Icon.spriteCoordinates(TEXTURE, 128, 32, 96, 96);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/Textures$SpriteMarker.class */
    public static class SpriteMarker {
        public static final ThemeTextureLocation TEXTURE = ThemeTextureLocation.of("misc/marker", 128, 64);
        public static final Icon CIRCLE = Icon.sprite(TEXTURE, 0, 0, 64);
        public static final Icon LINE = Icon.sprite(TEXTURE, 1, 0, 64);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/Textures$SpriteMinecraftIcons.class */
    public static class SpriteMinecraftIcons {
        public static final ThemeTextureLocation TEXTURE = ThemeTextureLocation.of("activities/minecraft_icons", 128, 128);
        public static final Icon LANGUAGE = Icon.sprite(TEXTURE, 0, 0, 16);
        public static final Icon ACCESSIBILITY = Icon.sprite(TEXTURE, 1, 0, 16);
        public static final Icon LOCK_CLOSED = Icon.sprite(TEXTURE, 2, 0, 16);
        public static final Icon LOCK_OPEN = Icon.sprite(TEXTURE, 3, 0, 16);
        public static final Icon FRIENDS = Icon.sprite(TEXTURE, 4, 0, 16);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/Textures$SpriteServerSelection.class */
    public static class SpriteServerSelection {
        public static final ThemeTextureLocation TEXTURE = ThemeTextureLocation.of("activities/multiplayer/selection");
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/Textures$SpriteShop.class */
    public static class SpriteShop {
        public static final ThemeTextureLocation TEXTURE = ThemeTextureLocation.of("activities/shop/shop", 64);
        public static final Icon FEATURED = Icon.sprite(TEXTURE, 0, 0, 8);
        public static final Icon PARTNER = Icon.sprite(TEXTURE, 1, 0, 8);
        public static final Icon AURAS = Icon.sprite(TEXTURE, 2, 0, 8);
        public static final Icon BODY = Icon.sprite(TEXTURE, 3, 0, 8);
        public static final Icon HEAD = Icon.sprite(TEXTURE, 4, 0, 8);
        public static final Icon PETS = Icon.sprite(TEXTURE, 5, 0, 8);
        public static final Icon SHOES = Icon.sprite(TEXTURE, 6, 0, 8);
        public static final Icon UNDERGLOWS = Icon.sprite(TEXTURE, 7, 0, 8);
        public static final Icon WINGS = Icon.sprite(TEXTURE, 0, 1, 8);
        public static final Icon DAILY = Icon.sprite(TEXTURE, 1, 1, 8);
        public static final Icon EPIC = Icon.sprite(TEXTURE, 2, 1, 8);
        public static final Icon LEGENDARY = Icon.sprite(TEXTURE, 3, 1, 8);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/Textures$SpriteWidgetEditor.class */
    public static class SpriteWidgetEditor {
        public static final ThemeTextureLocation TEXTURE = ThemeTextureLocation.of("activities/hud/widget_editor", 256, 256);
        public static final Icon TRASH_FRAME_0 = Icon.sprite16(TEXTURE, 0, 0);
        public static final Icon TRASH_FRAME_1 = Icon.sprite16(TEXTURE, 0, 1);
        public static final Icon TRASH_FRAME_2 = Icon.sprite16(TEXTURE, 0, 2);
        public static final Icon MAXIMIZE = Icon.sprite(TEXTURE, 4, 0, 16);
        public static final Icon MINIMIZE = Icon.sprite(TEXTURE, 4, 1, 16);
        public static final Icon GLOBE = Icon.sprite(TEXTURE, 5, 0, 16);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/Textures$Title.class */
    public static class Title extends ThemedResourceDirectory {
        private static final String GUI = "textures/gui/";
        public static final ResourceLocation MINECRAFT_LOGO = resource(GUI, "minecraft_logo.png", 1024, 1024);
        public static final ResourceLocation LABYMOD_EDITION = resource(GUI, "labymod_edition.png", 384, 48);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/Textures$SpriteBrands.class */
    public static class SpriteBrands {
        public static final ThemeTextureLocation TEXTURE = ThemeTextureLocation.of("activities/brands");
        public static final Icon TWITCH = Icon.sprite(TEXTURE, 0, 0, 32);
        public static final Icon TWITTER = Icon.sprite(TEXTURE, 1, 0, 32);
        public static final Icon YOUTUBE = Icon.sprite(TEXTURE, 2, 0, 32);
        public static final Icon REDDIT = Icon.sprite(TEXTURE, 3, 0, 32);
        public static final Icon DISCORD = Icon.sprite(TEXTURE, 0, 1, 32);
        public static final Icon TIKTOK = Icon.sprite(TEXTURE, 1, 1, 32);
        public static final Icon INSTAGRAM = Icon.sprite(TEXTURE, 2, 1, 32);
        public static final Icon GITHUB = Icon.sprite(TEXTURE, 3, 1, 32);
        public static final Icon SPOTIFY = Icon.sprite(TEXTURE, 0, 2, 32);
        public static final Icon XBOX = Icon.sprite(TEXTURE, 1, 2, 32);

        public static Icon byName(String service) {
            switch (StringUtil.toLowercase(service)) {
                case "twitch":
                    return TWITCH;
                case "twitter":
                    return TWITTER;
                case "youtube":
                    return YOUTUBE;
                case "reddit":
                    return REDDIT;
                case "discord":
                    return DISCORD;
                case "tiktok":
                    return TIKTOK;
                case "instagram":
                    return INSTAGRAM;
                case "github":
                    return GITHUB;
                case "spotify":
                    return SPOTIFY;
                case "xbox":
                    return XBOX;
                default:
                    return null;
            }
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/Textures$Splash.class */
    public static class Splash extends GlobalResourceDirectory {
        public static final String PATH_SPLASH = "textures/splash/";
        public static final String PATH_CAVE = "textures/splash/cave/";
        public static final ResourceLocation[] MOJANG_STUDIOS_FRAMES = new ResourceLocation[12];
        public static final ResourceLocation[] MINECRAFT_FRAMES = new ResourceLocation[9];
        public static final ResourceLocation LABYMOD = globalResource("textures/splash/labymod.png");
        public static final ResourceLocation CAVE_ENTITIES = globalResource("textures/splash/cave/entities.png");
        public static final ResourceLocation MINECRAFT_SPRITE = globalResource("textures/splash/minecraft_sprite.png");
        public static final ResourceLocation LAVA_FLOW = globalResource("textures/splash/cave/lava_flow.png");
        public static final ResourceLocation BLOCKS = globalResource("textures/splash/cave/blocks.png");
        public static final ResourceLocation PARTICLES = globalResource("textures/splash/cave/particles.png");
        public static final ResourceLocation SNOW = globalResource("textures/splash/cave/snow.png");
        public static final ResourceLocation HEARTS = globalResource("textures/splash/cave/hearts.png");

        static {
            for (int i = 0; i < MOJANG_STUDIOS_FRAMES.length; i++) {
                MOJANG_STUDIOS_FRAMES[i] = globalResource("textures/splash/mojangstudios/mojangstudios_" + i + ".png");
            }
            for (int i2 = 0; i2 < MINECRAFT_FRAMES.length; i2++) {
                MINECRAFT_FRAMES[i2] = globalResource("textures/splash/minecraft/minecraft_" + i2 + ".png");
            }
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/Textures$ThemedResourceDirectory.class */
    private static class ThemedResourceDirectory {
        private ThemedResourceDirectory() {
        }

        protected static ResourceLocation resource(String parent, String path, int width, int height) {
            return resource(parent + path, width, height);
        }

        protected static ResourceLocation resource(String path, int width, int height) {
            return ThemeResourceLocation.FACTORY.createThemeTexture(path, width, height);
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/Textures$GlobalResourceDirectory.class */
    private static class GlobalResourceDirectory {
        private GlobalResourceDirectory() {
        }

        protected static ResourceLocation globalResource(String path) {
            return Laby.labyAPI().renderPipeline().resources().resourceLocationFactory().create("labymod", path);
        }
    }
}
