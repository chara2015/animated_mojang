package net.labymod.api.client.gui.screen.widget.widgets.layout;

import java.util.function.BiPredicate;
import java.util.function.Consumer;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.action.ListSession;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.ListWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/layout/TilesGridFeedWidget.class */
@AutoWidget
public class TilesGridFeedWidget<T extends Widget> extends TilesGridWidget<T> {
    private final BiPredicate<TilesGridFeedWidget<T>, Consumer<T>> refresh;
    private final LssProperty<Float> refreshRadius = new LssProperty<>(Float.valueOf(-1.0f));
    private boolean doRefresh = true;

    public TilesGridFeedWidget(BiPredicate<TilesGridFeedWidget<T>, Consumer<T>> refresh) {
        this.refresh = refresh;
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.layout.TilesGridWidget, net.labymod.api.client.gui.screen.widget.widgets.layout.list.ListWidget
    public void updateVisibility(ListWidget<?> list, Parent parent) {
        super.updateVisibility(list, parent);
        if (!this.doRefresh || !(parent instanceof ScrollWidget)) {
            return;
        }
        Bounds bounds = bounds();
        Bounds parentBounds = parent.bounds();
        ListSession<?> session = ((ScrollWidget) parent).session();
        float height = bounds.getHeight();
        if (height <= 0.0f) {
            return;
        }
        float radius = this.refreshRadius.isDefaultValue() ? getTileHeight() * 2.0f : this.refreshRadius.get().floatValue();
        if (session.getScrollPositionY() >= (height - parentBounds.getHeight()) - radius) {
            this.doRefresh = false;
            if (this.refresh.test(this, this::addTileInitialized)) {
                updateTiles();
                this.doRefresh = true;
            }
        }
    }

    public LssProperty<Float> refreshRadius() {
        return this.refreshRadius;
    }

    public void doRefresh(boolean doRefresh) {
        this.doRefresh = doRefresh;
        if (doRefresh) {
            updateVisibility(this, this.parent);
        }
    }
}
