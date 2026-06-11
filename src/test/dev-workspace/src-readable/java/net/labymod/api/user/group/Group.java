package net.labymod.api.user.group;

import com.google.gson.annotations.SerializedName;
import java.awt.Color;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.util.logging.Logging;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/user/group/Group.class */
public class Group {
    private static final Logging LOGGER = Logging.getLogger();

    @SerializedName("id")
    private int identifier;

    @SerializedName("name")
    private String name;

    @SerializedName("nice_name")
    private String displayName;

    @SerializedName("color_hex")
    private String colorHex;

    @SerializedName("color_minecraft")
    private char minecraftColorChar;

    @SerializedName("tag_name")
    private String tagName;

    @SerializedName("display_type")
    private String displayTypeName;

    @SerializedName("is_staff")
    private boolean isStaff;
    private transient GroupDisplayType displayType;

    @NotNull
    private transient Color color = Color.WHITE;

    @NotNull
    private transient TextColor textColor = NamedTextColor.WHITE;

    public Group(int identifier, String name, String displayName, String colorHex, char minecraftColorChar, String tagName, String displayTypeName, boolean isStaff) {
        this.identifier = identifier;
        this.name = name;
        this.displayName = displayName;
        this.colorHex = colorHex;
        this.minecraftColorChar = minecraftColorChar;
        this.tagName = tagName;
        this.displayTypeName = displayTypeName;
        this.isStaff = isStaff;
    }

    public Group initialize() {
        if (this.displayTypeName == null) {
            this.displayType = GroupDisplayType.NONE;
        } else {
            GroupDisplayType displayType = GroupDisplayType.getDisplayType(this.displayTypeName);
            this.displayType = displayType == null ? GroupDisplayType.NONE : displayType;
        }
        try {
            if (this.colorHex != null && !this.colorHex.isEmpty()) {
                this.color = Color.decode("#" + this.colorHex);
                this.textColor = TextColor.color(this.color.getRGB());
            }
        } catch (Exception exception) {
            LOGGER.error("Failed to decode the color of the group '" + this.name + "'.", exception);
        }
        return this;
    }

    public int getIdentifier() {
        return this.identifier;
    }

    public String getName() {
        return this.name;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getColorHex() {
        return this.colorHex;
    }

    public char getMinecraftColorChar() {
        return this.minecraftColorChar;
    }

    public String getTagName() {
        return this.tagName;
    }

    public GroupDisplayType getDisplayType() {
        return this.displayType;
    }

    @NotNull
    public Color getColor() {
        return this.color;
    }

    @NotNull
    public TextColor getTextColor() {
        return this.textColor;
    }

    public boolean isDefault() {
        return this.identifier == 0;
    }

    public boolean isStaff() {
        return this.isStaff;
    }

    public boolean isStaffOrCosmeticCreator() {
        return this.isStaff || this.identifier == 11;
    }

    public boolean isLabyModPlus() {
        return this.identifier == 10;
    }

    public String toString() {
        return "Group[" + this.name + "(" + this.identifier + ")]";
    }
}
