package net.labymod.v26_2_snapshot_8.client.window;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/client/window/WindowAccessor.class */
public interface WindowAccessor {
    public static final WindowAccessor NOP = () -> {
    };

    void labyMod$dirtyResize();

    static WindowAccessor self(Object self) {
        if (!(self instanceof WindowAccessor)) {
            return NOP;
        }
        WindowAccessor accessor = (WindowAccessor) self;
        return accessor;
    }
}
