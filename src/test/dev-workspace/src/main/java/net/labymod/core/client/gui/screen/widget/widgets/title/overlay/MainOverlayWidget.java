package net.labymod.core.client.gui.screen.widget.widgets.title.overlay;

import java.util.List;
import java.util.Locale;
import java.util.UUID;
import net.labymod.api.BuildData;
import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.cursor.CursorTypes;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.configuration.labymod.main.laby.other.AdvancedConfig;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.protocol.LabyConnectState;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.notification.Notification;
import net.labymod.api.thirdparty.LabySentry;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.configuration.labymod.LabyConfigProvider;
import net.labymod.core.configuration.labymod.main.laby.other.DefaultAdvancedConfig;
import net.labymod.core.labyconnect.session.DefaultLabyConnectSession;
import net.labymod.core.localization.keys.UiTranslationKeys;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.advertisement.Advertisement;
import net.labymod.core.main.advertisement.AdvertisementService;
import net.labymod.core.main.advertisement.model.Announcement;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/title/overlay/MainOverlayWidget.class */
@AutoWidget
public class MainOverlayWidget extends AbstractWidget<Widget> {
    private static final Logging LOGGER = Logging.getLogger();
    public static final boolean IS_MODDED_INDICATOR_VERSION = MinecraftVersions.V1_16.orNewer();
    public static final Component COPYRIGHT_TEXT = Component.text("Copyright Mojang AB. Do not distribute!");
    private ComponentWidget copyrightWidget;
    private ComponentWidget labyModVersionWidget;
    private int devToolsClicks = 0;
    private final AdvertisementService advertisementService = LabyMod.references().advertisementService();

    public MainOverlayWidget() {
        this.advertisementService.subscribeToAdvertisement("main-overlay", () -> {
            ThreadSafe.executeOnRenderThread(this::reInitialize);
        });
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        VerticalListWidget<Widget> leftList = new VerticalListWidget<>();
        leftList.addId("left-side-announcements", "side-announcements");
        addChild(leftList);
        VerticalListWidget<Widget> rightList = new VerticalListWidget<>();
        rightList.addId("right-side-announcements", "side-announcements");
        addChild(rightList);
        if (this.labyAPI.config().appearance().titleScreen().socialMediaLinks().get().booleanValue()) {
            initializeSideAnnouncements(leftList, rightList);
        }
        initializeCenteredAnnouncements();
        initializeDashboardHead(rightList);
        initializeFooter();
    }

    private void initializeCenteredAnnouncements() {
        VerticalListWidget<Widget> centerList = new VerticalListWidget<>();
        centerList.addId("center-announcements");
        if (BuildData.isMarkedForRemoval(MinecraftVersions.current().version())) {
            initializeSimpleAnnouncement(centerList, UiTranslationKeys.getWarningsScheduledForRemovalTitle().style(Style.builder().decorate(TextDecoration.BOLD).color(NamedTextColor.RED).build()), UiTranslationKeys.getWarningsScheduledForRemovalDescription());
        }
        if (MinecraftVersions.V26_2_snapshot_2.orNewer()) {
            initializeSimpleAnnouncement(centerList, UiTranslationKeys.getWarningsNoVulkanBackendTitle().style(Style.builder().decorate(TextDecoration.BOLD).color(NamedTextColor.YELLOW).build()), UiTranslationKeys.getWarningsNoVulkanBackendDescription());
        }
        if (!centerList.getChildren().isEmpty()) {
            addChild(centerList);
        }
    }

    private void initializeSimpleAnnouncement(VerticalListWidget<Widget> list, Component title, Component description) {
        ComponentWidget titleWidget = ComponentWidget.component(title);
        titleWidget.addId("title", "centered");
        list.addChild(titleWidget);
        ComponentWidget descriptionWidget = ComponentWidget.component(description);
        descriptionWidget.addId("description", "centered");
        list.addChild(descriptionWidget);
    }

