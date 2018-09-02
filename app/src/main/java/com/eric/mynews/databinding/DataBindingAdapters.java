package com.eric.mynews.databinding;

import android.databinding.BindingAdapter;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import timber.log.Timber;

public class DataBindingAdapters {
    @BindingAdapter({"bgColourRes"})
    public static void setBackgroundColorRes(View view, @ColorRes int color) {
        if (color != 0) { view.setBackgroundColor(ContextCompat.getColor(view.getContext(), color)); }
    }

    @BindingAdapter({"visibility"})
    public static void setVisibility(View view, boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter({"isLoading"})
    public static void setIsLoading(ImageView view, boolean isLoading) {
        Timber.i("setIsLoading - %b", isLoading);
        if (isLoading) {
            RotateAnimation anim = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setInterpolator(new LinearInterpolator());
            anim.setDuration(1400);
            anim.setRepeatCount(TranslateAnimation.INFINITE);
            anim.setRepeatMode(TranslateAnimation.RESTART);
            view.startAnimation(anim);
        } else {
            view.clearAnimation();
        }
    }

    @BindingAdapter({"imgSrc"})
    public static void setImgSrc(ImageView view, String imgSrc) {
        Glide.with(view)
                .load(imgSrc)
                .apply(RequestOptions.centerCropTransform())
                .into(view);
    }
}
