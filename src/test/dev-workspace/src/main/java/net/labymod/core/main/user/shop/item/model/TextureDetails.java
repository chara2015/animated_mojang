package net.labymod.core.main.user.shop.item.model;

import java.util.Objects;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/item/model/TextureDetails.class */
public final class TextureDetails {
    private final UUID uuid;

    private TextureDetails(UUID uuid) {
        this.uuid = uuid;
    }

    @NotNull
    public static TextureDetails of(UUID uuid) {
        return new TextureDetails(uuid);
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TextureDetails textureDetails = (TextureDetails) o;
        return Objects.equals(this.uuid, textureDetails.uuid);
    }

    public int hashCode() {
        return Objects.hashCode(this.uuid);
    }
}
