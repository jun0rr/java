package net.bytebuddy.implementation.bytecode.member;

import net.bytebuddy.description.enumeration.EnumerationDescription;
import net.bytebuddy.description.field.FieldDescription;
import net.bytebuddy.description.field.FieldList;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.StackSize;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * An access representation to a given field.
 */
public enum FieldAccess {

    /**
     * The representation of field access to a static field.
     */
    STATIC(Opcodes.PUTSTATIC, Opcodes.GETSTATIC, StackSize.ZERO),

    /**
     * The representation of field access to an instance field.
     */
    INSTANCE(Opcodes.PUTFIELD, Opcodes.GETFIELD, StackSize.SINGLE);

    /**
     * The opcode for setting a field value.
     */
    private final int putterOpcode;

    /**
     * The opcode for getting a field value.
     */
    private final int getterOpcode;

    /**
     * The amount of operand slots this field access operation consumes when it is applied before eventually
     * adding new values onto the operand stack.
     */
    private final int targetSizeChange;

    /**
     * Creates a new field access.
     *
     * @param putterOpcode     The opcode for setting a field value.
     * @param getterOpcode     The opcode for getting a field value.
     * @param targetSizeChange The amount of operand slots this field access operation consumes when it is applied
     *                         before eventually adding new values onto the operand stack.
     */
    FieldAccess(int putterOpcode, int getterOpcode, StackSize targetSizeChange) {
        this.putterOpcode = putterOpcode;
        this.getterOpcode = getterOpcode;
        this.targetSizeChange = targetSizeChange.getSize();
    }

    /**
     * Creates an accessor to read an enumeration value.
     *
     * @param enumerationDescription The description of the enumeration.
     * @return A stack manipulation for reading the enumeration.
     */
    public static StackManipulation forEnumeration(EnumerationDescription enumerationDescription) {
        FieldList fieldList = enumerationDescription.getEnumerationType().getDeclaredFields().filter(named(enumerationDescription.getValue()));
        return fieldList.size() != 1 || !fieldList.getOnly().isStatic() || !fieldList.getOnly().isPublic() || !fieldList.getOnly().isEnum()
                ? StackManipulation.Illegal.INSTANCE
                : STATIC.new AccessDispatcher(fieldList.getOnly()).getter();
    }

    /**
     * Creates a field access representation for a given field.
     *
     * @param fieldDescription The field to be accessed.
     * @return A field access definition for the given field.
     */
    public static Defined forField(FieldDescription fieldDescription) {
        if (fieldDescription.isStatic()) {
            return STATIC.new AccessDispatcher(fieldDescription);
        } else {
            return INSTANCE.new AccessDispatcher(fieldDescription);
        }
    }

    @Override
    public String toString() {
        return "FieldAccess." + name();
    }

    /**
     * Representation of a field access for which a getter and a putter can be created.
     */
    public interface Defined {

        /**
         * Creates a getter representation for a given field.
         *
         * @return A stack manipulation representing the retrieval of a field value.
         */
        StackManipulation getter();

        /**
         * Creates a putter representation for a given field.
         *
         * @return A stack manipulation representing the setting of a field value.
         */
        StackManipulation putter();

        /**
         * Returns the field for which this field access is defined for.
         *
         * @return The field for which this field access was defined for.
         */
        FieldDescription getDefinedField();
    }

    /**
     * A dispatcher for implementing a read or write access on a field.
     */
    protected class AccessDispatcher implements Defined {

        /**
         * A description of the accessed field.
         */
        private final FieldDescription fieldDescription;

        /**
         * Creates a new access dispatcher.
         *
         * @param fieldDescription A description of the accessed field.
         */
        protected AccessDispatcher(FieldDescription fieldDescription) {
            this.fieldDescription = fieldDescription;
        }

        @Override
        public StackManipulation getter() {
            return new FieldGetInstruction();
        }

        @Override
        public StackManipulation putter() {
            return new FieldPutInstruction();
        }

