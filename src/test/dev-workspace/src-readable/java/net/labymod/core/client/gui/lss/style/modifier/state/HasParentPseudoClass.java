package net.labymod.core.client.gui.lss.style.modifier.state;

import net.labymod.api.client.gui.lss.style.Selector;
import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.gui.lss.style.modifier.attribute.state.AbstractPseudoClass;
import net.labymod.api.client.gui.lss.style.modifier.attribute.state.PseudoClass;
import net.labymod.api.client.gui.screen.widget.Widget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/modifier/state/HasParentPseudoClass.class */
public class HasParentPseudoClass extends AbstractPseudoClass {
    private final Selector parentSelector;

    public HasParentPseudoClass(Element element, Selector parentSelector) {
        super(element);
        this.parentSelector = parentSelector;
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.attribute.state.PseudoClass
    public boolean matchesState(Widget widget) {
        return (widget.getParent() instanceof Widget) && this.parentSelector.match((Widget) widget.getParent(), true);
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.attribute.state.PseudoClass
    public int getPriority() {
        PseudoClass pseudoClass = this.parentSelector.lastPseudoClass();
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
        HasParentPseudoClass that = (HasParentPseudoClass) object;
        return this.parentSelector.equals(that.parentSelector);
    }

    public int hashCode() {
        if (this.parentSelector != null) {
            return this.parentSelector.hashCode();
        }
        return 0;
    }
}
