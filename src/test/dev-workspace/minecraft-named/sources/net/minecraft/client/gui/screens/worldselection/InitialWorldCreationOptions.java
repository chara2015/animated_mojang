package net.minecraft.client.gui.screens.worldselection;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.client.gui.screens.worldselection.WorldCreationUiState;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.gamerules.GameRuleMap;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorPreset;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/screens/worldselection/InitialWorldCreationOptions.class */
public final class InitialWorldCreationOptions extends Record {
    private final WorldCreationUiState.SelectedGameMode selectedGameMode;
    private final GameRuleMap gameRuleOverwrites;
    private final ResourceKey<FlatLevelGeneratorPreset> flatLevelPreset;

    public InitialWorldCreationOptions(WorldCreationUiState.SelectedGameMode $$0, GameRuleMap $$1, ResourceKey<FlatLevelGeneratorPreset> $$2) {
        this.selectedGameMode = $$0;
        this.gameRuleOverwrites = $$1;
        this.flatLevelPreset = $$2;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, InitialWorldCreationOptions.class), InitialWorldCreationOptions.class, "selectedGameMode;gameRuleOverwrites;flatLevelPreset", "FIELD:Lnet/minecraft/client/gui/screens/worldselection/InitialWorldCreationOptions;->selectedGameMode:Lnet/minecraft/client/gui/screens/worldselection/WorldCreationUiState$SelectedGameMode;", "FIELD:Lnet/minecraft/client/gui/screens/worldselection/InitialWorldCreationOptions;->gameRuleOverwrites:Lnet/minecraft/world/level/gamerules/GameRuleMap;", "FIELD:Lnet/minecraft/client/gui/screens/worldselection/InitialWorldCreationOptions;->flatLevelPreset:Lnet/minecraft/resources/ResourceKey;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, InitialWorldCreationOptions.class), InitialWorldCreationOptions.class, "selectedGameMode;gameRuleOverwrites;flatLevelPreset", "FIELD:Lnet/minecraft/client/gui/screens/worldselection/InitialWorldCreationOptions;->selectedGameMode:Lnet/minecraft/client/gui/screens/worldselection/WorldCreationUiState$SelectedGameMode;", "FIELD:Lnet/minecraft/client/gui/screens/worldselection/InitialWorldCreationOptions;->gameRuleOverwrites:Lnet/minecraft/world/level/gamerules/GameRuleMap;", "FIELD:Lnet/minecraft/client/gui/screens/worldselection/InitialWorldCreationOptions;->flatLevelPreset:Lnet/minecraft/resources/ResourceKey;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, InitialWorldCreationOptions.class, Object.class), InitialWorldCreationOptions.class, "selectedGameMode;gameRuleOverwrites;flatLevelPreset", "FIELD:Lnet/minecraft/client/gui/screens/worldselection/InitialWorldCreationOptions;->selectedGameMode:Lnet/minecraft/client/gui/screens/worldselection/WorldCreationUiState$SelectedGameMode;", "FIELD:Lnet/minecraft/client/gui/screens/worldselection/InitialWorldCreationOptions;->gameRuleOverwrites:Lnet/minecraft/world/level/gamerules/GameRuleMap;", "FIELD:Lnet/minecraft/client/gui/screens/worldselection/InitialWorldCreationOptions;->flatLevelPreset:Lnet/minecraft/resources/ResourceKey;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public WorldCreationUiState.SelectedGameMode selectedGameMode() {
        return this.selectedGameMode;
    }

    public GameRuleMap gameRuleOverwrites() {
        return this.gameRuleOverwrites;
    }

    public ResourceKey<FlatLevelGeneratorPreset> flatLevelPreset() {
        return this.flatLevelPreset;
    }
}
