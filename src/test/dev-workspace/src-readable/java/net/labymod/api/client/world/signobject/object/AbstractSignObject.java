package net.labymod.api.client.world.signobject.object;

import java.util.ArrayList;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.world.signobject.SignObjectPosition;
import net.labymod.api.client.world.signobject.template.SignObjectMeta;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.api.util.time.TimeUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/signobject/object/AbstractSignObject.class */
public abstract class AbstractSignObject<M extends SignObjectMeta<?>> implements SignObject<M> {
    private final M meta;
    private final SignObjectPosition position;
    private final List<SignObjectListener<M>> listeners = new ArrayList();
    private final long creationTimestamp = TimeUtil.getCurrentTimeMillis();

    protected AbstractSignObject(M meta, SignObjectPosition position) {
        this.meta = meta;
        this.position = position;
    }

    @Override // net.labymod.api.client.world.signobject.object.SignObject
    public M meta() {
        return this.meta;
    }

    @Override // net.labymod.api.client.world.signobject.object.SignObject
    public SignObjectPosition position() {
        return this.position;
    }

    @Override // net.labymod.api.client.world.signobject.object.SignObject
    public long creationTimestamp() {
        return this.creationTimestamp;
    }

    @Override // net.labymod.api.client.world.signobject.object.SignObject
    public boolean isEnabled() {
        InstalledAddonInfo addon = this.meta.template().getAddon();
        return addon == null || Laby.labyAPI().addonService().isEnabled(addon, false);
    }

    @Override // net.labymod.api.client.world.signobject.object.SignObject
    public void addListener(SignObjectListener<M> listener) {
        this.listeners.add(listener);
    }

    @Override // net.labymod.api.client.world.signobject.object.SignObject
    public void dispose() {
        for (SignObjectListener<M> listener : this.listeners) {
            listener.onDispose(this);
        }
    }
}
