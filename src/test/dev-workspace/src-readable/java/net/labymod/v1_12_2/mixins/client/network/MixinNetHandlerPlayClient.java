package net.labymod.v1_12_2.mixins.client.network;

import io.netty.buffer.ByteBuf;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.Namespaces;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.network.ClientPacketListener;
import net.labymod.api.client.network.NettyConnectionAccessor;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.network.server.ServerController;
import net.labymod.api.client.network.server.storage.StorageServerData;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.ResourceLocationFactory;
import net.labymod.api.event.client.entity.player.ClientPlayerAbilitiesUpdateEvent;
import net.labymod.api.event.client.network.playerinfo.PlayerInfoAddEvent;
import net.labymod.api.event.client.network.playerinfo.PlayerInfoRemoveEvent;
import net.labymod.api.event.client.network.playerinfo.PlayerInfoUpdateEvent;
import net.labymod.api.event.client.network.server.ServerKickEvent;
import net.labymod.api.event.client.world.DimensionChangeEvent;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.input.ChatInputOverlay;
import net.labymod.core.client.network.server.DefaultAbstractServerController;
import net.labymod.serverapi.api.payload.PayloadChannelIdentifier;
import net.labymod.v1_12_2.client.gui.screen.LabyScreenRenderer;
import net.labymod.v1_12_2.client.gui.screen.VersionedScreenWrapper;
import net.labymod.v1_12_2.client.player.VersionedNetworkPlayerInfo;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/network/MixinNetHandlerPlayClient.class */
@Mixin({brz.class})
public abstract class MixinNetHandlerPlayClient implements ClientPacketListener {
    private static final int LABYMOD_PAYLOAD_MAX = 1048576;
    private final Map<UUID, NetworkPlayerInfo> labymod$playerInfos = new HashMap();
    private Collection<NetworkPlayerInfo> labymod$unmodifiablePlayerInfos;

    @Shadow
    @Final
    private Map<UUID, bsc> i;

    @Shadow
    @Final
    private static Logger b;

    @Shadow
    private bib f;

    @Shadow
    @Final
    private gw c;

    @Shadow
    public abstract void a(hh hhVar);

    @Override // net.labymod.api.client.network.ClientPacketListener
    public NettyConnectionAccessor getNettyConnection() {
        return this.c;
    }

