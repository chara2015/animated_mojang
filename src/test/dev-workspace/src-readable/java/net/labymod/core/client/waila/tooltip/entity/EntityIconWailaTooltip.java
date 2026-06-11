package net.labymod.core.client.waila.tooltip.entity;

import java.util.function.Function;
import java.util.function.Supplier;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.entity.decoration.ItemFrame;
import net.labymod.api.client.entity.decoration.Painting;
import net.labymod.api.client.entity.item.FallingBlock;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.minecraft.ItemStackWidget;
import net.labymod.api.client.gui.screen.widget.widgets.minecraft.entity.EntityWidget;
import net.labymod.api.client.waila.tooltip.EntityWailaTooltip;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.world.item.ItemStackFactory;
import net.labymod.api.client.world.item.VanillaItems;
import net.labymod.core.client.gui.hud.hudwidget.survival.WailaHudWidget;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/waila/tooltip/entity/EntityIconWailaTooltip.class */
public class EntityIconWailaTooltip extends EntityWailaTooltip {
    private final WailaHudWidget widget;
    private final ItemStackFactory itemStackFactory;

    public EntityIconWailaTooltip(WailaHudWidget widget) {
        super("icon");
        this.widget = widget;
        this.itemStackFactory = Laby.references().itemStackFactory();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.api.client.waila.tooltip.EntityWailaTooltip
    @Nullable
    protected Widget createWidget(Entity entity) {
        WailaHudWidget.WailaConfiguration config = (WailaHudWidget.WailaConfiguration) this.widget.getConfig();
        if (entity instanceof ItemFrame) {
            ItemFrame itemFrame = (ItemFrame) entity;
            return mapToBlockIcon(config, itemFrame, this::createItemFrameWidget);
        }
        if (entity instanceof Painting) {
            return mapToBlockIcon(config, () -> {
                return new ItemStackWidget(this.itemStackFactory.create(VanillaItems.PAINTING)).addId("block-icon");
            });
        }
        if (entity instanceof FallingBlock) {
            FallingBlock fallingBlock = (FallingBlock) entity;
            return mapToBlockIcon(config, fallingBlock, block -> {
                try {
                    ItemStack itemStack = this.itemStackFactory.create(block.blockState());
                    return new ItemStackWidget(itemStack);
                } catch (Exception e) {
                    return null;
                }
            });
        }
        if (!config.entityIcon().get().booleanValue()) {
            return null;
        }
        return new EntityWidget(entity);
    }

    private <T extends Entity> Widget mapToBlockIcon(WailaHudWidget.WailaConfiguration config, T entity, Function<T, Widget> supplier) {
        if (!config.blockIcon().get().booleanValue()) {
            return null;
        }
        return supplier.apply(entity);
    }

    private Widget mapToBlockIcon(WailaHudWidget.WailaConfiguration config, Supplier<Widget> supplier) {
        return mapToBlockIcon(config, null, ignored -> {
            return (Widget) supplier.get();
        });
    }

    private Widget createItemFrameWidget(ItemFrame itemFrame) {
        ItemStack itemFrameStack = this.itemStackFactory.create(itemFrame.getFrameType());
        ItemStack item = itemFrame.getItem();
        DivWidget itemFrameContainer = new DivWidget();
        itemFrameContainer.addId("item-frame-container");
        ItemStackWidget itemFrameWidget = new ItemStackWidget(itemFrameStack);
        itemFrameWidget.addId("item-frame");
        itemFrameContainer.addChild(itemFrameWidget);
        if (item != null && !item.isAir()) {
            ItemStackWidget itemInsideFrameWidget = new ItemStackWidget(item);
            itemInsideFrameWidget.addId("item-inside-frame");
            itemFrameWidget.addChild(itemInsideFrameWidget);
        }
        return itemFrameContainer;
    }
}
