package net.minecraft.client.gui.screens.worldselection;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;
import net.minecraft.client.gui.screens.worldselection.WorldCreationUiState;
import net.minecraft.core.LayeredRegistryAccess;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.RegistryLayer;
import net.minecraft.server.ReloadableServerResources;
import net.minecraft.world.level.WorldDataConfiguration;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.gamerules.GameRuleMap;
import net.minecraft.world.level.levelgen.WorldDimensions;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.levelgen.WorldOptions;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/screens/worldselection/WorldCreationContext.class */
public final class WorldCreationContext extends Record {
    private final WorldOptions options;
    private final Registry<LevelStem> datapackDimensions;
    private final WorldDimensions selectedDimensions;
    private final LayeredRegistryAccess<RegistryLayer> worldgenRegistries;
    private final ReloadableServerResources dataPackResources;
    private final WorldDataConfiguration dataConfiguration;
    private final InitialWorldCreationOptions initialWorldCreationOptions;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/screens/worldselection/WorldCreationContext$DimensionsUpdater.class */
    @FunctionalInterface
    public interface DimensionsUpdater extends BiFunction<RegistryAccess.Frozen, WorldDimensions, WorldDimensions> {
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/screens/worldselection/WorldCreationContext$OptionsModifier.class */
    public interface OptionsModifier extends UnaryOperator<WorldOptions> {
    }

