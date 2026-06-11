package net.labymod.api.client.gui.window;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.lss.variable.LssVariableHolder;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ParentScreen;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.util.bounds.Rectangle;
import org.joml.Vector2f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/window/Window.class */
public interface Window extends ParentScreen, LssVariableHolder {
    Bounds absoluteBounds();

    int getRawWidth();

    int getRawHeight();

    int getScaledWidth();

    int getAbsoluteScaledWidth();

    int getScaledHeight();

    int getAbsoluteScaledHeight();

    boolean isFocused();

    void transform(Parent parent, Stack stack, Rectangle rectangle, Runnable runnable);

    float getScale();

    long getPointer();

    Object getMostInnerScreen(Object obj) throws TooManyIterationsException;

    default Vector2f getRawSize() {
        return new Vector2f(getRawWidth(), getRawHeight());
    }

    default float getPercentageScaledWidth() {
        return getScaledWidth() / 100.0f;
    }

    default float getPercentageScaledHeight() {
        return getScaledHeight() / 100.0f;
    }

    default boolean allowCursorChange() {
        return false;
    }

    @Deprecated
    default void getActivityTree(Consumer<Activity> consumer) {
        Laby.references().activityController().getActivityTree(consumer);
    }

    @Deprecated
    default Collection<Activity> getOpenActivities() {
        return Laby.references().activityController().deprecated$getOpenActivities();
    }

    @Deprecated
    default Activity findOpenActivity(Predicate<Activity> filter) {
        return Laby.references().activityController().findOpenActivity(filter);
    }
}
