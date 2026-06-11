package net.labymod.core.client.render.model.animation.molang;

import net.laby.lib.bedrock.molang.MolangEngine;
import net.laby.lib.bedrock.molang.MolangExpression;
import net.labymod.api.client.render.model.animation.DynamicValue;
import net.labymod.api.client.render.model.animation.EvaluationContext;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/model/animation/molang/MolangDynamicValue.class */
public final class MolangDynamicValue implements DynamicValue {
    private final MolangExpression expression;
    private final boolean toRadians;
    private final boolean negate;

    private MolangDynamicValue(MolangExpression expression, boolean toRadians, boolean negate) {
        this.expression = expression;
        this.toRadians = toRadians;
        this.negate = negate;
    }

    public static MolangDynamicValue of(String expression) {
        return new MolangDynamicValue(engine().parse(expression), false, false);
    }

    public static MolangDynamicValue rotation(String expression) {
        return new MolangDynamicValue(engine().parse(expression), true, false);
    }

    public static MolangDynamicValue negated(String expression) {
        return new MolangDynamicValue(engine().parse(expression), false, true);
    }

    private static MolangEngine engine() {
        return MolangEvaluationContext.sharedEngine();
    }

    @Override // net.labymod.api.client.render.model.animation.DynamicValue
    public double resolve(@Nullable EvaluationContext context) {
        if (!(context instanceof MolangEvaluationContext)) {
            return 0.0d;
        }
        MolangEvaluationContext mctx = (MolangEvaluationContext) context;
        double result = this.expression.evaluate(mctx.molangContext());
        if (this.toRadians) {
            result = Math.toRadians(result);
        } else if (this.negate) {
            result = -result;
        }
        return result;
    }
}
