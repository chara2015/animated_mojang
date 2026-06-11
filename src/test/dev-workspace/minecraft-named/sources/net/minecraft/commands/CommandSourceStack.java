package net.minecraft.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandExceptionType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.commands.execution.TraceCallbacks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.PermissionSet;
import net.minecraft.util.Mth;
import net.minecraft.util.TaskChainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/commands/CommandSourceStack.class */
public class CommandSourceStack implements ExecutionCommandSource<CommandSourceStack>, SharedSuggestionProvider {
    public static final SimpleCommandExceptionType ERROR_NOT_PLAYER = new SimpleCommandExceptionType(Component.translatable("permissions.requires.player"));
    public static final SimpleCommandExceptionType ERROR_NOT_ENTITY = new SimpleCommandExceptionType(Component.translatable("permissions.requires.entity"));
    private final CommandSource source;
    private final Vec3 worldPosition;
    private final ServerLevel level;
    private final PermissionSet permissions;
    private final String textName;
    private final Component displayName;
    private final MinecraftServer server;
    private final boolean silent;
    private final Entity entity;
    private final CommandResultCallback resultCallback;
    private final EntityAnchorArgument.Anchor anchor;
    private final Vec2 rotation;
    private final CommandSigningContext signingContext;
    private final TaskChainer chatMessageChainer;

    public CommandSourceStack(CommandSource $$0, Vec3 $$1, Vec2 $$2, ServerLevel $$3, PermissionSet $$4, String $$5, Component $$6, MinecraftServer $$7, Entity $$8) {
        this($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8, false, CommandResultCallback.EMPTY, EntityAnchorArgument.Anchor.FEET, CommandSigningContext.ANONYMOUS, TaskChainer.immediate($$7));
    }

    private CommandSourceStack(CommandSource $$0, Vec3 $$1, Vec2 $$2, ServerLevel $$3, PermissionSet $$4, String $$5, Component $$6, MinecraftServer $$7, Entity $$8, boolean $$9, CommandResultCallback $$10, EntityAnchorArgument.Anchor $$11, CommandSigningContext $$12, TaskChainer $$13) {
        this.source = $$0;
        this.worldPosition = $$1;
        this.level = $$3;
        this.silent = $$9;
        this.entity = $$8;
        this.permissions = $$4;
        this.textName = $$5;
        this.displayName = $$6;
        this.server = $$7;
        this.resultCallback = $$10;
        this.anchor = $$11;
        this.rotation = $$2;
        this.signingContext = $$12;
        this.chatMessageChainer = $$13;
    }

    public CommandSourceStack withSource(CommandSource $$0) {
        if (this.source == $$0) {
            return this;
        }
        return new CommandSourceStack($$0, this.worldPosition, this.rotation, this.level, this.permissions, this.textName, this.displayName, this.server, this.entity, this.silent, this.resultCallback, this.anchor, this.signingContext, this.chatMessageChainer);
    }

    public CommandSourceStack withEntity(Entity $$0) {
        if (this.entity == $$0) {
            return this;
        }
        return new CommandSourceStack(this.source, this.worldPosition, this.rotation, this.level, this.permissions, $$0.getPlainTextName(), $$0.getDisplayName(), this.server, $$0, this.silent, this.resultCallback, this.anchor, this.signingContext, this.chatMessageChainer);
    }

    public CommandSourceStack withPosition(Vec3 $$0) {
        if (this.worldPosition.equals($$0)) {
            return this;
        }
        return new CommandSourceStack(this.source, $$0, this.rotation, this.level, this.permissions, this.textName, this.displayName, this.server, this.entity, this.silent, this.resultCallback, this.anchor, this.signingContext, this.chatMessageChainer);
    }

    public CommandSourceStack withRotation(Vec2 $$0) {
        if (this.rotation.equals($$0)) {
            return this;
        }
        return new CommandSourceStack(this.source, this.worldPosition, $$0, this.level, this.permissions, this.textName, this.displayName, this.server, this.entity, this.silent, this.resultCallback, this.anchor, this.signingContext, this.chatMessageChainer);
    }

