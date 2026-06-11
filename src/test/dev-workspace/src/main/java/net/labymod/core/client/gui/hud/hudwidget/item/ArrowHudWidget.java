package net.labymod.core.client.gui.hud.hudwidget.item;

import net.labymod.api.Laby;
import net.labymod.api.Namespaces;
import net.labymod.api.Textures;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.api.client.entity.player.Inventory;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.item.ItemHudWidget;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/item/ArrowHudWidget.class */
@SpriteSlot(x = 4, y = 1)
public class ArrowHudWidget extends ItemHudWidget<ArrowHudWidgetConfig> {
    private ItemStack arrowItemStack;

    public ArrowHudWidget() {
        super("arrow", ArrowHudWidgetConfig.class);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void load(ArrowHudWidgetConfig config) {
        super.load(config);
        this.arrowItemStack = Laby.references().itemStackFactory().create(ResourceLocation.create(Namespaces.MINECRAFT, "arrow"));
        updateItemStack(this.arrowItemStack, false);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void onTick(boolean isEditorContext) {
        ItemStack stack = this.arrowItemStack;
        int amount = isEditorContext ? 64 : 0;
        ClientPlayer player = this.labyAPI.minecraft().getClientPlayer();
        if (player != null) {
            ArrowHudWidgetConfig.DisplayMode displayMode = ((ArrowHudWidgetConfig) this.config).displayMode().get();
            switch (displayMode) {
                case ALL:
                    Inventory inventory = player.inventory();
                    amount = inventory.countAllArrows();
                    break;
                case NEXT:
                    stack = player.inventory().getNextArrows();
                    amount = stack == null ? 0 : stack.getSize();
                    break;
            }
        }
        updateItemStack(stack, isEditorContext);
        updateItemName(Component.text(amount + "x"), isEditorContext);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.item.ItemHudWidget, net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public boolean isVisibleInGame() {
        ClientPlayer player;
        Minecraft minecraft = this.labyAPI.minecraft();
        if (minecraft == null || (player = minecraft.getClientPlayer()) == null || player.gameMode() == GameMode.SPECTATOR) {
            return false;
        }
        ArrowHudWidgetConfig.DisplayMode displayMode = ((ArrowHudWidgetConfig) this.config).displayMode().get();
        switch (displayMode) {
            case ALL:
                Inventory inventory = player.inventory();
                if (inventory.countAllArrows() > 0) {
                }
                break;
            case NEXT:
                ItemStack stack = player.inventory().getNextArrows();
                if (stack == null || stack.getSize() <= 0) {
                }
                break;
        }
        return false;
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.item.ItemHudWidget
    public Icon createPlaceholderIcon() {
        return Textures.SpriteHudPlaceholder.ARROW;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/item/ArrowHudWidget$ArrowHudWidgetConfig.class */
    public static class ArrowHudWidgetConfig extends HudWidgetConfig {

        @DropdownWidget.DropdownSetting
        private final ConfigProperty<DisplayMode> displayMode = ConfigProperty.createEnum(DisplayMode.ALL);

        /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/item/ArrowHudWidget$ArrowHudWidgetConfig$DisplayMode.class */
        public enum DisplayMode {
            ALL,
            NEXT
        }

        public ConfigProperty<DisplayMode> displayMode() {
            return this.displayMode;
        }
    }
}
