package net.bytebuddy.implementation.bytecode.assign.primitive;

import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.StackSize;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * This delegate is responsible for widening a primitive type to represent a <i>larger</i> primitive type. The
 * rules for this widening are equivalent to those in the <a href="http://docs.oracle.com/javase/specs/">JLS</a>.
 */
public enum PrimitiveWideningDelegate {

    /**
     * The widening delegate for {@code boolean} values.
     */
    BOOLEAN(StackManipulation.LegalTrivial.INSTANCE,                                                // to boolean
            StackManipulation.Illegal.INSTANCE,                                                     // to byte
            StackManipulation.Illegal.INSTANCE,                                                     // to short
            StackManipulation.Illegal.INSTANCE,                                                     // to character
            StackManipulation.Illegal.INSTANCE,                                                     // to integer
            StackManipulation.Illegal.INSTANCE,                                                     // to long
            StackManipulation.Illegal.INSTANCE,                                                     // to float
            StackManipulation.Illegal.INSTANCE),                                                    // to double

    /**
     * The widening delegate for {@code byte} values.
     */
    BYTE(StackManipulation.Illegal.INSTANCE,                                                        // to boolean
            StackManipulation.LegalTrivial.INSTANCE,                                                // to byte
            StackManipulation.LegalTrivial.INSTANCE,                                                // to short
            StackManipulation.Illegal.INSTANCE,                                                     // to character
            StackManipulation.LegalTrivial.INSTANCE,                                                // to integer
            new WideningStackManipulation(Opcodes.I2L, StackSize.SINGLE.toIncreasingSize()),        // to long
            new WideningStackManipulation(Opcodes.I2F, StackSize.ZERO.toIncreasingSize()),          // to float
            new WideningStackManipulation(Opcodes.I2L, StackSize.SINGLE.toIncreasingSize())),       // to double

    /**
     * The widening delegate for {@code short} values.
     */
    SHORT(StackManipulation.Illegal.INSTANCE,                                                       // to boolean
            StackManipulation.Illegal.INSTANCE,                                                     // to byte
            StackManipulation.LegalTrivial.INSTANCE,                                                // to short
            StackManipulation.Illegal.INSTANCE,                                                     // to character
            StackManipulation.LegalTrivial.INSTANCE,                                                // to integer
            new WideningStackManipulation(Opcodes.I2L, StackSize.SINGLE.toIncreasingSize()),        // to long
            new WideningStackManipulation(Opcodes.I2F, StackSize.ZERO.toIncreasingSize()),          // to float
            new WideningStackManipulation(Opcodes.I2D, StackSize.SINGLE.toIncreasingSize())),       // to double

    /**
     * The widening delegate for {@code char} values.
     */
    CHARACTER(StackManipulation.Illegal.INSTANCE,                                                   // to boolean
            StackManipulation.Illegal.INSTANCE,                                                     // to byte
            StackManipulation.Illegal.INSTANCE,                                                     // to short
            StackManipulation.LegalTrivial.INSTANCE,                                                // to character
            StackManipulation.LegalTrivial.INSTANCE,                                                // to integer
            new WideningStackManipulation(Opcodes.I2L, StackSize.SINGLE.toIncreasingSize()),        // to long
            new WideningStackManipulation(Opcodes.I2F, StackSize.ZERO.toIncreasingSize()),          // to float
            new WideningStackManipulation(Opcodes.I2D, StackSize.SINGLE.toIncreasingSize())),       // to double

    /**
     * The widening delegate for {@code int} values.
     */
    INTEGER(StackManipulation.Illegal.INSTANCE,                                                     // to boolean
            StackManipulation.Illegal.INSTANCE,                                                     // to byte
            StackManipulation.Illegal.INSTANCE,                                                     // to short
            StackManipulation.Illegal.INSTANCE,                                                     // to character
            StackManipulation.LegalTrivial.INSTANCE,                                                // to integer
            new WideningStackManipulation(Opcodes.I2L, StackSize.SINGLE.toIncreasingSize()),        // to long
            new WideningStackManipulation(Opcodes.I2F, StackSize.ZERO.toIncreasingSize()),          // to float
            new WideningStackManipulation(Opcodes.I2D, StackSize.SINGLE.toIncreasingSize())),       // to double

