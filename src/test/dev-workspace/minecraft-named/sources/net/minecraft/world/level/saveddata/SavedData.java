package net.minecraft.world.level.saveddata;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/saveddata/SavedData.class */
public abstract class SavedData {
    private boolean dirty;

    public void setDirty() {
        setDirty(true);
    }

    public void setDirty(boolean $$0) {
        this.dirty = $$0;
    }

    public boolean isDirty() {
        return this.dirty;
    }
}
