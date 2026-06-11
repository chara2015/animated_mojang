package net.labymod.core.client.gui.lss.style.function;

import java.util.Arrays;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.gui.lss.style.function.Function;
import net.labymod.api.client.gui.lss.style.function.FunctionRegistry;
import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.gui.lss.style.modifier.WidgetModifier;
import net.labymod.api.client.gui.screen.widget.Widget;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/function/DefaultFunction.class */
public class DefaultFunction implements Function {
    private static final FunctionRegistry REGISTRY = Laby.references().functionRegistry();
    private static final WidgetModifier MODIFIER = Laby.references().widgetModifier();
    private final String name;
    private final Element[] elements;
    private final String rawValue;

    public DefaultFunction(String name, Element[] elements) {
        this.name = name;
        this.elements = elements;
        StringBuilder builder = new StringBuilder();
        int size = this.elements.length;
        for (int i = 0; i < size; i++) {
            Element element = this.elements[i];
            builder.append(element.getRawValue());
            if (i == size - 1) {
                break;
            }
            builder.append(", ");
        }
        this.rawValue = this.name + "(" + String.valueOf(builder) + ")";
    }

    @Override // net.labymod.api.client.gui.lss.style.function.Function
    public String getName() {
        return this.name;
    }

    @Override // net.labymod.api.client.gui.lss.style.function.Function
    public Element[] parameters() {
        return this.elements;
    }

    /* JADX WARN: Type inference failed for: r0v6, types: [net.labymod.api.client.gui.lss.style.modifier.ProcessedObject[], net.labymod.api.client.gui.lss.style.modifier.ProcessedObject[][]] */
    @Override // net.labymod.api.client.gui.lss.style.function.Function
    public ProcessedObject[][] allValues(Widget widget, String key) throws Exception {
        Class<?>[] types = null;
        if (REGISTRY.isFunctionRegistered(this.name)) {
            types = REGISTRY.getParameterTypes(this.name, this.elements.length);
            if (types == null) {
                throw new IllegalArgumentException(String.format(Locale.ROOT, "Invalid parameter length for function '%s'. Got %d parameters, expected a number of one out of [%s]", toString(), Integer.valueOf(this.elements.length), Arrays.stream(REGISTRY.getAllowedParameterCounts(this.name)).mapToObj(String::valueOf).collect(Collectors.joining(", "))));
            }
        }
        ?? r0 = new ProcessedObject[this.elements.length];
        if (types != null && r0.length != types.length) {
            throw new IllegalStateException("Function not matching required types: " + String.valueOf(this));
        }
        for (int i = 0; i < this.elements.length; i++) {
            ProcessedObject[] computeValues = this.elements[i].computeValue(widget, key, types != null ? types[i] : String.class);
            r0[i] = computeValues;
        }
        return r0;
    }

    @Override // net.labymod.api.client.gui.lss.style.function.Function
    public ProcessedObject[] firstValues(Widget widget, String key) throws Exception {
        ProcessedObject[][] allValues = allValues(widget, key);
        ProcessedObject[] firstValues = new ProcessedObject[allValues.length];
        for (int i = 0; i < allValues.length; i++) {
            firstValues[i] = allValues[i][0];
        }
        return firstValues;
    }

    @Override // net.labymod.api.client.gui.lss.style.function.Element
    public ProcessedObject[] computeValue(@NotNull Widget widget, @NotNull String key, @NotNull Class<?> type) throws Exception {
        return MODIFIER.processValue(widget, type, key, getRawValue(), this);
    }

    @Override // net.labymod.api.client.gui.lss.style.function.Element
    public String getRawValue() {
        return this.rawValue;
    }

    @Override // net.labymod.api.client.gui.lss.style.function.Element
    public void forEach(Consumer<Element> consumer) {
        consumer.accept(this);
        for (Element parameter : this.elements) {
            parameter.forEach(consumer);
        }
    }

    @Override // net.labymod.api.client.gui.lss.style.function.Function
    public String toString() {
        return getRawValue();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DefaultFunction that = (DefaultFunction) o;
        return this.name.equals(that.name) && Arrays.equals(this.elements, that.elements);
    }

    public int hashCode() {
        int result = this.name != null ? this.name.hashCode() : 0;
        return (31 * result) + Arrays.hashCode(this.elements);
    }
}
