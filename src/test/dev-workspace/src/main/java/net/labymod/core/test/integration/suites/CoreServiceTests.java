package net.labymod.core.test.integration.suites;

import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.Minecraft;
import net.labymod.api.event.EventBus;
import net.labymod.api.service.annotation.AutoService;
import net.labymod.api.test.IntegrationTest;
import net.labymod.api.test.IntegrationTestSuite;
import net.labymod.api.test.TestAssertion;
import net.labymod.api.test.TestContext;
import net.labymod.api.test.TestPhase;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/test/integration/suites/CoreServiceTests.class */
@AutoService(IntegrationTestSuite.class)
public class CoreServiceTests implements IntegrationTestSuite {
    @IntegrationTest(phase = TestPhase.AFTER_INIT, priority = -100, description = "Verify LabyAPI is available after initialization")
    public void testLabyAPIAvailable(TestContext context) {
        LabyAPI api = context.labyAPI();
        TestAssertion.assertNotNull(api, "LabyAPI should be available after AFTER_INIT phase");
    }

    @IntegrationTest(phase = TestPhase.AFTER_INIT, priority = -90, description = "Verify EventBus is available and functional")
    public void testEventBusAvailable(TestContext context) {
        EventBus eventBus = context.eventBus();
        TestAssertion.assertNotNull(eventBus, "EventBus should be available");
        TestAssertion.assertNotNull(eventBus.registry(), "EventBus registry should be available");
    }

    @IntegrationTest(phase = TestPhase.AFTER_INIT, priority = -80, description = "Verify Laby.references() returns valid storage")
    public void testReferenceStorageAvailable(TestContext context) {
        TestAssertion.assertNotNull(Laby.references(), "ReferenceStorage should be available");
    }

    @IntegrationTest(phase = TestPhase.AFTER_STARTUP, priority = 0, description = "Verify Minecraft instance is available after startup")
    public void testMinecraftAvailable(TestContext context) {
        Minecraft minecraft = context.minecraft();
        TestAssertion.assertNotNull(minecraft, "Minecraft should be available after AFTER_STARTUP");
    }

    @IntegrationTest(phase = TestPhase.AFTER_STARTUP, priority = 10, description = "Verify ThemeService is initialized")
    public void testThemeServiceAvailable(TestContext context) {
        LabyAPI api = context.labyAPI();
        TestAssertion.assertNotNull(api.themeService(), "ThemeService should be available");
    }

    @IntegrationTest(phase = TestPhase.AFTER_STARTUP, priority = 10, description = "Verify HudWidgetRegistry is initialized")
    public void testHudWidgetRegistryAvailable(TestContext context) {
        LabyAPI api = context.labyAPI();
        TestAssertion.assertNotNull(api.hudWidgetRegistry(), "HudWidgetRegistry should be available");
    }

    @IntegrationTest(phase = TestPhase.AFTER_STARTUP, priority = 10, description = "Verify NavigationRegistry is initialized")
    public void testNavigationRegistryAvailable(TestContext context) {
        LabyAPI api = context.labyAPI();
        TestAssertion.assertNotNull(api.navigationService(), "NavigationRegistry should be available");
    }

    @IntegrationTest(phase = TestPhase.AFTER_STARTUP, priority = 10, description = "Verify CommandService is initialized")
    public void testCommandServiceAvailable(TestContext context) {
        LabyAPI api = context.labyAPI();
        TestAssertion.assertNotNull(api.commandService(), "CommandService should be available");
    }

    @IntegrationTest(phase = TestPhase.AFTER_GAME_STARTED, priority = 0, description = "Verify LabyMod is fully initialized")
    public void testFullyInitialized(TestContext context) {
        LabyAPI api = context.labyAPI();
        TestAssertion.assertTrue(api.isFullyInitialized(), "LabyMod should be fully initialized after AFTER_GAME_STARTED");
    }

    @IntegrationTest(phase = TestPhase.AFTER_GAME_STARTED, priority = 10, description = "Verify version information is available")
    public void testVersionInfoAvailable(TestContext context) {
        LabyAPI api = context.labyAPI();
        TestAssertion.assertNotBlank(api.getVersion(), "Version should not be blank");
        Minecraft minecraft = context.minecraft();
        TestAssertion.assertNotBlank(minecraft.getVersion(), "Minecraft version should not be blank");
        context.log("LabyMod version: %s", api.getVersion());
        context.log("Minecraft version: %s", minecraft.getVersion());
    }

    @IntegrationTest(phase = TestPhase.IN_WORLD, priority = 0, description = "Verify player is available in world")
    public void testPlayerAvailableInWorld(TestContext context) {
        Minecraft minecraft = context.minecraft();
        TestAssertion.assertNotNull(minecraft, "Minecraft should be available");
        boolean playerAvailable = context.awaitCondition(() -> {
            return minecraft.getClientPlayer() != null;
        });
        TestAssertion.assertTrue(playerAvailable, "ClientPlayer should be available in world");
    }

    @IntegrationTest(phase = TestPhase.IN_WORLD, priority = 10, description = "Verify world is loaded")
    public void testWorldLoaded(TestContext context) {
        Minecraft minecraft = context.minecraft();
        Objects.requireNonNull(minecraft);
        boolean isIngame = context.awaitCondition(minecraft::isIngame);
        TestAssertion.assertTrue(isIngame, "Should be in-game after world entry");
        TestAssertion.assertNotNull(minecraft.clientWorld(), "ClientWorld should be available");
    }
}
