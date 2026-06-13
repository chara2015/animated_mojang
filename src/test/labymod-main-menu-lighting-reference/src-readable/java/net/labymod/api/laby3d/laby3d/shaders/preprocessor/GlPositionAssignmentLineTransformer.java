package net.labymod.api.laby3d.shaders.preprocessor;

import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.laby3d.api.shaders.preprocessor.LineTransformer;
import net.labymod.laby3d.api.shaders.preprocessor.ProcessingContext;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/shaders/preprocessor/GlPositionAssignmentLineTransformer.class */
public class GlPositionAssignmentLineTransformer implements LineTransformer {
    private static final String DEFINE_KEYWORD = "define";
    private static final String GL_POSITION_ASSIGNMENT = "gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);";
    private static final String VANILLA_POSITION_ASSIGNMENT = "gl_Position = ProjMat * ModelViewMat * vec4(Position.xy, 0.0, 1.0);";

    public String transformLine(ProcessingContext context, String line) {
        if (context.getDirectivesFor(DEFINE_KEYWORD).contains(RenderStates.LABYMOD_GUI_FLAG)) {
            String strippedLine = line.stripLeading();
            if (strippedLine.equals(GL_POSITION_ASSIGNMENT)) {
                return VANILLA_POSITION_ASSIGNMENT;
            }
        }
        return line;
    }
}
