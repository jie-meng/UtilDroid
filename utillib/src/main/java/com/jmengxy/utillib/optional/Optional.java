package com.jmengxy.utillib.optional;

import com.jmengxy.utillib.functors.Func1;

import java.io.Serializable;

/**
 * Created by jiemeng on 10/02/2018.
 */

public abstract class Optional<T> implements Serializable {

    public static <T> Optional<T> empty() {
        return Absent.withType();
    }

    public static <T> Optional<T> of(T reference) {
        return new Present<T>(checkNotNull(reference));
    }

    public static <T> Optional<T> ofNullable( T nullableReference) {
        return (nullableReference == null) ? Optional.<T>empty() : new Present<T>(nullableReference);
    }

    Optional() {}

    public abstract boolean isPresent();

    public abstract T get();

    public abstract T or(T defaultValue);

    public abstract Optional<T> or(Optional<? extends T> secondChoice);

    public abstract T orNull();

    public abstract <V> Optional<V> transform(Func1<? super T, V> function);

    @Override
    public abstract boolean equals( Object object);

    @Override
    public abstract int hashCode();

    @Override
    public abstract String toString();

    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    private static final long serialVersionUID = 0;
}

