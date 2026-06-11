package net.minecraft.server.jsonrpc.methods;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import java.util.Optional;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.status.ServerStatus;
import net.minecraft.server.jsonrpc.api.PlayerDto;
import net.minecraft.server.jsonrpc.internalapi.MinecraftApi;
import net.minecraft.server.level.ServerPlayer;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/jsonrpc/methods/ServerStateService.class */
public class ServerStateService {

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/jsonrpc/methods/ServerStateService$ServerState.class */
    public static final class ServerState extends Record {
        private final boolean started;
        private final List<PlayerDto> players;
        private final ServerStatus.Version version;
        public static final Codec<ServerState> CODEC = RecordCodecBuilder.create($$0 -> {
            return $$0.group(Codec.BOOL.fieldOf("started").forGetter((v0) -> {
                return v0.started();
            }), PlayerDto.CODEC.codec().listOf().lenientOptionalFieldOf("players", List.of()).forGetter((v0) -> {
                return v0.players();
            }), ServerStatus.Version.CODEC.fieldOf("version").forGetter((v0) -> {
                return v0.version();
            })).apply($$0, (v1, v2, v3) -> {
                return new ServerState(v1, v2, v3);
            });
        });
        public static final ServerState NOT_STARTED = new ServerState(false, List.of(), ServerStatus.Version.current());

        public ServerState(boolean $$0, List<PlayerDto> $$1, ServerStatus.Version $$2) {
            this.started = $$0;
            this.players = $$1;
            this.version = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ServerState.class), ServerState.class, "started;players;version", "FIELD:Lnet/minecraft/server/jsonrpc/methods/ServerStateService$ServerState;->started:Z", "FIELD:Lnet/minecraft/server/jsonrpc/methods/ServerStateService$ServerState;->players:Ljava/util/List;", "FIELD:Lnet/minecraft/server/jsonrpc/methods/ServerStateService$ServerState;->version:Lnet/minecraft/network/protocol/status/ServerStatus$Version;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ServerState.class), ServerState.class, "started;players;version", "FIELD:Lnet/minecraft/server/jsonrpc/methods/ServerStateService$ServerState;->started:Z", "FIELD:Lnet/minecraft/server/jsonrpc/methods/ServerStateService$ServerState;->players:Ljava/util/List;", "FIELD:Lnet/minecraft/server/jsonrpc/methods/ServerStateService$ServerState;->version:Lnet/minecraft/network/protocol/status/ServerStatus$Version;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ServerState.class, Object.class), ServerState.class, "started;players;version", "FIELD:Lnet/minecraft/server/jsonrpc/methods/ServerStateService$ServerState;->started:Z", "FIELD:Lnet/minecraft/server/jsonrpc/methods/ServerStateService$ServerState;->players:Ljava/util/List;", "FIELD:Lnet/minecraft/server/jsonrpc/methods/ServerStateService$ServerState;->version:Lnet/minecraft/network/protocol/status/ServerStatus$Version;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public boolean started() {
            return this.started;
        }

        public List<PlayerDto> players() {
            return this.players;
        }

        public ServerStatus.Version version() {
            return this.version;
        }
    }

    public static ServerState status(MinecraftApi $$0) {
        if (!$$0.serverStateService().isReady()) {
            return ServerState.NOT_STARTED;
        }
        return new ServerState(true, PlayerService.get($$0), ServerStatus.Version.current());
    }

    public static boolean save(MinecraftApi $$0, boolean $$1, ClientInfo $$2) {
        return $$0.serverStateService().saveEverything(true, $$1, true, $$2);
    }

    public static boolean stop(MinecraftApi $$0, ClientInfo $$1) {
        $$0.submit(() -> {
            $$0.serverStateService().halt(false, $$1);
        });
        return true;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/jsonrpc/methods/ServerStateService$SystemMessage.class */
    public static final class SystemMessage extends Record {
        private final Message message;
        private final boolean overlay;
        private final Optional<List<PlayerDto>> receivingPlayers;
        public static final Codec<SystemMessage> CODEC = RecordCodecBuilder.create($$0 -> {
            return $$0.group(Message.CODEC.fieldOf("message").forGetter((v0) -> {
                return v0.message();
            }), Codec.BOOL.fieldOf("overlay").forGetter((v0) -> {
                return v0.overlay();
            }), PlayerDto.CODEC.codec().listOf().lenientOptionalFieldOf("receivingPlayers").forGetter((v0) -> {
                return v0.receivingPlayers();
            })).apply($$0, (v1, v2, v3) -> {
                return new SystemMessage(v1, v2, v3);
            });
        });

        public SystemMessage(Message $$0, boolean $$1, Optional<List<PlayerDto>> $$2) {
            this.message = $$0;
            this.overlay = $$1;
            this.receivingPlayers = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, SystemMessage.class), SystemMessage.class, "message;overlay;receivingPlayers", "FIELD:Lnet/minecraft/server/jsonrpc/methods/ServerStateService$SystemMessage;->message:Lnet/minecraft/server/jsonrpc/methods/Message;", "FIELD:Lnet/minecraft/server/jsonrpc/methods/ServerStateService$SystemMessage;->overlay:Z", "FIELD:Lnet/minecraft/server/jsonrpc/methods/ServerStateService$SystemMessage;->receivingPlayers:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, SystemMessage.class), SystemMessage.class, "message;overlay;receivingPlayers", "FIELD:Lnet/minecraft/server/jsonrpc/methods/ServerStateService$SystemMessage;->message:Lnet/minecraft/server/jsonrpc/methods/Message;", "FIELD:Lnet/minecraft/server/jsonrpc/methods/ServerStateService$SystemMessage;->overlay:Z", "FIELD:Lnet/minecraft/server/jsonrpc/methods/ServerStateService$SystemMessage;->receivingPlayers:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, SystemMessage.class, Object.class), SystemMessage.class, "message;overlay;receivingPlayers", "FIELD:Lnet/minecraft/server/jsonrpc/methods/ServerStateService$SystemMessage;->message:Lnet/minecraft/server/jsonrpc/methods/Message;", "FIELD:Lnet/minecraft/server/jsonrpc/methods/ServerStateService$SystemMessage;->overlay:Z", "FIELD:Lnet/minecraft/server/jsonrpc/methods/ServerStateService$SystemMessage;->receivingPlayers:Ljava/util/Optional;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Message message() {
            return this.message;
        }

        public boolean overlay() {
            return this.overlay;
        }

        public Optional<List<PlayerDto>> receivingPlayers() {
            return this.receivingPlayers;
        }
    }

    public static boolean systemMessage(MinecraftApi $$0, SystemMessage $$1, ClientInfo $$2) {
        ServerPlayer $$6;
        Component $$3 = $$1.message().asComponent().orElse(null);
        if ($$3 == null) {
            return false;
        }
        if ($$1.receivingPlayers().isPresent()) {
            if ($$1.receivingPlayers().get().isEmpty()) {
                return false;
            }
            for (PlayerDto $$4 : $$1.receivingPlayers().get()) {
                if ($$4.id().isPresent()) {
                    $$6 = $$0.playerListService().getPlayer($$4.id().get());
                } else if ($$4.name().isPresent()) {
                    $$6 = $$0.playerListService().getPlayerByName($$4.name().get());
                }
                if ($$6 != null) {
                    $$6.sendSystemMessage($$3, $$1.overlay());
                }
            }
            return true;
        }
        $$0.serverStateService().broadcastSystemMessage($$3, $$1.overlay(), $$2);
        return true;
    }
}
