package net.labymod.core.flint.marketplace;

import com.google.gson.annotations.SerializedName;
import java.util.Optional;
import net.labymod.core.flint.FlintController;
import net.labymod.core.main.LabyMod;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/flint/marketplace/FlintTag.class */
public class FlintTag {
    private static final FlintController CONTROLLER = LabyMod.references().flintController();
    private FlintTag parent;
    private int id;
    private String tag;
    private String description;

    @SerializedName("parent_category")
    private int parentCategory;

    public Optional<FlintTag> getParent() {
        return CONTROLLER.getTag(this.parentCategory);
    }

    public String getName() {
        return this.tag;
    }

    public String getDescription() {
        return this.description;
    }

    public int getId() {
        return this.id;
    }

    public int getParentCategory() {
        return this.parentCategory;
    }

    public String toString() {
        return String.valueOf(getId());
    }
}
