package com.jmengxy.utildroid.utils.eventbus;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by jiemeng on 20/02/2018.
 */

public class EventBus {
    private PublishSubject<Object> subject = PublishSubject.create();
    private Map<Listener, Disposable> map = new HashMap<>();

    public void register(final Listener listener) {
        subject.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    public void onSubscribe(Disposable disposable) {
                        map.put(listener, disposable);
                    }

                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    public void onNext(Object t) {
                        try {
                            listener.onEvent(t);
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }

                    public void onComplete() {
                    }
                });
    }

    public void unregister(Listener listener) {
        Disposable disposable = map.remove(listener);
        if (disposable != null) {
            disposable.dispose();
        }
    }

    public void post(Object event) {
        subject.onNext(event);
    }

    public interface Listener {
        void onEvent(Object event);
    }
}