    /**
     * The widening delegate for {@code long} values.
     */
    LONG(StackManipulation.Illegal.INSTANCE,                                                        // to boolean
            StackManipulation.Illegal.INSTANCE,                                                     // to byte
            StackManipulation.Illegal.INSTANCE,                                                     // to short
            StackManipulation.Illegal.INSTANCE,                                                     // to character
            StackManipulation.Illegal.INSTANCE,                                                     // to integer
            StackManipulation.LegalTrivial.INSTANCE,                                                // to long
            new WideningStackManipulation(Opcodes.L2F, StackSize.SINGLE.toDecreasingSize()),        // to float
            new WideningStackManipulation(Opcodes.L2D, StackSize.ZERO.toIncreasingSize())),         // to double

    /**
     * The widening delegate for {@code float} values.
     */
    FLOAT(StackManipulation.Illegal.INSTANCE,                                                       // to boolean
            StackManipulation.Illegal.INSTANCE,                                                     // to byte
            StackManipulation.Illegal.INSTANCE,                                                     // to short
            StackManipulation.Illegal.INSTANCE,                                                     // to character
            StackManipulation.Illegal.INSTANCE,                                                     // to integer
            StackManipulation.Illegal.INSTANCE,                                                     // to long
            StackManipulation.LegalTrivial.INSTANCE,                                                // to float
            new WideningStackManipulation(Opcodes.F2D, StackSize.SINGLE.toIncreasingSize())),       // to double

    /**
     * The widening delegate for {@code double} values.
     */
    DOUBLE(StackManipulation.Illegal.INSTANCE,                                                      // to boolean
            StackManipulation.Illegal.INSTANCE,                                                     // to byte
            StackManipulation.Illegal.INSTANCE,                                                     // to short
            StackManipulation.Illegal.INSTANCE,                                                     // to character
            StackManipulation.Illegal.INSTANCE,                                                     // to integer
            StackManipulation.Illegal.INSTANCE,                                                     // to long
            StackManipulation.Illegal.INSTANCE,                                                     // to float
            StackManipulation.LegalTrivial.INSTANCE);                                               // to double

    /**
     * A stack manipulation that widens the type that is represented by this instance to a {@code boolean}.
     */
    private final StackManipulation toBooleanStackManipulation;

    /**
     * A stack manipulation that widens the type that is represented by this instance to a {@code byte}.
     */
    private final StackManipulation toByteStackManipulation;

    /**
     * A stack manipulation that widens the type that is represented by this instance to a {@code short}.
     */
    private final StackManipulation toShortStackManipulation;

    /**
     * A stack manipulation that widens the type that is represented by this instance to a {@code char}.
     */
    private final StackManipulation toCharacterStackManipulation;

    /**
     * A stack manipulation that widens the type that is represented by this instance to a {@code int}.
     */
    private final StackManipulation toIntegerStackManipulation;

    /**
     * A stack manipulation that widens the type that is represented by this instance to a {@code long}.
     */
    private final StackManipulation toLongStackManipulation;

    /**
     * A stack manipulation that widens the type that is represented by this instance to a {@code float}.
     */
    private final StackManipulation toFloatStackManipulation;

    /**
     * A stack manipulation that widens the type that is represented by this instance to a {@code double}.
     */
    private final StackManipulation toDoubleStackManipulation;

    /**
     * Creates a new primitive widening delegate.
     *
     * @param toBooleanStackManipulation   A stack manipulation that widens the type that is represented by this
     *                                     instance to a {@code boolean}.
     * @param toByteStackManipulation      A stack manipulation that widens the type that is represented by this
     *                                     instance to a {@code byte}.
     * @param toShortStackManipulation     A stack manipulation that widens the type that is represented by this
     *                                     instance to a {@code short}.
     * @param toCharacterStackManipulation A stack manipulation that widens the type that is represented by this
     *                                     instance to a {@code char}.
     * @param toIntegerStackManipulation   A stack manipulation that widens the type that is represented by this
     *                                     instance to a {@code int}.
     * @param toLongStackManipulation      A stack manipulation that widens the type that is represented by this
     *                                     instance to a {@code long}.
     * @param toFloatStackManipulation     A stack manipulation that widens the type that is represented by this
     *                                     instance to a {@code float}.
     * @param toDoubleStackManipulation    A stack manipulation that widens the type that is represented by this
     *                                     instance to a {@code double}.
     */
    PrimitiveWideningDelegate(StackManipulation toBooleanStackManipulation,
                              StackManipulation toByteStackManipulation,
                              StackManipulation toShortStackManipulation,
                              StackManipulation toCharacterStackManipulation,
                              StackManipulation toIntegerStackManipulation,
                              StackManipulation toLongStackManipulation,
                              StackManipulation toFloatStackManipulation,
                              StackManipulation toDoubleStackManipulation) {
        this.toBooleanStackManipulation = toBooleanStackManipulation;
        this.toByteStackManipulation = toByteStackManipulation;
        this.toShortStackManipulation = toShortStackManipulation;
        this.toCharacterStackManipulation = toCharacterStackManipulation;
        this.toIntegerStackManipulation = toIntegerStackManipulation;
        this.toLongStackManipulation = toLongStackManipulation;
        this.toFloatStackManipulation = toFloatStackManipulation;
        this.toDoubleStackManipulation = toDoubleStackManipulation;
    }

