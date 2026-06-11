package net.labymod.core.client.waila.tooltip.block.properties;

import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.waila.tooltip.BlockStateWailaTooltip;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.block.properties.BlockProperties;
import net.labymod.api.client.world.block.properties.BlockProperty;
import net.labymod.api.client.world.block.properties.ComparisonOperator;
import net.labymod.api.client.world.item.VanillaItems;
import net.labymod.core.client.gui.hud.hudwidget.survival.WailaHudWidget;
import net.labymod.core.client.waila.tooltip.block.ComponentBlockStateWailaTooltip;
import net.labymod.core.localization.keys.HudWidgetTranslationKeys;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/waila/tooltip/block/properties/BlockComparatorWailaTooltip.class */
public class BlockComparatorWailaTooltip extends ComponentBlockStateWailaTooltip {
    private final WailaHudWidget widget;
    private final BlockProperty delay;

    public BlockComparatorWailaTooltip(WailaHudWidget widget) {
        super("properties/comparator_mode");
        this.widget = widget;
        BlockProperties blockProperties = Laby.references().blockProperties();
        this.delay = blockProperties.getComparatorMode();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.core.client.waila.tooltip.block.ComponentBlockStateWailaTooltip
    @Nullable
    protected Component createComponent(BlockStateWailaTooltip.Context context) throws MatchException {
        ComparisonOperator value;
        if (!((WailaHudWidget.WailaConfiguration) this.widget.getConfig()).blockProperties().get().booleanValue()) {
            return null;
        }
        BlockState blockState = context.blockState();
        if (!blockState.block().is(VanillaItems.COMPARATOR) || (value = (ComparisonOperator) blockState.propertyHolder().getEnum(this.delay)) == null) {
            return null;
        }
        Component label = getComparatorModeLabel(value);
        return HudWidgetTranslationKeys.getWailaTooltipComparatorDescription(label);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    private Component getComparatorModeLabel(ComparisonOperator value) throws MatchException {
        switch (value) {
            case COMPARE:
                return HudWidgetTranslationKeys.getWailaTooltipComparatorModeCompare();
            case SUBTRACT:
                return HudWidgetTranslationKeys.getWailaTooltipComparatorModeSubtract();
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }
}
