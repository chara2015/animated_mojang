package net.minecraft.server;

import com.mojang.logging.LogUtils;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.Commands;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.LayeredRegistryAccess;
import net.minecraft.core.Registry;
import net.minecraft.server.ReloadableServerRegistries;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleReloadInstance;
import net.minecraft.server.permissions.PermissionSet;
import net.minecraft.util.Unit;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.crafting.RecipeManager;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/ReloadableServerResources.class */
public class ReloadableServerResources {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final CompletableFuture<Unit> DATA_RELOAD_INITIAL_TASK = CompletableFuture.completedFuture(Unit.INSTANCE);
    private final ReloadableServerRegistries.Holder fullRegistryHolder;
    private final Commands commands;
    private final RecipeManager recipes;
    private final ServerAdvancementManager advancements;
    private final ServerFunctionLibrary functionLibrary;
    private final List<Registry.PendingTags<?>> postponedTags;

    private ReloadableServerResources(LayeredRegistryAccess<RegistryLayer> $$0, HolderLookup.Provider $$1, FeatureFlagSet $$2, Commands.CommandSelection $$3, List<Registry.PendingTags<?>> $$4, PermissionSet $$5) {
        this.fullRegistryHolder = new ReloadableServerRegistries.Holder($$0.compositeAccess());
        this.postponedTags = $$4;
        this.recipes = new RecipeManager($$1);
        this.commands = new Commands($$3, CommandBuildContext.simple($$1, $$2));
        this.advancements = new ServerAdvancementManager($$1);
        this.functionLibrary = new ServerFunctionLibrary($$5, this.commands.getDispatcher());
    }

    public ServerFunctionLibrary getFunctionLibrary() {
        return this.functionLibrary;
    }

    public ReloadableServerRegistries.Holder fullRegistries() {
        return this.fullRegistryHolder;
    }

    public RecipeManager getRecipeManager() {
        return this.recipes;
    }

    public Commands getCommands() {
        return this.commands;
    }

    public ServerAdvancementManager getAdvancements() {
        return this.advancements;
    }

    public List<PreparableReloadListener> listeners() {
        return List.of(this.recipes, this.functionLibrary, this.advancements);
    }

    public static CompletableFuture<ReloadableServerResources> loadResources(ResourceManager $$0, LayeredRegistryAccess<RegistryLayer> $$1, List<Registry.PendingTags<?>> $$2, FeatureFlagSet $$3, Commands.CommandSelection $$4, PermissionSet $$5, Executor $$6, Executor $$7) {
        return ReloadableServerRegistries.reload($$1, $$2, $$0, $$6).thenCompose($$72 -> {
            ReloadableServerResources $$8 = new ReloadableServerResources($$72.layers(), $$72.lookupWithUpdatedTags(), $$3, $$4, $$2, $$5);
            return SimpleReloadInstance.create($$0, $$8.listeners(), $$6, $$7, DATA_RELOAD_INITIAL_TASK, LOGGER.isDebugEnabled()).done().thenApply($$12 -> {
                return $$8;
            });
        });
    }

    public void updateStaticRegistryTags() {
        this.postponedTags.forEach((v0) -> {
            v0.apply();
        });
    }
}
