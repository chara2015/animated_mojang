package net.labymod.api.client.render;

import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/Renderable.class */
public interface Renderable {
    boolean render(Stack stack, MutableMouse mutableMouse, float f);
}
