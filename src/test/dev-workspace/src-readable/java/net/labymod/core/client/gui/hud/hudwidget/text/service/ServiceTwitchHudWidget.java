package net.labymod.core.client.gui.hud.hudwidget.text.service;

import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.labynet.models.service.ServiceDataType;
import net.labymod.api.labynet.models.service.ServiceStatus;
import net.labymod.api.labynet.models.service.TwitchServiceData;
import net.labymod.api.util.I18n;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/text/service/ServiceTwitchHudWidget.class */
@SpriteSlot(x = 4, y = 3)
public class ServiceTwitchHudWidget extends ServiceHudWidget<TwitchServiceData> {
    private TextLine viewerCountLine;

    public ServiceTwitchHudWidget() {
        super(ServiceDataType.TWITCH, 60000);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget, net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void load(TextHudWidgetConfig config) {
        super.load(config);
        this.viewerCountLine = super.createLine("Live Viewers", "?");
        onError(ServiceStatus.Status.LOADING);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.labymod.core.client.gui.hud.hudwidget.text.service.ServiceHudWidget
    public void onUpdate(TwitchServiceData serviceData) {
        if (serviceData.getStream() != null) {
            this.viewerCountLine.updateAndFlush(Integer.valueOf(serviceData.getStream().getViewerCount()));
        } else {
            this.viewerCountLine.updateAndFlush(I18n.translate("labymod.hudWidget.service_twitch.offline", new Object[0]));
        }
        super.onUpdate(serviceData);
    }

    @Override // net.labymod.core.client.gui.hud.hudwidget.text.service.ServiceHudWidget
    protected void onError(ServiceStatus.Status status) {
        this.viewerCountLine.updateAndFlush(getErrorTranslation(status));
        super.onError(status);
    }
}
