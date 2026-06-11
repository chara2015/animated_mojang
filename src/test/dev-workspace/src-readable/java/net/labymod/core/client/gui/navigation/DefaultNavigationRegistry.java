package net.labymod.core.client.gui.navigation;

import java.util.Objects;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.gui.navigation.NavigationElement;
import net.labymod.api.client.gui.navigation.NavigationRegistry;
import net.labymod.api.client.gui.navigation.elements.ScreenBaseNavigationElement;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.LabyScreenAccessor;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.event.EventBus;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.gui.screen.ScreenDisplayEvent;
import net.labymod.api.event.client.world.WorldLoadEvent;
import net.labymod.api.models.Implements;
import net.labymod.api.service.DefaultRegistry;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.client.gui.navigation.elements.AccountNavigationElement;
import net.labymod.core.client.gui.navigation.elements.LabyConnectNavigationElement;
import net.labymod.core.client.gui.navigation.elements.LabyModNavigationElement;
import net.labymod.core.client.gui.navigation.elements.MenuNavigationElement;
import net.labymod.core.client.gui.navigation.elements.MultiplayerNavigationElement;
import net.labymod.core.client.gui.navigation.elements.OptionsNavigationElement;
import net.labymod.core.client.gui.navigation.elements.SingleplayerNavigationElement;
import net.labymod.core.client.gui.navigation.elements.VersionNavigationElement;
import net.labymod.core.client.gui.screen.activity.activities.NavigationActivity;
import net.labymod.core.client.gui.screen.activity.activities.menu.MainMenuActivity;
import net.labymod.core.client.gui.screen.activity.activities.worldsharing.DashboardActivity;
import net.labymod.core.client.worldsharing.Worldsharing;
import net.labymod.core.event.client.lifecycle.GameInitializeEvent;
import net.labymod.core.main.LabyMod;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/navigation/DefaultNavigationRegistry.class */
@Singleton
@Implements(NavigationRegistry.class)
public class DefaultNavigationRegistry extends DefaultRegistry<NavigationElement<?>> implements NavigationRegistry {
    private ScreenBaseNavigationElement<?> activeScreenElement;
    private long timeLastActiveChanged = 0;
    private int previousActiveIndex = 0;
    private int activeIndex = 0;
    private long timePassedSinceActiveChanged = 0;

    @Inject
    public DefaultNavigationRegistry(EventBus eventBus) {
        eventBus.registerListener(this);
    }

    @Subscribe
    public void onGameInitialize(GameInitializeEvent event) {
        if (event.getLifecycle() != GameInitializeEvent.Lifecycle.POST_GAME_STARTED) {
            return;
        }
        try {
            register("menu", new MenuNavigationElement());
            register("singleplayer", new SingleplayerNavigationElement());
            register("multiplayer", new MultiplayerNavigationElement());
            register("labyconnect", new LabyConnectNavigationElement());
            register("labymod", new LabyModNavigationElement());
            register("options", new OptionsNavigationElement());
            register("account", new AccountNavigationElement());
            register("version", new VersionNavigationElement());
            DashboardActivity dashboardActivity = LabyMod.references().worldsharing().dashboardActivity();
            Objects.requireNonNull(dashboardActivity);
            register(Worldsharing.NAMESPACE, new DashboardActivity.NavElement(dashboardActivity, Worldsharing.NAMESPACE));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void onWorldLoad(WorldLoadEvent event) {
        setIngameMenuActive();
    }

    @Subscribe(-64)
    public void onScreenOpenPre(ScreenDisplayEvent event) {
        ScreenInstance screen = event.getScreen();
        if (screen == null) {
            return;
        }
        if (NamedScreen.INGAME_MENU.isScreen(screen) && event.getPreviousScreen() == null && this.activeScreenElement != null && !(this.activeScreenElement instanceof MenuNavigationElement)) {
            if (!Laby.labyAPI().config().appearance().navigation().rememberLastTab().get().booleanValue()) {
                setIngameMenuActive();
            }
            event.setScreen(this.activeScreenElement.getScreen());
        }
        LabyAPI labyAPI = Laby.labyAPI();
        boolean inGame = labyAPI.minecraft().isIngame();
        boolean isMainMenu = NamedScreen.MAIN_MENU.isScreen(screen) || (screen.mostInnerScreen() instanceof MainMenuActivity);
        boolean isPauseMenu = NamedScreen.INGAME_MENU.isScreen(screen);
        if (inGame) {
            if (!isMainMenu) {
                return;
            }
        } else if (!isPauseMenu) {
            return;
        }
        event.setScreen(null);
    }

    @Subscribe(64)
    public void onScreenOpenPost(ScreenDisplayEvent event) {
        ScreenInstance wrapper = event.getScreen();
        if (wrapper == null) {
            return;
        }
        ScreenInstance instance = wrapper.mostInnerScreenInstance();
        Object raw = instance.mostInnerScreen();
        for (NavigationElement<?> element : values()) {
            if (element instanceof ScreenBaseNavigationElement) {
                ScreenBaseNavigationElement<?> screenElement = (ScreenBaseNavigationElement) element;
                if (screenElement.isScreen(raw)) {
                    screenElement.onUpdate(wrapper.unwrap());
                    this.activeScreenElement = screenElement;
                    ScreenInstance prev = event.getPreviousScreen();
                    if (prev instanceof ScreenWrapper) {
                        Object versioned = ((ScreenWrapper) prev).getVersionedScreen();
                        if (versioned instanceof LabyScreenAccessor) {
                            LabyScreen labyScreen = ((LabyScreenAccessor) versioned).screen();
                            if (labyScreen instanceof NavigationActivity) {
                                NavigationActivity activity = (NavigationActivity) labyScreen;
                                activity.displayScreen(screenElement);
                                event.setScreen(activity);
                                return;
                            }
                        }
                    }
                    event.setScreen(new NavigationActivity(screenElement));
                    return;
                }
            }
        }
    }

    @Override // net.labymod.api.client.gui.navigation.NavigationRegistry
    public NavigationElement<?> getActiveScreenElement() {
        return this.activeScreenElement;
    }

    @Override // net.labymod.api.client.gui.navigation.NavigationRegistry
    public void setIngameMenuActive() {
        setActiveElement((ScreenBaseNavigationElement) getById("menu"));
    }

    @Override // net.labymod.api.client.gui.navigation.NavigationRegistry
    public ScreenInstance createNavigation(ScreenBaseNavigationElement<?> element) {
        return new NavigationActivity(element);
    }

    public void setActiveElement(ScreenBaseNavigationElement<?> element) {
        if (this.activeScreenElement == element) {
            return;
        }
        this.activeScreenElement = element;
        this.timeLastActiveChanged = TimeUtil.getMillis();
        this.timePassedSinceActiveChanged = 0L;
        this.previousActiveIndex = this.activeIndex;
        this.activeIndex = indexOf(element);
    }

    public int getActiveIndex() {
        return this.activeIndex;
    }

    public int getPreviousActiveIndex() {
        return this.previousActiveIndex;
    }

    public long getTimeLastActiveChanged() {
        return this.timeLastActiveChanged;
    }

    public long getTimePassedSinceActiveChanged() {
        return this.timePassedSinceActiveChanged;
    }

    public void setTimePassedSinceActiveChanged(long timePassedSinceActiveChanged) {
        this.timePassedSinceActiveChanged = timePassedSinceActiveChanged;
    }
}
