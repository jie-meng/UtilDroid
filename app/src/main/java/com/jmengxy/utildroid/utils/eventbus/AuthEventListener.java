package com.jmengxy.utildroid.utils.eventbus;

import android.content.Context;

import com.jmengxy.utillib.architecture.eventbus.EventBus;

public class AuthEventListener implements EventBus.Listener {
    private Context context;

    public AuthEventListener(Context context) {
        this.context = context;
    }

    @Override
    public void onEvent(Object event) {
        if (event instanceof RemoteEvent.AuthTokenInvalidEvent) {
//            Intent intent = new Intent(context.getApplicationContext(), AuthExpiredActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            intent.putExtra(AuthExpiredActivity.ARG_MESSAGE, ((RemoteEvent.AuthTokenInvalidEvent) event).getMessage());
//            context.startActivity(intent);
        }
    }
}
