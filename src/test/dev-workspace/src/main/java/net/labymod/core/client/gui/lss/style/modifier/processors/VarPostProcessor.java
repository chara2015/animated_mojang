package net.labymod.core.client.gui.lss.style.modifier.processors;

import net.labymod.api.client.gui.lss.style.function.Function;
import net.labymod.api.client.gui.lss.style.modifier.SingleFunctionPostProcessor;
import net.labymod.api.client.gui.lss.style.modifier.WidgetModifier;
import net.labymod.api.client.gui.lss.variable.LssVariable;
import net.labymod.api.client.gui.screen.widget.Widget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/modifier/processors/VarPostProcessor.class */
public class VarPostProcessor extends SingleFunctionPostProcessor {
    private final WidgetModifier modifier;

    public VarPostProcessor(WidgetModifier modifier) {
        super("var");
        this.modifier = modifier;
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.FunctionPostProcessor
    public Object process(Widget widget, Class<?> type, String key, Function function) throws Exception {
        String var = (String) function.firstValues(widget, key)[0].value();
        LssVariable variable = widget.getVariable(var);
        if (variable != null) {
            return this.modifier.processValue(widget, type, key, variable.value());
        }
        return null;
    }
}
