package net.labymod.core.client.gui.hud.hudwidget.text;

import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.key.tracker.KeyTracker;
import net.labymod.api.client.gui.screen.key.tracker.RealTimeKeyTracker;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.input.MouseButtonEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/text/ClickTestHudWidget.class */
@SpriteSlot(x = 0, y = 4)
public class ClickTestHudWidget extends TextHudWidget<ClickTestHudWidgetConfig> {
    private TextLine singleLine;
    private TextLine leftLine;
    private TextLine rightLine;
    private KeyTracker leftTracker;
    private KeyTracker rightTracker;

    public ClickTestHudWidget() {
        super("click_test", ClickTestHudWidgetConfig.class);
        bindCategory(HudWidgetCategory.INGAME);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget, net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void load(ClickTestHudWidgetConfig config) {
        super.load(config);
        createTrackers();
        this.singleLine = super.createLine("Clicks", "0 | 0");
        this.leftLine = super.createLine("Left Clicks", (Object) 0);
        this.rightLine = super.createLine("Right Clicks", (Object) 0);
        boolean singleLine = config.singleLine.get().booleanValue();
        boolean displayAtZero = config.displayAtZeroSpeed.get().booleanValue();
        this.singleLine.setState(singleLine ? displayAtZero ? TextLine.State.VISIBLE : TextLine.State.HIDDEN : TextLine.State.DISABLED);
        this.leftLine.setState(singleLine ? TextLine.State.DISABLED : displayAtZero ? TextLine.State.VISIBLE : TextLine.State.HIDDEN);
        this.rightLine.setState(singleLine ? TextLine.State.DISABLED : displayAtZero ? TextLine.State.VISIBLE : TextLine.State.HIDDEN);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void onTick(boolean isEditorContext) {
        TextLine.State state;
        TextLine.State state2;
        TextLine.State state3;
        super.onTick(isEditorContext);
        int leftClicks = this.leftTracker.getClicksPerSecond();
        int rightClicks = this.rightTracker.getClicksPerSecond();
        boolean updated = this.leftLine.updateAndFlush(Integer.valueOf(leftClicks));
        if (this.rightLine.updateAndFlush(Integer.valueOf(rightClicks))) {
            updated = true;
        }
        if (updated) {
            this.singleLine.updateAndFlush(leftClicks + " | " + rightClicks);
        }
        boolean singleLine = ((ClickTestHudWidgetConfig) this.config).singleLine.get().booleanValue();
        if (((ClickTestHudWidgetConfig) this.config).displayAtZeroSpeed.get().booleanValue()) {
            this.singleLine.setState(singleLine ? TextLine.State.VISIBLE : TextLine.State.DISABLED);
            this.leftLine.setState(singleLine ? TextLine.State.DISABLED : TextLine.State.VISIBLE);
            this.rightLine.setState(singleLine ? TextLine.State.DISABLED : TextLine.State.VISIBLE);
            return;
        }
        TextLine textLine = this.leftLine;
        if (singleLine) {
            state = TextLine.State.DISABLED;
        } else if (leftClicks == 0) {
            state = TextLine.State.HIDDEN;
        } else {
            state = TextLine.State.VISIBLE;
        }
        textLine.setState(state);
        TextLine textLine2 = this.rightLine;
        if (singleLine) {
            state2 = TextLine.State.DISABLED;
        } else if (rightClicks == 0) {
            state2 = TextLine.State.HIDDEN;
        } else {
            state2 = TextLine.State.VISIBLE;
        }
        textLine2.setState(state2);
        TextLine textLine3 = this.singleLine;
        if (singleLine) {
            if (leftClicks == 0 && rightClicks == 0) {
                state3 = TextLine.State.HIDDEN;
            } else {
                state3 = TextLine.State.VISIBLE;
            }
        } else {
            state3 = TextLine.State.DISABLED;
        }
        textLine3.setState(state3);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget, net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public boolean isVisibleInGame() {
        this.leftTracker.update();
        this.rightTracker.update();
        return (!((ClickTestHudWidgetConfig) this.config).displayAtZeroSpeed.get().booleanValue() && this.leftTracker.getClicksPerSecond() == 0 && this.rightTracker.getClicksPerSecond() == 0) ? false : true;
    }

    @Subscribe
    public void onMouseButton(MouseButtonEvent event) {
        if (event.action() != MouseButtonEvent.Action.CLICK || !isEnabled()) {
            return;
        }
        MouseButton button = event.button();
        if (button == MouseButton.LEFT) {
            this.leftTracker.press();
        } else if (button == MouseButton.RIGHT) {
            this.rightTracker.press();
        }
    }

    private void createTrackers() {
        this.leftTracker = RealTimeKeyTracker.of(MouseButton.LEFT);
        this.rightTracker = RealTimeKeyTracker.of(MouseButton.RIGHT);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/text/ClickTestHudWidget$ClickTestHudWidgetConfig.class */
    public static class ClickTestHudWidgetConfig extends TextHudWidgetConfig {

        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> displayAtZeroSpeed = new ConfigProperty<>(true);

        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> singleLine = new ConfigProperty<>(true);

        public ConfigProperty<Boolean> displayAtZeroSpeed() {
            return this.displayAtZeroSpeed;
        }

        public ConfigProperty<Boolean> singleLine() {
            return this.singleLine;
        }
    }
}
