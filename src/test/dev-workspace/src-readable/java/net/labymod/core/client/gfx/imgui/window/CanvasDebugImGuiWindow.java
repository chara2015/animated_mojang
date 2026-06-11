package net.labymod.core.client.gfx.imgui.window;

import java.util.ArrayList;
import java.util.List;
import net.labymod.api.client.gfx.imgui.ImGuiWindow;
import net.labymod.api.client.gfx.imgui.LabyImGui;
import net.labymod.api.client.gfx.imgui.type.ImGuiBooleanType;
import net.labymod.api.client.gfx.imgui.viewport.ImGuiViewport;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.state.debug.CanvasDebugSnapshot;
import net.labymod.api.client.gui.screen.state.debug.CanvasDebugger;
import net.labymod.api.laby3d.vertex.VertexDescriptions;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.math.vector.FloatVector2;
import net.labymod.core.client.gui.screen.state.debug.CanvasDebugPreviewRenderer;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gfx/imgui/window/CanvasDebugImGuiWindow.class */
public class CanvasDebugImGuiWindow extends ImGuiWindow {
    private static final int COLOR_SNAPSHOT = -10053121;
    private static final int COLOR_COMPONENT = -5592406;
    private static final int COLOR_SELECTED = -256;
    private static final int COLOR_MATERIAL = -7798904;
    private static final int COLOR_SCISSOR = -30584;
    private static final int COLOR_HEADER = -6697729;
    private static final int COLOR_LABEL = -7829368;
    private static final int COLOR_VALUE = -1;
    private static final float WINDOW_WIDTH = 750.0f;
    private static final float WINDOW_HEIGHT = 550.0f;
    private static final float SCENE_PANEL_WIDTH = 280.0f;
    private static final float VIEWPORT_WIDTH = 853.0f;
    private static final float VIEWPORT_HEIGHT = 480.0f;
    private static final float ITEM_HEIGHT = 16.0f;
    private static final int MAX_VISIBLE_ITEMS = 30;
    private static final float SCROLLBAR_WIDTH = 12.0f;
    private static final int COLOR_SCROLLBAR_BG = -13421773;
    private static final int COLOR_SCROLLBAR_THUMB = -10066330;
    private static final int COLOR_SCROLLBAR_THUMB_HOVER = -7829368;
    private final List<NavigableItem> flatItems;
    private int selectedIndex;
    private int scrollOffset;
    private long lastCaptureTime;
    private int componentIndexCounter;
    private boolean isDraggingScrollbar;
    private float dragStartY;
    private int dragStartOffset;

