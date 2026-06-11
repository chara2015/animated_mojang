package net.labymod.api.client.controller;

import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/controller/ZoomTransitionType.class */
public enum ZoomTransitionType {
    NO_TRANSITION(null),
    LINEAR(CubicBezier.LINEAR),
    EASE_IN(CubicBezier.EASE_IN),
    EASE_OUT(CubicBezier.EASE_OUT),
    EASE_IN_OUT(CubicBezier.EASE_IN_OUT),
    BOUNCE(CubicBezier.BOUNCE);

    private final CubicBezier cubicBezier;

    ZoomTransitionType(CubicBezier cubicBezier) {
        this.cubicBezier = cubicBezier;
    }

    public CubicBezier getCubicBezier() {
        return this.cubicBezier;
    }
}
