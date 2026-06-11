package net.labymod.core.main.serverapi;

import java.util.Map;
import java.util.UUID;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.network.server.payload.PayloadRegistry;
import net.labymod.api.models.Implements;
import net.labymod.api.serverapi.LabyModProtocolService;
import net.labymod.api.serverapi.PayloadTranslationRegistry;
import net.labymod.api.serverapi.TranslationProtocol;
import net.labymod.core.main.serverapi.legacy.LabyModPayloadBridge;
import net.labymod.core.main.serverapi.payload.LabyModPayloadChannelIdentifierSerializer;
import net.labymod.core.main.serverapi.protocol.neo.handler.game.discord.DiscordRPCPacketHandler;
import net.labymod.core.main.serverapi.protocol.neo.handler.game.display.EconomyDisplayPacketHandler;
import net.labymod.core.main.serverapi.protocol.neo.handler.game.display.SubtitlePacketHandler;
import net.labymod.core.main.serverapi.protocol.neo.handler.game.display.tablist.TabListBannerPacketHandler;
import net.labymod.core.main.serverapi.protocol.neo.handler.game.display.tablist.TabListFlagPacketHandler;
import net.labymod.core.main.serverapi.protocol.neo.handler.game.feature.AddMarkerPacketHandler;
import net.labymod.core.main.serverapi.protocol.neo.handler.game.feature.EmotePacketHandler;
import net.labymod.core.main.serverapi.protocol.neo.handler.game.feature.FeaturePacketHandler;
import net.labymod.core.main.serverapi.protocol.neo.handler.game.feature.InteractionMenuPacketHandler;
import net.labymod.core.main.serverapi.protocol.neo.handler.game.feature.MarkerPacketHandler;
import net.labymod.core.main.serverapi.protocol.neo.handler.game.feature.PlayingGameModePacketHandler;
import net.labymod.core.main.serverapi.protocol.neo.handler.game.feature.ServerBadgePacketHandler;
import net.labymod.core.main.serverapi.protocol.neo.handler.game.feature.ServerUserBadgePacketHandler;
import net.labymod.core.main.serverapi.protocol.neo.handler.game.feature.UpdateLabyModUserIndicatorVisibilityPacketHandler;
import net.labymod.core.main.serverapi.protocol.neo.handler.game.moderation.AddonDisablePacketHandler;
import net.labymod.core.main.serverapi.protocol.neo.handler.game.moderation.AddonRecommendationPacketHandler;
import net.labymod.core.main.serverapi.protocol.neo.handler.game.moderation.InstalledAddonsRequestPacketHandler;
import net.labymod.core.main.serverapi.protocol.neo.handler.game.moderation.PermissionPacketHandler;
import net.labymod.core.main.serverapi.protocol.neo.handler.game.supplement.InputPromptPacketHandler;
import net.labymod.core.main.serverapi.protocol.neo.handler.game.supplement.ServerSwitchPromptPacketHandler;
import net.labymod.core.main.serverapi.protocol.neo.handler.game.supplement.UpdateReadTimeoutPacketHandler;
import net.labymod.core.main.serverapi.protocol.neo.translation.game.discord.DiscordRPCTranslationListener;
import net.labymod.core.main.serverapi.protocol.neo.translation.game.display.EconomyTranslationListener;
import net.labymod.core.main.serverapi.protocol.neo.translation.game.display.SubtitleTranslationListener;
import net.labymod.core.main.serverapi.protocol.neo.translation.game.display.tablist.TablistLanguageFlagTranslationListener;
import net.labymod.core.main.serverapi.protocol.neo.translation.game.display.tablist.TablistServerBannerTranslationListener;
import net.labymod.core.main.serverapi.protocol.neo.translation.game.feature.EmoteApiTranslationListener;
import net.labymod.core.main.serverapi.protocol.neo.translation.game.feature.InteractionMenuApiTranslationListener;
import net.labymod.core.main.serverapi.protocol.neo.translation.game.feature.MarkerTranslationListener;
import net.labymod.core.main.serverapi.protocol.neo.translation.game.feature.PlayingGameModeTranslationListener;
import net.labymod.core.main.serverapi.protocol.neo.translation.game.moderation.AddonRecommendationTranslationListener;
import net.labymod.core.main.serverapi.protocol.neo.translation.game.moderation.PermissionTranslationListener;
import net.labymod.core.main.serverapi.protocol.neo.translation.game.supplement.InputPromptTranslationListener;
import net.labymod.core.main.serverapi.protocol.neo.translation.game.supplement.ServerSwitchTranslationListener;
import net.labymod.core.main.serverapi.protocol.neo.translation.login.LoginTranslationListener;
import net.labymod.serverapi.api.Protocol;
import net.labymod.serverapi.api.logger.ProtocolPlatformLogger;
import net.labymod.serverapi.api.model.component.ServerAPIComponent;
import net.labymod.serverapi.api.model.component.ServerAPITextColor;
import net.labymod.serverapi.api.model.component.ServerAPITextComponent;
import net.labymod.serverapi.api.model.component.ServerAPITextDecoration;
import net.labymod.serverapi.api.packet.Packet;
import net.labymod.serverapi.api.packet.PacketHandler;
import net.labymod.serverapi.api.payload.PayloadChannelIdentifier;
import net.labymod.serverapi.api.payload.io.PayloadReader;
import net.labymod.serverapi.api.payload.io.PayloadWriter;
import net.labymod.serverapi.core.packet.clientbound.game.display.EconomyDisplayPacket;
import net.labymod.serverapi.core.packet.clientbound.game.display.SubtitlePacket;
import net.labymod.serverapi.core.packet.clientbound.game.display.TabListBannerPacket;
import net.labymod.serverapi.core.packet.clientbound.game.display.TabListFlagPacket;
import net.labymod.serverapi.core.packet.clientbound.game.feature.DiscordRPCPacket;
import net.labymod.serverapi.core.packet.clientbound.game.feature.EmotePacket;
import net.labymod.serverapi.core.packet.clientbound.game.feature.InteractionMenuPacket;
import net.labymod.serverapi.core.packet.clientbound.game.feature.PlayingGameModePacket;
import net.labymod.serverapi.core.packet.clientbound.game.feature.ServerBadgePacket;
import net.labymod.serverapi.core.packet.clientbound.game.feature.ServerUserBadgePacket;
import net.labymod.serverapi.core.packet.clientbound.game.feature.UpdateFeaturePacket;
import net.labymod.serverapi.core.packet.clientbound.game.feature.UpdateLabyModUserIndicatorVisibilityPacket;
import net.labymod.serverapi.core.packet.clientbound.game.feature.marker.AddMarkerPacket;
import net.labymod.serverapi.core.packet.clientbound.game.feature.marker.MarkerPacket;
import net.labymod.serverapi.core.packet.clientbound.game.moderation.AddonDisablePacket;
import net.labymod.serverapi.core.packet.clientbound.game.moderation.AddonRecommendationPacket;
import net.labymod.serverapi.core.packet.clientbound.game.moderation.InstalledAddonsRequestPacket;
import net.labymod.serverapi.core.packet.clientbound.game.moderation.PermissionPacket;
import net.labymod.serverapi.core.packet.clientbound.game.supplement.InputPromptPacket;
import net.labymod.serverapi.core.packet.clientbound.game.supplement.ServerSwitchPromptPacket;
import net.labymod.serverapi.core.packet.clientbound.game.supplement.UpdateReadTimeoutPacket;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/serverapi/DefaultLabyModProtocolService.class */
@Singleton
@Implements(LabyModProtocolService.class)
public class DefaultLabyModProtocolService extends LabyModProtocolService {
    private final ProtocolPlatformLogger logger = new LabyModProtocolPlatformLogger();
    private final LabyModPayloadChannelIdentifierSerializer payloadChannelIdentifierSerializer = new LabyModPayloadChannelIdentifierSerializer();
    private final PayloadTranslationRegistry translationRegistry = new DefaultPayloadTranslationRegistry();

