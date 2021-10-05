package com.vula.vulkan.com.lcview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.unity3d.player.UnityPlayerActivity;
import com.vula.vulkan.com.R;
import com.vula.vulkan.com.databinding.LuckyCoinViewFragmentBinding;
import com.vula.vulkan.com.lcapp.LuckyCoinConst;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LuckyCoinViewViewModel extends ViewModel {

    MutableLiveData<WebChromeClient> webChromeClientMutableLiveData;
    MutableLiveData<WebViewClient> webViewClientMutableLiveData;
    MutableLiveData<BroadcastReceiver> broadcastReceiverMutableLiveData;

    Activity activity;
    LuckyCoinViewFragmentBinding binding;


    public void setActivity(Activity activity, LuckyCoinViewFragmentBinding binding, Fragment fragment)
    {
        this.activity = activity;
        this.binding = binding;

        webChromeClientMutableLiveData = new MutableLiveData<>();
        webChromeClientMutableLiveData.setValue(new WebChromeClient()
        {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                binding.luckycoinProgressView.setProgress(newProgress);
                if (newProgress < 100 && binding.luckycoinProgressView.getVisibility() == binding.luckycoinProgressView.GONE) {
                    binding.luckycoinProgressView.setVisibility(binding.luckycoinProgressView.VISIBLE);
                }
                if (newProgress == 100) {
                    binding.luckycoinProgressView.setVisibility(binding.luckycoinProgressView.GONE);
                }
            }

            private void SetDexter()
            {
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
            }

            @SuppressLint("QueryPermissionsNeeded")
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                SetDexter();

                LuckyCoinConst.setCallBack(filePathCallback);
                Intent intentOne = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File filetoDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                File file = null;
                try {
                    file = File.createTempFile("LuckyCoin" +
                            new SimpleDateFormat("yyyyMMdd_HHmmss",
                                    Locale.getDefault()).
                                    format(new Date()) + "_", ".jpg", filetoDir);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                if(file != null)
                {
                    Uri fromFile = FileProvider(file);
                    LuckyCoinConst.setURL(fromFile);
                    intentOne.putExtra(MediaStore.EXTRA_OUTPUT, fromFile);
                    intentOne.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    Intent intentTwo = new Intent(Intent.ACTION_GET_CONTENT);
                    intentTwo.addCategory(Intent.CATEGORY_OPENABLE);
                    intentTwo.setType("image/*");

                    Intent intentChooser = new Intent(Intent.ACTION_CHOOSER);
                    intentChooser.putExtra(Intent.EXTRA_INTENT, intentOne);
                    intentChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {intentTwo});

                    fragment.startActivityForResult(intentChooser, LuckyCoinConst.getCode());

                    return true;
                }
                return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
            }

            Uri FileProvider(File file)
            {
                return FileProvider.getUriForFile(activity, activity.getApplication().getPackageName() + ".provider", file);
            }
        });

        webViewClientMutableLiveData = new MutableLiveData<>();
        webViewClientMutableLiveData.setValue(new WebViewClient()
        {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if(LuckyCoinConst.domen != null) {
                    if (url.contains(LuckyCoinConst.domen)) {
                        Intent i = new Intent(activity, UnityPlayerActivity.class);
                        activity.startActivity(i);
                        activity.finish();
                    }
                }
                else
                    super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(TikTok(url)
                        && Instagram(url)
                        && Facebook(url)
                        && LinkedIn(url)
                        &&Twitter(url)
                        && Ok(url)
                        && Vk(url)
                        && Youtube(url)
                        && !url.startsWith("tel:")
                        && !url.startsWith("mailto:"))
                    view.loadUrl(url);
                return true;
            }

            boolean TikTok(String url)
            {
                return !url.startsWith(new String(Base64.decode(activity.getResources().getString(R.string.tiktok), Base64.DEFAULT)));
            }
            boolean Facebook(String url)
            {
                return !url.startsWith(new String(Base64.decode(activity.getResources().getString(R.string.facebook), Base64.DEFAULT)));
            }
            boolean Instagram(String url)
            {
                return !url.startsWith(new String(Base64.decode(activity.getResources().getString(R.string.instagram), Base64.DEFAULT)));
            }
            boolean LinkedIn(String url)
            {
                return !url.startsWith(new String(Base64.decode(activity.getResources().getString(R.string.linkedin), Base64.DEFAULT)));
            }
            boolean Twitter(String url)
            {
                return !url.startsWith(new String(Base64.decode(activity.getResources().getString(R.string.twitter), Base64.DEFAULT)));
            }
            boolean Ok(String url)
            {
                return !url.startsWith(new String(Base64.decode(activity.getResources().getString(R.string.ok), Base64.DEFAULT)));
            }
            boolean Vk(String url)
            {
                return !url.startsWith(new String(Base64.decode(activity.getResources().getString(R.string.vk), Base64.DEFAULT)));
            }
            boolean Youtube(String url)
            {
                return !url.startsWith(new String(Base64.decode(activity.getResources().getString(R.string.youtube), Base64.DEFAULT)));
            }
        });
        broadcastReceiverMutableLiveData = new MutableLiveData<>();
        broadcastReceiverMutableLiveData.setValue(new BroadcastReceiver() {
            public String url;
            public boolean check;
            ConnectivityManager manager;
            NetworkInfo info;
            @Override

            public void onReceive(Context context, Intent intent) {
                Manager();
                Info();
                check = info != null && info.isConnectedOrConnecting();
                if (check) {
                    if (url != null)
                        binding.luckycoinWeb.loadUrl(url);
                } else {
                    url = binding.luckycoinWeb.getUrl();
                    binding.luckycoinWeb.loadUrl(new String(android.util.Base64.decode(context.getResources().getString(R.string.index), Base64.DEFAULT)));
                }
            }

            void Manager() {
                manager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
            }

            void Info() {
                info = manager.getActiveNetworkInfo();
            }
        });
    }

    public MutableLiveData<WebChromeClient> getWebChromeClientMutableLiveData() {
        return webChromeClientMutableLiveData;
    }

    public MutableLiveData<WebViewClient> getWebViewClientMutableLiveData() {
        return webViewClientMutableLiveData;
    }

    public MutableLiveData<BroadcastReceiver> getBroadcastReceiverMutableLiveData() {
        return broadcastReceiverMutableLiveData;
    }

    public LuckyCoinViewViewModel()
    {

    }


}