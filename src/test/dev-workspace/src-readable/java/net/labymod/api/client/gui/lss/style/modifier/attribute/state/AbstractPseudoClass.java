package net.labymod.api.client.gui.lss.style.modifier.attribute.state;

import net.labymod.api.client.gui.lss.style.function.Element;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/lss/style/modifier/attribute/state/AbstractPseudoClass.class */
public abstract class AbstractPseudoClass implements PseudoClass {
    private final Element element;

    public AbstractPseudoClass(Element element) {
        this.element = element;
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.attribute.state.PseudoClass
    public Element element() {
        return this.element;
    }
}
