package net.labymod.v1_12_2.client.settings;

import net.labymod.v1_12_2.client.LabyModKeyMapping;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/settings/KeyBindingIntHashMap.class */
public class KeyBindingIntHashMap extends rg<bhy> {
    /* JADX INFO: renamed from: addKey, reason: merged with bridge method [inline-methods] */
    public void a(int lvt_1_1_, bhy lvt_2_1_) {
        if (lvt_2_1_ instanceof LabyModKeyMapping) {
            return;
        }
        super.a(lvt_1_1_, lvt_2_1_);
    }
}