    @Override // net.minecraft.commands.ExecutionCommandSource
    public CommandSourceStack withCallback(CommandResultCallback $$0) {
        if (Objects.equals(this.resultCallback, $$0)) {
            return this;
        }
        return new CommandSourceStack(this.source, this.worldPosition, this.rotation, this.level, this.permissions, this.textName, this.displayName, this.server, this.entity, this.silent, $$0, this.anchor, this.signingContext, this.chatMessageChainer);
    }

    public CommandSourceStack withCallback(CommandResultCallback $$0, BinaryOperator<CommandResultCallback> $$1) {
        CommandResultCallback $$2 = (CommandResultCallback) $$1.apply(this.resultCallback, $$0);
        return withCallback($$2);
    }

    public CommandSourceStack withSuppressedOutput() {
        if (this.silent || this.source.alwaysAccepts()) {
            return this;
        }
        return new CommandSourceStack(this.source, this.worldPosition, this.rotation, this.level, this.permissions, this.textName, this.displayName, this.server, this.entity, true, this.resultCallback, this.anchor, this.signingContext, this.chatMessageChainer);
    }

    public CommandSourceStack withPermission(PermissionSet $$0) {
        if ($$0 == this.permissions) {
            return this;
        }
        return new CommandSourceStack(this.source, this.worldPosition, this.rotation, this.level, $$0, this.textName, this.displayName, this.server, this.entity, this.silent, this.resultCallback, this.anchor, this.signingContext, this.chatMessageChainer);
    }

    public CommandSourceStack withMaximumPermission(PermissionSet $$0) {
        return withPermission(this.permissions.union($$0));
    }

    public CommandSourceStack withAnchor(EntityAnchorArgument.Anchor $$0) {
        if ($$0 == this.anchor) {
            return this;
        }
        return new CommandSourceStack(this.source, this.worldPosition, this.rotation, this.level, this.permissions, this.textName, this.displayName, this.server, this.entity, this.silent, this.resultCallback, $$0, this.signingContext, this.chatMessageChainer);
    }

    public CommandSourceStack withLevel(ServerLevel $$0) {
        if ($$0 == this.level) {
            return this;
        }
        double $$1 = DimensionType.getTeleportationScale(this.level.dimensionType(), $$0.dimensionType());
        Vec3 $$2 = new Vec3(this.worldPosition.x * $$1, this.worldPosition.y, this.worldPosition.z * $$1);
        return new CommandSourceStack(this.source, $$2, this.rotation, $$0, this.permissions, this.textName, this.displayName, this.server, this.entity, this.silent, this.resultCallback, this.anchor, this.signingContext, this.chatMessageChainer);
    }

    public CommandSourceStack facing(Entity $$0, EntityAnchorArgument.Anchor $$1) {
        return facing($$1.apply($$0));
    }

    public CommandSourceStack facing(Vec3 $$0) {
        Vec3 $$1 = this.anchor.apply(this);
        double $$2 = $$0.x - $$1.x;
        double $$3 = $$0.y - $$1.y;
        double $$4 = $$0.z - $$1.z;
        double $$5 = Math.sqrt(($$2 * $$2) + ($$4 * $$4));
        float $$6 = Mth.wrapDegrees((float) (-(Mth.atan2($$3, $$5) * 57.2957763671875d)));
        float $$7 = Mth.wrapDegrees(((float) (Mth.atan2($$4, $$2) * 57.2957763671875d)) - 90.0f);
        return withRotation(new Vec2($$6, $$7));
    }

    public CommandSourceStack withSigningContext(CommandSigningContext $$0, TaskChainer $$1) {
        if ($$0 == this.signingContext && $$1 == this.chatMessageChainer) {
            return this;
        }
        return new CommandSourceStack(this.source, this.worldPosition, this.rotation, this.level, this.permissions, this.textName, this.displayName, this.server, this.entity, this.silent, this.resultCallback, this.anchor, $$0, $$1);
    }

