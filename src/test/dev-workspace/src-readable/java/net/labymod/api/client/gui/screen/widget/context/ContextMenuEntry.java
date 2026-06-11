package net.labymod.api.client.gui.screen.widget.context;

import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/context/ContextMenuEntry.class */
public class ContextMenuEntry {
    private final Supplier<Icon> icon;
    private final Supplier<Icon> subIcon;
    private final Supplier<Component> text;
    private final Supplier<Component> hoverComponent;
    private final ContextMenuHandler clickHandler;
    private final BooleanSupplier disabledSupplier;
    private final Supplier<ContextMenu> subMenu;

    private ContextMenuEntry(Supplier<Icon> icon, Supplier<Icon> subIcon, Supplier<Component> text, Supplier<Component> hoverComponent, ContextMenuHandler clickHandler, BooleanSupplier disabledSupplier, Supplier<ContextMenu> subMenu) {
        this.icon = icon;
        this.subIcon = subIcon;
        this.text = text;
        this.hoverComponent = hoverComponent;
        this.clickHandler = clickHandler;
        this.disabledSupplier = disabledSupplier;
        this.subMenu = subMenu;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Icon getIcon() {
        if (this.icon != null) {
            return this.icon.get();
        }
        return null;
    }

    public Icon getSubIcon() {
        if (this.subIcon != null) {
            return this.subIcon.get();
        }
        return null;
    }

    public Component getText() {
        if (this.text != null) {
            return this.text.get();
        }
        return null;
    }

    public Component getHoverComponent() {
        if (this.hoverComponent != null) {
            return this.hoverComponent.get();
        }
        return null;
    }

    public ContextMenuHandler clickHandler() {
        return this.clickHandler;
    }

    public boolean isDisabled() {
        return this.disabledSupplier != null && this.disabledSupplier.getAsBoolean();
    }

    public boolean hasSubMenu() {
        return this.subMenu != null;
    }

    public ContextMenu getSubMenu() {
        if (this.subMenu == null) {
            return null;
        }
        ContextMenu subMenu = this.subMenu.get();
        if (subMenu == null) {
            throw new IllegalArgumentException("subMenu supplier may not return null");
        }
        return subMenu;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/context/ContextMenuEntry$Builder.class */
    public static class Builder {
        private Supplier<Icon> icon;
        private Supplier<Icon> subIcon;
        private Supplier<Component> text;
        private Supplier<Component> hoverComponent;
        private ContextMenuHandler clickHandler;
        private BooleanSupplier disabled;
        private Supplier<ContextMenu> subMenu;

        public Builder icon(Icon icon) {
            return icon(() -> {
                return icon;
            });
        }

        public Builder icon(Supplier<Icon> iconSupplier) {
            this.icon = iconSupplier;
            return this;
        }

        public Builder subIcon(Icon subIcon) {
            return subIcon(() -> {
                return subIcon;
            });
        }

        public Builder subIcon(Supplier<Icon> subIconSupplier) {
            this.subIcon = subIconSupplier;
            return this;
        }

        public Builder text(Component text) {
            return text(() -> {
                return text;
            });
        }

        public Builder text(Supplier<Component> textSupplier) {
            this.text = textSupplier;
            return this;
        }

        public Builder hoverComponent(Component hoverComponent) {
            return hoverComponent(() -> {
                return hoverComponent;
            });
        }

        public Builder hoverComponent(Supplier<Component> hoverComponentSupplier) {
            this.hoverComponent = hoverComponentSupplier;
            return this;
        }

        @Deprecated(forRemoval = true, since = "4.3.11")
        public Builder clickHandler(BiConsumer<ContextMenuEntry, Object> clickHandler) {
            return clickHandler(entry -> {
                clickHandler.accept(entry, entry);
                return true;
            });
        }

        public Builder simpleClickHandler(Consumer<ContextMenuEntry> clickHandler) {
            return clickHandler(entry -> {
                clickHandler.accept(entry);
                return true;
            });
        }

        public Builder clickHandler(ContextMenuHandler clickHandler) {
            this.clickHandler = clickHandler;
            return this;
        }

        public Builder disabled() {
            return disabled(true);
        }

        public Builder disabled(boolean disabled) {
            return disabled(() -> {
                return disabled;
            });
        }

        public Builder disabled(BooleanSupplier supplier) {
            this.disabled = supplier;
            return this;
        }

        public Builder subMenu(Supplier<ContextMenu> subMenu) {
            this.subMenu = subMenu;
            return this;
        }

        public ContextMenuEntry build() {
            return new ContextMenuEntry(this.icon, this.subIcon, this.text, this.hoverComponent, this.clickHandler, this.disabled, this.subMenu);
        }
    }
}
