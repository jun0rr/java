package net.bytebuddy.implementation.bytecode.assign.primitive;

import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.bytecode.Removal;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.assign.Assigner;
import net.bytebuddy.implementation.bytecode.constant.DefaultValue;

/**
 * This assigner is able to handle the {@code void} type. This means:
 * <ol>
 * <li>If a {@code void} type is assigned to the {@code void} it will consider this a trivial operation.</li>
 * <li>If a {@code void} type is assigned to a non-{@code void} type, it will pop the top value from the stack.</li>
 * <li>If a non-{@code void} type is assigned to a {@code void} type, it will load the target type's default value
 * only if this was configured at the assigner's construction.</li>
 * <li>If two non-{@code void} types are subject of the assignment, it will delegate the assignment to its chained
 * assigner.</li>
 * </ol>
 */
public class VoidAwareAssigner implements Assigner {

    /**
     * An assigner that is capable of handling assignments that do not involve {@code void} types.
     */
    private final Assigner chained;

    /**
     * Creates a new assigner that is capable of handling void types.
     *
     * @param chained A chained assigner which will be queried by this assigner to handle assignments that
     *                do not involve a {@code void} type.
     */
    public VoidAwareAssigner(Assigner chained) {
        this.chained = chained;
    }

    @Override
    public StackManipulation assign(TypeDescription sourceType, TypeDescription targetType, boolean dynamicallyTyped) {
        if (sourceType.represents(void.class) && targetType.represents(void.class)) {
            return StackManipulation.LegalTrivial.INSTANCE;
        } else if (sourceType.represents(void.class) /* && subType != void.class */) {
            return dynamicallyTyped
                    ? DefaultValue.of(targetType)
                    : StackManipulation.Illegal.INSTANCE;
        } else if (/* superType != void.class && */ targetType.represents(void.class)) {
            return Removal.pop(sourceType);
        } else /* superType != void.class && subType != void.class */ {
            return chained.assign(sourceType, targetType, dynamicallyTyped);
        }
    }

    @Override
    public boolean equals(Object other) {
        return this == other || !(other == null || getClass() != other.getClass())
                && chained.equals(((VoidAwareAssigner) other).chained);
    }

    @Override
    public int hashCode() {
        return chained.hashCode();
    }

    @Override
    public String toString() {
        return "VoidAwareAssigner{chained=" + chained + '}';
    }
}
