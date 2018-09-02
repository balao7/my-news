package com.eric.mynews.commands;

import android.app.Activity;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class BackCommandImpl implements BackCommand {
    private final Activity activity;

    public BackCommandImpl(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void goBack() {
        activity.finish();
    }
}
