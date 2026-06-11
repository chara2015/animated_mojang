package net.minecraft.client.main;

import com.mojang.blaze3d.platform.DisplayData;
import java.io.File;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.net.Proxy;
import java.nio.file.Path;
import net.minecraft.client.User;
import net.minecraft.client.resources.IndexedAssetSource;
import net.minecraft.util.StringUtil;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/main/GameConfig.class */
public class GameConfig {
    public final UserData user;
    public final DisplayData display;
    public final FolderData location;
    public final GameData game;
    public final QuickPlayData quickPlay;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/main/GameConfig$QuickPlayVariant.class */
    public interface QuickPlayVariant {
        public static final QuickPlayVariant DISABLED = new QuickPlayDisabled();

        boolean isEnabled();
    }

    public GameConfig(UserData $$0, DisplayData $$1, FolderData $$2, GameData $$3, QuickPlayData $$4) {
        this.user = $$0;
        this.display = $$1;
        this.location = $$2;
        this.game = $$3;
        this.quickPlay = $$4;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/main/GameConfig$GameData.class */
    public static class GameData {
        public final boolean demo;
        public final String launchVersion;
        public final String versionType;
        public final boolean disableMultiplayer;
        public final boolean disableChat;
        public final boolean captureTracyImages;
        public final boolean renderDebugLabels;
        public final boolean offlineDeveloperMode;

        public GameData(boolean $$0, String $$1, String $$2, boolean $$3, boolean $$4, boolean $$5, boolean $$6, boolean $$7) {
            this.demo = $$0;
            this.launchVersion = $$1;
            this.versionType = $$2;
            this.disableMultiplayer = $$3;
            this.disableChat = $$4;
            this.captureTracyImages = $$5;
            this.renderDebugLabels = $$6;
            this.offlineDeveloperMode = $$7;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/main/GameConfig$UserData.class */
    public static class UserData {
        public final User user;
        public final Proxy proxy;

        public UserData(User $$0, Proxy $$1) {
            this.user = $$0;
            this.proxy = $$1;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/main/GameConfig$FolderData.class */
    public static class FolderData {
        public final File gameDirectory;
        public final File resourcePackDirectory;
        public final File assetDirectory;
        public final String assetIndex;

        public FolderData(File $$0, File $$1, File $$2, String $$3) {
            this.gameDirectory = $$0;
            this.resourcePackDirectory = $$1;
            this.assetDirectory = $$2;
            this.assetIndex = $$3;
        }

        public Path getExternalAssetSource() {
            return this.assetIndex == null ? this.assetDirectory.toPath() : IndexedAssetSource.createIndexFs(this.assetDirectory.toPath(), this.assetIndex);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/main/GameConfig$QuickPlaySinglePlayerData.class */
    public static final class QuickPlaySinglePlayerData extends Record implements QuickPlayVariant {
        private final String worldId;

        public QuickPlaySinglePlayerData(String $$0) {
            this.worldId = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, QuickPlaySinglePlayerData.class), QuickPlaySinglePlayerData.class, "worldId", "FIELD:Lnet/minecraft/client/main/GameConfig$QuickPlaySinglePlayerData;->worldId:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, QuickPlaySinglePlayerData.class), QuickPlaySinglePlayerData.class, "worldId", "FIELD:Lnet/minecraft/client/main/GameConfig$QuickPlaySinglePlayerData;->worldId:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, QuickPlaySinglePlayerData.class, Object.class), QuickPlaySinglePlayerData.class, "worldId", "FIELD:Lnet/minecraft/client/main/GameConfig$QuickPlaySinglePlayerData;->worldId:Ljava/lang/String;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public String worldId() {
            return this.worldId;
        }

        @Override // net.minecraft.client.main.GameConfig.QuickPlayVariant
        public boolean isEnabled() {
            return true;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/main/GameConfig$QuickPlayMultiplayerData.class */
    public static final class QuickPlayMultiplayerData extends Record implements QuickPlayVariant {
        private final String serverAddress;

        public QuickPlayMultiplayerData(String $$0) {
            this.serverAddress = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, QuickPlayMultiplayerData.class), QuickPlayMultiplayerData.class, "serverAddress", "FIELD:Lnet/minecraft/client/main/GameConfig$QuickPlayMultiplayerData;->serverAddress:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, QuickPlayMultiplayerData.class), QuickPlayMultiplayerData.class, "serverAddress", "FIELD:Lnet/minecraft/client/main/GameConfig$QuickPlayMultiplayerData;->serverAddress:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, QuickPlayMultiplayerData.class, Object.class), QuickPlayMultiplayerData.class, "serverAddress", "FIELD:Lnet/minecraft/client/main/GameConfig$QuickPlayMultiplayerData;->serverAddress:Ljava/lang/String;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public String serverAddress() {
            return this.serverAddress;
        }

        @Override // net.minecraft.client.main.GameConfig.QuickPlayVariant
        public boolean isEnabled() {
            return !StringUtil.isBlank(this.serverAddress);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/main/GameConfig$QuickPlayRealmsData.class */
    public static final class QuickPlayRealmsData extends Record implements QuickPlayVariant {
        private final String realmId;

        public QuickPlayRealmsData(String $$0) {
            this.realmId = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, QuickPlayRealmsData.class), QuickPlayRealmsData.class, "realmId", "FIELD:Lnet/minecraft/client/main/GameConfig$QuickPlayRealmsData;->realmId:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, QuickPlayRealmsData.class), QuickPlayRealmsData.class, "realmId", "FIELD:Lnet/minecraft/client/main/GameConfig$QuickPlayRealmsData;->realmId:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, QuickPlayRealmsData.class, Object.class), QuickPlayRealmsData.class, "realmId", "FIELD:Lnet/minecraft/client/main/GameConfig$QuickPlayRealmsData;->realmId:Ljava/lang/String;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public String realmId() {
            return this.realmId;
        }

        @Override // net.minecraft.client.main.GameConfig.QuickPlayVariant
        public boolean isEnabled() {
            return !StringUtil.isBlank(this.realmId);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/main/GameConfig$QuickPlayDisabled.class */
    public static final class QuickPlayDisabled extends Record implements QuickPlayVariant {
        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, QuickPlayDisabled.class), QuickPlayDisabled.class, "").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, QuickPlayDisabled.class), QuickPlayDisabled.class, "").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, QuickPlayDisabled.class, Object.class), QuickPlayDisabled.class, "").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        @Override // net.minecraft.client.main.GameConfig.QuickPlayVariant
        public boolean isEnabled() {
            return false;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/main/GameConfig$QuickPlayData.class */
    public static final class QuickPlayData extends Record {
        private final String logPath;
        private final QuickPlayVariant variant;

        public QuickPlayData(String $$0, QuickPlayVariant $$1) {
            this.logPath = $$0;
            this.variant = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, QuickPlayData.class), QuickPlayData.class, "logPath;variant", "FIELD:Lnet/minecraft/client/main/GameConfig$QuickPlayData;->logPath:Ljava/lang/String;", "FIELD:Lnet/minecraft/client/main/GameConfig$QuickPlayData;->variant:Lnet/minecraft/client/main/GameConfig$QuickPlayVariant;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, QuickPlayData.class), QuickPlayData.class, "logPath;variant", "FIELD:Lnet/minecraft/client/main/GameConfig$QuickPlayData;->logPath:Ljava/lang/String;", "FIELD:Lnet/minecraft/client/main/GameConfig$QuickPlayData;->variant:Lnet/minecraft/client/main/GameConfig$QuickPlayVariant;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, QuickPlayData.class, Object.class), QuickPlayData.class, "logPath;variant", "FIELD:Lnet/minecraft/client/main/GameConfig$QuickPlayData;->logPath:Ljava/lang/String;", "FIELD:Lnet/minecraft/client/main/GameConfig$QuickPlayData;->variant:Lnet/minecraft/client/main/GameConfig$QuickPlayVariant;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public String logPath() {
            return this.logPath;
        }

        public QuickPlayVariant variant() {
            return this.variant;
        }

        public boolean isEnabled() {
            return this.variant.isEnabled();
        }
    }
}
