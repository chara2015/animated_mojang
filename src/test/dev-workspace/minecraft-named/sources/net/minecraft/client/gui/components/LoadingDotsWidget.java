package net.minecraft.client.gui.components;

import java.util.Objects;
import net.minecraft.client.gui.ComponentPath;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.navigation.FocusNavigationEvent;
import net.minecraft.client.gui.screens.LoadingDotsText;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.util.CommonColors;
import net.minecraft.util.Util;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/components/LoadingDotsWidget.class */
public class LoadingDotsWidget extends AbstractWidget {
    private final Font font;

    /* JADX WARN: Illegal instructions before constructor call */
    public LoadingDotsWidget(Font $$0, Component $$1) {
        int iWidth = $$0.width($$1);
        Objects.requireNonNull($$0);
        super(0, 0, iWidth, 9 * 3, $$1);
        this.font = $$0;
    }

    @Override // net.minecraft.client.gui.components.AbstractWidget
    protected void renderWidget(GuiGraphics $$0, int $$1, int $$2, float $$3) {
        int $$4 = getX() + (getWidth() / 2);
        int $$5 = getY() + (getHeight() / 2);
        Component $$6 = getMessage();
        Font font = this.font;
        int iWidth = $$4 - (this.font.width($$6) / 2);
        Objects.requireNonNull(this.font);
        $$0.drawString(font, $$6, iWidth, $$5 - 9, -1);
        String $$7 = LoadingDotsText.get(Util.getMillis());
        Font font2 = this.font;
        int iWidth2 = $$4 - (this.font.width($$7) / 2);
        Objects.requireNonNull(this.font);
        $$0.drawString(font2, $$7, iWidth2, $$5 + 9, CommonColors.GRAY);
    }

    @Override // net.minecraft.client.gui.components.AbstractWidget
    protected void updateWidgetNarration(NarrationElementOutput $$0) {
    }

    @Override // net.minecraft.client.gui.components.AbstractWidget
    public void playDownSound(SoundManager $$0) {
    }

    @Override // net.minecraft.client.gui.components.AbstractWidget, net.minecraft.client.gui.narration.NarratableEntry
    public boolean isActive() {
        return false;
    }

    @Override // net.minecraft.client.gui.components.AbstractWidget, net.minecraft.client.gui.components.events.GuiEventListener, net.minecraft.client.gui.components.events.ContainerEventHandler
    public ComponentPath nextFocusPath(FocusNavigationEvent $$0) {
        return null;
    }
}
