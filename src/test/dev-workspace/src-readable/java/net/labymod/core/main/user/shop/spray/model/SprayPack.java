package net.labymod.core.main.user.shop.spray.model;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/spray/model/SprayPack.class */
public class SprayPack {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("stickers")
    private List<Spray> sprays = new ArrayList();

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public List<Spray> getSprays() {
        return this.sprays;
    }

    public String toString() {
        return "SprayPack[id=" + this.id + ",name=" + this.name + ",sprays=" + String.valueOf(this.sprays) + "]";
    }
}
