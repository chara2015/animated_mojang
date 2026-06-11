package net.labymod.api.client.gui.screen.state.offscreen;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/offscreen/ShelfBinPacker.class */
final class ShelfBinPacker {
    private int width;
    private int height;
    private int shelfY;
    private int shelfHeight;
    private int cursorX;
    private int lastX;
    private int lastY;

    ShelfBinPacker(int width, int height) {
        resize(width, height);
    }

    void resize(int width, int height) {
        this.width = width;
        this.height = height;
        reset();
    }

    void reset() {
        this.shelfY = 0;
        this.shelfHeight = 0;
        this.cursorX = 0;
    }

    boolean pack(int w, int h) {
        if (w > this.width || h > this.height) {
            return false;
        }
        if (this.cursorX + w > this.width) {
            this.shelfY += this.shelfHeight;
            this.cursorX = 0;
            this.shelfHeight = 0;
        }
        if (this.shelfY + h > this.height) {
            return false;
        }
        this.lastX = this.cursorX;
        this.lastY = this.shelfY;
        this.cursorX += w;
        if (h > this.shelfHeight) {
            this.shelfHeight = h;
            return true;
        }
        return true;
    }

    int lastX() {
        return this.lastX;
    }

    int lastY() {
        return this.lastY;
    }
}
