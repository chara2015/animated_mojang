package net.labymod.api.client.gui.screen.theme.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.activity.ActivityController;
import net.labymod.api.client.gui.screen.activity.types.IngameOverlayActivity;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.gui.screen.theme.ThemeEventListener;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.overlay.ScreenOverlay;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.gui.screen.ActivityOpenEvent;
import net.labymod.api.event.client.gui.screen.ScreenDisplayEvent;
import net.labymod.api.event.client.gui.screen.theme.ThemeChangeEvent;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.util.time.TimeUtil;
import org.jetbrains.annotations.ApiStatus;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/theme/controller/ActivityAnimationController.class */
public abstract class ActivityAnimationController implements ThemeEventListener {
    protected final Theme theme;
    private final Set<Class<? extends Activity>> currentActivityClasses = new HashSet();
    private Set<Class<? extends Activity>> previousActivityClasses = new HashSet();
    private final Map<Class<? extends Activity>, ActivityTransformer> activityClasses = new HashMap();

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/theme/controller/ActivityAnimationController$AnimationFunction.class */
    public interface AnimationFunction {
        void update(Transformer transformer);
    }

    protected abstract void register();

    protected abstract boolean isEnabled();

    public ActivityAnimationController(Theme theme) {
        this.theme = theme;
        register();
    }

    protected ActivityTransformer register(Class<? extends Activity> activityClass, AnimationFunction animator) {
        ActivityTransformer transformer = new ActivityTransformer(this, animator);
        this.activityClasses.put(activityClass, transformer);
        return transformer;
    }

    @Subscribe
    public void onScreenDisplay(ScreenDisplayEvent event) {
        this.previousActivityClasses = new HashSet(this.currentActivityClasses);
        this.currentActivityClasses.clear();
    }

    @Subscribe
    public void onActivityOpenPre(ActivityOpenEvent activityOpenEvent) {
        Activity activity = activityOpenEvent.activity();
        if ((activity instanceof ScreenOverlay) || (activity instanceof IngameOverlayActivity)) {
            return;
        }
        this.currentActivityClasses.add((Class<? extends Activity>) activity.getClass());
        HashSet hashSet = new HashSet();
        ActivityController activityController = Laby.references().activityController();
        for (Class<? extends Activity> cls : this.currentActivityClasses) {
            if (!activityController.isActivityOpen(cls)) {
                hashSet.add(cls);
            }
        }
        if (!hashSet.isEmpty()) {
            this.previousActivityClasses = hashSet;
            this.currentActivityClasses.removeAll(hashSet);
        }
        this.previousActivityClasses.removeAll(this.currentActivityClasses);
    }

    @Subscribe(64)
    public void onActivityOpenPost(ActivityOpenEvent event) {
        ActivityTransformer transformer = this.activityClasses.get(event.activity().getClass());
        if (transformer != null && !this.previousActivityClasses.contains(event.activity().getClass()) && isEnabled()) {
            transformer.setPreviousActivityClasses(this.previousActivityClasses);
            event.activity().setTransformer(transformer);
        }
    }

    @Subscribe
    public void onThemeChange(ThemeChangeEvent event) {
        if (event.phase() != Phase.PRE) {
            return;
        }
        this.activityClasses.clear();
    }

