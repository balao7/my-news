package com.eric.mynews.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.eric.mynews.R;
import com.eric.mynews.databinding.ActivityMainBinding;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import timber.log.Timber;

public class MainActivity extends DaggerAppCompatActivity {

    @Inject MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Timber.i("onCreate");
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setVm(mainViewModel);

        mainViewModel.onActivityCreated();
    }

    @Override
    protected void onDestroy() {
        mainViewModel.onActivityDestroyed();
        super.onDestroy();
    }
}
