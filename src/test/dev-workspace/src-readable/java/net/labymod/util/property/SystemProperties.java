package net.labymod.util.property;

import java.util.function.Function;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/util/property/SystemProperties.class */
public final class SystemProperties {
    private static final String DEBUGGING_PREFIX = "net.labymod.debugging";
    public static final BooleanSystemProperty RENDERDOC = (BooleanSystemProperty) debugProperty("renderdoc", BooleanSystemProperty::new);
    public static final BooleanSystemProperty ASM = (BooleanSystemProperty) debugProperty("asm", BooleanSystemProperty::new);
    public static final BooleanSystemProperty DISABLE_WIDGET_MODIFIER_CACHE = (BooleanSystemProperty) debugProperty("disable-widget-modifier-cache", BooleanSystemProperty::new);
    public static final BooleanSystemProperty UI = (BooleanSystemProperty) debugProperty("ui", BooleanSystemProperty::new);
    public static final BooleanSystemProperty OPENGL = (BooleanSystemProperty) debugProperty("opengl", BooleanSystemProperty::new);
    public static final BooleanSystemProperty OPENGL_CALL = (BooleanSystemProperty) debugProperty("opengl-call", BooleanSystemProperty::new);
    public static final BooleanSystemProperty KEYMAPPING = (BooleanSystemProperty) debugProperty("keymapping", BooleanSystemProperty::new);
    public static final BooleanSystemProperty RESOURCE_TRANSFORM = (BooleanSystemProperty) debugProperty("resource-transform", BooleanSystemProperty::new);
    public static final BooleanSystemProperty BUNDLED_OPTIFINE = (BooleanSystemProperty) debugProperty("bundled-optifine", BooleanSystemProperty::new);
    public static final FilesSystemProperty HOT_FILE_RELOADING_DIRECTORIES = (FilesSystemProperty) debugProperty("hot-file-reloading-directories", FilesSystemProperty::new);
    public static final BooleanSystemProperty DUMP_SPRITES = (BooleanSystemProperty) debugProperty("dump-sprites", BooleanSystemProperty::new);
    public static final BooleanSystemProperty MIXIN_AUDIT = (BooleanSystemProperty) debugProperty("mixin-audit", BooleanSystemProperty::new);
    public static final BooleanSystemProperty RENDER_DEVICE_VALIDATION = (BooleanSystemProperty) debugProperty("render-device-validation", BooleanSystemProperty::new);
    public static final BooleanSystemProperty RENDER_DEVICE_LEAKED_RESOURCES_DETECTION = (BooleanSystemProperty) debugProperty("render-device-leaked-resources-detection", BooleanSystemProperty::new);
    public static final BooleanSystemProperty RENDER_DEVICE_EXPORT_SHADERS = (BooleanSystemProperty) debugProperty("render-device-export-shaders", BooleanSystemProperty::new);
    public static final BooleanSystemProperty RENDER_DEVICE_DEBUG_CONTEXT = (BooleanSystemProperty) debugProperty("render-device-debug-context", BooleanSystemProperty::new);
    public static final BooleanSystemProperty RENDER_DEVICE_DEBUG_CONTEXT_SYNC = (BooleanSystemProperty) debugProperty("render-device-debug-context-sync", BooleanSystemProperty::new);
    public static final BooleanSystemProperty RENDER_DEVICE_USER_DEBUG = (BooleanSystemProperty) debugProperty("render-device-user-debug", BooleanSystemProperty::new);
    public static final BooleanSystemProperty RENDER_DEVICE_VERITY_OPENGL_EMULATION = (BooleanSystemProperty) debugProperty("render-device-verity-opengl-emulation", BooleanSystemProperty::new);
    public static final BooleanSystemProperty DEBUG_LOGGER = new BooleanSystemProperty("net.labymod.debug-logger");
    public static final BooleanSystemProperty ALLOW_OVERWOLF = new BooleanSystemProperty("net.labymod.allow-overwolf");
    public static final StringSystemProperty ADDON_RELEASE_CHANNEL = new StringSystemProperty("net.labymod.addons.release-channel");
    public static final BooleanSystemProperty DISABLE_INTEL_BLIT_FRAMEBUFFER_WORKAROUND = new BooleanSystemProperty("net.labymod.disable-intel-blit-framebuffer-workaround");
    public static final BooleanSystemProperty TEST_ENABLED = (BooleanSystemProperty) testProperty("enabled", BooleanSystemProperty::new);
    public static final BooleanSystemProperty TEST_EXIT_AFTER = (BooleanSystemProperty) testProperty("exitAfterTests", key -> {
        return new BooleanSystemProperty(key) { // from class: net.labymod.util.property.SystemProperties.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // net.labymod.util.property.BooleanSystemProperty, net.labymod.util.property.SystemProperty
            public Boolean get() {
                String value = System.getProperty(getKey());
                return Boolean.valueOf(value == null || Boolean.parseBoolean(value));
            }
        };
    });
    public static final LongSystemProperty TEST_GLOBAL_TIMEOUT = (LongSystemProperty) testProperty("globalTimeout", key -> {
        return new LongSystemProperty(key, 120000L);
    });
    public static final StringSystemProperty TEST_REPORT_DIR = (StringSystemProperty) testProperty("reportDir", key -> {
        return new StringSystemProperty(key) { // from class: net.labymod.util.property.SystemProperties.2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // net.labymod.util.property.StringSystemProperty, net.labymod.util.property.SystemProperty
            public String get() {
                String value = System.getProperty(getKey());
                return value != null ? value : "build/test-results/integrationTest";
            }
        };
    });
    public static final BooleanSystemProperty TEST_AUTO_JOIN_SERVER = (BooleanSystemProperty) testProperty("autoJoinServer", BooleanSystemProperty::new);

    private static <T extends SystemProperty<?>> T testProperty(String key, Function<String, T> factory) {
        return factory.apply("labymod.test." + key);
    }

    private static <T extends SystemProperty<?>> T debugProperty(String key, Function<String, T> factory) {
        return factory.apply("net.labymod.debugging." + key);
    }

    private SystemProperties() {
    }
}
