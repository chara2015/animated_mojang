package net.labymod.api.client.gfx.imgui.window;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.client.gui.screen.VanillaScreen;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.KeyHandler;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.debug.DebugRegistry;
import net.labymod.api.event.client.input.KeyEvent;
import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/imgui/window/DocumentHandler.class */
@Singleton
@Referenceable
public class DocumentHandler {
    private static final int VANILLA_ACTIVITY = -1;
    private static final int DEFAULT_ACTIVITY = 0;
    private final List<Activity> activities = new ArrayList();
    private int selectedActivity;
    private Widget selectedWidget;
    private Widget targetWidget;

    public void collectActivity(Activity activity) {
        if (!DebugRegistry.DEBUG_WINDOWS.isEnabled()) {
            this.activities.clear();
        } else if (!this.activities.contains(activity)) {
            this.activities.add(activity);
        }
    }

    public void onKey(KeyEvent event) {
        if (event.state() != KeyEvent.State.PRESS) {
            return;
        }
        Key key = event.key();
        if (key == Key.TAB) {
            if (KeyHandler.isShiftDown()) {
                this.selectedActivity--;
                if (this.selectedActivity < -1) {
                    this.selectedActivity = -1;
                    return;
                }
                return;
            }
            this.selectedActivity++;
        }
    }

    public void resetSelectedActivity() {
        this.selectedActivity = 0;
    }

    public void clear() {
        this.activities.clear();
    }

    @Nullable
    public Widget getSelectedWidget() {
        return this.selectedWidget;
    }

    public void setSelectedWidget(Widget selectedWidget) {
        this.selectedWidget = selectedWidget;
    }

    public boolean isSelectedWidget(Widget other) {
        return this.selectedWidget == other;
    }

    public Widget getTargetWidget() {
        return this.targetWidget;
    }

    public void setTargetWidget(Widget targetWidget) {
        this.targetWidget = targetWidget;
    }

    public List<Activity> getActivities() {
        return this.activities;
    }

    public String getCurrentActivityName() {
        Object selectedActivity = getSelectedActivity();
        String name = null;
        if (selectedActivity instanceof Activity) {
            Activity activity = (Activity) selectedActivity;
            name = activity.getName();
        } else if (selectedActivity instanceof VanillaScreen) {
            VanillaScreen vanillaScreen = (VanillaScreen) selectedActivity;
            name = vanillaScreen.getClass().getSimpleName();
        }
        if (name == null) {
            return "No Activity available";
        }
        return String.format(Locale.ROOT, "%s (%s/%s)", name, Integer.valueOf(this.selectedActivity + 1), Integer.valueOf(this.activities.size()));
    }

    public void setSelectedActivity(String name) {
        for (Activity activity : this.activities) {
            if (Objects.equals(activity.getName(), name)) {
                setSelectedActivity(activity);
                return;
            }
        }
    }

    public void setSelectedActivity(Activity newSelectedActivity) {
        this.selectedActivity = this.activities.indexOf(newSelectedActivity);
    }

    public Object getSelectedActivity() {
        if (this.selectedActivity == -1) {
            ScreenWrapper currentScreen = Laby.labyAPI().minecraft().minecraftWindow().currentScreen();
            if (currentScreen != null) {
                ScreenInstance screenInstance = currentScreen.mostInnerScreenInstance();
                if (screenInstance instanceof ScreenWrapper) {
                    ScreenWrapper screenWrapper = (ScreenWrapper) screenInstance;
                    if (screenWrapper.getVersionedScreen() instanceof VanillaScreen) {
                        return screenWrapper.getVersionedScreen();
                    }
                }
            }
            this.selectedActivity = 0;
        }
        if (this.selectedActivity > this.activities.size() - 1) {
            return null;
        }
        return this.activities.get(this.selectedActivity);
    }
}
