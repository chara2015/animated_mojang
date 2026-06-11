package net.labymod.core.client.gui.hud.hudwidget.text.service;

import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.labynet.models.service.ServiceDataType;
import net.labymod.api.labynet.models.service.ServiceStatus;
import net.labymod.api.labynet.models.service.YouTubeServiceData;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/text/service/ServiceYouTubeHudWidget.class */
@SpriteSlot(x = 5, y = 3)
public class ServiceYouTubeHudWidget extends ServiceHudWidget<YouTubeServiceData> {
    private TextLine subscribersLine;
    private TextLine viewsLine;

    public ServiceYouTubeHudWidget() {
        super(ServiceDataType.YOUTUBE);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget, net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void load(TextHudWidgetConfig config) {
        super.load(config);
        this.subscribersLine = super.createLine("Subscribers", "?");
        this.viewsLine = super.createLine("Views", "?");
        onError(ServiceStatus.Status.LOADING);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.labymod.core.client.gui.hud.hudwidget.text.service.ServiceHudWidget
    public void onUpdate(YouTubeServiceData serviceData) {
        this.subscribersLine.updateAndFlush(Integer.valueOf(serviceData.getSubscriberCount()));
        this.viewsLine.updateAndFlush(Integer.valueOf(serviceData.getViewCount()));
        super.onUpdate(serviceData);
    }

    @Override // net.labymod.core.client.gui.hud.hudwidget.text.service.ServiceHudWidget
    protected void onError(ServiceStatus.Status status) {
        this.subscribersLine.updateAndFlush(getErrorTranslation(status));
        this.viewsLine.updateAndFlush(getErrorTranslation(status));
        super.onError(status);
    }
}