    private void initializeFooter() {
        String str;
        HorizontalListWidget footer = new HorizontalListWidget();
        footer.addId("footer");
        VerticalListWidget<ComponentWidget> versionList = new VerticalListWidget<>();
        versionList.addId("version-list");
        this.labyModVersionWidget = ComponentWidget.text("LabyMod " + String.valueOf(BuildData.version()));
        this.labyModVersionWidget.setHoverComponent(Component.text(BuildData.getVersion()), 300.0f);
        this.labyModVersionWidget.addId("version");
        versionList.addChild(this.labyModVersionWidget);
        Minecraft minecraft = this.labyAPI.minecraft();
        String version = minecraft.getVersion();
        if (minecraft.isDemo()) {
            str = " Demo";
        } else {
            str = "release".equalsIgnoreCase(minecraft.getVersionType()) ? "" : "/" + minecraft.getVersionType();
        }
        String version2 = "Minecraft " + version + str + (IS_MODDED_INDICATOR_VERSION ? minecraft.getTranslation("menu.modded") : "");
        ComponentWidget minecraftVersion = ComponentWidget.text(version2);
        minecraftVersion.addId("build");
        versionList.addChild(minecraftVersion);
        footer.addEntry(versionList);
        DivWidget copyrightWrapper = new DivWidget();
        copyrightWrapper.addId("copyright-wrapper");
        this.copyrightWidget = ComponentWidget.component(COPYRIGHT_TEXT);
        this.copyrightWidget.setHoverCursor(CursorTypes.POINTING_HAND, true);
        this.copyrightWidget.addId("copyright");
        this.copyrightWidget.setPressable(() -> {
            this.labyAPI.minecraft().minecraftWindow().displayScreen(NamedScreen.CREDITS);
        });
        copyrightWrapper.addChild(this.copyrightWidget);
        footer.addEntry(copyrightWrapper);
        addChild(footer);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        if (super.mouseClicked(mouse, mouseButton)) {
            return true;
        }
        if (mouseButton != MouseButton.RIGHT || !this.labyModVersionWidget.bounds().isInRectangle(mouse)) {
            return false;
        }
        this.devToolsClicks++;
        if (this.devToolsClicks < 5) {
            return false;
        }
        AdvancedConfig advanced = LabyConfigProvider.INSTANCE.get().other().advanced();
        if (!(advanced instanceof DefaultAdvancedConfig)) {
            return false;
        }
        DefaultAdvancedConfig defaultAdvanced = (DefaultAdvancedConfig) advanced;
        this.devToolsClicks = 0;
        defaultAdvanced.devTools().set(Boolean.valueOf(!defaultAdvanced.devTools().get().booleanValue()));
        Notification notification = Notification.builder().title(Component.text("DevTools")).text(Component.text("DevTools " + (defaultAdvanced.devTools().get().booleanValue() ? "enabled" : "disabled"))).build();
        Laby.references().notificationController().push(notification);
        return true;
    }

    private void initializeSideAnnouncements(VerticalListWidget<Widget> leftList, VerticalListWidget<Widget> rightList) {
        try {
            Advertisement advertisement = this.advertisementService.getAdvertisement();
            if (advertisement == null) {
                return;
            }
            initializeSideAnnouncement(leftList, advertisement.left());
            initializeSideAnnouncement(rightList, advertisement.right());
        } catch (Throwable t) {
            LabySentry.capture(t);
            LOGGER.error("An error occurred while initializing the side announcements.", t);
        }
    }

    private void initializeSideAnnouncement(VerticalListWidget<Widget> list, List<Announcement> announcements) {
        if (announcements == null || announcements.isEmpty()) {
            return;
        }
        for (Announcement announcement : announcements) {
            list.addChild(new SideAnnouncementWidget(announcement));
        }
    }

    private void initializeDashboardHead(VerticalListWidget<Widget> list) {
        LabyConnect labyConnect = Laby.labyAPI().labyConnect();
        LabyConnectSession labyConnectSession = labyConnect.getSession();
        if (labyConnect.state() == LabyConnectState.PLAY && labyConnectSession != null) {
            DefaultLabyConnectSession session = (DefaultLabyConnectSession) labyConnectSession;
            UUID uniqueId = this.labyAPI.minecraft().sessionAccessor().getSession().getUniqueId();
            IconWidget headWidget = new IconWidget(Icon.head(uniqueId));
            headWidget.addId("dashboard-widget");
            headWidget.setHoverCursor(CursorTypes.POINTING_HAND, true);
            headWidget.setPressable(() -> {
                Minecraft minecraft = this.labyAPI.minecraft();
                session.requestDashboardPin(pin -> {
                    minecraft.chatExecutor().openUrl(String.format(Locale.ROOT, "https://www.labymod.net/key/?id=%s&pin=%s", uniqueId, pin));
                });
            });
            DivWidget dashboardWrapper = new DivWidget();
            dashboardWrapper.addId("dashboard-wrapper");
            dashboardWrapper.addChild(headWidget);
            list.addChild(dashboardWrapper);
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.Interactable
    public void tick() {
        Style styleUndecorate;
        super.tick();
        Style style = COPYRIGHT_TEXT.style();
        boolean hovered = this.copyrightWidget.isHovered();
        if (style.hasDecoration(TextDecoration.UNDERLINED) != hovered) {
            Component component = COPYRIGHT_TEXT;
            if (hovered) {
                styleUndecorate = style.decorate(TextDecoration.UNDERLINED);
            } else {
                styleUndecorate = style.undecorate(TextDecoration.UNDERLINED);
            }
            component.style(styleUndecorate);
            this.copyrightWidget.updateComponent();
        }
    }
}
