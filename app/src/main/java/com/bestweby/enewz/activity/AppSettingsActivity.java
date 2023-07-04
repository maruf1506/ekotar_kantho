package com.bestweby.enewz.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

import com.bestweby.enewz.BuildConfig;
import com.bestweby.enewz.R;
import com.bestweby.enewz.app.BaseActivity;
import com.bestweby.enewz.cache.preference.AppPreference;
import com.bestweby.enewz.databinding.ActivityAppSettingsLayoutBinding;
import com.bestweby.enewz.helper.AppHelper;

/**
 * Created by Md Sahidul Islam on 15-May-19.
 */
public class AppSettingsActivity extends BaseActivity {

    private ActivityAppSettingsLayoutBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_app_settings_layout);

        setToolbar(binding.primaryToolbar.toolbar, binding.primaryToolbar.toolbarTitle, getString(R.string.toolbar_settings));
        getSupportFragmentManager().beginTransaction().replace(R.id.preference_settings, new AppPreferenceFragment()).commit();
    }

    public static class AppPreferenceFragment extends PreferenceFragmentCompat {

        PreferenceScreen preferenceScreen;
        Preference versionPref;
        ListPreference themePreference;
        ListPreference languagePreference;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            addPreferencesFromResource(R.xml.settings_preference);

            initVars();
            initView();
            initListener();
        }

        private void initVars() {
        }

        private void initView() {
            preferenceScreen = getPreferenceScreen();
            versionPref = getPreferenceManager().findPreference(getString(R.string.pref_app_version));
            themePreference = getPreferenceManager().findPreference(getString(R.string.pref_theme));
            languagePreference = getPreferenceManager().findPreference(getString(R.string.pref_language));

            int themeValue = AppPreference.getInstance(getActivity()).getInteger(getActivity().getResources().getString(R.string.pref_theme));
            String themeMode = getResources().getStringArray(R.array.app_theme_entries)[themeValue];
            themePreference.setSummary(themeMode);
            themePreference.setValue(String.valueOf(themeValue));

            int languageValue = AppPreference.getInstance(getActivity()).getInteger(getActivity().getResources().getString(R.string.pref_language));
            String languageCode = getResources().getStringArray(R.array.app_locality_entries)[languageValue];
            languagePreference.setSummary(languageCode);
            languagePreference.setValue(String.valueOf(languageValue));

            String versionName = BuildConfig.VERSION_NAME;
            versionPref.setSummary("v" + versionName);
        }

        private void initListener() {
            themePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    AppPreference.getInstance(getActivity()).setInteger(getActivity().getResources().getString(R.string.pref_theme), Integer.parseInt(String.valueOf(newValue)));

                    if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    }
                    return false;
                }
            });

            languagePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    AppPreference.getInstance(getActivity()).setInteger(getActivity().getResources().getString(R.string.pref_language), Integer.parseInt(String.valueOf(newValue)));
                    AppHelper.restartApplicaion(getActivity());
                    return false;
                }
            });
        }
    }
}
