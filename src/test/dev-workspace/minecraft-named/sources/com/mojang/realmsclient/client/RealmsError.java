package com.mojang.realmsclient.client;

import com.google.common.base.Strings;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.exception.RealmsHttpException;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Locale;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.LenientJsonParser;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/client/RealmsError.class */
public interface RealmsError {
    public static final Component NO_MESSAGE = Component.translatable("mco.errorMessage.noDetails");
    public static final Logger LOGGER = LogUtils.getLogger();

    int errorCode();

    Component errorMessage();

    String logMessage();

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/client/RealmsError$ErrorWithJsonPayload.class */
    public static final class ErrorWithJsonPayload extends Record implements RealmsError {
        private final int httpCode;
        private final int code;
        private final String reason;
        private final String message;

        public ErrorWithJsonPayload(int $$0, int $$1, String $$2, String $$3) {
            this.httpCode = $$0;
            this.code = $$1;
            this.reason = $$2;
            this.message = $$3;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ErrorWithJsonPayload.class), ErrorWithJsonPayload.class, "httpCode;code;reason;message", "FIELD:Lcom/mojang/realmsclient/client/RealmsError$ErrorWithJsonPayload;->httpCode:I", "FIELD:Lcom/mojang/realmsclient/client/RealmsError$ErrorWithJsonPayload;->code:I", "FIELD:Lcom/mojang/realmsclient/client/RealmsError$ErrorWithJsonPayload;->reason:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/client/RealmsError$ErrorWithJsonPayload;->message:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ErrorWithJsonPayload.class), ErrorWithJsonPayload.class, "httpCode;code;reason;message", "FIELD:Lcom/mojang/realmsclient/client/RealmsError$ErrorWithJsonPayload;->httpCode:I", "FIELD:Lcom/mojang/realmsclient/client/RealmsError$ErrorWithJsonPayload;->code:I", "FIELD:Lcom/mojang/realmsclient/client/RealmsError$ErrorWithJsonPayload;->reason:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/client/RealmsError$ErrorWithJsonPayload;->message:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ErrorWithJsonPayload.class, Object.class), ErrorWithJsonPayload.class, "httpCode;code;reason;message", "FIELD:Lcom/mojang/realmsclient/client/RealmsError$ErrorWithJsonPayload;->httpCode:I", "FIELD:Lcom/mojang/realmsclient/client/RealmsError$ErrorWithJsonPayload;->code:I", "FIELD:Lcom/mojang/realmsclient/client/RealmsError$ErrorWithJsonPayload;->reason:Ljava/lang/String;", "FIELD:Lcom/mojang/realmsclient/client/RealmsError$ErrorWithJsonPayload;->message:Ljava/lang/String;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public int httpCode() {
            return this.httpCode;
        }

        public int code() {
            return this.code;
        }

        public String reason() {
            return this.reason;
        }

        public String message() {
            return this.message;
        }

        @Override // com.mojang.realmsclient.client.RealmsError
        public int errorCode() {
            return this.code;
        }

        @Override // com.mojang.realmsclient.client.RealmsError
        public Component errorMessage() {
            String $$0 = "mco.errorMessage." + this.code;
            if (I18n.exists($$0)) {
                return Component.translatable($$0);
            }
            if (this.reason != null) {
                String $$1 = "mco.errorReason." + this.reason;
                if (I18n.exists($$1)) {
                    return Component.translatable($$1);
                }
            }
            return this.message != null ? Component.literal(this.message) : NO_MESSAGE;
        }

        @Override // com.mojang.realmsclient.client.RealmsError
        public String logMessage() {
            return String.format(Locale.ROOT, "Realms service error (%d/%d/%s) with message '%s'", Integer.valueOf(this.httpCode), Integer.valueOf(this.code), this.reason, this.message);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/client/RealmsError$ErrorWithRawPayload.class */
    public static final class ErrorWithRawPayload extends Record implements RealmsError {
        private final int httpCode;
        private final String payload;

        public ErrorWithRawPayload(int $$0, String $$1) {
            this.httpCode = $$0;
            this.payload = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ErrorWithRawPayload.class), ErrorWithRawPayload.class, "httpCode;payload", "FIELD:Lcom/mojang/realmsclient/client/RealmsError$ErrorWithRawPayload;->httpCode:I", "FIELD:Lcom/mojang/realmsclient/client/RealmsError$ErrorWithRawPayload;->payload:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ErrorWithRawPayload.class), ErrorWithRawPayload.class, "httpCode;payload", "FIELD:Lcom/mojang/realmsclient/client/RealmsError$ErrorWithRawPayload;->httpCode:I", "FIELD:Lcom/mojang/realmsclient/client/RealmsError$ErrorWithRawPayload;->payload:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ErrorWithRawPayload.class, Object.class), ErrorWithRawPayload.class, "httpCode;payload", "FIELD:Lcom/mojang/realmsclient/client/RealmsError$ErrorWithRawPayload;->httpCode:I", "FIELD:Lcom/mojang/realmsclient/client/RealmsError$ErrorWithRawPayload;->payload:Ljava/lang/String;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public int httpCode() {
            return this.httpCode;
        }

        public String payload() {
            return this.payload;
        }

        @Override // com.mojang.realmsclient.client.RealmsError
        public int errorCode() {
            return this.httpCode;
        }

        @Override // com.mojang.realmsclient.client.RealmsError
        public Component errorMessage() {
            return Component.literal(this.payload);
        }

        @Override // com.mojang.realmsclient.client.RealmsError
        public String logMessage() {
            return String.format(Locale.ROOT, "Realms service error (%d) with raw payload '%s'", Integer.valueOf(this.httpCode), this.payload);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/client/RealmsError$AuthenticationError.class */
    public static final class AuthenticationError extends Record implements RealmsError {
        private final String message;
        public static final int ERROR_CODE = 401;

        public AuthenticationError(String $$0) {
            this.message = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, AuthenticationError.class), AuthenticationError.class, "message", "FIELD:Lcom/mojang/realmsclient/client/RealmsError$AuthenticationError;->message:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, AuthenticationError.class), AuthenticationError.class, "message", "FIELD:Lcom/mojang/realmsclient/client/RealmsError$AuthenticationError;->message:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, AuthenticationError.class, Object.class), AuthenticationError.class, "message", "FIELD:Lcom/mojang/realmsclient/client/RealmsError$AuthenticationError;->message:Ljava/lang/String;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public String message() {
            return this.message;
        }

        @Override // com.mojang.realmsclient.client.RealmsError
        public int errorCode() {
            return ERROR_CODE;
        }

        @Override // com.mojang.realmsclient.client.RealmsError
        public Component errorMessage() {
            return Component.literal(this.message);
        }

        @Override // com.mojang.realmsclient.client.RealmsError
        public String logMessage() {
            return String.format(Locale.ROOT, "Realms authentication error with message '%s'", this.message);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/client/RealmsError$CustomError.class */
    public static final class CustomError extends Record implements RealmsError {
        private final int httpCode;
        private final Component payload;
        public static final CustomError SERVICE_BUSY = new CustomError(429, Component.translatable("mco.errorMessage.serviceBusy"));
        public static final Component RETRY_MESSAGE = Component.translatable("mco.errorMessage.retry");
        public static final String BODY_TAG = "<body>";
        public static final String CLOSING_BODY_TAG = "</body>";

        public CustomError(int $$0, Component $$1) {
            this.httpCode = $$0;
            this.payload = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, CustomError.class), CustomError.class, "httpCode;payload", "FIELD:Lcom/mojang/realmsclient/client/RealmsError$CustomError;->httpCode:I", "FIELD:Lcom/mojang/realmsclient/client/RealmsError$CustomError;->payload:Lnet/minecraft/network/chat/Component;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, CustomError.class), CustomError.class, "httpCode;payload", "FIELD:Lcom/mojang/realmsclient/client/RealmsError$CustomError;->httpCode:I", "FIELD:Lcom/mojang/realmsclient/client/RealmsError$CustomError;->payload:Lnet/minecraft/network/chat/Component;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, CustomError.class, Object.class), CustomError.class, "httpCode;payload", "FIELD:Lcom/mojang/realmsclient/client/RealmsError$CustomError;->httpCode:I", "FIELD:Lcom/mojang/realmsclient/client/RealmsError$CustomError;->payload:Lnet/minecraft/network/chat/Component;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public int httpCode() {
            return this.httpCode;
        }

        public Component payload() {
            return this.payload;
        }

        public static CustomError unknownCompatibilityResponse(String $$0) {
            return new CustomError(500, Component.translatable("mco.errorMessage.realmsService.unknownCompatibility", $$0));
        }

        public static CustomError configurationError() {
            return new CustomError(500, Component.translatable("mco.errorMessage.realmsService.configurationError"));
        }

        public static CustomError connectivityError(RealmsHttpException $$0) {
            return new CustomError(500, Component.translatable("mco.errorMessage.realmsService.connectivity", $$0.getMessage()));
        }

        public static CustomError retry(int $$0) {
            return new CustomError($$0, RETRY_MESSAGE);
        }

        public static CustomError noPayload(int $$0) {
            return new CustomError($$0, null);
        }

        public static CustomError htmlPayload(int $$0, String $$1) {
            int $$2 = $$1.indexOf(BODY_TAG);
            int $$3 = $$1.indexOf(CLOSING_BODY_TAG);
            if ($$2 >= 0 && $$3 > $$2) {
                return new CustomError($$0, Component.literal($$1.substring($$2 + BODY_TAG.length(), $$3).trim()));
            }
            LOGGER.error("Got an error with an unreadable html body {}", $$1);
            return new CustomError($$0, null);
        }

        @Override // com.mojang.realmsclient.client.RealmsError
        public int errorCode() {
            return this.httpCode;
        }

        @Override // com.mojang.realmsclient.client.RealmsError
        public Component errorMessage() {
            return this.payload != null ? this.payload : NO_MESSAGE;
        }

        @Override // com.mojang.realmsclient.client.RealmsError
        public String logMessage() {
            return this.payload != null ? String.format(Locale.ROOT, "Realms service error (%d) with message '%s'", Integer.valueOf(this.httpCode), this.payload.getString()) : String.format(Locale.ROOT, "Realms service error (%d) with no payload", Integer.valueOf(this.httpCode));
        }
    }

    static RealmsError parse(int $$0, String $$1) {
        if ($$0 == 429) {
            return CustomError.SERVICE_BUSY;
        }
        if (Strings.isNullOrEmpty($$1)) {
            return CustomError.noPayload($$0);
        }
        try {
            JsonObject $$2 = LenientJsonParser.parse($$1).getAsJsonObject();
            String $$3 = GsonHelper.getAsString($$2, "reason", null);
            String $$4 = GsonHelper.getAsString($$2, "errorMsg", null);
            int $$5 = GsonHelper.getAsInt($$2, "errorCode", -1);
            if ($$4 != null || $$3 != null || $$5 != -1) {
                return new ErrorWithJsonPayload($$0, $$5 != -1 ? $$5 : $$0, $$3, $$4);
            }
        } catch (Exception $$6) {
            LOGGER.error("Could not parse RealmsError", $$6);
        }
        return new ErrorWithRawPayload($$0, $$1);
    }
}
