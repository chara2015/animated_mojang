package net.labymod.api.client.waila;

import java.util.Objects;
import net.labymod.api.client.entity.Entity;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/waila/WailaEntity.class */
public final class WailaEntity implements Waila<Entity> {
    private final Entity entity;

    WailaEntity(Entity entity) {
        this.entity = entity;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.waila.Waila
    public Entity getValue() {
        return this.entity;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WailaEntity that = (WailaEntity) o;
        return Objects.equals(this.entity, that.entity);
    }

    public int hashCode() {
        return Objects.hashCode(this.entity);
    }
}
