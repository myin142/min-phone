package myin.phone.config;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import myin.phone.views.home.HomeActivity;
import myin.phone.views.settings.apps.ManageAppsActivity;

@Module(includes = DatabaseModule.class)
public abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract HomeActivity homeActivity();

    @ContributesAndroidInjector
    abstract ManageAppsActivity editAppsActivity();

}