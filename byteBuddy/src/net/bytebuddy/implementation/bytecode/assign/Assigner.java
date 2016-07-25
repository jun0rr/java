package net.bytebuddy.implementation.bytecode.assign;

import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.assign.primitive.PrimitiveTypeAwareAssigner;
import net.bytebuddy.implementation.bytecode.assign.primitive.VoidAwareAssigner;
import net.bytebuddy.implementation.bytecode.assign.reference.ReferenceTypeAwareAssigner;

/**
 * An assigner is responsible for converting some type {@code A} to another type {@code B} if possible.
 * <p>&nbsp;</p>
 * An assigner is for example responsible for type casting, auto boxing or unboxing or for the widening of primitive
 * types.
 */
public interface Assigner {

    /**
     * A default assigner that can handle {@code void}, primitive types and references.
     */
    Assigner DEFAULT = new VoidAwareAssigner(new PrimitiveTypeAwareAssigner(ReferenceTypeAwareAssigner.INSTANCE));

    /**
     * Indicates to an {@link net.bytebuddy.implementation.bytecode.assign.Assigner} that a value should be typed statically.
     */
    boolean STATICALLY_TYPED = false;

    /**
     * Indicates to an {@link net.bytebuddy.implementation.bytecode.assign.Assigner} that a value should be casted at runtime.
     */
    boolean DYNAMICALLY_TYPED = true;

    /**
     * @param sourceType       The original type that is to be transformed into the {@code targetType}.
     * @param targetType       The target type into which the {@code sourceType} is to be converted.
     * @param dynamicallyTyped A hint whether the assignment should consider the runtime type of the source type,
     *                         i.e. if type down or cross castings are allowed. If this hint is set, this is
     *                         also an indication that {@code void} to non-{@code void} assignments are permitted.
     * @return A stack manipulation that transforms the {@code sourceType} into the {@code targetType} if this
     * is possible. An illegal stack manipulation otherwise.
     */
    StackManipulation assign(TypeDescription sourceType, TypeDescription targetType, boolean dynamicallyTyped);

    /**
     * An assigner that only allows to assign types if they are equal to another.
     */
    enum EqualTypesOnly implements Assigner {

        /**
         * The singleton instance.
         */
        INSTANCE;

        @Override
        public StackManipulation assign(TypeDescription sourceType,
                                        TypeDescription targetType,
                                        boolean dynamicallyTyped) {
            return sourceType.equals(targetType)
                    ? StackManipulation.LegalTrivial.INSTANCE
                    : StackManipulation.Illegal.INSTANCE;
        }

        @Override
        public String toString() {
            return "Assigner.EqualTypesOnly." + name();
        }
    }

    /**
     * An assigner that does not allow any assignments.
     */
    enum Refusing implements Assigner {

        /**
         * The singleton instance.
         */
        INSTANCE;

        @Override
        public StackManipulation assign(TypeDescription sourceType,
                                        TypeDescription targetType,
                                        boolean dynamicallyTyped) {
            return StackManipulation.Illegal.INSTANCE;
        }

        @Override
        public String toString() {
            return "Assigner.Refusing." + name();
        }
    }
}
