package net.labymod.api.client.gui.lss.property;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.event.EventBus;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.labymod.ServiceLoadEvent;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.service.CustomServiceLoader;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/lss/property/PropertyRegistry.class */
@Singleton
@Referenceable
public class PropertyRegistry {
    private final Map<Class<? extends Widget>, DirectPropertyValueAccessor> directPropertyValueAccessors = new HashMap();

    @Inject
    public PropertyRegistry(EventBus eventBus) {
        eventBus.registerListener(this);
    }

    @Subscribe
    public void registerDefaults(ServiceLoadEvent event) {
        CustomServiceLoader<DirectPropertyValueAccessorRegisterBulk> directBulks = event.load(DirectPropertyValueAccessorRegisterBulk.class, CustomServiceLoader.ServiceType.ADVANCED);
        for (DirectPropertyValueAccessorRegisterBulk directBulk : directBulks) {
            registerDirectBulk(directBulk);
        }
    }

    @Nullable
    public PropertyValueAccessor<?, ?, ?> getValueAccessor(Widget widget, String key) {
        DirectPropertyValueAccessor accessor = widget.getDirectPropertyValueAccessor();
        if (accessor == null) {
            return null;
        }
        return accessor.getPropertyValueAccessor(key);
    }

    @Nullable
    public LssPropertyResetter getPropertyResetter(Widget widget) {
        DirectPropertyValueAccessor accessor = widget.getDirectPropertyValueAccessor();
        if (accessor == null) {
            return null;
        }
        return accessor.getPropertyResetter();
    }

    @Nullable
    public DirectPropertyValueAccessor getDirectPropertyValueAccessor(Class<? extends Widget> widgetClass) {
        return this.directPropertyValueAccessors.get(widgetClass);
    }

    private void registerDirectBulk(DirectPropertyValueAccessorRegisterBulk directBulk) {
        Map<Class<? extends Widget>, DirectPropertyValueAccessor> map = new HashMap<>();
        directBulk.register(map);
        this.directPropertyValueAccessors.putAll(map);
    }
}
