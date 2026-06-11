package net.labymod.core.client.gui.hud.hudwidget.text;

import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.configuration.loader.annotation.IntroducedIn;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.position.Position;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/text/LightLevelHudWidget.class */
@SpriteSlot(x = 0, y = 5)
@IntroducedIn("4.1.0")
public class LightLevelHudWidget extends TextHudWidget<TextHudWidgetConfig> {
    private TextLine lightLevelLine;

    public LightLevelHudWidget() {
        super("light_level");
        bindCategory(HudWidgetCategory.INGAME);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget, net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void load(TextHudWidgetConfig config) {
        super.load(config);
        this.lightLevelLine = super.createLine("Light Level", "?");
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void onTick(boolean isEditorContext) {
        Entity entity = this.labyAPI.minecraft().getCameraEntity();
        ClientWorld world = this.labyAPI.minecraft().clientWorld();
        if (entity == null || world == null) {
            return;
        }
        Position position = entity.position();
        int x = MathHelper.floor(position.getX());
        int y = MathHelper.floor(position.getY());
        int z = MathHelper.floor(position.getZ());
        BlockState blockState = world.getBlockState(x, y, z);
        int level = blockState.getLightLevel();
        this.lightLevelLine.updateAndFlush(Integer.valueOf(level));
    }
}
