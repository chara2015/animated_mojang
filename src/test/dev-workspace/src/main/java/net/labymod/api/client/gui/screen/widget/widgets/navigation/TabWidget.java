package net.labymod.api.client.gui.screen.widget.widgets.navigation;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.theme.config.VanillaThemeConfigAccessor;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;
import net.labymod.api.client.gui.screen.widget.cursor.CursorTypes;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.navigation.tab.ComponentTab;
import net.labymod.api.client.gui.screen.widget.widgets.navigation.tab.Tab;
import net.labymod.api.client.sound.SoundType;
import net.labymod.api.loader.platform.PlatformEnvironment;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/navigation/TabWidget.class */
@AutoWidget
public class TabWidget extends SimpleWidget {
    private final Tab tab;

    public TabWidget(Tab tab) {
        this.tab = tab;
        setHoverCursor(CursorTypes.POINTING_HAND, true);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        VanillaThemeConfigAccessor config = (VanillaThemeConfigAccessor) Laby.labyAPI().themeService().getThemeConfig(VanillaThemeConfigAccessor.class);
        boolean freshUi = PlatformEnvironment.isFreshUI() && config != null && config.freshUI().get().booleanValue();
        if (freshUi) {
            addId("fresh-tab");
        }
        Tab tab = this.tab;
        if (tab instanceof ComponentTab) {
            ComponentTab componentTab = (ComponentTab) tab;
            addChild(ComponentWidget.component(componentTab.createComponent()));
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget
    protected SoundType getInteractionSound() {
        return SoundType.BUTTON_CLICK;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget
    protected boolean playInteractionSoundAfterHandling() {
        return true;
    }

    public Tab getTab() {
        return this.tab;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.Element
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (getParent() instanceof AbstractWidget) {
            ((AbstractWidget) getParent()).updateState(true);
        }
    }
}
