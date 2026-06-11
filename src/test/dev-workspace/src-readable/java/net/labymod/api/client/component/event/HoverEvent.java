package net.labymod.api.client.component.event;

import java.util.Objects;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.ComponentService;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.world.item.Item;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/component/event/HoverEvent.class */
public interface HoverEvent<T> {
    Action<T> action();

    T value();

    static HoverEvent<Component> showText(@NotNull Component text) {
        return ComponentService.hoverEvent(Action.SHOW_TEXT, text);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/component/event/HoverEvent$Action.class */
    public static class Action<T> {
        public static final Action<Component> SHOW_TEXT = new Action<>("show_text", Component.class);
        public static final Action<Item> SHOW_ITEM = new Action<>("show_item", Item.class);
        public static final Action<Entity> SHOW_ENTITY = new Action<>("show_entity", Entity.class);
        private final Class<T> type;
        private final String name;

        private Action(String name, Class<T> type) {
            this.name = name;
            this.type = type;
        }

        public String getName() {
            return this.name;
        }

        public Class<T> getType() {
            return this.type;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null || getClass() != object.getClass()) {
                return false;
            }
            Action<?> action = (Action) object;
            if (!Objects.equals(this.type, action.type)) {
                return false;
            }
            return Objects.equals(this.name, action.name);
        }

        public int hashCode() {
            int result = this.type != null ? this.type.hashCode() : 0;
            return (31 * result) + (this.name != null ? this.name.hashCode() : 0);
        }
    }
}
