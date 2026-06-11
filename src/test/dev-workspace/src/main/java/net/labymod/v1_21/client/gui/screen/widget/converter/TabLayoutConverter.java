package net.labymod.v1_21.client.gui.screen.widget.converter;

import com.google.common.collect.UnmodifiableIterator;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.converter.AbstractMinecraftWidgetConverter;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.client.gui.screen.widget.converter.WidgetReplacementStrategy;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.navigation.TabLayoutWidget;
import net.labymod.api.client.gui.screen.widget.widgets.navigation.TabWidget;
import net.labymod.v1_21.client.gui.TabNavigationBarAccessor;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21/client/gui/screen/widget/converter/TabLayoutConverter.class */
public class TabLayoutConverter extends AbstractMinecraftWidgetConverter<fkq, AbstractWidget<?>> {
    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public /* bridge */ /* synthetic */ void update(Object obj, AbstractWidget abstractWidget, @NotNull WidgetReplacementStrategy widgetReplacementStrategy) {
        update((fkq) obj, (AbstractWidget<?>) abstractWidget, widgetReplacementStrategy);
    }

    public TabLayoutConverter() {
        super(MinecraftWidgetType.TAB_NAVIGATION);
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public AbstractWidget<?> createDefault(fkq source) {
        TabNavigationBarAccessor accessor = (TabNavigationBarAccessor) source;
        fkp tabManager = accessor.getTabManager();
        fko currentTab = tabManager.a();
        TabLayoutWidget tabLayoutWidget = new TabLayoutWidget();
        tabLayoutWidget.addId("converted");
        UnmodifiableIterator it = accessor.getTabs().iterator();
        while (it.hasNext()) {
            fko tab = (fko) it.next();
            TabWidget tabWidget = new TabWidget(accessor.versionedTabOf(tab));
            tabWidget.setActive(currentTab == tab);
            tabWidget.setPressListener(() -> {
                if (tabManager.a() == tab) {
                    return false;
                }
                tabManager.a(tab, false);
                return true;
            });
            tabLayoutWidget.addEntry(tabWidget);
        }
        DivWidget divWidget = new DivWidget();
        divWidget.addChild(tabLayoutWidget);
        return divWidget;
    }

    public void update(fkq source, AbstractWidget<?> destination, @NotNull WidgetReplacementStrategy replacementStrategy) {
        if (replacementStrategy.isSet(WidgetReplacementStrategy.ReplacementBehavior.KEEP_STATES)) {
            TabNavigationBarAccessor accessor = (TabNavigationBarAccessor) source;
            fkp tabManager = accessor.getTabManager();
            TabNavigationBarAccessor.VersionedTab currentTab = accessor.versionedTabOf(tabManager.a());
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
            fms rectangle = source.H();
            destination.bounds().setOuterPosition(rectangle.d(), rectangle.b(), COPY_MINECRAFT_BOUNDS);
            destination.bounds().setOuterSize(rectangle.g(), rectangle.h(), COPY_MINECRAFT_BOUNDS);
        }
    }
}