    public WorldCreationContext(WorldOptions $$0, Registry<LevelStem> $$1, WorldDimensions $$2, LayeredRegistryAccess<RegistryLayer> $$3, ReloadableServerResources $$4, WorldDataConfiguration $$5, InitialWorldCreationOptions $$6) {
        this.options = $$0;
        this.datapackDimensions = $$1;
        this.selectedDimensions = $$2;
        this.worldgenRegistries = $$3;
        this.dataPackResources = $$4;
        this.dataConfiguration = $$5;
        this.initialWorldCreationOptions = $$6;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, WorldCreationContext.class), WorldCreationContext.class, "options;datapackDimensions;selectedDimensions;worldgenRegistries;dataPackResources;dataConfiguration;initialWorldCreationOptions", "FIELD:Lnet/minecraft/client/gui/screens/worldselection/WorldCreationContext;->options:Lnet/minecraft/world/level/levelgen/WorldOptions;", "FIELD:Lnet/minecraft/client/gui/screens/worldselection/WorldCreationContext;->datapackDimensions:Lnet/minecraft/core/Registry;", "FIELD:Lnet/minecraft/client/gui/screens/worldselection/WorldCreationContext;->selectedDimensions:Lnet/minecraft/world/level/levelgen/WorldDimensions;", "FIELD:Lnet/minecraft/client/gui/screens/worldselection/WorldCreationContext;->worldgenRegistries:Lnet/minecraft/core/LayeredRegistryAccess;", "FIELD:Lnet/minecraft/client/gui/screens/worldselection/WorldCreationContext;->dataPackResources:Lnet/minecraft/server/ReloadableServerResources;", "FIELD:Lnet/minecraft/client/gui/screens/worldselection/WorldCreationContext;->dataConfiguration:Lnet/minecraft/world/level/WorldDataConfiguration;", "FIELD:Lnet/minecraft/client/gui/screens/worldselection/WorldCreationContext;->initialWorldCreationOptions:Lnet/minecraft/client/gui/screens/worldselection/InitialWorldCreationOptions;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, WorldCreationContext.class), WorldCreationContext.class, "options;datapackDimensions;selectedDimensions;worldgenRegistries;dataPackResources;dataConfiguration;initialWorldCreationOptions", "FIELD:Lnet/minecraft/client/gui/screens/worldselection/WorldCreationContext;->options:Lnet/minecraft/world/level/levelgen/WorldOptions;", "FIELD:Lnet/minecraft/client/gui/screens/worldselection/WorldCreationContext;->datapackDimensions:Lnet/minecraft/core/Registry;", "FIELD:Lnet/minecraft/client/gui/screens/worldselection/WorldCreationContext;->selectedDimensions:Lnet/minecraft/world/level/levelgen/WorldDimensions;", "FIELD:Lnet/minecraft/client/gui/screens/worldselection/WorldCreationContext;->worldgenRegistries:Lnet/minecraft/core/LayeredRegistryAccess;", "FIELD:Lnet/minecraft/client/gui/screens/worldselection/WorldCreationContext;->dataPackResources:Lnet/minecraft/server/ReloadableServerResources;", "FIELD:Lnet/minecraft/client/gui/screens/worldselection/WorldCreationContext;->dataConfiguration:Lnet/minecraft/world/level/WorldDataConfiguration;", "FIELD:Lnet/minecraft/client/gui/screens/worldselection/WorldCreationContext;->initialWorldCreationOptions:Lnet/minecraft/client/gui/screens/worldselection/InitialWorldCreationOptions;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, WorldCreationContext.class, Object.class), WorldCreationContext.class, "options;datapackDimensions;selectedDimensions;worldgenRegistries;dataPackResources;dataConfiguration;initialWorldCreationOptions", "FIELD:Lnet/minecraft/client/gui/screens/worldselection/WorldCreationContext;->options:Lnet/minecraft/world/level/levelgen/WorldOptions;", "FIELD:Lnet/minecraft/client/gui/screens/worldselection/WorldCreationContext;->datapackDimensions:Lnet/minecraft/core/Registry;", "FIELD:Lnet/minecraft/client/gui/screens/worldselection/WorldCreationContext;->selectedDimensions:Lnet/minecraft/world/level/levelgen/WorldDimensions;", "FIELD:Lnet/minecraft/client/gui/screens/worldselection/WorldCreationContext;->worldgenRegistries:Lnet/minecraft/core/LayeredRegistryAccess;", "FIELD:Lnet/minecraft/client/gui/screens/worldselection/WorldCreationContext;->dataPackResources:Lnet/minecraft/server/ReloadableServerResources;", "FIELD:Lnet/minecraft/client/gui/screens/worldselection/WorldCreationContext;->dataConfiguration:Lnet/minecraft/world/level/WorldDataConfiguration;", "FIELD:Lnet/minecraft/client/gui/screens/worldselection/WorldCreationContext;->initialWorldCreationOptions:Lnet/minecraft/client/gui/screens/worldselection/InitialWorldCreationOptions;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public WorldOptions options() {
        return this.options;
    }

    public Registry<LevelStem> datapackDimensions() {
        return this.datapackDimensions;
    }

    public WorldDimensions selectedDimensions() {
        return this.selectedDimensions;
    }

    public LayeredRegistryAccess<RegistryLayer> worldgenRegistries() {
        return this.worldgenRegistries;
    }

    public ReloadableServerResources dataPackResources() {
        return this.dataPackResources;
    }

    public WorldDataConfiguration dataConfiguration() {
        return this.dataConfiguration;
    }

    public InitialWorldCreationOptions initialWorldCreationOptions() {
        return this.initialWorldCreationOptions;
    }

    public WorldCreationContext(WorldGenSettings $$0, LayeredRegistryAccess<RegistryLayer> $$1, ReloadableServerResources $$2, WorldDataConfiguration $$3) {
        this($$0.options(), $$0.dimensions(), $$1, $$2, $$3, new InitialWorldCreationOptions(WorldCreationUiState.SelectedGameMode.SURVIVAL, GameRuleMap.of(), null));
    }

    public WorldCreationContext(WorldOptions $$0, WorldDimensions $$1, LayeredRegistryAccess<RegistryLayer> $$2, ReloadableServerResources $$3, WorldDataConfiguration $$4, InitialWorldCreationOptions $$5) {
        this($$0, $$2.getLayer(RegistryLayer.DIMENSIONS).lookupOrThrow((ResourceKey) Registries.LEVEL_STEM), $$1, $$2.replaceFrom(RegistryLayer.DIMENSIONS, new RegistryAccess.Frozen[0]), $$3, $$4, $$5);
    }

    public WorldCreationContext withSettings(WorldOptions $$0, WorldDimensions $$1) {
        return new WorldCreationContext($$0, this.datapackDimensions, $$1, this.worldgenRegistries, this.dataPackResources, this.dataConfiguration, this.initialWorldCreationOptions);
    }

    public WorldCreationContext withOptions(OptionsModifier $$0) {
        return new WorldCreationContext((WorldOptions) $$0.apply(this.options), this.datapackDimensions, this.selectedDimensions, this.worldgenRegistries, this.dataPackResources, this.dataConfiguration, this.initialWorldCreationOptions);
    }

    public WorldCreationContext withDimensions(DimensionsUpdater $$0) {
        return new WorldCreationContext(this.options, this.datapackDimensions, $$0.apply(worldgenLoadContext(), this.selectedDimensions), this.worldgenRegistries, this.dataPackResources, this.dataConfiguration, this.initialWorldCreationOptions);
    }

    public RegistryAccess.Frozen worldgenLoadContext() {
        return this.worldgenRegistries.compositeAccess();
    }

    public void validate() {
        for (LevelStem $$0 : datapackDimensions()) {
            $$0.generator().validate();
        }
    }
}
