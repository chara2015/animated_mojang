package net.labymod.api.client.gfx.pipeline;

import java.util.ArrayDeque;
import java.util.Deque;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.util.function.Functional;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/RenderAttributesStack.class */
public final class RenderAttributesStack {
    private final Deque<RenderAttributes> stack = (Deque) Functional.of(new ArrayDeque(), stack -> {
        stack.push(new RenderAttributes());
    });

    public void push() {
        ThreadSafe.ensureRenderThread();
        RenderAttributes last = last();
        this.stack.addLast(new RenderAttributes(last));
    }

    public RenderAttributes pushAndGet() {
        push();
        return last();
    }

    public void pop() {
        ThreadSafe.ensureRenderThread();
        this.stack.removeLast();
        RenderAttributes attributes = last();
        attributes.apply();
    }

    public RenderAttributes last() {
        return this.stack.getLast();
    }

    public boolean clear() {
        return this.stack.size() == 1;
    }

    public void checkStack() {
        if (!clear()) {
            throw new IllegalStateException("RenderAttributes stack is not empty!");
        }
    }
}
