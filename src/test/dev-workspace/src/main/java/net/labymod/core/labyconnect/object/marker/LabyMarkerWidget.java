package net.labymod.core.labyconnect.object.marker;

import net.labymod.api.Textures;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.util.time.TimeUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/object/marker/LabyMarkerWidget.class */
@AutoWidget
public class LabyMarkerWidget extends AbstractWidget<Widget> {
    private final MarkerObject marker;

    public LabyMarkerWidget(MarkerObject marker) {
        this.marker = marker;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        bounds().setSize(20.0f, 16.0f, ModifyReason.of(LabyMarkerWidget.class, "initialize"));
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void renderWidget(ScreenContext context) {
        super.renderWidget(context);
        Stack stack = context.stack();
        stack.push();
        ScreenCanvas renderState = context.canvas();
        renderState.submitPlayerFace(this.marker.getOwner(), bounds().getX(), bounds().getY(), 16.0f, 16.0f, -1, true);
        float pause = (float) (Math.sin(TimeUtil.getMillis() / 500.0d) * 3.0d);
        if (pause < 0.0f) {
            pause = 0.0f;
        }
        float bounce = (float) Math.abs(Math.cos(TimeUtil.getMillis() / 100.0d) * ((double) (-pause)));
        renderState.submitIcon(Textures.SpriteCommon.EXCLAMATION_MARK_LIGHT, (bounds().getX() + 20.0f) - 7.0f, bounds().getY() - bounce, 8.0f, 16.0f);
        stack.pop();
    }
}
