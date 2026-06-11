package net.labymod.core.main.advertisement;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import net.labymod.core.main.advertisement.model.Announcement;
import net.labymod.core.main.advertisement.model.DailyEmote;
import net.labymod.core.main.advertisement.model.FooterButton;
import net.labymod.core.main.advertisement.model.SplashDate;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/advertisement/Advertisement.class */
public final class Advertisement extends Record {
    private final List<Announcement> left;
    private final List<Announcement> right;
    private final List<SplashDate> splashDates;
    private final List<DailyEmote> dailyEmotes;
    private final FooterButton footerButton;

    public Advertisement(List<Announcement> left, List<Announcement> right, List<SplashDate> splashDates, List<DailyEmote> dailyEmotes, FooterButton footerButton) {
        this.left = left;
        this.right = right;
        this.splashDates = splashDates;
        this.dailyEmotes = dailyEmotes;
        this.footerButton = footerButton;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Advertisement.class), Advertisement.class, "left;right;splashDates;dailyEmotes;footerButton", "FIELD:Lnet/labymod/core/main/advertisement/Advertisement;->left:Ljava/util/List;", "FIELD:Lnet/labymod/core/main/advertisement/Advertisement;->right:Ljava/util/List;", "FIELD:Lnet/labymod/core/main/advertisement/Advertisement;->splashDates:Ljava/util/List;", "FIELD:Lnet/labymod/core/main/advertisement/Advertisement;->dailyEmotes:Ljava/util/List;", "FIELD:Lnet/labymod/core/main/advertisement/Advertisement;->footerButton:Lnet/labymod/core/main/advertisement/model/FooterButton;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Advertisement.class), Advertisement.class, "left;right;splashDates;dailyEmotes;footerButton", "FIELD:Lnet/labymod/core/main/advertisement/Advertisement;->left:Ljava/util/List;", "FIELD:Lnet/labymod/core/main/advertisement/Advertisement;->right:Ljava/util/List;", "FIELD:Lnet/labymod/core/main/advertisement/Advertisement;->splashDates:Ljava/util/List;", "FIELD:Lnet/labymod/core/main/advertisement/Advertisement;->dailyEmotes:Ljava/util/List;", "FIELD:Lnet/labymod/core/main/advertisement/Advertisement;->footerButton:Lnet/labymod/core/main/advertisement/model/FooterButton;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Advertisement.class, Object.class), Advertisement.class, "left;right;splashDates;dailyEmotes;footerButton", "FIELD:Lnet/labymod/core/main/advertisement/Advertisement;->left:Ljava/util/List;", "FIELD:Lnet/labymod/core/main/advertisement/Advertisement;->right:Ljava/util/List;", "FIELD:Lnet/labymod/core/main/advertisement/Advertisement;->splashDates:Ljava/util/List;", "FIELD:Lnet/labymod/core/main/advertisement/Advertisement;->dailyEmotes:Ljava/util/List;", "FIELD:Lnet/labymod/core/main/advertisement/Advertisement;->footerButton:Lnet/labymod/core/main/advertisement/model/FooterButton;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public List<Announcement> left() {
        return this.left;
    }

    public List<Announcement> right() {
        return this.right;
    }

    public List<SplashDate> splashDates() {
        return this.splashDates;
    }

    public List<DailyEmote> dailyEmotes() {
        return this.dailyEmotes;
    }

    public FooterButton footerButton() {
        return this.footerButton;
    }
}
