package net.labymod.core.client.gui.lss.style;

import javax.inject.Singleton;
import net.labymod.api.client.gui.lss.style.Selector;
import net.labymod.api.client.gui.lss.style.StyleHelper;
import net.labymod.api.models.Implements;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/DefaultStyleHelper.class */
@Singleton
@Implements(StyleHelper.class)
public class DefaultStyleHelper implements StyleHelper {
    @Override // net.labymod.api.client.gui.lss.style.StyleHelper
    public Selector createSelector(String rawSelector) {
        return new DefaultSelector(rawSelector);
    }
}
