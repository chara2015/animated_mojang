package net.labymod.core.client.gui.screen.activity.activities.labymod.child.addons;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Consumer;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.activities.ConfirmActivity;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.action.Pressable;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.PopupWidget;
import net.labymod.api.client.gui.screen.widget.widgets.RatingWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.popup.SimpleAdvancedPopup;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.VariableIconWidget;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.configuration.converter.LegacyConfigConverter;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.localization.Internationalization;
import net.labymod.api.models.addon.info.AddonMeta;
import net.labymod.api.notification.Notification;
import net.labymod.api.util.CollectionHelper;
import net.labymod.api.util.TextFormat;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.math.MathHelper;
import net.labymod.core.addon.DefaultAddonService;
import net.labymod.core.client.gui.screen.widget.widgets.store.GradientWidget;
import net.labymod.core.client.gui.screen.widget.widgets.store.StoreItemWidget;
import net.labymod.core.client.gui.screen.widget.widgets.store.profile.ProfileInfoWidget;
import net.labymod.core.flint.FlintController;
import net.labymod.core.flint.downloader.DownloadState;
import net.labymod.core.flint.downloader.FlintDownloadTask;
import net.labymod.core.flint.downloader.FlintDownloader;
import net.labymod.core.flint.index.FlintIndex;
import net.labymod.core.flint.marketplace.FlintModification;
import net.labymod.core.main.LabyMod;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labymod/child/addons/AddonProfileActivity.class */
@Link("activity/flint/profile.lss")
@AutoActivity
public class AddonProfileActivity extends Activity {
    private final FlintController controller;
    private final Activity fallback;
    private final AddonInstallController installController;
    private final ProfileInfoWidget infoWidget;
    private final boolean hasUnsupportedDependencies;
    private FlintModification modification;
    private boolean loadingModification;
    private ScrollWidget scrollWidget;
    private final Internationalization internationalization = Laby.references().internationalization();
    private final String undefined = this.internationalization.getRawTranslation("labymod.misc.undefined");

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labymod/child/addons/AddonProfileActivity$DependencyPopupAction.class */
    public enum DependencyPopupAction {
        INSTALL,
        SKIP
    }

    public AddonProfileActivity(Activity fallback, FlintController flintController, FlintModification modification) {
        this.fallback = fallback;
        this.modification = modification;
        this.installController = new AddonInstallController(modification, this::reload);
        this.controller = flintController;
        this.hasUnsupportedDependencies = StoreItemWidget.hasUnsupportedDependencies(modification);
        this.infoWidget = (ProfileInfoWidget) new ProfileInfoWidget(modification).addId("profile-info-container");
    }

