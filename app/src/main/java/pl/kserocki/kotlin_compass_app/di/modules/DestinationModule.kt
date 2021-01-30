package pl.kserocki.kotlin_compass_app.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.kserocki.kotlin_compass_app.di.scopes.FragmentScope
import pl.kserocki.kotlin_compass_app.ui.destination.DestinationFragment

@Module
abstract class DestinationModule {
    @FragmentScope
    @ContributesAndroidInjector
    internal abstract fun destinationFragment(): DestinationFragment
}