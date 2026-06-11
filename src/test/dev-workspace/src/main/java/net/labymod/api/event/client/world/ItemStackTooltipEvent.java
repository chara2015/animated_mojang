package net.labymod.api.event.client.world;

import java.util.List;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.Event;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/world/ItemStackTooltipEvent.class */
public class ItemStackTooltipEvent implements Event {
    private final ItemStack itemStack;
    private final List<Component> tooltipLines;
    private final TooltipType type;
    private final boolean creative;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/world/ItemStackTooltipEvent$TooltipType.class */
    public enum TooltipType {
        NORMAL,
        ADVANCED
    }

    public ItemStackTooltipEvent(@NotNull ItemStack itemStack, @NotNull List<Component> tooltipLines, @NotNull TooltipType type, boolean creative) {
        this.itemStack = itemStack;
        this.tooltipLines = tooltipLines;
        this.type = type;
        this.creative = creative;
    }

    @NotNull
    public ItemStack itemStack() {
        return this.itemStack;
    }

    @NotNull
    public List<Component> getTooltipLines() {
        return this.tooltipLines;
    }

    @NotNull
    public TooltipType type() {
        return this.type;
    }

    public boolean isCreative() {
        return this.creative;
    }
}
