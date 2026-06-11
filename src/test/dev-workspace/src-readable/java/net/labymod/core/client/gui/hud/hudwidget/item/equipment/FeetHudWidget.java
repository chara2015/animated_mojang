package net.labymod.core.client.gui.hud.hudwidget.item.equipment;

import net.labymod.api.Textures;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.gui.hud.binding.dropzone.NamedHudWidgetDropzones;
import net.labymod.api.client.gui.hud.hudwidget.item.EquipmentHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.item.EquipmentWidgetConfig;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/item/equipment/FeetHudWidget.class */
@SpriteSlot(x = 3, y = 7)
public class FeetHudWidget extends EquipmentHudWidget<EquipmentWidgetConfig> {
    public FeetHudWidget() {
        super("feet");
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.item.ItemHudWidget
    public Icon createPlaceholderIcon() {
        return Textures.SpriteHudPlaceholder.BOOTS;
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void initializePreConfigured(EquipmentWidgetConfig config) {
        super.initializePreConfigured(config);
        config.setEnabled(true);
        config.setDropzone(NamedHudWidgetDropzones.ITEM_BOTTOM_RIGHT);
        config.displayMode().set(EquipmentWidgetConfig.DisplayMode.BAR);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.item.EquipmentHudWidget
    protected ItemStack getItemStackToDisplay(ClientPlayer player) {
        return player.getEquipmentItemStack(LivingEntity.EquipmentSpot.FEET);
    }
}
