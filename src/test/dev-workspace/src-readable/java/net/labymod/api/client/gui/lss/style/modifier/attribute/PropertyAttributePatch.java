package net.labymod.api.client.gui.lss.style.modifier.attribute;

import java.util.HashMap;
import java.util.Map;
import net.labymod.api.client.gui.lss.LssPropertyException;
import net.labymod.api.client.gui.lss.LssStyleException;
import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.gui.lss.style.function.Function;
import net.labymod.api.client.gui.lss.style.modifier.Forwarder;
import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.gui.lss.style.reader.SingleInstruction;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.WrappedWidget;
import net.labymod.api.util.function.ThrowableSupplier;
import net.labymod.api.util.logging.Logging;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/lss/style/modifier/attribute/PropertyAttributePatch.class */
public class PropertyAttributePatch extends AttributePatch {
    private static final Logging LOGGER = Logging.create((Class<?>) PropertyAttributePatch.class);
    private final Forwarder forwarder;
    private final Class<?> type;
    private final Element element;
    private final ThrowableSupplier<ProcessedObject[], Exception> objectSupplier;
    private ProcessedObject[] objects;

    public PropertyAttributePatch(Forwarder forwarder, Class<?> type, SingleInstruction instruction, Element element, ThrowableSupplier<ProcessedObject[], Exception> objectSupplier) {
        super(instruction, element.getRawValue());
        this.forwarder = forwarder;
        this.type = type;
        this.element = element;
        this.objectSupplier = objectSupplier;
    }

    public Forwarder forwarder() {
        return this.forwarder;
    }

    public Class<?> getType() {
        return this.type;
    }

    public Element element() {
        return this.element;
    }

    public ProcessedObject[] objects() {
        return this.objects;
    }

    public Map<String, String> collectVariables(Widget widget) {
        Map<String, String> variables = new HashMap<>();
        this.element.forEach(child -> {
            if (child instanceof Function) {
                Function function = (Function) child;
                if (function.getName().equals("var") && function.parameters().length == 1) {
                    try {
                        ProcessedObject[] variable = function.computeValue(widget, getKey(), String.class);
                        if (variable.length == 1) {
                            variables.put(function.parameters()[0].getRawValue(), variable[0].rawValue());
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
        });
        return variables;
    }

    private void updateObject() throws LssStyleException {
        try {
            this.objects = this.objectSupplier.get();
        } catch (Exception exception) {
            throw new LssStyleException(instruction().styleSheet(), instruction(), exception.getMessage());
        }
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.attribute.AttributePatch
    public void init() throws LssStyleException {
        updateObject();
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.attribute.AttributePatch
    public void patch(Widget widget) {
        try {
            updateObject();
            patch(widget, this.objects);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void patch(Widget widget, ProcessedObject... processedObject) {
        try {
            this.forwarder.forward(widget, getKey(), processedObject);
            if (widget instanceof WrappedWidget) {
                patch(((WrappedWidget) widget).childWidget());
            }
        } catch (LssPropertyException exception) {
            LOGGER.error("Failed to apply style instruction from {}:{} to widget {}: {}", instruction().styleSheet().file(), Integer.valueOf(instruction().getLineNumber()), widget.getIds(), exception.getMessage());
        } catch (Exception exception2) {
            exception2.printStackTrace();
        }
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.attribute.AttributePatch
    public void unpatch(Widget widget) {
        this.forwarder.reset(widget, getKey());
        if (widget instanceof WrappedWidget) {
            unpatch(((WrappedWidget) widget).childWidget());
        }
    }
}
