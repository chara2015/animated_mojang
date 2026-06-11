package net.labymod.api.client.gui.screen.key.tracker;

import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import it.unimi.dsi.fastutil.longs.LongListIterator;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.util.time.TimeUtil;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/key/tracker/RealTimeKeyTracker.class */
public class RealTimeKeyTracker extends KeyTracker {
    protected final LongList clicks;

    protected RealTimeKeyTracker(@NotNull Key key) {
        super(key);
        this.clicks = new LongArrayList();
    }

    public static RealTimeKeyTracker of(@NotNull Key key) {
        return new RealTimeKeyTracker(key);
    }

    @Override // net.labymod.api.client.gui.screen.key.tracker.KeyTracker
    public int getClicksPerSecond() {
        return this.clicks.size();
    }

    @Override // net.labymod.api.client.gui.screen.key.tracker.KeyTracker
    public void press() {
        this.clicks.add(TimeUtil.getMillis() + 1000);
    }

    @Override // net.labymod.api.client.gui.screen.key.tracker.KeyTracker
    public void update() {
        if (getClicksPerSecond() == 0) {
            return;
        }
        long currentTime = TimeUtil.getMillis();
        LongListIterator iterator = this.clicks.iterator();
        while (iterator.hasNext()) {
            if (iterator.nextLong() < currentTime) {
                iterator.remove();
            }
        }
    }
}
