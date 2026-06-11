package net.labymod.core.client.gui.screen.activity.activities;

import java.util.Arrays;
import net.labymod.api.client.gfx.pipeline.post.processors.PostProcessors;
import net.labymod.api.client.gui.navigation.NavigationElement;
import net.labymod.api.client.gui.navigation.NavigationWrapper;
import net.labymod.api.client.gui.navigation.elements.AbstractNavigationElement;
import net.labymod.api.client.gui.navigation.elements.ScreenBaseNavigationElement;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.overlay.OverlayRegistry;
import net.labymod.api.client.gui.screen.activity.types.SimpleActivity;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.navigation.NavigationElementWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.ScreenRendererWidget;
import net.labymod.api.configuration.labymod.main.laby.ingame.MenuBlurConfig;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.labymod.config.SettingUpdateEvent;
import net.labymod.api.util.KeyValue;
import net.labymod.core.client.gui.navigation.DefaultNavigationRegistry;
import net.labymod.core.client.gui.screen.widget.widgets.navigation.NavigationListWidget;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/NavigationActivity.class */
@Link("extension/navigation.lss")
@AutoActivity
public class NavigationActivity extends SimpleActivity implements NavigationWrapper {
    private final DefaultNavigationRegistry navigationRegistry;
    private final OverlayRegistry overlayRegistry;
    private ScreenBaseNavigationElement<?> element;
    private ScreenBaseNavigationElement<?> fallbackElement;
    private final NavigationListWidget header;
    private final NavigationListWidget footer;
    private boolean playAnimations;

    public NavigationActivity(ScreenBaseNavigationElement<?> element) {
        if (element == null) {
            throw new NullPointerException("element is null");
        }
        this.navigationRegistry = (DefaultNavigationRegistry) this.labyAPI.navigationService();
        this.overlayRegistry = this.labyAPI.activityOverlayRegistry();
        this.element = element;
        if (NamedScreen.MAIN_MENU.isScreen(this.previousScreen)) {
            this.playAnimations = true;
        }
        if (element.isFallback()) {
            this.fallbackElement = element;
        }
        this.header = new NavigationListWidget(this);
        this.header.addId("navigation", "header");
        this.footer = new NavigationListWidget(this);
        this.footer.addId("navigation", "footer");
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        Activity overlay = this.overlayRegistry.toOverlay(this.element.getScreen());
        this.element.onUpdate(overlay == null ? this.element.getScreen() : overlay);
        ScreenRendererWidget rendererWidget = new ScreenRendererWidget();
        rendererWidget.addId("navigation-window");
        rendererWidget.displayScreen(this.element.getScreen());
        ((Document) this.document).addChild(rendererWidget);
        NavigationElement<?> activeElement = this.navigationRegistry.getActiveScreenElement();
        int i = 0;
        while (i < 2) {
            NavigationListWidget nav = i == 0 ? this.header : this.footer;
            for (KeyValue<NavigationElement<?>> keyElement : this.navigationRegistry.getElements()) {
                NavigationElement<?> element = keyElement.getValue();
                if (element instanceof AbstractNavigationElement) {
                    ((AbstractNavigationElement) element).setNavigation(this);
                }
                NavigationElementWidget widget = new NavigationElementWidget(this, element);
                widget.addId("nav-entry", element.getWidgetId());
                nav.addEntry(widget);
                if (element == activeElement) {
                    widget.setActive(true);
                }
            }
            document().addChild(nav);
            i++;
        }
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.ScreenInstance
    public void render(ScreenContext context) {
        PostProcessors.processMenuBlur(MenuBlurConfig.ScreenType.PAUSE_MENU, context);
        super.render(context);
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.SimpleActivity
    public boolean shouldRenderBackground() {
        ScreenInstance screen = this.element.getScreen();
        if (screen instanceof SimpleActivity) {
            SimpleActivity activity = (SimpleActivity) screen;
            return activity.shouldRenderBackground();
        }
        return false;
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.LabyScreen
    protected void postInitialize() {
        super.postInitialize();
        if (this.playAnimations) {
            this.playAnimations = false;
            Widget header = document().getChild("header");
            if (header != null) {
                header.playAnimation("header-fade-in-nav");
            }
            Widget footer = document().getChild("footer");
            if (footer != null) {
                footer.playAnimation("footer-fade-in-nav");
            }
        }
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity
    public boolean shouldDocumentHandleKey(Key key, InputType type) {
        return key != Key.ESCAPE || this.element.shouldDocumentHandleEscape();
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity
    public boolean displayPreviousScreen() {
        if (this.labyAPI.minecraft().isIngame()) {
            this.labyAPI.minecraft().minecraftWindow().displayScreenRaw(null);
            return true;
        }
        if (this.element == this.fallbackElement) {
            this.labyAPI.minecraft().minecraftWindow().displayScreenRaw(null);
            return true;
        }
        if (this.fallbackElement != null) {
            displayScreen(this.fallbackElement);
            return true;
        }
        return true;
    }

    @Override // net.labymod.api.client.gui.navigation.NavigationWrapper
    public void displayScreen(ScreenBaseNavigationElement<?> element) {
        if (element == null) {
            throw new NullPointerException("element is null");
        }
        if (element.isFallback()) {
            this.fallbackElement = element;
        }
        if (!element.equals(this.element)) {
            this.element.onCloseScreen();
            this.element = element;
            this.navigationRegistry.setActiveElement(element);
        }
        reload();
    }

    @Subscribe
    public void onSettingUpdate(SettingUpdateEvent event) {
        if (event.setting().getPath().startsWith("settings.appearance.navigation")) {
            reload();
        }
    }

    @Override // net.labymod.api.client.gui.navigation.NavigationWrapper
    public void updateElement(NavigationElement<?> element) {
        for (NavigationListWidget list : Arrays.asList(this.header, this.footer)) {
            for (T child : list.getChildren()) {
                Widget inner = child.childWidget();
                if (inner instanceof NavigationElementWidget) {
                    NavigationElementWidget widget = (NavigationElementWidget) inner;
                    if (widget.getElement() == element) {
                        child.reInitialize();
                    }
                }
            }
            list.updateBounds();
        }
    }

    @Override // net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.ScreenInstance
    @NotNull
    public ScreenInstance mostInnerScreenInstance() {
        return this.element.getScreen().mostInnerScreenInstance();
    }

    @Override // net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.ScreenInstance, net.labymod.api.client.gui.screen.ParentScreen
    @NotNull
    public Object mostInnerScreen() {
        return this.element.getScreen().mostInnerScreen();
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.ScreenInstance
    public void onOpenScreen() {
        PostProcessors.resetMenuBlur();
        super.onOpenScreen();
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.ScreenInstance
    public void onCloseScreen() {
        super.onCloseScreen();
        this.element.onCloseScreen();
    }
}
