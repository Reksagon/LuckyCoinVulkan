package com.vula.vulkan.com.lcview;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.vula.vulkan.com.R;
import com.vula.vulkan.com.databinding.LuckyCoinProfileFragmentBinding;
import com.vula.vulkan.com.databinding.LuckyCoinViewFragmentBinding;
import com.vula.vulkan.com.lcapp.LuckyCoinConst;
import com.vula.vulkan.com.lcprofile.LuckyCoinProfileViewModel;

public class LuckyCoinView extends Fragment {

    private LuckyCoinViewViewModel mViewModel;

    public static String url = null;
    LuckyCoinViewFragmentBinding binding;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(LuckyCoinViewViewModel.class);
        binding = LuckyCoinViewFragmentBinding.inflate(getLayoutInflater());
        mViewModel.setActivity(getActivity(), binding, this);

        binding.luckycoinWeb.getSettings().setUseWideViewPort(true);
        binding.luckycoinWeb.getSettings().setLoadsImagesAutomatically(true);
        binding.luckycoinWeb.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        binding.luckycoinWeb.getSettings().setBuiltInZoomControls(false);
        binding.luckycoinWeb.getSettings().setLoadWithOverviewMode(true);
        binding.luckycoinWeb.getSettings().setDomStorageEnabled(true);
        binding.luckycoinWeb.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        binding.luckycoinWeb.setBackgroundColor(Color.WHITE);
        binding.luckycoinWeb.getSettings().setJavaScriptEnabled(true);
        binding.luckycoinWeb.getSettings().setSupportZoom(true);

        CookieManager cookieManager = CookieManager.getInstance();
        CookieManager.setAcceptFileSchemeCookies(true);
        cookieManager.setAcceptThirdPartyCookies(binding.luckycoinWeb, true);

        mViewModel.getBroadcastReceiverMutableLiveData().observe(getViewLifecycleOwner(), new Observer<BroadcastReceiver>() {
            @Override
            public void onChanged(@Nullable BroadcastReceiver s) {
                IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
                intentFilter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
                getActivity().registerReceiver(s, intentFilter);
            }
        });

        mViewModel.getWebChromeClientMutableLiveData().observe(getViewLifecycleOwner(), new Observer<WebChromeClient>() {
            @Override
            public void onChanged(@Nullable WebChromeClient s) {
                binding.luckycoinWeb.setWebChromeClient(s);
            }
        });

        mViewModel.getWebViewClientMutableLiveData().observe(getViewLifecycleOwner(), new Observer<WebViewClient>() {
            @Override
            public void onChanged(@Nullable WebViewClient s) {
                binding.luckycoinWeb.setWebViewClient(s);
            }
        });
        binding.luckycoinWeb.loadUrl(url);
        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        if (LuckyCoinConst.getCode() == requestCode)
            if (LuckyCoinConst.getCallBack() == null) return;
        if (resultCode != -1) {
            LuckyCoinConst.getCallBack().onReceiveValue(null);
            return;
        }
        Uri result = (data == null)? LuckyCoinConst.getURL() : data.getData();
        if(result != null && LuckyCoinConst.getCallBack() != null)
            LuckyCoinConst.getCallBack().onReceiveValue(new Uri[]{result});
        else if(LuckyCoinConst.getCallBack() != null)
            LuckyCoinConst.getCallBack().onReceiveValue(new Uri[] {LuckyCoinConst.getURL()});
        LuckyCoinConst.setCallBack(null);
        super.onActivityResult(requestCode, resultCode, data);
    }
}