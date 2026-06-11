package net.labymod.core.client.gui.hud.hudwidget;

import java.util.Iterator;
import java.util.Set;
import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.Namespaces;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gfx.pipeline.texture.atlas.Atlases;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.binding.dropzone.NamedHudWidgetDropzones;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.SimpleHudWidget;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.render.font.ComponentRenderer;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.util.parity.VanillaParityUtil;
import net.labymod.api.client.world.BossBar;
import net.labymod.api.client.world.BossBarColor;
import net.labymod.api.client.world.BossBarOverlay;
import net.labymod.api.client.world.BossBarProgressHandler;
import net.labymod.api.client.world.BossBarRegistry;
import net.labymod.api.client.world.DynamicBossBarProgressHandler;
import net.labymod.api.configuration.loader.annotation.IntroducedIn;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.annotation.SpriteTexture;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.render.overlay.IngameOverlayElementRenderEvent;
import net.labymod.api.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/BossBarHudWidget.class */
@SpriteSlot(x = 4, y = 5)
@IntroducedIn("4.1.0")
public class BossBarHudWidget extends SimpleHudWidget<BossBarHudWidgetConfig> {
    private static final ResourceLocation[] BAR_BACKGROUND_SPRITES = {create("boss_bar/pink_background"), create("boss_bar/blue_background"), create("boss_bar/red_background"), create("boss_bar/green_background"), create("boss_bar/yellow_background"), create("boss_bar/purple_background"), create("boss_bar/white_background")};
    private static final ResourceLocation[] BAR_PROGRESS_SPRITES = {create("boss_bar/pink_progress"), create("boss_bar/blue_progress"), create("boss_bar/red_progress"), create("boss_bar/green_progress"), create("boss_bar/yellow_progress"), create("boss_bar/purple_progress"), create("boss_bar/white_progress")};
    private static final ResourceLocation[] OVERLAY_BACKGROUND_SPRITES = {create("boss_bar/notched_6_background"), create("boss_bar/notched_10_background"), create("boss_bar/notched_12_background"), create("boss_bar/notched_20_background")};
    private static final ResourceLocation[] OVERLAY_PROGRESS_SPRITES = {create("boss_bar/notched_6_progress"), create("boss_bar/notched_10_progress"), create("boss_bar/notched_12_progress"), create("boss_bar/notched_20_progress")};
    private static final Set<BossBar> DUMMIES = Set.of(new DummyBossBar());
    private static final float BOSS_BAR_WIDTH = 182.0f;

    public BossBarHudWidget() {
        super("boss_bar", BossBarHudWidgetConfig.class);
        bindCategory(HudWidgetCategory.INGAME);
        bindDropzones(NamedHudWidgetDropzones.BOSS_BAR);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.SimpleHudWidget
    public void render(SimpleHudWidget.RenderPhase phase, ScreenContext context, boolean isEditorContext, HudSize size) {
        BossBarRegistry registry = Laby.references().bossBarRegistry();
        int maxHeight = this.labyAPI.minecraft().minecraftWindow().getScaledHeight() / 3;
        boolean showHealth = ((BossBarHudWidgetConfig) this.config).showBar().get().booleanValue();
        int height = 12;
        ScreenCanvas renderState = context.canvas();
        Set<BossBar> bossBars = isEditorContext ? DUMMIES : registry.getBossBars();
        Iterator<BossBar> it = bossBars.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            BossBar bossBar = it.next();
            ComponentRenderer componentRenderer = Laby.references().componentRenderer();
            float textHeight = componentRenderer.height();
            if (phase.canRender()) {
                context.pushStack();
                context.translate(0.0f, 0.0f, VanillaParityUtil.getBossBarZLevel());
                Component component = bossBar.displayName();
                float componentWidth = componentRenderer.width(component);
                float offsetX = this.anchor.getGapX(BOSS_BAR_WIDTH, componentWidth);
                renderState.submitComponent(component, offsetX, height - textHeight, -1, 1);
                if (showHealth) {
                    drawBar(context, 0, MathHelper.ceil(height), bossBar);
                }
                context.popStack();
            }
            if (showHealth) {
                height += 5;
            }
            height += 14;
            if (height >= maxHeight) {
                height = maxHeight;
                break;
            }
        }
        size.set(BOSS_BAR_WIDTH, Math.max(height - 14, 5));
    }

