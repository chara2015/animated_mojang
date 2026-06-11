package net.minecraft.client.gui.components;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/components/SelectableEntry.class */
public interface SelectableEntry {
    default boolean mouseOverIcon(int $$0, int $$1, int $$2) {
        return $$0 >= 0 && $$0 < $$2 && $$1 >= 0 && $$1 < $$2;
    }

    default boolean mouseOverLeftHalf(int $$0, int $$1, int $$2) {
        return $$0 >= 0 && $$0 < $$2 / 2 && $$1 >= 0 && $$1 < $$2;
    }

    default boolean mouseOverRightHalf(int $$0, int $$1, int $$2) {
        return $$0 >= $$2 / 2 && $$0 < $$2 && $$1 >= 0 && $$1 < $$2;
    }

    default boolean mouseOverTopRightQuarter(int $$0, int $$1, int $$2) {
        return $$0 >= $$2 / 2 && $$0 < $$2 && $$1 >= 0 && $$1 < $$2 / 2;
    }

    default boolean mouseOverBottomRightQuarter(int $$0, int $$1, int $$2) {
        return $$0 >= $$2 / 2 && $$0 < $$2 && $$1 >= $$2 / 2 && $$1 < $$2;
    }

    default boolean mouseOverTopLeftQuarter(int $$0, int $$1, int $$2) {
        return $$0 >= 0 && $$0 < $$2 / 2 && $$1 >= 0 && $$1 < $$2 / 2;
    }

    default boolean mouseOverBottomLeftQuarter(int $$0, int $$1, int $$2) {
        return $$0 >= 0 && $$0 < $$2 / 2 && $$1 >= $$2 / 2 && $$1 < $$2;
    }
}
