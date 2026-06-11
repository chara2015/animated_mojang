package net.labymod.api.generated.settings;

import java.util.List;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.settings.ActivitySettingWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.settings.AddonActivityWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.settings.CollectionResetWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.AdvancedSelectionWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.FileChooserWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.KeybindWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.MultiKeybindWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TagInputWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorPickerWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.itemstack.ItemStackPickerWidget;
import net.labymod.api.configuration.settings.widget.WidgetStorage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/generated/settings/DefaultWidgetStorage.class */
public final class DefaultWidgetStorage implements WidgetStorage {
    @Override // net.labymod.api.configuration.settings.widget.WidgetStorage
    public void store(List<Class<? extends Widget>> widgets) {
        widgets.add(SliderWidget.class);
        widgets.add(TextFieldWidget.class);
        widgets.add(KeybindWidget.class);
        widgets.add(SwitchWidget.class);
        widgets.add(MultiKeybindWidget.class);
        widgets.add(TagInputWidget.class);
        widgets.add(ColorPickerWidget.class);
        widgets.add(ItemStackPickerWidget.class);
        widgets.add(ButtonWidget.class);
        widgets.add(DropdownWidget.class);
        widgets.add(AdvancedSelectionWidget.class);
        widgets.add(FileChooserWidget.class);
        widgets.add(ActivitySettingWidget.class);
        widgets.add(AddonActivityWidget.class);
        widgets.add(CollectionResetWidget.class);
    }
}
