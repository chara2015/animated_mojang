package net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer;

import java.util.Iterator;
import java.util.List;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.action.ListSession;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerEntryWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.network.server.storage.MoveActionType;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.util.bounds.ReasonableMutableRectangle;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.time.TimeUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/activity/multiplayer/ServerListWidget.class */
@AutoWidget
public class ServerListWidget<Entry extends ServerEntryWidget> extends VerticalListWidget<Entry> {
    private static final ModifyReason LIST_CONTENT = ModifyReason.of("listContent");
    private ServerMover<Entry> mover;
    private MoveActionType actionType;
    private MoveActionType prevActionType;
    private MoveActionType dropActionType;
    private Entry draggingWidget;
    private Entry targetWidget;
    private Entry prevTargetWidget;
    private Entry dropTargetWidget;
    private Entry releasedDraggingWidget;
    private float draggingOffsetX;
    private float draggingOffsetY;
    private float releasedDraggingFromX;
    private float releasedDraggingFromY;
    private float releasedDraggingToX;
    private float releasedDraggingToY;
    private float mouseClickX;
    private float mouseClickY;
    private long targetTimePassed;
    private long draggingTimeReleased;
    private boolean canStartDragging;
    private boolean mouseMoved;
    private boolean keepOldPosition;
    private boolean dropListVisible;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/activity/multiplayer/ServerListWidget$ServerMover.class */
    public interface ServerMover<Entry> {
        void move(Entry entry, Entry entry2, MoveActionType moveActionType);
    }

