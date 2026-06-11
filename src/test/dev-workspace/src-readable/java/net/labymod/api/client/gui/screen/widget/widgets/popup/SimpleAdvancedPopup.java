package net.labymod.api.client.gui.screen.widget.widgets.popup;

import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/popup/SimpleAdvancedPopup.class */
public class SimpleAdvancedPopup extends AdvancedPopup {
    protected Component title;
    protected Component description;
    protected Consumer<VerticalListWidget<Widget>> widgetFunction;
    protected List<SimplePopupButton> buttons;

    protected SimpleAdvancedPopup() {
        this(null, null, null, null);
    }

    protected SimpleAdvancedPopup(Component title, Component description, Consumer<VerticalListWidget<Widget>> widgetFunction, List<SimplePopupButton> buttons) {
        this.title = title;
        this.description = description;
        this.widgetFunction = widgetFunction;
        if (buttons == null) {
            this.buttons = null;
            return;
        }
        this.buttons = List.copyOf(buttons);
        for (SimplePopupButton button : this.buttons) {
            button.setAttachedPopup(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.popup.AdvancedPopup
    public Widget initialize() {
        VerticalListWidget<Widget> container = new VerticalListWidget<>();
        initializeComponents(container);
        initializeCustomWidgets(container);
        initializeButtons(container);
        return container;
    }

    protected void initializeComponents(VerticalListWidget<Widget> container) {
        if (this.title != null) {
            container.addChild(ComponentWidget.component(this.title).addId("popup-title"));
        }
        if (this.description != null) {
            container.addChild(ComponentWidget.component(this.description).addId("popup-description"));
        }
    }

    protected void initializeCustomWidgets(VerticalListWidget<Widget> container) {
        if (this.widgetFunction != null) {
            this.widgetFunction.accept(container);
        }
    }

    protected void initializeButtons(VerticalListWidget<Widget> container) {
        if (this.buttons == null) {
            return;
        }
        HorizontalListWidget buttonContainer = new HorizontalListWidget();
        buttonContainer.addId("popup-button-container");
        for (SimplePopupButton button : this.buttons) {
            ButtonWidget buttonWidget = createButton(button);
            if (buttonWidget != null) {
                buttonContainer.addEntry(buttonWidget);
            }
        }
        if (!buttonContainer.getChildren().isEmpty()) {
            container.addChild(buttonContainer);
        }
    }

    protected ButtonWidget createButton(SimplePopupButton button) {
        ButtonWidget buttonWidget = ButtonWidget.component(button.text());
        buttonWidget.addId("popup-button");
        if (button.getIdentifier() != null) {
            buttonWidget.addId("popup-button-" + button.getIdentifier());
        }
        buttonWidget.setEnabled(button.isEnabled());
        buttonWidget.setActive(!button.isEnabled());
        buttonWidget.setPressable(() -> {
            if (button.getClickListener() != null) {
                button.getClickListener().accept(button);
            }
            close();
        });
        Objects.requireNonNull(buttonWidget);
        button.setEnabledConsumer(buttonWidget::setEnabled);
        return buttonWidget;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/popup/SimpleAdvancedPopup$Builder.class */
    public static class Builder {
        protected Component title;
        protected Component description;
        protected Consumer<VerticalListWidget<Widget>> widgetFunction;
        protected List<SimplePopupButton> buttons;

        protected Builder() {
        }

        @NotNull
        public Builder title(Component title) {
            this.title = title;
            return this;
        }

        @NotNull
        public Builder description(Component description) {
            this.description = description;
            return this;
        }

        @NotNull
        public Builder widgets(Consumer<VerticalListWidget<Widget>> widgetFunction) {
            this.widgetFunction = widgetFunction;
            return this;
        }

        @NotNull
        public Builder widget(Supplier<Widget> widgetSupplier) {
            return widgets(container -> {
                container.addChild(((Widget) widgetSupplier.get()).addId("popup-custom-content"));
            });
        }

        @NotNull
        public Builder addButton(SimplePopupButton button) {
            if (this.buttons == null) {
                this.buttons = new ArrayList();
            }
            this.buttons.add(button);
            return this;
        }

        @NotNull
        public Builder addButtons(List<SimplePopupButton> buttons) {
            if (this.buttons == null) {
                this.buttons = new ArrayList();
            }
            this.buttons.addAll(buttons);
            return this;
        }

        @NotNull
        public Builder addButtons(SimplePopupButton... buttons) {
            if (this.buttons == null) {
                this.buttons = new ArrayList();
            }
            this.buttons.addAll(Arrays.asList(buttons));
            return this;
        }

        @NotNull
        public SimpleAdvancedPopup build() {
            return new SimpleAdvancedPopup(this.title, this.description, this.widgetFunction, this.buttons);
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/popup/SimpleAdvancedPopup$SimplePopupButton.class */
    public static class SimplePopupButton {
        private final String identifier;
        private final Component text;
        private final Consumer<SimplePopupButton> clickListener;
        private SimpleAdvancedPopup attachedPopup;
        private boolean enabled;
        private BooleanConsumer enabledConsumer;

        protected SimplePopupButton(@Nullable String identifier, @NotNull Component text, @Nullable Consumer<SimplePopupButton> clickListener) {
            Objects.requireNonNull(text, "text");
            this.identifier = identifier == null ? null : identifier.toLowerCase(Locale.ROOT);
            this.text = text;
            this.clickListener = clickListener;
            this.enabled = true;
        }

        @NotNull
        public static SimplePopupButton create(@NotNull Component text) {
            return new SimplePopupButton(null, text, null);
        }

        @NotNull
        public static SimplePopupButton create(@NotNull Component text, @Nullable Consumer<SimplePopupButton> clickListener) {
            return new SimplePopupButton(null, text, clickListener);
        }

        @NotNull
        public static SimplePopupButton create(@Nullable String identifier, @NotNull Component text, @Nullable Consumer<SimplePopupButton> clickListener) {
            return new SimplePopupButton(identifier, text, clickListener);
        }

        @NotNull
        public static SimplePopupButton create(@Nullable String identifier, @NotNull Component text) {
            return new SimplePopupButton(identifier, text, null);
        }

        @NotNull
        public static SimplePopupButton confirm(@Nullable Consumer<SimplePopupButton> clickListener) {
            return new SimplePopupButton("confirm", Component.translatable("labymod.ui.button.confirm", new Component[0]), clickListener);
        }

        @NotNull
        public static SimplePopupButton confirm() {
            return confirm(null);
        }

        @NotNull
        public static SimplePopupButton cancel(@Nullable Consumer<SimplePopupButton> clickListener) {
            return new SimplePopupButton("cancel", Component.translatable("labymod.ui.button.cancel", NamedTextColor.RED), clickListener);
        }

        @NotNull
        public static SimplePopupButton cancel() {
            return cancel(null);
        }

        @NotNull
        public SimplePopupButton enabled(boolean enabled) {
            if (this.enabled == enabled) {
                return this;
            }
            this.enabled = enabled;
            if (this.enabledConsumer != null) {
                this.enabledConsumer.accept(enabled);
            }
            return this;
        }

        @Nullable
        public String getIdentifier() {
            return this.identifier;
        }

        @NotNull
        public Component text() {
            return this.text;
        }

        @Nullable
        public Consumer<SimplePopupButton> getClickListener() {
            return this.clickListener;
        }

        public boolean isEnabled() {
            return this.enabled;
        }

        @NotNull
        public SimplePopupButton enable() {
            return enabled(true);
        }

        @NotNull
        public SimplePopupButton disable() {
            return enabled(false);
        }

        void setEnabledConsumer(BooleanConsumer enabledConsumer) {
            this.enabledConsumer = enabledConsumer;
        }

        void setAttachedPopup(SimpleAdvancedPopup attachedPopup) {
            if (this.attachedPopup != null) {
                throw new IllegalStateException("This SimplePopupButton is already attached to a popup! Please do not reuse it.");
            }
            this.attachedPopup = attachedPopup;
        }
    }
}
