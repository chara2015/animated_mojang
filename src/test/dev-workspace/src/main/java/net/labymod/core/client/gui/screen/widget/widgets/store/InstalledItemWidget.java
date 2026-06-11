package net.labymod.core.client.gui.screen.widget.widgets.store;

import java.io.IOException;
import java.util.Optional;
import net.labymod.api.Textures;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.context.ContextMenu;
import net.labymod.api.client.gui.screen.widget.context.ContextMenuEntry;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.RatingWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.VariableIconWidget;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.core.addon.DefaultAddonService;
import net.labymod.core.flint.FlintController;
import net.labymod.core.flint.downloader.FlintDownloader;
import net.labymod.core.flint.marketplace.FlintModification;
import net.labymod.core.main.LabyMod;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/store/InstalledItemWidget.class */
@AutoWidget
public class InstalledItemWidget extends StoreItemWidget {
    private final FlintController controller;
    private final LoadedAddon loadedAddon;
    private final ResourceLocation iconResourceLocation;

    public InstalledItemWidget(FlintController flintController, LoadedAddon loadedAddon, FlintModification modification) {
        super(null, null);
        this.loadedAddon = loadedAddon;
        this.controller = flintController;
        setModification(modification);
        ResourceLocation resourceLocation = this.labyAPI.renderPipeline().resources().resourceLocationFactory().create(loadedAddon.info().getNamespace(), "textures/icon.png");
        this.iconResourceLocation = resourceLocation.exists() ? resourceLocation : null;
    }

    @Override // net.labymod.core.client.gui.screen.widget.widgets.store.StoreItemWidget
    protected void initializeStoreItem() {
        IconWidget iconWidget;
        FlexibleContentWidget container = new FlexibleContentWidget();
        container.addId("item-container");
        Icon icon = null;
        if (modification().getClass() != FlintModification.class && this.iconResourceLocation != null) {
            icon = Icon.texture(this.iconResourceLocation);
        }
        if (icon != null) {
            iconWidget = new IconWidget(icon);
        } else {
            FlintModification.Image image = modification().getIcon();
            iconWidget = new VariableIconWidget(Textures.DEFAULT_SERVER_ICON, image == null ? null : image.getIconUrl(), FlintController::getVariableBrandUrl);
            iconWidget.setCleanupOnDispose(true);
        }
        iconWidget.addId("item-icon");
        container.addContent(iconWidget);
        FlexibleContentWidget textContainer = new FlexibleContentWidget();
        textContainer.addId("item-text-container");
        ComponentWidget nameWidget = ComponentWidget.text(modification().getName());
        nameWidget.addId("item-name");
        textContainer.addContent(nameWidget);
        String organizationName = this.loadedAddon.info().getAuthor();
        String flintAuthor = modification().getAuthor();
        if (flintAuthor != null) {
            organizationName = flintAuthor;
        }
        ComponentWidget organizationWidget = ComponentWidget.i18n("labymod.addons.store.modification.organization.name", organizationName);
        organizationWidget.addId("item-organization");
        textContainer.addContent(organizationWidget);
        if (modification().getClass() == FlintModification.class) {
            FlintModification.Rating rating = modification().getRating();
            textContainer.addContent(new RatingWidget(rating.getRating(), rating.getCount()));
        }
        String description = modification().getShortDescription();
        ComponentWidget descriptionWidget = ComponentWidget.text(description);
        descriptionWidget.addId("item-description");
        textContainer.addContent(descriptionWidget);
        container.addFlexibleContent(textContainer);
        addChild(container);
        FlexibleContentWidget buttonContainer = new FlexibleContentWidget();
        buttonContainer.addId("button-container");
        Optional<Setting> settingOptional = this.controller.getSettings(this.loadedAddon.info().getNamespace());
        if (settingOptional.isPresent()) {
            ButtonWidget settingsButton = new ButtonWidget();
            settingsButton.addId("settings-button");
            settingsButton.icon().set(Textures.SpriteCommon.SETTINGS);
            settingsButton.setPressable(this.controller.displaySettings(settingOptional.get()));
            buttonContainer.addContent(settingsButton);
        }
        boolean additonalAddon = DefaultAddonService.getInstance().addonLoader().isAdditionalAddon(this.loadedAddon.info());
        ButtonWidget uninstallButton = new ButtonWidget();
        uninstallButton.addId("uninstall-button");
        uninstallButton.icon().set(Textures.SpriteCommon.TRASH);
        uninstallButton.setEnabled(!additonalAddon);
        FlintDownloader downloader = LabyMod.references().flintDownloader();
        if (additonalAddon) {
            uninstallButton.setHoverComponent(Component.translatable("labymod.addons.store.installed.uninstall.managed", new Component[0]));
        } else if (downloader.isScheduledForRemoval(modification())) {
            setHoverComponent(Component.translatable("labymod.addons.store.uninstalled.hover", new Component[0]));
            uninstallButton.setHoverComponent(Component.translatable("labymod.addons.store.installed.uninstall.revert.hover", new Component[0]));
        } else {
            setHoverComponent(null);
            uninstallButton.setHoverComponent(Component.translatable("labymod.addons.store.installed.uninstall.hover", new Component[0]));
        }
        uninstallButton.setPressable(this::uninstallItem);
        buttonContainer.addContent(uninstallButton);
        addChild(buttonContainer);
        ContextMenu contextMenu = createContextMenu();
        contextMenu.addEntry(ContextMenuEntry.builder().icon(Textures.SpriteCommon.TRASH).text(Component.translatable("labymod.ui.button.remove", new Component[0])).simpleClickHandler(mouse -> {
            uninstallItem();
        }).build());
    }

    private void uninstallItem() {
        try {
            this.controller.uninstallModification(modification(), this::reInitialize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
