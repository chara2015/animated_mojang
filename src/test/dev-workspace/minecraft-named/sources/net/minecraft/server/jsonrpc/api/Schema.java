package net.minecraft.server.jsonrpc.api;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.core.UUIDUtil;
import net.minecraft.server.jsonrpc.methods.BanlistService;
import net.minecraft.server.jsonrpc.methods.DiscoveryService;
import net.minecraft.server.jsonrpc.methods.GameRulesService;
import net.minecraft.server.jsonrpc.methods.IpBanlistService;
import net.minecraft.server.jsonrpc.methods.Message;
import net.minecraft.server.jsonrpc.methods.OperatorService;
import net.minecraft.server.jsonrpc.methods.PlayerService;
import net.minecraft.server.jsonrpc.methods.ServerStateService;
import net.minecraft.server.permissions.PermissionLevel;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.profiling.jfr.event.ChunkRegionIoEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;
import net.minecraft.world.level.gamerules.GameRuleType;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/jsonrpc/api/Schema.class */
public final class Schema<T> extends Record {
    private final Optional<URI> reference;
    private final List<String> type;
    private final Optional<Schema<?>> items;
    private final Map<String, Schema<?>> properties;
    private final List<String> enumValues;
    private final Codec<T> codec;
    public static final Codec<? extends Schema<?>> CODEC = Codec.recursive("Schema", $$0 -> {
        return RecordCodecBuilder.create($$1 -> {
            return $$1.group(ReferenceUtil.REFERENCE_CODEC.optionalFieldOf("$ref").forGetter((v0) -> {
                return v0.reference();
            }), ExtraCodecs.compactListCodec(Codec.STRING).optionalFieldOf(ChunkRegionIoEvent.Fields.TYPE, List.of()).forGetter((v0) -> {
                return v0.type();
            }), $$0.optionalFieldOf("items").forGetter((v0) -> {
                return v0.items();
            }), Codec.unboundedMap(Codec.STRING, $$0).optionalFieldOf("properties", Map.of()).forGetter((v0) -> {
                return v0.properties();
            }), Codec.STRING.listOf().optionalFieldOf("enum", List.of()).forGetter((v0) -> {
                return v0.enumValues();
            })).apply($$1, ($$0, $$1, $$2, $$3, $$4) -> {
                return null;
            });
        });
    }).validate($$02 -> {
        if ($$02 == null) {
            return DataResult.error(() -> {
                return "Should not deserialize schema";
            });
        }
        return DataResult.success($$02);
    });
    private static final List<SchemaComponent<?>> SCHEMA_REGISTRY = new ArrayList();
    public static final Schema<Boolean> BOOL_SCHEMA = ofType("boolean", Codec.BOOL);
    public static final Schema<Integer> INT_SCHEMA = ofType("integer", Codec.INT);
    public static final Schema<Either<Boolean, Integer>> BOOL_OR_INT_SCHEMA = ofTypes(List.of("boolean", "integer"), Codec.either(Codec.BOOL, Codec.INT));
    public static final Schema<Float> NUMBER_SCHEMA = ofType("number", Codec.FLOAT);
    public static final Schema<String> STRING_SCHEMA = ofType("string", Codec.STRING);
    public static final Schema<UUID> UUID_SCHEMA = ofType("string", UUIDUtil.CODEC);
    public static final Schema<DiscoveryService.DiscoverResponse> DISCOVERY_SCHEMA = ofType("string", DiscoveryService.DiscoverResponse.CODEC.codec());
    public static final SchemaComponent<Difficulty> DIFFICULTY_SCHEMA = registerSchema("difficulty", ofEnum(Difficulty::values, Difficulty.CODEC));
    public static final SchemaComponent<GameType> GAME_TYPE_SCHEMA = registerSchema("game_type", ofEnum(GameType::values, GameType.CODEC));
    public static final Schema<PermissionLevel> PERMISSION_LEVEL_SCHEMA = ofType("integer", PermissionLevel.INT_CODEC);
    public static final SchemaComponent<PlayerDto> PLAYER_SCHEMA = registerSchema("player", record(PlayerDto.CODEC.codec()).withField(Entity.TAG_ID, UUID_SCHEMA).withField(JigsawBlockEntity.NAME, STRING_SCHEMA));
    public static final SchemaComponent<DiscoveryService.DiscoverInfo> VERSION_SCHEMA = registerSchema("version", record(DiscoveryService.DiscoverInfo.CODEC.codec()).withField(JigsawBlockEntity.NAME, STRING_SCHEMA).withField("protocol", INT_SCHEMA));
    public static final SchemaComponent<ServerStateService.ServerState> SERVER_STATE_SCHEMA = registerSchema("server_state", record(ServerStateService.ServerState.CODEC).withField("started", BOOL_SCHEMA).withField("players", PLAYER_SCHEMA.asRef().asArray()).withField("version", VERSION_SCHEMA.asRef()));
    public static final Schema<GameRuleType> RULE_TYPE_SCHEMA = ofEnum(GameRuleType::values);
    public static final SchemaComponent<GameRulesService.GameRuleUpdate<?>> TYPED_GAME_RULE_SCHEMA = registerSchema("typed_game_rule", record(GameRulesService.GameRuleUpdate.TYPED_CODEC).withField("key", STRING_SCHEMA).withField("value", BOOL_OR_INT_SCHEMA).withField(ChunkRegionIoEvent.Fields.TYPE, RULE_TYPE_SCHEMA));
    public static final SchemaComponent<GameRulesService.GameRuleUpdate<?>> UNTYPED_GAME_RULE_SCHEMA = registerSchema("untyped_game_rule", record(GameRulesService.GameRuleUpdate.CODEC).withField("key", STRING_SCHEMA).withField("value", BOOL_OR_INT_SCHEMA));
    public static final SchemaComponent<Message> MESSAGE_SCHEMA = registerSchema("message", record(Message.CODEC).withField("literal", STRING_SCHEMA).withField("translatable", STRING_SCHEMA).withField("translatableParams", STRING_SCHEMA.asArray()));
    public static final SchemaComponent<ServerStateService.SystemMessage> SYSTEM_MESSAGE_SCHEMA = registerSchema("system_message", record(ServerStateService.SystemMessage.CODEC).withField("message", MESSAGE_SCHEMA.asRef()).withField("overlay", BOOL_SCHEMA).withField("receivingPlayers", PLAYER_SCHEMA.asRef().asArray()));
    public static final SchemaComponent<PlayerService.KickDto> KICK_PLAYER_SCHEMA = registerSchema("kick_player", record(PlayerService.KickDto.CODEC.codec()).withField("message", MESSAGE_SCHEMA.asRef()).withField("player", PLAYER_SCHEMA.asRef()));
    public static final SchemaComponent<OperatorService.OperatorDto> OPERATOR_SCHEMA = registerSchema("operator", record(OperatorService.OperatorDto.CODEC.codec()).withField("player", PLAYER_SCHEMA.asRef()).withField("bypassesPlayerLimit", BOOL_SCHEMA).withField("permissionLevel", INT_SCHEMA));
    public static final SchemaComponent<IpBanlistService.IncomingIpBanDto> INCOMING_IP_BAN_SCHEMA = registerSchema("incoming_ip_ban", record(IpBanlistService.IncomingIpBanDto.CODEC.codec()).withField("player", PLAYER_SCHEMA.asRef()).withField("ip", STRING_SCHEMA).withField("reason", STRING_SCHEMA).withField("source", STRING_SCHEMA).withField("expires", STRING_SCHEMA));
    public static final SchemaComponent<IpBanlistService.IpBanDto> IP_BAN_SCHEMA = registerSchema("ip_ban", record(IpBanlistService.IpBanDto.CODEC.codec()).withField("ip", STRING_SCHEMA).withField("reason", STRING_SCHEMA).withField("source", STRING_SCHEMA).withField("expires", STRING_SCHEMA));
    public static final SchemaComponent<BanlistService.UserBanDto> PLAYER_BAN_SCHEMA = registerSchema("user_ban", record(BanlistService.UserBanDto.CODEC.codec()).withField("player", PLAYER_SCHEMA.asRef()).withField("reason", STRING_SCHEMA).withField("source", STRING_SCHEMA).withField("expires", STRING_SCHEMA));

