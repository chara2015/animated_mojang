package com.mojang.realmsclient.dto;

import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.util.JsonUtils;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.util.LenientJsonParser;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/dto/WorldDownload.class */
public final class WorldDownload extends Record {
    private final String downloadLink;
    private final String resourcePackUrl;
    private final String resourcePackHash;
    private static final Logger LOGGER = LogUtils.getLogger();

    public WorldDownload(String $$0, String $$1, String $$2) {
        this.downloadLink = $$0;
        this.resourcePackUrl = $$1;
        this.resourcePackHash = $$2;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, WorldDownload.class), WorldDownload.class, "downloadLink;resourcePackUrl;resourcePackHash", "FIELD:Lcom/mojang/realmsclient/dto/WorldDownload;->downloadLink:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/WorldDownload;->resourcePackUrl:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/WorldDownload;->resourcePackHash:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, WorldDownload.class), WorldDownload.class, "downloadLink;resourcePackUrl;resourcePackHash", "FIELD:Lcom/mojang/realmsclient/dto/WorldDownload;->downloadLink:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/WorldDownload;->resourcePackUrl:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/WorldDownload;->resourcePackHash:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, WorldDownload.class, Object.class), WorldDownload.class, "downloadLink;resourcePackUrl;resourcePackHash", "FIELD:Lcom/mojang/realmsclient/dto/WorldDownload;->downloadLink:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/WorldDownload;->resourcePackUrl:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/WorldDownload;->resourcePackHash:Ljava/lang/String;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public String downloadLink() {
        return this.downloadLink;
    }

    public String resourcePackUrl() {
        return this.resourcePackUrl;
    }

    public String resourcePackHash() {
        return this.resourcePackHash;
    }

    public static WorldDownload parse(String $$0) {
        JsonObject $$1 = LenientJsonParser.parse($$0).getAsJsonObject();
        try {
            return new WorldDownload(JsonUtils.getStringOr("downloadLink", $$1, ""), JsonUtils.getStringOr("resourcePackUrl", $$1, ""), JsonUtils.getStringOr("resourcePackHash", $$1, ""));
        } catch (Exception $$2) {
            LOGGER.error("Could not parse WorldDownload", $$2);
            return new WorldDownload("", "", "");
        }
    }
}
