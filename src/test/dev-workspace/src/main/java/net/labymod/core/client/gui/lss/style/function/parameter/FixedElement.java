package net.labymod.core.client.gui.lss.style.function.parameter;

import java.util.function.Consumer;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.gui.lss.style.modifier.WidgetModifier;
import net.labymod.api.client.gui.screen.widget.Widget;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/function/parameter/FixedElement.class */
public class FixedElement implements Element {
    private static final WidgetModifier MODIFIER = Laby.references().widgetModifier();
    private final String value;

    public FixedElement(String value) {
        this.value = value;
    }

    @Override // net.labymod.api.client.gui.lss.style.function.Element
    public ProcessedObject[] computeValue(@NotNull Widget widget, @NotNull String key, @NotNull Class<?> type) throws Exception {
        if (type == Object.class) {
            return new ProcessedObject[]{new ProcessedObject(null, this.value, this.value)};
        }
        return MODIFIER.processValue(widget, type, key, this.value, this);
    }

    @Override // net.labymod.api.client.gui.lss.style.function.Element
    public String getRawValue() {
        return this.value;
    }

    public String toString() {
        return this.value;
    }

    @Override // net.labymod.api.client.gui.lss.style.function.Element
    public void forEach(Consumer<Element> consumer) {
        consumer.accept(this);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FixedElement that = (FixedElement) o;
        return this.value.equals(that.value);
    }

    public int hashCode() {
        if (this.value != null) {
            return this.value.hashCode();
        }
        return 0;
    }
}
