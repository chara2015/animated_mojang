package net.labymod.core.client.gui.hud.hudwidget.text;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.text.Formatting;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.util.math.MathHelper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/text/FDirectionHudWidget.class */
@SpriteSlot(x = 6)
public class FDirectionHudWidget extends TextHudWidget<FDirectionHudWidgetConfig> {
    private TextLine directionTextLine;
    private float lastYaw;

    public FDirectionHudWidget() {
        super("fdirection", FDirectionHudWidgetConfig.class);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget, net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void load(FDirectionHudWidgetConfig config) {
        super.load(config);
        this.directionTextLine = super.createLine("F", (Object) 0);
        bindCategory(HudWidgetCategory.INGAME);
        this.lastYaw = 0.0f;
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void onTick(boolean isEditorContext) {
        ClientPlayer player = this.labyAPI.minecraft().getClientPlayer();
        if (player == null) {
            return;
        }
        float yaw = player.getRotationYaw();
        float f = getFValue(yaw);
        if (this.lastYaw == f) {
            return;
        }
        this.lastYaw = f;
        this.directionTextLine.updateAndFlush(createComponent(f));
    }

    /* JADX WARN: Multi-variable type inference failed */
    private Component createComponent(float fValue) {
        Component component = Component.empty();
        if (((FDirectionHudWidgetConfig) getConfig()).directionNumber().get().booleanValue()) {
            component = component.append(Component.text(String.format(Locale.ROOT, "%.1f ", Float.valueOf(fValue))));
        }
        Boolean cardinalPoints = ((FDirectionHudWidgetConfig) getConfig()).cardinalPoints().get();
        Boolean xzDirection = ((FDirectionHudWidgetConfig) getConfig()).xzDirection().get();
        if (cardinalPoints.booleanValue() || xzDirection.booleanValue()) {
            XZDRange range = XZDRange.getRangeByF(fValue);
            List<XZDRange.DisplayedDirection> displayedDirections = Arrays.asList(range.getDisplayed());
            if (cardinalPoints.booleanValue()) {
                component = appendCardinalPoints(component, displayedDirections, ((FDirectionHudWidgetConfig) getConfig()).shortenNames().get().booleanValue());
            }
            if (xzDirection.booleanValue()) {
                component = appendXZDirection(component, displayedDirections);
            }
        }
        return component;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private Component appendXZDirection(Component component, List<XZDRange.DisplayedDirection> displayedDirections) {
        Component directionComponent = Component.empty();
        int valueColor = ((FDirectionHudWidgetConfig) getConfig()).valueColor().get().get();
        for (int i = 0; i < displayedDirections.size(); i++) {
            if (i > 0) {
                directionComponent = directionComponent.append(Component.text(", ", TextColor.color(valueColor)));
            }
            directionComponent = directionComponent.append(Component.text(displayedDirections.get(i).getText(), TextColor.color(valueColor)));
        }
        Formatting formatting = ((FDirectionHudWidgetConfig) getConfig()).formatting().get();
        boolean enclosed = formatting.isEnclosed();
        if (enclosed) {
            directionComponent = formatting.build(directionComponent, Component.empty(), !anchor().isRight(), ((FDirectionHudWidgetConfig) getConfig()).bracketColor().get().get());
        } else {
            component = component.append(Component.space());
        }
        return component.append(Component.text(" ")).append(directionComponent);
    }

    private float getFValue(float yaw) {
        float f = MathHelper.wrapDegrees(yaw);
        if (f <= 0.0f) {
            f += 360.0f;
        }
        float f2 = (float) (Math.round(((double) (f / 88.0f)) * 10.0d) / 10.0d);
        if (f2 >= 4.0f) {
            f2 = 0.0f;
        }
        return f2;
    }

    private Component appendCardinalPoints(Component component, List<XZDRange.DisplayedDirection> list, boolean isShortname) {
        for (int i = 0; i < list.size(); i++) {
            String text = "";
            if (i > 0) {
                text = text + "/";
            }
            component = component.append(Component.text(text + list.get(i).getName(isShortname)));
        }
        return component;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/text/FDirectionHudWidget$XZDRange.class */
    enum XZDRange {
        ZPLUS(0.0d, 0.3d, DisplayedDirection.ZPLUS),
        ZPLUS_XMINUS(0.3d, 0.8d, DisplayedDirection.ZPLUS, DisplayedDirection.XMINUS),
        XMINUS(0.8d, 1.4d, DisplayedDirection.XMINUS),
        XMINUS_ZMINUS(1.4d, 1.8d, DisplayedDirection.ZMINUS, DisplayedDirection.XMINUS),
        ZMINUS(1.8d, 2.4d, DisplayedDirection.ZMINUS),
        ZMINUS_XPLUS(2.4d, 2.8d, DisplayedDirection.ZMINUS, DisplayedDirection.XPLUS),
        XPLUS(2.8d, 3.4d, DisplayedDirection.XPLUS),
        XPLUS_ZPLUS(3.4d, 3.8d, DisplayedDirection.ZPLUS, DisplayedDirection.XPLUS),
        ZPLUS_2(3.8d, 4.0d, DisplayedDirection.ZPLUS);

        private final double min;
        private final double max;
        private final DisplayedDirection[] displayed;

        XZDRange(double min, double max, DisplayedDirection... displayed) {
            this.min = min;
            this.max = max;
            this.displayed = displayed;
        }

        public static XZDRange getRangeByF(double f) {
            if (f >= 4.0d) {
                return ZPLUS_2;
            }
            for (XZDRange range : values()) {
                if (f >= range.min && f < range.max) {
                    return range;
                }
            }
            return ZPLUS;
        }

        public DisplayedDirection[] getDisplayed() {
            return this.displayed;
        }

        /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/text/FDirectionHudWidget$XZDRange$DisplayedDirection.class */
        enum DisplayedDirection {
            XPLUS("X+", "East", "E"),
            XMINUS("X-", "West", "W"),
            ZMINUS("Z-", "North", "N"),
            ZPLUS("Z+", "South", "S");

            private final String text;
            private final String longName;
            private final String shortName;

            DisplayedDirection(String text, String longName, String shortName) {
                this.text = text;
                this.longName = longName;
                this.shortName = shortName;
            }

            public String getText() {
                return this.text;
            }

            public String getName(boolean isShortName) {
                return isShortName ? this.shortName : this.longName;
            }
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/text/FDirectionHudWidget$FDirectionHudWidgetConfig.class */
    public static class FDirectionHudWidgetConfig extends TextHudWidgetConfig {

        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> directionNumber = new ConfigProperty<>(true);

        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> cardinalPoints = new ConfigProperty<>(true);

        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> xzDirection = new ConfigProperty<>(true);

        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> shortenNames = new ConfigProperty<>(true);

        public ConfigProperty<Boolean> directionNumber() {
            return this.directionNumber;
        }

        public ConfigProperty<Boolean> cardinalPoints() {
            return this.cardinalPoints;
        }

        public ConfigProperty<Boolean> xzDirection() {
            return this.xzDirection;
        }

        public ConfigProperty<Boolean> shortenNames() {
            return this.shortenNames;
        }
    }
}
