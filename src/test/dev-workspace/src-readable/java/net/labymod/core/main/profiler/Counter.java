package net.labymod.core.main.profiler;

import net.labymod.api.client.gfx.imgui.LabyImGui;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/profiler/Counter.class */
public class Counter {
    private final int type;
    private final String name;
    private int count;

    public Counter(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public void update() {
        this.count++;
    }

    public void reset() {
        this.count = 0;
    }

    public int getType() {
        return this.type;
    }

    public int getCount() {
        return this.count;
    }

    public void renderImGui() {
        LabyImGui.keyValuePair(this.name, getCount());
    }
}