    public CanvasDebugImGuiWindow(@Nullable ImGuiBooleanType visible) {
        super("Canvas Debug", visible, 0);
        this.flatItems = new ArrayList();
        this.selectedIndex = 0;
        this.scrollOffset = 0;
        this.lastCaptureTime = 0L;
        this.componentIndexCounter = 0;
        this.isDraggingScrollbar = false;
        this.dragStartY = 0.0f;
        this.dragStartOffset = 0;
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiWindow
    protected void prepareWindow() {
        super.prepareWindow();
        ImGuiViewport windowViewport = LabyImGui.getWindowViewport();
        FloatVector2 windowPosition = windowViewport.position();
        float x = windowPosition.getX();
        float y = windowPosition.getY();
        LabyImGui.setNextWindowPosAndSize(x + windowViewport.getWidth() + 10.0f, y, WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiWindow
    protected void renderContent() {
        ImGuiBooleanType visible = getVisible();
        boolean isEnabled = visible != null && visible.get();
        if (isEnabled != CanvasDebugger.isCaptureEnabled()) {
            CanvasDebugger.setCaptureEnabled(isEnabled);
        }
        List<CanvasDebugSnapshot> snapshots = CanvasDebugger.getSnapshots();
        if (snapshots.isEmpty()) {
            LabyImGui.text("No canvas snapshot available");
            LabyImGui.text("Enable this window to start capturing");
            return;
        }
        long captureTime = CanvasDebugger.getCaptureTimeNanos();
        if (captureTime != this.lastCaptureTime) {
            rebuildFlatList(snapshots);
            this.lastCaptureTime = captureTime;
        }
        handleKeyboardNavigation();
        LabyImGui.beginGroup();
        renderScenePanel(snapshots);
        LabyImGui.endGroup();
        LabyImGui.sameLine();
        LabyImGui.beginGroup();
        renderViewport(snapshots);
        LabyImGui.endGroup();
        LabyImGui.separator();
        renderInspector();
    }

    private void renderScenePanel(List<CanvasDebugSnapshot> snapshots) {
        String expandMarker;
        renderSectionHeader("Scene Hierarchy");
        LabyImGui.pushStyleColor(0, ColorFormat.ARGB32.packTo(ColorFormat.ABGR32, -7829368));
        LabyImGui.text(totalComponents(snapshots) + " components, " + snapshots.size() + " snapshots");
        LabyImGui.popStyleColor();
        LabyImGui.separator();
        FloatVector2 windowPos = LabyImGui.getWindowPosition();
        FloatVector2 listStartPos = LabyImGui.getCursorScreenPos();
        float listStartY = listStartPos.getY();
        int visibleStart = this.scrollOffset;
        int visibleEnd = Math.min(this.flatItems.size(), visibleStart + 30);
        int totalItems = this.flatItems.size();
        int i = visibleStart;
        while (i < visibleEnd) {
            NavigableItem item = this.flatItems.get(i);
            boolean isSelected = i == this.selectedIndex;
            String indent = "  ".repeat(item.getDepth());
            if (item.hasChildren()) {
                expandMarker = item.isExpanded() ? "[-] " : "[+] ";
            } else {
                expandMarker = "    ";
            }
            int color = isSelected ? COLOR_SELECTED : item.getColor();
            LabyImGui.pushStyleColor(0, ColorFormat.ARGB32.packTo(ColorFormat.ABGR32, color));
            String label = indent + expandMarker + item.getLabel();
            if (item.getComponentIndex() >= 0) {
                label = label + " [" + item.getComponentIndex() + "]";
            }
            LabyImGui.text(label);
            if (LabyImGui.isItemClicked()) {
                this.selectedIndex = i;
            }
            LabyImGui.popStyleColor();
            i++;
        }
        int emptySlots = 30 - (visibleEnd - visibleStart);
        for (int i2 = 0; i2 < emptySlots; i2++) {
            LabyImGui.text("");
        }
        if (totalItems > 30) {
            renderScrollbar(((windowPos.getX() + SCENE_PANEL_WIDTH) - SCROLLBAR_WIDTH) - 5.0f, listStartY, VIEWPORT_HEIGHT, totalItems);
        }
    }

    private int totalComponents(List<CanvasDebugSnapshot> snapshots) {
        int total = 0;
        for (CanvasDebugSnapshot snapshot : snapshots) {
            total += snapshot.getComponents().size();
        }
        return total;
    }

    private void renderScrollbar(float x, float y, float height, int totalItems) {
        int i;
        int maxScroll = Math.max(0, totalItems - 30);
        float thumbHeight = Math.max(20.0f, height * (30.0f / totalItems));
        float trackSpace = height - thumbHeight;
        float thumbY = y + (maxScroll > 0 ? (trackSpace * this.scrollOffset) / maxScroll : 0.0f);
        FloatVector2 mousePos = LabyImGui.getMousePos();
        float mouseX = mousePos.getX();
        float mouseY = mousePos.getY();
        boolean isOverThumb = mouseX >= x && mouseX <= x + SCROLLBAR_WIDTH && mouseY >= thumbY && mouseY <= thumbY + thumbHeight;
        LabyImGui.setCursorScreenPos(x, y);
        LabyImGui.invisibleButton("##scrollbar_track", SCROLLBAR_WIDTH, height);
        boolean scrollbarClicked = LabyImGui.isItemClicked();
        if (scrollbarClicked && isOverThumb) {
            this.isDraggingScrollbar = true;
            this.dragStartY = mouseY;
            this.dragStartOffset = this.scrollOffset;
        }
        if (this.isDraggingScrollbar) {
            if (LabyImGui.isMouseDown(0)) {
                float deltaY = mouseY - this.dragStartY;
                if (trackSpace > 0.0f) {
                    float scrollPerPixel = maxScroll / trackSpace;
                    int newOffset = this.dragStartOffset + ((int) (deltaY * scrollPerPixel));
                    this.scrollOffset = Math.max(0, Math.min(maxScroll, newOffset));
                }
            } else {
                this.isDraggingScrollbar = false;
            }
        }
        if (scrollbarClicked && !isOverThumb) {
            if (mouseY < thumbY) {
                this.scrollOffset = Math.max(0, this.scrollOffset - 30);
            } else {
                this.scrollOffset = Math.min(maxScroll, this.scrollOffset + 30);
            }
        }
        LabyImGui.addRectangleFilled(x, y, x + SCROLLBAR_WIDTH, y + height, ColorFormat.ARGB32.packTo(ColorFormat.ABGR32, COLOR_SCROLLBAR_BG), 3.0f);
        if (isOverThumb || this.isDraggingScrollbar) {
            i = -7829368;
        } else {
            i = COLOR_SCROLLBAR_THUMB;
        }
        int thumbColor = i;
        LabyImGui.addRectangleFilled(x + 2.0f, thumbY + 2.0f, (x + SCROLLBAR_WIDTH) - 2.0f, (thumbY + thumbHeight) - 2.0f, ColorFormat.ARGB32.packTo(ColorFormat.ABGR32, thumbColor), 3.0f);
    }

    private void renderViewport(List<CanvasDebugSnapshot> snapshots) {
        renderSectionHeader("Viewport");
        NavigableItem selected = getSelectedItem();
        int componentIndex = selected != null ? selected.getComponentIndex() : -1;
        if (componentIndex >= 0) {
            LabyImGui.pushStyleColor(0, ColorFormat.ARGB32.packTo(ColorFormat.ABGR32, -7829368));
            LabyImGui.text("Rendering elements 0 to " + componentIndex);
            LabyImGui.popStyleColor();
        } else {
            LabyImGui.pushStyleColor(0, ColorFormat.ARGB32.packTo(ColorFormat.ABGR32, -7829368));
            LabyImGui.text("Select a component to preview");
            LabyImGui.popStyleColor();
        }
        LabyImGui.separator();
        if (componentIndex >= 0) {
            CanvasDebugPreviewRenderer renderer = CanvasDebugPreviewRenderer.getInstance();
            renderer.renderPreview(snapshots, componentIndex);
            int textureId = renderer.getPreviewTextureId();
            if (textureId > 0) {
                LabyImGui.image(textureId, VIEWPORT_WIDTH, VIEWPORT_HEIGHT, 0.0f, 1.0f, 1.0f, 0.0f);
                return;
            }
            return;
        }
        LabyImGui.pushStyleColor(0, ColorFormat.ARGB32.packTo(ColorFormat.ABGR32, -7829368));
        for (int i = 0; i < 14; i++) {
            LabyImGui.text("");
        }
        LabyImGui.popStyleColor();
    }

    private void renderInspector() {
        renderSectionHeader("Inspector");
        NavigableItem selected = getSelectedItem();
        if (selected == null) {
            CanvasDebugger.setSelectedBounds(null);
            LabyImGui.pushStyleColor(0, ColorFormat.ARGB32.packTo(ColorFormat.ABGR32, -7829368));
            LabyImGui.text("No component selected");
            LabyImGui.popStyleColor();
            return;
        }
        if (selected.getComponent() != null) {
            CanvasDebugSnapshot.ComponentSnapshot component = selected.getComponent();
            Rectangle bounds = component.getBounds();
            CanvasDebugger.setSelectedBounds(bounds);
            LabyImGui.beginGroup();
            renderLabel("Type");
            LabyImGui.sameLine(80.0f);
            renderValue(component.getTypeName());
            LabyImGui.endGroup();
            if (bounds != null) {
                LabyImGui.beginGroup();
                renderLabel(VertexDescriptions.POSITION_NAME);
                LabyImGui.sameLine(80.0f);
                renderValue(String.format("(%.1f, %.1f)", Float.valueOf(bounds.getX()), Float.valueOf(bounds.getY())));
                LabyImGui.endGroup();
                LabyImGui.sameLine(220.0f);
                LabyImGui.beginGroup();
                renderLabel("Size");
                LabyImGui.sameLine(80.0f);
                renderValue(String.format("%.1f x %.1f", Float.valueOf(bounds.getWidth()), Float.valueOf(bounds.getHeight())));
                LabyImGui.endGroup();
            }
            if (component.getMaterialId() != null) {
                LabyImGui.beginGroup();
                renderLabel("Material");
                LabyImGui.sameLine(80.0f);
                LabyImGui.pushStyleColor(0, ColorFormat.ARGB32.packTo(ColorFormat.ABGR32, COLOR_MATERIAL));
                LabyImGui.text(component.getMaterialId().toString());
                LabyImGui.popStyleColor();
                LabyImGui.endGroup();
            }
            if (component.hasScissor()) {
                LabyImGui.sameLine(220.0f);
                LabyImGui.pushStyleColor(0, ColorFormat.ARGB32.packTo(ColorFormat.ABGR32, COLOR_SCISSOR));
                LabyImGui.text("Has Scissor");
                LabyImGui.popStyleColor();
            }
            if (selected.getComponentIndex() >= 0) {
                LabyImGui.beginGroup();
                renderLabel("Index");
                LabyImGui.sameLine(80.0f);
                renderValue(String.valueOf(selected.getComponentIndex()));
                LabyImGui.endGroup();
            }
        }
        if (selected.getSnapshot() != null) {
            CanvasDebugger.setSelectedBounds(null);
            CanvasDebugSnapshot snapshot = selected.getSnapshot();
            LabyImGui.beginGroup();
            renderLabel("Snapshot");
            LabyImGui.sameLine(80.0f);
            renderValue(String.valueOf(snapshot.getIndex()));
            LabyImGui.endGroup();
            LabyImGui.sameLine(220.0f);
            LabyImGui.beginGroup();
            renderLabel("Components");
            LabyImGui.sameLine(80.0f);
            renderValue(String.valueOf(snapshot.getComponents().size()));
            LabyImGui.endGroup();
        }
    }

    private void renderSectionHeader(String title) {
        LabyImGui.pushStyleColor(0, ColorFormat.ARGB32.packTo(ColorFormat.ABGR32, COLOR_HEADER));
        LabyImGui.text(title);
        LabyImGui.popStyleColor();
    }

    private void renderLabel(String label) {
        LabyImGui.pushStyleColor(0, ColorFormat.ARGB32.packTo(ColorFormat.ABGR32, -7829368));
        LabyImGui.text(label + ":");
        LabyImGui.popStyleColor();
    }

    private void renderValue(String value) {
        LabyImGui.pushStyleColor(0, ColorFormat.ARGB32.packTo(ColorFormat.ABGR32, -1));
        LabyImGui.text(value);
        LabyImGui.popStyleColor();
    }

    private void handleKeyboardNavigation() {
        NavigableItem current;
        NavigableItem current2;
        if (LabyImGui.isWindowHovered()) {
            float wheel = LabyImGui.getMouseWheel();
            if (wheel != 0.0f) {
                int maxScroll = Math.max(0, this.flatItems.size() - 30);
                int scrollDelta = (int) ((-wheel) * 3.0f);
                this.scrollOffset = Math.max(0, Math.min(maxScroll, this.scrollOffset + scrollDelta));
            }
        }
        if (!LabyImGui.isWindowFocused()) {
            return;
        }
        if (LabyImGui.isKeyPressed(Key.ARROW_UP)) {
            this.selectedIndex = Math.max(0, this.selectedIndex - 1);
        }
        if (LabyImGui.isKeyPressed(Key.ARROW_DOWN)) {
            this.selectedIndex = Math.min(this.flatItems.size() - 1, this.selectedIndex + 1);
        }
        if (LabyImGui.isKeyPressed(Key.ARROW_LEFT) && (current2 = getSelectedItem()) != null && current2.isExpanded()) {
            current2.setExpanded(false);
            rebuildFlatListFromCurrent();
        }
        if (LabyImGui.isKeyPressed(Key.ARROW_RIGHT) && (current = getSelectedItem()) != null && current.hasChildren() && !current.isExpanded()) {
            current.setExpanded(true);
            rebuildFlatListFromCurrent();
        }
        if (LabyImGui.isKeyPressed(Key.PAGE_UP)) {
            this.selectedIndex = Math.max(0, this.selectedIndex - 30);
        }
        if (LabyImGui.isKeyPressed(Key.PAGE_DOWN)) {
            this.selectedIndex = Math.min(this.flatItems.size() - 1, this.selectedIndex + 30);
        }
    }

    private void rebuildFlatList(List<CanvasDebugSnapshot> snapshots) {
        this.flatItems.clear();
        this.componentIndexCounter = 0;
        for (CanvasDebugSnapshot snapshot : snapshots) {
            addSnapshotToFlatList(snapshot);
        }
        if (this.selectedIndex >= this.flatItems.size()) {
            this.selectedIndex = Math.max(0, this.flatItems.size() - 1);
        }
    }

    private void addSnapshotToFlatList(CanvasDebugSnapshot snapshot) {
        NavigableItem snapshotItem = new NavigableItem("Snapshot " + snapshot.getIndex(), 0, COLOR_SNAPSHOT, null, snapshot, -1);
        snapshotItem.setHasChildren(!snapshot.getComponents().isEmpty());
        this.flatItems.add(snapshotItem);
        if (snapshotItem.isExpanded()) {
            for (CanvasDebugSnapshot.ComponentSnapshot component : snapshot.getComponents()) {
                String typeName = component.getTypeName();
                int i = this.componentIndexCounter;
                this.componentIndexCounter = i + 1;
                NavigableItem componentItem = new NavigableItem(typeName, 1, COLOR_COMPONENT, component, null, i);
                this.flatItems.add(componentItem);
            }
            return;
        }
        this.componentIndexCounter += snapshot.getComponents().size();
    }

    private void rebuildFlatListFromCurrent() {
        List<CanvasDebugSnapshot> snapshots = CanvasDebugger.getSnapshots();
        if (snapshots.isEmpty()) {
            return;
        }
        NavigableItem current = getSelectedItem();
        String currentLabel = current != null ? current.getLabel() : null;
        rebuildFlatList(snapshots);
        if (currentLabel != null) {
            for (int i = 0; i < this.flatItems.size(); i++) {
                if (this.flatItems.get(i).getLabel().equals(currentLabel)) {
                    this.selectedIndex = i;
                    return;
                }
            }
        }
    }

    @Nullable
    private NavigableItem getSelectedItem() {
        if (this.selectedIndex >= 0 && this.selectedIndex < this.flatItems.size()) {
            return this.flatItems.get(this.selectedIndex);
        }
        return null;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gfx/imgui/window/CanvasDebugImGuiWindow$NavigableItem.class */
    private static class NavigableItem {
        private final String label;
        private final int depth;
        private final int color;

        @Nullable
        private final CanvasDebugSnapshot.ComponentSnapshot component;

        @Nullable
        private final CanvasDebugSnapshot snapshot;
        private final int componentIndex;
        private boolean expanded = true;
        private boolean hasChildren = false;

        NavigableItem(String label, int depth, int color, @Nullable CanvasDebugSnapshot.ComponentSnapshot component, @Nullable CanvasDebugSnapshot snapshot, int componentIndex) {
            this.label = label;
            this.depth = depth;
            this.color = color;
            this.component = component;
            this.snapshot = snapshot;
            this.componentIndex = componentIndex;
        }

        String getLabel() {
            return this.label;
        }

        int getDepth() {
            return this.depth;
        }

        int getColor() {
            return this.color;
        }

        @Nullable
        CanvasDebugSnapshot.ComponentSnapshot getComponent() {
            return this.component;
        }

        @Nullable
        CanvasDebugSnapshot getSnapshot() {
            return this.snapshot;
        }

        int getComponentIndex() {
            return this.componentIndex;
        }

        boolean isExpanded() {
            return this.expanded;
        }

        void setExpanded(boolean expanded) {
            this.expanded = expanded;
        }

        boolean hasChildren() {
            return this.hasChildren;
        }

        void setHasChildren(boolean hasChildren) {
            this.hasChildren = hasChildren;
        }
    }
}
