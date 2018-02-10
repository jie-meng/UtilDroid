package com.jmengxy.utillib.utils;

import android.os.Parcel;

/**
 * Created by jiemeng on 26/12/2017.
 */

public class DataUtils {
    @SuppressWarnings("unchecked")
    public static <P> P cloneParcel(P object) {
        Parcel p = Parcel.obtain();
        p.writeValue(object);
        p.setDataPosition(0);
        P retValue = (P) p.readValue(object.getClass().getClassLoader());
        p.recycle();
        return retValue;
    }
}
