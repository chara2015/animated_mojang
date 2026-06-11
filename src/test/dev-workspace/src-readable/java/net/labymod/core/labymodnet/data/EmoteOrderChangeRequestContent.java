package net.labymod.core.labymodnet.data;

import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.Map;
import java.util.function.Consumer;
import net.labymod.core.client.render.schematic.block.ParameterType;
import net.labymod.core.labymodnet.models.ChangeResponse;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labymodnet/data/EmoteOrderChangeRequestContent.class */
public class EmoteOrderChangeRequestContent extends AbstractRequestContent {
    private final JsonObject orderObject;

    public EmoteOrderChangeRequestContent(Int2IntOpenHashMap newOrders, Consumer<ChangeResponse> changeResponseConsumer) {
        super(CosmeticRequestType.MULTI, changeResponseConsumer);
        this.orderObject = new JsonObject();
        ObjectIterator it = newOrders.int2IntEntrySet().iterator();
        while (it.hasNext()) {
            Int2IntMap.Entry entry = (Int2IntMap.Entry) it.next();
            this.orderObject.addProperty(String.valueOf(entry.getIntKey()), Integer.valueOf(entry.getIntValue()));
        }
    }

    @Override // net.labymod.core.labymodnet.data.RequestContent
    public void fill(Map<String, String> body) {
        body.put(ParameterType.TYPE, "emotes");
        body.put("value", this.orderObject.toString());
    }
}
