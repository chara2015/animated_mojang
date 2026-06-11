package net.labymod.core.client.gui.screen.activity.activities.labymod.child.player;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.entity.player.OptiFinePlayer;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FoldingWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.options.MainHand;
import net.labymod.api.client.options.MinecraftOptions;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.client.resources.texture.LabyTexture;
import net.labymod.api.client.resources.texture.Texture;
import net.labymod.api.client.session.MinecraftServices;
import net.labymod.api.client.session.Session;
import net.labymod.api.client.session.model.MojangTexture;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labynet.LabyNetController;
import net.labymod.api.labynet.models.textures.Skin;
import net.labymod.api.labynet.models.textures.TextureResult;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.mojang.texture.MojangTextureService;
import net.labymod.api.mojang.texture.MojangTextureType;
import net.labymod.api.thirdparty.optifine.OptiFine;
import net.labymod.api.util.I18n;
import net.labymod.api.util.Pair;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.PlayerActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.SettingLikeWidget;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.skin.LastUsedSkinsContainerWidget;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.skin.SkinLayersWidget;
import net.labymod.core.labyconnect.session.ApplyTextureController;
import net.labymod.core.labynet.DefaultLabyNetController;
import net.labymod.core.main.LabyMod;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labymod/child/player/SkinActivity.class */
@AutoActivity
@Link("activity/player/skin.lss")
public class SkinActivity extends PlayerActivity.Child {
    private static final String IDENTIFIER = "skin";
    private final LastUsedSkinsContainerWidget lastUsedSkinsWidget;
    private final DropdownWidget<MojangTextureEntry> capeDropdownWidget;
    private SkinBrowseActivity skinBrowseActivity;

    @Nullable
    private ResourceLocation pendingSkinTexture;

    @Nullable
    public MinecraftServices.SkinPayload pendingSkinPayload;
    private static final MojangTextureEntry LOADING_CAPE = new MojangTextureEntry(null, "Loading...");
    private static final ApplyTextureController APPLY_TEXTURE_CONTROLLER = LabyMod.references().applyTextureController();
    private static final long OPTIFINE_RELOAD_CAPE_DELAY = TimeUnit.SECONDS.toMillis(15);

