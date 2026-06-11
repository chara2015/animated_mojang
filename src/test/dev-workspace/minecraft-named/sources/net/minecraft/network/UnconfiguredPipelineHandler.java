package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import io.netty.util.ReferenceCountUtil;
import net.minecraft.network.protocol.Packet;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/UnconfiguredPipelineHandler.class */
public class UnconfiguredPipelineHandler {

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/UnconfiguredPipelineHandler$Inbound.class */
    public static class Inbound extends ChannelDuplexHandler {
        /* JADX INFO: Thrown type has an unknown type hierarchy: io.netty.handler.codec.DecoderException */
        public void channelRead(ChannelHandlerContext $$0, Object $$1) throws DecoderException {
            if (($$1 instanceof ByteBuf) || ($$1 instanceof Packet)) {
                ReferenceCountUtil.release($$1);
                throw new DecoderException("Pipeline has no inbound protocol configured, can't process packet " + String.valueOf($$1));
            }
            $$0.fireChannelRead($$1);
        }

        public void write(ChannelHandlerContext $$0, Object $$1, ChannelPromise $$2) throws Exception {
            if ($$1 instanceof InboundConfigurationTask) {
                InboundConfigurationTask $$3 = (InboundConfigurationTask) $$1;
                try {
                    $$3.run($$0);
                    ReferenceCountUtil.release($$1);
                    $$2.setSuccess();
                    return;
                } catch (Throwable th) {
                    ReferenceCountUtil.release($$1);
                    throw th;
                }
            }
            $$0.write($$1, $$2);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/UnconfiguredPipelineHandler$Outbound.class */
    public static class Outbound extends ChannelOutboundHandlerAdapter {
        /* JADX INFO: Thrown type has an unknown type hierarchy: io.netty.handler.codec.EncoderException */
        public void write(ChannelHandlerContext $$0, Object $$1, ChannelPromise $$2) throws Exception {
            if ($$1 instanceof Packet) {
                ReferenceCountUtil.release($$1);
                throw new EncoderException("Pipeline has no outbound protocol configured, can't process packet " + String.valueOf($$1));
            }
            if ($$1 instanceof OutboundConfigurationTask) {
                OutboundConfigurationTask $$3 = (OutboundConfigurationTask) $$1;
                try {
                    $$3.run($$0);
                    ReferenceCountUtil.release($$1);
                    $$2.setSuccess();
                    return;
                } catch (Throwable th) {
                    ReferenceCountUtil.release($$1);
                    throw th;
                }
            }
            $$0.write($$1, $$2);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/UnconfiguredPipelineHandler$InboundConfigurationTask.class */
    @FunctionalInterface
    public interface InboundConfigurationTask {
        void run(ChannelHandlerContext channelHandlerContext);

        default InboundConfigurationTask andThen(InboundConfigurationTask $$0) {
            return $$1 -> {
                run($$1);
                $$0.run($$1);
            };
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/UnconfiguredPipelineHandler$OutboundConfigurationTask.class */
    @FunctionalInterface
    public interface OutboundConfigurationTask {
        void run(ChannelHandlerContext channelHandlerContext);

        default OutboundConfigurationTask andThen(OutboundConfigurationTask $$0) {
            return $$1 -> {
                run($$1);
                $$0.run($$1);
            };
        }
    }

    public static <T extends PacketListener> InboundConfigurationTask setupInboundProtocol(ProtocolInfo<T> $$0) {
        return setupInboundHandler(new PacketDecoder($$0));
    }

    private static InboundConfigurationTask setupInboundHandler(ChannelInboundHandler $$0) {
        return $$1 -> {
            $$1.pipeline().replace($$1.name(), HandlerNames.DECODER, $$0);
            $$1.channel().config().setAutoRead(true);
        };
    }

    public static <T extends PacketListener> OutboundConfigurationTask setupOutboundProtocol(ProtocolInfo<T> $$0) {
        return setupOutboundHandler(new PacketEncoder($$0));
    }

    private static OutboundConfigurationTask setupOutboundHandler(ChannelOutboundHandler $$0) {
        return $$1 -> {
            $$1.pipeline().replace($$1.name(), HandlerNames.ENCODER, $$0);
        };
    }
}
