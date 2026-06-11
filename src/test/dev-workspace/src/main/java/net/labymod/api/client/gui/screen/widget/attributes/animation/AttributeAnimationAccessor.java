package net.labymod.api.client.gui.screen.widget.attributes.animation;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/attributes/animation/AttributeAnimationAccessor.class */
public interface AttributeAnimationAccessor<T> {
    T get();

    void set(T t);

    String getName();

    Class<T> getType();
}
