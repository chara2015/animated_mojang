package net.minecraft.commands.arguments.selector;

import com.google.common.primitives.Doubles;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen;
import net.minecraft.commands.arguments.selector.options.EntitySelectorOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.PermissionSetSupplier;
import net.minecraft.server.permissions.Permissions;
import net.minecraft.util.Mth;
import net.minecraft.util.ToFloatFunction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.levelgen.Density;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/commands/arguments/selector/EntitySelectorParser.class */
public class EntitySelectorParser {
    public static final char SYNTAX_SELECTOR_START = '@';
    private static final char SYNTAX_OPTIONS_START = '[';
    private static final char SYNTAX_OPTIONS_END = ']';
    public static final char SYNTAX_OPTIONS_KEY_VALUE_SEPARATOR = '=';
    private static final char SYNTAX_OPTIONS_SEPARATOR = ',';
    public static final char SYNTAX_NOT = '!';
    public static final char SYNTAX_TAG = '#';
    private static final char SELECTOR_NEAREST_PLAYER = 'p';
    private static final char SELECTOR_ALL_PLAYERS = 'a';
    private static final char SELECTOR_RANDOM_PLAYERS = 'r';
    private static final char SELECTOR_CURRENT_ENTITY = 's';
    private static final char SELECTOR_ALL_ENTITIES = 'e';
    private static final char SELECTOR_NEAREST_ENTITY = 'n';
    public static final SimpleCommandExceptionType ERROR_INVALID_NAME_OR_UUID = new SimpleCommandExceptionType(Component.translatable("argument.entity.invalid"));
    public static final DynamicCommandExceptionType ERROR_UNKNOWN_SELECTOR_TYPE = new DynamicCommandExceptionType($$0 -> {
        return Component.translatableEscape("argument.entity.selector.unknown", $$0);
    });
    public static final SimpleCommandExceptionType ERROR_SELECTORS_NOT_ALLOWED = new SimpleCommandExceptionType(Component.translatable("argument.entity.selector.not_allowed"));
    public static final SimpleCommandExceptionType ERROR_MISSING_SELECTOR_TYPE = new SimpleCommandExceptionType(Component.translatable("argument.entity.selector.missing"));
    public static final SimpleCommandExceptionType ERROR_EXPECTED_END_OF_OPTIONS = new SimpleCommandExceptionType(Component.translatable("argument.entity.options.unterminated"));
    public static final DynamicCommandExceptionType ERROR_EXPECTED_OPTION_VALUE = new DynamicCommandExceptionType($$0 -> {
        return Component.translatableEscape("argument.entity.options.valueless", $$0);
    });
    public static final BiConsumer<Vec3, List<? extends Entity>> ORDER_NEAREST = ($$0, $$1) -> {
        $$1.sort(($$1, $$2) -> {
            return Doubles.compare($$1.distanceToSqr($$0), $$2.distanceToSqr($$0));
        });
    };
    public static final BiConsumer<Vec3, List<? extends Entity>> ORDER_FURTHEST = ($$0, $$1) -> {
        $$1.sort(($$1, $$2) -> {
            return Doubles.compare($$2.distanceToSqr($$0), $$1.distanceToSqr($$0));
        });
    };
    public static final BiConsumer<Vec3, List<? extends Entity>> ORDER_RANDOM = ($$0, $$1) -> {
        Collections.shuffle($$1);
    };
    public static final BiFunction<SuggestionsBuilder, Consumer<SuggestionsBuilder>, CompletableFuture<Suggestions>> SUGGEST_NOTHING = ($$0, $$1) -> {
        return $$0.buildFuture();
    };
    private final StringReader reader;
    private final boolean allowSelectors;
    private int maxResults;
    private boolean includesEntities;
    private boolean worldLimited;
    private MinMaxBounds.Doubles distance;
    private MinMaxBounds.Ints level;
    private Double x;
    private Double y;
    private Double z;
    private Double deltaX;
    private Double deltaY;
    private Double deltaZ;
    private MinMaxBounds.FloatDegrees rotX;
    private MinMaxBounds.FloatDegrees rotY;
    private boolean currentEntity;
    private String playerName;
    private int startPosition;
    private UUID entityUUID;
    private boolean hasNameEquals;
    private boolean hasNameNotEquals;
    private boolean isLimited;
    private boolean isSorted;
    private boolean hasGamemodeEquals;
    private boolean hasGamemodeNotEquals;
    private boolean hasTeamEquals;
    private boolean hasTeamNotEquals;
    private EntityType<?> type;
    private boolean typeInverse;
    private boolean hasScores;
    private boolean hasAdvancements;
    private boolean usesSelectors;
    private final List<Predicate<Entity>> predicates = new ArrayList();
    private BiConsumer<Vec3, List<? extends Entity>> order = EntitySelector.ORDER_ARBITRARY;
    private BiFunction<SuggestionsBuilder, Consumer<SuggestionsBuilder>, CompletableFuture<Suggestions>> suggestions = SUGGEST_NOTHING;

