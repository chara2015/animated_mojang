package net.labymod.api.client.gui.screen.util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.util.reflection.MethodOverrideAnalyzer;
import org.jetbrains.annotations.ApiStatus;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/util/StatefulRenderer.class */
@ApiStatus.Internal
public class StatefulRenderer {
    private static final Map<Class<? extends Widget>, MethodOverrideAnalyzer> RENDER_OVERRIDE_ANALYZERS = new HashMap();
    private static final Map<Class<? extends Widget>, MethodOverrideAnalyzer> RENDER_WIDGET_OVERRIDE_ANALYZERS = new HashMap();
    private static final Map<Class<? extends Activity>, MethodOverrideAnalyzer> ACTIVITY_RENDER_OVERRIDE_ANALYZERS = new HashMap();

    public static void tryRender(ScreenContext context, StateProvider stateProvider, BooleanSupplier useLegacy, Consumer<ScreenContext> newest, Consumer<ScreenContext> legacy) {
        if (stateProvider.get()) {
            newest.accept(context);
            return;
        }
        if (useLegacy.getAsBoolean()) {
            try {
                stateProvider.set(true);
                legacy.accept(context);
                stateProvider.set(false);
                return;
            } catch (Throwable th) {
                stateProvider.set(false);
                throw th;
            }
        }
        newest.accept(context);
    }

    public static void registerWidgetAnalyzer(Class<? extends Widget> widgetClass) {
        RENDER_OVERRIDE_ANALYZERS.computeIfAbsent(widgetClass, StatefulRenderer::createWidgetRenderAnalyzer);
        RENDER_WIDGET_OVERRIDE_ANALYZERS.computeIfAbsent(widgetClass, StatefulRenderer::createWidgetRenderWidgetAnalyzer);
    }

    public static void registerActivityAnalyzer(Class<? extends Activity> activityClass) {
        ACTIVITY_RENDER_OVERRIDE_ANALYZERS.computeIfAbsent(activityClass, StatefulRenderer::createActivityRenderAnalyzer);
    }

    public static boolean isWidgetRenderOverridden(Class<? extends Widget> widgetClass) {
        return isWidgetOverridden(widgetClass, RENDER_OVERRIDE_ANALYZERS);
    }

    public static boolean isWidgetRenderWidgetOverridden(Class<? extends Widget> widgetClass) {
        return isWidgetOverridden(widgetClass, RENDER_WIDGET_OVERRIDE_ANALYZERS);
    }

    public static boolean isActivityRenderOverridden(Class<? extends Activity> activityClass) {
        return ACTIVITY_RENDER_OVERRIDE_ANALYZERS.containsKey(activityClass) && ACTIVITY_RENDER_OVERRIDE_ANALYZERS.get(activityClass).isOverridden();
    }

    private static boolean isWidgetOverridden(Class<? extends Widget> widgetClass, Map<Class<? extends Widget>, MethodOverrideAnalyzer> analyzers) {
        return analyzers.containsKey(widgetClass) && analyzers.get(widgetClass).isOverridden();
    }

    private static MethodOverrideAnalyzer createWidgetRenderWidgetAnalyzer(Class<? extends Widget> widgetClass) {
        return new MethodOverrideAnalyzer(widgetClass, AbstractWidget.class, "renderWidget", Stack.class, MutableMouse.class, Float.TYPE);
    }

    private static MethodOverrideAnalyzer createWidgetRenderAnalyzer(Class<? extends Widget> widgetClass) {
        return new MethodOverrideAnalyzer(widgetClass, AbstractWidget.class, "render", Stack.class, MutableMouse.class, Float.TYPE);
    }

    private static MethodOverrideAnalyzer createActivityRenderAnalyzer(Class<? extends Activity> activityClass) {
        return new MethodOverrideAnalyzer(activityClass, Activity.class, "render", Stack.class, MutableMouse.class, Float.TYPE);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/util/StatefulRenderer$StateProvider.class */
    public static class StateProvider {
        private boolean value;

        public StateProvider(boolean value) {
            this.value = value;
        }

        public boolean get() {
            return this.value;
        }

        public void set(boolean value) {
            this.value = value;
        }
    }
}
