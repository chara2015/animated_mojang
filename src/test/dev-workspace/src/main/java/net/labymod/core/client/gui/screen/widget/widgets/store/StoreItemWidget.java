package net.labymod.core.client.gui.screen.widget.widgets.store;

import java.util.ArrayList;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;
import net.labymod.api.client.gui.screen.widget.action.Pressable;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.RatingWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.VariableIconWidget;
import net.labymod.api.client.sound.SoundType;
import net.labymod.api.thirdparty.optifine.OptiFine;
import net.labymod.api.util.CollectionHelper;
import net.labymod.api.util.I18n;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.addons.AddonProfileActivity;
import net.labymod.core.flint.FlintController;
import net.labymod.core.flint.downloader.AddonDownloadRequest;
import net.labymod.core.flint.index.FlintIndex;
import net.labymod.core.flint.marketplace.FlintModification;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/store/StoreItemWidget.class */
@AutoWidget
public class StoreItemWidget extends SimpleWidget {
    private final Pressable flintPressable;
    private FlintModification modification;
    private final LssProperty<Integer> installedColor = new LssProperty<>(0);
    private final LssProperty<Integer> deletedColor = new LssProperty<>(0);
    private final String loadingString = I18n.translate("", new Object[0]);
    protected final boolean hasUnsupportedDependencies;
    private boolean loadingModification;

    public StoreItemWidget(FlintModification modification, Pressable pressable) {
        this.modification = modification;
        this.flintPressable = pressable;
        this.hasUnsupportedDependencies = hasUnsupportedDependencies(modification);
        this.lazy = true;
    }

    public static boolean hasUnsupportedDependencies(FlintModification modification) {
        if (modification == null || modification.getDependencies().length == 0 || AddonDownloadRequest.UNSUPPORTED_ADDONS.size() == 0) {
            return false;
        }
        List<FlintModification> dependencies = new ArrayList<>();
        AddonProfileActivity.collectUninstalledDependencies(modification, dependencies, false);
        if (dependencies.size() == 0) {
            return false;
        }
        return CollectionHelper.anyMatch(dependencies, dependency -> {
            return AddonDownloadRequest.UNSUPPORTED_ADDONS.contains(dependency.getNamespace());
        });
    }

    public static Component getIncompatibleComponent(FlintModification modification, boolean compatible) {
        if (!modification.isBuildCompatible()) {
            return Component.translatable("labymod.addons.store.profile.incompatible.description", new Component[0]);
        }
        if (modification.getNamespace().equals(OptiFine.NAMESPACE)) {
            return Component.translatable("labymod.addons.store.profile.incompatibleVersion.optifineDescription", Component.text(Laby.labyAPI().labyModLoader().version().toString()));
        }
        if (!compatible) {
            return Component.translatable("labymod.addons.store.profile.incompatibleVersion." + (modification.isOrganizationLabyMod() ? "labyModDescription" : "description"), Component.text(modification.getName()), Component.text(Laby.labyAPI().labyModLoader().version().toString()));
        }
        return Component.translatable("labymod.addons.store.profile.unsupported", new Component[0]);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        if (this.modification != null && (!this.modification.isCompatible() || this.hasUnsupportedDependencies)) {
            addId("incompatible");
        } else {
            removeId("incompatible");
        }
        super.initialize(parent);
        loadModification();
        initializeStoreItem();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void handleAttributes() {
        super.handleAttributes();
        setBackgroundColor();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget
    protected SoundType getInteractionSound() {
        return SoundType.BUTTON_CLICK;
    }

    private void setBackgroundColor() {
        if (this.modification != null && this.modification.isInstalled()) {
            Integer deletedColor = this.deletedColor.get();
            if (this.modification.isDeleted() && deletedColor.intValue() != 0) {
                backgroundColor().set(deletedColor);
                return;
            }
            Integer installedColor = this.installedColor.get();
            if (installedColor.intValue() != 0) {
                backgroundColor().set(installedColor);
            }
        }
    }

    public FlintModification modification() {
        return this.modification;
    }

    public Pressable flintPressable() {
        return this.flintPressable;
    }

    public LssProperty<Integer> installedColor() {
        return this.installedColor;
    }

    public LssProperty<Integer> deletedColor() {
        return this.deletedColor;
    }

    protected void setModification(FlintModification modification) {
        this.modification = modification;
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
            this.labyAPI.minecraft().executeOnRenderThread(this::reInitialize);
        });
        if (mod != null) {
            this.modification = mod;
        } else {
            this.loadingModification = true;
        }
    }

    protected boolean isIncompatible() {
        return (this.modification.isBuildCompatible() && this.modification.isCompatible() && !this.hasUnsupportedDependencies) ? false : true;
    }

    protected Component getIncompatibleComponent() {
        return getIncompatibleComponent(this.modification, this.modification.isCompatible());
    }

    protected void initializeStoreItem() {
        if (this.modification == null) {
            return;
        }
        FlexibleContentWidget flexibleContentWidget = new FlexibleContentWidget();
        flexibleContentWidget.addId("item-container");
        FlintModification.Image icon = this.modification.getIcon();
        VariableIconWidget iconWidget = new VariableIconWidget(Textures.DEFAULT_SERVER_ICON, icon == null ? null : icon.getIconUrl(), FlintController::getVariableBrandUrl);
        iconWidget.setCleanupOnDispose(true);
        iconWidget.addId("item-icon");
        flexibleContentWidget.addContent(iconWidget);
        FlexibleContentWidget textContainer = new FlexibleContentWidget();
        textContainer.addId("item-text-container");
        Component displayName = Component.text(this.modification.getName());
        if (this.modification instanceof FlintIndex.FlintIndexModification) {
            displayName = Component.translatable("labymod.addons.loading", displayName);
        }
        ComponentWidget nameWidget = ComponentWidget.component(displayName);
        nameWidget.addId("item-name");
        if (isIncompatible()) {
            HorizontalListWidget itemNameWrapper = new HorizontalListWidget();
            itemNameWrapper.addId("item-name-wrapper");
            itemNameWrapper.addEntry(nameWidget);
            IconWidget incompatibleBadge = new IconWidget(Textures.SpriteFlint.WARNING);
            incompatibleBadge.addId("incompatible-badge");
            incompatibleBadge.setHoverComponent(getIncompatibleComponent());
            itemNameWrapper.addEntry(incompatibleBadge);
            textContainer.addContent(itemNameWrapper);
        } else {
            textContainer.addContent(nameWidget);
        }
        String authorName = this.modification.getAuthor();
        String organizationName = authorName == null ? "" : authorName;
        ComponentWidget organizationWidget = ComponentWidget.i18n("labymod.addons.store.modification.organization.name", organizationName);
        organizationWidget.addId("item-organization");
        textContainer.addContent(organizationWidget);
        if (!(this.modification instanceof FlintIndex.FlintIndexModification)) {
            FlintModification.Rating rating = this.modification.getRating();
            textContainer.addContent(new RatingWidget(rating.getRating(), rating.getCount()));
        }
        ComponentWidget descriptionWidget = ComponentWidget.text(this.modification.getShortDescription());
        descriptionWidget.addId("item-description");
        textContainer.addContent(descriptionWidget);
        flexibleContentWidget.addFlexibleContent(textContainer);
        if (this.flintPressable != null && !(this.modification instanceof FlintIndex.FlintIndexModification)) {
            setPressable(this.flintPressable);
        }
        addChild(flexibleContentWidget);
    }
}
