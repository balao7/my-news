package com.eric.mynews.commands;

import com.eric.mynews.models.Article;

public interface NewsDetailsCommand {
    void gotoDetails(Article news);
}
