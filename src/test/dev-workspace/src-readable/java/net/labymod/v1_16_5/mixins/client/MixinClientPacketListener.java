package net.labymod.v1_16_5.mixins.client;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.network.ClientPacketListener;
import net.labymod.api.client.network.NettyConnectionAccessor;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.network.server.ServerController;
import net.labymod.api.client.network.server.storage.StorageServerData;
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
import net.labymod.v1_16_5.client.player.VersionedNetworkPlayerInfo;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/MixinClientPacketListener.class */
@Mixin({dwu.class})
public abstract class MixinClientPacketListener implements ClientPacketListener {
    private final Map<UUID, NetworkPlayerInfo> labyMod$playerInfos = new HashMap();
    private Collection<NetworkPlayerInfo> labyMod$unmodifiablePlayerInfos;

    @Shadow
    @Final
    private Map<UUID, dwx> j;

    @Shadow
    private dwt g;

    @Shadow
    @Final
    private nd c;

    @Shadow
    @Final
    private djz f;

    @Shadow
    public abstract void a(nr nrVar);

    @Override // net.labymod.api.client.network.ClientPacketListener
    public NettyConnectionAccessor getNettyConnection() {
        return this.c;
    }

    @Insert(method = {"handleRespawn"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getScoreboard()Lnet/minecraft/world/scores/Scoreboard;", ordinal = 0))
    private void labyMod$fireDimensionChangeEvent(qp packet, InsertInfo ci) {
        vk from = this.g.Y().a();
        vk to = packet.c().a();
        Laby.fireEvent(new DimensionChangeEvent(ResourceLocation.create(from.b(), from.a()), ResourceLocation.create(to.b(), to.a())));
    }

    @Insert(method = {"handlePlayerInfo(Lnet/minecraft/network/protocol/game/ClientboundPlayerInfoPacket;)V"}, at = @At("TAIL"))
    private void labyMod$handlePlayerInfo(qi packet, InsertInfo ci) {
        for (b entry : packet.b()) {
            switch (AnonymousClass1.$SwitchMap$net$minecraft$network$protocol$game$ClientboundPlayerInfoPacket$Action[packet.c().ordinal()]) {
                case 1:
                case 2:
                case 3:
                    NetworkPlayerInfo info = this.labyMod$playerInfos.get(entry.a().getId());
                    if (info != null) {
                        PlayerInfoUpdateEvent.UpdateType updateType = null;
                        switch (AnonymousClass1.$SwitchMap$net$minecraft$network$protocol$game$ClientboundPlayerInfoPacket$Action[packet.c().ordinal()]) {
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
                    dwx info2 = this.j.get(entry.a().getId());
                    if (info2 != null) {
                        NetworkPlayerInfo playerInfo = new VersionedNetworkPlayerInfo(info2);
                        this.labyMod$playerInfos.put(playerInfo.profile().getUniqueId(), playerInfo);
                        Laby.fireEvent(new PlayerInfoAddEvent(playerInfo));
                    }
                    break;
                case 5:
                    NetworkPlayerInfo info3 = this.labyMod$playerInfos.remove(entry.a().getId());
                    if (info3 != null) {
                        Laby.fireEvent(new PlayerInfoRemoveEvent(info3));
                    }
                    break;
            }
        }
    }

    /* JADX INFO: renamed from: net.labymod.v1_16_5.mixins.client.MixinClientPacketListener$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/MixinClientPacketListener$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$network$protocol$game$ClientboundPlayerInfoPacket$Action = new int[a.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$network$protocol$game$ClientboundPlayerInfoPacket$Action[a.d.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$network$protocol$game$ClientboundPlayerInfoPacket$Action[a.b.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$network$protocol$game$ClientboundPlayerInfoPacket$Action[a.c.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$minecraft$network$protocol$game$ClientboundPlayerInfoPacket$Action[a.a.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$net$minecraft$network$protocol$game$ClientboundPlayerInfoPacket$Action[a.e.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    @Insert(method = {"handleSetEntityData(Lnet/minecraft/network/protocol/game/ClientboundSetEntityDataPacket;)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getEntity(I)Lnet/minecraft/world/entity/Entity;", shift = At.Shift.AFTER))
    private void labyMod$handleClassicPvPBlocking(ra packet, InsertInfo insertInfo) {
        int entityId = packet.c();
        bfw bfwVarA = this.g.a(entityId);
        if (!(bfwVarA instanceof bfw)) {
            return;
        }
        bfw player = bfwVarA;
        List<a<?>> unpackedData = packet.b();
        if (unpackedData == null || unpackedData.isEmpty()) {
            return;
        }
        unpackedData.stream().filter(entry -> {
            return entry.a().a() == 8;
        }).forEach(entry2 -> {
            Object value = entry2.b();
            if (!(value instanceof Byte)) {
                return;
            }
            aot activeHand = player.dX();
            int state = ((Byte) value).intValue();
            SwordOldAnimation animation = (SwordOldAnimation) LabyMod.getInstance().getOldAnimationRegistry().get(SwordOldAnimation.NAME);
            if (animation == null) {
                return;
            }
            try {
                bmb heldItem = player.b(activeHand);
                if (heldItem.b() == bmd.qn || (heldItem.b() instanceof bnf)) {
                    animation.setBlockingState(player.bS(), state == 1 || state == 3);
                }
            } catch (IllegalArgumentException e) {
                animation.setBlockingState(player.bS(), state == 1 || state == 3);
            }
        });
    }

    @ModifyVariable(method = {"onDisconnect"}, at = @At("HEAD"), argsOnly = true)
    public nr labyMod$modifyReadson(nr component) {
        ConnectableServerData serverData;
        ServerController serverController = Laby.labyAPI().serverController();
        StorageServerData currentStorageServerData = serverController.getCurrentStorageServerData();
        if (currentStorageServerData != null) {
            serverData = currentStorageServerData;
        } else if (serverController.getCurrentServerData() != null) {
            serverData = ConnectableServerData.from(serverController.getCurrentServerData());
        } else if (djz.C().E() != null) {
            serverData = ConnectableServerData.from(serverController.createServerData(djz.C().E()));
        } else {
            return component;
        }
        return ((ServerKickEvent) Laby.fireEvent(new ServerKickEvent(serverData, (Component) component, ServerKickEvent.Context.PLAY))).reason();
    }

    @Inject(method = {"send(Lnet/minecraft/network/protocol/game/ServerboundResourcePackPacket$Action;)V"}, at = {@At("HEAD")})
    private void labyMod$disableCustomFont(a action, CallbackInfo ci) {
        if (action == a.a) {
            LabyMod.references().clientNetworkPacketListener().onLoadServerResourcePack();
        }
    }

    @Inject(method = {"handlePlayerAbilities"}, at = {@At("RETURN")})
    private void labyMod$fireAbilityUpdateEvent(qg packet, CallbackInfo ci) {
        Laby.fireEvent(new ClientPlayerAbilitiesUpdateEvent(djz.C().s));
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
    public void simulateKick(Component reason) {
        ThreadSafe.ensureRenderThread();
        a((nr) Laby.labyAPI().minecraft().componentMapper().toMinecraftComponent(reason));
    }

    @Inject(method = {"handleCustomPayload"}, at = {@At("HEAD")}, cancellable = true)
    public void labymod$interceptCustomPayload(pk payload, CallbackInfo ci) {
        dwz serverData;
        if (!Worldsharing.CHANNEL.equals(payload.b().toString()) || payload.c().readableBytes() > 128 || (serverData = this.f.E()) == null) {
            return;
        }
        String target = payload.c().readCharSequence(payload.c().readableBytes(), StandardCharsets.UTF_8).toString();
        if (!Worldsharing.checkAddr(target)) {
            return;
        }
        payload.c().clear();
        this.c.a(new sm(payload.b(), payload.c()));
        Worldsharing.handle(serverData.b, target);
        ci.cancel();
    }
}
