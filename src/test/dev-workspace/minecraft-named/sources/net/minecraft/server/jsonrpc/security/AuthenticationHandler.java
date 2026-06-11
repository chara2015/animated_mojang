package net.minecraft.server.jsonrpc.security;

import com.google.common.collect.Sets;
import com.mojang.logging.LogUtils;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.AttributeKey;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Set;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/jsonrpc/security/AuthenticationHandler.class */
@ChannelHandler.Sharable
public class AuthenticationHandler extends ChannelDuplexHandler {
    private final Logger LOGGER = LogUtils.getLogger();
    private static final AttributeKey<Boolean> AUTHENTICATED_KEY = AttributeKey.valueOf("authenticated");
    private static final AttributeKey<Boolean> ATTR_WEBSOCKET_ALLOWED = AttributeKey.valueOf("websocket_auth_allowed");
    private static final String SUBPROTOCOL_VALUE = "minecraft-v1";
    private static final String SUBPROTOCOL_HEADER_PREFIX = "minecraft-v1,";
    public static final String BEARER_PREFIX = "Bearer ";
    private final SecurityConfig securityConfig;
    private final Set<String> allowedOrigins;

    public AuthenticationHandler(SecurityConfig $$0, String $$1) {
        this.securityConfig = $$0;
        this.allowedOrigins = Sets.newHashSet($$1.split(","));
    }

    public void channelRead(ChannelHandlerContext $$0, Object $$1) throws Exception {
        String $$2 = getClientIp($$0);
        if ($$1 instanceof HttpRequest) {
            HttpRequest $$3 = (HttpRequest) $$1;
            SecurityCheckResult $$4 = performSecurityChecks($$3);
            if ($$4.isAllowed()) {
                $$0.channel().attr(AUTHENTICATED_KEY).set(true);
                if ($$4.isTokenSentInSecWebsocketProtocol()) {
                    $$0.channel().attr(ATTR_WEBSOCKET_ALLOWED).set(Boolean.TRUE);
                }
            } else {
                this.LOGGER.debug("Authentication rejected for connection with ip {}: {}", $$2, $$4.getReason());
                $$0.channel().attr(AUTHENTICATED_KEY).set(false);
                sendUnauthorizedResponse($$0, $$4.getReason());
                return;
            }
        }
        Boolean $$5 = (Boolean) $$0.channel().attr(AUTHENTICATED_KEY).get();
        if (Boolean.TRUE.equals($$5)) {
            super.channelRead($$0, $$1);
        } else {
            this.LOGGER.debug("Dropping unauthenticated connection with ip {}", $$2);
            $$0.close();
        }
    }

    public void write(ChannelHandlerContext $$0, Object $$1, ChannelPromise $$2) throws Exception {
        if ($$1 instanceof HttpResponse) {
            HttpResponse $$3 = (HttpResponse) $$1;
            if ($$3.status().code() == HttpResponseStatus.SWITCHING_PROTOCOLS.code() && $$0.channel().attr(ATTR_WEBSOCKET_ALLOWED).get() != null && ((Boolean) $$0.channel().attr(ATTR_WEBSOCKET_ALLOWED).get()).equals(Boolean.TRUE)) {
                $$3.headers().set(HttpHeaderNames.SEC_WEBSOCKET_PROTOCOL, SUBPROTOCOL_VALUE);
            }
        }
        super.write($$0, $$1, $$2);
    }

    private SecurityCheckResult performSecurityChecks(HttpRequest $$0) {
        String $$1 = parseTokenInAuthorizationHeader($$0);
        if ($$1 != null) {
            if (isValidApiKey($$1)) {
                return SecurityCheckResult.allowed();
            }
            return SecurityCheckResult.denied("Invalid API key");
        }
        String $$2 = parseTokenInSecWebsocketProtocolHeader($$0);
        if ($$2 != null) {
            if (!isAllowedOriginHeader($$0)) {
                return SecurityCheckResult.denied("Origin Not Allowed");
            }
            if (isValidApiKey($$2)) {
                return SecurityCheckResult.allowed(true);
            }
            return SecurityCheckResult.denied("Invalid API key");
        }
        return SecurityCheckResult.denied("Missing API key");
    }

    private boolean isAllowedOriginHeader(HttpRequest $$0) {
        String $$1 = $$0.headers().get(HttpHeaderNames.ORIGIN);
        if ($$1 == null || $$1.isEmpty()) {
            return false;
        }
        return this.allowedOrigins.contains($$1);
    }

    private String parseTokenInAuthorizationHeader(HttpRequest $$0) {
        String $$1 = $$0.headers().get(HttpHeaderNames.AUTHORIZATION);
        if ($$1 != null && $$1.startsWith(BEARER_PREFIX)) {
            return $$1.substring(BEARER_PREFIX.length()).trim();
        }
        return null;
    }

    private String parseTokenInSecWebsocketProtocolHeader(HttpRequest $$0) {
        String $$1 = $$0.headers().get(HttpHeaderNames.SEC_WEBSOCKET_PROTOCOL);
        if ($$1 != null && $$1.startsWith(SUBPROTOCOL_HEADER_PREFIX)) {
            return $$1.substring(SUBPROTOCOL_HEADER_PREFIX.length()).trim();
        }
        return null;
    }

    public boolean isValidApiKey(String $$0) {
        if ($$0.isEmpty()) {
            return false;
        }
        byte[] $$1 = $$0.getBytes(StandardCharsets.UTF_8);
        byte[] $$2 = this.securityConfig.secretKey().getBytes(StandardCharsets.UTF_8);
        return MessageDigest.isEqual($$1, $$2);
    }

    private String getClientIp(ChannelHandlerContext $$0) {
        InetSocketAddress $$1 = (InetSocketAddress) $$0.channel().remoteAddress();
        return $$1.getAddress().getHostAddress();
    }

    private void sendUnauthorizedResponse(ChannelHandlerContext $$0, String $$1) {
        String $$2 = "{\"error\":\"Unauthorized\",\"message\":\"" + $$1 + "\"}";
        byte[] $$3 = $$2.getBytes(StandardCharsets.UTF_8);
        DefaultFullHttpResponse $$4 = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.UNAUTHORIZED, Unpooled.wrappedBuffer($$3));
        $$4.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json");
        $$4.headers().set(HttpHeaderNames.CONTENT_LENGTH, Integer.valueOf($$3.length));
        $$4.headers().set(HttpHeaderNames.CONNECTION, "close");
        $$0.writeAndFlush($$4).addListener($$12 -> {
            $$0.close();
        });
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/jsonrpc/security/AuthenticationHandler$SecurityCheckResult.class */
    static class SecurityCheckResult {
        private final boolean allowed;
        private final String reason;
        private final boolean tokenSentInSecWebsocketProtocol;

        private SecurityCheckResult(boolean $$0, String $$1, boolean $$2) {
            this.allowed = $$0;
            this.reason = $$1;
            this.tokenSentInSecWebsocketProtocol = $$2;
        }

        public static SecurityCheckResult allowed() {
            return new SecurityCheckResult(true, null, false);
        }

        public static SecurityCheckResult allowed(boolean $$0) {
            return new SecurityCheckResult(true, null, $$0);
        }

        public static SecurityCheckResult denied(String $$0) {
            return new SecurityCheckResult(false, $$0, false);
        }

        public boolean isAllowed() {
            return this.allowed;
        }

        public String getReason() {
            return this.reason;
        }

        public boolean isTokenSentInSecWebsocketProtocol() {
            return this.tokenSentInSecWebsocketProtocol;
        }
    }
}
