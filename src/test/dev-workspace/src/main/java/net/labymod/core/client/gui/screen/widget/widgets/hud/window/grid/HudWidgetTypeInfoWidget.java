package net.labymod.core.client.gui.screen.widget.widgets.hud.window.grid;

import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributeState;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.context.ContextMenu;
import net.labymod.api.client.gui.screen.widget.context.ContextMenuEntry;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.screen.widget.widgets.transform.InterpolateWidget;
import net.labymod.api.configuration.settings.SettingOverlayInfo;
import net.labymod.api.configuration.settings.type.SettingPermissionHolder;
import net.labymod.api.revision.Revision;
import net.labymod.api.user.permission.ClientPermission;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/hud/window/grid/HudWidgetTypeInfoWidget.class */
@AutoWidget
public class HudWidgetTypeInfoWidget extends InterpolateWidget {
    private final HudWidget<?> hudWidget;
    private final HudWidgetTilesGridWidget gridWidget;

    public HudWidgetTypeInfoWidget(HudWidget<?> hudWidget, HudWidgetTilesGridWidget gridWidget) {
        super(50.0f);
        this.hudWidget = hudWidget;
        this.gridWidget = gridWidget;
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.transform.InterpolateWidget, net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        ClientPermission permission;
        super.initialize(parent);
        SettingPermissionHolder permission2 = this.hudWidget.getPermission();
        if (permission2 != null) {
            permission = permission2.getPermission();
        } else {
            permission = null;
        }
        ClientPermission clientPermission = permission;
        boolean allowed = clientPermission == null || clientPermission.hasCurrentlyPermission();
        boolean enabled = this.hudWidget.isEnabled();
        setAttributeState(AttributeState.ENABLED, !enabled && allowed);
        if (!allowed) {
            SettingOverlayInfo info = clientPermission.overlayInfo();
            if (info.isActive() || info.isExclamationMarkAlwaysVisible()) {
                setHoverComponent(info.component());
            }
        }
        if (enabled && this.gridWidget.draggingWidget() != this) {
            removeId("available");
        } else {
            addId("available");
        }
        Icon icon = this.hudWidget.getIcon();
        IconWidget iconWidget = new IconWidget(icon == null ? Textures.SpriteCommon.QUESTION_MARK : icon);
        iconWidget.addId("icon");
        addChild(iconWidget);
        ComponentWidget componentWidget = ComponentWidget.component(this.hudWidget.displayName());
        componentWidget.addId("name");
        if (!allowed) {
            componentWidget.addId("disallowed");
        }
        addChild(componentWidget);
        Revision revision = this.hudWidget.getRevision();
        if (revision != null && revision.isRelevant()) {
            IconWidget newBadge = new IconWidget(Textures.SpriteCommon.NEW);
            newBadge.addId("new-badge");
            newBadge.setHoverComponent(Component.translatable("labymod.misc.introduced", new Component[0]).color(NamedTextColor.BLUE).argument(Component.text(revision.getDisplayName()).color(NamedTextColor.WHITE).decorate(TextDecoration.BOLD)));
            componentWidget.addId("new-name");
            addChild(newBadge);
        }
        ContextMenu contextMenu = createContextMenu();
        if (enabled) {
            contextMenu.addEntry(ContextMenuEntry.builder().text(Component.translatable("labymod.ui.button.settings", new Component[0])).simpleClickHandler(entry -> {
                this.gridWidget.editor().window().displaySettings(this.hudWidget);
            }).build());
            contextMenu.addEntry(ContextMenuEntry.builder().text(Component.translatable("labymod.ui.button.remove", new Component[0])).simpleClickHandler(entry2 -> {
                this.gridWidget.editor().renderer().destroyHudWidget(this.hudWidget, false);
            }).build());
            contextMenu.addEntry(ContextMenuEntry.builder().text(Component.translatable("labymod.ui.button.reset", new Component[0])).simpleClickHandler(entry3 -> {
                this.gridWidget.editor().renderer().resetHudWidget(this.hudWidget);
            }).build());
        }
        skipInterpolation();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget
    public boolean canHover() {
        return super.canHover();
    }

    public HudWidget<?> hudWidget() {
        return this.hudWidget;
    }
}
