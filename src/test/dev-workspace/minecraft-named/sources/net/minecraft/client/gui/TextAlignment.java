package net.minecraft.client.gui;

import net.minecraft.util.FormattedCharSequence;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/TextAlignment.class */
public enum TextAlignment {
    LEFT { // from class: net.minecraft.client.gui.TextAlignment.1
        @Override // net.minecraft.client.gui.TextAlignment
        public int calculateLeft(int $$0, int $$1) {
            return $$0;
        }

        @Override // net.minecraft.client.gui.TextAlignment
        public int calculateLeft(int $$0, Font $$1, FormattedCharSequence $$2) {
            return $$0;
        }
    },
    CENTER { // from class: net.minecraft.client.gui.TextAlignment.2
        @Override // net.minecraft.client.gui.TextAlignment
        public int calculateLeft(int $$0, int $$1) {
            return $$0 - ($$1 / 2);
        }
    },
    RIGHT { // from class: net.minecraft.client.gui.TextAlignment.3
        @Override // net.minecraft.client.gui.TextAlignment
        public int calculateLeft(int $$0, int $$1) {
            return $$0 - $$1;
        }
    };

    public abstract int calculateLeft(int i, int i2);

    public int calculateLeft(int $$0, Font $$1, FormattedCharSequence $$2) {
        return calculateLeft($$0, $$1.width($$2));
    }
}
