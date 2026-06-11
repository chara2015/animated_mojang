package net.minecraft.world.level.storage;

import java.nio.file.Path;
import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.WorldVersion;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.CommonColors;
import net.minecraft.util.StringUtil;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelSettings;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/LevelSummary.class */
public class LevelSummary implements Comparable<LevelSummary> {
    public static final Component PLAY_WORLD = Component.translatable("selectWorld.select");
    private final LevelSettings settings;
    private final LevelVersion levelVersion;
    private final String levelId;
    private final boolean requiresManualConversion;
    private final boolean locked;
    private final boolean experimental;
    private final Path icon;
    private Component info;

    public LevelSummary(LevelSettings $$0, LevelVersion $$1, String $$2, boolean $$3, boolean $$4, boolean $$5, Path $$6) {
        this.settings = $$0;
        this.levelVersion = $$1;
        this.levelId = $$2;
        this.locked = $$4;
        this.experimental = $$5;
        this.icon = $$6;
        this.requiresManualConversion = $$3;
    }

    public String getLevelId() {
        return this.levelId;
    }

    public String getLevelName() {
        return StringUtils.isEmpty(this.settings.levelName()) ? this.levelId : this.settings.levelName();
    }

    public Path getIcon() {
        return this.icon;
    }

    public boolean requiresManualConversion() {
        return this.requiresManualConversion;
    }

    public boolean isExperimental() {
        return this.experimental;
    }

    public long getLastPlayed() {
        return this.levelVersion.lastPlayed();
    }

    @Override // java.lang.Comparable
    public int compareTo(LevelSummary $$0) {
        if (getLastPlayed() < $$0.getLastPlayed()) {
            return 1;
        }
        if (getLastPlayed() > $$0.getLastPlayed()) {
            return -1;
        }
        return this.levelId.compareTo($$0.levelId);
    }

    public LevelSettings getSettings() {
        return this.settings;
    }

    public GameType getGameMode() {
        return this.settings.gameType();
    }

    public boolean isHardcore() {
        return this.settings.hardcore();
    }

    public boolean hasCommands() {
        return this.settings.allowCommands();
    }

    public MutableComponent getWorldVersionName() {
        if (StringUtil.isNullOrEmpty(this.levelVersion.minecraftVersionName())) {
            return Component.translatable("selectWorld.versionUnknown");
        }
        return Component.literal(this.levelVersion.minecraftVersionName());
    }

    public LevelVersion levelVersion() {
        return this.levelVersion;
    }

    public boolean shouldBackup() {
        return backupStatus().shouldBackup();
    }

    public boolean isDowngrade() {
        return backupStatus() == BackupStatus.DOWNGRADE;
    }

    public BackupStatus backupStatus() {
        WorldVersion $$0 = SharedConstants.getCurrentVersion();
        int $$1 = $$0.dataVersion().version();
        int $$2 = this.levelVersion.minecraftVersion().version();
        if (!$$0.stable() && $$2 < $$1) {
            return BackupStatus.UPGRADE_TO_SNAPSHOT;
        }
        if ($$2 > $$1) {
            return BackupStatus.DOWNGRADE;
        }
        return BackupStatus.NONE;
    }

    public boolean isLocked() {
        return this.locked;
    }

    public boolean isDisabled() {
        return isLocked() || requiresManualConversion() || !isCompatible();
    }

    public boolean isCompatible() {
        return SharedConstants.getCurrentVersion().dataVersion().isCompatible(this.levelVersion.minecraftVersion());
    }

    public Component getInfo() {
        if (this.info == null) {
            this.info = createInfo();
        }
        return this.info;
    }

    private Component createInfo() {
        MutableComponent mutableComponentTranslatable;
        if (isLocked()) {
            return Component.translatable("selectWorld.locked").withStyle(ChatFormatting.RED);
        }
        if (requiresManualConversion()) {
            return Component.translatable("selectWorld.conversion").withStyle(ChatFormatting.RED);
        }
        if (!isCompatible()) {
            return Component.translatable("selectWorld.incompatible.info", getWorldVersionName()).withStyle(ChatFormatting.RED);
        }
        if (isHardcore()) {
            mutableComponentTranslatable = Component.empty().append(Component.translatable("gameMode.hardcore").withColor(CommonColors.RED));
        } else {
            mutableComponentTranslatable = Component.translatable("gameMode." + getGameMode().getName());
        }
        MutableComponent $$0 = mutableComponentTranslatable;
        if (hasCommands()) {
            $$0.append(ComponentUtils.DEFAULT_SEPARATOR_TEXT).append(Component.translatable("selectWorld.commands"));
        }
        if (isExperimental()) {
            $$0.append(ComponentUtils.DEFAULT_SEPARATOR_TEXT).append(Component.translatable("selectWorld.experimental").withStyle(ChatFormatting.YELLOW));
        }
        MutableComponent $$1 = getWorldVersionName();
        MutableComponent $$2 = Component.literal(ComponentUtils.DEFAULT_SEPARATOR_TEXT).append(Component.translatable("selectWorld.version")).append(CommonComponents.SPACE);
        if (shouldBackup()) {
            $$2.append($$1.withStyle(isDowngrade() ? ChatFormatting.RED : ChatFormatting.ITALIC));
        } else {
            $$2.append($$1);
        }
        $$0.append($$2);
        return $$0;
    }

