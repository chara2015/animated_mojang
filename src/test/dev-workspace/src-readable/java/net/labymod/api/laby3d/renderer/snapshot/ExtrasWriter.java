package net.labymod.api.laby3d.renderer.snapshot;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/renderer/snapshot/ExtrasWriter.class */
public interface ExtrasWriter {
    <T> ExtrasWriter put(ExtraKey<T> extraKey, T t);
}
