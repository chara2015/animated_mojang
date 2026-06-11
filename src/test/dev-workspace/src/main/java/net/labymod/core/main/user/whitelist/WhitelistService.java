package net.labymod.core.main.user.whitelist;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import net.laby.lib.user.Whitelist;
import net.labymod.api.service.Service;
import net.labymod.api.user.GameUser;
import net.labymod.api.user.GameUserProfile;
import net.labymod.api.user.WhitelistState;
import net.labymod.core.main.user.DefaultGameUserProfile;
import net.labymod.core.main.util.LabyLibHttpClients;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/whitelist/WhitelistService.class */
public class WhitelistService extends Service {
    private final net.laby.lib.user.WhitelistService service;
    private Whitelist whitelist;
    private final List<Runnable> tasks = new ArrayList();
    private boolean loaded = false;

    public WhitelistService(LabyLibHttpClients httpClients) {
        this.service = new net.laby.lib.user.WhitelistService(httpClients.cachingHttpClient());
    }

    @Override // net.labymod.api.service.Service
    public void prepare() {
        this.service.getWhitelist().ifSuccess(this::load).ifFailure(throwable -> {
            LOGGER.error("Failed to load whitelist", throwable);
        });
    }

    @Override // net.labymod.api.service.Service
    public void onServiceUnload() {
        this.whitelist = null;
        this.loaded = false;
    }

    public void load(Whitelist whitelist) {
        this.whitelist = whitelist;
        this.loaded = true;
        this.tasks.forEach((v0) -> {
            v0.run();
        });
        this.tasks.clear();
    }

    public void fetch(GameUser user, Consumer<Boolean> callback) {
        GameUserProfile profile = user.profile();
        WhitelistState state = profile.whitelistState();
        if (state.isLoaded()) {
            callback.accept(Boolean.valueOf(state.isWhitelisted()));
        } else if (state == WhitelistState.UNKNOWN) {
            fetch(user.getUniqueId(), contains -> {
                ((DefaultGameUserProfile) profile).setWhitelistState(WhitelistState.of(contains.booleanValue()));
                callback.accept(contains);
            });
        }
    }

    private void fetch(UUID uuid, Consumer<Boolean> callback) {
        if (!this.loaded) {
            this.tasks.add(() -> {
                fetch(uuid, (Consumer<Boolean>) callback);
            });
        } else {
            callback.accept(Boolean.valueOf(contains(uuid)));
        }
    }

    private boolean contains(UUID uuid) {
        return this.whitelist != null && this.whitelist.contains(uuid);
    }

    public void reset() {
        this.loaded = false;
        this.whitelist = null;
        this.tasks.clear();
    }
}
