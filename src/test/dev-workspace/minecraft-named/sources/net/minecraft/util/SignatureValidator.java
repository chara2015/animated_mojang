package net.minecraft.util;

import com.mojang.authlib.yggdrasil.ServicesKeyInfo;
import com.mojang.authlib.yggdrasil.ServicesKeySet;
import com.mojang.authlib.yggdrasil.ServicesKeyType;
import com.mojang.logging.LogUtils;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Collection;
import java.util.Objects;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/SignatureValidator.class */
public interface SignatureValidator {
    public static final SignatureValidator NO_VALIDATION = ($$0, $$1) -> {
        return true;
    };
    public static final Logger LOGGER = LogUtils.getLogger();

    boolean validate(SignatureUpdater signatureUpdater, byte[] bArr);

    default boolean validate(byte[] $$0, byte[] $$1) {
        return validate($$12 -> {
            $$12.update($$0);
        }, $$1);
    }

    private static boolean verifySignature(SignatureUpdater $$0, byte[] $$1, Signature $$2) throws SignatureException {
        Objects.requireNonNull($$2);
        $$0.update($$2::update);
        return $$2.verify($$1);
    }

    static SignatureValidator from(PublicKey $$0, String $$1) {
        return ($$2, $$3) -> {
            try {
                Signature $$4 = Signature.getInstance($$1);
                $$4.initVerify($$0);
                return verifySignature($$2, $$3, $$4);
            } catch (Exception $$5) {
                LOGGER.error("Failed to verify signature", $$5);
                return false;
            }
        };
    }

    static SignatureValidator from(ServicesKeySet $$0, ServicesKeyType $$1) {
        Collection<ServicesKeyInfo> $$2 = $$0.keys($$1);
        if ($$2.isEmpty()) {
            return null;
        }
        return ($$12, $$22) -> {
            return $$2.stream().anyMatch($$22 -> {
                Signature $$3 = $$22.signature();
                try {
                    return verifySignature($$12, $$22, $$3);
                } catch (SignatureException $$4) {
                    LOGGER.error("Failed to verify Services signature", $$4);
                    return false;
                }
            });
        };
    }
}
