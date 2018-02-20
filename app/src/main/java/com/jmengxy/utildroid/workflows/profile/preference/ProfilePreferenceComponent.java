package com.jmengxy.utildroid.workflows.profile.preference;

import com.jmengxy.utildroid.app.AppComponent;
import com.jmengxy.utillib.architecture.dagger.FragmentScoped;

import dagger.Component;

/**
 * Created by jiemeng on 16/09/2017.
 */

@FragmentScoped
@Component(dependencies = AppComponent.class, modules = {ProfileReferenceModule.class})
public interface ProfilePreferenceComponent {
    void inject(ProfilePreferenceFragment profilePreferenceFragment);
}
