package net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer;

import java.util.function.Consumer;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.SelectionWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerInfoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.FloatVector2;
import net.labymod.api.util.time.TimeUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/activity/multiplayer/ServerEntryWidget.class */
public abstract class ServerEntryWidget extends AbstractWidget<Widget> {
    protected Consumer<ServerInfoWidget.Movable> moveHandler;
    private float mouseClickX = -1.0f;
    private float mouseClickY = -1.0f;
    private final AnimationData animationData = new AnimationData();
    private final FloatVector2 draggingPivot = new FloatVector2();
    protected ServerInfoWidget.Movable movable = ServerInfoWidget.Movable.NO;

    public abstract int getListIndex();

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        this.mouseClickX = mouse.getX();
        this.mouseClickY = mouse.getY();
        return super.mouseClicked(mouse, mouseButton);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void renderWidget(ScreenContext context) {
        MutableMouse mouse = context.mouse();
        mouse.transform(bounds().rectangle(BoundsType.OUTER), () -> {
            float ox = this.draggingPivot.getX();
            float oy = this.draggingPivot.getY();
            float x = MathHelper.lerp(mouse.getX(), ox);
            float y = MathHelper.lerp(mouse.getY(), oy);
            this.draggingPivot.set(x, y);
        });
        super.renderWidget(context);
        Widget iconWidget = getIconWidget();
        boolean mouseMoved = (this.mouseClickX == -1.0f || this.mouseClickY == -1.0f || (this.mouseClickX == ((float) mouse.getX()) && this.mouseClickY == ((float) mouse.getY()))) ? false : true;
        if (iconWidget == null || !isHovered()) {
            return;
        }
        if (!isDragging() || !mouseMoved) {
            for (Widget child : iconWidget.getChildren()) {
                child.render(context);
            }
        }
    }

    public void setMovable(ServerInfoWidget.Movable movable, Consumer<ServerInfoWidget.Movable> moveHandler) {
        this.movable = movable;
        this.moveHandler = moveHandler;
    }

    protected void addSelectionWidgets(IconWidget parent) {
        if (this.movable == ServerInfoWidget.Movable.NO) {
            return;
        }
        DivWidget overlay = new DivWidget();
        overlay.addId("overlay");
        parent.addChild(overlay);
        if (this.movable == ServerInfoWidget.Movable.UP || this.movable == ServerInfoWidget.Movable.ALL) {
            SelectionWidget upWidget = new SelectionWidget(SelectionWidget.SelectionIcon.UP);
            upWidget.setPressable(() -> {
                if (this.moveHandler != null) {
                    this.moveHandler.accept(ServerInfoWidget.Movable.UP);
                }
            });
            parent.addChild(upWidget);
        }
        if (this.movable == ServerInfoWidget.Movable.DOWN || this.movable == ServerInfoWidget.Movable.ALL) {
            SelectionWidget downWidget = new SelectionWidget(SelectionWidget.SelectionIcon.DOWN);
            downWidget.setPressable(() -> {
                if (this.moveHandler != null) {
                    this.moveHandler.accept(ServerInfoWidget.Movable.DOWN);
                }
            });
            parent.addChild(downWidget);
        }
        SelectionWidget addWidget = new SelectionWidget(SelectionWidget.SelectionIcon.ADD);
        addWidget.setPressable(() -> {
            if (this.moveHandler != null) {
                this.moveHandler.accept(ServerInfoWidget.Movable.ADD);
            }
        });
        parent.addChild(addWidget);
    }

    protected Widget getIconWidget() {
        return null;
    }

    public void applyAnimation(Widget parent) {
        if (parent.isHovered()) {
            this.animationData.startMoveIn();
        } else {
            this.animationData.startMoveOut();
        }
        float progress = this.animationData.getProgress();
        setScale(progress);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public int getSortingValue() {
        return getListIndex();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget
    public FloatVector2 getPivot() {
        return isDragging() ? this.draggingPivot : super.getPivot();
    }

    public boolean isMoveable() {
        return this.movable != ServerInfoWidget.Movable.NO;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/activity/multiplayer/ServerEntryWidget$AnimationData.class */
    private static class AnimationData {
        private boolean moveOutStarted;
        private long moveOutTime;
        private boolean moveInStarted;
        private long moveInTime;

        private AnimationData() {
        }

        public void startMoveOut() {
            if (!this.moveOutStarted) {
                this.moveOutStarted = true;
                this.moveOutTime = TimeUtil.getCurrentTimeMillis() + 1000;
                this.moveInStarted = false;
            }
        }

        public void startMoveIn() {
            if (!this.moveInStarted) {
                this.moveInStarted = true;
                this.moveInTime = this.moveOutStarted ? TimeUtil.getCurrentTimeMillis() + 1000 : TimeUtil.getCurrentTimeMillis();
                this.moveOutStarted = false;
            }
        }

        public float getProgress() {
            long timePassed = 0;
            if (this.moveOutStarted) {
                timePassed = this.moveOutTime - TimeUtil.getCurrentTimeMillis();
            }
            if (this.moveInStarted) {
                timePassed = this.moveInTime - TimeUtil.getCurrentTimeMillis();
            }
            if (timePassed == -1) {
                return 1.0f;
            }
            float progress = MathHelper.clamp((float) CubicBezier.EASE_IN_OUT.curve(MathHelper.clamp(timePassed / 1250.0f, 0.0f, 1.0f)), 0.5f, 1.0f);
            if (this.moveInStarted) {
                progress = 1.5f - progress;
            }
            return progress;
        }
    }
}
