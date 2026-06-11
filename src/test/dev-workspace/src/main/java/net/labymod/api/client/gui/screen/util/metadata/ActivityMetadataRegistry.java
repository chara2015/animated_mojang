package net.labymod.api.client.gui.screen.util.metadata;

import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.Map;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor;
import net.labymod.api.client.gui.screen.activity.ElementActivity;
import net.labymod.api.client.gui.screen.util.StatefulRenderer;
import net.labymod.api.client.gui.screen.widget.Widget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/util/metadata/ActivityMetadataRegistry.class */
public final class ActivityMetadataRegistry {
    private static final Map<Class<? extends ElementActivity>, ActivityMetadata> ACTIVITY_METADATA = new HashMap();
    private static final Map<Class<? extends Widget>, WidgetMetadata> WIDGET_METADATA = new HashMap();

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/util/metadata/ActivityMetadataRegistry$ActivityMetadataAccessor.class */
    @FunctionalInterface
    public interface ActivityMetadataAccessor {
        void accept(ActivityMetadata activityMetadata);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/util/metadata/ActivityMetadataRegistry$WidgetMetadataAccessor.class */
    @FunctionalInterface
    public interface WidgetMetadataAccessor {
        void accept(WidgetMetadata widgetMetadata);
    }

    public static void collectActivityMetadata(Class<? extends ElementActivity> activityClass, ActivityMetadataAccessor accessor) {
        ActivityMetadata metadata = ACTIVITY_METADATA.computeIfAbsent(activityClass, cls -> {
            String namespace = Laby.labyAPI().getNamespace((Class<?>) cls);
            String qualifiedName = getQualifiedName(cls);
            String simpleName = getSimpleName(cls, "Activity");
            return new ActivityMetadata(namespace, qualifiedName, simpleName);
        });
        accessor.accept(metadata);
    }

    public static void collectWidgetMetadata(Class<? extends Widget> widgetClass, WidgetMetadataAccessor accessor) {
        WidgetMetadata metadata = WIDGET_METADATA.computeIfAbsent(widgetClass, cls -> {
            String namespace = Laby.labyAPI().getNamespace((Class<?>) cls);
            String qualifiedName = getQualifiedName(cls);
            String simpleName = getSimpleName(cls, "Widget");
            DirectPropertyValueAccessor propertyAccessor = Laby.references().propertyRegistry().getDirectPropertyValueAccessor(cls);
            return new WidgetMetadata(namespace, qualifiedName, simpleName, propertyAccessor);
        });
        accessor.accept(metadata);
        StatefulRenderer.registerWidgetAnalyzer(widgetClass);
    }

    private static String getSimpleName(Class<?> cls, String suffix) {
        Class<?> resolved;
        Class<?> superclass = cls;
        while (true) {
            resolved = superclass;
            if (!resolved.isAnonymousClass() || resolved.getSuperclass() == null) {
                break;
            }
            superclass = resolved.getSuperclass();
        }
        String name = resolved.getSimpleName();
        if (name.endsWith(suffix)) {
            name = CharBuffer.wrap(name, 0, name.length() - suffix.length()).toString();
        }
        return name;
    }

    private static String getQualifiedName(Class<?> cls) {
        return cls.getName().replace(".", "/");
    }
}
