package net.labymod.core.client.gui.screen.activity.activities.labymod.child;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.AnimatedIcon;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.Links;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.entry.FlexibleContentEntry;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.ScreenRendererWidget;
import net.labymod.api.client.resources.AnimatedResourceLocation;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.TextureRepository;
import net.labymod.api.client.session.MinecraftServices;
import net.labymod.api.client.session.Session;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.session.SessionUpdateEvent;
import net.labymod.api.labynet.models.textures.Skin;
import net.labymod.api.util.ColorUtil;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.concurrent.task.Task;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.CosmeticsActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.EmotesActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.SkinActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.listener.CosmeticsItemTextureListener;
import net.labymod.core.client.gui.screen.widget.widgets.customization.PlayerModelWidget;
import net.labymod.core.labyconnect.session.ApplyTextureController;
import net.labymod.core.labymodnet.event.LabyModNetRefreshEvent;
import net.labymod.core.main.LabyMod;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labymod/child/PlayerActivity.class */
@Links({@Link("activity/player/player.lss"), @Link("activity/player/player-child.lss")})
@AutoActivity
public class PlayerActivity extends Activity {
    public static final String TRANSLATION_KEY_PREFIX = "labymod.activity.playerCustomization.";
    private static final String MODEL_VISIBLE_KEY = "--model-visible";
    private static final String STATIC_BUTTON_ID = "static-button";
    private static final String MODEL_VISIBILITY_ID = "model-visibility-toggle";
    private static final long TIMEOUT = 300000;
    public static final Map<String, CompletableResourceLocation> SKIN_CACHE = new HashMap();
    private final PlayerModelWidget modelWidget;
    protected Task task;
    private FlexibleContentWidget containerWrapper;
    private FlexibleContentWidget headerWidget;
    private DivWidget modelWrapper;
    private DivWidget modelExtraContainer;
    private IconWidget loadingIconWidget;
    private boolean modelVisible;
    private UUID uniqueId;
    private final CosmeticsItemTextureListener itemTextureListener = new CosmeticsItemTextureListener();
    private int modelColor = ColorUtil.WHITE_ARGB;
    private final CosmeticsActivity cosmeticsActivity = new CosmeticsActivity(this, TRANSLATION_KEY_PREFIX);
    private final EmotesActivity emotesActivity = new EmotesActivity(this, TRANSLATION_KEY_PREFIX);
    private final SkinActivity skinActivity = new SkinActivity(this, TRANSLATION_KEY_PREFIX);
    private final ScreenRendererWidget screenRenderer = new ScreenRendererWidget();

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labymod/child/PlayerActivity$UpdateContext.class */
    public enum UpdateContext {
        INITIALIZE,
        EVENT
    }

    public PlayerActivity() {
        this.screenRenderer.addId("screen-renderer");
        this.screenRenderer.displayScreen(this.cosmeticsActivity);
        this.screenRenderer.addPostDisplayListener(screen -> {
            refreshHeaderButtons();
        });
        this.modelVisible = true;
        this.modelWidget = new PlayerModelWidget();
        this.uniqueId = this.modelWidget.getPlayer().getUniqueId();
        this.task = Task.builder(() -> {
            this.modelColor = ColorFormat.ARGB32.pack(25, 25, 25, 255);
            this.loadingIconWidget.setVisible(true);
        }).delay(200L, TimeUnit.MILLISECONDS).build();
    }