    public Component getDisplayName() {
        return this.displayName;
    }

    public String getTextName() {
        return this.textName;
    }

    @Override // net.minecraft.server.permissions.PermissionSetSupplier
    public PermissionSet permissions() {
        return this.permissions;
    }

    public Vec3 getPosition() {
        return this.worldPosition;
    }

    public ServerLevel getLevel() {
        return this.level;
    }

    public Entity getEntity() {
        return this.entity;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.mojang.brigadier.exceptions.CommandSyntaxException */
    public Entity getEntityOrException() throws CommandSyntaxException {
        if (this.entity == null) {
            throw ERROR_NOT_ENTITY.create();
        }
        return this.entity;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.mojang.brigadier.exceptions.CommandSyntaxException */
    public ServerPlayer getPlayerOrException() throws CommandSyntaxException {
        Entity entity = this.entity;
        if (entity instanceof ServerPlayer) {
            ServerPlayer $$0 = (ServerPlayer) entity;
            return $$0;
        }
        throw ERROR_NOT_PLAYER.create();
    }

    public ServerPlayer getPlayer() {
        Entity entity = this.entity;
        if (!(entity instanceof ServerPlayer)) {
            return null;
        }
        ServerPlayer $$0 = (ServerPlayer) entity;
        return $$0;
    }

    public boolean isPlayer() {
        return this.entity instanceof ServerPlayer;
    }

    public Vec2 getRotation() {
        return this.rotation;
    }

    public MinecraftServer getServer() {
        return this.server;
    }

    public EntityAnchorArgument.Anchor getAnchor() {
        return this.anchor;
    }

    public CommandSigningContext getSigningContext() {
        return this.signingContext;
    }

    public TaskChainer getChatMessageChainer() {
        return this.chatMessageChainer;
    }

    public boolean shouldFilterMessageTo(ServerPlayer $$0) {
        ServerPlayer $$1 = getPlayer();
        if ($$0 == $$1) {
            return false;
        }
        return ($$1 != null && $$1.isTextFilteringEnabled()) || $$0.isTextFilteringEnabled();
    }

    public void sendChatMessage(OutgoingChatMessage $$0, boolean $$1, ChatType.Bound $$2) {
        if (this.silent) {
            return;
        }
        ServerPlayer $$3 = getPlayer();
        if ($$3 != null) {
            $$3.sendChatMessage($$0, $$1, $$2);
        } else {
            this.source.sendSystemMessage($$2.decorate($$0.content()));
        }
    }

    public void sendSystemMessage(Component $$0) {
        if (this.silent) {
            return;
        }
        ServerPlayer $$1 = getPlayer();
        if ($$1 != null) {
            $$1.sendSystemMessage($$0);
        } else {
            this.source.sendSystemMessage($$0);
        }
    }

    public void sendSuccess(Supplier<Component> $$0, boolean $$1) {
        boolean $$2 = this.source.acceptsSuccess() && !this.silent;
        boolean $$3 = $$1 && this.source.shouldInformAdmins() && !this.silent;
        if (!$$2 && !$$3) {
            return;
        }
        Component $$4 = $$0.get();
        if ($$2) {
            this.source.sendSystemMessage($$4);
        }
        if ($$3) {
            broadcastToAdmins($$4);
        }
    }

    private void broadcastToAdmins(Component $$0) {
        Component $$1 = Component.translatable("chat.type.admin", getDisplayName(), $$0).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC);
        GameRules $$2 = this.level.getGameRules();
        if (((Boolean) $$2.get(GameRules.SEND_COMMAND_FEEDBACK)).booleanValue()) {
            for (ServerPlayer $$3 : this.server.getPlayerList().getPlayers()) {
                if ($$3.commandSource() != this.source && this.server.getPlayerList().isOp($$3.nameAndId())) {
                    $$3.sendSystemMessage($$1);
                }
            }
        }
        if (this.source != this.server && ((Boolean) $$2.get(GameRules.LOG_ADMIN_COMMANDS)).booleanValue()) {
            this.server.sendSystemMessage($$1);
        }
    }

    public void sendFailure(Component $$0) {
        if (this.source.acceptsFailure() && !this.silent) {
            this.source.sendSystemMessage(Component.empty().append($$0).withStyle(ChatFormatting.RED));
        }
    }

    @Override // net.minecraft.commands.ExecutionCommandSource
    public CommandResultCallback callback() {
        return this.resultCallback;
    }

    @Override // net.minecraft.commands.SharedSuggestionProvider
    public Collection<String> getOnlinePlayerNames() {
        return Lists.newArrayList(this.server.getPlayerNames());
    }

    @Override // net.minecraft.commands.SharedSuggestionProvider
    public Collection<String> getAllTeams() {
        return this.server.getScoreboard().getTeamNames();
    }

    @Override // net.minecraft.commands.SharedSuggestionProvider
    public Stream<Identifier> getAvailableSounds() {
        return BuiltInRegistries.SOUND_EVENT.stream().map((v0) -> {
            return v0.location();
        });
    }

    @Override // net.minecraft.commands.SharedSuggestionProvider
    public CompletableFuture<Suggestions> customSuggestion(CommandContext<?> $$0) {
        return Suggestions.empty();
    }

    @Override // net.minecraft.commands.SharedSuggestionProvider
    public CompletableFuture<Suggestions> suggestRegistryElements(ResourceKey<? extends Registry<?>> $$0, SharedSuggestionProvider.ElementSuggestionType $$1, SuggestionsBuilder $$2, CommandContext<?> $$3) {
        if ($$0 == Registries.RECIPE) {
            return SharedSuggestionProvider.suggestResource((Stream<Identifier>) this.server.getRecipeManager().getRecipes().stream().map($$02 -> {
                return $$02.id().identifier();
            }), $$2);
        }
        if ($$0 == Registries.ADVANCEMENT) {
            Collection<AdvancementHolder> $$4 = this.server.getAdvancements().getAllAdvancements();
            return SharedSuggestionProvider.suggestResource((Stream<Identifier>) $$4.stream().map((v0) -> {
                return v0.id();
            }), $$2);
        }
        return (CompletableFuture) getLookup($$0).map($$22 -> {
            suggestRegistryElements($$22, $$1, $$2);
            return $$2.buildFuture();
        }).orElseGet(Suggestions::empty);
    }

    private Optional<? extends HolderLookup<?>> getLookup(ResourceKey<? extends Registry<?>> $$0) {
        Optional<? extends Registry<?>> $$1 = registryAccess().lookup($$0);
        if ($$1.isPresent()) {
            return $$1;
        }
        return this.server.reloadableRegistries().lookup().lookup($$0);
    }

    @Override // net.minecraft.commands.SharedSuggestionProvider
    public Set<ResourceKey<Level>> levels() {
        return this.server.levelKeys();
    }

    @Override // net.minecraft.commands.SharedSuggestionProvider
    public RegistryAccess registryAccess() {
        return this.server.registryAccess();
    }

    @Override // net.minecraft.commands.SharedSuggestionProvider
    public FeatureFlagSet enabledFeatures() {
        return this.level.enabledFeatures();
    }

    @Override // net.minecraft.commands.ExecutionCommandSource
    public CommandDispatcher<CommandSourceStack> dispatcher() {
        return getServer().getFunctions().getDispatcher();
    }

    @Override // net.minecraft.commands.ExecutionCommandSource
    public void handleError(CommandExceptionType $$0, Message $$1, boolean $$2, TraceCallbacks $$3) {
        if ($$3 != null) {
            $$3.onError($$1.getString());
        }
        if (!$$2) {
            sendFailure(ComponentUtils.fromMessage($$1));
        }
    }

    @Override // net.minecraft.commands.ExecutionCommandSource
    public boolean isSilent() {
        return this.silent;
    }
}
