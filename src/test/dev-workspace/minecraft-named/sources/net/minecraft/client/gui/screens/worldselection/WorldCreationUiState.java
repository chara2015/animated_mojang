package net.minecraft.client.gui.screens.worldselection;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.function.Consumer;
import net.minecraft.client.gui.screens.worldselection.WorldCreationContext;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.WorldPresetTags;
import net.minecraft.util.FileUtil;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.WorldDataConfiguration;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.levelgen.WorldOptions;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorPreset;
import net.minecraft.world.level.levelgen.presets.WorldPreset;
import net.minecraft.world.level.levelgen.presets.WorldPresets;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/screens/worldselection/WorldCreationUiState.class */
public class WorldCreationUiState {
    private static final Component DEFAULT_WORLD_NAME = Component.translatable("selectWorld.newWorld");
    private SelectedGameMode gameMode;
    private Boolean allowCommands;
    private String seed;
    private boolean generateStructures;
    private boolean bonusChest;
    private final Path savesFolder;
    private String targetFolder;
    private WorldCreationContext settings;
    private WorldTypeEntry worldType;
    private GameRules gameRules;
    private final List<Consumer<WorldCreationUiState>> listeners = new ArrayList();
    private String name = DEFAULT_WORLD_NAME.getString();
    private Difficulty difficulty = Difficulty.NORMAL;
    private final List<WorldTypeEntry> normalPresetList = new ArrayList();
    private final List<WorldTypeEntry> altPresetList = new ArrayList();

    public WorldCreationUiState(Path $$0, WorldCreationContext $$1, Optional<ResourceKey<WorldPreset>> $$2, OptionalLong $$3) {
        this.gameMode = SelectedGameMode.SURVIVAL;
        this.savesFolder = $$0;
        this.settings = $$1;
        this.worldType = new WorldTypeEntry(findPreset($$1, $$2).orElse(null));
        updatePresetLists();
        this.seed = $$3.isPresent() ? Long.toString($$3.getAsLong()) : "";
        this.generateStructures = $$1.options().generateStructures();
        this.bonusChest = $$1.options().generateBonusChest();
        this.targetFolder = findResultFolder(this.name);
        this.gameMode = $$1.initialWorldCreationOptions().selectedGameMode();
        this.gameRules = new GameRules($$1.dataConfiguration().enabledFeatures());
        this.gameRules.setAll($$1.initialWorldCreationOptions().gameRuleOverwrites(), (MinecraftServer) null);
        Optional.ofNullable($$1.initialWorldCreationOptions().flatLevelPreset()).flatMap($$12 -> {
            return $$1.worldgenLoadContext().lookup(Registries.FLAT_LEVEL_GENERATOR_PRESET).flatMap($$12 -> {
                return $$12.get($$12);
            });
        }).map($$02 -> {
            return ((FlatLevelGeneratorPreset) $$02.value()).settings();
        }).ifPresent($$03 -> {
            updateDimensions(PresetEditor.flatWorldConfigurator($$03));
        });
    }

    public void addListener(Consumer<WorldCreationUiState> $$0) {
        this.listeners.add($$0);
    }

    public void onChanged() {
        boolean $$0 = isBonusChest();
        if ($$0 != this.settings.options().generateBonusChest()) {
            this.settings = this.settings.withOptions($$1 -> {
                return $$1.withBonusChest($$0);
            });
        }
        boolean $$12 = isGenerateStructures();
        if ($$12 != this.settings.options().generateStructures()) {
            this.settings = this.settings.withOptions($$13 -> {
                return $$13.withStructures($$12);
            });
        }
        for (Consumer<WorldCreationUiState> $$2 : this.listeners) {
            $$2.accept(this);
        }
    }

    public void setName(String $$0) {
        this.name = $$0;
        this.targetFolder = findResultFolder($$0);
        onChanged();
    }

    private String findResultFolder(String $$0) {
        String $$1 = $$0.trim();
        try {
            return FileUtil.findAvailableName(this.savesFolder, !$$1.isEmpty() ? $$1 : DEFAULT_WORLD_NAME.getString(), "");
        } catch (Exception e) {
            try {
                return FileUtil.findAvailableName(this.savesFolder, "World", "");
            } catch (IOException $$2) {
                throw new RuntimeException("Could not create save folder", $$2);
            }
        }
    }

