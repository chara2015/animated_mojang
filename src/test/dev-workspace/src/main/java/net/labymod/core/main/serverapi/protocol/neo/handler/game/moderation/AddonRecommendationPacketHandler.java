package net.labymod.core.main.serverapi.protocol.neo.handler.game.moderation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.Links;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.CheckBoxWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.popup.SimpleAdvancedPopup;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.configuration.labymod.main.laby.OtherConfig;
import net.labymod.core.addon.DefaultAddonService;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.addons.AddonProfileActivity;
import net.labymod.core.client.gui.screen.widget.widgets.store.StoreItemWidget;
import net.labymod.core.configuration.labymod.LabyConfigProvider;
import net.labymod.core.flint.index.FlintIndex;
import net.labymod.core.flint.marketplace.FlintModification;
import net.labymod.core.main.LabyMod;
import net.labymod.serverapi.api.packet.Packet;
import net.labymod.serverapi.api.packet.PacketHandler;
import net.labymod.serverapi.core.model.moderation.RecommendedAddon;
import net.labymod.serverapi.core.packet.clientbound.game.moderation.AddonRecommendationPacket;
import net.labymod.serverapi.core.packet.serverbound.game.moderation.AddonRecommendationResponsePacket;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/serverapi/protocol/neo/handler/game/moderation/AddonRecommendationPacketHandler.class */
public class AddonRecommendationPacketHandler implements PacketHandler<AddonRecommendationPacket> {

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/serverapi/protocol/neo/handler/game/moderation/AddonRecommendationPacketHandler$AddonRecommendationCloseListener.class */
    public interface AddonRecommendationCloseListener {
        void onClose(boolean z, AddonRecommendationIgnoreState addonRecommendationIgnoreState);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/serverapi/protocol/neo/handler/game/moderation/AddonRecommendationPacketHandler$AddonRecommendationIgnoreState.class */
    public enum AddonRecommendationIgnoreState {
        UNSET,
        IGNORED,
        NOT_IGNORED
    }

