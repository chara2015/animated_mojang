package net.minecraft.client;

import com.mojang.blaze3d.Blaze3D;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.Window;
import com.mojang.logging.LogUtils;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.InputQuirks;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.input.MouseButtonInfo;
import net.minecraft.util.Mth;
import net.minecraft.util.SmoothDouble;
import net.minecraft.util.Util;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.levelgen.Density;
import org.joml.Vector2i;
import org.lwjgl.glfw.GLFWDropCallback;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/MouseHandler.class */
public class MouseHandler {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final long DOUBLE_CLICK_THRESHOLD_MS = 250;
    private final Minecraft minecraft;
    private boolean isLeftPressed;
    private boolean isMiddlePressed;
    private boolean isRightPressed;
    private double xpos;
    private double ypos;
    private LastClick lastClick;

    @MouseButtonInfo.MouseButton
    protected int lastClickButton;
    private int fakeRightMouse;
    private int clickDepth;
    private double mousePressedTime;
    private double accumulatedDX;
    private double accumulatedDY;
    private boolean mouseGrabbed;
    private MouseButtonInfo activeButton = null;
    private boolean ignoreFirstMove = true;
    private final SmoothDouble smoothTurnX = new SmoothDouble();
    private final SmoothDouble smoothTurnY = new SmoothDouble();
    private double lastHandleMovementTime = Double.MIN_VALUE;
    private final ScrollWheelHandler scrollWheelHandler = new ScrollWheelHandler();

    public MouseHandler(Minecraft $$0) {
        this.minecraft = $$0;
    }

    private void onButton(long $$0, MouseButtonInfo $$1, @MouseButtonInfo.Action int $$2) {
        Window $$3 = this.minecraft.getWindow();
        if ($$0 != $$3.handle()) {
            return;
        }
        this.minecraft.getFramerateLimitTracker().onInputReceived();
        if (this.minecraft.screen != null) {
            this.minecraft.setLastInputType(InputType.MOUSE);
        }
        boolean $$4 = $$2 == 1;
        MouseButtonInfo $$5 = simulateRightClick($$1, $$4);
        if ($$4) {
            if (this.minecraft.options.touchscreen().get().booleanValue()) {
                int i = this.clickDepth;
                this.clickDepth = i + 1;
                if (i > 0) {
                    return;
                }
            }
            this.activeButton = $$5;
            this.mousePressedTime = Blaze3D.getTime();
        } else if (this.activeButton != null) {
            if (this.minecraft.options.touchscreen().get().booleanValue()) {
                int i2 = this.clickDepth - 1;
                this.clickDepth = i2;
                if (i2 > 0) {
                    return;
                }
            }
            this.activeButton = null;
        }
        if (this.minecraft.getOverlay() == null) {
            if (this.minecraft.screen == null) {
                if (!this.mouseGrabbed && $$4) {
                    grabMouse();
                }
            } else {
                double $$6 = getScaledXPos($$3);
                double $$7 = getScaledYPos($$3);
                Screen $$8 = this.minecraft.screen;
                MouseButtonEvent $$9 = new MouseButtonEvent($$6, $$7, $$5);
                if ($$4) {
                    $$8.afterMouseAction();
                    try {
                        long $$10 = Util.getMillis();
                        boolean $$11 = this.lastClick != null && $$10 - this.lastClick.time() < 250 && this.lastClick.screen() == $$8 && this.lastClickButton == $$9.button();
                        if ($$8.mouseClicked($$9, $$11)) {
                            this.lastClick = new LastClick($$10, $$8);
                            this.lastClickButton = $$5.button();
                            return;
                        }
                    } catch (Throwable $$12) {
                        CrashReport $$13 = CrashReport.forThrowable($$12, "mouseClicked event handler");
                        $$8.fillCrashDetails($$13);
                        CrashReportCategory $$14 = $$13.addCategory("Mouse");
                        fillMousePositionDetails($$14, $$3);
                        $$14.setDetail("Button", Integer.valueOf($$9.button()));
                        throw new ReportedException($$13);
                    }
                } else {
                    try {
                        if ($$8.mouseReleased($$9)) {
                            return;
                        }
                    } catch (Throwable $$15) {
                        CrashReport $$16 = CrashReport.forThrowable($$15, "mouseReleased event handler");
                        $$8.fillCrashDetails($$16);
                        CrashReportCategory $$17 = $$16.addCategory("Mouse");
                        fillMousePositionDetails($$17, $$3);
                        $$17.setDetail("Button", Integer.valueOf($$9.button()));
                        throw new ReportedException($$16);
                    }
                }
            }
        }
        if (this.minecraft.screen == null && this.minecraft.getOverlay() == null) {
            if ($$5.button() == 0) {
                this.isLeftPressed = $$4;
            } else if ($$5.button() == 2) {
                this.isMiddlePressed = $$4;
            } else if ($$5.button() == 1) {
                this.isRightPressed = $$4;
            }
            InputConstants.Key $$18 = InputConstants.Type.MOUSE.getOrCreate($$5.button());
            KeyMapping.set($$18, $$4);
            if ($$4) {
                KeyMapping.click($$18);
            }
        }
    }

