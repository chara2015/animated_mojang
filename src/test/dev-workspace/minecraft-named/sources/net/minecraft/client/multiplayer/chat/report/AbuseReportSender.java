package net.minecraft.client.multiplayer.chat.report;

import com.mojang.authlib.exceptions.MinecraftClientException;
import com.mojang.authlib.exceptions.MinecraftClientHttpException;
import com.mojang.authlib.minecraft.UserApiService;
import com.mojang.authlib.minecraft.report.AbuseReport;
import com.mojang.authlib.minecraft.report.AbuseReportLimits;
import com.mojang.authlib.yggdrasil.request.AbuseReportRequest;
import com.mojang.datafixers.util.Unit;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ThrowingComponent;
import net.minecraft.util.Util;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/multiplayer/chat/report/AbuseReportSender.class */
public interface AbuseReportSender {
    CompletableFuture<Unit> send(UUID uuid, ReportType reportType, AbuseReport abuseReport);

    boolean isEnabled();

    static AbuseReportSender create(ReportEnvironment $$0, UserApiService $$1) {
        return new Services($$0, $$1);
    }

    default AbuseReportLimits reportLimits() {
        return AbuseReportLimits.DEFAULTS;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/multiplayer/chat/report/AbuseReportSender$Services.class */
    public static final class Services extends Record implements AbuseReportSender {
        private final ReportEnvironment environment;
        private final UserApiService userApiService;
        private static final Component SERVICE_UNAVAILABLE_TEXT = Component.translatable("gui.abuseReport.send.service_unavailable");
        private static final Component HTTP_ERROR_TEXT = Component.translatable("gui.abuseReport.send.http_error");
        private static final Component JSON_ERROR_TEXT = Component.translatable("gui.abuseReport.send.json_error");

        public Services(ReportEnvironment $$0, UserApiService $$1) {
            this.environment = $$0;
            this.userApiService = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Services.class), Services.class, "environment;userApiService", "FIELD:Lnet/minecraft/client/multiplayer/chat/report/AbuseReportSender$Services;->environment:Lnet/minecraft/client/multiplayer/chat/report/ReportEnvironment;", "FIELD:Lnet/minecraft/client/multiplayer/chat/report/AbuseReportSender$Services;->userApiService:Lcom/mojang/authlib/minecraft/UserApiService;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Services.class), Services.class, "environment;userApiService", "FIELD:Lnet/minecraft/client/multiplayer/chat/report/AbuseReportSender$Services;->environment:Lnet/minecraft/client/multiplayer/chat/report/ReportEnvironment;", "FIELD:Lnet/minecraft/client/multiplayer/chat/report/AbuseReportSender$Services;->userApiService:Lcom/mojang/authlib/minecraft/UserApiService;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Services.class, Object.class), Services.class, "environment;userApiService", "FIELD:Lnet/minecraft/client/multiplayer/chat/report/AbuseReportSender$Services;->environment:Lnet/minecraft/client/multiplayer/chat/report/ReportEnvironment;", "FIELD:Lnet/minecraft/client/multiplayer/chat/report/AbuseReportSender$Services;->userApiService:Lcom/mojang/authlib/minecraft/UserApiService;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public ReportEnvironment environment() {
            return this.environment;
        }

        public UserApiService userApiService() {
            return this.userApiService;
        }

        @Override // net.minecraft.client.multiplayer.chat.report.AbuseReportSender
        public CompletableFuture<Unit> send(UUID $$0, ReportType $$1, AbuseReport $$2) {
            return CompletableFuture.supplyAsync(() -> {
                AbuseReportRequest $$3 = new AbuseReportRequest(1, $$0, $$2, this.environment.clientInfo(), this.environment.thirdPartyServerInfo(), this.environment.realmInfo(), $$1.backendName());
                try {
                    this.userApiService.reportAbuse($$3);
                    return Unit.INSTANCE;
                } catch (MinecraftClientHttpException $$4) {
                    Component $$5 = getHttpErrorDescription($$4);
                    throw new CompletionException(new SendException($$5, $$4));
                } catch (MinecraftClientException $$6) {
                    Component $$7 = getErrorDescription($$6);
                    throw new CompletionException(new SendException($$7, $$6));
                }
            }, Util.ioPool());
        }

        @Override // net.minecraft.client.multiplayer.chat.report.AbuseReportSender
        public boolean isEnabled() {
            return this.userApiService.canSendReports();
        }

        private Component getHttpErrorDescription(MinecraftClientHttpException $$0) {
            return Component.translatable("gui.abuseReport.send.error_message", $$0.getMessage());
        }

        /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
        private Component getErrorDescription(MinecraftClientException $$0) throws MatchException {
            switch (AnonymousClass1.$SwitchMap$com$mojang$authlib$exceptions$MinecraftClientException$ErrorType[$$0.getType().ordinal()]) {
                case 1:
                    return SERVICE_UNAVAILABLE_TEXT;
                case 2:
                    return HTTP_ERROR_TEXT;
                case 3:
                    return JSON_ERROR_TEXT;
                default:
                    throw new MatchException((String) null, (Throwable) null);
            }
        }

        @Override // net.minecraft.client.multiplayer.chat.report.AbuseReportSender
        public AbuseReportLimits reportLimits() {
            return this.userApiService.getAbuseReportLimits();
        }
    }

    /* JADX INFO: renamed from: net.minecraft.client.multiplayer.chat.report.AbuseReportSender$1, reason: invalid class name */
    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/multiplayer/chat/report/AbuseReportSender$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$mojang$authlib$exceptions$MinecraftClientException$ErrorType = new int[MinecraftClientException.ErrorType.values().length];

        static {
            try {
                $SwitchMap$com$mojang$authlib$exceptions$MinecraftClientException$ErrorType[MinecraftClientException.ErrorType.SERVICE_UNAVAILABLE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$mojang$authlib$exceptions$MinecraftClientException$ErrorType[MinecraftClientException.ErrorType.HTTP_ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$mojang$authlib$exceptions$MinecraftClientException$ErrorType[MinecraftClientException.ErrorType.JSON_ERROR.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/multiplayer/chat/report/AbuseReportSender$SendException.class */
    public static class SendException extends ThrowingComponent {
        public SendException(Component $$0, Throwable $$1) {
            super($$0, $$1);
        }
    }
}
