package net.labymod.core.client.gui.screen.activity.activities.labymod.child.shop.widgets;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.action.Switchable;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.context.ContextMenuEntry;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.WrappedListWidget;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.cosmetics.CosmeticSettingsWidget;
import net.labymod.core.labymodnet.models.Cosmetic;
import net.labymod.core.labymodnet.widgetoptions.WidgetOption;
import net.labymod.core.labymodnet.widgetoptions.WidgetOptionService;
import net.labymod.core.main.LabyMod;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labymod/child/shop/widgets/CosmeticConfigurationWidget.class */
@AutoWidget
public class CosmeticConfigurationWidget extends AbstractWidget<Widget> {
    private static final ModifyReason REASON = ModifyReason.of("settingContent");
    private static final String TRANSLATION_KEY_PREFIX = "labymod.activity.playerCustomization.cosmetics.settings.";
    private final CosmeticSettingsWidget.CosmeticSettingsListener listener;
    private Cosmetic cosmetic;
    private List<WidgetOption> options;
    private DivWidget wrapper;
    private VerticalListWidget<Widget> container;
    private ScrollWidget scrollWidget;

    public CosmeticConfigurationWidget(final Consumer<Cosmetic> onDataUpdate) {
        this.listener = new CosmeticSettingsWidget.CosmeticSettingsListener(this) { // from class: net.labymod.core.client.gui.screen.activity.activities.labymod.child.shop.widgets.CosmeticConfigurationWidget.1
            @Override // net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.cosmetics.CosmeticSettingsWidget.CosmeticSettingsListener
            public void onOpenSettings(CosmeticSettingsWidget widget) {
            }

            @Override // net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.cosmetics.CosmeticSettingsWidget.CosmeticSettingsListener
            public void onCloseSettings(CosmeticSettingsWidget widget) {
            }

            @Override // net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.cosmetics.CosmeticSettingsWidget.CosmeticSettingsListener
            @Nullable
            public Switchable onSwitch() {
                return null;
            }

            @Override // net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.cosmetics.CosmeticSettingsWidget.CosmeticSettingsListener
            public void onDataUpdate(Cosmetic cosmetic) {
                onDataUpdate.accept(cosmetic);
            }
        };
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        if (this.cosmetic == null) {
            return;
        }
        if (this.options == null) {
            WidgetOptionService service = LabyMod.references().shopController().getWidgetOptionService();
            this.options = service.getOptions(this.cosmetic, null);
        }
        if (this.options.isEmpty()) {
            return;
        }
        this.container = new VerticalListWidget<>();
        this.container.addId("setting-container");
        ComponentWidget title = ComponentWidget.text(this.cosmetic.getName());
        title.addId("title");
        this.container.addChild(title);
        WrappedListWidget<Widget> listWidget = new WrappedListWidget<>();
        listWidget.addId("setting-list");
        for (WidgetOption option : this.options) {
            option.begin(this.cosmetic, this.listener, TRANSLATION_KEY_PREFIX);
            Objects.requireNonNull(listWidget);
            option.create(listWidget::addChild);
        }
        if (!this.options.isEmpty()) {
            this.container.addChild(listWidget);
            ComponentWidget info = ComponentWidget.i18n("labymod.activity.shop.cosmeticSettingsInfo");
            info.addId("info");
            this.container.addChild(info);
        }
        this.scrollWidget = new ScrollWidget((VerticalListWidget<?>) this.container);
        this.scrollWidget.addId("setting-scroll");
        this.wrapper = new DivWidget();
        this.wrapper.addId("setting-wrapper");
        this.wrapper.addChild(this.scrollWidget);
        addChild(this.wrapper);
        createContextMenuLazy(contextMenu -> {
            ContextMenuEntry resetOptionsEntry = ContextMenuEntry.builder().text(Component.translatable("labymod.activity.shop.cosmeticSettingsReset", new Component[0])).icon(Textures.SpriteCommon.TRASH).clickHandler(entry -> {
                this.cosmetic.resetData();
                this.listener.onDataUpdate(this.cosmetic);
                reInitialize();
                return true;
            }).build();
            contextMenu.addEntry(resetOptionsEntry);
        });
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget
    protected void updateContentBounds() {
        super.updateContentBounds();
        update();
    }

    public CosmeticConfigurationWidget update(Cosmetic cosmetic) {
        if (cosmetic == null) {
            this.cosmetic = null;
            this.options = null;
            return this;
        }
        if (this.cosmetic == null || this.cosmetic.getId() != cosmetic.getId()) {
            this.cosmetic = cosmetic;
            this.options = null;
        }
        return this;
    }

    public boolean isIdMatching(int id) {
        return this.cosmetic != null && this.cosmetic.getId() == id;
    }

    private void update() {
        if (this.wrapper == null || this.scrollWidget == null || this.container == null) {
            return;
        }
        Bounds bounds = bounds();
        Bounds wrapperBounds = this.wrapper.bounds();
        Bounds scrollBounds = this.scrollWidget.bounds();
        float maxContentHeight = bounds.getHeight() - wrapperBounds.getVerticalOffset(BoundsType.BORDER);
        float contentHeight = this.container.getContentHeight(BoundsType.OUTER) + scrollBounds.getVerticalOffset(BoundsType.OUTER);
        float scrollHeight = Math.min(contentHeight, maxContentHeight);
        float contentTop = wrapperBounds.getBottom() - scrollHeight;
        wrapperBounds.setX(bounds.getX(), BoundsType.BORDER, REASON);
        wrapperBounds.setSize(bounds.getWidth(), scrollHeight + wrapperBounds.getVerticalOffset(BoundsType.BORDER), BoundsType.BORDER, REASON);
        scrollBounds.setOuterPosition(wrapperBounds.getX(), contentTop, REASON);
        scrollBounds.setOuterSize(wrapperBounds.getWidth(), wrapperBounds.getHeight(), REASON);
        Bounds containerBounds = this.container.bounds();
        containerBounds.setOuterPosition(scrollBounds.getX(), scrollBounds.getY(), REASON);
        containerBounds.setOuterSize(scrollBounds.getWidth(), contentHeight, REASON);
    }
}
