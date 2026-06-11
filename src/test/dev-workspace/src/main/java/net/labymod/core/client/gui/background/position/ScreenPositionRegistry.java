package net.labymod.core.client.gui.background.position;

import java.util.ArrayList;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.api.client.gui.screen.game.GameScreenRegistry;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.gui.screen.ActivityOpenEvent;
import net.labymod.api.event.client.gui.screen.ScreenOpenEvent;
import net.labymod.api.event.client.network.server.ServerDisconnectEvent;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.client.gui.background.DynamicBackgroundController;
import net.labymod.core.client.gui.screen.activity.activities.account.AccountManagerActivity;
import net.labymod.core.client.gui.screen.activity.activities.labyconnect.LabyConnectActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.AddonsActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.PlayerActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.SettingsActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.ShopActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.WidgetsEditorActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.screenshots.ScreenshotBrowserActivity;
import net.labymod.core.client.gui.screen.activity.activities.menu.MainMenuActivity;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.child.FriendsServerListActivity;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.child.PrivateServerListActivity;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.child.PublicServerListActivity;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.directconnect.DirectConnectActivity;
import net.labymod.core.client.gui.screen.activity.activities.singleplayer.SingleplayerOverlay;
import net.labymod.core.util.camera.CinematicCamera;
import net.labymod.core.util.camera.spline.position.Location;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/background/position/ScreenPositionRegistry.class */
public class ScreenPositionRegistry {
    public static final CubicBezier DEFAULT_SCREEN_SWITCH_CURVE = new CubicBezier(0.2d, 0.32d, 0.2d, 1.0d);
    public static final int DEFAULT_SCREEN_SWITCH_DURATION = 500;
    private final List<PositionTransition> screenPositions = new ArrayList();
    private final CinematicCamera camera;
    private long timeLastDynamicDurationForce;
    private boolean forceDynamicDuration;
    private long dynamicDuration;

    public ScreenPositionRegistry(CinematicCamera camera) {
        this.camera = camera;
        Laby.labyAPI().eventBus().registerListener(this);
    }

    public void register() {
        this.screenPositions.clear();
        register(MainMenuActivity.class, DynamicBackgroundController.POS_TITLE_SCREEN);
        register(SingleplayerOverlay.class, 23.0f, 17.0f, 15.0f, -31.0f, 7.0f, 5.0f);
        register(PrivateServerListActivity.class, 23.0f, 13.0f, 15.0f, -31.0f, -13.0f, 5.0f);
        register(FriendsServerListActivity.class, 22.5f, 13.0f, 15.5f, -31.0f, -13.0f, 5.0f);
        register(PublicServerListActivity.class, 22.0f, 13.0f, 16.0f, -31.0f, -13.0f, 5.0f);
        register(DirectConnectActivity.class, 23.0f, 13.0f, 18.0f, -21.0f, -13.0f, 8.0f);
        register(NamedScreen.CONNECTING, DynamicBackgroundController.POS_OPENER_START, 3000L);
        register(NamedScreen.DISCONNECTED, 23.0f, 13.0f, 18.0f, -21.0f, -13.0f, 8.0f, 3000L);
        register(LabyConnectActivity.class, 23.0f, 13.0f, 15.0f, -25.0f, -13.0f, 1.0f);
        register(SettingsActivity.class, 23.0f, 13.0f, 15.0f, -20.0f, -13.0f, -1.0f);
        register(AddonsActivity.class, 23.0f, 13.0f, 20.0f, -10.0f, -13.0f, 1.0f);
        register(WidgetsEditorActivity.class, 23.0f, 13.0f, 30.0f, -3.0f, -13.0f, 1.0f);
        register(PlayerActivity.class, DynamicBackgroundController.CUSTOMIZATION_PLAYER_CAMERA);
        register(ShopActivity.class, DynamicBackgroundController.SHOP_PLAYER_CAMERA);
        register(ResourceLocation.create("labymod", "shop/leg_focus"), 21.5f, 13.0f, 39.2f, 45.0f, 10.0f, 0.0f);
        register(NamedScreen.OPTIONS, 23.0f, 13.0f, 15.0f, -15.0f, -13.0f, -5.0f);
        register(AccountManagerActivity.class, 18.0f, 13.0f, 18.0f, 10.0f, 0.0f, 1.0f);
        register(ScreenshotBrowserActivity.class, 22.0f, 13.0f, 15.0f, 30.0f, 0.0f, -5.0f);
    }

    @Subscribe
    public void onScreenOpen(ScreenOpenEvent event) {
        PositionTransition transition;
        GameScreen gameScreen = GameScreenRegistry.from(event.getScreen());
        if (gameScreen != null && (transition = findPositionTransition(gameScreen)) != null) {
            executeTransition(transition);
        }
    }

    @Subscribe
    public void onDisplayActivity(ActivityOpenEvent event) {
        PositionTransition transition = findPositionTransition(event.activity().getClass());
        if (transition != null) {
            executeTransition(transition);
        }
    }

    @Subscribe
    public void onServerDisconnect(ServerDisconnectEvent event) {
        this.timeLastDynamicDurationForce = TimeUtil.getMillis();
        this.dynamicDuration = 3000L;
    }

    public void executeTransition(ResourceLocation identifier) {
        PositionTransition transition = findPositionTransition(identifier);
        if (transition == null) {
            return;
        }
        executeTransition(transition);
    }

    public void executeTransition(PositionTransition transition) {
        long duration;
        long timePassedSinceLastDisconnect = TimeUtil.getMillis() - this.timeLastDynamicDurationForce;
        boolean useDynamicDuration = this.forceDynamicDuration || timePassedSinceLastDisconnect < 1000;
        CinematicCamera cinematicCamera = this.camera;
        if (useDynamicDuration) {
            duration = Math.max(this.dynamicDuration, transition.getDuration());
        } else {
            duration = transition.getDuration();
        }
        cinematicCamera.moveTo(duration, transition.curve(), transition.position());
        if (this.forceDynamicDuration) {
            this.timeLastDynamicDurationForce = TimeUtil.getMillis();
            this.forceDynamicDuration = false;
        }
        if (transition.getDuration() >= 1000) {
            this.forceDynamicDuration = true;
            this.dynamicDuration = transition.getDuration();
        }
    }

    @Nullable
    private PositionTransition findPositionTransition(Object screen) {
        for (PositionTransition screenPosition : this.screenPositions) {
            if (screenPosition.is(screen)) {
                return screenPosition;
            }
        }
        return null;
    }

    private void register(Object identifier, float x, float y, float z, float yaw, float pitch, float roll) {
        register(identifier, new Location(x, y, z, yaw, pitch, roll));
    }

    private void register(Object identifier, float x, float y, float z, float yaw, float pitch, float roll, long duration) {
        register(identifier, new Location(x, y, z, yaw, pitch, roll), duration);
    }

    private void register(Object identifier, Location position) {
        register(identifier, position, 500L);
    }

    private void register(Object identifier, Location position, long duration) {
        PositionTransition transition;
        if (identifier instanceof ResourceLocation) {
            ResourceLocation location = (ResourceLocation) identifier;
            transition = new ResourceLocationPositionTransition(position, DEFAULT_SCREEN_SWITCH_CURVE, duration, location);
        } else {
            transition = new ScreenPositionTransition(position, DEFAULT_SCREEN_SWITCH_CURVE, duration, identifier);
        }
        this.screenPositions.add(transition);
    }
}
