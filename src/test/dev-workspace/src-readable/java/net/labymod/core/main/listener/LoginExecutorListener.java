package net.labymod.core.main.listener;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.network.server.ServerController;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.network.server.ServerDisconnectEvent;
import net.labymod.api.event.client.resources.pack.ResourceReloadEvent;
import net.labymod.api.event.client.world.WorldEnterEvent;
import net.labymod.api.util.concurrent.task.Task;
import net.labymod.core.main.user.shop.spray.SprayConstants;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/listener/LoginExecutorListener.class */
public class LoginExecutorListener {
    private final ServerController serverController;
    private final Minecraft minecraft;
    private final Task delayedTask;
    private boolean packConfirmOpen;

    public LoginExecutorListener(LabyAPI labyAPI) {
        this.minecraft = labyAPI.minecraft();
        this.serverController = labyAPI.serverController();
        ServerController serverController = this.serverController;
        Objects.requireNonNull(serverController);
        this.delayedTask = Task.builder(serverController::executeLoginExecutor).delay(SprayConstants.LABYMOD_PLUS_LAST_LIFESPAN, TimeUnit.MILLISECONDS).build();
    }

    @Subscribe
    public void onWorldEnter(WorldEnterEvent event) {
        if (isConfirmScreenOpen()) {
            this.packConfirmOpen = true;
        } else {
            this.delayedTask.execute();
        }
    }

    @Subscribe
    public void onResourceReload(ResourceReloadEvent event) {
        if (event.phase() == Phase.POST && event.type() == ResourceReloadEvent.Type.RELOAD && this.packConfirmOpen) {
            this.delayedTask.execute();
            this.packConfirmOpen = false;
        }
    }

    @Subscribe
    public void onServerDisconnect(ServerDisconnectEvent event) {
        this.packConfirmOpen = false;
    }

    private boolean isConfirmScreenOpen() {
        Window window = this.minecraft.minecraftWindow();
        return window.isScreenDisplayed(NamedScreen.CONFIRM) || window.isScreenDisplayed(NamedScreen.PACK_CONFIRM);
    }
}
