package net.labymod.core.client.gui.screen.widget.window.debug.util;

import java.util.ArrayList;
import java.util.List;
import net.labymod.api.client.gui.screen.widget.Widget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/window/debug/util/VersionedWidget.class */
public class VersionedWidget {
    private final List<VersionedWidget> children;
    private final Class<?> clazz;
    private final Widget wrapped;
    private final boolean anonymous;

    public VersionedWidget(Class<?> vanillaClass, Widget wrapped) {
        Class<?> superclass;
        this.children = new ArrayList();
        this.wrapped = wrapped;
        if (vanillaClass != null && vanillaClass.isAnonymousClass() && (superclass = vanillaClass.getSuperclass()) != null && superclass != Object.class) {
            this.clazz = superclass;
            this.anonymous = true;
        } else {
            this.anonymous = false;
            this.clazz = vanillaClass;
        }
    }

    public VersionedWidget(Class<?> vanillaClass) {
        this(vanillaClass, null);
    }

    protected VersionedWidget() {
        this(null, null);
    }

    public List<VersionedWidget> getChildren() {
        return this.children;
    }

    public String getIdentifier() {
        String vanillaName = this.clazz.getSimpleName();
        if (this.anonymous) {
            vanillaName = vanillaName + " (anonymous)";
        }
        if (this.wrapped == null) {
            return vanillaName + " (vanilla)";
        }
        return this.wrapped.getTypeName() + " (from " + vanillaName + ")";
    }

    public Widget getWrapped() {
        return this.wrapped;
    }

    public void addChild(VersionedWidget child) {
        this.children.add(child);
    }
}
