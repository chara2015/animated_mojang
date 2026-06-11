package net.labymod.api.client.network.server.storage;

import net.labymod.api.util.Color;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/network/server/storage/ServerFolder.class */
public class ServerFolder {
    public static final Color DEFAULT_COLOR = Color.ofRGB(236, 190, 64, 255);
    private int index;
    private int length;
    private String name;
    private Color color;

    public ServerFolder(String name, int index, int length, Color color) {
        this.name = name;
        this.index = index;
        this.length = length;
        this.color = color;
    }

    public int getStartIndex() {
        return this.index;
    }

    public int getEndIndex() {
        return (this.index + this.length) - 1;
    }

    public int getLength() {
        return this.length;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return this.color == null ? DEFAULT_COLOR : this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void shift(int offset) {
        if (this.index + offset < 0) {
            throw new IllegalArgumentException("Cannot shift folder below 0");
        }
        this.index += offset;
    }

    public void expand() {
        this.length++;
    }

    public void shrink() {
        this.length--;
    }

    public boolean isEmpty() {
        return this.length == 0;
    }

    public String toString() {
        return "Folder[" + getStartIndex() + "-" + getEndIndex() + "]";
    }
}