    private MouseButtonInfo simulateRightClick(MouseButtonInfo $$0, boolean $$1) {
        if (InputQuirks.SIMULATE_RIGHT_CLICK_WITH_LONG_LEFT_CLICK && $$0.button() == 0) {
            if ($$1) {
                if (($$0.modifiers() & 2) == 2) {
                    this.fakeRightMouse++;
                    return new MouseButtonInfo(1, $$0.modifiers());
                }
            } else if (this.fakeRightMouse > 0) {
                this.fakeRightMouse--;
                return new MouseButtonInfo(1, $$0.modifiers());
            }
        }
        return $$0;
    }

    public void fillMousePositionDetails(CrashReportCategory $$0, Window $$1) {
        $$0.setDetail("Mouse location", () -> {
            return String.format(Locale.ROOT, "Scaled: (%f, %f). Absolute: (%f, %f)", Double.valueOf(getScaledXPos($$1, this.xpos)), Double.valueOf(getScaledYPos($$1, this.ypos)), Double.valueOf(this.xpos), Double.valueOf(this.ypos));
        });
        $$0.setDetail("Screen size", () -> {
            return String.format(Locale.ROOT, "Scaled: (%d, %d). Absolute: (%d, %d). Scale factor of %d", Integer.valueOf($$1.getGuiScaledWidth()), Integer.valueOf($$1.getGuiScaledHeight()), Integer.valueOf($$1.getWidth()), Integer.valueOf($$1.getHeight()), Integer.valueOf($$1.getGuiScale()));
        });
    }

    private void onScroll(long $$0, double $$1, double $$2) {
        if ($$0 == this.minecraft.getWindow().handle()) {
            this.minecraft.getFramerateLimitTracker().onInputReceived();
            boolean $$3 = this.minecraft.options.discreteMouseScroll().get().booleanValue();
            double $$4 = this.minecraft.options.mouseWheelSensitivity().get().doubleValue();
            double $$5 = ($$3 ? Math.signum($$1) : $$1) * $$4;
            double $$6 = ($$3 ? Math.signum($$2) : $$2) * $$4;
            if (this.minecraft.getOverlay() == null) {
                if (this.minecraft.screen != null) {
                    double $$7 = getScaledXPos(this.minecraft.getWindow());
                    double $$8 = getScaledYPos(this.minecraft.getWindow());
                    this.minecraft.screen.mouseScrolled($$7, $$8, $$5, $$6);
                    this.minecraft.screen.afterMouseAction();
                    return;
                }
                if (this.minecraft.player != null) {
                    Vector2i $$9 = this.scrollWheelHandler.onMouseScroll($$5, $$6);
                    if ($$9.x == 0 && $$9.y == 0) {
                        return;
                    }
                    int $$10 = $$9.y == 0 ? -$$9.x : $$9.y;
                    if (this.minecraft.player.isSpectator()) {
                        if (this.minecraft.gui.getSpectatorGui().isMenuActive()) {
                            this.minecraft.gui.getSpectatorGui().onMouseScrolled(-$$10);
                            return;
                        } else {
                            float $$11 = Mth.clamp(this.minecraft.player.getAbilities().getFlyingSpeed() + ($$9.y * 0.005f), 0.0f, 0.2f);
                            this.minecraft.player.getAbilities().setFlyingSpeed($$11);
                            return;
                        }
                    }
                    Inventory $$12 = this.minecraft.player.getInventory();
                    $$12.setSelectedSlot(ScrollWheelHandler.getNextScrollWheelSelection($$10, $$12.getSelectedSlot(), Inventory.getSelectionSize()));
                }
            }
        }
    }

    private void onDrop(long $$0, List<Path> $$1, int $$2) {
        this.minecraft.getFramerateLimitTracker().onInputReceived();
        if (this.minecraft.screen != null) {
            this.minecraft.screen.onFilesDrop($$1);
        }
        if ($$2 > 0) {
            SystemToast.onFileDropFailure(this.minecraft, $$2);
        }
    }

