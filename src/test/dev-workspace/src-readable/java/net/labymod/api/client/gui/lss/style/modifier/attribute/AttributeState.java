package net.labymod.api.client.gui.lss.style.modifier.attribute;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.core.client.gui.background.position.ScreenPositionRegistry;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/lss/style/modifier/attribute/AttributeState.class */
public class AttributeState {
    private static final Map<String, AttributeState> STATES = new HashMap();
    public static final AttributeState NORMAL = register("NORMAL", 0);
    public static final AttributeState SELECTED = register("SELECTED", 100);
    public static final AttributeState FOCUS = register("FOCUS", 200);
    public static final AttributeState ENABLED = register("ENABLED", 300);
    public static final AttributeState HOVER = register("HOVER", 400);
    public static final AttributeState ACTIVE = register("ACTIVE", ScreenPositionRegistry.DEFAULT_SCREEN_SWITCH_DURATION);
    public static final AttributeState DRAGGING = register("DRAGGING", 600);
    private final String name;
    private final String lowerName;
    private final int priority;
    private final boolean staticState;

    private AttributeState(String name, int priority, boolean staticState) {
        this.name = name;
        this.lowerName = name.toLowerCase(Locale.ENGLISH);
        this.priority = priority;
        this.staticState = staticState;
    }

    public static AttributeState register(String name, int priority) {
        return register(name, priority, false);
    }

    public static AttributeState staticState(String name, int priority) {
        return register(name, priority, true);
    }

    private static AttributeState register(String name, int priority, boolean staticState) {
        String name2 = name.toUpperCase(Locale.ENGLISH);
        if (STATES.containsKey(name2)) {
            return STATES.get(name2);
        }
        AttributeState state = new AttributeState(name2, priority, staticState);
        STATES.put(name2, state);
        return state;
    }

    public String getName() {
        return this.name;
    }

    public String getLowerName() {
        return this.lowerName;
    }

    public int getPriority() {
        return this.priority;
    }

    public boolean isStaticState() {
        return this.staticState;
    }

    public boolean isEnabled(Widget widget) {
        return this == NORMAL || widget.isAttributeStateEnabled(this);
    }

    public String toString() {
        return this.lowerName;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AttributeState state = (AttributeState) o;
        return this.name.equals(state.name);
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    public static AttributeState getByName(String name) {
        AttributeState state = STATES.get(name.toUpperCase(Locale.ENGLISH));
        if (state == null) {
            throw new IllegalArgumentException("Unknown AttributeState: " + name);
        }
        return state;
    }
}
