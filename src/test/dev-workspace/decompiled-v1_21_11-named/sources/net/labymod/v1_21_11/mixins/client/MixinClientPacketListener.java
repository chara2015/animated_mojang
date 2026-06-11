package net.labymod.v1_21_11.mixins.client;

import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.network.NettyConnectionAccessor;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.client.entity.player.ClientPlayerAbilitiesUpdateEvent;
import net.labymod.api.event.client.network.playerinfo.PlayerInfoAddEvent;
import net.labymod.api.event.client.network.playerinfo.PlayerInfoRemoveEvent;
import net.labymod.api.event.client.network.playerinfo.PlayerInfoUpdateEvent;
import net.labymod.api.event.client.world.DimensionChangeEvent;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.animation.old.animations.SwordOldAnimation;
import net.labymod.core.watcher.set.WatchableHashSet;
import net.labymod.v1_21_11.client.player.VersionedNetworkPlayerInfo;
import net.labymod.v1_21_11.client.util.MinecraftUtil;
import net.labymod.v1_21_11.client.util.WatchablePlayerInfoSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.CommonListenerCookie;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/MixinClientPacketListener.class */
@Mixin({ClientPacketListener.class})
public abstract class MixinClientPacketListener implements net.labymod.api.client.network.ClientPacketListener {
    private final Map<UUID, NetworkPlayerInfo> labyMod$playerInfos = new HashMap();
    private final Set<NetworkPlayerInfo> labyMod$shownPlayers = new ReferenceOpenHashSet();
    private Collection<NetworkPlayerInfo> labyMod$unmodifiablePlayerInfos;

    @Mutable
    @Shadow
    @Final
    private Set<PlayerInfo> E;

    @Shadow
    @Final
    private Map<UUID, PlayerInfo> D;

    @Shadow
    private ClientLevel B;

    @Shadow
    public abstract Connection m();

    public NettyConnectionAccessor getNettyConnection() {
        return m();
    }

