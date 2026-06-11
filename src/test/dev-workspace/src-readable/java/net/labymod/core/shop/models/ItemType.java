package net.labymod.core.shop.models;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.icon.Icon;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/shop/models/ItemType.class */
public class ItemType {
    public static final Set<ItemType> VALUES = new HashSet();
    public static final ItemType SEASON = register("SEASON", true);
    public static final ItemType FEATURED = register("FEATURED", true);
    public static final ItemType COSMETIC = register("COSMETIC", true);
    public static final ItemType EMOTE = register("EMOTE", true);
    public static final ItemType BUNDLE = register("BUNDLE", false);
    public static final ItemType PLUS = register("PLUS", false);
    public static final ItemType FLATRATE = register("FLATRATE", false);
    private final String type;
    private boolean visible;
    private Icon icon;

    private ItemType(String type, boolean visible) {
        this.visible = true;
        this.type = type;
        this.visible = visible;
        switch (type) {
            case "FEATURED":
                this.icon = Textures.SpriteShop.FEATURED;
                break;
        }
    }

    public String getNiceName() {
        return this.type.charAt(0) + this.type.substring(1).toLowerCase(Locale.ROOT);
    }

    public boolean isVisible() {
        return this.visible;
    }

    @Nullable
    public Icon getIcon() {
        return this.icon;
    }

    public static ItemType of(String type) {
        for (ItemType value : VALUES) {
            if (value.type.equalsIgnoreCase(type)) {
                return value;
            }
        }
        return new ItemType(type, false);
    }

    static ItemType register(String type, boolean visible) {
        ItemType value = new ItemType(type, visible);
        VALUES.add(value);
        return value;
    }

    public String getType() {
        return this.type;
    }

    public String toString() {
        return this.type.toLowerCase(Locale.ROOT);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemType)) {
            return false;
        }
        ItemType type1 = (ItemType) o;
        return Objects.equals(this.type, type1.type);
    }

    public int hashCode() {
        return this.type.hashCode();
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/shop/models/ItemType$ItemTypeTypeAdapter.class */
    public static class ItemTypeTypeAdapter implements JsonDeserializer<ItemType>, JsonSerializer<ItemType> {
        /* JADX INFO: renamed from: deserialize, reason: merged with bridge method [inline-methods] */
        public ItemType m1178deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            if (json.isJsonNull()) {
                return null;
            }
            return ItemType.of(json.getAsString());
        }

        public JsonElement serialize(ItemType src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.type);
        }
    }
}