    public String getName() {
        return this.name;
    }

    public String getTargetFolder() {
        return this.targetFolder;
    }

    public void setGameMode(SelectedGameMode $$0) {
        this.gameMode = $$0;
        onChanged();
    }

    public SelectedGameMode getGameMode() {
        if (isDebug()) {
            return SelectedGameMode.DEBUG;
        }
        return this.gameMode;
    }

    public void setDifficulty(Difficulty $$0) {
        this.difficulty = $$0;
        onChanged();
    }

    public Difficulty getDifficulty() {
        if (isHardcore()) {
            return Difficulty.HARD;
        }
        return this.difficulty;
    }

    public boolean isHardcore() {
        return getGameMode() == SelectedGameMode.HARDCORE;
    }

    public void setAllowCommands(boolean $$0) {
        this.allowCommands = Boolean.valueOf($$0);
        onChanged();
    }

    public boolean isAllowCommands() {
        if (isDebug()) {
            return true;
        }
        if (isHardcore()) {
            return false;
        }
        if (this.allowCommands == null) {
            return getGameMode() == SelectedGameMode.CREATIVE;
        }
        return this.allowCommands.booleanValue();
    }

    public void setSeed(String $$0) {
        this.seed = $$0;
        this.settings = this.settings.withOptions($$02 -> {
            return $$02.withSeed(WorldOptions.parseSeed(getSeed()));
        });
        onChanged();
    }

    public String getSeed() {
        return this.seed;
    }

    public void setGenerateStructures(boolean $$0) {
        this.generateStructures = $$0;
        onChanged();
    }

    public boolean isGenerateStructures() {
        if (isDebug()) {
            return false;
        }
        return this.generateStructures;
    }

    public void setBonusChest(boolean $$0) {
        this.bonusChest = $$0;
        onChanged();
    }

    public boolean isBonusChest() {
        if (isDebug() || isHardcore()) {
            return false;
        }
        return this.bonusChest;
    }

    public void setSettings(WorldCreationContext $$0) {
        this.settings = $$0;
        updatePresetLists();
        onChanged();
    }

    public WorldCreationContext getSettings() {
        return this.settings;
    }

    public void updateDimensions(WorldCreationContext.DimensionsUpdater $$0) {
        this.settings = this.settings.withDimensions($$0);
        onChanged();
    }

    protected boolean tryUpdateDataConfiguration(WorldDataConfiguration $$0) {
        WorldDataConfiguration $$1 = this.settings.dataConfiguration();
        if ($$1.dataPacks().getEnabled().equals($$0.dataPacks().getEnabled()) && $$1.enabledFeatures().equals($$0.enabledFeatures())) {
            this.settings = new WorldCreationContext(this.settings.options(), this.settings.datapackDimensions(), this.settings.selectedDimensions(), this.settings.worldgenRegistries(), this.settings.dataPackResources(), $$0, this.settings.initialWorldCreationOptions());
            return true;
        }
        return false;
    }

    public boolean isDebug() {
        return this.settings.selectedDimensions().isDebug();
    }

    public void setWorldType(WorldTypeEntry $$0) {
        this.worldType = $$0;
        Holder<WorldPreset> $$1 = $$0.preset();
        if ($$1 != null) {
            updateDimensions(($$12, $$2) -> {
                return ((WorldPreset) $$1.value()).createWorldDimensions();
            });
        }
    }

    public WorldTypeEntry getWorldType() {
        return this.worldType;
    }

    public PresetEditor getPresetEditor() {
        Holder<WorldPreset> $$0 = getWorldType().preset();
        if ($$0 != null) {
            return PresetEditor.EDITORS.get($$0.unwrapKey());
        }
        return null;
    }

    public List<WorldTypeEntry> getNormalPresetList() {
        return this.normalPresetList;
    }

    public List<WorldTypeEntry> getAltPresetList() {
        return this.altPresetList;
    }

