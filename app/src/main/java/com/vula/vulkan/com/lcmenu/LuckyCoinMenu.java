package com.vula.vulkan.com.lcmenu;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.MainThread;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.vula.vulkan.com.R;
import com.vula.vulkan.com.databinding.LuckyCoinMenuFragmentBinding;
import com.vula.vulkan.com.databinding.LuckyCoinSplashFragmentBinding;
import com.vula.vulkan.com.lcapp.LuckyCoinConst;

import static android.content.Context.MODE_PRIVATE;

public class LuckyCoinMenu extends Fragment {

    private LuckyCoinMenuViewModel mViewModel;
    private LuckyCoinMenuFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel = new ViewModelProvider(this).get(LuckyCoinMenuViewModel.class);
        binding = LuckyCoinMenuFragmentBinding.inflate(getLayoutInflater());
        mViewModel.setActivity(getActivity());
        mViewModel.setBase();

        mViewModel.getPlay().observe(getViewLifecycleOwner(), new Observer<View.OnClickListener>() {
            @Override
            public void onChanged(@Nullable View.OnClickListener s) {
                binding.play.setOnClickListener(s);
            }
        });

        mViewModel.getProfile().observe(getViewLifecycleOwner(), new Observer<View.OnClickListener>() {
            @Override
            public void onChanged(@Nullable View.OnClickListener s) {
                binding.profile.setOnClickListener(s);
            }
        });

        mViewModel.getSound().observe(getViewLifecycleOwner(), new Observer<View.OnClickListener>() {
            @Override
            public void onChanged(@Nullable View.OnClickListener s) {
                binding.sound.setOnClickListener(s);
            }
        });

        mViewModel.getInfo().observe(getViewLifecycleOwner(), new Observer<View.OnClickListener>() {
            @Override
            public void onChanged(@Nullable View.OnClickListener s) {
                binding.info.setOnClickListener(s);
            }
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true ) {
            @Override
            @MainThread
            public void handleOnBackPressed() {

                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory( Intent.CATEGORY_HOME );
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);

            }
        });


        return binding.getRoot();
    }



}