package net.labymod.core.client.gui.inventory;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/inventory/InventorySlotData.class */
public class InventorySlotData {
    private final Version version;
    private final String name;
    private final int slotIndex;
    private final int x;
    private final int y;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/inventory/InventorySlotData$Version.class */
    enum Version {
        LEGACY,
        MODERN
    }

    public InventorySlotData(Version version, String name, int slotIndex, int x, int y) {
        this.version = version;
        this.name = name;
        this.slotIndex = slotIndex;
        this.x = x;
        this.y = y;
    }

    public static InventorySlotData createLegacy(String name, int slotIndex, int x, int y) {
        return new InventorySlotData(Version.LEGACY, name, slotIndex, x, y);
    }

    public static InventorySlotData createModern(String name, int slotIndex, int x, int y) {
        return new InventorySlotData(Version.MODERN, name, slotIndex, x, y);
    }

    public Version getVersion() {
        return this.version;
    }

    public String getName() {
        return this.name;
    }

    public int getSlotIndex() {
        return this.slotIndex;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}
