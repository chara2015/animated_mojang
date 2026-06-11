package net.labymod.api.client.gui.screen.widget.widgets.input.itemstack;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Objects;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributeState;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.action.Selectable;
import net.labymod.api.client.world.item.ItemData;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.annotation.SettingElement;
import net.labymod.api.configuration.settings.annotation.SettingFactory;
import net.labymod.api.configuration.settings.annotation.SettingWidget;
import net.labymod.api.configuration.settings.widget.WidgetFactory;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/itemstack/ItemStackPickerWidget.class */
@AutoWidget
@SettingWidget
public class ItemStackPickerWidget extends SimpleWidget {
    private final Selectable<ItemData> selectable;
    private ItemData itemData;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/itemstack/ItemStackPickerWidget$ItemStackSetting.class */
    @SettingElement
    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ItemStackSetting {
    }

    public ItemStackPickerWidget(ItemData itemData, Selectable<ItemData> selectable) {
        this.itemData = itemData;
        this.selectable = selectable;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        Component itemName;
        super.initialize(parent);
        setPressable(() -> {
            ItemStack itemStackInHand;
            ClientPlayer player = this.labyAPI.minecraft().getClientPlayer();
            if (player == null || (itemStackInHand = player.getMainHandItemStack()) == null) {
                return;
            }
            ItemStack copyItemStack = itemStackInHand.copy();
            copyItemStack.setSize(1);
            this.itemData = copyItemStack.toItemData();
            this.selectable.select(this.itemData);
        });
        boolean enabled = false;
        ClientPlayer player = this.labyAPI.minecraft().getClientPlayer();
        if (player == null) {
            itemName = Component.translatable("labymod.ui.itemStackPicker.notInGame", new Component[0]).color(NamedTextColor.RED);
        } else {
            ItemStack itemStackInHand = player.getMainHandItemStack();
            if (itemStackInHand == null || itemStackInHand.isAir()) {
                itemName = Component.translatable("labymod.ui.itemStackPicker.noItemInHand", new Component[0]).color(NamedTextColor.RED);
            } else {
                itemName = itemStackInHand.getDisplayName().color(NamedTextColor.YELLOW);
                enabled = true;
            }
        }
        setHoverComponent(Component.translatable("labymod.ui.itemStackPicker.setItemInHand", itemName));
        setAttributeState(AttributeState.ENABLED, enabled);
        interactable().set(Boolean.valueOf(enabled));
    }

    public ItemStack itemStack() {
        return this.itemData.toItemStack();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public String getDefaultRendererName() {
        return "ItemStackPicker";
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/itemstack/ItemStackPickerWidget$Factory.class */
    @SettingFactory
    public static class Factory implements WidgetFactory<ItemStackSetting, Widget> {
        @Override // net.labymod.api.configuration.settings.widget.WidgetFactory
        public Widget[] create(Setting setting, ItemStackSetting annotation, SettingAccessor accessor) {
            ItemData itemData;
            Object object = accessor.get();
            if (object instanceof ItemStack) {
                ItemStack itemStack = (ItemStack) object;
                itemData = itemStack.toItemData();
            } else {
                itemData = (ItemData) object;
            }
            Objects.requireNonNull(accessor);
            ItemStackPickerWidget widget = new ItemStackPickerWidget(itemData, (v1) -> {
                r3.set(v1);
            });
            return new ItemStackPickerWidget[]{widget};
        }

        @Override // net.labymod.api.configuration.settings.widget.WidgetFactory
        public Class<?>[] types() {
            return new Class[]{ItemStack.class, ItemData.class};
        }
    }
}
