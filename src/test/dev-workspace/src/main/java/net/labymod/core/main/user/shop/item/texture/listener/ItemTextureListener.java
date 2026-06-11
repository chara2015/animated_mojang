package net.labymod.core.main.user.shop.item.texture.listener;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/item/texture/listener/ItemTextureListener.class */
public interface ItemTextureListener {
    public static final ItemTextureListener NOP_LISTENER = new ItemTextureListener() { // from class: net.labymod.core.main.user.shop.item.texture.listener.ItemTextureListener.1
        @Override // net.labymod.core.main.user.shop.item.texture.listener.ItemTextureListener
        public void onBegin() {
        }

        @Override // net.labymod.core.main.user.shop.item.texture.listener.ItemTextureListener
        public void onSuccess() {
        }

        @Override // net.labymod.core.main.user.shop.item.texture.listener.ItemTextureListener
        public void onError() {
        }
    };

    void onBegin();

    void onSuccess();

    void onError();
}
