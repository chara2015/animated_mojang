package net.labymod.core.client.gfx.imgui.window;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.imgui.ImGuiWindow;
import net.labymod.api.client.gfx.imgui.LabyImGui;
import net.labymod.api.client.gfx.imgui.type.ImGuiBooleanType;
import net.labymod.api.client.gfx.imgui.window.DocumentHandler;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.event.client.input.KeyEvent;
import net.labymod.api.util.StringUtil;
import net.labymod.api.util.color.format.ColorFormat;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gfx/imgui/window/DocumentImGuiWindow.class */
public class DocumentImGuiWindow extends ImGuiWindow {
    private static final int VISIBLE_HOVERED_ARGB = -1;
    private static final int VISIBLE_INVALID_ARGB = -52429;
    private static final int VISIBLE_VALID_DEPTH_0_ARGB = -14518273;
    private static final int VISIBLE_VALID_DEPTH_1_ARGB = -10061825;
    private static final int NOT_VISIBLE_ARGB = -12303292;
    private final DocumentHandler documentHandler;

    public DocumentImGuiWindow(@Nullable ImGuiBooleanType open) {
        super("Document", open, 0);
        this.documentHandler = Laby.references().documentHandler();
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiWindow
    protected void renderContent() {
        Object selectedActivity = this.documentHandler.getSelectedActivity();
        if (selectedActivity == null) {
            this.documentHandler.resetSelectedActivity();
        } else {
            String name = "";
            if (selectedActivity instanceof Activity) {
                name = ((Activity) selectedActivity).getName();
            }
            if (LabyImGui.beginCombo("##", name)) {
                Iterator<Activity> it = this.documentHandler.getActivities().iterator();
                while (it.hasNext()) {
                    Activity activity = it.next();
                    boolean isSelected = selectedActivity == activity;
                    if (LabyImGui.selectable(activity.getName(), isSelected)) {
                        selectedActivity = activity;
                        this.documentHandler.setSelectedActivity(activity);
                    }
                    if (isSelected) {
                        LabyImGui.setItemDefaultFocus();
                    }
                }
                LabyImGui.endCombo();
            }
            LabyImGui.separator();
            if (selectedActivity instanceof Activity) {
                renderElement(((Activity) selectedActivity).document());
            }
        }
        if (LabyImGui.isWindowFocused() && LabyImGui.isKeyPressed(Key.TAB)) {
            this.documentHandler.onKey(new KeyEvent(KeyEvent.State.PRESS, Key.TAB));
        }
        this.documentHandler.clear();
    }

    private void renderElement(Object widget) {
        Widget acutalWidget = null;
        if (widget instanceof Widget) {
            acutalWidget = (Widget) widget;
        }
        if (acutalWidget == null) {
            return;
        }
        List<? extends Widget> children = acutalWidget.getChildren();
        LabyImGui.pushId(acutalWidget.getUniqueId());
        boolean hovered = acutalWidget.isHovered();
        if (hovered) {
            this.documentHandler.setTargetWidget(acutalWidget);
        }
        if (this.documentHandler.isSelectedWidget(acutalWidget)) {
            this.documentHandler.setTargetWidget(acutalWidget);
        }
        boolean visible = acutalWidget.isVisible();
        boolean invalid = visible && (acutalWidget.bounds().getWidth() <= 0.0f || acutalWidget.bounds().getHeight() <= 0.0f);
        int color = evaluateElementARGB(visible, hovered, invalid);
        boolean open = buildWidgetItem(acutalWidget, color);
        if (open) {
            for (Widget child : children) {
                renderElement(child);
            }
            LabyImGui.popTree();
        }
        LabyImGui.popId();
    }

    private boolean buildWidgetItem(Widget widget, int color) {
        int treeNodeFlags;
        List<CharSequence> ids = widget.getIds();
        boolean noIds = ids.isEmpty();
        if (widget.getChildren().isEmpty()) {
            treeNodeFlags = 4228 | 256;
        } else {
            treeNodeFlags = 4228 | 32;
        }
        if (this.documentHandler.isSelectedWidget(widget)) {
            treeNodeFlags |= 1;
        }
        LabyImGui.pushStyleColor(0, ColorFormat.ARGB32.packTo(ColorFormat.ABGR32, color));
        LabyImGui.pushStyleColor(24, ColorFormat.ARGB32.packTo(ColorFormat.ABGR32, 553647872));
        LabyImGui.pushStyleColor(26, ColorFormat.ARGB32.packTo(ColorFormat.ABGR32, 1342242815));
        LabyImGui.pushStyleColor(25, ColorFormat.ARGB32.packTo(ColorFormat.ABGR32, 536936447));
        LabyImGui.pushId(widget.getUniqueId() + "_treeNode");
        boolean open = LabyImGui.treeNodeEx(widget.toString(), treeNodeFlags);
        if (LabyImGui.isItemHovered()) {
            this.documentHandler.setTargetWidget(widget);
        }
        if (LabyImGui.isItemClicked()) {
            this.documentHandler.setSelectedWidget(widget);
        }
        LabyImGui.popId();
        LabyImGui.popStyleColor();
        LabyImGui.popStyleColor();
        LabyImGui.popStyleColor();
        LabyImGui.popStyleColor();
        widget.renderExtraDebugInformation();
        if (!noIds) {
            LabyImGui.sameLine(0.0f, 0.0f);
            String widgetIds = " (" + StringUtil.join((Collection<?>) ids, (CharSequence) ", ") + ")";
            LabyImGui.text(widgetIds, 180, 180, 180, 255);
        }
        return open;
    }

    private int evaluateElementARGB(boolean visible, boolean hovered, boolean invalid) {
        if (visible) {
            if (hovered) {
                return -1;
            }
            if (invalid) {
                return VISIBLE_INVALID_ARGB;
            }
            return VISIBLE_VALID_DEPTH_0_ARGB;
        }
        return NOT_VISIBLE_ARGB;
    }
}
