package net.minecraft.server.jsonrpc.websocket;

import com.google.gson.JsonElement;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import java.util.List;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/jsonrpc/websocket/JsonToWebSocketEncoder.class */
public class JsonToWebSocketEncoder extends MessageToMessageEncoder<JsonElement> {
    protected /* synthetic */ void encode(ChannelHandlerContext channelHandlerContext, Object obj, List list) throws Exception {
        encode(channelHandlerContext, (JsonElement) obj, (List<Object>) list);
    }

    protected void encode(ChannelHandlerContext $$0, JsonElement $$1, List<Object> $$2) {
        $$2.add(new TextWebSocketFrame($$1.toString()));
    }
}
