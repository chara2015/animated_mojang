package net.labymod.api.laby3d;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.EnumMap;
import org.joml.Vector3f;
import org.joml.Vector3fc;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/GameLightingStorage.class */
public final class GameLightingStorage {
    public static final GameLightingStorage INSTANCE = new GameLightingStorage();
    private final EnumMap<Entry, LightDirections> directions = new EnumMap<>(Entry.class);

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/GameLightingStorage$Entry.class */
    public enum Entry {
        LEVEL,
        ITEMS_FLAT,
        ITEMS_3D,
        ENTITY_IN_UI,
        PLAYER_SKIN
    }

    private GameLightingStorage() {
        for (Entry entry : Entry.values()) {
            this.directions.put(entry, new LightDirections());
        }
    }

    public LightDirections getLightDirections(Entry entry) {
        return this.directions.get(entry);
    }

    public void setLightDirections(Entry entry, Vector3fc light0Direction, Vector3fc light1Direction) {
        LightDirections directions = this.directions.get(entry);
        directions.light0Direction().set(light0Direction);
        directions.light1Direction().set(light1Direction);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/GameLightingStorage$LightDirections.class */
    public static final class LightDirections extends Record {
        private final Vector3f light0Direction;
        private final Vector3f light1Direction;

        public LightDirections(Vector3f light0Direction, Vector3f light1Direction) {
            this.light0Direction = light0Direction;
            this.light1Direction = light1Direction;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, LightDirections.class), LightDirections.class, "light0Direction;light1Direction", "FIELD:Lnet/labymod/api/laby3d/GameLightingStorage$LightDirections;->light0Direction:Lorg/joml/Vector3f;", "FIELD:Lnet/labymod/api/laby3d/GameLightingStorage$LightDirections;->light1Direction:Lorg/joml/Vector3f;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, LightDirections.class), LightDirections.class, "light0Direction;light1Direction", "FIELD:Lnet/labymod/api/laby3d/GameLightingStorage$LightDirections;->light0Direction:Lorg/joml/Vector3f;", "FIELD:Lnet/labymod/api/laby3d/GameLightingStorage$LightDirections;->light1Direction:Lorg/joml/Vector3f;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, LightDirections.class, Object.class), LightDirections.class, "light0Direction;light1Direction", "FIELD:Lnet/labymod/api/laby3d/GameLightingStorage$LightDirections;->light0Direction:Lorg/joml/Vector3f;", "FIELD:Lnet/labymod/api/laby3d/GameLightingStorage$LightDirections;->light1Direction:Lorg/joml/Vector3f;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public Vector3f light0Direction() {
            return this.light0Direction;
        }

        public Vector3f light1Direction() {
            return this.light1Direction;
        }

        public LightDirections() {
            this(new Vector3f(), new Vector3f());
        }
    }
}