    public static void collectUninstalledDependencies(FlintModification modification, List<FlintModification> dependencies, boolean optional) {
        modification.forEachAddonDependency(Laby.labyAPI().labyModLoader().version(), dependency -> {
            if (dependency.isOptional() != optional) {
                return;
            }
            String namespace = dependency.getNamespace();
            if (CollectionHelper.anyMatch(dependencies, mod -> {
                return mod.getNamespace().equals(namespace);
            }) || DefaultAddonService.getInstance().getAddon(namespace).isPresent()) {
                return;
            }
            FlintIndex.IndexFilter filter = LabyMod.references().flintController().getFlintIndex().filter();
            Optional<FlintModification> optionalMod = filter.namespace(namespace);
            if (optionalMod.isEmpty()) {
                return;
            }
            FlintModification dependencyModification = optionalMod.get();
            if (dependencyModification.hasMeta(AddonMeta.HIDDEN)) {
                return;
            }
            dependencies.add(dependencyModification);
            collectUninstalledDependencies(dependencyModification, dependencies, optional);
        });
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.ScreenInstance
    public void onOpenScreen() {
        ((Document) this.document).playAnimation("fade-in");
        super.onOpenScreen();
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.ScreenInstance
    public void onCloseScreen() {
        ((Document) this.document).playAnimation("fade-out");
        super.onCloseScreen();
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        String lastUpdateString;
        super.initialize(parent);
        loadModification();
        VerticalListWidget<Widget> container = new VerticalListWidget<>();
        container.addId("container");
        DivWidget detailsContainer = new DivWidget();
        detailsContainer.addId("details-container");
        FlintModification.Image thumbnail = this.modification.getThumbnail();
        if (thumbnail != null) {
            detailsContainer.addId("details-with-thumbnail");
            VariableIconWidget variableIconWidget = new VariableIconWidget(thumbnail.getIconUrl(), FlintController::getVariableBrandUrl);
            variableIconWidget.addId("thumbnail");
            variableIconWidget.setCleanupOnDispose(true);
            detailsContainer.addChild(variableIconWidget);
        } else {
            detailsContainer.addId("details-no-thumbnail");
        }
        GradientWidget thumbnailGradient = new GradientWidget();
        thumbnailGradient.addId("thumbnail");
        detailsContainer.addChild(thumbnailGradient);
        FlintModification.Image image = this.modification.getIcon();
        VariableIconWidget variableIconWidget2 = new VariableIconWidget(Textures.DEFAULT_SERVER_ICON, image == null ? null : image.getIconUrl(), FlintController::getVariableBrandUrl);
        variableIconWidget2.addId("icon");
        variableIconWidget2.setCleanupOnDispose(true);
        detailsContainer.addChild(variableIconWidget2);
        VerticalListWidget<Widget> infoContainer = new VerticalListWidget<>();
        infoContainer.addId("info-container");
        String authorName = this.modification.getAuthor();
        String organizationName = authorName == null ? this.internationalization.getRawTranslation("labymod.addons.store.organization.loading") : authorName;
        ComponentWidget author = ComponentWidget.i18n("labymod.addons.store.modification.organization.name", organizationName);
        author.addId("author");
        infoContainer.addChild(author);
        ComponentWidget name = ComponentWidget.text(this.modification.getName());
        name.addId("name");
        infoContainer.addChild(name);
        ComponentWidget shortDescription = ComponentWidget.text(this.modification.getShortDescription());
        shortDescription.addId("short-description");
        infoContainer.addChild(shortDescription);
        FlintModification.Rating rating = this.modification.getRating();
        RatingWidget ratingWidget = new RatingWidget(rating.getRating(), rating.getCount());
        infoContainer.addChild(ratingWidget);
        detailsContainer.addChild(infoContainer);
        container.addChild(detailsContainer);
        HorizontalListWidget misc = new HorizontalListWidget();
        misc.addId("misc-container");
        ResourceLocation resource = Textures.SpriteFlint.TEXTURE;
        misc.addEntry(new MiscWidget(this, Icon.sprite32(resource, 0, 1), "labymod.addons.store.profile.downloads", this.modification.getDownloadsString()).addId("downloads"));
        if (this.modification.getLastUpdate() == 0) {
            lastUpdateString = null;
        } else {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat(this.internationalization.getRawTranslation("labymod.addons.store.profile.lastUpdate.format"));
                lastUpdateString = dateFormat.format(Long.valueOf(this.modification.getLastUpdate()));
            } catch (IllegalArgumentException e) {
                lastUpdateString = this.undefined;
            }
        }
        misc.addEntry(new MiscWidget(this, Icon.sprite32(resource, 1, 1), "labymod.addons.store.profile.lastUpdate.name", lastUpdateString).addId("last-update"));
        misc.addEntry(new MiscWidget(this, Icon.sprite32(resource, 2, 1), "labymod.addons.store.profile.versions", this.modification.getVersionString()).addId("versions"));
        container.addChild(misc);
        container.addChild(this.infoWidget);
        this.scrollWidget = new ScrollWidget((VerticalListWidget<?>) container);
        DivWidget scrollContainerWidget = (DivWidget) new DivWidget().addId("scroll-container");
        scrollContainerWidget.addChild(this.scrollWidget);
        document().addChild(scrollContainerWidget);
        ButtonWidget backButton = ButtonWidget.icon(Textures.SpriteCommon.BACK_BUTTON, () -> {
            this.fallback.displayScreen(this.fallback);
        });
        backButton.addId("back-button");
        DivWidget topContainer = (DivWidget) new DivWidget().addId("top-container");
        topContainer.addChild(backButton);
        topContainer.addChild(getManageWidget());
        document().addChild(topContainer);
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.LabyScreen, net.labymod.api.client.gui.Interactable
    public void tick() {
        float overflowHeight = this.scrollWidget.getOverflowHeight();
        float height = overflowHeight / 255.0f;
        Window window = this.labyAPI.minecraft().minecraftWindow();
        int alpha = MathHelper.clamp(MathHelper.ceil(this.scrollWidget.getBottomLeftSpace() * height * window.getScale()), 0, 100);
        setVariable("--addon-profile-scroll-color", Integer.valueOf(ColorFormat.ARGB32.pack(0, 0, 0, alpha)));
        int blur = alpha / 8;
        setVariable("--addon-profile-blur-radius", Integer.valueOf(blur));
        super.tick();
        this.installController.tick();
    }

    private Widget getManageWidget() {
        FlintDownloadTask downloadTask = this.installController.downloadTask;
        if (this.controller.isInstalled(this.modification) && (downloadTask == null || downloadTask.isFinished())) {
            this.installController.unsetInstallButton();
            HorizontalListWidget manageContainer = new HorizontalListWidget();
            manageContainer.addId("manage-container");
            Optional<Setting> settingOptional = this.controller.getSettings(this.modification);
            if (settingOptional.isPresent()) {
                ButtonWidget settingsButton = new ButtonWidget();
                settingsButton.addId("settings-button");
                settingsButton.icon().set(Textures.SpriteCommon.SETTINGS);
                settingsButton.setPressable(this.controller.displaySettings(settingOptional.get()));
                manageContainer.addEntry(settingsButton);
            }
            DefaultAddonService instance = DefaultAddonService.getInstance();
            boolean additonalAddon = instance.addonLoader().isAdditionalAddon(instance.getAddonInfo(this.modification.getNamespace()));
            ButtonWidget uninstallButton = new ButtonWidget();
            uninstallButton.addId("uninstall-button");
            uninstallButton.icon().set(Textures.SpriteCommon.TRASH);
            uninstallButton.setEnabled(!additonalAddon);
            FlintDownloader downloader = LabyMod.references().flintDownloader();
            if (additonalAddon) {
                uninstallButton.setHoverComponent(Component.translatable("labymod.addons.store.installed.uninstall.managed", new Component[0]));
            } else if (downloader.isScheduledForRemoval(this.modification)) {
                uninstallButton.setHoverComponent(Component.translatable("labymod.addons.store.installed.uninstall.revert.hover", new Component[0]));
            } else {
                uninstallButton.setHoverComponent(Component.translatable("labymod.addons.store.installed.uninstall.hover", new Component[0]));
            }
            uninstallButton.setPressable(() -> {
                try {
                    this.installController.unsetDownloadTask();
                    this.controller.uninstallModification(this.modification, this::reload);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            manageContainer.addEntry(uninstallButton);
            return manageContainer;
        }
        if (!this.modification.isBuildCompatible() || !this.modification.isCompatible() || this.hasUnsupportedDependencies) {
            this.installController.unsetInstallButton();
            ButtonWidget incompatibleButton = ButtonWidget.i18n("labymod.addons.store.profile.incompatible.name");
            incompatibleButton.addId("incompatible-button");
            incompatibleButton.setEnabled(false);
            incompatibleButton.setHoverComponent(StoreItemWidget.getIncompatibleComponent(this.modification, this.modification.isCompatible()));
            return incompatibleButton;
        }
        return this.installController.createInstallButton();
    }

    protected void loadModification() {
        if (this.loadingModification || this.modification == null || !(this.modification instanceof FlintIndex.FlintIndexModification)) {
            return;
        }
        FlintModification mod = this.modification.loadModification(result -> {
            if (!result.isPresent() || !(this.modification instanceof FlintIndex.FlintIndexModification)) {
                return;
            }
            this.modification = (FlintModification) result.get();
            this.loadingModification = false;
            this.labyAPI.minecraft().executeOnRenderThread(this::reload);
        });
        if (mod != null) {
            this.modification = mod;
        } else {
            this.loadingModification = true;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labymod/child/addons/AddonProfileActivity$AddonInstallController.class */
    public static class AddonInstallController {
        private final FlintModification modification;
        private final Runnable reload;
        private FlintDownloadTask downloadTask;
        private ButtonWidget installButton;

        public AddonInstallController(FlintModification modification, Runnable reload) {
            this.modification = modification;
            this.reload = reload;
            this.downloadTask = LabyMod.references().flintController().getDownloadTask(modification).orElse(null);
        }

        public void tick() {
            Component component;
            if (this.installButton == null || this.downloadTask == null || !this.installButton.isVisible()) {
                return;
            }
            double percentage = this.downloadTask.getPercentage();
            if (this.downloadTask.state() == DownloadState.DOWNLOADING && percentage != -1.0d) {
                this.installButton.setVariable("--download-percentage", Double.valueOf(percentage));
                component = Component.text(String.format(Locale.ROOT, "%.0f", Double.valueOf(percentage)) + "%");
            } else {
                String state = this.downloadTask.state().toString();
                component = Component.translatable("labymod.addons.store.profile." + state, new Component[0]);
                this.installButton.addId(TextFormat.CAMEL_CASE.toDashCase(state));
            }
            this.installButton.updateComponent(component);
            this.installButton.reInitialize();
        }

        public void install() {
            List<FlintModification> requiredDependencies = new ArrayList<>();
            List<FlintModification> optionalDependencies = new ArrayList<>();
            collectDependencies(requiredDependencies, optionalDependencies);
            FlintController flintController = LabyMod.references().flintController();
            FlintIndex.IndexFilter filter = flintController.getFlintIndex().filter();
            FlintModification modLoader = null;
            if (filter.isHidden(this.modification)) {
                modLoader = this.modification;
            } else {
                Iterator<FlintModification> it = requiredDependencies.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    FlintModification requiredDependency = it.next();
                    if (filter.isHidden(requiredDependency)) {
                        modLoader = requiredDependency;
                        break;
                    }
                }
            }
            if (modLoader == null) {
                startInstallProcess(requiredDependencies, optionalDependencies, new ArrayList());
                return;
            }
            boolean isMainAddon = modLoader.getNamespace().equals(this.modification.getNamespace());
            FlintModification finalModLoader = modLoader;
            flintController.setModLoader(modLoader.getNamespace(), true, isMainAddon, result -> {
                if (isMainAddon) {
                    if (result == FlintController.SetModLoaderResult.SUCCESS) {
                        this.downloadTask = FlintDownloadTask.dummy(finalModLoader, DownloadState.REQUIRES_RESTART);
                        LabyMod.references().flintDownloader().setDownloadTask(finalModLoader, this.downloadTask);
                        this.reload.run();
                        this.installButton.setEnabled(true);
                        return;
                    }
                    return;
                }
                if (result != FlintController.SetModLoaderResult.CONTINUE && result != FlintController.SetModLoaderResult.SUCCESS) {
                    return;
                }
                List<String> skippedDependencies = new ArrayList<>();
                if (result == FlintController.SetModLoaderResult.SUCCESS) {
                    skippedDependencies.add(finalModLoader.getNamespace());
                    requiredDependencies.remove(finalModLoader);
                }
                startInstallProcess(requiredDependencies, optionalDependencies, skippedDependencies);
            });
        }

        public void unsetDownloadTask() {
            this.downloadTask = null;
        }

        public void unsetInstallButton() {
            this.installButton = null;
        }

        public ButtonWidget createInstallButton() {
            this.installButton = new ButtonWidget();
            this.installButton.addId("install-button");
            if (this.downloadTask != null) {
                DownloadState state = this.downloadTask.state();
                this.installButton.setEnabled(state == DownloadState.REQUIRES_RESTART && Laby.references().launcherService().hasGameSessionId());
                this.installButton.addId(TextFormat.CAMEL_CASE.toDashCase(state.toString()));
                this.installButton.updateComponent(Component.translatable("labymod.addons.store.profile." + String.valueOf(state), new Component[0]));
                this.installButton.setPressable(this::openRestartPopup);
            } else {
                if (!this.modification.isCompatible()) {
                    this.installButton.updateComponent(Component.translatable("labymod.addons.store.profile.download", new Component[0]));
                } else {
                    this.installButton.updateComponent(Component.translatable("labymod.addons.store.profile.install", new Component[0]));
                }
                this.installButton.setPressable(this::install);
            }
            return this.installButton;
        }

        private void collectDependencies(List<FlintModification> requiredDependencies, List<FlintModification> optionalDependencies) {
            AddonProfileActivity.collectUninstalledDependencies(this.modification, requiredDependencies, false);
            AddonProfileActivity.collectUninstalledDependencies(this.modification, optionalDependencies, true);
        }

        private void startInstallProcess(List<FlintModification> requiredDependencies, List<FlintModification> optionalDependencies, List<String> skippedDependencies) {
            List<FlintModification> dependencies;
            boolean required = !requiredDependencies.isEmpty();
            if (!requiredDependencies.isEmpty()) {
                dependencies = requiredDependencies;
            } else if (!optionalDependencies.isEmpty()) {
                dependencies = optionalDependencies;
            } else {
                install(skippedDependencies);
                return;
            }
            FlintModification dependency = dependencies.remove(0);
            openDependencyPopup(dependency, required, action -> {
                if (action == DependencyPopupAction.SKIP) {
                    if (required) {
                        return;
                    }
                    if (!skippedDependencies.contains(dependency.getNamespace())) {
                        skippedDependencies.add(dependency.getNamespace());
                    }
                }
                startInstallProcess(requiredDependencies, optionalDependencies, skippedDependencies);
            });
        }

        private void install(List<String> skippedDependencies) {
            this.installButton.setEnabled(false);
            this.downloadTask = LabyMod.references().flintController().downloadModification(this.modification, skippedDependencies, downloadTask -> {
                this.installButton.setEnabled(false);
                if (downloadTask != null && downloadTask.state() == DownloadState.REQUIRES_RESTART && Laby.references().launcherService().hasGameSessionId()) {
                    Laby.labyAPI().minecraft().executeOnRenderThread(() -> {
                        this.reload.run();
                        this.installButton.setEnabled(true);
                        openRestartPopup();
                    });
                }
                if (downloadTask == null || downloadTask.isFinished()) {
                    this.downloadTask = null;
                    Laby.labyAPI().minecraft().executeOnRenderThread(() -> {
                        this.reload.run();
                        LegacyConfigConverter converter = Laby.references().legacyConfigConverter();
                        String namespace = this.modification.getNamespace();
                        if (converter.hasStuffToConvert(namespace)) {
                            TranslatableComponent title = Component.translatable("labymod.legacyconverter.convertSettings", Component.text(this.modification.getName()));
                            ConfirmActivity.confirm(title, Component.translatable("labymod.legacyconverter.yes", new Component[0]), Component.translatable("labymod.legacyconverter.no", new Component[0]), (Consumer<Boolean>) confirmed -> {
                                if (confirmed != null && confirmed.booleanValue()) {
                                    converter.convert(namespace);
                                }
                                converter.setConversionAsked(namespace);
                            });
                        }
                    });
                }
            });
        }

        private void openRestartPopup() {
            PopupWidget.builder().title(Component.translatable("labymod.addons.store.profile.restart.title", Component.text(this.modification.getName()))).text(Component.translatable("labymod.addons.store.profile.restart.description", Component.text(this.modification.getName()))).confirmComponent(Component.translatable("labymod.addons.store.profile.restart.now", new Component[0])).cancelComponent(Component.translatable("labymod.addons.store.profile.restart.later", new Component[0])).confirmCallback(() -> {
                try {
                    Laby.references().launcherService().restart();
                } catch (IllegalArgumentException e) {
                    Notification notification = Notification.builder().title(Component.translatable("labymod.addons.store.profile.restart.error.title", new Component[0])).text(Component.text(e.getMessage())).build();
                    Laby.references().notificationController().push(notification);
                }
            }).cancelCallback(Pressable.NOOP).build().displayInOverlay();
        }

        private void openDependencyPopup(FlintModification dependency, boolean required, Consumer<DependencyPopupAction> actionConsumer) {
            String translationKeyPrefix;
            Component skipComponent;
            if (required) {
                translationKeyPrefix = "labymod.addons.store.profile.dependency.required.";
                skipComponent = Component.translatable("labymod.ui.button.cancel", new Component[0]);
            } else {
                translationKeyPrefix = "labymod.addons.store.profile.dependency.optional.";
                skipComponent = Component.translatable("labymod.addons.store.profile.dependency.optional.skip", new Component[0]);
            }
            SimpleAdvancedPopup.builder().title(Component.translatable(translationKeyPrefix + "title", Component.text(this.modification.getName()), Component.text(dependency.getName()))).description(Component.translatable(translationKeyPrefix + "description", Component.text(this.modification.getName()), Component.text(dependency.getName()))).addButton(SimpleAdvancedPopup.SimplePopupButton.create(skipComponent, (Consumer<SimpleAdvancedPopup.SimplePopupButton>) button -> {
                actionConsumer.accept(DependencyPopupAction.SKIP);
            })).addButton(SimpleAdvancedPopup.SimplePopupButton.create(Component.translatable("labymod.addons.store.profile.install", new Component[0]), (Consumer<SimpleAdvancedPopup.SimplePopupButton>) button2 -> {
                actionConsumer.accept(DependencyPopupAction.INSTALL);
            })).build().displayInOverlay();
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labymod/child/addons/AddonProfileActivity$MiscWidget.class */
    @AutoWidget
    public class MiscWidget extends HorizontalListWidget {
        private final Icon icon;
        private final Component title;
        private final String value;

        protected MiscWidget(AddonProfileActivity this$0, Icon icon, String titleKey, String value) {
            String title = Laby.references().internationalization().getRawTranslation(titleKey);
            this.icon = icon;
            this.title = Component.text(title.toUpperCase(Locale.ENGLISH));
            if (value == null) {
                this.value = this$0.undefined;
            } else {
                this.value = value;
            }
        }

        @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
        public void initialize(Parent parent) {
            super.initialize(parent);
            IconWidget iconWidget = new IconWidget(this.icon);
            iconWidget.addId("misc-icon");
            addEntry(iconWidget);
            VerticalListWidget<ComponentWidget> textContainer = new VerticalListWidget<>();
            textContainer.addId("text-container");
            ComponentWidget titleComponent = ComponentWidget.component(this.title);
            titleComponent.addId("misc-title");
            textContainer.addChild(titleComponent);
            ComponentWidget valueComponent = ComponentWidget.text(this.value);
            valueComponent.addId("misc-value");
            textContainer.addChild(valueComponent);
            addEntry(textContainer);
        }
    }
}