    public void setup(Window $$0) {
        InputConstants.setupMouseCallbacks($$0, ($$02, $$1, $$2) -> {
            this.minecraft.execute(() -> {
                onMove($$02, $$1, $$2);
            });
        }, ($$03, $$12, $$22, $$3) -> {
            MouseButtonInfo $$4 = new MouseButtonInfo($$12, $$3);
            this.minecraft.execute(() -> {
                onButton($$03, $$4, $$22);
            });
        }, ($$04, $$13, $$23) -> {
            this.minecraft.execute(() -> {
                onScroll($$04, $$13, $$23);
            });
        }, ($$05, $$14, $$24) -> {
            List<Path> $$32 = new ArrayList<>($$14);
            int $$4 = 0;
            for (int $$5 = 0; $$5 < $$14; $$5++) {
                String $$6 = GLFWDropCallback.getName($$24, $$5);
                try {
                    $$32.add(Paths.get($$6, new String[0]));
                } catch (InvalidPathException $$7) {
                    $$4++;
                    LOGGER.error("Failed to parse path '{}'", $$6, $$7);
                }
            }
            if (!$$32.isEmpty()) {
                int $$8 = $$4;
                this.minecraft.execute(() -> {
                    onDrop($$05, $$32, $$8);
                });
            }
        });
    }

    private void onMove(long $$0, double $$1, double $$2) {
        if ($$0 != this.minecraft.getWindow().handle()) {
            return;
        }
        if (this.ignoreFirstMove) {
            this.xpos = $$1;
            this.ypos = $$2;
            this.ignoreFirstMove = false;
        } else {
            if (this.minecraft.isWindowActive()) {
                this.accumulatedDX += $$1 - this.xpos;
                this.accumulatedDY += $$2 - this.ypos;
            }
            this.xpos = $$1;
            this.ypos = $$2;
        }
    }

    public void handleAccumulatedMovement() {
        double $$0 = Blaze3D.getTime();
        double $$1 = $$0 - this.lastHandleMovementTime;
        this.lastHandleMovementTime = $$0;
        if (this.minecraft.isWindowActive()) {
            Screen $$2 = this.minecraft.screen;
            boolean $$3 = (this.accumulatedDX == Density.SURFACE && this.accumulatedDY == Density.SURFACE) ? false : true;
            if ($$3) {
                this.minecraft.getFramerateLimitTracker().onInputReceived();
            }
            if ($$2 != null && this.minecraft.getOverlay() == null && $$3) {
                Window $$4 = this.minecraft.getWindow();
                double $$5 = getScaledXPos($$4);
                double $$6 = getScaledYPos($$4);
                try {
                    $$2.mouseMoved($$5, $$6);
                    if (this.activeButton != null && this.mousePressedTime > Density.SURFACE) {
                        double $$10 = getScaledXPos($$4, this.accumulatedDX);
                        double $$11 = getScaledYPos($$4, this.accumulatedDY);
                        try {
                            $$2.mouseDragged(new MouseButtonEvent($$5, $$6, this.activeButton), $$10, $$11);
                        } catch (Throwable $$12) {
                            CrashReport $$13 = CrashReport.forThrowable($$12, "mouseDragged event handler");
                            $$2.fillCrashDetails($$13);
                            CrashReportCategory $$14 = $$13.addCategory("Mouse");
                            fillMousePositionDetails($$14, $$4);
                            throw new ReportedException($$13);
                        }
                    }
                    $$2.afterMouseMove();
                } catch (Throwable $$7) {
                    CrashReport $$8 = CrashReport.forThrowable($$7, "mouseMoved event handler");
                    $$2.fillCrashDetails($$8);
                    CrashReportCategory $$9 = $$8.addCategory("Mouse");
                    fillMousePositionDetails($$9, $$4);
                    throw new ReportedException($$8);
                }
            }
            if (isMouseGrabbed() && this.minecraft.player != null) {
                turnPlayer($$1);
            }
        }
        this.accumulatedDX = Density.SURFACE;
        this.accumulatedDY = Density.SURFACE;
    }

    public static double getScaledXPos(Window $$0, double $$1) {
        return ($$1 * ((double) $$0.getGuiScaledWidth())) / ((double) $$0.getScreenWidth());
    }

    public double getScaledXPos(Window $$0) {
        return getScaledXPos($$0, this.xpos);
    }

    public static double getScaledYPos(Window $$0, double $$1) {
        return ($$1 * ((double) $$0.getGuiScaledHeight())) / ((double) $$0.getScreenHeight());
    }

    public double getScaledYPos(Window $$0) {
        return getScaledYPos($$0, this.ypos);
    }

