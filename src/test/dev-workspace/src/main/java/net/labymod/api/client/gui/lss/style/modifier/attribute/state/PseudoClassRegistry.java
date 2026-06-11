package net.labymod.api.client.gui.lss.style.modifier.attribute.state;

import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/lss/style/modifier/attribute/state/PseudoClassRegistry.class */
@Referenceable
public interface PseudoClassRegistry {
    void registerFactory(String str, PseudoClassFactory pseudoClassFactory);

    PseudoClass parse(String str);

    PseudoClass parse(Element element);
}
