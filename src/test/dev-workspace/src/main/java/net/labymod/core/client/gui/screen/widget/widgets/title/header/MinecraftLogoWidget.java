package net.labymod.core.client.gui.screen.widget.widgets.title.header;

import java.util.Locale;
import net.labymod.api.Laby;
import net.labymod.api.Namespaces;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.title.header.LogoWidgetAccessor;
import net.labymod.api.client.gui.screen.widget.widgets.activity.title.header.SplashWidgetAccessor;
import net.labymod.api.event.client.gui.screen.title.TitleScreenLogoInitializeEvent;
import net.labymod.api.event.client.gui.screen.title.TitleScreenSplashTextEvent;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.core.client.gui.screen.widget.widgets.title.header.type.LegacyMinecraftLogoWidget;
import net.labymod.core.client.gui.screen.widget.widgets.title.header.type.ModernMinecraftLogoWidget;
import net.labymod.core.client.resources.SplashLoader;
import net.labymod.core.event.labymod.gui.title.TitleScreenLogoPreRenderEvent;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/title/header/MinecraftLogoWidget.class */
@AutoWidget
public abstract class MinecraftLogoWidget extends AbstractWidget<Widget> implements LogoWidgetAccessor {
    private final String type;
    private Widget minecraftWidget;
    private Widget editionWidget;
    private SplashWidget splashWidget;

    @Nullable
    protected abstract Widget createMinecraftWidget();

    @Nullable
    protected abstract Widget createEditionWidget();

    public MinecraftLogoWidget(String type) {
        this.type = type;
        addId(String.format(Locale.ROOT, "%s-logo", type));
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        TitleScreenLogoInitializeEvent event = (TitleScreenLogoInitializeEvent) Laby.fireEvent(new TitleScreenLogoInitializeEvent(this.type, createMinecraftWidget(), createEditionWidget()));
        this.minecraftWidget = event.getMinecraftWidget();
        if (this.minecraftWidget != null) {
            this.minecraftWidget.addId(Namespaces.MINECRAFT, String.format(Locale.ROOT, "minecraft-%s", this.type));
            addChild(this.minecraftWidget);
        }
        this.editionWidget = event.getEditionWidget();
        if (this.editionWidget != null) {
            this.editionWidget.addId("edition", String.format(Locale.ROOT, "edition-%s", this.type));
            addChild(this.editionWidget);
        }
        TitleScreenSplashTextEvent splashTextEvent = (TitleScreenSplashTextEvent) Laby.fireEvent(new TitleScreenSplashTextEvent(SplashLoader.INSTANCE.getSplashToday()));
        boolean showSplash = !splashTextEvent.isCancelled();
        if (this.labyAPI.minecraft().options().isHideSplashTexts()) {
            showSplash = false;
        }
        if (showSplash) {
            this.splashWidget = new SplashWidget();
            this.splashWidget.addId("splash", String.format(Locale.ROOT, "splash-%s", this.type));
            this.splashWidget.setSplashText(splashTextEvent.getSplashText());
            addChild(this.splashWidget);
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void renderWidget(ScreenContext context) {
        Laby.fireEvent(new TitleScreenLogoPreRenderEvent(context.stack(), context.mouse(), context.getTickDelta(), this));
        super.renderWidget(context);
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.activity.title.header.LogoWidgetAccessor
    public Widget getMinecraftWidget() {
        return this.minecraftWidget;
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.activity.title.header.LogoWidgetAccessor
    @Nullable
    public Widget getEditionWidget() {
        return this.editionWidget;
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.activity.title.header.LogoWidgetAccessor
    public SplashWidgetAccessor getSplashWidget() {
        return this.splashWidget;
    }

    public static MinecraftLogoWidget create() {
        boolean legacy = MinecraftVersions.V1_19_4.orOlder();
        return legacy ? new LegacyMinecraftLogoWidget() : new ModernMinecraftLogoWidget();
    }
}
