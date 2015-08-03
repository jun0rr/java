package net.bytebuddy.matcher;

import net.bytebuddy.description.type.DeclaredInType;
import net.bytebuddy.description.type.TypeDescription;

/**
 * An element matcher that matches the declaring type of another element, only if this element is actually declared
 * in a type.
 *
 * @param <T> The exact type of the element being matched.
 */
public class DeclaringTypeMatcher<T extends DeclaredInType> extends ElementMatcher.Junction.AbstractBase<T> {

    /**
     * The type matcher to be applied if the target element is declared in a type.
     */
    private final ElementMatcher<? super TypeDescription> typeMatcher;

    /**
     * Creates a new matcher for the declaring type of an element.
     *
     * @param typeMatcher The type matcher to be applied if the target element is declared in a type.
     */
    public DeclaringTypeMatcher(ElementMatcher<? super TypeDescription> typeMatcher) {
        this.typeMatcher = typeMatcher;
    }

    @Override
    public boolean matches(T target) {
        TypeDescription typeDescription = target.getDeclaringType();
        return typeDescription != null && typeMatcher.matches(typeDescription);
    }

    @Override
    public boolean equals(Object other) {
        return this == other || !(other == null || getClass() != other.getClass())
                && typeMatcher.equals(((DeclaringTypeMatcher<?>) other).typeMatcher);
    }

    @Override
    public int hashCode() {
        return typeMatcher.hashCode();
    }

    @Override
    public String toString() {
        return "declaredBy(" + typeMatcher + ")";
    }
}
