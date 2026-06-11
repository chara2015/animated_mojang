package net.labymod.core.main.advertisement.model;

import com.google.gson.annotations.SerializedName;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.api.Constants;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/advertisement/model/Announcement.class */
public final class Announcement extends Record {

    @SerializedName("title")
    private final String title;

    @SerializedName("isNew")
    private final boolean isNew;

    @SerializedName("color")
    private final String color;

    @SerializedName("colorHover")
    private final String colorHover;

    @SerializedName("iconName")
    private final String iconName;

    @SerializedName("url")
    private final String url;

    @SerializedName("visible")
    private final boolean visible;

    public Announcement(String title, boolean isNew, String color, String colorHover, String iconName, String url, boolean visible) {
        this.title = title;
        this.isNew = isNew;
        this.color = color;
        this.colorHover = colorHover;
        this.iconName = iconName;
        this.url = url;
        this.visible = visible;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Announcement.class), Announcement.class, "title;isNew;color;colorHover;iconName;url;visible", "FIELD:Lnet/labymod/core/main/advertisement/model/Announcement;->title:Ljava/lang/String;", "FIELD:Lnet/labymod/core/main/advertisement/model/Announcement;->isNew:Z", "FIELD:Lnet/labymod/core/main/advertisement/model/Announcement;->color:Ljava/lang/String;", "FIELD:Lnet/labymod/core/main/advertisement/model/Announcement;->colorHover:Ljava/lang/String;", "FIELD:Lnet/labymod/core/main/advertisement/model/Announcement;->iconName:Ljava/lang/String;", "FIELD:Lnet/labymod/core/main/advertisement/model/Announcement;->url:Ljava/lang/String;", "FIELD:Lnet/labymod/core/main/advertisement/model/Announcement;->visible:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Announcement.class), Announcement.class, "title;isNew;color;colorHover;iconName;url;visible", "FIELD:Lnet/labymod/core/main/advertisement/model/Announcement;->title:Ljava/lang/String;", "FIELD:Lnet/labymod/core/main/advertisement/model/Announcement;->isNew:Z", "FIELD:Lnet/labymod/core/main/advertisement/model/Announcement;->color:Ljava/lang/String;", "FIELD:Lnet/labymod/core/main/advertisement/model/Announcement;->colorHover:Ljava/lang/String;", "FIELD:Lnet/labymod/core/main/advertisement/model/Announcement;->iconName:Ljava/lang/String;", "FIELD:Lnet/labymod/core/main/advertisement/model/Announcement;->url:Ljava/lang/String;", "FIELD:Lnet/labymod/core/main/advertisement/model/Announcement;->visible:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Announcement.class, Object.class), Announcement.class, "title;isNew;color;colorHover;iconName;url;visible", "FIELD:Lnet/labymod/core/main/advertisement/model/Announcement;->title:Ljava/lang/String;", "FIELD:Lnet/labymod/core/main/advertisement/model/Announcement;->isNew:Z", "FIELD:Lnet/labymod/core/main/advertisement/model/Announcement;->color:Ljava/lang/String;", "FIELD:Lnet/labymod/core/main/advertisement/model/Announcement;->colorHover:Ljava/lang/String;", "FIELD:Lnet/labymod/core/main/advertisement/model/Announcement;->iconName:Ljava/lang/String;", "FIELD:Lnet/labymod/core/main/advertisement/model/Announcement;->url:Ljava/lang/String;", "FIELD:Lnet/labymod/core/main/advertisement/model/Announcement;->visible:Z").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    @SerializedName("title")
    public String title() {
        return this.title;
    }

    @SerializedName("isNew")
    public boolean isNew() {
        return this.isNew;
    }

    @SerializedName("color")
    public String color() {
        return this.color;
    }

    @SerializedName("colorHover")
    public String colorHover() {
        return this.colorHover;
    }

    @SerializedName("iconName")
    public String iconName() {
        return this.iconName;
    }

    @SerializedName("url")
    public String url() {
        return this.url;
    }

    @SerializedName("visible")
    public boolean visible() {
        return this.visible;
    }

    @Nullable
    public String getIconUrl() {
        if (this.iconName == null) {
            return null;
        }
        return Constants.Urls.ANNOUNCEMENTS_ICONS + "/" + this.iconName + ".png";
    }
}