    public SkinActivity(PlayerActivity playerActivity, String translationKeyPrefix) {
        super(playerActivity, translationKeyPrefix + "skin.", IDENTIFIER);
        this.lastUsedSkinsWidget = new LastUsedSkinsContainerWidget(this);
        ResourceLocation resourceLocation = Skin.LOADING;
        this.capeDropdownWidget = new DropdownWidget<>();
        this.capeDropdownWidget.setChangeListener(texture -> {
            if (texture == LOADING_CAPE) {
                return;
            }
            String id = texture == MojangTextureEntry.NONE ? null : texture.texture().getId().toString();
            APPLY_TEXTURE_CONTROLLER.setCapeAsync(id, response -> {
                MojangTexture activeCape = response.getActiveCape();
                MojangTextureService service = Laby.references().mojangTextureService();
                LabyConnectSession session = Laby.labyAPI().labyConnect().getSession();
                if (session != null) {
                    session.sendTextureUpdated(response);
                }
                ThreadSafe.executeOnRenderThread(() -> {
                    service.applyTexture(this.uniqueId, MojangTextureType.CAPE, activeCape == null ? null : activeCape.getUrl());
                });
            });
        });
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        this.pendingSkinTexture = null;
        this.pendingSkinPayload = null;
        DropdownWidget<SkinType> skinTypeDropdown = new DropdownWidget<>();
        VerticalListWidget<Widget> container = new VerticalListWidget<>();
        container.addId("list");
        HorizontalListWidget selectSkinContainer = new HorizontalListWidget();
        selectSkinContainer.addId("select-skin-container");
        ButtonWidget browseComputerButton = ButtonWidget.i18n(this.translationKeyPrefix + "browseLocalButton");
        skinTypeDropdown.setChangeListener(type -> {
            MinecraftServices.SkinVariant skinVariant = (type == null ? SkinType.AUTOMATIC : type).skinVariant;
            if (!this.playerActivity.modelWidget().isModified() && this.pendingSkinTexture == null && this.pendingSkinPayload == null && skinVariant != null) {
                MojangTextureService textureService = Laby.references().mojangTextureService();
                textureService.getTexture(Laby.labyAPI().getUniqueId(), MojangTextureType.SKIN, resourceLocation -> {
                    this.pendingSkinTexture = ResourceLocation.create(resourceLocation.getNamespace(), resourceLocation.getPath());
                    Texture texture = Laby.references().textureRepository().getTexture(this.pendingSkinTexture);
                    if (texture instanceof LabyTexture) {
                        LabyTexture labyTexture = (LabyTexture) texture;
                        this.pendingSkinPayload = new MinecraftServices.SkinPayload(labyTexture.getImage());
                    }
                });
            }
            if (this.pendingSkinTexture != null) {
                if (skinVariant != null) {
                    this.pendingSkinTexture.metadata().set("variant", skinVariant.getId());
                }
                this.playerActivity.setSkinTexture(new CompletableResourceLocation(this.pendingSkinTexture));
            }
        });
        browseComputerButton.setPressable(() -> {
            SkinType selected = (SkinType) skinTypeDropdown.getSelected();
            APPLY_TEXTURE_CONTROLLER.browseSkinFile(path -> {
                if (path == null) {
                    return;
                }
                try {
                    Pair<ResourceLocation, GameImage> skinLocation = APPLY_TEXTURE_CONTROLLER.loadSkinFile(path, (selected == null ? SkinType.AUTOMATIC : selected).skinVariant);
                    if (skinLocation == null) {
                        return;
                    }
                    this.labyAPI.minecraft().executeOnRenderThread(() -> {
                        this.pendingSkinTexture = (ResourceLocation) skinLocation.getFirst();
                        this.pendingSkinPayload = new MinecraftServices.SkinPayload((GameImage) skinLocation.getSecond());
                        this.playerActivity.setSkinTexture(new CompletableResourceLocation((ResourceLocation) skinLocation.getFirst()));
                    });
                } catch (IOException e) {
                    LOGGER.error("failed to load skin file: {}", e);
                }
            });
        });
        selectSkinContainer.addEntry(browseComputerButton);
        selectSkinContainer.addEntry(ComponentWidget.i18n(this.translationKeyPrefix + "browseButtonSplitter")).addId("button-splitter");
        ButtonWidget browseLabyButton = ButtonWidget.i18n(this.translationKeyPrefix + "browseOnlineButton");
        browseLabyButton.addId("accent-button");
        browseLabyButton.addId("browse-online-button");
        browseLabyButton.removeId("primary-button");
        browseLabyButton.setPressable(() -> {
            this.playerActivity.displayScreen((PlayerActivity.Child) skinBrowseActivity());
        });
        selectSkinContainer.addEntry(browseLabyButton);
        container.addChild(selectSkinContainer);
        String skinTypeTranslationKey = this.translationKeyPrefix + "settings.skinType.";
        skinTypeDropdown.addAll(SkinType.VALUES);
        skinTypeDropdown.setSelected(SkinType.AUTOMATIC);
        skinTypeDropdown.setTranslationKeyPrefix(skinTypeTranslationKey + "entries");
        container.addChild(new SettingLikeWidget(Component.translatable(skinTypeTranslationKey + "name", new Component[0]), skinTypeDropdown).addId("setting"));
        container.addChild(new SettingLikeWidget(Component.translatable(this.translationKeyPrefix + "settings.cape.name", new Component[0]), this.capeDropdownWidget).addId("setting"));
        if (MinecraftVersions.V1_9.orNewer()) {
            MinecraftOptions options = this.labyAPI.minecraft().options();
            String mainHandTranslationKey = this.translationKeyPrefix + "settings.mainHand.";
            DropdownWidget<MainHand> mainHandDropdown = new DropdownWidget<>();
            mainHandDropdown.addAll(MainHand.VALUES);
            mainHandDropdown.setSelected(options.mainHand());
            mainHandDropdown.setTranslationKeyPrefix(mainHandTranslationKey + "entries");
            mainHandDropdown.setChangeListener(mainHand -> {
                options.setMainHand(mainHand);
                options.sendOptionsToServer();
                options.save();
                this.playerActivity.modelWidget().update();
            });
            container.addChild(new SettingLikeWidget(Textures.SpriteCustomization.HAND, Component.translatable(mainHandTranslationKey + "name", new Component[0]), mainHandDropdown).addId("setting"));
        }
        if (OptiFine.isPresent()) {
            initializeOptiFineFeatures(container);
        }
        DivWidget displayNameWrapper = new DivWidget();
        displayNameWrapper.addId("display-name-wrapper");
        ComponentWidget displayName = ComponentWidget.i18n(this.translationKeyPrefix + "settings.layers.name");
        displayName.addId("display-name");
        displayNameWrapper.addChild(displayName);
        FoldingWidget foldingWidget = (FoldingWidget) new FoldingWidget(displayNameWrapper, new SkinLayersWidget(() -> {
            this.playerActivity.modelWidget().update();
        })).addId("setting");
        foldingWidget.addChild(new DivWidget().addId("status-indicator"));
        container.addChild(foldingWidget);
        container.addChild(this.lastUsedSkinsWidget);
        this.lastUsedSkinsWidget.setVisible(this.lastUsedSkinsWidget.hasValidTextures());
        ((Document) this.document).addChild(new ScrollWidget((VerticalListWidget<?>) container).addId("player-scroll"));
    }

