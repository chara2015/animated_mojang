package net.labymod.v1_12_2.client.gui;

import net.labymod.api.client.gui.mouse.MutableMouse;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/gui/GuiSliderAccessor.class */
public interface GuiSliderAccessor {
    boolean isDragging();

    float getMinValue();

    float getMaxValue();

    float getValue();

    float getStep();

    void labymod$mouseDragged(bib bibVar, MutableMouse mutableMouse);
}
