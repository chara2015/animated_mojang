package net.minecraft.network.chat;

import com.mojang.logging.LogUtils;
import java.time.Instant;
import java.util.UUID;
import java.util.function.BooleanSupplier;
import net.minecraft.util.SignatureValidator;
import net.minecraft.util.Signer;
import net.minecraft.world.entity.player.ProfilePublicKey;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/SignedMessageChain.class */
public class SignedMessageChain {
    static final Logger LOGGER = LogUtils.getLogger();
    SignedMessageLink nextLink;
    Instant lastTimeStamp = Instant.EPOCH;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/SignedMessageChain$Encoder.class */
    @FunctionalInterface
    public interface Encoder {
        public static final Encoder UNSIGNED = $$0 -> {
            return null;
        };

        MessageSignature pack(SignedMessageBody signedMessageBody);
    }

    public SignedMessageChain(UUID $$0, UUID $$1) {
        this.nextLink = SignedMessageLink.root($$0, $$1);
    }

    public Encoder encoder(Signer $$0) {
        return $$1 -> {
            SignedMessageLink $$2 = this.nextLink;
            if ($$2 == null) {
                return null;
            }
            this.nextLink = $$2.advance();
            return new MessageSignature($$0.sign($$22 -> {
                PlayerChatMessage.updateSignature($$22, $$2, $$1);
            }));
        };
    }

    public Decoder decoder(final ProfilePublicKey $$0) {
        final SignatureValidator $$1 = $$0.createSignatureValidator();
        return new Decoder() { // from class: net.minecraft.network.chat.SignedMessageChain.1
            @Override // net.minecraft.network.chat.SignedMessageChain.Decoder
            public PlayerChatMessage unpack(MessageSignature $$02, SignedMessageBody $$12) throws DecodeException {
                if ($$02 == null) {
                    throw new DecodeException(DecodeException.MISSING_PROFILE_KEY);
                }
                if ($$0.data().hasExpired()) {
                    throw new DecodeException(DecodeException.EXPIRED_PROFILE_KEY);
                }
                SignedMessageLink $$2 = SignedMessageChain.this.nextLink;
                if ($$2 == null) {
                    throw new DecodeException(DecodeException.CHAIN_BROKEN);
                }
                if ($$12.timeStamp().isBefore(SignedMessageChain.this.lastTimeStamp)) {
                    setChainBroken();
                    throw new DecodeException(DecodeException.OUT_OF_ORDER_CHAT);
                }
                SignedMessageChain.this.lastTimeStamp = $$12.timeStamp();
                PlayerChatMessage $$3 = new PlayerChatMessage($$2, $$02, $$12, null, FilterMask.PASS_THROUGH);
                if (!$$3.verify($$1)) {
                    setChainBroken();
                    throw new DecodeException(DecodeException.INVALID_SIGNATURE);
                }
                if ($$3.hasExpiredServer(Instant.now())) {
                    SignedMessageChain.LOGGER.warn("Received expired chat: '{}'. Is the client/server system time unsynchronized?", $$12.content());
                }
                SignedMessageChain.this.nextLink = $$2.advance();
                return $$3;
            }

            @Override // net.minecraft.network.chat.SignedMessageChain.Decoder
            public void setChainBroken() {
                SignedMessageChain.this.nextLink = null;
            }
        };
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/SignedMessageChain$Decoder.class */
    @FunctionalInterface
    public interface Decoder {
        PlayerChatMessage unpack(MessageSignature messageSignature, SignedMessageBody signedMessageBody) throws DecodeException;

        static Decoder unsigned(UUID $$0, BooleanSupplier $$1) {
            return ($$2, $$3) -> {
                if ($$1.getAsBoolean()) {
                    throw new DecodeException(DecodeException.MISSING_PROFILE_KEY);
                }
                return PlayerChatMessage.unsigned($$0, $$3.content());
            };
        }

        default void setChainBroken() {
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/SignedMessageChain$DecodeException.class */
    public static class DecodeException extends ThrowingComponent {
        static final Component MISSING_PROFILE_KEY = Component.translatable("chat.disabled.missingProfileKey");
        static final Component CHAIN_BROKEN = Component.translatable("chat.disabled.chain_broken");
        static final Component EXPIRED_PROFILE_KEY = Component.translatable("chat.disabled.expiredProfileKey");
        static final Component INVALID_SIGNATURE = Component.translatable("chat.disabled.invalid_signature");
        static final Component OUT_OF_ORDER_CHAT = Component.translatable("chat.disabled.out_of_order_chat");

        public DecodeException(Component $$0) {
            super($$0);
        }
    }
}
