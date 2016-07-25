package net.bytebuddy.implementation.bind.annotation;

import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.method.ParameterDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.assign.Assigner;
import net.bytebuddy.implementation.bytecode.constant.NullConstant;
import net.bytebuddy.implementation.bytecode.member.MethodVariableAccess;

import java.lang.annotation.*;

/**
 * Parameters that are annotated with this annotation will be assigned a reference to the instrumented object, if
 * the instrumented method is not static. Otherwise, the method with this parameter annotation will be excluded from
 * the list of possible binding candidates of the static source method.
 *
 * @see net.bytebuddy.implementation.MethodDelegation
 * @see net.bytebuddy.implementation.bind.annotation.TargetMethodAnnotationDrivenBinder
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface This {

    /**
     * Determines if the annotated parameter should be bound to {@code null} when intercepting a {@code static} method.
     *
     * @return {@code true} if the annotated parameter should be bound to {@code null} as a fallback.
     */
    boolean optional() default false;

    /**
     * A binder for handling the
     * {@link net.bytebuddy.implementation.bind.annotation.This}
     * annotation.
     *
     * @see TargetMethodAnnotationDrivenBinder
     */
    enum Binder implements TargetMethodAnnotationDrivenBinder.ParameterBinder<This> {

        /**
         * The singleton instance.
         */
        INSTANCE;

        /**
         * The index of the {@code this} reference of method variable arrays of non-static methods.
         */
        private static final int THIS_REFERENCE_INDEX = 0;

        @Override
        public Class<This> getHandledType() {
            return This.class;
        }

        @Override
        public MethodDelegationBinder.ParameterBinding<?> bind(AnnotationDescription.Loadable<This> annotation,
                                                               MethodDescription source,
                                                               ParameterDescription target,
                                                               Implementation.Target implementationTarget,
                                                               Assigner assigner) {
            if (target.getTypeDescription().isPrimitive()) {
                throw new IllegalStateException(String.format("The %d. argument virtual %s is a primitive type " +
                        "and can never be bound to an instance", target.getIndex(), target));
            } else if (target.getTypeDescription().isArray()) {
                throw new IllegalStateException(String.format("The %d. argument virtual %s is an array type " +
                        "and can never be bound to an instance", target.getIndex(), target));
            } else if (source.isStatic() && !annotation.loadSilent().optional()) {
                return MethodDelegationBinder.ParameterBinding.Illegal.INSTANCE;
            }
            StackManipulation assignment = source.isStatic()
                    ? NullConstant.INSTANCE
                    : new StackManipulation.Compound(MethodVariableAccess.REFERENCE.loadOffset(THIS_REFERENCE_INDEX),
                    assigner.assign(implementationTarget.getTypeDescription(), target.getTypeDescription(), RuntimeType.Verifier.check(target)));
            return assignment.isValid()
                    ? new MethodDelegationBinder.ParameterBinding.Anonymous(assignment)
                    : MethodDelegationBinder.ParameterBinding.Illegal.INSTANCE;
        }

        @Override
        public String toString() {
            return "This.Binder." + name();
        }
    }
}

