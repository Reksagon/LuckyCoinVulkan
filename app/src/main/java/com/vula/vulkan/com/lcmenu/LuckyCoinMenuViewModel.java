package com.vula.vulkan.com.lcmenu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.MainThread;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.unity3d.player.UnityPlayerActivity;
import com.vula.vulkan.com.R;
import com.vula.vulkan.com.lcapp.LuckyCoinConst;
import com.yandex.metrica.impl.ob.Ba;

import static android.content.Context.MODE_PRIVATE;

public class LuckyCoinMenuViewModel extends ViewModel {

    Activity activity;

    private String fullLink = null;
    private String keyDrop = null;
    private String urlF = null;
    private String keyF = null;

    MutableLiveData<View.OnClickListener> play, profile, sound, info;

    public void setActivity(Activity activity)
    {
        this.activity = activity;
    }

    public MutableLiveData<View.OnClickListener> getPlay() {
        return play;
    }

    public MutableLiveData<View.OnClickListener> getProfile() {
        return profile;
    }

    public MutableLiveData<View.OnClickListener> getSound() {
        return sound;
    }

    public MutableLiveData<View.OnClickListener> getInfo() {
        return info;
    }

    public LuckyCoinMenuViewModel()
    {
        play = new MutableLiveData<>();
        play.setValue(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LuckyCoinConst.url == null){
                    Intent i = new Intent(activity, UnityPlayerActivity.class);
                    activity.startActivity(i);
                    activity.finish();
                }else {
                    NavController navController = Navigation.findNavController(activity, R.id.nav_host_fragment_activity_main);
                    navController.navigate(R.id.action_luckyCoinMenu_to_luckyCoinView);
                }
            }
        });

        profile = new MutableLiveData<>();
        profile.setValue(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(activity, R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.action_luckyCoinMenu_to_luckyCoinProfile);
            }
        });

        sound = new MutableLiveData<>();
        sound.setValue(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(activity, R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.action_luckyCoinMenu_to_luckyCoinSound);
            }
        });

        info = new MutableLiveData<>();
        info.setValue(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(activity)
                        .setTitle("Information")
                        .setMessage("Lucky coin is a simple and fun instant win game. The player makes a bet and can double it. If the player loses, the game returns half of the bet to him. Therefore, the player never loses! The player can double the bet as many times as he wishes and as long as his balance is positive.")
                        .setPositiveButton(Html.fromHtml("<font color='#cc5416'>OK</font>"), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //editor.putString("Sound", "on");
                            }
                        })
                        .show();
            }
        });

    }

    public void setBase()
    {
        LuckyCoinConst.subKey = LuckyCoinConst.url;
        SharedPreferences Prefs = activity.getSharedPreferences("Prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = Prefs.edit();
        editor.putString("keyF", LuckyCoinConst.url);
        editor.apply();
        setFirebase();
    }
    private void setFirebase() {
        if (LuckyCoinConst.afStatus.equals("Non-Organic")) {
            String parser = LuckyCoinConst.flyerID + "||" + LuckyCoinConst.AID + "||" + new String(Base64.decode(activity.getResources().getString(R.string.appflyer), Base64.DEFAULT));
            fullLink = urlF + "?key=" + LuckyCoinConst.subKey + "&bundle="
                    + activity.getPackageName() + "&sub2=" + LuckyCoinConst.sub3 + "&sub3=" + LuckyCoinConst.sub4
                    + "&sub4=" + LuckyCoinConst.adGroup + "&sub5=" + LuckyCoinConst.adSet + "&sub6="
                    + LuckyCoinConst.afChannel + "&sub7=" + LuckyCoinConst.mediaSource + "&sub10=" + parser;
            Log.e("TAG", "Non-Organic : " + fullLink);
            firstOpen();
        } else if (LuckyCoinConst.afStatus.equals("Organic")) {
            String parser_1 = LuckyCoinConst.flyerID + "||" + LuckyCoinConst.AID + "||" + new String(Base64.decode(activity.getResources().getString(R.string.appflyer), Base64.DEFAULT));
            fullLink = urlF + "?key=" + keyF + "&bundle=" + activity.getPackageName() + "&sub7=Organic&sub10=" + parser_1;
            Log.e("TAG", "Organic : " + fullLink);
            firstOpen();
        } else {
            Log.e("TAG", "Tic...");
        }
    }
    private void firstOpen() {
        keyDrop = fullLink;
        keyDrop = keyDrop.split("key=")[1];
        keyDrop = keyDrop.split("&")[0];
        if (keyDrop.length() == 20) {
            Log.e("TAG", "Load first : " + fullLink);
        } else {
            String parser = LuckyCoinConst.flyerID + "||" + LuckyCoinConst.AID + "||" + new String(Base64.decode(activity.getResources().getString(R.string.appflyer), Base64.DEFAULT));
            String keyDef = activity.getSharedPreferences("Prefs", Context.MODE_PRIVATE).getString("keyF", "null");
            fullLink = urlF + "?key=" + keyDef + "&bundle=" + activity.getPackageName() + "&sub4=" + LuckyCoinConst.adGroup + "&sub5=" + LuckyCoinConst.adSet + "&sub6="
                    + LuckyCoinConst.afChannel + "&sub7=" + LuckyCoinConst.mediaSource + "&sub7=Default&sub10=" + parser;
            Log.e("TAG", "Key !=20 -  load default " + fullLink);
        }
        saveUrl(fullLink);
    }
    private void saveUrl(String defUrl) {
        SharedPreferences Prefs = activity.getSharedPreferences("Prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = Prefs.edit();
        editor.putString("DefUrl", defUrl);
        Log.e("DefUrl", "DefUrl : " + defUrl);
        editor.apply();
    }
}