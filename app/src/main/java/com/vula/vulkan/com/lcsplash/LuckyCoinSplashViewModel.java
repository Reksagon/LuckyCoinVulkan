package com.vula.vulkan.com.lcsplash;

import android.app.Activity;
import android.os.AsyncTask;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.vula.vulkan.com.R;
import com.vula.vulkan.com.databinding.LuckyCoinSplashFragmentBinding;
import java.util.concurrent.TimeUnit;
public class LuckyCoinSplashViewModel extends ViewModel {
    MutableLiveData<AsyncTask<Void, Integer, Void>> asyncTaskMutableLiveData;
    Activity activity;
    LuckyCoinSplashFragmentBinding binding;


    public void setActivity(Activity activity, LuckyCoinSplashFragmentBinding binding)
    {
        this.activity = activity;
        this.binding = binding;
    }

    public LuckyCoinSplashViewModel()
    {
        //setFirebase();
        asyncTaskMutableLiveData = new MutableLiveData<AsyncTask<Void, Integer, Void>>();
        asyncTaskMutableLiveData.setValue(new AsyncTask<Void,Integer,Void>()
        {

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                binding.luckycoinProgress.setProgressPercentage(values[0], true);
            }

            @Override
            protected void onPostExecute(Void unused) {
                NavController navController = Navigation.findNavController(activity, R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.action_luckyCoinSplash_to_luckyCoinMenu);
                super.onPostExecute(unused);
            }
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    for(int i = 0; i < 100; i+=2)
                    {
                        TimeUnit.MILLISECONDS.sleep(100);
                        publishProgress(i);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }

    public MutableLiveData<AsyncTask<Void, Integer, Void>> getAsyncTaskMutableLiveData() {
        return asyncTaskMutableLiveData;
    }


}