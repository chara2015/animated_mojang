package net.labymod.api.client.render.model;

import java.util.Map;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/ModelPartHolder.class */
public interface ModelPartHolder {
    void addChild(String str, ModelPart modelPart);

    ModelPart getChild(String str);

    Map<String, ModelPart> getChildren();
}
