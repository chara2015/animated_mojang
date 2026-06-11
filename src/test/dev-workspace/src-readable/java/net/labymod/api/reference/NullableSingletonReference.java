package net.labymod.api.reference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;
import net.labymod.api.reference.exception.CircularDependencyException;
import net.labymod.api.reference.util.ReferenceUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/reference/NullableSingletonReference.class */
public class NullableSingletonReference<T> extends Reference<T> {
    private static final int STACK_TRACE_DEPTH = 32;
    private static final StackWalker WALKER = StackWalker.getInstance(Collections.emptySet(), 32);
    private State state;
    private List<StackTraceElement> creatingStackTraces;
    private T reference;
    private final Lock lock;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/reference/NullableSingletonReference$State.class */
    enum State {
        NONE,
        CREATING,
        CREATED
    }

    public NullableSingletonReference(Class<T> referenceClass, Supplier<T> referenceSupplier) {
        super(referenceClass, referenceSupplier);
        this.state = State.NONE;
        this.creatingStackTraces = new ArrayList();
        this.lock = new ReentrantLock();
    }

    public T get() {
        if (this.state == State.CREATED) {
            return this.reference;
        }
        try {
            this.lock.lock();
            if (this.state == State.CREATING) {
                throw createCircularDependencyException();
            }
            if (this.reference == null) {
                createReference();
            }
            return this.reference;
        } finally {
            this.lock.unlock();
        }
    }

    private void createReference() {
        try {
            this.creatingStackTraces = createStackTrace();
            this.state = State.CREATING;
            this.reference = this.referenceSupplier.get();
            this.state = State.CREATED;
            this.creatingStackTraces = null;
        } catch (Throwable throwable) {
            ReferenceUtil.onError(throwable);
        }
    }

    private List<StackTraceElement> createStackTrace() {
        List<StackTraceElement> frames = new ArrayList<>(32);
        WALKER.forEach(stackFrame -> {
            frames.add(stackFrame.toStackTraceElement());
        });
        return frames;
    }

    private CircularDependencyException createCircularDependencyException() {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("Circular dependency detected for ").append(this.referenceClass.getName()).append("!");
        if (this.creatingStackTraces == null) {
            messageBuilder.append(" (no previous stacktrace)");
        }
        CircularDependencyException rootException = new CircularDependencyException(messageBuilder.toString());
        if (this.creatingStackTraces != null) {
            CircularDependencyException cause = new CircularDependencyException("Previous stacktrace:");
            cause.setStackTrace((StackTraceElement[]) this.creatingStackTraces.toArray(new StackTraceElement[0]));
            rootException.initCause(cause);
        }
        return rootException;
    }
}