    @Subscribe
    public void onIngameOverlayElementRender(IngameOverlayElementRenderEvent event) {
        if (!isEnabled() || event.phase() != Phase.PRE || event.elementType() != IngameOverlayElementRenderEvent.OverlayElementType.BOSS_BAR) {
            return;
        }
        event.setCancelled(true);
    }

    private void drawBar(ScreenContext context, int x, int y, BossBar bar) {
        drawBar(context, x, y, 182, bar, BAR_BACKGROUND_SPRITES, OVERLAY_BACKGROUND_SPRITES);
        float barProgress = bar.progressHandler().getProgress();
        int progress = lerpDiscrete(barProgress, 0, 182);
        if (progress > 0) {
            drawBar(context, x, y, progress, bar, BAR_PROGRESS_SPRITES, OVERLAY_PROGRESS_SPRITES);
        }
    }

    private void drawBar(ScreenContext context, int x, int y, int progress, BossBar bar, ResourceLocation[] bars, ResourceLocation[] overlays) {
        drawBar(context, bars[bar.bossBarColor().ordinal()], x, y, progress);
        if (bar.bossBarOverlay() != BossBarOverlay.PROGRESS) {
            drawBar(context, overlays[bar.bossBarOverlay().ordinal() - 1], x, y, progress);
        }
    }

    private void drawBar(ScreenContext context, ResourceLocation spriteLocation, int x, int y, int progress) {
        TextureAtlas atlas = Laby.references().atlasRegistry().getAtlas(Atlases.BARS_ATLAS);
        ScreenCanvas renderState = context.canvas();
        renderState.submitGuiSprite(atlas, spriteLocation, x, y, progress, 5, 0, 0, 182, 5, -1);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public boolean isVisibleInGame() {
        return true;
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public boolean renderInDebug() {
        return true;
    }

    private int lerpDiscrete(float value, int start, int end) {
        int diff = end - start;
        return start + MathHelper.floor(value * (diff - 1)) + (value > 0.0f ? 1 : 0);
    }

    private static ResourceLocation create(String path) {
        return ResourceLocation.create(Namespaces.MINECRAFT, path);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/BossBarHudWidget$BossBarHudWidgetConfig.class */
    @SpriteTexture("settings/hud/hud")
    public static class BossBarHudWidgetConfig extends HudWidgetConfig {

        @SpriteSlot(x = 5, y = 5)
        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> showBar = new ConfigProperty<>(true);

        public ConfigProperty<Boolean> showBar() {
            return this.showBar;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/BossBarHudWidget$DummyBossBar.class */
    private static class DummyBossBar implements BossBar {
        private static final Component DISPLAY_NAME = Component.text("Herobrine");

        private DummyBossBar() {
        }

        @Override // net.labymod.api.client.world.BossBar
        @NotNull
        public UUID getIdentifier() {
            return UUID.randomUUID();
        }

        @Override // net.labymod.api.client.world.BossBar
        public Component displayName() {
            return DISPLAY_NAME;
        }

        @Override // net.labymod.api.client.world.BossBar
        public BossBarColor bossBarColor() {
            return BossBarColor.PINK;
        }

        @Override // net.labymod.api.client.world.BossBar
        public BossBarOverlay bossBarOverlay() {
            return BossBarOverlay.PROGRESS;
        }

        @Override // net.labymod.api.client.world.BossBar
        public BossBarProgressHandler progressHandler() {
            return new DynamicBossBarProgressHandler(t -> {
            }, () -> {
                return 0.8f;
            });
        }
    }
}