    private void initializeOptiFineFeatures(VerticalListWidget<Widget> container) {
        Session session = this.labyAPI.minecraft().sessionAccessor().getSession();
        ButtonWidget capeEditorButton = ButtonWidget.i18n(this.translationKeyPrefix + "settings.ofCapeEditor.text");
        capeEditorButton.setEnabled(session != null && session.isPremium());
        capeEditorButton.setPressable(() -> {
            Session currentSession = this.labyAPI.minecraft().sessionAccessor().getSession();
            if (currentSession == null || !currentSession.isPremium()) {
                return;
            }
            capeEditorButton.setEnabled(false);
            Random r1 = new Random();
            Random r2 = new Random(System.identityHashCode(new Object()));
            BigInteger random1Bi = new BigInteger(128, r1);
            BigInteger random2Bi = new BigInteger(128, r2);
            BigInteger serverBi = random1Bi.xor(random2Bi);
            String serverId = serverBi.toString(16);
            Laby.references().minecraftAuthenticator().joinServer(currentSession, serverId).exceptionally(throwable -> {
                return false;
            }).thenAccept(valid -> {
                ThreadSafe.executeOnRenderThread(() -> {
                    openCapeEditor(session, serverId, capeEditorButton, valid.booleanValue());
                });
            });
        });
        container.addChild(new SettingLikeWidget(Component.translatable(this.translationKeyPrefix + "settings.ofCapeEditor.name", new Component[0]), capeEditorButton).addId("setting"));
        ButtonWidget reloadCapeButton = ButtonWidget.i18n(this.translationKeyPrefix + "settings.ofCapeReload.text");
        ClientPlayer player = Laby.labyAPI().minecraft().getClientPlayer();
        reloadCapeButton.setEnabled((player == null || session == null || !session.isPremium()) ? false : true);
        reloadCapeButton.setPressable(() -> {
            if (player instanceof OptiFinePlayer) {
                OptiFinePlayer optiFinePlayer = (OptiFinePlayer) player;
                optiFinePlayer.bridge$optifine$setReloadCapeTime(TimeUtil.getCurrentTimeMillis() + OPTIFINE_RELOAD_CAPE_DELAY);
            }
        });
        container.addChild(new SettingLikeWidget(Component.translatable(this.translationKeyPrefix + "settings.ofCapeReload.name", new Component[0]), reloadCapeButton).addId("setting"));
    }

    @Nullable
    public MinecraftServices.SkinVariant getSkinVariant() {
        String variant;
        if (this.pendingSkinTexture == null || (variant = (String) this.pendingSkinTexture.metadata().get("variant")) == null) {
            return null;
        }
        return MinecraftServices.SkinVariant.of(variant);
    }

