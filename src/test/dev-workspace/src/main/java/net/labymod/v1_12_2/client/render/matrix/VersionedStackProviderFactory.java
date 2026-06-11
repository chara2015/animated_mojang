package net.labymod.v1_12_2.client.render.matrix;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.StackProviderFactory;
import net.labymod.api.models.Implements;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/render/matrix/VersionedStackProviderFactory.class */
@Singleton
@Implements(StackProviderFactory.class)
public class VersionedStackProviderFactory implements StackProviderFactory {
    @Inject
    public VersionedStackProviderFactory() {
    }

    @Override // net.labymod.api.client.render.matrix.StackProviderFactory
    public Stack create() {
        return VersionedStackProvider.DEFAULT_STACK;
    }

    @Override // net.labymod.api.client.render.matrix.StackProviderFactory
    public Stack create(Object poseStack) {
        return VersionedStackProvider.DEFAULT_STACK;
    }

    @Override // net.labymod.api.client.render.matrix.StackProviderFactory
    public Stack create(Object poseStack, Object multiBufferSource) {
        return VersionedStackProvider.DEFAULT_STACK;
    }
}
