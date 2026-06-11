package net.labymod.core.rename;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import net.labymod.api.mappings.MappingFile;
import net.labymod.api.mappings.io.SrgMappingReader;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.volt.rename.ClassProvider;
import net.labymod.core.util.logging.DefaultLoggingFactory;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.commons.Remapper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/rename/RenamedFromService.class */
public class RenamedFromService {
    private static final Logging LOGGER = DefaultLoggingFactory.createLogger((Class<?>) RenamedFromService.class);
    private final ClassProvider classProvider;
    private final String namespace;
    private final ClassLoader classLoader;
    private MappingFile mappingFile;
    private Remapper remapper;

    public RenamedFromService(String namespace, ClassLoader classLoader, ClassProvider.ResourceSupplier resourceSupplier) {
        this.classProvider = ClassProvider.getSingleton(resourceSupplier);
        this.namespace = namespace;
        this.classLoader = classLoader;
    }

    public void load() {
        URL mappingsResource = this.classLoader.getResource("assets/" + this.namespace + "/mappings/" + this.namespace + ".srg");
        if (mappingsResource == null) {
            LOGGER.debug("", new Object[0]);
            return;
        }
        try {
            InputStream stream = mappingsResource.openStream();
            try {
                SrgMappingReader reader = new SrgMappingReader();
                this.mappingFile = reader.read(stream);
                this.remapper = new RenamedFromRemapper(this.mappingFile, this.classProvider);
                if (stream != null) {
                    stream.close();
                }
            } finally {
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to load mappings", exception);
        }
    }

    public MappingFile getMappingFile() {
        return this.mappingFile;
    }

    @Nullable
    public Remapper getRemapper() {
        return this.remapper;
    }
}
