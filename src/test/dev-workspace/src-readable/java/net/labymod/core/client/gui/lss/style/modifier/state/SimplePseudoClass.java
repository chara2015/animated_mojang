package net.labymod.core.client.gui.lss.style.modifier.state;

import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributeState;
import net.labymod.api.client.gui.lss.style.modifier.attribute.state.AbstractPseudoClass;
import net.labymod.api.client.gui.screen.widget.Widget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/modifier/state/SimplePseudoClass.class */
public class SimplePseudoClass extends AbstractPseudoClass {
    private final AttributeState state;

    public SimplePseudoClass(Element element, AttributeState state) {
        super(element);
        this.state = state;
    }

    public AttributeState state() {
        return this.state;
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.attribute.state.PseudoClass
    public boolean matchesState(Widget widget) {
        return this.state.isEnabled(widget);
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.attribute.state.PseudoClass
    public int getPriority() {
        return this.state.getPriority();
    }
}
