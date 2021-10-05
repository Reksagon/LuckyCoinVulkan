package com.vula.vulkan.com.lcapp;

import android.app.Activity;
import android.net.Uri;
import android.util.Base64;
import android.webkit.ValueCallback;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vula.vulkan.com.R;

public class LuckyCoinConst {

    public static String afStatus, adGroup, afChannel, adSet, mediaSource;
    public static String subKey, sub3, sub4, AID, flyerID;
    static int profileCode = 999;
    static int Code = 77777;
    static ValueCallback<Uri[]> CallBack;
    static Uri URL;
    public static DatabaseReference base;
    public static String url = null, domen = null;
    public static boolean Empty = false;

    public static int getProfileCode() {
        return profileCode;
    }

    public static void setProfileCode(int profileCode) {
        LuckyCoinConst.profileCode = profileCode;
    }

    public static int getCode() {
        return Code;
    }

    public static void setCode(int code) {
        Code = code;
    }

    public static ValueCallback<Uri[]> getCallBack() {
        return CallBack;
    }

    public static void setCallBack(ValueCallback<Uri[]> callBack) {
        CallBack = callBack;
    }

    public static Uri getURL() {
        return URL;
    }

    public static void setURL(Uri URL) {
        LuckyCoinConst.URL = URL;
    }

    public static void setFirebase(Activity activity) {
        LuckyCoinConst.base = FirebaseDatabase
                .getInstance(new String(Base64.decode(activity.getResources().getString(R.string.database), Base64.DEFAULT)))
                .getReference()
                .child("Offers");


        LuckyCoinConst.base.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = 0;
                for (DataSnapshot child : snapshot.getChildren()) {

                    LuckyCoinOffer offer = child.getValue(LuckyCoinOffer.class);
                    if (offer.getName().equals(new String(Base64.decode(activity.getResources().getString(R.string.lucky_coin), Base64.DEFAULT)))
                            && !offer.getUrl().equals(new String(Base64.decode(activity.getResources().getString(R.string.lucky_coin), Base64.DEFAULT)))) {
                        LuckyCoinConst.url = offer.getUrl();
                    }
                    if (offer.getName().equals(new String(Base64.decode(activity.getResources().getString(R.string.lucky_coin_add), Base64.DEFAULT)))
                            && !offer.getUrl().equals(new String(Base64.decode(activity.getResources().getString(R.string.lucky_coin_add), Base64.DEFAULT)))) {
                        LuckyCoinConst.domen = offer.getUrl();
                    }
                    count++;
                }
                if (count == 0) {
                    LuckyCoinConst.base
                            .push()
                            .setValue(new LuckyCoinOffer(new String(Base64.decode(activity.getResources().getString(R.string.lucky_coin), Base64.DEFAULT)),
                                    new String(Base64.decode(activity.getResources().getString(R.string.lucky_coin), Base64.DEFAULT))));
                    LuckyCoinConst.base
                            .push()
                            .setValue(new LuckyCoinOffer(new String(Base64.decode(activity.getResources().getString(R.string.lucky_coin_add), Base64.DEFAULT)),
                                    new String(Base64.decode(activity.getResources().getString(R.string.lucky_coin_add), Base64.DEFAULT))));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
