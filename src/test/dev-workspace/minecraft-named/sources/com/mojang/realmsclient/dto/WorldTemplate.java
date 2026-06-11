package com.mojang.realmsclient.dto;

import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.util.JsonUtils;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.util.profiling.jfr.event.ChunkRegionIoEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/dto/WorldTemplate.class */
public final class WorldTemplate extends Record {
    private final String id;
    private final String name;
    private final String version;
    private final String author;
    private final String link;
    private final String image;
    private final String trailer;
    private final String recommendedPlayers;
    private final WorldTemplateType type;
    private static final Logger LOGGER = LogUtils.getLogger();

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/dto/WorldTemplate$WorldTemplateType.class */
    public enum WorldTemplateType {
        WORLD_TEMPLATE,
        MINIGAME,
        ADVENTUREMAP,
        EXPERIENCE,
        INSPIRATION
    }

    public WorldTemplate(String $$0, String $$1, String $$2, String $$3, String $$4, String $$5, String $$6, String $$7, WorldTemplateType $$8) {
        this.id = $$0;
        this.name = $$1;
        this.version = $$2;
        this.author = $$3;
        this.link = $$4;
        this.image = $$5;
        this.trailer = $$6;
        this.recommendedPlayers = $$7;
        this.type = $$8;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, WorldTemplate.class), WorldTemplate.class, "id;name;version;author;link;image;trailer;recommendedPlayers;type", "FIELD:Lcom/mojang/realmsclient/dto/WorldTemplate;->id:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/WorldTemplate;->name:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/WorldTemplate;->version:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/WorldTemplate;->author:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/WorldTemplate;->link:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/WorldTemplate;->image:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/WorldTemplate;->trailer:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/WorldTemplate;->recommendedPlayers:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/WorldTemplate;->type:Lcom/mojang/realmsclient/dto/WorldTemplate$WorldTemplateType;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, WorldTemplate.class), WorldTemplate.class, "id;name;version;author;link;image;trailer;recommendedPlayers;type", "FIELD:Lcom/mojang/realmsclient/dto/WorldTemplate;->id:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/WorldTemplate;->name:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/WorldTemplate;->version:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/WorldTemplate;->author:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/WorldTemplate;->link:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/WorldTemplate;->image:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/WorldTemplate;->trailer:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/WorldTemplate;->recommendedPlayers:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/WorldTemplate;->type:Lcom/mojang/realmsclient/dto/WorldTemplate$WorldTemplateType;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, WorldTemplate.class, Object.class), WorldTemplate.class, "id;name;version;author;link;image;trailer;recommendedPlayers;type", "FIELD:Lcom/mojang/realmsclient/dto/WorldTemplate;->id:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/WorldTemplate;->name:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/WorldTemplate;->version:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/WorldTemplate;->author:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/WorldTemplate;->link:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/WorldTemplate;->image:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/WorldTemplate;->trailer:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/WorldTemplate;->recommendedPlayers:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/WorldTemplate;->type:Lcom/mojang/realmsclient/dto/WorldTemplate$WorldTemplateType;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public String id() {
        return this.id;
    }

    public String name() {
        return this.name;
    }

    public String version() {
        return this.version;
    }

    public String author() {
        return this.author;
    }

    public String link() {
        return this.link;
    }

    public String image() {
        return this.image;
    }

    public String trailer() {
        return this.trailer;
    }

    public String recommendedPlayers() {
        return this.recommendedPlayers;
    }

    public WorldTemplateType type() {
        return this.type;
    }

    public static WorldTemplate parse(JsonObject $$0) {
        try {
            String $$1 = JsonUtils.getStringOr(ChunkRegionIoEvent.Fields.TYPE, $$0, null);
            return new WorldTemplate(JsonUtils.getStringOr(Entity.TAG_ID, $$0, ""), JsonUtils.getStringOr(JigsawBlockEntity.NAME, $$0, ""), JsonUtils.getStringOr("version", $$0, ""), JsonUtils.getStringOr(StructureBlockEntity.AUTHOR_TAG, $$0, ""), JsonUtils.getStringOr("link", $$0, ""), JsonUtils.getStringOr("image", $$0, null), JsonUtils.getStringOr("trailer", $$0, ""), JsonUtils.getStringOr("recommendedPlayers", $$0, ""), $$1 == null ? WorldTemplateType.WORLD_TEMPLATE : WorldTemplateType.valueOf($$1));
        } catch (Exception $$2) {
            LOGGER.error("Could not parse WorldTemplate", $$2);
            return null;
        }
    }
}
