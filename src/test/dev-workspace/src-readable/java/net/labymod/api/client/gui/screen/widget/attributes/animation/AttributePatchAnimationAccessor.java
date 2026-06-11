package net.labymod.api.client.gui.screen.widget.attributes.animation;

import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributePatch;
import net.labymod.api.client.gui.screen.widget.Widget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/attributes/animation/AttributePatchAnimationAccessor.class */
public class AttributePatchAnimationAccessor implements AttributeAnimationAccessor<AttributePatch> {
    private final String name;
    private final Widget widget;
    private final AttributePatch firstPatch;
    private AttributePatch currentPatch;

    public AttributePatchAnimationAccessor(String name, Widget widget, AttributePatch firstPatch) {
        this.name = name;
        this.widget = widget;
        this.firstPatch = firstPatch;
        this.currentPatch = this.firstPatch;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.screen.widget.attributes.animation.AttributeAnimationAccessor
    public AttributePatch get() {
        return this.currentPatch;
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.animation.AttributeAnimationAccessor
    public void set(AttributePatch value) {
        this.currentPatch = value;
        value.patch(this.widget);
        this.widget.handleAttributes();
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.animation.AttributeAnimationAccessor
    public String getName() {
        return this.name;
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.animation.AttributeAnimationAccessor
    public Class<AttributePatch> getType() {
        return AttributePatch.class;
    }
}
