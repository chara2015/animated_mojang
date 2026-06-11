package net.labymod.api.client.gui.screen.util.scissor;

import java.util.ArrayDeque;
import java.util.Deque;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/util/scissor/ScissorStack.class */
class ScissorStack {
    private final Deque<ScissorArea> stack = new ArrayDeque();

    ScissorStack() {
    }

    public ScissorArea push(ScissorArea rectangle) {
        ScissorArea latest = peek();
        if (latest == null) {
            this.stack.addLast(rectangle);
            return rectangle;
        }
        ScissorArea result = rectangle.intersection(latest);
        this.stack.addLast(result);
        return result;
    }

    public ScissorArea pop() {
        if (this.stack.isEmpty()) {
            throw new IllegalStateException("Scissor stack underflow");
        }
        this.stack.removeLast();
        return peek();
    }

    @Nullable
    public ScissorArea peek() {
        return this.stack.peekLast();
    }
}
