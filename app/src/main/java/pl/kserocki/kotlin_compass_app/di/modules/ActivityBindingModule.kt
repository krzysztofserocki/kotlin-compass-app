package pl.kserocki.kotlin_compass_app.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.kserocki.kotlin_compass_app.di.scopes.ActivityScope
import pl.kserocki.kotlin_compass_app.ui.compass.CompassActivity
import pl.kserocki.kotlin_compass_app.ui.destination.DestinationActivity
import pl.kserocki.kotlin_compass_app.ui.splash.SplashActivity

@Module
internal abstract class ActivityBindingModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [CompassModule::class])
    internal abstract fun compassActivity(): CompassActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [DestinationModule::class])
    internal abstract fun destinationActivity(): DestinationActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [SplashModule::class])
    internal abstract fun splashActivity(): SplashActivity
}