        @Override
        public FieldDescription getDefinedField() {
            return fieldDescription;
        }

        @Override
        public boolean equals(Object other) {
            return this == other || !(other == null || getClass() != other.getClass())
                    && FieldAccess.this.equals(((AccessDispatcher) other).getFieldAccess())
                    && fieldDescription.equals(((AccessDispatcher) other).fieldDescription);
        }

        @Override
        public int hashCode() {
            return fieldDescription.hashCode() + 31 * FieldAccess.this.hashCode();
        }

        @Override
        public String toString() {
            return "FieldAccess.AccessDispatcher{" +
                    "fieldAccess=" + FieldAccess.this +
                    ", fieldDescription=" + fieldDescription +
                    '}';
        }

        /**
         * Returns the outer instance.
         *
         * @return The outer instance.
         */
        private FieldAccess getFieldAccess() {
            return FieldAccess.this;
        }

        /**
         * An abstract base implementation for accessing a field value.
         */
        private abstract class AbstractFieldInstruction implements StackManipulation {

            @Override
            public boolean isValid() {
                return true;
            }

            @Override
            public Size apply(MethodVisitor methodVisitor, Implementation.Context implementationContext) {
                methodVisitor.visitFieldInsn(getOpcode(),
                        fieldDescription.getDeclaringType().getInternalName(),
                        fieldDescription.getInternalName(),
                        fieldDescription.getDescriptor());
                return resolveSize(fieldDescription.getFieldType().getStackSize());
            }

            /**
             * Returns the opcode for implementing the field access.
             *
             * @return The opcode for implementing the field access.
             */
            protected abstract int getOpcode();

            /**
             * Resolves the actual size of this field access operation.
             *
             * @param fieldSize The size of the accessed field.
             * @return The size of the field access operation based on the field's size.
             */
            protected abstract Size resolveSize(StackSize fieldSize);
        }

        /**
         * A reading field access operation.
         */
        protected class FieldGetInstruction extends AbstractFieldInstruction {

            @Override
            protected int getOpcode() {
                return getterOpcode;
            }

            @Override
            protected Size resolveSize(StackSize fieldSize) {
                int sizeChange = fieldSize.getSize() - targetSizeChange;
                return new Size(sizeChange, sizeChange);
            }

            @Override
            public boolean equals(Object other) {
                return this == other || !(other == null || getClass() != other.getClass())
                        && getAccessDispatcher().equals(((FieldGetInstruction) other).getAccessDispatcher());
            }

            @Override
            public int hashCode() {
                return getAccessDispatcher().hashCode() + 7;
            }

            @Override
            public String toString() {
                return "FieldAccess.AccessDispatcher.FieldGetInstruction{fieldDescription=" + fieldDescription + '}';
            }

            /**
             * Returns the outer instance.
             *
             * @return The outer instance.
             */
            private AccessDispatcher getAccessDispatcher() {
                return AccessDispatcher.this;
            }
        }

        /**
         * A writing field access operation.
         */
        protected class FieldPutInstruction extends AbstractFieldInstruction {

            @Override
            protected int getOpcode() {
                return putterOpcode;
            }

            @Override
            protected Size resolveSize(StackSize fieldSize) {
                return new Size(-1 * (fieldSize.getSize() + targetSizeChange), 0);
            }

            @Override
            public boolean equals(Object other) {
                return this == other || !(other == null || getClass() != other.getClass())
                        && getAccessDispatcher().equals(((FieldPutInstruction) other).getAccessDispatcher());
            }

            @Override
            public int hashCode() {
                return getAccessDispatcher().hashCode() + 14;
            }

            @Override
            public String toString() {
                return "FieldAccess.AccessDispatcher.FieldPutInstruction{fieldDescription=" + fieldDescription + '}';
            }

            /**
             * Returns the outer instance.
             *
             * @return The outer instance.
             */
            private AccessDispatcher getAccessDispatcher() {
                return AccessDispatcher.this;
            }
        }
    }
}
