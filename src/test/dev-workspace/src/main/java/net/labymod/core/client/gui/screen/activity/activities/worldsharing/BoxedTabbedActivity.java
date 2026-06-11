package net.labymod.core.client.gui.screen.activity.activities.worldsharing;

import java.util.ArrayList;
import java.util.List;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.types.SimpleActivity;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.navigation.TabWidget;
import net.labymod.api.client.gui.screen.widget.widgets.navigation.tab.Tab;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.ScreenRendererWidget;
import net.labymod.api.service.Registry;
import net.labymod.api.util.KeyValue;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.time.TimeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/worldsharing/BoxedTabbedActivity.class */
@AutoActivity
public abstract class BoxedTabbedActivity extends SimpleActivity implements Registry<Tab> {
    private final List<KeyValue<Tab>> elements = new ArrayList();
    private Tab activeTab;
    private ScreenInstance tempActiveScreen;
    protected HorizontalListWidget tabMenu;
    protected ScreenRendererWidget screenRenderer;
    protected ScreenRendererWidget prevScreenRenderer;
    private int animationProgress;
    private boolean animationDirectionRight;

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        this.tabMenu = new HorizontalListWidget();
        this.tabMenu.addId("tabs");
        for (Tab tab : values()) {
            TabWidget tabWidget = new TabWidget(tab);
            tabWidget.setPressListener(() -> {
                if (tab != this.activeTab) {
                    switchTab(tab, true);
                    return true;
                }
                return false;
            });
            this.tabMenu.addEntry(tabWidget);
        }
        this.prevScreenRenderer = new ScreenRendererWidget();
        this.prevScreenRenderer.addId("tabbed-activity");
        this.prevScreenRenderer.setVisible(false);
        this.prevScreenRenderer.setTicking(false);
        this.prevScreenRenderer.interactable().set(false);
        this.screenRenderer = new ScreenRendererWidget();
        this.screenRenderer.addId("main-content");
        this.screenRenderer.addPostDisplayListener(screen -> {
            Tab newTab = getTabByScreen(screen);
            if (newTab != this.activeTab && newTab != null) {
                updateActiveTab(newTab);
                this.activeTab = newTab;
            }
        });
        if (!this.elements.isEmpty()) {
            Tab target = this.activeTab;
            if (target == null) {
                target = values().get(0);
            }
            switchTab(target, true);
        }
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.ScreenInstance
    public void render(ScreenContext context) {
        if (this.prevScreenRenderer.isVisible()) {
            int iConvertDeltaToMilliseconds = (int) (this.animationProgress + TimeUtil.convertDeltaToMilliseconds(context.getTickDelta()));
            this.animationProgress = iConvertDeltaToMilliseconds;
            long timePassed = iConvertDeltaToMilliseconds;
            float progress = (float) CubicBezier.EASE_IN_OUT.curve(MathHelper.clamp(timePassed / 200.0f, 0.0f, 1.0f));
            this.prevScreenRenderer.opacity().set(Float.valueOf(1.0f - progress));
            this.screenRenderer.opacity().set(Float.valueOf(progress));
            this.prevScreenRenderer.translateX().set(Float.valueOf(progress * 50.0f * (this.animationDirectionRight ? -1 : 1)));
            this.screenRenderer.translateX().set(Float.valueOf((1.0f - progress) * 50.0f * (this.animationDirectionRight ? 1 : -1)));
        }
        super.render(context);
    }

    @Nullable
    public ScreenInstance switchTab(String id, boolean withTransition) {
        Tab tab = getById(id);
        if (tab == null) {
            return null;
        }
        return switchTab(tab, withTransition);
    }

    public <T extends ScreenInstance> T switchTab(Class<? extends T> cls, boolean z) {
        Tab tabByScreen = getTabByScreen(cls);
        if (tabByScreen == null) {
            return null;
        }
        return (T) switchTab(tabByScreen, z);
    }

    public ScreenInstance switchTab(@NotNull Tab tab, boolean withTransition) {
        Tab prevTab = this.activeTab;
        if (this.tabMenu == null) {
            this.activeTab = tab;
            this.tempActiveScreen = tab.provideScreen();
            return this.tempActiveScreen;
        }
        int i = 0;
        while (true) {
            if (i >= this.elements.size()) {
                break;
            }
            if (this.elements.get(i).getValue() != tab) {
                i++;
            } else {
                tab.setIndex(i);
                break;
            }
        }
        if (this.activeTab != null) {
            boolean transitionToRight = tab.getIndex() > this.activeTab.getIndex();
            this.activeTab.setTransitionToRight(transitionToRight);
            tab.setTransitionToRight(!transitionToRight);
        }
        this.activeTab = tab;
        boolean hasTransition = withTransition && this.labyAPI.themeService().currentTheme().metadata().getBoolean("transition");
        if (hasTransition) {
            this.prevScreenRenderer.setScreenFrom(this.screenRenderer);
        }
        ScreenInstance screen = this.tempActiveScreen != null ? this.tempActiveScreen : tab.provideScreen();
        this.tempActiveScreen = null;
        if (screen != this.screenRenderer.getScreen()) {
            this.screenRenderer.displayScreen(screen);
        }
        if (hasTransition && prevTab != tab) {
            handleTransition(prevTab, tab);
        }
        updateActiveTab(tab);
        return screen;
    }

    private void updateActiveTab(Tab activeTab) {
        for (T entry : this.tabMenu.getChildren()) {
            TabWidget tabWidget = (TabWidget) entry.childWidget();
            Tab tab = tabWidget.getTab();
            tabWidget.setActive(activeTab == tab);
            tabWidget.setVisible(!tab.isHidden());
            if (!tab.isHidden()) {
                tabWidget.reInitialize();
            }
        }
    }

    private void handleTransition(Tab tabFrom, Tab tabTo) {
        this.prevScreenRenderer.setVisible(true);
        this.animationDirectionRight = indexOf(tabFrom) < indexOf(tabTo);
        this.animationProgress = 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Tab getTabByScreen(ScreenInstance screen) {
        return getTabByScreen((Class<? extends ScreenInstance>) screen.getClass());
    }

    public Tab getTabByScreen(Class<? extends ScreenInstance> screen) {
        for (Tab tab : values()) {
            if (tab.isScreen(screen)) {
                return tab;
            }
        }
        return null;
    }

    public TabWidget getTabWidget(Tab tab) {
        if (this.tabMenu == null) {
            return null;
        }
        for (T entry : this.tabMenu.getChildren()) {
            TabWidget tabWidget = (TabWidget) entry.childWidget();
            if (tabWidget.getTab() == tab) {
                return tabWidget;
            }
        }
        return null;
    }

    @Nullable
    public Tab getActiveTab() {
        return this.activeTab;
    }

    @Nullable
    public String getActiveTabId() {
        return getId(this.activeTab);
    }

    @Override // net.labymod.api.service.Registry
    public List<KeyValue<Tab>> getElements() {
        return this.elements;
    }
}
