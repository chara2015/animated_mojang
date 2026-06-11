package net.labymod.core.labyconnect.session;

import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.gui.mouse.Mouse;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.input.KeyEvent;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.configuration.LabyConnectConfigAccessor;
import net.labymod.api.labyconnect.protocol.model.UserStatus;
import net.labymod.api.util.time.TimeUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/session/LabyConnectAwayTracker.class */
public class LabyConnectAwayTracker {
    private static final long TIME_UNTIL_AFK = 300000;
    private final LabyConnect labyConnect;
    private int lastMouseX;
    private int lastMouseY;
    private boolean away;
    private boolean statusChanged;
    private final Minecraft minecraft = Laby.labyAPI().minecraft();
    private long timeLastMovement = TimeUtil.getMillis();

    public LabyConnectAwayTracker(LabyConnect labyConnect) {
        this.labyConnect = labyConnect;
    }

    private void updateState(boolean away) {
        this.away = away;
        LabyConnectConfigAccessor config = (LabyConnectConfigAccessor) this.labyConnect.configProvider().get();
        ConfigProperty<UserStatus> property = config.onlineStatus();
        if (away) {
            if (property.get() == UserStatus.ONLINE) {
                property.set(UserStatus.AWAY);
                this.statusChanged = true;
                return;
            }
            return;
        }
        if (this.statusChanged && property.get() == UserStatus.AWAY) {
            property.set(UserStatus.ONLINE);
        }
        this.statusChanged = false;
    }

    @Subscribe
    public void onTick(GameTickEvent event) {
        if (event.phase() != Phase.PRE) {
            return;
        }
        Mouse mouse = this.minecraft.absoluteMouse();
        int currentMouseX = mouse.getX();
        int currentMouseY = mouse.getY();
        if (currentMouseX == this.lastMouseX && currentMouseY == this.lastMouseY) {
            long timePassed = TimeUtil.getMillis() - this.timeLastMovement;
            if (!this.away && timePassed > TIME_UNTIL_AFK) {
                updateState(true);
            }
        } else {
            this.timeLastMovement = TimeUtil.getMillis();
            if (this.away) {
                updateState(false);
            }
        }
        this.lastMouseX = currentMouseX;
        this.lastMouseY = currentMouseY;
    }

    @Subscribe
    public void keyPress(KeyEvent event) {
        this.timeLastMovement = TimeUtil.getMillis();
        if (this.away) {
            updateState(false);
        }
    }
}
