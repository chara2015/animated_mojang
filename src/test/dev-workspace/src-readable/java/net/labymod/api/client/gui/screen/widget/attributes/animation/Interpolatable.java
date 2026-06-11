package net.labymod.api.client.gui.screen.widget.attributes.animation;

import net.labymod.api.client.gui.screen.widget.attributes.animation.Interpolatable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/attributes/animation/Interpolatable.class */
public interface Interpolatable<I extends Interpolatable<I>> {
    I interpolate(CubicBezier cubicBezier, I i, long j, long j2, long j3);
}
