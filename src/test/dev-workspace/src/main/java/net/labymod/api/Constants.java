package net.labymod.api;

import java.nio.file.Path;
import java.nio.file.Paths;
import net.labymod.api.Textures;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.ThemeResourceLocation;
import net.labymod.api.client.resources.ThemeResourceRegistry;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/Constants.class */
public class Constants {

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/Constants$Branding.class */
    public static class Branding {
        public static final String NAME = "LabyMod";
    }

    private Constants() {
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/Constants$Files.class */
    public static class Files {
        public static final Path LABYMOD_DIRECTORY = Paths.get("labymod-neo", new String[0]);
        public static final Path ADDONS = fromArguments("net.labymod.addons-dir", LABYMOD_DIRECTORY.resolve("addons"));
        public static final Path LIBRARIES = LABYMOD_DIRECTORY.resolve("libraries");
        public static final Path NATIVES = LABYMOD_DIRECTORY.resolve("natives");
        public static final Path CONFIGS = LABYMOD_DIRECTORY.resolve("configs");
        public static final Path MAPPINGS = LABYMOD_DIRECTORY.resolve("mappings");
        public static final Path FILE_CACHE = LABYMOD_DIRECTORY.resolve("cache");
        public static final Path LABY_NET_SERVER_GROUPS = FILE_CACHE.resolve("server-groups");
        public static final Path LABYMOD_DEBUG_DIRECTORY = LABYMOD_DIRECTORY.resolve("debug");
        public static final Path PROFILER_EXPORTS = LABYMOD_DIRECTORY.resolve("profiler-exports");
        public static final Path PROFILER_EXPORTS_SUPPORT = PROFILER_EXPORTS.resolve("support");
        public static final Path ACCOUNTS = LABYMOD_DIRECTORY.resolve("accounts.json");
        public static final Path SERVER_FOLDERS = LABYMOD_DIRECTORY.resolve("server-folders.json");
        public static final Path TOKENS = LABYMOD_DIRECTORY.resolve("tokens.json");
        public static final Path ADDONS_SCHEDULE_FOR_REMOVAL = LABYMOD_DIRECTORY.resolve(".asfr");
        public static final Path RESTART = LABYMOD_DIRECTORY.resolve(".restart");
        public static final Path ADDONS_INDEX = FILE_CACHE.resolve("index.json");
        public static final Path LABY_NET_INDEX = FILE_CACHE.resolve("server-groups.json");
        public static final String LABYMOD_DIRECTORY_PATH = LABYMOD_DIRECTORY.toString();
        public static final String UPDATER_PATH = LABYMOD_DIRECTORY_PATH + "/updater-%s.jar";
        public static final String QUICK_LAUNCHER_DIRECTORY = LABYMOD_DIRECTORY_PATH + "/quick-launcher/";
        public static final String QUICK_LAUNCHER_JAR_PATH = QUICK_LAUNCHER_DIRECTORY + "quick-launcher.jar";
        public static final String MOJANG_MAPPINGS_PATH = String.valueOf(MAPPINGS) + "/%s-mojang.proguard";
        public static final String MCP_MAPPINGS_PATH = String.valueOf(MAPPINGS) + "/%s-mcp.tsrg2";
        public static final String SEARGE_MAPPINGS_PATH = String.valueOf(MAPPINGS) + "/%s-searge.tsrg2";
        public static final String REMAP_JAR_PATH = String.valueOf(MAPPINGS) + "/%s-%s-remapped.jar";
        public static final Path LABYMOD_ASSETS = Paths.get("assets", new String[0]).resolve("labymod");
        public static final Path CACHE = LABYMOD_ASSETS.resolve("textures");
        public static final Path SCREENSHOT_META_CACHE = LABYMOD_ASSETS.resolve("screenshot_meta.bin");
        public static final Path SCREENSHOT_DIRECTORY = Paths.get("screenshots", new String[0]);

        private Files() {
        }

        public static Path fromArguments(String argument, Path fallback) {
            String path = System.getProperty(argument);
            if (path == null) {
                return fallback;
            }
            return Paths.get(path, new String[0]);
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/Constants$LegacyUrls.class */
    public static class LegacyUrls {
        public static final String BASE = "https://dl.labymod.net/";
        public static final String CUSTOM_TEXTURES = protocol("textures/%s/%s");
        public static final String SERVER_GROUPS = protocol("server_groups.json");
        public static final String PUBLIC_SERVERS = protocol("public_servers.json");
        public static final String ADVERTISEMENT_DATA = protocol("advertisement/entries.json");
        public static final String QUICK_LAUNCHER = protocol("latest/install/quicklauncher/quicklauncher.jar");

        private LegacyUrls() {
        }

        private static String protocol(String path) {
            return "https://dl.labymod.net/" + path;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/Constants$Urls.class */
    public static class Urls {
        public static final String WEB_BASE = "https://www.labymod.net/";
        public static final String WEB_SHOP = "https://www.labymod.net/shop";
        public static final String WEB_USER_ITEMS = "https://www.labymod.net/api/user-items";
        public static final String WEB_USER_ITEM_CHANGE = "https://www.labymod.net/api/change";
        public static final String WEB_USER_ITEM_CHANGE_BULK = "https://www.labymod.net/api/change/cosmetics";
        public static final String WEB_USER_COSMETIC_OPTIONS = "https://www.labymod.net/api/user-cosmetic-options";
        public static final String WEB_SHOP_PRODUCTS_IMAGE = "https://www.labymod.net/page/tpl/assets/images/shop/products/%s_0.png";
        public static final String NEO_BASE = "https://neo.labymod.net/";
        public static final String RELEASE_BASE = "https://releases.labymod.net/api/v1/";
        public static final String API_BASE = "https://next.api.labymod.net/client/";
        public static final String LABYNET_BASE = "https://laby.net/";
        public static final String TEXTURE_LABYNET_BASE = "https://texture.laby.net/";
        public static final String ISSUE_CERTIFICATE = "https://issue.labymod.net/certificate.php";
        public static final String LABYNET_PROFILE_NAME = "https://laby.net/@%s";
        public static final String LABYNET_SCREENSHOT_UPLOAD = "https://laby.net/api/v3/post";
        public static final String LABYNET_SKIN_PAGE = "https://laby.net/skin/%s";
        public static final String LABYNET_TEXTURE_SEARCH = "https://laby.net/api/texture/search?type=%s&order=%s";
        public static final String LABYNET_SURVEY = "https://laby.net/api/v2/survey/%s";
        public static final String LABYNET_SURVEY_SOURCE_CLIENT = "https://laby.net/api/v2/survey?source=CLIENT";
        public static final String LABYNET_USER_GET_TEXTURES = "https://laby.net/api/user/%s/get-textures";
        public static final String LABYNET_USER_SOCIALS = "https://laby.net/api/v2/user/%s/socials";
        public static final String LABYNET_SERVICE_WIDGET = "https://laby.net/api/v2/service/%s/widget";
        public static final String LABYNET_USER_DATA = "https://laby.net/api/v3/user/%s/userdata";
        public static final String LABYNET_USERS_BINARY = "https://resource.laby.net/users.bin";
        public static final String LABYNET_GROUPS = "https://laby.net/api/v3/groups";
        public static final String LABYNET_USER_GET_NAMES = "https://laby.net/api/v3/user/%s/names";
        public static final String LABYNET_USER_GET_UUID = "https://laby.net/api/v3/user/%s/uuid";
        public static final String LABYNET_HEAD = "https://laby.net/texture/profile/head/%s.png?size=32";
        public static final String LABYNET_SKIN = "https://laby.net/api/v3/user/%s/skin.png?strategy=skip";
        public static final String LABYNET_TEXTURE_DOWNLOAD = "https://texture.laby.net/%s.png";
        public static final String LABYNET_SKIN_PREVIEW = "https://laby.net/api/v3/render/skin/%s.png";
        public static final String LABYNET_SERVER_PING = "https://laby.net/api/v3/server/ping";
        public static final String LABYMOD_MANIFEST = releasesProtocol("manifest/%s/latest");
        public static final String LIBRARY_MANIFEST = releasesProtocol("libraries/%s");
        public static final String UPDATER_MANIFEST = releasesProtocol("updater/%s");
        public static final String UPDATER_DOWNLOAD = releasesProtocol("download/updater/%s/%s");
        public static final String ASSET_DOWNLOAD = releasesProtocol("download/assets/labymod4/%s/%s/%s/%s");
        public static final String ANNOUNCEMENTS = apiProtocol("announcements");
        public static final String ANNOUNCEMENTS_ICONS = protocol(neoProtocol("announcements"), "icons");
        public static final String INCENTIVES_DATA = neoProtocol("incentives/index.json");
        public static final String REMOTE_INCENTIVES_GEOMETRY = neoProtocol("incentives/%s/geo.json");
        public static final String REMOTE_INCENTIVES_ANIMATION = neoProtocol("incentives/%s/animation.json");
        public static final String REMOTE_INCENTIVES_TEXTURE = neoProtocol("incentives/%s/texture.png");
        public static final String EMOTES_DIRECTORY = neoProtocol("emotes");
        public static final String EMOTE_DATA = protocol(EMOTES_DIRECTORY, "index.json");
        public static final String EMOTE_ANIMATION = protocol(EMOTES_DIRECTORY, "%s/animation.json");
        public static final String EMOTE_GEOMETRY = protocol(EMOTES_DIRECTORY, "%s/geo.json");
        public static final String EMOTE_TEXTURE = protocol(EMOTES_DIRECTORY, "%s/texture.png");
        public static final String EMOTE_PLAYER_MODEL_STEVE = protocol(EMOTES_DIRECTORY, "%s/model_steve.json");
        public static final String EMOTE_PLAYER_MODEL_ALEX = protocol(EMOTES_DIRECTORY, "%s/model_alex.json");
        public static final String DISCORD_NATIVES = "https://dl-game-sdk.discordapp.net/3.2.1/discord_game_sdk.zip";

        private Urls() {
        }

        @Contract(pure = true)
        @NotNull
        private static String neoProtocol(String path) {
            return "https://neo.labymod.net/" + path;
        }

        @Contract(pure = true)
        @NotNull
        private static String releasesProtocol(String path) {
            return "https://releases.labymod.net/api/v1/" + path;
        }

        @Contract(pure = true)
        @NotNull
        private static String apiProtocol(String path) {
            return "https://next.api.labymod.net/client/" + path;
        }

        @Contract(pure = true)
        @NotNull
        private static String protocol(String parent, String path) {
            return parent + "/" + path;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/Constants$Domains.class */
    public static class Domains {
        public static final String LABY_DOMAIN = "laby.net";
        public static final String LAN_LABY_DOMAIN = "lan.laby.net";

        public static String createUserLanDomain(String username) {
            return username + ".lan.laby.net";
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/Constants$NamedThemeResource.class */
    @Deprecated(forRemoval = true, since = "4.2.7")
    public static class NamedThemeResource extends ThemeResourceRegistry {
        public static final ResourceLocation ACCOUNT_MANAGER = Textures.SpriteAccountManager.TEXTURE;
        public static final ResourceLocation COMMON = Textures.SpriteCommon.TEXTURE;
        public static final ResourceLocation BRANDS = texture("textures/activities/brands.png");
        public static final ResourceLocation LABYMOD = Textures.SpriteLabyMod.TEXTURE;
        public static final ResourceLocation CUSTOMIZATION = Textures.SpriteCustomization.TEXTURE;
        public static final ResourceLocation FLINT = Textures.SpriteFlint.TEXTURE;
        public static final ResourceLocation WHITE = Textures.WHITE;
        public static final ResourceLocation NOTIFICATION = Textures.NOTIFICATION;
        public static final ResourceLocation DEFAULT_SERVER_ICON = Textures.DEFAULT_SERVER_ICON;
        public static final ResourceLocation FLINT_DEFAULT_ICON = texture("textures/activities/flint/default_icon.png");
        public static final ResourceLocation SURVIVAL_INVENTORY_BACKGROUND = Textures.SURVIVAL_INVENTORY_BACKGROUND;
        public static final ResourceLocation SPRITE_VANILLA_WINDOW = Textures.POPUP_BACKGROUND;
        public static final ResourceLocation CREATIVE_INVENTORY_TAB_INVENTORY = Textures.CREATIVE_INVENTORY_TAB_INVENTORY;
        public static final ResourceLocation CREATIVE_INVENTORY_TAB_ITEM_SEARCH = Textures.CREATIVE_INVENTORY_TAB_ITEM_SEARCH;
        public static final ResourceLocation CREATIVE_INVENTORY_TAB_ITEMS = Textures.CREATIVE_INVENTORY_TAB_ITEMS;
        public static final ResourceLocation CREATIVE_INVENTORY_TABS = Textures.CREATIVE_INVENTORY_TABS;
        public static final ResourceLocation WIDGET_EDITOR = Textures.SpriteWidgetEditor.TEXTURE;
        public static final ResourceLocation HUD_PLACEHOLDER = Textures.SpriteHudPlaceholder.TEXTURE;
        public static final ResourceLocation LABYMOD_LOGO = Textures.LABYMOD_LOGO;
        public static final ResourceLocation TRANSPARENT = Textures.TRANSPARENT;
        public static final ResourceLocation STAR = Textures.STAR;

        private static ResourceLocation texture(String path) {
            return ThemeResourceLocation.FACTORY.createThemeTexture(path, 128, 128);
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/Constants$Resources.class */
    public static class Resources {
        public static final ResourceLocation SOUND_CHAT_MESSAGE = resource("misc.pop");
        public static final ResourceLocation SOUND_PLACE_MARKER = resource("marker.place_marker");
        public static final ResourceLocation SOUND_MARKER_NOTIFY = resource("marker.marker_notify");
        public static final ResourceLocation SOUND_HUDWIDGET_ALIGN = resource("hudwidget.align");
        public static final ResourceLocation SOUND_HUDWIDGET_ATTACH = resource("hudwidget.attach");
        public static final ResourceLocation SOUND_HUDWIDGET_TRASH = resource("hudwidget.trash");
        public static final ResourceLocation SOUND_UI_SWITCH_ON = resource("ui.switch.on");
        public static final ResourceLocation SOUND_UI_SWITCH_OFF = resource("ui.switch.off");
        public static final ResourceLocation SOUND_UI_BUTTON_CLICK = resource("ui.button.click");
        public static final ResourceLocation SOUND_UI_SERVER_MOVE = resource("ui.server.move");
        public static final ResourceLocation SOUND_SPRAY_CAN_SHAKE = resource("spray_can.shake");
        public static final ResourceLocation SOUND_SPRAY_CAN_SPRAY = resource("spray_can.spray");
        public static final ResourceLocation SOUND_SCREENSHOT_CAPTURE = resource("screenshot.capture");
        public static final ResourceLocation SOUND_LOOTBOX_OPEN = resource("lootbox.open");
        public static final ResourceLocation SOUND_LOOTBOX_COMMON = resource("lootbox.common");
        public static final ResourceLocation SOUND_LOOTBOX_SPECIAL = resource("lootbox.special");
        public static final ResourceLocation SOUND_LOOTBOX_LEGENDARY = resource("lootbox.legendary");
        public static final ResourceLocation MODEL_STEVE = resource("models/steve.geo.json");
        public static final ResourceLocation MODEL_ALEX = resource("models/alex.geo.json");
        public static final ResourceLocation MODEL_HEAD = resource("models/head.geo.json");
        public static final ResourceLocation CLOAK_AND_ELYTRA = resource("models/cloak_and_elytra.geo.json");
        public static final ResourceLocation CAVE_ENTITIES = resource("models/cave_entities.geo.json");
        public static final ResourceLocation MINECRAFT_LOGO = resource("models/minecraft_logo.geo.json");
        public static final ResourceLocation SYMBOLS = resource("data/symbols.txt");
        public static final ResourceLocation NORMAL_CAVE = resource("data/normal_cave.schem");
        public static final ResourceLocation WINTER_CAVE = resource("data/winter_cave.schem");
        public static final ResourceLocation CAVE_ENTITIES_ANIMATION = resource("animations/cave_entities.animation.json");
        public static final ResourceLocation MINECRAFT_LOGO_ANIMATION = resource("animations/minecraft_logo.animation.json");

        private Resources() {
        }

        private static ResourceLocation labyMod3Identifier(String path) {
            return resource("labymod3", path);
        }

        static ResourceLocation resource(String path) {
            return resource("labymod", path);
        }

        private static ResourceLocation minecraftTexture(String path) {
            return resource(Namespaces.MINECRAFT, path);
        }

        private static ResourceLocation resource(String namespace, String path) {
            return ResourceLocation.create(namespace, path);
        }
    }
}
