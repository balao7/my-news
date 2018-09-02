package com.eric.mynews;

import android.support.annotation.StringRes;

public interface ResourceProvider {
    String getString(@StringRes int resId);
}
