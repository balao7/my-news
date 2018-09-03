package com.eric.mynews.main;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.eric.mynews.BR;
import com.eric.mynews.R;
import com.eric.mynews.commands.NewsDetailsCommand;
import com.eric.mynews.models.Article;

import java.util.ArrayList;
import java.util.List;

public class MainRVAdapter extends RecyclerView.Adapter<MainRVAdapter.NewsListItemViewHolder> {
    private final NewsDetailsCommand router;
    private List<Article> newsList = new ArrayList<>();

    MainRVAdapter(NewsDetailsCommand router) {
        this.router = router;
    }

    @NonNull
    @Override
    public NewsListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item_news, parent, false);
        return new NewsListItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsListItemViewHolder holder, int position) {
        final Article news = newsList.get(position);
        holder.bind(news);
        holder.itemView.setOnClickListener(v -> router.gotoDetails(news));
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void update(List<Article> list) {
        newsList = list;
        notifyDataSetChanged();
    }

    static class NewsListItemViewHolder extends RecyclerView.ViewHolder {

        private final ViewDataBinding binding;

        NewsListItemViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Article object) {
            binding.setVariable(BR.article, object);
            binding.executePendingBindings();
        }
    }
}
