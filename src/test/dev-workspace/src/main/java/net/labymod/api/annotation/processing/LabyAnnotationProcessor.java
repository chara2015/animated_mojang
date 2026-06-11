package net.labymod.api.annotation.processing;

import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.JavaFileManager;
import net.labymod.api.annotation.processing.exception.ProcessingException;
import net.labymod.api.annotation.processing.util.ProcessorUtil;
import net.labymod.api.annotation.processing.util.StringUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/annotation/processing/LabyAnnotationProcessor.class */
public abstract class LabyAnnotationProcessor extends AbstractProcessor {
    private static final String PROJECT_ID_KEY = "projectId";
    private static final String PROJECT_NAME_KEY = "projectName";
    private static final String DEFAULT_PACKAGE_NAME_KEY = "defaultPackageName";
    private static final String VERSIONED_MODULE_KEY = "versionedModule";
    private static final String SOURCE_SET_NAME_KEY = "sourceSetName";
    private static final String RUNNING_VERSION_KEY = "runningVersion";
    private static final String JAVA_VERSION_KEY = "javaVersion";
    private LabyProcessingEnvironment processingEnvironment;
    private String projectId;
    private String projectName;
    private boolean versionedModule;
    private String defaultPackageName;
    private String sourceSetName;
    private String runningVersion;
    private int classVersion;

    protected abstract void onProcess(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment);

    protected abstract void onProcessingOver();

    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.processingEnvironment = new LabyProcessingEnvironment(processingEnv);
        this.projectId = getOption(PROJECT_ID_KEY);
        this.projectName = getOption(PROJECT_NAME_KEY);
        this.classVersion = readClassVersion();
        this.defaultPackageName = getOption(DEFAULT_PACKAGE_NAME_KEY);
        this.versionedModule = Boolean.parseBoolean(getOption(VERSIONED_MODULE_KEY, "false"));
        this.sourceSetName = StringUtil.validateName(getOption(SOURCE_SET_NAME_KEY));
        this.runningVersion = getOption(RUNNING_VERSION_KEY);
        Messager messager = processingEnv.getMessager();
        validateProject(messager, PROJECT_ID_KEY, this.projectId);
        validateProject(messager, PROJECT_NAME_KEY, this.projectName);
    }

    private void validateProject(Messager messager, String name, String value) {
        if (value == null) {
            messager.printMessage(Diagnostic.Kind.WARNING, "Project was not set up correctly, " + name + " is null");
        }
    }

    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        boolean shouldProcess = ProcessorUtil.shouldProcess(this, this.processingEnvironment.getMessager(), () -> {
            return this.projectId == null;
        });
        if (!shouldProcess) {
            return false;
        }
        if (roundEnv.processingOver()) {
            onProcessingOver();
            return true;
        }
        onProcess(annotations, roundEnv);
        return true;
    }

    public FileObject createServiceResource(String name) {
        return this.processingEnvironment.createServiceResource(name);
    }

    public FileObject createCustomServiceResource(String name) {
        return this.processingEnvironment.createCustomServiceResource(name);
    }

    public FileObject createResource(JavaFileManager.Location location, String relativeName, Element... elements) {
        return this.processingEnvironment.createResource(location, relativeName, elements);
    }

    public Filer getFiler() {
        return this.processingEnvironment.getFiler();
    }

    public void printMessage(Diagnostic.Kind kind, CharSequence message) {
        Messager messager = this.processingEnvironment.getMessager();
        messager.printMessage(kind, message);
    }

    public String getOption(String key) {
        return this.processingEnvironment.getOption(key);
    }

    public String getOption(String key, String def) {
        return this.processingEnvironment.getOption(key, def);
    }

    public String getPackageName() {
        String name = _getSourceSetName();
        return this.defaultPackageName + "." + name + ".generated";
    }

    public String getProjectId() {
        return this.projectId;
    }

    public String getProjectName() {
        return this.projectName;
    }

    public boolean isVersionedModule() {
        return this.versionedModule;
    }

    public String getDefaultPackageName() {
        return this.defaultPackageName;
    }

    public String getSourceSetName() {
        return this.sourceSetName;
    }

    public String getRunningVersion() {
        return this.runningVersion;
    }

    public int getClassVersion() {
        return this.classVersion;
    }

    protected boolean isNotSupportedAnnotationType(TypeElement element) {
        return !isSupportedAnnotationType(element);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x005e A[PHI: r11
  0x005e: PHI (r11v1 's' java.lang.String) = (r11v0 's' java.lang.String), (r11v2 's' java.lang.String) binds: [B:6:0x003e, B:8:0x0056] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected boolean isSupportedAnnotationType(javax.lang.model.element.TypeElement r6) {
        /*
            r5 = this;
            r0 = r6
            javax.lang.model.element.Name r0 = r0.getQualifiedName()
            java.lang.String r0 = r0.toString()
            r7 = r0
            r0 = 0
            r8 = r0
            r0 = r5
            java.util.Set r0 = r0.getSupportedAnnotationTypes()
            r9 = r0
            r0 = r9
            java.util.Iterator r0 = r0.iterator()
            r10 = r0
        L1d:
            r0 = r10
            boolean r0 = r0.hasNext()
            if (r0 == 0) goto L6f
            r0 = r10
            java.lang.Object r0 = r0.next()
            java.lang.String r0 = (java.lang.String) r0
            r11 = r0
            r0 = r11
            java.lang.String r1 = "*"
            boolean r0 = r0.endsWith(r1)
            r12 = r0
            r0 = r12
            if (r0 == 0) goto L5e
            r0 = r11
            r1 = 0
            r2 = r11
            int r2 = r2.length()
            r3 = 1
            int r2 = r2 - r3
            java.lang.String r0 = r0.substring(r1, r2)
            r11 = r0
            r0 = r7
            r1 = r11
            boolean r0 = r0.startsWith(r1)
            if (r0 == 0) goto L5e
            r0 = 1
            r8 = r0
            goto L6f
        L5e:
            r0 = r7
            r1 = r11
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L6c
            r0 = 1
            r8 = r0
            goto L6f
        L6c:
            goto L1d
        L6f:
            r0 = r8
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: net.labymod.api.annotation.processing.LabyAnnotationProcessor.isSupportedAnnotationType(javax.lang.model.element.TypeElement):boolean");
    }

    private String _getSourceSetName() {
        if (this.versionedModule) {
            return this.sourceSetName;
        }
        return StringUtil.validateName(this.projectName);
    }

    private int readClassVersion() {
        String value = getOption(JAVA_VERSION_KEY, "52.0");
        try {
            return (int) Double.parseDouble(value);
        } catch (NumberFormatException exception) {
            throw new ProcessingException("Invalid class version (" + value + ")", exception);
        }
    }
}
