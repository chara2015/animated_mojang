package net.labymod.core.labyconnect.object.lootbox.content;

import java.util.Objects;
import net.labymod.api.client.gfx.GlConst;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.labynet.models.textures.Skin;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/object/lootbox/content/LootBoxShopItem.class */
public final class LootBoxShopItem {
    private final short id;
    private final String name;
    private final PoolCategory category;
    private final Integer color;
    private final Icon icon;

    public LootBoxShopItem(short id, String name, String imageUrl, Integer color, PoolCategory category) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.category = category;
        if (imageUrl == null) {
            this.icon = Icon.texture(Skin.LOADING).resolution(135, GlConst.GL_LOAD);
        } else {
            this.icon = Icon.url(imageUrl);
        }
    }

    public Icon getIcon() {
        return this.icon;
    }

    public short id() {
        return this.id;
    }

    public String name() {
        return this.name;
    }

    public Integer getColor() {
        return this.color;
    }

    public PoolCategory category() {
        return this.category;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        LootBoxShopItem that = (LootBoxShopItem) obj;
        return this.id == that.id && Objects.equals(this.name, that.name) && Objects.equals(this.category, that.category);
    }

    public int hashCode() {
        return Objects.hash(Short.valueOf(this.id), this.name, this.category);
    }

    public String toString() {
        return this.name;
    }
}
