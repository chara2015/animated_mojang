package net.labymod.api.client.gui.screen.state;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.labymod.api.client.gui.screen.state.LayerSpatialHash;
import net.labymod.api.client.gui.screen.state.states.commands.GuiClearComponent;
import net.labymod.api.client.gui.screen.state.states.commands.GuiCommandComponent;
import net.labymod.api.util.bounds.Rectangle;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/LayerNode.class */
final class LayerNode {

    @Nullable
    private final LayerNode parent;
    private final LayerSpatialHash spatialHash;

    @Nullable
    private LayerNode up;

    @Nullable
    private LayerNode down;
    private GuiComponent clearCommand;
    private boolean preparation;
    private final List<GuiComponent> states = new ArrayList();
    private int clearCommandIndex = Integer.MAX_VALUE;

    public LayerNode(@Nullable LayerNode parent, LayerSpatialHash.Context hashContext) {
        this.parent = parent;
        this.spatialHash = new LayerSpatialHash(hashContext);
    }

    public void submit(GuiComponent state) {
        if (this.preparation && this.clearCommandIndex != Integer.MAX_VALUE) {
            this.clearCommand = this.states.remove(this.clearCommandIndex);
            this.clearCommandIndex = Integer.MAX_VALUE;
        }
        this.states.add(state);
        if (!this.preparation && (state instanceof GuiClearComponent)) {
            this.clearCommandIndex = this.states.size() - 1;
        } else {
            this.spatialHash.insert(state, state instanceof GuiCommandComponent ? null : state.bounds());
        }
    }

    @Nullable
    public LayerNode getParent() {
        return this.parent;
    }

    @Nullable
    public LayerNode getUp() {
        return this.up;
    }

    public void setUp(@Nullable LayerNode up) {
        this.up = up;
    }

    @Nullable
    public LayerNode getDown() {
        return this.down;
    }

    public void setDown(@Nullable LayerNode down) {
        this.down = down;
    }

    public List<GuiComponent> getStates() {
        return this.states;
    }

    public void traverse(Consumer<LayerNode> consumer) {
        LayerNode down = getDown();
        if (down != null) {
            down.traverse(consumer);
        }
        consumer.accept(this);
        LayerNode up = getUp();
        if (up != null) {
            up.traverse(consumer);
        }
    }

    public boolean hasIntersection(Rectangle bounds) {
        return this.spatialHash.hasIntersection(bounds);
    }

    @ApiStatus.Internal
    public void prepareLayer() {
        if (this.down != null) {
            this.down.prepareLayer();
        }
        this.preparation = true;
        if (this.up != null) {
            this.up.prepareLayer();
        }
    }

    public String toString() {
        return "LayerNode{states=" + this.states.size() + ", hasContent=" + (!this.states.isEmpty()) + ", up=" + (this.up != null) + ", down=" + (this.down != null) + "}";
    }

    @ApiStatus.Internal
    public void finalizePreparation() {
        if (this.down != null) {
            this.down.finalizePreparation();
        }
        this.preparation = false;
        if (this.clearCommand != null) {
            this.states.add(this.clearCommand);
            this.clearCommand = null;
        }
        if (this.up != null) {
            this.up.finalizePreparation();
        }
    }
}
