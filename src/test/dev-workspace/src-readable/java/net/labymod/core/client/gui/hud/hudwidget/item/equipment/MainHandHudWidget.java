package net.labymod.core.client.gui.hud.hudwidget.item.equipment;

import net.labymod.api.Textures;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.gui.hud.binding.dropzone.NamedHudWidgetDropzones;
import net.labymod.api.client.gui.hud.hudwidget.item.EquipmentHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.item.EquipmentWidgetConfig;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/item/equipment/MainHandHudWidget.class */
@SpriteSlot(x = 4, y = 7)
public class MainHandHudWidget extends EquipmentHudWidget<EquipmentWidgetConfig> {
    public MainHandHudWidget() {
        super("main_hand");
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.item.ItemHudWidget
    public Icon createPlaceholderIcon() {
        return Textures.SpriteHudPlaceholder.MAIN_AHND;
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void initializePreConfigured(EquipmentWidgetConfig config) {
        super.initializePreConfigured(config);
        config.setEnabled(true);
        config.setDropzone(NamedHudWidgetDropzones.ITEM_TOP_RIGHT);
        config.displayMode().set(EquipmentWidgetConfig.DisplayMode.BAR);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.item.EquipmentHudWidget
    protected ItemStack getItemStackToDisplay(ClientPlayer player) {
        return player.getMainHandItemStack();
    }
}
