package net.labymod.v1_19_4.mixins.client;

import com.mojang.authlib.GameProfile;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.network.ClientPacketListener;
import net.labymod.api.client.network.NettyConnectionAccessor;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.network.server.ServerController;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.client.network.server.storage.StorageServerData;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.event.client.entity.player.ClientPlayerAbilitiesUpdateEvent;
import net.labymod.api.event.client.network.playerinfo.PlayerInfoAddEvent;
import net.labymod.api.event.client.network.playerinfo.PlayerInfoRemoveEvent;
import net.labymod.api.event.client.network.playerinfo.PlayerInfoUpdateEvent;
import net.labymod.api.event.client.network.server.ServerKickEvent;
import net.labymod.api.event.client.world.DimensionChangeEvent;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.worldsharing.Worldsharing;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.animation.old.animations.SwordOldAnimation;
import net.labymod.core.watcher.set.WatchableHashSet;
import net.labymod.v1_19_4.client.multiplayer.server.VersionedServerController;
import net.labymod.v1_19_4.client.player.VersionedNetworkPlayerInfo;
import net.labymod.v1_19_4.client.util.WatchablePlayerInfoSet;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/client/MixinClientPacketListener.class */
@Mixin({fdk.class})
public abstract class MixinClientPacketListener implements ClientPacketListener {
    private final Map<UUID, NetworkPlayerInfo> labyMod$playerInfos = new HashMap();
    private final Set<NetworkPlayerInfo> labyMod$shownPlayers = new ReferenceOpenHashSet();
    private Collection<NetworkPlayerInfo> labyMod$unmodifiablePlayerInfos;

    @Mutable
    @Shadow
    @Final
    private Set<fdo> q;

    @Shadow
    @Final
    private Map<UUID, fdo> p;

    @Shadow
    private fdj n;

    @Shadow
    @Final
    private sq h;

    @Shadow
    public abstract void a(tj tjVar);

    @Override // net.labymod.api.client.network.ClientPacketListener
    public NettyConnectionAccessor getNettyConnection() {
        return this.h;
    }

