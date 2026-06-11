package net.labymod.v1_12_2.client.resources.pack;

import java.util.function.Consumer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/resources/pack/ResourceLister.class */
public interface ResourceLister {
    void listResources(String str, String str2, Consumer<nf> consumer);

    static void listResources(cer pack, String namespace, String baseDirectory, Consumer<nf> locationConsumer) {
        if (!(pack instanceof ResourceLister)) {
            return;
        }
        ResourceLister resourceLister = (ResourceLister) pack;
        resourceLister.listResources(namespace, baseDirectory, locationConsumer);
    }
}
