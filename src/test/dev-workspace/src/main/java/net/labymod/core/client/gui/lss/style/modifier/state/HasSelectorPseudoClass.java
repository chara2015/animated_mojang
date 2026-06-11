package net.labymod.core.client.gui.lss.style.modifier.state;

import net.labymod.api.client.gui.lss.style.Selector;
import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.gui.lss.style.modifier.attribute.state.AbstractPseudoClass;
import net.labymod.api.client.gui.lss.style.modifier.attribute.state.PseudoClass;
import net.labymod.api.client.gui.screen.widget.Widget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/modifier/state/HasSelectorPseudoClass.class */
public class HasSelectorPseudoClass extends AbstractPseudoClass {
    private final Selector subSelector;

    public HasSelectorPseudoClass(Element element, Selector subSelector) {
        super(element);
        this.subSelector = subSelector;
    }

    public Selector subSelector() {
        return this.subSelector;
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.attribute.state.PseudoClass
    public boolean matchesState(Widget widget) {
        for (Widget child : widget.getChildren()) {
            if (this.subSelector.match(child, true)) {
                return true;
            }
        }
        return false;
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.attribute.state.PseudoClass
    public int getPriority() {
        PseudoClass pseudoClass = this.subSelector.lastPseudoClass();
        if (pseudoClass != null) {
            return pseudoClass.getPriority();
        }
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        HasSelectorPseudoClass that = (HasSelectorPseudoClass) object;
        return this.subSelector.equals(that.subSelector);
    }

    public int hashCode() {
        if (this.subSelector != null) {
            return this.subSelector.hashCode();
        }
        return 0;
    }
}
