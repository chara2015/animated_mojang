package net.labymod.core.main.advertisement.model;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Locale;
import net.labymod.api.Constants;
import net.labymod.api.client.gui.icon.Icon;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/advertisement/model/FooterButton.class */
public final class FooterButton extends Record {
    private final String name;
    private final boolean isNew;
    private final String color;
    private final String borderColor;
    private final String colorHover;
    private final String iconName;
    private final String url;
    private final int buttonWidth;
    private final boolean visible;

    public FooterButton(String name, boolean isNew, String color, String borderColor, String colorHover, String iconName, String url, int buttonWidth, boolean visible) {
        this.name = name;
        this.isNew = isNew;
        this.color = color;
        this.borderColor = borderColor;
        this.colorHover = colorHover;
        this.iconName = iconName;
        this.url = url;
        this.buttonWidth = buttonWidth;
        this.visible = visible;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, FooterButton.class), FooterButton.class, "name;isNew;color;borderColor;colorHover;iconName;url;buttonWidth;visible", "FIELD:Lnet/labymod/core/main/advertisement/model/FooterButton;->name:Ljava/lang/String;", "FIELD:Lnet/labymod/core/main/advertisement/model/FooterButton;->isNew:Z", "FIELD:Lnet/labymod/core/main/advertisement/model/FooterButton;->color:Ljava/lang/String;", "FIELD:Lnet/labymod/core/main/advertisement/model/FooterButton;->borderColor:Ljava/lang/String;", "FIELD:Lnet/labymod/core/main/advertisement/model/FooterButton;->colorHover:Ljava/lang/String;", "FIELD:Lnet/labymod/core/main/advertisement/model/FooterButton;->iconName:Ljava/lang/String;", "FIELD:Lnet/labymod/core/main/advertisement/model/FooterButton;->url:Ljava/lang/String;", "FIELD:Lnet/labymod/core/main/advertisement/model/FooterButton;->buttonWidth:I", "FIELD:Lnet/labymod/core/main/advertisement/model/FooterButton;->visible:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, FooterButton.class), FooterButton.class, "name;isNew;color;borderColor;colorHover;iconName;url;buttonWidth;visible", "FIELD:Lnet/labymod/core/main/advertisement/model/FooterButton;->name:Ljava/lang/String;", "FIELD:Lnet/labymod/core/main/advertisement/model/FooterButton;->isNew:Z", "FIELD:Lnet/labymod/core/main/advertisement/model/FooterButton;->color:Ljava/lang/String;", "FIELD:Lnet/labymod/core/main/advertisement/model/FooterButton;->borderColor:Ljava/lang/String;", "FIELD:Lnet/labymod/core/main/advertisement/model/FooterButton;->colorHover:Ljava/lang/String;", "FIELD:Lnet/labymod/core/main/advertisement/model/FooterButton;->iconName:Ljava/lang/String;", "FIELD:Lnet/labymod/core/main/advertisement/model/FooterButton;->url:Ljava/lang/String;", "FIELD:Lnet/labymod/core/main/advertisement/model/FooterButton;->buttonWidth:I", "FIELD:Lnet/labymod/core/main/advertisement/model/FooterButton;->visible:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, FooterButton.class, Object.class), FooterButton.class, "name;isNew;color;borderColor;colorHover;iconName;url;buttonWidth;visible", "FIELD:Lnet/labymod/core/main/advertisement/model/FooterButton;->name:Ljava/lang/String;", "FIELD:Lnet/labymod/core/main/advertisement/model/FooterButton;->isNew:Z", "FIELD:Lnet/labymod/core/main/advertisement/model/FooterButton;->color:Ljava/lang/String;", "FIELD:Lnet/labymod/core/main/advertisement/model/FooterButton;->borderColor:Ljava/lang/String;", "FIELD:Lnet/labymod/core/main/advertisement/model/FooterButton;->colorHover:Ljava/lang/String;", "FIELD:Lnet/labymod/core/main/advertisement/model/FooterButton;->iconName:Ljava/lang/String;", "FIELD:Lnet/labymod/core/main/advertisement/model/FooterButton;->url:Ljava/lang/String;", "FIELD:Lnet/labymod/core/main/advertisement/model/FooterButton;->buttonWidth:I", "FIELD:Lnet/labymod/core/main/advertisement/model/FooterButton;->visible:Z").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public String name() {
        return this.name;
    }

    public boolean isNew() {
        return this.isNew;
    }

    public String color() {
        return this.color;
    }

    public String borderColor() {
        return this.borderColor;
    }

    public String colorHover() {
        return this.colorHover;
    }

    public String iconName() {
        return this.iconName;
    }

    public String url() {
        return this.url;
    }

    public int buttonWidth() {
        return this.buttonWidth;
    }

    public boolean visible() {
        return this.visible;
    }

    public String getIconUrl() {
        return String.format(Locale.ROOT, "%s/%s.png", Constants.Urls.ANNOUNCEMENTS_ICONS, this.iconName);
    }

    public Icon getIcon() {
        return Icon.url(getIconUrl());
    }
}
