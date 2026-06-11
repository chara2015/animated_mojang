package net.labymod.api.client.render.matrix;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/matrix/VanillaStackAccessor.class */
public interface VanillaStackAccessor {
    Stack stack(Object obj);

    int index();

    default Stack stack() {
        return stack(null);
    }
}