    public DefaultLabyModProtocolService() {
        initializeLabyModNeoProtocol();
        PayloadRegistry payloadRegistry = Laby.references().payloadRegistry();
        payloadRegistry.registerPayloadChannel(PayloadRegistry.MINECRAFT_REGISTER);
        payloadRegistry.registerPayloadChannel(PayloadRegistry.MINECRAFT_UNREGISTER);
        registry().addRegisterListener(protocol -> {
            Laby.references().payloadRegistry().registerPayloadChannel(protocol.identifier());
        });
    }

    public void send(@NotNull PayloadChannelIdentifier payloadChannelIdentifier, @NotNull UUID uuid, @NotNull PayloadWriter payloadWriter) {
        Laby.labyAPI().serverController().sendPayload(this.payloadChannelIdentifierSerializer.serialize(payloadChannelIdentifier), payloadWriter.toByteArray());
    }

    @NotNull
    public ProtocolPlatformLogger logger() {
        return this.logger;
    }

    @Override // net.labymod.api.serverapi.LabyModProtocolService
    @NotNull
    public PayloadTranslationRegistry translationRegistry() {
        return this.translationRegistry;
    }

    public boolean isInitialized() {
        return true;
    }

    private void initializeLabyModNeoProtocol() {
        registerHandler(DiscordRPCPacket.class, new DiscordRPCPacketHandler());
        registerHandler(SubtitlePacket.class, new SubtitlePacketHandler());
        registerHandler(TabListBannerPacket.class, new TabListBannerPacketHandler());
        registerHandler(TabListFlagPacket.class, new TabListFlagPacketHandler());
        registerHandler(EmotePacket.class, new EmotePacketHandler());
        registerHandler(InteractionMenuPacket.class, new InteractionMenuPacketHandler());
        registerHandler(PermissionPacket.class, new PermissionPacketHandler());
        registerHandler(MarkerPacket.class, new MarkerPacketHandler());
        registerHandler(AddMarkerPacket.class, new AddMarkerPacketHandler());
        registerHandler(InputPromptPacket.class, new InputPromptPacketHandler());
        registerHandler(UpdateReadTimeoutPacket.class, new UpdateReadTimeoutPacketHandler());
        registerHandler(ServerBadgePacket.class, new ServerBadgePacketHandler());
        registerHandler(ServerUserBadgePacket.class, new ServerUserBadgePacketHandler());
        registerHandler(UpdateLabyModUserIndicatorVisibilityPacket.class, new UpdateLabyModUserIndicatorVisibilityPacketHandler());
        registerHandler(UpdateFeaturePacket.class, new FeaturePacketHandler());
        registerHandler(ServerSwitchPromptPacket.class, new ServerSwitchPromptPacketHandler());
        registerHandler(PlayingGameModePacket.class, new PlayingGameModePacketHandler());
        registerHandler(AddonRecommendationPacket.class, new AddonRecommendationPacketHandler());
        registerHandler(EconomyDisplayPacket.class, new EconomyDisplayPacketHandler());
        registerHandler(AddonDisablePacket.class, new AddonDisablePacketHandler());
        registerHandler(InstalledAddonsRequestPacket.class, new InstalledAddonsRequestPacketHandler());
        TranslationProtocol protocol = new TranslationProtocol(PayloadChannelIdentifier.create("labymod3", "main"), this.labyModProtocol);
        this.translationRegistry.register(protocol);
        protocol.registerListener(new LoginTranslationListener());
        protocol.registerListener(new DiscordRPCTranslationListener());
        protocol.registerListener(new SubtitleTranslationListener());
        protocol.registerListener(new EmoteApiTranslationListener());
        protocol.registerListener(new InteractionMenuApiTranslationListener());
        protocol.registerListener(new MarkerTranslationListener());
        protocol.registerListener(new PlayingGameModeTranslationListener());
        protocol.registerListener(new AddonRecommendationTranslationListener());
        protocol.registerListener(new EconomyTranslationListener(this.labyModProtocol));
        protocol.registerListener(new TablistServerBannerTranslationListener());
        protocol.registerListener(new TablistLanguageFlagTranslationListener());
        protocol.registerListener(new PermissionTranslationListener());
        protocol.registerListener(new InputPromptTranslationListener());
        protocol.registerListener(new ServerSwitchTranslationListener());
    }

