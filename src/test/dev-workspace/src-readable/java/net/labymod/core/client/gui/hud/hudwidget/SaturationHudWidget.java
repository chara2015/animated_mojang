package net.labymod.core.client.gui.hud.hudwidget;

import net.labymod.api.Laby;
import net.labymod.api.Namespaces;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.binding.dropzone.NamedHudWidgetDropzones;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.widget.widgets.hud.HudWidgetWidget;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.util.math.MathHelper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/SaturationHudWidget.class */
@SpriteSlot(x = 1, y = 2)
public class SaturationHudWidget extends HudWidget<SaturationHudWidgetConfig> {
    private static final ResourceLocation FOOD_EMPTY_SPRITE = ResourceLocation.create(Namespaces.MINECRAFT, "hud/food_empty");
    private static final ResourceLocation FOOD_HALF_SPRITE = ResourceLocation.create(Namespaces.MINECRAFT, "hud/food_half");
    private static final ResourceLocation FOOD_FULL_SPRITE = ResourceLocation.create(Namespaces.MINECRAFT, "hud/food_full");
    private static final float DUMMY_SATURATION = 15.0f;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/SaturationHudWidget$SaturationHudWidgetConfig.class */
    public static class SaturationHudWidgetConfig extends HudWidgetConfig {
    }

    public SaturationHudWidget() {
        super("saturation", SaturationHudWidgetConfig.class);
        bindCategory(HudWidgetCategory.INGAME);
        bindDropzones(NamedHudWidgetDropzones.SATURATION);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void render(ScreenContext context, boolean isEditorContext, HudSize size) {
        Player player = this.labyAPI.minecraft().getClientPlayer();
        float saturation = player == null ? DUMMY_SATURATION : player.foodData().getSaturationLevel();
        if (saturation <= 0.0f) {
            saturation = 15.0f;
        }
        int max = isEditorContext ? 10 : MathHelper.ceil(saturation / 2.0f);
        ScreenCanvas renderState = context.canvas();
        ResourceLocation location = this.labyAPI.minecraft().textures().iconsTexture();
        TextureAtlas atlas = Laby.references().atlasRegistry().getAtlas(location);
        int roundedSaturation = (int) Math.ceil(saturation * 2.0f);
        for (int tile = 0; tile < max; tile++) {
            renderSaturationIcon(renderState, atlas, roundedSaturation, tile);
        }
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void updateSize(HudWidgetWidget widget, boolean editorContext, HudSize size) {
        size.set(80, 9);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public boolean isVisibleInGame() {
        GameMode gameMode = this.labyAPI.minecraft().gameMode();
        Player player = this.labyAPI.minecraft().getClientPlayer();
        return (player == null || gameMode == GameMode.CREATIVE || gameMode == GameMode.SPECTATOR || player.foodData().getSaturationLevel() <= 0.0f || (player.getVehicle() instanceof LivingEntity) || this.labyAPI.minecraft().options().isHideGUI()) ? false : true;
    }

    private void renderSaturationIcon(ScreenCanvas renderState, TextureAtlas atlas, int saturation, int tile) {
        int index = (tile * 2) + 1;
        boolean isFull = index < saturation;
        boolean isHalf = index == saturation;
        float width = tile * 8;
        float foodX = 72.0f - width;
        renderState.submitGuiSprite(atlas, FOOD_EMPTY_SPRITE, (int) foodX, 0.0f, 9.0f, 9.0f, -1);
        if (isFull) {
            renderState.submitGuiSprite(atlas, FOOD_FULL_SPRITE, (int) foodX, 0.0f, 9.0f, 9.0f, -1);
        }
        if (isHalf) {
            renderState.submitGuiSprite(atlas, FOOD_HALF_SPRITE, (int) foodX, 0.0f, 9.0f, 9.0f, -1);
        }
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public boolean renderInDebug() {
        return true;
    }
}
