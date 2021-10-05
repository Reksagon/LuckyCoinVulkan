package com.vula.vulkan.com.lcsound;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vula.vulkan.com.R;
import com.vula.vulkan.com.databinding.LuckyCoinSoundFragmentBinding;
import com.vula.vulkan.com.databinding.LuckyCoinSplashFragmentBinding;
import com.vula.vulkan.com.lcsplash.LuckyCoinSplashViewModel;

import static android.content.Context.MODE_PRIVATE;

public class LuckyCoinSound extends Fragment {

    private LuckyCoinSoundViewModel mViewModel;
    private LuckyCoinSoundFragmentBinding binding;
    SharedPreferences myPrefrence;
    SharedPreferences.Editor editor;
    String sound = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(LuckyCoinSoundViewModel.class);
        binding = LuckyCoinSoundFragmentBinding.inflate(getLayoutInflater());

        myPrefrence = getActivity().getSharedPreferences("com.vula.vulkan.com.v2.playerprefs", MODE_PRIVATE);
        editor = myPrefrence.edit();
        sound = myPrefrence.getString("Sound", "On");
        mViewModel.setActivity(getActivity(), sound, binding);

        if(sound.equals("On"))
            binding.bttnClick.setImageDrawable(getActivity().getDrawable(R.drawable.on));
        else
            binding.bttnClick.setImageDrawable(getActivity().getDrawable(R.drawable.off));

        mViewModel.getBack().observe(getViewLifecycleOwner(), new Observer<View.OnClickListener>() {
            @Override
            public void onChanged(@Nullable View.OnClickListener s) {
                binding.back.setOnClickListener(s);
            }
        });

        mViewModel.getClick().observe(getViewLifecycleOwner(), new Observer<View.OnClickListener>() {
            @Override
            public void onChanged(@Nullable View.OnClickListener s) {
                binding.bttnClick.setOnClickListener(s);
            }
        });



        return binding.getRoot();
    }



}