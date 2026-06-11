package net.labymod.core.client.gui.hud.hudwidget;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.SimpleHudWidget;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorPickerWidget;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.network.server.ServerDisconnectEvent;
import net.labymod.api.event.labymod.serverapi.EconomyUpdateEvent;
import net.labymod.api.uri.URIParser;
import net.labymod.api.util.Color;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.serverapi.core.model.display.EconomyDisplay;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/EconomyDisplayHudWidget.class */
@SpriteSlot(x = 7, y = 5)
public class EconomyDisplayHudWidget extends SimpleHudWidget<EconomyDisplayHudWidgetConfig> {
    private static final Icon DEFAULT_BANK_ICON = Icon.texture(ResourceLocation.create("labymod", "textures/hudwidgets/economy/bank.png"));
    private static final Icon DEFAULT_CASH_ICON = Icon.texture(ResourceLocation.create("labymod", "textures/hudwidgets/economy/cash.png"));
    private static final Map<String, EconomyDisplayWrapper> DUMMY_ECONOMIES = new HashMap();
    private final Map<String, EconomyDisplayWrapper> economies;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/EconomyDisplayHudWidget$EconomyDisplayHudWidgetConfig.class */
    public static class EconomyDisplayHudWidgetConfig extends HudWidgetConfig {

        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> interpolateBalance = new ConfigProperty<>(true);

        @ColorPickerWidget.ColorPickerSetting
        private final ConfigProperty<Color> defaultColor = new ConfigProperty<>(Color.WHITE);

        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> bankUseOwnColor = new ConfigProperty<>(false);

        @ColorPickerWidget.ColorPickerSetting
        @SettingRequires("bankUseOwnColor")
        private final ConfigProperty<Color> bankColor = new ConfigProperty<>(Color.WHITE);

        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> cashUseOwnColor = new ConfigProperty<>(false);

        @ColorPickerWidget.ColorPickerSetting
        @SettingRequires("cashUseOwnColor")
        private final ConfigProperty<Color> cashColor = new ConfigProperty<>(Color.WHITE);
    }

    static {
        DUMMY_ECONOMIES.put("bank", new EconomyDisplayWrapper(new EconomyDisplay("bank", true, 6459.0d, (String) null, (EconomyDisplay.DecimalFormat) null)));
        DUMMY_ECONOMIES.put("cash", new EconomyDisplayWrapper(new EconomyDisplay("cash", true, 2369.0d, (String) null, (EconomyDisplay.DecimalFormat) null)));
    }

