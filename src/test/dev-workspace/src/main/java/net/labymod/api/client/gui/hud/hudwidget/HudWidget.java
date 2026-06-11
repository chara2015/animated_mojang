package net.labymod.api.client.gui.hud.hudwidget;

import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.hud.position.HudWidgetAnchor;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.widgets.hud.HudWidgetWidget;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.type.SettingPermissionHolder;
import net.labymod.api.event.client.gui.hud.HudWidgetUpdateRequestEvent;
import net.labymod.api.notification.Notification;
import net.labymod.api.revision.Revision;
import net.labymod.api.service.Identifiable;
import net.labymod.api.user.permission.ClientPermission;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.ide.IdeUtil;
import net.labymod.api.util.logging.Logging;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/hud/hudwidget/HudWidget.class */
public abstract class HudWidget<T extends HudWidgetConfig> implements Identifiable {
    private static final Logging LOGGER = Logging.getLogger();
    private static final String REASON_CONFIG_RELOAD = "config_reload";
    protected final String id;
    protected final Class<T> configClass;
    protected final Component displayName;
    protected T config;
    protected List<Setting> settings;
    protected Icon icon;
    protected HudWidget<?> parent;
    protected HudWidget<?> child;
    private SettingPermissionHolder permission;
    protected HudWidgetAnchor anchor = HudWidgetAnchor.LEFT_TOP;
    private HudWidgetCategory category = HudWidgetCategory.MISCELLANEOUS;
    private HudWidgetDropzone[] dropzones = new HudWidgetDropzone[0];
    private Revision revision = null;
    protected final LabyAPI labyAPI = Laby.labyAPI();
    protected final String namespace = this.labyAPI.getNamespace(getClass());

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/hud/hudwidget/HudWidget$Updatable.class */
    public interface Updatable {
        void update(String str);
    }

    public abstract void render(ScreenContext screenContext, boolean z, HudSize hudSize);

    public abstract boolean isVisibleInGame();

    protected HudWidget(String id, Class<T> configClass) {
        this.id = id;
        this.configClass = configClass;
        String translationKey = String.format(Locale.ROOT, "%s.hudWidget.%s.name", this.namespace, id);
        this.displayName = Component.translatable(translationKey, new Component[0]);
    }

    public final void tick(boolean isEditorContext) {
        try {
            onTick(isEditorContext);
        } catch (Throwable throwable) {
            LOGGER.error("Failed to tick hud widget {}", this.id, throwable);
            onException(throwable);
        }
    }

    public void onTick(boolean isEditorContext) {
    }

    public void initialize(HudWidgetWidget widget) {
        updateSize(widget, widget.accessor().isEditor(), widget.size());
    }

    public void postInitialize(HudWidgetWidget widget) {
    }

