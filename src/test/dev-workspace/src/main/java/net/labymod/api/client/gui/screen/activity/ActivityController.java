package net.labymod.api.client.gui.screen.activity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.widget.overlay.ScreenOverlay;
import net.labymod.api.client.gui.screen.widget.overlay.ScreenOverlayHandler;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.ThreadSafe;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/activity/ActivityController.class */
@Singleton
@Referenceable
public final class ActivityController {
    private final List<Activity> openActivities = new ArrayList();

    @Deprecated
    private final Collection<Activity> deprecated$OpenActivities = new ArrayList();
    private Activity[] bakedOpenActivities = new Activity[0];

    @Inject
    public ActivityController() {
    }

    public void addOpenActivity(Activity activity) {
        this.openActivities.add(activity);
        bakeOpenActivities();
    }

    public void removeOpenActivity(Activity activity) {
        this.openActivities.remove(activity);
        bakeOpenActivities();
    }

    public void getActivityTree(Consumer<Activity> consumer) {
        ThreadSafe.ensureRenderThread();
        LabyScreen screen = Laby.labyAPI().minecraft().minecraftWindow().currentLabyScreen();
        if (screen instanceof Activity) {
            screen.doScreenAction("getActivityTree", Collections.singletonMap("consumer", consumer));
        }
        ScreenOverlayHandler overlayHandler = Laby.labyAPI().screenOverlayHandler();
        if (overlayHandler != null) {
            for (ScreenOverlay overlay : overlayHandler.overlays()) {
                overlay.doScreenAction("getActivityTree", Collections.singletonMap("consumer", consumer));
            }
        }
    }

    public Activity findOpenActivity(Predicate<Activity> filter) {
        for (Activity activity : this.bakedOpenActivities) {
            if (filter.test(activity)) {
                return activity;
            }
        }
        return null;
    }

    public boolean isActivityOpen(Class<? extends Activity> activityClass) {
        Objects.requireNonNull(activityClass);
        return findOpenActivity((v1) -> {
            return r1.isInstance(v1);
        }) != null;
    }

    public Activity[] getOpenActivities() {
        return this.bakedOpenActivities;
    }

    public void reloadOpenActivities() {
        for (Activity activity : getOpenActivities()) {
            activity.reload();
        }
    }

    @Deprecated
    public Collection<Activity> deprecated$getOpenActivities() {
        return this.deprecated$OpenActivities;
    }

    private void bakeOpenActivities() {
        this.bakedOpenActivities = (Activity[]) this.openActivities.toArray(new Activity[0]);
        this.deprecated$OpenActivities.clear();
        this.deprecated$OpenActivities.addAll(this.openActivities);
    }
}