    /**
     * Locates the delegate that is capable of widening the given type into another type.
     *
     * @param typeDescription A non-void primitive type that is to be widened into another type.
     * @return A delegate for the given type.
     */
    public static PrimitiveWideningDelegate forPrimitive(TypeDescription typeDescription) {
        if (typeDescription.represents(boolean.class)) {
            return BOOLEAN;
        } else if (typeDescription.represents(byte.class)) {
            return BYTE;
        } else if (typeDescription.represents(short.class)) {
            return SHORT;
        } else if (typeDescription.represents(char.class)) {
            return CHARACTER;
        } else if (typeDescription.represents(int.class)) {
            return INTEGER;
        } else if (typeDescription.represents(long.class)) {
            return LONG;
        } else if (typeDescription.represents(float.class)) {
            return FLOAT;
        } else if (typeDescription.represents(double.class)) {
            return DOUBLE;
        } else {
            throw new IllegalArgumentException("Not a primitive, non-void type: " + typeDescription);
        }
    }

    /**
     * Attempts to widen the represented type into another type.
     *
     * @param typeDescription A non-void primitive type that is the expected result of the widening operation.
     * @return A widening instruction or an illegal stack manipulation if such widening is not legitimate.
     */
    public StackManipulation widenTo(TypeDescription typeDescription) {
        if (typeDescription.represents(boolean.class)) {
            return toBooleanStackManipulation;
        } else if (typeDescription.represents(byte.class)) {
            return toByteStackManipulation;
        } else if (typeDescription.represents(short.class)) {
            return toShortStackManipulation;
        } else if (typeDescription.represents(char.class)) {
            return toCharacterStackManipulation;
        } else if (typeDescription.represents(int.class)) {
            return toIntegerStackManipulation;
        } else if (typeDescription.represents(long.class)) {
            return toLongStackManipulation;
        } else if (typeDescription.represents(float.class)) {
            return toFloatStackManipulation;
        } else if (typeDescription.represents(double.class)) {
            return toDoubleStackManipulation;
        } else {
            throw new IllegalArgumentException("Not a primitive non-void type: " + typeDescription);
        }
    }

    @Override
    public String toString() {
        return "PrimitiveWideningDelegate." + name();
    }

    /**
     * A stack manipulation that widens a primitive type into a more general primitive type.
     */
    protected static class WideningStackManipulation implements StackManipulation {

        /**
         * The opcode for executing the conversion.
         */
        private final int conversionOpcode;

        /**
         * The size change of applying the conversion.
         */
        private final Size size;

        /**
         * Creates a new widening stack manipulation.
         *
         * @param conversionOpcode The opcode for executing the conversion.
         * @param size             The size change of applying the conversion.
         */
        protected WideningStackManipulation(int conversionOpcode, Size size) {
            this.conversionOpcode = conversionOpcode;
            this.size = size;
        }

        @Override
        public boolean isValid() {
            return true;
        }

        @Override
        public Size apply(MethodVisitor methodVisitor, Implementation.Context implementationContext) {
            methodVisitor.visitInsn(conversionOpcode);
            return size;
        }

        @Override
        public boolean equals(Object other) {
            return this == other || !(other == null || getClass() != other.getClass())
                    && conversionOpcode == ((WideningStackManipulation) other).conversionOpcode
                    && size.equals(((WideningStackManipulation) other).size);
        }

        @Override
        public int hashCode() {
            return 31 * conversionOpcode + size.hashCode();
        }

        @Override
        public String toString() {
            return "PrimitiveWideningDelegate.WideningStackManipulation{" +
                    "conversionOpcode=" + conversionOpcode +
                    ", size=" + size +
                    '}';
        }
    }
}