    public Schema(Optional<URI> $$0, List<String> $$1, Optional<Schema<?>> $$2, Map<String, Schema<?>> $$3, List<String> $$4, Codec<T> $$5) {
        this.reference = $$0;
        this.type = $$1;
        this.items = $$2;
        this.properties = $$3;
        this.enumValues = $$4;
        this.codec = $$5;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Schema.class), Schema.class, "reference;type;items;properties;enumValues;codec", "FIELD:Lnet/minecraft/server/jsonrpc/api/Schema;->reference:Ljava/util/Optional;", "FIELD:Lnet/minecraft/server/jsonrpc/api/Schema;->type:Ljava/util/List;", "FIELD:Lnet/minecraft/server/jsonrpc/api/Schema;->items:Ljava/util/Optional;", "FIELD:Lnet/minecraft/server/jsonrpc/api/Schema;->properties:Ljava/util/Map;", "FIELD:Lnet/minecraft/server/jsonrpc/api/Schema;->enumValues:Ljava/util/List;", "FIELD:Lnet/minecraft/server/jsonrpc/api/Schema;->codec:Lcom/mojang/serialization/Codec;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Schema.class), Schema.class, "reference;type;items;properties;enumValues;codec", "FIELD:Lnet/minecraft/server/jsonrpc/api/Schema;->reference:Ljava/util/Optional;", "FIELD:Lnet/minecraft/server/jsonrpc/api/Schema;->type:Ljava/util/List;", "FIELD:Lnet/minecraft/server/jsonrpc/api/Schema;->items:Ljava/util/Optional;", "FIELD:Lnet/minecraft/server/jsonrpc/api/Schema;->properties:Ljava/util/Map;", "FIELD:Lnet/minecraft/server/jsonrpc/api/Schema;->enumValues:Ljava/util/List;", "FIELD:Lnet/minecraft/server/jsonrpc/api/Schema;->codec:Lcom/mojang/serialization/Codec;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Schema.class, Object.class), Schema.class, "reference;type;items;properties;enumValues;codec", "FIELD:Lnet/minecraft/server/jsonrpc/api/Schema;->reference:Ljava/util/Optional;", "FIELD:Lnet/minecraft/server/jsonrpc/api/Schema;->type:Ljava/util/List;", "FIELD:Lnet/minecraft/server/jsonrpc/api/Schema;->items:Ljava/util/Optional;", "FIELD:Lnet/minecraft/server/jsonrpc/api/Schema;->properties:Ljava/util/Map;", "FIELD:Lnet/minecraft/server/jsonrpc/api/Schema;->enumValues:Ljava/util/List;", "FIELD:Lnet/minecraft/server/jsonrpc/api/Schema;->codec:Lcom/mojang/serialization/Codec;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Optional<URI> reference() {
        return this.reference;
    }

    public List<String> type() {
        return this.type;
    }

    public Optional<Schema<?>> items() {
        return this.items;
    }

    public Map<String, Schema<?>> properties() {
        return this.properties;
    }

    public List<String> enumValues() {
        return this.enumValues;
    }

    public Codec<T> codec() {
        return this.codec;
    }

    public static <T> Codec<Schema<T>> typedCodec() {
        return (Codec<Schema<T>>) CODEC;
    }

    public Schema<T> info() {
        return new Schema<>(this.reference, this.type, this.items.map((v0) -> {
            return v0.info();
        }), (Map) this.properties.entrySet().stream().collect(Collectors.toMap((v0) -> {
            return v0.getKey();
        }, $$0 -> {
            return ((Schema) $$0.getValue()).info();
        })), this.enumValues, this.codec);
    }

    private static <T> SchemaComponent<T> registerSchema(String $$0, Schema<T> $$1) {
        SchemaComponent<T> $$2 = new SchemaComponent<>($$0, ReferenceUtil.createLocalReference($$0), $$1);
        SCHEMA_REGISTRY.add($$2);
        return $$2;
    }

    public static List<SchemaComponent<?>> getSchemaRegistry() {
        return SCHEMA_REGISTRY;
    }

    public static <T> Schema<T> ofRef(URI $$0, Codec<T> $$1) {
        return new Schema<>(Optional.of($$0), List.of(), Optional.empty(), Map.of(), List.of(), $$1);
    }

    public static <T> Schema<T> ofType(String $$0, Codec<T> $$1) {
        return ofTypes(List.of($$0), $$1);
    }

    public static <T> Schema<T> ofTypes(List<String> $$0, Codec<T> $$1) {
        return new Schema<>(Optional.empty(), $$0, Optional.empty(), Map.of(), List.of(), $$1);
    }

    public static <E extends Enum<E> & StringRepresentable> Schema<E> ofEnum(Supplier<E[]> $$0) {
        return ofEnum($$0, StringRepresentable.fromEnum($$0));
    }

    public static <E extends Enum<E> & StringRepresentable> Schema<E> ofEnum(Supplier<E[]> supplier, Codec<E> codec) {
        return ofEnum((List<String>) Stream.of((Object[]) supplier.get()).map($$0 -> {
            return ((StringRepresentable) $$0).getSerializedName();
        }).toList(), (Codec) codec);
    }

    public static <T> Schema<T> ofEnum(List<String> $$0, Codec<T> $$1) {
        return new Schema<>(Optional.empty(), List.of("string"), Optional.empty(), Map.of(), $$0, $$1);
    }

    public static <T> Schema<List<T>> arrayOf(Schema<?> $$0, Codec<T> $$1) {
        return new Schema<>(Optional.empty(), List.of("array"), Optional.of($$0), Map.of(), List.of(), $$1.listOf());
    }

    public static <T> Schema<T> record(Codec<T> $$0) {
        return new Schema<>(Optional.empty(), List.of("object"), Optional.empty(), Map.of(), List.of(), $$0);
    }

    private static <T> Schema<T> record(Map<String, Schema<?>> $$0, Codec<T> $$1) {
        return new Schema<>(Optional.empty(), List.of("object"), Optional.empty(), $$0, List.of(), $$1);
    }

    public Schema<T> withField(String $$0, Schema<?> $$1) {
        HashMap<String, Schema<?>> $$2 = new HashMap<>(this.properties);
        $$2.put($$0, $$1);
        return record($$2, this.codec);
    }

    public Schema<List<T>> asArray() {
        return arrayOf(this, this.codec);
    }
}
