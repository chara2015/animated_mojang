package net.labymod.api.labynet.models;

import com.google.gson.annotations.SerializedName;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.models.OperatingSystem;
import net.labymod.api.notification.Notification;
import net.labymod.api.notification.NotificationController;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/labynet/models/SocialMediaEntry.class */
public class SocialMediaEntry {
    private final String name;
    private final String url;
    private final String service;

    @SerializedName("service_name")
    private final String serviceName;

    public SocialMediaEntry(String name, String url, String service, String serviceName) {
        this.name = name;
        this.url = url;
        this.service = service;
        this.serviceName = serviceName;
    }

    public String getName() {
        return this.name;
    }

    public String getUrl() {
        return this.url;
    }

    public String getService() {
        return this.service;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public void open() {
        OperatingSystem.getPlatform().openUrl(this.url);
    }

    public void copyName() {
        Laby.labyAPI().minecraft().chatExecutor().copyToClipboard(this.name);
        NotificationController notificationController = Laby.labyAPI().notificationController();
        notificationController.push(Notification.builder().title(Component.translatable("labymod.notification.copied", new Component[0])).text(Component.text(this.name)).duration(4000L).build());
    }
}
