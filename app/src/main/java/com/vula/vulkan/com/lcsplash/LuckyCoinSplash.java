package com.vula.vulkan.com.lcsplash;

import androidx.lifecycle.ViewModelProvider;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vula.vulkan.com.databinding.LuckyCoinSplashFragmentBinding;
import com.vula.vulkan.com.lcapp.LuckyCoinConst;

import androidx.lifecycle.Observer;

public class LuckyCoinSplash extends Fragment {


    private LuckyCoinSplashViewModel mViewModel;
    private LuckyCoinSplashFragmentBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(LuckyCoinSplashViewModel.class);
        binding = LuckyCoinSplashFragmentBinding.inflate(getLayoutInflater());
        mViewModel.setActivity(getActivity(), binding);
        LuckyCoinConst.setFirebase(getActivity());
        mViewModel.getAsyncTaskMutableLiveData().observe(getViewLifecycleOwner(), new Observer<AsyncTask<Void, Integer, Void>>() {
            @Override
            public void onChanged(@Nullable AsyncTask<Void, Integer, Void> s) {
                s.execute();
            }
        });

        return binding.getRoot();

    }



}