    public EntitySelectorParser(StringReader $$0, boolean $$1) {
        this.reader = $$0;
        this.allowSelectors = $$1;
    }

    public static <S> boolean allowSelectors(S $$0) {
        if ($$0 instanceof PermissionSetSupplier) {
            PermissionSetSupplier $$1 = (PermissionSetSupplier) $$0;
            if ($$1.permissions().hasPermission(Permissions.COMMANDS_ENTITY_SELECTORS)) {
                return true;
            }
        }
        return false;
    }

    @Deprecated
    public static boolean allowSelectors(PermissionSetSupplier $$0) {
        return $$0.permissions().hasPermission(Permissions.COMMANDS_ENTITY_SELECTORS);
    }

    public EntitySelector getSelector() {
        AABB $$3;
        Function<Vec3, Vec3> $$5;
        if (this.deltaX != null || this.deltaY != null || this.deltaZ != null) {
            $$3 = createAabb(this.deltaX == null ? Density.SURFACE : this.deltaX.doubleValue(), this.deltaY == null ? Density.SURFACE : this.deltaY.doubleValue(), this.deltaZ == null ? Density.SURFACE : this.deltaZ.doubleValue());
        } else if (this.distance != null && this.distance.max().isPresent()) {
            double $$1 = this.distance.max().get().doubleValue();
            $$3 = new AABB(-$$1, -$$1, -$$1, $$1 + 1.0d, $$1 + 1.0d, $$1 + 1.0d);
        } else {
            $$3 = null;
        }
        if (this.x == null && this.y == null && this.z == null) {
            $$5 = $$0 -> {
                return $$0;
            };
        } else {
            $$5 = $$02 -> {
                return new Vec3(this.x == null ? $$02.x : this.x.doubleValue(), this.y == null ? $$02.y : this.y.doubleValue(), this.z == null ? $$02.z : this.z.doubleValue());
            };
        }
        return new EntitySelector(this.maxResults, this.includesEntities, this.worldLimited, List.copyOf(this.predicates), this.distance, $$5, $$3, this.order, this.currentEntity, this.playerName, this.entityUUID, this.type, this.usesSelectors);
    }

    private AABB createAabb(double $$0, double $$1, double $$2) {
        boolean $$3 = $$0 < Density.SURFACE;
        boolean $$4 = $$1 < Density.SURFACE;
        boolean $$5 = $$2 < Density.SURFACE;
        double $$6 = $$3 ? $$0 : Density.SURFACE;
        double $$7 = $$4 ? $$1 : Density.SURFACE;
        double $$8 = $$5 ? $$2 : Density.SURFACE;
        double $$9 = ($$3 ? Density.SURFACE : $$0) + 1.0d;
        double $$10 = ($$4 ? Density.SURFACE : $$1) + 1.0d;
        double $$11 = ($$5 ? Density.SURFACE : $$2) + 1.0d;
        return new AABB($$6, $$7, $$8, $$9, $$10, $$11);
    }

    private void finalizePredicates() {
        if (this.rotX != null) {
            this.predicates.add(createRotationPredicate(this.rotX, (v0) -> {
                return v0.getXRot();
            }));
        }
        if (this.rotY != null) {
            this.predicates.add(createRotationPredicate(this.rotY, (v0) -> {
                return v0.getYRot();
            }));
        }
        if (this.level != null) {
            this.predicates.add($$0 -> {
                if ($$0 instanceof ServerPlayer) {
                    ServerPlayer $$1 = (ServerPlayer) $$0;
                    if (this.level.matches($$1.experienceLevel)) {
                        return true;
                    }
                }
                return false;
            });
        }
    }

