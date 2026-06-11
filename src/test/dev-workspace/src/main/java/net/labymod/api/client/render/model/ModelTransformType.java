package net.labymod.api.client.render.model;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/ModelTransformType.class */
public enum ModelTransformType {
    NONE,
    THIRD_PERSON_LEFT_HAND,
    THIRD_PERSON_RIGHT_HAND,
    FIRST_PERSON_LEFT_HAND,
    FIRST_PERSON_RIGHT_HAND,
    HEAD,
    GUI,
    GROUND,
    FIXED,
    ON_SHELF;

    public boolean isFirstPerson() {
        return this == FIRST_PERSON_LEFT_HAND || this == FIRST_PERSON_RIGHT_HAND;
    }
}