    @Insert(method = {"handleRespawn"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;getScoreboard()Lnet/minecraft/scoreboard/Scoreboard;", ordinal = 0))
    private void labyMod$fireDimensionChangeEvent(jw packet, InsertInfo ci) {
        ayn from = ayn.a(this.f.h.am);
        ayn to = ayn.a(packet.a());
        ResourceLocationFactory factory = Laby.references().resourceLocationFactory();
        Laby.fireEvent(new DimensionChangeEvent(factory.createMinecraft(from.b()), factory.createMinecraft(to.b())));
    }

    @Inject(method = {"handleJoinGame"}, at = {@At("TAIL")})
    private void labyMod$handleNetworkJoin(jh packet, CallbackInfo ci) {
        ServerController serverController = Laby.labyAPI().serverController();
        serverController.loginOrServerSwitch(serverController.createServerData(bib.z().C()));
    }

    @Inject(method = {"handleCustomPayload"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/network/PacketThreadUtil;checkThreadAndEnqueue(Lnet/minecraft/network/Packet;Lnet/minecraft/network/INetHandler;Lnet/minecraft/util/IThreadListener;)V", shift = At.Shift.AFTER)})
    private void labyMod$handlePayloadReceive(iw packet, CallbackInfo ci) {
        ResourceLocation identifier;
        String channelName = packet.a();
        gy bufferData = packet.b();
        ResourceLocationFactory provider = Laby.labyAPI().renderPipeline().resources().resourceLocationFactory();
        if (channelName.equalsIgnoreCase("MC|Brand")) {
            identifier = provider.createMinecraft("brand");
        } else if (channelName.equalsIgnoreCase("MC|TriList")) {
            identifier = provider.createMinecraft("trilist");
        } else if (channelName.equalsIgnoreCase("MC|BOpen")) {
            identifier = provider.createMinecraft("bopen");
        } else if (channelName.contains(":")) {
            String[] split = channelName.split(":");
            if (split.length != 2) {
                System.err.println("Weird channel name: " + channelName);
                return;
            }
            identifier = provider.create(split[0].toLowerCase(Locale.US), split[1].toLowerCase(Locale.US));
        } else {
            identifier = provider.create(Namespaces.MINECRAFT, channelName.toLowerCase(Locale.US));
        }
        int readable = bufferData.readableBytes();
        if (readable > 1048576) {
            b.warn("Dropping oversized custom payload on channel {} ({} bytes)", identifier, Integer.valueOf(readable));
            return;
        }
        byte[] data = readByteBuf(bufferData.copy());
        ((DefaultAbstractServerController) Laby.labyAPI().serverController()).payloadReceive(identifier.getNamespace(), identifier.getPath(), data);
        boolean canHandleCustomPayload = labyMod$getServerController().handleCustomPayload(PayloadChannelIdentifier.create(identifier.getNamespace(), identifier.getPath()), data);
        if (!canHandleCustomPayload && !labymod$isMinecraftIdentifier(identifier)) {
            b.warn("Unknown custom payload channel: " + String.valueOf(identifier));
        }
    }

    @Inject(method = {"handlePlayerListItem"}, at = {@At("TAIL")})
    private void labyMod$handlePlayerInfo(jp packet, CallbackInfo ci) {
        for (b entry : packet.a()) {
            switch (AnonymousClass1.$SwitchMap$net$minecraft$network$play$server$SPacketPlayerListItem$Action[packet.b().ordinal()]) {
                case 1:
                case 2:
                case 3:
                    NetworkPlayerInfo info = this.labymod$playerInfos.get(entry.a().getId());
                    if (info != null) {
                        PlayerInfoUpdateEvent.UpdateType updateType = null;
                        switch (AnonymousClass1.$SwitchMap$net$minecraft$network$play$server$SPacketPlayerListItem$Action[packet.b().ordinal()]) {
                            case 1:
                                updateType = PlayerInfoUpdateEvent.UpdateType.DISPLAY_NAME;
                                break;
                            case 2:
                                updateType = PlayerInfoUpdateEvent.UpdateType.GAME_MODE;
                                break;
                            case 3:
                                updateType = PlayerInfoUpdateEvent.UpdateType.PING;
                                break;
                        }
                        if (updateType != null) {
                            Laby.fireEvent(new PlayerInfoUpdateEvent(info, updateType));
                        }
                    }
                    break;
                case 4:
                    bsc info2 = this.i.get(entry.a().getId());
                    if (info2 != null) {
                        NetworkPlayerInfo playerInfo = new VersionedNetworkPlayerInfo(info2);
                        this.labymod$playerInfos.put(playerInfo.profile().getUniqueId(), playerInfo);
                        Laby.fireEvent(new PlayerInfoAddEvent(playerInfo));
                    }
                    break;
                case 5:
                    NetworkPlayerInfo info3 = this.labymod$playerInfos.remove(entry.a().getId());
                    if (info3 != null) {
                        Laby.fireEvent(new PlayerInfoRemoveEvent(info3));
                    }
                    break;
            }
        }
    }

    /* JADX INFO: renamed from: net.labymod.v1_12_2.mixins.client.network.MixinNetHandlerPlayClient$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/network/MixinNetHandlerPlayClient$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$network$play$server$SPacketPlayerListItem$Action = new int[a.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$network$play$server$SPacketPlayerListItem$Action[a.d.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$network$play$server$SPacketPlayerListItem$Action[a.b.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$network$play$server$SPacketPlayerListItem$Action[a.c.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$minecraft$network$play$server$SPacketPlayerListItem$Action[a.a.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$net$minecraft$network$play$server$SPacketPlayerListItem$Action[a.e.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    @Insert(method = {"handleTabComplete"}, at = @At("TAIL"))
    private void labyMod$handleTabComplete(im packet, InsertInfo ci) {
        String[] strings = packet.a();
        blk currentScreen = this.f.m;
        if (!(currentScreen instanceof LabyScreenRenderer)) {
            return;
        }
        LabyScreen labyScreen = ((LabyScreenRenderer) currentScreen).screen();
        if (!(labyScreen instanceof ChatInputOverlay)) {
            return;
        }
        ChatInputOverlay overlay = (ChatInputOverlay) labyScreen;
        ScreenInstance screenInstanceMostInnerScreenInstance = overlay.mostInnerScreenInstance();
        if (!(screenInstanceMostInnerScreenInstance instanceof VersionedScreenWrapper)) {
            return;
        }
        VersionedScreenWrapper screenWrapper = (VersionedScreenWrapper) screenInstanceMostInnerScreenInstance;
        Object versionedScreen = screenWrapper.getVersionedScreen();
        if (!(versionedScreen instanceof bkn)) {
            return;
        }
        ((bkn) versionedScreen).a(strings);
    }

    @Insert(method = {"handleChat"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;addChatMessage(Lnet/minecraft/util/text/ChatType;Lnet/minecraft/util/text/ITextComponent;)V", shift = At.Shift.BEFORE))
    private void labyMod$storeChatVisibility(in packet, InsertInfo ci) {
        this.f.q.d().setChatVisibility(packet.a(), b.a(packet.c().ordinal()));
    }

    @Insert(method = {"handleChat"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;addChatMessage(Lnet/minecraft/util/text/ChatType;Lnet/minecraft/util/text/ITextComponent;)V", shift = At.Shift.AFTER))
    private void labyMod$clearChatVisibility(in packet, InsertInfo ci) {
        this.f.q.d().clearChatVisibility(packet.a());
    }

    @ModifyVariable(method = {"onDisconnect"}, at = @At("HEAD"), argsOnly = true)
    public hh labyMod$modifyReadson(hh component) {
        ConnectableServerData serverData;
        ServerController serverController = Laby.labyAPI().serverController();
        StorageServerData currentStorageServerData = serverController.getCurrentStorageServerData();
        if (currentStorageServerData != null) {
            serverData = currentStorageServerData;
        } else if (serverController.getCurrentServerData() != null) {
            serverData = ConnectableServerData.from(serverController.getCurrentServerData());
        } else if (bib.z().C() != null) {
            serverData = ConnectableServerData.from(serverController.createServerData(bib.z().C()));
        } else {
            return component;
        }
        return ((ServerKickEvent) Laby.fireEvent(new ServerKickEvent(serverData, (Component) component, ServerKickEvent.Context.PLAY))).reason();
    }

    @Inject(method = {"handlePlayerAbilities"}, at = {@At("RETURN")})
    private void labyMod$fireAbilityUpdateEvent(jn packet, CallbackInfo ci) {
        Laby.fireEvent(new ClientPlayerAbilitiesUpdateEvent(bib.z().h));
    }

    @Override // net.labymod.api.client.network.ClientPacketListener
    public Collection<NetworkPlayerInfo> getNetworkPlayerInfos() {
        if (this.labymod$unmodifiablePlayerInfos == null) {
            this.labymod$unmodifiablePlayerInfos = Collections.unmodifiableCollection(this.labymod$playerInfos.values());
        }
        return this.labymod$unmodifiablePlayerInfos;
    }

    @Override // net.labymod.api.client.network.ClientPacketListener
    @Nullable
    public NetworkPlayerInfo getNetworkPlayerInfo(UUID uniqueId) {
        return this.labymod$playerInfos.get(uniqueId);
    }

    @Override // net.labymod.api.client.network.ClientPacketListener
    @Nullable
    public NetworkPlayerInfo getNetworkPlayerInfo(String username) {
        for (NetworkPlayerInfo playerInfo : this.labymod$playerInfos.values()) {
            if (username.equals(playerInfo.profile().getUsername())) {
                return playerInfo;
            }
        }
        return null;
    }

    private boolean labymod$isMinecraftIdentifier(@NotNull ResourceLocation location) {
        if (!location.getNamespace().equals(Namespaces.MINECRAFT)) {
            return false;
        }
        String path = location.getPath();
        return path.equals("register") || path.equals("brand") || path.equals("bopen") || path.equals("trilist");
    }

    private ServerController labyMod$getServerController() {
        return Laby.labyAPI().serverController();
    }

    private byte[] readByteBuf(@NotNull ByteBuf buffer) {
        byte[] data = new byte[buffer.readableBytes()];
        buffer.readBytes(data);
        return data;
    }

    @Override // net.labymod.api.client.network.ClientPacketListener
    public void simulateKick(Component reason) {
        ThreadSafe.ensureRenderThread();
        a((hh) Laby.labyAPI().minecraft().componentMapper().toMinecraftComponent(reason));
    }
}
