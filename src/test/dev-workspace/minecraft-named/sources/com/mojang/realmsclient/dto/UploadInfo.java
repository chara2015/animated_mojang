package com.mojang.realmsclient.dto;

import com.google.common.annotations.VisibleForTesting;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.util.JsonUtils;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.util.LenientJsonParser;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/dto/UploadInfo.class */
public final class UploadInfo extends Record {
    private final boolean worldClosed;
    private final String token;
    private final URI uploadEndpoint;
    private static final String DEFAULT_SCHEMA = "http://";
    private static final int DEFAULT_PORT = 8080;
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Pattern URI_SCHEMA_PATTERN = Pattern.compile("^[a-zA-Z][-a-zA-Z0-9+.]+:");

    public UploadInfo(boolean $$0, String $$1, URI $$2) {
        this.worldClosed = $$0;
        this.token = $$1;
        this.uploadEndpoint = $$2;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, UploadInfo.class), UploadInfo.class, "worldClosed;token;uploadEndpoint", "FIELD:Lcom/mojang/realmsclient/dto/UploadInfo;->worldClosed:Z", "FIELD:Lcom/mojang/realmsclient/dto/UploadInfo;->token:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/UploadInfo;->uploadEndpoint:Ljava/net/URI;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, UploadInfo.class), UploadInfo.class, "worldClosed;token;uploadEndpoint", "FIELD:Lcom/mojang/realmsclient/dto/UploadInfo;->worldClosed:Z", "FIELD:Lcom/mojang/realmsclient/dto/UploadInfo;->token:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/UploadInfo;->uploadEndpoint:Ljava/net/URI;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, UploadInfo.class, Object.class), UploadInfo.class, "worldClosed;token;uploadEndpoint", "FIELD:Lcom/mojang/realmsclient/dto/UploadInfo;->worldClosed:Z", "FIELD:Lcom/mojang/realmsclient/dto/UploadInfo;->token:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/dto/UploadInfo;->uploadEndpoint:Ljava/net/URI;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public boolean worldClosed() {
        return this.worldClosed;
    }

    public String token() {
        return this.token;
    }

    public URI uploadEndpoint() {
        return this.uploadEndpoint;
    }

    public static UploadInfo parse(String $$0) {
        try {
            JsonObject $$1 = LenientJsonParser.parse($$0).getAsJsonObject();
            String $$2 = JsonUtils.getStringOr("uploadEndpoint", $$1, null);
            if ($$2 != null) {
                int $$3 = JsonUtils.getIntOr("port", $$1, -1);
                URI $$4 = assembleUri($$2, $$3);
                if ($$4 != null) {
                    boolean $$5 = JsonUtils.getBooleanOr("worldClosed", $$1, false);
                    String $$6 = JsonUtils.getStringOr("token", $$1, null);
                    return new UploadInfo($$5, $$6, $$4);
                }
                return null;
            }
            return null;
        } catch (Exception $$7) {
            LOGGER.error("Could not parse UploadInfo", $$7);
            return null;
        }
    }

    @VisibleForTesting
    public static URI assembleUri(String $$0, int $$1) {
        Matcher $$2 = URI_SCHEMA_PATTERN.matcher($$0);
        String $$3 = ensureEndpointSchema($$0, $$2);
        try {
            URI $$4 = new URI($$3);
            int $$5 = selectPortOrDefault($$1, $$4.getPort());
            if ($$5 != $$4.getPort()) {
                return new URI($$4.getScheme(), $$4.getUserInfo(), $$4.getHost(), $$5, $$4.getPath(), $$4.getQuery(), $$4.getFragment());
            }
            return $$4;
        } catch (URISyntaxException $$6) {
            LOGGER.warn("Failed to parse URI {}", $$3, $$6);
            return null;
        }
    }

    private static int selectPortOrDefault(int $$0, int $$1) {
        if ($$0 != -1) {
            return $$0;
        }
        if ($$1 != -1) {
            return $$1;
        }
        return DEFAULT_PORT;
    }

    private static String ensureEndpointSchema(String $$0, Matcher $$1) {
        if ($$1.find()) {
            return $$0;
        }
        return "http://" + $$0;
    }

    public static String createRequest(String $$0) {
        JsonObject $$1 = new JsonObject();
        if ($$0 != null) {
            $$1.addProperty("token", $$0);
        }
        return $$1.toString();
    }
}