    public void handle(@NotNull UUID uuid, @NotNull AddonRecommendationPacket packet) {
        String configKey;
        boolean showScreen;
        if (packet.getAddons().isEmpty()) {
            return;
        }
        LabyAPI labyAPI = Laby.labyAPI();
        ServerData currentServerData = labyAPI.serverController().getCurrentServerData();
        if (currentServerData == null) {
            return;
        }
        OtherConfig otherConfig = LabyConfigProvider.INSTANCE.get().other();
        List<String> strings = otherConfig.ignoredAddonRecommendations().get();
        AddonRecommendationResponsePacket responsePacket = sendResponse(packet, true);
        if (responsePacket.isAllInstalled()) {
            showScreen = false;
            configKey = null;
        } else {
            configKey = generateKeyFromServer(currentServerData, packet);
            showScreen = !strings.contains(configKey);
            if (!showScreen) {
                for (String missingAddon : responsePacket.getMissingAddons()) {
                    Iterator it = packet.getAddons().iterator();
                    while (true) {
                        if (it.hasNext()) {
                            RecommendedAddon addon = (RecommendedAddon) it.next();
                            if (addon.isRequired() && addon.getNamespace().equals(missingAddon)) {
                                showScreen = true;
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (!showScreen || configKey == null) {
            Laby.references().labyModProtocolService().sendLabyModPacket(new AddonRecommendationResponsePacket(packet, false, responsePacket.isAllInstalled(), responsePacket.getMissingAddons(), responsePacket.getInstalledAddons()));
        } else {
            String str = configKey;
            new AddonRecommendationPopup(packet, currentServerData, strings.contains(configKey), (canceled, ignoreState) -> {
                if (!canceled && ignoreState != AddonRecommendationIgnoreState.UNSET) {
                    if (ignoreState == AddonRecommendationIgnoreState.IGNORED) {
                        if (!strings.contains(str)) {
                            strings.add(str);
                        }
                    } else {
                        strings.remove(str);
                    }
                    otherConfig.ignoredAddonRecommendations().set(strings);
                    LabyConfigProvider.INSTANCE.save();
                }
                sendResponse(packet, false);
            }).displayAsActivity();
        }
    }

    private String generateKeyFromServer(ServerData serverData, AddonRecommendationPacket packet) {
        StringBuilder addonString = new StringBuilder();
        for (RecommendedAddon addon : packet.getAddons()) {
            if (!addonString.isEmpty()) {
                addonString.append('#');
            }
            if (addon.isRequired()) {
                addonString.append('!');
            }
            addonString.append(addon.getNamespace());
        }
        return serverData.actualAddress().toString() + "#" + String.valueOf(addonString);
    }

    private AddonRecommendationResponsePacket sendResponse(AddonRecommendationPacket packet, boolean initial) {
        DefaultAddonService instance = DefaultAddonService.getInstance();
        List<String> installedAddons = new ArrayList<>();
        List<String> missingAddons = new ArrayList<>();
        for (RecommendedAddon addon : packet.getAddons()) {
            if (instance.getOptionalAddon(addon.getNamespace()).isPresent()) {
                installedAddons.add(addon.getNamespace());
            } else {
                missingAddons.add(addon.getNamespace());
            }
        }
        Packet addonRecommendationResponsePacket = new AddonRecommendationResponsePacket(packet, initial, missingAddons.isEmpty(), missingAddons, installedAddons);
        Laby.references().labyModProtocolService().sendLabyModPacket(addonRecommendationResponsePacket);
        return addonRecommendationResponsePacket;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/serverapi/protocol/neo/handler/game/moderation/AddonRecommendationPacketHandler$AddonRecommendationPopup.class */
    @Links({@Link("activity/overlay/addon-recommendation.lss"), @Link("activity/flint/addon-item.lss")})
    public static class AddonRecommendationPopup extends SimpleAdvancedPopup {
        private static final String TRANSLATION_PREFIX = "labymod.activity.addonRecommendation.";
        private final AddonRecommendationPacket packet;
        private final ServerData serverData;
        private final AddonRecommendationCloseListener closeListener;
        private final SimpleAdvancedPopup.SimplePopupButton doneButton;
        private final boolean hasRequired;
        private final boolean hasRecommended;
        private AddonRecommendationIgnoreState ignoreState;
        private boolean calledCloseListener = false;

        public AddonRecommendationPopup(AddonRecommendationPacket packet, ServerData serverData, boolean ignored, AddonRecommendationCloseListener closeListener) {
            String translationKey;
            this.packet = packet;
            this.serverData = serverData;
            this.closeListener = closeListener;
            boolean hasRequired = false;
            boolean hasRecommended = false;
            for (RecommendedAddon addon : packet.getAddons()) {
                if (addon.isRequired()) {
                    hasRequired = true;
                } else {
                    hasRecommended = true;
                }
            }
            this.hasRequired = hasRequired;
            this.hasRecommended = hasRecommended;
            if (!this.hasRecommended) {
                this.ignoreState = AddonRecommendationIgnoreState.UNSET;
            } else {
                this.ignoreState = ignored ? AddonRecommendationIgnoreState.IGNORED : AddonRecommendationIgnoreState.NOT_IGNORED;
            }
            if (this.hasRequired && this.hasRecommended) {
                translationKey = "title";
            } else if (this.hasRequired) {
                translationKey = "titleRequired";
            } else {
                translationKey = "titleRecommend";
            }
            this.title = Component.translatable("labymod.activity.addonRecommendation." + translationKey, NamedTextColor.GREEN, Component.text(this.serverData.address().toString(), NamedTextColor.YELLOW));
            this.buttons = new ArrayList();
            this.buttons.add(SimpleAdvancedPopup.SimplePopupButton.cancel());
            this.doneButton = SimpleAdvancedPopup.SimplePopupButton.create("done", Component.translatable("labymod.ui.button.done", new Component[0]), button -> {
                if (!this.calledCloseListener) {
                    this.calledCloseListener = true;
                    this.closeListener.onClose(false, this.ignoreState);
                }
            }).enabled(allRequiredInstalled());
            this.buttons.add(this.doneButton);
        }

        @Override // net.labymod.api.client.gui.screen.widget.widgets.popup.AdvancedPopup
        protected void onClose() {
            super.onClose();
            if (this.calledCloseListener) {
                return;
            }
            this.calledCloseListener = true;
            this.closeListener.onClose(true, this.ignoreState);
        }

        @Override // net.labymod.api.client.gui.screen.widget.widgets.popup.AdvancedPopup
        protected void tick() {
            super.tick();
            if (this.doneButton == null) {
                return;
            }
            this.doneButton.enabled(allRequiredInstalled());
        }

        private boolean allRequiredInstalled() {
            DefaultAddonService instance = DefaultAddonService.getInstance();
            for (RecommendedAddon addon : this.packet.getAddons()) {
                if (addon.isRequired() && instance.getOptionalAddon(addon.getNamespace()).isEmpty()) {
                    return false;
                }
            }
            return true;
        }

        @Override // net.labymod.api.client.gui.screen.widget.widgets.popup.SimpleAdvancedPopup
        protected void initializeCustomWidgets(VerticalListWidget<Widget> container) {
            CheckBoxWidget.State state;
            super.initializeCustomWidgets(container);
            VerticalListWidget<Widget> addonList = new VerticalListWidget<>();
            addonList.addId("addon-list");
            FlintIndex.IndexFilter filter = LabyMod.references().flintController().getFlintIndex().filter();
            for (RecommendedAddon addon : this.packet.getAddons()) {
                Optional<FlintModification> optionalModification = filter.namespace(addon.getNamespace());
                if (!optionalModification.isEmpty()) {
                    addonList.addChild(new AddonRecommendationItemWidget(optionalModification.get(), addon.isRequired()));
                }
            }
            container.addChild(new ScrollWidget((VerticalListWidget<?>) addonList));
            if (this.hasRecommended) {
                FlexibleContentWidget ignoreContainer = new FlexibleContentWidget();
                ignoreContainer.addId("ignore-container");
                ComponentWidget ignoreComponentwidget = ComponentWidget.i18n("labymod.activity.addonRecommendation.ignore", NamedTextColor.GRAY, this.serverData.address().toString());
                ignoreContainer.addFlexibleContent(ignoreComponentwidget);
                CheckBoxWidget ignoreWidget = new CheckBoxWidget();
                if (this.ignoreState == AddonRecommendationIgnoreState.IGNORED) {
                    state = CheckBoxWidget.State.CHECKED;
                } else {
                    state = CheckBoxWidget.State.UNCHECKED;
                }
                ignoreWidget.setState(state);
                ignoreWidget.setPressable(() -> {
                    AddonRecommendationIgnoreState addonRecommendationIgnoreState;
                    if (ignoreWidget.state() == CheckBoxWidget.State.CHECKED) {
                        addonRecommendationIgnoreState = AddonRecommendationIgnoreState.IGNORED;
                    } else {
                        addonRecommendationIgnoreState = AddonRecommendationIgnoreState.NOT_IGNORED;
                    }
                    this.ignoreState = addonRecommendationIgnoreState;
                });
                ignoreContainer.addContent(ignoreWidget);
                container.addChild(ignoreContainer);
            }
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/serverapi/protocol/neo/handler/game/moderation/AddonRecommendationPacketHandler$AddonRecommendationItemWidget.class */
    @AutoWidget
    public static class AddonRecommendationItemWidget extends StoreItemWidget {
        private final AddonProfileActivity.AddonInstallController installController;
        private final boolean required;

        public AddonRecommendationItemWidget(FlintModification modification, boolean required) {
            super(modification, null);
            this.required = required;
            this.installController = new AddonProfileActivity.AddonInstallController(modification, this::reInitialize);
            addId("addon-item");
        }

        @Override // net.labymod.core.client.gui.screen.widget.widgets.store.StoreItemWidget
        protected void initializeStoreItem() {
            ButtonWidget installButton;
            super.initializeStoreItem();
            DivWidget requiredWrapper = new DivWidget();
            requiredWrapper.addId("required-wrapper");
            if (!modification().isInstalled()) {
                requiredWrapper.addId("not-installed");
            }
            if (this.required) {
                IconWidget requiredBadge = new IconWidget(Textures.SpriteCommon.EXCLAMATION_MARK_LIGHT);
                requiredBadge.addId("required-badge");
                requiredBadge.setHoverComponent(Component.translatable("labymod.activity.addonRecommendation.required", new Component[0]));
                requiredWrapper.addChild(requiredBadge);
            }
            addChild(requiredWrapper);
            if (modification().isInstalled()) {
                return;
            }
            if (isIncompatible()) {
                this.installController.unsetInstallButton();
                installButton = ButtonWidget.text("Install");
                installButton.addId("install-button");
                if (isIncompatible()) {
                    installButton.setEnabled(false);
                    installButton.setHoverComponent(getIncompatibleComponent());
                }
            } else {
                installButton = this.installController.createInstallButton();
            }
            addChild(installButton);
        }

        @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.Interactable
        public void tick() {
            super.tick();
            this.installController.tick();
        }
    }
}
