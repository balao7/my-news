package com.eric.mynews.commands;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class OpenWebBrowserCmdImpl implements OpenWebBrowserCmd {
    private final Activity activity;

    public OpenWebBrowserCmdImpl(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void openUrl(@Nullable String url) {
        if (url == null || url.isEmpty()) { return; }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        activity.startActivity(intent);
    }
}