    private void updatePresetLists() {
        Registry<WorldPreset> $$0 = getSettings().worldgenLoadContext().lookupOrThrow((ResourceKey) Registries.WORLD_PRESET);
        this.normalPresetList.clear();
        this.normalPresetList.addAll(getNonEmptyList($$0, WorldPresetTags.NORMAL).orElseGet(() -> {
            return $$0.listElements().map((v1) -> {
                return new WorldTypeEntry(v1);
            }).toList();
        }));
        this.altPresetList.clear();
        this.altPresetList.addAll(getNonEmptyList($$0, WorldPresetTags.EXTENDED).orElse(this.normalPresetList));
        Holder<WorldPreset> $$1 = this.worldType.preset();
        if ($$1 != null) {
            WorldTypeEntry $$2 = (WorldTypeEntry) findPreset(getSettings(), $$1.unwrapKey()).map(WorldTypeEntry::new).orElse((WorldTypeEntry) this.normalPresetList.getFirst());
            boolean $$3 = PresetEditor.EDITORS.get($$1.unwrapKey()) != null;
            if ($$3) {
                this.worldType = $$2;
            } else {
                setWorldType($$2);
            }
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/screens/worldselection/WorldCreationUiState$WorldTypeEntry.class */
    public static final class WorldTypeEntry extends Record {
        private final Holder<WorldPreset> preset;
        private static final Component CUSTOM_WORLD_DESCRIPTION = Component.translatable("generator.custom");

        public WorldTypeEntry(Holder<WorldPreset> $$0) {
            this.preset = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, WorldTypeEntry.class), WorldTypeEntry.class, "preset", "FIELD:Lnet/minecraft/client/gui/screens/worldselection/WorldCreationUiState$WorldTypeEntry;->preset:Lnet/minecraft/core/Holder;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, WorldTypeEntry.class), WorldTypeEntry.class, "preset", "FIELD:Lnet/minecraft/client/gui/screens/worldselection/WorldCreationUiState$WorldTypeEntry;->preset:Lnet/minecraft/core/Holder;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, WorldTypeEntry.class, Object.class), WorldTypeEntry.class, "preset", "FIELD:Lnet/minecraft/client/gui/screens/worldselection/WorldCreationUiState$WorldTypeEntry;->preset:Lnet/minecraft/core/Holder;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Holder<WorldPreset> preset() {
            return this.preset;
        }

        public Component describePreset() {
            return (Component) Optional.ofNullable(this.preset).flatMap((v0) -> {
                return v0.unwrapKey();
            }).map($$0 -> {
                return Component.translatable($$0.identifier().toLanguageKey("generator"));
            }).orElse(CUSTOM_WORLD_DESCRIPTION);
        }

        public boolean isAmplified() {
            return Optional.ofNullable(this.preset).flatMap((v0) -> {
                return v0.unwrapKey();
            }).filter($$0 -> {
                return $$0.equals(WorldPresets.AMPLIFIED);
            }).isPresent();
        }
    }

    private static Optional<Holder<WorldPreset>> findPreset(WorldCreationContext $$0, Optional<ResourceKey<WorldPreset>> $$1) {
        return $$1.flatMap($$12 -> {
            return $$0.worldgenLoadContext().lookupOrThrow((ResourceKey) Registries.WORLD_PRESET).get($$12);
        });
    }

    private static Optional<List<WorldTypeEntry>> getNonEmptyList(Registry<WorldPreset> $$0, TagKey<WorldPreset> $$1) {
        return $$0.get($$1).map($$02 -> {
            return $$02.stream().map(WorldTypeEntry::new).toList();
        }).filter($$03 -> {
            return !$$03.isEmpty();
        });
    }

    public void setGameRules(GameRules $$0) {
        this.gameRules = $$0;
        onChanged();
    }

    public GameRules getGameRules() {
        return this.gameRules;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/screens/worldselection/WorldCreationUiState$SelectedGameMode.class */
    public enum SelectedGameMode {
        SURVIVAL("survival", GameType.SURVIVAL),
        HARDCORE("hardcore", GameType.SURVIVAL),
        CREATIVE("creative", GameType.CREATIVE),
        DEBUG("spectator", GameType.SPECTATOR);

        public final GameType gameType;
        public final Component displayName;
        private final Component info;

        SelectedGameMode(String $$0, GameType $$1) {
            this.gameType = $$1;
            this.displayName = Component.translatable("selectWorld.gameMode." + $$0);
            this.info = Component.translatable("selectWorld.gameMode." + $$0 + ".info");
        }

        public Component getInfo() {
            return this.info;
        }
    }
}
