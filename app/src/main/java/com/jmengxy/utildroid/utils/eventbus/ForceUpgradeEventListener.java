package com.jmengxy.utildroid.utils.eventbus;

import android.content.Context;

import com.jmengxy.utillib.architecture.eventbus.EventBus;

/**
 * Created by jiemeng on 29/12/2017.
 */

public class ForceUpgradeEventListener implements EventBus.Listener {
    private Context context;

    public ForceUpgradeEventListener(Context context) {
        this.context = context;
    }

    @Override
    public void onEvent(Object event) {
        if (event instanceof RemoteEvent.ForceUpdateEvent) {
//            Intent intent = new Intent(context.getApplicationContext(), ForceUpgradeActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            intent.putExtra(ForceUpgradeActivity.ARG_MESSAGE, ((RemoteEvent.ForceUpdateEvent) event).getMessage());
//            context.startActivity(intent);
        }
    }
}
