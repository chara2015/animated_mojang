package net.labymod.api.event.client.gui.screen;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.event.Event;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/gui/screen/ActivityInitializeEvent.class */
public final class ActivityInitializeEvent extends Record implements Event {
    private final Activity activity;

    public ActivityInitializeEvent(Activity activity) {
        this.activity = activity;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ActivityInitializeEvent.class), ActivityInitializeEvent.class, "activity", "FIELD:Lnet/labymod/api/event/client/gui/screen/ActivityInitializeEvent;->activity:Lnet/labymod/api/client/gui/screen/activity/Activity;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ActivityInitializeEvent.class), ActivityInitializeEvent.class, "activity", "FIELD:Lnet/labymod/api/event/client/gui/screen/ActivityInitializeEvent;->activity:Lnet/labymod/api/client/gui/screen/activity/Activity;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ActivityInitializeEvent.class, Object.class), ActivityInitializeEvent.class, "activity", "FIELD:Lnet/labymod/api/event/client/gui/screen/ActivityInitializeEvent;->activity:Lnet/labymod/api/client/gui/screen/activity/Activity;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public Activity activity() {
        return this.activity;
    }

    public String getIdentifier() {
        return this.activity.getIdentifier();
    }

    public boolean is(Class<? extends Activity> clazz) {
        return clazz.isAssignableFrom(this.activity.getClass());
    }

    public boolean is(String namespace, String name) {
        return this.activity.getNamespace().equals(namespace) && this.activity.getName().equals(name);
    }
}
