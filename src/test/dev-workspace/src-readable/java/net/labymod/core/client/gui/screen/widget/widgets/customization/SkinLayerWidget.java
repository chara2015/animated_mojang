package net.labymod.core.client.gui.screen.widget.widgets.customization;

import java.util.Locale;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.HorizontalAlignment;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.CheckBoxWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.options.MinecraftOptions;
import net.labymod.api.client.options.SkinLayer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/customization/SkinLayerWidget.class */
@AutoWidget
public class SkinLayerWidget extends HorizontalListWidget {
    private final SkinLayer layer;
    private final Icon icon;
    private final Component component;

    public SkinLayerWidget(SkinLayer layer) {
        this.layer = layer;
        this.icon = Icon.sprite32(Textures.SpriteCustomization.TEXTURE, layer.ordinal() % 4, layer.ordinal() / 4);
        this.component = Component.translatable(String.format(Locale.ROOT, "labymod.activity.customization.layers.layer.%s", layer.getLocaleId()), new Component[0]);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        if (this.icon != null) {
            addEntry(new IconWidget(this.icon)).alignment().set(HorizontalAlignment.LEFT);
        }
        if (this.component != null) {
            ComponentWidget componentWidget = ComponentWidget.component(this.component);
            addEntry(componentWidget).alignment().set(HorizontalAlignment.LEFT);
        }
        MinecraftOptions options = this.labyAPI.minecraft().options();
        boolean enabled = this.layer.isEnabled(options.getModelParts());
        boolean partlyEnabled = this.layer.isPartlyEnabled(options.getModelParts());
        CheckBoxWidget widget = new CheckBoxWidget();
        widget.setState(enabled ? CheckBoxWidget.State.CHECKED : partlyEnabled ? CheckBoxWidget.State.PARTLY : CheckBoxWidget.State.UNCHECKED);
        widget.addId("checkbox");
        widget.setActionListener(() -> {
            int value = options.getModelParts();
            if (partlyEnabled) {
                value |= this.layer.getBitMask();
            }
            options.setModelParts(value ^ this.layer.getBitMask());
            options.sendOptionsToServer();
            options.save();
            reInitialize();
            callActionListeners();
        });
        addEntry(widget).alignment().set(HorizontalAlignment.RIGHT);
    }
}
