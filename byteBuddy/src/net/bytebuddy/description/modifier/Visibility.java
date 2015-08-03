package net.bytebuddy.description.modifier;

import org.objectweb.asm.Opcodes;

/**
 * Describes a type's, field's or a method's visibility.
 */
public enum Visibility implements ModifierContributor.ForType, ModifierContributor.ForMethod, ModifierContributor.ForField {

    /**
     * A modifier contributor for {@code public} visibility.
     */
    PUBLIC(Opcodes.ACC_PUBLIC),

    /**
     * Modifier for a package-private visibility. (This is the default modifier.)
     */
    PACKAGE_PRIVATE(EMPTY_MASK),

    /**
     * A modifier contributor for {@code protected} visibility.
     */
    PROTECTED(Opcodes.ACC_PROTECTED),

    /**
     * A modifier contributor for {@code private} visibility.
     */
    PRIVATE(Opcodes.ACC_PRIVATE);

    /**
     * The mask the modifier contributor.
     */
    private final int mask;

    /**
     * Creates a new visibility representation.
     *
     * @param mask The modifier mask of this instance.
     */
    Visibility(int mask) {
        this.mask = mask;
    }

    @Override
    public int getMask() {
        return mask;
    }

    /**
     * Returns {@code true} if this instance describes {@code public} visibility.
     *
     * @return {@code true} if this instance describes {@code public} visibility.
     */
    public boolean isPublic() {
        return (mask & Opcodes.ACC_PUBLIC) != 0;
    }

    /**
     * Returns {@code true} if this instance describes {@code protected} visibility.
     *
     * @return {@code true} if this instance describes {@code protected} visibility.
     */
    public boolean isProtected() {
        return (mask & Opcodes.ACC_PROTECTED) != 0;
    }

    /**
     * Returns {@code true} if this instance describes package-private visibility.
     *
     * @return {@code true} if this instance describes package-private visibility.
     */
    public boolean isPackagePrivate() {
        return !(isPublic() || isPrivate() || isProtected());
    }

    /**
     * Returns {@code true} if this instance describes {@code private} visibility.
     *
     * @return {@code true} if this instance describes {@code private} visibility.
     */
    public boolean isPrivate() {
        return (mask & Opcodes.ACC_PRIVATE) != 0;
    }

    @Override
    public String toString() {
        return "Visibility." + name();
    }
}
