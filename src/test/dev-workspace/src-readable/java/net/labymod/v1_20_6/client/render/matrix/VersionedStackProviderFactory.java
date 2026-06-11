package net.labymod.v1_20_6.client.render.matrix;

import java.util.Objects;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.api.client.render.matrix.StackProviderFactory;
import net.labymod.api.models.Implements;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/client/render/matrix/VersionedStackProviderFactory.class */
@Singleton
@Implements(StackProviderFactory.class)
public class VersionedStackProviderFactory implements StackProviderFactory {
    @Inject
    public VersionedStackProviderFactory() {
    }

    @Override // net.labymod.api.client.render.matrix.StackProviderFactory
    public Stack create() {
        return Stack.create((StackProvider) new VersionedStackProvider(new faa()));
    }

    @Override // net.labymod.api.client.render.matrix.StackProviderFactory
    public Stack create(Object poseStack) {
        Objects.requireNonNull(poseStack, "poseStack must not be null");
        if (!(poseStack instanceof faa)) {
            throw new IllegalStateException(poseStack.getClass().getName() + " is not an instance of " + String.valueOf(faa.class));
        }
        faa stack = (faa) poseStack;
        return Stack.create((StackProvider) new VersionedStackProvider(stack));
    }

    @Override // net.labymod.api.client.render.matrix.StackProviderFactory
    public Stack create(Object poseStack, Object multiBufferSource) {
        Objects.requireNonNull(poseStack, "poseStack must not be null");
        if (!(poseStack instanceof faa)) {
            throw new IllegalStateException(poseStack.getClass().getName() + " is not an instance of " + String.valueOf(faa.class));
        }
        faa stack = (faa) poseStack;
        return Stack.create((StackProvider) new VersionedStackProvider(stack)).multiBufferSource(multiBufferSource);
    }
}
