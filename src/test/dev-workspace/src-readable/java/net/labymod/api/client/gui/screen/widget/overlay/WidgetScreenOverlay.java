package net.labymod.api.client.gui.screen.widget.overlay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.ScreenCustomFontStack;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.overlay.WidgetReference;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.util.ThreadSafe;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/overlay/WidgetScreenOverlay.class */
@AutoActivity
public class WidgetScreenOverlay extends ScreenOverlay {
    private final List<WidgetReference> references;

    public WidgetScreenOverlay() {
        super(20);
        this.references = new ArrayList();
        setActive(true);
    }

    @Override // net.labymod.api.client.gui.screen.LabyScreen
    public void load(Parent parent) {
        List<Widget> genericChildren = ((Document) this.document).getGenericChildren();
        Widget[] children = (Widget[]) genericChildren.toArray(new Widget[0]);
        genericChildren.clear();
        super.load(parent);
        Collections.addAll(genericChildren, children);
        for (WidgetReference reference : (WidgetReference[]) this.references.toArray(new WidgetReference[0])) {
            if (reference.clickRemoveStrategy() != WidgetReference.ClickRemoveStrategy.NEVER) {
                reference.remove();
            }
        }
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.ScreenInstance
    public void render(ScreenContext context) {
        ((Document) this.document).renderChildren().set(false);
        Iterator<WidgetReference> it = this.references.iterator();
        while (it.hasNext()) {
            it.next().onPreRender();
        }
        super.render(context);
        for (WidgetReference reference : this.references) {
            context.canvas().nextLayer();
            ScreenCustomFontStack screenCustomFontStack = Laby.references().screenCustomFontStack();
            LabyScreen sourceScreen = reference.getSourceScreen();
            try {
                screenCustomFontStack.push(sourceScreen);
                reference.widget().render(context);
                screenCustomFontStack.pop(sourceScreen);
            } catch (Throwable th) {
                screenCustomFontStack.pop(sourceScreen);
                throw th;
            }
        }
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        if (this.references.isEmpty()) {
            return false;
        }
        WidgetReference[] references = (WidgetReference[]) this.references.toArray(new WidgetReference[0]);
        boolean flag = super.mouseClicked(mouse, mouseButton);
        WidgetReference toRemove = null;
        for (WidgetReference reference : references) {
            WidgetReference.ClickRemoveStrategy strategy = reference.clickRemoveStrategy();
            boolean hovered = reference.widget().isHovered();
            switch (strategy) {
                case ALWAYS:
                    reference.remove();
                    return true;
                case INSIDE:
                    if (hovered) {
                        reference.remove();
                        return true;
                    }
                    break;
                    break;
                case OUTSIDE:
                    if (hovered) {
                        toRemove = null;
                    } else {
                        toRemove = reference;
                    }
                    break;
            }
        }
        if (toRemove != null) {
            toRemove.remove();
            return true;
        }
        return flag;
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.LabyScreen, net.labymod.api.client.gui.Interactable
    public void tick() {
        super.tick();
        if (!this.references.isEmpty() && this.labyAPI.minecraft().isMouseLocked()) {
            for (WidgetReference reference : (WidgetReference[]) this.references.toArray(new WidgetReference[0])) {
                if (reference.clickRemoveStrategy() != WidgetReference.ClickRemoveStrategy.NEVER) {
                    reference.remove();
                }
            }
        }
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.MouseUser
    public boolean mouseScrolled(MutableMouse mouse, double scrollDelta) {
        if (this.references.isEmpty()) {
            return false;
        }
        for (WidgetReference reference : (WidgetReference[]) this.references.toArray(new WidgetReference[0])) {
            WidgetReference.ClickRemoveStrategy strategy = reference.clickRemoveStrategy();
            boolean hovered = reference.widget().isHovered();
            if (hovered && reference.widget().mouseScrolled(mouse, scrollDelta)) {
                return true;
            }
            switch (strategy) {
                case ALWAYS:
                    reference.remove();
                    break;
                case INSIDE:
                    if (hovered) {
                        reference.remove();
                    }
                    break;
                case OUTSIDE:
                    if (!hovered) {
                        reference.remove();
                    }
                    break;
            }
        }
        Iterator<WidgetReference> it = this.references.iterator();
        while (it.hasNext()) {
            if (it.next().widget().isHovered()) {
                return super.mouseScrolled(mouse, scrollDelta);
            }
        }
        return super.mouseScrolled(mouse, scrollDelta);
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.KeyboardUser
    public boolean keyPressed(Key key, InputType type) {
        if (this.references.isEmpty()) {
            return false;
        }
        for (WidgetReference reference : (WidgetReference[]) this.references.toArray(new WidgetReference[0])) {
            switch (reference.keyPressRemoveStrategy()) {
                case ESCAPE:
                    if (key == Key.ESCAPE) {
                        reference.remove();
                        return true;
                    }
                    break;
                    break;
                case ALWAYS:
                    reference.remove();
                    return true;
            }
        }
        return super.keyPressed(key, type);
    }

    public WidgetReference display(List<StyleSheet> styles, Widget widget) {
        return display(null, styles, widget);
    }

    public WidgetReference display(LabyScreen sourceScreen, List<StyleSheet> styles, Widget widget) {
        ThreadSafe.ensureRenderThread();
        if (this.references.isEmpty()) {
            reload();
        }
        WidgetReference reference = new WidgetReference(this, sourceScreen, styles, widget);
        ScreenCustomFontStack screenCustomFontStack = Laby.references().screenCustomFontStack();
        try {
            screenCustomFontStack.push(sourceScreen);
            try {
                for (StyleSheet styleSheet : styles) {
                    ((Document) this.document).applyStyleSheet(styleSheet);
                }
            } catch (Exception exception) {
                LOGGER.error("Failed to apply style sheets", exception);
            }
            this.references.add(reference);
            document().addChildInitialized(widget);
            screenCustomFontStack.pop(sourceScreen);
            return reference;
        } catch (Throwable th) {
            screenCustomFontStack.pop(sourceScreen);
            throw th;
        }
    }

    public void destroy(WidgetReference reference) {
        ThreadSafe.ensureRenderThread();
        this.references.remove(reference);
        List<StyleSheet> styles = reference.getStyleSheets();
        if (styles != null) {
            ((Document) this.document).getStyleSheets().removeAll(styles);
        }
        Widget widget = reference.widget();
        document().removeChild(widget);
        widget.dispose();
        widget.destroy();
        for (Runnable destroyHandler : reference.destroyHandlers()) {
            destroyHandler.run();
        }
    }

    public List<WidgetReference> getReferences() {
        return this.references;
    }

    @Override // net.labymod.api.client.gui.screen.widget.overlay.ScreenOverlay
    public boolean isHideGui() {
        return false;
    }
}