    public ServerListWidget(ListSession<Entry> session) {
        super(session);
        this.prevActionType = MoveActionType.NONE;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget, net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.Element
    public void postInitialize() {
        super.postInitialize();
        if (this.releasedDraggingWidget != null) {
            String string = this.releasedDraggingWidget.toString();
            ServerEntryWidget serverEntryWidget = null;
            boolean z = false;
            Iterator it = this.children.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                ServerEntryWidget serverEntryWidget2 = (ServerEntryWidget) it.next();
                if (serverEntryWidget2.toString().equals(string)) {
                    if (serverEntryWidget != null) {
                        z = true;
                        break;
                    }
                    serverEntryWidget = serverEntryWidget2;
                }
            }
            this.releasedDraggingWidget = z ? null : (Entry) serverEntryWidget;
            this.draggingTimeReleased = TimeUtil.getCurrentTimeMillis();
            updateContentBounds();
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget, net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void renderWidget(ScreenContext context) {
        for (T t : this.children) {
            t.setSelected(selectable().get().booleanValue() && t.equals(this.session.getSelectedEntry()));
        }
        if (this.releasedDraggingWidget != null) {
            Bounds bounds = this.releasedDraggingWidget.bounds();
            float progress = (float) CubicBezier.EASE_OUT.curve(Math.min(1.0f, (TimeUtil.getCurrentTimeMillis() - this.draggingTimeReleased) / 100.0f));
            if (progress > 0.0f && progress < 1.0f) {
                bounds.setOuterPosition(this.releasedDraggingFromX + ((this.releasedDraggingToX - this.releasedDraggingFromX) * progress), this.releasedDraggingFromY + ((this.releasedDraggingToY - this.releasedDraggingFromY) * progress), LIST_CONTENT);
            } else {
                this.releasedDraggingWidget = null;
                updateContentBounds();
            }
        }
        updateOutOfBoundsState();
        for (T t2 : this.children) {
            if (renderOutOfBounds().get().booleanValue() || !t2.isOutOfBounds()) {
                if (t2 != this.draggingWidget) {
                    t2.render(context);
                }
            }
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget, net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        if (this.labyAPI.config().multiplayer().serverList().draggableEntries().get().booleanValue()) {
            this.canStartDragging = true;
            this.mouseClickX = mouse.getX();
            this.mouseClickY = mouse.getY();
        }
        return super.mouseClicked(mouse, mouseButton);
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseDragged(MutableMouse mouse, MouseButton button, double deltaX, double deltaY) {
        MoveActionType moveActionType;
        float offsetX = Math.abs(mouse.getX() - this.mouseClickX);
        float offsetY = Math.abs(mouse.getY() - this.mouseClickY);
        boolean hasMoved = offsetX > 10.0f || offsetY > 10.0f;
        if (hasMoved) {
            this.mouseMoved = true;
        }
        if (this.draggingWidget != null && this.draggingOffsetX == 0.0f && this.draggingOffsetY == 0.0f) {
            Bounds bounds = this.draggingWidget.bounds();
            this.draggingOffsetX = this.mouseClickX - bounds.getX();
            this.draggingOffsetY = this.mouseClickY - bounds.getY();
        }
        if (this.mover != null && this.draggingWidget == null && button == MouseButton.LEFT && this.canStartDragging && hasMoved) {
            this.canStartDragging = false;
            List<T> childrenAt = getChildrenAt((int) this.mouseClickX, (int) this.mouseClickY);
            if (!childrenAt.isEmpty()) {
                Entry entry = (Entry) childrenAt.get(0);
                if (entry.isMoveable()) {
                    this.draggingWidget = entry;
                    this.keepOldPosition = false;
                    int size = this.children.size();
                    if (size != 1) {
                        int index = this.children.indexOf(this.draggingWidget);
                        boolean below = size > index + 1;
                        ServerEntryWidget serverEntryWidget = below ? (ServerEntryWidget) this.children.get(index + 1) : (ServerEntryWidget) this.children.get(index - 1);
                        if (below) {
                            moveActionType = MoveActionType.INSERT_ABOVE;
                        } else {
                            moveActionType = MoveActionType.INSERT_BELOW;
                        }
                        setTargetAction(serverEntryWidget, moveActionType);
                    }
                    this.keepOldPosition = true;
                    this.prevActionType = MoveActionType.NONE;
                    updateContentBounds();
                    return true;
                }
            }
        }
        if (this.draggingWidget != null) {
            this.draggingWidget.bounds().setOuterPosition(mouse.getX() - this.draggingOffsetX, mouse.getY() - this.draggingOffsetY, LIST_CONTENT);
            updateContentBounds();
            if (this.dropListVisible) {
                addIdSilent("drop-list");
            } else {
                removeIdSilent("drop-list");
            }
        }
        return super.mouseDragged(mouse, button, deltaX, deltaY);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseReleased(MutableMouse mouse, MouseButton mouseButton) {
        if (this.draggingWidget != null && this.mover != null) {
            Rectangle bounds = this.draggingWidget.bounds();
            this.releasedDraggingWidget = this.draggingWidget;
            this.draggingTimeReleased = TimeUtil.getCurrentTimeMillis();
            this.releasedDraggingFromX = bounds.getX();
            this.releasedDraggingFromY = bounds.getY();
            this.mover.move(this.draggingWidget, this.dropTargetWidget, this.dropActionType);
            this.draggingWidget = null;
            this.actionType = MoveActionType.NONE;
            this.targetWidget = null;
            this.dropTargetWidget = null;
            this.dropActionType = MoveActionType.NONE;
            removeId("drop-list");
            updateContentBounds();
        }
        this.canStartDragging = false;
        this.draggingOffsetX = 0.0f;
        this.draggingOffsetY = 0.0f;
        return super.mouseReleased(mouse, mouseButton);
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget, net.labymod.api.client.gui.screen.widget.AbstractWidget
    protected void updateContentBounds() {
        float transitionProgress;
        float spaceBetweenEntries = spaceBetweenEntries().get().floatValue();
        float draggingWidgetHeight = 0.0f;
        if (this.draggingWidget != null) {
            draggingWidgetHeight = this.draggingWidget.bounds().getHeight(BoundsType.OUTER) + spaceBetweenEntries;
            Rectangle draggingBounds = this.draggingWidget.bounds().rectangle(BoundsType.INNER);
            float draggingY = draggingBounds.getY() + this.draggingOffsetY;
            boolean hasTarget = false;
            ServerEntryWidget serverEntryWidget = null;
            ServerEntryWidget serverEntryWidget2 = null;
            float offsetY = 0.0f;
            Iterator it = this.children.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                ServerEntryWidget serverEntryWidget3 = (ServerEntryWidget) it.next();
                if (serverEntryWidget3.isVisible() && serverEntryWidget3.isMoveable()) {
                    if (serverEntryWidget3 == this.draggingWidget) {
                        if (this.keepOldPosition) {
                            offsetY -= draggingWidgetHeight;
                        }
                    } else {
                        if (serverEntryWidget3 == this.targetWidget && this.actionType == MoveActionType.INSERT_ABOVE) {
                            offsetY += draggingWidgetHeight;
                        }
                        Rectangle childBounds = serverEntryWidget3.bounds().rectangle(BoundsType.OUTER);
                        float childHeight = childBounds.getHeight();
                        float childY = childBounds.getY() - offsetY;
                        float slice = childHeight / 3.0f;
                        boolean isInBetween = draggingY >= childY + slice && draggingY <= (childY + childHeight) - slice;
                        boolean isAbove = draggingY >= childY && draggingY < childY + slice;
                        boolean isBelow = draggingY > (childY + childHeight) - slice && draggingY <= childY + childHeight;
                        if (isAbove) {
                            setTargetAction(serverEntryWidget3, MoveActionType.INSERT_ABOVE);
                            hasTarget = true;
                            break;
                        }
                        if (isBelow) {
                            setTargetAction(serverEntryWidget3, MoveActionType.INSERT_BELOW);
                            hasTarget = true;
                            break;
                        }
                        if (isInBetween) {
                            setTargetAction(serverEntryWidget3, MoveActionType.ADD_TO_FOLDER);
                            hasTarget = true;
                            break;
                        }
                        if (serverEntryWidget3 == this.targetWidget && this.actionType == MoveActionType.INSERT_BELOW) {
                            offsetY += draggingWidgetHeight;
                        }
                        if (draggingY > (childY + childHeight) - slice) {
                            serverEntryWidget2 = serverEntryWidget3;
                        }
                        if (serverEntryWidget == null && draggingY < childY) {
                            serverEntryWidget = serverEntryWidget3;
                        }
                    }
                }
            }
            if (draggingY < bounds().getY()) {
                setTargetAction(serverEntryWidget, MoveActionType.NONE);
            }
            if (!hasTarget && serverEntryWidget2 != null) {
                setTargetAction(serverEntryWidget2, this.keepOldPosition ? MoveActionType.NONE : MoveActionType.INSERT_BELOW);
            }
        }
        long timePassedSinceTargetUpdate = TimeUtil.getCurrentTimeMillis() - this.targetTimePassed;
        if (this.keepOldPosition) {
            transitionProgress = 1.0f;
        } else {
            transitionProgress = Math.min(1.0f, timePassedSinceTargetUpdate / 200.0f);
        }
        float transitionProgress2 = (float) CubicBezier.EASE_IN_OUT.curve(transitionProgress);
        float y = 0.0f;
        ReasonableMutableRectangle bounds = bounds().rectangle(BoundsType.INNER);
        for (T t : this.children) {
            if (t.isVisible() && t != this.draggingWidget) {
                if (t == this.targetWidget && this.actionType == MoveActionType.INSERT_ABOVE) {
                    y += draggingWidgetHeight * transitionProgress2;
                }
                if (t == this.prevTargetWidget && this.prevActionType == MoveActionType.INSERT_ABOVE) {
                    y += draggingWidgetHeight * (1.0f - transitionProgress2);
                }
                Bounds childBounds2 = t.bounds();
                if (this.releasedDraggingWidget != null && this.releasedDraggingWidget.getListIndex() == t.getListIndex()) {
                    this.releasedDraggingToX = bounds.getX();
                    this.releasedDraggingToY = bounds.getY() + y;
                } else {
                    childBounds2.setOuterPosition(bounds.getLeft(), bounds.getTop() + y, LIST_CONTENT);
                }
                childBounds2.setOuterWidth(bounds.getWidth(), LIST_CONTENT);
                if (t == this.targetWidget && this.actionType == MoveActionType.INSERT_BELOW) {
                    y += draggingWidgetHeight * transitionProgress2;
                }
                if (t == this.prevTargetWidget && this.prevActionType == MoveActionType.INSERT_BELOW) {
                    y += draggingWidgetHeight * (1.0f - transitionProgress2);
                }
                y += childBounds2.getHeight(BoundsType.OUTER) + spaceBetweenEntries;
            }
        }
        handleSizeAttributes();
    }

    private void setTargetAction(Entry target, MoveActionType actionType) {
        if (target != this.targetWidget || actionType != this.actionType) {
            long timePassedSinceTargetUpdate = TimeUtil.getCurrentTimeMillis() - this.targetTimePassed;
            if (timePassedSinceTargetUpdate < 200 || !this.mouseMoved) {
                return;
            }
            if (this.keepOldPosition) {
                ServerEntryWidget serverEntryWidget = null;
                ServerEntryWidget serverEntryWidget2 = null;
                boolean found = false;
                Iterator it = this.children.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    ServerEntryWidget serverEntryWidget3 = (ServerEntryWidget) it.next();
                    if (serverEntryWidget3 == this.draggingWidget) {
                        found = true;
                    } else {
                        if (found) {
                            serverEntryWidget2 = serverEntryWidget3;
                            break;
                        }
                        serverEntryWidget = serverEntryWidget3;
                    }
                }
                if (target == serverEntryWidget && actionType == MoveActionType.INSERT_BELOW) {
                    return;
                }
                if (target == serverEntryWidget2 && actionType == MoveActionType.INSERT_ABOVE) {
                    return;
                }
                if (actionType == MoveActionType.ADD_TO_FOLDER || actionType == MoveActionType.NONE) {
                    if (this.prevTargetWidget != target || this.prevActionType != actionType) {
                        this.dropTargetWidget = target;
                        this.dropActionType = actionType;
                        this.dropListVisible = actionType == MoveActionType.ADD_TO_FOLDER;
                        return;
                    }
                    return;
                }
                this.keepOldPosition = false;
            }
            this.prevTargetWidget = this.targetWidget;
            this.prevActionType = this.actionType;
            this.targetWidget = target;
            this.actionType = actionType;
            this.targetTimePassed = TimeUtil.getCurrentTimeMillis();
            this.dropTargetWidget = target;
            this.dropActionType = actionType;
            this.dropListVisible = actionType == MoveActionType.ADD_TO_FOLDER;
            this.mouseMoved = false;
        }
    }

    private void addIdSilent(String id) {
        if (!hasId(id)) {
            getIds().add(id);
            reloadStyleSheets(false);
        }
    }

    private void removeIdSilent(String id) {
        if (hasId(id)) {
            getIds().remove(id);
        }
    }

    public Entry getDraggingWidget() {
        return this.draggingWidget;
    }

    public void registerServerMover(ServerMover<Entry> mover) {
        this.mover = mover;
    }
}
