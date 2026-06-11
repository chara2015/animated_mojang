package net.labymod.core.main.user.shop.spray.model;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.labymod.api.util.Lazy;
import net.labymod.core.flint.FlintUrls;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/spray/model/Spray.class */
public class Spray {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName(FlintUrls.QUERY_TAGS_PARAM)
    private final List<String> tags = new ArrayList();
    private final transient Lazy<UUID> uuid = Lazy.of(() -> {
        return new UUID(this.id, 0L);
    });

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public List<String> getTags() {
        return this.tags;
    }

    public UUID getUuid() {
        return this.uuid.get();
    }

    public String toString() {
        return "Spray[id=" + this.id + ",name=" + this.name + ",tags=" + String.valueOf(this.tags) + "]";
    }
}
