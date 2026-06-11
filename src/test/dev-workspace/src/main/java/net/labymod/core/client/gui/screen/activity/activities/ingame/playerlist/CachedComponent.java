package net.labymod.core.client.gui.screen.activity.activities.ingame.playerlist;

import java.util.Objects;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.HorizontalAlignment;
import net.labymod.api.client.render.font.RenderableComponent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/ingame/playerlist/CachedComponent.class */
class CachedComponent {
    public static final RenderableComponent EMPTY_COMPONENT = RenderableComponent.of(Component.empty());
    protected Component component;
    protected RenderableComponent renderableComponent;
    private float maxWidth;
    private int ticksSinceLastUse;

    public CachedComponent() {
    }

    public CachedComponent(Component component) {
        update(component);
    }

    public void update(Component component) {
        update(component, 0.0f);
    }

    public void update(Component component, float maxWidth) {
        this.maxWidth = maxWidth;
        if (Objects.equals(this.component, component)) {
            return;
        }
        this.component = component;
        this.renderableComponent = null;
    }

    public Component component() {
        return this.component;
    }

    public RenderableComponent renderableComponent() {
        RenderableComponent renderableComponent;
        this.ticksSinceLastUse = 0;
        if (this.renderableComponent == null) {
            Component component = component();
            if (component == null) {
                return EMPTY_COMPONENT;
            }
            this.renderableComponent = RenderableComponent.of(component, this.maxWidth, HorizontalAlignment.CENTER);
        }
        if (this.renderableComponent == null) {
            renderableComponent = EMPTY_COMPONENT;
        } else {
            renderableComponent = this.renderableComponent;
        }
        RenderableComponent renderableComponent2 = renderableComponent;
        return renderableComponent2;
    }

    public void invalidate() {
        this.component = null;
        this.renderableComponent = null;
    }

    public void refresh() {
        this.renderableComponent = null;
    }

    public boolean isEmpty() {
        return this.component == null;
    }

    public void tick() {
        if (this.renderableComponent == null) {
            this.ticksSinceLastUse = 0;
            return;
        }
        this.ticksSinceLastUse++;
        if (this.ticksSinceLastUse > 100) {
            this.renderableComponent = null;
        }
    }
}
