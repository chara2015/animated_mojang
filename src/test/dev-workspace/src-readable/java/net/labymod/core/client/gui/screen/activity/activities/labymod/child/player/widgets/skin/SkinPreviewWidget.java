package net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.skin;

import java.util.Locale;
import net.labymod.api.Constants;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributeState;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.context.ContextMenu;
import net.labymod.api.client.gui.screen.widget.context.ContextMenuEntry;
import net.labymod.api.client.gui.screen.widget.widgets.input.SimpleButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.labynet.models.textures.Skin;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.SkinActivity;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labymod/child/player/widgets/skin/SkinPreviewWidget.class */
@AutoWidget
public class SkinPreviewWidget extends AbstractWidget<Widget> {
    private final SkinActivity skinActivity;
    private final Skin skin;

    public SkinPreviewWidget(SkinActivity skinActivity, Skin skin) {
        this.skinActivity = skinActivity;
        this.skin = skin;
        this.lazy = true;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        IconWidget iconWidget = new IconWidget(this.skin.previewIcon());
        iconWidget.setCleanupOnDispose(true);
        addChild(iconWidget);
        SimpleButtonWidget applyButton = new SimpleButtonWidget(Component.translatable(this.skinActivity.getTranslationKeyPrefix() + "skin.preview", new Component[0]));
        applyButton.addId("button", "apply-button");
        applyButton.setAttributeState(AttributeState.ENABLED, true);
        applyButton.setPressable(() -> {
            this.skinActivity.setModelTexture(this.skin);
        });
        addChild(applyButton);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        if (mouseButton == MouseButton.RIGHT && !hasContextMenu()) {
            ContextMenu contextMenu = createContextMenu();
            contextMenu.addEntry(ContextMenuEntry.builder().text(Component.translatable(this.skinActivity.getTranslationKeyPrefix() + "skin.open", new Component[0])).clickHandler(entry -> {
                this.labyAPI.minecraft().chatExecutor().openUrl(String.format(Locale.ROOT, Constants.Urls.LABYNET_SKIN_PAGE, this.skin.getImageHash()));
                return true;
            }).build());
        }
        return super.mouseClicked(mouse, mouseButton);
    }
}
