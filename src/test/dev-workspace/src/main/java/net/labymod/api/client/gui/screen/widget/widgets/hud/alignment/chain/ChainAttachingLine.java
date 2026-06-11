package net.labymod.api.client.gui.screen.widget.widgets.hud.alignment.chain;

import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.widgets.hud.alignment.AlignmentLine;
import net.labymod.api.client.gui.screen.widget.widgets.hud.alignment.ChainAlignmentSide;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.color.format.ColorFormat;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/hud/alignment/chain/ChainAttachingLine.class */
public class ChainAttachingLine extends AlignmentLine {
    private final ChainAlignmentSide side;

    public ChainAttachingLine(ChainAlignmentSide side) {
        this.side = side;
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.hud.alignment.AlignmentLine
    public void render(ScreenContext context, Rectangle chain) {
        super.render(context, chain);
        float opacity = getOpacity(context.getTickDelta());
        if (opacity > 0.0f) {
            context.canvas().submitRelativeRect(0.0f, this.side == ChainAlignmentSide.TOP ? 0.0f : chain.getHeight(), chain.getWidth(), 1.0f, ColorFormat.ARGB32.pack(255, 255, 255, (int) (opacity * 255.0f)));
        }
    }
}
