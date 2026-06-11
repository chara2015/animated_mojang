package net.labymod.api.event.client.blockentity;

import net.labymod.api.client.blockentity.SignBlockEntity;
import net.labymod.api.event.Event;
import net.labymod.api.nbt.tags.NBTTagCompound;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/blockentity/SignBlockEntityPostLoadEvent.class */
public class SignBlockEntityPostLoadEvent implements Event {
    private final SignBlockEntity sign;
    private final NBTTagCompound tag;

    public SignBlockEntityPostLoadEvent(@NotNull SignBlockEntity sign, @NotNull NBTTagCompound tag) {
        this.sign = sign;
        this.tag = tag;
    }

    @NotNull
    public SignBlockEntity sign() {
        return this.sign;
    }

    @NotNull
    public NBTTagCompound tag() {
        return this.tag;
    }
}
