package com.eric.mynews.commands;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public interface OpenWebBrowserCmd {
    void openUrl(@Nullable String url);
}