    public void transform(Stack stack, MutableMouse mouse, float partialTicks, Activity activity, ActivityTransformer transformer) {
        AnimationFunction function = transformer.animationFunction();
        transformer.setup(activity);
        function.update(transformer);
        Bounds bounds = activity.bounds();
        stack.translate(bounds.getWidth() / 2.0f, bounds.getHeight() / 2.0f, 0.0f);
        FloatVector3 scale = transformer.getScale();
        stack.scale(scale.getX(), scale.getY(), scale.getZ());
        FloatVector3 translation = transformer.getTranslation();
        stack.translate(translation.getX(), translation.getY(), translation.getZ());
        FloatVector3 rotation = transformer.getRotation();
        stack.rotate(rotation.getX(), 1.0f, 0.0f, 0.0f);
        stack.rotate(rotation.getY(), 0.0f, 1.0f, 0.0f);
        stack.rotate(rotation.getZ(), 0.0f, 0.0f, 1.0f);
        activity.document().opacity().set(Float.valueOf(transformer.getOpacity()));
        stack.translate((-bounds.getWidth()) / 2.0f, (-bounds.getHeight()) / 2.0f, 0.0f);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/theme/controller/ActivityAnimationController$ActivityTransformer.class */
    public static class ActivityTransformer implements Transformer {
        private final ActivityAnimationController controller;
        private final AnimationFunction animationFunction;
        private float timePassed;
        private float opacity;
        private Activity activity;
        private boolean active;
        private Set<Class<? extends Activity>> previousActivityClasses = new HashSet();
        private final FloatVector3 translation = new FloatVector3();
        private final FloatVector3 rotation = new FloatVector3();
        private final FloatVector3 scale = new FloatVector3(1.0f, 1.0f, 1.0f);

        public ActivityTransformer(ActivityAnimationController controller, AnimationFunction animationFunction) {
            this.controller = controller;
            this.animationFunction = animationFunction;
        }

        public void initialize() {
            this.timePassed = 0.0f;
            this.active = true;
        }

        public void render(float tickDelta) {
            this.timePassed += TimeUtil.convertDeltaToMilliseconds(tickDelta);
        }

        @ApiStatus.Internal
        protected void setPreviousActivityClasses(Set<Class<? extends Activity>> classes) {
            this.previousActivityClasses = classes;
        }

        protected void setup(Activity activity) {
            this.activity = activity;
            reset();
        }

        public void dispose() {
            this.active = false;
            reset();
        }

        private void reset() {
            this.translation.set(0.0f, 0.0f, 0.0f);
            this.rotation.set(0.0f, 0.0f, 0.0f);
            this.scale.set(1.0f, 1.0f, 1.0f);
            this.opacity = 1.0f;
        }

        @Override // net.labymod.api.client.gui.screen.theme.controller.ActivityAnimationController.Transformer
        public float getProgress(int duration, TimeUnit timeUnit) {
            long millisDuration = timeUnit.toMillis(duration);
            if (millisDuration < 0) {
                return 1.0f;
            }
            float progress = this.timePassed / millisDuration;
            if (progress > 1.0f) {
                return 1.0f;
            }
            return progress;
        }

        @Override // net.labymod.api.client.gui.screen.theme.controller.ActivityAnimationController.Transformer
        public Bounds getBounds() {
            return this.activity.bounds();
        }

        @Override // net.labymod.api.client.gui.screen.theme.controller.ActivityAnimationController.Transformer
        public boolean isPreviously(Class<? extends Activity> clazz) {
            return this.previousActivityClasses.contains(clazz);
        }

        @Override // net.labymod.api.client.gui.screen.theme.controller.ActivityAnimationController.Transformer
        public boolean hasNoPreviouslyActivity() {
            return this.previousActivityClasses.isEmpty();
        }

        @Override // net.labymod.api.client.gui.screen.theme.controller.ActivityAnimationController.Transformer
        public void scale(float scale) {
            this.scale.setX(scale);
            this.scale.setY(scale);
            this.scale.setZ(scale);
        }

        @Override // net.labymod.api.client.gui.screen.theme.controller.ActivityAnimationController.Transformer
        public void opacity(float opacity) {
            this.opacity = opacity;
        }

        @Override // net.labymod.api.client.gui.screen.theme.controller.ActivityAnimationController.Transformer
        public void translate(float x, float y) {
            this.translation.setX(x);
            this.translation.setY(y);
        }

        @Override // net.labymod.api.client.gui.screen.theme.controller.ActivityAnimationController.Transformer
        public void rotateX(float angle) {
            this.rotation.setX(angle);
        }

        @Override // net.labymod.api.client.gui.screen.theme.controller.ActivityAnimationController.Transformer
        public void rotateY(float angle) {
            this.rotation.setY(angle);
        }

        @Override // net.labymod.api.client.gui.screen.theme.controller.ActivityAnimationController.Transformer
        public void rotateZ(float angle) {
            this.rotation.setZ(angle);
        }

        public FloatVector3 getTranslation() {
            return this.translation;
        }

        public FloatVector3 getRotation() {
            return this.rotation;
        }

        public FloatVector3 getScale() {
            return this.scale;
        }

        public float getOpacity() {
            return this.opacity;
        }

        public ActivityAnimationController controller() {
            return this.controller;
        }

        public AnimationFunction animationFunction() {
            return this.animationFunction;
        }

        public boolean isActive() {
            return this.active;
        }

        public float getTimePassed() {
            return this.timePassed;
        }

        @Deprecated
        public long getTimeInitialized() {
            return 0L;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/theme/controller/ActivityAnimationController$Transformer.class */
    public interface Transformer {
        float getProgress(int i, TimeUnit timeUnit);

        Bounds getBounds();

        boolean isPreviously(Class<? extends Activity> cls);

        boolean hasNoPreviouslyActivity();

        void scale(float f);

        void opacity(float f);

        void translate(float f, float f2);

        void rotateX(float f);

        void rotateY(float f);

        void rotateZ(float f);

        default float getProgress(int duration, TimeUnit timeUnit, CubicBezier curve) {
            return (float) curve.curve(getProgress(duration, timeUnit));
        }
    }

    protected static float customFunc(Transformer transformer, float from, float to, int duration, CubicBezier curve) {
        return from + ((to - from) * transformer.getProgress(duration, TimeUnit.MILLISECONDS, curve));
    }
}
