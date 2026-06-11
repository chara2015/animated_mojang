package net.minecraft.network.syncher;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/syncher/EntityDataAccessor.class */
public final class EntityDataAccessor<T> extends Record {
    private final int id;
    private final EntityDataSerializer<T> serializer;

    public EntityDataAccessor(int $$0, EntityDataSerializer<T> $$1) {
        this.id = $$0;
        this.serializer = $$1;
    }

    public int id() {
        return this.id;
    }

    public EntityDataSerializer<T> serializer() {
        return this.serializer;
    }

    @Override // java.lang.Record
    public boolean equals(Object $$0) {
        if (this == $$0) {
            return true;
        }
        if ($$0 == null || getClass() != $$0.getClass()) {
            return false;
        }
        EntityDataAccessor<?> $$1 = (EntityDataAccessor) $$0;
        return this.id == $$1.id;
    }

    @Override // java.lang.Record
    public int hashCode() {
        return this.id;
    }

    @Override // java.lang.Record
    public String toString() {
        return "<entity data: " + this.id + ">";
    }
}
