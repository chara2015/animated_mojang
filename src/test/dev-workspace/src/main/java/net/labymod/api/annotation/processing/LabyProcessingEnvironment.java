package net.labymod.api.annotation.processing;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.FileObject;
import javax.tools.JavaFileManager;
import javax.tools.StandardLocation;
import net.labymod.api.annotation.processing.exception.ProcessingException;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/annotation/processing/LabyProcessingEnvironment.class */
public class LabyProcessingEnvironment implements ProcessingEnvironment {
    private final ProcessingEnvironment delegate;

    public LabyProcessingEnvironment(ProcessingEnvironment delegate) {
        this.delegate = delegate;
    }

    public Map<String, String> getOptions() {
        return this.delegate.getOptions();
    }

    public Messager getMessager() {
        return this.delegate.getMessager();
    }

    public Filer getFiler() {
        return this.delegate.getFiler();
    }

    public Elements getElementUtils() {
        return this.delegate.getElementUtils();
    }

    public Types getTypeUtils() {
        return this.delegate.getTypeUtils();
    }

    public SourceVersion getSourceVersion() {
        return this.delegate.getSourceVersion();
    }

    public Locale getLocale() {
        return this.delegate.getLocale();
    }

    public boolean isPreviewEnabled() {
        return this.delegate.isPreviewEnabled();
    }

    public String getOption(String key) {
        return getOption(key, null);
    }

    public String getOption(String key, String def) {
        return getOptions().getOrDefault(key, def);
    }

    public FileObject createServiceResource(String name) {
        return createResource(StandardLocation.CLASS_OUTPUT, "META-INF/services/" + name, new Element[0]);
    }

    public FileObject createCustomServiceResource(String name) {
        return createResource(StandardLocation.CLASS_OUTPUT, "META-INF/custom-services/" + name + ".json", new Element[0]);
    }

    public FileObject createResource(JavaFileManager.Location location, String relativeName, Element... elements) {
        Filer filer = getFiler();
        try {
            return filer.createResource(location, "", relativeName, elements);
        } catch (IOException exception) {
            throw new ProcessingException("Could not create resource", exception);
        }
    }
}
