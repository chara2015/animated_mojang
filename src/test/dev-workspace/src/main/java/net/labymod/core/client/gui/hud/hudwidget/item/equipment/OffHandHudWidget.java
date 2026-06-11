package net.labymod.core.client.gui.hud.hudwidget.item.equipment;

import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.gui.hud.binding.dropzone.NamedHudWidgetDropzones;
import net.labymod.api.client.gui.hud.hudwidget.item.EquipmentHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.item.EquipmentWidgetConfig;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.ClassicPvPConfig;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/item/equipment/OffHandHudWidget.class */
@SpriteSlot(x = 5, y = 7)
public class OffHandHudWidget extends EquipmentHudWidget<OffHandHudWidgetConfig> {
    private final ClassicPvPConfig classicPvPConfig;

    public OffHandHudWidget() {
        super("off_hand", OffHandHudWidgetConfig.class);
        this.classicPvPConfig = Laby.labyAPI().config().multiplayer().classicPvP();
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void initializePreConfigured(OffHandHudWidgetConfig config) {
        super.initializePreConfigured(config);
        config.setEnabled(true);
        config.setDropzone(NamedHudWidgetDropzones.ITEM_TOP_LEFT);
        config.displayMode().set(EquipmentWidgetConfig.DisplayMode.BAR);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.item.ItemHudWidget
    public Icon createPlaceholderIcon() {
        return Textures.SpriteHudPlaceholder.OFF_HAND;
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.item.EquipmentHudWidget
    protected ItemStack getItemStackToDisplay(ClientPlayer player) {
        ItemStack itemStack = player.getOffHandItemStack();
        if (this.classicPvPConfig.oldSword().get().booleanValue() && itemStack.isShield() && ((OffHandHudWidgetConfig) this.config).hideShieldWithOldSword().get().booleanValue()) {
            return null;
        }
        return itemStack;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/item/equipment/OffHandHudWidget$OffHandHudWidgetConfig.class */
    public static class OffHandHudWidgetConfig extends EquipmentWidgetConfig {

        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> hideShieldWithOldSword = new ConfigProperty<>(true);

        public ConfigProperty<Boolean> hideShieldWithOldSword() {
            return this.hideShieldWithOldSword;
        }
    }
}