    public void resetPending() {
        this.pendingSkinTexture = null;
        this.pendingSkinPayload = null;
        this.playerActivity.modelWidget().update();
    }

    @Override // net.labymod.core.client.gui.screen.activity.activities.labymod.child.PlayerActivity.Child
    protected void onSessionUpdate(PlayerActivity.UpdateContext context, UUID uniqueId) {
        LabyNetController labyNetController = Laby.references().labyNetController();
        ((DefaultLabyNetController) labyNetController).loadTexturesFromUser(TextureResult.Type.SKIN, uniqueId, result -> {
            if (!result.isPresent()) {
                this.lastUsedSkinsWidget.setVisible(false);
            } else {
                this.labyAPI.minecraft().executeOnRenderThread(() -> {
                    this.lastUsedSkinsWidget.setTextureResult((TextureResult) result.get());
                });
            }
        });
        this.capeDropdownWidget.clear();
        this.capeDropdownWidget.add(LOADING_CAPE);
        this.capeDropdownWidget.setSelected(LOADING_CAPE);
        APPLY_TEXTURE_CONTROLLER.getProfileAsync(response -> {
            if (response == null) {
                return;
            }
            this.capeDropdownWidget.clear();
            DropdownWidget<MojangTextureEntry> dropdownWidget = this.capeDropdownWidget;
            MojangTextureEntry mojangTextureEntry = MojangTextureEntry.NONE;
            MojangTextureEntry selected = mojangTextureEntry;
            dropdownWidget.add(mojangTextureEntry);
            MojangTexture[] capes = response.getCapes();
            if (capes != null) {
                for (MojangTexture cape : capes) {
                    if (cape != null) {
                        MojangTextureEntry entry = new MojangTextureEntry(cape);
                        this.capeDropdownWidget.add(entry);
                        if (cape.isActive()) {
                            selected = entry;
                        }
                    }
                }
            }
            this.capeDropdownWidget.setSelected(selected, false);
        });
    }

    private SkinBrowseActivity skinBrowseActivity() {
        if (this.skinBrowseActivity == null) {
            this.skinBrowseActivity = new SkinBrowseActivity(this);
        }
        return this.skinBrowseActivity;
    }

    private void openCapeEditor(Session session, String serverId, ButtonWidget capeEditorButton, boolean valid) {
        capeEditorButton.setEnabled(true);
        if (!valid) {
            return;
        }
        String username = session.getUsername();
        String uniqueId = session.getUniqueId().toString().replace("-", "");
        this.labyAPI.minecraft().chatExecutor().openUrl("https://optifine.net/capeChange?u=" + uniqueId + "&n=" + username + "&s=" + serverId);
    }

    public void setModelTexture(Skin skin) {
        this.playerActivity.setSkinTexture(skin);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labymod/child/player/SkinActivity$SkinType.class */
    public enum SkinType {
        AUTOMATIC(null),
        CLASSIC(MinecraftServices.SkinVariant.CLASSIC),
        SLIM(MinecraftServices.SkinVariant.SLIM);

        public static final SkinType[] VALUES = values();
        private final MinecraftServices.SkinVariant skinVariant;

        SkinType(MinecraftServices.SkinVariant skinVariant) {
            this.skinVariant = skinVariant;
        }

        public MinecraftServices.SkinVariant skinVariant() {
            return this.skinVariant;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labymod/child/player/SkinActivity$MojangTextureEntry.class */
    public static class MojangTextureEntry {
        public static final MojangTextureEntry NONE = new MojangTextureEntry(null, I18n.translate("labymod.activity.customization.textures.cape.noCape", new Object[0]));
        private final MojangTexture texture;
        private final String name;

        public MojangTextureEntry(MojangTexture texture) {
            this(texture, texture.getAlias());
        }

        public MojangTextureEntry(MojangTexture texture, String name) {
            this.texture = texture;
            this.name = name;
        }

        public MojangTexture texture() {
            return this.texture;
        }

        public String getName() {
            return this.name;
        }

        public String toString() {
            return getName();
        }
    }
}
