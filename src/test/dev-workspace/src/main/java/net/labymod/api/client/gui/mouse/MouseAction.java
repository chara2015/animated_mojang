package net.labymod.api.client.gui.mouse;

import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.util.time.TimeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/mouse/MouseAction.class */
public class MouseAction {
    private long timestamp;
    private MouseButton button;

    public long getTimestamp() {
        return this.timestamp;
    }

    @Nullable
    public MouseButton getButton() {
        return this.button;
    }

    public boolean isDoubleClick() {
        return TimeUtil.getMillis() - this.timestamp < 200;
    }

    public void update(@NotNull MouseButton button) {
        this.timestamp = TimeUtil.getMillis();
        this.button = button;
    }
}
