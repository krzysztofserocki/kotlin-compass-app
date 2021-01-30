package pl.kserocki.kotlin_compass_app.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.kserocki.kotlin_compass_app.di.scopes.FragmentScope
import pl.kserocki.kotlin_compass_app.ui.compass.CompassFragment

@Module
abstract class CompassModule {
    @FragmentScope
    @ContributesAndroidInjector
    internal abstract fun compassFragment(): CompassFragment
}