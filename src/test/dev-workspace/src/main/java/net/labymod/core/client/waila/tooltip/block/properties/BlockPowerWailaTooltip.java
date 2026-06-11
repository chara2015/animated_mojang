package net.labymod.core.client.waila.tooltip.block.properties;

import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.waila.tooltip.BlockStateWailaTooltip;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.block.properties.BlockProperties;
import net.labymod.api.client.world.block.properties.IntegerBlockProperty;
import net.labymod.core.client.gui.hud.hudwidget.survival.WailaHudWidget;
import net.labymod.core.client.waila.tooltip.block.ComponentBlockStateWailaTooltip;
import net.labymod.core.localization.keys.HudWidgetTranslationKeys;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/waila/tooltip/block/properties/BlockPowerWailaTooltip.class */
public class BlockPowerWailaTooltip extends ComponentBlockStateWailaTooltip {
    private final WailaHudWidget widget;
    private final IntegerBlockProperty power;

    public BlockPowerWailaTooltip(WailaHudWidget widget) {
        super("properties/power");
        this.widget = widget;
        BlockProperties blockProperties = Laby.references().blockProperties();
        this.power = (IntegerBlockProperty) blockProperties.getPower();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.core.client.waila.tooltip.block.ComponentBlockStateWailaTooltip
    @Nullable
    protected Component createComponent(BlockStateWailaTooltip.Context context) {
        if (!((WailaHudWidget.WailaConfiguration) this.widget.getConfig()).blockProperties().get().booleanValue()) {
            return null;
        }
        BlockState state = context.blockState();
        Integer value = state.propertyHolder().getInt(this.power);
        if (value == null) {
            return null;
        }
        return HudWidgetTranslationKeys.getWailaTooltipPowerDescription(Component.text(value));
    }
}