    @Inject(method = {"handleRespawn"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;addMapData(Ljava/util/Map;)V")})
    private void labyMod$fireDimensionChangeEvent(ClientboundRespawnPacket packet, CallbackInfo ci) {
        Identifier from = this.B.dimension().identifier();
        Identifier to = packet.commonPlayerSpawnInfo().dimension().identifier();
        Laby.fireEvent(new DimensionChangeEvent(ResourceLocation.create(from.getNamespace(), from.getPath()), ResourceLocation.create(to.getNamespace(), to.getPath())));
    }

    @Inject(method = {"<init>"}, at = {@At("TAIL")})
    private void labyMod$watchableListedPlayers(Minecraft $$0, Connection $$1, CommonListenerCookie $$2, CallbackInfo ci) {
        this.E = new WatchableHashSet(new WatchablePlayerInfoSet(this.labyMod$shownPlayers));
    }

    @Inject(method = {"handlePlayerInfoUpdate"}, at = {@At(value = "INVOKE", target = "Lorg/slf4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V", shift = At.Shift.BEFORE, remap = false)}, cancellable = true)
    private void labyMod$disableWarn(ClientboundPlayerInfoUpdatePacket $$0, CallbackInfo ci) {
        ci.cancel();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Inject(method = {"handlePlayerInfoUpdate"}, at = {@At("TAIL")})
    private void labyMod$handleAddPlayer(ClientboundPlayerInfoUpdatePacket packet, CallbackInfo ci) throws MatchException {
        PlayerInfoUpdateEvent.UpdateType updateType;
        for (ClientboundPlayerInfoUpdatePacket.Entry entry : packet.newEntries()) {
            UUID profileId = entry.profileId();
            if (!this.labyMod$playerInfos.containsKey(profileId)) {
                NetworkPlayerInfo playerInfo = new VersionedNetworkPlayerInfo(this.D.get(profileId));
                this.labyMod$playerInfos.put(profileId, playerInfo);
                Laby.fireEvent(new PlayerInfoAddEvent(playerInfo));
            }
        }
        for (ClientboundPlayerInfoUpdatePacket.Entry entry2 : packet.entries()) {
            NetworkPlayerInfo playerInfo2 = this.labyMod$playerInfos.get(entry2.profileId());
            if (playerInfo2 != null) {
                for (ClientboundPlayerInfoUpdatePacket.Action action : packet.actions()) {
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
                        case 7:
                            updateType = PlayerInfoUpdateEvent.UpdateType.UPDATE_LIST_ORDER;
                            break;
                        case 8:
                            updateType = PlayerInfoUpdateEvent.UpdateType.UPDATE_HAT;
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

    /* JADX INFO: renamed from: net.labymod.v1_21_11.mixins.client.MixinClientPacketListener$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/MixinClientPacketListener$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$network$protocol$game$ClientboundPlayerInfoUpdatePacket$Action = new int[ClientboundPlayerInfoUpdatePacket.Action.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$network$protocol$game$ClientboundPlayerInfoUpdatePacket$Action[ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$network$protocol$game$ClientboundPlayerInfoUpdatePacket$Action[ClientboundPlayerInfoUpdatePacket.Action.INITIALIZE_CHAT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$network$protocol$game$ClientboundPlayerInfoUpdatePacket$Action[ClientboundPlayerInfoUpdatePacket.Action.UPDATE_LISTED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$minecraft$network$protocol$game$ClientboundPlayerInfoUpdatePacket$Action[ClientboundPlayerInfoUpdatePacket.Action.UPDATE_GAME_MODE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$net$minecraft$network$protocol$game$ClientboundPlayerInfoUpdatePacket$Action[ClientboundPlayerInfoUpdatePacket.Action.UPDATE_LATENCY.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$net$minecraft$network$protocol$game$ClientboundPlayerInfoUpdatePacket$Action[ClientboundPlayerInfoUpdatePacket.Action.UPDATE_DISPLAY_NAME.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$net$minecraft$network$protocol$game$ClientboundPlayerInfoUpdatePacket$Action[ClientboundPlayerInfoUpdatePacket.Action.UPDATE_LIST_ORDER.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$net$minecraft$network$protocol$game$ClientboundPlayerInfoUpdatePacket$Action[ClientboundPlayerInfoUpdatePacket.Action.UPDATE_HAT.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
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
    private void labyMod$handleClassicPvPBlocking(ClientboundSetEntityDataPacket packet, InsertInfo insertInfo) {
        int entityId = packet.id();
        Player entity = this.B.getEntity(entityId);
        if (!(entity instanceof Player)) {
            return;
        }
        Player player = entity;
        List<SynchedEntityData.DataValue<?>> unpackedData = packet.packedItems();
        if (unpackedData == null || unpackedData.isEmpty()) {
            return;
        }
        unpackedData.stream().filter(entry -> {
            return entry.id() == 8;
        }).forEach(entry2 -> {
            Object value = entry2.value();
            if (!(value instanceof Byte)) {
                return;
            }
            InteractionHand activeHand = player.getUsedItemHand();
            int state = ((Byte) value).intValue();
            SwordOldAnimation animation = LabyMod.getInstance().getOldAnimationRegistry().get("sword");
            if (animation == null) {
                return;
            }
            try {
                ItemStack heldItem = MinecraftUtil.fromMinecraft(player.getItemInHand(activeHand));
                if (heldItem.isShield() || heldItem.isSword()) {
                    animation.setBlockingState(player.getUUID(), state == 1 || state == 3);
                }
            } catch (IllegalArgumentException e) {
                animation.setBlockingState(player.getUUID(), state == 1 || state == 3);
            }
        });
    }

    @Inject(method = {"handlePlayerAbilities"}, at = {@At("RETURN")})
    private void labyMod$fireAbilityUpdateEvent(ClientboundPlayerAbilitiesPacket packet, CallbackInfo ci) {
        Laby.fireEvent(new ClientPlayerAbilitiesUpdateEvent(Minecraft.getInstance().player));
    }

    @Nullable
    public NetworkPlayerInfo getNetworkPlayerInfo(UUID uniqueId) {
        return this.labyMod$playerInfos.get(uniqueId);
    }

    @Nullable
    public NetworkPlayerInfo getNetworkPlayerInfo(String username) {
        for (NetworkPlayerInfo playerInfo : this.labyMod$playerInfos.values()) {
            if (username.equals(playerInfo.profile().getUsername())) {
                return playerInfo;
            }
        }
        return null;
    }

    public Collection<NetworkPlayerInfo> getNetworkPlayerInfos() {
        if (this.labyMod$unmodifiablePlayerInfos == null) {
            this.labyMod$unmodifiablePlayerInfos = Collections.unmodifiableCollection(this.labyMod$playerInfos.values());
        }
        return this.labyMod$unmodifiablePlayerInfos;
    }

    public Collection<NetworkPlayerInfo> getShownOnlinePlayers() {
        return this.labyMod$shownPlayers;
    }

    public void simulateKick(Component reason) {
        ThreadSafe.ensureRenderThread();
        Connection connection = m();
        if (connection != null) {
            connection.disconnect((net.minecraft.network.chat.Component) Laby.labyAPI().minecraft().componentMapper().toMinecraftComponent(reason));
        }
    }
}