    public EconomyDisplayHudWidget() {
        super("economy_display", EconomyDisplayHudWidgetConfig.class);
        this.economies = new HashMap();
        bindCategory(HudWidgetCategory.INGAME);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.SimpleHudWidget
    public void render(SimpleHudWidget.RenderPhase phase, ScreenContext context, boolean isEditorContext, HudSize size) {
        Map<String, EconomyDisplayWrapper> economies = (!isEditorContext || isVisibleInGame()) ? this.economies : DUMMY_ECONOMIES;
        ScreenCanvas renderState = context.canvas();
        float y = 2.0f;
        float widgetWidth = size.getActualWidth();
        boolean interpolate = ((EconomyDisplayHudWidgetConfig) this.config).interpolateBalance.get().booleanValue();
        for (EconomyDisplayWrapper display : economies.values()) {
            if (display.economy.isVisible()) {
                ConfigProperty<Color> colorProperty = ((EconomyDisplayHudWidgetConfig) this.config).defaultColor;
                String key = display.economy.getKey();
                if (key.equals("bank") && ((EconomyDisplayHudWidgetConfig) this.config).bankUseOwnColor.get().booleanValue()) {
                    colorProperty = ((EconomyDisplayHudWidgetConfig) this.config).bankColor;
                } else if (key.equals("cash") && ((EconomyDisplayHudWidgetConfig) this.config).cashUseOwnColor.get().booleanValue()) {
                    colorProperty = ((EconomyDisplayHudWidgetConfig) this.config).cashColor;
                }
                RenderableComponent text = RenderableComponent.of(display.component(interpolate));
                float iconSize = (text.getHeight() - 1.0f) * 2.0f;
                float textWidth = text.getWidth() * 2.0f;
                boolean right = anchor().isRight();
                float iconX = right ? (widgetWidth - 1.0f) - iconSize : 1.0f;
                float textX = right ? (((widgetWidth - 1.0f) - iconSize) - textWidth) - 4.0f : iconSize + 4.0f + 1.0f;
                Icon icon = display.icon();
                if (phase.canRender()) {
                    renderState.submitIcon(icon, iconX, y, iconSize, iconSize);
                    renderState.submitRenderableComponent(text, textX, y, colorProperty.get().get(), 2.0f, 1);
                }
                float width = iconSize + textWidth + 4.0f;
                widgetWidth = Math.max(widgetWidth, width);
                y = y + (text.getHeight() * 2.0f) + 2.0f;
            }
        }
        size.setHeight(y - 3.0f);
        size.setWidth(widgetWidth);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public boolean isVisibleInGame() {
        for (EconomyDisplayWrapper value : this.economies.values()) {
            if (value.economy.isVisible()) {
                return true;
            }
        }
        return false;
    }

    @Subscribe
    public void onServerLeave(ServerDisconnectEvent event) {
        this.economies.clear();
    }

    @Subscribe(126)
    public void onEconomyUpdate(EconomyUpdateEvent event) {
        if (event.isCancelled()) {
            return;
        }
        EconomyDisplay economy = event.economy();
        if (ThreadSafe.isRenderThread()) {
            applyEconomyUpdate(economy);
        } else {
            ThreadSafe.executeOnRenderThread(() -> {
                applyEconomyUpdate(economy);
            });
        }
    }

    private void applyEconomyUpdate(EconomyDisplay economy) {
        EconomyDisplayWrapper display = this.economies.computeIfAbsent(economy.getKey(), key -> {
            return new EconomyDisplayWrapper(economy);
        });
        display.economy = economy;
        boolean changed = false;
        double balance = economy.getBalance();
        if (display.balance != balance) {
            changed = true;
            display.balance = balance;
            display.balanceUpdatedAt = TimeUtil.getCurrentTimeMillis();
        }
        String format = null;
        double divisor = Double.NaN;
        EconomyDisplay.DecimalFormat decimalFormat = economy.getDecimalFormat();
        if (decimalFormat != null) {
            format = decimalFormat.format();
            divisor = decimalFormat.divisor();
        }
        if (format != display.format || divisor != display.divisor) {
            changed = true;
            display.format = format;
            display.divisor = divisor;
            display.decimalFormat = format == null ? null : new DecimalFormat(format);
        }
        String iconUrl = economy.getIconUrl();
        if (iconUrl != null && !URIParser.isHttpUrl(iconUrl)) {
            iconUrl = null;
        }
        if (iconUrl != null && !iconUrl.equals(display.iconUrl)) {
            display.iconUrl = iconUrl;
            display.icon = null;
        } else if (iconUrl == null && display.iconUrl != null) {
            display.iconUrl = null;
            display.icon = null;
        }
        if (changed) {
            display.component = null;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/EconomyDisplayHudWidget$EconomyDisplayWrapper.class */
    private static class EconomyDisplayWrapper {
        private static final long INTERPOLATION_DURATION = 1000;
        private EconomyDisplay economy;
        private Icon icon;
        private String iconUrl;
        private Component component;
        private double balance;
        private double prevBalance;
        private long balanceUpdatedAt = TimeUtil.getCurrentTimeMillis();
        private DecimalFormat decimalFormat;
        private String format;
        private double divisor;

        public EconomyDisplayWrapper(EconomyDisplay economy) {
            this.economy = economy;
            this.balance = economy.getBalance();
            String iconUrl = economy.getIconUrl();
            this.iconUrl = URIParser.isHttpUrl(iconUrl) ? iconUrl : null;
            EconomyDisplay.DecimalFormat decimalFormat = economy.getDecimalFormat();
            if (decimalFormat != null) {
                this.format = decimalFormat.format();
                this.divisor = decimalFormat.divisor();
                this.decimalFormat = this.format == null ? null : new DecimalFormat(this.format);
            }
        }

        public Icon icon() {
            if (this.icon == null) {
                if (this.iconUrl == null) {
                    if (this.economy.getKey().equals("bank")) {
                        this.icon = EconomyDisplayHudWidget.DEFAULT_BANK_ICON;
                    } else {
                        this.icon = EconomyDisplayHudWidget.DEFAULT_CASH_ICON;
                    }
                } else {
                    this.icon = Icon.url(this.iconUrl);
                }
            }
            return this.icon;
        }

        private double getInterpolatedBalance() {
            long timePassed = TimeUtil.getCurrentTimeMillis() - this.balanceUpdatedAt;
            if (timePassed > 1000) {
                return this.balance;
            }
            double input = (Math.min(1000L, timePassed) / 1000.0d) * 4.0d;
            double sigmoid = 1.0d / (1.0d + Math.exp(((-input) * 2.0d) + 4.0d));
            double economyDifference = this.prevBalance - this.balance;
            return Math.round(this.prevBalance - (economyDifference * sigmoid));
        }

        public Component component(boolean interpolated) {
            String value;
            double balance = this.balance;
            if (interpolated) {
                double interpolatedBalance = getInterpolatedBalance();
                if (interpolatedBalance != this.prevBalance) {
                    this.component = null;
                    balance = interpolatedBalance;
                }
            }
            if (this.component == null) {
                if (this.decimalFormat != null && this.divisor > 0.0d) {
                    double dividedBalance = balance / this.divisor;
                    value = this.decimalFormat.format(dividedBalance);
                } else if (balance == ((int) balance)) {
                    value = String.valueOf((int) balance);
                } else {
                    value = String.valueOf(balance);
                }
                this.prevBalance = balance;
                this.component = Component.text(value);
            }
            return this.component;
        }
    }
}