    private void turnPlayer(double $$0) {
        double $$10;
        double $$11;
        double $$1 = (this.minecraft.options.sensitivity().get().doubleValue() * 0.6000000238418579d) + 0.20000000298023224d;
        double $$2 = $$1 * $$1 * $$1;
        double $$3 = $$2 * 8.0d;
        if (this.minecraft.options.smoothCamera) {
            double $$4 = this.smoothTurnX.getNewDeltaValue(this.accumulatedDX * $$3, $$0 * $$3);
            double $$5 = this.smoothTurnY.getNewDeltaValue(this.accumulatedDY * $$3, $$0 * $$3);
            $$10 = $$4;
            $$11 = $$5;
        } else if (this.minecraft.options.getCameraType().isFirstPerson() && this.minecraft.player.isScoping()) {
            this.smoothTurnX.reset();
            this.smoothTurnY.reset();
            $$10 = this.accumulatedDX * $$2;
            $$11 = this.accumulatedDY * $$2;
        } else {
            this.smoothTurnX.reset();
            this.smoothTurnY.reset();
            $$10 = this.accumulatedDX * $$3;
            $$11 = this.accumulatedDY * $$3;
        }
        this.minecraft.getTutorial().onMouse($$10, $$11);
        if (this.minecraft.player != null) {
            this.minecraft.player.turn(this.minecraft.options.invertMouseX().get().booleanValue() ? -$$10 : $$10, this.minecraft.options.invertMouseY().get().booleanValue() ? -$$11 : $$11);
        }
    }

    public boolean isLeftPressed() {
        return this.isLeftPressed;
    }

    public boolean isMiddlePressed() {
        return this.isMiddlePressed;
    }

    public boolean isRightPressed() {
        return this.isRightPressed;
    }

    public double xpos() {
        return this.xpos;
    }

    public double ypos() {
        return this.ypos;
    }

    public void setIgnoreFirstMove() {
        this.ignoreFirstMove = true;
    }

    public boolean isMouseGrabbed() {
        return this.mouseGrabbed;
    }

    public void grabMouse() {
        if (!this.minecraft.isWindowActive() || this.mouseGrabbed) {
            return;
        }
        if (InputQuirks.RESTORE_KEY_STATE_AFTER_MOUSE_GRAB) {
            KeyMapping.setAll();
        }
        this.mouseGrabbed = true;
        this.xpos = this.minecraft.getWindow().getScreenWidth() / 2;
        this.ypos = this.minecraft.getWindow().getScreenHeight() / 2;
        InputConstants.grabOrReleaseMouse(this.minecraft.getWindow(), InputConstants.CURSOR_DISABLED, this.xpos, this.ypos);
        this.minecraft.setScreen(null);
        this.minecraft.missTime = 10000;
        this.ignoreFirstMove = true;
    }

    public void releaseMouse() {
        if (!this.mouseGrabbed) {
            return;
        }
        this.mouseGrabbed = false;
        this.xpos = this.minecraft.getWindow().getScreenWidth() / 2;
        this.ypos = this.minecraft.getWindow().getScreenHeight() / 2;
        InputConstants.grabOrReleaseMouse(this.minecraft.getWindow(), InputConstants.CURSOR_NORMAL, this.xpos, this.ypos);
    }

    public void cursorEntered() {
        this.ignoreFirstMove = true;
    }

    public void drawDebugMouseInfo(Font $$0, GuiGraphics $$1) {
        Window $$2 = this.minecraft.getWindow();
        double $$3 = getScaledXPos($$2);
        double $$4 = getScaledYPos($$2) - 8.0d;
        String $$5 = String.format(Locale.ROOT, "%.0f,%.0f", Double.valueOf($$3), Double.valueOf($$4));
        $$1.drawString($$0, $$5, (int) $$3, (int) $$4, -1);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/MouseHandler$LastClick.class */
    static final class LastClick extends Record {
        private final long time;
        private final Screen screen;

        LastClick(long $$0, Screen $$1) {
            this.time = $$0;
            this.screen = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, LastClick.class), LastClick.class, "time;screen", "FIELD:Lnet/minecraft/client/MouseHandler$LastClick;->time:J", "FIELD:Lnet/minecraft/client/MouseHandler$LastClick;->screen:Lnet/minecraft/client/gui/screens/Screen;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, LastClick.class), LastClick.class, "time;screen", "FIELD:Lnet/minecraft/client/MouseHandler$LastClick;->time:J", "FIELD:Lnet/minecraft/client/MouseHandler$LastClick;->screen:Lnet/minecraft/client/gui/screens/Screen;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, LastClick.class, Object.class), LastClick.class, "time;screen", "FIELD:Lnet/minecraft/client/MouseHandler$LastClick;->time:J", "FIELD:Lnet/minecraft/client/MouseHandler$LastClick;->screen:Lnet/minecraft/client/gui/screens/Screen;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public long time() {
            return this.time;
        }

        public Screen screen() {
            return this.screen;
        }
    }
}
