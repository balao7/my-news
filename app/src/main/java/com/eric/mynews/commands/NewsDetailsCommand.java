package com.eric.mynews.commands;

import com.eric.mynews.models.Article;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Navigation to a article details screen
 */
@ParametersAreNonnullByDefault
public interface NewsDetailsCommand {
    void gotoDetails(Article news);
}
