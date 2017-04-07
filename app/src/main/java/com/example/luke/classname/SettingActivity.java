package com.example.luke.classname;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Process;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.v7.widget.Toolbar;

/**
 * Created by LukeDada on 2017/3/20.
 */

public class SettingActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        //addPreferencesFromResource(R.xml.settings);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
    }
    public static class MyPreferenceFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(Bundle saveInstanceState)
        {
            super.onCreate(saveInstanceState);
            addPreferencesFromResource(R.xml.settings);

        }

    }

}
