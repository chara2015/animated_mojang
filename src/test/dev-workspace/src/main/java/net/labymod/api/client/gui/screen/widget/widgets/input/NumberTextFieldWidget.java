package net.labymod.api.client.gui.screen.widget.widgets.input;

import it.unimi.dsi.fastutil.ints.IntConsumer;
import java.util.function.Consumer;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.mouse.MutableMouse;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/NumberTextFieldWidget.class */
@AutoWidget
public class NumberTextFieldWidget extends TextFieldWidget {
    private int value = 0;
    private IntConsumer onUpdate;

    public NumberTextFieldWidget() {
        setValue(this.value);
        this.updateListener = text -> {
            int newValue = 0;
            if (!text.isEmpty() && !text.equals("-")) {
                try {
                    newValue = Integer.parseInt(text);
                } catch (NumberFormatException e) {
                    newValue = this.value;
                }
            }
            if (newValue != this.value) {
                this.value = newValue;
                if (this.onUpdate != null) {
                    this.onUpdate.accept(this.value);
                }
            }
        };
        validator(text2 -> {
            if (text2.isEmpty() || text2.equals("-")) {
                return true;
            }
            try {
                Integer.parseInt(text2);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        });
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.Interactable
    public void tick() {
        super.tick();
        if (isFocused()) {
            return;
        }
        if (this.text.isEmpty() || this.text.equals("-")) {
            setValue(0);
        }
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseScrolled(MutableMouse mouse, double scrollDelta) {
        if (!isFocused()) {
            return super.mouseScrolled(mouse, scrollDelta);
        }
        if (scrollDelta > 0.0d) {
            setValue(getValue() + 1);
            return true;
        }
        setValue(getValue() - 1);
        return true;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        super.setText(String.valueOf(value));
        super.setCursorAtEnd();
        this.viewIndex = 0;
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget
    @Deprecated
    public void setText(String text) {
        super.setText(text);
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget
    @Deprecated
    public TextFieldWidget updateListener(Consumer<String> updateListener) {
        return super.updateListener(updateListener);
    }

    public NumberTextFieldWidget onUpdate(IntConsumer onUpdate) {
        this.onUpdate = onUpdate;
        return this;
    }
}
