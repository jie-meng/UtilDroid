package com.jmengxy.utildroid.utils.eventbus;

import android.content.Context;

import java.util.Set;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

/**
 * Created by jiemeng on 20/02/2018.
 */

@Module
public class EventBusModule {

    @Provides
    @Singleton
    EventBus provideEventBus(Set<EventBus.Listener> listeners) {
        final EventBus eventBus = new EventBus();
        for (EventBus.Listener listener : listeners) {
            eventBus.register(listener);
        }
        return eventBus;
    }

    @Provides
    @IntoSet
    EventBus.Listener provideAuthEventListener(Context context) {
        return new AuthEventListener(context);
    }

    @Provides
    @IntoSet
    EventBus.Listener provideForceEventEventListener(Context context) {
        return new ForceUpgradeEventListener(context);
    }
}
