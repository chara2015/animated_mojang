package net.labymod.core.client.gui.lss.style.modifier.state;

import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.gui.lss.style.modifier.attribute.state.AbstractPseudoClass;
import net.labymod.api.client.gui.lss.style.modifier.attribute.state.PseudoClass;
import net.labymod.api.client.gui.screen.widget.Widget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/modifier/state/InvertedPseudoClass.class */
public class InvertedPseudoClass extends AbstractPseudoClass {
    private final PseudoClass state;

    public InvertedPseudoClass(Element element, PseudoClass state) {
        super(element);
        this.state = state;
    }

    public PseudoClass state() {
        return this.state;
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.attribute.state.PseudoClass
    public boolean matchesState(Widget widget) {
        return !this.state.matchesState(widget);
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.attribute.state.PseudoClass
    public int getPriority() {
        return this.state.getPriority();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InvertedPseudoClass that = (InvertedPseudoClass) o;
        return this.state.equals(that.state);
    }

    public int hashCode() {
        if (this.state != null) {
            return this.state.hashCode();
        }
        return 0;
    }
}
