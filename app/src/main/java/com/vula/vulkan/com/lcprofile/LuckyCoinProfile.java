package com.vula.vulkan.com.lcprofile;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vula.vulkan.com.R;
import com.vula.vulkan.com.databinding.LuckyCoinMenuFragmentBinding;
import com.vula.vulkan.com.databinding.LuckyCoinProfileFragmentBinding;
import com.vula.vulkan.com.lcapp.LuckyCoinConst;
import com.vula.vulkan.com.lcmenu.LuckyCoinMenuViewModel;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class LuckyCoinProfile extends Fragment {

    private LuckyCoinProfileViewModel mViewModel;
    private LuckyCoinProfileFragmentBinding binding;
    private SharedPreferences myPrefrence;
    private SharedPreferences.Editor editor;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(LuckyCoinProfileViewModel.class);
        binding = LuckyCoinProfileFragmentBinding.inflate(getLayoutInflater());
        myPrefrence = getActivity().getSharedPreferences("App_settings", MODE_PRIVATE);
        editor = myPrefrence.edit();
        mViewModel.setActivity(getActivity(), binding, this);

        binding.nickname.setText(myPrefrence.getString("nickname", "Player"));
        binding.age.setText(myPrefrence.getString("age", "13"));
        binding.country.setText(myPrefrence.getString("country", "Poland"));

        mViewModel.getTypefaceMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Typeface>() {
            @Override
            public void onChanged(@Nullable Typeface s) {
                binding.age.setTypeface(s);
                binding.nickname.setTypeface(s);
                binding.country.setTypeface(s);
            }
        });
        mViewModel.getPhotoRun().observe(getViewLifecycleOwner(), new Observer<Runnable>() {
            @Override
            public void onChanged(@Nullable Runnable s) {
                s.run();
            }
        });

        mViewModel.getPrivacy().observe(getViewLifecycleOwner(), new Observer<View.OnClickListener>() {
            @Override
            public void onChanged(@Nullable View.OnClickListener s) {
                binding.privacy.setOnClickListener(s);
            }
        });

        mViewModel.getPhoto().observe(getViewLifecycleOwner(), new Observer<View.OnClickListener>() {
            @Override
            public void onChanged(@Nullable View.OnClickListener s) {
                binding.photo.setOnClickListener(s);
            }
        });

        mViewModel.getBack().observe(getViewLifecycleOwner(), new Observer<View.OnClickListener>() {
            @Override
            public void onChanged(@Nullable View.OnClickListener s) {
                binding.back.setOnClickListener(s);
            }
        });
        return binding.getRoot();
    }

    String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        return imageEncoded;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LuckyCoinConst.getProfileCode() && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            binding.photo.setImageBitmap(BitmapFactory.decodeFile(picturePath));


            editor.putString("Luckycoin", encodeTobase64(BitmapFactory.decodeFile(picturePath)));
            editor.commit();
        }
    }
}