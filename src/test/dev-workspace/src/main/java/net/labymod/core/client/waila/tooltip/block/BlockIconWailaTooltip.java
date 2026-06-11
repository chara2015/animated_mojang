package net.labymod.core.client.waila.tooltip.block;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.minecraft.FireWidget;
import net.labymod.api.client.gui.screen.widget.widgets.minecraft.ItemStackWidget;
import net.labymod.api.client.waila.WailaUtil;
import net.labymod.api.client.waila.tooltip.BlockStateWailaTooltip;
import net.labymod.api.client.world.block.Block;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.core.client.gui.hud.hudwidget.survival.WailaHudWidget;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/waila/tooltip/block/BlockIconWailaTooltip.class */
public class BlockIconWailaTooltip extends BlockStateWailaTooltip {
    private final WailaHudWidget widget;

    public BlockIconWailaTooltip(WailaHudWidget widget) {
        super("icon");
        this.widget = widget;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.api.client.waila.tooltip.BlockStateWailaTooltip
    @Nullable
    protected Widget createWidget(BlockStateWailaTooltip.Context context) {
        if (!((WailaHudWidget.WailaConfiguration) this.widget.getConfig()).blockIcon().get().booleanValue()) {
            return null;
        }
        BlockState state = context.blockState();
        Block block = state.block();
        if (WailaUtil.isFire(block)) {
            return new FireWidget();
        }
        return new ItemStackWidget(context.itemStack());
    }
}
