package net.labymod.api.client.gui.lss.style.modifier.attribute;

import net.labymod.api.client.gui.lss.style.reader.SingleInstruction;
import net.labymod.api.client.gui.screen.widget.Widget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/lss/style/modifier/attribute/VariableAttributePatch.class */
public class VariableAttributePatch extends AttributePatch {
    public VariableAttributePatch(SingleInstruction instruction, String rawValue) {
        super(instruction, rawValue);
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.attribute.AttributePatch
    public void init() {
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.attribute.AttributePatch
    public void patch(Widget widget) {
        widget.setVariable(getKey(), rawValue());
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.attribute.AttributePatch
    public void unpatch(Widget widget) {
        widget.clearVariable(getKey());
    }
}
