package com.vula.vulkan.com.lcsound;

import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.vula.vulkan.com.R;
import com.vula.vulkan.com.databinding.LuckyCoinSoundFragmentBinding;

import static android.content.Context.MODE_PRIVATE;

public class LuckyCoinSoundViewModel extends ViewModel {

    Activity activity;
    MutableLiveData<View.OnClickListener> back, click;
    MutableLiveData<SharedPreferences> sharedPreferencesMutableLiveData;
    MutableLiveData<SharedPreferences.Editor> sharedPreferencesEditorMutableLiveData;
    String soundd;

    SharedPreferences myPrefrence;
    SharedPreferences.Editor editor;

    public void setActivity(Activity activity, String sound, LuckyCoinSoundFragmentBinding binding)
    {
        this.soundd = sound;
        this.activity = activity;
        myPrefrence = activity.getSharedPreferences("com.vula.vulkan.com.v2.playerprefs", MODE_PRIVATE);
        editor = myPrefrence.edit();
        back = new MutableLiveData<>();
        back.setValue(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(activity, R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.action_luckyCoinSound_to_luckyCoinMenu);
            }
        });

        click = new MutableLiveData<>();
        click.setValue(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(soundd.equals("On"))
                {
                    soundd = "Off";
                    binding.bttnClick.setImageDrawable(activity.getDrawable(R.drawable.off));
                    editor.putString("Sound", "Off");
                    editor.commit();
                }
                else
                {
                    soundd = "On";
                    binding.bttnClick.setImageDrawable(activity.getDrawable(R.drawable.on));
                    editor.putString("Sound", "On");
                    editor.commit();
                }
            }
        });

        sharedPreferencesMutableLiveData = new MutableLiveData<>();
        sharedPreferencesMutableLiveData.setValue(myPrefrence);
        sharedPreferencesEditorMutableLiveData = new MutableLiveData<>();
        sharedPreferencesEditorMutableLiveData.setValue(editor);
    }

    public MutableLiveData<SharedPreferences> getSharedPreferencesMutableLiveData() {
        return sharedPreferencesMutableLiveData;
    }

    public MutableLiveData<SharedPreferences.Editor> getSharedPreferencesEditorMutableLiveData() {
        return sharedPreferencesEditorMutableLiveData;
    }

    public MutableLiveData<View.OnClickListener> getBack() {
        return back;
    }

    public MutableLiveData<View.OnClickListener> getClick() {
        return click;
    }

    public LuckyCoinSoundViewModel()
    {

    }


}