package net.labymod.core.client.waila.tooltip.block;

import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.waila.tooltip.BlockStateWailaTooltip;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.core.client.gui.hud.hudwidget.survival.WailaHudWidget;
import net.labymod.core.client.waila.tool.DefaultToolTesterRegistry;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/waila/tooltip/block/BlockRequiredToolWailaTooltip.class */
public class BlockRequiredToolWailaTooltip extends ComponentBlockStateWailaTooltip {
    private final WailaHudWidget widget;
    private final DefaultToolTesterRegistry testerRegistry;

    public BlockRequiredToolWailaTooltip(WailaHudWidget widget) {
        super("required_tool");
        this.widget = widget;
        this.testerRegistry = (DefaultToolTesterRegistry) Laby.references().toolTesterRegistry();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.core.client.waila.tooltip.block.ComponentBlockStateWailaTooltip
    @Nullable
    protected Component createComponent(BlockStateWailaTooltip.Context context) {
        BlockState state = context.blockState();
        WailaHudWidget.WailaConfiguration config = (WailaHudWidget.WailaConfiguration) this.widget.getConfig();
        if (config.blockRequiredTool().get().booleanValue()) {
            if (!state.requiresMinToolForDrops() && config.blockRequiredToolForDrops().get().booleanValue()) {
                return null;
            }
            List<ItemStack> items = this.testerRegistry.test(state);
            if (items.isEmpty()) {
                return null;
            }
            TextComponent.Builder text = Component.text();
            text.append(Component.text(""));
            for (int index = 0; index < items.size(); index++) {
                text = text.append(items.get(index).getDisplayName());
                if (index < items.size() - 1) {
                    text = text.append(Component.newline());
                }
            }
            return text.build();
        }
        return null;
    }
}
