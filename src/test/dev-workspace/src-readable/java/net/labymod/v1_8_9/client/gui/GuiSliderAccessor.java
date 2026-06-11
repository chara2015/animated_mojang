package net.labymod.v1_8_9.client.gui;

import net.labymod.api.client.gui.mouse.MutableMouse;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/gui/GuiSliderAccessor.class */
public interface GuiSliderAccessor {
    boolean isDragging();

    float getMinValue();

    float getMaxValue();

    float getValue();

    float getStep();

    void labymod$mouseDragged(ave aveVar, MutableMouse mutableMouse);
}
