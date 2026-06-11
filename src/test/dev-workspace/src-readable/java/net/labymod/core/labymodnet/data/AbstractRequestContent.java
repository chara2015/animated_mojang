package net.labymod.core.labymodnet.data;

import java.util.function.Consumer;
import net.labymod.core.labymodnet.models.ChangeResponse;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labymodnet/data/AbstractRequestContent.class */
public abstract class AbstractRequestContent implements RequestContent {
    protected final CosmeticRequestType type;
    protected final Consumer<ChangeResponse> changeResponseConsumer;

    public AbstractRequestContent(CosmeticRequestType type, Consumer<ChangeResponse> changeResponseConsumer) {
        this.type = type;
        this.changeResponseConsumer = changeResponseConsumer;
    }

    @Override // net.labymod.core.labymodnet.data.RequestContent
    public CosmeticRequestType getType() {
        return this.type;
    }

    @Override // net.labymod.core.labymodnet.data.RequestContent
    public Consumer<ChangeResponse> getChangeResponseConsumer() {
        return this.changeResponseConsumer;
    }
}
