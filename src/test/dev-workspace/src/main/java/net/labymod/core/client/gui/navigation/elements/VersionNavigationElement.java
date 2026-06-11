package net.labymod.core.client.gui.navigation.elements;

import java.util.UUID;
import net.labymod.api.BuildData;
import net.labymod.api.Laby;
import net.labymod.api.Namespaces;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.navigation.NavigationElement;
import net.labymod.api.client.gui.navigation.NavigationWrapper;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.user.GameUser;
import net.labymod.api.user.group.Group;
import net.labymod.api.util.color.format.ColorFormat;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/navigation/elements/VersionNavigationElement.class */
public class VersionNavigationElement implements NavigationElement<Widget> {
    @Override // net.labymod.api.client.gui.navigation.NavigationElement
    public String getWidgetId() {
        return "version";
    }

    @Override // net.labymod.api.client.gui.navigation.NavigationElement
    public Widget createWidget(NavigationWrapper wrapper) {
        Icon icon;
        HorizontalListWidget mainWidget = new HorizontalListWidget();
        UUID clientUniqueId = Laby.labyAPI().getUniqueId();
        GameUser user = Laby.references().gameUserService().gameUser(clientUniqueId);
        Group group = user.visibleGroup();
        boolean isDefaultGroup = group.isDefault();
        if (isDefaultGroup) {
            icon = Textures.SpriteLabyMod.DEFAULT_WOLF_HIGH_RES;
        } else {
            icon = Textures.SpriteLabyMod.WHITE_WOLF_HIGH_RES;
        }
        IconWidget icon2 = new IconWidget(icon);
        if (!isDefaultGroup) {
            icon2.color().set(Integer.valueOf(ColorFormat.ARGB32.pack(user.displayColor().getValue(), 255)));
        }
        mainWidget.addEntry(icon2);
        VerticalListWidget<ComponentWidget> version = (VerticalListWidget) new VerticalListWidget().addId("info");
        ComponentWidget labymod = ComponentWidget.text("LabyMod " + String.valueOf(BuildData.version()));
        labymod.setHoverComponent(Component.text(BuildData.getVersion()), 300.0f);
        labymod.addId("labymod");
        version.addChild(labymod);
        ComponentWidget minecraft = ComponentWidget.text("Minecraft " + Laby.labyAPI().minecraft().getVersion());
        minecraft.addId(Namespaces.MINECRAFT);
        version.addChild(minecraft);
        mainWidget.addEntry(version);
        return mainWidget;
    }
}
