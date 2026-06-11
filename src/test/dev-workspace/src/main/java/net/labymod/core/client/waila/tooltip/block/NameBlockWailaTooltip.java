package net.labymod.core.client.waila.tooltip.block;

import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.waila.WailaUtil;
import net.labymod.api.client.waila.tooltip.BlockStateWailaTooltip;
import net.labymod.api.client.world.item.ItemStackFactory;
import net.labymod.core.localization.keys.HudWidgetTranslationKeys;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/waila/tooltip/block/NameBlockWailaTooltip.class */
public class NameBlockWailaTooltip extends ComponentBlockStateWailaTooltip {
    private final ItemStackFactory itemStackFactory;

    public NameBlockWailaTooltip() {
        super("name");
        this.itemStackFactory = Laby.references().itemStackFactory();
    }

    @Override // net.labymod.core.client.waila.tooltip.block.ComponentBlockStateWailaTooltip
    @Nullable
    protected Component createComponent(BlockStateWailaTooltip.Context context) {
        if (WailaUtil.isFire(context.blockState())) {
            return HudWidgetTranslationKeys.getWailaSpecialBlocksFire();
        }
        return context.itemStack().getDisplayName();
    }
}
