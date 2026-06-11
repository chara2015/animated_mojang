package net.labymod.core.client.gui.hud.hudwidget;

import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.SimpleHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.background.BackgroundHudWidget;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorPickerWidget;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.world.effect.PotionEffect;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.render.overlay.IngameOverlayElementRenderEvent;
import net.labymod.api.util.bounds.area.RectangleAreaPosition;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.main.LabyMod;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/PotionEffectHudWidget.class */
@SpriteSlot(x = 1, y = 1)
public class PotionEffectHudWidget extends BackgroundHudWidget<PotionEffectHudWidgetConfig> {
    private static final PotionEffect[] DUMMY_POTIONS = {new DummyPotionEffect(1300, 3, "labymod.hudWidget.potion.dummy.name")};
    private static final PotionEffect[] EMPTY_POTIONS = new PotionEffect[0];
    private static final String MAX_DURATION = "**:**";
    private static final String INFINITE = "∞";

    public PotionEffectHudWidget() {
        super("potion", PotionEffectHudWidgetConfig.class);
        bindCategory(HudWidgetCategory.INGAME);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void initializePreConfigured(PotionEffectHudWidgetConfig config) {
        super.initializePreConfigured(config);
        config.setEnabled(true);
        config.setAreaIdentifier(RectangleAreaPosition.MIDDLE_LEFT);
        config.setX(2.0f);
        config.setY(-8.0f);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.api.client.gui.hud.hudwidget.background.BackgroundHudWidget, net.labymod.api.client.gui.hud.hudwidget.SimpleHudWidget
    public void render(SimpleHudWidget.RenderPhase phase, ScreenContext context, boolean isEditorContext, HudSize size) {
        Component componentEmpty;
        super.render(phase, context, isEditorContext, size);
        ClientPlayer player = LabyMod.getInstance().minecraft().getClientPlayer();
        PotionEffect[] potionEffects = EMPTY_POTIONS;
        if (player != null) {
            potionEffects = (PotionEffect[]) player.getActivePotionEffects().toArray(new PotionEffect[0]);
        }
        if (potionEffects.length == 0) {
            potionEffects = DUMMY_POTIONS;
        }
        int y = 0;
        int width = 0;
        boolean rightBound = this.anchor.isRight();
        ScreenCanvas renderState = context.canvas();
        for (PotionEffect potionEffect : potionEffects) {
            if (phase.canRender()) {
                potionEffect.renderIcon(context, (int) (rightBound ? (size.getActualWidth() - 18.0f) - 0 : 0), y, 18, 18);
            }
            int x = 0 + 20;
            String translationKey = potionEffect.getTranslationKey();
            Component nameComponent = Component.translatable(translationKey, new Component[0]).color(TextColor.color(((PotionEffectHudWidgetConfig) getConfig()).nameColor().get().intValue()));
            int amplifier = potionEffect.getAmplifier();
            if (amplifier != 0) {
                String enchantmentKey = "enchantment.level." + (amplifier + 1);
                if (amplifier >= 1 && amplifier <= 9) {
                    componentEmpty = Component.text(" ").append(Component.translatable(enchantmentKey, new Component[0]));
                } else {
                    componentEmpty = Component.empty();
                }
                Component amplifierComponent = componentEmpty;
                TextColor color = TextColor.color(((PotionEffectHudWidgetConfig) getConfig()).amplifierColor().get().intValue());
                nameComponent = nameComponent.append(amplifierComponent.color(color));
            }
            String time = getTime(potionEffect);
            Component timeComponent = Component.text(time).color(TextColor.color(((PotionEffectHudWidgetConfig) getConfig()).durationColor().get().intValue()));
            RenderableComponent renderHeader = RenderableComponent.of(nameComponent);
            RenderableComponent renderTime = RenderableComponent.of(timeComponent);
            if (phase.canRender()) {
                renderState.submitRenderableComponent(renderHeader, rightBound ? (size.getActualWidth() - x) - renderHeader.getWidth() : x, y + 1, -1, 1);
            }
            int y2 = (int) (y + renderHeader.getHeight());
            int width2 = (int) Math.max(width, x + renderHeader.getWidth());
            if (phase.canRender()) {
                renderState.submitRenderableComponent(renderTime, rightBound ? (size.getActualWidth() - x) - renderTime.getWidth() : x, y2 + 1, -1, 1);
            }
            y = (int) (y2 + renderTime.getHeight());
            width = (int) Math.max(width2, x + renderTime.getWidth());
        }
        size.set(width, y);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public boolean isVisibleInGame() {
        ClientPlayer player = LabyMod.getInstance().minecraft().getClientPlayer();
        return (player == null || player.getActivePotionEffects().isEmpty()) ? false : true;
    }

    @Subscribe
    public void onPotionEffectsRender(IngameOverlayElementRenderEvent event) {
        if (isEnabled() && event.elementType() == IngameOverlayElementRenderEvent.OverlayElementType.POTION_EFFECTS && isVisibleInGame()) {
            event.setCancelled(true);
        }
    }

    private String getTime(PotionEffect effect) {
        if (effect.isInfiniteDuration()) {
            return INFINITE;
        }
        if (effect.hasMaxDuration()) {
            return MAX_DURATION;
        }
        String format = TimeUtil.formatTickDuration(effect.getDuration());
        if (!((PotionEffectHudWidgetConfig) this.config).leadingZero().get().booleanValue() && format.startsWith("0")) {
            format = format.substring(1);
        }
        return format;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/PotionEffectHudWidget$DummyPotionEffect.class */
    private static class DummyPotionEffect implements PotionEffect {
        private final int duration;
        private final int amplifier;
        private final String translationKey;

        public DummyPotionEffect(int duration, int amplifier, String translationKey) {
            this.duration = duration;
            this.amplifier = amplifier;
            this.translationKey = translationKey;
        }

        @Override // net.labymod.api.client.world.effect.PotionEffect
        public int getDuration() {
            return this.duration;
        }

        @Override // net.labymod.api.client.world.effect.PotionEffect
        public int getAmplifier() {
            return this.amplifier;
        }

        @Override // net.labymod.api.client.world.effect.PotionEffect
        public String getTranslationKey() {
            return this.translationKey;
        }

        @Override // net.labymod.api.client.world.effect.PotionEffect
        public Icon getIcon() {
            return Icon.sprite(Textures.SURVIVAL_INVENTORY_BACKGROUND, 72, 198, 18, 18, 256, 256);
        }

        @Override // net.labymod.api.client.world.effect.PotionEffect
        public void renderIcon(ScreenContext context, int x, int y, int width, int height) {
            Icon icon = getIcon();
            if (icon == null) {
                return;
            }
            context.canvas().submitIcon(icon, x, y, width, height);
        }

        @Override // net.labymod.api.client.world.effect.PotionEffect
        public boolean hasMaxDuration() {
            return false;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/PotionEffectHudWidget$PotionEffectHudWidgetConfig.class */
    public static class PotionEffectHudWidgetConfig extends BackgroundHudWidget.BackgroundHudWidgetConfig {

        @ColorPickerWidget.ColorPickerSetting
        private final ConfigProperty<Integer> nameColor = new ConfigProperty<>(-1);

        @ColorPickerWidget.ColorPickerSetting
        private final ConfigProperty<Integer> durationColor = new ConfigProperty<>(-1);

        @ColorPickerWidget.ColorPickerSetting
        private final ConfigProperty<Integer> amplifierColor = new ConfigProperty<>(-1);

        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> leadingZero = new ConfigProperty<>(false);

        public ConfigProperty<Integer> nameColor() {
            return this.nameColor;
        }

        public ConfigProperty<Integer> durationColor() {
            return this.durationColor;
        }

        public ConfigProperty<Integer> amplifierColor() {
            return this.amplifierColor;
        }

        public ConfigProperty<Boolean> leadingZero() {
            return this.leadingZero;
        }
    }
}
