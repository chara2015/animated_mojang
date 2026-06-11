package net.minecraft.util.parsing.packrat;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/parsing/packrat/Rule.class */
public interface Rule<S, T> {

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/parsing/packrat/Rule$RuleAction.class */
    @FunctionalInterface
    public interface RuleAction<S, T> {
        T run(ParseState<S> parseState);
    }

    T parse(ParseState<S> parseState);

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/parsing/packrat/Rule$SimpleRuleAction.class */
    @FunctionalInterface
    public interface SimpleRuleAction<S, T> extends RuleAction<S, T> {
        T run(Scope scope);

        @Override // net.minecraft.util.parsing.packrat.Rule.RuleAction
        default T run(ParseState<S> $$0) {
            return run($$0.scope());
        }
    }

    static <S, T> Rule<S, T> fromTerm(Term<S> $$0, RuleAction<S, T> $$1) {
        return new WrappedTerm($$1, $$0);
    }

    static <S, T> Rule<S, T> fromTerm(Term<S> $$0, SimpleRuleAction<S, T> $$1) {
        return new WrappedTerm($$1, $$0);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/parsing/packrat/Rule$WrappedTerm.class */
    public static final class WrappedTerm<S, T> extends Record implements Rule<S, T> {
        private final RuleAction<S, T> action;
        private final Term<S> child;

        public WrappedTerm(RuleAction<S, T> $$0, Term<S> $$1) {
            this.action = $$0;
            this.child = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, WrappedTerm.class), WrappedTerm.class, "action;child", "FIELD:Lnet/minecraft/util/parsing/packrat/Rule$WrappedTerm;->action:Lnet/minecraft/util/parsing/packrat/Rule$RuleAction;", "FIELD:Lnet/minecraft/util/parsing/packrat/Rule$WrappedTerm;->child:Lnet/minecraft/util/parsing/packrat/Term;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, WrappedTerm.class), WrappedTerm.class, "action;child", "FIELD:Lnet/minecraft/util/parsing/packrat/Rule$WrappedTerm;->action:Lnet/minecraft/util/parsing/packrat/Rule$RuleAction;", "FIELD:Lnet/minecraft/util/parsing/packrat/Rule$WrappedTerm;->child:Lnet/minecraft/util/parsing/packrat/Term;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, WrappedTerm.class, Object.class), WrappedTerm.class, "action;child", "FIELD:Lnet/minecraft/util/parsing/packrat/Rule$WrappedTerm;->action:Lnet/minecraft/util/parsing/packrat/Rule$RuleAction;", "FIELD:Lnet/minecraft/util/parsing/packrat/Rule$WrappedTerm;->child:Lnet/minecraft/util/parsing/packrat/Term;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public RuleAction<S, T> action() {
            return this.action;
        }

        public Term<S> child() {
            return this.child;
        }

        @Override // net.minecraft.util.parsing.packrat.Rule
        public T parse(ParseState<S> $$0) {
            Scope $$1 = $$0.scope();
            $$1.pushFrame();
            try {
                if (this.child.parse($$0, $$1, Control.UNBOUND)) {
                    T tRun = this.action.run($$0);
                    $$1.popFrame();
                    return tRun;
                }
                return null;
            } finally {
                $$1.popFrame();
            }
        }
    }
}
