package net.labymod.api.labynet.models.service;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/labynet/models/service/ServiceDataType.class */
public enum ServiceDataType {
    YOUTUBE("service_youtube", YouTubeServiceData.class),
    TWITCH("service_twitch", TwitchServiceData.class),
    TIKTOK("service_tiktok", TikTokServiceData.class);

    private final Class<? extends ServiceData> clazz;
    private final String id;

    ServiceDataType(String id, Class cls) {
        this.id = id;
        this.clazz = cls;
    }

    public String getId() {
        return this.id;
    }

    public Class<? extends ServiceData> getClazz() {
        return this.clazz;
    }
}
