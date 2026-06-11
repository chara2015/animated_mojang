package net.minecraft.server.jsonrpc.security;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.security.SecureRandom;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/jsonrpc/security/SecurityConfig.class */
public final class SecurityConfig extends Record {
    private final String secretKey;
    private static final String SECRET_KEY_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public SecurityConfig(String $$0) {
        this.secretKey = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, SecurityConfig.class), SecurityConfig.class, "secretKey", "FIELD:Lnet/minecraft/server/jsonrpc/security/SecurityConfig;->secretKey:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, SecurityConfig.class), SecurityConfig.class, "secretKey", "FIELD:Lnet/minecraft/server/jsonrpc/security/SecurityConfig;->secretKey:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, SecurityConfig.class, Object.class), SecurityConfig.class, "secretKey", "FIELD:Lnet/minecraft/server/jsonrpc/security/SecurityConfig;->secretKey:Ljava/lang/String;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public String secretKey() {
        return this.secretKey;
    }

    public static boolean isValid(String $$0) {
        if ($$0.isEmpty()) {
            return false;
        }
        return $$0.matches("^[a-zA-Z0-9]{40}$");
    }

    public static String generateSecretKey() {
        SecureRandom $$0 = new SecureRandom();
        StringBuilder $$1 = new StringBuilder(40);
        for (int $$2 = 0; $$2 < 40; $$2++) {
            $$1.append(SECRET_KEY_CHARS.charAt($$0.nextInt(SECRET_KEY_CHARS.length())));
        }
        return $$1.toString();
    }
}
