package net.labymod.v1_21_11.client.util;

import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/client/util/PoseStackVisitor.class */
public class PoseStackVisitor implements d {
    private final fzm stack;

    public PoseStackVisitor(fzm stack) {
        this.stack = stack;
    }

    public void visit(@NotNull a pose, @NotNull String partName, int index, @NotNull a cube) {
        this.stack.a(pose.a());
    }
}
