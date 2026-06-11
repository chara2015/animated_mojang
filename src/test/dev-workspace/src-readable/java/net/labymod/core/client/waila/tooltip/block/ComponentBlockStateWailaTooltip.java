package net.labymod.core.client.waila.tooltip.block;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.waila.tooltip.BlockStateWailaTooltip;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/waila/tooltip/block/ComponentBlockStateWailaTooltip.class */
public abstract class ComponentBlockStateWailaTooltip extends BlockStateWailaTooltip {
    @Nullable
    protected abstract Component createComponent(BlockStateWailaTooltip.Context context);

    protected ComponentBlockStateWailaTooltip(String id) {
        super(id);
    }

    @Override // net.labymod.api.client.waila.tooltip.BlockStateWailaTooltip
    @Nullable
    protected Widget createWidget(BlockStateWailaTooltip.Context context) {
        Component component = createComponent(context);
        if (component == null) {
            return null;
        }
        return ComponentWidget.component(component);
    }
}
