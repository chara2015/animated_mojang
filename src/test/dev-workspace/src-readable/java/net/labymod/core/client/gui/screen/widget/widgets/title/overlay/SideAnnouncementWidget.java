package net.labymod.core.client.gui.screen.widget.widgets.title.overlay;

import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.core.main.advertisement.model.Announcement;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/title/overlay/SideAnnouncementWidget.class */
@AutoWidget
public class SideAnnouncementWidget extends HorizontalListWidget {
    private final Announcement announcement;

    public SideAnnouncementWidget(Announcement announcement) {
        this.announcement = announcement;
        addId("side-announcement-entry");
        String color = announcement.color();
        if (color != null) {
            setVariable("--announcement-color", color);
        }
        String colorHover = announcement.colorHover();
        if (colorHover != null) {
            setVariable("--announcement-hover-color", announcement.colorHover());
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        Icon iconUrl;
        super.initialize(parent);
        String title = this.announcement.title();
        if (title == null) {
            return;
        }
        String iconUrl2 = this.announcement.getIconUrl();
        if (iconUrl2 == null) {
            iconUrl = Textures.SpriteLabyMod.DEFAULT_WOLF_SHARP;
        } else {
            iconUrl = Icon.url(iconUrl2);
        }
        IconWidget iconWidget = new IconWidget(iconUrl);
        addEntry(iconWidget);
        TextComponent component = Component.text(title);
        addEntry(ComponentWidget.component(component));
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        String url = this.announcement.url();
        if (isHovered() && url != null) {
            this.labyAPI.minecraft().chatExecutor().openUrlOrJoinServer(url);
        }
        return super.mouseClicked(mouse, mouseButton);
    }
}
