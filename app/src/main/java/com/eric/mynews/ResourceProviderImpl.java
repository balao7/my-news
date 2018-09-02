package com.eric.mynews;

import android.content.Context;

public class ResourceProviderImpl implements ResourceProvider {
    private final Context context;

    public ResourceProviderImpl(Context context) {
        this.context = context;
    }
    
    @Override
    public String getString(int resId) {
        return context.getString(resId);
    }
}
