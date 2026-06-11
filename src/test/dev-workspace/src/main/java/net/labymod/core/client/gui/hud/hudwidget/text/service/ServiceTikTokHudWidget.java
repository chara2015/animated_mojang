package net.labymod.core.client.gui.hud.hudwidget.text.service;

import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.labynet.models.service.ServiceDataType;
import net.labymod.api.labynet.models.service.ServiceStatus;
import net.labymod.api.labynet.models.service.TikTokServiceData;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/text/service/ServiceTikTokHudWidget.class */
@SpriteSlot(x = 6, y = 3)
public class ServiceTikTokHudWidget extends ServiceHudWidget<TikTokServiceData> {
    private TextLine followersLine;
    private TextLine likesLine;

    public ServiceTikTokHudWidget() {
        super(ServiceDataType.TIKTOK);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget, net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void load(TextHudWidgetConfig config) {
        super.load(config);
        this.followersLine = super.createLine("Followers", "?");
        this.likesLine = super.createLine("Likes", "?");
        onError(ServiceStatus.Status.LOADING);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.labymod.core.client.gui.hud.hudwidget.text.service.ServiceHudWidget
    public void onUpdate(TikTokServiceData serviceData) {
        this.followersLine.updateAndFlush(Integer.valueOf(serviceData.getFollowerCount()));
        this.likesLine.updateAndFlush(Integer.valueOf(serviceData.getLikesCount()));
        super.onUpdate(serviceData);
    }

    @Override // net.labymod.core.client.gui.hud.hudwidget.text.service.ServiceHudWidget
    protected void onError(ServiceStatus.Status status) {
        this.followersLine.updateAndFlush(getErrorTranslation(status));
        this.likesLine.updateAndFlush(getErrorTranslation(status));
        super.onError(status);
    }
}
