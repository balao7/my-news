package com.eric.mynews;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class MyConnectivityManagerImpl implements MyConnectivityManager {
    private final ConnectivityManager connectivityManager;

    public MyConnectivityManagerImpl(Context context) {
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Override
    public boolean isOnline() {
        final NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
