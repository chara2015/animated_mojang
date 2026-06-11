package net.labymod.v26_1_2.client.gui.screen.widget.converter;

import com.google.common.collect.UnmodifiableIterator;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.converter.AbstractMinecraftWidgetConverter;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.client.gui.screen.widget.converter.WidgetReplacementStrategy;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.navigation.TabLayoutWidget;
import net.labymod.api.client.gui.screen.widget.widgets.navigation.TabWidget;
import net.labymod.v26_1_2.client.gui.TabNavigationBarAccessor;
import net.minecraft.client.gui.components.tabs.Tab;
import net.minecraft.client.gui.components.tabs.TabManager;
import net.minecraft.client.gui.components.tabs.TabNavigationBar;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/client/gui/screen/widget/converter/TabLayoutConverter.class */
public class TabLayoutConverter extends AbstractMinecraftWidgetConverter<TabNavigationBar, AbstractWidget<?>> {
    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public /* bridge */ /* synthetic */ void update(Object obj, AbstractWidget abstractWidget, @NotNull WidgetReplacementStrategy widgetReplacementStrategy) {
        update((TabNavigationBar) obj, (AbstractWidget<?>) abstractWidget, widgetReplacementStrategy);
    }

    public TabLayoutConverter() {
        super(MinecraftWidgetType.TAB_NAVIGATION);
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public AbstractWidget<?> createDefault(TabNavigationBar source) {
        TabNavigationBarAccessor accessor = (TabNavigationBarAccessor) source;
        TabManager tabManager = accessor.getTabManager();
        Tab currentTab = tabManager.getCurrentTab();
        TabLayoutWidget tabLayoutWidget = new TabLayoutWidget();
        tabLayoutWidget.addId("converted");
        UnmodifiableIterator it = accessor.getTabs().iterator();
        while (it.hasNext()) {
            Tab tab = (Tab) it.next();
            TabWidget tabWidget = new TabWidget(accessor.versionedTabOf(tab));
            tabWidget.setActive(currentTab == tab);
            tabWidget.setPressListener(() -> {
                if (tabManager.getCurrentTab() == tab) {
                    return false;
                }
                tabManager.setCurrentTab(tab, false);
                return true;
            });
            tabLayoutWidget.addEntry(tabWidget);
        }
        DivWidget divWidget = new DivWidget();
        divWidget.addChild(tabLayoutWidget);
        return divWidget;
    }

    public void update(TabNavigationBar source, AbstractWidget<?> destination, @NotNull WidgetReplacementStrategy replacementStrategy) {
        if (replacementStrategy.isSet(WidgetReplacementStrategy.ReplacementBehavior.KEEP_STATES)) {
            TabNavigationBarAccessor accessor = (TabNavigationBarAccessor) source;
            TabManager tabManager = accessor.getTabManager();
            TabNavigationBarAccessor.VersionedTab currentTab = accessor.versionedTabOf(tabManager.getCurrentTab());
            Widget menu = destination.getChild("menu");
            if (menu instanceof TabLayoutWidget) {
                TabLayoutWidget tabLayoutWidget = (TabLayoutWidget) menu;
                for (T child : tabLayoutWidget.getChildren()) {
                    Widget widget = child.childWidget();
                    if (widget instanceof TabWidget) {
                        TabWidget tabWidget = (TabWidget) widget;
                        tabWidget.setActive(tabWidget.getTab() == currentTab);
                    }
                }
            }
        }
        if (replacementStrategy.isSet(WidgetReplacementStrategy.ReplacementBehavior.KEEP_BOUNDS)) {
            ScreenRectangle rectangle = source.getRectangle();
            destination.bounds().setOuterPosition(rectangle.left(), rectangle.top(), COPY_MINECRAFT_BOUNDS);
            destination.bounds().setOuterSize(rectangle.width(), rectangle.height(), COPY_MINECRAFT_BOUNDS);
        }
    }
}
