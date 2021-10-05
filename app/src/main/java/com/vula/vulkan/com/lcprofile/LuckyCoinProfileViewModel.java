package com.vula.vulkan.com.lcprofile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.util.Base64;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.vula.vulkan.com.R;
import com.vula.vulkan.com.databinding.LuckyCoinProfileFragmentBinding;
import com.vula.vulkan.com.lcapp.LuckyCoinConst;
import com.vula.vulkan.com.lcview.LuckyCoinView;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class LuckyCoinProfileViewModel extends ViewModel {
    Activity activity;
    MutableLiveData<Typeface> typefaceMutableLiveData;
    MutableLiveData<Runnable> photoRun;
    SharedPreferences myPrefrence;
    SharedPreferences.Editor editor;
    MutableLiveData<View.OnClickListener> privacy, photo, back;

    public LuckyCoinProfileViewModel()
    {

    }

    public MutableLiveData<Runnable> getPhotoRun() {
        return photoRun;
    }

    public MutableLiveData<View.OnClickListener> getPrivacy() {
        return privacy;
    }

    public MutableLiveData<View.OnClickListener> getPhoto() {
        return photo;
    }

    public MutableLiveData<View.OnClickListener> getBack() {
        return back;
    }

    public void setActivity(Activity activity, LuckyCoinProfileFragmentBinding binding, Fragment fragment)
    {
        this.activity = activity;
        typefaceMutableLiveData = new MutableLiveData<>();
        typefaceMutableLiveData.setValue(Typeface.createFromAsset(activity.getAssets(),
                "meromsans.ttf"));

        myPrefrence = activity.getSharedPreferences("App_settings", MODE_PRIVATE);
        editor = myPrefrence.edit();

        photoRun = new MutableLiveData<>();
        photoRun.setValue(new Runnable() {
            @SuppressLint("InlinedApi")
            @Override
            public void run() {
                String photo = myPrefrence.getString("Luckycoin", "photo");
                assert photo != null;
                if(!photo.equals("photo"))
                {
                    byte[] b = Base64.decode(photo, Base64.DEFAULT);
                    InputStream is = new ByteArrayInputStream(b);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    binding.photo.setImageBitmap(bitmap);
                }
            }
        });

        privacy = new MutableLiveData<>();
        privacy.setValue(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("nickname", binding.nickname.getText().toString());
                editor.putString("age", binding.age.getText().toString());
                editor.putString("country", binding.country.getText().toString());
                editor.commit();
                NavController navController = Navigation.findNavController(activity, R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.action_luckyCoinProfile_to_luckyCoinView);
                LuckyCoinView.url = activity.getResources().getString(R.string.privacy);
            }
        });

        photo = new MutableLiveData<>();
        photo.setValue(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withContext(activity)
                        .withPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                            }
                        }).check();

                Intent loadIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                fragment.startActivityForResult(loadIntent, LuckyCoinConst.getProfileCode());
            }
        });

        back = new MutableLiveData<>();
        back.setValue(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("nickname", binding.nickname.getText().toString());
                editor.putString("age", binding.age.getText().toString());
                editor.putString("country", binding.country.getText().toString());
                editor.commit();
                NavController navController = Navigation.findNavController(activity, R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.action_luckyCoinProfile_to_luckyCoinMenu);
            }
        });
    }

    public MutableLiveData<Typeface> getTypefaceMutableLiveData() {
        return typefaceMutableLiveData;
    }
}