    public Component primaryActionMessage() {
        return PLAY_WORLD;
    }

    public boolean primaryActionActive() {
        return !isDisabled();
    }

    public boolean canUpload() {
        return (requiresManualConversion() || isLocked()) ? false : true;
    }

    public boolean canEdit() {
        return !isDisabled();
    }

    public boolean canRecreate() {
        return !isDisabled();
    }

    public boolean canDelete() {
        return true;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/LevelSummary$BackupStatus.class */
    public enum BackupStatus {
        NONE(false, false, ""),
        DOWNGRADE(true, true, "downgrade"),
        UPGRADE_TO_SNAPSHOT(true, false, "snapshot");

        private final boolean shouldBackup;
        private final boolean severe;
        private final String translationKey;

        BackupStatus(boolean $$0, boolean $$1, String $$2) {
            this.shouldBackup = $$0;
            this.severe = $$1;
            this.translationKey = $$2;
        }

        public boolean shouldBackup() {
            return this.shouldBackup;
        }

        public boolean isSevere() {
            return this.severe;
        }

        public String getTranslationKey() {
            return this.translationKey;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/LevelSummary$SymlinkLevelSummary.class */
    public static class SymlinkLevelSummary extends LevelSummary {
        private static final Component MORE_INFO_BUTTON = Component.translatable("symlink_warning.more_info");
        private static final Component INFO = Component.translatable("symlink_warning.title").withColor(CommonColors.RED);

        @Override // net.minecraft.world.level.storage.LevelSummary, java.lang.Comparable
        public /* synthetic */ int compareTo(LevelSummary levelSummary) {
            return super.compareTo(levelSummary);
        }

        public SymlinkLevelSummary(String $$0, Path $$1) {
            super(null, null, $$0, false, false, false, $$1);
        }

        @Override // net.minecraft.world.level.storage.LevelSummary
        public String getLevelName() {
            return getLevelId();
        }

        @Override // net.minecraft.world.level.storage.LevelSummary
        public Component getInfo() {
            return INFO;
        }

        @Override // net.minecraft.world.level.storage.LevelSummary
        public long getLastPlayed() {
            return -1L;
        }

        @Override // net.minecraft.world.level.storage.LevelSummary
        public boolean isDisabled() {
            return false;
        }

        @Override // net.minecraft.world.level.storage.LevelSummary
        public Component primaryActionMessage() {
            return MORE_INFO_BUTTON;
        }

        @Override // net.minecraft.world.level.storage.LevelSummary
        public boolean primaryActionActive() {
            return true;
        }

        @Override // net.minecraft.world.level.storage.LevelSummary
        public boolean canUpload() {
            return false;
        }

        @Override // net.minecraft.world.level.storage.LevelSummary
        public boolean canEdit() {
            return false;
        }

        @Override // net.minecraft.world.level.storage.LevelSummary
        public boolean canRecreate() {
            return false;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/LevelSummary$CorruptedLevelSummary.class */
    public static class CorruptedLevelSummary extends LevelSummary {
        private static final Component INFO = Component.translatable("recover_world.warning").withStyle($$0 -> {
            return $$0.withColor(CommonColors.RED);
        });
        private static final Component RECOVER = Component.translatable("recover_world.button");
        private final long lastPlayed;

        @Override // net.minecraft.world.level.storage.LevelSummary, java.lang.Comparable
        public /* synthetic */ int compareTo(LevelSummary levelSummary) {
            return super.compareTo(levelSummary);
        }

        public CorruptedLevelSummary(String $$0, Path $$1, long $$2) {
            super(null, null, $$0, false, false, false, $$1);
            this.lastPlayed = $$2;
        }

        @Override // net.minecraft.world.level.storage.LevelSummary
        public String getLevelName() {
            return getLevelId();
        }

        @Override // net.minecraft.world.level.storage.LevelSummary
        public Component getInfo() {
            return INFO;
        }

        @Override // net.minecraft.world.level.storage.LevelSummary
        public long getLastPlayed() {
            return this.lastPlayed;
        }

        @Override // net.minecraft.world.level.storage.LevelSummary
        public boolean isDisabled() {
            return false;
        }

        @Override // net.minecraft.world.level.storage.LevelSummary
        public Component primaryActionMessage() {
            return RECOVER;
        }

        @Override // net.minecraft.world.level.storage.LevelSummary
        public boolean primaryActionActive() {
            return true;
        }

        @Override // net.minecraft.world.level.storage.LevelSummary
        public boolean canUpload() {
            return false;
        }

        @Override // net.minecraft.world.level.storage.LevelSummary
        public boolean canEdit() {
            return false;
        }

        @Override // net.minecraft.world.level.storage.LevelSummary
        public boolean canRecreate() {
            return false;
        }
    }
}
