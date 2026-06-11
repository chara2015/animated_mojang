package net.minecraft.server.jsonrpc.security;

import com.mojang.logging.LogUtils;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/jsonrpc/security/JsonRpcSslContextProvider.class */
public class JsonRpcSslContextProvider {
    private static final String PASSWORD_ENV_VARIABLE_KEY = "MINECRAFT_MANAGEMENT_TLS_KEYSTORE_PASSWORD";
    private static final String PASSWORD_SYSTEM_PROPERTY_KEY = "management.tls.keystore.password";
    private static final Logger log = LogUtils.getLogger();

    public static SslContext createFrom(String $$0, String $$1) throws Exception {
        if ($$0.isEmpty()) {
            throw new IllegalArgumentException("TLS is enabled but keystore is not configured");
        }
        File $$2 = new File($$0);
        if (!$$2.exists() || !$$2.isFile()) {
            throw new IllegalArgumentException("Supplied keystore is not a file or does not exist: '" + $$0 + "'");
        }
        String $$3 = getKeystorePassword($$1);
        return loadKeystoreFromPath($$2, $$3);
    }

    private static String getKeystorePassword(String $$0) {
        String $$1 = System.getenv().get(PASSWORD_ENV_VARIABLE_KEY);
        if ($$1 != null) {
            return $$1;
        }
        String $$2 = System.getProperty(PASSWORD_SYSTEM_PROPERTY_KEY, null);
        if ($$2 != null) {
            return $$2;
        }
        return $$0;
    }

    private static SslContext loadKeystoreFromPath(File $$0, String $$1) throws Exception {
        KeyStore $$2 = KeyStore.getInstance("PKCS12");
        InputStream $$3 = new FileInputStream($$0);
        try {
            $$2.load($$3, $$1.toCharArray());
            $$3.close();
            KeyManagerFactory $$4 = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            $$4.init($$2, $$1.toCharArray());
            TrustManagerFactory $$5 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            $$5.init($$2);
            return SslContextBuilder.forServer($$4).trustManager($$5).build();
        } catch (Throwable th) {
            try {
                $$3.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static void printInstructions() {
        log.info("To use TLS for the management server, please follow these steps:");
        log.info("1. Set the server property 'management-server-tls-enabled' to 'true' to enable TLS");
        log.info("2. Create a keystore file of type PKCS12 containing your server certificate and private key");
        log.info("3. Set the server property 'management-server-tls-keystore' to the path of your keystore file");
        log.info("4. Set the keystore password via the environment variable 'MINECRAFT_MANAGEMENT_TLS_KEYSTORE_PASSWORD', or system property 'management.tls.keystore.password', or server property 'management-server-tls-keystore-password'");
        log.info("5. Restart the server to apply the changes.");
    }
}
