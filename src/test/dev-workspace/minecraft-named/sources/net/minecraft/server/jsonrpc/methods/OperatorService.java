package net.minecraft.server.jsonrpc.methods;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import net.minecraft.server.jsonrpc.api.PlayerDto;
import net.minecraft.server.jsonrpc.internalapi.MinecraftApi;
import net.minecraft.server.permissions.PermissionLevel;
import net.minecraft.server.players.NameAndId;
import net.minecraft.server.players.ServerOpListEntry;
import net.minecraft.util.Util;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/jsonrpc/methods/OperatorService.class */
public class OperatorService {

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/jsonrpc/methods/OperatorService$OperatorDto.class */
    public static final class OperatorDto extends Record {
        private final PlayerDto player;
        private final Optional<PermissionLevel> permissionLevel;
        private final Optional<Boolean> bypassesPlayerLimit;
        public static final MapCodec<OperatorDto> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
            return $$0.group(PlayerDto.CODEC.codec().fieldOf("player").forGetter((v0) -> {
                return v0.player();
            }), PermissionLevel.INT_CODEC.optionalFieldOf("permissionLevel").forGetter((v0) -> {
                return v0.permissionLevel();
            }), Codec.BOOL.optionalFieldOf("bypassesPlayerLimit").forGetter((v0) -> {
                return v0.bypassesPlayerLimit();
            })).apply($$0, OperatorDto::new);
        });

        public OperatorDto(PlayerDto $$0, Optional<PermissionLevel> $$1, Optional<Boolean> $$2) {
            this.player = $$0;
            this.permissionLevel = $$1;
            this.bypassesPlayerLimit = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, OperatorDto.class), OperatorDto.class, "player;permissionLevel;bypassesPlayerLimit", "FIELD:Lnet/minecraft/server/jsonrpc/methods/OperatorService$OperatorDto;->player:Lnet/minecraft/server/jsonrpc/api/PlayerDto;", "FIELD:Lnet/minecraft/server/jsonrpc/methods/OperatorService$OperatorDto;->permissionLevel:Ljava/util/Optional;", "FIELD:Lnet/minecraft/server/jsonrpc/methods/OperatorService$OperatorDto;->bypassesPlayerLimit:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, OperatorDto.class), OperatorDto.class, "player;permissionLevel;bypassesPlayerLimit", "FIELD:Lnet/minecraft/server/jsonrpc/methods/OperatorService$OperatorDto;->player:Lnet/minecraft/server/jsonrpc/api/PlayerDto;", "FIELD:Lnet/minecraft/server/jsonrpc/methods/OperatorService$OperatorDto;->permissionLevel:Ljava/util/Optional;", "FIELD:Lnet/minecraft/server/jsonrpc/methods/OperatorService$OperatorDto;->bypassesPlayerLimit:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, OperatorDto.class, Object.class), OperatorDto.class, "player;permissionLevel;bypassesPlayerLimit", "FIELD:Lnet/minecraft/server/jsonrpc/methods/OperatorService$OperatorDto;->player:Lnet/minecraft/server/jsonrpc/api/PlayerDto;", "FIELD:Lnet/minecraft/server/jsonrpc/methods/OperatorService$OperatorDto;->permissionLevel:Ljava/util/Optional;", "FIELD:Lnet/minecraft/server/jsonrpc/methods/OperatorService$OperatorDto;->bypassesPlayerLimit:Ljava/util/Optional;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public PlayerDto player() {
            return this.player;
        }

        public Optional<PermissionLevel> permissionLevel() {
            return this.permissionLevel;
        }

        public Optional<Boolean> bypassesPlayerLimit() {
            return this.bypassesPlayerLimit;
        }

        public static OperatorDto from(ServerOpListEntry $$0) {
            return new OperatorDto(PlayerDto.from((NameAndId) Objects.requireNonNull($$0.getUser())), Optional.of($$0.permissions().level()), Optional.of(Boolean.valueOf($$0.getBypassesPlayerLimit())));
        }
    }

    public static List<OperatorDto> get(MinecraftApi $$0) {
        return $$0.operatorListService().getEntries().stream().filter($$02 -> {
            return $$02.getUser() != null;
        }).map(OperatorDto::from).toList();
    }

    public static List<OperatorDto> clear(MinecraftApi $$0, ClientInfo $$1) {
        $$0.operatorListService().clear($$1);
        return get($$0);
    }

    public static List<OperatorDto> remove(MinecraftApi $$0, List<PlayerDto> $$1, ClientInfo $$2) {
        List<CompletableFuture<Optional<NameAndId>>> $$3 = $$1.stream().map($$12 -> {
            return $$0.playerListService().getUser($$12.id(), $$12.name());
        }).toList();
        for (Optional<NameAndId> $$4 : (List) Util.sequence($$3).join()) {
            $$4.ifPresent($$22 -> {
                $$0.operatorListService().deop($$22, $$2);
            });
        }
        return get($$0);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/jsonrpc/methods/OperatorService$Op.class */
    static final class Op extends Record {
        private final NameAndId user;
        private final Optional<PermissionLevel> permissionLevel;
        private final Optional<Boolean> bypassesPlayerLimit;

        Op(NameAndId $$0, Optional<PermissionLevel> $$1, Optional<Boolean> $$2) {
            this.user = $$0;
            this.permissionLevel = $$1;
            this.bypassesPlayerLimit = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Op.class), Op.class, "user;permissionLevel;bypassesPlayerLimit", "FIELD:Lnet/minecraft/server/jsonrpc/methods/OperatorService$Op;->user:Lnet/minecraft/server/players/NameAndId;", "FIELD:Lnet/minecraft/server/jsonrpc/methods/OperatorService$Op;->permissionLevel:Ljava/util/Optional;", "FIELD:Lnet/minecraft/server/jsonrpc/methods/OperatorService$Op;->bypassesPlayerLimit:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Op.class), Op.class, "user;permissionLevel;bypassesPlayerLimit", "FIELD:Lnet/minecraft/server/jsonrpc/methods/OperatorService$Op;->user:Lnet/minecraft/server/players/NameAndId;", "FIELD:Lnet/minecraft/server/jsonrpc/methods/OperatorService$Op;->permissionLevel:Ljava/util/Optional;", "FIELD:Lnet/minecraft/server/jsonrpc/methods/OperatorService$Op;->bypassesPlayerLimit:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Op.class, Object.class), Op.class, "user;permissionLevel;bypassesPlayerLimit", "FIELD:Lnet/minecraft/server/jsonrpc/methods/OperatorService$Op;->user:Lnet/minecraft/server/players/NameAndId;", "FIELD:Lnet/minecraft/server/jsonrpc/methods/OperatorService$Op;->permissionLevel:Ljava/util/Optional;", "FIELD:Lnet/minecraft/server/jsonrpc/methods/OperatorService$Op;->bypassesPlayerLimit:Ljava/util/Optional;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public NameAndId user() {
            return this.user;
        }

        public Optional<PermissionLevel> permissionLevel() {
            return this.permissionLevel;
        }

        public Optional<Boolean> bypassesPlayerLimit() {
            return this.bypassesPlayerLimit;
        }
    }

    public static List<OperatorDto> add(MinecraftApi $$0, List<OperatorDto> $$1, ClientInfo $$2) {
        List<CompletableFuture<Optional<Op>>> $$3 = $$1.stream().map($$12 -> {
            return $$0.playerListService().getUser($$12.player().id(), $$12.player().name()).thenApply($$12 -> {
                return $$12.map($$12 -> {
                    return new Op($$12, $$12.permissionLevel(), $$12.bypassesPlayerLimit());
                });
            });
        }).toList();
        for (Optional<Op> $$4 : (List) Util.sequence($$3).join()) {
            $$4.ifPresent($$22 -> {
                $$0.operatorListService().op($$22.user(), $$22.permissionLevel(), $$22.bypassesPlayerLimit(), $$2);
            });
        }
        return get($$0);
    }

    public static List<OperatorDto> set(MinecraftApi $$0, List<OperatorDto> $$1, ClientInfo $$2) {
        List<CompletableFuture<Optional<Op>>> $$3 = $$1.stream().map($$12 -> {
            return $$0.playerListService().getUser($$12.player().id(), $$12.player().name()).thenApply($$12 -> {
                return $$12.map($$12 -> {
                    return new Op($$12, $$12.permissionLevel(), $$12.bypassesPlayerLimit());
                });
            });
        }).toList();
        Set<Op> $$4 = (Set) ((List) Util.sequence($$3).join()).stream().flatMap((v0) -> {
            return v0.stream();
        }).collect(Collectors.toSet());
        Set<Op> $$5 = (Set) $$0.operatorListService().getEntries().stream().filter($$02 -> {
            return $$02.getUser() != null;
        }).map($$03 -> {
            return new Op($$03.getUser(), Optional.of($$03.permissions().level()), Optional.of(Boolean.valueOf($$03.getBypassesPlayerLimit())));
        }).collect(Collectors.toSet());
        $$5.stream().filter($$13 -> {
            return !$$4.contains($$13);
        }).forEach($$22 -> {
            $$0.operatorListService().deop($$22.user(), $$2);
        });
        $$4.stream().filter($$14 -> {
            return !$$5.contains($$14);
        }).forEach($$23 -> {
            $$0.operatorListService().op($$23.user(), $$23.permissionLevel(), $$23.bypassesPlayerLimit(), $$2);
        });
        return get($$0);
    }
}
