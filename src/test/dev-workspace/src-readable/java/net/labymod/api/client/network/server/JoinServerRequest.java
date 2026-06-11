package net.labymod.api.client.network.server;

import java.util.Objects;
import java.util.function.Consumer;
import net.labymod.api.Laby;
import net.labymod.api.client.network.server.ConnectableServerData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/network/server/JoinServerRequest.class */
public class JoinServerRequest {
    private final ConnectableServerData serverData;
    private final Object screen;
    private final Runnable loginExecutor;

    private JoinServerRequest(@NotNull ConnectableServerData serverData, @Nullable Object screen, @Nullable Runnable loginExecutor) {
        this.serverData = serverData;
        this.screen = screen;
        this.loginExecutor = loginExecutor;
    }

    public static Builder builder() {
        return new Builder();
    }

    @NotNull
    public ConnectableServerData serverData() {
        return this.serverData;
    }

    @Nullable
    public Object screen() {
        return Laby.labyAPI().minecraft().minecraftWindow().getMostInnerScreen(this.screen);
    }

    @Nullable
    public Runnable loginExecutor() {
        return this.loginExecutor;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/network/server/JoinServerRequest$Builder.class */
    public static class Builder {
        private ConnectableServerData serverData;
        private Object screen;
        private Runnable loginExecutor;

        private Builder() {
        }

        public Builder serverData(@NotNull ConnectableServerData serverData) {
            this.serverData = serverData;
            return this;
        }

        public Builder serverData(@NotNull Consumer<ConnectableServerData.Builder> consumer) {
            ConnectableServerData.Builder builder = ConnectableServerData.builder();
            consumer.accept(builder);
            return serverData(builder.build());
        }

        public Builder screen(@Nullable Object screen) {
            this.screen = screen;
            return this;
        }

        public Builder loginExecutor(@Nullable Runnable loginExecutor) {
            this.loginExecutor = loginExecutor;
            return this;
        }

        public JoinServerRequest build() {
            Objects.requireNonNull(this.serverData, "Server data cannot be null!");
            return new JoinServerRequest(this.serverData, this.screen, this.loginExecutor);
        }
    }
}
