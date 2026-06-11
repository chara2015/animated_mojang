package net.labymod.api.client.gui.screen.key.tracker;

import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.util.time.TimeUtil;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/key/tracker/InputKeyTracker.class */
public class InputKeyTracker extends KeyTracker {
    protected long nextCPSCheck;
    protected int cpsCount;
    protected int cpsCountSecond;

    protected InputKeyTracker(@NotNull Key key) {
        super(key);
    }

    public static InputKeyTracker of(@NotNull Key key) {
        return new InputKeyTracker(key);
    }

    @Override // net.labymod.api.client.gui.screen.key.tracker.KeyTracker
    public int getClicksPerSecond() {
        return this.cpsCountSecond;
    }

    @Override // net.labymod.api.client.gui.screen.key.tracker.KeyTracker
    public void press() {
        this.cpsCount++;
    }

    @Override // net.labymod.api.client.gui.screen.key.tracker.KeyTracker
    public void update() {
        if (this.nextCPSCheck < TimeUtil.getMillis()) {
            this.nextCPSCheck = TimeUtil.getMillis() + 1000;
            this.cpsCountSecond = this.cpsCount;
            this.cpsCount = 0;
        }
    }
}
