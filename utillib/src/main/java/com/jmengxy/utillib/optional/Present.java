package com.jmengxy.utillib.optional;

import com.jmengxy.utillib.functors.Func1;

/**
 * Created by jiemeng on 10/02/2018.
 */

final class Present<T> extends Optional<T> {
    private final T reference;

    Present(T reference) {
        this.reference = reference;
    }

    @Override
    public boolean isPresent() {
        return true;
    }

    @Override
    public T get() {
        return reference;
    }

    @Override
    public T or(T defaultValue) {
        checkNotNull(defaultValue);
        return reference;
    }

    @Override
    public Optional<T> or(Optional<? extends T> secondChoice) {
        checkNotNull(secondChoice);
        return this;
    }

    @Override
    public T orNull() {
        return reference;
    }

    @Override
    public <V> Optional<V> transform(Func1<? super T, V> function) {
        return new Present<V>(
                checkNotNull(
                        function.apply(reference)));
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Present) {
            Present<?> other = (Present<?>) object;
            return reference.equals(other.reference);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 0x598df91c + reference.hashCode();
    }

    @Override
    public String toString() {
        return "Optional.of(" + reference + ")";
    }

    private static final long serialVersionUID = 0;
}