    @Insert(method = {"handleRespawn"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getScoreboard()Lnet/minecraft/world/scores/Scoreboard;", ordinal = 0))
    private void labyMod$fireDimensionChangeEvent(xs packet, InsertInfo ci) {
        add from = this.n.ab().a();
        add to = packet.c().a();
        Laby.fireEvent(new DimensionChangeEvent(ResourceLocation.create(from.b(), from.a()), ResourceLocation.create(to.b(), to.a())));
    }

    @Inject(method = {"<init>"}, at = {@At("TAIL")})
    private void labyMod$watchableListedPlayers(emh $$0, etd $$1, sq $$2, fdq $$3, GameProfile $$4, fxw $$5, CallbackInfo ci) {
        this.q = new WatchableHashSet(new WatchablePlayerInfoSet(this.labyMod$shownPlayers));
    }

    @Inject(method = {"handlePlayerInfoUpdate"}, at = {@At(value = "INVOKE", target = "Lorg/slf4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;)V", shift = At.Shift.BEFORE)}, cancellable = true)
    private void labyMod$disableWarn(xl $$0, CallbackInfo ci) {
        ci.cancel();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Inject(method = {"handlePlayerInfoUpdate"}, at = {@At("TAIL")})
    private void labyMod$handleAddPlayer(xl packet, CallbackInfo ci) throws MatchException {
        PlayerInfoUpdateEvent.UpdateType updateType;
        for (b entry : packet.d()) {
            UUID profileId = entry.a();
            if (!this.labyMod$playerInfos.containsKey(profileId)) {
                NetworkPlayerInfo playerInfo = new VersionedNetworkPlayerInfo(this.p.get(profileId));
                this.labyMod$playerInfos.put(profileId, playerInfo);
                Laby.fireEvent(new PlayerInfoAddEvent(playerInfo));
            }
        }
        for (b entry2 : packet.c()) {
            NetworkPlayerInfo playerInfo2 = this.labyMod$playerInfos.get(entry2.a());
            if (playerInfo2 != null) {
                for (a action : packet.a()) {
                    switch (AnonymousClass1.$SwitchMap$net$minecraft$network$protocol$game$ClientboundPlayerInfoUpdatePacket$Action[action.ordinal()]) {
                        case 1:
                        case 2:
                            updateType = null;
                            break;
                        case 3:
                            updateType = PlayerInfoUpdateEvent.UpdateType.UPDATE_LISTED;
                            break;
                        case 4:
                            updateType = PlayerInfoUpdateEvent.UpdateType.GAME_MODE;
                            break;
                        case 5:
                            updateType = PlayerInfoUpdateEvent.UpdateType.PING;
                            break;
                        case 6:
                            updateType = PlayerInfoUpdateEvent.UpdateType.DISPLAY_NAME;
                            break;
                        default:
                            throw new MatchException((String) null, (Throwable) null);
                    }
                    PlayerInfoUpdateEvent.UpdateType updateType2 = updateType;
                    if (updateType2 != null) {
                        Laby.fireEvent(new PlayerInfoUpdateEvent(playerInfo2, updateType2));
                    }
                }
            }
        }
    }

    /* JADX INFO: renamed from: net.labymod.v1_19_4.mixins.client.MixinClientPacketListener$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/client/MixinClientPacketListener$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$network$protocol$game$ClientboundPlayerInfoUpdatePacket$Action = new int[a.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$network$protocol$game$ClientboundPlayerInfoUpdatePacket$Action[a.a.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$network$protocol$game$ClientboundPlayerInfoUpdatePacket$Action[a.b.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$network$protocol$game$ClientboundPlayerInfoUpdatePacket$Action[a.d.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$minecraft$network$protocol$game$ClientboundPlayerInfoUpdatePacket$Action[a.c.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$net$minecraft$network$protocol$game$ClientboundPlayerInfoUpdatePacket$Action[a.e.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$net$minecraft$network$protocol$game$ClientboundPlayerInfoUpdatePacket$Action[a.f.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    @Redirect(method = {"handlePlayerInfoRemove"}, at = @At(value = "INVOKE", target = "Ljava/util/Map;remove(Ljava/lang/Object;)Ljava/lang/Object;"))
    public Object labyMod$handlePlayerInfoRemove(Map<?, ?> instance, Object value) {
        UUID profileId = (UUID) value;
        NetworkPlayerInfo networkPlayerInfo = this.labyMod$playerInfos.remove(profileId);
        if (networkPlayerInfo != null) {
            Laby.fireEvent(new PlayerInfoRemoveEvent(networkPlayerInfo));
        }
        return instance.remove(value);
    }

    @Insert(method = {"handleSetEntityData(Lnet/minecraft/network/protocol/game/ClientboundSetEntityDataPacket;)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getEntity(I)Lnet/minecraft/world/entity/Entity;", shift = At.Shift.AFTER))
    private void labyMod$handleClassicPvPBlocking(yj packet, InsertInfo insertInfo) {
        int entityId = packet.a();
        bym bymVarA = this.n.a(entityId);
        if (!(bymVarA instanceof bym)) {
            return;
        }
        bym player = bymVarA;
        List<b<?>> unpackedData = packet.c();
        if (unpackedData == null || unpackedData.isEmpty()) {
            return;
        }
        unpackedData.stream().filter(entry -> {
            return entry.a() == 8;
        }).forEach(entry2 -> {
            Object value = entry2.c();
            if (!(value instanceof Byte)) {
                return;
            }
            bdx activeHand = player.ff();
            int state = ((Byte) value).intValue();
            SwordOldAnimation animation = (SwordOldAnimation) LabyMod.getInstance().getOldAnimationRegistry().get(SwordOldAnimation.NAME);
            if (animation == null) {
                return;
            }
            try {
                cfv heldItem = player.b(activeHand);
                if (heldItem.c() == cfy.ut || (heldItem.c() instanceof chf)) {
                    animation.setBlockingState(player.cs(), state == 1 || state == 3);
                }
            } catch (IllegalArgumentException e) {
                animation.setBlockingState(player.cs(), state == 1 || state == 3);
            }
        });
    }

    @ModifyVariable(method = {"onDisconnect"}, at = @At("HEAD"), argsOnly = true)
    public tj labyMod$modifyReason(tj component) {
        ConnectableServerData serverData;
        ServerController serverController = Laby.labyAPI().serverController();
        StorageServerData currentStorageServerData = serverController.getCurrentStorageServerData();
        if (currentStorageServerData != null) {
            serverData = currentStorageServerData;
        } else if (serverController.getCurrentServerData() != null) {
            serverData = ConnectableServerData.from(serverController.getCurrentServerData());
        } else {
            ServerData data = ((VersionedServerController) serverController).getConnectingServerData();
            if (data == null) {
                return component;
            }
            serverData = ConnectableServerData.from(data);
        }
        ComponentMapper componentMapper = Laby.labyAPI().minecraft().componentMapper();
        return (tj) componentMapper.toMinecraftComponent(((ServerKickEvent) Laby.fireEvent(new ServerKickEvent(serverData, componentMapper.fromMinecraftComponent(component), ServerKickEvent.Context.PLAY))).reason());
    }

    @Inject(method = {"send(Lnet/minecraft/network/protocol/game/ServerboundResourcePackPacket$Action;)V"}, at = {@At("HEAD")})
    private void labyMod$disableCustomFont(a action, CallbackInfo ci) {
        if (action == a.a) {
            LabyMod.references().clientNetworkPacketListener().onLoadServerResourcePack();
        }
    }

    @Inject(method = {"handlePlayerAbilities"}, at = {@At("RETURN")})
    private void labyMod$fireAbilityUpdateEvent(xf packet, CallbackInfo ci) {
        Laby.fireEvent(new ClientPlayerAbilitiesUpdateEvent(emh.N().t));
    }

    @Inject(method = {"handleCustomPayload"}, at = {@At("HEAD")}, cancellable = true)
    public void labymod$interceptCustomPayload(wc payload, CallbackInfo ci) {
        if (!Worldsharing.CHANNEL.equals(payload.a().toString()) || payload.c().readableBytes() > 128) {
            return;
        }
        SocketAddress socketAddress = this.h.n;
        if (!(socketAddress instanceof InetSocketAddress)) {
            return;
        }
        InetSocketAddress address = (InetSocketAddress) socketAddress;
        String target = payload.c().readCharSequence(payload.c().readableBytes(), StandardCharsets.UTF_8).toString();
        if (!Worldsharing.checkAddr(target)) {
            return;
        }
        payload.c().clear();
        this.h.a(new aad(payload.a(), payload.c()));
        Worldsharing.handle(address.getHostName(), target);
        ci.cancel();
    }

    @Override // net.labymod.api.client.network.ClientPacketListener
    @Nullable
    public NetworkPlayerInfo getNetworkPlayerInfo(UUID uniqueId) {
        return this.labyMod$playerInfos.get(uniqueId);
    }

    @Override // net.labymod.api.client.network.ClientPacketListener
    @Nullable
    public NetworkPlayerInfo getNetworkPlayerInfo(String username) {
        for (NetworkPlayerInfo playerInfo : this.labyMod$playerInfos.values()) {
            if (username.equals(playerInfo.profile().getUsername())) {
                return playerInfo;
            }
        }
        return null;
    }

    @Override // net.labymod.api.client.network.ClientPacketListener
    public Collection<NetworkPlayerInfo> getNetworkPlayerInfos() {
        if (this.labyMod$unmodifiablePlayerInfos == null) {
            this.labyMod$unmodifiablePlayerInfos = Collections.unmodifiableCollection(this.labyMod$playerInfos.values());
        }
        return this.labyMod$unmodifiablePlayerInfos;
    }

    @Override // net.labymod.api.client.network.ClientPacketListener
    public Collection<NetworkPlayerInfo> getShownOnlinePlayers() {
        return this.labyMod$shownPlayers;
    }

    @Override // net.labymod.api.client.network.ClientPacketListener
    public void simulateKick(Component reason) {
        ThreadSafe.ensureRenderThread();
        a((tj) Laby.labyAPI().minecraft().componentMapper().toMinecraftComponent(reason));
    }
}
