package net.labymod.api.configuration.settings.creator.visibility;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import net.labymod.api.LabyAPI;
import net.labymod.api.configuration.settings.creator.MemberInspector;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/creator/visibility/VisibleProcessorRegistry.class */
public class VisibleProcessorRegistry {
    private final List<VisibilityEvaluator<? extends Annotation>> processors = new ArrayList();
    private final LabyAPI labyAPI;

    public VisibleProcessorRegistry(LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
        registerDefaults();
    }

    private void registerDefaults() {
        registerProcessor(new ModRequirementVisibilityEvaluator(this.labyAPI));
        registerProcessor(new OptiFineRequirementVisibilityEvaluator());
        registerProcessor(new ModLoaderVisibilityEvaluator(this.labyAPI));
    }

    public void registerProcessor(VisibilityEvaluator<?> processor) {
        this.processors.add(processor);
    }

    public BooleanSupplier canSeeElement(MemberInspector element) {
        for (VisibilityEvaluator<? extends Annotation> visibilityEvaluator : this.processors) {
            Annotation annotation = element.getAnnotation(visibilityEvaluator.getAnnotationClass());
            if (visibilityEvaluator.hasAnnotation(annotation)) {
                return visibilityEvaluator.canSeeElement(annotation, element);
            }
        }
        return null;
    }
}
