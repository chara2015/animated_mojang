package net.labymod.api.client.gui.screen.state.debug;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import net.labymod.api.client.gui.screen.state.GuiComponent;
import net.labymod.api.client.gui.screen.state.GuiRenderState;
import net.labymod.api.client.gui.screen.state.states.GuiEffectGlyphRenderState;
import net.labymod.api.client.gui.screen.state.states.GuiGlyphRenderState;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.laby3d.api.resource.AssetId;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/debug/CanvasDebugSnapshot.class */
public class CanvasDebugSnapshot {
    private static final Predicate<GuiComponent> IGNORE_COMPONENTS = component -> {
        return (component instanceof GuiGlyphRenderState) || (component instanceof GuiEffectGlyphRenderState);
    };
    private final int index;
    private final List<ComponentSnapshot> components;

    public CanvasDebugSnapshot(int index, List<ComponentSnapshot> components) {
        this.index = index;
        this.components = components;
    }

    public static CanvasDebugSnapshot of(int index, List<GuiComponent> source) {
        List<ComponentSnapshot> components = new ArrayList<>(source.size());
        for (GuiComponent component : source) {
            if (!IGNORE_COMPONENTS.test(component)) {
                components.add(new ComponentSnapshot(component));
            }
        }
        return new CanvasDebugSnapshot(index, components);
    }

    public int getIndex() {
        return this.index;
    }

    public List<ComponentSnapshot> getComponents() {
        return this.components;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/debug/CanvasDebugSnapshot$ComponentSnapshot.class */
    public static class ComponentSnapshot {
        private final GuiComponent component;
        private final String typeName;
        private final AssetId materialId;
        private final Rectangle bounds;
        private final boolean hasScissor;

        public ComponentSnapshot(GuiComponent component) {
            this.component = component;
            this.typeName = component.getClass().getSimpleName();
            this.bounds = component.bounds();
            this.hasScissor = component.getScissorArea() != null;
            if (component instanceof GuiRenderState) {
                GuiRenderState renderState = (GuiRenderState) component;
                this.materialId = renderState.material().id();
            } else {
                this.materialId = null;
            }
        }

        public GuiComponent getComponent() {
            return this.component;
        }

        public String getTypeName() {
            return this.typeName;
        }

        @Nullable
        public AssetId getMaterialId() {
            return this.materialId;
        }

        public Rectangle getBounds() {
            return this.bounds;
        }

        public boolean hasScissor() {
            return this.hasScissor;
        }
    }
}
