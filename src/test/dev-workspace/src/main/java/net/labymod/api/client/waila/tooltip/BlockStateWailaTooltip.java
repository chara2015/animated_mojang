package net.labymod.api.client.waila.tooltip;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.waila.Waila;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.world.item.ItemStackFactory;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/waila/tooltip/BlockStateWailaTooltip.class */
public abstract class BlockStateWailaTooltip extends WailaTooltip {
    private final ItemStackFactory itemStackFactory;

    @Nullable
    protected abstract Widget createWidget(Context context);

    public BlockStateWailaTooltip(String id) {
        super("block/" + id);
        this.itemStackFactory = Laby.references().itemStackFactory();
    }

    @Override // net.labymod.api.client.waila.tooltip.WailaTooltip
    @Nullable
    public final Widget createWidget() {
        Waila<?> waila = getWaila();
        if (waila == null) {
            return null;
        }
        Object value = waila.getValue();
        if (!(value instanceof BlockState)) {
            return null;
        }
        BlockState blockState = (BlockState) value;
        if (blockState.block().isAir()) {
            return null;
        }
        ItemStack itemStack = this.itemStackFactory.create(blockState);
        if (itemStack.isAir()) {
            return null;
        }
        return createWidget(new Context(blockState, itemStack));
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/waila/tooltip/BlockStateWailaTooltip$Context.class */
    public static final class Context extends Record {
        private final BlockState blockState;
        private final ItemStack itemStack;

        public Context(BlockState blockState, ItemStack itemStack) {
            this.blockState = blockState;
            this.itemStack = itemStack;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Context.class), Context.class, "blockState;itemStack", "FIELD:Lnet/labymod/api/client/waila/tooltip/BlockStateWailaTooltip$Context;->blockState:Lnet/labymod/api/client/world/block/BlockState;", "FIELD:Lnet/labymod/api/client/waila/tooltip/BlockStateWailaTooltip$Context;->itemStack:Lnet/labymod/api/client/world/item/ItemStack;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Context.class), Context.class, "blockState;itemStack", "FIELD:Lnet/labymod/api/client/waila/tooltip/BlockStateWailaTooltip$Context;->blockState:Lnet/labymod/api/client/world/block/BlockState;", "FIELD:Lnet/labymod/api/client/waila/tooltip/BlockStateWailaTooltip$Context;->itemStack:Lnet/labymod/api/client/world/item/ItemStack;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Context.class, Object.class), Context.class, "blockState;itemStack", "FIELD:Lnet/labymod/api/client/waila/tooltip/BlockStateWailaTooltip$Context;->blockState:Lnet/labymod/api/client/world/block/BlockState;", "FIELD:Lnet/labymod/api/client/waila/tooltip/BlockStateWailaTooltip$Context;->itemStack:Lnet/labymod/api/client/world/item/ItemStack;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public BlockState blockState() {
            return this.blockState;
        }

        public ItemStack itemStack() {
            return this.itemStack;
        }
    }
}
