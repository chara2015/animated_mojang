package net.minecraft.client.data.models.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/data/models/model/DelegatedModel.class */
public class DelegatedModel implements ModelInstance {
    private final Identifier parent;

    public DelegatedModel(Identifier $$0) {
        this.parent = $$0;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.function.Supplier
    public JsonElement get() {
        JsonObject $$0 = new JsonObject();
        $$0.addProperty("parent", this.parent.toString());
        return $$0;
    }
}
