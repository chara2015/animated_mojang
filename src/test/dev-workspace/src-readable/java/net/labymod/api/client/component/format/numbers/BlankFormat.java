package net.labymod.api.client.component.format.numbers;

import net.labymod.api.client.component.Component;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/component/format/numbers/BlankFormat.class */
public class BlankFormat implements NumberFormat {
    public static final BlankFormat INSTANCE = new BlankFormat();

    @Override // net.labymod.api.client.component.format.numbers.NumberFormat
    public Component format(int number) {
        return Component.empty();
    }
}
