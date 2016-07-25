package net.bytebuddy.matcher;

import java.util.Collection;

/**
 * An element matcher that matches a collection by its size.
 *
 * @param <T> The type of the matched entity.
 */
public class CollectionSizeMatcher<T extends Iterable<?>> extends ElementMatcher.Junction.AbstractBase<T> {

    /**
     * The expected size of the matched collection.
     */
    private final int size;

    /**
     * Creates a new matcher that matches the size of a matched collection.
     *
     * @param size The expected size of the matched collection.
     */
    public CollectionSizeMatcher(int size) {
        this.size = size;
    }

    @Override
    public boolean matches(T target) {
        if (target instanceof Collection) {
            return ((Collection<?>) target).size() == size;
        } else {
            int size = 0;
            for (Object ignored : target) {
                size++;
            }
            return size == this.size;
        }
    }

    @Override
    public boolean equals(Object other) {
        return this == other || !(other == null || getClass() != other.getClass())
                && size == ((CollectionSizeMatcher) other).size;
    }

    @Override
    public int hashCode() {
        return size;
    }

    @Override
    public String toString() {
        return "ofSize(" + size + ')';
    }
}
