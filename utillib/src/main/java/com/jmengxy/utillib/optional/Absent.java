package com.jmengxy.utillib.optional;

import com.jmengxy.utillib.functors.Func1;

/**
 * Created by jiemeng on 10/02/2018.
 */

final class Absent<T> extends Optional<T> {
    static final Absent<Object> INSTANCE = new Absent<>();

    @SuppressWarnings("unchecked") // implementation is "fully variant"
    static <T> Optional<T> withType() {
        return (Optional<T>) INSTANCE;
    }

    private Absent() {}

    @Override
    public boolean isPresent() {
        return false;
    }

    @Override
    public T get() {
        throw new IllegalStateException("Optional.get() cannot be called on an absent value");
    }

    @Override
    public T or(T defaultValue) {
        return checkNotNull(defaultValue);
    }

    @SuppressWarnings("unchecked") // safe covariant cast
    @Override
    public Optional<T> or(Optional<? extends T> secondChoice) {
        return (Optional<T>) checkNotNull(secondChoice);
    }

    @Override
    public T orNull() {
        return null;
    }

    @Override
    public <V> Optional<V> transform(Func1<? super T, V> function) {
        checkNotNull(function);
        return Optional.empty();
    }

    @Override
    public boolean equals( Object object) {
        return object == this;
    }

    @Override
    public int hashCode() {
        return 0x79a31aac;
    }

    @Override
    public String toString() {
        return "Optional.absent()";
    }

    private Object readResolve() {
        return INSTANCE;
    }

    private static final long serialVersionUID = 0;
}