    private Predicate<Entity> createRotationPredicate(MinMaxBounds.FloatDegrees $$0, ToFloatFunction<Entity> $$1) {
        float $$2 = Mth.wrapDegrees($$0.min().orElse(Float.valueOf(0.0f)).floatValue());
        float $$3 = Mth.wrapDegrees($$0.max().orElse(Float.valueOf(359.0f)).floatValue());
        return $$32 -> {
            float $$4 = Mth.wrapDegrees($$1.applyAsFloat($$32));
            return $$2 > $$3 ? $$4 >= $$2 || $$4 <= $$3 : $$4 >= $$2 && $$4 <= $$3;
        };
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.mojang.brigadier.exceptions.CommandSyntaxException */
    protected void parseSelector() throws CommandSyntaxException {
        boolean $$8;
        this.usesSelectors = true;
        this.suggestions = this::suggestSelector;
        if (!this.reader.canRead()) {
            throw ERROR_MISSING_SELECTOR_TYPE.createWithContext(this.reader);
        }
        int $$0 = this.reader.getCursor();
        char $$1 = this.reader.read();
        switch ($$1) {
            case SELECTOR_ALL_PLAYERS /* 97 */:
                this.maxResults = Integer.MAX_VALUE;
                this.includesEntities = false;
                this.order = EntitySelector.ORDER_ARBITRARY;
                limitToType(EntityType.PLAYER);
                $$8 = false;
                break;
            case LivingEntity.EQUIPMENT_SLOT_OFFSET /* 98 */:
            case Item.ABSOLUTE_MAX_STACK_SIZE /* 99 */:
            case 'd':
            case ClientboundGameEventPacket.DEMO_PARAM_HINT_2 /* 102 */:
            case ClientboundGameEventPacket.DEMO_PARAM_HINT_3 /* 103 */:
            case ClientboundGameEventPacket.DEMO_PARAM_HINT_4 /* 104 */:
            case 'i':
            case LivingEntity.SADDLE_OFFSET /* 106 */:
            case 'k':
            case 'l':
            case 'm':
            case 'o':
            case AdvancementsScreen.WINDOW_INSIDE_HEIGHT /* 113 */:
            default:
                this.reader.setCursor($$0);
                throw ERROR_UNKNOWN_SELECTOR_TYPE.createWithContext(this.reader, "@" + String.valueOf($$1));
            case 'e':
                this.maxResults = Integer.MAX_VALUE;
                this.includesEntities = true;
                this.order = EntitySelector.ORDER_ARBITRARY;
                $$8 = true;
                break;
            case SELECTOR_NEAREST_ENTITY /* 110 */:
                this.maxResults = 1;
                this.includesEntities = true;
                this.order = ORDER_NEAREST;
                $$8 = true;
                break;
            case SELECTOR_NEAREST_PLAYER /* 112 */:
                this.maxResults = 1;
                this.includesEntities = false;
                this.order = ORDER_NEAREST;
                limitToType(EntityType.PLAYER);
                $$8 = false;
                break;
            case 'r':
                this.maxResults = 1;
                this.includesEntities = false;
                this.order = ORDER_RANDOM;
                limitToType(EntityType.PLAYER);
                $$8 = false;
                break;
            case SELECTOR_CURRENT_ENTITY /* 115 */:
                this.maxResults = 1;
                this.includesEntities = true;
                this.currentEntity = true;
                $$8 = false;
                break;
        }
        if ($$8) {
            this.predicates.add((v0) -> {
                return v0.isAlive();
            });
        }
        this.suggestions = this::suggestOpenOptions;
        if (this.reader.canRead() && this.reader.peek() == '[') {
            this.reader.skip();
            this.suggestions = this::suggestOptionsKeyOrClose;
            parseOptions();
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.mojang.brigadier.exceptions.CommandSyntaxException */
    protected void parseNameOrUUID() throws CommandSyntaxException {
        if (this.reader.canRead()) {
            this.suggestions = this::suggestName;
        }
        int $$0 = this.reader.getCursor();
        String $$1 = this.reader.readString();
        try {
            this.entityUUID = UUID.fromString($$1);
            this.includesEntities = true;
        } catch (IllegalArgumentException e) {
            if ($$1.isEmpty() || $$1.length() > 16) {
                this.reader.setCursor($$0);
                throw ERROR_INVALID_NAME_OR_UUID.createWithContext(this.reader);
            }
            this.includesEntities = false;
            this.playerName = $$1;
        }
        this.maxResults = 1;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.mojang.brigadier.exceptions.CommandSyntaxException */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0062, code lost:
    
        r4.reader.setCursor(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0075, code lost:
    
        throw net.minecraft.commands.arguments.selector.EntitySelectorParser.ERROR_EXPECTED_OPTION_VALUE.createWithContext(r4.reader, r0);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void parseOptions() throws com.mojang.brigadier.exceptions.CommandSyntaxException {
        /*
            Method dump skipped, instruction units count: 273
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: net.minecraft.commands.arguments.selector.EntitySelectorParser.parseOptions():void");
    }

    public boolean shouldInvertValue() {
        this.reader.skipWhitespace();
        if (this.reader.canRead() && this.reader.peek() == '!') {
            this.reader.skip();
            this.reader.skipWhitespace();
            return true;
        }
        return false;
    }

    public boolean isTag() {
        this.reader.skipWhitespace();
        if (this.reader.canRead() && this.reader.peek() == '#') {
            this.reader.skip();
            this.reader.skipWhitespace();
            return true;
        }
        return false;
    }

    public StringReader getReader() {
        return this.reader;
    }

    public void addPredicate(Predicate<Entity> $$0) {
        this.predicates.add($$0);
    }

    public void setWorldLimited() {
        this.worldLimited = true;
    }

    public MinMaxBounds.Doubles getDistance() {
        return this.distance;
    }

    public void setDistance(MinMaxBounds.Doubles $$0) {
        this.distance = $$0;
    }

    public MinMaxBounds.Ints getLevel() {
        return this.level;
    }

    public void setLevel(MinMaxBounds.Ints $$0) {
        this.level = $$0;
    }

    public MinMaxBounds.FloatDegrees getRotX() {
        return this.rotX;
    }

    public void setRotX(MinMaxBounds.FloatDegrees $$0) {
        this.rotX = $$0;
    }

    public MinMaxBounds.FloatDegrees getRotY() {
        return this.rotY;
    }

    public void setRotY(MinMaxBounds.FloatDegrees $$0) {
        this.rotY = $$0;
    }

    public Double getX() {
        return this.x;
    }

    public Double getY() {
        return this.y;
    }

    public Double getZ() {
        return this.z;
    }

    public void setX(double $$0) {
        this.x = Double.valueOf($$0);
    }

    public void setY(double $$0) {
        this.y = Double.valueOf($$0);
    }

    public void setZ(double $$0) {
        this.z = Double.valueOf($$0);
    }

    public void setDeltaX(double $$0) {
        this.deltaX = Double.valueOf($$0);
    }

    public void setDeltaY(double $$0) {
        this.deltaY = Double.valueOf($$0);
    }

    public void setDeltaZ(double $$0) {
        this.deltaZ = Double.valueOf($$0);
    }

    public Double getDeltaX() {
        return this.deltaX;
    }

    public Double getDeltaY() {
        return this.deltaY;
    }

    public Double getDeltaZ() {
        return this.deltaZ;
    }

    public void setMaxResults(int $$0) {
        this.maxResults = $$0;
    }

    public void setIncludesEntities(boolean $$0) {
        this.includesEntities = $$0;
    }

    public BiConsumer<Vec3, List<? extends Entity>> getOrder() {
        return this.order;
    }

    public void setOrder(BiConsumer<Vec3, List<? extends Entity>> $$0) {
        this.order = $$0;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.mojang.brigadier.exceptions.CommandSyntaxException */
    public EntitySelector parse() throws CommandSyntaxException {
        this.startPosition = this.reader.getCursor();
        this.suggestions = this::suggestNameOrSelector;
        if (this.reader.canRead() && this.reader.peek() == '@') {
            if (!this.allowSelectors) {
                throw ERROR_SELECTORS_NOT_ALLOWED.createWithContext(this.reader);
            }
            this.reader.skip();
            parseSelector();
        } else {
            parseNameOrUUID();
        }
        finalizePredicates();
        return getSelector();
    }

    private static void fillSelectorSuggestions(SuggestionsBuilder $$0) {
        $$0.suggest("@p", Component.translatable("argument.entity.selector.nearestPlayer"));
        $$0.suggest("@a", Component.translatable("argument.entity.selector.allPlayers"));
        $$0.suggest("@r", Component.translatable("argument.entity.selector.randomPlayer"));
        $$0.suggest("@s", Component.translatable("argument.entity.selector.self"));
        $$0.suggest("@e", Component.translatable("argument.entity.selector.allEntities"));
        $$0.suggest("@n", Component.translatable("argument.entity.selector.nearestEntity"));
    }

    private CompletableFuture<Suggestions> suggestNameOrSelector(SuggestionsBuilder $$0, Consumer<SuggestionsBuilder> $$1) {
        $$1.accept($$0);
        if (this.allowSelectors) {
            fillSelectorSuggestions($$0);
        }
        return $$0.buildFuture();
    }

    private CompletableFuture<Suggestions> suggestName(SuggestionsBuilder $$0, Consumer<SuggestionsBuilder> $$1) {
        SuggestionsBuilder $$2 = $$0.createOffset(this.startPosition);
        $$1.accept($$2);
        return $$0.add($$2).buildFuture();
    }

    private CompletableFuture<Suggestions> suggestSelector(SuggestionsBuilder $$0, Consumer<SuggestionsBuilder> $$1) {
        SuggestionsBuilder $$2 = $$0.createOffset($$0.getStart() - 1);
        fillSelectorSuggestions($$2);
        $$0.add($$2);
        return $$0.buildFuture();
    }

    private CompletableFuture<Suggestions> suggestOpenOptions(SuggestionsBuilder $$0, Consumer<SuggestionsBuilder> $$1) {
        $$0.suggest(String.valueOf('['));
        return $$0.buildFuture();
    }

    private CompletableFuture<Suggestions> suggestOptionsKeyOrClose(SuggestionsBuilder $$0, Consumer<SuggestionsBuilder> $$1) {
        $$0.suggest(String.valueOf(']'));
        EntitySelectorOptions.suggestNames(this, $$0);
        return $$0.buildFuture();
    }

    private CompletableFuture<Suggestions> suggestOptionsKey(SuggestionsBuilder $$0, Consumer<SuggestionsBuilder> $$1) {
        EntitySelectorOptions.suggestNames(this, $$0);
        return $$0.buildFuture();
    }

    private CompletableFuture<Suggestions> suggestOptionsNextOrClose(SuggestionsBuilder $$0, Consumer<SuggestionsBuilder> $$1) {
        $$0.suggest(String.valueOf(','));
        $$0.suggest(String.valueOf(']'));
        return $$0.buildFuture();
    }

    private CompletableFuture<Suggestions> suggestEquals(SuggestionsBuilder $$0, Consumer<SuggestionsBuilder> $$1) {
        $$0.suggest(String.valueOf('='));
        return $$0.buildFuture();
    }

    public boolean isCurrentEntity() {
        return this.currentEntity;
    }

    public void setSuggestions(BiFunction<SuggestionsBuilder, Consumer<SuggestionsBuilder>, CompletableFuture<Suggestions>> $$0) {
        this.suggestions = $$0;
    }

    public CompletableFuture<Suggestions> fillSuggestions(SuggestionsBuilder $$0, Consumer<SuggestionsBuilder> $$1) {
        return this.suggestions.apply($$0.createOffset(this.reader.getCursor()), $$1);
    }

    public boolean hasNameEquals() {
        return this.hasNameEquals;
    }

    public void setHasNameEquals(boolean $$0) {
        this.hasNameEquals = $$0;
    }

    public boolean hasNameNotEquals() {
        return this.hasNameNotEquals;
    }

    public void setHasNameNotEquals(boolean $$0) {
        this.hasNameNotEquals = $$0;
    }

    public boolean isLimited() {
        return this.isLimited;
    }

    public void setLimited(boolean $$0) {
        this.isLimited = $$0;
    }

    public boolean isSorted() {
        return this.isSorted;
    }

    public void setSorted(boolean $$0) {
        this.isSorted = $$0;
    }

    public boolean hasGamemodeEquals() {
        return this.hasGamemodeEquals;
    }

    public void setHasGamemodeEquals(boolean $$0) {
        this.hasGamemodeEquals = $$0;
    }

    public boolean hasGamemodeNotEquals() {
        return this.hasGamemodeNotEquals;
    }

    public void setHasGamemodeNotEquals(boolean $$0) {
        this.hasGamemodeNotEquals = $$0;
    }

    public boolean hasTeamEquals() {
        return this.hasTeamEquals;
    }

    public void setHasTeamEquals(boolean $$0) {
        this.hasTeamEquals = $$0;
    }

    public boolean hasTeamNotEquals() {
        return this.hasTeamNotEquals;
    }

    public void setHasTeamNotEquals(boolean $$0) {
        this.hasTeamNotEquals = $$0;
    }

    public void limitToType(EntityType<?> $$0) {
        this.type = $$0;
    }

    public void setTypeLimitedInversely() {
        this.typeInverse = true;
    }

    public boolean isTypeLimited() {
        return this.type != null;
    }

    public boolean isTypeLimitedInversely() {
        return this.typeInverse;
    }

    public boolean hasScores() {
        return this.hasScores;
    }

    public void setHasScores(boolean $$0) {
        this.hasScores = $$0;
    }

    public boolean hasAdvancements() {
        return this.hasAdvancements;
    }

    public void setHasAdvancements(boolean $$0) {
        this.hasAdvancements = $$0;
    }
}
