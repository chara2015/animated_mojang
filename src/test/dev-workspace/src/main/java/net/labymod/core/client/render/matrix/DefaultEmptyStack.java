package net.labymod.core.client.render.matrix;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.render.matrix.DefaultStackProvider;
import net.labymod.api.client.render.matrix.EmptyStack;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.api.models.Implements;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/matrix/DefaultEmptyStack.class */
@Singleton
@Implements(EmptyStack.class)
public class DefaultEmptyStack implements EmptyStack {
    private final Stack stack = Stack.create((StackProvider) new DefaultStackProvider());

    @Inject
    public DefaultEmptyStack() {
    }

    @Override // net.labymod.api.client.render.matrix.EmptyStack
    public Stack create() {
        return this.stack;
    }
}