    public void onBoundsChanged(HudWidgetWidget widget, Rectangle previousRect, Rectangle newRect) {
    }

    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        return false;
    }

    public void updateSize(HudWidgetWidget widget, boolean isEditorContext, HudSize size) {
    }

    public void onUpdate() {
    }

    public void updateAnchor(HudWidgetAnchor anchor) {
        HudWidgetAnchor prevAnchor = this.anchor;
        this.anchor = anchor;
        if (prevAnchor != anchor) {
            onUpdate();
        }
    }

    public HudWidgetAnchor anchor() {
        return this.anchor;
    }

    public final void renderWidget(ScreenContext context, boolean isEditorContext, HudSize size) {
        try {
            render(context, isEditorContext, size);
        } catch (Throwable throwable) {
            LOGGER.error("Failed to render hud widget {}", this.id, throwable);
            onException(throwable);
        }
    }

    public void load(T config) {
        this.config = config;
        if (this.settings == null) {
            this.settings = config.toSettings();
        }
    }

    public void reloadConfig() {
        load(this.config);
        tick(true);
        requestUpdate(REASON_CONFIG_RELOAD);
    }

    @Override // net.labymod.api.service.Identifiable
    public String getId() {
        return this.id;
    }

    public Class<T> getConfigClass() {
        return this.configClass;
    }

    public T getConfig() {
        return this.config;
    }

    public List<Setting> getSettings() {
        return this.settings;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NotNull
    public HudWidget<?> firstWidget() {
        return this.parent == null ? this : this.parent.firstWidget();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NotNull
    public HudWidget<?> lastWidget() {
        return this.child == null ? this : this.child.lastWidget();
    }

    public boolean contains(@NotNull HudWidget<?> widget) {
        if (equals(widget)) {
            return true;
        }
        HudWidget<?> hudWidget = this.parent;
        while (true) {
            HudWidget<?> parent = hudWidget;
            if (parent != null) {
                if (parent.equals(widget)) {
                    return true;
                }
                hudWidget = parent.parent;
            } else {
                HudWidget<?> hudWidget2 = this.child;
                while (true) {
                    HudWidget<?> child = hudWidget2;
                    if (child != null) {
                        if (child.equals(widget)) {
                            return true;
                        }
                        hudWidget2 = child.child;
                    } else {
                        return false;
                    }
                }
            }
        }
    }

    public void forEach(Consumer<HudWidget<?>> action) {
        HudWidget<?> hudWidgetFirstWidget = firstWidget();
        while (true) {
            HudWidget<?> widget = hudWidgetFirstWidget;
            if (widget != null) {
                action.accept(widget);
                hudWidgetFirstWidget = widget.child;
            } else {
                return;
            }
        }
    }

    @Nullable
    public HudWidget<?> getParent() {
        return this.parent;
    }

    public void updateParent(@Nullable HudWidget<?> parent) {
        if (parent != null && contains(parent)) {
            return;
        }
        HudWidget<?> prevParent = this.parent;
        if (prevParent != null) {
            prevParent.setChildInternal(null);
        }
        if (parent == null) {
            setParentInternal(null);
            return;
        }
        setParentInternal(parent);
        HudWidget<?> child = parent.getChild();
        if (child != null) {
            child.updateParent(lastWidget());
        }
        parent.setChildInternal(this);
    }

    private void setParentInternal(@Nullable HudWidget<?> parent) {
        this.config.setParentId(parent == null ? null : parent.getId());
        this.parent = parent;
    }

    @Nullable
    public HudWidget<?> getChild() {
        return this.child;
    }

    public void updateChild(@Nullable HudWidget<?> child) {
        if (child != null && contains(child)) {
            return;
        }
        if (child == null) {
            HudWidget<?> prevChild = getChild();
            if (prevChild != null) {
                prevChild.updateParent(null);
            }
            setChildInternal(null);
            return;
        }
        child.updateParent(this);
    }

    private void setChildInternal(@Nullable HudWidget<?> child) {
        if (child != null) {
            child.getConfig().setParentId(this.id);
        }
        this.child = child;
    }

    public Icon getIcon() {
        return this.icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
        IdeUtil.dumpSpriteIcons(this.namespace + ".hud_widget." + getId(), icon);
    }

    public boolean isEnabled() {
        return this.config.isEnabled();
    }

    public boolean isAllowed() {
        ClientPermission clientPermission;
        return this.permission == null || (clientPermission = this.permission.getPermission()) == null || clientPermission.hasCurrentlyPermission();
    }

    public HudWidget<?> getNextVisibleChild(boolean isEditorContext) {
        HudWidget<?> hudWidget;
        HudWidget<?> child = getChild();
        while (true) {
            hudWidget = child;
            if (hudWidget == null || (hudWidget.isHolderEnabled() && (hudWidget.isVisibleInGame() || isEditorContext))) {
                break;
            }
            child = hudWidget.getChild();
        }
        return hudWidget;
    }

    @NotNull
    public Component displayName() {
        return this.displayName;
    }

    public HudWidgetDropzone getAttachedDropzone() {
        if (this.dropzones.length == 0 || this.config.getDropzoneId() == null) {
            return null;
        }
        for (HudWidgetDropzone dropzone : this.dropzones) {
            if (dropzone.getId().equals(this.config.getDropzoneId())) {
                return dropzone;
            }
        }
        return null;
    }

    public HudWidgetDropzone[] getDropzones() {
        return this.dropzones;
    }

    protected final void bindCategory(HudWidgetCategory category) {
        this.category = category == null ? HudWidgetCategory.MISCELLANEOUS : category;
    }

    protected final void bindDropzones(HudWidgetDropzone... dropzones) {
        this.dropzones = new HudWidgetDropzone[dropzones.length];
        for (int i = 0; i < dropzones.length; i++) {
            this.dropzones[i] = dropzones[i].copy();
        }
    }

    public void initializePreConfigured(T config) {
    }

    public final HudWidgetCategory category() {
        return this.category;
    }

    public boolean isHolderEnabled() {
        return Laby.labyAPI().addonService().isEnabled(this.namespace);
    }

    public boolean renderInDebug() {
        return false;
    }

    public boolean canPreInitialize() {
        return true;
    }

    public boolean isResizeable() {
        return this instanceof ResizeableHudWidget;
    }

    public void requestUpdate(@NotNull String reason) throws IllegalStateException {
        if (!isEnabled()) {
            throw new IllegalStateException("Refused request because the widget is disabled");
        }
        Laby.fireEvent(new HudWidgetUpdateRequestEvent(this, reason));
    }

    public boolean handlesScaling() {
        return false;
    }

    public boolean fitsInDropzone(HudWidgetDropzone zone) {
        for (HudWidgetDropzone dropzone : this.dropzones) {
            if (dropzone.equals(zone)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasRevision() {
        return this.revision != null;
    }

    @Nullable
    public Revision getRevision() {
        return this.revision;
    }

    public void setRevision(Revision revision) {
        this.revision = revision;
    }

    public boolean hasPermission() {
        return this.permission != null;
    }

    public SettingPermissionHolder getPermission() {
        return this.permission;
    }

    public void setPermission(SettingPermissionHolder permission) {
        this.permission = permission;
    }

    private void onException(Throwable throwable) {
        Notification.builder().title(Component.text(Constants.Branding.NAME)).text(Component.translatable("labymod.hudWidget.crash_notification.text", displayName())).type(Notification.Type.SYSTEM).buildAndPush();
        if (this.config == null) {
            return;
        }
        this.config.setEnabled(false);
    }
}
