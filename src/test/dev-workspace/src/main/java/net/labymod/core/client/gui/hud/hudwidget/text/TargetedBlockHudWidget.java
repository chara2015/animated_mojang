package net.labymod.core.client.gui.hud.hudwidget.text;

import java.util.Locale;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.client.world.phys.hit.BlockHitResult;
import net.labymod.api.client.world.phys.hit.HitResult;
import net.labymod.api.configuration.loader.annotation.IntroducedIn;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.util.math.vector.FloatVector3;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/text/TargetedBlockHudWidget.class */
@SpriteSlot(x = 2, y = 6)
@IntroducedIn("4.4.5")
public class TargetedBlockHudWidget extends TextHudWidget<TextHudWidgetConfig> {
    private static final String COORDINATES_FORMAT = "%d, %d, %d";
    private TextLine coordinatesLine;

    public TargetedBlockHudWidget() {
        super("targetedBlock");
        bindCategory(HudWidgetCategory.INGAME);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget, net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void load(TextHudWidgetConfig config) {
        super.load(config);
        this.coordinatesLine = createLine("Targeted Block", String.format(Locale.ROOT, COORDINATES_FORMAT, 0, 0, 0));
        this.coordinatesLine.setState(TextLine.State.VISIBLE);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void onTick(boolean isEditorContext) {
        HitResult hitResult = this.labyAPI.minecraft().getHitResult();
        if (hitResult != null && hitResult.type() == HitResult.HitType.BLOCK && (hitResult instanceof BlockHitResult)) {
            BlockHitResult blockHit = (BlockHitResult) hitResult;
            FloatVector3 blockPosition = blockHit.getBlockPosition();
            if (blockPosition == null) {
                return;
            }
            int x = (int) blockPosition.getX();
            int y = (int) blockPosition.getY();
            int z = (int) blockPosition.getZ();
            this.coordinatesLine.updateAndFlush(String.format(Locale.ROOT, COORDINATES_FORMAT, Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z)));
        }
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget, net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public boolean isVisibleInGame() {
        HitResult hitResult = this.labyAPI.minecraft().getHitResult();
        return hitResult != null && hitResult.type() == HitResult.HitType.BLOCK;
    }
}
