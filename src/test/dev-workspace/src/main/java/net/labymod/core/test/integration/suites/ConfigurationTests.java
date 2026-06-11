package net.labymod.core.test.integration.suites;

import net.labymod.api.LabyAPI;
import net.labymod.api.client.gfx.imgui.flag.ImGuiFlags;
import net.labymod.api.configuration.labymod.main.LabyConfig;
import net.labymod.api.configuration.settings.type.AbstractSettingRegistry;
import net.labymod.api.configuration.settings.widget.WidgetRegistry;
import net.labymod.api.service.annotation.AutoService;
import net.labymod.api.test.IntegrationTest;
import net.labymod.api.test.IntegrationTestSuite;
import net.labymod.api.test.TestAssertion;
import net.labymod.api.test.TestContext;
import net.labymod.api.test.TestPhase;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/test/integration/suites/ConfigurationTests.class */
@AutoService(IntegrationTestSuite.class)
public class ConfigurationTests implements IntegrationTestSuite {
    @IntegrationTest(phase = TestPhase.AFTER_STARTUP, priority = 0, description = "Verify LabyConfig is loaded")
    public void testLabyConfigLoaded(TestContext context) {
        LabyAPI api = context.labyAPI();
        LabyConfig config = api.config();
        TestAssertion.assertNotNull(config, "LabyConfig should be loaded");
    }

    @IntegrationTest(phase = TestPhase.AFTER_STARTUP, priority = 10, description = "Verify core setting registry exists")
    public void testCoreSettingRegistryExists(TestContext context) {
        LabyAPI api = context.labyAPI();
        AbstractSettingRegistry registry = api.coreSettingRegistry();
        TestAssertion.assertNotNull(registry, "Core setting registry should exist");
    }

    @IntegrationTest(phase = TestPhase.AFTER_STARTUP, priority = 10, description = "Verify WidgetRegistry is available")
    public void testWidgetRegistryAvailable(TestContext context) {
        LabyAPI api = context.labyAPI();
        WidgetRegistry registry = api.widgetRegistry();
        TestAssertion.assertNotNull(registry, "WidgetRegistry should be available");
    }

    @IntegrationTest(phase = TestPhase.AFTER_STARTUP, priority = ImGuiFlags.StyleColors.SliderGrabActive, description = "Verify config sub-sections are accessible")
    public void testConfigSubSectionsAccessible(TestContext context) {
        LabyAPI api = context.labyAPI();
        LabyConfig config = api.config();
        TestAssertion.assertNotNull(config.ingame(), "Ingame config section should exist");
        TestAssertion.assertNotNull(config.hotkeys(), "Hotkeys config section should exist");
        TestAssertion.assertNotNull(config.appearance(), "Appearance config section should exist");
        TestAssertion.assertNotNull(config.other(), "Other config section should exist");
        context.log("Successfully verified all main config sections are accessible");
    }

    @IntegrationTest(phase = TestPhase.AFTER_GAME_STARTED, priority = 0, description = "Verify widget config is loaded")
    public void testWidgetConfigLoaded(TestContext context) {
        LabyAPI api = context.labyAPI();
        TestAssertion.assertNotNull(api.widgetConfig(), "Widget config should be loaded after game started");
    }

    @IntegrationTest(phase = TestPhase.AFTER_GAME_STARTED, priority = 10, description = "Verify internationalization is initialized")
    public void testInternationalizationInitialized(TestContext context) {
        LabyAPI api = context.labyAPI();
        TestAssertion.assertNotNull(api.internationalization(), "Internationalization should be initialized");
        String translated = api.internationalization().getTranslation("labymod.misc.enabled", new Object[0]);
        TestAssertion.assertNotNull(translated, "Should be able to translate 'labymod.misc.enabled'");
        context.log("Translation test: 'labymod.misc.enabled' = '%s'", translated);
    }
}
