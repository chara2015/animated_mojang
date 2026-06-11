package com.mojang.realmsclient.dto;

import com.google.gson.annotations.SerializedName;
import com.mojang.realmsclient.dto.RealmsServer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.util.StringUtil;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelSettings;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/dto/RealmsWorldOptions.class */
public class RealmsWorldOptions extends ValueObject implements ReflectionBasedSerialization {

    @SerializedName("spawnProtection")
    public int spawnProtection;

    @SerializedName("forceGameMode")
    public boolean forceGameMode;

    @SerializedName("difficulty")
    public int difficulty;

    @SerializedName("gameMode")
    public int gameMode;

    @SerializedName("slotName")
    private String slotName;

    @SerializedName("version")
    public String version;

    @SerializedName("compatibility")
    public RealmsServer.Compatibility compatibility;

    @SerializedName("worldTemplateId")
    public long templateId;

    @SerializedName("worldTemplateImage")
    public String templateImage;

    @Exclude
    public boolean empty;

    private RealmsWorldOptions() {
        this.spawnProtection = 0;
        this.forceGameMode = false;
        this.difficulty = 2;
        this.gameMode = 0;
        this.slotName = "";
        this.version = "";
        this.compatibility = RealmsServer.Compatibility.UNVERIFIABLE;
        this.templateId = -1L;
        this.templateImage = null;
    }

    public RealmsWorldOptions(int $$0, int $$1, int $$2, boolean $$3, String $$4, String $$5, RealmsServer.Compatibility $$6) {
        this.spawnProtection = 0;
        this.forceGameMode = false;
        this.difficulty = 2;
        this.gameMode = 0;
        this.slotName = "";
        this.version = "";
        this.compatibility = RealmsServer.Compatibility.UNVERIFIABLE;
        this.templateId = -1L;
        this.templateImage = null;
        this.spawnProtection = $$0;
        this.difficulty = $$1;
        this.gameMode = $$2;
        this.forceGameMode = $$3;
        this.slotName = $$4;
        this.version = $$5;
        this.compatibility = $$6;
    }

    public static RealmsWorldOptions createDefaults() {
        return new RealmsWorldOptions();
    }

    public static RealmsWorldOptions createDefaultsWith(GameType $$0, Difficulty $$1, boolean $$2, String $$3, String $$4) {
        RealmsWorldOptions $$5 = createDefaults();
        $$5.difficulty = $$1.getId();
        $$5.gameMode = $$0.getId();
        $$5.slotName = $$4;
        $$5.version = $$3;
        return $$5;
    }

    public static RealmsWorldOptions createFromSettings(LevelSettings $$0, String $$1) {
        return createDefaultsWith($$0.gameType(), $$0.difficulty(), $$0.hardcore(), $$1, $$0.levelName());
    }

    public static RealmsWorldOptions createEmptyDefaults() {
        RealmsWorldOptions $$0 = createDefaults();
        $$0.setEmpty(true);
        return $$0;
    }

    public void setEmpty(boolean $$0) {
        this.empty = $$0;
    }

    public static RealmsWorldOptions parse(GuardedSerializer $$0, String $$1) {
        RealmsWorldOptions $$2 = (RealmsWorldOptions) $$0.fromJson($$1, RealmsWorldOptions.class);
        if ($$2 == null) {
            return createDefaults();
        }
        finalize($$2);
        return $$2;
    }

    private static void finalize(RealmsWorldOptions $$0) {
        if ($$0.slotName == null) {
            $$0.slotName = "";
        }
        if ($$0.version == null) {
            $$0.version = "";
        }
        if ($$0.compatibility == null) {
            $$0.compatibility = RealmsServer.Compatibility.UNVERIFIABLE;
        }
    }

    public String getSlotName(int $$0) {
        if (StringUtil.isBlank(this.slotName)) {
            if (this.empty) {
                return I18n.get("mco.configure.world.slot.empty", new Object[0]);
            }
            return getDefaultSlotName($$0);
        }
        return this.slotName;
    }

    public String getDefaultSlotName(int $$0) {
        return I18n.get("mco.configure.world.slot", Integer.valueOf($$0));
    }

    public RealmsWorldOptions copy() {
        return new RealmsWorldOptions(this.spawnProtection, this.difficulty, this.gameMode, this.forceGameMode, this.slotName, this.version, this.compatibility);
    }
}