    public void displayChild(Class<? extends Child> childClass) {
        if (childClass == CosmeticsActivity.class) {
            displayChild(this.cosmeticsActivity);
        } else if (childClass == EmotesActivity.class) {
            displayChild(this.emotesActivity);
        } else if (childClass == SkinActivity.class) {
            displayChild(this.skinActivity);
        }
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        if (this.uniqueId != this.labyAPI.getUniqueId()) {
            this.uniqueId = this.labyAPI.getUniqueId();
            setSkinTexture(this.uniqueId);
            this.modelWidget.updatePlayer(this.uniqueId);
        }
        this.containerWrapper = new FlexibleContentWidget();
        this.containerWrapper.addId("container-wrapper");
        FlexibleContentWidget container = new FlexibleContentWidget();
        container.addId("container");
        this.headerWidget = new FlexibleContentWidget();
        this.headerWidget.addId("header");
        this.headerWidget.addFlexibleContent(createButton(this.cosmeticsActivity));
        this.headerWidget.addFlexibleContent(createButton(this.emotesActivity));
        this.headerWidget.addFlexibleContent(createButton(this.skinActivity));
        ButtonWidget refreshLabyMod = ButtonWidget.icon(Textures.SpriteCommon.REFRESH);
        refreshLabyMod.addId("refresh-labymod", STATIC_BUTTON_ID);
        refreshLabyMod.setHoverComponent(Component.translatable("labymod.activity.playerCustomization.refreshLabyMod.description", new Component[0]));
        refreshLabyMod.setPressable(() -> {
            Child activeChild = getActiveChild();
            if (activeChild != null) {
                activeChild.onLabyModRefresh(UpdateContext.INITIALIZE);
            }
            refreshLabyMod.setEnabled(false);
            Laby.labyAPI().refresh();
        });
        this.headerWidget.addContent(refreshLabyMod);
        refreshHeaderButtons();
        container.addContent(this.headerWidget);
        container.addFlexibleContent(this.screenRenderer);
        this.modelWrapper = new DivWidget();
        this.modelWrapper.addId("model-wrapper");
        setModelVisible(this.modelVisible);
        setSkinApplyable(this.modelWidget.isModified());
        this.modelWidget.addId("model");
        this.modelWidget.draggable().set(true);
        this.modelWrapper.addChild(this.modelWidget);
        this.modelExtraContainer = new DivWidget();
        this.modelExtraContainer.addId("model-extra-container");
        this.modelWrapper.addChild(this.modelExtraContainer);
        DivWidget loadingContainer = new DivWidget();
        loadingContainer.addId("model-loading-container");
        this.modelWrapper.addChild(loadingContainer);
        loadingContainer.addChild(createLoadingIcon());
        this.containerWrapper.addContent(this.modelWrapper);
        this.containerWrapper.addFlexibleContent(container);
        DivWidget stencilWrapper = new DivWidget();
        stencilWrapper.addId("container-stencil-wrapper");
        stencilWrapper.addChild(this.containerWrapper);
        ((Document) this.document).addChild(stencilWrapper);
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.ScreenInstance
    public void render(ScreenContext context) {
        if (this.modelWidget != null) {
            this.modelWidget.modelColor().set(Integer.valueOf(this.modelColor));
        }
        super.render(context);
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.ScreenInstance
    public void onCloseScreen() {
        super.onCloseScreen();
        TextureRepository textureRepository = Laby.references().textureRepository();
        SKIN_CACHE.forEach((uuid, skin) -> {
            ResourceLocation completed = skin.getCompleted();
            if (completed != null) {
                textureRepository.releaseTexture(completed);
            }
        });
        SKIN_CACHE.clear();
    }

    public void displayScreen(Child child) {
        this.screenRenderer.displayScreen(child);
    }

    public PlayerModelWidget modelWidget() {
        return this.modelWidget;
    }

    private ButtonWidget createButton(Child child) {
        ButtonWidget buttonWidget = ButtonWidget.i18n(child.translationKeyPrefix + "name");
        buttonWidget.addId(child.groupIdentifier);
        buttonWidget.setPressable(() -> {
            displayChild(child);
        });
        return buttonWidget;
    }

    private void setModelVisible(boolean visible) {
        this.modelVisible = visible;
        if (this.modelWrapper != null) {
            this.modelWrapper.setVariable(MODEL_VISIBLE_KEY, visible);
        }
        if (this.containerWrapper != null) {
            this.containerWrapper.updateBounds();
        }
    }

    private void setSkinApplyable(boolean applyable) {
        if (this.modelExtraContainer == null) {
            return;
        }
        this.modelExtraContainer.removeChildIf(widget -> {
            return widget.hasId("apply-skin-button");
        });
        if (!applyable) {
            return;
        }
        String translationKeyPrefix = this.skinActivity.translationKeyPrefix + "apply.";
        ButtonWidget applySkinButton = ButtonWidget.i18n(translationKeyPrefix + "upload");
        applySkinButton.addId("apply-skin-button");
        if (!this.labyAPI.minecraft().sessionAccessor().isPremium()) {
            applySkinButton.setEnabled(false);
            applySkinButton.setHoverComponent(Component.translatable(translationKeyPrefix + "noPremium", new Component[0]));
        }
        applySkinButton.setPressable(() -> {
            MinecraftServices.SkinPayload payload;
            MinecraftServices.SkinVariant variant;
            if (!this.modelWidget.isModified()) {
                return;
            }
            applySkinButton.setEnabled(false);
            applySkinButton.updateComponent(Component.translatable(translationKeyPrefix + "uploading", new Component[0]));
            Skin previewedSkin = this.modelWidget.getPreviewedSkin();
            if (this.skinActivity.pendingSkinPayload != null) {
                payload = this.skinActivity.pendingSkinPayload;
                variant = this.skinActivity.getSkinVariant();
                if (variant == null) {
                    return;
                }
            } else {
                payload = new MinecraftServices.SkinPayload(previewedSkin.getDownloadUrl());
                variant = previewedSkin.skinVariant();
            }
            ApplyTextureController applyTextureController = LabyMod.references().applyTextureController();
            applyTextureController.uploadSkinAsync(variant, payload, result -> {
                ThreadSafe.executeOnRenderThread(() -> {
                    applySkinButton.updateComponent(Component.translatable(translationKeyPrefix + "uploading" + (result.isPresent() ? "Success" : "Failed"), new Component[0]));
                });
            });
            this.skinActivity.resetPending();
        });
        this.modelExtraContainer.addChildInitialized(applySkinButton);
    }

    public DivWidget getModelExtraContainer() {
        return this.modelExtraContainer;
    }

    public void addWidgetToModelWrapper(Widget widget) {
        this.modelExtraContainer.addChildInitialized(widget);
    }

    private void refreshHeaderButtons() {
        Child activeChild = getActiveChild();
        if (activeChild == null || (!activeChild.allowModifiedModel() && this.modelWidget.isModified())) {
            setSkinTexture(this.uniqueId);
        }
        if (activeChild != null && !this.uniqueId.equals(activeChild.uniqueId)) {
            activeChild.onSessionUpdate(UpdateContext.INITIALIZE, this.uniqueId);
            activeChild.uniqueId = this.uniqueId;
        }
        if (this.modelExtraContainer != null) {
            Predicate<Widget> predicate = activeChild == null ? widget -> {
                return true;
            } : activeChild.getModelContainerClearPredicate();
            this.modelExtraContainer.removeChildIf(predicate);
        }
        if (this.headerWidget == null) {
            return;
        }
        ScreenInstance screen = this.screenRenderer.getScreen();
        if (!(screen instanceof Child)) {
            return;
        }
        String childIdentifier = ((Child) screen).groupIdentifier;
        for (FlexibleContentEntry child : this.headerWidget.getChildren()) {
            Widget widget2 = child.childWidget();
            if (widget2 instanceof ButtonWidget) {
                ButtonWidget buttonWidget = (ButtonWidget) widget2;
                if (!widget2.hasId(STATIC_BUTTON_ID)) {
                    boolean active = widget2.hasId(childIdentifier);
                    buttonWidget.setEnabled(!active);
                    buttonWidget.setActive(active);
                }
            }
        }
    }

    @Subscribe
    public void onSessionUpdate(SessionUpdateEvent event) {
        Session session = event.newSession();
        if (session == null || !session.hasUniqueId()) {
            return;
        }
        UUID uniqueId = session.getUniqueId();
        if (uniqueId != this.uniqueId) {
            this.uniqueId = uniqueId;
            setSkinTexture(uniqueId);
            this.modelWidget.updatePlayer(uniqueId);
        }
        Child activeChild = getActiveChild();
        if (activeChild != null && uniqueId != activeChild.uniqueId) {
            activeChild.uniqueId = uniqueId;
            activeChild.onSessionUpdate(UpdateContext.EVENT, uniqueId);
        }
    }

    @Subscribe
    public void onLabyModRefresh(LabyModNetRefreshEvent event) {
        FlexibleContentEntry refreshLabyModButton;
        if (this.headerWidget != null && (refreshLabyModButton = (FlexibleContentEntry) this.headerWidget.findFirstChildIf(entry -> {
            return entry.hasId("refresh-labymod");
        })) != null) {
            Widget widgetChildWidget = refreshLabyModButton.childWidget();
            if (!(widgetChildWidget instanceof ButtonWidget)) {
                return;
            }
            ButtonWidget widget = (ButtonWidget) widgetChildWidget;
            widget.setEnabled(false);
            Task.builder(() -> {
                widget.setEnabled(true);
            }).delay(5L, TimeUnit.SECONDS).build().executeOnRenderThread();
        }
    }

    public Child getActiveChild() {
        ScreenInstance screen = this.screenRenderer.getScreen();
        if (!(screen instanceof Child)) {
            return null;
        }
        return (Child) screen;
    }

    public void setSkinTexture(CompletableResourceLocation location) {
        this.modelWidget.updateSkinTexture(location);
        setSkinApplyable(true);
    }

    public void setSkinTexture(Skin skin) {
        this.modelWidget.updateSkinTexture(skin);
        setSkinApplyable(this.modelWidget.isModified());
    }

    public void setSkinTexture(UUID uniqueId) {
        this.modelWidget.updateSkinTextureFrom(uniqueId);
        setSkinApplyable(this.modelWidget.isModified());
    }

    private void displayChild(Child child) {
        if (this.screenRenderer.getScreen() != child) {
            this.screenRenderer.displayScreen(child);
        }
    }

    private IconWidget createLoadingIcon() {
        AnimatedResourceLocation.Builder builder = this.labyAPI.renderPipeline().resources().resourceLocationFactory().builder();
        AnimatedResourceLocation spinnerLocation = builder.resourceLocations("labymod", "textures/spinner/spinner", 30).delay(33L).build();
        this.loadingIconWidget = new IconWidget(AnimatedIcon.of(spinnerLocation));
        this.loadingIconWidget.addId("model-loading-icon");
        this.loadingIconWidget.setVisible(false);
        return this.loadingIconWidget;
    }

    public CosmeticsItemTextureListener itemTextureListener() {
        return this.itemTextureListener;
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.LabyScreen, net.labymod.api.client.gui.Interactable
    public void tick() {
        super.tick();
        boolean timeout = this.itemTextureListener.isTimeout(TIMEOUT);
        if (timeout) {
            resetLoading();
            return;
        }
        CosmeticsItemTextureListener.State state = this.itemTextureListener.state();
        if (state == CosmeticsItemTextureListener.State.BEGIN) {
            this.task.executeOnRenderThread();
        } else {
            resetLoading();
        }
    }

    private void resetLoading() {
        this.modelColor = ColorUtil.WHITE_ARGB;
        if (this.loadingIconWidget != null) {
            this.loadingIconWidget.setVisible(false);
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labymod/child/PlayerActivity$Child.class */
    @Link("activity/player/player-child.lss")
    public static abstract class Child extends Activity {
        protected final PlayerActivity playerActivity;
        protected final String translationKeyPrefix;
        protected final String groupIdentifier;
        protected UUID uniqueId;

        protected Child(PlayerActivity playerActivity, String translationKeyPrefix, String groupIdentifier) {
            this.playerActivity = playerActivity;
            this.translationKeyPrefix = translationKeyPrefix;
            this.groupIdentifier = groupIdentifier;
            ((Document) this.document).setLazy(true);
        }

        protected boolean allowModifiedModel() {
            return false;
        }

        protected void onSessionUpdate(UpdateContext context, UUID uniqueId) {
        }

        protected void onLabyModRefresh(UpdateContext context) {
        }

        protected Predicate<Widget> getModelContainerClearPredicate() {
            return widget -> {
                return true;
            };
        }

        public PlayerActivity playerActivity() {
            return this.playerActivity;
        }

        public String getGroupIdentifier() {
            return this.groupIdentifier;
        }

        public String getTranslationKeyPrefix() {
            return this.translationKeyPrefix;
        }
    }
}
