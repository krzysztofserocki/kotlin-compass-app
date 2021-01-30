package pl.kserocki.kotlin_compass_app.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.kserocki.kotlin_compass_app.di.scopes.FragmentScope
import pl.kserocki.kotlin_compass_app.ui.splash.SplashFragment

@Module
abstract class SplashModule {
    @FragmentScope
    @ContributesAndroidInjector
    internal abstract fun splashFragment(): SplashFragment
}