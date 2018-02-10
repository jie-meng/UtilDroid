package com.jmengxy.utillib.architecture.retrofit;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.jmengxy.utillib.functors.Func1;

import org.reactivestreams.Publisher;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.internal.operators.completable.CompletableError;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;

/**
 * Created by jiemeng on 10/02/2018.
 */

public class RxErrorHandlingCallAdapterFactory extends CallAdapter.Factory {
    private final RxJava2CallAdapterFactory original;
    private final Gson gson;
    private final Func1<Throwable, Throwable> errorFilter;

    private RxErrorHandlingCallAdapterFactory(RxJava2CallAdapterFactory original, Gson gson, Func1<Throwable, Throwable> errorFilter) {
        this.original = original;
        this.gson = gson;
        this.errorFilter = errorFilter;
    }

    public static CallAdapter.Factory create(RxJava2CallAdapterFactory original, Gson gson, Func1<Throwable, Throwable> errorFilter) {
        return new RxErrorHandlingCallAdapterFactory(original, gson, errorFilter);
    }

    @Override
    public CallAdapter<?, ?> get(@android.support.annotation.NonNull Type returnType,
                                 @android.support.annotation.NonNull Annotation[] annotations,
                                 @android.support.annotation.NonNull Retrofit retrofit) {
        CallAdapter<?, ?> callAdapter = original.get(returnType, annotations, retrofit);
        return new RxCallAdapterWrapper(gson, returnType, callAdapter, errorFilter);
    }

    private static class RxCallAdapterWrapper<R> implements CallAdapter<R, Object> {
        private final Gson gson;
        private final CallAdapter<R, Object> wrapped;
        private final Func1<Throwable, Throwable> errorFilter;

        private final boolean isObservable;
        private final boolean isFlowable;
        private final boolean isSingle;
        private final boolean isMaybe;
        private final boolean isCompletable;

        @SuppressWarnings("unchecked")
        RxCallAdapterWrapper(Gson gson, Type returnType, CallAdapter wrapped, Func1<Throwable, Throwable> errorFilter) {
            this.gson = gson;
            this.errorFilter = errorFilter;
            Class<?> rawType = getRawType(returnType);
            this.isObservable = rawType == Observable.class;
            this.isFlowable = rawType == Flowable.class;
            this.isSingle = rawType == Single.class;
            this.isMaybe = rawType == Maybe.class;
            this.isCompletable = rawType == Completable.class;
            this.wrapped = (CallAdapter<R, Object>) wrapped;
        }

        @Override
        public Type responseType() {
            return wrapped.responseType();
        }

        @Override
        @SuppressWarnings("unchecked")
        public Object adapt(@android.support.annotation.NonNull Call<R> call) {
            Object object = wrapped.adapt(call);

            if (isObservable) {
                return ((Observable) object).onErrorResumeNext(new Function<Throwable, ObservableSource>() {
                    @Override
                    public ObservableSource apply(@NonNull Throwable throwable) throws Exception {
                        return Observable.error(asRetrofitException(throwable));
                    }
                });
            }

            if (isFlowable) {
                return ((Flowable) object).onErrorResumeNext(new Function<Throwable, Publisher>() {
                    @Override
                    public Publisher apply(@NonNull Throwable throwable) throws Exception {
                        return Flowable.error(asRetrofitException(throwable));
                    }
                });
            }

            if (isSingle) {
                return ((Single) object).onErrorResumeNext(new Function<Throwable, SingleSource>() {
                    @Override
                    public SingleSource apply(@NonNull Throwable throwable) throws Exception {
                        return Single.error(asRetrofitException(throwable));
                    }
                });
            }

            if (isMaybe) {
                return ((Maybe) object).onErrorResumeNext(new Function<Throwable, MaybeSource>() {
                    @Override
                    public MaybeSource apply(@NonNull Throwable throwable) throws Exception {
                        return Maybe.error(asRetrofitException(throwable));
                    }
                });
            }

            if (isCompletable) {
                return ((Completable) object).onErrorResumeNext(new Function<Throwable, CompletableSource>() {
                    @Override
                    public Completable apply(@NonNull Throwable throwable) throws Exception {
                        return new CompletableError(asRetrofitException(throwable));
                    }
                });
            }

            return null;
        }

        private Throwable asRetrofitException(Throwable throwable) {
            if (errorFilter != null) {
                Throwable retThrowable = errorFilter.apply(throwable);
                if (retThrowable != null) {
                    return retThrowable;
                }
            }

            return throwable;
        }


        private static Class<?> getRawType(Type type) {
            if (type == null) {
                throw new IllegalArgumentException("type == null");
            }

            if (type instanceof Class<?>) {
                // Type is a normal class.
                return (Class<?>) type;
            }
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;

                // I'm not exactly sure why getRawType() returns Type instead of Class. Neal isn't either but
                // suspects some pathological case related to nested classes exists.
                Type rawType = parameterizedType.getRawType();
                if (!(rawType instanceof Class)) throw new IllegalArgumentException();
                return (Class<?>) rawType;
            }
            if (type instanceof GenericArrayType) {
                Type componentType = ((GenericArrayType) type).getGenericComponentType();
                return Array.newInstance(getRawType(componentType), 0).getClass();
            }
            if (type instanceof TypeVariable) {
                // We could use the variable's bounds, but that won't work if there are multiple. Having a raw
                // type that's more general than necessary is okay.
                return Object.class;
            }
            if (type instanceof WildcardType) {
                return getRawType(((WildcardType) type).getUpperBounds()[0]);
            }

            throw new IllegalArgumentException("Expected a Class, ParameterizedType, or "
                    + "GenericArrayType, but <" + type + "> is of type " + type.getClass().getName());
        }
    }
}