    private <T extends Packet> void registerHandler(@NotNull Class<T> packetClass, @NotNull PacketHandler<T> packetHandler) {
        this.labyModProtocol.registerHandler(packetClass, packetHandler);
    }

    public void afterPacketSent(@NotNull Protocol protocol, @NotNull Packet packet, @NotNull UUID recipient) {
        for (TranslationProtocol translationProtocol : this.translationRegistry.getProtocols()) {
            if (translationProtocol.targetProtocol().equals(protocol)) {
                translationProtocol.forEachListener(listener -> {
                    try {
                        PayloadWriter writer = listener.translateOutgoingPayload(packet);
                        if (writer != null) {
                            send(translationProtocol.identifier(), recipient, writer);
                            return true;
                        }
                        return false;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                });
            }
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.serverapi.LabyModProtocolService
    public Component mapComponent(ServerAPIComponent serverAPIComponent) throws MatchException {
        Component component;
        TextDecoration textDecoration;
        if (serverAPIComponent == null) {
            return null;
        }
        ServerAPITextColor color = serverAPIComponent.getColor();
        TextColor textColor = color == null ? null : TextColor.color(color.getValue());
        if (serverAPIComponent instanceof ServerAPITextComponent) {
            ServerAPITextComponent textComponent = (ServerAPITextComponent) serverAPIComponent;
            String text = textComponent.getText();
            component = text.isEmpty() ? Component.empty() : Component.text(text);
        } else {
            component = Component.empty();
        }
        if (textColor != null) {
            component = component.color(textColor);
        }
        for (Map.Entry<ServerAPITextDecoration, Boolean> entry : serverAPIComponent.getDecorations().entrySet()) {
            switch (AnonymousClass1.$SwitchMap$net$labymod$serverapi$api$model$component$ServerAPITextDecoration[entry.getKey().ordinal()]) {
                case 1:
                    textDecoration = TextDecoration.BOLD;
                    break;
                case 2:
                    textDecoration = TextDecoration.ITALIC;
                    break;
                case 3:
                    textDecoration = TextDecoration.UNDERLINED;
                    break;
                case 4:
                    textDecoration = TextDecoration.STRIKETHROUGH;
                    break;
                case 5:
                    textDecoration = TextDecoration.OBFUSCATED;
                    break;
                default:
                    throw new MatchException((String) null, (Throwable) null);
            }
            TextDecoration textDecoration2 = textDecoration;
            Boolean state = entry.getValue();
            if (state == null) {
                component.unsetDecoration(textDecoration2);
            } else if (state.booleanValue()) {
                component.decorate(textDecoration2);
            } else {
                component.undecorate(textDecoration2);
            }
        }
        for (ServerAPIComponent child : serverAPIComponent.getChildren()) {
            component.append(mapComponent(child));
        }
        return component;
    }

    /* JADX INFO: renamed from: net.labymod.core.main.serverapi.DefaultLabyModProtocolService$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/serverapi/DefaultLabyModProtocolService$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$labymod$serverapi$api$model$component$ServerAPITextDecoration = new int[ServerAPITextDecoration.values().length];

        static {
            try {
                $SwitchMap$net$labymod$serverapi$api$model$component$ServerAPITextDecoration[ServerAPITextDecoration.BOLD.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$labymod$serverapi$api$model$component$ServerAPITextDecoration[ServerAPITextDecoration.ITALIC.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$labymod$serverapi$api$model$component$ServerAPITextDecoration[ServerAPITextDecoration.UNDERLINED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$labymod$serverapi$api$model$component$ServerAPITextDecoration[ServerAPITextDecoration.STRIKETHROUGH.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$net$labymod$serverapi$api$model$component$ServerAPITextDecoration[ServerAPITextDecoration.OBFUSCATED.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    @Override // net.labymod.api.serverapi.LabyModProtocolService
    public boolean readPayload(PayloadChannelIdentifier identifier, byte[] payload) {
        boolean handled = false;
        for (Protocol protocol : registry().getProtocols()) {
            if (protocol.identifier().equals(identifier)) {
                try {
                    protocol.handleIncomingPayload(DUMMY_UUID, new PayloadReader(payload));
                    handled = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        PayloadReader reader = new PayloadReader(payload);
        for (TranslationProtocol protocol2 : this.translationRegistry.getProtocols()) {
            if (protocol2.identifier().equals(identifier)) {
                boolean translated = protocol2.forEachListener(listener -> {
                    try {
                        try {
                            Packet packet = listener.translateIncomingPayload(reader);
                            if (packet == null) {
                                reader.reset();
                                return false;
                            }
                            protocol2.targetProtocol().handlePacket(DUMMY_UUID, packet);
                            reader.reset();
                            return true;
                        } catch (Exception e2) {
                            e2.printStackTrace();
                            reader.reset();
                            return false;
                        }
                    } catch (Throwable th) {
                        reader.reset();
                        throw th;
                    }
                });
                if (translated) {
                    handled = true;
                }
            }
        }
        if (!handled) {
            return Laby.references().labyProtocolApi().getProtocolService().readCustomPayload(LabyModPayloadBridge.newToOldIdentifier(identifier), payload, true);
        }
        return handled;
    }
}
