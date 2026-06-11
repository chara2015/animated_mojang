package net.labymod.core.client.waila.tooltip.block.properties;

import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.waila.tooltip.BlockStateWailaTooltip;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.block.properties.BlockProperties;
import net.labymod.api.client.world.block.properties.BlockProperty;
import net.labymod.api.client.world.item.VanillaItems;
import net.labymod.core.client.gui.hud.hudwidget.survival.WailaHudWidget;
import net.labymod.core.client.waila.tooltip.block.ComponentBlockStateWailaTooltip;
import net.labymod.core.localization.keys.HudWidgetTranslationKeys;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/waila/tooltip/block/properties/BlockPoweredWailaTooltip.class */
public class BlockPoweredWailaTooltip extends ComponentBlockStateWailaTooltip {
    private final WailaHudWidget widget;
    private final BlockProperty powered;

    public BlockPoweredWailaTooltip(WailaHudWidget widget) {
        super("properties/powered");
        this.widget = widget;
        BlockProperties blockProperties = Laby.references().blockProperties();
        this.powered = blockProperties.getPowered();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.core.client.waila.tooltip.block.ComponentBlockStateWailaTooltip
    @Nullable
    protected Component createComponent(BlockStateWailaTooltip.Context context) {
        Boolean value;
        Component wailaTooltipStateOff;
        if (!((WailaHudWidget.WailaConfiguration) this.widget.getConfig()).blockProperties().get().booleanValue()) {
            return null;
        }
        BlockState blockState = context.blockState();
        if (!blockState.block().is(VanillaItems.LEVER) || (value = blockState.propertyHolder().getBoolean(this.powered)) == null) {
            return null;
        }
        if (value.booleanValue()) {
            wailaTooltipStateOff = HudWidgetTranslationKeys.getWailaTooltipStateOn();
        } else {
            wailaTooltipStateOff = HudWidgetTranslationKeys.getWailaTooltipStateOff();
        }
        Component state = wailaTooltipStateOff;
        return HudWidgetTranslationKeys.getWailaTooltipStateDescription(state);
    }
}
