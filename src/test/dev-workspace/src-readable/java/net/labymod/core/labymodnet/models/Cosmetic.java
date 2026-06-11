package net.labymod.core.labymodnet.models;

import com.google.gson.annotations.SerializedName;
import java.util.Locale;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.notification.Notification;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labymodnet/models/Cosmetic.class */
public class Cosmetic {
    private final int id;

    @SerializedName("item_id")
    private int itemId;
    private final String name;
    private final String[] options;
    private String[] data;
    private String[] defaultData;
    private boolean enabled;

    @SerializedName("group_id")
    @Nullable
    private Integer groupId;
    private final String category;
    private transient Icon icon;

    public Cosmetic(int id, int itemId, String name, String[] options, String[] data, String[] defaultData, boolean enabled, int groupId, String category) {
        this.id = id;
        this.itemId = itemId;
        this.name = name;
        this.options = options;
        this.data = data;
        this.defaultData = defaultData;
        this.enabled = enabled;
        this.groupId = Integer.valueOf(groupId);
        this.category = category;
    }

    public int getId() {
        return this.id;
    }

    public int getItemId() {
        return this.itemId;
    }

    public String getName() {
        return this.name;
    }

    public String[] getOptions() {
        return this.options;
    }

    public String[] getData() {
        return this.data;
    }

    public void setData(String[] data) {
        this.data = data;
    }

    public String[] getDefaultData() {
        return this.defaultData;
    }

    public void setDefaultData(String[] defaultData) {
        this.defaultData = defaultData;
    }

    public void resetData() {
        if (this.defaultData == null) {
            Notification notification = Notification.builder().title(Component.translatable("labymod.misc.error", new Component[0])).text(Component.translatable("labymod.notification.cosmetic_default_data.description", new Component[0])).build();
            Laby.references().notificationController().push(notification);
        } else {
            System.arraycopy(this.defaultData, 0, this.data, 0, this.data.length);
        }
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Nullable
    public Integer getGroupId() {
        return this.groupId;
    }

    public String getCategory() {
        return this.category;
    }

    public Icon icon() {
        if (this.icon == null) {
            this.icon = Icon.url(String.format(Locale.ROOT, Constants.Urls.WEB_SHOP_PRODUCTS_IMAGE, this.name.toLowerCase(Locale.ROOT).replace(" ", "-")));
        }
        return this.icon;
    }
}
